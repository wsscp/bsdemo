Ext.define("bsmes.view.OEEList", {
	extend : 'Ext.panel.Panel',
	alias : 'widget.oEEList',
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
		
		 var startTime=new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-6,0,0,0);
		 var endTime=new Date();
		 var store=Ext.create('bsmes.store.LineStore');
		 store.getProxy().url = '/bsmes/wip/realEquipStatusChart/equipHistoryLine';
		 store.load({params:{"equipCode":code,"oEE":'0'}});
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
					id:'radiogroupsId',
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
						var radioGroup=Ext.getCmp('radiogroupsId');
						var type=radioGroup.getValue().type;
						store.getProxy().url = '/bsmes/wip/realEquipStatusChart/equipHistoryLine';
						store.load({params:{"equipCode":code,"oEE":'0',"startTime":start,"endTime":end,"type":type}});
					}
				}]
		 },{
				xtype : 'chart',
				padding :'10 0 0 0',
				animate: true,
				shadow: true,
				store:store,
				width: document.body.scrollWidth-25,
				height: document.body.scrollHeight-120,
				axes : [ {
					type : 'Numeric',
					position : 'left',
					fields : [ 'process'],
					title:'百分比(%)',
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
			         title: '设备OEE'
				} ],
				series : [{
		            type: 'line',
		            highlight: {
		                size: 7,
		                radius: 7
		            },
		            tips: {
		                trackMouse: true,
		                width:200,
		                renderer: function(storeItem, item) {
		                    this.setTitle('设备开机时间:'+storeItem.get('name'));
		                    this.update('设备OEE(%):'+storeItem.get('process'));
		                }
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
		        }]
		 }];
		 me.callParent(arguments);
	}
});
