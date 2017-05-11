Ext.define('bsmes.store.UserProcessTraceStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.UserProcessTrace',
	sorters : [ {
		property : 'contractNo',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'userProcessTrace'
	}
});