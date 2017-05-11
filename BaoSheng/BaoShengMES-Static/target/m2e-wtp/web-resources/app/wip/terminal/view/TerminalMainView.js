Ext.define('bsmes.view.TerminalMainView', {
	extend : 'Ext.container.Container',
	alias : 'widget.terminalMainView',
	layout : 'vbox',
	equipInfos : {}, // 页面设备缓存，报功用
	initComponent : function() {
		var me = this;
		var btnReportText, btnDebugText, taskInfoXtype;// 报工按钮,调试按钮
		var equipCode = Ext.fly('equipInfo').getAttribute('code');// 设备编号
		var section = Ext.fly("processInfo").getAttribute('section');// 工段
		var processCode = Ext.fly("processInfo").getAttribute('code');// 工序代码
		var isPrint=true;
		if(processCode=='Respool'||processCode=='Jacket-Extrusion'){
			isPrint=false;
		}
		var isReceived = true;
		if (Ext.fly('orderInfo').getAttribute('status') == 'TO_DO') {
			btnReportText = Oit.msg.wip.terminal.btn.receive;
			isReceived = false;
		} else {
			btnReportText = Oit.msg.wip.terminal.btn.report;
		}
		var btnBack = {
			xtype : 'button',
			itemId : 'back',
			margin : '0 30 0 0',
			text : Oit.msg.wip.terminal.btn.back,
			href : '../terminal.action',
			hrefTarget : '_self'
		};
		// 判断接受按钮是否显示
		var disableReceiveBtn = (btnReportText == Oit.msg.wip.terminal.btn.report && Ext.fly('equipInfo')
				.getAttribute('status') != 'IN_PROGRESS');

		var issuedParamIshidden = true;
		var changeBtnIshidden = true;

		// 根据工序判断是否显示火花点按钮
		var disableQuerySparkBtn = true;
		if (processCode == 'Respool') {
			disableQuerySparkBtn = false;
		}

		var disableUnitOperationBZBtn = true;
		if (processCode == 'Braiding') {
			disableUnitOperationBZBtn = false;
		}

		var disableUnitOperationJYBtn = true;
		if (processCode == 'Extrusion-Single') {
			disableUnitOperationJYBtn = false;
		}

		var disableUnitOperationRBBtn = true;
		if (processCode == 'wrapping' || processCode == 'wrapping_ymd') {
			disableUnitOperationRBBtn = false;
		}

		var disableUnitOperationHTBtn = true;
		if (processCode == 'Jacket-Extrusion') {
			disableUnitOperationHTBtn = false;
		}

		// 根据工序设置taskInfoType
		switch (section) {
			case "绝缘" :
				taskInfoXtype = 'taskInfoGridJY';
				break;
			case "成缆" :
				taskInfoXtype = "taskInfoGridCL";
				break;
			case "护套" :
				taskInfoXtype = "taskInfoGridHT";
				break;
			default :
				taskInfoXtype = "taskInfoGridJY";
		}
		var isSteam = (processCode == 'Steam-Line'); // 判断是否蒸线
		var isJY = (section == '绝缘'); // 判断是否绝缘

		if (Ext.fly('orderInfo').getAttribute('status') == 'IN_PROGRESS') {
			changeBtnIshidden = false;
			btnDebugText = Oit.msg.wip.terminal.btn.debug;
			if (Ext.fly('equipInfo').getAttribute('status') == 'IN_DEBUG') {
				btnDebugText = Oit.msg.wip.terminal.btn.product;
				issuedParamIshidden = false;
			} else {
				btnDebugText = Oit.msg.wip.terminal.btn.debug;
				issuedParamIshidden = true;
			}
		} else {
			changeBtnIshidden = true;
			issuedParamIshidden = true;
		}

		if (processCode == 'Steam-Line') {
			issuedParamIshidden = true;
		}

		/**
		 * 1、优化代码，速度 提前请求后台获取所有需要数据
		 * 
		 * @param workOrder:1、生产单
		 * @param taskInfoListData:2、主显示任务列表
		 * @param materialListData:3、物料需求
		 * @param receiptListData:4、操作参数/数采下发
		 * @param processQcListData:5、生产要求/质量参数QC
		 * @param materialPlanListData:6、物料计划
		 * @param dailyCheckListData:7、质检信息/点检/上下车检
		 * @param mesClientManEquip:8、设备上的信息，包括采集长度，剩余长度等
		 * @param middleTitleInfo:9、主题显示标题
		 */
		var workOrder, taskInfoListData = [], materialListData = [], receiptListData = [], processQcListData = [], materialPlanListData = [], dailyCheckListData = [], mesClientManEquip = null, middleTitleInfo = '', dao = [];
		Ext.Ajax.request({
			url : 'initTerminalSingleAllData',
			async : false,
			method : 'GET',
			params : {
				section : section,
				equipCode : equipCode,
				workOrderNo : Ext.fly('orderDetail').getAttribute('num')
			},
			success : function(response) {
				var result = Ext.decode(response.responseText); // 请求响应转换成json格式
				workOrder = result.workOrder; // 1、生产单
				if(isReceived){//点接受才会显示数据
					taskInfoListData = result.taskInfoList; // 2、主显示任务列表
				}
				materialListData = result.materialList; // 3、物料需求表
				receiptListData = result.receiptList; // 4、操作参数/数采下发
				processQcListData = result.processQcList; // 5、生产要求/质量参数QC
				materialPlanListData = result.materialPlanList; // 6、物料计划
				dailyCheckListData = result.dailyCheckList; // 7、质检信息/点检/上下车检
				mesClientManEquip = Ext.create('bsmes.model.MesClientEquipInfo', result.mesClientManEquip); // 8、mesClientManEquip:
				// 设备上的信息，包括采集长度，剩余长度等
				// Ext.ComponentQuery.query('#feedText')[0].focus(false, 100);
				// Ext.ComponentQuery.query('#feedText')[0].equipInfos.add(info.equipCode,
				// infoModel);
				me.equipInfos[result.mesClientManEquip.equipCode] = mesClientManEquip; // 8.1、设备信息放入页面缓存

				// 9、主题显示标题
				if (workOrder.isDispatch)
					dao.push("急件");
				if (workOrder.isAbroad)
					dao.push("出口");
				if (workOrder.isOldLine)
					dao.push("陈线");
				middleTitleInfo = "<span style='font-size:20px;line-height: 24px;color:#ffffff;font-weight: bold;'>当前生产单:"
						+ workOrder.workOrderNo
						+ (dao.length > 0 ? '<font color="#ff0000">(' + dao.join() + ')</font>' : '') + '</span>';

			}
//			,
//			failure : function(response, action) {
//				Ext.Msg.alert(Oit.msg.WARN, '数据加载失败！');
//			}
		});
		

		// 2、任务栏设置
		var mainItems = [{
					xtype : taskInfoXtype,
					dataArray : taskInfoListData,
					workOrderInfo : mesClientManEquip
				}];

		var toolBar = {
			xtype : 'toolbar',
			width : '100%',
			height : 45,
			padding : '0 0 0 5',
			items : [{
						xtype : 'container',
						contentEl : 'equipInfo'
					}, '->', {
						xtype : 'button',
						itemId : 'changeEquipStatus',
						text : btnDebugText,
						hidden : changeBtnIshidden,
						equipCode : equipCode,
						margin : '0 10 0 0'
					}, {
						xtype : 'button',
						itemId : 'printerParam',
						text : Oit.msg.wip.terminal.btn.printer,
						hidden : isPrint,
						equipCode : equipCode,
						margin : '0 10 0 0',
						handler: function(){
							var selection = Ext.ComponentQuery.query('taskInfoGridHT')[0].getSelectionModel()
							.getSelection();
							if (selection.length > 0) {	
								var outmatdesc = selection[0].data.OUTMATDESC;
								outmatdesc = outmatdesc.toLocaleUpperCase();
								var myDate = new Date();
								if(outmatdesc.indexOf('X(班次)')){
									if(myDate.getHours() >= 7 && myDate.getHours() <15){
										outmatdesc = outmatdesc.replace('X(班次)','  早班  ');
									}else if(myDate.getHours() >= 15 && myDate.getHours() < 23){
										outmatdesc = outmatdesc.replace('X(班次)','  中班  ');
									}else{
										outmatdesc = outmatdesc.replace('X(班次)','  夜班  ');
									}
								}
								if(outmatdesc.indexOf('XXX(生产日期)')){
									outmatdesc = outmatdesc.replace('XXX(生产日期)',' '+Ext.util.Format.date(myDate, "Y-m-d")+' ');
								}
								if(outmatdesc.indexOf('XXX(日期)')){
									outmatdesc = outmatdesc.replace('XXX(日期)',' '+Ext.util.Format.date(myDate, "Y-m-d")+' ');
								}
								if(outmatdesc.indexOf('XXX(机台号)')){
									var name = Ext.fly('equipInfo').getAttribute('equipAlias');
									outmatdesc = outmatdesc.replace('XXX(机台号)',name+' ');
								}
								if(outmatdesc.indexOf('XX(机台号)')){
									var name = Ext.fly('equipInfo').getAttribute('equipAlias');
									outmatdesc = outmatdesc.replace('XX(机台号)',name+' ');
								}
								if(outmatdesc.indexOf('XXX年')){
									outmatdesc = outmatdesc.replace('XXX年',myDate.getFullYear()+'年 ');
								}
								if(outmatdesc.indexOf('生产年份')){
									outmatdesc = outmatdesc.replace('生产年份',' '+myDate.getFullYear()+'年  ');
								}
								if(outmatdesc.indexOf('XXX(米数)M')){
									outmatdesc = outmatdesc.replace('XXX(米数)M','');
								}
								if(outmatdesc.indexOf('米数')){
									outmatdesc = outmatdesc.replace('米数','');
								}
								if(outmatdesc.indexOf('-XX(材料厂家)')){
									outmatdesc = outmatdesc.replace('-XX(材料厂家)','');
								}
								Ext.MessageBox.confirm("确认印字内容", outmatdesc, function (btn) {
							        if(btn == 'yes'){
							        	Ext.Ajax.request({
											url : 'printerParam',
											params : {
												'outmatdesc' : outmatdesc
											},
											success : function(response) {
												Ext.Msg.alert(Oit.msg.WARN, '印字下发成功!');
											}
										});
							        }
							    });
							}else{
								Ext.Msg.alert(Oit.msg.PROMPT, '请选择对应产品！');
							}
							/*var win=Ext.create('bsmes.view.PrintPreviewWindow',{
								title: '下发预览',
								printParams: printParams
							});
							win.show();*/
						}
					},{
						xtype : 'button',
						text : Oit.msg.wip.terminal.btn.QADataEntry,
						itemId : 'QADataEntry',
						woNo : Ext.fly('orderInfo').getAttribute('num'),
						equipCode : equipCode,
						hidden : isSteam,
						margin : '0 10 0 0'
					}, {
						xtype : 'button',
						itemId : 'issuedParam',
						text : Oit.msg.wip.terminal.btn.issuedParam,
						hidden : isSteam,
						margin : '0 10 0 0'
					}, {
						xtype : 'button',
						itemId : 'check',
						text : Oit.msg.wip.terminal.btn.dailyMaintain,
						hidden : isSteam,
						equipCode : equipCode,
						margin : '0 10 0 0'
					}, btnBack]
		};
		var middle = {
			xtype : 'panel',
			layout : 'fit',
			width : '100%',
			height : (document.body.scrollHeight - 45) / 2,
			header : {
				xtype : 'toolbar',
				itemId : 'woTool',
				height : 45,
				items : [middleTitleInfo, '->', {
							xtype : 'button',
							itemId : 'receive',
							text : btnReportText,
							equipCode : equipCode,
							hidden : disableReceiveBtn,
							margin : '0 10 0 0'
						}, {
							xtype : 'button',
							itemId : 'change',
							text : Oit.msg.wip.terminal.btn.change,
							equipCode : equipCode,
							margin : '0 10 0 0'
						}, /*{
							 xtype : 'button',
							 itemId : 'shiftRecord',
							 hidden : isSteam,
					         text : Oit.msg.wip.terminal.btn.shiftRecord,
							 equipCode : equipCode,
							 margin : '0 10 0 0'
							 },*/ {
							xtype : 'button',
							itemId : 'turnOverMatreial',
							hidden : isSteam,
							text : Oit.msg.wip.terminal.btn.turnOverMatreial,
							equipCode : equipCode,
							// hidden: shiftRecordBtn,
							margin : '0 10 0 0'
						}, {
							xtype : 'button',
							text : Oit.msg.wip.terminal.btn.reportDetail,
							itemId : 'reportDetail',
							equipCode : equipCode,
							margin : '0 10 0 0'
						}, {
							xtype : 'button',
							text : Oit.msg.wip.terminal.btn.querySpark,
							itemId : 'sparkRepairDetail',
							equipCode : equipCode,
							hidden : disableQuerySparkBtn,
							margin : '0 10 0 0'
						}, {
							xtype : 'button',
							text : Oit.msg.wip.terminal.btn.triggerAlarm,
							itemId : 'triggerEquipAlarm',
							margin : '0 10 0 0'
						}, {
							xtype : 'button',
							text : Oit.msg.wip.terminal.btn.handleAlarm,
							itemId : 'handleEquipAlarm',
							margin : '0 10 0 0'
						}, {
							xtype : 'button',
							text : '编织单元操作',
							margin : '0 10 0 0',
							hidden : disableUnitOperationBZBtn,
							handler : function() {
								var selection = Ext.ComponentQuery.query('taskInfoGridCL')[0].getSelectionModel()
										.getSelection();
								if (selection.length) {					
									var contractNo = selection[0].data.CONTRACTNO;
									var custOrderItemId = selection[0].data.ID;
									var taskId = selection[0].data.TASKID;
									var productType = selection[0].data.CUSTPRODUCTTYPE;
									var productSpec = selection[0].data.CUSTPRODUCTSPEC;
									var taskLength = selection[0].data.TASKLENGTH;
									Ext.Ajax.request({
												url : 'unitOperation.action',
												async : false,
												params : {
													'contractNo' : contractNo,
													'taskId' : taskId,
													'productType' : productType,
													'productSpec' : productSpec,
													'taskLength' : taskLength,
													'equipCode' : equipCode
												},
												success : function(response) {
													var html = response.responseText;
													var win = me.getUnitOperationWin(html);
													win.show();
												}
											});
								} else {
									Ext.Msg.alert(Oit.msg.PROMPT, '请选择对应产品！');
								}

							}
						}, {
							xtype : 'button',
							text : '绝缘单元操作',
							margin : '0 30 0 0',
							hidden : disableUnitOperationJYBtn,
							handler : function() {
								var selection = Ext.ComponentQuery.query('#westGrid')[0].getSelectionModel()
								.getSelection();
								if (selection.length) {			
									var contractNo = selection[0].data.CONTRACTNO;
									var custOrderItemId = selection[0].data.ID;
									var taskId = selection[0].data.TASKID;
									var productType = selection[0].data.CUSTPRODUCTTYPE;
									var productSpec = selection[0].data.CUSTPRODUCTSPEC;
									var taskLength = selection[0].data.TASKLENGTH;
									var id = selection[0].data.ID;
									console.log(taskId);
									Ext.Ajax.request({
										url : 'unitOperationJY.action',
										async : false,
										params : {
											'contractNo' : contractNo,
											'taskId' : taskId,
											'productType' : productType,
											'productSpec' : productSpec,
											'taskLength' : taskLength,
											'equipCode' : equipCode,
											'custOrderItemId': id
										},
										success : function(response) {
											var html = response.responseText;
											var win = me.getUnitOperationWin(html);
											win.show();
										}
									});
								} else {
									Ext.Msg.alert(Oit.msg.PROMPT, '请选择对应产品！');
								}

						
							}
						}, {
							xtype : 'button',
							text : '绕包单元操作',
							margin : '0 30 0 0',
							hidden : disableUnitOperationRBBtn,
							handler : function() {
									var selection;
									if(processCode == 'wrapping_ymd'){
										selection = Ext.ComponentQuery.query('#westGrid')[0].getSelectionModel()
										.getSelection();
									}else{
										selection = Ext.ComponentQuery.query('taskInfoGridCL')[0].getSelectionModel()
										.getSelection();
									}
									//console.log(selection);
									if (selection.length) {			
										var contractNo = selection[0].data.CONTRACTNO;
										var custOrderItemId = selection[0].data.ID;
										var taskId = selection[0].data.TASKID;
										var productType = selection[0].data.CUSTPRODUCTTYPE;
										var productSpec = selection[0].data.CUSTPRODUCTSPEC;
										var taskLength = selection[0].data.TASKLENGTH;
										console.log(taskId);
										Ext.Ajax.request({
											url : 'unitOperationRB.action',
											async : false,
											params : {
												'contractNo' : contractNo,
												'taskId' : taskId,
												'productType' : productType,
												'productSpec' : productSpec,
												'taskLength' : taskLength,
												'equipCode' : equipCode,
												'processCode': processCode
											},
											success : function(response) {
												var html = response.responseText;
												var win = me.getUnitOperationWin(html);
												win.show();
											}
										});
									} else {
										Ext.Msg.alert(Oit.msg.PROMPT, '请选择对应产品！');
									}

							}
						}, {
							xtype : 'button',
							text : '护套单元操作',
							margin : '0 30 0 0',
							hidden : disableUnitOperationHTBtn,
							handler : function() {
								var selection = Ext.ComponentQuery.query('taskInfoGridHT')[0].getSelectionModel()
										.getSelection();
								if (selection.length) {			
									var contractNo = selection[0].data.CONTRACTNO;
									var custOrderItemId = selection[0].data.ID;
									var taskId = selection[0].data.TASKID;
									var productType = selection[0].data.CUSTPRODUCTTYPE;
									var productSpec = selection[0].data.CUSTPRODUCTSPEC;
									var taskLength = selection[0].data.TASKLENGTH;
									console.log(taskId);
									Ext.Ajax.request({
												url : 'unitOperationHT.action',
												async : false,
												params : {
													'contractNo' : contractNo,
													'taskId' : taskId,
													'productType' : productType,
													'productSpec' : productSpec,
													'taskLength' : taskLength,
													'equipCode' : equipCode
												},
												success : function(response) {
													var html = response.responseText;
													var win = me.getUnitOperationWin(html);
													win.show();
												}
											});
								} else {
									Ext.Msg.alert(Oit.msg.PROMPT, '请选择对应产品！');
								}

							
							}
						}]
			},
			items : mainItems
		};

		var footRightItems = me.getFootRightItems(); // 获取foot右侧信息
		var commentsItems = me.getCommentsItems();
		var foot = {
			xtype : 'panel',
			width : '100%',
			itemId : 'orderDetailPanel',
			layout : 'hbox',
			items : [{
						region : "west",
						border : 1,
						width : '75%',
						height : '100%',
						items : [{
									xtype : 'tabpanel',
									width : '100%',
									height : (document.body.scrollHeight - 45) / 2,
									items : [{
												xtype : 'materialGrid',
												woNo : Ext.fly('orderInfo').getAttribute('num'),
												dataArray : materialListData
											}, {
												xtype : 'processReceiptGrid',
												hidden : isSteam,
												dataArray : receiptListData
											}, {
												xtype : 'processQcGrid',
												hidden : isSteam,
												dataArray : processQcListData
											}, {
												xtype : 'taskInfoDetail', // 不需要传dataArray，与主任务列表共享store
												hidden : (isSteam || !isJY)
											}, {
												xtype : 'todayMatPlanView',
												hidden : isSteam,
												dataArray : materialPlanListData
											}, {
												xtype : 'dailyCheckGrid',
												hidden : isSteam,
												dataArray : dailyCheckListData
											}, commentsItems
											]
								}]
					}, {
						region : "east",
						width : '25%',
						height : '100%',
						border : 1,
						height : (document.body.scrollHeight - 45) / 2,
						items : [footRightItems]
					}

			]
		}
		me.items = [toolBar, middle, foot]
		me.callParent(arguments);

	},
	setController : function(controller) {
		var me = this;
		me.controller = controller;
	},
	getController : function() {
		var me = this;
		return me.controller;
	},

	/**
	 * 获取注意事项和备注
	 */
	getFootRightItems : function() {
		var me = this;
		var userComment = Ext.fly('orderInfo').getAttribute('userComment');
		userComment = (userComment == null ? '' : userComment);
		var code = Ext.fly('processInfo').getAttribute('code');
		var infos = [];
		console.log(userComment);
		if (userComment != '') {
			infos.push({
						xtype : 'displayfield',
						value : '<font style="color:red;">'+userComment+'</font>'
					});
			infos.push({
						xtype : 'panel',
						height : 10
					});
		}
		return {
			title : '注意事项',
			xtype : 'panel',
			anchor : '100%',
			layout : 'vbox',
			autoScroll : true,
			height : (document.body.scrollHeight - 45) / 2,
			items : [{
						padding : '10 10 10 10',
						defaults : {
							fieldStyle : {
								color : 'black'
							},
							width : (document.body.scrollWidth * 0.25 - 30)
						},
						items : infos
					}]
		};
	},
	
	getCommentsItems : function() {
		var me = this;
		var code = Ext.fly('processInfo').getAttribute('code');
		var infos = [];

		if (code == 'Braiding') {
			// 1、严格控制编织密度，不低于80%
			// 2、编织层不允许整体接续
			// 3、生产时必须严格遵守机台操作规程
			// 4、生产结束后，应挂上制造卡，注明型号、规格长度、生产日期、制造人
			infos.push({
						xtype : 'displayfield',
						value : '1、严格控制编织密度，不低于80%'
					});
			infos.push({
						xtype : 'displayfield',
						value : '2、编织层不允许整体接续'
					});
			infos.push({
						xtype : 'displayfield',
						value : '3、按生产工艺、材料定额组织生产，保证产品质量'
					});
			infos.push({
						xtype : 'displayfield',
						value : '4、生产结束后，应挂上制造卡，注明型号、规格长度、生产日期、制造人'
					});
		} else {
			infos.push({
						xtype : 'displayfield',
						value : '1、请严格对照相应工艺文件生产，平均值不小于标称值'
					});
			infos.push({
						xtype : 'displayfield',
						value : '2、最薄点厚度应不小于标称值的90%-0.1mm'
					});
			infos.push({
						xtype : 'displayfield',
						value : '3、生产时必须严格遵守机台操作规程'
					});
			infos.push({
						xtype : 'displayfield',
						value : '4、严格按照顺序生产，确保每根绝缘线芯长度，做好分段标识，不得私自分头'
					});
			infos.push({
						xtype : 'displayfield',
						value : '5、认真填写相关记录，按时间完成生产工作'
					});
			infos.push({
						xtype : 'displayfield',
						value : '6、请按生产进度要求组织生产，保证电缆按时交货'
					});
		}
		return {
			title : '备注',
			xtype : 'panel',
			autoScroll : true,
			height : (document.body.scrollHeight - 45) / 2,
			items : [{
						padding : '10 10 10 10',
						defaults : {
							fieldStyle : {
								color : 'red'
							}
						},
						items : infos
					}]
		};
	},

	// 获取单元操作弹出框
	getUnitOperationWin : function(html) {
		var me = this;
		var win = Ext.create('Ext.window.Window', {
					width : document.body.scrollWidth - 300,
					minWidth : 850,
					height : document.body.scrollHeight - 50,
					autoScroll : true,
					padding : '20 0 0 0',
					html : html,
					title : '单元操作',
					buttons : [{
								text : '打印',
								handler : me.printUnitOperation
							}]
				});
		return win;
	},

	// 打印单元操作
	printUnitOperation : function() {
		$('div#UnitOperationPrintArea').printArea();
	}
});