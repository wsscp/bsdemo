Ext.define('bsmes.store.AmoebaStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.Amoeba',
	sorters:[{}],
	proxy : {
		type: 'rest',
		url : 'amoeba'
	}
});