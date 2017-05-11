Ext.define('bsmes.store.ChooseCraftsStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ChooseCrafts',
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'handSchedule/chooseCrafts'
			}
		});