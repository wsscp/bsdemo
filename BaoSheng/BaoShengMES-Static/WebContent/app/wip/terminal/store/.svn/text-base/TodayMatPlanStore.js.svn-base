Ext.define('bsmes.store.TodayMatPlanStore', {
			extend : 'Oit.app.data.GridStore',
			fields : ['matCode', 'matName', {
						name : 'quantity',
						type : 'double'
					}, {
						name : 'unFinishedLength',
						type : 'double'
					}, 'inAttrDesc', 'unit', 'workOrderNo', 'contractNo', 'color'],
			proxy : {
				url : 'toDayMatPlan?equipCode=' + Ext.fly('equipInfo').getAttribute('code')
			},
			autoLoad : false
		});
