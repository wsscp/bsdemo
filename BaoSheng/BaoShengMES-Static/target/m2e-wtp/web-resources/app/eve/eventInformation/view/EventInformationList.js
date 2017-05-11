Ext.define("bsmes.view.EventInformationList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.eventInformationList',
	store : 'EventInformationStore',
	defaultEditingPlugin : false,
	forceFit : false,
	selType : 'rowmodel',
	stripeRows : true,
	viewConfig : {
		stripeRows : false,
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rowParams, store) {
			if (record.get('eventStatus') != 'COMPLETED') {
				var processSeq = record.get('processSeq');
				if (processSeq == 1) {
					return 'x-grid-record-blue';
				} else if (processSeq == 2) {
					return 'x-grid-record-orange';
				} else if (processSeq == 3) {
					return 'x-grid-record-red';
				}
			}
			return '';
		}
	},
	columns : [{
				text : Oit.msg.eve.eventInformation.eventTitle,
				dataIndex : 'eventTitle',
				minWidth : 85,
				flex : 1.7,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.eventContent,
				dataIndex : 'eventContent',
				minWidth : 300,
				flex : 6,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.createTime,
				dataIndex : 'createTime',
				xtype : 'datecolumn',
				format : 'm-d H:i',
				minWidth : 80,
				flex : 1.6
			}, {
				text : Oit.msg.eve.eventInformation.eventStatus,
				dataIndex : 'eventStatus',
				minWidth : 65,
				flex : 1.3,
				renderer : function(value, metaData, record, row, column) {
					if (value == 'UNCOMPLETED') {
						return "未处理";
					} else if (value == 'RESPONDED') {
						return "已响应";
					} else if (value == 'PENDING') {
						return "待确认";
					} else {
						return "已完成";
					}
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.equipCode,
				dataIndex : 'equipCode',
				minWidth : 135,
				flex : 2.7,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.productCode,
				dataIndex : 'productCode',
				minWidth : 100,
				flex : 2,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.pendingProcessing,
				dataIndex : 'pendingProcessing',
				minWidth : 70,
				flex : 1.4,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.responsible,
				dataIndex : 'responsible',
				minWidth : 70,
				flex : 1.4,
				minWidth : 80,
				flex : 1.6,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.processSeq,
				dataIndex : 'processSeq',
				minWidth : 80,
				flex : 1.6,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.completeTime,
				dataIndex : 'completeTime',
				xtype : 'datecolumn',
				format : 'm-d H:i:s',
				minWidth : 80,
				flex : 1.6
			}, {
				text : Oit.msg.eve.eventInformation.name,
				dataIndex : 'name',
				minWidth : 65,
				flex : 1.3,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.processType,
				dataIndex : 'processType',
				minWidth : 65,
				flex : 1.3,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.eventReason,
				dataIndex : 'eventReason',
				minWidth : 100,
				flex : 2,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.eventResult,
				dataIndex : 'eventResult',
				minWidth : 100,
				flex : 2,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}, {
				text : Oit.msg.eve.eventInformation.batchNo,
				dataIndex : 'batchNo',
				minWidth : 75,
				flex : 1.5,
				renderer : function(value, metaData, record, row, column) {
					return value;
				}
			}],
	actioncolumn : [{
				itemId : 'edit',
				handler : function(grid, rowIndex) {
					var record = grid.getStore().getAt(rowIndex);
					if (record.data.eventStatus == 'COMPLETED') {
						eventInformationView(grid.getStore(), record, true);

					} else {
						eventInformationView(grid.getStore(), record, false);
					}
				}
			}, '', {
				tooltip : Oit.msg.eve.eventInformation.search,
				iconCls : 'icon_detail',
				handler : function(grid, rowIndex) {
					eventInformationView(grid.getStore(), grid.getStore().getAt(rowIndex), true);
				}
			}],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			title : '查询条件',
			xtype : 'fieldset',
			width : '100%',
			items : [{
				xtype : 'form',
				width : '100%',
				layout : 'vbox',
				buttonAlign : 'left',
				labelAlign : 'right',
				bodyPadding : 2,
				defaults : {
					xtype : 'panel',
					width : '100%',
					layout : 'hbox',
					defaults : {
						labelAlign : 'right'
					}
				},
				items : [{
							items : [{
										fieldLabel : Oit.msg.eve.eventInformation.eventTitle,
										xtype : 'combobox',
										id : 'myCombo',
										name : 'eventTitle',
										editable : false,
										mode : 'remote',
										displayField : 'text',
										valueField : 'value',
										value : '质量警报',
										width : 250,
										store : new Ext.data.Store({
													fields : [{
																name : 'text',
																mapping : 'eventTitle'
															}, {
																name : 'value',
																mapping : 'eventTitle'
															}],
													autoLoad : true,
													proxy : {
														type : 'rest',
														url : 'eventInformation/eventTitle'
													}
												})
									}, {
										fieldLabel : Oit.msg.eve.eventInformation.eventStatus,
										xtype : 'combobox',
										name : 'eventStatus',
										editable : false,
										mode : 'remote',
										width : 200,
										displayField : 'text',
										valueField : 'eventStatus',
										store : new Ext.data.Store({
													fields : [{
																name : 'text'
															}, {
																name : 'eventStatus',
																mapping : 'value'
															}],
													autoLoad : true,
													proxy : {
														type : 'rest',
														url : 'eventInformation/eventStatus'
													}
												})
									}, {
										fieldLabel : Oit.msg.eve.eventInformation.batchNo,
										xtype : 'textfield',
										width : 250,
										name : 'batchNo'
									}, {
										fieldLabel : Oit.msg.eve.eventInformation.equipCode,
										xtype : 'combobox',
										name : 'equipCode',
										mode : 'remote',
										displayField : 'name',
										valueField : 'code',
										width : 420,
										store : new Ext.data.Store({
													fields : ['code', 'name'],
													autoLoad : true,
													proxy : {
														type : 'rest',
														url : 'eventInformation/equip'
													}
												}),
										listeners : {
											beforequery : function(e) {
												var combo = e.combo;
												if (!e.forceAll) {
													var value = e.query;
													combo.store.filterBy(function(record, id) {
																var text = record.get(combo.displayField);
																return (text.indexOf(value) != -1);
															});
													combo.expand();
													return false;
												}
											}
										}
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
						}, {
							itemId : 'exportToXls',
							text : Oit.btn.export
						}, '->', {
							xtype : 'panel',
							html : '<div style="background-color:#DFEAF2;width:256px;height:18px;">'
									+ '<span style="display:block;float:left;">紧急程度：</span>'
									+ '<span style="display:block;float:left;">一般</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:blue;display:block;float:left;border:1px solid #B3C1CF;"></span>'
									+ '<span style="display:block;float:left;">紧急</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:#FF8C30;display:block;float:left;border:1px solid #B3C1CF;"></span>'
									+ '<span style="display:block;float:left;">特急</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:red;display:block;float:left;border:1px solid #B3C1CF;"></span>'
									+ '</div>'
						}]

			}]
		}]
	}]
});

function eventInformationView(store, record, isRead) {
	var formPanel = null;
	var title = null;
	if (isRead) {
		formPanel = viewFormPanel();
		title = Oit.msg.eve.eventInformation.view;
	} else {
		formPanel = editFormPanel();
		title = Oit.msg.eve.eventInformation.edit;
	}
	var win = new Ext.window.Window({
				autoShow : true,
				title : title,
				width : 600,
				height : 470,
				modal : true,
				items : formPanel,
				buttons : [{
							text : '处理完毕',
							hidden : isRead,
							handler : function() {
								Ext.MessageBox.confirm('确认', '确认修改吗?', function(btn) {
											if (btn == 'yes') {
												var form = formPanel.getForm();
												if (form.isValid()) {
													Ext.Ajax.request({
																url : '/bsmes/eve/eventInformation/update',
																method : 'GET',
																params : {
																	'jsonText' : Ext.encode(form.getValues())
																},
																success : function(response) {
																	win.close();
																	store.reload();
																}
															});
												}
											}
										});
							}
						}, {
							text : '保存',
							hidden : isRead,
							handler : function() {
								Ext.MessageBox.confirm('保存', '确认保存吗?', function(btn) {
											if (btn == 'yes') {
												var form = formPanel.getForm();
												if (form.isValid()) {
													Ext.Ajax.request({
																url : '/bsmes/eve/eventInformation/save',
																method : 'GET',
																params : {
																	'jsonText' : Ext.encode(form.getValues())
																},
																success : function(response) {
																	win.close();
																	store.reload();
																}
															});
												}
											}
										});
							}
						}, {
							text : Oit.btn.close,
							handler : function() {
								win.close();
							}
						}]
			});
	formPanel.loadRecord(record);
	win.show();
}

function viewFormPanel() {
	return new Ext.form.Panel({
				bodyPadding : "10 10 10",
				frame : false,
				layout : "form",
				labelWidth : 10,
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 100,
					anchor : '100%'
				},
				items : [{
							fieldLabel : Oit.msg.eve.eventInformation.eventTitle,
							xtype : 'displayfield',
							name : 'eventTitle'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.eventContent,
							xtype : 'displayfield',
							name : 'eventContent'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.eventStatus,
							xtype : 'displayfield',
							name : 'eventStatus',
							renderer : function(value) {
								if (value == 'UNCOMPLETED') {
									return "未处理";
								} else if (value == 'RESPONDED') {
									return "已响应";
								} else if (value == 'PENDING') {
									return "待确认";
								} else {
									return "已完成";
								}
							}
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.equipCode,
							xtype : 'displayfield',
							name : 'equipCode'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.productCode,
							xtype : 'displayfield',
							name : 'productCode'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.pendingProcessing,
							xtype : 'displayfield',
							name : 'pendingProcessing',
							renderer : function(value) {
								if (value == 'true') {
									return "是";
								} else {
									return "否";
								}
							}
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.batchNo,
							xtype : 'displayfield',
							name : 'batchNo'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.createTime,
							xtype : 'datefield',
							name : 'createTime',
							readOnly : true,
							format : 'Y-m-d H:i:s'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.eventReason,
							xtype : 'textareafield',
							name : 'eventReason',
							readOnly : true,
							rows : 3,
							anchor : '80%'
						}, {
							fieldLabel : '处理方案',
							xtype : 'textareafield',
							name : 'eventResult',
							rows : 3,
							maxLength : 1000,
							anchor : '80%'
						}]
			});
}
function editFormPanel() {
	return new Ext.form.Panel({
				bodyPadding : "10 10 10",
				frame : false,
				layout : "form",
				labelWidth : 10,
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 100,
					anchor : '100%'
				},
				items : [{
							xtype : 'hiddenfield',
							name : 'id'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.eventTitle,
							xtype : 'textfield',
							fieldStyle : 'background-color:none;',
							name : 'eventTitle'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.eventContent,
							xtype : 'textfield',
							name : 'eventContent'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.eventStatus,
							xtype : 'displayfield',
							name : 'eventStatus',
							renderer : function(value) {
								if (value == 'UNCOMPLETED') {
									return "未处理";
								} else if (value == 'RESPONDED') {
									return "已响应";
								} else if (value == 'PENDING') {
									return "待确认";
								} else {
									return "已完成";
								}
							}
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.equipCode,
							xtype : 'displayfield',
							name : 'equipCode'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.productCode,
							xtype : 'displayfield',
							name : 'productCode'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.pendingProcessing,
							xtype : 'displayfield',
							name : 'pendingProcessing',
							renderer : function(value) {
								if (value == '1') {
									return "是";
								} else {
									return "否";
								}
							}
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.batchNo,
							xtype : 'displayfield',
							name : 'batchNo'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.createTime,
							xtype : 'datefield',
							name : 'createTime',
							readOnly : true,
							format : 'Y-m-d'
						}, {
							fieldLabel : Oit.msg.eve.eventInformation.eventReason,
							xtype : 'textareafield',
							name : 'eventReason',
							rows : 3,
							maxLength : 1000,
							anchor : '80%'
						}, {
							fieldLabel : '处理方案',
							xtype : 'textareafield',
							name : 'eventResult',
							rows : 3,
							maxLength : 1000,
							anchor : '80%'
						}]
			});
}