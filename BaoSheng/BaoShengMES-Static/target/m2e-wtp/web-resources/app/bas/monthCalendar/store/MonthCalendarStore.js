Ext.define('bsmes.store.MonthCalendarStore', {
	extend : 'Ext.data.Store',
	model : 'bsmes.model.MonthCalendar',
	proxy: {
		reader : {
			root : 'rows'
		},
		type: 'ajax',
	    url : 'monthCalendar/queryWorkDay'
	}
});