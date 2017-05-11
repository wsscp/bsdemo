Ext.define('bsmes.view.PriorityWindow', {
	extend : 'Ext.window.Window',
	title : Oit.msg.wip.btn.setPriority,
	layout : "border",
	alias : 'widget.priorityWindow',
	width : 1300,
	height : 543,
	modal : true,
	closeAction : 'destory',
	plain : true,
	requires : [ 'bsmes.view.PriorityLeftList', 'bsmes.view.PriorityCenter',
		'bsmes.view.PriorityRightList' ],
	items : [{
			// title: Oit.msg.wip.workOrder.disorder,
			region : "north",
			collapsible : false,
			split : true,
			width : '100%',
			items : [ {
				itemId :"workOrderSearchForm",
				xtype : 'form',
				layout : 'vbox',
				width : '100%',
				buttonAlign : 'left',
				labelAlign : 'left',
				defaults : {
				xtype : 'panel',
					width : '100%',
					layout : 'column',
					defaults : {
					width : 250,
						padding : 5,
						labelAlign : 'left'
				}
			},
items : [{
	items:[{
		fieldLabel : Oit.msg.wip.workOrder.equipName,
		name : 'equipCode',
		xtype : 'combobox',
		displayField : 'name',
		valueField : 'code',
		minChars : 1,
		store : Ext.create('bsmes.store.WorkOrderEquipStore'),
		listeners : {
			'beforequery' : function(queryPlan, eOpts) {
				var me = this;
				var url = 'workOrder/equip';
				if (queryPlan.query) {
					me.getStore().getProxy().url = url + "/"
					+ queryPlan.query;
				} else {
					me.getStore().getProxy().url = url + "/-1";
				}

			}
		}
	},{
		itemId : 'search',
		xtype:'button',
		text : Oit.btn.search,
		width : 100
	}]
}]
}]
}, {
	title : Oit.msg.wip.workOrder.disorder,
		region : "west",
		collapsible : false,
		split : true,
		width : 600,
		items : [ {
		height: 395,
		xtype : 'leftGridView'
	} ]
}, {
	region : "center",
		split : true,
		width : 50,
		height : 500,
		items : [ {
		xtype : 'centerView'
	} ]
}, {
	title : Oit.msg.wip.workOrder.selectedOrder,
		region : "east",
		split : true,
		height: 395,
		width : 600,
		items : [ {
		xtype : 'rightGridView'
	} ]
} ],
buttons : [ {
	text : Oit.btn.close,
	itemId : 'close'
} ]
});