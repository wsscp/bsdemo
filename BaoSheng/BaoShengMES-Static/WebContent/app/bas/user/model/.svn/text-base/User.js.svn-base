//User
Ext.define('bsmes.model.User', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'userCode',
		type : 'string'
	},{
		name : 'orgCode',
		type : 'string'
	},{
		name : 'orgName',
		type : 'string'
	},{
		name : 'role',
		type : 'string'
	},{
		name : 'name',
		type : 'string',
		persist : false
	},{
		name : 'password',
		type : 'string',
		renderer : function(value) {
			return "******";
		}
	}, {
		name : 'status',
		type : 'string',
		renderer : function(value) {
			if (value == '0') {
				return Oit.msg.bas.user.freeze;
			} else if (value == '1') {
				return Oit.msg.bas.user.normal;
			} else {
				return "";
			}
		}
	}, {
		name : 'createTime',
		type : 'date'
	} ],
	validations : [{
		type : 'length',
		field : 'userCode',
		min : 1
	}]
});
