Ext.define('bsmes.controller.OrderOAController', {
			extend : 'Oit.app.controller.GridController',
			view : 'orderOAList',
			deliveryView:'setDelivery',
			views : ['OrderOAList','SetDelivery'],
			stores : ['OrderOAStore'],
			exportUrl : 'orderOA/export/订单OA结果',
			constructor: function() {
				var me = this;
				me.refs = me.refs || [];
				
				// editForm
				me.refs.push({
					ref: 'deliveryView', 
					selector: me.deliveryView, 
					autoCreate: true, 
					xtype: me.deliveryView
				});
				
				me.refs.push({
					ref: 'deliveryForm', 
					selector: me.deliveryView + ' form'
				});
				
				me.callParent(arguments);
			},
			init : function() {
				var me = this;
				me.control(me.view + ' button[itemId=search]', {
							click : me.onSearch
						});
				me.control(me.view + ' button[itemId=mrpView]', {
							click : me.openMrpView
						});
				me.control(me.view + ' button[itemId=export]', {
							click : me.onExport
						});
				me.control(me.view + ' button[itemId=setDelivery]', {
					click: me.onSetDelivery
				});
				me.control(me.deliveryView + ' button[itemId=ok]', {
					click: me.updateDelivery
				});
				me.control(me.view + ' button[itemId=orderOAResource]', {
							click : me.onOrderOAResource
						});
				me.control(me.view + ' button[itemId=resourceUsePlan]', {
							click : me.onResourceUsePlan
						});
				me.control(me.view + ' button[itemId=recalculate]', {
							click : me.doRecalculate
						});
			},
			onSetDelivery:function(){
				var me = this;
				var data = me.getSelectedData();
				if(data){
					me.doSetDelivery(data = data[0]);
				}
			},
			doSetDelivery:function(data){
				var me = this;
				me.getDeliveryView().show();
				me.getDeliveryForm().loadRecord(data);
			},
			updateDelivery:function(){
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
			openMrpView : function() {
				parent.openTab(Oit.msg.pla.orderOA.mrp.title, 'pla/orderOAMrp.action');
			},
			onExport : function() {
				var me = this;
				if (!me.exportUrl) {
					Ext.Error.raise("A view configuration must be specified!");
				}
				var searchForm = me.getSearchForm();
				searchForm.updateRecord();

				var queryStatus = "";
				if (searchForm.getValues().orderItemStatus) {
					queryStatus = searchForm.getValues().orderItemStatus.toString();
				}
				var findParams = searchForm.getValues();
				findParams.queryStatus = queryStatus;

				falseAjaxTarget.document.write('<form method="post"><input id="queryParams" name="queryParams"></form>');
				falseAjaxTarget.document.getElementById("queryParams").value = JSON.stringify(findParams);
				var form = falseAjaxTarget.document.forms[0];
				form.action = me.exportUrl;
				form.submit();
			},
			onOrderOAResource : function() {
				parent.openTab(Oit.msg.pla.orderOA.resourceGantt, 'fac/workTask.action?type=0');
			},
			onResourceUsePlan : function() {
				parent.openTab(Oit.msg.pla.orderOA.resourceUsePlan, 'fac/workTask.action?type=1');
			},
			doRecalculate : function() {
				Ext.MessageBox.confirm('确认', '确认重新计算?', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
											url : 'orderOA/calculateOA',
											method : 'POST',
											success : function(response) {
												Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.pla.orderOA.msg.successCalculate);
											},
											failure : function(response, action) {
												Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.pla.orderOA.msg.failureCalculate);
											}
										});
							}
						});
			}
		});