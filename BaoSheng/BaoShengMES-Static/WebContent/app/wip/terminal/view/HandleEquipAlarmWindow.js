Ext.define('bsmes.view.HandleEquipAlarmWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.handleEquipAlarmWindow',
	title : '设备警报',
	width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-100,
	modal : true,
	plain : true,
	requires : [ 'bsmes.view.HandleEquipAlarmGrid' ],
	items : [ {
		xtype : 'handleEquipAlarmGrid'
	} ]
});
