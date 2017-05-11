Ext.define('bsmes.controller.ResourcesController', {
	extend : 'Oit.app.controller.GridController',
	view : 'resourcesList',
	editview : 'resourcesEdit',
	treeController:'ResourcesControllerWest',
	views : [ 'ResourcesList','ResourcesEdit' ],
	stores : [ 'ResourcesStore' ],
	onLaunch : function() {
		var me = this;
		me.callParent(arguments);
		
		// 绑定searchForm record
		if (me.getSearchForm()) {
			var grid = me.getGrid();
			var record = Ext.create(grid.getStore().model);
			var treeView = me.getTreeController().getTreeView();
			record.set( "parentName", treeView.getRootNode().get('name')); 
			record.set( "parentId", treeView.getRootNode().get('id'));
			me.getSearchForm().loadRecord(record);
		}
		
	},
	/**
	 * @private
	 */
	getTreeController:function(){
		return bsmes.app.getController(this.treeController);
	},
	onSave: function() {
		var me = this;
		me.callParent(arguments);
	}
});
