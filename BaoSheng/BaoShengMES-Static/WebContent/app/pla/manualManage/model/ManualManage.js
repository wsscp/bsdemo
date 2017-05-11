Ext.define('bsmes.model.ManualManage', {
			extend : 'Ext.data.Model',
			fields : [{name : 'contractNo',type : 'string'},
			{name: 'custProductType',type : 'string'},
			{name: 'contractLength',type : 'string'},
			'id',
			{name: 'ptFinishTime',type : 'date'},
			{name: 'clFinishTime',type : 'date'},
			{name: 'bzFinishTime',type : 'date'},
			{name: 'htFinishTime',type : 'date'},
			{name: 'remarks',type : 'string'},
			{name: 'coordinateTime',type : 'date'},
			{name: 'infoSources',type : 'string'}
			]
		});