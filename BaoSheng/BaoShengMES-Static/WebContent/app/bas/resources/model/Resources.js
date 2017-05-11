//Employee
Ext.regModel('bsmes.model.Resources', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    { name: 'name', type: 'string'},
	    'parentId', 
	    'parentName', 
	    'uri',
	    'type',
	    'typeName',
	    'description',
	    'seq',
	    {
			name : 'createTime',
			type : 'date'
		},
		'leaf'
	 ]
});