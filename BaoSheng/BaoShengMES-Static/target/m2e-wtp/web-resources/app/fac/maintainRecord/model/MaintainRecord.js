Ext.define('bsmes.model.MaintainRecord',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'modifyUserCode',
		type : 'string'
	},{
		name : 'modifyTime',
		type : 'date'
	},{
		name : 'status',
		type : 'string'
	},{
		name : 'createTime',
		type : 'date'
	},{
		name : 'startTime',
		type : 'date'
	},{
		name : 'finishTime',
		type : 'date'
	},{
		name : 'equipCode',
		type : 'string'
	},{
		name : 'id',
		type : 'string'
	},{
		name : 'createUserCode',
		type : 'string'
	},{
        name : 'type',
        type : 'string'
    }]
});