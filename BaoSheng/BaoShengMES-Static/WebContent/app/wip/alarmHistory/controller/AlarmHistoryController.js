Ext.define('bsmes.controller.AlarmHistoryController', {
	extend : 'Oit.app.controller.GridController',
	view : 'alarmHistoryGrid',
	views : [ 'AlarmHistoryGrid' ],
	stores : [ 'AlarmHistoryStore' ]
});

