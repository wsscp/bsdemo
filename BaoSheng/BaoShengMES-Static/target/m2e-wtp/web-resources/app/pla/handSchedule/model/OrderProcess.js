Ext.define('bsmes.model.OrderProcess', {
			extend : 'Ext.data.Model',
			fields : ['id', 'processCode', 'orderItemId', 'processName', 'seq',
					'outProductColor', 'halfProductCode', 'equipCode',
					'nextProcessName', {
						name : 'latestStartDate',
						type : 'date'
					}, {
						name : 'lastFinishDate',
						type : 'date'
					}, {
						name : 'planWorkHours',
						type : 'double'
					}]
		});