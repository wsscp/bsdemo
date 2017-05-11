Ext.define('bsmes.store.ProcessReceiptEquipStore', {
	extend : 'Ext.data.Store',
	model : 'bsmes.model.Equip',
	idProperty: 'code',
	autoLoad : false,
	sorters:[{}],
	proxy : {
		type: 'rest',
		url : '../fac/equipInfo/getEquipLine'
	}
});