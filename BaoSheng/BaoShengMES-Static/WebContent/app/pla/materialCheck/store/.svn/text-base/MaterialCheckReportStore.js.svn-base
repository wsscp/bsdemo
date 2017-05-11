Ext.define('bsmes.store.MaterialCheckReportStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.MaterialCheckReportModel',
			autoLoad: false,
			sorters : [{
						property : 'manuName',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'materialCheck'
			}
		});