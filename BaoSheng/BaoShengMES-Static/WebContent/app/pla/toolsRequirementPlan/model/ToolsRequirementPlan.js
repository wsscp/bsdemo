Ext.define('bsmes.model.ToolsRequirementPlan',{
	extend : 'Ext.data.Model',
	fields : [{name : 'id',type : 'string'},
	          {name : 'processName',type : 'string'},
	          {name : 'processCode',type : 'string'},
	          {name : 'planDate',type : 'date'},
	          {name : 'equipCode',type : 'string'},
	          {name : 'quantity',type : 'int'},
              {name : 'workOrderNO',type : 'string'},
              {name : 'tooles',type : 'string'}]
});