Ext.define('bsmes.store.EquipMaintenanceStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.EquipMaintenance',
	sorters : [ {
		property : 'equipCode',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'equipMaintenance'
	}
});