Ext.define("bsmes.store.ProductStatusTraceStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.ProductStatusTrace',
		proxy : {
			type: 'rest',
			url : 'productStatusTrace'
		},
		sorters : [{
			property : 'workOrderNo',
			direction : 'ASC'
		}]
});