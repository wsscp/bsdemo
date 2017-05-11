Ext.define('bsmes.view.ReportGrid', {
			extend : 'Ext.grid.Panel',
			store : 'ReportStore',
			alias : 'widget.reportGrid',
			columnLines : true,
			record : null, // 报工信息
			reprotUser : {}, // 报工人员信息
			processCode : null,
			initComponent : function() {
				var me = this;
				var isWireCoreShow = true;
				var buttonHidden = false;
				if(me.processCode == 'Braiding' || me.processCode == 'wrapping' || me.processCode == 'Cabling' || me.processCode == 'Twisting'
					|| me.processCode == 'shield' || me.processCode == 'wrapping_ht' || processCode == 'Respool'){
					isWireCoreShow = false;
				}
				if(me.processCode == 'Steam-Line') {
					buttonHidden = true
				}
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
											//width修改，使得panel在初始界面可以全部显示出来
											width : document.body.scrollWidth - 150,
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
														fieldLabel : '定制区',
														name : 'kuaQu',
														id : 'kuaQu',
														xtype : 'combobox',
														editable : false,
														displayField : 'kuaQu',
														valueField : 'kuaQu',
														emptyText : '跨区',
														width : 200,
														height : 20,
														store : new Ext.data.Store({
																	proxy : {
																		type : 'rest',
																		url : 'getLocationName?processCode='
																				+ Ext.fly('processInfo')
																						.getAttribute('code'),
																		reader : {
																			type : 'json'
																		}
																	},
																	fields : ['kuaQu'],
																	listeners : {
																		load : function() {
																			var k, repeat = [], state = {};
																			this.each(function(r) {
																						k = r.get('kuaQu');
																						if (state[k])
																							repeat.push(r);
																						else
																							state[k] = true;
																					});
																			this.remove(repeat);
																		}
																	}
																})
													}, {
														name : 'locationName',
														id : 'locationName',
														xtype : 'combobox',
														editable : false,
														displayField : 'locationName',
														valueField : 'locationName',
														queryMode : 'local',
														width : 150,
														height : 20,
														emptyText : '存放位置',
														store : new Ext.data.Store({
																	autoLoad : false,
																	proxy : {
																		type : 'rest',
																		url : 'getLocationName?processCode='
																				+ Ext.fly('processInfo')
																						.getAttribute('code'),
																		reader : {
																			type : 'json'
																		}
																	},
																	fields : ['locationName', 'locationName']
																}),
														listeners : {
															beforequery : function(e) {
																var combo = e.combo;
																var comb = Ext.ComponentQuery.query('#kuaQu')[0];
																var kuaQu = comb.getRawValue();
																combo.store.load({
																			params : {
																				'kuaQu' : kuaQu
																			}
																		});
															}
														}
													},{
														fieldLabel : '盘具',
														name : 'disk',
														id : 'disk',
														xtype : 'combobox',
														hidden : isWireCoreShow,
														editable : false,
														displayField : 'disk',
														valueField : 'disk',
														emptyText : '盘具',
														width : 300,
														labelWidth : 130,
														height : 20,
														margin : '2 30 0 0',
														store : new Ext.data.Store({
																	fields : ['disk'],
																	data : [{'disk':'φ400型'},
																	        {'disk':'φ500型'},
																	        {'disk':'φ630型'},
																	        {'disk':'φ800型'},
																	        {'disk':'φ1000型'},
																	        {'disk':'φ1200型'},
																	        {'disk':'φ1250型'},
																	        {'disk':'φ1400型'},
																	        {'disk':'φ1600型'},
																	        {'disk':'φ1800型'},
																	        {'disk':'φ2000型'}]
																})
													},{
														fieldLabel : '盘号',
														name : 'diskNumber',
														id : 'diskNumber',
														hidden : isWireCoreShow,
														xtype : 'textfield',
														emptyText : 0,
														margin : '2 30 0 0',
														plugins : {
															ptype : 'virtualKeyBoard'
														}
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
										itemId : 'QADataEntry',
										text : Oit.msg.wip.terminal.btn.QADataEntry,
										hidden : buttonHidden
									}, {
										xtype : 'button',
										itemId : 'ok',
										id : 'reportGridOkBtn',
										text : Oit.btn.ok
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