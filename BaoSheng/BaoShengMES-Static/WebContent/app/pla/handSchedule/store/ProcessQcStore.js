Ext.define('bsmes.store.ProcessQcStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ProcessQc',
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : '../pro/processWip/qc',
				reader : {
					type : 'json',
					root : 'rows'
				}
			},
			sorters : [{
						property : 'checkItemName',
						direction : 'ASC'
					}]
		});