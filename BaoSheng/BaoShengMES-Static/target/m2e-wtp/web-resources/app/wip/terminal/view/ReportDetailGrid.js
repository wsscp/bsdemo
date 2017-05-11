/**
 * Created by Administrator on 2014/6/17.
 */
Ext.define("bsmes.view.ReportDetailGrid", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.reportDetailGrid',
			id : 'reportDetailGrid',
			store : 'EquipReportStore',
			overflowY : 'auto',
			overflowX : 'auto',
			height : document.body.scrollHeight - 140,
			forceFit : true,
			defaultEditingPlugin : false,
			columns : [{
						text : '合同号',
						dataIndex : 'contractNo',
						sortable : false,
						flex : 1.5,
						minWidth : 75,
						renderer : function(v) {
							v = v.replace(/[a-zA-Z]/g, "");
							if (v && v.length > 5) {
								return v.substring(v.length - 5, v.length);
							} else {
								return v;
							}
						}
					}, {
						text : Oit.msg.wip.terminal.productType,
						dataIndex : 'custProductType',
						sortable : false,
						flex : 3,
						renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
							metaData.style = "white-space:normal";
							return value.replace(/,/g, '<br/>');
						}
					}, {
						text : Oit.msg.wip.terminal.productSpec,
						dataIndex : 'productSpec',
						sortable : false,
						flex : 1
					}, {
						dataIndex : 'productColor',
						text : Oit.msg.wip.terminal.productColor,
						sortable : false,
						flex : 4,
						minWidth : 200,
						renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
							metaData.style = "white-space:normal";
							return value;
						}
					}, {
						dataIndex : 'coilNum',
						text : '线盘号',
						sortable : false,
						flex : 1.5,
						minWidth : 75
					}, {
						dataIndex : 'reportLength',
						text : '总长度',
						sortable : false,
						flex : 2,
						minWidth : 100
					}, {
						dataIndex : 'goodLength',
						text : '合格长度',
						sortable : false,
						flex : 2,
						minWidth : 100
					}, {
						dataIndex : 'reportUserName',
						text : '报工人',
						sortable : false,
						flex : 1.5,
						minWidth : 75
					}, {
						dataIndex : 'reportTime',
						text : '报工时间',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s',
						sortable : false,
						flex : 4,
						minWidth : 200

					}],

			initComponent : function() {
				var me = this;
				me.dockedItems = [{
							xtype : 'toolbar',
							dock : 'top',
							layout : 'vbox',
							items : [{
										title : '查询条件',
										xtype : 'fieldset',
										collapsible : true,
										width : '99%',
										items : {
											xtype : 'form',
											width : '100%',
											layout : 'vbox',
											buttonAlign : 'left',
											labelAlign : 'right',
											bodyPadding : 5,
											items : [{
														xtype : 'panel',
														width : '100%',
														layout : 'column',
														defaults : {
															labelAlign : 'right',
															labelWidth : 100
														},
														items : [{
																	fieldLabel : "报工时间",
																	xtype : 'radiogroup',
																	id : 'reportTimeRound',
																	width : document.body.scrollWidth - 200,
																	columns : 5,
																	items : [{
																				boxLabel : '当天',
																				name : 'reportDate',
																				inputValue : '0',
																				checked : true
																			}, {
																				boxLabel : '近三天',
																				name : 'reportDate',
																				inputValue : '-2'
																			}, {
																				boxLabel : '近七天',
																				name : 'reportDate',
																				inputValue : '-6'
																			}],
																	listeners : {
																		change : function(me, newValue, oldValue, opts) {
																			var me = this;
																			var grid = me.up('grid');
																			grid.getStore().loadPage(1, {
																						params : {
																							reportDate : newValue
																						}
																					});
																		}
																	}
																}]
													}],
											buttons : [{
														text : Oit.btn.reset,
														handler : function(e) {
															this.up("form").getForm().reset();
														}
													}, {
														itemId : 'print',
														text : Oit.btn.print,
														xtype : 'button'
													}, '->', {
														xtype : 'button',
														text : '关闭',
														handler : function() {
															this.up('window').close();
														}
													}]
										}
									}]
						}];
				me.callParent(arguments);
			}
		});