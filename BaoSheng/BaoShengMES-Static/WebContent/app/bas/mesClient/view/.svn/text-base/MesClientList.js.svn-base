Ext.define("bsmes.view.MesClientList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.mesClientlist',
	store : 'MesClientStore',
	columns : [{
		text : Oit.msg.bas.mesClient.clientMac,
		dataIndex : 'clientMac'
	}, {
		text : Oit.msg.bas.mesClient.clientIp,
		dataIndex : 'clientIp'
	},{
		text : Oit.msg.bas.mesClient.clientName,
		dataIndex : 'clientName'
	}],
	actioncolumn : [{
    	itemId : 'edit'
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	},{
		xtype:'button',
		text:'进入终端',
		itemId:'openTerminal'
	}],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.mesClient.clientName,
		        name: 'clientName'
		    }]
		}, {
			itemId : 'search',
			style:{
				top : '29px'
			}
		}]
	}]
});

