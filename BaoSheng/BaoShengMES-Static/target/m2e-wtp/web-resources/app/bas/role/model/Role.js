//User
Ext.define('bsmes.model.Role', {
	extend : 'Ext.data.Model',
	fields : [ 
	    {
	    	name:'id',
	    	type:'string'
	    },{
	    	name:'code',
	    	type:'string'
	    },{
	    	name:'name',
	    	type:'string'
	    },  {
	    	name:'orgCode',
	    	type:'string',
	    }, {
	    	name:'orgName',
	    	type:'string',
	    	persist : false
	    }, {
	    	name:'description',
	    	type:'string'
	    } 
	 ],
	validations : [ {
		type : 'length',
		field : 'name',
		min : 1
	},{
		type : 'length',
		field: 'orgCode',
		min:1
	} ]
});

