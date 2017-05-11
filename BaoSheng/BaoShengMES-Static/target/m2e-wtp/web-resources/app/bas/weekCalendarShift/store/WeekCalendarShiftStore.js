Ext.define('bsmes.store.WeekCalendarShiftStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.WeekCalendarShift',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'orgCode'
	} ],
	proxy : {
		url : 'weekCalendarShift'
	}
});