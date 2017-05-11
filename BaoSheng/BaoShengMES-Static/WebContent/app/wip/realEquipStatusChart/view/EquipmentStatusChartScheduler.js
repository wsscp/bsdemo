Ext.define("bsmes.view.EquipmentStatusChartScheduler", {
			extend : 'Sch.panel.SchedulerGrid',
			columnLines : false,
			rowLines : false,
			alias : 'widget.equipmentStatusChartScheduler',
			width : document.body.scrollWidth,
			height : (document.body.scrollHeight - 125) / 3,
			readOnly : true,
			viewPreset : 'minuteAndHour',
			schedulerConfig : {
				columnLines : false,
				flex : 0
			},
			resourceStore : 'EquipmentStatusChartResourceStore',
			eventStore : 'EquipmentStatusChartEventStore',
			tooltipTpl : new Ext.XTemplate('<dl class="tip">',
					'<dt  >开始时间 :{[Ext.Date.format(values.startDate, "Y-m-d G:i")]}</dt>',
					'<dt  >结束时间 :{[Ext.Date.format(values.endDate, "Y-m-d G:i")]}</dt>', '</dl>').compile(),
			eventRenderer : function(item, r, tplData, row) {
				var me = this;
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
				var height = (document.body.scrollHeight - 125) / 3 - 64;
				tplData.style = "background-color:" + color + ";height:" + height + "px;border-color:" + color + ";";
			}
		});