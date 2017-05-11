var thisobject;
Ext.define('bsmes.controller.TerminalMultipleController', {
			extend : 'bsmes.controller.TerminalController',
			view : 'terminalMultipleView',
			signView : 'signView',
			detailValidWin : 'userIntoDetailValidWindow',
			reportValidWin : 'reportUserValidWindow',
			checkOrderView : 'checkOrderView',
			checkOrderWindow : 'checkOrderWindow',
			checkOrderJYView : 'checkOrderJYView',
			checkOrderJYWindow : 'checkOrderJYWindow',
			multiReportDetailView : 'multiReportDetailView',
			views : ['TerminalMultipleView', 'SignView', 'ReportGrid', 'HandleEquipValidWindow', 'VirtualKeyBoard',
					'UserIntoDetailValidWindow', 'ReportUserValidWindow', 'CheckOrderView', 'CheckOrderWindow', 'CheckOrderJYView',
					'CheckOrderJYWindow', 'MultiReportDetailView', 'MultiReportDetailGrid', 'CheckOperationManual'],
			stores : ['ReportStore', 'SectionStore', 'EquipReportStore', 'CheckWorkOrderStore'],
			constructor : function() {
				var me = this;
				// 初始化refs
				me.refs = me.refs || [];

				me.refs.push({
							ref : 'view',
							selector : me.view
						});

				me.refs.push({
							ref : 'signView',
							selector : me.signView,
							autoCreate : true,
							xtype : me.signView
						});

				me.refs.push({
							ref : 'signViewForm',
							selector : me.signView + ' form'
						});

				me.refs.push({
							ref : 'detailValidWin',
							selector : me.detailValidWin,
							autoCreate : true,
							xtype : me.detailValidWin
						});

				me.refs.push({
							ref : 'reportValidWin',
							selector : me.reportValidWin,
							autoCreate : true,
							xtype : me.reportValidWin
						});

				me.refs.push({
							ref : 'multiReportDetailView',
							selector : me.multiReportDetailView,
							autoCreate : true,
							xtype : me.multiReportDetailView
						});

				me.refs.push({
							ref : 'checkOrderView',
							selector : me.checkOrderView,
							autoCreate : true,
							xtype : me.checkOrderView
						});
				me.refs.push({
							ref : 'checkOrderWindow',
							selector : me.checkOrderWindow,
							autoCreate : true,
							xtype : me.checkOrderWindow
						});
				me.refs.push({
							ref : 'checkOrderJYView',
							selector : me.checkOrderJYView,
							autoCreate : true,
							xtype : me.checkOrderJYView
						});
				me.refs.push({
					ref : 'checkOrderJYWindow',
					selector : me.checkOrderJYWindow,
					autoCreate : true,
					xtype : me.checkOrderJYWindow
				});

				me.callParent(arguments);
				
				setInterval(function() {
					me.refreshEventConfrm.apply(me);
				}, 900000);
			},

			init : function() {
				var me = this;
				thisobject = me;
				me.control(me.view + ' equipView button[itemId=handleEquipError]', {
							click : me.onHandleEquipError
						});

				me.control(me.view + ' button[itemId=multiReportDetail]', {
							click : function() {
								var win = me.getMultiReportDetailView();
								var reportGrid = Ext.getCmp('multiReportDetailGrid_01');
								var store_01 = reportGrid.getStore();
								store_01.getProxy().url = 'terminal/reportDetail/' + Ext.get('equip0').getAttribute('equipCode');
								store_01.loadPage(1, {
											params : {
												reportDate : 0
											}
										});
								win.show();
							}
						});

				me.control('multiReportDetailView button[itemId=print]', {
							click : function() {
								var reportGrid = Ext.getCmp('multiReportDetailGrid_01');
								var data = reportGrid.getSelectionModel().getSelection();
								if (data.length == 0) {
									Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.msg.selectOnReport);
									return null;
								}
								me.printBarCode(data[0].get('id'));
							}
						});

				me.control(me.view + ' button[itemId=detail]', {
							click : function(btn) {
								var code = btn.equipCode + '_EQUIP';
								var me = this;
								var processCode = '';

								Ext.Ajax.request({
											url : '/bsmes/wip/terminal/getProcessCode',
											params : {
												code : code
											},
											async:false,
											success : function(response) {
												var result = Ext.decode(response.responseText);
												processCode = result[0].processCode;
											}
										});
								
								if(processCode == 'Steam-Line'){
									var window = me.getDetailValidWin();
									var form = window.down('form').getForm();
									form.findField('equipCode').setValue(btn.equipCode);
									window.show();
									form.findField('userCode').focus(false, 100);
								} else {
									Ext.Ajax.request({
										url : '/bsmes/wip/terminal/dailyCheckIsFinished',
										method : 'POST',
										params : {
											code : code
										},
										success : function(response) {
											var result = Ext.decode(response.responseText);
											if(result == false) {
												Ext.Msg.alert(Oit.msg.WARN,'请完成本班次的点检，否则无法进入详情');
												return;
											}
											var window = me.getDetailValidWin();
											var form = window.down('form').getForm();
											form.findField('equipCode').setValue(btn.equipCode);
											window.show();
											form.findField('userCode').focus(false, 100);
										},
										failure : function() {
											Ext.Msg.alert(Oit.msg.WARN,'请完成本班次的点检，否则无法进入详情');
										}
									});	
								}	
							}
						});

				/**
				 * 点检按钮
				 */
				me.control(me.view + ' button[itemId=check]', {
							click : function(btn) {
								var code = btn.equipCode + '_EQUIP';
								var me = this;
								
								Ext.Ajax.request({
											url : '/bsmes/wip/terminal/getProcessCode',
											params : {
												code : code
											},
											success : function(response) {
												var result = Ext.decode(response.responseText);
												var processCode = result[0].processCode;

												var items = Ext.create('bsmes.view.CheckOperationManual', {
															processCode : processCode
														});
												var win = new Ext.Window({
															title : '操作规程',
															padding : '10',
															width : document.body.scrollWidth * 0.7,
															height : document.body.scrollHeight * 0.7,
															layout : 'fit',
															items : items,
															buttons : ['->', {
																text : '确定',
																handler : function() {
																	this.up('window').close();
																	me.getCheckGridWin().show();
																	var checkStore = me.getCheckGrid().getStore();
																	checkStore.getProxy().url = "/bsmes/wip/terminal/check?equipCode="
																			+ btn.equipCode;
																	checkStore.reload();
																}
															}, {
																text : '关闭',
																handler : function() {
																	this.up('window').close();
																}
															}]
														});
												win.show();
											}
										});
							}
						});

				me.control(me.view + ' button[itemId=reportDetail]', {
							click : function(button) {
								var equipCode = button.equipCode;
								me.getReportDetailView().show();
								var grid = Ext.getCmp('reportDetailGrid');
								var store = grid.getStore();
								store.getProxy().url = "reportDetail/" + equipCode + '/';
								store.loadPage(1, {
											params : {
												reportTimeAround : 0
											}
										});
							}
						});

				//查看生产单
				me.control(me.view + ' button[itemId=checkOrderItemId]', {
							click : function(btn) {
								var window = me.getCheckOrderWindow();
								var form = window.down('form').getForm();
								form.findField('equipCode').setValue(btn.equipCode);
								form.findField('section').setValue(btn.section);
								//                window.show();
								var section = btn.section;
								console.log(section);
								if (section == '绝缘') {
									console.log('dhgfdhf');
									window = me.getCheckOrderJYWindow();
									form = window.down('form').getForm();
									form.findField('equipCode').setValue(btn.equipCode);
									form.findField('section').setValue(btn.section);
								}
								var store = Ext.ComponentQuery.query('#checkOrderGrid')[0].getStore();
								store.load({
											params : {
												section : section,
												equipCode : btn.equipCode,
												type : 'TO_DO'
											}
										});
								window.show();
							}
						});

				//触发警报
				me.control(me.view + ' button[itemId=triggerEquipAlarm]', {
							click : function(btn) {
								var code = btn.equipCode + '_EQUIP';
								var me = this;
								Ext.Ajax.request({
											url : '/bsmes/wip/terminal/getProcessCode',
											params : {
												code : code
											},
											success : function(response) {
												var result = Ext.decode(response.responseText);
												var processCode = result[0].processCode;
												var window = me.getTriggerEquipAlarmWin({
															processCode : processCode
														});
												window.down('form').form.findField('equipCode').setValue(btn.equipCode);
												window.show();
											}
										});
							}
						});
				//处理警报
				me.control(me.view + ' button[itemId=handleEquipAlarm]', {
							click : function(btn) {
								var me = this;
								var window = me.getHandleEquipAlarmWin();
								window.down('checkboxgroup').setValue('UNCOMPLETED');
								console.log(btn.equipCode);
								window.down('hform').form.findField('equipCode').setValue(btn.equipCode);
								me.getHandleEquipAlarmGrid().getStore().load({
											params : {
												eventStatusFindParam : 'UNCOMPLETED'
											}
										});
								window.show();
							}
						});

				// 初始化按： 处理设备警报列表->处理验证弹出按钮
				me.control(me.handleEquipAlarmWin + ' button[itemId=handleAlarm]', {
							click : function(btn) {
								var me = this;
								var selection = me.getHandleEquipAlarmGrid().getSelectionModel().getSelection();
								if (selection && selection.length > 0) {
									var record = selection[0];
									if (record.getData().eventStatus == 'COMPLETED' || record.getData().eventStatus == 'RESPONDED') {
										Ext.Msg.alert(Oit.msg.WARN, Oit.error.handled);
										return;
									}
									// 创建验证窗口
									var window = Ext.create('bsmes.view.HandleEquipValidWindow', {
												// 弹出后刷新grid的参数为：grid中的查询参数
												refreshParams : {
													eventStatusFindParam : Ext.getCmp("eventStatusComb").getValue()
												}
											})

									var equipCode = record.getData().equipCode;
									// 验证窗口部分值赋值
									window.down('form').form.findField('id').setValue(record.getData().id);
									window.down('form').form.findField('equipCode').setValue(equipCode);
									window.show(); // 弹出
									window.down('form').form.findField('userCode').focus(false, 100);
								} else {
									Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
									return;
								}
							}
						});
				// 初始化按： 处理设备警报列表->处理验证->提交按钮
				me.control(me.handleEquipValidWin + ' button[itemId=ok]', {
							click : function() {
								var me = this;
								var validWin = me.getHandleEquipValidWin(); // 获取验证弹出窗
								var form = validWin.down('form').getForm(); // 获取验证弹出窗的FORM
								if (form.isValid()) {
									Ext.Msg.wait('处理中，请稍后...', '提示');
									Ext.getCmp('handleEquipValidWindowOkBtn').disable();
									form.submit({
												success : function(form, action) {
													Ext.Msg.hide(); //隐藏进度条
													validWin.close(); // 关闭验证弹出窗
													// 刷新数据
													var alarmGrid = me.getHandleEquipAlarmGrid();
													alarmGrid.getStore().load({
																params : validWin.refreshParams
															});
												},
												failure : function(form, action) {
													Ext.getCmp('handleEquipValidWindowOkBtn').enable();
													Ext.Msg.hide(); //隐藏进度条
													action = Ext.decode(action.response.responseText)
													Ext.Msg.alert(Oit.msg.wip.title.fail, action.message);
													//Ext.Msg.alert(Oit.msg.wip.title.fail, action.result ? Oit.msg.wip.workOrder.msg.userValidFalse : 'No response');
												}
											});
								}
							}
						});
				// 初始化按钮：工人完成维修后操作
				me.control(me.handleEquipAlarmWin + ' button[itemId=finishRepair]', {
							click : me.handleRepairResult
						});
				me.control(me.reportValidWin + ' button[itemId=ok]', {
							click : function() {
								var win = me.getReportValidWin();
								var form = win.down('form').getForm();
								var equipCode = form.findField('equipCode').getValue();
								if (form.isValid()) {
									var userCode = form.findField('userCode').getValue();
									form.submit({
												success : function(form, action) {
													win.close();
													//var record = me.equipInfos.get(equipCode);
													var record = Ext.ComponentQuery.query('terminalMultipleView')[0].equipInfos[button.equipCode];
													me.getReportView().show();
													var currentLength = 0;
													if (record.get('currentLength') != null) {
														currentLength = record.get('currentReportLength');
													}
													record.data.currentLength = currentLength;
													record.data.reportLength = currentLength;
													me.getReportForm().loadRecord(record);

													var reportGrid = me.getReportGrid();
													var reportStore = reportGrid.getStore();
													reportStore.getProxy().url = "/bsmes/wip/terminal/queryReport/"
															+ record.get("workOrderNo");
													reportStore.reload();
													Ext.util.Cookies.set("operator", userCode);
												},
												failure : function(form, action) {
													var result = Ext.decode(action.response.responseText);
													Ext.Msg.alert(Oit.msg.PROMPT, result.msg);
												}
											})
								}
							}
						});

				me.callParent(arguments);
			},

			// 处理设备警报列表窗->弹出
			onHandleEquipError : function(btn) {
				var me = this;
				var window = Ext.create('bsmes.view.HandleEquipValidWindow', {
							url : 'terminal/handleEquipError'
						})
				window.down('form').form.findField('equipCode').setValue(btn.equipCode);
				window.show();
				window.down('form').form.findField('userCode').focus(false, 100);
			},
			handleRepairResult : function() {
				var me = this;
				var selection = me.getHandleEquipAlarmGrid().getSelectionModel().getSelection();
				if (selection && selection.length > 0) {
					var record = selection[0];
					var id = record.getData().id;
					Ext.Ajax.request({
						url : '/bsmes/wip/terminal/findEventStatus',
						async : false,
						method : 'POST',
						params : {
							eventId : id
						},
						success : function(response) {
							var record = Ext.decode(response.responseText);
							if (record.eventStatus == 'COMPLETED') {
								Ext.Msg.alert(Oit.msg.WARN, Oit.error.handled);
								return;
							} else if (record.eventStatus == 'UNCOMPLETED' || record.eventStatus == 'RESPONDED') {
								Ext.Msg.alert(Oit.msg.WARN, '请先处理警报！');
								return;
							} else if (record.eventStatus == 'PENDING') {
								// 创建验证窗口
								var window = Ext.create('bsmes.view.FinishRepairValidWindow');
								var equipCode = record.equipCode;
								// 验证窗口部分值赋值
								window.down('form').form.findField('id').setValue(id);
								window.down('form').form.findField('equipCode').setValue(equipCode);
								window.show(); // 弹出
							}
						}
					});
				} else {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
					return;
				}
			},
			promptMiddleCheck : function() {
				var equipNames = '';
				for (var i = 0; i < 6; i++) {
					if (Ext.getCmp('needMiddleCheck_equip' + i)) {
						if (Ext.getCmp('needMiddleCheck_equip' + i).getValue() == '1') {
							equipNames += Ext.getCmp('equipName_equip' + i).getValue() + '、';
						}
					}
				}
				if (equipNames) {
					Ext.Msg.alert(Oit.msg.WARN, "设备" + equipNames.substring(0, equipNames.length - 1) + "上生产的"
									+ Oit.msg.wip.terminal.msg.needMiddleCheckMsg);
				}
			},

			//产品QA检测手工录入 TODO JINHANYUN
			openEntryWindow : function() {
				var me = this;
				var window = me.getProcessQAEntryView();
				me.getProcessQAEntryGrid().getStore().reload();
				window.show();
			},
			saveProcessQAValue : function() {
				var me = this;
				var form = me.getProcessQAEntrySearchForm();
				var store = me.getProcessQAEntryGrid().getStore();
				var dataArray = new Array();
				Ext.each(store.getUpdatedRecords(), function(record, i) {
							var data = record.getData();
							data.type = form.getValues().type;
							dataArray.push(data);
						});
				Ext.Ajax.request({
							url : '/bsmes/wip/terminal/saveProcessQCValue',
							method : 'POST',
							params : {
								'jsonText' : Ext.encode(dataArray)
							},
							success : function(response) {
								me.getProcessQAEntryView().close();
							}
						});
			},
			refresh : function() {
				var me = this;
				Ext.Ajax.request({
							url : '../wip/terminal/refresh',
							timeout : 60000,

							method : 'GET',
							success : function(response) {
								var result = Ext.decode(response.responseText);
								if (result.success == false) {
									Ext.Msg.alert(Oit.msg.PROMPT, result.message);
								} else {
									Ext.each(result, function(info) {
												if (!info.equipCode) {
													return;
												}
												var infoModel = Ext.create('bsmes.model.MesClientEquipInfo', info);
												//me.equipInfos.add(info.equipCode, infoModel);
												Ext.ComponentQuery.query('terminalMultipleView')[0].equipInfos[info.equipCode] = infoModel;
												var equipForm = Ext.ComponentQuery.query('#equipForm' + info.eqipId);
												if (equipForm.length == 0) {
													return;
												}
												equipForm[0].loadRecord(infoModel);
												var equipView = Ext.ComponentQuery.query('#equipView' + info.eqipId);
//												var detailBtn = equipView[0].dockedItems.items[1].items.items[0];
												var handleBtn = equipView[0].dockedItems.items[1].items.items[2]; // 处理设备异常 按钮
//												if ((info.toDoTaskNum > 0 && info.status == 'IDLE') || info.status == 'IN_DEBUG'
//														|| info.status == 'IN_PROGRESS') {
//													detailBtn.show();
//												} else {
//													detailBtn.hide();
//												}
												if (info.status == 'ERROR') {
													handleBtn.show();
												}
											});
								}
							}
						});
			},
			refreshEventConfrm : function(){
				var me = this;
				//如果打开了处理警报界面，就不需要提醒了
				var m = Ext.ComponentQuery.query('handleEquipAlarmWindow');
				if(m.length>0){
					return;
				}
				Ext.Ajax.request({
					url : 'terminal/refreshEquipEvent',
					method : 'GET',
					params : {
						equipCode : ''
					},
					success : function(response) {
						var results = Ext.decode(response.responseText).equipEventConfirm;
						var msg = '';
						if(results.length > 0){
							Ext.Array.each(results, function(name, index, countriesItSelf) {
								msg = msg + '<p style = "margin : 0 20 0 20;color:red;font-weight:normal;">'+name.eventContent+'</p>';
							});
							Ext.Msg.show({
							     title:'提醒',
							     msg: msg,
							     buttons: Ext.Msg.YES,
							     buttonText : {yes : '处理警报'},
							     fn : function(btn){
							    	 if(btn == 'yes'){
							    		 me.openHandleEquipAlarmWindow();
							    	 }
							     }
							});
						}
					}
				});
			},
			// 处理设备警报列表窗弹出
			openHandleEquipAlarmWindow : function() {
				var me = this;
				var window = me.getHandleEquipAlarmWin();
				window.down('checkboxgroup').setValue('PENDING');
				window.show();
			}
		});

Ext.onReady(function() {

			var me = thisobject;
			console.log('TerminalMultipleController.js (3 matches)-----------');
			Ext.Ajax.request({
						url : '../wip/terminal/refresh',
						timeout : 60000,
						method : 'GET',
						async : false,
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if (result.success == false) {
								Ext.Msg.alert(Oit.msg.PROMPT, result.message);
							} else {
								Ext.each(result, function(info) {
											if (!info.equipCode) {
												return;
											}
											var infoModel = Ext.create('bsmes.model.MesClientEquipInfo', info);
											//me.equipInfos.add(info.equipCode, infoModel);
											Ext.ComponentQuery.query('terminalMultipleView')[0].equipInfos[info.equipCode] = infoModel;
											var equipForm = Ext.ComponentQuery.query('#equipForm' + info.eqipId);
											if (equipForm.length == 0) {
												return;
											}
											equipForm[0].loadRecord(infoModel);
											var equipView = Ext.ComponentQuery.query('#equipView' + info.eqipId);
//											var detailBtn = equipView[0].dockedItems.items[1].items.items[0];
											var handleBtn = equipView[0].dockedItems.items[1].items.items[2]; // 处理设备异常 按钮
//											if ((info.toDoTaskNum > 0 && info.status == 'IDLE') || info.status == 'IN_DEBUG'
//													|| info.status == 'IN_PROGRESS') {
//												detailBtn.show();
//											} else {
//												detailBtn.hide();
//											}
											if (info.status == 'ERROR') {
												handleBtn.show();
											}
										});
							}
						}
					});

		});
