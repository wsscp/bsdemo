Ext.define("bsmes.controller.ProcessBalanceChartController",{
	extend : 'Oit.app.controller.GridController',
	view : 'processBalanceChart',
	views : [ 'ProcessBalanceChart'],
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
		if(form.isValid()){
			var startDate= Ext.getCmp("processBalanceChart_start").getValue();
	  	    var endDate=Ext.getCmp("processBalanceChart_end").getValue();
	  	    if(endDate<startDate){
	  		     Ext.MessageBox.alert("提示", "开始时间必须小于结束时间!");
			     return;
	  		}
	  	    if(startDate>new Date()){
	  	    	 Ext.MessageBox.alert("提示", "开始时间必须小于当前时间!");
			     return;
	  	    }
	  	 Ext.Ajax.request({
			 url:'processBalanceChart/processChart',
			 method:'GET',
			 async:false,
			 params:{
				 productCode: Ext.getCmp("processBalanceChart_code").getValue(),
				 startTime:Ext.getCmp("processBalanceChart_start").getValue(),
				 endTime:Ext.getCmp("processBalanceChart_end").getValue()
			 },
			 success: function (response) {
				 if(response.responseText){
					 var result = Ext.decode(response.responseText);
					 var processName=result.processName;
					 var processData=result.processData;
					 var debugData=result.debugData;
					 $('#processBalanceChartId').highcharts({
					        chart: {
					            type: 'column',
					            options3d: {
					                enabled: true,
					                depth: 40
					            },
					            marginTop: 80,
					            marginRight: 40
					        },

					        title: {
					            text: ''
					        },

					        xAxis: {
					            categories: processName
					        },
					        credits: {
					            text: ''
					        },
//					        yAxis: {
//					            allowDecimals: false,
//					            min: 0,
//					            title: {
//					                text: '<span style="font-size:20">分钟/100m</span>'
//					            }
//					        },

					        yAxis: {
			                    min: 0,
			                    title: {
			                        text: '<span style="font-size:20">分钟/100m</span>'
			                    },
			                    stackLabels: {
			                        enabled: true,
			                        style: {
			                            fontWeight: 'bold',
			                            color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
			                        }
			                    }
			                },
			                
					        tooltip: {
					            headerFormat: '<b>{point.key}</b><br>',
					            formatter: function() {
					            	var flag=false;
					            	if(this.y=='0.01'){
					            		this.y=0;
					            		flag=true;
					            	}
					            	var total='';
					            	if(this.point.stackTotal<=0.02){
					            		total=0;
					            	}else{
					            		if(flag){
					            			total=this.point.stackTotal-0.01;
					            		}else{
					            			total=this.point.stackTotal;
					            		}
					            	}
					            	return '<b>'+ this.x +'</b><br>'+ this.series.name +'(分钟): '+ this.y +'<br>'+ '总计(分钟): '+ total; 
					            }
					        },

					        plotOptions: {
					        	column: {
			                        stacking: 'normal',
			                        dataLabels: {
			                            enabled: true,
			                            padding:-5,
			                            color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
			                        }
			                    }
					        },
					        legend: {
			                    align: 'right',
			                    x: -70,
			                    verticalAlign: 'top',
			                    y: 20,
			                    floating: true,
			                    backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
			                    borderColor: '#CCC',
			                    borderWidth: 1,
			                    shadow: false
			                },
					        exporting: {
								enabled: false
							},
					        series: [{
					            name: '加工',
					            color:'green',
					            data:processData

					        }, {
					            name: '调试',
					            color:'blue',
					            data: debugData

					        }]
					    });
					 Ext.getCmp('processBalanceChartId').show();
				 }else{
					 Ext.Msg.alert('提示','查询结果为空');
					 Ext.getCmp('processBalanceChartId').hide();
				 }
			 }
		 });
		}
	}
})
