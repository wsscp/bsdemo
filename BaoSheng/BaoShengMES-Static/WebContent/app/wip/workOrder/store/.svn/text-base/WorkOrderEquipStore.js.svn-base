Ext.define('bsmes.store.WorkOrderEquipStore', {
	extend : 'Ext.data.Store',
	model : 'bsmes.model.WorkOrderEquip',
	//fields: ['name', 'id'],
	idProperty: 'code',
	sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : '/workOrder/equip'
		
	}
});