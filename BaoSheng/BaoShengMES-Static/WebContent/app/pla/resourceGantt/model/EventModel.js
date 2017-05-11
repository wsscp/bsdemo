Ext.define('bsmes.model.EventModel', {
			extend : 'Sch.model.Event',
			resourceIdField : 'resourceId',
			startDateField : 'startDate',
			endDateField : 'endDate',
			nameField : 'name',
			fields : [{
						name : 'level',
						type : 'number'
					}, {
						name : 'outPut',
						type : 'string'
					}, {
						name : 'percentDone',
						type : 'string'
					}]
		});
