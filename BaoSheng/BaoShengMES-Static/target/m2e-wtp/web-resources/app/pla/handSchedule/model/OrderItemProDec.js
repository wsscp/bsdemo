Ext.define('bsmes.model.OrderItemProDec', {
			extend : 'Ext.data.Model',
			fields : ['id', 'workOrderNo', 'equipCode', 'equipName',
					'processId', 'processName', 'halfProductCode',
					'orderLength', 'cancelLength', 'color', 'remarks',
					'isDelayed', 'fixedEquipCode', 'fixedEquipName', {
						name : 'seq',
						type : 'int'
					}, {
						name : 'auditTime',
						type : 'date'
					}, {
						name : 'preStartTime',
						type : 'date'
					}, {
						name : 'preEndTime',
						type : 'date'
					}, {
						name : 'realStartTime',
						type : 'date'
					}, {
						name : 'realEndTime',
						type : 'date'
					}, 'status', {
						name : 'createTime',
						type : 'date'
					},
					/** ******form查询条件******** */
					'orgName', 'customerContractNO', 'productSpec',
					'unFinishedLength', 'productType', 'operator', {
						name : 'preStartTimeFrom',
						type : 'date'
					}, {
						name : 'preStartTimeTo',
						type : 'date'
					}, 'percent', 'operator', 'outProductColor']
		});