Ext.define('bsmes.store.ProductQCResultStore',{
	extend:'Oit.app.data.GridStore',
	model : 'bsmes.model.ProductQCResult',
	sorters : [ {
		property : 'productCode',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'productQCResult'
	}
});