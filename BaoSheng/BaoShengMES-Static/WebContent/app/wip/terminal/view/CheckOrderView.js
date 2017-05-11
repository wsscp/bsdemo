/**
 * 查看生产单
 */
Ext.define("bsmes.view.CheckOrderView", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.checkOrderView',
			id : 'checkOrderGrid',
			store : 'CheckWorkOrderStore',
			overflowY : 'auto',
			overflowX : 'auto',
			autoScroll : true,
			rowLines : true,
			columnLines : true,
			height : document.body.scrollHeight - 187,
			forceFit : true,
			defaultEditingPlugin : false,
			columns : [{
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
					}, {
						text : "实际完成日期",
						dataIndex : 'realEndTime',
						sortable : false,
						hidden : true,
						minWidth : 70,
						flex : 1.4,
						renderer : function(value, metaData, record, row, column) {
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							return value.substr(5);
						}
					}, {
						text : '投产长度',
						dataIndex : 'colorProductLength',
						sortable : false,
						minWidth : 200,
						flex : 4,
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
					}],
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						items : [{
									xtype : 'hform',
									items : [{
												xtype : 'radiogroup',
												labelAlign : 'left',
												columns : 2,
												width : 320,
												defaults : { // 设置样式
													margin : '5 20 0 0'
													//boxLabelCls: 80  // 设置label的宽度
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
														if (newValue.type == 'FINISHED') {
															grid.columns[2].hide();
															grid.columns[3].hide();
															grid.columns[4].show();
														} else {
															grid.columns[2].show();
															grid.columns[3].show();
															grid.columns[4].hide();
														}
														var form = this.up('hform').getForm();
														var equipCode = form.findField('equipCode').getValue();
														var section = form.findField('section').getValue();
														console.log(equipCode);
														var store = grid.getStore();

														store.getProxy().url = '/bsmes/wip/terminal/recentOrders';
														store.load({
																	params : {
																		equipCode : equipCode,
																		type : newValue.type,
																		section : section
																	}
																});
													}
												}
											}, {
												xtype : 'button',
												text : Oit.btn.close,
												handler : function() {
													this.up('window').close();
												}
											}, {
												xtype : 'hiddenfield',
												name : 'equipCode'
											}, {
												xtype : 'hiddenfield',
												name : 'section'
											}]
								}]
					}]
		});