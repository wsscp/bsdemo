Ext.define('bsmes.store.DailyMonitorStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.DailyMonitorModel',
//			autoLoad: false,
			sorters : [{
						property : 'code',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'personalMonitor'
			}
		});