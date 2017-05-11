Ext.define("bsmes.view.ProductProcessTraceList",{
		extend 	: 'Oit.app.view.Grid',
		alias 	: 'widget.productProcessTraceList',
		store 	: 'ProductProcessTraceStore',
		defaultEditingPlugin:false,
		forceFit:false,
		columns	:[{
			         text:Oit.msg.wip.productProcessTrace.contractNo,
		       	     dataIndex:'contractNo',
		       		 width:200,
		       		 renderer:function (value, metaData, record, rowIdx, colIdx, store) {
		       			   if(value){
		       				   metaData.tdAttr = 'data-qtip=" <div style=\'word-break:break-all;word-wrap:break-word;\'>' + value + '</div>"';
		       			   }	
		       			   return value;
					}
				   },{
		       		   text:Oit.msg.wip.productProcessTrace.productType,
		       		   width:250,
		       		   dataIndex:'productType'
				   },{
		       		   text:Oit.msg.wip.productProcessTrace.productSpec,
		       		   width:250,
		       		   dataIndex:'productSpec'
				   },{
		       		   text:Oit.msg.wip.productProcessTrace.batchNo,
		       		   width:250,
		       		   dataIndex:'batchNo'	   
		       			   
		       	   },{
		       		   text:Oit.msg.wip.productProcessTrace.matCode,
		       		   width:250,
		       		   dataIndex:'matCode'
		       	   
		       	   },{
		       		   text:Oit.msg.wip.productProcessTrace.processName,
		       		   width:150,
		       		   dataIndex:'processName'
		       	   },{
		       		   text:Oit.msg.wip.productProcessTrace.equipCode,
		       		   width:150,
		       		   dataIndex:'equipCode'
		       	   },{
		       		   text:Oit.msg.wip.productProcessTrace.reportUserCode,
		       		   width:150,
		       		   dataIndex:'reportUserCode'
		       	   },{
		       		   text:Oit.msg.wip.productProcessTrace.realStartTime,
		       		   width:150,
		       		   dataIndex:'realStartTime',
		       		   renderer:Ext.util.Format.dateRenderer('Y-m-d') 
		       	   },{
		       		   text:Oit.msg.wip.productProcessTrace.completedLength,
		       		   width:150,
		       		   dataIndex:'completedLength'
		       	   },{
		       		   text:Oit.msg.wip.productProcessTrace.processingTime,
		       		   width:150,
		       		   dataIndex:'processTime'
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
									fieldLabel: Oit.msg.wip.productProcessTrace.contractNo,
									xtype : 'textfield',
									name: 'contractNo'
								},{
							        fieldLabel: Oit.msg.wip.productProcessTrace.productType,
							        xtype : 'textfield',
							        name: 'productType'
								},{
							        fieldLabel: Oit.msg.wip.productProcessTrace.productSpec,
							        xtype : 'textfield',
							        name: 'productSpec'
							    },{
							        fieldLabel: Oit.msg.wip.productProcessTrace.equipCode,
							        xtype : 'textfield',
							        name: 'equipCode'
							    }]
		    				}, {
		    					height : 5
		    				}, {
		    					items : [{
							    	 fieldLabel: Oit.msg.wip.productProcessTrace.batchNo,
							    	 xtype : 'textfield',
								     name: 'batchNo'
							    },{
							    	 fieldLabel: Oit.msg.wip.productProcessTrace.processName,
								     name: 'processCode',
								     xtype : 'combobox',
									 editable : false,
									 displayField : 'processName',
									 valueField : 'processCode',
									 mode : 'remote',
									 store : new Ext.data.Store({
											fields : [ 'processName', 'processCode' ],
											proxy : {
												type : 'rest',
												url : 'qualityTrace/process'
											}
									 })

							    }]
		    				} ],
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