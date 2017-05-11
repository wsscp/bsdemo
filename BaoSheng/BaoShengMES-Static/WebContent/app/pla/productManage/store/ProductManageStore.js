Ext.define('bsmes.store.ProductManageStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.ProductManage',
	sorters:[{}],
	proxy : {
		type: 'rest',
		url : 'productManage'
	}
});