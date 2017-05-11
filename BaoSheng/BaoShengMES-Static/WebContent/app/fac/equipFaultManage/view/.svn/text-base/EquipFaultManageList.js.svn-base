Ext.define("bsmes.view.EquipFaultManageList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.equipFaultManageList',
	store : 'EquipFaultManageStore',
	defaultEditingPlugin : false,
	invalidateScrollerOnRefresh : false, // extjs4 grid store.load 时不改变滚动条位置
	columnLines : true,
	viewConfig : {
		stripeRows : false,
		getRowClass : function(record, rowIndex, rowParams, store) {
			var className = '';
			if (record.get('eventStatus') == 'RESPONDED') {
				className += 'x-grid-record-red ';
			} else if (record.get('eventStatus') == 'INCOMPLETED') {
				className += 'x-grid-record-gree ';
			} else if (record.get('eventStatus') == 'COMPLETED') {
				className += 'x-grid-record-blue ';
			} else {
				className += 'x-grid-record-black ';
			}
			if (rowIndex % 2 != 0) { // 添加默认间隔样式
				className += 'x-grid-row-alt ';
			}
			return className;
		}
	},
	columns : [{
				text : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				width : 120,
				dataIndex : 'eventStatus',
				renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
					if (value == 'PENDING' || value == 'COMPLETED') {
						return '<button class = "gridButton" style="margin : 0 5 0 48" onClick = "openCheckWin(\''
								+ record.get('id') + '\')">查看</button>';
					} else {
						return '<button class = "gridButton" style="margin : 0 5 0 0;" onClick = "openRegisterWin(\''
								+ record.get('id')
								+ '\')">登记</button><button class = "gridButton" style="margin : 0 5 0 0;" onClick = "openCheckWin(\''
								+ record.get('id') + '\')">查看</button>';
					}
				}
			}, {
				text : Oit.msg.fac.eventInformation.eventTitle,
				flex : 2,
				minWidth : 100,
				dataIndex : 'eventTitle'
			}, {
				text : Oit.msg.fac.eventInformation.eventContent,
				flex : 4.5,
				minWidth : 225,
				dataIndex : 'eventContent'
			}, {
				text : Oit.msg.fac.eventInformation.userName,
				flex : 2,
				minWidth : 100,
				dataIndex : 'userName',
				renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
					if (record.get('maintainPeople') == 'dg') {
						value = value + '&nbsp;<span style ="font-weight : bold;">(需电工)</span>';
					} else if (record.get('maintainPeople') == 'qg') {
						value = value + '&nbsp;<span style ="font-weight : bold;">(需钳工)</span>';
					} else {
						value = value + '&nbsp;(未确认)';
					}
					return value;
				}
			}, {
				text : Oit.msg.fac.eventInformation.createTime,
				dataIndex : 'createTime',
				flex : 1.5,
				minWidth : 75,
				xtype : 'datecolumn',
				format : 'm-d H:i'
			}, {
				text : Oit.msg.fac.eventInformation.eventStatus,
				flex : 1.2,
				minWidth : 60,
				dataIndex : 'eventStatus',
				renderer : function(value) {
					if (value == 'UNCOMPLETED') {
						return Oit.msg.fac.eventInformation.uncompleted;
					} else if (value == 'RESPONDED') {
						return Oit.msg.fac.eventInformation.responded;
					} else if (value == 'PENDING') {
						return Oit.msg.fac.eventInformation.pending;
					} else if (value == 'INCOMPLETED') {
						return Oit.msg.fac.eventInformation.incompleted;
					} else {
						return Oit.msg.fac.eventInformation.completed;
					}
				}
			}, /*
				 * { text : Oit.msg.fac.eventInformation.equipCode, width : 150, hidden : true, dataIndex : 'equipCode' }, {
				 * text : Oit.msg.fac.eventInformation.productCode, width : 150, hidden : true, dataIndex :
				 * 'productCode' }, { text : Oit.msg.fac.eventInformation.pendingProcessing, width : 150, hidden : true,
				 * dataIndex : 'pendingProcessing' },
				 */{
				text : Oit.msg.fac.eventInformation.responsible,
				flex : 1,
				minWidth : 100,
				hidden : true,
				dataIndex : 'responsible'
			}, {
				text : Oit.msg.fac.eventInformation.processSeq,
				flex : 1.2,
				minWidth : 60,
				dataIndex : 'processSeq',
				renderer : function(value) {
					if (value == '0') {
						return Oit.msg.fac.eventInformation.zero;
					} else if (value == '1') {
						return Oit.msg.fac.eventInformation.one;
					} else if (value == '2') {
						return Oit.msg.fac.eventInformation.two;
					} else if (value == '3') {
						return Oit.msg.fac.eventInformation.three;
					}
					return '';
				}
			}, {
				text : Oit.msg.fac.eventInformation.completeTime,
				dataIndex : 'completeTime',
				flex : 1.5,
				minWidth : 125,
				xtype : 'datecolumn',
				format : 'm-d H:i:s'
			}/*
				 * , { text : Oit.msg.fac.eventInformation.name, width : 150, hidden : true, dataIndex : 'name' },
				 *//*
				 * { text : Oit.msg.fac.eventInformation.processType, flex : 1.5, minWidth : 75, dataIndex :
				 * 'processType' }
				 *//*
				 * , { text : Oit.msg.fac.eventInformation.eventReason, width : 250, hidden : true, dataIndex :
				 * 'eventReason' }, { text : Oit.msg.fac.eventInformation.eventResult, width : 200, hidden : true,
				 * dataIndex : 'eventResult' }, { text : Oit.msg.fac.eventInformation.batchNo, width : 150, hidden :
				 * true, dataIndex : 'batchNo' }
				 */],
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
							fieldLabel : Oit.msg.fac.eventInformation.equipCode,
							xtype : 'combobox',
							name : 'equipCode',
							mode : 'remote',
							displayField : 'name',
							valueField : 'code',
							store : new Ext.data.Store({
										fields : ['code', 'name'],
										autoLoad : true,
										proxy : {
											type : 'rest',
											url : 'equipFaultManage/equip'
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
						}, {
							fieldLabel : Oit.msg.fac.eventInformation.eventStatus,
							xtype : 'combobox',
							name : 'eventStatus',
							editable : false,
							mode : 'remote',
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
											url : 'equipFaultManage/eventStatus'
										}
									})
						}],
				buttons : [{
							itemId : 'search',
							text : Oit.btn.search,
							iconCls : 'icon_search'
						}, {
							itemId : 'reset',
							text : '重置',
							handler : function(e) {
								this.up("form").getForm().reset();
							}
						}, /*
							 * { itemId : 'check', text : '查看' }, { itemId : 'register', text : '登记' },
							 */'->', {
							xtype : 'panel',
							html : '<div style="background-color:#DFEAF2;width:256px;height:19px;">'
									+ '<span class="x-grid-record-blue" style="display:block;float:right;">'
									+ Oit.msg.fac.eventInformation.completed + '</span>'
									+ '<span class="x-grid-record-black" style="display:block;float:right;">'
									+ Oit.msg.fac.eventInformation.pending + ',</span>'
									+ '<span class="x-grid-record-gree" style="display:block;float:right;">'
									+ Oit.msg.fac.eventInformation.incompleted + ',</span>'
									+ '<span class="x-grid-record-red" style="display:block;float:right;">'
									+ Oit.msg.fac.eventInformation.responded + ',</span></div>'
						}]
			}]
		}]
	}]
});
function openRegisterWin(id) {
	Ext.Ajax.request({
				url : 'equipFaultManage/getRegisterData',
				params : {
					id : id
				},
				success : function(response) {
					var json = Ext.decode(response.responseText).eve;
					var uuid = new Ext.data.UuidGenerator();
					var recordId = uuid.generate();
					var store = Ext.create('bsmes.store.SparePartStore');
					var win = Ext.create('bsmes.view.EquipEventFlowEdit', {
								store : store
							});
					var form = win.down('form').getForm();
					var equipName;
					if (json.equipAlias == '' || json.equipAlias == null) {
						equipName = json.name + '[' + json.equipCode + ']'
					} else {
						equipName = json.equipAlias + '-' + json.name + '[' + json.equipCode + ']';
					}
					form.setValues({
								id : recordId,
								eventInfoId : id,
								equipName : equipName,
								createTime : new Date(json.createTime),
								equipModelStandard : json.name,
								protectMan : json.userName
							});
					win.show();
				}
			});
};

function openCheckWin(id) {
	Ext.Ajax.request({
				url : 'equipFaultManage/getCheckData',
				params : {
					id : id
				},
				success : function(response) {
					var result = Ext.decode(response.responseText).eve;
					if (result.length == 0) {
						Ext.Msg.alert(Oit.msg.WARN, '事件未处理或未登记。。');
						return;
					}
					var win = Ext.create('Ext.window.Window', {
								title : '设备维修记录',
								width : 650,
								maxHeight : 850,
								layout : 'fit',
								height : document.body.scrollHeight - 100,
								items : {
									xtype : 'tabpanel',
									enableTabScroll : true,
									defaults : {
										autoScroll : true
									},
									animScroll : true, // 使用动画滚动效果
									items : []
								}
							})
					Ext.Array.each(result, function(json, index, countriesItSelf) {
								var tab = win.down('tabpanel');
								var store = Ext.create('bsmes.store.SparePartStore');
								var panel = openRepairedRecord(json, store);
								tab.add(panel);
								if (index == 0) {
									tab.setActiveTab(panel);
								}
							});
					win.doLayout();
					win.show();
				}
			});
};

function openRepairedRecord(json, store, index) {
	var win = Ext.create("bsmes.view.CheckEventFlowPanel", {
				store : store
			});
	var seq = Number(json.seq) + 1;
	win.title = '第' + seq + '次维修记录';
	var form = win.down('form').getForm();
	var equipName;
	if (json.equipAlias != '') {
		equipName = json.equipAlias + '-' + json.equipName + '[' + json.equipCode + ']';
	} else {
		equipName = json.equipName + '[' + json.equipCode + ']';
	}
	var evaluate = json.evaluate;
	if (!evaluate) {
		win.query('radiogroup')[1].setVisible(false);
	}
	form.setValues({
				equipName : equipName,
				createTime : new Date(json.createTime),
				equipModelStandard : json.equipName,
				protectMan : json.userName,
				startRepairTime : new Date(json.startRepairTime),
				finishRepairTime : new Date(json.finishRepairTime),
				repairMan : json.repairMan,
				failureModel : json.failureModel,
				equipTroubleDescribetion : json.equipTroubleDescribetion,
				equipTroubleAnalyze : json.equipTroubleAnalyze,
				repairMeasures : json.repairMeasures,
				evaluate : evaluate
			});
	var grid = win.down('grid');
	grid.getStore().load({
				params : {
					recordId : json.id
				}
			});
	return win;
};
