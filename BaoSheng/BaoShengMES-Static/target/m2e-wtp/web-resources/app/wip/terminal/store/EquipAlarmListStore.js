Ext.define('bsmes.store.EquipAlarmListStore', {
	extend:'Oit.app.data.GridStore',
	model : 'bsmes.model.EquipAlarmList',
	autoLoad : false,
	sorters : [ {
		property : 'eventTitle',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : '/bsmes/wip/terminal/equipAlarmList'
	}
});