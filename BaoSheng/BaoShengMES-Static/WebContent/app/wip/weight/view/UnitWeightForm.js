Ext.define("bsmes.view.UnitWeightForm", {
	extend : 'Oit.app.view.form.HForm',
	alias : 'widget.unitWeightForm',
	layout : 'vbox',
	defaults : {
		labelAlign : 'right'
	},
	items : [ {
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
