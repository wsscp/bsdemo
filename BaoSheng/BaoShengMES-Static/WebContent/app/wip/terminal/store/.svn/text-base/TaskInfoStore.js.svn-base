Ext.define('bsmes.store.TaskInfoStore', {
			extend : 'Oit.app.data.GridStore',
			fields : ['ID', 'TASKID', 'RELATEORDERIDS', 'PRODUCTCODE', 'CONTRACTNO', 'OPERATOR', 'PRODUCTTYPE', 'PRODUCTSPEC',
					'CUSTPRODUCTTYPE', 'CUSTPRODUCTSPEC', 'NUMBEROFWIRES', 'WIRESSTRUCTURE', 'SPLITLENGTHROLE', 'WIRECOILCOUNT', 'COLOR',
					'WIRECOIL', 'OUTMATDESC', 'OUTATTRDESC', 'STATUS', 'EQUIPCODE', 'SPLITLENGTHROLEWITHYULIANG', {
						name : 'FINISHEDLENGTH',
						type : 'double'
					}, {
						name : 'TASKLENGTH',
						type : 'double'
					}, {
						name : 'ORDERLENGTH',
						type : 'double'
					}, {
						name : 'CONTRACTLENGTH',
						type : 'double'
					}],

			sorters : [{
						property : 'RELATEORDERIDS',
						direction : 'ASC'
					}, {
						property : 'COLOR',
						direction : 'ASC'
					}],
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'taskInfo/' + Ext.fly('orderInfo').getAttribute('num')
			}
		});
