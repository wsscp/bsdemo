Ext.define('bsmes.view.ProductEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.productEdit',
	title: Oit.msg.pla.product.editForm.title,
	formItems: [{
        fieldLabel: Oit.msg.pla.product.productCode,
        name: 'productCode',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.pla.product.productName,
        name: 'productName',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.pla.product.productType,
        name: 'productType',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.pla.product.productSpec,
        name: 'productSpec',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.pla.product.standardLength,
        name: 'standardLength'
    },{
    	fieldLabel: Oit.msg.pla.product.complex,
        xtype:'radiogroup',
        columns:4,
        vertical:true,
        items:[{boxLabel: "是", name: 'complex', inputValue: 'true' },
               {boxLabel: "否", name: 'complex', inputValue: 'false'}]
    },{
        fieldLabel: Oit.msg.pla.product.craftsCode,
        name: 'craftsCode',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.pla.product.craftsVersion,
        name: 'craftsVersion',
        xtype: 'displayfield'
    },{
    	fieldLabel: Oit.msg.pla.product.usedStock,
        name: 'usedStockText',
        xtype: 'displayfield'
    }]
});

