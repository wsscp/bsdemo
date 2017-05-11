Ext.define('bsmes.store.ProcessReceiptStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ProcessReceipt',
			autoLoad : false,
			proxy : {
				url : 'processReceipt?woNo=' + Ext.fly('orderDetail').getAttribute('num') + "&equipCode="
						+ Ext.fly('equipInfo').getAttribute('code')
			}
		});