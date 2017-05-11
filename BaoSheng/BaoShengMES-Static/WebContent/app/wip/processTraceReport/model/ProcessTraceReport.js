Ext.define('bsmes.model.ProcessTraceReport', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'contractNo', 
	    'equipCode',
	    'productCode',
	    'processCode',
	    'processName',
	    {name:'realStartTime',type : 'date'},
	    {name:'realEndTime',type : 'date'},
	    'reportUserCode' ,		
	    'orderlength'
	 ]
});