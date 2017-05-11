Ext.define('bsmes.store.ProductStore',{
	extend:'Ext.data.Store',
	model : 'bsmes.model.Product',
	sorters : [{} ],
	proxy : {
		type: 'rest',
		url : 'product'
	}
});