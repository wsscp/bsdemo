Ext.define('Oit.app.controller.TreeController', {
	extend: 'Ext.app.Controller',
	constructor: function() {
		var me = this;
		
		// 初始化refs
		me.refs = me.refs || [];
		
		me.refs.push({  
			ref: 'treeView',  
			selector: me.view
       });
		
		// editForm
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
		if (!me.view) {
			Ext.Error.raise("A view configuration must be specified!");
		}
		/**
		 * @event detail
		 * 点击add按钮前触发。
		 * @param {Ext.button.Button} btn 点击的button
		 */
		
		// 初始化工具栏
		me.control(me.view + ' button[itemId=add]', {
			click: me.onAdd
		});
		me.control(me.view + ' button[itemId=remove]', {
			click: me.onRemove
		});
		
		// 初始化编辑表单按钮
		me.control(me.editview + ' button[itemId=ok]', {
			click: me.onFormSave
		});
	},
	onLaunch : function() {
		var me = this;
		var treeView = me.getTreeView();
		treeView.on("itemclick", function(self, record, item, index, e, eOpts){
			me.onDetail(record);
		});
	},
	/**
	 * 获取选中行的记录
	 * @protected
	 * @param {Ext.tree.Panel} 
	 */
	getSelectedData : function() {
		var me = this;
		var treeView = me.getTreeView();
		return treeView.getSelectionModel().getLastSelected() ;
	},
	/**
	 * @private
	 */ 
	onAdd: function() {
		var me = this;
		me.doAdd();
	},
	/**
	 * Template method
	 * @protected
	 */
	doAdd:function(){
		var me = this;
		var editView = me.getEditView();
		var treeView = me.getTreeView();
		var parentModelData = me.getSelectedData() || treeView.getRootNode();
		var record = Ext.create(treeView.getStore().model);
		record.set("parentId",parentModelData.get("id"));
		record.set("parentName",parentModelData.get("name"));
		me.getEditForm().loadRecord(record);
		editView.show();
	},
	/**
	 * @private
	 */ 
	onFormSave: function(btn) {
		var me = this;
		var form = me.getEditForm(); 
		var treeView = me.getTreeView();
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getTreeView().getStore();
			// 同步到服务器
			var parentNode = me.getSelectedData() || treeView.getRootNode();
			var record = form.getRecord();
			
			var child = parentNode.insertChild(0, record);
			store.sync();
			
			// 关闭窗口
			me.getEditView().close();
			
			me.onDetail(parentNode);
//			setTimeout(function(){ // why 重新load？
//				store.load({
//					node:parentNode
//				});
//			},0);
			
        }
	},
	/**
	 * @private
	 */ 
	onRemove: function() {
		var me = this;
		var data = me.getSelectedData();
		if (data) {
			me.doRemove(data);
		}
	},
	/**
	 * Template method
	 * @protected
	 * @param {Ext.data.Model} data
	 */
	doRemove: function(data) {
		var me = this;
		Ext.MessageBox.confirm('确认', '确认删除?',  function(btn){
			if (btn == 'yes'){
				var treeView = me.getTreeView();
				var store = treeView.getStore();
				var parentNode=data.parentNode;
				if(parentNode){
					var removeIndex = parentNode.indexOf(data); 
					parentNode.removeChild(data, false); 
					store.sync({
						success:function(batch,options){
							Ext.Msg.show({title: '操作成功', msg: "删除成功", buttons: Ext.Msg.OK, icon: Ext.Msg.INFO});
						},
						failure :function(batch,operation){
							parentNode.insertChild(removeIndex,data);
							store.removed=[];
						}
					});
				}
			}
		})
	},
	/**
	 * @protected
	 */ 
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