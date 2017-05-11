Ext.define("bsmes.view.ProductTraceGrid", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.productTraceGrid',
	store : 'ProductTraceStore',
	forceFit : false,
	columnLines : true,
	defaultEditingPlugin : false,
	columns : [{
		text : '客户合同号',
		dataIndex : 'CONTRACTNO',
		// flex : 2.12,
		width : 80,
		sortable : false,
		renderer : function(value, metaData, record) {
			var me = this;
			var reg = /[a-zA-Z]/g;
			value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length
					- 5) : value.replace(reg, ""));
			// + '[' + record.get('OPERATOR') + ']';
			metaData.tdAttr = 'data-qtip="' + value + '"';
			// value = '<a
			// style="width:16px;height:16px;display:block;float:left;"
			// class="icon_remove"></a>&nbsp;&nbsp;' + value;
			return value;
		}
	}, {
		text : '经办人',
		dataIndex : 'OPERATOR',
		// flex : 2.12,
		width : 60,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record) {
			var me = this;
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : '单位',
		dataIndex : 'CUSTOMERCOMPANY',
		flex : 2.4,
		minWidth : 120,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : '产品型号规格',
		dataIndex : 'PRODUCTTYPE',
		flex : 2.4,
		minWidth : 120,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			value = value + ' ' + record.get('PRODUCTSPEC');
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		},
		menuDisabled : true
	}, {
		text : '客户型号规格',
		dataIndex : 'CUSTPRODUCTTYPE',
		flex : 3.4,
		minWidth : 170,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			value = value + ' ' + record.get('CUSTPRODUCTSPEC');
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		},
		menuDisabled : true
	}, {
		text : '合同<br/>长度',
		dataIndex : 'CONTRACTLENGTH',
		flex : 1.2,
		minWidth : 60,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : '下达<br/>时间',
		dataIndex : 'OADATE',
		// flex : 1.2,
		width : 60,
		hidden : true,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			value = '';
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : '交货期',
		dataIndex : 'OADATE',
		// flex : 1.2,
		width : 60,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			value = value.substring(value.indexOf('-') + 1);
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : '附件',
		dataIndex : 'ID',
		// flex : 1,
		width : 50,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			var nofile = (record.get('ORDERFILENUM') == 0);
			var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"'
					+ (nofile ? '' : 'onclick="lookUpFile(\'' + value + '\', \'' + record.get('CONTRACTNO') + '\')"')
					+ '>附件</a>';
			return html;
		}
	}, {
		text : '备注',
		dataIndex : 'ID',
		flex : 8,
		// minWidth : 350,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			var processRequire = '技术要求:' + (record.get('PROCESSREQUIRE') == '' ? '无' : record.get('PROCESSREQUIRE'));
			var remarks = '备注:' + (record.get('REMARKS') == '' ? '无' : record.get('REMARKS'));
			var showStr = processRequire + '<br/>' + remarks;
			metaData.tdAttr = 'data-qtip="' + showStr + '"';
			return processRequire + '; ' + remarks;
		}
	}, {
		text : '当前工艺',
		dataIndex : 'CRAFTSCNAME',
		// flex : 1.5,
		width : 75,
		hidden : true,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			metaData.tdAttr = 'data-qtip="' + value + '"';
			value = '<a style="color:blue;cursor:pointer;" onclick="getInstanceProcessGrid(\'' + record.get('CRAFTSID')
					+ '\')">' + value + '</a>';
			return value;
		}
	}, {
		text : '订单<br/>状态',
		dataIndex : 'STATUS',
		// flex : 1.2,
		width : 150,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			var sendPercent = record.get("SPECIALFLAG");
			if (value == 'TO_DO') {
				return '未开始';
			} else if (value == 'IN_PROGRESS') {
				return '生产中';
			} else if (value == 'FINISHED') {
				if(sendPercent == '2'){
					return '已完成<font color=red>(手工单)</font>';
				}else if(sendPercent == '3'){
					return '已完成<font color=red>(成品现货)</font>';
				}else{
					return '已完成';
				}
			} else if (value == 'CANCELED') {
				return '已取消';
			} else if (value == 'PAUSE') {
				return '已暂停';
			} else {
				return '订单状态不明确'
			}
		}
	}, {
		text : '追溯',
		dataIndex : 'SENDDOWNPERCENT',
		flex : 1,
		minWidth : 50,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var sendPercent = record.get("SPECIALFLAG");
			if(sendPercent=='2'||sendPercent=='3'){
				value = '<font color=grey>查看</font>';
			}else{
				value = '<a style="color:blue;cursor:pointer;" onclick="showProductTraceDeatilWin(\'' + record.get('ID')
				+ '\')">查看</a>';
			}
			return value;
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
											url : '../pla/handSchedule/getcontractNo'
										},
										sorters : [{
													property : 'contractNo',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('productTraceGrid toolbar form')[0];
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
							fieldLabel : '经办人',
							xtype : 'combobox',
							name : 'operator',
							displayField : 'operator',
							valueField : 'operator',
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
										fields : ['operator'],
										proxy : {
											type : 'rest',
											url : '../pla/handSchedule/getoperator'
										},
										sorters : [{
													property : 'operator',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('productTraceGrid toolbar form')[0];
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
							fieldLabel : '单位',
							name : 'customerCompany',
							xtype : 'combobox',
							displayField : 'customerCompany',
							valueField : 'customerCompany',
							multiSelect : true,
							hidden : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										fields : ['customerCompany'],
										proxy : {
											type : 'rest',
											url : '../pla/handSchedule/getcustomerCompany'
										},
										sorters : [{
													property : 'customerCompany',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('productTraceGrid toolbar form')[0];
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
											url : '../pla/handSchedule/getcustproductType'
										},
										sorters : [{
													property : 'custProductType',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('productTraceGrid toolbar form')[0];
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
											url : '../pla/handSchedule/getproductType'
										},
										sorters : [{
													property : 'productType',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('productTraceGrid toolbar form')[0];
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
											url : '../pla/handSchedule/getproductSpec'
										},
										sorters : [{
													property : 'productSpec',
													direction : 'ASC'
												}],
										listeners : {
											beforeload : function(store, operation, eOpts) {
												var form = Ext.ComponentQuery.query('productTraceGrid toolbar form')[0];
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
							fieldLabel : '开始日期',
							xtype : 'datefield',
							name : 'createDate',
							format : 'Y-m-d',
							value : Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -5), "Y-m-d")
						}, {
							fieldLabel : '订单加工状态',
							xtype : 'checkboxgroup',
							width : 500,
							columns : 5,
							vertical : true,
							items : [{
										boxLabel : '生产中',
										name : 'status',
										inputValue : 'IN_PROGRESS',
										checked : true
									}, {
										boxLabel : '已完成',
										name : 'status',
										inputValue : 'FINISHED'
									}]
						}],
				buttons : [{
							text : Oit.btn.search,
							iconCls : 'icon_search',
							handler : function() {
								var store = Ext.ComponentQuery.query('productTraceGrid')[0].getStore();
								store.loadPage(1);
							}
						}, {
							text : '重置',
							handler : function(e) {
								this.up("form").getForm().reset();
//								var a = Ext.ComponentQuery.query('productTraceGrid combobox');
//								for (var i = 0; i < a.length; i++) {
//									a[i].getStore().load();
//								}
							}
						}, '->']
			}]
		}]
	}]

});

function showProductTraceDeatilWin(orderItemId) {
	Ext.Msg.wait('数据查询中，请稍后...', '提示');
	Ext.Ajax.request({
				url : 'productTrace/getTraceInitData?orderItemId=' + orderItemId,
				success : function(response) {
					Ext.Msg.hide(); // 隐藏进度条
					var result = Ext.decode(response.responseText);
					var win = Ext.create('bsmes.view.ProductTraceDeatilWindow', {
								allDatas : result
							});
					win.show();
				},
				failure : function(response, action) {
					Ext.Msg.hide(); // 隐藏进度条
					Ext.Msg.alert(Oit.msg.WARN, '查询数据失败！');
				}
			});
};