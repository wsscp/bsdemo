Ext.define("bsmes.model.ProductStatusTrace",{
	extend 	: 'Ext.data.Model',
	fields	:[
	     	  'id',
	     	  'contractNo',
	     	  'workOrderNo',
	     	  'halfProductCode',
	     	  'batchNo',
	     	  'matCode',
	     	  'status',
	     	  'processCode',
	     	  'processName',
	     	  'equipCode',
	     	  'reportUserCode',
	     	  {name:'realStartTime',type : 'date'},
	     	  'orderlength',
	     	  'completedLength',
	     	  'color',
	     	  'specification'
	     	  ]
});