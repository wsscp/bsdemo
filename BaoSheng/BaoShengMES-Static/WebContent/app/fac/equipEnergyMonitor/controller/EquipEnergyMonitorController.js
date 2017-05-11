Ext.define("bsmes.controller.EquipEnergyMonitorController",{
	extend : 'Oit.app.controller.GridController',
	view : 'equipEnergyMonitorPanel',
	equipEnergyLoad : 'equipEnergyLoad',
	equipEnergyMonitorList:'equipEnergyMonitorList',
	equipEnergyQuantity : 'equipEnergyQuantity',
	historyEnergyTraceView : 'historyEnergyTraceView',
	historyEnergyLoadTraceView : 'historyEnergyLoadTraceView',
	equipMonthEnergyLoad : 'equipMonthEnergyLoad',
	equipMonthEnergyQuantity : 'equipMonthEnergyQuantity',
	historyEnergyQuantityTraceView : 'historyEnergyQuantityTraceView',
	stores : ['EquipEnergyMonitorStore','ProductReportStore','EquipEnergyLoadStore','EquipEnergyQuantityStore','EquipMonthEnergyLoadStore','EquipMonthEnergyQuantityStore'],
	views : ['EquipEnergyMonitorPanel','EquipEnergyMonitorList','ProductReportDetailsView','ProductReportDetailGrid','HistoryEnergyTraceView','EquipEnergyLoad','EquipEnergyQuantity','EquipMonthEnergyLoad','EquipMonthEnergyQuantity','HistoryEnergyLoadTraceView','HistoryEnergyQuantityTraceView'],
	constructor : function(){
		var me = this;
		// 初始化refs
		me.refs = me.refs || [];
		
		me.refs.push({
			ref : 'equipEnergyMonitorPanel',
			selector : '#equipEnergyMonitorPanel'
		});
		
		me.refs.push({
					ref : 'productReportDetailsView',
					selector : 'productReportDetailsView',
					autoCreate : true,
					xtype : 'productReportDetailsView'
				});
				
		me.refs.push({
					ref : 'equipEnergyLoad',
					selector : 'equipEnergyLoad',
					autoCreate : true,
					xtype : 'equipEnergyLoad'
				});
				
		
				
		me.refs.push({
					ref : 'equipEnergyQuantity',
					selector : 'equipEnergyQuantity',
					autoCreate : true,
					xtype : 'equipEnergyQuantity'
				});
				
		me.refs.push({
					ref : 'productReportDetailGrid',
					selector : 'productReportDetailGrid',
					autoCreate : true,
					xtype : 'productReportDetailGrid'
				});
				
		me.refs.push({
					ref : 'historyEnergyTraceView',
					selector : 'historyEnergyTraceView',
					autoCreate : true,
					xtype : 'historyEnergyTraceView'
				});
				
				
		me.refs.push({
					ref : 'historyEnergyLoadTraceView',
					selector : 'historyEnergyLoadTraceView',
					autoCreate : true,
					xtype : 'historyEnergyLoadTraceView'
				});
		
		me.refs.push({
					ref : 'historyEnergyQuantityTraceView',
					selector : 'historyEnergyQuantityTraceView',
					autoCreate : true,
					xtype : 'historyEnergyQuantityTraceView'
				});
				
		me.refs.push({
					ref : 'equipEnergyMonitorList',
					selector : 'equipEnergyMonitorList',
					autoCreate : true,
					xtype : 'equipEnergyMonitorList'
				});				
		
		me.refs.push({
					ref : 'historyForm',
					selector : me.historyEnergyTraceView + " form"
				});
		
		me.refs.push({
					ref : 'searchForm',
					selector : me.equipEnergyMonitorList + " form"
				});
				
		me.callParent(arguments);
		
		setInterval(function(){
					me.refresh.apply(me);
				}, 900000);
	},
	init : function(){
		var me = this;
		me.control(me.equipEnergyMonitorList + ' button[itemId=realTimeCurve]', {
							click : me.openRealTimeCurve
						});
				
		me.control(me.equipEnergyLoad + ' button[itemId=realTimeCurve]', {
							click : me.checkRealTimeCurve
						});
					
		me.control(me.historyEnergyTraceView+' button[itemId=historySearch]',{
							click : me.onHistorySearch
						});
		
		me.control(me.historyEnergyLoadTraceView+' button[itemId=historySearch]',{
							click : me.onHistoryLoadSearch
						});
						
		me.control(me.historyEnergyQuantityTraceView+' button[itemId=historySearch]',{
							click : me.onHistoryQuantitySearch
						});
						
		me.control(me.equipEnergyMonitorList + ' button[itemId=equipEnergyInfo]',{
							click : me.openEquipEnergyInfo
						});
		me.control(me.equipEnergyQuantity + ' button[itemId=realTimeCurve]', {
							click : me.openRealTimeCurve1
						});
	},

	
	
	onHistorySearch : function(){
		var me = this ;
		var grid = me.getEquipEnergyMonitorList();
		var selection = grid.getSelectionModel().getSelection();
		var equipName = selection[0].data.EQUIP_NAME
		var startTime = Ext.getCmp("equipEnergyMonitor_startTime").getValue();
		var endTime  = Ext.getCmp("equipEnergyMonitor_endTime").getValue();
		
		if (endTime < startTime) {
			Ext.MessageBox.alert("提示", "开始时间必须小于结束时间!");
			return;
		}
		
		Ext.Ajax.request({
				url : 'equipEnergyMonitor/realReceiptChart',
				method:'POST',
				params : {
					equipName : selection[0].data.EQUIP_NAME,
					startTime : Ext.getCmp("equipEnergyMonitor_startTime").getValue(),
					endTime : Ext.getCmp("equipEnergyMonitor_endTime").getValue()
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					me.createHighChart(result,equipName);
				}
		})
	},
	
	onHistoryLoadSearch : function(){
		var me = this ;
		var grid = me.getEquipEnergyLoad();
		var selection = grid.getSelectionModel().getSelection();
		var equipName = selection[0].data.EQUIP_NAME
		var startTime = Ext.getCmp("equipEnergyMonitor_startTime").getValue();
		var endTime  = Ext.getCmp("equipEnergyMonitor_endTime").getValue();
		
		if (endTime < startTime) {
			Ext.MessageBox.alert("提示", "开始时间必须小于结束时间!");
			return;
		}
		
		Ext.Ajax.request({
				url : 'equipEnergyMonitor/realReceiptChart',
				method:'POST',
				params : {
					equipName : selection[0].data.EQUIP_NAME,
					startTime : Ext.getCmp("equipEnergyMonitor_startTime").getValue(),
					endTime : Ext.getCmp("equipEnergyMonitor_endTime").getValue()
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					me.createHighChart(result,equipName);
				}
		})
	},
	
	onHistoryQuantitySearch: function(){
		var me = this ;
		var grid = me.getEquipEnergyQuantity();
		var selection = grid.getSelectionModel().getSelection();
		var equipName = selection[0].data.EQUIP_NAME
		var startTime = Ext.getCmp("equipEnergyMonitor_startTime").getValue();
		var endTime  = Ext.getCmp("equipEnergyMonitor_endTime").getValue();
		
		if (endTime < startTime) {
			Ext.MessageBox.alert("提示", "开始时间必须小于结束时间!");
			return;
		}
		
		Ext.Ajax.request({
				url : 'equipEnergyMonitor/realReceiptChart',
				method:'POST',
				params : {
					equipName : selection[0].data.EQUIP_NAME,
					startTime : Ext.getCmp("equipEnergyMonitor_startTime").getValue(),
					endTime : Ext.getCmp("equipEnergyMonitor_endTime").getValue()
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					me.createHighChart(result,equipName);
				}
		})
	},
	openRealTimeCurve : function(){
		var me = this;
		var grid = me.getEquipEnergyMonitorList();
		var selection = grid.getSelectionModel().getSelection();
		if(selection && selection.length>0){
			var win = me.getHistoryEnergyTraceView();
			win.show();
			me.createRealChart(selection[0].data, 'energyHistoryChart');
		}else{
			Ext.Msg.alert(Oit.msg.WARN, '请选择一条记录');
			return;
		}
	},
	createHighChart : function(result,equipName) {
				$('#energyHistoryChart').highcharts({
				chart : {
					type : 'spline',
					animation : Highcharts.svg, // don't animate in old IE
					marginRight : 10
				},
				title : {
					text : equipName
				},
				xAxis : {
					type : 'datetime',
					labels : {
						step : 1,
						formatter : function() {
							
							var date = new Date(this.value)
							return Ext.util.Format.date(date, 'Y-m-d H:i:s');
						}
					}
				},
				credits : {
					text : ''
				},
				yAxis : {
					title : {
						text : ''
					}
				},
				tooltip : {
					formatter : function() {
						var date = new Date(this.x);
						return '<b>' + this.series.name + ":" + this.y + '</b><br>时间:'
								+ Ext.util.Format.date(date, 'Y-m-d H:i:s') + '<br>';
					}
				},
				exporting : {
					enabled : false
				},
				series : [{
							name : '负荷',
							data : result.realdata,
							color : 'red'
						}]
			});
	},
	
	checkRealTimeCurve : function(){
		var me = this;
		var grid = me.getEquipEnergyLoad();
		var selection = grid.getSelectionModel().getSelection();
		if(selection && selection.length>0){
			var win = me.getHistoryEnergyLoadTraceView();
			win.show();
			me.createRealChart(selection[0].data, 'energyHistoryChart');
		}else{
			Ext.Msg.alert(Oit.msg.WARN, '请选择一条记录');
			return;
		}
	},
	openRealTimeCurve1 : function(){
		var me = this;
		var grid = me.getEquipEnergyQuantity();
		var selection = grid.getSelectionModel().getSelection();
		if(selection && selection.length>0){
			var win = me.getHistoryEnergyQuantityTraceView();
			win.show();
			me.createRealChart(selection[0].data, 'energyHistoryChart');
		}else{
			Ext.Msg.alert(Oit.msg.WARN, '请选择一条记录');
			return;
		}
	},
	createRealChart : function(data,panelId){
				var me = this;
				var receiptName = data.EQUIP_NAME;
				var type = '';
				if (receiptName != null && receiptName != '') {
					type = 'receipt';
				}
				var real_title =  receiptName;
				Ext.Ajax.request({
							url : 'equipEnergyMonitor/realReceiptChart',
							method:'POST',
							params : {
								equipName : data.EQUIP_NAME,
								startTime : Ext.getCmp("equipEnergyMonitor_startTime").getValue(),
								endTime : Ext.getCmp("equipEnergyMonitor_endTime").getValue()
							},
							success : function(response) {
								if (response.responseText) {
									var result = Ext.decode(response.responseText);
								} else {
									return;
								}
								$('#' + panelId).highcharts({
									chart : {
										type : 'spline',
										animation : Highcharts.svg, // don't animate in old IE
										marginRight : 10
									},
									title : {
										text : real_title
									},
									xAxis : {
										type : 'datetime',
										labels : {
											step : 1,
											formatter : function() {
												
												var date = new Date(this.value)
												return Ext.util.Format.date(date, 'Y-m-d H:i:s');
											}
										}
									},
									credits : {
										text : ''
									},
									yAxis : {
										title : {
											text : 'kw/h'
										}
									},
									tooltip : {
										formatter : function() {
											var date = new Date(this.x);
											return '<b>' + this.series.name + ":" + this.y + '</b><br>时间:'
													+ Ext.util.Format.date(date, 'Y-m-d H:i:s') + '<br>';
										}
									},
									exporting : {
										enabled : false
									},
									series : [{
												name : '负荷',
												data : result.realdata,
												color : 'red'
											}]
								});
							}
						});
			},
	refresh : function(){
		var me = this;
		me.getGrid().getStore().load();
	}
	
})
