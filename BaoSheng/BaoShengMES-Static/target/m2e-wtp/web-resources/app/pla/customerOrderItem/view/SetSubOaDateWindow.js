Ext.define('bsmes.view.SetSubOaDateWindow',{
    extend:'Ext.window.Window',
    title: '可选设备',
    width:500,
    layout: 'fit',
    modal: true,
    plain: true,
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            buttons: [{
                itemId: 'ok',
                text:Oit.btn.ok,
                handler:function(){
                    var form = me.items.items[0]
                    if( form.getValues().subOaDate ){
                        Ext.Ajax.request({
                            url:'customerOrderItem/updateItemOaDate',
                            method:'POST',
                            params:form.getValues(),
                            success:function(response){
                                me.parentGrid.getStore().reload();
                                me.close();
                            }
                        });
                    }else{
                        Ext.Msg.alert(Oit.msg.WARN,'请选择订单交期');
                    }
                }
            }, {
                itemId: 'cancel',
                text:Oit.btn.cancel,
                scope: me,
                handler: me.close
            }]
        });

        me.items = [{
            xtype: 'form',
            bodyPadding: '12 10 10',
            defaultType: 'textfield',
            items: [{
                    xtype: 'displayfield',
                    labelAlign: 'right',
                    fieldLabel: Oit.msg.pla.customerOrderItem.productCode,
                    name:'salesOrderItem.productCode'},
                {
                    xtype: 'datefield',
                    fieldLabel: Oit.msg.pla.customerOrderItem.selectCustomerOaDate,
                    labelAlign: 'right',
                    name:'subOaDate',
                    format:'Y-m-d',
                    minValue:new Date(),
                    maxValue:new Date(me.parentOaDate)
                },
                {
                    xtype:'hiddenfield',
                    name:'id'
                }]
        }],

        this.callParent(arguments);
    }
});