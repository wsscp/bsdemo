Ext.define('bsmes.store.ReportCLPBStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ReportCLPBModel',
			sorters : [{
						property : 'reportDate',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'clpb'
			}
		});