Ext.define('bsmes.store.EqipCalendarShiftStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.EqipCalendarShift',
	sorters : [ {
		property : 'orgCode'
	} ],
	proxy : {
		url : 'eqipCalendarShift'
	}
});