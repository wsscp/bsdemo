// Employee
Ext.define('bsmes.model.WorkOrderSubMode', {
			extend : 'Ext.data.Model',
			fields : ['id', 'workOrderNo', {
						name : 'subOaDate',
						type : 'date'
					}, {
						name : 'latestStartDate',
						type : 'date'
					}, {
						name : 'latestFinishDate',
						type : 'date'
					}, 'equipCode', 'equipName', 'orgName', 'contractNo', 'productSpec', 'productType', 'processName',
					'equipName', 'shift', 'taskLength', 'reportNum', 'color', 'reqTec', 'remarks', 'status',
					'isDelayed', 'operator', {
						name : 'auditTime',
						type : 'date'
					}, {
						name : 'planStartDate',
						type : 'date'
					}, {
						name : 'planFinishDate',
						type : 'date'
					}, 'percent', 'section', 'wiresStructure', 'halfProductCode'

			]
		});