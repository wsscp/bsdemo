Ext.define('bsmes.controller.InventoryController', {
			extend : 'Oit.app.controller.GridController',
			view : 'inventoryList',
			addview : 'inventoryAdd',
			editview : 'inventoryEdit',
			views : ['InventoryList', 'InventoryAdd', 'InventoryEdit'],
			stores : ['InventoryStore', 'MaterialNameStore', 'WarehouseStore'],
			init : function() {
				var me = this;
				if (!me.view) {
					Ext.Error.raise("A view configuration must be specified!");
				}
				me.callParent(arguments);
			}
		});
