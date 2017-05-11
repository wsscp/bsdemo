Ext.define("bsmes.view.ProductList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.productList',
	store : 'ProductStore',
	columns : [{
		text : Oit.msg.pla.product.productCode,
		dataIndex : 'productCode'
	}, {
		text : Oit.msg.pla.product.productName,
		dataIndex : 'productName'
	},{
		text : Oit.msg.pla.product.productType,
		dataIndex : 'productType'
	},{
		text : Oit.msg.pla.product.productSpec,
		dataIndex : 'productSpec'
	},{
		text : Oit.msg.pla.product.standardLength,
		dataIndex : 'standardLength',
		editor : 'textfield'
	},{
		text : Oit.msg.pla.product.complex,
		dataIndex : 'complexText'
	},{
		text : Oit.msg.pla.product.craftsCode,
		dataIndex : 'craftsCode'
	},{
		text : Oit.msg.pla.product.craftsVersion,
		dataIndex : 'craftsVersion'
	},{
		text : Oit.msg.pla.product.usedStock,
		dataIndex : 'usedStockText'
	}],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.pla.product.productCode,
		        name: 'productCode',
		        emptyText : Oit.msg.pla.product.productCode,
		        width:220,
		        labelWidth : 60,
				xtype : 'combobox',
				displayField : 'productCode',
				valueField : 'productCode',
				minChars : 1,
				store: new Ext.data.Store({
					proxy : { type: 'rest', url: 'product/productsCombo', reader: { type: 'json' } },
					sorters : [ { property : 'productName', direction : 'ASC' } ], // 排序
					fields: [{name: 'productCode',   type: 'string'},  
			 	        {name: 'productName',  type: 'string'}]
				}),
				listeners : {
					'beforequery' : function(queryPlan, eOpts) {
						var me = this;
						var url = 'product/productsCombo';
						if (queryPlan.query) {
							me.getStore().getProxy().url = url + "/" + queryPlan.query;
						} else {
							me.getStore().getProxy().url = url + "/-1";
						}
					}
				}
		    },{
		        fieldLabel: Oit.msg.pla.product.productName,
		        name: 'productName',
		        emptyText : Oit.msg.pla.product.productName,
		        width:220,
		        labelWidth : 60,
				xtype : 'combobox',
				displayField : 'productName',
				valueField : 'productName',
				minChars : 1,
				store: new Ext.data.Store({
					proxy : { type: 'rest', url: 'product/productsCombo', reader: { type: 'json' } },
					sorters : [ { property : 'productName', direction : 'ASC' } ], // 排序
					fields: [{name: 'productCode',   type: 'string'},  
			 	        {name: 'productName',  type: 'string'}]
				}),
				listeners : {
					'beforequery' : function(queryPlan, eOpts) {
						var me = this;
						var url = 'product/productsCombo';
						if (queryPlan.query) {
							me.getStore().getProxy().url = url + "/" + queryPlan.query;
						} else {
							me.getStore().getProxy().url = url + "/-1";
						}
					}
				}
		    },{
		        fieldLabel: Oit.msg.pla.product.productType,
		        name: 'productType',
		        emptyText : Oit.msg.pla.product.productType,
		        width:220,
		        labelWidth : 60,
				xtype : 'combobox',
				displayField : 'productType',
				valueField : 'productType',
				minChars : 1,
				store: new Ext.data.Store({
					proxy : { type: 'rest', url: 'product/productsTypeCombo', reader: { type: 'json' } },
					sorters : [ { property : 'productType', direction : 'ASC' } ], // 排序
					fields: [{name: 'productType',   type: 'string'}]
				}),
				listeners : {
					'beforequery' : function(queryPlan, eOpts) {
						var me = this;
						var url = 'product/productsTypeCombo';
						if (queryPlan.query) {
							me.getStore().getProxy().url = url + "/" + queryPlan.query;
						} else {
							me.getStore().getProxy().url = url + "/-1";
						}
					}
				}
		    }]
		}, {
			itemId : 'search'
		},{
            itemId : 'read',
            text: Oit.btn.detail,
            iconCls : 'icon_message'
        },{
            itemId : 'export',
            text: Oit.btn.export
        }]
	} ],
	actioncolumn : [{
    	itemId : 'edit'
    }]
});
