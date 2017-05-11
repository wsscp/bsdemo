Ext.define('bsmes.store.EquipMESWWMappingStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.EquipMESWWMapping',
	sorters:[{}],
	proxy : {
		type: 'rest',
		url : 'equipMESWWMapping'
	}
});