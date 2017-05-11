Ext.define('bsmes.model.AlarmHistoryModel', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'tagName', 
	    'description',
	    'area',
	    'type',
	    'value',
	    'checkValue',
	    'eventStampUTC',
	    'equipCode',
	    'tagNameDec'
	 ]
});