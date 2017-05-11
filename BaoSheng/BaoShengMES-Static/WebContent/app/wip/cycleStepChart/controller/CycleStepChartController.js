Ext.define("bsmes.controller.CycleStepChartController",{
	extend : 'Oit.app.controller.GridController',
	view : 'cycleStepChart',
	views : [ 'CycleStepChart'],
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
			Ext.Ajax.request({
				url:'cycleStepChart/getCycleStep',
				method:'POST',
				params:form.getValues(),
				success:function(response){
					 if(response.responseText){
						 var result = Ext.decode(response.responseText);
						 var stepNames=result.x_axis;
						 var columnDatas=result.y_axis;
							 $('#cycleStepChart_Id').highcharts({                                          
							        chart: {                                                          
							        },                                                                
							        title: {                                                          
							            text: '节拍图',
							            align:'center',
							            style: {
											fontWeight: 'bold',
											fontSize:20
							            }
							        },                                                                
							        xAxis: {                                                          
							            categories: stepNames
							        },  
							        yAxis: {
										title: {
											text: '出现次数/(次)',
											style: {
													fontSize:20
											 }
										}
									},
									plotOptions: {
										column: {
											cursor: 'pointer',
											dataLabels: {
												enabled: true,
												style: {
													fontWeight: 'bold'
												},
												formatter: function() {
													return this.y;
												}
											}
										}
									},
							        tooltip: {                                                        
							            formatter: function() {                                       
							                var s;                                                    
							                if (this.point.name) {                   
							                    s = '区间:'+this.point.name +'<br>出现次数(次): '+ this.y;         
							                } else {                                                  
							                    s = '区间:'+this.x +'<br>出现次数(次):'+ this.y;                            
							                }      
							                return s;                                                 
							            }                                                             
							        },                                                                
							        labels: {                                                         
							            items: [{                                                     
							                style: {                                                  
							                    left: '40px',                                         
							                    top: '8px'                                         
							                }                                                         
							            }]                                                            
							        }, 
							        exporting: {
										enabled: false
									},
							        series: [{ 
							            type: 'column',
							            color:'blue',
							            data:columnDatas                                       
							        },{                                                              
							            type: 'spline',                                               
							            data:columnDatas, 
							            color:'red'
							        }]                                                                
							    });   
							 Ext.getCmp('cycleStepChart_Id').show();
					 }else{
						 Ext.Msg.alert('提示','查询结果为空');
						 Ext.getCmp('cycleStepChart_Id').hide();
					 }
				}
			});
		}
	}
})              

                                                            				