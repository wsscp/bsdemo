var colors = ['#32B168','#57A0CC','#CAD638','#C2C6C9','#EEB12B','#E52E20'];  
Ext.define('Ext.chart.theme.Fancy', {
		       extend: 'Ext.chart.theme.Base',
		       constructor: function(config) {
		             this.callParent([Ext.apply({
		                 colors: colors
		             }, config)]);
		        }
});
Ext.define("bsmes.view.LineList", {
	extend : 'Ext.panel.Panel',
	alias : 'widget.lineList',
	requires : [ 'Ext.chart.Chart' ],
	autoScroll : true,
	overflowY:'auto',
	modal : true,
	plain : true,
	maximizable:true,
	initComponent : function() {
		 var me=this;
		 var code=Ext.get('equipCode').getAttribute('value');
		 var name=Ext.get('equipName').getAttribute('value');
		
		 var startTime=new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-6,0,0,0);
		 var endTime=new Date();
		 var store=Ext.create('bsmes.store.LineStore');
		 store.getProxy().url = '/bsmes/wip/realEquipStatusChart/equipHistoryLine';
		 store.load({params:{"equipCode":code}});
		 me.items = [{
				xtype : 'form',
				layout : 'hbox',
				padding: '10 0 0 0',
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
					fieldLabel :Oit.msg.wip.statusChart.startTime,
					xtype : 'datefield',
					value:startTime,
					name:startTime,
					editable:false,
					format : 'Y-m-d',
					listeners: { 
						'change':function(m, newValue, oldValue, eOpts){
							startTime=newValue;
						}
					}
				},{
					fieldLabel :Oit.msg.wip.statusChart.endTime,
					xtype : 'datefield',
					value:endTime,
					name:endTime,
					editable:false,
					format : 'Y-m-d',
					listeners: { 
						'change':function(m, newValue, oldValue, eOpts){
							endTime=newValue;
						}
					}
				},{
					width : 5
				},{
					xtype:'radiogroup',
					id:'radiogroupId',
					width:150,
					vertical:true,
					items:[{boxLabel:'天',name: 'type',inputValue: 'DAY',checked:true},
					       {boxLabel:'周',name: 'type',inputValue: 'WEEK'},
					       {boxLabel:'月',name: 'type',inputValue: 'MONTH'}
					       ]
				},{
					xtype : 'button',
					text : Oit.btn.search,
					handler:function(){
						var start=startTime.getFullYear() + "-"+ (startTime.getMonth()+1) + "-" + startTime.getDate();
						var end=endTime.getFullYear() + "-"+ (endTime.getMonth()+1) + "-" + endTime.getDate();
						var radioGroup=Ext.getCmp('radiogroupId');
						var type=radioGroup.getValue().type;
						store.getProxy().url = '/bsmes/wip/realEquipStatusChart/equipHistoryLine';
						store.load({params:{"equipCode":code,"startTime":start,"endTime":end,"type":type}});
					}
				}]
		 },{

				xtype : 'chart',
				padding :'10 0 0 0',
				animate: true,
				shadow: true,
				store:store,
				theme: 'Fancy',
				width: document.body.scrollWidth-25,
				height: document.body.scrollHeight-120,
			    legend: {
			            position: 'top'
			    },
				axes : [ {
					type : 'Numeric',
					position : 'left',
					fields : [ 'process','debug','idle','closed','error','maint'],
					title:Oit.msg.wip.statusChart.processTime,
			        grid: {
			             odd: {
			                  opacity: 1,
			                  stroke: '#bbb',
			                  'stroke-width': 0.5
			              }
			        }
				}, {
					 type: 'Category',
			         position: 'bottom',
			         fields: ['name'],
			         title: Oit.msg.wip.statusChart.equipEffectiveUse
				} ],
				series : [{
		            type: 'line',
		            highlight: {
		                size: 30,
		                radius: 7
		            },
		            tips: {
		                trackMouse: true,
		                width:200,
		                renderer: function(storeItem, item) {
		                    this.setTitle( Oit.msg.wip.statusChart.processDate+storeItem.get('name'));
		                    this.update(Oit.msg.wip.statusChart.processTimes+storeItem.get('process'));
		                }
		            },
		            style:{
		            	 'stroke-width':4,
		            	  'color':'green'
		            },
		            axis: 'left',
		            title:Oit.msg.wip.statusChart.status.process,
		            xField: 'name',
		            yField: 'process',
		            markerConfig: {
		                type: 'circle',
		                size: 4,
		                radius: 4,
		                'stroke-width': 0
		            }
		        }, {
		            type: 'line',
		            highlight: {
		                size: 7,
		                radius: 7
		            },
		            axis: 'left',
		            title:Oit.msg.wip.statusChart.status.debug,
		            smooth: true,
		            xField: 'name',
		            style:{
		            	 'stroke-width':4,
		            	  'color':'blue'
		            },
		            yField: 'debug',
		            tips: {
		                trackMouse: true,
		                width:200,
		                renderer: function(storeItem, item) {
		                    this.setTitle(Oit.msg.wip.statusChart.debugDate+storeItem.get('name'));
		                    this.update(Oit.msg.wip.statusChart.debugTimes+storeItem.get('debug'));
		                }
		            },
		            markerConfig: {
		                type: 'circle',
		                size: 4,
		                radius: 4,
		                'stroke-width': 0
		            }
		        }, {
		            type: 'line',
		            highlight: {
		                size: 7,
		                radius: 7
		            },
		            style:{
		            	 'stroke-width':4,
		            	  'color':'yellow'
		            },
		            axis: 'left',
		            smooth: true,
		            xField: 'name',
		            title:Oit.msg.wip.statusChart.status.idle,
		            yField: 'idle',
		            tips: {
		                trackMouse: true,
		                width:200,
		                renderer: function(storeItem, item) {
		                    this.setTitle(Oit.msg.wip.statusChart.idleDate+storeItem.get('name'));
		                    this.update(Oit.msg.wip.statusChart.idleTimes+storeItem.get('idle'));
		                }
		            },
		            markerConfig: {
		                type: 'circle',
		                size: 4,
		                radius: 4,
		                'stroke-width': 0
		            }
		        },{

		            type: 'line',
		            highlight: {
		                size: 7,
		                radius: 7
		            },
		            axis: 'left',
		            smooth: true,
		            xField: 'name',
		            title:Oit.msg.wip.statusChart.status.closed,
		            style:{
		            	 'stroke-width':4,
		            	  'color':'grey'
		            },
		            yField: 'closed',
		            tips: {
		                trackMouse: true,
		                width:200,
		                renderer: function(storeItem, item) {
		                    this.setTitle(Oit.msg.wip.statusChart.closedDate+storeItem.get('name'));
		                    this.update(Oit.msg.wip.statusChart.closedTimes+storeItem.get('closed'));
		                }
		            },
		            markerConfig: {
		                type: 'circle',
		                size: 4,
		                radius: 4,
		                'stroke-width': 0
		            }
		        },{
		            type: 'line',
		            highlight: {
		                size: 7,
		                radius: 7
		            },
		            axis: 'left',
		            smooth: true,
		            xField: 'name',
		            title:Oit.msg.wip.statusChart.status.maint,
		            yField: 'maint',
		            style:{
		            	 'stroke-width':4,
		            	  'color':'#ff8000'
		            },
		            tips: {
		                trackMouse: true,
		                width:200,
		                renderer: function(storeItem, item) {
		                    this.setTitle(Oit.msg.wip.statusChart.maintDate+storeItem.get('name'));
		                    this.update(Oit.msg.wip.statusChart.maintTimes+storeItem.get('maint'));
		                }
		            },
		            markerConfig: {
		                type: 'circle',
		                size: 4,
		                radius: 4,
		                'stroke-width': 0
		            }
		        },{
		            type: 'line',
		            highlight: {
		                size: 7,
		                radius: 7
		            },
		            style:{
		            	 'stroke-width':4,
		            	  'color':'red'
		            },
		            tips: {
		                trackMouse: true,
		                width:200,
		                renderer: function(storeItem, item) {
		                    this.setTitle(Oit.msg.wip.statusChart.errorDate+storeItem.get('name'));
		                    this.update(Oit.msg.wip.statusChart.errorTimes+storeItem.get('error'));
		                }
		            },
		            axis: 'left',
		            smooth: true,
		            xField: 'name',
		            title:Oit.msg.wip.statusChart.status.error,
		            yField: 'error',
		            markerConfig: {
		                type: 'circle',
		                size: 4,
		                radius: 4,
		                'stroke-width': 0
		            }
		        
		        }]
		 }];
		 me.callParent(arguments);
	}
});
