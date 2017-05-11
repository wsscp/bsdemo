Ext.define("bsmes.view.EquipmentStatusChartColumn", {
	extend : 'Ext.panel.Panel',
	alias : 'widget.equipmentStatusChartColumn',
	title : Oit.msg.wip.realTimeEquipmentStatus.timeDiagram,
	// autoScroll : true,
	layout : 'anchor',
	columnHighChar : null, // 柱状图
	pieHighChar : null, // 饼图
	initComponent : function() {
		var me = this;
		var code = Ext.fly('equipInfo').getAttribute('code');
		var name = Ext.fly('equipInfo').getAttribute('equipName');

		me.items = [{
					xtype : 'equipmentStatusChartScheduler',
					startDate : new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0),
					endDate : new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 23, 59,
							59)
				}, {
					xtype : 'equipmentStatusChartColumnPercent'
				}];
		me.tbar = [{
					xtype : 'form',
					layout : 'column',
					defaults : {
						labelAlign : 'right'
					},
					items : [{
								fieldLabel : Oit.msg.wip.realTimeEquipmentStatus.searchTime,
								xtype : 'datefield',
								editable : false,
								name : 'startDate',
								value : Ext.util.Format.date(new Date(), "Y-m-d"),
								format : 'Y-m-d'
							}, {
								fieldLabel : '结束时间',
								xtype : 'datefield',
								value : Ext.util.Format.date(new Date(), "Y-m-d"),
								name : 'endDate',
								editable : false,
								format : 'Y-m-d'
							}]
				}, {
					text : Oit.btn.search,
					iconCls : 'icon_search',
					scope : me,
					handler : me.search
				}, {
					text : Oit.btn.reset,
					iconCls : 'icon_reset',
					handler : function(e) {
						this.up("equipmentStatusChartColumn").down('form').getForm().reset();
					}
				}, {
					text : '导出',
					iconCls : 'icon_export',
					handler : function() {
						var stime = startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-"
								+ startDate.getDate();
						var etime = endDate.getFullYear() + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate();
						window.location.href = 'realEquipStatusChart/exportToXls/' + code + '/' + stime + '/' + etime;
					}
				}, '->', me.getBarText()];
		me.callParent(arguments);
	},
	listeners : {
		afterlayout : function(container, component, eOpts) {
			// console.log('--afterlayout--')
			// 1、刷新时序图 // 2、刷新柱状图和饼图
			this.search();
			//var startDate = endDate = Ext.util.Format.date(new Date(), "Y-m-d");
			// 1、刷新时序图
			//this.freshScheduler(startDate + ' 00:00:00', endDate + ' 23:59:59');
			// 2、刷新柱状图和饼图
			//this.freshPercentAndPie(startDate + ' 00:00:00', endDate + ' 23:59:59');
		},
		afterrender : function(component, eOpts) {
			// console.log('--afterrender--')
		}
	},
	constructor : function() {
		var me = this;
		me.callParent(arguments);

		// setInterval(function() {
		// me.search.apply(me);
		// }, 15000);

	},

	// 页面查询按钮
	search : function() {
		var me = this;
		var form = me.down('form').getForm();
		var startDate = form.findField('startDate').getRawValue() + ' 00:00:00';
		var endDate = form.findField('endDate').getRawValue() + ' 23:59:59';

		if (startDate > endDate) {
			Ext.Msg.alert('提示', '开始时间不能大于结束时间');
			return;
		}
		// 1、刷新时序图
		me.freshScheduler(startDate, endDate);
		// 2、刷新: 柱状图\饼图
		me.freshPercentAndPie(startDate, endDate);
	},

	// 刷新时序图
	freshScheduler : function(startTime, endTime) {
		var me = this;
		var code = Ext.fly('equipInfo').getAttribute('code');
		var scheduler = this.down('equipmentStatusChartScheduler');
		var resourceStore = scheduler.resourceStore;
		resourceStore.load({
					params : {
						"equipCode" : code
					}
				});
		var eventStore = scheduler.eventStore;
		eventStore.load({
					params : {
						"equipCode" : code,
						"startTime" : startTime,
						"endTime" : endTime
					}
				});

		// 日期格式修改主要是变更为通用的，方便字符串日期转Date
		startTime =	startTime.replace(/-/g,'/'); 
		endTime =	endTime.replace(/-/g,'/'); 
		scheduler.switchViewPreset('minuteAndHour', new Date(Date.parse(startTime)), new Date(Date.parse(endTime)));
	},

	// 刷新加工时间统计：柱状图\饼图
	freshPercentAndPie : function(startTime, endTime) {
		var me = this;
		Ext.Ajax.request({
					url : 'realEquipStatusChart/equipHistoryColumnPercent',
					method : 'GET',
					params : {
						'equipCode' : Ext.fly('equipInfo').getAttribute('code'),
						'startTime' : startTime,
						'endTime' : endTime
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (me.columnHighChar) {
							me.columnHighChar.series[0].setData(result.columnData);
						} else {
							me.createColumnHighChar(result.columnData);
						}
						if (me.pieHighChar) {
							me.pieHighChar.series[0].setData(result.pieData);
						} else {
							me.createPieHighChar(result.pieData);
						}
					}
				});
	},

	// 创建柱状图
	createColumnHighChar : function(columnData) {
		var me = this;
		me.columnHighChar = $('#columnChartId').highcharts({
					chart : {
						type : 'column',
						options3d : {
							enabled : true,
							alpha : 10,
							beta : 15,
							depth : 50,
							viewDistance : 25
						}
					},
					title : {
						text : ''
					},
					xAxis : {
						// categories : names
						type : 'category'
					},
					yAxis : {
						title : {
							text : '<span style="font-size:14px;">工 作 时 间 ( 小 时 )</span>',
							style : {
								fontWeight : 'bold',
								fontSize : 20
							}
						}
					},
					credits : {
						text : ''
					},
					plotOptions : {
						column : {
							cursor : 'pointer',
							dataLabels : {
								enabled : true,
								style : {
									fontWeight : 'bold',
									fontSize : 12
								},
								formatter : function() {
									return this.y;
								}
							}
						}
					},
					tooltip : {
						formatter : function() {
							return '设备状态: ' + this.x + '<br>时间(小时):  ' + this.y;
						}
					},
					series : [{
								name : '<span style="font-size:14px;">待机/关机/故障/调试/加工(小时)</span>',
								data : columnData,
								color : 'white'
							}],
					exporting : {
						enabled : false
					}
				}).highcharts();
	},

	// 创建饼图
	createPieHighChar : function(pieData) {
		var me = this;
		me.pieHighChar = $('#pieChartId').highcharts({
					chart : {
						type : 'pie',
						options3d : {
							enabled : true,
							alpha : 45,
							beta : 0
						}
					},
					title : {
						text : ''
					},
					credits : {
						text : ''
					},
					tooltip : {
						pointFormat : '{series.name}: <b>{point.percentage:.1f}</b>'
					},
					plotOptions : {
						pie : {
							allowPointSelect : true,
							cursor : 'pointer',
							depth : 35,
							dataLabels : {
								enabled : true,
								format : '{point.name}'
							}
						}
					},
					series : [{
								type : 'pie',
								name : '百分比(%)',
								data : pieData
							}],
					exporting : {
						enabled : false
					}
				}).highcharts();
	},

	// 获取bar的提示小信息
	getBarText : function() {
		return {
			xtype : 'panel',
			html : '<div style="width:295px;height:16px;">'
					+ '<span style="display:block;float:left;">'
					+ Oit.msg.wip.realTimeEquipmentStatus.status.process
					+ '</span><span style="margin-right:6px;margin-left:3px;margin-top: 2px;width:14px;height:14px;background-color:#32B168;display:block;float:left;border:1px solid #32B168;"></span>'
					+ '<span style="display:block;float:left;">'
					+ Oit.msg.wip.realTimeEquipmentStatus.status.debug
					+ '</span><span style="margin-right:6px;margin-left:3px;margin-top: 2px;width:14px;height:14px;background-color:#57A0CC;display:block;float:left;border:1px solid #57A0CC;"></span>'
					+ '<span style="display:block;float:left;">'
					+ Oit.msg.wip.realTimeEquipmentStatus.status.idle
					+ '</span><span style="margin-right:6px;margin-left:3px;margin-top: 2px;width:14px;height:14px;background-color:#CAD638;display:block;float:left;border:1px solid #CAD638;"></span>'
					+ '<span style="display:block;float:left;">'
					+ Oit.msg.wip.realTimeEquipmentStatus.status.closed
					+ '</span><span style="margin-right:6px;margin-left:3px;margin-top: 2px;width:14px;height:14px;background-color:#C2C6C9;display:block;float:left;border:1px solid #C2C6C9;"></span>'
					+ '<span style="display:block;float:left;">'
					+ Oit.msg.wip.realTimeEquipmentStatus.status.error
					+ '</span><span style="margin-right:6px;margin-left:3px;margin-top: 2px;width:14px;height:14px;background-color:#E52E20;display:block;float:left;border:1px solid #E52E20;"></span>'
					+ '<span style="display:block;float:left;">'
					+ Oit.msg.wip.realTimeEquipmentStatus.status.maint
					+ '</span><span style="margin-right:6px;margin-left:3px;margin-top: 2px;width:14px;height:14px;background-color:#EEB12B;display:block;float:left;border:1px solid #EEB12B;"></span>'
					+ '</div>'
		}
	}

});
