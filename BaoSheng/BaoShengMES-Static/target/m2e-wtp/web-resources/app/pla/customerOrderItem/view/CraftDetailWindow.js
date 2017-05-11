Ext.define('bsmes.view.CraftDetailWindow',{
    extend:'Ext.window.Window',
    title: Oit.msg.pla.customerOrderItem.button.craftDetail,
    width: 1000,
    height: 600,
    modal: true,
    plain: true,
    layout: "border",
    initComponent:function(){
        var me = this;
        var record = me.record;
        var productCode = record.get("salesOrderItem.productCode");
        var craftData = null;
        console.log("productCode"+productCode);
        Ext.Ajax.request({
            url: '/bsmes/pla/customerOrderItem/findCraftProcess',
            method: 'GET',
            params:{
                'productCode':productCode
            },
            async: false,
            success: function (response) {
                craftData = Ext.decode(response.responseText);
            }
        });
        console.log(craftData);
        var store = Ext.create('Ext.data.TreeStore', {
            root: {expanded: true, children: craftData}
        });
        me.items = [{
                        region: "west",
                        collapsible: false,
                        split: true,
                        width: 300,
                        items: Ext.create('Ext.tree.Panel', {
                            store: store,
                            rootVisible: false,
                            listeners: {
                                itemclick: function (self, record, item, index, e, eOpts) {
                                    var processReceiptGrid = Ext.ComponentQuery.query('#processReceiptGrid')[0];
                                    processReceiptGrid.getStore().load({params: {processId: record.getData().id}});

                                    var processQcGrid = Ext.ComponentQuery.query('#processQcGrid')[0];
                                    processQcGrid.getStore().load({params: {processId: record.getData().id}});

                                    var processInOutGrid = Ext.ComponentQuery.query('#processInOutGrid')[0];
                                    processInOutGrid.getStore().load({params: {processId: record.getData().id}});
                                }
                            }
                        })
                    },
                    {
                        region: "center",
                        split: true,
                        width: 700,
                        items: Ext.create('Ext.panel.Panel', {
                                width: '100%',
                                height: 560,
                                layout: 'accordion',
                                items: [
                                    {
                                        title: Oit.msg.pla.customerOrderItem.craftParameter,
                                        xtype:'processReceiptList',
                                        itemId:'processReceiptGrid'
                                    },
                                    {
                                        title: Oit.msg.pla.customerOrderItem.qualityParameter,
                                        xtype:'processQcList',
                                        itemId:'processQcGrid'
                                    },
                                    {
                                        title: Oit.msg.pla.customerOrderItem.BOM,
                                        xtype:'processInOutList',
                                        itemId:'processInOutGrid'
                                    }
                                ]
                            }
                        )
                    }];
        me.callParent(arguments);
    }
});