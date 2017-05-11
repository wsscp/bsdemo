
Ext.define("bsmes.view.PersonalMonitorPanel", {
		extend : 'Ext.tab.Panel',
		autoScroll : true,
		alias : 'widget.personalMonitorPanel',
		itemId : 'personalMonitorPanel',
		width : document.body.scrollWidth,
		height : document.body.scrollHeight,
		modal : true,
		plain : true,
		items : [{
			title : '人员监控日报',
			xtype: 'dailyPersonalMonitorGrid'
		},{
			title : '人员监控月报',
			xtype: 'monthPersonalMonitorGrid'
		},{
			title : '人员考勤报表',
			xtype: 'creditCardGrid'
		}],
		initComponent : function() {
			var me = this;
			this.callParent(arguments);
		}
});