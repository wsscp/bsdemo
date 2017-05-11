Ext.define('bsmes.view.ItemSplitWindow',{
    extend:'Ext.window.Window',
    title: Oit.msg.pla.customerOrderItem.orderSplit,
    width:500,
    layout: 'fit',
    modal: true,
    plain: true,
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            buttons: [{
                        xtype:'button',
                        text:Oit.btn.add,
                        handler:function(){
                            var panel = Ext.ComponentQuery.query("#splitItem")[0];
                            panel.add(ItemSplitSub);
                        }
                    },{
                        itemId: 'ok',
                        text:Oit.btn.ok,
                        handler:function(){
                            var form = me.items.items[0]
                            var values = form.getValues();
                            var flag = true;
                            var notNullSubLength = true;
                            if(Ext.isArray(values.subOrderLength)){
                                values.subOrderLengths = values.subOrderLength.join(',');
                                var sumLength = 0;
                                Ext.each(values.subOrderLength,function(length){
                                    if(length == null || length == ''){
                                        notNullSubLength = false;
                                    }
                                    sumLength+=parseInt(length);
                                });
                            }else{
                              if(values.subOrderLength == null || values.subOrderLength == ''){
                                  notNullSubLength = false;
                              }
                            }
                            if(sumLength != me.orderLength){
                                flag = false;
                            }
                            if (form.isValid()) {
                                if(notNullSubLength){
                                    if(flag){
                                        Ext.Ajax.request({
                                            url:'customerOrderItem/addOrderItem',
                                            method:'POST',
                                            params:values,
                                            success:function(response){
                                                me.parentGrid.getStore().reload();
                                                me.close();
                                            }
                                        });
                                    }else{
                                        Ext.Msg.alert(Oit.msg.WARN,Oit.msg.pla.customerOrderItem.validationInfo.itemSplitSumLengthNotEqualsOrderLengthMsg);
                                    }
                                }else{
                                    Ext.Msg.alert(Oit.msg.WARN,Oit.msg.pla.customerOrderItem.validationInfo.itemSplitLengthIsNull);
                                }
                            }
                        }
                    }, {
                        itemId: 'cancel',
                        text:Oit.btn.cancel,
                        scope: me,
                        handler: me.close
                    }]
        });

        var ItemSplitSub = {
                            xtype: 'panel',
                            layout: 'hbox',
                            height:30,
                            items:[{
                                    xtype: 'numberfield',
                                    labelAlign: 'right',
                                    fieldLabel: Oit.msg.pla.customerOrderItem.orderSplitLength,
                                    name:'subOrderLength',
                                    maxValue:me.maxLength,
                                    minValue:me.idealMinLength
                                },
                                {xtype: 'component',width:10},
                                {
                                    xtype: 'button',
                                    text:Oit.btn.remove,
                                    handler:function(e){
                                        var panel = Ext.ComponentQuery.query("#splitItem")[0];
                                        panel.remove(this.up('panel'));
                                    }
                                }]
                            };

        var item = Ext.create('Ext.panel.Panel',{
            id:'splitItem',
            width:'100%',
            layout:'column',
            defaults:{
                width:350
            },
            items:[ItemSplitSub]
        });

        me.items = [{
            xtype:'form',
            bodyPadding: "12 10 10",
            items:[{
                        xtype:'displayfield',
                        labelAlign: 'right',
                        fieldLabel:Oit.msg.pla.customerOrderItem.orderLength,
                        name:'orderLength'
                    },{
                        xtype:'hiddenfield',
                        name:'id'
                    },
                    {
                        xtype:'panel',
                        layout:'vbox',
                        items:item
                    }
            ]
        }],
        this.callParent(arguments);
    }
});