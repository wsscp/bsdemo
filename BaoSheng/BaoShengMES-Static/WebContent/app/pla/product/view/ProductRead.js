Ext.define('bsmes.view.ProductRead', {
	extend: 'Ext.window.Window',
	alias: 'widget.productRead',
	title: Oit.msg.pla.product.readForm.title,
	iconCls: 'feed-add',
	width: 500,
	layout: 'fit',
	modal: true,
	plain: true,
	closable: false,
    initComponent: function() {
		var me = this;
		Ext.apply(me, {
			buttons: [{
				itemId: 'ok',
				text:Oit.btn.ok,
				handler:function(){
					me.hide();
				}
			}]
		});
		
		me.items = [{
			xtype: 'form',
			bodyPadding: '12 10 10',
		    items: [{
		        fieldLabel: Oit.msg.pla.product.productCode,
		        name: 'productCode',
		        xtype: 'displayfield',
		        readOnly: true
		    },{
		        fieldLabel: Oit.msg.pla.product.productName,
		        name: 'productName',
		        xtype: 'displayfield',
		        readOnly: true
		    },{
		        fieldLabel: Oit.msg.pla.product.productType,
		        name: 'productType',
		        xtype: 'displayfield',
		        readOnly: true
		    },{
		        fieldLabel: Oit.msg.pla.product.productSpec,
		        name: 'productSpec',
		        xtype: 'displayfield',
		        readOnly: true
		    },{
		        fieldLabel: Oit.msg.pla.product.standardLength,
		        name: 'standardLength',
		        xtype: 'displayfield',
		        readOnly: true
		    },{
		    	fieldLabel: Oit.msg.pla.product.complex,
		        name: 'complexText',
		        xtype: 'displayfield',
		        readOnly: true
		    },{
		        fieldLabel: Oit.msg.pla.product.craftsCode,
		        name: 'craftsCode',
		        xtype: 'displayfield',
		        readOnly: true
		    },{
		        fieldLabel: Oit.msg.pla.product.craftsVersion,
		        name: 'craftsVersion',
		        xtype: 'displayfield',
		        readOnly: true
		    },{
		    	fieldLabel: Oit.msg.pla.product.usedStock,
		        name: 'usedStockText',
		        xtype: 'displayfield',
		        readOnly: true
		    }]
		}],
		this.callParent(arguments);
	} 
});
