Ext.define("bsmes.view.ProductStatusTraceList",{
		extend 	: 'Oit.app.view.Grid',
		alias 	: 'widget.productStatusTraceList',
		store 	: 'ProductStatusTraceStore',
		forceFit : false,
		defaultEditingPlugin:false,
		columns	:[{
					 xtype:'rownumberer',
					 width:30
				  },{
		       		   text:Oit.msg.wip.productStatusTrace.contractNo,
		       		   dataIndex:'contractNo',
		       		    width:120 
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.workOrderNo,
		       		   dataIndex:'workOrderNo',
		       		    width:140
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.halfProductCode,
		       		   dataIndex:'halfProductCode',
		       		   width:230
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.color,
		       		   dataIndex:'color',
		       		  width:90
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.specification,
		       		   dataIndex:'specification',
		       		  width:90
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.batchNo,
		       		   dataIndex:'batchNo',
		       		 width:230
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.matCode,
		       		   dataIndex:'matCode',
		       		 width:230
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.status,
		       		   width:60,
		       		   dataIndex:'status',
		       		   renderer:function(value){
		    			if(value=='TO_AUDIT'){
		    				return "待审核";
		    			}else if(value=='TO_DO'){
		    				return "已审核";
		    			}else if(value=='IN_PROGRESS'){
		    				return "生产中";
		    			}else if(value=='CANCELED'){
		    				return "已取消";
		    			}else{
		    				return "已完成";
		    			}
		       		   }
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.processCode,
		       		   dataIndex:'processCode',
		       		   width:150
		       	   },{
		       		 text:Oit.msg.wip.productStatusTrace.processName,
		       		   dataIndex:'processName',
		       		   width:150
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.equipCode,
		       		   dataIndex:'equipCode',
		       		   width:130
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.reportUserCode,
		       		   dataIndex:'reportUserCode',
		       		   width:100
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.realStartTime,
		       		   dataIndex:'realStartTime',
		       		   renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		       		   width:150
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.orderlength,
		       		   dataIndex:'orderlength',
		       		  width:100
		       	   },{
		       		   text:Oit.msg.wip.productStatusTrace.completedLength,
		       		   dataIndex:'completedLength',
		       		  width:100
		       			   
		       	   }], 
		dockedItems : [{
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
				        fieldLabel: Oit.msg.wip.productStatusTrace.contractNo,
				        xtype : 'textfield',
				        name: 'contractNo'
				    },{
				    	 fieldLabel: Oit.msg.wip.productStatusTrace.workOrderNo,
				    	 xtype : 'textfield',
					     name: 'workOrderNo'
				    },{
				    	  fieldLabel: Oit.msg.wip.productStatusTrace.processCode,
					      name: 'processCode',
					      xtype : 'combobox',
						  editable:false,  
						  displayField:'processName',
						  valueField: 'processCode',
						  mode:'remote',
						  width:390,
						  store:new Ext.data.Store({
								  fields:['processName', 'processCode'],
								  proxy:{
									  type: 'rest',
									  url:'qualityTrace/process'
								  }
							})
				    }]
				}],
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
	}]	

});