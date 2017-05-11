Ext.define("bsmes.model.SpecialCraftsTrace", {
			extend : 'Ext.data.Model',
			fields : ['id', 'contractNo', 'productInfo', 'craftsCode',
					'processName', 'modifyValue', 'type','code','name',
					'processId','salesOrderItemId','operator','saleorderLength',
					'customerCompany','modifyUserCode', {
						name : 'modifyTime',
						type : 'Date'
					}]
		});
