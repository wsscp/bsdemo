Ext.define("bsmes.view.OrderStatisticsList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.orderStatisticsList',
			hasPaging : false,
			columnLines : true,
			defaultEditingPlugin : false,
			id : 'orderGrid',
			store : 'OrderStatisticsStore',
			// forceFit : false,
			selModel : {
				mode : 'MULTI'
			},
			columns : [{
						text : 'id',
						width : 50,
						dataIndex : 'id',
						hidden : true
					}, {
						text : '工段',
						width : 50,
						dataIndex : 'section'
					}, {
						text : '工序',
						width : 50,
						dataIndex : 'name'

					}, {
						text : '生产总量',
						width : 50,
						dataIndex : 'sumLength'
					}],
			dockedItems : [{
						xtype : 'toolbar',
						hidden : true,
						dock : 'top',
						items : [{
									title : '查询条件',
									xtype : 'fieldset',
									collapsible : true,
									width : '99.3%',
									items : [{
												xtype : 'form',
												layout : 'column',
												defaults : {
													width : 300,
													padding : 1,
													labelAlign : 'right',
													xtype : 'textfield'
												},
												items : [{
															name : 'section'
														},{
															name : 'startTime'
														}, {
															name : 'endTime'
														}, {
															name : 'shiftName'
														}]
											}]
								}

						]
					}],
			initComponent : function() {
				me = this;
				me.callParent(arguments);
			}
		});
