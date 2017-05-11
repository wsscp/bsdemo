Ext.define('bsmes.model.MaintainItem',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'modifyUserCode',
		type : 'string'
	},{
		name : 'modifyTime',
		type : 'date'
	},{
		name : 'createTime',
		type : 'date'
	},{
		name : 'tempId',
		type : 'string',
        defaultValue : Oit.url.urlParam('tempId')
	},{
		name : 'id',
		type : 'string'
	},{
		name : 'createUserCode',
		type : 'string'
	},{
		name : 'describe',
		type : 'string'
	}]
});