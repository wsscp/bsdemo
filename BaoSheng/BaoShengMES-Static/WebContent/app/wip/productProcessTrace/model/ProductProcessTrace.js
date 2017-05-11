Ext.define("bsmes.model.ProductProcessTrace",{
	extend 	: 'Ext.data.Model',
	fields	:['contractNo',
	      	  'workOrderNo',
	     	  'batchNo',
	     	  'matCode',
	     	  'processCode',
	     	  'processName',
	     	  'productSpec',
	     	  'productType',     	  
	     	  'equipCode',
	     	  'reportUserCode',
	     	  {name:'realStartTime',type : 'date'},
	     	  'completedLength',
	     	  'processTime'
	     	  ]
});