/**
 * 绝缘
 */
Ext.define('bsmes.view.TaskInfoGridJY', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.taskInfoGridJY',
	layout : 'hbox',
	id : 'taskInfoGridJY',
	width : '100%',
	height : '100%',
	dataArray : null, // 加载数据对象
	workOrderInfo : null, // 生产单信息，set采集值，剩余长度等
	initComponent : function() {
		var me = this;

		var code = Ext.fly('processInfo').getAttribute('code');
		var isSteamLine = (code == "Steam-Line"); // 是否是蒸线工序

		var columns = [];
		if (code == "Respool") {
			columns = [{
						text : '颜色',
						dataIndex : 'COLOR',
						minWidth : 100,
						flex : 2,
						renderer : function(value, metaData, record, row, column) {
							return value;
						}
					}, {
						text : '投产长度',
						dataIndex : 'TASKLENGTH',
						flex : 2.5,
						minWidth : 125,
						renderer : function(value, metaData, record, row, column) {
							if (record.get('SPLITLENGTHROLEWITHYULIANG') && record.get('SPLITLENGTHROLEWITHYULIANG') != '') {
								if (value != record.get('SPLITLENGTHROLEWITHYULIANG')) {
									value += '=' + record.get('SPLITLENGTHROLEWITHYULIANG');
								}
							}
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							return value;
						}
					}, {
						text : '盘具类型',
						dataIndex : 'WIRECOIL',
						flex : 1.7,
						minWidth : 85,
						renderer : function(value, metaData, record, row, column) {
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							return value;
						}
					}, {
						text : '已完成长度',
						dataIndex : 'FINISHEDLENGTH',
						flex : 1.6,
						minWidth : 80,
						sortable : false,
						menuDisabled : true,
						renderer : function(value, metaData, record, row, column) {
							if (isNaN(value)) {
								value = 0;
							}
							return value;
						}
					}];

		} else if (code == 'wrapping_ymd') {
			columns = [{
						text : '投产长度',
						dataIndex : 'TASKLENGTH',
						flex : 2.5,
						minWidth : 125,
						renderer : function(value, metaData, record, row, column) {
							if (record.get('SPLITLENGTHROLEWITHYULIANG') && record.get('SPLITLENGTHROLEWITHYULIANG') != '') {
								if (value != record.get('SPLITLENGTHROLEWITHYULIANG')) {
									value += '=' + record.get('SPLITLENGTHROLEWITHYULIANG');
								}
							}
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							return value;
						}
					}, {
						text : '已完成长度',
						dataIndex : 'FINISHEDLENGTH',
						flex : 1.6,
						minWidth : 80,
						renderer : function(value, metaData, record, row, column) {
							return value;
						}
					}];
		} else {
			columns = [{
						text : '颜色',
						dataIndex : 'COLOR',
						flex : 2,
						minWidth : 100,
						renderer : function(value, metaData, record, row, column) {
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							return value;
						}
					}, {
						text : '模芯模套',
						dataIndex : 'OUTATTRDESC',
						flex : 1.3,
						minWidth : 65,
						renderer : function(value, metaData, record, row, column) {
							var json = Ext.decode(value);
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							value = typeof(json.moldCoreSleeve) == "undefined" ? '' : json.moldCoreSleeve;
							return value;
						}
					}, {
						text : '投产长度',
						dataIndex : 'TASKLENGTH',
						flex : 2.5,
						minWidth : 125,
						renderer : function(value, metaData, record, row, column) {
							if (record.get('SPLITLENGTHROLEWITHYULIANG') && record.get('SPLITLENGTHROLEWITHYULIANG') != '') {
								if (value != record.get('SPLITLENGTHROLEWITHYULIANG')) {
									value += '=' + record.get('SPLITLENGTHROLEWITHYULIANG');
								}
							}
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							return value;
						}
					}, {
						text : '已完成长度',
						dataIndex : 'FINISHEDLENGTH',
						flex : 1.6,
						minWidth : 80,
						renderer : function(value, metaData, record, row, column) {
							return value;
						}
					}];
		}

		// 左侧订单产品列表
		var westGrid = {
			xtype : 'grid',
			id: 'westGrid',
			invalidateScrollerOnRefresh: false, // extjs4 grid store.load 时不改变滚动条位置
			height : (document.body.scrollHeight - 45) / 2 - 45,
			store : Ext.create('Ext.data.Store', {
						fields : ['ID', 'TASKID', 'RELATEORDERIDS', 'PRODUCTCODE', 'CONTRACTNO', 'OPERATOR', 'PRODUCTTYPE', 'PRODUCTSPEC',
								'CUSTPRODUCTTYPE', 'CUSTPRODUCTSPEC', 'NUMBEROFWIRES', 'WIRESSTRUCTURE', 'SPLITLENGTHROLE',
								'WIRECOILCOUNT', 'COLOR', 'WIRECOIL', 'OUTMATDESC', 'OUTATTRDESC', 'STATUS', 'EQUIPCODE', {
									name : 'FINISHEDLENGTH',
									type : 'double'
								}, {
									name : 'TASKLENGTH',
									type : 'double'
								}, {
									name : 'ORDERLENGTH',
									type : 'double'
								}, {
									name : 'CONTRACTLENGTH',
									type : 'double'
								}],
						sorters : [{
									property : 'RELATEORDERIDS',
									direction : 'ASC'
								}]
					}),
			forceFit : true,
			columnLines : true,
			allowDeselect : true,
			columns : [{
				text : '合同号',
				dataIndex : 'CONTRACTNO',
				flex : 1.2,
				minWidth : 60,
				sortable : false,
				menuDisabled : true,
				renderer : function(value, metaData, record, row, column) {
					var reg = /[a-zA-Z]/g;
					value = (value.replace(reg, "").length > 5
							? value.replace(reg, "").substring(value.replace(reg, "").length - 5)
							: value.replace(reg, ""));
							//+ '[' + record.get('OPERATOR') + ']';
					metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					return value;
				}
			}, {
				text : '客户型号规格',
				dataIndex : 'CUSTPRODUCTTYPE',
				flex : 3,
				minWidth : 150,
				sortable : false,
				menuDisabled : true,
				renderer : function(value, metaData, record, row, column) {
					value = value + ' ' + record.get('CUSTPRODUCTSPEC');
					metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					return value;
				}
			}, {
				text : '火花颜色',
				dataIndex : 'COLOR',
				minWidth : 200,
				flex : 1.5,
				hidden : true,
				renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
					metaData.style = "white-space:normal";
					return value.replace(/,/g, ' ');
				}
			}, {
				text : '合同长度',
				dataIndex : 'CONTRACTLENGTH',
				flex : 1.7,
				minWidth : 85,
				sortable : false,
				menuDisabled : true,
				renderer : function(value, metaData, record, row, column) {
					var json = Ext.decode(record.get('OUTATTRDESC'));
					var splitLengthRole = typeof(json.splitLengthRole) == "undefined" ? '' : json.splitLengthRole;
					if (splitLengthRole != '') {
						value += '=' + splitLengthRole;
					}
					metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					return value;
				}
			}, {
				text : '导体结构',
				dataIndex : 'OUTATTRDESC',
				minWidth : 100,
				flex : 1,
				renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
					var json = Ext.decode(value);
					metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					value = typeof(json.conductorStruct) == "undefined" ? '' : json.conductorStruct;
					return value;
				}
			}],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				padding : '5 0 0 0',
				items : {
					xtype : 'form',
					itemId : 'baseInfoForm',
					width : '100%',
					url : 'saveValue',
					layout : 'vbox',
					defaults : {
						xtype : 'panel',
						width : '100%',
						layout : 'column',
						defaults : {
							width : 150,
							padding : 1,
							labelAlign : 'right',
							labelWidth : 90
						}
					},
					items : [{
						items : [{
									fieldLabel : '下达日期',
									xtype : 'displayfield',
									width : 180,
									name : 'releaseDate'
								}, {
									fieldLabel : '要求完成日期',
									xtype : 'displayfield',
									width : 210,
									labelWidth : 110,
									name : 'requireFinishDate'
								}, {
									fieldLabel : '时间',
									name : 'time',
									itemId : 'time',
									xtype : 'combobox',
									editable : false,
									width : 210,
									height : 25,
									displayField : 'value',
									valueField : 'value',
									store : new Ext.data.Store({
												fields : ['code', 'value'],
												autoLoad : true,
												data : [{
															code : '1',
															value : '1.5'
														}, {
															code : '2',
															value : '2'
														}, {
															code : '3',
															value : '2.5'
														}, {
															code : '4',
															value : '3'
														}, {
															code : '5',
															value : '3.5'
														}, {
															code : '6',
															value : '4'
														}, {
															code : '7',
															value : '4.5'
														}, {
															code : '8',
															value : '5'
														}, {
															code : '9',
															value : '5.5'
														}, {
															code : '10',
															value : '6'
														}, {
															code : '11',
															value : '6.5'
														}, {
															code : '12',
															value : '7'
														}, {
															code : '13',
															value : '7.5'
														}, {
															code : '14',
															value : '8'
														}]
											}),
									hidden : !isSteamLine

								}, {
									fieldLabel : '温度',
									labelWidth : 60,
									width : 180,
									margin : '0 95 0 0',
									name : 'temperature',
									editable : false,
									itemId : 'temperature',
									xtype : 'combobox',
									height : 25,
									displayField : 'value',
									valueField : 'value',
									store : new Ext.data.Store({
												fields : ['code', 'value'],
												autoLoad : true,
												data : [{
															code : '1',
															value : '60'
														}, {
															code : '2',
															value : '65'
														}, {
															code : '3',
															value : '70'
														}, {
															code : '4',
															value : '75'
														}, {
															code : '5',
															value : '80'
														}, {
															code : '6',
															value : '85'
														}, {
															code : '7',
															value : '90'
														}]
											}),
									hidden : !isSteamLine
								}, {
									xtype : 'button',
									itemId : 'saveValue',
									hidden : !isSteamLine,
									width : 70,
									text : '保存',
									margin : '0 10 0 0'
								}, {
									fieldLabel : '生产单长度',
									xtype : 'displayfield',
									name : 'planLength',
									hidden : isSteamLine,
									renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
										return Math.round(value);
									}

								}, {
									fieldLabel : '采集长度',
									xtype : 'displayfield',
									name : 'currentReportLength',
									hidden : isSteamLine,
									fieldStyle : {
										color : 'red'
									}
								}, {
									fieldLabel : '剩余长度',
									xtype : 'displayfield',
									hidden : isSteamLine,
									name : 'remainQLength'
								}, {
									name : 'workOrderNo',
									value : Ext.fly('orderInfo').getAttribute('num'),
									hidden : true
								}, {
									xtype : 'panel',
									width : 230,
									margin : '0 0 0 32',
									html : '<div style="height:38px;padding:5px 0px 0px 0px;">'
											+ '<span style="width:16px;height:16px;background-color:white;border:1px solid #B3C1CF;color:black;padding:5px;margin-right:5px;">待加工</span>'
											+ '<span style="width:16px;height:16px;background-color:green;border:1px solid #B3C1CF;color:white;padding:5px;margin-right:5px;">加工中</span>'
											+ '<span style="width:16px;height:16px;background-color:yellow;border:1px solid #B3C1CF;color:black;padding:5px;margin-right:5px;">已完成</span>'
											+ '<span style="width:16px;height:16px;background-color:grey;border:1px solid #B3C1CF;color:white;padding:5px;margin-right:5px;">已绑定</span>'
											+ '</div>'
								},{
									fieldLabel : '本班能耗(kwh)',
									xtype : 'displayfield',
									name : 'energyConsumptio',
									width : 210,
									labelWidth : 130,
									hidden : isSteamLine
								}]
					}]
				}
			}]
		};

		// 右侧任务TASK列表
		var eastGrid = {
			xtype : 'grid',
			invalidateScrollerOnRefresh: false, // extjs4 grid store.load 时不改变滚动条位置
			height : (document.body.scrollHeight - 45) / 2 - 45,
			store : 'TaskInfoStore',
			itemId : 'taskInfoGrid',
			forceFit : true,
			columnLines : true,
			allowDeselect : true,
			columns : columns,
			viewConfig : {
				stripeRows : false,
				getRowClass : function(record, rowIndex, rowParams, store) {
					var statusColorMap = {
						FINISHED : 'x-grid-record-yellow',
						IN_PROGRESS : 'x-grid-record-green',
						TO_DO : 'x-grid-record-white'
					}
					var status = record.get("STATUS");
					var equipCode = Ext.fly('equipInfo').getAttribute('code');
					if (status == 'IN_PROGRESS' && equipCode != record.get('EQUIPCODE')) {
						return 'x-grid-record-grey';
					} else {
						return statusColorMap[status];
					}
				}
			},
			listeners : {
				itemdblclick : function(me, record, index) {
					console.log(record);
					// 1、定义对象{statusColorMap:颜色状态map,columns:所点击的行对象,equipCode:设备编码,status:任务状态,store:任务grid的store}
					var statusColorMap = {
						FINISHED : 'x-grid-record-yellow',
						IN_PROGRESS : 'x-grid-record-green',
						TO_DO : 'x-grid-record-white'
					}, columns = me.getNode(index).childNodes;
					var equipCode = Ext.fly('equipInfo').getAttribute('code'), status = record.get('STATUS'), store = me.getStore(), hasInProgress = false;
					for (var i = 0; i < store.getCount(); i++) {
						var r = store.getAt(i);
						if (r.get('STATUS') == 'IN_PROGRESS' && equipCode == r.get('EQUIPCODE')) { // 如果处于加工中，并且设备是该设备，表示设备上已经有了任务
							hasInProgress = true;
							break;
						}
					};
					// 2、根据原任务状态设置任务更新状态
					if(status == 'TO_DO'){
						status = 'IN_PROGRESS';
					}else if(status == 'IN_PROGRESS'){
						if(equipCode != record.get('EQUIPCODE')){
							Ext.MessageBox.alert(Oit.msg.WARN, '该任务已经在[' + record.get('EQUIPCODE') + ']上加工！');
							return;
						}else{
							equipCode = '', status = "TO_DO";
						}
					}else if(status == 'FINISHED' || status == 'CANCELED'){
						return;
					}
					// 4、一个机器上报工任务只能一个个来 @edit:云母绕包和挤出可以一起报工，后面分开报 @edit:绝缘部分全部放开
										if (code != "Extrusion-Single" && code != "wrapping_ymd" && hasInProgress && status == 'IN_PROGRESS') {
											Ext.MessageBox.alert(Oit.msg.WARN, '请先将当前任务报工！');
											return;
										}
					// 5、后台请求更新任务状态
					Ext.Ajax.request({
								url : 'startTask',
								method : 'POST',
								params : {
									status : record.get('STATUS'),
									ids : record.get('TASKID'),
									equipCode : equipCode
								},
								success : function(response) {
									// 5.1、更新任务单的颜色，更新record的颜色
									record.set('STATUS', status);
									record.set('EQUIPCODE', equipCode);
//									Ext.each(columns, function(column) {
//												column.style.backgroundColor = statusColorMap[status];
//												column.style.color = (status == 'IN_PROGRESS' ? 'white' : 'black');
//											});
									// 5.2、foot部分的物料需求tab的查询条件更新值
									Ext.ComponentQuery.query('#feedOrderTaskId')[0].setValue(record.get('TASKID'));
									Ext.ComponentQuery.query('#feedColor')[0].setValue(record.get('COLOR'));
								},
								failure : function(response) {
									// 5.3、失败重新设置对象
									var statusColorMap = {
										FINISHED : 'yellow',
										IN_PROGRESS : 'grey',
										TO_DO : 'white'
									}
									var dtoJsonMap = Ext.decode(response.responseText);
									var targetStatus = dtoJsonMap.targetStatus;
									var uEquipCode = dtoJsonMap.equipCode;
									// 5.4、失败更新选中行的颜色
									record.set('STATUS', targetStatus);
									record.set('EQUIPCODE', uEquipCode);
									Ext.each(columns, function(column) {
												column.style.backgroundColor = statusColorMap[targetStatus];
//												column.style.color = (status == 'IN_PROGRESS' ? 'white' : 'black');
											});
								}
							});
				}
			},
			/**
			 * 页面自动刷新调用方法
			 */
			refresh : function(taskInfoListData) {
				var orderData = [], taskData = [];
				me.getGridData(taskInfoListData, orderData, taskData); // 封装获取数据
				me.query('grid')[0].getStore().loadData(orderData);
				me.query('grid')[1].getStore().loadData(taskData); // 加载数据，右侧任务信息
			}
		};

		me.items = [{
					region : "west",
					border : 1,
					width : '60%',
					height : '100%',
					items : [westGrid]
				}, {
					region : "east",
					border : 1,
					width : '40%',
					height : '100%',
					items : [eastGrid]
				}];

		me.callParent(arguments);

		var orderData = [], taskData = [];
		me.getGridData(me.dataArray, orderData, taskData); // 封装获取数据
		me.query('grid')[0].getStore().loadData(orderData); // 加载数据，左侧订单产品
		me.query('grid')[1].getStore().loadData(taskData); // 加载数据，右侧任务信息
		me.down('#baseInfoForm').loadRecord(me.workOrderInfo); // 加载页面信息:下达/要求完成日期;生产单/采集/剩余长度
	},

	/**
	 * 根据数据源封装表数据
	 * 
	 * @param dataArray 数据源
	 * @param orderData 返回的订单信息：左侧grid
	 * @param taskData 返回的任务信息：右侧grid
	 */
	getGridData : function(dataArray, orderData, taskData) {
		var me = this, colorCountMap = {}, orderDataMap = {}, taskDataMap = {}, processCode = Ext.fly('processInfo').getAttribute('code');;
		Ext.Array.each(dataArray, function(record, i) {
					if (!orderDataMap[record.ID]) {
						orderDataMap[record.ID] = Ext.clone(record);
					}

					// 颜色计数 // 16.3.2特别修改：针对同一个产品中包含有相同颜色的线，火花部分不允许合并，现将其中稍作处理，添加一个颜色数量来区分是否同一个产品中的
					if (colorCountMap[record.ID + record.COLOR]) {
						colorCountMap[record.ID + record.COLOR] += 1;
					} else {
						colorCountMap[record.ID + record.COLOR] = 1
					}
					record.COLORCOUNT = colorCountMap[record.ID + record.COLOR];

					if (record.SPLITLENGTHROLE && record.SPLITLENGTHROLE != '') {
						var specSplitRequire = me.analysisSplitRoleExpression(record.SPLITLENGTHROLE);
						Ext.Array.each(specSplitRequire, function(require, m) {
									var mapData = Ext.clone(record);
									var outyl = me.redundantAmount(record.PRODUCTTYPE, require.length, record.WIRESSTRUCTURE, processCode); // 计算余量
									mapData.SPLITLENGTHROLEWITHYULIANG = require.volNum == 1
											? (Number(require.length) + Number(outyl))
											: ((Number(require.length) + Number(outyl)) + '*' + require.volNum);
									mapData.TASKLENGTH = Math.round((Number(require.length) + Number(outyl)) * require.volNum);
									// 重新定义完成长度: 完成长度大于一段：完成的取任务长度，总任务的完成长度减去任务长度; 否则小于：完成的取完成长度，总任务取0; 状态长度大于就设置成已完成；
									var more = ((mapData.FINISHEDLENGTH - mapData.TASKLENGTH) > 0);
									mapData.FINISHEDLENGTH = more ? mapData.TASKLENGTH : mapData.FINISHEDLENGTH;
									mapData.STATUS = more ? 'FINISHED' : mapData.STATUS;
									record.FINISHEDLENGTH = more ? (record.FINISHEDLENGTH - mapData.TASKLENGTH) : 0;
									taskDataMap['random-' + Math.random()] = mapData;
								});
					} else {
						// 合并规则加上relateOrderIds：相关联的订单ID，有调度手动合并
						var item = taskDataMap[record.RELATEORDERIDS + me.getReplaceColor(record.COLOR) + record.COLORCOUNT];
						if (item) {
							item.TASKLENGTH = Math.round(item.TASKLENGTH) + Math.round(record.TASKLENGTH);
							item.FINISHEDLENGTH = Math.round(item.FINISHEDLENGTH) + Math.round(record.FINISHEDLENGTH);
							item.STATUS = (item.TASKLENGTH > item.FINISHEDLENGTH && item.STATUS == 'FINISHED')
									? record.STATUS
									: item.STATUS; // 解决合并长度问题:合并里面有已完成/在加工/待加工，状态已经完成，但合并长度并没有，取新的状态，否则取以前的
							item.TASKID += ',' + record.TASKID;
						} else {
							taskDataMap[record.RELATEORDERIDS + me.getReplaceColor(record.COLOR) + record.COLORCOUNT] = Ext.clone(record);;
						}
					}
				});
		for (var id in orderDataMap) {
			orderData.push(orderDataMap[id]);
		}
		for (var id in taskDataMap) {
			taskData.push(taskDataMap[id]);
		}
	},

	/**
	 * 解析表达式，返回一个数组解析式
	 * 
	 * @description 前：1500*1+1000*2+500*3
	 * @description 后：[{length: 1500, volNum: 1}, {length: 1000, volNum: 2},
	 *              {length: 500, volNum: 3}]
	 */
	analysisSplitRoleExpression : function(splitLengthRole) {
		var specSplitRequire = [];
		if (splitLengthRole && splitLengthRole != '') {
			Ext.Array.each(splitLengthRole.split('+'), function(roleItem, i) {
						if (roleItem.split('*').length > 1) {
							specSplitRequire.push({
										length : roleItem.split('*')[0],
										volNum : roleItem.split('*')[1]
									});
						} else {
							specSplitRequire.push({
										length : roleItem,
										volNum : 1
									});
						}
					});
			return specSplitRequire;
		} else {
			return [];
		}
	},

	/**
	 * 余量计算 四舍五入，取整数
	 */
	redundantAmount : function(productType, length, wiresStructure, processCode) {
		var yuliang = 0;
		if (processCode && processCode == 'wrapping_ymd') {
			yuliang = length * 0.05;
		} else {
			if (productType.indexOf('DJ') > 0) {
				if (length > 500) {
					yuliang = length * 0.02;
				} else {
					yuliang = 10;
				}
			} else {
				if (wiresStructure == 'A') {
					if (length > 1000) {
						yuliang = length * 0.01;
					} else {
						yuliang = 10;
					}
				} else if (wiresStructure == 'B') {
					if (length > 600) {
						yuliang = length * 0.015;
					} else {
						yuliang = 10;
					}
				} else if (wiresStructure == 'C') {
					if (length > 500) {
						yuliang = length * 0.02;
					} else {
						yuliang = 10;
					}
				}
			}
		}
		return Math.round(yuliang.toFixed());
	},

	getReplaceColor : function(color) {
		if (color) {
			return color.replace('双色', '').replace('色', '');
		} else {
			return '';
		}
	}
});