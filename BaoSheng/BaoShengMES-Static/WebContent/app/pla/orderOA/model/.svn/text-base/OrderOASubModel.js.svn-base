Ext.define('bsmes.model.OrderOASubModel', {
			extend : 'Ext.data.Model',
			fields : ['processName', 'equipCode', 'equipName', 'halfProductCode', 'optionalEquipCode', 'orderItemId',
					'processId', 'color', {
						name : 'length',
						type : 'double'
					}, {
						name : 'finishedLength',
						type : 'double'
					}, {
						name : 'usedLockLength',
						type : 'double'
					}, {
						name : 'latestStartDate',
						type : 'date'
					}, {
						name : 'latestFinishDate',
						type : 'date'
					}, {
						name : 'planWorkHours',
						type : 'double'
					}, {
						name : 'totalVolumes',
						type : 'int'
					}, {
						name : 'status',
						type : 'string',
						conver : function(value) {
							if (value == 'TO_DO') {
								return '未开始';
							} else if (value == 'IN_PROGRESS') {
								return '生产中';
							} else {
								return '已完成';
							}
						}
					}]
		});