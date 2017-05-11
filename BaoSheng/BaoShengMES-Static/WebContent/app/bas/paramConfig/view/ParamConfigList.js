Ext.define("bsmes.view.ParamConfigList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.paramConfigList',
	store : 'ParamConfigStore',
	columns : [{
		text : Oit.msg.bas.param.code,
		dataIndex : 'code'
	}, {
		text : Oit.msg.bas.param.name,
		dataIndex : 'name'
	},{
		text : Oit.msg.bas.param.value,
		dataIndex : 'value'
	},{
		text : Oit.msg.bas.param.type,
		dataIndex : 'type'
	},{
		text : Oit.msg.bas.param.description,
		dataIndex : 'description'
	},{
		text : Oit.msg.bas.param.status,
		dataIndex : 'status',
		editor : {
			xtype:'radiogroup',
			items:[{boxLabel: Oit.msg.bas.param.normal, name: 'status', inputValue: '1'},
			       {boxLabel: Oit.msg.bas.param.freeze, name: 'status', inputValue: '0'}]
		}
	}],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.param.code,
		        name: 'code'
		    },{
		        fieldLabel: Oit.msg.bas.param.name,
		        name: 'name'
		    }]
		}, {
			itemId : 'search'
		}]
	} ],
	actioncolumn : [{
    	itemId : 'edit'
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	}]
});
