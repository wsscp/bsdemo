Ext.define('bsmes.controller.ResourcesControllerWest', {
	extend: 'Oit.app.controller.TreeController',
	views : [ 'ResourcesTreeView','ResourcesAddView','SeqWindow'],
	view : "resourcesTreeView",
	editview : "resourcesAddView",
	seqView : 'seqWindow',
	stores : [ 'ResourcesTreeStore'],
	listController:"ResourcesController",constructor: function() {
		var me = this;
		
		// 初始化refs
		me.refs = me.refs || [];
		
		me.refs.push({  
			ref: 'seqView',  
			autoCreate: true, 
			xtype: me.seqView,
			selector: me.seqView
		});
		me.refs.push({  
			ref: 'seqGrid',  
			selector: me.seqView + " grid"
		});
		
		me.callParent(arguments);
	},
	init: function() {
		var me = this;
		/**
		 * @event detail
		 * 点击add按钮前触发。
		 * @param {Ext.button.Button} btn 点击的button
		 */
		
		// 初始化工具栏
		me.control(me.view + ' button[itemId=seq]', {
			click: me.onSeq
		});
		
		me.control(me.seqView + ' button[itemId=move]',{
			click:me.upMove
		});
		
		me.control(me.seqView + ' button[itemId=down]',{
			click:me.downMove
		});
		
		me.control(me.seqView + ' button[itemId=top]',{
			click:me.topMove
		});
		
		me.control(me.seqView + ' button[itemId=end]',{
			click:me.endMove
		});
		me.control(me.seqView + ' button[itemId=ok]',{
			click:me.updateSeq
		});
		
		
		me.callParent(arguments);
	},
	/**
	 * @private
	 */
	getListController:function(){
		return bsmes.app.getController(this.listController);
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
	},
	onSeq:function(){
		var me = this;
		var store = Ext.create('bsmes.store.ResourcesStore',{
			autoLoad:false
		});
		var record = me.getSelectedData();
		var url = 'resources/tree/menu';
		if(record){
			store.getProxy().url = url + "/" + record.get("id");
		}else{
			store.getProxy().url = url+ "/-1";
		}
		me.getSeqView().show();
		me.getSeqGrid().reconfigure(store);
		store.load();
	},
	upMove:function(){
		var me = this;
		var grid = me.getSeqGrid();
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		
		if(selection){
			selection.sort(me.sortSeq);
			var insertIndex = store.indexOf(selection[0]);
			Ext.Array.each(selection,function(record,i){
				var index = store.indexOf(record);
				if (insertIndex > 0) {  
					store.removeAt(index);  
					store.insert(index-1, record);  
				}
			});
			grid.getView().refresh();  
		}
	},
	downMove:function(){
		var me = this; 
		var grid = me.getSeqGrid();
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if(selection){
			selection.sort(me.sortSeq);
			var insertIndex = store.indexOf(selection[0]);
			Ext.Array.each(selection,function(record,i){
				var index = store.indexOf(record);  
				if (index < store.getCount() - 1) {  
					store.removeAt(index);  
					store.insert(index + 1, selection);  
				}
			});
			grid.getView().refresh();//刷新行号
		}
	},
	topMove:function(){
		var me = this;
		var grid = me.getSeqGrid();
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if(selection){
			selection.sort(me.sortSeq);
			Ext.Array.each(selection,function(record,i){
				var index = store.indexOf(record); 
				store.removeAt(index);
				store.insert(i, record);  
			});
			grid.getView().refresh();//刷新行号  
		}
	},
	endMove:function(){
		var me = this;
		var grid = me.getSeqGrid();
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if(selection){
			selection.sort(me.sortSeq);
			Ext.Array.each(selection,function(record,i){
				var index = store.indexOf(record); 
				store.removeAt(index);
				store.insert(store.getCount()+i, record); 
			});
		  	grid.getView().refresh();//刷新行号  
		}
	},
	sortSeq:function(resources1,resources2){
		return resources1.data.seq-resources2.data.seq;
	},
	updateSeq:function(){
		var me = this;
		var store = me.getSeqGrid().getStore();
		//更新列表数据
		var result = new Array();
		var recordDatas = store.data.items;
		if(recordDatas){
			Ext.each(recordDatas,function(record,i){
				record.set("seq",i+1);
				result.push(record.getData());
			});
		}
		Ext.Ajax.request({
			url:'resources/updateSeq',
			method:'POST',
			params:{
			  	'updateSeq':Ext.encode(result)
			},
			success:function(response){
				var data = me.getSelectedData();
				if(data){
				}else{
					data = Ext.create("bsmes.model.Resources");
					data.set("name","根资源");
					data.set("id","-1");
					data.set("text","根资源");
				}
				me.onDetail(data);
			}
		});
	}
});