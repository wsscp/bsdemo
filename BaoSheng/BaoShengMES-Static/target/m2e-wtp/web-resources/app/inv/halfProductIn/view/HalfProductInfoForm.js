Ext.define('bsmes.view.HalfProductInfoForm',{
    extend : 'Ext.panel.Panel',
    alias : 'widget.halfProductInfoForm',
    layout : 'vbox',
    padding:'10 10 10 10',
    items: [{
            xtype: 'fieldset',
            title:Oit.msg.inv.halfProductOut.scanBarCodeTitle,
            width:'100%',
            padding:'5 5 5 5',
            items: [{
                        xtype: 'panel',
                        layout: 'hbox',
                        height:30,
                        defaults:{
                            labelAlign:'right'
                        },
                        items:[{
                                    xtype: 'textfield',
                                    fieldLabel: Oit.msg.inv.halfProductOut.barCode,
                                    name:'barCode',
                                    id:'barCodeField',
                                    labelAlign:'right',
                                    labelWidth : 150,
                                    width:500
                                },
                                {
                                    xtype: 'component',
                                    width:10
                                },
                                {
                                    xtype: 'button',
                                    text:Oit.btn.ok,
                                    handler:function(){
                                        var barCode = Ext.getCmp('barCodeField').getValue();
                                        if(Ext.isEmpty(barCode)){
                                            return;
                                        }
                                        var comb = Ext.getCmp('warehouseComField');
                                        comb.clearValue();
                                        Ext.getCmp('locationComField').clearValue();
                                        var store =comb.getStore();
                                        store.getProxy().url = 'halfProductIn/warehouseList/'+barCode;
                                        store.reload();

                                        //load product Info .....

                                        Ext.Ajax.request({
                                            url:'halfProductIn/productDetail',
                                            method:'POST',
                                            params:{
                                                barCode:barCode
                                            },
                                            success:function(response){
                                                var result = Ext.decode(response.responseText);
                                                var infoModel = Ext.create('bsmes.model.ReportProductInfo', result);
                                                var form = Ext.getCmp('halfProductDetailForm');
                                                form.loadRecord(infoModel);
                                            },
                                            failure:function(response){
                                                var result = Ext.decode(response.responseText);
                                                Ext.MessageBox.alert(Oit.msg.PROMPT, result.msg);
                                            }
                                        });
                                    }
                                }]
                    },{
                        fieldLabel:'仓库',
                        id:'warehouseComField',
                        name:'warehouseId',
                        xtype : 'combobox',
                        displayField : 'warehouseName',
                        valueField: 'id',
                        labelAlign:'right',
                        labelWidth : 150,
                        store:'WarehouseStore',
                        width:500,
                        listeners: {
                            'change':function(o, newValue, oldValue, eOpts ){
                                var barCode = Ext.getCmp('barCodeField').getValue();
                                if(Ext.isEmpty(barCode)){
                                    return;
                                }
                                var comb = Ext.getCmp('locationComField');
                                comb.clearValue();
                                var store =comb.getStore();
                                store.getProxy().url = 'halfProductIn/locations/'+newValue+'/'+barCode;
                                store.reload();
                             },
                            'beforequery':function( queryEvent,eOpts ){
                                var barCode = Ext.getCmp('barCodeField').getValue();
                                if(Ext.isEmpty(barCode)){
                                    Ext.MessageBox.alert(Oit.msg.WARN, Oit.msg.inv.halfProductIn.msg.selectRefreshBarCode);
                                    return false;
                                }
                            }
                        }
                    },{
                        fieldLabel:'库位',
                        id:'locationComField',
                        name:'locationId',
                        xtype : 'combobox',
                        displayField : 'locationName',
                        valueField: 'id',
                        labelAlign:'right',
                        store:'LocationStore',
                        labelWidth : 150,
                        width:500,
                        listeners:{
                            'beforequery':function( queryEvent,eOpts ){
                                var warehouseId  = Ext.getCmp('warehouseComField').getValue();
                                if(Ext.isEmpty(warehouseId)){
                                    Ext.MessageBox.alert(Oit.msg.WARN, Oit.msg.inv.halfProductIn.msg.selectWarehouseMsg);
                                    return false;
                                }
                            }
                        }
                    }]
        },{
            xtype: 'fieldset',
            title:Oit.msg.inv.halfProductIn.productInfoTitle,
            width:'100%',
            items:[{
                xtype:'form',
                id:'halfProductDetailForm',
                defaults : {
                    labelAlign : 'right',
                    labelWidth : 150,
                    width:500
                },
                defaultType:'displayfield',
                items:[{
                            fieldLabel:'半产品代码',
                            name:'materialCode'
                        },{
                            fieldLabel:'半产品名称',
                            name:'materialName'
                        },{
                            fieldLabel:'长度',
                            name:'reportLength'
                        }]
            }]
        }],
    buttons : [ {
        itemId : 'save',
        text : Oit.btn.ok
    },{
        itemId:'print',
        text:Oit.btn.print
    }]
});