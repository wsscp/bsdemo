Ext.define('bsmes.model.WorkOrder', {
			extend : 'Ext.data.Model',
			fields : ['id', 'workOrderNo', 'equipCode', 'equipName', 'processName', 'workOrderSection', {
						name : 'preStartTime',
						type : 'date'
					}, {
						name : 'preEndTime',
						type : 'date'
					}, 'orderLength', 'seq', 'docMakerUserCode', 'receiverUserCode', {
						name : 'releaseDate',
						type : 'date'
					}, {
						name : 'requireFinishDate',
						type : 'date'
					}, {
						name : 'realEndTime',
						type : 'date'
					}, 'userComment', 'processCode', {
						name : 'isOldLine',
						type : 'boolean'
					}, {
						name : 'isDispatch',
						type : 'boolean'
					}, {
						name : 'isAbroad',
						type : 'boolean'
					}, 'status', 'contractNo', 'typeSpec', 'specialReqSplit', 'contractLength', {
						name : 'percent',
						type : 'double'
					}, 
					'cusOrderItemIds', // 生产单关联的订单ID
					'auditedCusOrderItemIds', // 生产单下面的订单已经下发的订单ID
					'completeCusOrderItemIds', // 针对nextSection=5: 生产单已经完成成缆部分的订单ID
					'nextSection', // 下一道工段/当前所属工段: 1绝缘;2成缆;3护套;5成缆/护套
					'orderfilenum' // 生产单关联的订单附件数量
			]
		});