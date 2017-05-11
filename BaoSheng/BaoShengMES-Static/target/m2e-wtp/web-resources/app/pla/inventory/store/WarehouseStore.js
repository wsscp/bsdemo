Ext.define('bsmes.store.WarehouseStore', {
			extend : 'Oit.app.data.GridStore',
			fields : ['id', 'warehouseName'],
			autoLoad : true,
			proxy : {
				type : 'rest',
				url : '../inv/warehouse/getAll'
			}
		});