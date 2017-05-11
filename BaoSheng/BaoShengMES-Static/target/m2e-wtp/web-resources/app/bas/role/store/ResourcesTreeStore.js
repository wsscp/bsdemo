Ext.define('bsmes.store.ResourcesTreeStore', {
	extend : 'Oit.app.data.TreeStore',
	model : 'bsmes.model.Resources',
	// defaultRootId: '-1',
	autoLoad : false,
	clearOnLoad : true,
	root : {
		expanded : true,
		id : "-1",
		text : "根资源",
		name : "根资源"
	},
	proxy : {
		/*url : 'role/resourcesTree/-1',*/
		api : {
			create : undefined  ,
			read : 'role/resourcesTree/-1',
			update : 'role/saveRoleResource',
			destroy : undefined
		}
	}

});