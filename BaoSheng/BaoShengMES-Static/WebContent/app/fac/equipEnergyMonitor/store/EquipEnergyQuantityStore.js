Ext.define('bsmes.store.EquipEnergyQuantityStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.EnergyQuantity',
			proxy : {
				type : 'rest',
				url : 'equipEnergyMonitor/energyQuantity'
			}
		});