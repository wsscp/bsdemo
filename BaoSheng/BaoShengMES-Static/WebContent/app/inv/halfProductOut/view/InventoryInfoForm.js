Ext.define('bsmes.view.InventoryInfoForm',{
    extend : 'Ext.panel.Panel',
    alias : 'widget.inventoryInfoForm',
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
                    items:[{
                                xtype: 'textfield',
                                fieldLabel: Oit.msg.inv.halfProductOut.barCode,
                                labelAlign:'right',
                                name:'barCode',
                                id:'barCodeField',
                                labelWidth : 150,
                                width:500
                            },
                            {
                                xtype: 'component',
                                width:10},
                            {
                                xtype: 'button',
                                text:Oit.btn.ok,
                                handler:function(){
                                    var barCode = Ext.getCmp('barCodeField').getValue();
                                    if(Ext.isEmpty(barCode)){
                                        return;
                                    }
                                    Ext.Ajax.request({
                                        url:'halfProductOut/inventoryInfo',
                                        method:'POST',
                                        params:{
                                            barCode:barCode
                                        },
                                        success:function(response){
                                            var result = Ext.decode(response.responseText);
                                            var infoModel = Ext.create('bsmes.model.InventoryInfo', result);
                                            var form = Ext.getCmp('inventoryInfoForm');
                                            form.loadRecord(infoModel);
                                        },
                                        failure:function(response,action){}
                                    });
                                }
                            }]
                    }]
        },{
            xtype: 'fieldset',
            title:Oit.msg.inv.halfProductOut.detailTitle,
            width:'100%',
            items:[{
                    xtype:'form',
                    id:'inventoryInfoForm',
                    defaults : {
                        labelAlign : 'right',
                        labelWidth : 150,
                        width:500
                    },
                    defaultType:'displayfield',
                    items:[{
                                fieldLabel:'物料代码',
                                name:'materialCode'
                            },{
                                fieldLabel:'物料名称',
                                name:'materialName'
                            },{
                                fieldLabel:'数量',
                                name:'quantity'
                            },{
                                fieldLabel: '冻结量',
                                name: 'lockedQuantity'
                            },{
                                fieldLabel:'仓库名称',
                                name:'warehouseName'
                            },{
                                fieldLabel:'库位',
                                name:'locationName'
                            },{
                                fieldLabel:'位置X',
                                name:'locationX'
                            },{
                                fieldLabel:'位置Y',
                                name:'locationY'
                            },{
                                fieldLabel:'位置Z',
                                name:'locationZ'
                            }]
                    }]
        }],
    buttons : [ {
        itemId : 'save',
        text : Oit.btn.ok,
        handler:function(){
            var barCode = Ext.getCmp('barCodeField').getValue();
            if(Ext.isEmpty(barCode)){
                return;
            }
            Ext.Ajax.request({
                url:'halfProductOut/productOut',
                method:'POST',
                params:{
                    barCode:barCode
                },
                success:function(){
                    Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.inv.halfProductOut.msg.outSuccess);
                    var infoModel = Ext.create('bsmes.model.InventoryInfo');
                    var form = Ext.getCmp('inventoryInfoForm');
                    form.loadRecord(infoModel);
                    Ext.getCmp('barCodeField').setValue('');
                },
                failure:function(){
                    Ext.MessageBox.alert(Oit.msg.WARN, Oit.msg.inv.halfProductOut.msg.outFailure);
                    Ext.getCmp('barCodeField').setValue('');
                }
            });
        }
    }]
});