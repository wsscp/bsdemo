Ext.define('bsmes.store.SemiFinishedProductsStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.SemiFinishedProducts',
	sorters : [ {
		property : 'FINISH_DATE',
		direction : 'DESC'
	} ],
	proxy : {
		type: 'rest',
		url : 'SemiFinishedProducts'
	}
});