Ext.define('bsmes.model.WeekCalendarShift', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'weekCalendarId',
		type : 'string'
	}, {
		name : 'workShiftId',
		type : 'string'
	}, {
		name : 'shiftStartTime',
		type : 'string'
	}, {
		name : 'shiftEndTime',
		type : 'string'
	}, {
		name : 'status',
		type : 'string',
		renderer : function(value) {
			if (value == '1') {
				return "有效";
			} else if (value == '0') {
				return "无效";
			} else {
				return "";
			}
		}
	}, {
		name : 'createTime',
		type : 'date'
	}, {
		name : 'orgCode',
		type : 'string'
	}
	]
});
