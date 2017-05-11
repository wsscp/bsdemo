Ext.define("bsmes.view.EqipCalendarShiftList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.eqipCalendarShiftView',
	store : 'EqipCalendarShiftStore',
	columns : [{
		text : Oit.msg.bas.eqipCalendarShift.equipCode,
		dataIndex : 'equipCode'
	}, {
		text : Oit.msg.bas.eqipCalendarShift.name,
		dataIndex : 'name'
	},{
		text : Oit.msg.bas.eqipCalendarShift.dateOfWork,
		dataIndex : 'dateOfWork'
	}, {
		text : Oit.msg.bas.eqipCalendarShift.workShiftId,
		dataIndex : 'workShiftId'
	}],
	actioncolumn : [{
    	itemId : 'edit'
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
		        fieldLabel: Oit.msg.bas.eqipCalendarShift.equipCode,
		        name: 'equipCode'
		    }]
		}, {
			itemId : 'search'
		}]
	}]
});