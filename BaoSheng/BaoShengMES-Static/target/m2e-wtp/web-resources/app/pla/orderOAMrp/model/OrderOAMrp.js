Ext.define('bsmes.model.OrderOAMrp', {
			extend : 'Ext.data.Model',
			fields : ['id', 'contractNo', 'customerCompany', 'operator', 'custProductType', 'productType',
					'productSpec', 'matCode', 'matName', 'productCode', 'productName', 'unit', 'status', 'equipCode',
					'equipName', {
						name : 'quantity',
						type : 'double'
					}, {
						name : 'planDate',
						type : 'date'
					}]
		});