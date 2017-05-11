Ext.define('bsmes.store.EquipMonthEnergyLoadStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.EquipEnergyMonitor',
			proxy : {
				type : 'rest',
				url : 'equipEnergyMonitor/energyMonthLoad'
			}
		});