Ext.define("bsmes.view.PropConfigList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.propConfigList',
	store : 'PropConfigStore',
	columns : [{
		text : Oit.msg.bas.prop.keyK,
		dataIndex : 'keyK'
	}, {
		text : Oit.msg.bas.prop.valueV,
		dataIndex : 'valueV',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.prop.description,
		dataIndex : 'description',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.prop.status,
		dataIndex : 'status',
		editor : {
			xtype:'radiogroup',
			items:[{boxLabel: Oit.msg.bas.prop.normal, name: 'status', inputValue: '1'},
			       {boxLabel: Oit.msg.bas.prop.freeze, name: 'status', inputValue: '0'}]
		}
	}],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.prop.keyK,
		        name: 'keyK'
		    },{
		        fieldLabel: Oit.msg.bas.prop.valueV,
		        name: 'valueV'
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
