Ext.define('bsmes.store.ProductStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.Product',
	sorters:[{}],
	proxy : {
		type: 'rest',
		url : 'product'
	}
});