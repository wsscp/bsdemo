//ParamConfig
Ext.define('bsmes.model.ParamConfig', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'code',
	    'name',
	    'value',
	    'type',
	    'description',
	    'createUserCode',
	    {
	    	name : 'createTime',
	    	type : 'date'
	    },
	    'modifyUserCode',
	    {
	    	name : 'modifyTime',
	    	type : 'date'
	    },
	    {
	    	name : 'status',
			type : 'string',
			renderer : function(value) {
				if (value == 'true') {
					return Oit.msg.bas.param.normal;
				} else if (value == 'false') {
					return Oit.msg.bas.param.freeze;
				} else {
					return "";
				}
			}
	    },
	    'orgCode'
	 ]
});