Ext.define('bsmes.controller.MonthCalendarController', {
	extend : 'Ext.app.Controller',
	view : 'calendarpanel',
	views : [ 'MonthCalendarView'],
	stores : [ 'MonthCalendarStore']
});