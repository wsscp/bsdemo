Ext.define('bsmes.store.OrgTreeStore', {
	extend : 'Oit.app.data.TreeStore',
	model : 'bsmes.model.Org',
	root: {
		expanded: true,
		id:"-1",
		text: "机构",
		name:"机构"
	},
	proxy : {
		url : 'org'
	}
});