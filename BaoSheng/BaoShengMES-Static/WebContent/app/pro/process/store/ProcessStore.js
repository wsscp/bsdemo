Ext.define('bsmes.store.ProcessStore',{
	extend:'Oit.app.data.GridStore',
	model : 'bsmes.model.Process',
	sorters : [{
		property : 'seq',
		direction : 'ASC'
	}],
	proxy : {
		type: 'rest',
		url : 'processBz',
		extraParams : {
			productCraftsId: Ext.fly('craftsIdSearch').getAttribute('value')
		}	
	}
});