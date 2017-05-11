Ext.define('bsmes.store.ResourcesStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.Resources',
	//fields: ['name', 'id'],
	autoLoad : {
        params: {parentId : -1}
    },
	sorters : [ {
		property : 'seq',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'resources'
	}
});