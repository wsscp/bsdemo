Ext.define('bsmes.controller.CustomerOrderItemController', {
			extend : 'Oit.app.controller.GridController',
			view : 'customerOrderList',
			deliveryView : 'setDelivery',
			priorityView : 'priorityWindow',
			importOrderView : 'importOrder',
			views : ['CustomerOrderList', 'CustomerOrderItemList', 'LookUpAttachFileWindow', 'SetDelivery',
					'PriorityWindow', 'ImportOrderWindow', 'ProcessInOutList', 'ProcessQcList', 'ProcessReceiptList'],
			stores : ['CustomerOrderStore', 'CustomerOrderItemStore', 'LeftStore', 'RightStore', 'ProcessInOutStore',
					'ProcessReceiptStore', 'ProcessQcStore', 'StockStore'],
			exportUrl : 'customerOrderItem/export/订单信息',
			constructor : function() {
				var me = this;

				// 初始化refs
				me.refs = me.refs || [];

				// editForm
				me.refs.push({
							ref : 'deliveryView',
							selector : me.deliveryView,
							autoCreate : true,
							xtype : me.deliveryView
						});

				me.refs.push({
							ref : 'deliveryForm',
							selector : me.deliveryView + ' form'
						});

				me.refs.push({
							ref : 'priorityView',
							selector : me.priorityView,
							autoCreate : true,
							xtype : me.priorityView
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
							ref : 'leftGridSearchForm',
							selector : me.priorityView + ' leftGridView form'
						});

				me.refs.push({
							ref : 'importOrderView',
							selector : me.importOrderView,
							autoCreate : true,
							xtype : me.importOrderView
						});

				me.refs.push({
							ref : 'importOrderForm',
							selector : me.importOrderView + ' form'
						});

				me.refs.push({
							ref : 'splitOrderItemView',
							selector : me.splitOrderItemView,
							autoCreate : true,
							xtype : me.splitOrderItemView
						});

				me.refs.push({
							ref : 'splitOrderItemForm',
							selector : me.splitOrderItemView + ' splitOrderItemForm'
						});

				me.callParent(arguments);
			},
			init : function() {
				var me = this;

				me.control(me.view + ' button[itemId=importOrder]', {
							click : me.openImportOrderWin
						});

				me.control(me.view + ' button[itemId=setDelivery]', {
							click : me.onSetDelivery
						});

				me.control(me.view + ' button[itemId=setPriority]', {
							click : me.onSetPriority
						});

				me.control(me.view + ' button[itemId=confirmOrderDelivery]', {
							click : me.onConfirmOrderDelivery
						});

				me.control(me.deliveryView + ' button[itemId=ok]', {
							click : me.updateDelivery
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

				me.control(me.priorityView + ' leftGridView' + ' button[itemId=search]', {
							click : me.onLeftGridFormSearch
						});

				me.control(me.importOrderView + ' button[itemId=ok]', {
							click : me.importOrder
						});

				me.control(me.importOrderView + ' button[itemId=downTemplate]', {
							click : me.downTemplate
						});

				me.callParent(arguments);
			},

			/*
			 * onSearch: function(btn) { var me = this; var store = me.getGrid().getStore(); var form =
			 * me.getSearchForm(); var queryStatus = ""; var queryExceedOa = ""; if( form.getValues().orderStatus){
			 * queryStatus = form.getValues().orderStatus.toString(); } if(form.getValues().exceedOa){ queryExceedOa =
			 * form.getValues().exceedOa.toString(); } var findParams = form.getValues(); findParams.queryStatus =
			 * queryStatus; findParams.queryExceedOa = queryExceedOa; store.loadPage(1, { params: {
			 * queryParams:Ext.encode(findParams) } }); },
			 */

			/*
			 * onExport: function() { var me = this; if (!me.exportUrl) { Ext.Error.raise("A view configuration must be
			 * specified!"); } var searchForm = me.getSearchForm(); searchForm.updateRecord(); var queryStatus = ""; var
			 * queryExceedOa = ""; if( searchForm.getValues().orderStatus){ queryStatus =
			 * searchForm.getValues().orderStatus.toString(); } if(searchForm.getValues().exceedOa){ queryExceedOa =
			 * searchForm.getValues().exceedOa.toString(); } var findParams = searchForm.getValues();
			 * findParams.queryStatus = queryStatus; findParams.queryExceedOa = queryExceedOa; var params = [];
			 * Ext.each(me.getGrid().columns, function(column) { if (!column.dataIndex) { return; } params.push({ text :
			 * column['text'], dataIndex : column['dataIndex'] }) }); falseAjaxTarget.document.write('<form
			 * method="post"><input id="params" name="params"><input id="queryParams" name="queryParams"></form>');
			 * falseAjaxTarget.document.getElementById("params").value = JSON.stringify(params);
			 * falseAjaxTarget.document.getElementById("queryParams").value = JSON.stringify(findParams); var form =
			 * falseAjaxTarget.document.forms[0]; form.action = me.exportUrl; form.submit(); },
			 */

			onConfirmOrderDelivery : function() {
				var me = this;
				var record = me.getSelectedData();
				if (record) {
					var data = record[0].data;
					var status = data.status;
					if ((status == 'TO_DO') && data.customerOaDate) {
						Ext.Ajax.request({
									url : 'customerOrderItem/confirmOrderDeliver/' + data.id,
									method : 'POST',
									success : function(response) {
										me.onSearch();
									}
								});
					} else {
						Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.customerOrderItem.msg.confirmOrderMsg);
					}
				}
			},
			onLeftGridFormSearch : function() {
				var me = this;
				var store = me.getLeftGrid().getStore();
				var form = me.getLeftGridSearchForm();
				store.loadPage(1, {
							params : form.getValues()
						});
			},
			onSetDelivery : function() {
				var me = this;
				var data = me.getSelectedData();
				if (data) {
					me.doSetDelivery(data = data[0]);
				}
			},
			doSetDelivery : function(data) {
				var me = this;
				me.getDeliveryView().show();
				me.getDeliveryForm().loadRecord(data);
			},
			updateDelivery : function() {
				var me = this;
				var form = me.getDeliveryForm();
				form.updateRecord();
				if (form.isValid()) {
					var store = me.getGrid().getStore();
					// 同步到服务器
					store.sync();
					// 关闭窗口
					me.getDeliveryView().close();
				}
			},
			onSetPriority : function() {
				var me = this;
				me.getPriorityView().show();

				// ① 因为一个页面有多个grid，grid只init一次，故导致loadgrid拦截器的searchForm混乱，此处手动重置xtype
				gridXtype = me.getLeftGrid().xtype; // 见 ①

				me.getLeftGrid().getStore().reload();
				me.getRightGrid().getStore().reload();
			},
			rightMove : function() {
				var me = this;
				var leftgrid = me.getLeftGrid();
				var rightgrid = me.getRightGrid();
				var leftStore = leftgrid.getStore();
				var rightStore = rightgrid.getStore();
				var selection = leftgrid.getSelectionModel().getSelection();
				if (selection) {
					Ext.Array.each(selection, function(record, i) {
								me.closeSubGrid(record);
								var index = leftStore.indexOf(record);
								leftStore.removeAt(index);
								rightStore.insert(rightStore.getCount() + 1, record);
								leftgrid.getView().refresh();// 刷新行号
								rightgrid.getView().refresh();// 刷新行号
							});
				}
			},
			leftMove : function() {
				var me = this;
				var leftgrid = me.getLeftGrid();
				var rightgrid = me.getRightGrid();
				var leftStore = leftgrid.getStore();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					Ext.Array.each(selection, function(record, i) {
								me.closeSubGrid(record);
								var index = rightStore.indexOf(record);
								rightStore.removeAt(index);
								leftStore.insert(leftStore.getCount() + 1, record);
								leftgrid.getView().refresh();// 刷新行号
								rightgrid.getView().refresh();// 刷新行号
							});
				}
			},
			upMove : function() {
				var me = this;
				var rightgrid = me.getRightGrid();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					var insertIndex = rightStore.indexOf(selection[0]);
					Ext.Array.each(selection, function(record, i) {
								me.closeSubGrid(record);
								var index = rightStore.indexOf(record);
								if (insertIndex > 0) {
									rightStore.removeAt(index);
									rightStore.insert(index - 1, record);
								}
							});
					rightgrid.getView().refresh();
				}
			},
			downMove : function() {
				var me = this;
				var rightgrid = me.getRightGrid();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					var insertIndex = rightStore.indexOf(selection[0]);
					Ext.Array.each(selection, function(record, i) {
								me.closeSubGrid(record);
								var index = rightStore.indexOf(record);
								if (index < rightStore.getCount() - 1) {
									rightStore.removeAt(index);
									rightStore.insert(index + 1, selection);
								}
							});
					rightgrid.getView().refresh();// 刷新行号
				}
			},
			topMove : function() {
				var me = this;
				var rightgrid = me.getRightGrid();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					Ext.Array.each(selection, function(record, i) {
								me.closeSubGrid(record);
								var index = rightStore.indexOf(record);
								rightStore.removeAt(index);
								rightStore.insert(i, record);
							});
					rightgrid.getView().refresh();// 刷新行号
				}
			},
			endMove : function() {
				var me = this;
				var rightgrid = me.getRightGrid();
				var rightStore = rightgrid.getStore();
				var selection = rightgrid.getSelectionModel().getSelection();
				if (selection) {
					Ext.Array.each(selection, function(record, i) {
								me.closeSubGrid(record);
								var index = rightStore.indexOf(record);
								rightStore.removeAt(index);
								rightStore.insert(rightStore.getCount() + i, record);
							});
					rightgrid.getView().refresh();// 刷新行号
				}
			},
			setPrioritySave : function() {
				var me = this;
				var rightStore = me.getRightGrid().getStore();
				var leftStore = me.getLeftGrid().getStore();
				// 更新右边列表数据
				var rightResult = new Array();
				var rightData = rightStore.data.items;
				if (rightData) {
					Ext.each(rightData, function(record, i) {
								var data = record.getData();
								data.seq = i;
								rightResult.push(data);
							});
				}

				// 更新左边列表数据
				var leftResult = new Array();
				var leftData = leftStore.data.items;
				if (leftData) {
					Ext.each(leftData, function(record, i) {
								var data = record.getData();
								leftResult.push(data);
							});
				}
				Ext.Ajax.request({
							url : '/bsmes/pla/customerOrderItem/updateSeq',
							method : 'POST',
							params : {
								'rightJsonText' : Ext.encode(rightResult),
								'leftJsonText' : Ext.encode(leftResult)
							},
							success : function(response) {
								me.getPriorityView().close();
								// ① 因为一个页面有多个grid，grid只init一次，故导致loadgrid拦截器的searchForm混乱，此处手动重置xtype
								gridXtype = me.getGrid().xtype; // 见 ①
								me.onSearch();
							}
						});
			},
			closePriorityWindow : function() {
				var me = this;
				me.getPriorityView().close();
			},
			openImportOrderWin : function() {
				var me = this;
				me.getImportOrderView().show();
			},
			importOrder : function() {
				var me = this;
				var form = me.getImportOrderForm();
				console.log(form);

				if (form.isValid()) {
					form.submit({
								waitMsg : '正在导入计划,请耐心等待...',
								success : function(form, action) {
									var result = Ext.decode(action.response.responseText);
									Ext.Msg.alert(Oit.msg.PROMPT, result.message);
									me.getImportOrderView().close();
									me.onSearch();
								},
								failure : function(form, action) {
									var result = Ext.decode(action.response.responseText);
									Ext.Msg.alert(Oit.msg.WARN, result.message);
								}
							});
				}
			},

			downTemplate : function() {
				falseAjaxTarget.document.write('<form method="post"></form>');
				var form = falseAjaxTarget.document.forms[0];
				form.action = 'customerOrderItem/downImportTemplate';
				form.submit();
			},

			closeSubGrid : function(record) {
				var parent = document.getElementById(record.get('contractNo'));
				var child = parent.firstChild;
				while (child) {
					child.parentNode.removeChild(child);
					child = child.nextSibling;
				}
			}
		});