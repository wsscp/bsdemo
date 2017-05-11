Ext.define('bsmes.store.MesClientStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.MesClient',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'clientMac',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'mesClient'
	}
});