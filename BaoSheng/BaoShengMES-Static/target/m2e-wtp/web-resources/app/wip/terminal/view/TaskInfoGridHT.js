/**
 * 绝缘
 */
Ext.define('bsmes.view.TaskInfoGridHT', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.taskInfoGridHT',
	forceFit : true,
	store : 'TaskInfoStore',
	itemId : 'taskInfoGrid',
	id: 'taskInfoGridHT',
	width : '100%',
	height : (document.body.scrollHeight - 45) / 2 - 45,
	rowLines : true,
	columnLines : true,
	dataArray : null, // 加载数据对象
	workOrderInfo : null, // 生产单信息，set采集值，剩余长度等
	invalidateScrollerOnRefresh: false, // extjs4 grid store.load 时不改变滚动条位置
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
	initComponent : function() {
		var me = this;
		var columns = [{
			text : '合同号',
			dataIndex : 'CONTRACTNO',
			flex : 1.2,
			minWidth : 60,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var reg = /[a-zA-Z]/g;
				value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length - 5) : value
						.replace(reg, ""));
					//	+ '<br/>[' + record.get('OPERATOR') + ']'
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				return value;
			}
		}, {
			text : '客户型号规格',
			dataIndex : 'CUSTPRODUCTTYPE',
			flex : 3.5,
			minWidth : 175,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				value = value + ' ' + record.get('CUSTPRODUCTSPEC')
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				return value;
			}
		}, {
			text : '投产长度',
			dataIndex : 'TASKLENGTH',
			flex : 1.5,
			minWidth : 75,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var json = Ext.decode(record.get('OUTATTRDESC'));
				var splitLengthRoleWithYuliang = typeof(json.splitLengthRoleWithYuliang) == "undefined"
						? ''
						: json.splitLengthRoleWithYuliang;
				if (splitLengthRoleWithYuliang != '') {
					value += '<br/>' + splitLengthRoleWithYuliang;
				}
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				return value;
			}
		}, {
			text : '交货长度',
			dataIndex : 'CONTRACTLENGTH',
			flex : 1.5,
			minWidth : 75,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var json = Ext.decode(record.get('OUTATTRDESC'));
				var splitLengthRole = typeof(json.splitLengthRole) == "undefined" ? '' : json.splitLengthRole;
				if (splitLengthRole != '') {
					value += '<br/>' + splitLengthRole;
				}
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
				if (value == '') {
					value = 0;
				}
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				return value;
			}
		}, {
			text : '材料',
			dataIndex : 'OUTATTRDESC',
			flex : 2.8,
			minWidth : 140,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var json = Ext.decode(value);
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = typeof(json.material) == "undefined" ? '' : json.material;
				return value;
			}
		}, {
			text : '颜色字码',
			dataIndex : 'COLOR',
			flex : 1.7,
			minWidth : 85,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				return value;
			}
		}, {
			text : '模芯模套',
			dataIndex : 'OUTATTRDESC',
			flex : 1.2,
			minWidth : 60,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var json = Ext.decode(value);
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = typeof(json.moldCoreSleeve) == "undefined" ? '' : json.moldCoreSleeve;
				return value;
			}
		}, {
			text : '标称厚度',
			dataIndex : 'OUTATTRDESC',
			flex : 1.2,
			minWidth : 60,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var json = Ext.decode(value);
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = typeof(json.standardPly) == "undefined" ? '' : json.standardPly;
				return value;
			}
		}, {
			text : '指导厚度',
			dataIndex : 'OUTATTRDESC',
			flex : 1.2,
			minWidth : 60,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var json = Ext.decode(value);
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = typeof(json.guidePly) == "undefined" ? '' : json.guidePly;
				return value;
			}
		}, {
			text : '标准外径',
			dataIndex : 'OUTATTRDESC',
			flex : 1.2,
			minWidth : 60,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var json = Ext.decode(value);
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = typeof(json.outsideValue) == "undefined" ? '' : json.outsideValue;
				return value;
			}
		}, {
			text : '最大外径',
			dataIndex : 'OUTATTRDESC',
			flex : 1.2,
			minWidth : 60,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				var json = Ext.decode(value);
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = typeof(json.outsideMaxValue) == "undefined" ? '' : json.outsideMaxValue;
				return value;
			}
		}, {
			text : '收线盘具',
			dataIndex : 'WIRECOIL',
			flex : 1.5,
			minWidth : 75,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				return value;
			}
		}, {
			text : '印字要求',
			dataIndex : 'OUTMATDESC',
			flex : 3.6,
			minWidth : 180,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, row, column) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = '<font style="font-size:16px;line-height: 16px;">' + value + '</font>'
				return value;
			}
		}];
		var dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			items : {
				xtype : 'form',
				itemId : 'baseInfoForm',
				width : '100%',
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
								width : 180,
								xtype : 'displayfield',
								name : 'releaseDate'
							}, {
								fieldLabel : '要求完成日期',
								xtype : 'displayfield',
								width : 210,
								labelWidth : 110,
								name : 'requireFinishDate'
							}, {
								fieldLabel : '生产单长度',
								xtype : 'displayfield',
								name : 'planLength',
								renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
									return Math.round(value);
								}
							}, {
								fieldLabel : '采集长度',
								xtype : 'displayfield',
								name : 'currentReportLength',
								fieldStyle : {
									color : 'red'
								}
							}, {
								fieldLabel : '剩余长度',
								xtype : 'displayfield',
								name : 'remainQLength'
							}, {
									fieldLabel : '本班能耗(kwh)',
									xtype : 'displayfield',
									name : 'energyConsumptio',
									width : 210,
									labelWidth : 130
							}, {
								xtype : 'panel',
								width : 230,
								html : '<div style="height:38px;padding:5px 0px 0px 0px;">'
										+ '<span style="width:16px;height:16px;background-color:white;border:1px solid #B3C1CF;color:black;padding:5px;margin-right:5px;">待加工</span>'
										+ '<span style="width:16px;height:16px;background-color:green;border:1px solid #B3C1CF;color:white;padding:5px;margin-right:5px;">加工中</span>'
										+ '<span style="width:16px;height:16px;background-color:yellow;border:1px solid #B3C1CF;color:black;padding:5px;margin-right:5px;">已完成</span>'
										+ '<span style="width:16px;height:16px;background-color:grey;border:1px solid #B3C1CF;color:white;padding:5px;margin-right:5px;">已绑定</span>'
										+ '</div>'
							}]
				}]
			}
		}];
		me.dockedItems = dockedItems;
		me.columns = columns;
		me.callParent(arguments);
		me.getStore().loadData(me.dataArray); // 加载数据
		me.down('#baseInfoForm').loadRecord(me.workOrderInfo); // 加载页面信息:下达/要求完成日期;生产单/采集/剩余长度
	},
	/**
	 * 页面自动刷新调用方法
	 */
	refresh : function(taskInfoListData) {
		var me = this;
		me.getStore().loadData(taskInfoListData);
	},
	listeners : {
		itemdblclick : function(me, record, index) {
			// 1、定义对象{statusColorMap:颜色状态map,columns:所点击的行对象,equipCode:设备编码,status:任务状态,store:任务grid的store,hasInProgress:是否有加工的任务单}
			var statusColorMap = {
				CANCELED : 'yellow',
				FINISHED : 'yellow',
				IN_PROGRESS : 'green',
				TO_DO : 'white'
			}, columns = me.getNode(index).childNodes;
			var equipCode = Ext.fly('equipInfo').getAttribute('code'), status = record.get('STATUS'), store = me.getStore(), hasInProgress = false;
			// 2、判断是否有在加工中的任务
			for (var i = 0; i < store.getCount(); i++) {
				var r = store.getAt(i);
				if (r.get('STATUS') == 'IN_PROGRESS' && equipCode == r.get('EQUIPCODE')) { // 如果处于加工中，并且设备是该设备，表示设备上已经有了任务
					hasInProgress = true;
					break;
				}
			};
			// 3、根据原任务状态设置任务更新状态
			if (status == 'FINISHED' || status == 'CANCELED') {
				Ext.each(columns, function(column) {
							column.style.backgroundColor = statusColorMap[status];
						});
				return;
			} else if (status == 'TO_DO') {
				status = 'IN_PROGRESS';
			} else if (status == 'IN_PROGRESS') {
				if (equipCode != record.get('EQUIPCODE')) {
					Ext.each(columns, function(column) {
								column.style.backgroundColor = 'grey';
							});
					Ext.MessageBox.alert(Oit.msg.WARN, '该任务已经在[' + record.get('EQUIPCODE') + ']上加工！');
					return;
				}
				equipCode = '', status = "TO_DO";
			}
			// 4、一个机器上报工任务只能一个个来
			if (hasInProgress && status == 'IN_PROGRESS') {
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
							Ext.each(columns, function(column) {
										column.style.backgroundColor = statusColorMap[status];
										column.style.color = (status == 'IN_PROGRESS' ? 'white' : 'black');
									});
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
										column.style.color = (status == 'IN_PROGRESS' ? 'white' : 'black');
									});
						}
					});
		}
	}
});
