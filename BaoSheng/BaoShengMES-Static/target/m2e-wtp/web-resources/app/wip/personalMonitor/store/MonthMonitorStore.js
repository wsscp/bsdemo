Ext.define('bsmes.store.MonthMonitorStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.MonthMonitorModel',
//			autoLoad: false,
			sorters : [{
						property : 'code',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'personalMonitor/monthReport'
			}
		});