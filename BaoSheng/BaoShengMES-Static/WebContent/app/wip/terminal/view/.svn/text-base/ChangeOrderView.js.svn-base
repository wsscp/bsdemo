/**
 * 查看生产单
 */
Ext.define("bsmes.view.ChangeOrderView", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.changeOrderView',
			store : 'WorkOrderStore',
			id : 'recentOrderGrid',
			columnLines : true,
			defaultEditingPlugin : false,
			listeners : {
				'afterrender' : function(grid,eOpts){
					grid.columns[5].hide();
					grid.columns[8].show();
				}
			},
			initComponent : function() {
				var me = this;
				var section = Ext.fly('processInfo').getAttribute('section');
				var jySection = (section == '绝缘');
				var dockedItems = [{
							xtype : 'toolbar',
							dock : 'top',
							items : [{
										xtype : 'hform',
										items : [{
													xtype : 'hiddenfield',
													name : 'equipCode',
													value : Ext.fly('equipInfo').getAttribute('code')
												}, {
													xtype : 'hiddenfield',
													name : 'section',
													value : Ext.fly('processInfo').getAttribute('section')
												}, {
													xtype : 'radiogroup',
													labelAlign : 'left',
													columns : 2,
													width : 320,
													defaults : { // 设置样式
														margin : '5 20 0 0'
													},
													vertical : true,
													items : [{
																boxLabel : '未完成生产单',
																name : 'type',
																inputValue : 'TO_DO',
																checked : true
															}, {
																boxLabel : '已完成生产单',
																name : 'type',
																inputValue : 'FINISHED'
															}],
													listeners : {
														'change' : function(me, newValue, oldValue, eOpts) {
															var grid = this.up('grid');
															// 已完成的生产单只能看不能操作
															if (newValue.type == 'FINISHED') {
																var buttons = this.up('hform').items.items;
																Ext.each(buttons, function(btn) {
																			if (btn.xtype == 'button' && typeof(btn.itemId) != 'undefined') {
																				btn.hide();
																			}
																		});
																grid.columns[3].hide();
																grid.columns[4].hide();
																grid.columns[5].show();
																grid.columns[8].hide();
															} else {
																var buttons = this.up('hform').items.items;
																Ext.each(buttons, function(btn) {
																			if (btn.xtype == 'button') {
																				btn.show();
																			}
																		});
																grid.columns[3].show();
																grid.columns[4].show();
																grid.columns[5].hide();
																grid.columns[8].show();
															}
															var store = grid.getStore();
															store.load();
														}
													}
												}, {
													xtype : 'button',
													text : '切换生产单',
													itemId : 'pause',
													margin : '0 10 0 30'
												}, {
													xtype : 'button',
													itemId : 'printMatList',
													text : '物料清单',
													margin : '0 10 0 10'
												}, {
													xtype : 'button',
													itemId : 'getMaterial',
													text : '要料',
													margin : '0 10 0 10'
												}, {
													xtype : 'button',
													itemId : 'supplementMaterial',
													text : '补料',
													hidden : true,
													margin : '0 10 0 10'
												}, {
													xtype : 'button',
													itemId : 'changeEquip',
													text : '调整机台',
													margin : '0 10 0 10'
												}, {
													xtype : 'button',
													text : Oit.btn.close,
													handler : function() {
														this.up('window').close();
													},
													margin : '0 10 0 10'
												}]
									}]
						}];

				var columns = [{
							text : "生产单号",
							dataIndex : 'workOrderNo',
							flex : 1.4,
							minWidth : 70,
							menuDisabled : true,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value.substring(value.length - 6);
							}
						}, {
							text : '合同号[经办人] 客户型号规格 (颜色,线芯结构)-合同长度',
							dataIndex : 'contractNo',
							flex : 7.5,
							minWidth : 350,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value.replace(/;/g, '<br/>');
							}
						}, {
							text : "工序名称",
							dataIndex : 'processName',
							sortable : false,
							hidden : true,
							minWidth : 70,
							flex : 1.4,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value;
							}
						}, {
							text : "下发日期",
							dataIndex : 'releaseDate',
							sortable : false,
							minWidth : 70,
							flex : 1.4,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value.substr(5);
							}
						}, {
							text : "要求完成日期",
							dataIndex : 'requireFinishDate',
							sortable : false,
							minWidth : 70,
							flex : 1.4,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value.substr(5);
							}
						}, {//5
							text : "实际完成日期",
							dataIndex : 'realEndTime',
							sortable : false,
							minWidth : 70,
							flex : 1.4,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value.substr(5);
							}
						} ,{
							text : '投产长度',
							dataIndex : 'orderLength',
							sortable : false,
							minWidth : 100,
							flex : 2,
							hidden : jySection,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value;
							}
						}, {
							text : '颜色-投产长度',
							dataIndex : 'colorProductLength',
							sortable : false,
							minWidth : 200,
							flex : 4,
							hidden : !jySection,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value.replace(/,/g, '<br/>');
							}
						}, {
							text : Oit.msg.wip.terminal.status,
							dataIndex : 'status',
							sortable : false,
							minWidth : 75,
							flex : 1.5,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value;
							}
						}, {
							text : '物料状态',
							dataIndex : 'matStatusText',
							sortable : false,
							minWidth : 75,
							flex : 1.5,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value;
							}
						}, {
							text : '切换原因',
							dataIndex : 'operateReason',
							sortable : false,
							minWidth : 100,
							flex : 2,
							renderer : function(value, metaData, record, row, column) {
								metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
								return value;
							}
						}];

				me.dockedItems = dockedItems;
				me.columns = columns;
				me.callParent(arguments);
			}
		});