Ext.define('bsmes.view.FixedEquipWindow', {
	extend : 'Oit.app.view.form.EditForm',
	title : Oit.msg.wip.btn.setFixedEquipCode,
	alias : 'widget.fixedEquipWindow',
	modal : true,
	plain : true,
	formItems : [ {
		fieldLabel : Oit.msg.wip.workOrder.workOrderNO,
		name : 'workOrderNo',
		xtype : 'displayfield'
	}, {
		fieldLabel : Oit.msg.wip.workOrder.processName,
		name : 'processName',
		xtype : 'displayfield'
	}, {
		fieldLabel : Oit.msg.wip.workOrder.equipName,
		name : 'equipName',
		xtype : 'displayfield'
	}, {
		itemId:'fixedEquip',
		fieldLabel : Oit.msg.wip.workOrder.fixedEquipName,
		displayField: 'equipName',
	    valueField: 'equipCode',
		name : 'fixedEquipCode',
		editable:false,
//		store : Ext.create('bsmes.store.WorkOrderEquipListStore'),
		xtype : 'combobox'
	} ]/*,
	buttons : [ {
		text : Oit.btn.ok,
		itemId : 'ok'
	}, {
		text : Oit.btn.close,
		itemId : 'close',
		handler : function() {
			this.up('.window').close();
		}
	} ]*/
});