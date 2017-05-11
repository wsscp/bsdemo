Ext.define('bsmes.store.EventStore', {
			extend : 'Sch.data.EventStore',
			model : 'bsmes.model.EventModel',
			autoLoad : true,
			proxy : {
				type : 'ajax',
				url : 'workTask/getTasks',
				reader : {
					type : 'json'
				},
				timeout : 120000
			}
		});
