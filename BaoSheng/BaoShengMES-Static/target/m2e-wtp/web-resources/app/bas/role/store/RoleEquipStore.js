Ext.define('bsmes.store.RoleEquipStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.RoleEquip',
	sorters:[{}],
    autoLoad:false,
    defaultPageSize:14,
	proxy : {
		type: 'rest',
		url : 'role/getRoleEquip'
	}
});