Ext.define('bsmes.model.ProcessQcTrace', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'equipCode',
	    'craftsCode',
	    'checkItemCode',
	    'checkItemName',
	    'receiptCode',
	    'processCode',
	    'daValue' ,		//采集值
	    'startTime',
	    'endTime',
	    'type'			//采集参数类型
	 ]
});