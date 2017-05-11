//Employee
Ext.regModel('bsmes.model.Resources', {
	extend : 'Ext.data.Model',
	fields : [ 'id', {
		name : 'name',
		type : 'string'
	}, 'parentId', 'roleId','parentName', 'uri', 'type', 'description', {
		name : 'createTime',
		type : 'date'
	}, {
		name : 'roleQuery',
		type : 'boolean'
	},{
		name : 'roleCreate',
		type : 'boolean'
	},{
		name : 'roleDelete',
		type : 'boolean'
	},{
		name : 'roleEdit',
		type : 'boolean'
	},{
		name : 'roleAdvanced',
		type : 'boolean'
	}]
});