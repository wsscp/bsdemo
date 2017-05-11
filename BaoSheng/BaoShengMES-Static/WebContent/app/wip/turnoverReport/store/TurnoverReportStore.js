Ext.define("bsmes.store.TurnoverReportStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.TurnoverReport',
		sorters: [{property:'shiftDate', direction:'DESC'}], 
		proxy : {			
			url : 'turnoverReport'
		},
		autoload: false
});