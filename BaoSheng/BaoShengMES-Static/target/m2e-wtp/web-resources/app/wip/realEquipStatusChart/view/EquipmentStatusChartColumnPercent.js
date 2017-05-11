Ext.define("bsmes.view.EquipmentStatusChartColumnPercent", {
			extend : 'Ext.panel.Panel',
			alias : 'widget.equipmentStatusChartColumnPercent',
			width : document.body.scrollWidth,
			height : (document.body.scrollHeight - 125) / 3 * 2 - 20,
			layout : 'hbox',
			padding : '20 0 0 0',
			initComponent : function() {
				var me = this;
				me.items = [{
							xtype : 'panel',
							id : 'columnChartId',
							width : '60%',
							height : me.height
						}, {
							xtype : 'panel',
							id : 'pieChartId',
							width : '40%',
							height : me.height
						}];
				me.callParent(arguments);
			}
		});
