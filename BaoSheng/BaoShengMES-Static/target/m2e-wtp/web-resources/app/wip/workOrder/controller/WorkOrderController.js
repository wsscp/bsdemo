Ext.define('bsmes.controller.WorkOrderController', {
			extend : 'Oit.app.controller.GridController',
			view : 'workOrderList',
			logisticalRequestView : 'logisticalRequestView',
			priorityView : 'priorityWindow',
			fixedEquipWin : 'fixedEquipWindow',
			processItemGrid : 'processItemGrid', // 工序颜色分类
			matOAGrid : 'matOAGrid', // 物料需求
			processWorkOrderGrid : 'processWorkOrderGrid', // 合并生产弹出Grid
			processWorkOrderJYWindow : 'processWorkOrderJYWindow', // 合并生产绝缘弹出框
			processWorkOrderHTWindow : 'processWorkOrderHTWindow', // 合并生产护套弹出框
			processWorkOrderCLWindow : 'processWorkOrderCLWindow', // 合并生产成缆弹出框
			views : ['ProcessItemGrid', 'MatOAGrid', 'ProcessWorkOrderGrid', 'ProcessWorkOrderJYWindow',
					'ProcessWorkOrderHTWindow', 'ProcessWorkOrderCLWindow', 'WorkOrderList', 'LogisticalRequestView',
					'PriorityWindow', 'FixedEquipWindow'],
			stores : ['WorkOrderStore', 'WorkOrderSubStore', 'LeftStore', 'RightStore', 'WorkOrderProductStore',
					'WorkOrderMatReqStore', 'WorkOrderToolsReqStore'],
			exportUrl : 'workOrder/export/生产单',
			constructor : function(){
				var me = this;
				// 初始化refs
				me.refs = me.refs || [];

				// me.refs.push({
				// ref: 'searchForm',
				// selector: me.view +" form"
				// });
				me.refs.push({
							ref : 'processWorkOrderJYWindow',
							selector : me.processWorkOrderJYWindow,
							autoCreate : true,
							xtype : me.processWorkOrderJYWindow
						});
				me.refs.push({
							ref : 'processWorkOrderHHWindow',
							selector : me.processWorkOrderHHWindow,
							autoCreate : true,
							xtype : me.processWorkOrderHHWindow
						});
				me.refs.push({
							ref : 'processWorkOrderCLWindow',
							selector : me.processWorkOrderCLWindow,
							autoCreate : true,
							xtype : me.processWorkOrderCLWindow
						});

				me.refs.push({
							ref : 'logisticalRequestView',
							selector : me.logisticalRequestView,
							autoCreate : true,
							xtype : me.logisticalRequestView
						});
				me.refs.push({
							ref : 'logisticalRequestViewFrom',
							selector : me.logisticalRequestView + " form"
						});
				me.refs.push({
							ref : 'priorityWindow',
							selector : me.priorityView,
							autoCreate : true,
							xtype : me.priorityView
						});

				me.refs.push({
							ref : 'prioritySearchForm',
							selector : me.priorityView + ' form'
						});

				me.refs.push({
							ref : 'leftGrid',
							selector : me.priorityView + ' leftGridView'
						});

				me.refs.push({
							ref : 'rightGrid',
							selector : me.priorityView + ' rightGridView'
						});

				me.refs.push({
							ref : 'productGrid',
							selector : me.logisticalRequestView + " workOrderProductList"
						});
				me.refs.push({
							ref : 'toolsGrid',
							selector : me.logisticalRequestView + " workOrderToolsReqList"
						});
				me.refs.push({
							ref : 'matReqGrid',
							selector : me.logisticalRequestView + " workOrderMatReqList"
						});

				me.refs.push({
							ref : 'fixedEquipWin',
							selector : me.fixedEquipWin,
							autoCreate : true,
							xtype : me.fixedEquipWin
						});

				me.refs.push({
							ref : 'fixedEquipFrom',
							selector : me.fixedEquipWin + " form"
						});
				me.callParent(arguments);
			},
			init : function(){
				var me = this;
				if (!me.view) {
					Ext.Error.raise("A view configuration must be specified!");
				}
				/**
				 * @event detail 点击add按钮前触发。
				 * @param {Ext.button.Button}
				 *            btn 点击的button
				 */
				// 初始化工具栏
				me.control(me.view + ' button[itemId=search]', {
							click : me.onSearch
						});
				me.control(me.view + ' button[itemId=audit]', {
							click : me.onAudit
						});
				me.control(me.view + ' button[itemId=cancel]', {
							click : me.onCancel
						});
				me.control(me.view + ' button[itemId=setPriority]', {
							click : me.onSetPriority
						})
				me.control(me.view + ' button[itemId=setFixedEquipCode]', {
							click : me.onSetFixedEquipCode
						})
				me.control(me.priorityView + ' button[itemId=search]', {
							click : me.onWorkOrderSearch
						});
				me.control(me.priorityView + ' button[itemId=close]', {
							click : me.onClosePriority
						});
				me.control(me.priorityView + ' button[itemId=right]', {
							click : me.rightMove
						});

				me.control(me.priorityView + ' button[itemId=left]', {
							click : me.leftMove
						});

				me.control(me.priorityView + ' button[itemId=move]', {
							click : me.upMove
						});

				me.control(me.priorityView + ' button[itemId=down]', {
							click : me.downMove
						});

				me.control(me.priorityView + ' button[itemId=top]', {
							click : me.topMove
						});

				me.control(me.priorityView + ' button[itemId=end]', {
							click : me.endMove
						});

				me.control(me.priorityView + ' button[itemId=ok]', {
							click : me.setPrioritySave
						});

				me.control(me.priorityView + ' button[itemId=cancel]', {
							click : me.closePriorityWindow
						});

				me.control(me.fixedEquipWin + ' button[itemId=ok]', {
							click : me.saveFixedEquipWindow
						});

				me.control(me.view + ' button[itemId=export]', {
							click : me.onExport
						});

				me.control(me.view + ' button[itemId=recalculate]', {
							click : me.doRecalculate
						});

			},
			onLaunch : function(){
				var me = this;
				var grid = me.getGrid();

				/**
				 * @event toRequestPlan 后勤需求
				 * @param {Ext.grid.Panel}
				 *            grid
				 */
				grid.getView().on('logisticalRequest', me.toLogisticalRequest, me);

				/**
				 * @event toRequestPlan 查看生产单
				 * @param {Ext.grid.Panel}
				 *            grid
				 */
				grid.getView().on('showWorkOrderDetail', me.toShowWorkOrderDetail, me);

				me.callParent(arguments);
			},
			onSearch : function(btn){
				var me = this;
				var store = me.getGrid().getStore();
				var form = me.getSearchForm();
				store.loadPage(1, {
							params : form.getValues()
						});
			},
			onAudit : function(){
				var me = this;
				Ext.MessageBox.confirm('确认', '确认审核通过?', function(btn){
							if (btn == 'yes') {
								Ext.Msg.wait('处理中，请稍后...', '提示');
								Ext.Ajax.request({
											url : 'workOrder/auditWorkOrder',
											method : "GET",
											waitMsg : "处理中...",
											success : function(response){
												me.getGrid().getStore().reload();
												// 隐藏进度条
												Ext.Msg.hide();
											},
											failure : function(response, options){
												// 隐藏进度条
												Ext.Msg.hide();
												Ext.MessageBox.alert('失败', '错误编号：' + response.status);
											}
										});

							}
						});

			},
			onCancel : function(){
				var me = this;
				var me = this;
				var datas = me.getSelectedData();
				if (datas) {
					for (var i = 0; i < datas.length; i++) {
						var workOrder = datas[i];
						var status = workOrder.get("status");
						if (status == "IN_PROGRESS") {
							Ext.Msg.alert('提示', Oit.msg.wip.workOrder.workOrderNO + ":" + workOrder.get("workOrderNo")
											+ "," + Oit.msg.wip.workOrder.msg.cancelError);
							return;
						}
					}
				}

				Ext.MessageBox.confirm('确认', '确认取消生产单?', function(btn){
							if (btn == 'yes') {
								var array = new Array();
								Ext.each(datas, function(workOrder, index){
											array.push(workOrder.data);
										});
								Ext.Ajax.request({
											url : 'workOrder/cancelOrder',
											method : "POST",
											params : {
												array : Ext.encode(array)
											},
											success : function(response){
												Ext.Msg.alert('提示', Oit.msg.wip.workOrder.msg.success);
												me.getGrid().getStore().reload();
											},
											failure : function(response, options){
												Ext.MessageBox.alert('失败', '错误编号：' + response.status);
											}
										});
							}
						});
			},
			toLogisticalRequest : function(data){
				var me = this;
				var lrv = me.getLogisticalRequestView();
				var form = me.getLogisticalRequestViewFrom();
				form.loadRecord(data);
				var productGrid = me.getProductGrid();
				productGrid.getStore().getProxy().url = 'workOrder/productList/' + data.get('workOrderNo');
				productGrid.getStore().reload();

				var toolsGrid = me.getToolsGrid();
				toolsGrid.getStore().getProxy().url = 'workOrder/toolsList/' + data.get('workOrderNo');
				toolsGrid.getStore().reload();

				var matReqGrid = me.getMatReqGrid();
				matReqGrid.getStore().getProxy().url = 'workOrder/matList/' + data.get('workOrderNo');
				matReqGrid.getStore().reload();

				lrv.show();
			},

			/**
			 * 产看生产单
			 */
			toShowWorkOrderDetail : function(data){
				var me = this;
				var win = me.getProcessWorkOrderJYWindow();
				
//				// 获取成功取第一个工序CODE的工序明细
//				Ext.Ajax.request({
//							url : 'handSchedule/getOrderProcessByCode',
//							params : {
//								processCode : processCode,
//								orderItemIdArray : orderItemIdArray
//							},
//							success : function(response){
//								var result = Ext.decode(response.responseText);
//								me.drawingGridData(processCode, result);
//								me.getEquip(processCode, orderItemIdArray);
//							}
//						});

				win.show();
			},
			onSetPriority : function(){
				var me = this;
				me.getPriorityWindow().show();
				var formPanel = me.getPrioritySearchForm();
				var record = formPanel.getRecord();
				if (record) {

				} else {
					record = Ext.create("bsmes.model.WorkOrder");
					formPanel.form.loadRecord(record);
				}

			},
			onWorkOrderSearch : function(){
				var me = this;
				var formPanel = me.getPrioritySearchForm();
				formPanel.updateRecord();
				var record = formPanel.getRecord();
				if (record.get("equipCode") && record.get("equipCode") != '') {
					var leftStore = me.getLeftGrid().getStore();
					leftStore.getProxy().url = 'workOrder/disorderWorkOrder/' + record.get("equipCode") + '/';
					leftStore.reload();

					var rightStore = me.getRightGrid().getStore();
					rightStore.getProxy().url = 'workOrder/seqWorkOrder/' + record.get("equipCode") + '/';
					rightStore.reload();

				} else {
					Ext.MessageBox.alert('提示', "请选择机台后再进行查询!");
				}
			},
			onClosePriority : function(){
				var me = this;
				me.getPriorityWindow().close();
			},
			rightMove : function(){
				var me = this;
				var leftgrid = me.getLeftGrid();
				var rightgrid = me.getRightGrid();
				var leftStore = leftgrid.getStore();
				var rightStore = rightgrid.getStore();
				var selection = leftgrid.getSelectionModel().getSelection();
				if (selection) {
					var workOrderNoList = [];
					Ext.Array.each(selection, function(record, i){
								workOrderNoList.push(record.get("workOrderNo"));
							});
					Ext.Ajax.request({
								waitMsg : Oit.msg.wip.terminal.debugInfo.msg.loading,
								url : 'workOrder/moveToRight',
								method : 'POST',
								params : {
									workOrderNoList : workOrderNoList
								},
								success : function(response){
									leftStore.reload();
									rightStore.reload();
								},
								failure : function(response, opts){
									Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.wip.terminal.debugInfo.msg.error);
								}
							});
				}
			},
			leftMove : function(){
				var me = this;
				var leftgrid = me.getLeftGrid();
				var rightgrid = me.getRightGrid();
				var leftStore = leftgrid.getStore();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					var workOrderNoList = [];
					Ext.Array.each(selection, function(record, i){
								workOrderNoList.push(record.get("workOrderNo"));
							});
					Ext.Ajax.request({
								waitMsg : Oit.msg.wip.terminal.debugInfo.msg.loading,
								url : 'workOrder/moveToLeft',
								method : 'POST',
								params : {
									workOrderNoList : workOrderNoList
								},
								success : function(response){
									leftStore.reload();
									rightStore.reload();
								},
								failure : function(response, opts){
									Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.wip.terminal.debugInfo.msg.error);
								}
							});
				}
			},
			upMove : function(){
				var me = this;
				var rightgrid = me.getRightGrid();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();

				if (selection) {
					selection.sort(me.sortWorkOrderBySeq);
					var insertIndex = rightStore.indexOf(selection[0]);
					Ext.Array.each(selection, function(record, i){
								var index = rightStore.indexOf(record);
								if (insertIndex > 0) {
									rightStore.removeAt(index);
									rightStore.insert(index - 1, record);
								}
							});
					rightgrid.getView().refresh();
					me.updatePriority();
				}
			},
			downMove : function(){
				var me = this;
				var rightgrid = me.getRightGrid();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					selection.sort(me.sortWorkOrderBySeq);
					var insertIndex = rightStore.indexOf(selection[0]);
					Ext.Array.each(selection, function(record, i){
								var index = rightStore.indexOf(record);
								if (index < rightStore.getCount() - 1) {
									rightStore.removeAt(index);
									rightStore.insert(index + 1, selection);
								}
							});
					rightgrid.getView().refresh();// 刷新行号
					me.updatePriority();
				}
			},
			topMove : function(){
				var me = this;
				var rightgrid = me.getRightGrid();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					selection.sort(me.sortWorkOrderBySeq);
					Ext.Array.each(selection, function(record, i){
								var index = rightStore.indexOf(record);
								rightStore.removeAt(index);
								rightStore.insert(i, record);
							});
					rightgrid.getView().refresh();// 刷新行号
					me.updatePriority();
				}
			},
			endMove : function(){
				var me = this;
				var rightgrid = me.getRightGrid();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					selection.sort(me.sortWorkOrderBySeq);
					Ext.Array.each(selection, function(record, i){
								var index = rightStore.indexOf(record);
								rightStore.removeAt(index);
								rightStore.insert(rightStore.getCount() + i, record);
							});
					rightgrid.getView().refresh();// 刷新行号
					me.updatePriority();
				}
			},
			updatePriority : function(){
				var me = this;
				var rightStore = me.getRightGrid().getStore();
				// 更新右边列表数据
				var rightResult = new Array();
				var rightData = rightStore.data.items;
				if (rightData) {
					Ext.each(rightData, function(record, i){
								record.set("seq", i + 1);
								rightResult.push(record.getData());
							});
				}
				Ext.Ajax.request({
							url : 'workOrder/updateSeq',
							method : 'POST',
							params : {
								'updateSeq' : Ext.encode(rightResult)
							},
							success : function(response){
								me.onWorkOrderSearch();
							}
						});
			},
			sortWorkOrderBySeq : function(workOrder1, workOrder2){
				return workOrder1.data.seq - workOrder2.data.seq;
			},
			onSetFixedEquipCode : function(){
				var me = this;
				var grid = me.getGrid();
				var selection = grid.getSelectionModel().getSelection();
				if (selection && selection.length > 0) {
					var record = selection[0];
					var processId = record.get("processId");
					me.getFixedEquipWin().show();
					var form = me.getFixedEquipFrom();
					var url = 'workOrder/equipListByProcessId'
					var fixedEquipField = form.items.getByKey("fixedEquip");
					var store = Ext.create('bsmes.store.WorkOrderEquipListStore');
					store.getProxy().url = url + "/" + processId;
					fixedEquipField.store = store;
					store.load(function(records, operation, success){
								form.loadRecord(record);
							});
				} else {
					Ext.MessageBox.alert(Oit.msg.ERROR, Oit.error.noRowSelect);
				}

			},
			saveFixedEquipWindow : function(){
				var me = this;
				var form = me.getFixedEquipFrom();
				form.updateRecord();
				Ext.Ajax.request({
							url : 'workOrder/updateFixedEquip',
							method : 'POST',
							params : form.getRecord().data,
							success : function(response){
								me.onSearch();
								me.getFixedEquipWin().hide();
							}
						});

			},
			doRecalculate : function(){
				Ext.MessageBox.confirm('确认', '确认重新计算?', function(btn){
							if (btn == 'yes') {
								var me = this;
								Ext.Ajax.request({
											url : 'workOrder/reScheduleWorkOrder',
											method : 'POST',
											success : function(response){
												Ext.MessageBox.alert(Oit.msg.PROMPT,
														Oit.msg.wip.workOrder.msg.successCalculate
												);
											},
											failure : function(response, action){
												Ext.MessageBox.alert(Oit.msg.PROMPT,
														Oit.msg.wip.workOrder.msg.failureCalculate
												);
											}
										});
							}
						});
			}

		});