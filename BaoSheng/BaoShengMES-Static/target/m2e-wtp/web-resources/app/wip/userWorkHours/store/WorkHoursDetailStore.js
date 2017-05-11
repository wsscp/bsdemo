Ext.define('bsmes.store.WorkHoursDetailStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.WorkHoursDetailModel',
			autoLoad: false,
			sorters : [{
						property : 'createTime',
						column : 'CREATE_TIME',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'userWorkHours/workHoursDetail'
			}
		});