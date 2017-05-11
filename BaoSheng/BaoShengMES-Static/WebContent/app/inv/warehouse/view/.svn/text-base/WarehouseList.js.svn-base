Ext.define("bsmes.view.WarehouseList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.warehouseList',
	store : 'WarehouseStore',
	defaultEditingPlugin : false,
	columns : [{
		text : Oit.msg.inv.warehouse.warehouseCode,
		dataIndex : 'warehouseCode'
	}, {
		text : Oit.msg.inv.warehouse.warehouseName,
		dataIndex : 'warehouseName'
	},{
		text : Oit.msg.inv.warehouse.address,
		dataIndex : 'address'
	},{
		text : Oit.msg.inv.warehouse.type,
		dataIndex : 'type'
	}],
	actioncolumn : [{
    	itemId : 'edit'
    },'',{
    	tooltip : '库位',
		iconCls : 'icon_detail',
		handler : function(grid, rowIndex) {
			   var row = grid.getStore().getAt(rowIndex);
	           Oit.app.controller.GridController.openSubWindow('location.action?warehouseId=' + row.get('id'),'库位');
		}
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	}],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
		        fieldLabel:Oit.msg.inv.warehouse.warehouseCode,
		        name: 'warehouseCode'
		    },{
		        fieldLabel: Oit.msg.inv.warehouse.warehouseName,
		        name: 'warehouseName'
		    }]
		}, {
			itemId : 'search'
		}]
	}]
});
