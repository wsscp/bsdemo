Ext.define("bsmes.view.QualityTraceList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.qualityTraceList',
	store : 'QualityTraceStore',
	defaultEditingPlugin : false,
	forceFit : false,
	columns : [{
		text : Oit.msg.wip.qualityTrace.contractNo,
		dataIndex : 'contractNo',
		flex : 2.12,
		minWidth : 106,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			var reg = /[a-zA-Z]/g;
			value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length
					- 5) : value.replace(reg, ""))
					+ '[' + record.get('operator') + ']';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.workOrderNo,
		dataIndex : 'workOrderNo',
		flex : 1.4,
		minWidth : 70,
		hidden : true,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			value = value.substring(value.length - 6);
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.custProductTypeSpec,
		dataIndex : 'custProductType',
		flex : 3.6,
		minWidth : 180,
		renderer : function(value, metaData, record) {
			value = value + ' ' + record.get('custProductSpec');
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.checkItemCode,
		dataIndex : 'checkItemName',
		flex : 3.3,
		minWidth : 165,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.eqipCode,
		dataIndex : 'eqipCode',
		flex : 4.5,
		minWidth : 225,
		renderer : function(value, metaData, record) {
			if (record.get('equipAlias') != '') {
				value = record.get('equipAlias') + '[' + value + ']-' + record.get('equipName');
			} else {
				value = record.get('equipName') + '[' + value + ']';
			}
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.processName,
		dataIndex : 'processName',
		flex : 2,
		minWidth : 100,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.type,
		dataIndex : 'type',
		flex : 1.3,
		minWidth : 65
	}, {
		text : Oit.msg.wip.qualityTrace.qcValue,
		dataIndex : 'qcValue',
		flex : 1.3,
		minWidth : 65,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.qcResult,
		dataIndex : 'qcResult',
		flex : 1.3,
		minWidth : 65,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.checkEqipCode,
		dataIndex : 'checkEqipCode',
		flex : 1.3,
		minWidth : 65,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.userName,
		dataIndex : 'userName',
		flex : 1.7,
		minWidth : 85,
		renderer : function(value, metaData, record) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.wip.qualityTrace.createTime,
		dataIndex : 'createTime',
		xtype : 'datecolumn',
		format : 'm-d H:i',
		flex : 1.7,
		minWidth : 85
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
						xtype : 'textfield',
						labelAlign : 'right'
					}
				},
				items : [{
					items : [{
								fieldLabel : Oit.msg.wip.qualityTrace.contractNo,
								name : 'contractNo'
							}, {
								fieldLabel : Oit.msg.wip.qualityTrace.custProductType,
								name : 'custProductType'
							}, {
								fieldLabel : Oit.msg.wip.qualityTrace.custProductSpec,
								name : 'custProductSpec'
							}, {
								fieldLabel : Oit.msg.wip.qualityTrace.processName,
								xtype : 'combobox',
								name : 'processCode',
								editable : false,
								displayField : 'name',
								valueField : 'code',
								mode : 'remote',
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
											var combo = this.up("form").getForm().findField('eqipCode');
											combo.store.getProxy().url = '../fac/equipInfo/getEquipLine?processName='
													+ newValue;
											combo.store.load();
											combo.expand();
										}
									}
								}
							}, {
								fieldLabel : Oit.msg.wip.qualityTrace.eqipCode,
								name : 'eqipCode',
								xtype : 'combobox',
								displayField : 'name',
								valueField : 'code',
								queryMode : 'local',
								store : new Ext.data.Store({
											fields : ['code', {
												name : 'name',
												type : 'string',
												convert : function(value, record) {
													if (record.get('equipAlias') == '') {
														return value + '[' + record.get('code') + ']';
													} else {
														return record.get('equipAlias') + '-' + value + '['
																+ record.get('code') + ']';
													}
												}
											}, 'equipAlias'],
											proxy : {
												type : 'rest'
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
								fieldLabel : Oit.msg.wip.qualityTrace.type,
								name : 'type',
								xtype : 'combobox',
								displayField : 'name',
								labelAlign : 'right',
								valueField : 'code',
								value : '',
								editable : false,
								store : new Ext.data.Store({
											fields : ['code', 'name'],
											data : [{
														"name" : Oit.msg.wip.qualityTrace.all,
														"code" : ""
													}, {
														"name" : Oit.msg.wip.qualityTrace.inCheck,
														"code" : "IN_CHECK"
													}, {
														"name" : Oit.msg.wip.qualityTrace.middleCheck,
														"code" : "MIDDLE_CHECK"
													}, {
														"name" : Oit.msg.wip.qualityTrace.outCheck,
														"code" : "OUT_CHECK"
													}]
										})
							}, {
								fieldLabel : Oit.msg.wip.qualityTrace.startTime,
								xtype : 'datefield',
								name : 'startTime',
								format : 'Y-m-d',
								value : Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.DAY, -7), "Y-m-d")
							}, {
								fieldLabel : Oit.msg.wip.qualityTrace.endTime,
								xtype : 'datefield',
								name : 'endTime',
								format : 'Y-m-d',
								value : Ext.util.Format.date(new Date(), "Y-m-d")
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
						}]
			}]
		}]
	}]
});