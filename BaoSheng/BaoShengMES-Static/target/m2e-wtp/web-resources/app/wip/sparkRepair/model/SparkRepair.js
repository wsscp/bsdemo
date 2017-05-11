Ext.define("bsmes.model.SparkRepair", {
			extend : 'Ext.data.Model',
			fields : ['id', 'contractNo', 'workOrderNo', 'productCode',
					'sparkPosition', 'equipCode', 'repairType',
					'repairUserName', 'status', {
						name : 'createTime',
						type : 'Date'
					}, {
						name : 'modifyTime',
						type : 'Date'
					}, {
						name : 'isNotice',
						type : 'Boolean'
					}]
		});
