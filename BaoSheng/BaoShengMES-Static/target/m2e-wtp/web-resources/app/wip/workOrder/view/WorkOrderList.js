Ext.define("bsmes.view.WorkOrderList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.workOrderList',
			store : 'WorkOrderStore',
			plugins : [{
						ptype : 'rowexpander',
						rowBodyTpl : ['<div id="{id}">', '</div>']
					}],
			collapsible : true,
			defaultEditingPlugin : false,
			animCollapse : false,
			selType : 'rowmodel',
			stripeRows : true,
			viewConfig : {
				stripeRows : false,
				getRowClass : function(record, rowIndex, rowParams, store) {
					var resultColor = '';
					if (record.get("isDelayed") == true) {
						resultColor = 'x-grid-record-yellow';
					}
					return resultColor;

				}
			},
			forceFit : false,
			columns : [{
						text : Oit.msg.wip.workOrder.workOrderNO,
						dataIndex : 'workOrderNo',
						width : 250,
						renderer : function(value, metaData, record) {
							return value + '[' + record.get('operator') + ']';
						}
					}, {
						text : Oit.msg.wip.workOrder.equipName,
						dataIndex : 'equipName',
						width : 200,
						renderer : function(value, metaData, record) {
							if (value == '') {
								return value;
							}
							return '' + record.get('equipCode') + '' + value;
						}
					}
					// , {
					// text : Oit.msg.wip.workOrder.fixedEquipName,
					// dataIndex : 'fixedEquipName',
					// width : 100
					// }
					, {
						text : Oit.msg.wip.workOrder.processName,
						dataIndex : 'processName',
						width : 100
					}, {
						text : Oit.msg.wip.workOrder.orderLength,
						dataIndex : 'orderLength',
						width : 100
					},
					// , {
					// text : Oit.msg.wip.workOrder.cancelLength,
					// dataIndex : 'cancelLength',
					// width : 100
					// },
					// {
					// text : Oit.msg.wip.workOrder.color,
					// dataIndex : 'outProductColor',
					// width : 100
					// }, {
					// text : Oit.msg.wip.workOrder.halfProductCode,
					// dataIndex : 'halfProductCode',
					// width : 300
					// },
					// {
					// text : Oit.msg.wip.workOrder.preStartTime,
					// dataIndex : 'preStartTime',
					// xtype : 'datecolumn',
					// format : 'Y-m-d H:i:s',
					// width : 150
					// },
					// {
					// text : Oit.msg.wip.workOrder.realStartTime,
					// dataIndex : 'realStartTime',
					// xtype : 'datecolumn',
					// format : 'Y-m-d H:i:s',
					// width : 150
					// },
					{
						text : Oit.msg.wip.workOrder.auditTime,
						dataIndex : 'auditTime',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s',
						width : 150
					}, {
						text : Oit.msg.wip.workOrder.preEndTime,
						dataIndex : 'requireFinishDate',
						xtype : 'datecolumn',
						format : 'Y-m-d',
						width : 150
					}, {
						text : Oit.msg.wip.workOrder.realEndTime,
						dataIndex : 'realEndTime',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s',
						width : 150
					}, {
						text : Oit.msg.wip.workOrder.status,
						dataIndex : 'status',
						renderer : function(value) {
							switch (value) {
								case 'TO_AUDIT' :
									return Oit.msg.wip.workOrder.statusName.toAudit;
								case 'TO_DO' :
									return Oit.msg.wip.workOrder.statusName.toDo;
								case 'IN_PROGRESS' :
									return Oit.msg.wip.workOrder.statusName.inProgress;
								case 'CANCELED' :
									return Oit.msg.wip.workOrder.statusName.canceled;
								case 'FINISHED' :
									return Oit.msg.wip.workOrder.statusName.finished;
								case 'PAUSE' :
									return Oit.msg.wip.workOrder.statusName.pause;
								default :
									return '';
							}
						},
						width : 100
					}, {
						text : Oit.msg.wip.workOrder.percent,
						dataIndex : 'percent',
						renderer : function(value) {
							return value + '%';
						},
						sortable : false,
						width : 100
					}, {
						text : Oit.msg.wip.workOrder.remarks,
						dataIndex : 'remarks',
						width : 150
					}],
			actioncolumn : [{
						itemId : 'logisticalRequest',
						tooltip : Oit.msg.wip.btn.logisticalRequest,
						handler : function(grid, rowIndex, colIndex, item, e, record) {
							// grid.fireEvent('logisticalRequest',
							// grid.getStore().getAt(rowIndex));
							grid.fireEvent('logisticalRequest', record);
						},
						getClass : function(value, metadata, record, rowIndex, colIndex, store) {
							if (record.get("status") == "TO_AUDIT") {
								return "";
							} else {
								return "icon_detail";
							}

						},
						isDisabled : function(view, rowIndex, colIndex, item, record) {
							if (record.get("status") == "TO_AUDIT") {
								return true;
							} else {
								return false;
							}

						}
					}, '', {
						itemId : 'showWorkOrderDetail',
						tooltip : '产看生产单',
						iconCls : 'icon_detail',
						handler : function(grid, rowIndex, colIndex, item, e, record) {
							grid.fireEvent('showWorkOrderDetail', record);
						}
					}],
			tbar : [{
						itemId : 'audit',
						text : Oit.msg.wip.btn.audit
					},/*
						 * '', { itemId : 'cancel', text :
						 * Oit.msg.wip.btn.cancel },'', { itemId : 'printOrder',
						 * text : Oit.msg.wip.btn.printOrder },
						 */'', {
						itemId : 'setPriority',
						text : Oit.msg.wip.btn.setPriority
					}, '', {
						itemId : 'resourceGantt',
						text : Oit.msg.wip.btn.resourceGantt,
						handler : function(e) {
							// createTabPanel(Ext.create('bsmes.view.OrderTaskList'),'orderTaskList',Oit.msg.wip.btn.resourceGantt);
							parent.openTab(Oit.msg.wip.btn.resourceGantt, 'pla/orderTask.action');
						}
					}, '',
					// {
					// itemId : 'recalculate',
					// text : Oit.msg.wip.btn.recalculate
					// /*
					// * handler:function(e){
					// *
					// window.location.href="workOrder/reScheduleWorkOrder.action";
					// }
					// */
					// } , '', {
					// itemId : 'setFixedEquipCode',
					// text : Oit.msg.wip.btn.setFixedEquipCode
					// }, '',
					{
						itemId : 'export',
						text : Oit.btn.export
					}],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
							title : '查询条件',
							xtype : 'fieldset',
							collapsible : true,
							width : '100%',
							items : [{
										id : 'workOrderSearchForm',
										xtype : 'form',
										width : '100%',
										layout : 'vbox',
										buttonAlign : 'left',
										labelAlign : 'right',
										bodyPadding : 5,
										defaults : {
											xtype : 'panel',
											width : '100%',
											layout : 'column',
											defaults : {
												width : 300,
												padding : 1,
												labelAlign : 'right'
											}
										},
										items : [{
													items : [ /*
																 * { fieldLabel :
																 * Oit.msg.wip.workOrder.orgName,
																 * xtype :
																 * 'textfield',
																 * name :
																 * 'orgName' },
																 */{
																fieldLabel : Oit.msg.wip.workOrder.customerContractNO,
																xtype : 'textfield',
																name : 'customerContractNO'
															}, {
																fieldLabel : Oit.msg.wip.workOrder.preStartTimeFrom,
																xtype : 'datefield',
																name : 'preStartTimeFrom',
																format : 'Y-m-d'
															}, {
																fieldLabel : Oit.msg.wip.workOrder.preStartTimeTo,
																xtype : 'datefield',
																name : 'preStartTimeTo',
																format : 'Y-m-d'
															}, {
																fieldLabel : Oit.msg.wip.workOrder.productSpec,
																xtype : 'textfield',
																name : 'productSpec'
															}, {
																fieldLabel : Oit.msg.wip.workOrder.productType,
																xtype : 'textfield',
																name : 'productType'
															}, {
																fieldLabel : Oit.msg.wip.workOrder.processName,
																name : 'processCode',
																xtype : 'combobox',
																displayField : 'name',
																valueField : 'code',
																store : new Ext.data.Store({
																			fields : ['name', 'code'],
																			autoLoad : true,
																			proxy : {
																				type : 'rest',
																				url : '../pro/processInfo/getAllProcess'
																			}
																		})
															}, {
																fieldLabel : Oit.msg.wip.workOrder.status,
																xtype : 'combobox',
																name : 'status',
																displayField : 'name',
																valueField : 'code',
																value : '',
																editable : false,
																store : new Ext.data.Store({
																			fields : ['code', 'name'],
																			autoLoad : true,
																			proxy : {
																				type : 'rest',
																				url : 'workOrder/workOrderStatusCombo'
																			}
																		})
															}, {
																fieldLabel : Oit.msg.wip.workOrder.operator,
																xtype : 'textfield',
																name : 'operator'
															}, {
																fieldLabel : Oit.msg.wip.workOrder.workOrderNO,
																xtype : 'textfield',
																name : 'workOrderNo'
															}, {
																fieldLabel : Oit.msg.wip.workOrder.equipName,
																name : 'equipCode',
																xtype : 'combobox',
																displayField : 'name',
																valueField : 'code',
																minChars : 1,
																width : 410,
																store : Ext.create('bsmes.store.WorkOrderEquipStore'),
																listeners : {
																	'beforequery' : function(queryPlan, eOpts) {
																		var me = this;
																		var url = 'workOrder/equip';
																		if (queryPlan.query) {
																			me.getStore().getProxy().url = url + "/" + queryPlan.query
																					+ '/';
																		} else {
																			me.getStore().getProxy().url = url + "/-1";
																		}
																	}
																}
															}, {
																fieldLabel : '是否超OA交期',
																xtype : 'checkboxgroup',
																vertical : true,
																items : [{
																			boxLabel : '超OA交期',
																			name : 'isDelayed',
																			inputValue : '1'
																		}, {
																			boxLabel : '未超OA交期',
																			name : 'isDelayed',
																			inputValue : '0'
																		}]
															}]
												}],
										buttons : [{
													itemId : 'search',
													text : Oit.btn.search
												}, {
													itemId : 'reset',
													text : '重置',
													handler : function(e) {
														this.up("form").getForm().reset();
													}
												}]

									}]
						}]
			}],
			initComponent : function() {
				var me = this;
				this.callParent(arguments);
				me.view.on('expandBody', function(rowNode, record, expandRow, eOpts) {
					var renderId = record.get('id');
					var processName = record.get('processName');
					console.log(processName);

					/*
					 * if(processName == '挤出-单层' || processName == '挤出-双层'){ var
					 * url = "workOrder/workOrderReport/" +
					 * record.get('workOrderNo'); var innerStore =
					 * Ext.create('bsmes.store.WorkOrderReportStore');
					 * innerStore.getProxy().url = url; innerStore.reload();
					 * 
					 * var subGrid =
					 * Ext.create("bsmes.view.WorkOrderReportList", { store :
					 * innerStore, renderTo : renderId });
					 * 
					 * subGrid.getEl().swallowEvent(['mousedown', 'mouseup',
					 * 'click', 'contextmenu', 'mouseover', 'mouseout',
					 * 'dblclick', 'mousemove']); }else{
					 */

					var url = "workOrder/orderTasks/" + renderId;
					var innerStore = Ext.create('bsmes.store.WorkOrderSubStore');
					innerStore.getProxy().url = url;
					innerStore.reload();

					var subGrid = Ext.create("bsmes.view.WorkOrderSubList", {
								store : innerStore,
								renderTo : renderId
							});

					subGrid.getEl().swallowEvent(['mousedown', 'mouseup', 'click', 'contextmenu', 'mouseover', 'mouseout', 'dblclick',
							'mousemove']);

						// }

					});
				me.view.on('collapsebody', function(rowNode, record, expandRow, eOpts) {
							var parent = document.getElementById(record.get('id'));
							var child = parent.firstChild;
							while (child) {
								child.parentNode.removeChild(child);
								child = child.nextSibling;
							}
						});

			}
		});

function createTabPanel(resource, id, title) {
	var tabPanel = parent.getTabPanel();
	var addPanel = Ext.getCmp(resource);
	if (!addPanel) {
		var tab = tabPanel.add(new Ext.Panel({
					id : id,
					title : title,
					closable : true,
					animScroll : true, // 使用动画滚动效果
					enableTabScroll : true,
					width : 1370,
					height : 700,
					layout : 'fit',
					items : resource
				}));
		tabPanel.setActiveTab(tab);
	} else {
		tabPanel.setActiveTab(addPanel);
	}
}