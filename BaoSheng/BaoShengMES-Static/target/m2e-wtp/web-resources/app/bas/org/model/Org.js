Ext.define('bsmes.model.Org', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'orgCode',
	    'name',
	    'parentId',
	    'parentCode',
	    'type',
	    'description',
	    'demo',
	    {
			name : 'createTime',
			type : 'date'
		}
	 ]
});