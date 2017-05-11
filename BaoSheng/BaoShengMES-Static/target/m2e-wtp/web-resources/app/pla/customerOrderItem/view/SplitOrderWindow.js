Ext.define('bsmes.view.SplitOrderWindow',{
    extend:'Ext.window.Window',
    alias:'widget.splitOrderWindow',
    title: Oit.msg.pla.customerOrderItem.itemDecSplit,
    width:800,
    height:600,
    modal: true,
    overflowY:'auto',
    result:new Array(),
    requires:[
                'Ext.ux.form.MultiSelect',
                'Ext.ux.form.ItemSelector',
                'Ext.ux.ajax.JsonSimlet',
                'Ext.ux.ajax.SimManager'
    ],
    initComponent:function(){
        var me = this;
        Ext.apply(me, {
            buttons: [{
                itemId: 'ok',
                text:Oit.btn.ok,
                handler:function(){
                    var itemOffset = Ext.ComponentQuery.query('#itemOffset')[0];
                    //冲抵数据
                    var values = itemOffset.getValue();
                    console.log(itemOffset.getSubmitValue());

                    var sumLength = 0;

                    Ext.each(values,function(id){
                       var record =  me.dsStore.getById(id);
                       sumLength += record.get('length');
                    });

                    var offsetLength = sumLength;
                    var splitVolumePanel = Ext.ComponentQuery.query('#splitVolumePanel')[0];
                    var flag = true; //是否验证通过
                    var totalLength = me.record.get('orderLength'), // 总长度
                        minItemLength = me.record.get('salesOrderItem.idealMinLength'); // 每卷最小长度

                    var updateItemDec = new Array();
                    Ext.each(splitVolumePanel.items.items,function(item){
                       var array = item.items.items;
                       var itemDecLength = array[0].getValue();
                       sumLength+=itemDecLength;
                       var itemDecId = array[1].value;
                       
                      
                       if(!itemDecLength){
                           flag = false;
                           Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.customerOrderItem.validationInfo.dataNotValidate);
                           return;
                       }else if(sumLength > totalLength){
                           flag = false;
                           Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.customerOrderItem.validationInfo.lengthIsMore);
                           return;
                       }else if(itemDecLength < minItemLength){
                           flag = false;
                           Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.customerOrderItem.validationInfo.itemLengthIsLess);
                           return;
                       }
                       if(array[0].oldValue  != itemDecLength){
                           updateItemDec.push({'id':itemDecId,'decLength':itemDecLength});
                       }
                    });
                    
                    if(flag && sumLength < totalLength){
                       flag = false;
                       Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.customerOrderItem.validationInfo.lengthIsLess);
                       return;
                    }
                    
                    if(flag){
                        Ext.Ajax.request({
                            url:'customerOrderItem/saveCustomerOrderItem',
                            method:'POST',
                            params:{
                                'deleteJsonData':Ext.encode(me.result),
                                'updateJsonData':Ext.encode(updateItemDec),
                                'offsetJsonData':itemOffset.getSubmitValue(),
                                'orderItemId':me.record.get('id'),
                                'offsetItemDecId':me.offsetItemDecId,
                                'offsetLength':offsetLength
                            },
                            success:function(response){
                                me.close();
                            },
                            failure:function(){
                                Ext.Msg.alert(Oit.msg.WARN,'保存失败');
                            }
                        });
                    }
                }
            },{
                itemId: 'cancel',
                text:Oit.btn.cancel,
                scope: me,
                handler: me.close
            }]
        });

        var infoForm = Ext.create('bsmes.view.SplitOrderInfoForm');
        infoForm.loadRecord(me.record);

        var splitVolumePanel = Ext.create('bsmes.view.SplitVolume',{
            record:me.record,
            result:me.result
        });

        var productCode = me.record.get('salesOrderItem.productCode');
        var orderLength = me.record.get('orderLength');
        var idealMinLength = me.record.get('salesOrderItem.idealMinLength');
        var itemId = me.record.get('id');

        var ds = Ext.create('Ext.data.ArrayStore',{
            fields:['id','length'],
            proxy:{
                type:'ajax',
                url:'customerOrderItem/queryInventorys'
            },
            autoLoad:false
        });

        ds.load({
                params:{
                    productCode: productCode,
                    orderLength:orderLength,
                    idealMinLength:idealMinLength,
                    itemId:itemId
                }
        });

        me.dsStore = ds;

        var value = new Array();
        Ext.Ajax.request({
           url:'customerOrderItem/getItemOffsetDetail/'+itemId,
           method:'GET',
           async:false,
           success:function(response){
               var result = Ext.decode(response.responseText);
               console.log(result.success != false);
               if(result.success != false){
                   Ext.each(result,function(detail){
                       value.push(detail.id);
                       me.offsetItemDecId = detail.refId;
                   });
               }
           }
        });

        me.oldOffset = value;

        me.items = [{
            xtype:'panel',
            bodyPadding: "12 10 10",
            items:[{
                        xtype: 'fieldset',
                        title: Oit.msg.pla.customerOrderItem.productInfo,
                        anchor: '100%',
                        items:infoForm
                    },{
                        xtype:'fieldset',
                        title:'库存冲抵',
                        items:[{
                            xtype: 'itemselector',
                            name: 'itemselector',
                            itemId:'itemOffset',
                            anchor: '100%',
                            store: ds,
                            displayField: 'length',
                            valueField: 'id',
                            value: value,
                            buttons: ['add', 'remove']
                        }]
                    },{
                        xtype:'button',
                        text:Oit.btn.add,
                        handler:function(){
                            var panel = Ext.ComponentQuery.query("#splitVolumePanel")[0];
                            panel.add(formfieldObject());
                        }
                    },{
                        xtype: 'fieldset',
                        title : Oit.msg.pla.customerOrderItem.button.orderItemSpilt,
                        anchor: '100%',
                        layout:'vbox',
                        items :splitVolumePanel
                    }]
        }];
        me.callParent(arguments);
    }
});