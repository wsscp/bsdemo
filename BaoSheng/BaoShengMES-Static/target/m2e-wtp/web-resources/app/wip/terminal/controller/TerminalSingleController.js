var terminalSingleControllerObject;
Ext.define('bsmes.controller.TerminalSingleController', {
			extend : 'bsmes.controller.TerminalController',
			view : 'terminalSingleView',
			debugView : 'debugInfoView', // 下发参数
			processQAEntryView : 'processQAEntryWindow',
			sparkRepairListWindow : 'sparkRepairListWindow',
			repairTypeWindow : 'repairTypeWindow',
			supplementMaterialWindow : 'supplementMaterialWindow',
			getMaterialWindow : 'getMaterialWindow',
			matPlanView : 'todayMatPlanView',
			changeOrderView : 'changeOrderView',
			changeOrderWindow : 'changeOrderWindow',
			productionRecordView : 'productionRecordView',
			changeEquipUserCV : 'changeEquipUserCVWindow',
			shiftRecordView : 'shiftRecordView',
			taskInfoGridJY : 'taskInfoGridJY',
			shiftRecordWindow : 'shiftRecordWindow',
			workOrderNoInfoGrid: 'workOrderNoInfoGrid',
			turnOverMaterialWindow : 'turnOverMaterialWindow',
			views : ['TerminalSingleView', 'DebugInfoView', 'ChangeOrderView', 'ChangeOrderWindow', 'ChangeOrderValid',
					'ProductionRecordView', 'ProcessQAEntryWindow', 'ReportGrid', 'ReportDetailView', 'MaterialGrid', 'ProcessReceiptGrid',
					'ProcessQcGrid', 'AgreementGrid', 'DailyCheckGrid', 'ReportDetailGrid', 'VirtualKeyBoard', 'VirtualKeyBoardWindow',
					'SparkRepairListWindow', 'RepairTypeWindow', 'TodayMatPlanView', 'ReportHTJYGrid',
					'ChangeEquipUserCVWindow', 'SupplementMaterialWindow', 'ShiftRecordWindow', 'ShiftRecordView',
					'TurnOverMaterialWindow', 'GetMaterialWindow', 'TaskInfoGridJY','WorkOrderNoInfoGrid','ApplyMaterials'],
			stores : ['ProcessReceiptStore', 'CheckGridStore', 'ProcessQcStore', 'WorkOrderStore', 'ProcessInStore', 'AgreementStore',
					'ReportStore', 'SectionStore', 'EquipReportStore', 'InvLocationStore', 'DailyCheckStore', 'SparkRepairStore',
					'TodayMatPlanStore', 'ShiftRecordStore','WorkOrderNoInfoStore'],
			workOrder : {},
			constructor : function() {
				var me = this;
				terminalSingleControllerObject = me;
				
				// 初始化refsW
				me.refs = me.refs || [];

				me.refs.push({
							ref : 'materialGrid',
							selector : '#materialGrid'
						});

				me.refs.push({
							ref : 'debugView',
							selector : me.debugView,
							autoCreate : true,
							xtype : me.debugView
						});

				me.refs.push({
							ref : 'turnOverMaterialWindow',
							selector : me.turnOverMaterialWindow,
							autoCreate : true,
							xtype : me.turnOverMaterialWindow
						});
				
				me.refs.push({
					ref : 'changeEquipUserCVWindow',
					selector : me.changeEquipUserCV,
					autoCreate : true,
					xtype : me.changeEquipUserCV
				});
				
				me.refs.push({
					ref : 'applyMaterials',
					selector : 'applyMaterials',
					autoCreate : true,
					xtype : 'applyMaterials'
				});
				
				me.refs.push({
							ref : 'EquipUserCVWindow',
							selector : me.changeEquipUserCV,
							autoCreate : true,
							xtype : me.changeEquipUserCV
						});

				me.refs.push({
							ref : 'matPlanView',
							selector : me.matPlanView,
							autoCreate : true,
							xtype : me.matPlanView
						});

				// 注册查看生产单window
				me.refs.push({
							ref : 'changeOrderView',
							selector : me.changeOrderView,
							autoCreate : true,
							xtype : me.changeOrderView
						});

				me.refs.push({
							ref : 'changeOrderWindow',
							selector : me.changeOrderWindow,
							autoCreate : true,
							xtype : me.changeOrderWindow
						});
						
					// 注册get方法：切换生产的验证弹出view
				me.refs.push({
							ref : 'changeOrderValid',
							selector : 'changeOrderValid',
							autoCreate : true,
							xtype : 'changeOrderValid'
						});

				me.refs.push({
							ref : 'shiftRecordWindow',
							selector : me.shiftRecordWindow,
							autoCreate : true,
							xtype : me.shiftRecordWindow
						});

				me.refs.push({
							ref : 'taskInfoGridJY',
							selector : me.taskInfoGridJY,
							autoCreate : true,
							xtype : me.taskInfoGridJY
						});

				me.refs.push({
							ref : 'shiftRecordView',
							selector : me.shiftRecordView,
							autoCreate : true,
							xtype : me.shiftRecordView
						});

				me.refs.push({
							ref : 'supplementMaterialWindow',
							selector : me.supplementMaterialWindow,
							autoCreate : true,
							xtype : me.supplementMaterialWindow
						});

				me.refs.push({
							ref : 'getMaterialWindow',
							selector : me.getMaterialWindow,
							autoCreate : true,
							xtype : me.getMaterialWindow
						});

				me.refs.push({
							ref : 'productionRecordView',
							selector : me.productionRecordView,
							autoCreate : true,
							xtype : me.productionRecordView
						});

				// 产品QA检测手工录入 window
				me.refs.push({
							ref : 'processQAEntryView',
							selector : me.processQAEntryView,
							autoCreate : true,
							xtype : me.processQAEntryView
						});

				me.refs.push({
							ref : 'processQAEntryGrid',
							selector : me.processQAEntryView + ' processQAEntryList'
						});

				me.refs.push({
							ref : 'processQAEntrySearchForm',
							selector : me.processQAEntryView + ' form'
						});

				me.refs.push({
							ref : 'changeEquipStatusBtn',
							selector : me.view + ' button[itemId=changeEquipStatus]'
						});
				me.refs.push({
							ref : 'issuedParamBtn',
							selector : me.view + ' button[itemId=issuedParam]'
						});

				me.refs.push({
							ref : 'qADataEntryBtn',
							selector : me.view + ' button[itemId=QADataEntry]'
						});

				me.refs.push({
							ref : 'receiveBtn',
							selector : me.view + ' button[itemId=receive]'
						});

				me.refs.push({
							ref : 'userRoleValidWin',
							selector : me.userRoleValidWin,
							autoCreate : true,
							xtype : me.userRoleValidWin
						});

				me.refs.push({
							ref : 'feedForm',
							selector : me.view + ' form[id=feedForm]'
						});
				me.refs.push({
							ref : 'feedText',
							selector : me.view + ' textfield[itemId=feedText]'
						});

				me.refs.push({
							ref : 'feedBtn',
							selector : me.view + ' button[itemId=feed]'
						});
				me.refs.push({
							ref : 'seeBtn',
							selector : me.view + ' button[itemId=see]'
				});

				me.refs.push({
							ref : 'sparkRepairListWindow',
							selector : me.sparkRepairListWindow,
							autoCreate : true,
							xtype : me.sparkRepairListWindow
						});

				me.refs.push({
							ref : 'repairTypeWindow',
							selector : me.repairTypeWindow,
							autoCreate : true,
							xtype : me.repairTypeWindow
						});
				me.refs.push({
					ref : 'workOrderNoInfoGrid',
					selector : me.workOrderNoInfoGrid,
					autoCreate : true,
					xtype : me.workOrderNoInfoGrid
				});

				me.callParent(arguments);
				me.workOrder = Ext.create('bsmes.model.WorkOrder', {
							workOrderNo : Ext.fly('orderInfo').getAttribute('num')
						});
				//单终端时，每隔15分钟提示消息，告诉终端操作工处理待确认的警报
				setInterval(function() {
					me.refreshEquipEvent.apply(me);
				}, 900000);
			},
			init : function() {
				var me = this;
				var processCode = Ext.fly("processInfo").getAttribute('code');
				// 初始化工具栏
				me.control(me.view + ' button[itemId=issuedParam]', {
							click : me.onDebug
						});

				// 下发参数
				me.control(me.debugView + ' button[itemId=debugInfoSave]', {
							click : me.debugSubmit
						});
				
				/**
				 * 终端详情界面点检按钮
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
				
				// 改变设备状态
				me.control(me.view + ' button[itemId=changeEquipStatus]', {
							click : me.onChangeEquipStatus
						});

				// 投料
				me.control(me.view + ' button[itemId=feed]', {
							click : me.submitMaterial
						});

				// 查看生产单
				me.control(me.view + ' button[itemId=change]', {
							click : me.toChangeOrder
						});
				
				// 查看投料信息                   2016-4-6
				me.control(me.view + ' button[itemId=see]', {
							click : me.seeMaterialInfo
						});

				// 生产记录表
				me.control(me.view + ' button[itemId=productionRecord]', {
							click : me.toProductionRecord
						});

				// 交班报表
				me.control(me.view + ' button[itemId=shiftRecord]', {
							click : me.toShiftRecord
						});

				// 交料
				me.control(me.view + ' button[itemId=turnOverMatreial]', {
							click : me.toTurnOverMatreial
						});

				// 暂停订单按钮
				me.control(me.changeOrderView + ' button[itemId=pause]', {
							click : function(btn) {
								var changeOrderGrid = Ext.ComponentQuery.query('#recentOrderGrid')[0];
								var records = changeOrderGrid.getSelectionModel().getSelection();
								if (records.length == 0) {
									Ext.Msg.alert(Oit.msg.WARN, '请选择一条要生产的生产单!');
									return;
								}
								var window = me.getChangeOrderValid();
								window.show();
							}
						});
				
				//编织机通过半成品编码切换生产单
				me.control(me.workOrderNoInfoGrid + ' button[itemId=pause]', {
					click : function(btn) {
						var changeOrderGrid = Ext.ComponentQuery.query('#workOrderNoInfoGrid')[0];
						var records = changeOrderGrid.getSelectionModel().getSelection();
						if (records.length == 0) {
							Ext.Msg.alert(Oit.msg.WARN, '请选择一条要生产的生产单!');
							return;
						}
						var window = me.getChangeOrderValid();
						window.show();
					}
				});
				// 切换生产单
				me.control(me.changeOrderView + ' button[itemId=changeOrder]', {
							click : me.onChangeOrder
						});

				// 打印生产单物料清单
				me.control(me.changeOrderView + ' button[itemId=printMatList]', {
							click : me.toPrintMatList
						});

				// 调整机台 ->权限验证
				me.control(me.changeOrderView + ' button[itemId=changeEquip]', {
							click : me.userCompetenceVerification
						});

				me.control(me.changeOrderView + ' button[itemId=getMaterial]', {
							click : me.getMaterial
						});

				// 补料
				me.control(me.changeOrderView + ' button[itemId=supplementMaterial]', {
							click : me.supplementMaterial
						});

				me.control(me.view + ' button[itemId=QADataEntry]', {
							click : me.openEntryWindow
						});

				me.control(me.view + ' button[itemId=toDayMatPlan]', {
							click : me.openMatPlan
						});

				// 初始化按： 触发设备警报弹出按钮
				me.control(me.view + ' button[itemId=triggerEquipAlarm]', {
							click : me.openTriggerEquipAlarmWindow
						});

				// 初始化按： 处理设备警报列表弹出按钮
				me.control(me.view + ' button[itemId=handleEquipAlarm]', {
							click : function(){
								me.openHandleEquipAlarmWindow(1);
							}
						})
				// 初始化按： 处理设备警报列表->处理验证弹出按钮
				me.control(me.handleEquipAlarmWin + ' button[itemId=handleAlarm]', {
							click : me.openHandleEquipValidWindow
						});

				// 初始化按钮：工人完成维修后操作
				me.control(me.handleEquipAlarmWin + ' button[itemId=finishRepair]', {
							click : me.handleRepairResult
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
													Ext.Msg.hide(); // 隐藏进度条
													validWin.close(); // 关闭验证弹出窗
													// 刷新数据
													var alarmGrid = me.getHandleEquipAlarmGrid();
													alarmGrid.getStore().load({
																params : validWin.refreshParams
															});
												},
												failure : function(form, action) {
													Ext.getCmp('handleEquipValidWindowOkBtn').enable();
													Ext.Msg.hide(); // 隐藏进度条
													action = Ext.decode(action.response.responseText)
													Ext.Msg.alert(Oit.msg.wip.title.fail, action.message);
													// Ext.Msg.alert(Oit.msg.wip.title.fail,
													// action.result ?
													// Oit.msg.wip.workOrder.msg.userValidFalse
													// : 'No response');
												}
											});
								}

							}
						});

				me.control(me.processQAEntryView + ' button[itemId=ok]', {
							click : me.saveProcessQAValue
						});

				me.control(me.view + ' button[itemId=dailyMaintain]', {
							click : function() {
								var equipCode = Ext.fly('equipInfo').getAttribute('code');
								Oit.app.controller.GridController
										.openSubWindow('../../fac/maintainRecordItem.action?recordId=-1&touch=true&type=DAILY&equipCode='
												+ equipCode);
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

				me.control(me.view + ' button[itemId=receive]', {
							click : function(button) {
								if (button.text == Oit.msg.wip.terminal.btn.report) { // report
									var result;
									var code = button.equipCode + '_EQUIP';
									var me = this;
									if(processCode == 'Steam-Line'){
										Ext.Ajax.request({
											url : '../terminal/checkOrderId/',
											method : 'POST',
											async : false,
											params : {
												workOrderNo : Ext.fly('orderInfo').getAttribute('num')
											},
											success : function(response) {
												result = Ext.decode(response.responseText);
											}
										});

										if (result == false) {
											Ext.MessageBox.alert(Oit.msg.PROMPT, "请选中一个任务!");
											return;
										}
										//									var record = me.equipInfos.get(button.equipCode);
										var record = Ext.ComponentQuery.query('terminalMainView')[0].equipInfos[button.equipCode];
										var currentLength = 0;
										if (record.get('currentReportLength') != null) {
											currentLength = record.get('currentReportLength');
										}
										record.data.currentLength = currentLength;
										record.data.reportLength = currentLength;
										me.getReportView({
													record : record
												}).show();
										//me.getReportForm().loadRecord(record);
										var reportGrid = me.getReportGrid();
										var reportStore = reportGrid.getStore();
										reportStore.getProxy().url = "/bsmes/wip/terminal/queryReport/" + record.get("workOrderNo");
										reportStore.reload();
									} else {
										Ext.Ajax.request({
											url : '/bsmes/wip/terminal/dailyCheckIsFinished',
											method : 'POST',
											params : {
												code : code
											},
											success : function(response) {
												var res = Ext.decode(response.responseText);
												if(res == false) {
													Ext.Msg.alert(Oit.msg.WARN,'请完成本班次的点检，否则无法报工');
													return;
												}
												Ext.Ajax.request({
													url : '../terminal/checkOrderId/',
													method : 'POST',
													async : false,
													params : {
														workOrderNo : Ext.fly('orderInfo').getAttribute('num')
													},
													success : function(response) {
														result = Ext.decode(response.responseText);
													}
												});

												if (result == false) {
													Ext.MessageBox.alert(Oit.msg.PROMPT, "请选中一个任务!");
													return;
												}
												//									var record = me.equipInfos.get(button.equipCode);
												var record = Ext.ComponentQuery.query('terminalMainView')[0].equipInfos[button.equipCode];
												var currentLength = 0;
												if (record.get('currentReportLength') != null) {
													currentLength = record.get('currentReportLength');
												}
												record.data.currentLength = currentLength;
												record.data.reportLength = currentLength;
												me.getReportView({
															record : record
														}).show();
												//me.getReportForm().loadRecord(record);
												var reportGrid = null;
												if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling'
														|| processCode == 'Twisting' || processCode == 'Respool'
															|| processCode == 'Extrusion-Single' || processCode == 'Jacket-Extrusion') {
													reportGrid = me.getReportGrid();
												} else {
													reportGrid = me.getReportHTJYGrid();
												}
												var reportStore = reportGrid.getStore();
												reportStore.getProxy().url = "/bsmes/wip/terminal/queryReport/" + record.get("workOrderNo");
												reportStore.reload();
											},
											failure : function() {
												Ext.Msg.alert(Oit.msg.WARN,'请完成本班次的点检，否则无法进入详情');
											}
										});
									}
								} else { // receive order
									var me = this;
									var lastWorkOrderStatus = Ext.fly('orderInfo').getAttribute('lastWorkOrderStatus');
									if (lastWorkOrderStatus != 'FINISHED') {
										Ext.MessageBox.confirm('确认', '上一道工序未完成或未报工，是否继续接受任务?', function(btn) {
													if (btn == 'yes') {
														return;
													}
												});
									}
									Ext.Msg.wait('处理中，请稍后...', '提示');
									Ext.Ajax.request({
												url : '../terminal/acceptTask/',
												method : 'POST',
												params : {
													woNo : Ext.fly('orderInfo').getAttribute('num'),
													equipCode : button.equipCode,
													operator : Ext.util.Cookies.get("operator")
												},
												success : function(response) {
													var result = Ext.decode(response.responseText);
													var taskInfoListData = result.taskInfoList;
													var taskGrid = Ext.ComponentQuery.query('#taskInfoGrid')[0]; // 任务列表
													taskGrid.refresh(taskInfoListData);
													Ext.Msg.hide();
													Ext.Msg.alert(Oit.msg.PROMPT, Oit.msg.wip.terminal.msg.receiveOK);
													button.setText(Oit.msg.wip.terminal.btn.report);
													var img = document.getElementById("titleImg");
													var titleHtml = document.getElementById("titleHtml");
													Ext.fly('orderInfo').set({
																'status' : 'IN_PROGRESS'
															});
													Ext.fly('equipInfo').set({
																'status' : 'IN_DEBUG'
															});
													me.getChangeEquipStatusBtn().setText(Oit.msg.wip.terminal.btn.product);
													if(processCode == 'Steam-Line'){
														me.getQADataEntryBtn().hide();
														me.getIssuedParamBtn().hide();
													}else{
														me.getQADataEntryBtn().show();
														me.getIssuedParamBtn().show();
													}
													me.getReceiveBtn().hide();
													me.getChangeEquipStatusBtn().show();
													me.getReceiveBtn().hide();
													img.src = "/bsstatic/icons/IN_DEBUG.png ";
													titleHtml.innerHTML = titleHtml.innerHTML.replace("空闲", "调试");
												},
												failure : function(response, options) {
													Ext.Msg.hide();
													var result = Ext.decode(response.responseText).mesClientAcceptTask;
													
													Ext.Msg.alert(Oit.msg.PROMPT, result.msg, function(){
													    location.reload(true);
													});
												}
											});
								}
							}
						});

				// 初始化按： 查看火花点明细弹出窗
				me.control(me.view + ' button[itemId=sparkRepairDetail]', {
							click : me.openSparkRepairDetailWindow
						});
				me.control(me.sparkRepairListWindow + ' button[itemId=handleSpark]', {
							click : me.openRepairTypeWindow
						});

				me.control(me.repairTypeWindow + ' button[itemId=ok]', {
							click : me.submitRepairType
						});

				me.control(me.taskInfoGridJY + ' button[itemId=saveValue]', {
							click : me.saveValue
						});
				me.callParent(arguments);
			},
			onLaunch : function() {
				var me = this;
				me.callParent(arguments);

			},
			/**
			 * 下发参数点击弹出窗口
			 */
			onDebug : function() {
				var me = this;
				var window = me.getDebugView();
				window.down('grid').getStore().load(); // 加载数据
				window.show();
			},
			getWorkOrderNum : function() {
				return this.workOrder.get('workOrderNo');
			},
			debugSubmit : function() {
				var me = this;
				var window = me.getDebugView();
				var grid = window.down('grid');
				var receiptList = [], receiptUpdateRecords = grid.getStore().getUpdatedRecords();
				if (receiptUpdateRecords) {
					for (var i = 0; i < receiptUpdateRecords.length; i++) {
						receiptList.push(receiptUpdateRecords[i].data);
					}
				}
				if (receiptList.length == 0) {
					Ext.MessageBox.alert(Oit.msg.ERROR, '请修改再下发参数！');
					return;
				}
				Ext.Ajax.request({
							url : 'saveDebugInfo',
							params : {
								workOrderNO : Ext.fly('orderDetail').getAttribute('num'),
								receiptList : Ext.encode(receiptList),
								operator : Ext.util.Cookies.get("operator")
							},
							success : function(response, action) {
								window.close();
								var processReceiptStore = Ext.ComponentQuery.query('#processReceiptGrid')[0].getStore();
								processReceiptStore.reload();
								Ext.Msg.alert(Oit.msg.PROMPT, '下发成功');
							},
							failure : function(response, action) {
								Ext.MessageBox.alert(Oit.msg.ERROR, Oit.msg.wip.terminal.debugInfo.msg.error);
							}
						});
			},
			onChangeEquipStatus : function(button) {
				var me = this;
				var status = '';
				if (Ext.fly('equipInfo').getAttribute('status') == 'IN_DEBUG') {
					status = '加工';
				} else {
					status = '生产';
				}
				Ext.MessageBox.confirm('确认', '确认更改设备状态?', function(btn) {
							if (btn == 'yes') {
								Ext.Msg.wait('处理中，请稍后...', '提示');
								me.getChangeEquipStatusBtn().disable();
								Ext.Ajax.request({
											waitMsg : Oit.msg.wip.terminal.debugInfo.msg.loading,
											url : 'changeEquipStatus',
											method : 'POST',
											params : {
												equipCode : button.equipCode,
												operator : Ext.util.Cookies.get("operator")
											},
											success : function(response) {
												Ext.Msg.hide();
												me.changeDebugButton();
												me.getChangeEquipStatusBtn().enable();
											},
											failure : function(response, opts) {
												me.getChangeEquipStatusBtn().enable();
												Ext.Msg.hide();
												Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.wip.terminal.debugInfo.msg.error);
											}
										});
							}
						});
			},

			changeDebugButton : function() {
				var me = this;
				var img = document.getElementById("titleImg");
				var titleHtml = document.getElementById("titleHtml");
				var changeBtn = me.getChangeEquipStatusBtn();
				var issuedParamBtn = me.getIssuedParamBtn();
				var receiveBtn = me.getReceiveBtn();
				if (Ext.fly('orderInfo').getAttribute('status') == 'IN_PROGRESS') {
					var equipInfo = Ext.fly('equipInfo');
					if (equipInfo.getAttribute('status') == 'IN_DEBUG') {
						equipInfo.set({
									'status' : 'IN_PROGRESS'
								})
						changeBtn.setText(Oit.msg.wip.terminal.btn.debug);
						issuedParamBtn.hide();
						receiveBtn.show();
						img.src = "/bsstatic/icons/IN_PROGRESS.png ";
						titleHtml.innerHTML = titleHtml.innerHTML.replace("调试", "加工中");
					} else {
						equipInfo.set({
									'status' : 'IN_DEBUG'
								});
						changeBtn.setText(Oit.msg.wip.terminal.btn.product);
						if(processCode == 'Steam-Line'){
							issuedParamBtn.hide();
						}else{
							issuedParamBtn.show();
						}
						receiveBtn.hide();
						img.src = "/bsstatic/icons/IN_DEBUG.png ";
						titleHtml.innerHTML = titleHtml.innerHTML.replace("加工中", "调试");
						titleHtml.innerHTML = titleHtml.innerHTML.replace("空闲", "调试");
					}
				} else {
				}
			},

			toChangeOrder : function(btn) {
				var me = this;
				var window = me.getChangeOrderWindow();
				var store = Ext.ComponentQuery.query('#recentOrderGrid')[0].getStore();
				store.load();
				window.show();
			},

			toProductionRecord : function(btn) {
				var me = this;
				var window = me.getProductionRecordView();
				window.show();
				Ext.getCmp("dbId").setValue("");
				Ext.getCmp("fdbId").setValue("");
				Ext.getCmp("fzgId").setValue("");
				Ext.getCmp("formId").removeAll();
			},

			toShiftRecord : function() {
				var me = this;
				var win = me.getShiftRecordWindow();
				win.show();
			},

			toTurnOverMatreial : function() {
				var me = this;
				var win = me.getTurnOverMaterialWindow();
				win.show();
			},

			onChangeOrder : function() {
				var me = this;
				var changeOrderGrid = Ext.ComponentQuery.query('#recentOrderGrid')[0];
				var records = changeOrderGrid.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
				} else if (Ext.fly('orderInfo').getAttribute('num') == records[0].get('workOrderNo')) {
					Ext.Msg.alert(Oit.msg.WARN, '请选择非当前生产单');
				} else {
					var win = new Ext.Window({
								title : '请选择切换原因',
								width : 420,
								height : 180,
								alias : 'widget.changeWorkOrderReason',
								padding : '10',
								items : [{
											xtype : 'radiogroup',
											labelAlign : 'left',
											columns : 2,
											width : 320,
											itemId : 'changeRadiogroup',
											defaults : { // 设置样式
												margin : '5 20 0 0'
												// boxLabelCls: 80 // 设置label的宽度
											},
											vertical : true,
											items : [{
														boxLabel : '材料不足',
														name : 'type',
														inputValue : '材料不足',
														checked : true
													}, {
														boxLabel : '盘具不足',
														name : 'type',
														inputValue : '盘具不足'
													}, {
														boxLabel : '质量问题',
														name : 'type',
														inputValue : '质量问题'
													}]
										}],
								buttons : [{
											text : '确定',
											handler : function() {
												var reason = this.up('window').down('radiogroup').lastValue.type;
												var userCode = Ext.util.Cookies.get("operator");
												Ext.Ajax.request({
															url : '../terminal/saveChangeWorkOrderNoReason',
															method : 'POST',
															params : {
																reason : reason,
																oldWorkOrderNo : Ext.fly('orderInfo').getAttribute('num'),
																equipCode : Ext.fly('equipInfo').getAttribute('code'),
																userCode : userCode
															},
															success : function(response) {
															}
														});
												Ext.Ajax.request({
															url : '../terminal/changeNewWorkOrderNo',
															method : 'POST',
															params : {
																oldWorkOrderNo : Ext.fly('orderInfo').getAttribute('num'),
																newWorkOrderNo : records[0].get('workOrderNo')
															},
															success : function(response) {
																location.reload(true);
															}
														});
												this.up('window').close();
											}
										}, {
											text : '返回',
											handler : function() {
												this.up('window').close();
											}
										}]
							});
					win.show();
				}
			},
			getMaterial : function() {
				var me = this;
//				var store = new Ext.data.Store({
//					fields : ['material'],
//					autoLoad:true,
//					proxy:{
//		        		type: 'rest',
//		        		url:'getMaterials?processCode='+processCode
//		        	}
//				});
//				if(processCode=="Extrusion-Single"){
//					me.getApplyMaterials({
//						title : '绝缘要料',
//						store : store,
//						equipCode : Ext.fly('equipInfo').getAttribute('code')
//					}).show();
//				}else if(processCode=="Cabling"){
//					me.getApplyMaterials({
//						title : '成缆要料',
//						store : store,
//						equipCode : Ext.fly('equipInfo').getAttribute('code')
//					}).show();
//				}else if(processCode=="shield" || processCode=="Jacket-Extrusion"){
//					me.getApplyMaterials({
//						title : '护套要料',
//						store : store,
//						equipCode : Ext.fly('equipInfo').getAttribute('code')
//					}).show();
//				}else{
//					Ext.Msg.alert(Oit.msg.WARN, "无需要料!!");
//				}
				
				var changeOrderGrid = Ext.ComponentQuery.query('#recentOrderGrid')[0];
				var records = changeOrderGrid.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
					return;
				}
				var workOrderNo = records[0].get('workOrderNo');
				Ext.Ajax.request({
							url : 'getMaterialInventory',
							method : 'POST',
							params : {
								workOrderNo : records[0].get('workOrderNo'),
								userCode : Ext.util.Cookies.get('operator')
							},
							success : function(response) {
								var groupMap = Ext.decode(response.responseText).list;
								var status = Ext.decode(response.responseText).MaterialStatus;
								var userName = Ext.decode(response.responseText).userName;
								if (status != 'MAT_UN_DOWN') {
									Ext.Msg.alert(Oit.msg.WARN, '已经要料，请补料');
									return;
								}
								if (groupMap.length > 0) {
									var win = me.getGetMaterialWindow({
												workOrderNo : workOrderNo,
												userName : userName,
												userCode : Ext.util.Cookies.get('operator'),
												groupMap : groupMap
											});
									win.show();
								} else {
									Ext.Msg.alert(Oit.msg.WARN, '无需要料');
								}
							}
						});

			},
			supplementMaterial : function() {
				var me = this;
				var changeOrderGrid = Ext.ComponentQuery.query('#recentOrderGrid')[0];
				var records = changeOrderGrid.getSelectionModel().getSelection();
				var matStatusText = records[0].get('matStatusText');
				console.log(matStatusText)
				if (records.length == 0) {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
					return;
				} else if (matStatusText == '未要料') {
					Ext.Msg.alert(Oit.msg.WARN, '无法补料，请先要料。');
					return;
				}
				Ext.Ajax.request({
							url : 'getMaterialInventory',
							method : 'POST',
							params : {
								workOrderNo : records[0].get('workOrderNo'),
								userCode : Ext.util.Cookies.get('operator')
							},
							success : function(response) {
								var groupMap = Ext.decode(response.responseText).list;
								var userName = Ext.decode(response.responseText).userName;
								if (groupMap.length > 0) {
									var win = me.getSupplementMaterialWindow({
												workOrderNo : records[0].get('workOrderNo'),
												userName : userName,
												userCode : Ext.util.Cookies.get('operator'),
												groupMap : groupMap
											});
									win.show();
								} else {
									Ext.Msg.alert(Oit.msg.WARN, '无需补料');
								}
							}
						});

			},

			feedFormHide : function(flg) {
				var me = this;
				var receiveBtn = me.getReceiveBtn();
				if (flg) {
					receiveBtn.show();
				} else {
					receiveBtn.hide();
				}
			},
			submitMaterial : function(btn) {
				var me = this;
				var grid = me.getMaterialGrid();
				var store = grid.getStore();
				var barCode = Ext.ComponentQuery.query('#feedText')[0].getValue();

				if (!Ext.ComponentQuery.query('#feedOrderTaskId')[0].getValue()) {
					Ext.MessageBox.alert(Oit.msg.PROMPT, "请确认需要生产的任务!");
					return;
				}

				var form = grid.getDockedItems('toolbar[dock="top"]')[0].items.items[0];
				if (!barCode) {
					Ext.MessageBox.alert(Oit.msg.PROMPT, "请输入批次号!");
					return;
				}
				var workOrderNO = me.getWorkOrderNum();
				form.updateRecord();

				// 先要判断该物料是否已投入barCode
				// 后台判断是否已投入
				btn = me.getFeedBtn();
				Ext.Ajax.request({
							url : 'checkProductPutIn',
							method : 'POST',
							params : {
								workOrderNo : workOrderNO,
								barCode : barCode
							},
							success : function(response) { // success 表示未投入
								// 投料 若已投入则提示用户是否取消 若未投入添加确认信息投入
								btn.enable();
								me.feedMaterialOk(form, workOrderNO, store);
							},
							failure : function(response) { // 表示已投入，提示用户是否取消
								btn.enable();
								var record = Ext.decode(response.responseText);
								if ('MATERIALS' == record.matType) {
									Ext.MessageBox.confirm('确认', '是否继续投料还是取消投料,是:确定投料,否:取消投料?', function(btn) {
												if (btn == 'yes') {
													me.feedMaterialOk(form, workOrderNO, store);
												} else {
													me.cancelFeedMaterial(workOrderNO, barCode, store);
												}
											});
								} else {
									// 取消投料，提示
									Ext.MessageBox.confirm('确认', '确认取消投料?', function(btn) {
												if (btn == 'yes') {
													me.cancelFeedMaterial(workOrderNO, barCode, store);
												}
												Ext.ComponentQuery.query('#feedText')[0].setValue('');
												Ext.ComponentQuery.query('#feedText')[0].focus(false, 100);
											});
								}
							}
						})
			},

			cancelFeedMaterial : function(workOrderNo, barCode, store) {
				var me = this;
				Ext.Ajax.request({
							url : 'cancelFeedMaterial',
							method : 'POST',
							params : {
								workOrderNo : workOrderNo,
								barCode : barCode
							},
							success : function(response) {
								store.on({
											load : {
												fn : function(store, records, successful, eOpts) {
													// 如果所有物料投放完毕,显示接受按钮
													var flg = true;
													Ext.each(records, function(record) {
																if (!record.get("hasPutIn")) {
																	// 如果有一个物料没有投放,不显示接按钮
																	flg = false;
																}
															});
													// me.feedFormHide(flg);
												},
												scope : this,
												single : true
											}

										});
								store.reload();
								Ext.ComponentQuery.query('#feedText')[0].setValue('');
								Ext.ComponentQuery.query('#feedText')[0].focus(false, 100);
							}
						})
			},

			feedMaterialOk : function(form, workOrderNO, store) {
				var me = this;
				form.submit({
							waitMsg : Oit.msg.wip.terminal.debugInfo.msg.loading,
							url : "feedMaterial/" + workOrderNO + '/' + Ext.util.Cookies.get("operator") + '/',
							method : 'POST',
							success : function(form, action) {
								store.on({
											load : {
												fn : function(store, records, successful, eOpts) {
													// 如果所有物料投放完毕,显示接受按钮
													var flg = true;
													Ext.each(records, function(record) {
																if (!record.get("hasPutIn")) {
																	// 如果有一个物料没有投放,不显示接按钮
																	flg = false;
																}
															});
													// me.feedFormHide(flg);
												},
												scope : this,
												single : true
											}

										});
								store.reload();
								Ext.ComponentQuery.query('#feedText')[0].setValue('');
								Ext.ComponentQuery.query('#feedText')[0].focus(false, 100);
							},
							failure : function(form, action) {
								var result = Ext.decode(action.response.responseText);
								Ext.MessageBox.show({
											title : Oit.msg.PROMPT,
											msg : '<span style="font-size:20px;line-height: 24px;color:red;font-weight: bold;">'
													+ result.msg + '</span>',
											buttons : Ext.Msg.YES,
											icon : Ext.Msg.ERROR
										});
								Ext.ComponentQuery.query('#feedText')[0].setValue('');
								Ext.ComponentQuery.query('#feedText')[0].focus(false, 100);
							}
						});
			},
			
			//查看投料信息******************************2016-4-6
			seeMaterialInfo : function(btn){
				me = this;
				var barCode = Ext.ComponentQuery.query('#feedText')[0].getValue();
				if(!barCode) {
					Ext.MessageBox.alert(Oit.msg.PROMPT, "请输入半成品生产单号!");
					return;
				}
				btn = me.getSeeBtn();
				Ext.Ajax.request({
					url : 'isWorkOrderNo',
					method : 'GET',
					params : {
						barCode : barCode
					},
					success : function(response) { // success 表示该字段
						//为生产单号，可以进行正常查询
						var record = Ext.decode(response.responseText);
						if(record.length == 0){
							Ext.Msg.alert('提醒','您输入的不是有效半成品编码');
						}else{
							var woNoGrid = me.getWorkOrderNoInfoGrid();
							var woNoInfoStore = woNoGrid.getStore();
							woNoInfoStore.getProxy().url = "/bsmes/wip/terminal/queryWorkOrderNoInfo/" + barCode;
							woNoInfoStore.reload();
							var win = Ext.create(Ext.window.Window,{
								title : '半成品生产单信息',
								width : document.body.scrollWidth - 200,
								height : document.body.scrollHeight - 200,
							    layout: 'fit',
							    items: {
							        xtype: me.workOrderNoInfoGrid,            
							        store: woNoInfoStore
							    }
							});
							var grid = Ext.ComponentQuery.query('#workOrderNoInfoGrid')[0];
							if(woNoInfoStore.nextProcessWoNo == '下道工艺尚未下发'){
								grid.buttons[1].hide();
							}
							win.show();
						}
					},
					failure : function(response){
						Ext.Msg.alert('提醒','服务器未响应');
					}
				});
			},

			// 产品QA检测手工录入
			openEntryWindow : function(btn) {
				var me = this;
				me.getProcessQAEntryView().show();
				me.getProcessQAEntryGrid().getStore().load({
							params : {
								workOrderNo : btn.woNo,
								equipCode : btn.equipCode,
								type : 'IN_CHECK'
							}
						});
			},
			saveProcessQAValue : function() {
				var me = this;
				var form = me.getProcessQAEntrySearchForm();
				var store = me.getProcessQAEntryGrid().getStore();

				// 将原来的change事件移至提交处
				var itemsLen = me.getProcessQAEntryGrid().getStore().data.items.length;

				if (form.getValues().type == 'IN_CHECK') {
					var hasNoPass = false;
					for (var rowIndex = 0; rowIndex < itemsLen; rowIndex++) {
						var radio = document.getElementsByName("qcResult" + rowIndex);
						if (!radio[0].checked) {
							Ext.Msg.alert(Oit.msg.PROMPT, me.formMessage("上车检所有检测项都通过才能保存,请确认!"));
							return;
						}
					}
				}

				for (var rowIndex = 0; rowIndex < itemsLen; rowIndex++) {
					var radio = document.getElementsByName("qcResult" + rowIndex);
					for (var i = 0; i < radio.length; i++) {
						if (radio[i].checked) {
							store.getAt(rowIndex).set("qcResult", radio[i].value);
						}
					}
				}

				var dataArray = new Array();
				Ext.each(store.getUpdatedRecords(), function(record, i) {
							var data = record.getData();
							data.type = form.getValues().type;
							dataArray.push(data);
						});

				Ext.Msg.wait('处理中，请稍后...', '提示');
				Ext.Ajax.request({
							url : '/bsmes/wip/terminal/saveProcessQCValue',
							method : 'POST',
							params : {
								'jsonText' : Ext.encode(dataArray),
								'coilNum' : -1,
								'equipCode' : Ext.fly('equipInfo').getAttribute('code')
							},
							success : function(response) {
								Ext.Msg.alert(Oit.msg.PROMPT, "保存成功");
								me.getProcessQAEntryView().close();
								var dailyCheckStores = Ext.getCmp('dailyCheckGridId').getStore();
								dailyCheckStores.reload();
								Ext.Msg.hide(); // 隐藏进度条
							},
							failure : function(response, options) {
								var rjson = Ext.decode(response.responseText);
								Ext.Msg.alert(Oit.msg.wip.title.fail, rjson.message);
								me.close();
								Ext.Msg.hide(); // 隐藏进度条
							}
						});
			},

			// 触发设备警报窗弹出
			openTriggerEquipAlarmWindow : function() {
				var me = this;
				var window = me.getTriggerEquipAlarmWin();
				window.down('form').form.findField('equipCode').setValue(Ext.fly('equipInfo').getAttribute('code'));
				window.show();
			},
			// 处理设备警报列表窗弹出
			openHandleEquipAlarmWindow : function(isDone) {
				var me = this;
				var window = me.getHandleEquipAlarmWin();
				window.down('hform').form.findField('equipCode').setValue(Ext.fly('equipInfo').getAttribute('code'));
				var box = window.down('checkboxgroup');
				if(isDone){
					box.setValue('UNCOMPLETED');
				}else{
					box.setValue('PENDING');
				}
				window.show();
			},
			// 处理设备警报列表窗->处理验证窗弹出
			openHandleEquipValidWindow : function() {
				var me = this;
				var selection = me.getHandleEquipAlarmGrid().getSelectionModel().getSelection();
				if (selection && selection.length > 0) {
					var record = selection[0];
					if (record.getData().eventStatus == 'UNCOMPLETED') {
						// 创建验证窗口
						var window = Ext.create('bsmes.view.HandleEquipValidWindow', {
									// 弹出后刷新grid的参数为：grid中的查询参数
									refreshParams : {
										eventStatusFindParam : Ext.getCmp("eventStatusComb").getValue()
									}
								})
						var equipCode = Ext.fly('equipInfo').getAttribute('code');
						// 验证窗口部分值赋值
						window.down('form').form.findField('id').setValue(record.getData().id);
						window.down('form').form.findField('equipCode').setValue(equipCode);
						window.show(); // 弹出
						window.down('form').form.findField('userCode').focus(false, 100);
					}else{
						Ext.Msg.alert(Oit.msg.WARN, Oit.error.handled+'请点维修完成!');
						return;
					}
					
				} else {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
					return;
				}
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
				if (Ext.getCmp('needMiddleCheck').getValue() == '1' && Ext.fly('equipInfo').getAttribute('status') == 'IN_PROGRESS') {
					var me = this;
					var win = me.getProcessQAEntryView();
					win.show();
					me.getProcessQAEntryGrid().getStore().load({
								params : {
									workOrderNo : Ext.fly('orderInfo').getAttribute('num'),
									type : 'MIDDLE_CHECK'
								}
							});
					win.down('form').form.findField('type').setValue('MIDDLE_CHECK');
					win.down('form').items.items[0].setReadOnly(true);
				}
			},

			userCompetenceVerification : function() {
				var me = this;
				var changeOrderGrid = Ext.ComponentQuery.query('#recentOrderGrid')[0];
				var records = changeOrderGrid.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
				} else {
					var record = records[0];
					// if(record.get('status') == '生产中'){
					// Ext.Msg.alert(Oit.msg.WARN, '生产中的生产单不允许调整设备');
					// return;
					// }
					var window = me.getChangeEquipUserCVWindow();
					var form = window.down('form').getForm();
					var equipCode = Ext.fly('equipInfo').getAttribute('code');
					form.findField('workOrderNo').setValue(record.get('workOrderNo'));
					form.findField('equipCode').setValue(equipCode);
					window.show();
				}
			},

			refresh : function() {
			},

			openSparkRepairDetailWindow : function(btn) {
				var me = this;
				var window = me.getSparkRepairListWindow();
				window.down('grid').getStore().load({
							params : {
								statusFindParam : 'UNCOMPLETED',
								equipCode : btn.equipCode
							}
						});
				window.show(); // 弹出
			},

			openMatPlan : function() {
				var me = this;
				var window = me.getMatPlanView();
				window.show();
			},

			openRepairTypeWindow : function() {
				var me = this;
				var grid = me.getSparkRepairListWindow().down('grid');
				var selection = grid.getSelectionModel().getSelection();
				if (selection && selection.length > 0) {
					var record = selection[0];
					if (record.getData().status == 'COMPLETED' || record.getData().status == 'RESPONDED') {
						Ext.Msg.alert(Oit.msg.WARN, Oit.error.handled);
						return;
					}

					var record = selection[0];
					var window = me.getRepairTypeWindow();
					window.down('form').form.findField('id').setValue(record.getData().id);
					window.show(); // 弹出
				} else {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
					return;
				}
			},

			submitRepairType : function() {
				var me = this;
				var window = me.getRepairTypeWindow();
				var form = window.down('form').getForm();
				if (form.isValid()) {
					Ext.Msg.wait('处理中，请稍后...', '提示');
					var id = Ext.ComponentQuery.query('repairTypeWindow #id')[0].getValue();
					var repairType = Ext.ComponentQuery.query('repairTypeWindow #repairTypeComb')[0].getValue();
					Ext.Ajax.request({
								url : form.url,
								params : {
									id : id,
									repairType : repairType
								},
								// ajax提交有响应都是返回success，没有响应或者错误才会返回failure，返回字符串（json格式需转换）
								success : function(response) {
									Ext.Msg.hide(); // 隐藏进度条
									window.close();
									me.getSparkRepairListWindow().down('grid').getStore().load();
								},
								failure : function(response, options) {
									Ext.Msg.hide(); // 隐藏进度条
									var rjson = Ext.decode(response.responseText);
									Ext.Msg.alert(Oit.msg.wip.title.fail, rjson.message);
									window.close();
								}
							})
				}

			},

			saveValue : function() {
				var me = this;
				var form = Ext.ComponentQuery.query('#baseInfoForm')[0];
				if (form.isValid()) {
					form.submit({
								success : function(form, action) {
									var result = Ext.decode(action.response.responseText);
									Ext.Msg.alert(Oit.msg.PROMPT, result.message);
								},
								failure : function(response, action) {
									Ext.Msg.alert(Oit.msg.PROMPT, '保存失败！');
								}
							});
				}
			},

			reportChangeSatus : function() {
				var me = this;
				Ext.Ajax.request({
							waitMsg : Oit.msg.wip.terminal.debugInfo.msg.loading,
							url : 'changeEquipStatus',
							method : 'POST',
							params : {
								workOrderNO : me.getWorkOrderNum(),
								operator : Ext.util.Cookies.get("operator")
							},
							success : function(response) {
								var img = document.getElementById("titleImg");
								var titleHtml = document.getElementById("titleHtml");
								var changeBtn = me.getChangeEquipStatusBtn();
								var issuedParamBtn = me.getIssuedParamBtn();
								var equipInfo = Ext.fly('equipInfo');
								equipInfo.set({
											'status' : 'IN_DEBUG'
										});
								changeBtn.setText(Oit.msg.wip.terminal.btn.product);
								issuedParamBtn.show();
								// qADataEntry.hide();
								me.getReceiveBtn().hide();
								img.src = "/bsstatic/icons/IN_DEBUG.png ";
								titleHtml.innerHTML = titleHtml.innerHTML.replace("加工中", "调试");
							},
							failure : function(response, opts) {
								me.getChangeEquipStatusBtn().enable();
								Ext.Msg.hide();
								Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.wip.terminal.debugInfo.msg.error);
							}
						});
			}
		});