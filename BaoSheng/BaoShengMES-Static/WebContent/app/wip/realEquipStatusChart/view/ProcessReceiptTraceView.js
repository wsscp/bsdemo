Ext.define("bsmes.view.ProcessReceiptTraceView", {
			extend : 'Ext.panel.Panel',
			alias : 'widget.processReceiptTraceView',
			autoScroll : true,
			overflowY : 'auto',
			modal : true,
			plain : true,
			maximizable : true,
			layout : 'hbox',
			width : document.body.scrollWidth,
			initComponent : function() {
				var me = this;
				me.items = [{
							xtype : 'grid',
							itemId : 'processReceiptGrid',
							width : "19.9%",
							minwidth : 200,
							height : document.body.scrollHeight - 70,
							autoScroll : true,
							overflowY : 'auto',
							forceFit : true,
							controller : {},
							store : 'ProcessReceiptTraceStore',
							columns : [{
										text : "工艺参数名称",
										dataIndex : 'receiptName',
										flex : 2.4
									}, {
										text : "值",
										flex : 1.6,
										dataIndex : 'daValue'
									}],
							tbar : [{
										xtype : 'button',
										text : '刷新',
										handler : function(){
										    Ext.ComponentQuery.query('#processReceiptGrid')[0].getStore().load();
										}
									}, {
										xtype : 'button',
										itemId : 'historySearch',
										text : '历史数据曲线图'
									}, {
										xtype : 'button',
										itemId : 'realSearch',
										hidden : true,
										text : '实时数据曲线图'
									}],
							setController : function(controller) {
								var me = this;
								me.controller = controller;
							},
							getController : function() {
								var me = this;
								return me.controller;
							}
						}, {
							xtype : 'panel',
							id : 'realReceiptChart_id1',
							width : "80%",
							height : document.body.scrollHeight - 70,
							layout : {
								align : 'middle',
								pack : 'center',
								type : 'hbox'
							},
							items : [{
										xtype : 'displayfield',
										value : '<font style="font-size: 16px;margin:0 auto;">实 时 数 据 曲 线 图：请选择一个设备</font>'
									}]
						}];
				me.callParent(arguments);
			}
		});
