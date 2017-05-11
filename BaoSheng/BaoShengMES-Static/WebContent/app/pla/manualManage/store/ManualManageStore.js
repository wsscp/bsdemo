Ext.define('bsmes.store.ManualManageStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ManualManage',
			proxy : {
				type : 'rest',
				url : 'manualManage'
			}
		});