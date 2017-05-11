Ext.define('bsmes.store.HandScheduleStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.HandSchedule',
			pageSize : 50,
			sorters : [{
						property : 'FINISHJY',
						direction : 'ASC'
					}, {
						property : 'CREATETIME',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'handSchedule'
			}
		});