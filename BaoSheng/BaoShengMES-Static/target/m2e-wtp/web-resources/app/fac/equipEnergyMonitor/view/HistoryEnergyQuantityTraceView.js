Ext.define("bsmes.view.HistoryEnergyQuantityTraceView", {
			extend : 'Ext.window.Window',
			alias : 'widget.historyEnergyQuantityTraceView',
			id : 'historyEnergyQuantityTraceView',
			width : document.body.scrollWidth - 5,
			height : document.body.scrollHeight - 5,
			maximizable : true,
			title : "历史数据曲线图",
			autoScroll : true,
			overflowY : 'auto',
			modal : true,
			closeAction : "hide",
			plain : true,
			requires : ['bsmes.dateTime.DateTimeField'],
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
										width : 5
									},{
										fieldLabel : '开始时间',
										xtype : 'dateTimeField',
										name : 'startTime',
										id : 'equipEnergyMonitor_startTime',
										dateFormat : "Y-m-d H:i:s",
										allowBlank : false,
										value :  Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,-1), "Y-m-d 00:00:00")
									}, {
										fieldLabel : '结束时间',
										xtype : 'dateTimeField',
										name : 'endTime',
										id : 'equipEnergyMonitor_endTime',
										dateFormat : "Y-m-d H:i:s",
										allowBlank : false,
										value : Ext.util.Format.date(new Date(), "Y-m-d H:i:s")
									} , {
										width : 5
									} , {
										itemId : 'historySearch',
										xtype : 'button',
										text : Oit.btn.search
									}]
						}, {
							xtype : 'panel',
							id : 'energyHistoryChart',
							width : document.body.scrollWidth - 20,
							height : document.body.scrollHeight - 115
						}]
				me.callParent(arguments);
			},

			buttons : [{
						text : Oit.btn.close,
						handler : function() {
							this.up('.window').hide();
						}
					}]

		});