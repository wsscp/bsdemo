
Ext.define("bsmes.view.EquipEnergyMonitorPanel", {
		extend : 'Ext.tab.Panel',
		autoScroll : true,
		alias : 'widget.equipEnergyMonitorPanel',
		itemId : 'equipEnergyMonitorPanel',
		width : document.body.scrollWidth,
		height : document.body.scrollHeight,
		modal : true,
		plain : true,
		items : [
			{
			title : '能源监控详情',
			xtype: 'equipEnergyMonitorList'
		},
			{
			title : '能源日电量详情',
			xtype: 'equipEnergyLoad'
		},{
			title : '设备日负荷详情',
			xtype: 'equipEnergyQuantity'
		},{
			title : '能源月电量详情',
			xtype: 'equipMonthEnergyLoad'
		},{
			title : '能源月负荷详情',
			xtype : 'equipMonthEnergyQuantity'
		}],
		initComponent : function() {
			var me = this;
			this.callParent(arguments);
		}
});