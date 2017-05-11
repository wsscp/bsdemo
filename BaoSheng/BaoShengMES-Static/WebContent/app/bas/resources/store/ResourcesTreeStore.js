Ext.define('bsmes.store.ResourcesTreeStore', {
	extend : 'Oit.app.data.TreeStore',
	model : 'bsmes.model.Resources',
//	defaultRootId: '-1',
	root: {
		expanded: true,
		id:"-1",
		text: "根资源",
		name:"根资源"
	},
	proxy : {
		url : 'resources/tree'
	}
	
});