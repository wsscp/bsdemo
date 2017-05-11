Ext.define('bsmes.store.ProductSOPStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.ProductSOP',
	sorters : [ {
		property : 'productType',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest'
		//url : 'productSOP'
	}
});