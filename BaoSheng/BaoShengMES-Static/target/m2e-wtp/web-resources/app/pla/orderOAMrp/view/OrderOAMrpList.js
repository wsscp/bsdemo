Ext.define("bsmes.view.OrderOAMrpList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.orderOAMrpList',
	store : 'OrderOAMrpStore',
	forceFit : false,
	defaultEditingPlugin : false,
	columns : [{
		text : Oit.msg.pla.orderOA.contractNo,
		dataIndex : 'contractNo',
		flex : 2,
		minWidth : 100,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record) {
			var reg = /[a-zA-Z]/g;
			value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length - 5) : value
					.replace(reg, ""))
					+ '[' + record.get('operator') + ']';
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.customerOrderItem.customerCompany,
		dataIndex : 'customerCompany',
		flex : 3.6,
		minWidth : 180,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : '客户型号规格',
		dataIndex : 'custProductType',
		flex : 3,
		minWidth : 150,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			value = value + ' ' + record.get('productSpec');
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.mrp.matName,
		dataIndex : 'matName',
		flex : 2,
		minWidth : 100,
		menuDisabled : true,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.mrp.matCode,
		dataIndex : 'matCode',
		flex : 3,
		minWidth : 150,
		menuDisabled : true,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.mrp.quantity,
		dataIndex : 'quantity',
		flex : 1.6,
		minWidth : 80,
		menuDisabled : true, // 
		renderer : function(value, metaData, record) {
			if (record.get('unit') != '') {
				value += ' ' + record.get('unit');
			}
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.equipCode,
		dataIndex : 'equipName',
		flex : 6,
		minWidth : 300,
		menuDisabled : true,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.mrp.planDate,
		dataIndex : 'planDate',
		xtype : 'datecolumn',
		format : 'm-d H:i',
		flex : 1.6,
		minWidth : 80,
		menuDisabled : true
	}, {
		text : Oit.msg.pla.orderOA.mrp.status,
		dataIndex : 'status',
		flex : 1.2,
		minWidth : 60,
		renderer : function(value) {
			switch (value) {
				case 'UNAUDITED' :
					return '未审核';
				case 'AUDITED' :
					return '已审核';
				case 'CANCELED' :
					return '已取消';
				case 'ISSUED' :
					return '已下发';
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
														fieldLabel : Oit.msg.pla.orderOA.contractNo,
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
																		url : 'handSchedule/getcustproductType/-1'
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
																	combo.store.getProxy().url = 'handSchedule/getcustproductType' + '/'
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
																	e.getStore().getProxy().url = 'handSchedule/getcustproductType/-1';
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
																	combo.store.getProxy().url = 'handSchedule/getproductSpec' + '/'
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
														fieldLabel : Oit.msg.pla.orderOA.mrp.matName,
														name : 'matCode',
														xtype : 'combobox',
														emptyText : Oit.msg.comboEmptySearchMsg,
														displayField : 'matName',
														valueField : 'matName',
														multiSelect : true,
														queryMode : 'local',
														store : new Ext.data.Store({
																	autoLoad : false,
																	fields : ['matName', 'matCode'],
																	proxy : {
																		type : 'rest',
																		url : 'orderOAMrp/matsCombo/-1'
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
																	combo.store.getProxy().url = 'orderOAMrp/matsCombo' + '/' + value;
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
																	e.getStore().getProxy().url = 'orderOAMrp/matsCombo/-1';
																	e.getStore().load({
																				params : f
																			});
																}
															}
														}
													}, {
														fieldLabel : Oit.msg.pla.orderOA.equipCode,
														name : 'equipCode',
														xtype : 'combobox',
														emptyText : Oit.msg.comboEmptySearchMsg,
														displayField : 'equipName',
														valueField : 'equipCode',
														multiSelect : true,
														queryMode : 'local',
														store : new Ext.data.Store({
																	autoLoad : false,
																	fields : ['equipName', 'equipCode'],
																	proxy : {
																		type : 'rest',
																		url : 'orderOAMrp/equipCombo/-1'
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
																	combo.store.getProxy().url = 'orderOAMrp/equipCombo' + '/' + value;
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
																	e.getStore().getProxy().url = 'orderOAMrp/equipCombo/-1';
																	e.getStore().load({
																				params : f
																			});
																}
															}
														}
													}, {
														fieldLabel : Oit.msg.pla.orderOA.mrp.planDate,
														xtype : 'datefield',
														name : 'planDate',
														format : 'Y-m-d'
													}, {
														fieldLabel : Oit.msg.pla.orderOA.mrp.status,
														xtype : 'checkboxgroup',
														width : 500,
														columns : 5,
														vertical : true,
														items : [{
																	boxLabel : '未审核',
																	name : 'queryStatus',
																	inputValue : 'UNAUDITED',
																	checked : true
																}, {
																	boxLabel : '已审核',
																	name : 'queryStatus',
																	inputValue : 'AUDITED'
																}, {
																	boxLabel : '已取消',
																	name : 'queryStatus',
																	inputValue : 'CANCELED'
																}, {
																	boxLabel : '已下发',
																	name : 'queryStatus',
																	inputValue : 'ISSUED'
																}]
													}]
										}],
								buttons : [{
											itemId : 'search',
											text : Oit.btn.search
										}, {
											itemId : 'reset',
											text : Oit.btn.reset,
											handler : function(e) {
												this.up("form").getForm().reset();
											}
										}, {
											itemId : 'export',
											text : Oit.btn.export
										}]
							}]
				}]
	}]
});
