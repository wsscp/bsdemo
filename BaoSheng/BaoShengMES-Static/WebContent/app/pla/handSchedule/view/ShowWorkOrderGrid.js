/**
 * 查看生产单
 */

Ext.define('bsmes.view.ShowWorkOrderGrid', {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.showWorkOrderGrid',
	store : 'WorkOrderStore',
	defaultEditingPlugin : false,
	columnLines : true,
	selType : 'checkboxmodel',
	selModel : {
		mode : "MULTI" // "SINGLE"/"SIMPLE"/"MULTI"
	},
	columns : [{
				text : '生产单号',
				dataIndex : 'workOrderNo',
				flex : 1.8,
				minWidth : 90,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					var isDispatch = record.get('isDispatch');
					metaData.tdAttr = 'data-qtip="' + value + (isDispatch ? '(急)' : '') + '"';
					value = value.substring(value.length - 6) + (isDispatch ? '<font color="red">(急)</font>' : '');
					return value;
				}
			}, {
				text : '合同号 - 客户型号规格 - 合同长度',
				dataIndex : 'contractNo',
				menuDisabled : true,
				sortable : false,
				flex : 4,
				minWidth : 200,
				renderer : function(value, metaData, record) {
					var showInfo = '';
					var showInfo2 = '';
					for (var i = 0; i < value.split(",").length; i++) {
						var cppl = value.split(",")[i].split(';')[0]; // 显示的型号规格和长度
						showInfo2 += cppl + "<br/>";
						if(cppl.indexOf('(盘具') > 0)
						{
							cppl = cppl.replace('(盘具','<font = color="red">(盘具');
							cppl += '</font>';
						}
						showInfo += cppl + "<br/>";
					}
					metaData.tdAttr = 'data-qtip="' + showInfo2 + '"';
					return showInfo;
				}
			}, {
				text : '生产线',
				dataIndex : 'equipName',
				flex : 3,
				minWidth : 150,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					var machine = value.split(",");
					var res = '', res1 = '';
					for (var i = 0; i < machine.length; i++) {
						res = res + '<a style="color:blue;cursor:pointer;" onclick="intoMachine(\'' + machine[i]
								+ '\')">' + machine[i] + '</a>' + '<br/>';
						res1 = res1 + machine[i] + '<br/>';
					}
					metaData.tdAttr = 'data-qtip="' + res1 + '"';
					return res;
				}
			}, {
				text : '工序',
				dataIndex : 'processName',
				flex : 1.6,
				minWidth : 80,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					metaData.tdAttr = 'data-qtip="' + value + '"';
					return value;
				}
			}, {
				text : '特殊分<br/>盘要求',
				dataIndex : 'specialReqSplit',
				flex : 1.3,
				minWidth : 65,
				sortable : false,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					// metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					metaData.tdAttr = 'data-qtip="' + value + '"';
					return value;
				}
			}, {
				text : '总任<br/>务数',
				dataIndex : 'orderLength',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					metaData.tdAttr = 'data-qtip="' + value + '"';
					return value;
				}
			}, {
				text : '下发<br/>日期',
				dataIndex : 'releaseDate',
				xtype : 'datecolumn',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true,
				format : 'm-d'
			}, {
				text : '要求完<br/>成日期',
				dataIndex : 'requireFinishDate',
				xtype : 'datecolumn',
				format : 'm-d',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true
			}, {
				text : '实际完<br/>成日期',
				dataIndex : 'realEndTime',
				xtype : 'datecolumn',
				format : 'm-d',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true
			}, {
				text : '完成<br/>进度',
				dataIndex : 'percent',
				flex : 1,
				minWidth : 50,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					if(record.get('isOldLine')){
						return '<font color="red">陈线</font>';
					}
					value = (Number(value) * 100).toFixed(0);
					metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					var html = '<a style="color:blue;cursor:pointer;" onclick="showPercentDetail(\''
							+ record.get('workOrderNo') + '\', \'\', \'\')">' + value + '%</a>';
					metaData.tdAttr = 'data-qtip="' + value + '%"';
					return html;
				}
				// }, {
				// text : '是否<br/>急件',
				// dataIndex : 'isDispatch',
				// flex : 1,
				// minWidth : 50,
				// menuDisabled : true,
				// renderer : function(value) {
				// switch (value) {
				// case true :
				// return '是';
				// default :
				// return '否';
				// }
				// }
			}, {
				text : '附件',
				dataIndex : 'workOrderNo',
				flex : 1,
				minWidth : 50,
				renderer : function(value, metaData, record, rowIndex) {
					var nofile = (record.get('orderfilenum') == 0);
					var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"'
							+ (nofile ? '' : 'onclick="lookUpAttachFileWindow(\'\', \'' + value + '\', \'\')"')
							+ '>附件</a>';
					return html;
				},
				sortable : false,
				menuDisabled : true
			}, {
				text : '备注&技术要求',
				dataIndex : 'userComment',
				flex : 4,
				minWidth : 200,
				menuDisabled : true,
				renderer : function(value, metaData, record, rowIndex) {
					var svalue = value.replace(/\n/g, "<br/>");
					// value.replace(/\n|\r|(\r\n)|(\u0085)|(\u2028)|(\u2029)/g, "<br>") // 去除字符串中换行符
					// metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					metaData.tdAttr = 'data-qtip="' + svalue + '"';
					return '<a style="color:blue;cursor:pointer;" onclick="showremarks(\'' + svalue + '\')">' + value
							+ '</a>';
				}
			}, {
				text : '状态',
				dataIndex : 'status',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true,
				renderer : function(value) {
					switch (value) {
						case 'TO_AUDIT' :
							return Oit.msg.pla.handSchedule.statusName.toAudit;
						case 'TO_DO' :
							return Oit.msg.pla.handSchedule.statusName.toDo;
						case 'IN_PROGRESS' :
							return Oit.msg.pla.handSchedule.statusName.inProgress;
						case 'FINISHED' :
							return Oit.msg.pla.handSchedule.statusName.finished;
						case 'PAUSE' :
							return Oit.msg.pla.handSchedule.statusName.pause;
						case 'CANCELED' :
							return Oit.msg.pla.handSchedule.statusName.canceled;
						default :
							return '';
					}
				}
			}],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			title : '查询条件',
			xtype : 'fieldset',
			collapsible : true,
			width : '99.3%',
			items : [{
				xtype : 'form',
				layout : 'column',
				defaults : {
					width : 300,
					padding : 1,
					labelAlign : 'right'
				},
				items : [{
							fieldLabel : '生产单号',
							xtype : 'textfield',
							name : 'workOrderNo'
						}, {
							fieldLabel : '客户合同号',
							xtype : 'combobox',
							name : 'contractNo',
							displayField : 'contractNo',
							valueField : 'contractNo',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										autoLoad : false,
										fields : ['contractNo'],
										proxy : {
											type : 'rest',
											url : 'handSchedule/getWorkOrderContractNo'
										},
										sorters : [{
													property : 'contractNo',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('showWorkOrderGrid toolbar form')[0];
												var param = form.getForm().getValues();
												store.getProxy().extraParams = param;
											}
										}
									}),
							listeners : {
								beforequery : function(e) {
									var combo = e.combo;
									combo.collapse(); // 折叠
									if (!e.forceAll) { // 模糊查询走的方法
										var value = e.query;
										if (value != null && value != '') {
											combo.getStore().load({
														params : {
															'query' : value
														}
													});
										} else {
											combo.getStore().load();
										}
										combo.expand(); // 展开
										return false;
									} else { // 点击下拉框
										combo.firstExpand++;
									}
								},
								expand : function(e) {
									if (e.firstExpand > 1) {
										e.getStore().load();
									}
								}
							}
						}, {
							fieldLabel : '客户型号',
							xtype : 'combobox',
							name : 'custProductType',
							displayField : 'custProductType',
							valueField : 'custProductType',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										autoLoad : false,
										fields : ['custProductType'],
										proxy : {
											type : 'rest',
											url : 'handSchedule/getWorkOrderCustproductType'
										},
										sorters : [{
													property : 'custProductType',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('showWorkOrderGrid toolbar form')[0];
												var param = form.getForm().getValues();
												store.getProxy().extraParams = param;
											}
										}
									}),
							listeners : {
								beforequery : function(e) {
									var combo = e.combo;
									combo.collapse(); // 折叠
									if (!e.forceAll) { // 模糊查询走的方法
										var value = e.query;
										if (value != null && value != '') {
											combo.getStore().load({
														params : {
															'query' : value
														}
													});
										} else {
											combo.getStore().load();
										}
										combo.expand(); // 展开
										return false;
									} else { // 点击下拉框
										combo.firstExpand++;
									}
								},
								expand : function(e) {
									if (e.firstExpand > 1) {
										e.getStore().load();
									}
								}
							}
						}, {
							fieldLabel : '产品型号',
							xtype : 'combobox',
							name : 'productType',
							displayField : 'productType',
							valueField : 'productType',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										autoLoad : false,
										fields : ['productType'],
										proxy : {
											type : 'rest',
											url : 'handSchedule/getWorkOrderProductType'
										},
										sorters : [{
													property : 'productType',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('showWorkOrderGrid toolbar form')[0];
												var param = form.getForm().getValues();
												store.getProxy().extraParams = param;
											}
										}
									}),
							listeners : {
								beforequery : function(e) {
									var combo = e.combo;
									combo.collapse(); // 折叠
									if (!e.forceAll) { // 模糊查询走的方法
										var value = e.query;
										if (value != null && value != '') {
											combo.getStore().load({
														params : {
															'query' : value
														}
													});
										} else {
											combo.getStore().load();
										}
										combo.expand(); // 展开
										return false;
									} else { // 点击下拉框
										combo.firstExpand++;
									}
								},
								expand : function(e) {
									if (e.firstExpand > 1) {
										e.getStore().load();
									}
								}
							}
						}, {
							fieldLabel : '产品规格',
							name : 'productSpec',
							xtype : 'combobox',
							displayField : 'productSpec',
							valueField : 'productSpec',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										autoLoad : false,
										fields : ['productSpec'],
										proxy : {
											type : 'rest',
											url : 'handSchedule/getWorkOrderProductSpec'
										},
										sorters : [{
													property : 'productSpec',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('showWorkOrderGrid toolbar form')[0];
												var param = form.getForm().getValues();
												store.getProxy().extraParams = param;
											}
										}
									}),
							listeners : {
								beforequery : function(e) {
									var combo = e.combo;
									combo.collapse(); // 折叠
									if (!e.forceAll) { // 模糊查询走的方法
										var value = e.query;
										if (value != null && value != '') {
											combo.getStore().load({
														params : {
															'query' : value
														}
													});
										} else {
											combo.getStore().load();
										}
										combo.expand(); // 展开
										return false;
									} else { // 点击下拉框
										combo.firstExpand++;
									}
								},
								expand : function(e) {
									if (e.firstExpand > 1) {
										e.getStore().load();
									}
								}
							}
						}, {
							fieldLabel : '线芯结构',
							xtype : 'combobox',
							name : 'wiresStructure',
							displayField : 'wiresStructure',
							valueField : 'wiresStructure',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										autoLoad : false,
										fields : ['wiresStructure'],
										proxy : {
											type : 'rest',
											url : 'handSchedule/getWorkOrderWiresStructure'
										},
										sorters : [{
													property : 'wiresStructure',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('showWorkOrderGrid toolbar form')[0];
												var param = form.getForm().getValues();
												store.getProxy().extraParams = param;
											}
										}
									}),
							listeners : {
								beforequery : function(e) {
									var combo = e.combo;
									combo.collapse(); // 折叠
									if (!e.forceAll) { // 模糊查询走的方法
										var value = e.query;
										if (value != null && value != '') {
											combo.getStore().load({
														params : {
															'query' : value
														}
													});
										} else {
											combo.getStore().load();
										}
										combo.expand(); // 展开
										return false;
									} else { // 点击下拉框
										combo.firstExpand++;
									}
								},
								expand : function(e) {
									if (e.firstExpand > 1) {
										e.getStore().load();
									}
								}
							}
						}, {
							fieldLabel : '工序',
							name : 'processCode',
							xtype : 'combobox',
							displayField : 'name',
							valueField : 'code',
							editable : false,
							store : new Ext.data.Store({
										fields : ['name', 'code'],
										proxy : {
											type : 'rest',
											url : '../pro/processInfo/getAllProcess'
										}
									}),
							listeners : {
								change : function(field, newValue, oldValue) {
									if (newValue != '') {
										var combo = this.up("form").getForm().findField('equipCode');
										combo.store.getProxy().url = '../fac/equipInfo/getEquipLine?processName='
												+ newValue;
										combo.store.load();
										combo.expand();
									}
								}
							}
						}, {
							fieldLabel : '生产线',
							name : 'equipCode',
							xtype : 'combobox',
							displayField : 'name',
							valueField : 'code',
							// queryMode : 'local',
							store : new Ext.data.Store({
										fields : ['code', {
													name : 'name',
													type : 'string',
													convert : function(value, record) {
														if (record.get('equipAlias') == '') {
															return value + '[' + record.get('code') + ']';
														} else {
															return record.get('equipAlias') + '[' + record.get('code')
																	+ ']';
														}
													}
												}, 'equipAlias'],
										proxy : {
											type : 'rest',
											url : '../fac/equipInfo/getEquipLine'
										}
									}),
							listeners : {
								beforequery : function(e) {
									equipEmpty = true;
									var combo = e.combo;
									combo.collapse();
									if (!e.forceAll) {
										equipEmpty = false;
										var value = e.query;
										if (value != null && value != '') {
											combo.store.filterBy(function(record, id) {
														var text = record.get('name');
														return (text.indexOf(value) != -1);
													});
										} else {
											combo.store.clearFilter();
										}
										combo.onLoad();
										combo.expand();
										return false;
									}
								}
							}
						}, {
							fieldLabel : '下发日期',
							xtype : 'datefield',
							name : 'releaseDate',
							format : 'Y-m-d'
						}, {
							fieldLabel : '要求完成日期',
							xtype : 'datefield',
							name : 'requireFinishDate',
							format : 'Y-m-d'
						}, {
							fieldLabel : '是否为急件',
							xtype : 'checkbox',
							name : 'isDispatch',
							checked : false
						}, {
							fieldLabel : '生产单状态',
							xtype : 'checkboxgroup',
							width : 550,
							columns : 6,
							vertical : true,
							items : [{
										boxLabel : '待下发',
										name : 'status',
										inputValue : 'TO_AUDIT',
										checked : true
									}, {
										boxLabel : '已下发',
										name : 'status',
										inputValue : 'TO_DO',
										checked : true
									}, {
										boxLabel : '生产中',
										name : 'status',
										inputValue : 'IN_PROGRESS',
										checked : true
									}, {
										boxLabel : '已完成',
										name : 'status',
										inputValue : 'FINISHED'
									}, {
										boxLabel : '已暂停',
										name : 'status',
										inputValue : 'PAUSE'
									}, {
										boxLabel : '已取消',
										name : 'status',
										inputValue : 'CANCELED'
									}]
						}],
				buttons : [{
							itemId : 'searchWorkOrder',
							text : Oit.btn.search,
							iconCls : 'icon_search'
						}, {
							itemId : 'reset',
							handler : function(e) {
								this.up("form").getForm().reset();
							}
						}, {
							text : '详情',
							iconCls : 'icon_detail',
							itemId : 'showWorkOrderDetail'
						}, {
							text : '层级查看',
							itemId : 'showSeriesWorkOrder'
						}, {
							text : '下发生产单',
							itemId : 'auditWorkOrder'
						}, {
							text : '设置为急件',
							itemId : 'setdispatch'
						}, {
							text : '调整生产线',
							iconCls : 'change_equip',
							itemId : 'changeEquip'
						}, {
							text : '调整机台生产单顺序',
							itemId : 'changeWorkOrderSeq'
						}, {
							text : '生产单操作历史',
							itemId : 'showWorkOrderOperateLog'
						}, {
							text : '报工记录',
							itemId : 'showReportRecords'
						}, {
							text : '取消生产单',
							handler : function() {
								var showWorkOrderGrid = this.up('grid');
								var selection = showWorkOrderGrid.getSelectionModel().getSelection();
								var workOrderNo = '';
								if (selection.length > 0) {
									var inProgress = false;
									for (var i in selection) {
										var record = selection[i];
										workOrderNo += record.get('workOrderNo') + ',';
										if (record.get('status') == 'IN_PROGRESS') {
											inProgress = true;
										}
									}
									if (inProgress) {
										Ext.Msg.alert(Oit.msg.PROMPT, '生产单处于加工中的，不能做取消操作！');
										return;
									}
									Ext.MessageBox.confirm('确认', '确认取消生产单？', function(btn) {
												if (btn == 'yes') {
													Ext.Ajax.request({
																url : 'handSchedule/updateWorkerOrderStatus',
																params : {
																	workOrderNo : workOrderNo,
																	status : 'CANCELED'
																},
																success : function(response) {
																	Ext.Msg.alert(Oit.msg.PROMPT, '取消成功');
																	showWorkOrderGrid.getStore().load();
																},
																failure : function(response, action) {
																	var result = Ext.decode(response.responseText);
																	Ext.Msg.alert(Oit.msg.WARN, result.message);
																}
															});
												}
											});
								} else {
									Ext.Msg.alert(Oit.msg.PROMPT, '请选择生产单！');
								}
							}
						}, '->']
			}]
		}]
	}]
});
