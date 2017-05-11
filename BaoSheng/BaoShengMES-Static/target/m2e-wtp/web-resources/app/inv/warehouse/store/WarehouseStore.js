Ext.define('bsmes.store.WarehouseStore',{
		extend:'Oit.app.data.GridStore',
		model:'bsmes.model.Warehouse',
		sorters:[{
					property : 'id',
					direction : 'DESC'
		}],
		proxy:{
			type: 'rest',
			url:'warehouse'
		}
});