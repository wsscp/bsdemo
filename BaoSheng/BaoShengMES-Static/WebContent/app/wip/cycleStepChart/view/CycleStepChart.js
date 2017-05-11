Ext.define("bsmes.view.CycleStepChart", {
	extend : 'Ext.container.Container',
	alias : 'widget.cycleStepChart',
	height : '100%',
	layout : 'vbox',
	padding : 5,
	defaults : {
		width : '100%',
		height : '200'
	},
	controller:{},
	items : [{
        title : '查询条件',
        xtype : 'fieldset',
        collapsible: true,
        width : '100%',
        height:120,
        items : [{
            xtype : 'form',
            width : '100%',
            layout : 'vbox',
            buttonAlign : 'left',
            labelAlign : 'right',
            bodyPadding : 5,
            defaults : {
                xtype : 'panel',
                width : '100%',
               
                layout:'column',
                defaults:{
                    padding:1,
                    labelAlign:'right'
                }
            },
            items:[{
                   items:[{
                	   fieldLabel : Oit.msg.wip.cycleStepChart.productName,
                	   xtype : 'combo',
                	   name : 'productCode',
                	   id:'cycleStepChart_productCode',
                	   width:350,
                	   mode : 'remote',
                	   allowBlank  : false,
                	   displayField : 'productName',
                	   valueField : 'productCode',
                	   selectOnFocus : true,
                	   forceSelection:true,
                	   hideTrigger : true,
                	   minChars : 1,
                	   store : new Ext.data.Store({
                		   fields : [ 'productCode', 'productName' ],
                		   proxy : {
                			   type : 'rest',
                			   url : 'processBalanceChart/product'
                		   }
                	   }),
                	   listeners: { 
                		   'beforequery': function (queryPlan, eOpts) {
                			   var me=this;
                			   var processComb = me.findParentByType("form").queryById("cycleStepChart_processCode");
                			   var equipComb = me.findParentByType("form").queryById("cycleStepChart_id");
                			   processComb.clearValue();
                			   equipComb.clearValue();
                		   }
				}
              },{
            	  fieldLabel : Oit.msg.wip.cycleStepChart.processCode,
            	  name : 'processCode',
            	  xtype : 'combobox',
            	  displayField : 'processName',
            	  id:'cycleStepChart_processCode',
            	  valueField : 'processCode',
            	  minChars : 1,
            	  editable:false,
            	  allowBlank  : false,
            	  store : new Ext.data.Store({
            		  fields : [ 'processCode', 'processName'],
            		  proxy : {
            			  type : 'rest',
            			  url : 'cycleStepChart/process'
            		  }
            	  }),
            	  listeners : {
            		  'beforequery' : function(queryPlan, eOpts) {
            			  var me = this;
            			  me.clearValue();
            			  var productComb = me.findParentByType("form").queryById("cycleStepChart_productCode");
            			  var productCode = productComb.getValue();
            			  var equipComb = me.findParentByType("form").queryById("cycleStepChart_id");
            			  equipComb.clearValue();
            			  if(productCode){
            				  queryPlan.query=productCode;
            			  }
//            			  me.getStore().getProxy().url = url;
//            			  me.getStore().reload();
//            			  
//            			  var productCode='';
//             			   if(queryPlan.query.trim()){
//             				   productCode=queryPlan.query;
//             			   }else{
//             				   productCode= '1_';
//             			   }
//             			   console.log(productCode);
             			  // me.getStore().load({params:{'productCode':productCode}});
            		  }
            	  }
              },{
            	  fieldLabel : Oit.msg.wip.cycleStepChart.equipCode,
            	  xtype : 'combobox',
            	  name : 'equipCode',
            	  id:'cycleStepChart_id',
            	  editable:false,  
            	  allowBlank  : false,
            	  mode:'remote',
            	  displayField:'text',
            	  valueField : 'value',
            	  width:420,
            	  store:new Ext.data.Store({
            		  fields:[{name:'text',mapping:'name'},{name:'value',mapping:'code'}],
            		  proxy:{
            			  type: 'rest',
            			  url:'cycleStepChart/equip'
            		  }
            	  }),
            	  listeners : {
            		  'beforequery' : function(queryPlan, eOpts) {
            			  var me = this;
            			  me.clearValue();
            			  var processComb = me.findParentByType("form").queryById("cycleStepChart_processCode");
            			  var processCode = processComb.getValue();
            			  if(processCode){
            				  queryPlan.query=processCode;
            			  }
            		  }
            	  }
              },{
            	  fieldLabel :  Oit.msg.wip.cycleStepChart.startTime,
            	  xtype : 'datefield',
//            	  id:'cycleStepChart_start',
            	  name : 'startTime',
            	  allowBlank  : false,
            	  emptyText:'请选择起始时间',
            	  blankText:'请输入起始时间',
            	  format: 'Y-m-d'
              },{
            	  fieldLabel :  Oit.msg.wip.cycleStepChart.endTime,
            	  xtype : 'datefield',
            	  name : 'endTime',
            	  padding:'0 0 0 71',
//            	  id:'cycleStepChart_end',
            	  emptyText:'请选择结束时间',
            	  blankText:'请输入结束时间',
            	  allowBlank  : false,
            	  format: 'Y-m-d'
              }]
            }],
           buttons : [{
                itemId : 'search',
                text : Oit.btn.search
           },{
                itemId:'reset',
                text : Oit.btn.reset,
                handler : function(e) {
                    this.up("form").getForm().reset();
                }
           }]
        }]
    
	}, {
		id : 'cycleStepChart_Id',
		xtype : 'panel',
		width: document.body.scrollWidth-150,
    	height: document.body.scrollHeight-100
	} ]
	
});
