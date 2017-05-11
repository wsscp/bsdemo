/**
 * 弹出产品追溯明细
 */
Ext.define('bsmes.view.ProductTraceDeatilWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.productTraceDeatilWindow',
	width : document.body.scrollWidth,
	height : document.body.scrollHeight,
	title : '<a style="font-size: 16px;">产 品 追 溯 明 细</a>',
	titleAlign : 'center',
	bodyPadding : "12 10 10",
	layout : 'anchor',
	modal : true,
	plain : true,
	autoScroll : true,
	allDatas : null, // 页面加载所需要数据，open之前已经全部查询好了

	initComponent : function() {
		var me = this;

		Ext.apply(me, {
					buttons : ['->', {
								itemId : 'cancel',
								text : '关 闭',
								scope : me,
								handler : me.close
							}]
				});

		this.callParent(arguments);

		me.drawingComponent(); // 渲染页面
	},

	/**
	 * 渲染工序选择控件，渲染页面按钮
	 */
	drawingComponent : function() {
		var me = this;
		var data = me.allDatas; // 请求响应转换成json格式
		me.drawingBox(data.workOrderArray, data.qcArray, data.reportArray, data.equipWWMapArray);
		me.renderHighChar();
	},

	// 创建BOX
	drawingBox : function(workOrderArray, qcArray, reportArray, equipWWMapArray) {
		var me = this;
		var qcArrayMap = {}; // {生产单号:[qcdata]} qcBox
		var reportArrayMap = {}; // {生产单号:[reportdata]} reportBox
		var reportUserMap = {}; // {报工ID:[username]} reportBox的人
		var equipUserMap = {}; // {生产单号:{设备号:[username]}} equipBox
		var equipWWMapMap = {}; // {设备号:[equipWWMap]} 设备映射

		// 1、封装QC
		Ext.Array.each(qcArray, function(qc, n) {
					var qcArr = qcArrayMap[qc.WORK_ORDER_NO]
					if (qcArr) {
						qcArr.push(qc)
					} else {
						qcArrayMap[qc.WORK_ORDER_NO] = [qc];
					}
				});

		// 2、封装报工\生产单设备报工人
		var workRepId = {}; // 过滤报工记录用
		Ext.Array.each(reportArray, function(report, n) {
					var reportArr = reportArrayMap[report.WORK_ORDER_NO]
					if (reportArr) {
						if (!workRepId[report.WORK_ORDER_NO + report.ID]) {
							reportArr.push(report);
							workRepId[report.WORK_ORDER_NO + report.ID] = report;
						}
					} else {
						reportArrayMap[report.WORK_ORDER_NO] = [report];
						workRepId[report.WORK_ORDER_NO + report.ID] = report;
					}

					var reportUser = reportUserMap[report.ID]
					if (reportUser) {
						reportUser.push(report.USER_NAME);
					} else {
						reportUserMap[report.ID] = [report.USER_NAME];
					}

					var equipUser = equipUserMap[report.WORK_ORDER_NO]
					if (equipUser) {
						if (equipUser[report.EQUIP_CODE]) {
							if (!me.arrayContains(equipUser[report.EQUIP_CODE].USER_NAME, report.USER_NAME))
								equipUser[report.EQUIP_CODE].USER_NAME.push(report.USER_NAME)
						} else {
							equipUser[report.EQUIP_CODE] = {
								EQUIP_NAME : report.EQUIP_NAME,
								USER_NAME : [report.USER_NAME]
							};
						}
					} else {
						var m = {};
						m[report.EQUIP_CODE] = {
							EQUIP_NAME : report.EQUIP_NAME,
							USER_NAME : [report.USER_NAME]
						};
						equipUserMap[report.WORK_ORDER_NO] = m;
					}
				});

		// 3、报工添加报工人
		for (var no in reportArrayMap) {
			Ext.Array.each(reportArrayMap[no], function(report, n) {
						report.USER_NAME = reportUserMap[report.ID];
					});
		}

		// 4、封装设备映射关系
		Ext.Array.each(equipWWMapArray, function(equipWWMap, n) {
					var equipWWMapArr = equipWWMapMap[equipWWMap.EQUIP_CODE]
					if (equipWWMapArr) {
						equipWWMapArr.push(equipWWMap)
					} else {
						equipWWMapMap[equipWWMap.EQUIP_CODE] = [equipWWMap];
					}
				});

		me.equipWWMapMap = equipWWMapMap;

		var panelItems = [];
		Ext.Array.each(workOrderArray, function(workOrder, n) {
					var equipInfoBox = me.createEquipInfoBox(equipUserMap[workOrder.WORK_ORDER_NO]);
					var highCharBox = me.createHighCharBox(workOrder.WORK_ORDER_NO);
					var qcBox = me.createQCBox(workOrder.WORK_ORDER_NO, qcArrayMap[workOrder.WORK_ORDER_NO]);
					var reportBox = me.createReportBox(reportArrayMap[workOrder.WORK_ORDER_NO]);
					var qc_reportBox = me.createQcReportBox(qcBox, reportBox);
					var workOrderBoxItems = [equipInfoBox, highCharBox, qc_reportBox];
					var workOrderBox = me.createWorkOrderBox(workOrder, workOrderBoxItems);
					panelItems.push(workOrderBox)
				});
		me.items.addAll(panelItems);
	},

	// 创建生产单BOX
	createWorkOrderBox : function(workOrder, items) {
		var me = this;
		if(workOrder.IS_OLD_LINE == 1){
			return Ext.create('Ext.form.FieldSet', {
				title : workOrder.PROCESS_NAME + ' : ' + workOrder.WORK_ORDER_NO + '<font color=red>(陈线)</font>',
				workOrderNo : workOrder.WORK_ORDER_NO,
				name : 'WorkOrderBox',
				padding : '5',
				layout : 'hbox',
				items : []
			});
		}else if(workOrder.IS_SKIPED == 1){
			return Ext.create('Ext.form.FieldSet', {
				title : workOrder.PROCESS_NAME + ' : ' + workOrder.WORK_ORDER_NO + '<font color=red>(跳过)</font>',
				workOrderNo : workOrder.WORK_ORDER_NO,
				name : 'WorkOrderBox',
				padding : '5',
				layout : 'hbox',
				items : []
			});
		}else{
			return Ext.create('Ext.form.FieldSet', {
				title : workOrder.PROCESS_NAME + ' : ' + workOrder.WORK_ORDER_NO,
				workOrderNo : workOrder.WORK_ORDER_NO,
				name : 'WorkOrderBox',
				padding : '5',
				layout : 'hbox',
				items : items
			});
		}
	},

	// 创建设备信息BOX
	createEquipInfoBox : function(equipUser) {
		var me = this;

		var items = [];
		var index = 0;
		for (var equip in equipUser) {
			items.push(new Ext.form.FieldSet({
						layout : 'vbox',
						name : 'EquipInfoBox',
						width : '100%',
						equipCode : equip,
						equipName : equipUser[equip].EQUIP_NAME,
						items : [{
							xtype : 'button',
							baseCls : '',
							index : index,
							style : 'cursor: pointer;',
							width : '100%',
							text : '<font style="color:blue;font-weight:700;">'
									+ equipUser[equip].EQUIP_NAME + '</font>',
							handler : function() {
								var workOrderBox = this.up('[name="WorkOrderBox"]');
								me.renderBoxHighChar(workOrderBox, this.index);
							}
						}, {
							xtype : 'button',
							baseCls : '',
							width : '100%',
							text : equipUser[equip].USER_NAME.join()
						}]
					}));
			index++;
		}

		return Ext.create('Ext.panel.Panel', {
					layout : 'vbox',
					width : '12.5%',
					margin : '9 0 0 0',
					items : items
				})
	},

	// 创建QC和report的组建
	createQcReportBox : function(qcBox, reportBox) {
		var me = this;
		return Ext.create('Ext.panel.Panel', {
					layout : 'vbox',
					width : '37%',
					items : [reportBox, qcBox]
				});
	},

	// 创建质检信息BOX
	createQCBox : function(workOrderNo, qcData) {
		var me = this;
		var data = me.getQcData(qcData, 'OUT_CHECK'); // 默认取上车检数据
		return Ext.create('Ext.form.FieldSet', {
					title : '点检信息',
					padding : 5,
					margin : '0 0 0 5',
					width : '100%',
					items : [{
								xtype : 'grid',
								maxHeight : Math.floor((document.body.scrollHeight - 136) / 4),
								columnLines : true,
								store : Ext.create('Ext.data.Store', {
											fields : ['CHECK_ITEM_NAME', 'QC_RESULT', 'EQUIP_NAME', 'COIL_NUM'],
											data : data
										}),
								columns : [{
											text : '检测项',
											flex : 2,
											dataIndex : 'CHECK_ITEM_NAME',
											renderer : function(value, metaData, record) {
												metaData.tdAttr = 'data-qtip="' + value + '"';
												return value;
											}
										}, {
											text : '结果',
											flex : 1,
											dataIndex : 'QC_RESULT'
										}, {
											text : '线盘号',
											flex : 1,
											hidden : true,
											dataIndex : 'COIL_NUM'
										}, {
											text : '设备',
											flex : 2,
											dataIndex : 'EQUIP_NAME',
											renderer : function(value, metaData, record) {
												metaData.tdAttr = 'data-qtip="' + value + '"';
												return '<font style="font-size:12px;">' + value + '</font>';
											}
										}],
								dockedItems : [{
											xtype : 'radiogroup',
											// columns : 3,
											// width : 150,
											// Math.floor((document.body.scrollWidth
											// - 65) * 0.324 / 4),
											items : [{
														boxLabel : '上车检',
														name : 'type' + workOrderNo,
														inputValue : 'IN_CHECK'
													}, {
														boxLabel : '中车检',
														name : 'type' + workOrderNo,
														inputValue : 'MIDDLE_CHECK'
													}, {
														boxLabel : '下车检',
														name : 'type' + workOrderNo,
														inputValue : 'OUT_CHECK',
														checked : true
													}],
											listeners : {
												change : function(component, newValue, oldValue, eOpts) {
													me.changeQcRadio(this.up('grid'), qcData, newValue['type'
																	+ workOrderNo]);
												}
											}
										}]
							}]
				})
	},

	// 创建报工信息BOX
	createReportBox : function(reportData) {
		var me = this;

		return Ext.create('Ext.form.FieldSet', {
					title : '报工信息',
					padding : 5,
					margin : '0 0 0 5',
					width : '100%',
					items : [{
								xtype : 'grid',
								maxHeight : Math.floor((document.body.scrollHeight - 136) / 4),
								columnLines : true,
								store : Ext.create('Ext.data.Store', {
											fields : ['CREATE_TIME', 'REPORT_LENGTH', 'USER_NAME', 'EQUIP_NAME',
													'COIL_NUM'],
											data : reportData
										}),
								columns : [{
											text : '报工时间',
											dataIndex : 'CREATE_TIME',
											flex : 1.5
										}, {
											text : '长度',
											flex : 1,
											dataIndex : 'REPORT_LENGTH'
										}, {
											text : '报工人',
											flex : 2,
											dataIndex : 'USER_NAME',
											renderer : function(value, metaData, record) {
												metaData.tdAttr = 'data-qtip="' + value + '"';
												return '<font style="font-size:12px;">' + value + '</font>';
											}
										}, {
											text : '线盘号',
											flex : 1,
//											hidden : true,
											dataIndex : 'COIL_NUM'
										}, {
											text : '设备',
											flex : 2,
											dataIndex : 'EQUIP_NAME',
											renderer : function(value, metaData, record) {
												metaData.tdAttr = 'data-qtip="' + value + '"';
												return '<font style="font-size:12px;">' + value + '</font>';
											}
										}]

							}]
				})

	},

	// 创建生产参数图表BOX
	createHighCharBox : function(workOrderNo) {
		var me = this;
		return Ext.create('Ext.form.FieldSet', {
					title : '设备工艺参数信息',
					width : '50%',
					padding : 5,
					layout : 'hbox',
					name : 'HighCharBox',
					margin : '0 0 0 5',
					items : [{
								xtype : 'panel',
								autoScroll : true,
								width : '15%',
								maxHeight : Math.floor((document.body.scrollHeight - 77) / 2),
								items : []
							}, {
								xtype : 'panel',
								id : 'hisHighChart' + workOrderNo,
								width : '85%',
								items : []
							}]
				})
	},

	// 查询生产单当前设备当前工艺参数的历史数据
	searchHighChartData : function(workOrderNo, equipCode, equipName, receiptCode, receiptName) {
		var me = this;
		var title = equipName + '[' + receiptName + ']';
		Ext.Ajax.request({
					url : 'productTrace/getDaHistory?workOrderNo=' + workOrderNo + '&equipCode=' + equipCode
							+ '&receiptCode=' + receiptCode,
					success : function(response) {
						var historyData = Ext.decode(response.responseText); // 请求响应转换成json格式
						me.createHighChart(title, workOrderNo, historyData);
					},
					failure : function(response, action) {
						Ext.Msg.alert(Oit.msg.PROMPT, '数据加载失败！');
					}
				});

	},

	// 创建HighChart图表
	createHighChart : function(title, workOrderNo, data) {
		var me = this;

		var historyData = data.historyData;
		var maxList = data.maxList;
		var minList = data.minList;
		$('#hisHighChart' + workOrderNo).highcharts({
			chart : {
				type : 'spline'
			},
			title : {
				useHTML : true,
				text : title
			},
			xAxis : {
				type : 'datetime',
				labels : {
					step : 1,
					formatter : function() {
						var v;
						var date = new Date(this.value);
						v = Ext.util.Format.date(date, 'm月d日 H:i');
						console.log(v);
						if(title.indexOf("击穿点位置")>=0){
							v = this.value
							console.log(v);
						}
						return v;
						
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
				min : 0
			},
			tooltip : {
				formatter : function() {
					var date = new Date(this.x);
					return '<b>' + this.series.name + ":" + this.y + '</b><br>'
							+ Ext.util.Format.date(date, 'Y-m-d H:i:s') + '<br>';
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

	// 渲染highChar
	renderHighChar : function() {
		var me = this;

		var workOrderBoxArray = Ext.ComponentQuery.query('[name="WorkOrderBox"]');
		Ext.Array.each(workOrderBoxArray, function(workOrderBox, n) {
					me.renderBoxHighChar(workOrderBox, 0); // 渲染一个workOrderBox的highChar
				});

	},

	// 渲染一个workOrderBox的highChar
	renderBoxHighChar : function(workOrderBox, index) {
		var me = this;
		var equipInfoBoxArray = workOrderBox.query('[name="EquipInfoBox"]');
		if (equipInfoBoxArray.length < index + 1) {
			return; // 超出下标退出循环
		}
		var equipInfoBox = equipInfoBoxArray[index];
		var equipCode = equipInfoBox.equipCode;
		var equipName = equipInfoBox.equipName;
		var equipWWMapArray = me.equipWWMapMap[equipCode + '_EQUIP'];
		var items = [];
		Ext.Array.each(equipWWMapArray, function(equipWWMap, m) {
			items.push(new Ext.Button({
				receiptCode : equipWWMap.RECEIPT_CODE,
				text : equipWWMap.RECEIPT_NAME,
				baseCls : '',
				style : 'border: 0px;cursor: pointer;border-style:none none solid none;margin: 5px 0 0 0;display:block;font-size:10px;color:blue;',
				handler : function() { // 注册点击工艺参数渲染图表事件
					me.searchHighChartData(workOrderBox.workOrderNo, equipWWMap.EQUIP_CODE, equipName,
							equipWWMap.RECEIPT_CODE, equipWWMap.RECEIPT_NAME);
				}
			}))
		});

		if (items.length > 0) {
			var highCharBox = workOrderBox.query('[name="HighCharBox"]')[0]; // 图表框
			var recepitPanel = highCharBox.query('panel')[0]; // 图表框左侧设备工艺参数
			var highCharPanel = highCharBox.query('panel')[1]; // 图表框右侧图表
			recepitPanel.removeAll();
			recepitPanel.items.addAll(items);
			recepitPanel.doLayout();
			highCharPanel.setHeight(Math.floor((document.body.scrollHeight - 77) / 2));
			// 初始化工艺参数图表，默认查询第一个工艺参数
			me.searchHighChartData(workOrderBox.workOrderNo, equipCode + '_EQUIP', equipName, items[0].receiptCode,
					items[0].text);
		}

	},

	// QC表radio改变事件
	changeQcRadio : function(grid, qcData, value) {
		var me = this;
		var data = me.getQcData(qcData, value);
		grid.getStore().loadData(data, false);
	},

	getQcData : function(qcData, value) {
		var me = this;
		var data = [];
		Ext.Array.each(qcData, function(qc, m) {
					if (qc.TYPE == value) {
						data.push(qc);
					}
				});
		return data
	},

	listeners : {
		afterrender : function(component, eOpts) {
			// var historyData = '[[1460995200000, 33235.4],[1461003182121,
			// 33549], [1461024087676, 34820],[1461024467777, 34820.1],
			// [1461029409090, 35106.4], [1461029789191, 35133.5],
			// [1461032830000, 20000]]';
			//
			// console.log(component.equipUserMap);
			// for (var workOrderNo in component.equipUserMap) {
			// component.createHighChart(workOrderNo, historyData);
			// };
		}
	},

	/**
	 * 判断数组中包含element元素
	 * 
	 * @param array 源数组
	 * @param element 可为数组或者单个
	 */
	arrayContains : function(array, element) {
		if (Object.prototype.toString.call(element) === '[object Array]') {
			for (var j = 0; j < element.length; j++) {
				var has = false;
				for (var i = 0; i < array.length; i++) {
					if (array[i] == element[j]) {
						has = true;
						break;
					}
				}
				if (!has) {
					return false;
				}
			}
			return true;
		} else {
			for (var i = 0; i < array.length; i++) {
				if (array[i] == element) {
					return true;
				}
			}
			return false;
		}
	}

});
