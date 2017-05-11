Ext.define('bsmes.store.ProcessQAStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ProcessQA',
			sorters : [{
						property : 'createTime',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'processQA'
			}
		});