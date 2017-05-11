Ext.define('bsmes.store.ReportHHPTStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ReportHHPTModel',
			sorters : [{
						property : 'reportDate',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'hhpt'
			}
		});