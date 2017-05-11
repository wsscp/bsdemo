Ext.define('bsmes.model.EquipEventCase', {
    extend: 'Ext.data.Model',
    fields: [{name : 'equipCode',type : 'string'},
	          {name : 'eventContent',type : 'string'},
	          {name : 'equipName',type : 'string'},
	          {name : 'createTime',type : 'date'},
	          {name : 'responsed',type : 'string'},
	          {name : 'responseTime',type : 'date'},
	          {name : 'responseTimes',type : 'long'},
	          {name : 'complete',type : 'string'},
	          {name : 'completeTime',type : 'date'},
	          {name : 'status',type : 'string'},
	          {name : 'completeTimes',type : 'long'},
	          {name : 'id',type : 'string'},
	          {name : 'equipModelStandard',type : 'string'},
	          {name : 'userName',type : 'string'},
	          {name : 'protectMan',type : 'string'}]
});
