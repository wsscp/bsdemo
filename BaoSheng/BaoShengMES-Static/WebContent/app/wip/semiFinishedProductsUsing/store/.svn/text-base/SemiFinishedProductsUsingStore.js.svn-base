Ext.define('bsmes.store.SemiFinishedProductsUsingStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.SemiFinishedProductsUsing',
	sorters : [ {
		property : 'FINISH_DATE',
		direction : 'DESC'
	} ],
	proxy : {
		type: 'rest',
		url : 'SemiFinishedProductsUsing'
	}
});