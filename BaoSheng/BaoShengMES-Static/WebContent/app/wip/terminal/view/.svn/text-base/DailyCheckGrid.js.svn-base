Ext.define("bsmes.view.DailyCheckGrid", {
			extend : 'Ext.grid.Panel',
			id : 'dailyCheckGridId',
			alias : 'widget.dailyCheckGrid',
			title : "质检信息",
			store : 'DailyCheckStore',
			itemId : 'dailyCheckGrid',
			forceFit : true,
			rowLines : true,
			columnLines : true,
			dataArray : null, // 加载数据对象
			columns : [{
						text : "检测类型",
						dataIndex : 'equipCode',
						flex : 1
					}, {
						text : '检测时间',
						dataIndex : 'finishTime',
						flex : 1,
						xtype : 'datecolumn',
						format : 'Y-m-d H:i'
					}],
			initComponent : function() {
				var me = this;
				this.callParent(arguments);
				me.getStore().loadData(me.dataArray) // 加载数据
			}
		});