Ext.define('bsmes.view.ProductQCResultList', {
	extend : 'Oit.app.view.Grid',
	id : 'productQCResultListId',
	alias : 'widget.productQCResultList',
//	store : 'ProductQCResultStore',
	defaultEditingPlugin : false,
	forceFit : false,
	columns : [ {
		text : Oit.msg.pro.productQCResult.sampleBarcode,
		dataIndex : 'sampleBarcode',
		width : '21%'
	}, {
		text : Oit.msg.pro.productQCResult.productCode,
		dataIndex : 'productCode',
		width : '21%'
	}, {
		text : Oit.msg.pro.productQCResult.createTime,
		dataIndex : 'createTime',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		width : '22%'
	}, {
		text : Oit.msg.pro.productQCResult.conclusion,
		dataIndex : 'conclusion',
		width : '24%'
	} ],
	actioncolumn : [
			'',
			{
				itemId : 'edit',
				width : 10,
				handler : function(grid, rowIndex) {
					editQCResult(grid, rowIndex, false);
				}
			},
			'',
			{
				tooltip : Oit.msg.pro.productQCResult.search,
				iconCls : 'icon_detail',
				width : 10,
				handler : function(grid, rowIndex) {
					editQCResult(grid, rowIndex, true);
				}
			},
			'',
			{
				itemId : 'remove',
				width : 10,
				handler : function(grid, rowIndex) {
					var data = grid.getStore().getAt(rowIndex).getData();
					if (data) {
						Ext.Ajax.request({
							url : '/bsmes/pro/productQCResult/delete',
							method : 'GET',
							params : {
								'id' : data.id
							},
							success : function(response) {
								if (response) {
									grid.getStore().reload();
								}
							}
						});
					}

				}
			},
			'',
			{
				tooltip : Oit.msg.pro.productQCResult.alert,
				iconCls : 'icon_warn',
				handler : function(grid, rowIndex) {
					Ext.MessageBox.confirm(Oit.msg.pro.productQCResult.alert,
							'确认生成警报?', function(opt) {
								if (opt == 'yes') {
									Ext.MessageBox.confirm(
											Oit.msg.pro.productQCResult.alert,
											'是否拦截在制品?', function(opt) {
												if (opt == 'yes') {
													QCAlertGeneration(grid,
															rowIndex, true);
												} else {
													QCAlertGeneration(grid,
															rowIndex, false);
												}
											});

								}
							});
				}
			} ],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			title : '查询条件',
			xtype : 'fieldset',
			width : '100%',
			items : [ {
				xtype : 'form',
				width : '100%',
				layout : 'hbox',
				buttonAlign : 'left',
				labelAlign : 'right',
				bodyPadding : 5,
				defaults : {
					labelWidth : 40
				},
				items : [ {
					fieldLabel : Oit.msg.pro.productQCResult.sampleBarcode,
					xtype : 'textfield',
					name : 'sampleBarcode'
				}, {
					fieldLabel : Oit.msg.pro.productQCResult.productCode,
					name : 'productCode',
					xtype : 'combobox',
					margin : '0 0 0 20',
					width : 320,
					mode : 'remote',
					displayField : 'productName',
					valueField : 'productCode',
					store : new Ext.data.Store({
						fields : [ 'productName', 'productCode' ],
						autoLoad : true,
						proxy : {
							type : 'rest',
							url : 'productQCResult/product'
						}
					})
				} ],
				buttons : [ {
					itemId : 'search',
					text : Oit.btn.search
				}, {
					itemId : 'reset',
					text : '重置',
					handler : function(e) {
						this.up("form").getForm().reset();
					}
				}, {
					itemId : 'addProductTemp',
					text : '添加',
					handler : function(e) {
						addQCResult();
					}
				}, {
                    itemId:'export',
                    text:Oit.btn.export
                } ]

			} ]
		} ]
	} ]
});
//用于存放添加 字段的store
var insertStore = null;
//用于存放文本框样品代码
var sampleCode = "";
function addQCResult() {
	sampleCode = "";
	var northPanel = new Ext.form.Panel({
		defaultType : 'textfield',
		bodyPadding : 5,
		layout : 'column',
		fieldDefaults : {
			anchor : '100%',
			labelAlign : 'right'
		},
		items : [ {
			fieldLabel : Oit.msg.pro.productQCResult.sampleBarcode,
			width : '33%',
			name : 'sampleBarcode',
			listeners : {
				specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						if (this.value == '' || this.value == null) {
							return;
						} else {
							sampleCode = this.value;
							var url = 'productQCDetail/getReslist';
							gridStore.getProxy().url = url;
							gridStore.load({
								params : {
									sampleCode : sampleCode
								}
							});
						}
					}
				},
				blur : function(e) {
					sampleCode = e.value;
					if (sampleCode == null || sampleCode == '') {
						return;
					}
					var url = 'productQCDetail/getReslist';
					gridStore.getProxy().url = url;
					gridStore.load({
						params : {
							sampleCode : sampleCode
						}
					});
				}
			}
		}, {
			fieldLabel : Oit.msg.pro.productQCResult.productCode,
			width : '33%',
			xtype : 'displayfield',
			name : 'productCode'
		}, {
			fieldLabel : Oit.msg.pro.productQCResult.conclusion,
			width : '33%',
			name : 'conclusion',
		} ],
		resets : function() {
			this.getForm().reset();
		}
	});
	var gridStore = new Ext.data.Store({
		fields : [ 'qcResult', 'productCode', 'name', 'wireRequest',
				'preProcess', 'environmentParameter', 'environmentValue',
				'matRequest', 'equipRequest', 'characterDesc',
				'characterValue', 'refContent', 'remarks' ],
		proxy : {
			type : 'rest',
			url : 'productQCDetail/getReslist/' + sampleCode,
			reader : {
				type : 'json',
				root : 'rows'
			}
		}
	});
	gridStore.on('load', function(store, record, opts) {
		if (record != null && gridStore.getCount() > 0) {
			northPanel.loadRecord(record[0]);
			insertStore = store;
		} else {
			insertStore = null;
			northPanel.getForm().reset();
		}
	});
	getWindows(getGridPanel(gridStore, false), northPanel, null, false).show();
}

function editQCResult(grid, rowIndex, isView) {
	var data = grid.getStore().getAt(rowIndex).data;
	var store = new Oit.app.data.GridStore({
		fields : [ 'qcResult', 'productCode', 'name', 'wireRequest',
				'preProcess', 'environmentParameter', 'environmentValue',
				'matRequest', 'equipRequest', 'characterDesc',
				'characterValue', 'refContent', 'remarks' ],
		proxy : {
			type : 'rest',
			url : 'productQCDetail/getByResId/' + data.id,
			reader : {
				type : 'json',
				root : 'rows'
			}
		}
	});
	var gridPanel = getGridPanel(store, isView);
	gridPanel.on('edit', function(editor, e) {
		var data = e.record.data;
		if(isView){
			return;
		}
		if (data.qcResult == null || data.qcResult == '') {
			Ext.Msg.alert('info', '请输入结论');
			return;
		}
		Ext.Ajax.request({
			url : '/bsmes/pro/productQCDetail/updateQCDetail',
			params : {
				'id' : data.id,
				'qcResult' : data.qcResult
			},
			success : function(response) {
				e.record.commit();
			}
		});
	});

	var northPanel = new Ext.form.Panel({
		defaultType : 'textfield',
		bodyPadding : 5,
		layout : 'column',
		fieldDefaults : {
			anchor : '100%',
			labelAlign : 'right'
		},
		items : [ {
			xtype : 'hiddenfield',
			name : 'id'
		}, {
			fieldLabel : Oit.msg.pro.productQCResult.sampleBarcode,
			xtype : 'displayfield',
			width : '33%',
			name : 'sampleBarcode',
		}, {
			fieldLabel : Oit.msg.pro.productQCResult.productCode,
			width : '33%',
			xtype : 'displayfield',
			name : 'productCode'
		}, {
			fieldLabel : Oit.msg.pro.productQCResult.conclusion,
			width : '33%',
			disabled : isView,
			name : 'conclusion',
		} ]
	});

	northPanel.loadRecord(grid.getStore().getAt(rowIndex));
	getWindows(gridPanel, northPanel, grid, isView).show();
}

function getGridPanel(store, isview) {
	var grid = new Oit.app.view.Grid({
		store : store,
		width : 990,
		height : document.body.scrollHeight - 160,
		stripeRows : true,
		defaultEditingPlugin : !isview,
		forceFit : false,
		plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 1
		}) ],
		columns : [ {
			text : '序号',
			xtype : 'rownumberer',
			width : 50
		}, {
			text : Oit.msg.pro.productQCTemplate.name,
			dataIndex : 'name',
			width : 200
		}, {
			text : Oit.msg.pro.productQCTemplate.testMethod,
			columns : [ {
				text : Oit.msg.pro.productQCTemplate.productCode,
				dataIndex : 'productCode',
				width : 200
			}, {
				text : Oit.msg.pro.productQCTemplate.wireRequest,
				dataIndex : 'wireRequest',
				width : 200
			}, {
				text : Oit.msg.pro.productQCTemplate.preProcess,
				dataIndex : 'preProcess',
				width : 200
			}, {
				text : Oit.msg.pro.productQCTemplate.environmentParameter,
				dataIndex : 'environmentParameter',
				width : 200
			}, {
				text : Oit.msg.pro.productQCTemplate.environmentValue,
				dataIndex : 'environmentValue',
				width : 200
			}, {
				text : Oit.msg.pro.productQCTemplate.matRequest,
				dataIndex : 'matRequest',
				width : 200
			}, {
				text : Oit.msg.pro.productQCTemplate.equipRequest,
				dataIndex : 'equipRequest',
				width : 200
			} ]
		}, {
			text : Oit.msg.pro.productQCTemplate.performanceRequ,
			columns : [ {
				text : Oit.msg.pro.productQCTemplate.characterDesc,
				dataIndex : 'characterDesc',
				width : 100
			}, {
				text : Oit.msg.pro.productQCTemplate.characterValue,
				dataIndex : 'characterValue',
				width : 100
			} ]
		}, {
			text : Oit.msg.pro.productQCTemplate.refContent,
			dataIndex : 'refContent',
			width : 200
		}, {
			text : Oit.msg.pro.productQCTemplate.remarks,
			dataIndex : 'remarks',
			width : 200
		}, {
			text : Oit.msg.pro.productQCResult.qcResult,
			editor : 'textfield',
			dataIndex : 'qcResult',
			width : 200
		}],
		listeners:{
			'beforeedit':function(e){
				if(isview){
					return false;
				}
			}
		}
	});
	Ext.each(grid.dockedItems.items, function(item, index) {
		if (item.xtype == 'pagingtoolbar') {
			item.hidden = true;
		}
	});
	return grid;
}

function getWindows(gridPanel, northPanel, grid, isHide) {
	var formWindow = new Ext.window.Window(
			{
				title : Oit.msg.pro.productQCResult.title,
				width : 1000,
				height : document.body.scrollHeight - 20,

				layout : 'border',
				closeAction : 'destory',
				items : [ {
					region : 'north',
					items : Ext.create('Ext.panel.Panel', {
						height : 50,
						layout : 'fit',
						items : northPanel
					})
				}, {
					region : 'center',
					items : Ext.create('Ext.panel.Panel', {

						height : document.body.scrollHeight - 140,
						border : true,
						items : gridPanel
					})
				} ],
				buttons : [{
							text : Oit.btn.ok,
							hidden : isHide,
							handler : function() {
								var form = northPanel.getForm();
								var formValue = northPanel.getForm()
										.getValues();
								if (formValue['conclusion'] == '') {
									Ext.Msg.alert('info', '检验结论为空,无法提交');
									return;
								}
								//根据grid是否为null 判断是添加或者更新
								if (grid == null) {
									if (insertStore != null) {
										var commRec = new Array();
										var result = false;
										insertStore.each(function(record) {
											var data = record.data;
											if (data.qcResult == null || data.qcResult == '') {										
												commRec.push(data);
											} else {
												commRec.push(data);
											}
										});
										if (!result) {
											Ext.Ajax.request({
														url : '/bsmes/pro/productQCDetail/insert',
														method : 'POST',
														params : {
															'jsonText' : Ext.encode(form.getValues()),
															'resDet' : Ext.encode(commRec)
														},
														success : function(response) {
															formWindow.close();
															var productQCResultGrid = Ext.getCmp('productQCResultListId');
															productQCResultGrid.getStore().reload();
														}
											});
										} else {
											Ext.Msg.alert('info', '结论为空,无法提交');
											return;
										}
									} else {
										formWindow.close();
									}
								} else {
									if (form.isValid()) {
										Ext.Ajax.request({
											url : '/bsmes/pro/productQCResult/update',
											method : 'GET',
											params : {
												'jsonText' : Ext.encode(form.getValues())
											},
											success : function(response) {
												if (grid != null) {
													grid.getStore().reload();
												}
												formWindow.close();
											}
								      });
									}
								}

							}
						}, {
							text : Oit.btn.close,
							handler : function() {
								formWindow.close()
							}
						}]
			});
	return formWindow;
}
function QCAlertGeneration(grid, rowIndex, flag) {
	var data = grid.getStore().getAt(rowIndex).data;
	Ext.Ajax.request({
		url : '/bsmes/pro/productQCResult/generation',
		method : 'GET',
		params : {
			'jsonText' : data.productCode,
			'checked' : flag
		},
		success : function(response) {
			Ext.Msg.alert('info', '警报创建成功');
		},
		failure : function(response) {
			Ext.Msg.alert('info', '警报创建失败');
		}
	});
}