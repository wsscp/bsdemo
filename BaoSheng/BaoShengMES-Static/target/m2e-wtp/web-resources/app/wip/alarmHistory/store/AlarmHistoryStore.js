Ext.define('bsmes.store.AlarmHistoryStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.AlarmHistoryModel',
	sorters : [ {
//		property : 'eventStampUTC',
//		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'alarmHistory'
	}
});