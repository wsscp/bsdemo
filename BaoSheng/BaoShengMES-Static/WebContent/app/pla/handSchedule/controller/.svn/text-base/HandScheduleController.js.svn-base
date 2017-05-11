Ext.define('bsmes.controller.HandScheduleController', {
	extend : 'Oit.app.controller.GridController',
	
	// // 导入订单[已不用，待删]
	importOrderWindow : 'importOrderWindow',
	importProductWindow : 'importProductWindow',
	importProductDetailWindow : 'importProductDetailWindow',
	importProductDetail : 'importProductDetail',
	// 自动排成预计完成时间[未开启]
	sortAndCalculateGrid : 'sortAndCalculateGrid', // 订单排序并计算Grid
	sortAndCalculateWindow : 'sortAndCalculateWindow', // 订单排序并计算Win
	/*finishedProductList : 'finishedProductList',*/
	finishedProductWindow : 'finishedProductWindow',
	
	view : 'handScheduleGrid', // 默认表单: 绝缘排产页面
	views : ['HandSchedulePanel', 'HandScheduleGrid', 'HandSchedule2Grid', // 排产列表
			 'ProcessWorkOrderJYWindow', 'ProcessWorkOrderHTWindow', 'ProcessWorkOrderCLWindow', // 排产页面
			'ShowProcessWorkOrderJYWindow', 'ShowProcessWorkOrderCLWindow', 'ShowProcessWorkOrderHTWindow', // 排产页面查看
			'ChooseCustomerOrderDecWindow', // 排产->排产拆分选择
			'Notification', // 主页->设备事件提醒[右下角弹出小窗口]
			'ChooseCraftsWindow', // 订单排产页->工艺切换
			'InstanceProcessWindow', // 订单排产页->工艺查看/编辑
			'ReportPlanWindow', // 订单排产页->报计划
			'LookUpProgressWindow', // 订单排产页/查看生产单->生产单任务明细进度
			'ShowWorkOrderWindow', 'ShowWorkOrderGrid', // 查看生产单
			'ShowSeriesWorkOrderWindow', // 查看生产单->层级查看
			'ChangeWorkOrderSeqWindow', // 查看生产单->调整机台生产单加工顺序
			'WorkOrderOperateLogWindow', // 查看生产单->操作历史
			'ReportRecordsWindow', // 查看生产单->报工记录
			'InventoryGridWindow', // 订单排产页->库存明细查看
			'SortAndCalculateGrid', 'SortAndCalculateWindow', // 自动排成预计完成时间[未开启]
			'ImportOrderWindow', 'ImportProductWindow', 'ImportProductDetailWindow', 'ImportProductDetail' // 导入订单[已不用，待删]
			,'ImportFinishedProductWindow','FinishedProductList','FinishedProductEdit','FinishedProductWindow','ImportTechniqueWin'],
	stores : ['HandScheduleStore', 'HandSchedule2Store', 'SortAndCalculateStore',
			'ChooseCraftsStore', 'WorkOrderStore', 'ChangeWorkOrderSeqStore', 'ImportProductStore',
			'ProcessInOutStore', 'ProcessQcStore', 'ProcessMatPropStore', 'ReportRecordsStore', 'WorkOrderOperateLogStore','FinishedProductStore'],
	constructor : function() {
		var me = this;

		// 初始化refs
		me.refs = me.refs || [];

		// 下单主列表[排生产单:成缆/护套]
		me.refs.push({
			ref : 'handSchedule2Grid',
			selector : 'handSchedule2Grid',
			autoCreate : true,
			xtype : 'handSchedule2Grid'
		});
		// 下单主列表->工艺切换[成缆]
		me.refs.push({
			ref : 'chooseCraftsWindow',
			selector : 'chooseCraftsWindow',
			autoCreate : true,
			xtype : 'chooseCraftsWindow'
		});
		// 下单列表->下发生产单选择订单窗口[Window]		
		me.refs.push({
			ref : 'chooseCustomerOrderDecWindow',
			selector : 'chooseCustomerOrderDecWindow',
			autoCreate : true,
			xtype : 'chooseCustomerOrderDecWindow'
		});
		// 下单列表->绝缘下单窗口[Window]
		me.refs.push({
			ref : 'processWorkOrderJYWindow',
			selector : 'processWorkOrderJYWindow',
			autoCreate : true,
			xtype : 'processWorkOrderJYWindow'
		});
		// 下单列表->成缆下单窗口[Window]
		me.refs.push({
			ref : 'processWorkOrderCLWindow',
			selector : 'processWorkOrderCLWindow',
			autoCreate : true,
			xtype : 'processWorkOrderCLWindow'
		});
		// 下单列表->护套下单窗口[Window]
		me.refs.push({
			ref : 'processWorkOrderHTWindow',
			selector : 'processWorkOrderHTWindow',
			autoCreate : true,
			xtype : 'processWorkOrderHTWindow'
		});
		// 下单列表->查看生产单[WINDOW]
		me.refs.push({
			ref : 'showWorkOrderWindow',
			selector : 'showWorkOrderWindow',
			autoCreate : true,
			xtype : 'showWorkOrderWindow'
		});
		// 下单列表->查看生产单[Grid]
		me.refs.push({
			ref : 'showWorkOrderGrid',
			selector : 'showWorkOrderGrid',
			autoCreate : true,
			xtype : 'showWorkOrderGrid'
		});
		// 下单列表->查看生产单->查看报工记录
		me.refs.push({
			ref : 'reportRecordsWindow',
			selector : 'reportRecordsWindow',
			autoCreate : true,
			xtype : 'reportRecordsWindow'
		});
		// 下单列表->查看生产单->生产单操作历史查看
		me.refs.push({
			ref : 'workOrderOperateLogWindow',
			selector : 'workOrderOperateLogWindow',
			autoCreate : true,
			xtype : 'workOrderOperateLogWindow'
		});		
				
		// 下单列表->查看生产单->绝缘生产单查看窗口[Window]
		me.refs.push({
			ref : 'showProcessWorkOrderJYWindow',
			selector : 'showProcessWorkOrderJYWindow',
			autoCreate : true,
			xtype : 'showProcessWorkOrderJYWindow'
		});
		// 下单列表->查看生产单->成缆生产单查看窗口[Window]
		me.refs.push({
			ref : 'showProcessWorkOrderCLWindow',
			selector : 'showProcessWorkOrderCLWindow',
			autoCreate : true,
			xtype : 'showProcessWorkOrderCLWindow'
		});
		// 下单列表->查看生产单->护套生产单查看窗口[Window]
		me.refs.push({
			ref : 'showProcessWorkOrderHTWindow',
			selector : 'showProcessWorkOrderHTWindow',
			autoCreate : true,
			xtype : 'showProcessWorkOrderHTWindow'
		});
		// 下单列表->查看生产单->层级查看生产单窗口[Window]
		me.refs.push({
			ref : 'showSeriesWorkOrderWindow',
			selector : 'showSeriesWorkOrderWindow',
			autoCreate : true,
			xtype : 'showSeriesWorkOrderWindow'
		});
		// 下单列表->查看生产单->调整生产单加工顺序窗口[Window]
		me.refs.push({
			ref : 'changeWorkOrderSeqWindow',
			selector : 'changeWorkOrderSeqWindow',
			autoCreate : true,
			xtype : 'changeWorkOrderSeqWindow'
		});

		// =====================delete=======================
		// 注册 - 导入新订单WINDOW视图[删除]
		me.refs.push({
			ref : 'importOrderWindow',
			selector : me.importOrderWindow,
			autoCreate : true,
			xtype : me.importOrderWindow
		});
		// 注册 - 导入新产品WINDOW视图[删除]
		me.refs.push({
					ref : 'importProductWindow',
					selector : me.importProductWindow,
					autoCreate : true,
					xtype : me.importProductWindow
				});
		// 注册 - 导入新产品详情WINDOW视图[删除]
		me.refs.push({
					ref : 'importProductDetailWindow',
					selector : me.importProductDetailWindow,
					autoCreate : true,
					xtype : me.importProductDetailWindow
				});
		// 注册 - 导入产品详情GRID视图[删除]
		me.refs.push({
					ref : 'importProductDetail',
					selector : '#importProductDetail'
				});
		// =====================delete=======================

		// =====================hidden=======================
		// 注册 - 订单排序并计算GRID视图[影藏]
		me.refs.push({
					ref : 'sortAndCalculateGrid',
					selector : '#sortAndCalculateGrid'
				});
		// 注册 - 订单排序并计算WINDOW视图[影藏]
		me.refs.push({
					ref : 'sortAndCalculateWindow',
					selector : me.sortAndCalculateWindow,
					autoCreate : true,
					xtype : me.sortAndCalculateWindow
				});
		//导入成品现货
		me.refs.push({
			ref : 'importFinishedProductWindow',
			selector : 'importFinishedProductWindow',
			autoCreate : true,
			xtype : 'importFinishedProductWindow'
		});
		//导入内部工艺
		me.refs.push({
			ref : 'importTechniqueWin',
			selector : 'importTechniqueWin',
			autoCreate : true,
			xtype : 'importTechniqueWin'
		});
		
		me.refs.push({
			ref : 'finishedProductWindow',
			selector : me.finishedProductWindow,
			autoCreate : true,
			xtype : me.finishedProductWindow
		});
		/*me.refs.push({
			ref : 'finishedProductList',
			selector : me.finishedProductList,
			autoCreate : true,
			xtype : me.finishedProductList
		});*/
		
		// =====================hidden=======================
				
		me.callParent(arguments);

		setInterval(function() {
					// me.refreshEventPrompt.apply(me);
				}, 60000);
	},

	init : function() {
		var me = this;
		// 1、下单主列表->排生产单[云母绕包]
		me.control('handSchedulePanel button[itemId=mergeYunMuProduce]', {
			click : me.mergeProduceYM
		});
		// 2、下单主列表->排生产单
		me.control('handSchedulePanel button[itemId=mergeProduce]', {
			click : me.mergeProduce
		});
		// 3、下单主列表->查看生产单
		me.control('handSchedulePanel button[itemId=showWorkOrderList]', {
			click : me.showWorkOrderList
		});
		// 4、查询无工艺产品[隐藏]
		me.control('handScheduleGrid button[itemId=searchNoCrafts]', {
			click : me.searchNoCrafts
		});
		// 5、下单主列表->保存下发清单
		me.control('handScheduleGrid button[itemId=saveTemp]', {
			click : me.saveTemp
		});
		// 6、下单主列表->查询下发清单
		me.control('handScheduleGrid button[itemId=searchTemp]', {
			click : me.searchTemp
		});
		// 7、下单主列表->清空下发清单
		me.control('handScheduleGrid button[itemId=clearTemp]', {
			click : me.clearTemp
		});
		// 8、下单主列表->报计划弹出框
		me.control('handScheduleGrid button[itemId=reportPlan]', {
			click : me.openReportPlan
		});
		// 9、下单主列表->外计划设置
		me.control('handScheduleGrid button[itemId=outPlan]', {
			click : function() {
				me.updateSpecialFlag('确认标识为厂外计划？', '0', true); // 更新订单特殊状态 : 0:外计划
			}
		});
		// 10、下单主列表->导出外计划
		me.control('handScheduleGrid button[itemId=exportOutPlan]', {
			click : me.onExportOutPlan
		});
		// 11、下单主列表->绝缘转手工单
		me.control('handScheduleGrid button[itemId=changeOrder2PaperJY]', {
			click : function() {
				me.updateSpecialFlag('确认转手工单？', '2', true); // 更新订单特殊状态 : 2:手工单
			}
		});
		// 12、下单主列表->库存生产
		me.control('handScheduleGrid button[itemId=stockOrder]', {
			click : function() {
				
				me.openFinishedProductList();
//				me.updateSpecialFlag('确认库存生产？', '3', true); // 更新订单特殊状态 : 3:库存生产
			}
		});
		// 13、下单主列表->切换工艺->保存
		me.control('chooseCraftsWindow button[itemId=ok]', {
			click : me.saveOrderProductCrafts
		});
		// 13.1、下单主列表[成缆/护套]->查询
		me.control('handSchedule2Grid button[itemId=search]', {
					click : me.search2
				});
		// 13.2、下单主列表[成缆]->手工单
		me.control('handSchedule2Grid button[itemId=changeOrder2Paper]', {
					click : me.changeOrder2Paper
				});
		// 13.3、下单主列表[成缆/护套]->生产单拆分->保存
		me.control('chooseCustomerOrderDecWindow button[itemId=ok]', {
					click : me.mergeProduceCLHT
				});
		// 14、下单主列表->生产单列表->条件查询
		me.control('showWorkOrderGrid button[itemId=searchWorkOrder]', {
					click : me.searchWorkOrder
				});
		// 15、下单主列表->生产单列表->生产单详情
		me.control('showWorkOrderGrid button[itemId=showWorkOrderDetail]', {
					click : me.showWorkOrderDetail
				});
		// 16、下单主列表->生产单列表->层级查看生产单
		me.control('showWorkOrderGrid button[itemId=showSeriesWorkOrder]', {
					click : me.showSeriesWorkOrder
				});
		// 17、下单主列表->生产单列表->下发生产单
		me.control('showWorkOrderGrid button[itemId=auditWorkOrder]', {
					click : me.auditWorkOrder
				});
		// 18、下单主列表->生产单列表->设置为急件
		me.control('showWorkOrderGrid button[itemId=setdispatch]', {
					click : me.setdispatch
				});
		// 19、生产单列表->调整生产线
		me.control('showWorkOrderGrid button[itemId=changeEquip]', {
					click : me.changeEquip
				});
		// 20、生产单列表->调整生产单加工顺序
		me.control('showWorkOrderGrid button[itemId=changeWorkOrderSeq]', {
					click : me.changeWorkOrderSeq
				});
		// 21、下单主列表->生产单列表->调整生产单加工顺序->上
		me.control('changeWorkOrderSeqWindow button[itemId=move]', {
					click : me.upMoveWorkOrder
				});
		// 22、下单主列表->生产单列表->调整生产单加工顺序->下
		me.control('changeWorkOrderSeqWindow button[itemId=down]', {
					click : me.downMoveWorkOrder
				});
		// 23、下单主列表->生产单列表->调整生产单加工顺序->置顶
		me.control('changeWorkOrderSeqWindow button[itemId=top]', {
					click : me.topMoveWorkOrder
				});
		// 24、下单主列表->生产单列表->调整生产单加工顺序->置地
		me.control('changeWorkOrderSeqWindow button[itemId=end]', {
					click : me.endMoveWorkOrder
				});
		// 25、下单主列表->生产单列表->调整生产单加工顺序->保存
		me.control('changeWorkOrderSeqWindow button[itemId=save]', {
					click : me.changeWorkOrderSeqSub
				});
		// 26、下单主列表->生产单列表->生产单操作历史查看
		me.control('showWorkOrderGrid button[itemId=showWorkOrderOperateLog]', {
					click : me.showWorkOrderOperateLogWin
				});	
		// 27、下单主列表->生产单列表->查看报工记录
		me.control('showWorkOrderGrid button[itemId=showReportRecords]', {
					click : me.showReportRecordsWin
				});
		// 28、下单主列表->生产单列表->层级查看->查看生产单明细			
		me.control('showSeriesWorkOrderWindow button[itemId=showWorkOrderDetail]', {
					click : me.showSeriesWorkOrderDetail
				});
		// 29、下单主列表->自动排成预计完成时间->调整顺序->上[未开启]
		me.control(me.sortAndCalculateGrid + ' button[itemId=move]', {
					click : me.upMove
				});
		// 30、下单主列表->自动排成预计完成时间->调整顺序->下[未开启]
		me.control(me.sortAndCalculateGrid + ' button[itemId=down]', {
					click : me.downMove
				});
		// 31、下单主列表->自动排成预计完成时间->调整顺序->置顶[未开启]
		me.control(me.sortAndCalculateGrid + ' button[itemId=top]', {
					click : me.topMove
				});
		// 32、下单主列表->自动排成预计完成时间->调整顺序->置底[未开启]
		me.control(me.sortAndCalculateGrid + ' button[itemId=end]', {
					click : me.endMove
				});
		// 33、下单主列表->自动排成预计完成时间->调整顺序->保存计算[未开启]
		me.control(me.sortAndCalculateWindow + ' button[itemId=ok]', {
					click : me.sortAndCalculateSub
				});
		// 34、下单主列表->导入订单[删除]
		me.control('handSchedulePanel button[itemId=importNewOrder]', {
					click : me.importNewOrder
				});
		// 35、下单主列表->导入订单[删除]
		me.control('handSchedulePanel button[itemId=importNewProduct]', {
					click : me.importNewProduct
				});
		// 36、下单主列表->保存并发送无工艺路线的产品[删除]
		me.control('handSchedulePanel button[itemId=saveNoPrcv]', {
					click : me.saveNoPrcv
				});
		// 37、下单主列表->导入订单[删除]
		me.control(me.importOrderWindow + ' button[itemId=ok]', {
					click : me.importNewOrderSub
				});
		// 38、下单主列表->导入订单[删除]
		me.control(me.importProductWindow + ' button[itemId=ok]', {
					click : me.importNewProductSub
				});
		// 39、下单主列表->导入订单[删除]
		me.control(me.importProductWindow + ' button[itemId=prcvXml]', {
					click : me.importPrcvXml
				});
		// 40、下单主列表->导入订单[删除]
		me.control(me.importProductWindow + ' button[itemId=detail]', {
					click : me.importNewProductDe
				});
		// 41 导入成品现货
		me.control('handSchedulePanel button[itemId=importFinishedProduct]', {
			click : me.openImportFinishedProduct
		});
		
		// 41 导入内部工艺
		me.control('handSchedulePanel button[itemId=importTechnique]', {
			click : me.openImportTechniqueWin
		});
		
		me.control('importTechniqueWin button[itemId=ok]', {
			click : me.importTechnique
		});
		
		me.control('importFinishedProductWindow button[itemId=ok]', {
			click : me.importFinishedProduct
		});
		me.control('finishedProductEdit button[itemId=ok]', {
			click : me.finishedProductEdit
		});
		me.control('finishedProductList button[itemId=socket]', {
			click : function(){
				me.updateSpecialFlag('确认库存生产？', '3', true); // 更新订单特殊状态 : 3:库存生产
			}
		});
		
		me.callParent(arguments);
	},
	
	// 1、下单主列表->排生产单[云母绕包]
	mergeProduceYM : function() {
		var me = this;
		// 1、获取grid和选择行
		var grid = me.getGrid();
		var selection = grid.getSelectionModel().getSelection();
		var orderItemIdArray = [], errorMessage = '';
		if (selection && selection.length > 0) {
			// 2、 获取所选的订单产品ID
			Ext.Array.each(selection, function(record, i) {
						// 2.1、合并前端校验：云母\绝缘
						errorMessage = me.mergeFrontValidate(record, errorMessage, orderItemIdArray);
					});
			if (errorMessage != '') {
				Ext.Msg.alert(Oit.msg.WARN, errorMessage);
				return;
			}
			// 3、后台校验：(a)工艺是否完整;(b)是否已经下发了;
			me.validateDB(selection, orderItemIdArray, true);
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择订单产品');
			return;
		}
	},
	
	// 2、下单主列表->排生产单
	mergeProduce : function() {
		var me = this;
		// 1、获取grid和选择行,工段选择
		var processSection = Ext.ComponentQuery.query('handSchedulePanel #chooseWorkSection')[0].getValue().processCode; // 页面工段选择
		var grid = me.getGrid() ? me.getGrid() : me.getHandSchedule2Grid();
		var selection = grid.getSelectionModel().getSelection();
		var orderItemIdArray = [], errorMessage = '', workOrderIds = '';
		if (selection && selection.length > 0) {
			// 2、 获取所选的订单产品ID\生产单号
			Ext.Array.each(selection, function(record, i) {
						if (processSection == 'CL' || processSection == 'HT') { // 成缆护套是单选
							workOrderIds += (record.get('id') + ',');
							preWorkOrderNo = record.get('workOrderNo');
						} else {
							// 2.1、合并前端校验：云母\绝缘
							errorMessage = me.mergeFrontValidate(record, errorMessage, orderItemIdArray);
						}
					});
			if (errorMessage != '') {
				Ext.Msg.alert(Oit.msg.WARN, errorMessage);
				return;
			}
			// 3、前端处理完后：成缆护套弹出，绝缘后台校验
			if (processSection == 'CL' || processSection == 'HT') {
				me.openJobOrderWin(processSection, workOrderIds, preWorkOrderNo, selection[0]);
			} else { // 绝缘工序
				// 3、后台校验：(a)工艺是否完整;(b)是否已经下发了;
				me.validateDB(selection, orderItemIdArray, false);
			}
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择订单产品');
			return;
		}
	},
	
	// 3、下单主列表->查看生产单
	showWorkOrderList : function() {
		var me = this, orderItemIds = [];

		var win = me.getShowWorkOrderWindow();
		var store = me.getShowWorkOrderGrid().getStore();
		store.load();
		win.show();
	},
	
	// 4、查询无工艺产品[隐藏]
	searchNoCrafts : function() {
		var me = this;
		var grid = me.getGrid();
		var store = grid.getStore();
		grid.getStore().getProxy().url = 'handSchedule/tempSearch/1';
		// console.log(grid.getStore().getProxy().url);
		store.load();
		grid.getStore().getProxy().url = 'handSchedule';
	},
	
	// 5、下单主列表->保存下发清单
	saveTemp : function() {
		var me = this;
		var grid = me.getGrid();
		var selection = grid.getSelectionModel().getSelection();
		var orderItemIds = [];
		if (selection && selection.length > 0) {
			Ext.Array.each(selection, function(record) {
						orderItemIds.push(record.get('ID'));
					});
			Ext.Ajax.request({
				url : 'handSchedule/tempSave',
				method : 'GET',
				params : {
					orderItemIds : orderItemIds.join()
				},
				success : function(response, action) {
					Ext.Msg.alert(Oit.msg.PROMPT, '保存成功');
				},
				failure : function(response, action) {
					Ext.Msg.alert(Oit.msg.ERROR, '保存失败');
				}

			});
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择产品');
			return;
		}
	},
	
	// 6、下单主列表->查询下发清单
	searchTemp : function() {
		var me = this;
		var grid = me.getGrid();
		var store = grid.getStore();
		grid.getStore().getProxy().url = 'handSchedule/tempSearch/1';
		store.load();
		grid.getStore().getProxy().url = 'handSchedule';
	},
	
	// 7、下单主列表->清空下发清单
	clearTemp : function() {
		var me = this;
		var grid = me.getGrid();
		var selections = grid.getSelectionModel().getSelection();
		var orderItemIds = [];
		if (selections && selections.length > 0) {
			Ext.Array.each(selections, function(record) {
						orderItemIds.push(record.get('ID'));
					});
			Ext.Ajax.request({
				url : 'handSchedule/clearTemp',
				params : {
					orderItemIds : orderItemIds.join()
				},
				success : function(response, action) {
					var store = grid.getStore();
					store.getProxy().url = 'handSchedule/tempSearch/1';
					store.load();
					grid.getStore().getProxy().url = 'handSchedule';
				},
				failure : function(response, action) {
					Ext.Msg.alert(Oit.msg.ERROR, '清除失败');
				}
			});
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择产品');
			return;
		}
	},
	
	// 8、下单主列表->报计划弹出框
	openReportPlan : function() {
		var me = this, dataArray = [];
		var grid = me.getGrid();
		var selection = grid.getSelectionModel().getSelection();
		if (selection && selection.length > 0) {
			Ext.Array.each(selection, function(record, i) {
						dataArray.push({
									INDEX : record.index,
									ID : record.get('ID'),
									NUMBEROFWIRES : record.get('NUMBEROFWIRES'),
									ORDERLENGTH : record.get('ORDERLENGTH')

								});
					});
			var win = Ext.create('bsmes.view.ReportPlanWindow', {
						dataArray : dataArray
					});
			win.show();
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择订单产品');
		}
	},
	
	// 9、11、12、下单主列表->外计划设置
	// private : 更新订单特殊状态: [0:厂外计划;1:计划已报;2:手工单;3:库存生产]
	// @param title 提示标题
	// @param ids 订单id
	// @param specialFlag 状态值
	// @param finished 是否完成订单状态
	updateSpecialFlag : function(title, specialFlag, finished) {
		var me = this;
		// 1、获取grid和选择行
		var grid = me.getGrid();
		var selection = grid.getSelectionModel().getSelection();
		if (selection && selection.length > 0) {
			var orderItemIdArray = [];
			Ext.Array.each(selection, function(record) {
						orderItemIdArray.push(record.get('ID'));
					});
			Ext.MessageBox.confirm('确认', title, function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
										url : 'handSchedule/updateSpecialFlag',
										params : {
											ids : orderItemIdArray.join(),
											specialFlag : specialFlag,
											finished : finished ? true : false
										},
										success : function(response) {
											Ext.Msg.alert(Oit.msg.PROMPT, '操作成功');
											grid.getStore().reload();
										}
									});
						}
					});
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择订单产品');
		}
	},
	
	// 10、下单主列表->导出外计划
	onExportOutPlan : function() {
		var me = this, dataArray = [], hasOther = false;
		var grid = me.getGrid();
		var selection = grid.getSelectionModel().getSelection();
		if (selection && selection.length > 0) {
			Ext.Array.each(selection, function(record, i) {
						dataArray.push(record.getData());
						if (record.get('SPECIALFLAG') != '0') {
							hasOther = true;
						}
					});
			var title = '确认将外计划导出？';
			if (hasOther)
				title = '包含有非外计划的订单，确认一起导出外计划？';
			Ext.MessageBox.confirm('确认', title, function(btn) {
				if (btn == 'yes') {
					var columnArray = ["合同号", "经办人", "单位", "客户产品型号", "客户产品规格", "产品型号", "产品规格", "长度", "交货期", "技术要求",
							"备注", "ERP编码"];
					var dataIndexArray = ["CONTRACTNO", "OPERATOR", "CUSTOMERCOMPANY", "CUSTPRODUCTTYPE",
							"CUSTPRODUCTSPEC", "PRODUCTTYPE", "PRODUCTSPEC", "CONTRACTLENGTH", "OADATE",
							"PROCESSREQUIRE", "REMARKS", "ERPBM"];
					Ext.Ajax.request({
						url : 'handSchedule/putOutPlan2Excel',
						params : {
							columnArray : Ext.encode(columnArray),
							dataIndexArray : Ext.encode(dataIndexArray),
							dataArray : Ext.encode(dataArray)
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if (result != '') {
								var form = $('<form method="get" target="_blank" style="display:none" action="handSchedule/onExportOutPlan"><input name="sourcePath" value="'
										+ result + '"></form>')
								$('body').append(form);
								form.submit();
							} else {
								Ext.Msg.alert(Oit.msg.ERROR, '下载文件不存在！');
							}
						},
						failure : function(response, action) {
							// var result = Ext.decode(response.responseText);
							// Ext.Msg.alert(Oit.msg.ERROR, result.message);
						}
					});
				}
			});
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择订单产品');
		}
	},
	
	// 13、下单主列表->切换工艺->保存
	saveOrderProductCrafts : function() {
		var me = this;
		var win = me.getChooseCraftsWindow();
		var rowIndex = win.rowIndex, oldCraftsId = win.oldCraftsId, orderItemId = win.orderItemId, newCraftsId;
		var grid = win.down('grid');
		var record = grid.getSelectionModel().getSelection();
		if (record.length > 0) {
			newCraftsId = record[0].get('id');
			if (newCraftsId == oldCraftsId) {
				win.close();
				return;
			}
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择一条记录！');
			return;
		}

		// 更新订单产品的工艺Id
		Ext.Msg.wait('工艺数据正在初始化，请稍后...', '提示');
		Ext.Ajax.request({
					url : 'handSchedule/updateOrderItemCraftsId',
					params : {
						orderItemId : orderItemId,
						craftsId : newCraftsId
					},
					success : function(response) {
						Ext.Msg.hide(); // 隐藏进度条
						var result = Ext.decode(response.responseText);
						// result = result.message;
						// 更改列表grid信息
						var handScheduleList = me.getGrid();
						var store = handScheduleList.getStore();
						var record = store.getAt(rowIndex);
						record.set('PARENTCRAFTSID', newCraftsId);
						record.set('CRAFTSID', result.id);
						record.set('CRAFTSCODE', result.craftsCode);
						record.set('CRAFTSCNAME', result.craftsCname);
						Ext.Msg.alert(Oit.msg.PROMPT, '工艺切换成功！');
						if (win)
							win.close();
					},
					failure : function(response, action) {
						Ext.Msg.hide(); // 隐藏进度条
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(Oit.msg.ERROR, '工艺数据初始化失败，请联系管理员！');
					}
				});
	},
	
	// 13.1、下单主列表[成缆/护套]->查询
	search2 : function() {
		var me = this;
		me.getHandSchedule2Grid().getStore().loadPage(1);
	},
	
	// 13.2、下单主列表[成缆/护套]->手工单
	changeOrder2Paper : function() {
		var me = this;
		var grid = me.getHandSchedule2Grid();
		var selection = grid.getSelectionModel().getSelection();
		if (selection && selection.length > 0) {
			Ext.MessageBox.confirm('确认', '确认转手工单？', function(btn) {
				if (btn == 'yes') {
					var workOrderNo = [];
					Ext.Array.each(selection, function(record, i) {
						workOrderNo.push(record.get('workOrderNo'));
					})
					Ext.Ajax.request({
						url : 'handSchedule/updateNextSection?workOrderNo=' + workOrderNo.join() + '&nextSection=0',
						success : function(response) {
							grid.getStore().load(); // 刷新列表
							Ext.Msg.alert(Oit.msg.PROMPT, '操作成功！');
						}
					});
				}
			});
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择生产单！');
		}
	},
	
	// 13.3、下单主列表[成缆/护套]->生产单拆分->保存
	mergeProduceCLHT : function() {
		var me = this;
		var orderDecWin = me.getChooseCustomerOrderDecWindow();
		var orderGrid = orderDecWin.down('grid');
		if (orderGrid) {
			var selection = orderGrid.getSelectionModel().getSelection();
			// data : 编辑后的数据
			// orderItemIdProcessArray : 查询工序checkbox需要的订单ID
			// tmpProcessArrayMap :
			// 附属：用于辅助获取orderItemIdProcessArray的对象
			// orderIdProductTypeMap : 订单id和产品类型的映射
			var data = [], orderItemIdArray = [], orderItemIdProcessArray = [], tmpProcessArrayMap = {}, orderIdProductTypeMap = {};

			if (selection && selection.length > 0) {
				Ext.Array.each(selection, function(record, i) {
					record.set('isAudit', true);
					var productType = record.get('productType') + record.get('productSpec');
					orderIdProductTypeMap[record.get('id')] = productType;
					var tmp = tmpProcessArrayMap[productType];
					if (!tmp) {
						orderItemIdProcessArray.push(record.get('id'));
						tmpProcessArrayMap[productType] = record.get('id');
					}
					orderItemIdArray.push(record.get('id'));
					data.push(Ext.clone(record.data));
				});

				// 获取后台数据并打开弹出框
				me.getAllDataAndOpenWin(orderDecWin.processSection, orderItemIdArray, orderItemIdProcessArray,
						orderIdProductTypeMap, preWorkOrderNo, data, orderDecWin.workOrderInfo);
			}
		} else {
			orderDecWin.close();
		}
	},
	
	// 14、下单主列表->生产单列表->条件查询
	searchWorkOrder : function() {
		var me = this;
		var store = me.getShowWorkOrderGrid().getStore();
		store.loadPage(1);
	},
	
	// 15、下单主列表->生产单列表->生产单详情	
	showWorkOrderDetail : function() {
		var me = this;
		var showWorkOrderGrid = me.getShowWorkOrderGrid();
		var selection = showWorkOrderGrid.getSelectionModel().getSelection();
		if (selection && selection.length == 1) {
			me.openShowWorkOrderDetailWin(selection[0]);
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条生产单！');
		}
	},
	
	// 16、下单主列表->生产单列表->层级查看生产单
	showSeriesWorkOrder : function() {
		var me = this;
		var showWorkOrderGrid = me.getShowWorkOrderGrid();
		var selection = showWorkOrderGrid.getSelectionModel().getSelection();
		if (selection && selection.length == 1) {
			var record = selection[0];
			var window = me.getShowSeriesWorkOrderWindow({
						preWorkOrderNo : record.get('workOrderNo')
					});
			var store = window.down('grid').getStore();
			store.load({
				params : {
					'preWorkOrderNo' : record.get('workOrderNo')
				}
			});
			window.show();
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条生产单！');
		}
	},
	
	// 17、下单主列表->生产单列表->下发生产单
	auditWorkOrder : function() {
		var me = this;
		var showWorkOrderGrid = me.getShowWorkOrderGrid();
		var selection = showWorkOrderGrid.getSelectionModel().getSelection();
		if (selection && selection.length > 0) {
			var workOrderNo = '';
			for (var i in selection) {
				var record = selection[i];
				if (record.get('status') == 'TO_AUDIT' || record.get('status') == 'CANCELED') {
					workOrderNo += record.get('workOrderNo') + ',';
				}
			}
			if (workOrderNo != '') {
				Ext.Ajax.request({
							url : 'handSchedule/updateWorkerOrderStatus',
							params : {
								workOrderNo : workOrderNo,
								status : 'TO_DO'
							},
							success : function(response) {
								Ext.Msg.alert(Oit.msg.PROMPT, '下发成功');
								showWorkOrderGrid.getStore().load();
							},
							failure : function(response, action) {
								var result = Ext.decode(response.responseText);
								Ext.Msg.alert(Oit.msg.ERROR, result.message);
							}
						});
			} else {
				Ext.Msg.alert(Oit.msg.WARN, '请选择一条状态待下发的生产单！');
			}
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择一条生产单！');
		}
	},
	
	// 18、下单主列表->生产单列表->设置为急件
	setdispatch : function() {
		var me = this;
		var showWorkOrderGrid = me.getShowWorkOrderGrid();
		var selection = showWorkOrderGrid.getSelectionModel().getSelection();
		if (selection && selection.length > 0) {
			var workOrderNo = '';
			for (var i in selection) {
				var record = selection[i];
				workOrderNo += record.get('workOrderNo') + ',';

			}
			Ext.Ajax.request({
						url : 'handSchedule/updateWorkerOrderToDispatch',
						params : {
							'workOrderNo' : workOrderNo
						},
						success : function(response) {
							Ext.Msg.alert(Oit.msg.PROMPT, '设置成功');
							showWorkOrderGrid.getStore().load();
						},
						failure : function(response, action) {
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert(Oit.msg.ERROR, result.message);
						}
					});

		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条生产单！');
		}
	},
	
	// 19、生产单列表->调整生产线
	changeEquip : function() {
		var me = this;
		var showWorkOrderGrid = me.getShowWorkOrderGrid();
		var selection = showWorkOrderGrid.getSelectionModel().getSelection();
		if (selection && selection.length == 1) {
			var record = selection[0];
			var window = Ext.create('bsmes.view.ChangeWorkOrderEquipWindow', {
						processCode : record.get('processCode'),
						equipCode : record.get('equipCode'),
						workOrderNo : record.get('workOrderNo')
					});
			window.show();
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条生产单！');
		}
	},
	
	// 20、生产单列表->调整生产单加工顺序
	changeWorkOrderSeq : function() {
		var me = this;
		// 1、获取工段信息
		Ext.Msg.wait(Oit.msg.LOADING, Oit.msg.PROMPT);
		Ext.Ajax.request({
					url : '../pro/processInfo/section',
					success : function(response, action) {
						Ext.Msg.hide(); // 隐藏进度条
						// 2、弹出窗口
						if(response.responseText){
							var result = Ext.decode(response.responseText);
							var win = me.getChangeWorkOrderSeqWindow({processSection : result});
							win.show();
						}
					}
				});
	},
	
	// 21、下单主列表->生产单列表->调整生产单加工顺序->上
	upMoveWorkOrder : function() {
		var me = this;
		var grid = me.getChangeWorkOrderSeqWindow().down('grid');
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			var insertIndex = store.indexOf(selection[0]);
			Ext.Array.each(selection, function(record, i) {
						var index = store.indexOf(record);
						if (insertIndex > 0) {
							store.insert(index - 1, record);
						}
					});
			grid.getView().refresh();
		}
	},
	
	// 22、下单主列表->生产单列表->调整生产单加工顺序->下
	downMoveWorkOrder : function() {
		var me = this;
		var grid = me.getChangeWorkOrderSeqWindow().down('grid');
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			var insertIndex = store.indexOf(selection[0]);
			Ext.Array.each(selection, function(record, i) {
						var index = store.indexOf(record);
						if (insertIndex < store.getCount() - 1) {
							store.insert(index + 2, record);
						}
					});
			grid.getView().refresh();// 刷新行号
		}
	},

	// 23、下单主列表->生产单列表->调整生产单加工顺序->置顶
	topMoveWorkOrder : function() {
		var me = this;
		var grid = me.getChangeWorkOrderSeqWindow().down('grid');
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			Ext.Array.each(selection, function(record, i) {
						var index = store.indexOf(record);
						store.insert(i, record);
					});
			grid.getView().refresh();// 刷新行号
		}
	},
	
	// 24、下单主列表->生产单列表->调整生产单加工顺序->置地
	endMoveWorkOrder : function() {
		var me = this;
		var grid = me.getChangeWorkOrderSeqWindow().down('grid');
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			Ext.Array.each(selection, function(record, i) {
						store.insert(store.getCount() + i, record);
					});
			grid.getView().refresh();// 刷新行号
		}
	},
	
	// 25、下单主列表->生产单列表->调整生产单加工顺序->保存
	changeWorkOrderSeqSub : function() {
		var me = this;
		var grid = me.getChangeWorkOrderSeqWindow().down('grid');
		var store = grid.getStore();
		var workOrderIds = [];
		for (var i = 0; i < store.getCount(); i++) {
			var record = store.getAt(i);
			workOrderIds.push(record.get('id'));
		}
		var equipCompany = grid.query('form combobox[name="equipCode"]')[0];
		var equipCode = equipCompany.getValue();
		if (equipCode == '' || workOrderIds.length == 0) {
			return;
		}

		Ext.Ajax.request({
					url : 'handSchedule/updateWOrkOrderSeq',
					method : 'POST',
					params : {
						equipCode : equipCode,
						updateSeq : Ext.encode(workOrderIds)
					},
					success : function(response) {
						Ext.Msg.alert(Oit.msg.PROMPT, '保存成功');
					}
				});
	},
	
	// 26、下单主列表->生产单列表->生产单操作历史查看
	showWorkOrderOperateLogWin : function() {
		var me = this;
		var showWorkOrderGrid = me.getShowWorkOrderGrid();
		var selection = showWorkOrderGrid.getSelectionModel().getSelection();
		if (selection && selection.length == 1) {
			var record = selection[0];
			var window = me.getWorkOrderOperateLogWindow();
			console.log(window)
			var grid = window.down('grid');
			console.log(grid)
			var store = grid.getStore();
			store.load({
				params : {
					workOrderNo : record.get('workOrderNo')
				}
			})
			window.show();
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条生产单！');
		}
	},
	
	// 27、下单主列表->生产单列表->查看报工记录
	showReportRecordsWin : function() {
		var me = this;
		var showWorkOrderGrid = me.getShowWorkOrderGrid();
		var selection = showWorkOrderGrid.getSelectionModel().getSelection();
		if (selection && selection.length == 1) {
			var record = selection[0];
			var window = me.getReportRecordsWindow();
			var grid = window.down('grid');
			var store = grid.getStore();
			store.load({
				params : {
					workOrderNo : record.get('workOrderNo')
				}
			})
			window.show();
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条生产单！');
		}
	},
	
	// 28、下单主列表->生产单列表->层级查看->查看生产单明细			
	showSeriesWorkOrderDetail : function() {
		var me = this;
		var showSeriesWorkOrderGrid = me.getShowSeriesWorkOrderWindow().down('grid');
		var selection = showSeriesWorkOrderGrid.getSelectionModel().getSelection();
		if (selection && selection.length == 1) {
			me.openShowWorkOrderDetailWin(selection[0]);
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条生产单！');
		}
	},
	// 29、下单主列表->自动排成预计完成时间->弹出窗口[未开启]
	sortAndCalculate : function() {
		var me = this;
		var win = me.getSortAndCalculateWindow();
		var store = me.getSortAndCalculateGrid().getStore();
		store.load();
		win.show();
	},
	// 29、下单主列表->自动排成预计完成时间->调整顺序->上[未开启]
	upMove : function() {
		var me = this;
		var grid = me.getSortAndCalculateGrid();
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			var insertIndex = store.indexOf(selection[0]);
			Ext.Array.each(selection, function(record, i) {
						var index = store.indexOf(record);
						if (insertIndex > 0) {
							store.insert(index - 1, record);
						}
					});
			grid.getView().refresh();
		}
	},
	
	// 30、下单主列表->自动排成预计完成时间->调整顺序->下[未开启]
	downMove : function() {
		var me = this;
		var grid = me.getSortAndCalculateGrid();
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			var insertIndex = store.indexOf(selection[0]);
			Ext.Array.each(selection, function(record, i) {
						var index = store.indexOf(record);
						if (insertIndex < store.getCount() - 1) {
							store.insert(index + 2, record);
						}
					});
			grid.getView().refresh();// 刷新行号
		}
	},
	
	// 31、下单主列表->自动排成预计完成时间->调整顺序->置顶[未开启]
	topMove : function() {
		var me = this;
		var grid = me.getSortAndCalculateGrid();
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			Ext.Array.each(selection, function(record, i) {
						var index = store.indexOf(record);
						store.insert(i, record);
					});
			grid.getView().refresh();// 刷新行号
		}
	},
	
	// 32、下单主列表->自动排成预计完成时间->调整顺序->置底[未开启]
	endMove : function() {
		var me = this;
		var grid = me.getSortAndCalculateGrid();
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			Ext.Array.each(selection, function(record, i) {
						store.insert(store.getCount() + i, record);
					});
			grid.getView().refresh();// 刷新行号
		}
	},
	
	// 33、下单主列表->自动排成预计完成时间->调整顺序->保存计算[未开启]
	sortAndCalculateSub : function() {
		var me = this;
		var store = me.getSortAndCalculateGrid().getStore();
		if (store.getCount() == 0) {
			me.getSortAndCalculateWindow().close();
			return;
		}
		// 更新列表数据
		var result = new Array();
		var data = store.data.items;
		if (data) {
			Ext.each(data, function(record, i) {
						record.set("seq", i);
						result.push(record.getData());
					});
		}
		Ext.Msg.alert(Oit.msg.PROMPT, '正在为您计算订单OA，请稍后...', '提示');
		Ext.Ajax.request({
					url : 'handSchedule/updateSeqAndCalculate',
					method : 'POST',
					params : {
						'jsonText' : Ext.encode(result)
					},
					success : function(response) {
						me.getSortAndCalculateWindow().close();
					},
					failure : function(response, action) {
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(Oit.msg.ERROR, result.message);
					}
				});
	},
	// 33、下单主列表->自动排成预计完成时间->重新计算[未开启]
	calculateAgain : function() {
		Ext.Ajax.request({
					url : 'handSchedule/calculateAgain',
					method : 'POST',
					success : function(response) {
						Ext.Msg.alert(Oit.msg.PROMPT, '正在为您计算生产单排程，稍后请刷新...');
					},
					failure : function(response, action) {
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(Oit.msg.ERROR, result.message);
					}
				});
	},
	
	// 34、下单主列表->导入订单[删除]
	importNewOrder : function() {
		var me = this;
		var win = me.getImportOrderWindow();
		win.show();
	},
	
	// 35、下单主列表->导入订单[删除]
	importNewProduct : function() {
		var me = this;
		var win = me.getImportProductWindow();
		win.show();
	},
	
	// 36、下单主列表->保存并发送无工艺路线的产品[删除]
	saveNoPrcv : function() {
		var me = this;
		var grid = me.getGrid();
		var selection = grid.getSelectionModel().getSelection();
		var pro = [];
		var prcvs = [];
		var orderItemIds = [];
		if (selection && selection.length > 0) {
			Ext.MessageBox.confirm('确认', '确认保存？', function(btn) {
				if (btn == 'yes') {
					Ext.Array.each(selection, function(record) {
						pro.push(record.get('CONTRACTNO') + '\t' + record.get('PRODUCTTYPE') + record.get('PRODUCTSPEC') + '\t' + record.get('ERPBM'));
						prcvs.push(record.get('ERPBM'));
						orderItemIds.push(record.get('ID'));
					});
					Ext.Ajax.request({
						url : 'handSchedule/saveNoPrcv',
						method : 'GET',
						params : {
							pro : pro.join(),
							prcvs : prcvs.join(),
							orderItemIds : orderItemIds.join()
						},
						success : function(response, action) {
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert(Oit.msg.PROMPT, result.message);
						},
						failure : function(response, action) {
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert(Oit.msg.ERROR, '邮件发送失败');
						}
					});
				}
			});
		} else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择产品');
			return;
		}
	},
	
	// 37、下单主列表->导入订单[删除]
	importNewOrderSub : function() {
		var me = this;
		var win = me.getImportOrderWindow();
		var form = win.down('form');

		if (form.isValid()) {
			form.submit({
						waitMsg : '正在导入并分解订单计划,请耐心等待...',
						success : function(form, action) {
							var result = Ext.decode(action.response.responseText);
							Ext.Msg.alert(Oit.msg.PROMPT, result.message);
							me.getGrid().getStore().load();
							win.close();
						},
						failure : function(form, action) {
							var result = Ext.decode(action.response.responseText);
							Ext.Msg.alert(Oit.msg.ERROR, result.message);
						}
					});
		}

	},
	
	// 38、下单主列表->导入订单[删除]
	importNewProductSub : function() {
		var me = this;
		var win = me.getImportProductWindow();
		var form = win.down('form');
		var filename = '';
		var item = '';
		var series = Ext.ComponentQuery.query('importProductWindow combobox')[0].getRawValue();
		if (!series) {
			Ext.Msg.alert(Oit.msg.WARN, '请输入系列名');
			return;
		}
		for (var i = 1; i < form.items.items.length; i++) {
			item = form.items.items[i];
			if (item.getValue()) {
				filename = item.getValue().substr(0, (item.getValue().indexOf('.')));
				if (item.getXtype != 'textfield' && filename != '' && filename != item.getName()) {
					Ext.Msg.alert(Oit.msg.WARN, item.getFieldLabel() + '文件名不匹配!');
					return;
				}
			}
		}
		if (filename == '') {
			Ext.Msg.alert(Oit.msg.WARN, '请选择对应的导入文件');
			return;
		}
		if (form.isValid()) {
			form.submit({
						waitMsg : '正在导入产品,请耐心等待...',
						success : function(form, action) {
							var result = Ext.decode(action.response.responseText);
							Ext.Msg.alert(Oit.msg.PROMPT, result.message);
							// me.getGrid().getStore().load();
							win.close();
						},
						failure : function(form, action) {
							var result = Ext.decode(action.response.responseText);
							Ext.Msg.alert(Oit.msg.ERROR, result.message);
						}
					});
		}

	},
	
	// 39、下单主列表->导入订单[删除]
	importPrcvXml : function() {
		Ext.MessageBox.confirm('确认', '确认生成工艺文件？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : 'customerOrderItem/importPrcvXml',
					method : 'POST',
					success : function(response) {
						Ext.Msg.alert(Oit.msg.PROMPT, '生成完毕');
					},
					failure : function(response, action) {
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(Oit.msg.ERROR, result.message);
					}
				});
			}
		});
	},
	
	// 40、下单主列表->导入订单[删除]
	importNewProductDe : function() {
		var me = this;
		var win = this.getImportProductDetailWindow();
		var store = me.getImportProductDetail().getStore();
		store.load();
		win.show();
	},
			
	// private : 15、28、查看生产单->生产单详情[公共方法]
	openShowWorkOrderDetailWin : function(record) {
		var me = this;
		var workOrderRecord = record.getData();
		var workOrderSection = record.get('workOrderSection'); // 生产单所属工段

		Ext.Msg.wait(Oit.msg.LOADING, Oit.msg.PROMPT);
		Ext.Ajax.request({
			url : 'handSchedule/showWorkOrderDetail?workOrderNo=' + workOrderRecord.workOrderNo,
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				var result = Ext.decode(response.responseText);

				if (workOrderSection == '1') {
					win = me.getShowProcessWorkOrderJYWindow({
						title : '生产单：' + record.get('workOrderNo') + '-' + record.get('processName'),
						workOrderRecord : workOrderRecord,
						allDatas : result
					});
				} else if (workOrderSection == '2') {
					win = me.getShowProcessWorkOrderCLWindow({
						title : '生产单：' + record.get('workOrderNo') + '-' + record.get('processName'),
						workOrderRecord : workOrderRecord,
						allDatas : result
					});
				} else if (workOrderSection == '3') {
					win = me.getShowProcessWorkOrderHTWindow({
						title : '生产单：' + record.get('workOrderNo') + '-' + record.get('processName'),
						workOrderRecord : workOrderRecord,
						allDatas : result
					});
				}
				if (win) {
					win.show();
				}
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.ERROR, '查询数据失败！');
			}
		});
	},
	
	// private : 1、2、合并前端校验：云母\绝缘[公共方法]
	// @param record grid每一条数据对象
	// @param errorMessage 显示错误信息
	// @param orderItemIdArray 前端验证正确的订单产品ID
	mergeFrontValidate : function(record, errorMessage, orderItemIdArray) {
		var me = this;
		var contract = me.getShortContractNo(record.get('CONTRACTNO'), record.get('OPERATOR'), 1);
		var typeSpec = record.get('CUSTPRODUCTTYPE') + ' ' + record.get('PRODUCTSPEC');
		var length = record.get('ORDERLENGTH');
		var config = {
			contract : contract,
			typeSpec : typeSpec,
			length : length
		};
		if(record.get('FINISHJY') == '1'){
			errorMessage += StringFormat('[{contract}-{typeSpec}]已经全部下发<br/>', config);
		}else if (record.get('CRAFTSID') == null || record.get('CRAFTSID') == '') {
			errorMessage += StringFormat('[{contract}-{typeSpec}-{length}]没有配置工艺路线<br/>', config);
		} else if (record.get('STATUS') == 'CANCELED') {
			errorMessage += StringFormat('[{contract}-{typeSpec}-{length}]订单已经取消<br/>', config);
		} else if (record.get('STATUS') == 'FINISHED') {
			errorMessage += StringFormat('[{contract}-{typeSpec}-{length}]订单已经完成<br/>', config);
		} else {
			orderItemIdArray.push(record.get('ID'));
		}
		return errorMessage;
	},
	
	// private : 1、2、后台数据库查询校验[公共方法]
    // @param selection grid选中的数据对象
	// @param orderItemIdArray 前端验证正确的订单产品ID
	// @param isyunmu 是否是云母按钮
	validateDB : function(selection, orderItemIdArray, isyunmu) {
		var me = this;
		Ext.Msg.wait('数据验证中，请稍后...', '提示');
		Ext.Ajax.request({
			url : 'handSchedule/hasAuditOrder',
			method : 'GET',
			params : {
				orderItemIdArray : orderItemIdArray.join(),
				isyunmu : isyunmu
			},
			success : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				// console.log(response);
				var result = Ext.decode(response.responseText);
				// 验证没有问题封装并弹出
				me.mergeAfterValidate(selection, result);
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				var result = Ext.decode(response.responseText);
				if(result.message && result.message != ''){
					Ext.Msg.alert(Oit.msg.ERROR, result.message);
				}else{
					Ext.Msg.alert(Oit.msg.ERROR, '数据加载失败！');
				}
			}
		});
	},
	
	// private for validateDB(): 验证没有问题封装并弹出:主要针对云母绕包和挤出火花[公共方法]
	// @param selection 表格选中对象
	// @param preParams 上一道生产单信息{preWorkOrderNo : result.preWorkOrderNo, preProcessCode : result.preProcessCode},
	mergeAfterValidate : function(selection, preOrdersParams) {
		var me = this;
		// data:弹出框store数据；orderItemIdArray:后台查询订单产品id
		var data = [], orderItemIdArray = [];
		Ext.Array.each(selection, function(record, i) {
			orderItemIdArray.push(record.get('ID'));
			data.push({
				id : record.get('ID'),
				relateOrderIds : [record.get('ID')],
				contractNo : record.get('CONTRACTNO'),
				operator : record.get('OPERATOR'),
				productType : record.get('PRODUCTTYPE'),
				productSpec : record.get('PRODUCTSPEC'),
				custProductType : record.get('CUSTPRODUCTTYPE'),
				custProductSpec : record.get('CUSTPRODUCTSPEC'),
				contractLength : record.get('CONTRACTLENGTH'),
				orderLength : record.get('CONTRACTLENGTH'),
				// 没有太多意义，第二次点开窗口下火花单时，余量会重复 orderLength :
				// record.get('ORDERLENGTH'),
				wiresStructure : record.get('WIRESSTRUCTURE'),
				// conductorStruct : record.get('WIRESSTRUCTURE') + ' '
				// + record.get('CONDUCTORSTRUCT'),
				remarks : record.get('REMARKS'),
				processRequire : record.get('PROCESSREQUIRE'), // 技术要求
				numberOfWires : record.get('NUMBEROFWIRES'),
				splitLengthRole : record.get('SPLITLENGTHROLE'),
				strategy : 0
					// strategy 合并策略：{0:'正常长度叠加就可以', 1:'合并后放余量'}
				});
		});
		// 弹出通知单明细页面
		if (data.length > 0) {
			var s = new Date();
			Ext.Msg.wait('数据处理中，请稍后...', '提示');
			Ext.Ajax.request({
				url : 'handSchedule/getInitData?section=JY&orderItemIdArray=' + orderItemIdArray + '&preWorkOrderNo=',
				success : function(response) {
					Ext.Msg.hide(); // 隐藏进度条
					var e = new Date();
					console.log('数据查询初始渲染用时:' + Math.round(e - s))

					// 1、请求响应转换成json格式
					var result = Ext.decode(response.responseText);

					var e0 = new Date();
					console.log('解码查询结果用时:' + Math.round(e0 - e))

					var win = me.getProcessWorkOrderJYWindow({
						orderData : data,
						allDatas : result,
						preOrdersParams : preOrdersParams
					});
					win.show();
				},
				failure : function(response, action) {
					Ext.Msg.hide(); // 隐藏进度条
					Ext.Msg.alert(Oit.msg.ERROR, '数据加载失败！');
				}
			});
		}
	},
	
	// private : 2、排生产单: 弹出通知单明细页面：成缆、护套[公共方法]
	// @param processSection 工段
	// @param workOrderIds 生产单ID [成缆专用]
	// @param preWorkOrderNo 上一道工序生产单号 [成缆专用]
	// @param selectedRecord 选择好的记录 [成缆专用]
	openJobOrderWin : function(processSection, workOrderIds, preWorkOrderNo, selectedRecord) {
		var me = this;
		var workOrderInfo = {
			'isDispatch' : selectedRecord.get('isDispatch'),
			'isAbroad' : selectedRecord.get('isAbroad')
		};
		Ext.Msg.wait('查询订单数据中，请稍后...', '提示');
		Ext.Ajax.request({
			url : 'handSchedule/getOrderData?workOrderIds=' + workOrderIds,
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				// 弹出工序编辑窗口
				var result = Ext.decode(response.responseText), data = [], orderItemIdArray = [], orderItemIdProcessArray = [], tmpProcessArrayMap = {}, orderIdProductTypeMap = {};
				Ext.Array.each(result, function(record, i) {
					// 下单显示订单：如果已经完成或者已经下发，订单过滤
					var completeCusOrderItemIds = selectedRecord.get('completeCusOrderItemIds');
					var auditedCusOrderItemIds = selectedRecord.get('auditedCusOrderItemIds');
					if (processSection == 'JY') {
						if ((auditedCusOrderItemIds && auditedCusOrderItemIds.indexOf(record.ID) >= 0)) {
							return true;
						}
					} else if (processSection == 'CL') {
						if ((completeCusOrderItemIds && completeCusOrderItemIds.indexOf(record.ID) >= 0)
								|| (auditedCusOrderItemIds && auditedCusOrderItemIds.indexOf(record.ID) >= 0)) {
							return true;
						}
					} else if (processSection == 'HT') {
						// 当下一到工段为5=2+3，即：成缆护套都有的情况，未完成的在护套这边忽略
						if (completeCusOrderItemIds && completeCusOrderItemIds.indexOf(record.ID) < 0) {
							return true;
						}
					} else {
						return true;
					}
					
					var productType = record.PRODUCTTYPE + record.PRODUCTSPEC;
					orderIdProductTypeMap[record.ID] = productType;
					var tmp = tmpProcessArrayMap[productType];
					if (!tmp) {
						orderItemIdProcessArray.push(record.ID);
						tmpProcessArrayMap[productType] = record.ID;
					}
					orderItemIdArray.push(record.ID);

					data.push({
						id : record.ID,
						contractNo : record.CONTRACTNO,
						operator : record.OPERATOR,
						custProductType : record.CUSTPRODUCTTYPE,
						custProductSpec : record.CUSTPRODUCTSPEC,
						productType : record.PRODUCTTYPE,
						productSpec : record.PRODUCTSPEC,
						contractLength : record.CONTRACTLENGTH,
						orderLength : record.ORDERLENGTH,
						wiresStructure : record.WIRESSTRUCTURE,
						conductorStruct : record.WIRESSTRUCTURE + ' ' + record.CONDUCTORSTRUCT,
						color : (processSection == 'HT'? record.HTCOLOR : record.COLOR),
						wireCoilCount : record.WIRECOILCOUNT,
						splitLengthRole : record.SPLITLENGTHROLE,
						remarks : record.REMARKS,
						processRequire : record.PROCESSREQUIRE, // 技术要求
						voltage : record.CUSTPRODUCTTYPE.substring(record.CUSTPRODUCTTYPE.lastIndexOf('-') + 1, record.CUSTPRODUCTTYPE.length)
					});
				});

				if (data.length == 0) {
					Ext.Msg.alert(Oit.msg.WARN, '生产单已下发！');
				} else if (data.length == 1) {
					me.getAllDataAndOpenWin(processSection, orderItemIdArray, orderItemIdProcessArray, orderIdProductTypeMap,
							preWorkOrderNo, data, workOrderInfo);
				} else {
					var win = me.getChooseCustomerOrderDecWindow({
						preWorkOrderNo : preWorkOrderNo,
						orderItemIdProcessArray : orderItemIdProcessArray,
						orderIdProductTypeMap : orderIdProductTypeMap,
						orderData : data,
						processSection : processSection,
						workOrderInfo : workOrderInfo
					});
					win.show();
				}
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.ERROR, '数据加载失败！');
			}
		});

	},
	
	// private for openJobOrderWin() & 13.5 : 获取成缆护套下单页面所有数据[公共方法]
	// @param processSection 工段
	// @param orderItemIdArray 订单id，查询订单的
	// @param orderItemIdProcessArray 查询工序的订单id
	// @param orderIdProductTypeMap 订单id与型号规格的衍射
	// @param preWorkOrderNo 上一道工序生产单号 [成缆专用]
	// @param data 下单页面的orderData
	// @param workOrderInfo 生产单信息
	getAllDataAndOpenWin : function(processSection, orderItemIdArray, orderItemIdProcessArray, orderIdProductTypeMap,
			preWorkOrderNo, data, workOrderInfo) {
		var me = this
		var s = new Date();
		Ext.Msg.wait('数据处理中，请稍后...', '提示');
		Ext.Ajax.request({
			url : 'handSchedule/getInitData',
			method : 'GET',
			params : {
				section : processSection,
				orderItemIdArray : orderItemIdArray,
				orderItemIdProcessArray : orderItemIdProcessArray,
				preWorkOrderNo : preWorkOrderNo
			},
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条

				e = new Date();
				console.log('数据查询初始渲染用时:' + Math.round(e - s))

				var result = Ext.decode(response.responseText); // 请求响应转换成json格式

				var params = {
					allDatas : result,
					orderItemIdProcessArray : orderItemIdProcessArray,
					orderIdProductTypeMap : orderIdProductTypeMap,
					preWorkOrderNo : preWorkOrderNo,
					orderData : data,
					workOrderInfo : workOrderInfo
				};
				var win = null;
				if (processSection == 'JY')
					win = me.getProcessWorkOrderJYWindow(params);
				else if (processSection == 'CL')
					win = me.getProcessWorkOrderCLWindow(params);
				else if (processSection == 'HT')
					win = me.getProcessWorkOrderHTWindow(params);
				if (win) {
					win.show();
				}
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.ERROR, '数据加载失败！');
			}
		});
	},

	// private for mergeFrontValidate() : 获取精简型合同号[公共方法]
	// @param contractNo 合同号
	// @param contractNo 经办人
	// @param delimiter 界定符，默认[]，为1使用()
	getShortContractNo : function(contractNo, operator, delimiter) {
		if (contractNo && contractNo != '') {
			var value = contractNo;
			var reg = /[a-zA-Z]/g;
			value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length
					- 5) : value.replace(reg, ""));
			if (operator && operator != '') {
				if (delimiter && delimiter == 1) {
					return value + '(' + operator + ')';
				} else {
					return value + '[' + operator + ']';
				}
			} else {
				return value;
			}
		} else {
			return '';
		}
	},
	
	// 39、全局->刷新弹出提示信息[报警故障已经处理完毕，请调度员提示操作工终端确认]
	refreshEventPrompt : function() {
		Ext.Ajax.request({
			url : '/bsmes/eve/eventInformation/getEquipEventPending',
			success : function(response) {
				var results = Ext.decode(response.responseText).list;
				var msg = '';
				if (results.length == 0) {
					return;
				}
				Ext.Array.each(results, function(value, index, countriesItSelf) {
					var equipName = '';
					if (value.equipAlias) {
						equipName = value.equipAlias + '[' + value.equipCode + ']';
					} else {
						equipName = value.name + '[' + value.equipCode + ']';
					}
					msg = msg + '<p style = "font-size:12px;color:red;text-align:left;font-weight:normal;margin:2.5 0 2.5 0">'
							+ equipName + '报警故障已经处理完毕，请调度员提示操作工终端确认</p>';
				});
				Ext.create('widget.uxNotification', {
					title : '设备事件提醒',
					manager : 'demo1',
					autoCloseDelay : 10000,
					html : msg
				}).show();
			}
		});
	},
	
	openImportFinishedProduct : function(){
		var me = this;
		me.getImportFinishedProductWindow().show();
	},
	//导入成品现货
	importFinishedProduct : function(){
		var me = this;
		var win = me.getImportFinishedProductWindow();
		var form = win.down('form');
		if(form.isValid()){
			form.submit({
				waitMsg : '正在导入成品现货,请耐心等待...',
				success : function(form, action) {
					var result = Ext.decode(action.response.responseText);
					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
					win.close();
				},
				failure : function(form, action) {
					var result = Ext.decode(action.response.responseText);
					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
				}
			});
		}
	},
	//打开成品现货列表
	openFinishedProductList : function(){
		var me = this;
		// 1、获取grid和选择行
		var grid = me.getGrid();
		var selection = grid.getSelectionModel().getSelection();
		if (selection.length ==1) {
			var record = selection[0];
			if(record.get('STATUS') == 'FINISHED'){
				Ext.Msg.alert(Oit.msg.WARN, '订单已经完成!');
				return;
			}
			var model = record.get('CUSTPRODUCTTYPE');
			//去掉电压，对型号进行模糊查寻
			if(model.indexOf('-')>0){
				model = model.substring(0,model.lastIndexOf('-'));
			}
			var spec = record.get('CUSTPRODUCTSPEC');
			var win = me.getFinishedProductWindow();
			var grid = win.down('grid');
			var form = grid.down('form');
			form.getForm().setValues({
				model : model,
				spec : spec/*,
				id : record.get('ID'),
				contractLength : record.get('CONTRACTLENGTH')*/
			});
			grid.getStore().load({
				params : {
					model : model,
					spec : spec
				}
			});
			win.show();
		}else {
			Ext.Msg.alert(Oit.msg.WARN, '请选择一个订单产品');
		}
	},
	
	finishedProductEdit : function(){
		var me = this;
		var formPanel = Ext.ComponentQuery.query('#finishedProductEdit')[0];
		var form = formPanel.down('form');
		if(Number(form.getForm().getValues().uselength)>Number(form.getForm().getValues().length)){
			Ext.Msg.alert(Oit.msg.WARN, '使用长度超过剩余长度!');
			return;
		}
		var grid = me.getFinishedProductWindow().down('grid');
		var view =  Ext.ComponentQuery.query('handScheduleGrid')[0];
		var selection = view.getSelectionModel().getSelection();
		var record = selection[0];
		form.submit({
			url : 'handSchedule/updateFinishedProduct',
			params : {
				salesOrderItemId : record.get('SALESORDERITEMID')
			},
			success : function(form, action){
				var result = Ext.decode(action.response.responseText);
				formPanel.close();
				grid.getStore().reload();
			},
			failure : function(form, action) {
				
			}
		});
	},
	
	openImportTechniqueWin : function(){
		var me = this;
		var panel = Ext.ComponentQuery.query('handSchedulePanel')[0];
		var grid = panel.down('grid');
		var selection = grid.getSelectionModel().getSelection();
		if(selection.length == 1){
			me.getImportTechniqueWin().show();
		}else{
			Ext.Msg.alert(Oit.msg.WARN, '请选择一个订单产品');
		}
//		me.getImportFinishedProductWindow().show();
	},
	
	importTechnique : function(){
		var me = this;
		var grid = Ext.ComponentQuery.query('handSchedulePanel')[0].down('grid');
		var record = grid.getSelectionModel().getSelection()[0];
		var win = me.getImportTechniqueWin();
		var form = win.down('form');
		if(form.isValid()){
			form.submit({
				params : {
					contractNo : record.get('CONTRACTNO')
            	},
				waitMsg : '正在导入内部工艺,请耐心等待...',
				success : function(form, action) {
					var result = Ext.decode(action.response.responseText);
					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
					win.close();
					grid.getStore().reload();
				},
				failure : function(form, action) {
					var result = Ext.decode(action.response.responseText);
					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
				}
			});
		}
	}

});
