Ext.define('bsmes.store.ProcessReceiptTraceStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ProcessReceiptTrace',
			autoLoad : true,
			remoteSort : false,
			sorters : [{}],
			proxy : {
				type : 'rest',
				url : '/bsmes/wip/processReceiptTrace',
				extraParams : {
					'equipCode' : Ext.fly('equipInfo').getAttribute('code') + '_EQUIP'
				}
			}
		});