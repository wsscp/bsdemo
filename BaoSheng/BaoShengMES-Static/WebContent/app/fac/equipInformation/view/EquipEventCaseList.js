Ext.define("bsmes.view.EquipEventCaseList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.equipEventCaseList',
	id : 'equipEventCaseList',
	defaultEditingPlugin : false,
	selModel : {
		mode : 'MULTI'
	},
	selType : 'checkboxmodel',
	store : 'EquipEventCaseStore',
	columns : [{
		text : 'id',
		dataIndex : 'id',
		hidden : true
	},{
		text : Oit.msg.fac.equipMaintenance.eventContent,
        dataIndex : 'eventContent',
        flex : 6,
        minWidth : 300
	},{
		text : Oit.msg.fac.equipMaintenance.createTime,
		dataIndex : 'createTime',
		xtype:'datecolumn',
		format: 'Y-m-d H:i:s',
		flex : 3,
        minWidth : 150
	},{
		text : Oit.msg.fac.equipMaintenance.responseTime,
		dataIndex : 'responseTime',
		xtype:'datecolumn',
		format: 'Y-m-d H:i:s',
		flex : 3,
        minWidth : 150
	},{
		text : Oit.msg.fac.equipMaintenance.responsed,
		dataIndex : 'responsed',
		flex : 2,
        minWidth : 100
	},{
		text : Oit.msg.fac.equipMaintenance.responseTimes,
		dataIndex : 'responseTimes',
		flex : 2,
        minWidth : 100
	},{
		text : Oit.msg.fac.equipMaintenance.completeTime,
		dataIndex : 'completeTime',
		xtype:'datecolumn',
		format: 'Y-m-d H:i:s',
		flex : 3,
        minWidth : 150
	},{
		text : Oit.msg.fac.equipMaintenance.complete,
		dataIndex : 'complete',
		flex : 2,
        minWidth : 100
	},{
		text : Oit.msg.fac.equipMaintenance.completeTimes,
		dataIndex : 'completeTimes',
		flex : 2,
        minWidth : 100
	}],
	tbar : [ {
        text : '查看',
        itemId : 'check'
	}]
});


