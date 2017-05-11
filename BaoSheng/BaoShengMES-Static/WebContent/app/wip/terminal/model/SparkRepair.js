Ext.define("bsmes.model.SparkRepair",{
	extend 	: 'Ext.data.Model',
	fields	:[	      	 
	      	  'contractNo',
	     	  'workOrderNo',
	     	  'productCode',
	     	  'sparkPosition',
	     	  'repairType',
	     	  'status', 
	     	  {name : 'createTime',type : 'Date'}
	     	   ]
});
