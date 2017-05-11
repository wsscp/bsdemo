Ext.define('bsmes.view.InventoryList', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.inventoryList',
			store : 'InventoryStore',
			columns : [{
						text : '物料编码',
						dataIndex : 'materialCode',
						minWidth : 100,
						flex : 2
					}, {
						text : '物料名称',
						dataIndex : 'materialName',
						minWidth : 100,
						flex : 2
					}, {
						text : '物料信息',
						dataIndex : 'materialDesc',
						minWidth : 100,
						flex : 2
					}, {
						text : '库存量',
						dataIndex : 'quantity',
						minWidth : 80,
						flex : 1.6
					}, {
						text : '单位',
						dataIndex : 'unit',
						minWidth : 50,
						flex : 1
					}, {
						text : '所在工序',
						dataIndex : 'processName',
						minWidth : 80,
						flex : 1.6
					}, {
						text : '仓库',
						dataIndex : 'warehouseName',
						minWidth : 75,
						flex : 1.5
					}, {
						text : '库位',
						dataIndex : 'locationName',
						minWidth : 75,
						flex : 1.5
					}, {
						text : '位置X',
						dataIndex : 'locationX',
						minWidth : 70,
						flex : 1.4
					}, {
						text : '位置Y',
						dataIndex : 'locationY',
						minWidth : 70,
						flex : 1.4
					}, {
						text : '位置Z',
						dataIndex : 'locationZ',
						minWidth : 70,
						flex : 1.4
					}],
			actioncolumn : [{
						itemId : 'edit'
					}],
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						items : [{
									title : '查询条件',
									xtype : 'fieldset',
									collapsible : true,
									width : '99%',
									items : [{
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
															items : [{
																		fieldLabel : '仓库',
																		name : 'warehouseId',
																		xtype : 'combobox',
																		displayField : 'warehouseName',
																		valueField : 'id',
																		store : new Ext.data.Store({
																					fields : ['id', 'warehouseName'],
																					proxy : {
																						type : 'rest',
																						url : 'inventory/wareHouseCombox'
																					}
																				})
																	}, {
																		fieldLabel : '库位',
																		name : 'locationName',
																		xtype : 'combobox',
																		displayField : 'locationName',
																		valueField : 'locationName',
																		store : new Ext.data.Store({
																					fields : ['locationName'],
																					proxy : {
																						type : 'rest',
																						url : 'inventory/invLocationCombox'
																					}
																				})
																	}, {
																		fieldLabel : '所在工序',
																		name : 'processCode',
																		xtype : 'combobox',
																		displayField : 'name',
																		valueField : 'code',
																		store : new Ext.data.Store({
																					fields : ['code', 'name'],
																					proxy : {
																						type : 'rest',
																						url : '../pro/processInfo/getAllProcess'
																					}
																				})
																	}, {
																		fieldLabel : '物料编码',
																		name : 'materialCode',
																		xtype : 'textfield'
																	}],
															buttons : [{
																		text : '新增',
																		itemId : 'add'
																	}, {
																		text : '修改',
																		itemId : 'edit'
																	}, '->', {
																		text : '查找',
																		itemId : 'search'
																	}, {
																		text : '重置',
																		itemId : 'reset',
																		handler : function(e) {
																			this.up("form").getForm().reset();
																		}
																	}]
														}]
											}]
								}]
					}]
		});