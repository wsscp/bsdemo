Ext.define("bsmes.view.TurnoverReportGrid", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.turnoverReportGrid',
	store : 'TurnoverReportStore',
	forceFit : false,
	defaultEditingPlugin : false,
	columns : [
			{
				text : Oit.msg.wip.workOrder.workOrderNO,
				width : 80,
				dataIndex : 'workOrderNo',
				align: 'center',
				renderer : function(value, metaData, record, rowIndex,
						columnIndex, store, view) {
					return value.substring(value.length - 5);
				}
			},{
				text : '合同号',
				width : 120,
				align: 'center',
				dataIndex : 'contractNo'
			},{
				text : Oit.msg.wip.turnoverReport.equipCode,
				dataIndex : 'equipCode',
				align: 'center',
				width : 70
			},{
				text : Oit.msg.wip.turnoverReport.shiftName,
				dataIndex : 'shiftName',
				align: 'center',
				width : 80
			}, {
				text : Oit.msg.wip.turnoverReport.shiftDate,
				dataIndex : 'shiftDate',
				align: 'center',
				width : 90,
				renderer : function(value, metaData, record, rowIndex,
						columnIndex, store, view) {
					return Ext.Date.format(value,'Y-m-d');
				}
			},{
				text : Oit.msg.wip.turnoverReport.processName,
				dataIndex : 'processName',
				align: 'center',
				width : 80
			},{
				text : Oit.msg.wip.workOrder.productType,
				width : 120,
				align: 'center',
				dataIndex : 'custProductType'
			},{
				text : Oit.msg.wip.workOrder.productSpec,
				width : 70,
				align: 'center',
				dataIndex : 'custProductSpec'
			},{
				text : Oit.msg.wip.terminal.productLength,
				width : 70,
				align: 'center',
				dataIndex : 'workOrderLength'
			},{
				text : Oit.msg.wip.workOrder.reportNum,
				width : 70,
				align: 'center',
				dataIndex : 'reportLength'
			},{
				text : Oit.msg.wip.workOrder.dbUserName,
				width : 130,
				align: 'center',
				dataIndex : 'dbUserName'
			},{
				text : Oit.msg.wip.workOrder.fdbUserName,
				width : 130,
				align: 'center',
				dataIndex : 'fdbUserName'
			},{
				text : Oit.msg.wip.workOrder.fzgUserName,
				width : 130,
				align: 'center',
				dataIndex : 'fzgUserName'
			},{
				text : Oit.msg.wip.workOrder.matName,
				dataIndex : 'matName',
				align: 'center',
				width : 130,
				renderer : function(value, metaData, record, rowIndex,
						columnIndex, store, view) {
					metaData.style = "white-space:normal";
					return value.replace(/,/g, '<br/>');
				}
			}, {
				dataIndex : 'matCode',
				width : 130,
				align: 'center',
				hidden : true
			}, {
				text : Oit.msg.wip.terminal.quotaAmount,
				dataIndex : 'quotaQuantity',
				width : 110,
				align: 'center',
				// hidden: isHidden,
				renderer : function(value, metaData, record) {
					return value.replace(/,/g, '<br/>');
				}
			}, {
				text : Oit.msg.wip.terminal.realAmount,
				dataIndex : 'realQuantity',
				width : 160,
				align: 'center',
				renderer: function(value, metaData, record) {
					return value.replace(/,/g, '<br/>');
				}
			}, {
				text : Oit.msg.wip.turnoverReport.processCode,
				dataIndex : 'processCode',
				width : 80,
				align: 'center',
				hidden:true
			}],
			
	dockedItems	:[{
		  xtype: 'toolbar',
		  dock: 'top',
		  margin: '10',
		  layout: 'vbox',
		  items: [{
			  title:'查询条件',
			  width: '99%',
			  height: 120,
			  xtype:'fieldset',
			  collapsible: true,
			  items:[{
				  xtype:'form',
				  padding: '5,15,10,10',
				  layout:'hbox',
				  items:[{
					    fieldLabel : '人员姓名',
						name : 'userCode',
						id:'userCode',
						xtype: 'combobox',
						width : 220,
						labelWidth:70,
						displayField : 'dbUserName',
						valueField:'dbUserName',
						selectOnFocus : true,
						//forceSelection:true,
						hideTrigger : true,
						minChars : 1,
						height: 25,
						margin: '10,15,10,10',
						store: new Ext.data.Store({
							fields:['dbUserName','dbUserName'],
							proxy:{
								type:'rest',
								url:'turnoverReport/getUserName/-1'
							}
						}),
						listeners: {
					    	 beforequery : function(e) {
									var combo = e.combo;
									combo.collapse();
									if (!e.forceAll) {
										var value = e.query;
										//console.log(value);
										if(value==null||value==''){
											value=-1;
										}
										combo.store.getProxy().url='turnoverReport/getUserName'+'/'+value;
										combo.store.load();
										combo.expand();
										return false;
									}
								}
					    }
				  },{
					    fieldLabel : '班次',
						name : 'shiftName',
						id:'shiftName',
						xtype: 'combobox',
						displayField:'shiftName',
				        valueField: 'shiftName',
						width : 200,
						labelWidth:50,
						height: 25,
						margin: '10,15,10,10',
						mode: 'local',
				        allowBlank:false,
				        store: new Ext.data.Store({
				        	fields:['shiftName','shiftName'],
							autoLoad:true,
							data:[{shiftCode:'早班',shiftName:'早班'},
							      {shiftCode:'中班',shiftName:'中班'},
							      {shiftCode:'夜班',shiftName:'夜班'}]
				        })
				  },{
					    fieldLabel : '班次日期',
						name : 'shiftDate',
						id:'shiftDate',
						xtype: 'datefield',
						margin: '10,15,10,10',
						width : 220,
						height:25,
						labelWidth:70,
						format:'Y-m-d'
				  },{
					    fieldLabel : '工序',
						name : 'processCode',
						id:'processCode',
						xtype: 'combobox',
						margin: '10,0,10,10',
						displayField:'processName',
				        valueField: 'processName',
						width : 200,
						height:25,
						labelWidth:50,
						allowBlank:false,
						minChars : 1,
					    store: new Ext.data.Store({
					        	fields:['processName','processName'],
								autoLoad:true,
								proxy:{
									type:'rest',
									url:'turnoverReport/getProcessName'
								}
					        }),
					    listeners: {
					    	 beforequery : function(e) {
					    		 var combo = e.combo;
					    	     combo.collapse();
					    	     if (!e.forceAll){
					    	    	 var value = e.query;
					    	    	 console.log(value);
					    	          if (value != null && value != '') {
					    	        	  combo.store.filterBy(function(record, id) {
				                          var text =record.get('processName'); 
				                          return (text.indexOf(value) != -1);
				                         });
					    	          }
					    	          combo.store.load();
					    	          combo.expand();
					    	          return false;
					    	     }
					    	 }
					    }
				  },{
					    fieldLabel : '设备编号',
						name : 'equipCode',
						id:'equipCode',
						xtype: 'combobox',
						margin: '10,0,10,10',
						displayField:'equipCode',
				        valueField: 'equipCode',
						width : 220,
						height:25,
						labelWidth:70,
						allowBlank:false,
						minChars: 1,
					    store: new Ext.data.Store({
					        	fields:['equipCode','equipCode'],
								autoLoad:true,
								proxy:{
									type:'rest',
									url:'turnoverReport/getEquipCode'
								}
					        }),
					        listeners: {
						    	 beforequery : function(e) {
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											var value = e.query;
											var regExp =new RegExp("^.*"+value+".*$", 'i');
											if (value != null && value != '') {
												combo.store.filterBy(function(record, id) {
															var text = record.get('equipCode');
															return regExp.test(text);
														});
											} else {
												combo.store.clearFilter();
											}
											combo.onLoad();
											combo.expand();
											return false;
										}
									}
						    }    				  
				  }]
			  },{
					text: '查询',
					xtype:'button',
					height: 30,
					width:50,
					margin: '0 5 10 10',
					handler: function(){
						var store=Ext.ComponentQuery.query('turnoverReportGrid')[0].getStore();
						//console.log(processCode);
						store.load();
					}				
			  },{
					text : Oit.btn.reset,
					xtype:'button',
					height: 30,
					width:50,
					margin: '0 10 10 5',
					handler: function(){
							var me = this;
							me.up('grid').down('form').getForm().reset()
						}		
			  }]
		  }]
	}]	


 
});