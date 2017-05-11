Ext.define("bsmes.view.ResourcesList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.resourcesList',
	store : 'ResourcesStore',
	columns : [ 
	/*{
		text : "ID",
		dataIndex : 'id'
	},*/{
		text : Oit.msg.bas.resources.name,
		dataIndex : 'name',
		editor : 'textfield'
	}, {
		text : Oit.msg.bas.resources.uri,
		dataIndex : 'uri'
	},{
		text : Oit.msg.bas.resources.type,
		dataIndex : 'typeName',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.resources.description,
		dataIndex : 'description',
		editor : 'textfield'
	}],
	actioncolumn : [{
    	itemId : 'edit'
    }],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.resources.name,
		        name: 'name'
		    },{
		        fieldLabel: Oit.msg.bas.resources.uri,
		        name: 'uri'
		    },{
		    	fieldLabel: Oit.msg.bas.resources.parentRes,
		        name: 'parentId',
		        xtype: 'hiddenfield'
		    },{
		        fieldLabel: Oit.msg.bas.resources.parentRes,
		        name: 'parentName'
		    }]
		}, {
			itemId : 'search'
		}]
	}]
});
