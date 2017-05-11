Ext.define('bsmes.store.MesClientManEqipStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.MesClientManEqip',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'mesClientId',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'mesClientManEqip'
	}
});

