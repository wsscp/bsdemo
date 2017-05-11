// 查看生产单->调整生产单加工顺序
Ext.define('bsmes.view.ChangeWorkOrderSeqWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.changeWorkOrderSeqWindow',
			title : '调整生产单加工顺序',
			width : document.body.scrollWidth,
			height : document.body.scrollHeight,
			modal : true,
			plain : true,
			layout : 'fit',
			processSection : [], // 工段组
			initComponent : function() {
				var me = this;

				var processSection = [];
				Ext.each(me.processSection, function(process, i) {
							processSection.push({
										boxLabel : process.section,
										name : 'processCode',
										inputValue : process.sectionSeq
									});
						});
				me.items = [{
					xtype : 'grid',
					defaultEditingPlugin : true,
					columnLines : true,
					store : 'ChangeWorkOrderSeqStore',
					columns : [{
						text : '生产单号',
						dataIndex : 'workOrderNo',
						flex : 1.7,
						minWidth : 85,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							var isDispatch = record.get('isDispatch');
							metaData.tdAttr = 'data-qtip="' + value + (isDispatch ? '(急)' : '') + '"';
							value = value.substring(value.length - 6)
									+ (isDispatch ? '<font color="red">(急)</font>' : '');
							return value;
						}
					}, {
						text : '合同号 - 客户型号规格 - 合同长度',
						dataIndex : 'contractNo',
						menuDisabled : true,
						sortable : false,
						flex : 5,
						minWidth : 250,
						renderer : function(value, metaData, record) {
							var machine = value.split(",");
							var res = '';
							for (var i = 0; i < machine.length; i++) {
								res = res + machine[i].split(';')[0] + "<br/>";
							}
							metaData.tdAttr = 'data-qtip="' + res + '"';
							return res;
						}
					}, {
						text : '生产线',
						dataIndex : 'equipName',
						flex : 3,
						minWidth : 150,
						hidden : true,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							var machine = value.split(",");
							var res = '', res1 = '';
							for (var i = 0; i < machine.length; i++) {
								res = res + '<a style="color:blue;cursor:pointer;" onclick="intoMachine(\''
										+ machine[i] + '\')">' + machine[i] + '</a>' + '<br/>';
								res1 = res1 + machine[i] + '<br/>';
							}
							metaData.tdAttr = 'data-qtip="' + res1 + '"';
							return res;
						}
					}, {
						text : '工序',
						dataIndex : 'processName',
						flex : 1.6,
						minWidth : 80,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : '特殊分<br/>盘要求',
						dataIndex : 'specialReqSplit',
						flex : 1.3,
						minWidth : 65,
						sortable : false,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							// metaData.style =
							// "white-space:normal;word-break:break-all;padding:5px
							// 5px 5px 5px;";
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : '总任<br/>务数',
						dataIndex : 'orderLength',
						flex : 1.2,
						minWidth : 60,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : '下发<br/>日期',
						dataIndex : 'releaseDate',
						xtype : 'datecolumn',
						flex : 1.2,
						minWidth : 60,
						menuDisabled : true,
						format : 'm-d'
					}, {
						text : '要求完<br/>成日期',
						dataIndex : 'requireFinishDate',
						xtype : 'datecolumn',
						format : 'm-d',
						flex : 1.2,
						minWidth : 60,
						menuDisabled : true
					}, {
						text : '实际完<br/>成日期',
						dataIndex : 'realEndTime',
						xtype : 'datecolumn',
						format : 'm-d',
						flex : 1.2,
						minWidth : 60,
						menuDisabled : true
					}, {
						text : '完成<br/>进度',
						dataIndex : 'percent',
						flex : 1,
						minWidth : 50,
						menuDisabled : true,
						sortable : false,
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							var html = '<a style="color:blue;cursor:pointer;" onclick="showPercentDetail(\''
									+ record.get('workOrderNo') + '\', \'\', \'\')">' + value + '%</a>';
							metaData.tdAttr = 'data-qtip="' + value + '%"';
							return html;
						}
					}, {
						text : '附件',
						dataIndex : 'workOrderNo',
						flex : 1,
						minWidth : 50,
						renderer : function(value, metaData, record, rowIndex) {
							var nofile = (record.get('orderfilenum') == 0);
							var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"'
									+ (nofile ? '' : 'onclick="lookUpAttachFileWindow(\'\', \'' + value + '\', \'\')"')
									+ '>附件</a>';
							return html;
						},
						sortable : false,
						menuDisabled : true
					}, {
						text : '备注&技术要求',
						dataIndex : 'userComment',
						flex : 5,
						minWidth : 250,
						menuDisabled : true,
						renderer : function(value, metaData, record, rowIndex) {
							var svalue = value.replace(/\n/g, "<br/>");
							metaData.tdAttr = 'data-qtip="' + svalue + '"';
							return '<a style="color:blue;cursor:pointer;" onclick="showremarks(\'' + svalue + '\')">'
									+ value + '</a>';
						}
					}, {
						text : '状态',
						dataIndex : 'status',
						flex : 1.2,
						minWidth : 60,
						menuDisabled : true,
						renderer : function(value) {
							switch (value) {
								case 'TO_AUDIT' :
									return Oit.msg.pla.handSchedule.statusName.toAudit;
								case 'TO_DO' :
									return Oit.msg.pla.handSchedule.statusName.toDo;
								case 'IN_PROGRESS' :
									return Oit.msg.pla.handSchedule.statusName.inProgress;
								case 'FINISHED' :
									return Oit.msg.pla.handSchedule.statusName.finished;
								case 'PAUSE' :
									return Oit.msg.pla.handSchedule.statusName.pause;
								case 'CANCELED' :
									return Oit.msg.pla.handSchedule.statusName.canceled;
								default :
									return '';
							}
						}
					}],
					tbar : [{
						xtype : 'form',
						layout : 'hbox',
						items : [{
									fieldLabel : '选择工段',
									labelWidth : 70,
									width : 350,
									xtype : 'radiogroup',
									vertical : true,
									margins : '0 30 0 10',
									defaults : { // 设置样式：label的宽度
										width : 80
									},
									items : processSection,
									listeners : {
										change : function(radio, newValue, oldValue, eOpts) {
											var equipCode = radio.up('form').getForm().findField('equipCode');
											equipCode.clearValue();
											equipCode.getStore().load({												
														params : {
															'section' : newValue
														},
														callback : function(records, operation, success) {
															Ext.Array.each(records, function(record, i) {
																		equipCode.select(record);
																		var grid = radio.up('grid');
																		grid.getStore().load({
																					params : {
																						equipCode : record.get('code')
																					}
																				});
																		return false;
																	});
														}
													});
										}
									}
								}, {
									xtype : 'combobox',
									fieldLabel : '选择生产线',
									labelWidth : 80,
									displayField : 'name',
									valueField : 'code',
									name : 'equipCode',
									width : 300,
									queryMode : 'local',
									store : new Ext.data.Store({
												fields : ['code', 'name', 'equipAlias'],
												autoLoad : false,
												proxy : {
													type : 'rest',
													url : 'handSchedule/getEquipByProcessSectionN'
												}
											}),
									listeners : {
										select : function(combo, newValue, oldValue, eOpts) {
											var me = this;
											var grid = combo.up('grid');
											grid.getStore().load({
														params : {
															equipCode : combo.getValue()
														}
													});
										}
									}
								}]
					}, '->', {
						itemId : 'move',
						text : Oit.msg.pla.customerOrderItem.button.move
					}, {
						itemId : 'down',
						text : Oit.msg.pla.customerOrderItem.button.down
					}, {
						itemId : 'top',
						text : Oit.msg.pla.customerOrderItem.button.top
					}, {
						itemId : 'end',
						text : Oit.msg.pla.customerOrderItem.button.end
					}]
				}];

				Ext.apply(me, {
							buttons : ['->', {
										itemId : 'save',
										text : Oit.btn.save,
										iconCls : 'icon_save'
									}, {
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});


				this.callParent(arguments);
				// 设置选中第一个选择框
				if(me.processSection.length > 0){
				me.down('radiogroup').setValue({
							processCode : me.processSection[0].sectionSeq
						});
				};
				
			}
		});