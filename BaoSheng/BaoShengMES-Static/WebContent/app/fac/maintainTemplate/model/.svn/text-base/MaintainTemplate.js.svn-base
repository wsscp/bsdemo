Ext.define('bsmes.model.MaintainTemplate',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'modifyUserCode',
		type : 'string'
	},{
		name : 'eqipCategory',
		type : 'string'
	},{
		name : 'model',
		type : 'string'
	},{
		name : 'modifyTime',
		type : 'date'
	},{
		name : 'createTime',
		type : 'date'
	},{
		name : 'id',
		type : 'string'
	},{
		name : 'orgCode',
		type : 'string'
	},{
		name : 'createUserCode',
		type : 'string'
	},{
		name : 'triggerCycle',
		type : 'int'
	},{
		name : 'triggerType',
		type : 'string',
        defaultValue : 'NATURE'
	},{
		name : 'triggerCycleH',
		type : 'int'
	},{
		name : 'triggerTypeH',
		type : 'string'
	},{
		name : 'type',
		type : 'string',
        defaultValue : 'DAILY'
	},{
		name : 'describe',
		type : 'string'
	},{
		name : 'unit',
		type : 'string',
        persist : false,
        convert: function (v, record) {
            var triggerType = record.get('triggerType');
            if (triggerType == 'NATURE') {
                return '月';
            }
            return '小时';
        }
    }],
    validations: [{
        type: 'presence',
        field: 'model'
    }]
});