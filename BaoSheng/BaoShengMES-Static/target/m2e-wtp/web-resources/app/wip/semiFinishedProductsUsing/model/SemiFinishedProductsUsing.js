Ext.define('bsmes.model.SemiFinishedProductsUsing', {
	extend : 'Ext.data.Model',
	fields : ['id','matCode','contractNo','locationName',
	          'userName','shiftName','productCode','productType',
	          'productSpec','taskLength','processName',
	          'wireCoil','matName','isUsed',
	          {	
				name : 'finishDate',
				type: 'Date'
	          }]
});