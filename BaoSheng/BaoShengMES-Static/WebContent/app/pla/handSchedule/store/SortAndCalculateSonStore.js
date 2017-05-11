Ext.define('bsmes.store.SortAndCalculateSonStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.HandSchedule',
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'handSchedule'
			}
		});