Ext.define("bsmes.controller.ProductTraceController", {
			extend : 'Oit.app.controller.GridController',
			view : 'productTraceGrid',
			views : ['ProductTraceGrid', 'ProductTraceDeatilWindow'],
			stores : ['ProductTraceStore'],
			init : function() {
				var me = this;
			},
			onSearch : function() {
			},

			onHistorySearch : function() {
				var me = this;
				var form = me.getHistoryForm();
				var formRecord = form.getRecord();

				Ext.Ajax.request({
							url : 'processReceiptTrace/historyTrace',
							params : form.getValues(),
							success : function(response) {
								var result = Ext.decode(response.responseText);
								me.createHighChart(result);
							}
						});
			},

			createHighChart : function(result) {
				var me = this;
				var historyData = result.historyData;
				var maxList = result.maxList;
				var minList = result.minList;
				var record = me.getHistoryForm().getRecord();
				console.log(record)
				var equipCombs = Ext.getCmp('equipNameAll');
				var names = equipCombs.rawValue + " " + record.get('receiptName');
				$('#receiptDataHighChart').highcharts({
					chart : {
						type : 'spline'
					},
					title : {
						text : names
					},
					xAxis : {
						type : 'datetime',
						labels : {
							step : 1,
							formatter : function() {
								var date = new Date(this.value)
								return Ext.util.Format.date(date, 'm月d日 H:i');
							}
						}
					},
					credits : {
						text : ''
					},
					yAxis : {
						title : {
							text : ''
						},
						min : 0
					},
					tooltip : {
						formatter : function() {
							var date = new Date(this.x);
							return '<b>' + this.series.name + ":" + this.y + '</b><br>' + Ext.util.Format.date(date, 'Y-m-d H:i:s')
									+ '<br>';
						}
					},
					exporting : {
						enabled : false
					},
					series : [{
								name : '实时数据',
								data : historyData,
								color : 'green'
							}, {
								name : '参数上限',
								data : maxList,
								color : 'red'
							}, {
								name : '参数下限',
								data : minList,
								color : 'yellow'
							}]
				});
			}
		})