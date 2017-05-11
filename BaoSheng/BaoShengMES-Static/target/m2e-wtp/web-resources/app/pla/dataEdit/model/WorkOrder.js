Ext.define('bsmes.model.WorkOrder', {
			extend : 'Ext.data.Model',
			fields : ['id', 'workOrderNo', 'equipCode', 'equipName', 'processName', 'workOrderSection', {
						name : 'preStartTime',
						type : 'date'
					}, {
						name : 'preEndTime',
						type : 'date'
					}, 'orderLength', 'seq', 'docMakerUserCode', 'receiverUserCode', 'releaseDate', {
						name : 'requireFinishDate',
						type : 'date'
					}, 'userComment', 'processCode', 'status', 'contractNo', 'typeSpec', 'specialReqSplit', {
						name : 'percent',
						type : 'double'
					}, 'cusOrderItemIds']
		});