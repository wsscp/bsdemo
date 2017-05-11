//PropConfig
Ext.define('bsmes.model.PropConfig', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'keyK',
	    'valueV',
	    'description',
	    {
	    	name : 'status',
			type : 'string',
			renderer : function(value) {
				if (value == 'true') {
					return Oit.msg.bas.prop.normal;
				} else if (value == 'false') {
					return Oit.msg.bas.prop.freeze;
				} else {
					return "";
				}
			}
	    },
	    'createUserCode',
	    {
	    	name : 'createTime',
	    	type : 'date'
	    },
	    'modifyUserCode',
	    {
	    	name : 'modifyTime',
	    	type : 'date'
	    }
	 ]
});