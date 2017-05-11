var yFieldTitle = '.';
Ext.define('bsmes.controller.ProcessReceiptTraceController', {
			extend : 'Ext.app.Controller',
			view : 'processReceiptTraceView',
			historyView : 'traceHistoryView',
			realReceiptChart : 'realReceiptChart',
			views : ['ProcessReceiptTraceView', 'TraceHistoryView', 'RealReceiptChart'],
			stores : ['ProcessReceiptTraceStore', 'ProcessQcTraceStore'],
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
							ref : 'processQcGrid',
							selector : "#processQcTraceGrid"
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
				if (me.getProcessQcGrid()) {
					me.getProcessQcGrid().setController(me);
				}

				if (me.getHistoryView()) {
					var record = Ext.create("bsmes.model.TraceFormRecord");
					me.getHistoryForm().loadRecord(record);
				} else {
				}

				var processReceiptGrid = me.getProcessReceiptGrid();
				processReceiptGrid.on('itemdblclick', me.traceHistory); // 双击显示历史记录
				processReceiptGrid.on('itemclick', me.itemClickTraceLive); // 单击显示实时记录
				// var processQcGrid = me.getProcessQcGrid();
				// processQcGrid.on('itemdblclick',me.traceHistory);

			},
			onSearch : function() {
				var me = this;
				var form = me.getSearchForm();
				form.updateRecord();
				var processReceiptStore = me.getProcessReceiptGrid().getStore();
				var equipCode = form.getRecord().get('equipCode');
				var processCode = form.getRecord().get('processCode');
				var productCode = '';
				var status = '';
				if (equipCode == null || equipCode == '') {
					// || processCode == null || processCode == ''
					Ext.Msg.alert(Oit.msg.WARN, '请选择一台设备！');
					return;
				}

				Ext.Ajax.request({
							url : 'workOrder/getProductByEquipCode?equipCode=' + equipCode.replace('_EQUIP', ''),
							success : function(response) {
								var responseText = Ext.decode(response.responseText);
								var product = responseText.product; // 设备状态
								var status = responseText.status; // 产品名称合同号
								switch (status) {
									case 'IN_PROGRESS' :
										status = '加工中';
										break;
									case 'IN_DEBUG' :
										status = '调试';
										break;
									case 'IDLE' :
										status = '空闲';
										break;
									case 'CLOSED' :
										status = '关机';
										break;
									case 'ERROR' :
										status = '故障';
										break;
									case 'IN_MAINTAIN' :
										status = '维修中';
										break;
									default :
										status = '空闲';
								};

								var productField = Ext.getCmp('productCodeId');
								productField.setValue(product);
								productField.setVisible(true);
								var statuField = Ext.getCmp('statusId');
								statuField.setValue(status);
								statuField.setVisible(true);
								// processReceiptStore.loadPage(1, {
								// params : form.getRecord().getData()
								// });
								processReceiptStore.getProxy().url = 'processReceiptTrace?equipCode=' + form.getRecord().get('equipCode')
								processReceiptStore.load({
											//									scope : this,
											//									params : form.getRecord().getData(),
											callback : function(records, operation, success) {
												Ext.Array.each(records, function(row, i) {
															if (row.get('receiptCode') == 'R_Length') {
																me.getProcessReceiptGrid().getSelectionModel().select(row);
																me.itemClickTraceLive(me.getProcessReceiptGrid(), row);
																return false;
															}
														});
											}
										})
							}
						});

				/**
				 * @date 2015/6/24
				 * @author DingXintao
				 * @description 修改工艺参数报表，屏蔽QC
				 */
				// var processQcStore = me.getProcessQcGrid().getStore();
				// processQcStore.loadPage(1, {
				// params : form.getRecord().getData()
				// });
			},
			onRealSearch : function() {
				var me = this;
				var processReceiptGrid = me.getProcessReceiptGrid();
				// var processQcGrid = me.getProcessQcGrid();
				var result = null;
				var selection = processReceiptGrid.getSelectionModel().getSelection();
				if (selection && selection.length > 0) {
					result = true;
				} else {
					// @description 修改工艺参数报表，屏蔽QC
					// selection = processQcGrid.getSelectionModel().getSelection();
					// if (selection && selection.length > 0) {
					// result = true;
					// }
				}
				if (result) {
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
				console.log(form);
				form.updateRecord();

				var startDate = Ext.getCmp("processReceiptTrace_startTime").getValue();
				console.log(startDate);
				if (startDate == null) {
					Ext.MessageBox.alert("提示", "请输入开始时间!");
					return;
				}
				var endDate = Ext.getCmp("processReceiptTrace_endTime").getValue();
				if (endDate == null) {
					Ext.MessageBox.alert("提示", "请输入结束时间!");
					return;
				}
				if (endDate < startDate) {
					Ext.MessageBox.alert("提示", "开始时间必须小于结束时间!");
					return;
				}

				/*
				 * if(form.isValid()){ var chart = me.getHistoryChart(); var
				 * store = chart.store; chart.show();
				 * chart.axes.items[0].setTitle(yFieldTitle);
				 * 
				 * var time =
				 * (form.getRecord().get("endTime")-form.getRecord().get("startTime"))/1000;
				 * if(time>0&&time<60){ //一分钟之内
				 * chart.axes.items[1].step=[Ext.Date.SECOND, 1]; }else
				 * if(time>=60&&time<3600){ //一小时之内
				 * chart.axes.items[1].step=[Ext.Date.MINUTE , 1]; }else
				 * if(time>=3600&&time<86400){ //一天之内
				 * chart.axes.items[1].step=[Ext.Date.HOUR , 1]; }else
				 * if(time>=86400&&time<604800){ //一星期之内
				 * chart.axes.items[1].step=[Ext.Date.DAY , 1]; }else
				 * if(time>=604800&&time<2678400){ //一个月之内
				 * chart.axes.items[1].step=[Ext.Date.MONTH , 1/4]; }else
				 * if(time>=2678400&&time<32140800){ //一年之内
				 * chart.axes.items[1].step=[Ext.Date.MONTH , 1]; }else{ //超过一年
				 * chart.axes.items[1].step=[Ext.Date.YEAR , 1]; }
				 * 
				 * 
				 * chart.axes.items[1].fromDate =startDate ;
				 * chart.axes.items[1].toDate =endDate;
				 * 
				 * store.loadPage(1, { params : form.getRecord().getData() }); }
				 */

				var formData = form.getRecord().getData();

				Ext.Ajax.request({
							url : 'processReceiptTrace/historyTrace',
							method : 'GET',
							async : false,
							params : {
								equipCode : me.getSearchForm().getRecord().getData().equipCode,
								processCode : formData.processCode,
								receiptCode : formData.receiptCode,
								type : formData.type,
								startTime : Ext.getCmp("processReceiptTrace_startTime").getValue(),
								endTime : Ext.getCmp("processReceiptTrace_endTime").getValue()
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								// historyData = result.historyData;
								// maxList = result.maxList;
								// minList = result.maxList;

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

			traceHistory : function(view, record, item, index, e, eOpts) {
				var me = this;
				// var controller = me.getController();
				var controller = Ext.ComponentQuery.query('#processReceiptGrid')[0].getController();
				var form = controller.getSearchForm();
				var formRecord = form.getRecord();
				if (record.get("receiptName")) {
					yFieldTitle = record.get("receiptName");
					formRecord.set('type', 'receipt');
					formRecord.set("receiptCode", record.get('receiptCode'));
				} else {
					yFieldTitle = record.get("checkItemName");
					formRecord.set('type', 'qc');
					formRecord.set("receiptCode", record.get('checkItemCode'));
				}

				formRecord.set("processCode", form.getRecord().get('processCode'));
				controller.getHistoryView().show();
				controller.getHistoryForm().loadRecord(formRecord);
				var stratDate = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
				var endDate = new Date();
				Ext.getCmp("processReceiptTrace_startTime").setValue(stratDate);
				Ext.getCmp("processReceiptTrace_endTime").setValue(endDate);

				controller.onHistorySearch();
			},

			/**
			 * 创建实时工艺参数图表
			 * 
			 * @param data 工艺参数选中记录record
			 * @param panelId 图标面板ID，显示在此处
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
				var equipCombs = Ext.getCmp('equipCodeComb');
				// var processCombs = Ext.getCmp('processReceiptTrace_process');
				// var real_title = equipCombs.rawValue + " " + processCombs.rawValue +
				// " " + receiptName;
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
								var realData = result.realData;
								var yPlotLines = result.yPlotLines; // 警戒线
								var yPlotLinesItem = [], miny = null, maxy = null;
								if (yPlotLines) {
									Ext.Array.each(yPlotLines, function(value, i) {
												yPlotLinesItem.push({
															color : 'red',
															width : 2,
															value : value
														});
											});
									if (yPlotLines.length > 1) {
										miny = Number(yPlotLines[0]) * 0.9;
										maxy = Number(yPlotLines[yPlotLines.length - 1]) * 1.1;
									}
								};
								$('#' + panelId).highcharts({
									chart : {
										type : 'spline',
										animation : Highcharts.svg, // don't animate in old IE
										marginRight : 10,
										events : {
											load : function() {
												var series = this.series[0];
												me.intervalTime = setInterval(function() {
															Ext.Ajax.request({
																		url : 'processReceiptTrace/realReceipt',
																		method : 'POST',
																		async : false,
																		params : {
																			equipCode : data.equipCode,
																			receiptCode : data.receiptCode
																			// type:type
																		},
																		success : function(response) {
																			if (response.responseText) {
																				var result = Ext.decode(response.responseText);
																				var x = new Date(result.date).getTime(), y = result.realData
																						* 1;
																				if (y && y != '') {
																					series.addPoint([x, y], true, true);
																				}
																			} else {
																				return;
																			}
																		}
																	});
															var chart = $('#' + panelId).highcharts();
															//chart.yAxis[0].setExtremes(series.dataMin * 0.9, series.dataMax * (1.1));
															chart.yAxis[0]
																	.setExtremes(((miny && miny < series.dataMin * 0.9)
																					? miny
																					: series.dataMin * 0.9), ((maxy && maxy > series.dataMax
																					* (1.1)) ? maxy : series.dataMax * (1.1)));
														}, 5000);
											}
										}
									},
									title : {
										text : real_title
									},
									xAxis : {
										type : 'datetime',
										labels : {
											step : 1,
											formatter : function() {
												var date = new Date(this.value)
												return Ext.util.Format.date(date, 'H:i:s');
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
										plotLines : yPlotLinesItem
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
												color : 'green'
											}]
								});
								var chart = $('#' + panelId).highcharts();
								chart.yAxis[0].setExtremes(((miny && miny < chart.series[0].dataMin * 0.9) ? miny : chart.series[0].dataMin
												* 0.9), ((maxy && maxy > chart.series[0].dataMax * (1.1)) ? maxy : chart.series[0].dataMax
												* (1.1)));
							}
						});
			},
			createHighChart : function(result) {
				var historyData = result.historyData;
				var maxList = result.maxList;
				var minList = result.minList;
				var equipCombs = Ext.getCmp('equipCodeComb');
				// var processCombs = Ext.getCmp('processReceiptTrace_process');
				// var names = equipCombs.rawValue + " " + processCombs.rawValue + " " +
				// yFieldTitle;
				var names = equipCombs.rawValue + "   " + yFieldTitle;
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
						}
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
			}
		});
