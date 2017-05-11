Ext.define('bsmes.view.CheckOrderJYWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.checkOrderJYWindow',
	title : '查看生产单',
	width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-148,
	modal : true,
	plain : true,
	items : [ {
		xtype : 'checkOrderJYView'
	} ]
});
