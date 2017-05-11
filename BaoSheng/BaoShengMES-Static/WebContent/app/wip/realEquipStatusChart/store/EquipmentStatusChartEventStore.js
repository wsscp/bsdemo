Ext.define('bsmes.store.EquipmentStatusChartEventStore', {
			extend : 'Sch.data.EventStore',
			model : 'bsmes.model.Event',
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'realEquipStatusChart/getEvent'
			}
		});
