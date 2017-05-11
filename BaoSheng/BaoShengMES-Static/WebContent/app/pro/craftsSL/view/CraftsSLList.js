Ext.define('bsmes.view.CraftsSLList', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.craftsSLList',
			store : 'CraftsSLStore',
			columns : [{
						text : Oit.msg.pro.crafts.craftsName,
						flex : 1.5,
						dataIndex : 'craftsName'
					}, {
						text : Oit.msg.pro.crafts.productName,
						flex : 1.5,
						dataIndex : 'productName'
					}, {
						text : Oit.msg.pro.crafts.productCode,
						flex : 1.5,
						dataIndex : 'productCode'
					}, {
						text : Oit.msg.pro.crafts.craftsVersion,
						flex : 0.6,
						dataIndex : 'craftsVersion'
					}, {
						text : Oit.msg.pro.crafts.startDate,
						flex : 1,
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
						dataIndex : 'startDate'
					}, {
						text : Oit.msg.pro.crafts.endDate,
						flex : 1,
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
						dataIndex : 'endDate'
					}
			// , {
			// text : Oit.msg.pro.crafts.isDefault,
			// flex: 0.6,
			// sortable: false,
			// dataIndex : 'isDefaultText'
			// }
			],
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
															name : 'craftsCode',
															fieldLabel : Oit.msg.pro.crafts.craftsName,
															emptyText : Oit.msg.comboEmptySearchMsg,
															xtype : 'combobox',
															displayField : 'craftsName',
															valueField : 'craftsCode',
															queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
															minChars : 3, // 最少几个字开始查询
															triggerAction : 'all', // 请设置为”all”,否则默认
															// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
															typeAhead : true, // 是否延迟查询
															typeAheadDelay : 1000, // 延迟时间
															store : new Ext.data.Store({
																		proxy : {
																			type : 'rest',
																			url : 'craftsSL/craftsCombo'
																		},
																		fields : ['craftsCode', 'craftsName']
																	}),
															listeners : {
																'beforequery' : function(e, eOpts) {
																	var combo = e.combo;
																	if (e.query) {
																		combo.getStore().load({
																					params : {
																						query : e.query
																					}
																				});
																		combo.expand(); // 展开
																	}
																}
															}
														}, {
															name : 'productName',
															fieldLabel : Oit.msg.pro.crafts.productName,
															emptyText : Oit.msg.pro.crafts.productName,
															xtype : 'combobox',
															displayField : 'productName',
															valueField : 'productName',
															queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
															minChars : 3, // 最少几个字开始查询
															triggerAction : 'all', // 请设置为”all”,否则默认
															// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
															typeAhead : true, // 是否延迟查询
															typeAheadDelay : 1000, // 延迟时间
															store : new Ext.data.Store({
																		proxy : {
																			type : 'rest',
																			url : 'craftsBz/productsCombo'
																		},
																		fields : ['productName', 'productCode']
																	}),
															listeners : {
																'beforequery' : function(e, eOpts) {
																	var combo = e.combo;
																	if (e.query) {
																		combo.getStore().load({
																					params : {
																						query : e.query
																					}
																				});
																		combo.expand(); // 展开
																	}
																}
															}
														}, {
															fieldLabel : Oit.msg.pro.crafts.productCode,
															name : 'productCode',
															emptyText : Oit.msg.pro.crafts.productCode,
															xtype : 'combobox',
															displayField : 'productCode',
															valueField : 'productCode',
															queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
															minChars : 3, // 最少几个字开始查询
															triggerAction : 'all', // 请设置为”all”,否则默认
															// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
															typeAhead : true, // 是否延迟查询
															typeAheadDelay : 1000, // 延迟时间
															store : new Ext.data.Store({
																		proxy : {
																			type : 'rest',
																			url : 'craftsBz/productsCombo'
																		},
																		fields : ['productName', 'productCode']
																	}),
															listeners : {
																'beforequery' : function(e, eOpts) {
																	var combo = e.combo;
																	if (e.query) {
																		combo.getStore().load({
																					params : {
																						query : e.query
																					}
																				});
																		combo.expand(); // 展开
																	}
																}
															}
														}, {
															fieldLabel : Oit.msg.pro.crafts.From,
															xtype : 'datefield',
															name : 'startDate',
															hidden : true,
															format : 'Y-m-d'
														}, {
															fieldLabel : Oit.msg.pro.crafts.To,
															xtype : 'datefield',
															name : 'endDate',
															hidden : true,
															format : 'Y-m-d'
														}],
												buttons : [{
															itemId : 'search'
														}, {
															itemId : 'reset'
														}, {
															itemId : 'processDetail',
															text : '查看产品工序'
														}, '->']
											}]
								}]
					}

			]
		});