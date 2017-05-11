Ext.define('bsmes.store.UserWorkHoursStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.UserWorkHoursModel',
			autoLoad: false,
			sorters : [{
						property : 'reportDate',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'userWorkHours'
			}
		});