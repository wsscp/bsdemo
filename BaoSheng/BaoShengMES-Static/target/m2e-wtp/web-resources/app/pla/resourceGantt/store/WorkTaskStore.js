Ext.define('bsmes.store.WorkTaskStore', {
			extend : 'Sch.data.ResourceTreeStore',
			model : 'bsmes.model.WorkTask',
			proxy : {
				type : 'ajax',
				url : 'workTask/loadData',
				reader : {
					type : 'json'
				},
				timeout : 120000
			}
		});
