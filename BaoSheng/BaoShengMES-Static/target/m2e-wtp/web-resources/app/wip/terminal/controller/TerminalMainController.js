Ext.define('bsmes.controller.TerminalMainController', {
			extend : 'bsmes.controller.TerminalSingleController',
			view : 'terminalMainView',
			views : ['TerminalMainView', 'TaskInfoGrid', 'TaskInfoGridJY', 'TaskInfoDetail', 'TaskInfoGridCL', 'TaskInfoGridHT'],
			stores : ['TaskInfoStore'],
			toPrintMatList : function() {
				var me = this;
				var changeOrderGrid = Ext.ComponentQuery.query('#recentOrderGrid')[0];
				var records = changeOrderGrid.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
				} else {
					Ext.Msg.wait('开始打印，请稍后...', '提示');
					Ext.Ajax.request({
								url : '../terminal/printMatList',
								method : 'POST',
								params : {
									workOrderNo : records[0].get('workOrderNo')
								},
								success : function(response) {
									var result = Ext.decode(response.responseText);
									var printNum = result.printNum;
									var matList = result.mats;
									var processGroupRespool = result.processGroupRespool;
									var locationName = result.locationName;
									var top = 10;
									var left = 10;
									var printWidth = 355;
									var printHeight = 535;
									var trHeight = 25;
									var topSuf = 5;
									var leftSuf = 5;
									Ext.Msg.hide();
									var LODOP = getLodop();
									LODOP.PRINT_INIT("报工条码打印");
									LODOP.SET_PRINT_PAGESIZE(0, 1040, 807, "BC");
									LODOP.SET_PRINT_STYLE("FontSize", 12);
									LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);

									LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "当前生产单:" + records[0].get('workOrderNo'));
									top = top + trHeight;
									LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);

									if (processGroupRespool) {
										LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "上道生产单:" + processGroupRespool);
										top = top + trHeight;
										LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
									}

									if (locationName) {
										LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, "库位:" + locationName);
										top = top + trHeight;
										LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
									}

									Ext.each(matList, function(row) {
												LODOP.ADD_PRINT_TEXT(top + topSuf, left + leftSuf, 375, 20, row.CONTRACT_NO + "|"
																+ row.COLOR + "|" + row.TASK_LENGTH);
												top = top + trHeight;
												LODOP.ADD_PRINT_LINE(top, left, top, printWidth + left, 0, 1);
											});

									if (LODOP.SET_PRINT_COPIES(printNum)) {
										LODOP.PRINT();
									}
								},
								failure : function(response) {
									Ext.Msg.hide();
									Ext.Msg.alert('提示', me.formMessage('当前工序无半成品物料!'));
								}
							});

					// 合同号，型号 规格 长度
				}
			},

			/**
			 * 单终端时TerminalSingleController中的refresh()<br/>
			 * 
			 * @description 1、刷新订单设备信息:采集长度<br/>
			 * @description 2、刷新任务信息:任务的加工状态<br/>
			 * @description 3、刷新操作参数:数采信息<br/>
			 * @description 4、刷新设备状态:暂不执行<br/>
			 */
			refresh : function() {
				var me = this;
				var equipCode = Ext.fly('equipInfo').getAttribute('code'), workOrderNo = Ext.fly('orderInfo').getAttribute('num');
				Ext.Ajax.request({
							url : '../terminal/refreshSingle',
							method : 'GET',
							params : {
								workOrderNo : Ext.fly('orderInfo').getAttribute('num'),
								equipCode : equipCode
							},
							success : function(response) {
								var result = Ext.decode(response.responseText); // 1、请求响应转换成json格式
								var taskInfoListData = result.taskInfoList; // 2、任务列表数据
								var processQcListData = result.processQcList; // 4、操作参数/数采下发
								var mesClientManEquip = Ext.create('bsmes.model.MesClientEquipInfo', result.mesClientManEquip); // 3、mesClientManEquip: 设备上的信息，包括采集长度，剩余长度等
								Ext.ComponentQuery.query('terminalMainView')[0].equipInfos[result.mesClientManEquip.equipCode] = mesClientManEquip; // 3.1、设备信息：放入页面缓存
								Ext.ComponentQuery.query('#baseInfoForm')[0].loadRecord(mesClientManEquip); // 3.2、重新加载：设备上的信息

								if (Ext.fly('orderInfo').getAttribute('status') != 'TO_DO') {
									var taskGrid = Ext.ComponentQuery.query('#taskInfoGrid')[0]; // 任务列表
									taskGrid.refresh(taskInfoListData);
								}
								var processReceiptGrid = Ext.ComponentQuery.query('#processReceiptGrid')[0]
								processReceiptGrid.refresh(processQcListData);
							}
						});
			},
			/**
			 * 单终端时,刷新待确认的报警信息，提示操作工完成确认工作
			 */
			refreshEquipEvent : function(){
				//如果打开了处理警报界面，就不需要提醒了
				var m = Ext.ComponentQuery.query('handleEquipAlarmWindow');
				if(m.length>0){
					return;
				}
				var me = this;
				var equipCode = Ext.fly('equipInfo').getAttribute('code');
				Ext.Ajax.request({
					url : '../terminal/refreshEquipEvent',
					method : 'GET',
					params : {
						equipCode : equipCode
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
							    		 me.openHandleEquipAlarmWindow(0);
							    	 }
							     }
							});
						}
					}
				});
			}
		});