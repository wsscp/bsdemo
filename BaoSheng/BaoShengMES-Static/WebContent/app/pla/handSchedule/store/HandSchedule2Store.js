Ext.define('bsmes.store.HandSchedule2Store', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.WorkOrder',
			pageSize : 100,
			autoLoad : false,
			sorters : [{
						property : 'releaseDate',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'handSchedule/list2'
			}
		});