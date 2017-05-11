Ext.define('bsmes.view.SparkRepairListWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.sparkRepairListWindow',
	title : '查看火花点',
	width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-100,
	modal : true,
	plain : true,
	requires : [ 'bsmes.view.SparkRepairList' ],
	items : [ {
		xtype : 'sparkRepairList'
	} ]
});
