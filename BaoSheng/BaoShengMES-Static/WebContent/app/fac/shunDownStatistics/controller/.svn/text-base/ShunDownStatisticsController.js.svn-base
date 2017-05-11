var pieData='';
var pieChart='';
Ext.define("bsmes.controller.ShunDownStatisticsController",{
	extend : 'Oit.app.controller.GridController',
	view : 'shunDownStatisticsChart',
	views : [ 'ShunDownStatisticsChart'],
	init: function() {
		var me = this;
		me.control(me.view + ' button[itemId=search]', {
			click: me.onSearch
		});
	},
	onSearch: function() {
		var me = this;
		var form = me.getSearchForm();
		form.updateRecord();
	 	 Ext.Ajax.request({
			 url:'shunDownStatistics/statistics',
			 method:'POST',
			 async:false,
			 params:form.getValues(),
			 success: function (response) {
				 if(response.responseText){
					 var result = Ext.decode(response.responseText);
					 pieData=result.pieData;
					 pieChart.series[0].setData(pieData);
				 }
			 }
		 });
	}
})

Ext.onReady(function(){
	Ext.Ajax.request({
		 url:'shunDownStatistics/statistics',
		 method:'POST',
		 async:false,
		 success: function (response) {
			 console.log(response.responseText);
			 if(response.responseText){
				 var result = Ext.decode(response.responseText);
				 pieData=result.pieData;
			 }
		 }
	 });
	pieChart=$('#shunDownStatistics_id').highcharts({
		chart: {
			type: 'pie',
			options3d: {
				enabled: true,
				alpha: 45,
				beta: 0
			}
		},
		title: {
			text: '停机原因分析'
		},
		credits: {
            text: ''
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}</b>'
        },
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				depth: 35,
				dataLabels: {
					enabled: true,
					format: '{point.name}'
				}
			}
		},
		exporting: {
			enabled: false
		},
		series: [{
			type: 'pie',
			name: '百分比(%)',
			data: pieData
		}]
	}).highcharts();
})
