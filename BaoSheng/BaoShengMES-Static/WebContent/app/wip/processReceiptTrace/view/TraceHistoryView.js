Ext.define("bsmes.view.TraceHistoryView", {
			extend : 'Ext.window.Window',
			alias : 'widget.traceHistoryView',
			width : document.body.scrollWidth - 5,
			height : document.body.scrollHeight - 5,
			maximizable : true,
			title : "历史数据曲线图",
			autoScroll : true,
			overflowY : 'auto',
			modal : true,
			closeAction : "hide",
			plain : true,
			requires : ['Ext.chart.Chart', 'bsmes.dateTime.DateTimeField'],
			initComponent : function() {
				var me = this;

				me.items = [{
							itemId : 'historySearchForm',
							xtype : 'form',
							layout : 'hbox',
							defaults : {
								labelAlign : 'right'
							},
							margin : '5 0 0 5',
							items : [{
										xtype : 'hiddenfield',
										name : 'equipCode'
									}, {
										xtype : 'hiddenfield',
										name : 'processCode'
									}, {
										xtype : 'hiddenfield',
										name : 'receiptCode'
									}, {
										xtype : 'hiddenfield',
										name : 'type'
									}, {
										fieldLabel : Oit.msg.wip.processReceiptTrace.startTime,
										xtype : 'dateTimeField',
										name : 'startTime',
										id : 'processReceiptTrace_startTime',
										dateFormat : "Y-m-d H:i:s",
										emptyText : '请选择起始时间',
										blankText : '请输入起始时间',

										allowBlank : false
									}, {
										fieldLabel : Oit.msg.wip.processReceiptTrace.endTime,
										xtype : 'dateTimeField',
										name : 'endTime',
										id : 'processReceiptTrace_endTime',
										dateFormat : "Y-m-d H:i:s",
										emptyText : '请选择结束时间',
										blankText : '请输入结束时间',
										allowBlank : false
									}, {
										width : 5
									}, {
										itemId : 'historySearch',
										xtype : 'button',
										text : Oit.btn.search
									}, {
										width : 5
									}, {
										itemId : 'exportToXls',
										xtype : 'button',
										text : '导出'
									}]
						}, {
							xtype : 'panel',
							id : 'receiptDataHighChart',
							width : document.body.scrollWidth - 20,
							height : document.body.scrollHeight - 115
						}]
				me.callParent(arguments);
			},

			buttons : [{
						text : Oit.btn.close,
						handler : function() {
							// chart.hide();
							this.up('.window').hide();
						}
					}]

		});