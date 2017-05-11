Ext.define('bsmes.store.EquipFaultManageStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.EquipFaultManage',
	sorters : [ {
		property : 'eventTitle',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'equipFaultManage'
	}
});