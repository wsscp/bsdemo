Ext.define('bsmes.model.EnergyMonthQuantity', {
			extend : 'Ext.data.Model',
			fields : [{name : 'EQUIP_NAME',type : 'String'},
			{name : 'ENERGY_MODIFY_TIME',type : 'String'},
			{name : 'MAX_POWER',type : 'String'},
			{name : 'MIN_POWER',type : 'String'},
			{name : 'AVG_POWER',type : 'String'},
			{name : 'DIFR',type : 'String'},
			{name : 'AVGR',type : 'String'}]
		});