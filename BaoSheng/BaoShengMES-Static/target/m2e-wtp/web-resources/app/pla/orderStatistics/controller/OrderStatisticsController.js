Ext.define('bsmes.controller.OrderStatisticsController', {
	extend : 'Oit.app.controller.GridController',
	orderStatisticsList : 'orderStatisticsList',
	view : 'orderStatisticsWindows',
	views : ['OrderStatisticsList', 'OrderStatisticsWindows'],
	stores : ['OrderStatisticsStore'],

	constructor : function() {
		var me = this;

		me.refs = me.refs || [];

		me.refs.push({
					ref : 'orderStatisticsList',
					selector : me.orderStatisticsList,
					autoCreate : true,
					xtype : me.orderStatisticsList
				});

		me.callParent(arguments);
	},
	init : function() {
		var me = this;
		me.callParent(arguments);
	},
	onSearch : function() {
		var me = this;
		var f0 = me.getGrid().down('form');
		var f2 = me.getGrid().down('orderStatisticsList form');
		f2.getForm().setValues(f0.getForm().getValues());
		var mypanel = me.getGrid().down('tabpanel');
		var startDate = Ext.getCmp('sdate').getValue();
		var endDate = Ext.getCmp('edate').getValue();
		var e = Ext.util.Format.date(startDate, 'Y-m-d');//格式化日期控件值  
        var s= Ext.util.Format.date(endDate, 'Y-m-d');//格式化日期控件值
        var end = new Date(s);  
       var start = new Date(e);  
       var elapsed = Math.round((end.getTime() - start.getTime())/(86400000)); // 计算间隔天数
       if(endDate>startDate){
        if(elapsed<=31){
        Ext.Msg.wait(Oit.msg.LOADING, Oit.msg.PROMPT);
		Ext.Ajax.request({
			url : 'orderStatistics/queryData',
			params : f0.getForm().getValues(),
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				var data = Ext.decode(response.responseText);
				var grid = Ext.getCmp('orderGrid');
				var me = this;
				var store = grid.getStore();
				store.loadData(data.list, false);
				var arr10 = [];
				var arr11 = [];
				var arr2 = [];
				Ext.Array.each(data.list, function(record, i) {
							arr10.push(record.section + ' - ' + record.name);
							arr11.push(record.sumLength);
//							arr2.push({
//										name : record.section + ' - ' + record.name,
//										y : record.sumLength
//									});
						});
				var tabIdx = mypanel.getActiveTab();

				mypanel.setActiveTab(1);
				var myChartwin = Ext.getCmp('myChart');
			//	var myChart1win = Ext.getCmp('myChart1');
				// myChartwin.show();
				// myChart1win.show();
				Highcharts.setOptions({
			        colors: ['#f75354', '#bdd446', '#f79c53', '#ffca00', '#02cae4', '#56f99b', '#0F9655', '#fAF9C4', '#ACF263','#89F994','#f27b53','#FF7F50','#2828ff']
			    });
			 Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {  
			    return {  
			        radialGradient: { cx: 0.8, cy: 0.8, r: 3 },  
			        stops: [  
			            [0, color],  
			            [1, Highcharts.Color(color).brighten(0.5).get('rgb')] // darken  
			        ]  
			    };  
			});  
				$('#myChart').highcharts({
							chart : {
								type : 'column'
								// width:600
							},
							 
							title : {
								text : data.section
							},

							xAxis : {
								categories : arr10,
								tickInterval : 1,
								lineWidth : 1,
								min : 0
							},
							yAxis : {
								tickInterval : 1,
								lineWidth : 1,
								min : 0,
								title : {
									text : '生产总量(m)'
								}
							},
							credits : {
								text : ''
							},

							plotOptions : {
								series : {
									colorByPoint : true,
									maxPointWidth:80//设置柱状图宽度
								}
							},

							series : [{
										name : '生产总量',
										data : arr11,
										tickInterval : 1,
										lineWidth : 1,
										min : 0
									}],
							exporting : {
								type : 'image/png',
								buttons : {
									contextButton : {
										menuItems : [{
													text : '导出图表',
													onclick : function() {
														this.exportChart();
													},
													separator : false
												}]
									}
								}

							}
						});
				mypanel.setActiveTab(2);
				
				mypanel.setActiveTab(tabIdx);

			}
		})}else{
			 Ext.MessageBox.alert("提示","开始时间相隔于结束时间30天且开始时间必须小于结束时间");  
		}
       }else{
    	   Ext.MessageBox.alert("提示","开始时间必须小于结束时间");   
        }
       
	}

});
