Ext.define('bsmes.store.ReportBZPBStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ReportBZPBModel',
			sorters : [{
						property : 'reportDate',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'bzpb'
			}
		});