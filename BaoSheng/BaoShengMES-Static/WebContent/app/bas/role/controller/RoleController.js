Ext.define('bsmes.controller.RoleController', {
	extend : 'Oit.app.controller.GridController',
	view : 'rolelist',
	editview : 'roleedit',
	addview : 'roleadd',
	resourcesView : 'roleResourceView',
	resourcesTreeView : 'resourcesTreeView',
	views : [ 'RoleList', 'RoleEdit' ,'RoleAdd','RoleResourceView' ,'ResourcesTreeView'],
	stores : [ 'RoleStore','ResourcesTreeStore' ],
	constructor: function() {
		var me = this;
		// 初始化refs
		me.refs = me.refs || [];
		
		me.refs.push({
			ref: 'resourcesView', 
			selector: me.resourcesView,
			autoCreate: true,
			xtype: me.resourcesView
		});
		me.refs.push({
			ref: 'resourcesTreeView', 
			selector: me.resourcesView +" "+me.resourcesTreeView
		});
		me.refs.push({
			ref: 'resourcesViewForm', 
			selector: me.resourcesView +" form"
		});
		
		me.callParent(arguments);
	},
	init: function() {
		var me = this;
		if (!me.view) {
			Ext.Error.raise("A view configuration must be specified!");
		}
		/**
		 * @event detail
		 * 点击add按钮前触发。
		 * @param {Ext.button.Button} btn 点击的button
		 */
		// 初始化工具栏
		me.control(me.view + ' button[itemId=assignResources]', {
			click: me.openResourcesView
		});
		me.control(me.resourcesView + ' button[itemId=roleResourceSave]', {
            click: me.saveRoleResource
        });
		me.control(me.resourcesView + ' button[itemId=roleResourceClose]', {
			click: me.closeResourcesView
		});
		me.callParent(arguments);
	},
	openResourcesView:function(){
		var me = this;
		var win = me.getResourcesView();
		var selectModel = me.getGrid().getSelectionModel();
		if(selectModel.hasSelection()){
			var selected = selectModel.getSelection();
			me.getResourcesViewForm().loadRecord(selected[0]);
			var root = me.getResourcesTreeView().getRootNode();
			me.getResourcesTreeView().getSelectionModel().select(root);
			me.getResourcesTreeView().getStore().getProxy().api.read= 'role/resourcesTree/'+selected[0].get("id");
			me.getResourcesTreeView().getStore().load();
			win.show();
		}else{
			Ext.MessageBox.alert(Oit.msg.WARN, "请选择一个角色!");
		}
	},
	closeResourcesView : function(){
		var me = this;
		var win = me.getResourcesView();
//		me.getResourcesTreeView().collapseAll();
		win.close();
	},
	saveRoleResource:function(){
		var me = this;
		Ext.MessageBox.confirm('确认', '确认更改角色资源?',  function(btn){
			if (btn == 'yes'){
				var win = me.getResourcesView();
				var modifiedRecords = me.getResourcesTreeView().getStore().getModifiedRecords();				
				me.getResourcesTreeView().getStore().sync({
					success:function(batch,options){
						Ext.MessageBox.alert(Oit.msg.PROMPT, "保存成功!");
					},
					failure :function(batch,options){
						Ext.MessageBox.alert(Oit.msg.PROMPT, "操作失败!");
					}
				});
				
//				Ext.Ajax.request({
//					waitMsg:Oit.msg.LOADING,  
//				    url: 'saveRoleResource',
//				    method: 'POST',
//				    params : {roleResourcesList:Ext.encode(modifiedRecords)},
//				    success: function(response){
//				    	
//		            	Ext.MessageBox.alert(Oit.msg.PROMPT, "操作成功!");
//				    },
//				    failure: function(response, opts) {
//				    	Ext.MessageBox.alert(Oit.msg.PROMPT, "操作失败!");
//				    }
//				});				
			}
		});
		
		//win.close();
	}
});