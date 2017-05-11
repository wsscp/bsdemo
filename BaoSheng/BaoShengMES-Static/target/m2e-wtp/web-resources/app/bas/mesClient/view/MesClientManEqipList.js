Ext.define("bsmes.view.MesClientManEqipList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.mesClientManEqiplist',
	store : 'MesClientManEqipStore',
	columns : [{
		text : Oit.msg.bas.mesClientManEqip.mesClientId,
		dataIndex : 'mesClientName'
	}, {
		text : Oit.msg.bas.mesClientManEqip.eqipId,
		dataIndex : 'equipName'
	}],
	actioncolumn : [{
    	itemId : 'edit'
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	}]
});