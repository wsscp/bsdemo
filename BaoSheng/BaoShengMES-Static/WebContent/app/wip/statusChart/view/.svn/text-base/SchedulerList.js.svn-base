var	startDate =new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),0,0,0);
var endDate =new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59);

var pieData='';
var columnData='';
var names='';
var columncharts='';
var piecharts='';

Ext.define("bsmes.view.SchedulerList", {
	extend : 'Ext.panel.Panel',
	alias : 'widget.schedulerList',
	requires : [ 'Ext.chart.Chart'],
	autoScroll : true,
	overflowY:'auto',
	modal : true,
	plain : true,
	maximizable:true,
	initComponent : function() {
		 var me=this; 
		 var code=Ext.get('equipCode').getAttribute('value');
		 var name=Ext.get('equipName').getAttribute('value');
		 var resourceStore=Ext.create('bsmes.store.ResourceStore');
		 resourceStore.getProxy().url = '/bsmes/wip/realEquipStatusChart/getResource';
		 resourceStore.load({params:{"equipCode":code}});
		 //时序图
		 var eventStore=Ext.create('bsmes.store.EventStore');
		 eventStore.getProxy().url = '/bsmes/wip/realEquipStatusChart/getEvent';
		 eventStore.load({params:{"equipCode":code}});
		
		 var scheduler= Ext.create("bsmes.view.Scheduler", {
	       	  	startDate  : startDate,
	            endDate    : endDate,
	            resourceStore : resourceStore,
	            eventStore    : eventStore,
	            width: document.body.scrollWidth-20,
	        	height:300
	     });
		 var columnChart=Ext.create('bsmes.view.ColumnPercent',{
			  width: document.body.scrollWidth-40,
	          height: (document.body.scrollHeight-60)/2,
		 });
		 
		 me.items = [{
				xtype : 'form',
				layout : 'hbox',
				defaults : {
					labelAlign : 'right'
				},
				items : [{
					fieldLabel :Oit.msg.wip.statusChart.equipCode,
					xtype : 'displayfield',
				    value:code
				},{
					fieldLabel :Oit.msg.wip.statusChart.equipName,
					xtype : 'displayfield',
					value:name
				},{
					fieldLabel : Oit.msg.wip.statusChart.searchTime,
					xtype : 'datefield',
					editable:false,
					name:'startDate',
					value:startDate,
					format : 'Y-m-d',
					listeners: { 
						'change':function(m, newValue, oldValue, eOpts){
							if(newValue>new Date()){
								return;
							}else{
								startDate=newValue;
							}
						},
						'select': function (field,value){
							if(value>new Date()){
								var form=me.items.items[0];
								form.getForm().findField('startDate').setValue(startDate);
							}
						}
					}
				},{
					fieldLabel :'结束时间',
					xtype : 'datefield',
					value:endDate,
					name:'endDate',
					editable:false,
					format : 'Y-m-d',
					listeners: { 
						'change':function(m, newValue, oldValue, eOpts){
							endDate=newValue;
						}
					}
				},{
					width : 5
				},{
					xtype : 'button',
					text : Oit.btn.search,
					listeners:{
						 click:function(){
							 if(startDate>endDate){
									Ext.Msg.alert('提示','开始时间不能大于结束时间');
									return;
								}
								var stime=startDate.getFullYear() + "-"+ (startDate.getMonth()+1) + "-" + startDate.getDate();
								var etime=endDate.getFullYear() + "-"+ (endDate.getMonth()+1) + "-" + endDate.getDate();
								resourceStore.getProxy().url = '/bsmes/wip/realEquipStatusChart/getResource';
								resourceStore.load({params:{"equipCode":code}});
								
								eventStore.getProxy().url = '/bsmes/wip/realEquipStatusChart/getEvent';
								eventStore.load({params:{"equipCode":code,"startTime":stime,"endTime":etime}});
								scheduler.switchViewPreset('minuteAndHour',startDate, endDate);
								
								Ext.Ajax.request({
								    url: '/bsmes/wip/realEquipStatusChart/equipHistoryColumnPercent',
								    method: 'GET',
								    async:false,
								    params : {"equipCode":code,"startTime":stime,"endTime":etime},
								    success: function(response){
								    	var result=Ext.decode(response.responseText);
								    	pieData=result.pieData;
								    	columnData=result.columnData;
								    	names=result.names;
								    	columncharts.series[0].setData(columnData);
								    	piecharts.series[0].setData(pieData);
								    }
								});	
						 }
					}
				},{padding:'0 50 0 0'},{
                	xtype:'panel',
                	html:'<div style="background-color:#DFEAF2;width:385px;height:18px;">' +
                		'<span style="display:block;float:left;">'+Oit.msg.wip.statusChart.status.process+'</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:#32B168;display:block;float:left;border:1px solid #32B168;"></span>' +
                		'<span style="display:block;float:left;">'+Oit.msg.wip.statusChart.status.debug+'</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:#57A0CC;display:block;float:left;border:1px solid #57A0CC;"></span>' +
                		'<span style="display:block;float:left;">'+Oit.msg.wip.statusChart.status.idle+'</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:#CAD638;display:block;float:left;border:1px solid #CAD638;"></span>' +
                		'<span style="display:block;float:left;">'+Oit.msg.wip.statusChart.status.closed+'</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:#C2C6C9;display:block;float:left;border:1px solid #C2C6C9;"></span>' +
                		'<span style="display:block;float:left;">'+Oit.msg.wip.statusChart.status.error+'</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:#E52E20;display:block;float:left;border:1px solid #E52E20;"></span>' +
                		'<span style="display:block;float:left;">'+Oit.msg.wip.statusChart.status.maint+'</span><span style="margin-right:10px;margin-left:5px;width:16px;height:16px;background-color:#EEB12B;display:block;float:left;border:1px solid #EEB12B;"></span>' +
                		'</div>'
				}]
		 },{
			 xtype:'panel',
			 padding:'0 0 0 20',
			 layout : 'vbox',
			 items:[scheduler,{xtype:'panel', padding:'20 0 0 0',items:columnChart}]
		 }];
		 me.callParent(arguments);
	},
	 constructor: function () {
	        var me = this;
	        me.callParent(arguments);
	        setInterval(function() {
	            me.autoRefresh.apply(me);
	         }, 15000);

	},
	autoRefresh:function(){
		var me=this;
		var button=me.items.items[0].items.items[5];
		button.fireEvent('click');
	}
});

Ext.onReady(function(){
	
	Ext.Ajax.request({
	    url: '/bsmes/wip/realEquipStatusChart/equipHistoryColumnPercent',
	    method: 'GET',
	    async:false,
	    params : {"equipCode":Ext.get('equipCode').getAttribute('value')},
	    success: function(response){
	    	var result=Ext.decode(response.responseText);
	    	pieData=result.pieData;
	    	columnData=result.columnData;
	    	names=result.names;
	    }
	});	
	
	columncharts = $('#columnChartId').highcharts({
		chart: {
			type: 'column',
			 options3d: {
	                enabled: true,
	                alpha: 10,
	                beta: 15,
	                depth: 50,
	                viewDistance: 25
	         }
		},
		title: {
			text: ''
		},
		xAxis: {
			categories: names
		},
		yAxis: {
			title: {
				text: '工 作 时 间 ( 小 时 )',
				style: {
						fontWeight: 'bold',
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
						fontWeight: 'bold',
						fontSize:12
					},
					formatter: function() {
						return this.y;
					}
				}
			}
		},
		tooltip: {
			formatter: function() {
				return '设备状态: '+this.x +'<br>时间(小时):  '+ this.y;
			}
		},
		credits: {
            text: '',
            href: ''
        },
		series: [{
			name: '<span style="font-size:20">空闲/关机/故障/调试/加工(小时)</span>',
			data: columnData,
			color: 'white',
			
		}],
		exporting: {
			enabled: false
		}
	}).highcharts(); 


	piecharts=$('#pieChartId').highcharts({
		chart: {
			type: 'pie',
			options3d: {
				enabled: true,
				alpha: 45,
				beta: 0
			}
		},
		title: {
			text: ''
		},
		credits: {
            text: '',
            href: ''
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
		series: [{
			type: 'pie',
			name: '时长(小时)',
			data: pieData
		}],
		exporting: {
			enabled: false
		}
	}).highcharts();
});
