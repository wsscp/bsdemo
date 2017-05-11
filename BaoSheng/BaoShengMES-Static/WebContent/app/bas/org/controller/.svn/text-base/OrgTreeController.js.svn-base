Ext.define('bsmes.controller.OrgTreeController', {
	extend: 'Ext.app.Controller',
	views : [ 'OrgTreeView'],
	view : "orgTreeView",
	stores : [ 'OrgTreeStore'],
	listController:"OrgController",
	constructor: function() {
		var me = this;
		me.refs = me.refs || [];
		
		me.refs.push({  
			ref: 'treeView',  
			selector: 'orgTreeView'
       });
		me.refs.push({
			ref: 'editView', 
			selector: me.editview, 
			autoCreate: true, 
			xtype: me.editview
		});
		me.refs.push({
			ref: 'editForm', 
			selector: me.editview + ' form'
		});
		me.callParent(arguments);
	},
	init: function() {
		var me = this;
		if (!me.views) {
			Ext.Error.raise("A view configuration must be specified!");
		}
	},
	onLaunch : function() {
		var me = this;
		var treeView = me.getTreeView();
		treeView.on("itemclick",function(self, record, item, index, e, eOpts){
			me.onDetail(record)
		});
		treeView.on("itemdblclick",function(self,record, item, index, e, eOpts){
			parent.openTab("用户管理", 'bas/user.action?orgCode='+record.data.orgCode+'&orgName='+record.data.name);
		});
	},
	getListController:function(){
		return bsmes.app.getController(this.listController);
	},
	onDetail: function(data) {
		var me = this;
		var listGrid = me.getListController().getGrid();
		var store = listGrid.getStore();
		var searchForm = me.getListController().getSearchForm();
		var record = Ext.create(store.model);
		record.set( "parentName", data.get("name") ); 
		record.set( "parentId", data.get("id") ); 
		searchForm.loadRecord(record);
		searchForm.updateRecord();
		store.loadPage(1, {
		    params: searchForm.getRecord().getData()
		});
	}
});

