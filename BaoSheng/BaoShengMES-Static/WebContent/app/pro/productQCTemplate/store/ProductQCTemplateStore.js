Ext.define('bsmes.store.ProductQCTemplateStore',{
	extend:'Oit.app.data.GridStore',
	model : 'bsmes.model.ProductQCTemplate',
	sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'productQCTemplate'
	}
});