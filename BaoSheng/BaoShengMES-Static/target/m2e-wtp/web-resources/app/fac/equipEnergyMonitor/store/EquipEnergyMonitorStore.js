Ext.define('bsmes.store.EquipEnergyMonitorStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.EquipEnergyMonitor',
			proxy : {
				type : 'rest',
				url : 'equipEnergyMonitor'
			}
		});