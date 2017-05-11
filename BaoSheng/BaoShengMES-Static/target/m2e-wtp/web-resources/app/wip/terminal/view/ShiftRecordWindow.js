Ext.define('bsmes.view.ShiftRecordWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.shiftRecordWindow',
	title : '交班报表',
	width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-100,
    id: 'shiftRecordWindow',
	modal : true,
	plain : true,
	items : [ {
		xtype : 'shiftRecordView'
	} ]
});
