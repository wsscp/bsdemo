Ext.define('bsmes.view.ProductReportDetailsView',{
    extend: 'Ext.window.Window',
    alias:'widget.productReportDetailsView',
    id : 'productReportDetailsView',
    modal: true,
    plain: true,
    width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-100,
    equipCodes : null,
    items:[{
        xtype:'productReportDetailGrid'
    }]
});