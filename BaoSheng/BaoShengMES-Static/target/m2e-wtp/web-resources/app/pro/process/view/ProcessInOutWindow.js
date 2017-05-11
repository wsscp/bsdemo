Ext.define('bsmes.view.ProcessInOutWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.processInOutWindow',
	title : '工序投入产出维护',
	width:document.body.scrollWidth-100,
	height:document.body.scrollHeight-100,
	modal : true,
	plain : true,
	requires : [ 'bsmes.view.ProcessInOutGrid' ],
	items : [ {
		xtype : 'processInOutGrid'
	} ]
});