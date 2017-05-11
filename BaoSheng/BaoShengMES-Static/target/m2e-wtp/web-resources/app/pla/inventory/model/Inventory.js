Ext.define('bsmes.model.Inventory', {
			extend : 'Ext.data.Model',
			fields : ['id', 'locationId', 'warehouseId', 'warehouseName', 'materialCode', 'materialName', 'materialDesc', 'barCode', 'quantity',
					'unit', 'locationName', 'processCode', 'processName', 'locationX', 'locationY', 'locationZ']
		});