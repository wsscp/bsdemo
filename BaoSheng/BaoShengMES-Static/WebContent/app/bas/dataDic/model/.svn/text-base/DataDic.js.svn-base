Ext.define('bsmes.model.DataDic', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'termsCode',
	    'termsName',
	    'code',
	    'name',
	    'seq',
	    'lan',
	    'extatt',
	    'marks',
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
				if (value == '1') {
					return Oit.msg.bas.dic.normal;
				} else if (value == '0') {
					return Oit.msg.bas.dic.freeze;
				} else {
					return "";
				}
			}
	    },
	    'canModify'
	 ]
});