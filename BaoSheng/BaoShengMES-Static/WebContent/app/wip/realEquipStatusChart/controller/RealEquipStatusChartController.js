Ext.define('bsmes.controller.RealEquipStatusChartController', {
			extend : 'Oit.app.controller.GridController',
			view : 'processReceiptTraceView',
			historyView : 'traceHistoryView',
			realReceiptChart : 'realReceiptChart',
			views : ['ProcessReceiptTraceView', 'TraceHistoryView', 'RealReceiptChart', 'RealEquipStatusChartList',
					'EquipmentStatusChartColumn', 'EquipmentStatusChartColumnPercent', 'EquipmentStatusChartLine',
					'EquipmentStatusChartOEE','EquipmentStatusChartYield', 'EquipmentStatusChartScheduler', 'ReportDetailGrid'],
			stores : ['ProcessReceiptTraceStore', 'ProcessQcTraceStore', 
			// 'EquipmentStatusChartColumnStore',
					'EquipmentStatusChartEventStore', 'EquipmentStatusChartResourceStore', 'EquipReportStore'],
			intervalTime : 0, // 循环执行数据的时间
			constructor : function() {
				var me = this;
				// 初始化refs
				me.refs = me.refs || [];
				me.refs.push({
							ref : 'searchForm',
							selector : me.view + " form"
						});
				me.refs.push({
							ref : 'processReceiptGrid',
							selector : "#processReceiptGrid"
						});
				me.refs.push({
							ref : 'realReceiptChart',
							selector : me.realReceiptChart,
							autoCreate : true,
							xtype : me.realReceiptChart
						});
				me.refs.push({
							ref : 'historyView',
							selector : me.historyView,
							autoCreate : true,
							xtype : me.historyView
						});
				me.refs.push({
							ref : 'historyForm',
							selector : me.historyView + " form"
						});
				me.refs.push({
							ref : 'historyChart',
							selector : me.historyView + " chart"
						});
				me.callParent(arguments);
				me.refresh();
//				setInterval(function() {
//							me.refresh.apply(me);
//						}, 30000);
			},
			init : function() {
				var me = this;
				if (!me.view) {
					Ext.Error.raise("A view configuration must be specified!");
				};
				// 初始化工具栏
				me.control(me.view + ' button[itemId=search]', {
							click : me.onSearch
						});
				me.control(me.view + ' button[itemId=realSearch]', {
							click : me.onRealSearch
						});
				me.control(me.view + ' button[itemId=historySearch]', {
							click : me.openTraceHistory
						});
				// 初始化工具栏
				me.control(me.historyView + ' button[itemId=historySearch]', {
							click : me.onHistorySearch
						});
				me.control(me.historyView + ' button[itemId=exportToXls]', {
							click : me.onExportToXls
						});

				// 添加实时数据曲线关闭监听
				me.getRealReceiptChart().on('close', me.onCloseIntervalTime, this);
				me.getRealReceiptChart().on('disable', me.onCloseIntervalTime, this);
				me.getRealReceiptChart().on('hide', me.onCloseIntervalTime, this);

			},

			// 关闭当前循环任务
			onCloseIntervalTime : function() {
				var me = this;
				clearInterval(me.intervalTime);
			},

			onLaunch : function() {
				var me = this;
				// 绑定searchForm record
				if (me.getSearchForm()) {
					var record = Ext.create("bsmes.model.TraceFormRecord");
					me.getSearchForm().loadRecord(record);
				}
				//
				if (me.getProcessReceiptGrid()) {
					me.getProcessReceiptGrid().setController(me);
				}
				if (me.getHistoryView()) {
					var record = Ext.create("bsmes.model.TraceFormRecord");
					me.getHistoryForm().loadRecord(record);
				} else {
				}

				var processReceiptGrid = me.getProcessReceiptGrid();
				processReceiptGrid.on('itemdblclick', me.traceHistory); // 双击显示历史记录
				processReceiptGrid.on('itemclick', me.itemClickTraceLive); // 单击显示实时记录
			},
			onRealSearch : function() {
				var me = this;
				var processReceiptGrid = me.getProcessReceiptGrid();
				var selection = processReceiptGrid.getSelectionModel().getSelection();
				if (selection && selection.length > 0) {
					me.onCloseIntervalTime();
					me.getRealReceiptChart().show();
					me.createRealChart(selection[0].data, 'realReceiptChart_id');
				}
			},

			/**
			 * 单击显示实时工艺参数
			 * 
			 */
			itemClickTraceLive : function(view, record, item, index, e, eOpts) {
				var me = this;
				var controller = Ext.ComponentQuery.query('#processReceiptGrid')[0].getController();
				controller.onCloseIntervalTime();
				controller.createRealChart(record.data, 'realReceiptChart_id1');
			},

			onHistorySearch : function() {
				var me = this;
				var form = me.getHistoryForm();
				var formRecord = form.getRecord();

				if (formRecord.get('startTime') == '') {
					Ext.MessageBox.alert("提示", "请输入开始时间!");
					return;
				}
				if (formRecord.get('endTime') == '') {
					Ext.MessageBox.alert("提示", "请输入结束时间!");
					return;
				}
				if (formRecord.get('endTime') < formRecord.get('startTime')) {
					Ext.MessageBox.alert("提示", "开始时间必须小于结束时间!");
					return;
				}
				Ext.Ajax.request({
							url : 'processReceiptTrace/historyTrace',
							method : 'GET',
							params : form.getValues(),
							success : function(response) {
								var result = Ext.decode(response.responseText);
								me.createHighChart(result);
							}
						});
			},
			onExportToXls : function() {
				var me = this;
				var form = me.getHistoryForm();
				form.updateRecord();
				var data = form.getValues();
				if (form.isValid()) {
					window.location.href = 'processReceiptTrace/' + data.equipCode + '/' + data.type + '/' + data.receiptCode
							+ '.action?startTime=' + data.startTime + '&endTime=' + data.endTime;
				}
			},

			/**
			 * 主页面按钮弹出历史记录
			 * 
			 */
			openTraceHistory : function() {
				var me = this;
				var processReceiptGrid = me.getProcessReceiptGrid();
				var selection = processReceiptGrid.getSelectionModel().getSelection();
				if (selection && selection.length > 0) {
					me.traceHistory(processReceiptGrid, selection[0]);
				}
			},

			/**
			 * 点击弹出历史记录的按钮方法
			 */
			traceHistory : function(view, record, item, index, e, eOpts) {
				var me = this;
				// var controller = me.getController();
				var startTime = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
				var endTime = new Date();
				var controller = Ext.ComponentQuery.query('#processReceiptGrid')[0].getController();
				controller.getHistoryView().show();
				controller.getHistoryForm().loadRecord(Ext.create('bsmes.model.TraceFormRecord', {
							equipCode : Ext.fly('equipInfo').getAttribute('code') + '_EQUIP',
							type : 'receipt',
							receiptCode : record.get('receiptCode'),
							receiptName : record.get('receiptName'),
							startTime : startTime,
							endTime : endTime
						}));
				controller.onHistorySearch();
			},

			/**
			 * 实时工艺参数曲线图
			 * 
			 * @param data 查询数据
			 * @param panelId 显示的面板ID
			 */
			createRealChart : function(data, panelId) {
				var me = this;
				var receiptName = data.receiptName;
				var type = '';
				if (receiptName != null && receiptName != '') {
					type = 'receipt';
				} else {
					type = 'qc';
					receiptName = data.checkItemName;
				}
				var equipCombs = Ext.getCmp('equipNameAll');
				var real_title = equipCombs.rawValue + "   " + receiptName;
				Ext.Ajax.request({
							url : 'processReceiptTrace/realReceiptChart',
							method : 'POST',
							async : false,
							params : {
								equipCode : data.equipCode,
								receiptCode : data.receiptCode,
								type : type
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								var realData = result.realData; // 曲线数据
								var yPlotLines = result.yPlotLines; // 警戒线
								var yPlotBandItem = [], miny = null, maxy = null;
								var uplimit;
								var downlimit;
								if (yPlotLines) {
									if (yPlotLines.length > 1) {
										Ext.Array.each(yPlotLines, function(value, i) {
											yPlotBandItem.push({
														color: '#FCFFC5',
														from: Number(yPlotLines[0]),
														to: Number(yPlotLines[yPlotLines.length - 1])
											});
										});
										miny = Number(yPlotLines[0]) * 0.9;
										maxy = Number(yPlotLines[yPlotLines.length - 1]) * 1.1;
									}
									uplimit=Number(yPlotLines[yPlotLines.length - 1]);
									downlimit=Number(yPlotLines[0]);
								}else{
									downlimit=-100;
									uplimit=1000000;
								}

								$('#' + panelId).highcharts({
									chart : {
										type : 'spline',
										animation : Highcharts.svg, // don't animate in old IE
										marginRight : 10,
										events : {
											load : function() {
												var series = this.series[0];
												me.intervalTime = setInterval(function() {
													        var current;
													        Ext.Ajax.request({
																url : 'processReceiptTrace/getServerTime',
																method : 'POST',
																async : false,
																success: function(response){
																	current=Ext.decode(response.responseText);
																}
															});
															Ext.Ajax.request({
																		url : 'processReceiptTrace/realReceipt',
																		method : 'POST',
																		async : false,
																		params : {
																			equipCode : data.equipCode,
																			receiptCode : data.receiptCode
																		},
																		success : function(response) {
																			if (response.responseText) {
																				var result = Ext.decode(response.responseText);
																				var x = new Date(result.date).getTime(), y = result.realData
																						* 1;
																				if (y && y != '') {
																					//Ext.Msg.alert(Oit.msg.PROMPT, Ext.util.Format.date(x, 'H:i:s'));
																					series.addPoint([current, y], true, true);
																				}
																			} else {
																				return;
																			}
																		}
																	});
															var chart = $('#' + panelId).highcharts();
															if(receiptName.indexOf('径')!=-1){
																chart.yAxis[0].setExtremes(((miny && miny < series.dataMin * 0.6)
																		? miny
																		: series.dataMin * 0.6), ((maxy && maxy > series.dataMax
																		* (1.6)) ? maxy : series.dataMax * (1.6)));
															}else{
																chart.yAxis[0].setExtremes(((miny && miny < series.dataMin * 0.9)
																		? miny
																		: series.dataMin * 0.9), ((maxy && maxy > series.dataMax
																		* (1.1)) ? maxy : series.dataMax * (1.1)));
															}
															
															//chart.xAxis[0].setExtremes(current-1000*60*5,current+1000*10);
														}, 5000);
											}
										}
									},
									plotOptions: {
										series: {
											marker: {
												enabled: true
											}
										}
										
									},
									title : {
										text : real_title
									},
									xAxis : {
										type : 'datetime',
										labels : {
											formatter : function() {
												var date = new Date(this.value)
												return Ext.util.Format.date(date, 'H:i:s');
											}
										},	
									   tickInterval: 1000*60*1
									},
									credits : {
										text : ''
									},
									yAxis : {
										title : {
											text : ''
										},
										allowDecimals:false,
										plotBands:yPlotBandItem
									},
									tooltip : {
										formatter : function() {
											var date = new Date(this.x);
											return '<b>' + this.series.name + ":" + this.y + '</b><br>时间:'
													+ Ext.util.Format.date(date, 'H:i:s') + '<br>';
										}
									},
									exporting : {
										enabled : false
									},
									series : [{
												name : '实时值',
												data : realData,
												zones: [{
									                value: downlimit,
									                color: 'red'
									            }, {
									                value: uplimit,
									                color: 'green'
									            }, {
									                color: 'red'
									            }]
											}]
								});
								var chart = $('#' + panelId).highcharts();
								//chart.yAxis[0].setExtremes(chart.series[0].dataMin * 0.9, chart.series[0].dataMax * (1.1));
								if(receiptName.indexOf('径')!=-1){
									chart.yAxis[0].setExtremes(((miny && miny < chart.series[0].dataMin * 0.6) ? miny : chart.series[0].dataMin
											* 0.6), ((maxy && maxy > chart.series[0].dataMax * (1.6)) ? maxy : chart.series[0].dataMax
											* (1.6)));
								}else{
									chart.yAxis[0].setExtremes(((miny && miny < chart.series[0].dataMin * 0.9) ? miny : chart.series[0].dataMin
											* 0.9), ((maxy && maxy > chart.series[0].dataMax * (1.1)) ? maxy : chart.series[0].dataMax
											* (1.1)));
								}
								
							}
						});
			},
			createHighChart : function(result) {
				var me = this;
				var historyData = result.historyData;
				var maxList = result.maxList;
				var minList = result.minList;
				var record = me.getHistoryForm().getRecord();
				console.log(record)
				var equipCombs = Ext.getCmp('equipNameAll');
				var names = equipCombs.rawValue + " " + record.get('receiptName');
				$('#receiptDataHighChart').highcharts({
					chart : {
						type : 'spline'
					},
					title : {
						text : names
					},
					xAxis : {
						type : 'datetime',
						labels : {
							step : 1,
							formatter : function() {
								var date = new Date(this.value)
								return Ext.util.Format.date(date, 'm月d日 H:i');
							}
						}
					},
					credits : {
						text : ''
					},
					yAxis : {
						title : {
							text : ''
						},
						min : 0,
						allowDecimals:false
					},
					tooltip : {
						formatter : function() {
							var date = new Date(this.x);
							return '<b>' + this.series.name + ":" + this.y + '</b><br>' + Ext.util.Format.date(date, 'Y-m-d H:i:s')
									+ '<br>';
						}
					},
					exporting : {
						enabled : false
					},
					series : [{
								name : '实时数据',
								data : historyData,
								color : 'green'
							}, {
								name : '参数上限',
								data : maxList,
								color : 'red'
							}, {
								name : '参数下限',
								data : minList,
								color : 'yellow'
							}]
				});
			},
			refresh : function() {
				var me = this;
				var equipCode = Ext.fly('equipInfo').getAttribute('code');
//				Ext.Ajax.request({
//							url : 'terminal/refresh/' + equipCode + '/',
//							timeout : 60000,
//							method : 'GET',
//							success : function(response) {
//								var result = Ext.decode(response.responseText);
//								if (result.success == false) {
//									Ext.Msg.alert(Oit.msg.PROMPT, result.message);
//								} else {
//									var infoModel = Ext.create('bsmes.model.MesClientEquipInfo', result);
//									var equipForm = Ext.ComponentQuery.query('#equipForm' + result.eqipId);
//									if (equipForm.length == 0) {
//										return;
//									}
//									equipForm[0].loadRecord(infoModel);
//								}
//							}
//						});
			}
		});
