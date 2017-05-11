Ext.define('bsmes.view.ReportHTJYGrid', {
	extend : 'Ext.grid.Panel',
	store : 'ReportStore',
	alias : 'widget.reportHTJYGrid',
	columnLines : true,
	record : null, // 报工信息
	reprotUser : {}, // 报工人员信息
	initComponent : function() {
		var me = this;

		me.columns = [{
					dataIndex : 'serialNum',
					flex : 1,
					text : '条码'
				}, {
					text : Oit.msg.wip.terminal.discNo,
					flex : 1,
					dataIndex : 'coilNum'
				}, {
					dataIndex : 'reportLength',
					flex : 1,
					text : '报工长度'
				}, {
					dataIndex : 'goodLength',
					flex : 1,
					text : '合格长度'
				}];

		me.dockedItems = [{
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
							itemId : 'reportForm',
							id : 'reportInfoForm',
							bodyPadding : 5,
							layout : 'column',
							items : [{
										items : [{
													fieldLabel : '挡班',
													xtype : 'checkboxgroup',
													labelWidth : 130,
													hidden : (me.reprotUser ? me.reprotUser.dbItems.length == 0 : true),
													items : (me.reprotUser ? me.reprotUser.dbItems : [])
												}/*, {
													fieldLabel : '副挡班',
													xtype : 'checkboxgroup',
													labelWidth : 130,
													hidden : (me.reprotUser ? me.reprotUser.fdbItems.length == 0 : true),
													items : (me.reprotUser ? me.reprotUser.fdbItems : [])
												}, {
													fieldLabel : '辅助工',
													xtype : 'checkboxgroup',
													labelWidth : 130,
													hidden : (me.reprotUser ? me.reprotUser.fzgItems.length == 0 : true),
													items : (me.reprotUser ? me.reprotUser.fzgItems : [])
												}*/, {
													xtype : 'panel',
													width : '100%',
													layout : 'column',
													defaults : {
														width : 300,
														margin : '0 30 0 0',
														labelWidth : 130
													},
													defaultType : 'displayfield',
													items : [{
																name : 'productColor',
																fieldLabel : Oit.msg.wip.terminal.productColor
															}, {
																fieldLabel : Oit.msg.wip.terminal.workOrderNo,
																xtype : 'textfield',
																readOnly : true,
																fieldStyle : 'border-width : 0px;',
																name : 'workOrderNo'
															}, {
																fieldLabel : Oit.msg.wip.terminal.processCode,
																name : 'currentProcess'
															}, {
																fieldLabel : Oit.msg.wip.terminal.planLength,
																id : 'reportPlanLength',
																name : 'planLength'
															}, {
																fieldLabel : '剩余长度',
																id : 'reportRemainQLength',
																name : 'remainQLength'
															}, {
																fieldLabel : '总计合格长度',
																id : 'qualifiedLength',
																name : 'qualifiedLength'
															}, {
																fieldLabel : '可报工长度',
																name : 'currentLength',
																xtype : 'textfield',
																itemId : 'reportCurrentLength',
																plugins : {
																	ptype : 'virtualKeyBoard'
																}
															}, {
																fieldLabel : '报工长度',
																name : 'reportLength',
																id : 'reportLength',
																xtype : 'textfield',
																emptyText : 0,
																plugins : {
																	ptype : 'virtualKeyBoard'
																}
															}, {
																xtype : 'hiddenfield',
																name : 'locationName'
															}, {
																xtype : 'hiddenfield',
																name : 'needFirstCheck'
															}, {
																xtype : 'hiddenfield',
																name : 'needOutCheck'
															}, {
																xtype : 'hiddenfield',
																name : 'equipCode'
															}, {
																xtype : 'hiddenfield',
																name : 'operator'
															}]
												}]
									}],
							buttons : [{
										xtype : 'button',
										itemId : 'ok',
										id : 'reportGridOkBtn',
										text : Oit.btn.ok
									}, {
										xtype : 'button',
										itemId : 'QADataEntry',
										text : Oit.msg.wip.terminal.btn.QADataEntry
									}, {
										xtype : 'button',
										itemId : 'finishWorkOrder',
										text : Oit.msg.wip.terminal.btn.finishWorkOrder
									}, '->', {
										xtype : 'button',
										text : Oit.btn.close,
										handler : function() {
											this.up('window').close();
										}
									}]
						}]
			}]
		}];

		this.callParent(arguments);
		Ext.ComponentQuery.query('#reportForm')[0].loadRecord(me.record);
	}
});