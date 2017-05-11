Ext.define("bsmes.view.WeekCalendarShiftList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.weekCalendarShiftlist',
	store : 'WeekCalendarShiftStore',
	columns : [{
		text : Oit.msg.bas.weekCalendarShift.weekCalendarId,
		dataIndex : 'weekCalendarId'
	}, {
		text : Oit.msg.bas.weekCalendarShift.workShiftId,
		dataIndex : 'workShiftId'
	},{
		text : Oit.msg.bas.weekCalendarShift.shiftStartTime,
		dataIndex : 'shiftStartTime'
	},{
		text : Oit.msg.bas.weekCalendarShift.shiftEndTime,
		dataIndex : 'shiftEndTime'
	},{
		text : Oit.msg.bas.weekCalendarShift.status,
		dataIndex : 'status'
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
		        fieldLabel: Oit.msg.bas.weekCalendarShift.workShiftId,
		        name: 'workShiftId'
		    }]
		}, {
			itemId : 'search'
		}]
	}]
});