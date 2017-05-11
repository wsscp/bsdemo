Ext.define("bsmes.view.OrderOAList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.orderOAList',
	store : 'OrderOAStore',
	defaultEditingPlugin : false,
	columnLines : true,
	width : document.body.scrollWidth - 15,
	forceFit : false,
	plugins : [{
				ptype : 'rowexpander',
				rowBodyTpl : ['<div id="{id}">', '</div>']
			}],
	columns : [{
		text : Oit.msg.pla.orderOA.contractNo,
		dataIndex : 'contractNo',
		flex : 2,
		minWidth : 100,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record) {
			var reg = /[a-zA-Z]/g;
			value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length
					- 5) : value.replace(reg, ""))
					+ '[' + record.get('operator') + ']';
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
		text : Oit.msg.pla.orderOA.section,
		dataIndex : 'section',
		flex : 1,
		minWidth : 50,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.wiresStructure,
		dataIndex : 'wiresStructure',
		flex : 1,
		minWidth : 50,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.contractLength,
		dataIndex : 'contractLength',
		flex : 1.2,
		minWidth : 60,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.orderLength,
		dataIndex : 'orderLength',
		flex : 1.2,
		minWidth : 60,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.finishedLength,
		dataIndex : 'finishedLength',
		flex : 1.2,
		minWidth : 60,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.planStartDate,
		dataIndex : 'planStartDate',
		xtype : 'datecolumn',
		format : 'm-d H:i',
		flex : 1.6,
		minWidth : 80,
		menuDisabled : true
	}, {
		text : Oit.msg.pla.orderOA.planFinishDate,
		dataIndex : 'planFinishDate',
		xtype : 'datecolumn',
		format : 'm-d H:i',
		flex : 1.6,
		minWidth : 80,
		menuDisabled : true
	}, {
		text : Oit.msg.pla.orderOA.planWorkHours,
		dataIndex : 'planWorkHours',
		flex : 1.3,
		minWidth : 65,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			if (value && value != 0) {
				var hour = parseInt(value / 60);
				var minute = parseInt(value % 60);
				if (hour > 0) {
					value = hour + '时' + minute + '分';
				} else {
					value = minute + '分';
				}
			}
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.lastOa,
		dataIndex : 'lastOa',
		xtype : 'datecolumn',
		format : 'm-d H:i',
		flex : 1.6,
		minWidth : 80,
		menuDisabled : true
	}, {
		text : Oit.msg.pla.orderOA.oA,
		dataIndex : 'oa',
		xtype : 'datecolumn',
		format : 'm-d H:i',
		flex : 1.6,
		minWidth : 80,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			if (value && value != '') {
				value = Ext.util.Format.date(value, "m-d H:i");
			} else if (record.get('oaDate') && record.get('oaDate') != '') {
				value = Ext.util.Format.date(record.get('oaDate'), "m-d");
			} else {
				value = '';
			}
			metaData.tdAttr = 'data-qtip="' + value + '"';
			return value;
		}
	}, {
		text : Oit.msg.pla.orderOA.status,
		dataIndex : 'statusText',
		flex : 1.2,
		minWidth : 60,
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			metaData.tdAttr = 'data-qtip="' + value + '"';
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
								fieldLabel : Oit.msg.pla.orderOA.planDate,
								xtype : 'datefield',
								name : 'planDate',
								format : 'Y-m-d'
							}, {
								fieldLabel : Oit.msg.pla.orderOA.to,
								xtype : 'datefield',
								name : 'to',
								format : 'Y-m-d'
							}, {
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
												url : 'handSchedule/getSection/-1'
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
												combo.store.getProxy().url = 'handSchedule/getSection' + '/' + value;
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
											e.getStore().getProxy().url = 'handSchedule/getSection/-1';
											e.getStore().load({
														params : f
													});
										}
									}
								}
							}, {
								fieldLabel : Oit.msg.pla.orderOA.orderItemStatus,
								xtype : 'checkboxgroup',
								width : 500,
								columns : 5,
								vertical : true,
								items : [{
											boxLabel : '未开始',
											name : 'orderItemStatus',
											inputValue : 'TO_DO',
											checked : true
										}, {
											boxLabel : '生产中',
											name : 'orderItemStatus',
											inputValue : 'IN_PROGRESS',
											checked : true
										}, {
											boxLabel : '已完成',
											name : 'orderItemStatus',
											inputValue : 'FINISHED'
										}, {
											boxLabel : '已暂停',
											name : 'orderItemStatus',
											inputValue : 'PAUSE'
										}, {
											boxLabel : '已取消',
											name : 'orderItemStatus',
											inputValue : 'CANCELED'
										}]
							}]
				}],
				buttons : [{
							itemId : 'mrpView',
							text : Oit.msg.pla.orderOA.mrp.title
						}, {
							itemId : 'resourceUsePlan',
							text : Oit.msg.pla.orderOA.resourceUsePlan
						}, {
							itemId : 'orderOAResource',
							text : Oit.msg.pla.orderOA.resourceGantt
						}, {
							itemId : 'recalculate',
							text : Oit.msg.pla.orderOA.recalculate
						}, {
							itemId : 'export',
							text : Oit.btn.export
						}, {
							itemId : 'setDelivery',
							text : Oit.msg.pla.customerOrderItem.button.setDelivery
						}, {
							itemId : 'search',
							text : Oit.btn.search
						}, {
							itemId : 'reset',
							text : Oit.btn.reset,
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
					var orderItemId = record.get('id');
					var innerStore = Ext.create('bsmes.store.OrderOASubStore');
					innerStore.getProxy().url = "orderOA/orderProcess/" + orderItemId;
					innerStore.reload();
					var innerGrid = Ext.create('bsmes.view.OrderOASubList', {
								store : innerStore,
								renderTo : orderItemId,
								orderItemId : orderItemId
							});
					innerGrid.getEl().swallowEvent(['mousedown', 'mouseup', 'click', 'contextmenu', 'mouseover',
							'mouseout', 'dblclick', 'mousemove']);
					innerGrid.on('toOpenSetWindow', me.doOpenSetWindow, me);
				});

		me.view.on('collapsebody', function(rowNode, record, expandRow, eOpts) {
					var parent = document.getElementById(record.get('id'));
					var child = parent.firstChild;
					while (child) {
						child.parentNode.removeChild(child);
						child = child.nextSibling;
					}
				});
	},
	doOpenSetWindow : function(data, grid) {
		var optionalEquipCode = data.get('optionalEquipCode');
		var optionalEquipCodeArray = null;
		if (optionalEquipCode) {
			optionalEquipCodeArray = optionalEquipCode.split(',');
		}
		Ext.Ajax.request({
					url : 'orderOA/findIdleEquip/' + data.get("processId"),
					method : 'GET',
					success : function(response) {
						var equipList = Ext.decode(response.responseText);
						var checkboxItems = new Array();
						Ext.each(equipList, function(equip, i) {
									var checked = false;
									if (optionalEquipCodeArray) {
										Ext.each(optionalEquipCodeArray, function(code, i) {
													if (code == equip.equipCode) {
														checked = true;
													}
												});
									}
									checkboxItems.push({
												boxLabel : equip.equipCode,
												name : 'equipCode',
												inputValue : equip.equipCode,
												checked : checked
											});
								});
						if (checkboxItems.length == 0) {
							Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.orderOA.msg.noIdleEquipMsg);
							return;
						}
						var window = Ext.create('bsmes.view.ProcessIdleEquipWindow', {
									checkboxItem : checkboxItems,
									orderItemId : data.get('orderItemId'),
									processId : data.get('processId'),
									parentGrid : grid

								});
						window.show();
					}
				});

	}
});
