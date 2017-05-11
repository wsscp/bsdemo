Ext.define('bsmes.store.WorkOrderOperateLogStore', {
			extend : 'Oit.app.data.GridStore',
			fields : ['equipCode', 'equipName', 'userName', 'operateReason', {
						name : 'createTime',
						type : 'date'
					}],
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'handSchedule/workOrderOperateLog'
			}
		});
