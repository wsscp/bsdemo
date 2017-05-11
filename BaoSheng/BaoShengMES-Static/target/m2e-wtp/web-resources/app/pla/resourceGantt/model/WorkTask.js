Ext.define('bsmes.model.WorkTask', {
			extend : 'Sch.model.Resource',
			fields : [{
						name : 'Id',
						mapping : 'id'
					}, {
						name : 'Name',
						mapping : 'name'
					}, {
						name : 'equipCode',
						type : 'string'
					}, {
						name : 'startDate',
						type : 'string'
					}, {
						name : 'endDate',
						type : 'string'
					}, {
						name : 'outPut',
						type : 'string'
					}]
		});
