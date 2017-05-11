Ext.define('bsmes.store.ProcessQcStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ProcessQc',
			autoLoad : false,
			proxy : {
				url : 'processQc?woNo=' + Ext.fly('orderDetail').getAttribute('num')
			}
		});