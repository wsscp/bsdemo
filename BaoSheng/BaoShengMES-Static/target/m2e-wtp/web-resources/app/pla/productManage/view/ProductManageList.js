/**
 * 主列表：订单产品列表
 */

Ext.define("bsmes.view.ProductManageList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.productManageList',
	store : 'ProductManageStore',
	defaultEditingPlugin : false,
	forceFit : false,
	columnLines : true,
	selType : 'checkboxmodel',
	selModel : {
		mode : "SIMPLE", // "SINGLE"/"SIMPLE"/"MULTI"
		checkOnly : true
	},
	/*viewConfig : {
		stripeRows : false,
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rowParams, store) {
			var specialFlag = record.get("SPECIALFLAG"); // 计划是否已报
			if (specialFlag == '0') {
				return 'x-grid-record-orange-color';
			} else if (specialFlag == '1') {
				return 'x-grid-record-green-color';
			}
			return '';
		}
	},*/

	columns : [{
		text : Oit.msg.pla.orderOA.contractNo,
		dataIndex : 'contractNo',
		minWidth : 50,
		flex : 0.5,
		sortable : false,
		renderer : function(value, metaData, record) {
			var me = this;
			var reg = /[a-zA-Z]/g;
			value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length
					- 5) : value.replace(reg, ""));
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : '经办人',
		dataIndex : 'operator',
		minWidth : 50,
		flex : 0.5,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record) {
			var me = this;
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.customerOrderItem.customerCompany,
		dataIndex : 'customerCompany',
		flex : 1.2,
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
		dataIndex : 'productType',
		flex : 1.2,
		minWidth : 120,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			value = value + ' ' + record.get('productSpec');
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		},
		menuDisabled : true
	}, {
		text : '客户型号规格',
		dataIndex : 'custProductType',
		flex : 1.7,
		minWidth : 170,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			value = value + ' ' + record.get('custProductSpec');
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		},
		menuDisabled : true
	}, {
		text : '合同<br/>长度',
		dataIndex : 'contractLength',
		flex : 0.5,
		minWidth : 50,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : '交货期',
		dataIndex : 'oaDate',
		minWidth : 80,
		flex : 0.8,
		menuDisabled : true,
		renderer : Ext.util.Format.dateRenderer('y-m-d')
	}, {
		text : '附件',
		dataIndex : 'customerOrderItemId',
		minWidth : 50,
		flex : 0.5,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			var nofile = (record.get('orderFileNum') == 0);
			var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"'
					+ (nofile ? '' : 'onclick="lookUpFile(\'' + value + '\', \'' + record.get('contractNo') + '\')"')
					+ '>附件</a>';
			return html;
		}
	}, {
		text : '备注',
		minWidth : 300,
		flex : 3,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			var processRequire = '技术要求:' + (record.get('processRequire') == '' ? '无' : record.get('processRequire'));
			var remarks = '备注:' + (record.get('remarks') == '' ? '无' : record.get('remarks'));
			var showStr = processRequire + '<br/>' + remarks;
			metaData.tdAttr = 'data-qtip="' + showStr + '"';
			return processRequire + '; ' + remarks;
		}
	}, {
		text : '订单<br/>状态',
		dataIndex : 'status',
		minWidth : 60,
		flex : 0.6,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			if (value == 'FINISHED') {
				return '已完成';
			} else if (value == 'QUALIFIED') {
				return '订单合格';
			} else if (value == 'NOTQUALIFIED') {
				return '不合格';
			} else {
				return '订单状态不明确'
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
			width : '100%',
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
								fieldLabel : '客户合同号',
								xtype : 'combobox',
								name : 'contractNo',
								displayField : 'contractNo',
								valueField : 'contractNo',
								multiSelect : true,
								queryMode : 'local',
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['contractNo'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getcontractNo/-1'
											},
											sorters : [{
														property : 'contractNo',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										conEmpty = true;
										combo.collapse(); // 折叠
										if (!e.forceAll) {
											conEmpty = false;
											var value = e.query;
											if (value == null || value == '') {
												value = -1
											}
											combo.store.getProxy().url = 'handSchedule/getcontractNo' + '/' + value;
											var f = this.up("toolbar").down("form").getForm().getValues();
											combo.store.load({
														params : f
													});
											combo.expand(); // 展开
											return false;
										}
									},
									expand : function(e) {
										if (conEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getcontractNo/-1';
											e.getStore().load({
														params : f
													});
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
								queryMode : 'local',
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['operator'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getoperator/-1'
											},
											sorters : [{
														property : 'operator',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										opeaEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											opeaEmpty = false
											var value = e.query;
											if (value == null || value == '') {
												value = -1
											}
											combo.store.getProxy().url = 'handSchedule/getoperator' + '/' + value;
											var f = this.up("toolbar").down("form").getForm().getValues();
											combo.store.load({
														params : f
													});
											combo.expand(); // 展开
											return false;
										}
									},
									expand : function(e) {
										if (opeaEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getoperator/-1';
											e.getStore().load({
														params : f
													});
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
								queryMode : 'local',
								store : new Ext.data.Store({
											fields : ['customerCompany'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getcustomerCompany/-1'
											},
											sorters : [{
														property : 'customerCompany',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										comEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											comEmpty = false
											var value = e.query;
											if (value == null || value == '') {
												value = -1
											}
											combo.store.getProxy().url = 'handSchedule/getcustomerCompany' + '/'
													+ value;
											var f = this.up("toolbar").down("form").getForm().getValues();
											combo.store.load({
														params : f
													});
											combo.expand(); // 展开
											return false;
										}
									},
									expand : function(e) {
										if (comEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getcustomerCompany/-1';
											e.getStore().load({
														params : f
													});
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
								queryMode : 'local',
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['custProductType'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getcustproductType?query=-1'
											},
											sorters : [{
														property : 'custProductType',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										ctypeEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											ctypeEmpty = false;
											var value = e.query;
											if (value == null || value == '') {
												value = -1
											}
											combo.store.getProxy().url = 'handSchedule/getcustproductType?query='
													+ value;
											var f = this.up("toolbar").down("form").getForm().getValues();
											combo.store.load({
														params : f
													});
											combo.expand(); // 展开
											return false;
										}
									},
									expand : function(e) {
										if (ctypeEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getcustproductType?query=-1';
											e.getStore().load({
														params : f
													});
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
								queryMode : 'local',
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['productType'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getproductType?query=-1'
											},
											sorters : [{
														property : 'productType',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										typeEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											typeEmpty = false;
											var combo = e.combo;
											combo.collapse();
											if (!e.forceAll) {
												var value = e.query;
												if (value == null || value == '') {
													value = -1
												}
												combo.store.getProxy().url = 'handSchedule/getproductType?query='
														+ value;
												var f = this.up("toolbar").down("form").getForm().getValues();
												combo.store.load({
															params : f
														});
												combo.expand(); // 展开
												return false;
											}
										}
									},
									expand : function(e) {
										if (typeEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getproductType?query=-1';
											e.getStore().load({
														params : f
													});
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
								queryMode : 'local',
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['productSpec'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getproductSpec/-1'
											},
											sorters : [{
														property : 'productSpec',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										specEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											specEmpty = false
											var value = e.query;
											if (value == null || value == '') {
												value = -1
											}
											combo.store.getProxy().url = 'handSchedule/getproductSpec' + '/' + value;
											var f = this.up("toolbar").down("form").getForm().getValues();
											combo.store.load({
														params : f
													});
											combo.expand(); // 展开
											return false;
										}
									},
									expand : function(e) {
										if (specEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getproductSpec/-1';
											e.getStore().load({
														params : f
													});
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
								queryMode : 'local',
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['wiresStructure'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getwiresStructure/-1'
											},
											sorters : [{
														property : 'wiresStructure',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										wireEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											wireEmpty = false;
											var value = e.query;
											if (value == null || value == '') {
												value = -1
											}
											combo.store.getProxy().url = 'handSchedule/getwiresStructure' + '/' + value;
											var f = this.up("toolbar").down("form").getForm().getValues();
											combo.store.load({
														params : f
													});
											combo.expand(); // 展开
											return false;
										}
									},
									expand : function(e) {
										if (wireEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getwiresStructure/-1';
											e.getStore().load({
														params : f
													});
										}
									}
								}
							}, {
								fieldLabel : '线芯数',
								xtype : 'combobox',
								name : 'numberOfWires',
								displayField : 'numberOfWires',
								valueField : 'numberOfWires',
								multiSelect : true,
								queryMode : 'local',
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['numberOfWires'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getnumberOfWires/-1'
											},
											sorters : [{
														property : 'numberOfWires',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										numWireEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											numWireEmpty = false;
											var value = e.query;
											if (value == null || value == '') {
												value = -1
											}
											combo.store.getProxy().url = 'handSchedule/getnumberOfWires' + '/' + value;
											var f = this.up("toolbar").down("form").getForm().getValues();
											combo.store.load({
														params : f
													});
											combo.expand(); // 展开
											return false;
										}
									},
									expand : function(e) {
										if (numWireEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getnumberOfWires/-1';
											e.getStore().load({
														params : f
													});
										}
									}
								}
							}, {
								fieldLabel : '截面',
								xtype : 'combobox',
								name : 'section',
								displayField : 'section',
								valueField : 'section',
								multiSelect : true,
								queryMode : 'local',
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['section'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getSection?query=-1'
											},
											sorters : [{
														property : 'section',
														direction : 'ASC'
													}]
										}),
								listeners : {
									beforequery : function(e) {
										sectionEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											sectionEmpty = false;
											var combo = e.combo;
											combo.collapse();
											if (!e.forceAll) {
												var value = e.query;
												if (value == null || value == '') {
													value = -1
												}
												combo.store.getProxy().url = 'handSchedule/getSection?query=' + value;
												var f = this.up("toolbar").down("form").getForm().getValues();
												combo.store.load({
															params : f
														});
												combo.expand(); // 展开
												return false;
											}
										}
									},
									expand : function(e) {
										if (sectionEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'handSchedule/getSection?query=-1';
											e.getStore().load({
														params : f
													});
										}
									}
								}
							}, {
								fieldLabel : Oit.msg.pla.orderOA.orderItemStatus,
								xtype : 'checkboxgroup',
								width : 600,
								columns : 5,
								vertical : true,
								items : [{
											boxLabel : '已完成',
											name : 'status',
											inputValue : 'FINISHED',
											checked : true
										}, {
											boxLabel : '订单合格',
											name : 'status',
											inputValue : 'QUALIFIED',
											checked : true
										}, {
											boxLabel : '订单不合格',
											name : 'status',
											inputValue : 'NOTQUALIFIED'
										}]
							}]
				}],
				buttons : [{
							itemId : 'search',
							text : '查找'
						}, {
							itemId : 'reset',
							text : '重置',
							handler : function(e) {
								this.up("form").getForm().reset();
								var a = Ext.ComponentQuery.query('handScheduleJYGrid combobox');
								for (var i = 0; i < a.length; i++) {
									a[i].getStore().load();
								}
							}
						},{
							itemId : 'finished',
							text : '完成订单'
						}]
			}]
		}]
	}]

});

function lookUpFile(orderItemId, contractNoStr) {
	var contractNo = new Array();
	contractNo.push(contractNoStr);
	var wint = Ext.create('bsmes.view.LookUpAttachFileWindow', {
				orderItemId : orderItemId,
				contractNo : contractNo
			});
	wint.show();
};
