Ext.define("bsmes.view.RoleResourceView", {
	extend : 'Ext.window.Window',
	alias : 'widget.roleResourceView',
	title : Oit.msg.bas.role.assignResources,
	width : 900,
	height : 500,
	// layout : {
	// type : 'vbox',
	// padding : 5
	// },
	modal : true,
	plain : true,
	buttons : [ {
		itemId : 'roleResourceSave',
		text : Oit.btn.ok
	}, {
		itemId : 'roleResourceClose',
		text : Oit.btn.close
	} ],
	items : [{
		xtype : 'form',
		layout : 'vbox',
		defaults : {
			labelAlign : 'right'			
		},
		items : [ {
			xtype:'displayfield',
			name : 'name',
			height : 20,
			fieldLabel : Oit.msg.bas.role.name
		} ]
	}, {
		xtype : 'resourcesTreeView',
		padding:'10 0 0 0'
	} ]
});
