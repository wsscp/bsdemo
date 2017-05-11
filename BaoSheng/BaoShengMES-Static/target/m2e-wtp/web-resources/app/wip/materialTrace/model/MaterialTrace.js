Ext.define("bsmes.model.MaterialTrace",{
	extend 	: 'Ext.data.Model',
	fields	:['id',
	     	  'matCode',
	     	  'batchNo',
	     	  'productBatches',
	     	  'equipCode',
	     	  'operator',
	     	  'quantity',
	     	  'workOrderNo',
	     	  {
	  			name : 'realStartTime',
	  			type : 'date'
	     	  }]
});
