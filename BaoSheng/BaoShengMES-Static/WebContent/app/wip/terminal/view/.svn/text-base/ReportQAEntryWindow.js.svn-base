Ext.define('bsmes.view.ReportQAEntryWindow',{
    extend:'Ext.window.Window',
    title:'下车检数据录入',
    alias:'widget.reportQAEntryWindow',
    modal : true,
    plain : true,
    width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-100,
    requires:['bsmes.view.ReportQAEntryList'],
    closeAction:'destory',
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            buttons: [{
                itemId: 'ok',
                text:Oit.btn.ok
            },'->',{
                itemId: 'cancel',
                text:Oit.btn.cancel,
                scope: me,
                handler: me.close
            }]
        });

        me.items = [{
            xtype: 'reportQAEntryList'
        }],

        this.callParent(arguments);
    }
});