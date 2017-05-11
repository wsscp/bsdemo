Ext.define("bsmes.view.OrgList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.orglist',
	store : 'OrgStore',
	columns : [{
		text : Oit.msg.bas.org.orgCode,
		dataIndex : 'orgCode',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.org.name,
		dataIndex : 'name',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.org.parentCode,
		dataIndex : 'parentCode',
		editor : 'textfield'
	},{
		text :Oit.msg.bas.org.type,
		dataIndex : 'type',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.org.description,
		dataIndex : 'description',
		editor : 'textfield'
	}],
	actioncolumn : [{
		itemId : 'detail'
    },'',{
    	itemId : 'edit'
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	} ],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.org.orgCode,
		        name: 'orgCode'
		    },
		    {
		        fieldLabel: Oit.msg.bas.org.name,
		        name: 'name'
		    }]
		}, {
			itemId : 'search'
		}]
	}]
});
