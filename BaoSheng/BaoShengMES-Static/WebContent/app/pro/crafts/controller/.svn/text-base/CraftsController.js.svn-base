Ext.define('bsmes.controller.CraftsController', {
			extend : 'Oit.app.controller.GridController',
			view : 'craftsList',
			views : ['CraftsList', 'InstanceProcessWindow'],
			stores : ['CraftsStore', 'ProcessInOutStore', 'ProcessMatPropStore', 'ProcessQcStore'],
			exportUrl : 'craftsBz/export/产品工艺',
			constructor : function() {
				var me = this;

				// 初始化refs
				me.refs = me.refs || [];

				me.refs.push({
							ref : 'instanceProcessWindow',
							selector : 'instanceProcessWindow',
							autoCreate : true,
							xtype : 'instanceProcessWindow'
						});

				me.callParent(arguments);
			},
			init : function() {
				var me = this;
				if (!me.view) {
					Ext.Error.raise("A view configuration must be specified!");
				}
				// 初始化工具栏
				me.control(me.view + ' button[itemId=processDetail]', {
							click : me.showProcessWindow
						});

				me.callParent(arguments);
			},
			/**
			 * 显示工序列表
			 */
			showProcessWindow : function() {
				var me = this;
				var selection = me.getGrid().getSelectionModel().getSelection();
				if (selection && selection.length == 1) {
					var record = selection[0];

					// craftsId, craftsCode, salesOrderItemId, isWip 
					Ext.Msg.wait(Oit.msg.LOADING, Oit.msg.PROMPT);
					Ext.Ajax.request({
								url : 'processBz/getByCraftsId?craftsId=' + record.get('id'),
								success : function(response) {
									Ext.Msg.hide(); // 隐藏进度条
									var result = Ext.decode(response.responseText);
									var processArray = [], processId = null;
									Ext.Array.each(result, function(record, i) {
												processId = (processId ? processId : record.id);
												processArray.push({
															text : record.seq + '-' + record.processName + '['
																	+ record.processCode + ']',
															id : record.id,
															leaf : true
														});
											});

									var win = me.getInstanceProcessWindow({
												title : '工艺相关数据查看：' + record.get('craftsCode'),
												spencialParam : {
													processId : processId, // 给个初始的processId
													processArray : processArray
												}
											});
									win.show();
								}
							});
				} else {
					Ext.Msg.alert(Oit.msg.WARN, '请选择一条记录！');
				}
			}
		});
