Ext.define('bsmes.controller.TerminalController', {
			extend : 'Ext.app.Controller',
			requires : ['Oit.app.data.GridStore', 'Oit.app.controller.GridController'],
			reportQAEntryWindow : 'reportQAEntryWindow',
			triggerEquipAlarmWin : 'triggerEquipAlarmWindow',
			handleEquipAlarmWin : 'handleEquipAlarmWindow',
			handleEquipValidWin : 'handleEquipValidWindow',
			checkGridWin : 'checkGridWindow',
			reportView : 'reportView',
			views : ['ReportQAEntryWindow', 'CheckGridWindow', 'ReportView', 'TriggerEquipAlarmWindow', 'HandleEquipAlarmWindow',
					'HandleEquipValidWindow','FinishRepairValidWindow','ProductReportDetailsView','ProductReportDetailGrid'],
			stores : ['ProcessQAEntryStore', 'EquipAlarmListStore', 'CheckGridStore','ProductReportStore'],
			equipInfos : new Ext.util.HashMap(),
			constructor : function() {
				var me = this;
				// 初始化refs
				me.refs = me.refs || [];

				me.refs.push({
							ref : 'reportView',
							selector : 'reportView',
							autoCreate : true,
							xtype : 'reportView'
						});

				me.refs.push({
							ref : 'reportGrid',
							selector : me.reportView + ' reportGrid'
						});

				me.refs.push({
							ref : 'reportHTJYGrid',
							selector : me.reportView + ' reportHTJYGrid'
						});

				me.refs.push({
							ref : 'reportDetailView',
							selector : 'reportDetailView',
							autoCreate : true,
							xtype : 'reportDetailView'
						});
				
				
				me.refs.push({
							ref : 'productReportDetailsView',
							selector : 'productReportDetailsView',
							autoCreate : true,
							xtype : 'productReportDetailsView'
						});
				
				me.refs.push({
							ref : 'productReportDetailGrid',
							selector : 'productReportDetailGrid',
							autoCreate : true,
							xtype : 'productReportDetailGrid'
						});
				
				me.refs.push({
							ref : 'shiftRecordViews',
							selector : 'shiftRecordViews',
							autoCreate : true,
							xtype : 'shiftRecordViews'
						});	
						
				me.refs.push({
							ref : 'shiftRecordWindows',
							selector : 'shiftRecordWindows',
							autoCreate : true,
							xtype : 'shiftRecordWindows'
						});	
				
				
				me.refs.push({
							ref : 'reportForm',
							selector : '#reportForm'
						});

				//点检
				me.refs.push({
							ref : 'checkGridWin',
							selector : me.checkGridWin,
							autoCreate : true,
							xtype : me.checkGridWin
						});

				me.refs.push({
							ref : 'checkGrid',
							selector : me.checkGridWin + ' checkGrid'
						});


				//产品QA检测手工录入 window
				me.refs.push({
							ref : 'reportQAEntryWindow',
							selector : me.reportQAEntryWindow,
							autoCreate : true,
							xtype : me.reportQAEntryWindow
						});

				me.refs.push({
							ref : 'reportQAEntryList',
							selector : me.reportQAEntryWindow + ' reportQAEntryList'
						});

				// 注册get方法：触发设备警报窗
				me.refs.push({
							ref : 'triggerEquipAlarmWin',
							selector : me.triggerEquipAlarmWin,
							autoCreate : true,
							xtype : me.triggerEquipAlarmWin
						});

				// 注册get方法：处理设备警报窗
				me.refs.push({
							ref : 'handleEquipAlarmWin',
							selector : me.handleEquipAlarmWin,
							autoCreate : true,
							xtype : me.handleEquipAlarmWin
						});

				me.refs.push({
							ref : 'handleEquipValidWin',
							selector : me.handleEquipValidWin,
							autoCreate : true,
							xtype : me.handleEquipValidWin
						});

				me.refs.push({
							ref : 'handleEquipAlarmGrid',
							selector : me.handleEquipAlarmWin + " handleEquipAlarmGrid"
						});
				
				me.refs.push({
					ref : 'finishRepairValidWindow',
					selector : me.finishRepairValidWindow,
					autoCreate : true,
					xtype : me.finishRepairValidWindow
				});

				me.callParent(arguments);
				// 定时刷新:1、多终端时TerminalMultipleController中的refresh; 2、单终端时TerminalSingleController中的refresh()。
				setInterval(function() {
							me.refresh.apply(me);
						}, 30000);
				
				var interval = Ext.fly('interval').getAttribute('value');
				setInterval(function() {
							me.promptMiddleCheck.apply(me);
						}, interval);
			},
			init : function() {
				var me = this;
				me.control(me.view + ' button[itemId=sign]', {
							click : function() {
								var model = Ext.create('bsmes.model.CreditCard');
								var view = me.getSignView();
								var form = me.getSignViewForm();
								form.loadRecord(model);
								view.show();
								form.getForm().findField('userCode').focus(false, 100);
							}
						});

				me.control(me.view + ' button[itemId=closewindow]', {
							click : function() {
								Ext.MessageBox.confirm('确认', '确认退出?', function(btn) {
											if (btn == 'yes') {
												top.close();

												// 打开firefox,在地址栏输入about:config
												// 找到dom.allow_scripts_to_close_windows
											}
										});
							}
						});

				me.control(me.checkGridWin + ' button[itemId=complete]', {
							click : me.dailyCheckComplete
						});

				me.control('reportDetailView button[itemId=print]', {
							click : function() {
								var reportGrid = Ext.getCmp('reportDetailGrid');
								var data = reportGrid.getSelectionModel().getSelection();
								if (data.length == 0) {
									Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.msg.selectOnReport);
									return null;
								}
								me.printBarCode(data[0].get('id'));
							}
						});

				me.control(me.signView + ' button[itemId=ok]', {
							click : function(button) {
								me.useRefCard();
							}
						});

				me.control('reportView button[itemId=cancel]', {
							click : function() {
								me.getReportView().close();
							}
						});

				//报工确认提交
				me.control('reportView button[itemId=ok]', {
							click : me.reportSubmitValidate
						});

				//废弃
				me.control('reportView button[itemId=print]', {
							click : function() {
								var me = this;
								var data = null;
								var processCode = Ext.fly("processInfo").getAttribute('code');
								if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling'
										|| processCode == 'Twisting' || processCode == 'Respool' || processCode == 'Steam-Line'
											|| processCode == 'Extrusion-Single' || processCode == 'Jacket-Extrusion') {
									var reportGrid = me.getReportGrid();
									data = reportGrid.getSelectionModel().getSelection();
								} else {
									var reportGrid = me.getReportHTJYGrid();
									data = reportGrid.getSelectionModel().getSelection();
								}

								if (data.length == 0) {
									Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.msg.selectOnReport);
									return null;
								}
								var reportForm = Ext.ComponentQuery.query('#reportForm')[0];
								var formRecord = reportForm.getRecord();

								var resultMsg = '';
								Ext.Ajax.request({
											url : '/bsmes/wip/terminal/checkExistsInputQcValue',
											async : false,
											method : 'GET',
											params : {
												woNo : formRecord.get('workOrderNo'),
												equipCode : formRecord.get('equipCode'),
												coilNum : data[0].get('coilNum')
											},
											success : function(response) {
												var result = Ext.decode(response.responseText);
												resultMsg = result.msg;
											}
										});

								if (resultMsg) {
									Ext.Msg.alert(Oit.msg.WARN, resultMsg);
									return;
								}
								me.printBarCode(data[0].get('id'));
							}
						});

				// 报工界面：完成生产单按钮
				me.control('reportView button[itemId=finishWorkOrder]', {
							click : me.checkAndFinishWorkOrder
						});

				me.control('reportView button[itemId=QADataEntry]', {
							click : function() {
//								var processCode = Ext.fly("processInfo").getAttribute('code');
//								if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling'
//										|| processCode == 'Twisting' || processCode == 'Respool' || processCode == 'Steam-Line'
//											|| processCode == 'Extrusion-Single' || processCode == 'Jacket-Extrusion') {
//									var data = me.getReportGrid().getSelectionModel().getSelection();
//									if (data.length == 0) {
//										Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.msg.selectOnReport);
//										return null;
//									}
//								} else {
//									var data = me.getReportHTJYGrid().getSelectionModel().getSelection();
//									if (data.length == 0) {
//										Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.msg.selectOnReport);
//										return null;
//									}
//								}
								me.openReportEntryWindow();
							}
						});

				me.control('reportQAEntryWindow button[itemId=ok]', {
							click : function() {
								var processCode = Ext.fly("processInfo").getAttribute('code');
								if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling'
										|| processCode == 'Twisting' || processCode == 'Respool' || processCode == 'Steam-Line'
											|| processCode == 'Extrusion-Single' || processCode == 'Jacket-Extrusion') {
									var data = me.getReportGrid().getSelectionModel().getSelection();
									me.saveReportQAValue(data[0]);
								} else {
									var data = me.getReportHTJYGrid().getSelectionModel().getSelection();
									me.saveReportQAValue(data[0]);
								}
							}
						});

				me.control('reportView button[itemId=save]', {
							click : function() {
								var grid = me.getReportView().query('grid')[0];
								var store = grid.getStore();
								var success = true;
								store.each(function(record) {
											var wasteLength = record.get('wasteLength');
											if (wasteLength < 0) {
												Ext.Msg.alert(Oit.msg.WARN, me.formMessage(Oit.msg.wip.terminal.error.report));
												success = false;
											}
										});
								if (success) {
									store.updateAll();
									var reportForm = me.getReportForm();
									var equipInfo = reportForm.getRecord();
									var remainQLength = equipInfo.get('remainQLength');
									var equipCode = equipInfo.get('equipCode');
									me.getReportView().close();
									if (remainQLength == 0) {
										Ext.MessageBox.confirm('Confirm', Oit.msg.wip.terminal.finishWork, function(btn) {
													if (btn == 'yes') {
														Ext.Ajax.request({
																	url : '/bsmes/wip/terminal/' + equipCode + '/finish',
																	success : function() {
																		location.reload(true);
																	}
																});
													}
												});
									}
								}
							}
						});
			},

			checkExistsInPutQc : function() {
				var me = this;
				var data = null;
				var processCode = Ext.fly("processInfo").getAttribute('code');
				if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling' || processCode == 'Twisting'
						|| processCode == 'Respool' || processCode == 'Steam-Line' || processCode == 'Extrusion-Single'
							|| processCode == 'Jacket-Extrusion') {
					var reportGrid = me.getReportGrid();
					data = reportGrid.getSelectionModel().getSelection();
				} else {
					var reportGrid = me.getReportHTJYGrid();
					data = reportGrid.getSelectionModel().getSelection();
				}
				if (data.length == 0) {
					Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.msg.selectOnReport);
					return null;
				}
				var reportForm = Ext.ComponentQuery.query('#reportForm')[0];
				var formRecord = reportForm.getRecord();

				var resultMsg = '';
				Ext.Ajax.request({
							url : '/bsmes/wip/terminal/checkExistsInputQcValue',
							async : false,
							method : 'GET',
							params : {
								woNo : formRecord.get('workOrderNo'),
								equipCode : formRecord.get('equipCode'),
								coilNum : data[0].get('coilNum')
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								resultMsg = result.msg;
							}
						});

				if (resultMsg) {
					Ext.Msg.alert(Oit.msg.WARN, resultMsg);
					return;
				}
				me.printBarCode(data[0].get('id'));
			},

			printBarCode : function(reportId) {
				var me = this;

				Ext.Ajax.request({
							url : '/bsmes/wip/terminal/print',
							method : 'POST',
							async : false,
							params : {
								reportId : reportId
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								var report = result.report;

								var LODOP = getLodop();

								var top = 10;
								var left = 10;
								var printWidth = 355;
								var printHeight = 535;
								var trHeight = 20;
								var topSuf = 5;
								var leftSuf = 5;
								LODOP.PRINT_INIT("报工条码打印");

								LODOP.SET_PRINT_PAGESIZE(0, 1040, 807, "BC");
								LODOP.SET_PRINT_STYLE("FontSize", 10);

								LODOP.ADD_PRINT_LINE(top, left, 5, printWidth + left, 0, 1);

								//设置BarCode
								top = top + 10; //top = 20
								LODOP.ADD_PRINT_BARCODE(25, 30, 330, 60, "128Auto", report.serialNum);
								//print
								top = top + 70; //top = 80

								//top = top + trHeight; //top = 130
								LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
								LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "打印时间:" + report.printTime);

								top = top + trHeight;
								LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
								LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "合同号:" + report.contractNo);

								top = top + trHeight; //top = 130
								LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
								LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "工序:" + report.processName);

								if (report.processName == "火花配套") {
									top = top + trHeight; //top = 130
									LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
									LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "负责人:" + report.userName);

									top = top + trHeight;
									LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
									LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "型号规格:" + report.custType);

									top = top + trHeight; //top = 130
									LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
									LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "绝缘材料:" + report.material);
								}

								if (typeof(report.color) != 'undefined') {
									top = top + trHeight;
									LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
									LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 40, "颜色:" + report.color);
								}

								top = top + trHeight + 20;
								LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
								LODOP.ADD_PRINT_LINE(top, 210, top, 210, 0, 1);
								LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 150, 20, "定制区:" + report.locationName);
								if (report.processName == "火花配套") {
									LODOP.ADD_PRINT_LINE(top, 210, top, 210, 0, 1);
									LODOP.ADD_PRINT_TEXT(top + topSuf, 110, 150, 20, "导体结构:" + report.conductorStruct);
								}
								LODOP.ADD_PRINT_TEXT(top + topSuf, 240, 150, 20, "投产长度:" + report.reportLength + "M");

								top = top + trHeight;
								LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
								if (report.processName == "火花配套") {
									LODOP.ADD_PRINT_LINE(top - trHeight, 100, top, 100, 0, 1);
								}
								LODOP.ADD_PRINT_LINE(top - trHeight, 235, top, 235, 0, 1);
								LODOP.ADD_PRINT_LINE(10, 10, top, 10, 0, 1);
								LODOP.ADD_PRINT_LINE(10, printWidth + left, top, printWidth + left, 0, 1);

								if (LODOP.SET_PRINT_COPIES(report.printNum)) {
									LODOP.PRINT();
								}

							},
							failure : function(response) {

							}
						});
			},
			useRefCard : function() {
				var me = this;
				var form = me.getSignViewForm();
				var values = Ext.encode(form.getValues());
				var userCodeField = form.items.items[1];
				if (form.isValid()) {
					if (userCodeField.validateOnChange) {
						Ext.Ajax.request({
									url : '/bsmes/wip/terminal/employeeCreditCard',
									method : 'GET',
									params : {
										"jsonText" : Ext.encode(form.getValues())
									},
									success : function(response) {
										var data = Ext.decode(response.responseText);
										var status = data.status;
										var usedEquipCode = data.usedEquipCode;
										var usedUserName = data.usedUserName;
										console.log(usedEquipCode);
										if (data.success == false) {
											Ext.Msg.alert(Oit.msg.PROMPT, data.message);
										} else {
											me.getSignView().close();
											var workUsers = data.result;
											var userViewObject = Ext.ComponentQuery.query('#loginUserList')[0];
											if (status == "0") {
												Ext.Msg.alert("提示", "用户已被冻结");
											}
											if (usedEquipCode != null && usedEquipCode != "") {
												Ext.Msg.alert("提示", "设备(" + usedEquipCode + ")已被[" + usedUserName +  "]使用，请联系班组长或值班为该挡班刷下班卡");
												return;
											}
											userViewObject.removeAll();
											if (workUsers != null && workUsers != '') {
												var onUserViewArray = new Array();
												var array = workUsers.split(",");
												console.log(workUsers);
												Ext.Array.each(array, function(userName, i) {
															onUserViewArray.push({
																		xtype : 'label',
																		text : userName,
																		margin : '10 10 0 20'
																	});
														});
												userViewObject.add(onUserViewArray);
											}
										}
										var exceptionType = data.map.exceptionType;
										/*var onTime = new Date(data.map.OnTime)
										var offTime = new Date(data.map.OffTime)*/
										if(exceptionType=='OFF_WORK'){
											var window = me.getProductReportDetailsView();
											var store = window.down('grid').getStore();
											store.load({
												params : {
													'onTime' : data.map.OnTime,
													'offTime' : data.map.OffTime,
													'equipCodes' : data.map.equipeCodes,
													'userCode' : data.map.userCode
												}
											})
											window.show();
										}
									}
								});
					} else {
						Ext.Msg.alert(Oit.msg.PROMPT, me.formMessage(Oit.msg.wip.terminal.error.EmployeeCreditCardUserCodeNotFound));
					}
				} else {
					Ext.Msg.alert(Oit.msg.PROMPT, me.formMessage("请选择今天负责的设备"));
				}
			},

			dailyCheckComplete : function() {
				Ext.Msg.wait('处理中，请稍后...', '提示');
				var me = this;
				var window = me.getCheckGridWin();
				var store = me.getCheckGrid().getStore();
				// 将原来的change事件移至提交处
				for (var rowIndex = 0; rowIndex < store.totalCount; rowIndex++) {
					var radio = $('input[name="isPassed' + rowIndex + '"]').each(function(n, d) {
								if ($(d).prop('checked')) {
									store.getAt(rowIndex).set("isPassed", $(d).val());
								}
							});
				}
				var checkGridStore = store.getUpdatedRecords();
				var dataArray = new Array();
				var total = 0;
				Ext.each(checkGridStore, function(record, i) {
							var data = record.getData();
							dataArray.push(data);
							total = total + 1;
						});
				if (store.totalCount > total) {
					Ext.Msg.alert('提示', me.formMessage('点检未完成'));
					$("input[type='radio'], input[type='checkbox']").ionCheckRadio();
					return;
				} else {
					Ext.Ajax.request({
								url : '/bsmes/wip/terminal/saveDailyCheck',
								method : 'POST',
								params : {
									'jsonText' : Ext.encode(dataArray)
								},
								success : function(response) {
									window.close();
									var result = Ext.getCmp('dailyCheckGridId');
									if (result != null) {
										result.getStore().reload();
									}
									Ext.Msg.hide(); //隐藏进度条
								},
								failure : function(response, options) {
									var rjson = Ext.decode(response.responseText);
									Ext.Msg.alert(Oit.msg.wip.title.fail, rjson.message);
									me.close();
									Ext.Msg.hide(); //隐藏进度条
								}
							});
				}

			},

			// 报工确定提交
			reportSubmitValidate : function() {
				var me = this;
				var reportCoilNum = me.getReportGrid().getStore().getCount();
				var qcCoilNum;
				var qcListNum;
				var ifQCVali = true;
				var ifSteam = true;
				
				var processCode = Ext.fly("processInfo").getAttribute('code');
				if(processCode == 'Steam-Line'){
					ifSteam = false;
				}

				Ext.Ajax.request({
					url : '/bsmes/wip/terminal/queryProcessQcValue',
					method : 'get',
					async : false,
					params : {
						'workOrderNo' : Ext.fly('orderInfo').getAttribute('num'),
						'type' : 'OUT_CHECK',
						'equipCode' : Ext.fly('equipInfo').getAttribute('code')
					},
					success : function(response) {
						var qc = Ext.decode(response.responseText);
						qcListNum = qc['total'];
					}
				})
				
				if(qcListNum == 0){
					ifQCVali = false;
				}
				
				Ext.Ajax.request({
					url : '/bsmes/wip/terminal/queryProcessQcValueCoilNum',
					method : 'POST',
					async : false,
					params : {
						'workOrderNo' : Ext.fly('orderInfo').getAttribute('num'),
						'type' : 'OUT_CHECK',
						'equipCode' : Ext.fly('equipInfo').getAttribute('code')
					},
					success : function(response) {
						qcCoilNum = Ext.decode(response.responseText);
						if (qcCoilNum == reportCoilNum && ifQCVali && ifSteam) {
							Ext.Msg.alert(Oit.msg.WARN,'请先做下车检，才能进行报工');
							return;
						}
						var reportForm = Ext.ComponentQuery.query('#reportForm')[0];
						//				console.log(reportForm.getForm().getValues())
						reportForm.updateRecord();
						var formRecord = reportForm.getRecord();
						var tasks = Ext.ComponentQuery.query('#taskInfoGrid')[0].getStore().data.items;
						var equipCode = Ext.fly('equipInfo').getAttribute('code');
						//可报工长度
						var unFinishLen = 0;
						Ext.each(tasks, function(record) {
									if (record.get('STATUS') == 'IN_PROGRESS') {
										unFinishLen += (record.get('TASKLENGTH') - record.get('FINISHEDLENGTH'))
									}
								});

						if (unFinishLen == 0) {
							Ext.Msg.alert(Oit.msg.WARN, "请开始一个任务!");
							return;
						}
						if (!reportForm.getForm().getValues().reprotUser || reportForm.getForm().getValues().reprotUser.length == 0) {
							Ext.Msg.alert(Oit.msg.WARN, '<font color="red">请选择报工人，若无选择，请挡班刷卡登记上班！</font>'); // [挡班/副挡板/辅助工]
							return;
						}
						var currentLength = new Number(formRecord.get('currentLength')).valueOf();
						var reportLength = new Number(formRecord.get('reportLength')).valueOf();
						var remainQLength = new Number(formRecord.get('remainQLength')).valueOf();
						var processCode = Ext.fly("processInfo").getAttribute('code');
						if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling' || processCode == 'Twisting'
								|| processCode == 'Respool' || processCode == 'Steam-Line' || processCode == 'Extrusion-Single'
									|| processCode == 'Jacket-Extrusion') {
							var locationName = formRecord.get('locationName');
							if (locationName == "") {
								Ext.Msg.alert(Oit.msg.WARN, '请选择存放位置...');
								return;
							}
						}
						if(processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling' || processCode == 'Twisting'
							|| processCode == 'shield' || processCode == 'wrapping_ht' || processCode == 'Respool'){
							var disk = formRecord.get('disk');
							var diskNumber = formRecord.get('diskNumber');
							if(disk == '' || diskNumber == ''){
								Ext.Msg.alert(Oit.msg.WARN, '盘具盘号不能为空');
								return;
							}
						}
						if (remainQLength == 0) {
							Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.error.remainQLengthIsZero);
							return;
						}

						if (reportLength <= 0 || reportLength > unFinishLen + 5) {
							Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.error.reportLengthIsSoMin);
							return;
						}
						if (equipCode == '444-76' || equipCode == '444-77' || equipCode == '444-78' || equipCode == '444-79'
								|| equipCode == '444-72') {
							if (currentLength + 10 < reportLength) {
								Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.error.reportLengthErrorMsg);
								return;
							}
							Ext.MessageBox.confirm('重要提示', '因当前设备存在采集误差，允许报工长度不大于 [可报工长度 + 10（米）],请根据实际情况填写报工长度,点击‘是’继续报工,点击‘否’重新填写报工长度', function(
											btn) {
										if (btn == 'yes') {
											Ext.MessageBox.confirm('重要提示', me.formMessage('确认报工长度为' + formRecord.get('reportLength')),
													function(btn) {
														if (btn == 'yes') {
															me.reportSubmit(reportForm, formRecord, equipCode);
														}
													});
										}
									});
						} else {
							if (currentLength < reportLength) {
								Ext.Msg.alert(Oit.msg.WARN, Oit.msg.wip.terminal.error.reportLengthErrorMsg);
								return;
							}
							Ext.MessageBox.confirm('确认', me.formMessage('确认报工长度为' + formRecord.get('reportLength')), function(btn) {
										if (btn == 'yes') {
											me.reportSubmit(reportForm, formRecord, equipCode);
										}
									});
						}
					},
					failure : function(response, options) {
						var rjson = Ext.decode(response.responseText);
						Ext.Msg.alert(Oit.msg.wip.title.fail, rjson.message);
						me.close();
						Ext.Msg.hide(); //隐藏进度条
					}
				});
				
				
			},

			/**
			 * 报工确定提交
			 */
			reportSubmit : function(reportForm, record, equipCode) {
				//				console.log(record);
				//				console.log(record.get('db'));
				//				return
				var me = this;
				Ext.Msg.wait('处理中，请稍后...', '提示');
				//		Ext.getCmp('reportGridOkBtn').disable();
				Ext.Ajax.request({
							url : '/bsmes/wip/terminal/report',
							//							method : 'POST',
							//							timeout : 300000,
							params : reportForm.getForm().getValues(),
							//								{
							//								workOrderNo : record.get('workOrderNo'),
							//								reportLength : record.get('reportLength'),
							//								operator : Ext.util.Cookies.get('operator'),
							//								locationName : record.get('locationName'),
							//								equipCode : record.get('equipCode')
							//							},
							success : function(response) {
								Ext.Msg.hide(); //隐藏进度条  
								var result = Ext.decode(response.responseText);
								me.loadWOInfo(record.get('equipCode'), record, result);
								//Ext.getCmp('reportGridOkBtn').enable();
								//me.reportChangeSatus();
								Ext.ComponentQuery.query('#taskInfoGrid')[0].refresh(result.taskInfoList); //重新加载taskInfoGrid
								
								
								var processCode = Ext.fly("processInfo").getAttribute('code');
								if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling'
										|| processCode == 'Twisting' || processCode == 'Respool' || processCode == 'Steam-Line'
											|| processCode == 'Extrusion-Single' || processCode == 'Jacket-Extrusion') {
									var reportGridStore = me.getReportGrid().getStore();
									var num = 0;
									reportGridStore.load({
									    scope: this,
									    callback: function(records, operation, success) {
									        // 对象 operation 包含
									        // 所有 load 操作的详细信息
									    	num = reportGridStore.getTotalCount();
									        var reportData = reportGridStore.getAt(num-1).get('id');
									        me.printBarCode(reportData);
									    }
									});
								} else {
									var reportGridStore = me.getReportHTJYGrid().getStore();
									var num = 0;
									reportGridStore.load({
									    scope: this,
									    callback: function(records, operation, success) {
									        // 对象 operation 包含
									        // 所有 load 操作的详细信息
									    	num = reportGridStore.getTotalCount();
									        var reportData = reportGridStore.getAt(num-1).get('id');
									        me.printBarCode(reportData);
									    }
									});
								}						
							},
							failure : function(response, action) {
								Ext.Msg.hide(); //隐藏进度条  
								//Ext.getCmp('reportGridOkBtn').enable();
								var result = Ext.decode(response.responseText);
								Ext.Msg.alert(Oit.msg.PROMPT, result.message?result.message:'报工系统异常，请联系管理员！');
								me.getReportGrid().getStore().reload();
								Ext.ComponentQuery.query('#taskInfoGrid')[0].refresh(result.taskInfoList); //重新加载taskInfoGrid
							}
						});
			},
			loadWOInfo : function(equipCode, record, model) {
				var me = this;
				Ext.Msg.hide();
				var processCode = Ext.fly("processInfo").getAttribute('code');
				if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling' || processCode == 'Twisting'
						|| processCode == 'Respool' || processCode == 'Steam-Line' || processCode == 'Extrusion-Single'
							|| processCode == 'Jacket-Extrusion') {
					var reportGrid = me.getReportGrid();
					var reportStore = reportGrid.getStore();
					reportStore.reload();
				} else {
					var reportGrid = me.getReportHTJYGrid();
					var reportStore = reportGrid.getStore();
					reportStore.reload();
				}

				var planLength = Ext.getCmp('reportPlanLength').getValue();
				var rqLength = planLength - model.sumGoodLength;
				Ext.getCmp("reportRemainQLength").setValue(rqLength > 0 ? rqLength : 0);

				//设置可报工长度 报工长度
				var reportLengthField = Ext.getCmp('reportLength');
				var currentLengthField = Ext.ComponentQuery.query('#reportCurrentLength')[0];
				currentLengthField.setValue(Ext.util.Format.number((currentLengthField.getValue() - reportLengthField.getValue()),
						'0,000.00'));
				reportLengthField.setValue(0);

				var qualifiedLengthField = Ext.getCmp('qualifiedLength');
				qualifiedLengthField.setValue(model.sumGoodLength);

				// 重新更新缓存的长度信息，重新load主页的长度信息
				var mesClientManEquip = Ext.ComponentQuery.query('terminalMainView')[0].equipInfos[equipCode]; // 3.1、设备信息：放入页面缓存
				mesClientManEquip.set('remainQLength', rqLength > 0 ? rqLength : 0);
				mesClientManEquip.set('qualifiedLength', model.sumGoodLength);
				Ext.ComponentQuery.query('#baseInfoForm')[0].loadRecord(mesClientManEquip); // 3.2、重新加载：设备上的信息

				//		me.refresh();
			},
			
			//产品QA检测手工录入 TODO JINHANYUN
			openReportEntryWindow : function() {
				var me = this; 
				
				Ext.Ajax.request({
					url : '/bsmes/wip/terminal/queryProcessQcValueCoilNum',
					method : 'POST',
					async : false,
					params : {
						'workOrderNo' : Ext.fly('orderInfo').getAttribute('num'),
						'type' : 'OUT_CHECK',
						'equipCode' : Ext.fly('equipInfo').getAttribute('code')
					},
					success : function(response) {
						var qcCoilNum = Ext.decode(response.responseText);
						var reportCoilNum = me.getReportGrid().getStore().getCount();
						if(qcCoilNum > reportCoilNum){
							Ext.Msg.alert(Oit.msg.WARN,"质检次数是:" + qcCoilNum + '，报工次数是:' + reportCoilNum + ',上次质检后未进行报工，请补完上次报工');
							return;
						}
						me.getReportQAEntryWindow().show();
						var store = me.getReportQAEntryList().getStore();
						store.load({
									params : {
										workOrderNo : Ext.getCmp('reportInfoForm').getRecord().get('workOrderNo'),
										type : 'OUT_CHECK',
										equipCode : Ext.fly('equipInfo').getAttribute('code')
									}
								})
					},
					failure : function(response, options) {
						var rjson = Ext.decode(response.responseText);
						Ext.Msg.alert(Oit.msg.wip.title.fail, rjson.message);
						me.close();
						Ext.Msg.hide(); //隐藏进度条
					}
				});
				
				
			},
			saveReportQAValue : function(record) {
				Ext.Msg.wait('处理中，请稍后...', '提示');
				var me = this;
				var coilNum = me.getReportGrid().getStore().getCount() + 1;
				//var form = me.getReportQAEntrySearchForm();
				var store = me.getReportQAEntryList().getStore();


				for (var rowIndex = 0; rowIndex < store.totalCount; rowIndex++) {
					var radio = document.getElementsByName("qcResult0" + rowIndex);
					if (!radio[0].checked && !radio[1].checked) {
						Ext.Msg.alert(Oit.msg.PROMPT, me.formMessage("下车检所有检测项都检测完毕才能保存,请确认!"));
						return;
					}
				}


				// 将原来的change事件移至提交处
				for (var rowIndex = 0; rowIndex < store.totalCount; rowIndex++) {
					var radio = $('input[name="qcResult0' + rowIndex + '"]').each(function(n, d) {
								if ($(d).prop('checked')) {
									store.getAt(rowIndex).set("qcResult", $(d).val());
								}
							});
				}

				var dataArray = new Array();
				Ext.each(store.getUpdatedRecords(), function(record, i) {
							
							var data = record.getData();
							//data.type = form.getValues().type;
							data.type = 'OUT_CHECK';
							dataArray.push(data);
						});

				var reportForm = me.getReportForm();
				var formRecord = reportForm.getRecord();

				Ext.Ajax.request({
							url : '/bsmes/wip/terminal/saveProcessQCValue',
							method : 'POST',
							params : {
								'jsonText' : Ext.encode(dataArray),
								'coilNum' : coilNum,
								'equipCode' : formRecord.get('equipCode')

							},
							success : function(response) {
								me.getReportQAEntryWindow().close();
								Ext.Msg.hide(); //隐藏进度条
							},
							failure : function(response, options) {
								var rjson = Ext.decode(response.responseText);
								Ext.Msg.alert(Oit.msg.wip.title.fail, rjson.message);
								me.close();
								Ext.Msg.hide(); //隐藏进度条  
							}
						});
			},
			
			// 报工页面: 完成生产单按钮: 校验并完成
			checkAndFinishWorkOrder : function() {
				var me = this;
				// 1、获取报工页面的form信息
				var reportForm = Ext.ComponentQuery.query('#reportForm')[0];
				reportForm.updateRecord();
				var formRecord = reportForm.getRecord();
				var params = {
						workOrderNo : formRecord.get('workOrderNo'),
						equipCode : formRecord.get('equipCode'),
						operator : Ext.util.Cookies.get('operator')
					};
				// 2、验证生产单下面的订单是否都已经完成
				Ext.Msg.wait('验证中，请稍后...', '提示');
				Ext.Ajax.request({
					url : 'checkIsFinished',
					params : params,
					success : function(response) {
						Ext.Msg.hide(); // 隐藏进度条
						location.reload(true);
					},
					failure : function(response) {
						Ext.Msg.hide(); // 隐藏进度条
						var result = Ext.decode(response.responseText);
						/*var message = me.formMessage(result.message + "是否继续完成生产单?");*/
						Ext.Msg.alert(Oit.msg.PROMPT,result.message);
						/*Ext.MessageBox.confirm('确认', message, function(btn) {
							if (btn == 'yes') {
								me.finishWorkOrder(params);
							}
						});*/
					}
				});
			},
			
			// 报工页面: 完成生产单按钮: 完成生产单
			finishWorkOrder : function(params) {
				var me = this;
				Ext.Msg.wait('处理中，请稍后...', '提示');
				Ext.Ajax.request({
					url : 'finishWorkOrder',
					params : params,
					success : function(response) {
						Ext.Msg.hide(); // 隐藏进度条
						location.reload(true);
					},
					failure : function(form, action) {
						Ext.Msg.hide(); // 隐藏进度条
						Ext.Msg.alert(Oit.msg.ERROR, "数据处理异常，请联系管理员！");
					}
				});
			},

			formMessage : function(msg) {
				return '<span style="font: bold 15px/12px helvetica,arial,verdana,sans-serif;line-height: 16px;color:red;">' + msg
						+ '</span>';
			}
		});
