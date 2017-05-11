Ext.define("bsmes.view.MonthPersonalMonitorGrid", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.monthPersonalMonitorGrid',
			itemId : 'monthPersonalMonitorGrid',
			store : 'MonthMonitorStore',
			forceFit : false,
			defaultEditingPlugin : false,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight - 36,
			columns : [{
						flex : 1.2,
						minWidth : 60,
						sortable : false,
						menuDisabled : true,
						text : '机台',
						dataIndex : 'equipAlias'
					}, {
						flex : 1.5,
						minWidth : 75,
						sortable : false,
						menuDisabled : true,
						text : '人员',
						dataIndex : 'userName'
					}, {
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						text : '总时间(小时)',
						dataIndex : 'totalTime'
					}, {
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						text : '违纪现象',
						dataIndex : 'disciplinePhenomenon'
			}],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
							title : '查询条件',
							xtype : 'fieldset',
							collapsible : true,
							width : '100%',
							items : [{
										xtype : 'hform',
										width : '100%',
//										layout : 'vbox',
										buttonAlign : 'left',
										labelAlign : 'right',
										bodyPadding : 5,
										items : [{
													fieldLabel : '查询年月',
													xtype : 'datefield',
													name : 'yearMonth',
													id : 'yearMonth',
													format : 'Y-m',
													labelWidth : 60,
													firstLoad : true,
													width : 250
												},{
													fieldLabel: "机台",
											        name: 'code',
											        xtype:'combobox',
											        allowBlank:false,
											        labelWidth : 45,
											        width : 250,
											        displayField:'equipAlias',
											        valueField: 'code',
											        store:new Ext.data.Store({
											        	fields:['code','equipAlias'],
											        	autoLoad:false,
											        	proxy:{
											        		type: 'rest',
											        		url:''
											        	},
											        	sorters : [{
															property : 'code',
															direction : 'ASC'
														}]
											        }),
											        listeners:{
														expand:function(){
															var date = Ext.util.Format.date(Ext.getCmp('yearMonth').getValue(),'Y-m');
															console.log(date);
															var store = Ext.ComponentQuery.query('monthPersonalMonitorGrid combobox')[0].getStore();
															store.proxy.url = 'personalMonitor/getMonthEquipCode?yearMonth='+date;
															store.load();
														}
											        }
												},{
													fieldLabel: "员工",
											        name: 'userCode',
											        xtype:'combobox',
											        labelWidth : 45,
											        width : 250,
											        allowBlank:false,
											        displayField:'userName',
											        valueField: 'userCode',
											        store:new Ext.data.Store({
											        	fields:['userCode','userName'],
											        	autoLoad:false,
											        	proxy:{
											        		type: 'rest',
											        		url:''
											        	},
											        	sorters : [{
															property : 'userCode',
															direction : 'ASC'
														}]
											        }),
											        listeners:{
														expand:function(){
															var date = Ext.util.Format.date(Ext.getCmp('yearMonth').getValue(),'Y-m');
															console.log(date);
															var store = Ext.ComponentQuery.query('monthPersonalMonitorGrid combobox')[1].getStore();
															store.proxy.url = 'personalMonitor/getMonthUserCode?yearMonth='+date;
															store.load();
														}
											        }
												}],
										buttons : [{
													itemId : 'searchMonthReport',
													text : '查找'
												}]
									}]
						}]
			}],		
			initComponent : function() {
				var me = this;

				this.callParent(arguments);
//				设置默认查询时间
				me.down('form').form.findField('yearMonth').setValue(Ext.util.Format.date(new Date(), "Y-m"));
				console.log(me.down('form').form.findField('yearMonth').getValue());
//				me.getStore().reload();

			}
});