Ext.define("bsmes.view.ScrapWeightForm", {
	extend : 'Oit.app.view.form.HForm',
	alias : 'widget.scrapWeightForm',
	layout : 'vbox',
	defaults : {
		labelAlign : 'right'
	},
	items : [{
		fieldLabel : Oit.msg.wip.weight.matCode,
		xtype : 'textfield',
		name : 'matCode',
		width : '100%'
	}, {
		fieldLabel : Oit.msg.wip.weight.workOrderNO,
		xtype : 'textfield',
		name : 'workOrderNO',
		width : '100%'
	}, {
		fieldLabel : Oit.msg.wip.weight.userCode,
		xtype : 'textfield',
		name : 'userCode',
		width : '100%'
	}, {
		fieldLabel : Oit.msg.wip.weight.weight,
		xtype : 'textfield',
		name : 'weight',
		width : '100%'
	}],
	buttons : [ {
		itemId : 'save',
		text : Oit.btn.save
	}]
});
