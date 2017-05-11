Ext.define("bsmes.view.MaterialTraceList",{
		extend 	: 'Oit.app.view.Grid',
		alias 	: 'widget.materialTraceList',
		store 	: 'MaterialTraceStore',
		defaultEditingPlugin:false,
		forceFit:false,
		columns	:[{
			 		  xtype:'rownumberer',
			 		  width:30
				  },{
		       		   text:Oit.msg.wip.materialTrace.workOrderNo,
		       		   width:200,	
		       		   dataIndex:'workOrderNo'
		       	   },{
		       		   text:Oit.msg.wip.materialTrace.productBatches,
		       		   width:250,
		       		   dataIndex:'productBatches'
		       	   },{
		       		   text:Oit.msg.wip.materialTrace.batchNo,
		       		   width:250,
		       		   dataIndex:'batchNo'
		       	   },{
		       		   text:Oit.msg.wip.materialTrace.matCode,
		       		   width:250,
		       		   dataIndex:'matCode'
		       	   },{
		       		   text:Oit.msg.wip.materialTrace.equipCode,
		       		   width:150,
		       		   dataIndex:'equipCode'
		       	   },{
		       		   text:Oit.msg.wip.materialTrace.operator,
		       		   width:100,
		       		   dataIndex:'operator'
		       	   },{
		       		   text:Oit.msg.wip.materialTrace.realStartTime,
		       		   width:150,
		       		   dataIndex:'realStartTime',
		       		   renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s') 
		       	   },{
		       		   text:Oit.msg.wip.materialTrace.quantity,
		       		   width:100,
		       		   dataIndex:'quantity'
		       	   }], 
		       	dockedItems : [ {
		    		xtype : 'toolbar',
		    		dock : 'top',
		    		items : [ {
		    			title : '查询条件',
		    			xtype : 'fieldset',
		    			collapsible : true,
		    			width : '100%',
		    			items : [ {
		    				xtype : 'form',
		    				width : '100%',
		    				layout : 'vbox',
		    				buttonAlign : 'left',
		    				labelAlign : 'right',
		    				bodyPadding : 5,
		    				defaults : {
		    					xtype : 'panel',
		    					width : '100%',
		    					layout : 'hbox',
		    					defaults : {
		    						labelAlign : 'right'
		    					}
		    				},
		    				items : [ {
		    					items : [{
							 	    fieldLabel: Oit.msg.wip.materialTrace.workOrderNo,
							 	    xtype : 'textfield',
							 		name: 'workOrderNo'
								},{
							        fieldLabel: Oit.msg.wip.materialTrace.matCode,
							        xtype : 'textfield',
							        name: 'matCode'
							    },{
							        fieldLabel: Oit.msg.wip.materialTrace.productBatches,
							        xtype : 'textfield',
							        name: 'productBatches'
							    },{
							    	 fieldLabel: Oit.msg.wip.materialTrace.equipCode,
								     name: 'equipCode',
								     xtype : 'combo',
										mode : 'remote',
										displayField : 'name',
										valueField:'code',
										selectOnFocus : true,
										forceSelection:true,
										hideTrigger : true,
										minChars : 1,
										store:new Ext.data.Store({
											  fields:['code', 'name'],
											  proxy:{
												  type: 'rest',
												  url:'eventTrace/equip'
											  }
										}),
										listeners: { 
											'beforequery': function (queryPlan, eOpts) {
												var me=this;
												var url = 'eventTrace/equip/'+queryPlan.query+'/';
												me.getStore().getProxy().url=url;
											}
										}
							    }]
		    				},],
		    				buttons : [ {
		    					itemId : 'search',
		    					text : Oit.btn.search
		    				}, {
		    					itemId : 'reset',
		    					text : '重置',
		    					handler : function(e) {
		    						this.up("form").getForm().reset();
		    					}
		    				}, {
		    					itemId : 'exportToXls',
		    					text : Oit.btn.export
		    				} ]

		    			} ]
		    		} ]
		    	} ]
});