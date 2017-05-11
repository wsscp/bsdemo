Ext.define("bsmes.view.Scheduler", {
	extend:'Sch.panel.SchedulerGrid',
	columnLines : false,
	rowLines : false,
	alias : 'widget.Scheduler',
	readOnly : true,
	viewPreset : 'minuteAndHour',
	schedulerConfig : {
		columnLines : false,
		flex : 0
	},
	eventRenderer : function(item, r, tplData, row) {
		var type = item.get('name');
		var color;
		if (type == 'IN_PROGRESS') {
			color = '#32B168';
		} else if (type == 'IN_DEBUG') {
			color = '#57A0CC';
		} else if (type == 'IDLE') {
			color = '#CAD638';
		} else if (type == 'CLOSED') {
			color = '#C2C6C9';
		} else if (type == 'ERROR') {
			color = '#E52E20';
		} else {
			color = '#EEB12B';
		}
		var height = document.body.scrollHeight -160;
		tplData.style = "background-color:" + color + ";height:" + height
				+ ";border-color:" + color + ";";
	}
});