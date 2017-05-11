Ext.define('bsmes.view.MultiReportDetailView',{
    extend: 'Ext.window.Window',
    alias:'widget.multiReportDetailView',
    modal: true,
    plain: true,
    width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-100,
    title:'报工记录',
    items:[{
        xtype:'multiReportDetailGrid'
    }]
});