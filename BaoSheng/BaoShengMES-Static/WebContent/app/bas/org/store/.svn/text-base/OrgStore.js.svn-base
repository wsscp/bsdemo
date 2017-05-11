Ext.define('bsmes.store.OrgStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.Org',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'org'
	}
});

