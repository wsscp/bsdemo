Ext.define('bsmes.store.ReportJSStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ReportJSModel',
			sorters : [{
						property : 'reportDate',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'js'
			}
		});