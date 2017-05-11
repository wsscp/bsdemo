Ext.define('bsmes.store.EquipMonthEnergyQuantityStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.EnergyMonthQuantity',
			proxy : {
				type : 'rest',
				url : 'equipEnergyMonitor/energyMonthQuantity'
			}
		});