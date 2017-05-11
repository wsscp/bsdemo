Ext.define('bsmes.view.OrderStatisticsWindows', {
	extend : 'Ext.container.Container',
	id : "OrderStatisticsWindows",
	alias : 'widget.orderStatisticsWindows',
	layout : 'vbox',
	items : [{
				xtype : 'toolbar',
				dock : 'top',
				width : '100%',
				layout : 'vbox',
				items : [{
							title : '查询条件',
							xtype : 'fieldset',
							collapsible : true,
							width : '100%',
							items : [{
										xtype : 'form',
										id : 'myform',
										width : '100%',
										buttonAlign : 'left',
										labelAlign : 'right',
										bodyPadding : 5,
										defaults : {
											layout : {
												type : 'table',
												columns : 5
											}
										},
										items : [{
													items : [{
														fieldLabel : '工段',
														allowBlank : false,
														labelAlign : 'right',
														labelWidth : 80,
														xtype : 'combobox',
														name : 'section',
														//id : 'modelId',
														displayField : 'section',
														valueField : 'section',
														defaultValue : '',
														triggerAction : 'all',
														store : new Ext.data.Store({
															fields : ['section'],
															proxy : {
																type : 'rest',
																url : 'orderStatistics/getSection'
															}
														}),
														//listeners : {}
								
													},{
														fieldLabel : '工序',
														allowBlank : false,
														labelAlign : 'right',
														labelWidth : 80,
														xtype : 'combobox',
														name : 'name',
														displayField : 'name',
														valueField : 'name',
														defaultValue : '',
														triggerAction : 'all',
														store : new Ext.data.Store({
															fields : ['name'],
															proxy : {
																type : 'rest',
																url : 'orderStatistics/getName'
															}
														}),
														//listeners : {}
								
													},{
														fieldLabel : '开始日期',
														labelAlign : 'right',
														id : 'sdate',
														labelWidth : 100,
														xtype : 'datefield',
														name : 'startTime',
														format : 'Y-m-d',
														value : Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -1), "Y-m-d"),
														listeners: {
															"select": function () {
																var startDate = Ext.getCmp('sdate').getValue();
											                    var endDate = Ext.getCmp('edate').getValue();
											                    if (startDate > endDate) {
											                    	 Ext.MessageBox.alert("提示","开始时间不能大于结束时间");    
											                        Ext.getCmp('sdate').setValue("");
											                    }
															}
														},
													}, {
														fieldLabel : '结束日期',
														labelAlign : 'right',
														id : 'edate',
														labelWidth : 100,
														xtype : 'datefield',
														name : 'endTime',
														format : 'Y-m-d',
														value : Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH), "Y-m-d"),
														listeners: {
															"select": function () {
																var startDate = Ext.getCmp('sdate').getValue();
											                    var endDate = Ext.getCmp('edate').getValue();
											                    if (startDate > endDate) {
											                    	 Ext.MessageBox.alert("提示","开始时间不能大于结束时间");    
											                        Ext.getCmp('edate').setValue("");
											                    }
															}
														}
														
													},{
														fieldLabel :'班次',
														labelAlign : 'right',
														xtype : 'checkboxgroup',
														width : 600,
														columns : 7,
														vertical : true,
														items : [{
																	boxLabel : '早班',
																	name : 'shiftNameList',
																	inputValue : '早班',
																	checked : true
																}, {
																	boxLabel : '中班',
																	name : 'shiftNameList',
																	inputValue : '中班',
																	checked : true
																}, {
																	boxLabel : '夜班',
																	name : 'shiftNameList',
																	inputValue : '夜班',
																	checked : true
																}]
													}]
												}],
										buttons : [{
													itemId : 'search',
													iconCls : 'icon_search',
													text : Oit.btn.search
												}, {
													itemId : 'reset',
													text : Oit.btn.reset,
													iconCls : 'icon_reset',
													handler : function(e) {
														this.up("form").getForm().reset();
													}
												}
						
										]
									}]
						}, {
							id : "mypanel",
							xtype : 'tabpanel',
							width : '100%',
							height : document.body.scrollHeight - 135,
							items : [{
										title : '结果',
										width : '100%',
										autoLoad : true,
										xtype : 'orderStatisticsList',
										itemId : 'orderStatisticsList'
									}, {
										title : '柱状图',
										id : 'myChart',
										width : '100%',
										height : document.body.scrollHeight - 150,
										autoLoad : true,
										xtype : 'panel'
									}]
						}
				
						]
	}],
	initComponent : function() {
		var me = this;
		me.callParent(arguments);
		var mypanel = Ext.ComponentQuery.query('#mypanel')[0];
		mypanel.setActiveTab(1);
		//mypanel.setActiveTab(2);
		mypanel.setActiveTab(0);

	}

	
	
});