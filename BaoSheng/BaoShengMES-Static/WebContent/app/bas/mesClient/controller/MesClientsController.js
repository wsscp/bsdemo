Ext.define('bsmes.controller.MesClientsController', {
	extend : 'Oit.app.controller.GridController',
	view : 'mesClientlist',
	editview : 'mesClientedit',
	addview  : 'mesClientadd',
	views : [ 'MesClientList', 'MesClientEdit' ,'MesClientAdd'],
	stores : [ 'MesClientStore' ],
	listController:"MesClientManEqipController",
	data:'',
	
	init : function(){
		var me = this;
		
        me.control(me.view + ' button[itemId=openTerminal]', {
            click: me.onEnterClick
        });
        me.callParent(arguments);
	},
	onSearch: function(btn) {
		var me = this;
		var store = me.getGrid().getStore();
		var form = me.getSearchForm();
		form.updateRecord();
		store.loadPage(1, {
		    params: {'clientName':encodeURI(form.getRecord().getData().clientName)}
		});
	},
	getListController:function(){
		return bsmes.app.getController(this.listController);
	},
//	doAdd : function(btn){
//		console.log('hdj');
//	},
////	onFormSave : function(btn){
////		console.log('dhf');
//		var me = this;
//		var form = me.getEditForm();
//		form.updateRecord();
//		if (form.isValid()) {
//			var store = me.getGrid().getStore();
//			// 同步到服务器
//			store.sync();
//			// 关闭窗口
//			me.getEditView().close();
//		}
//	},
	onFormAdd: function(btn) {
		var me = this;
		var form = me.getAddForm(); 
		var clientMac = form.getValues().clientMac;
		var result = true;
		form.updateRecord();
		Ext.Ajax.request({
			async: false,
			url:'mesClient/checkUnique/'+clientMac+'/',
			method:'GET',
			success:function(response){
				if(response.responseText=="true"){
					result = false;
					Ext.Msg.alert("错误信息","录入的数据已存在!");
				}
			}
		});
		if (form.isValid()&&result) {
			var store = me.getGrid().getStore();
			// 同步到服务器
			store.insert(0, form.getRecord());
			store.sync();
			// 关闭窗口
			me.getAddView().close();
        }
	},
	onLaunch : function() {
		var me = this;
		var grid = me.getGrid();
		
		if (!me.newFormToEdit) { // for edit plugin
			grid.on('edit', function() {
				me.onSave(grid);
			});
		}
		
		// 绑定searchForm record
		if (me.getSearchForm()) {
			var record = Ext.create(grid.getStore().model);
			me.getSearchForm().loadRecord(record);
		}
		
		grid.on("itemclick", function(self, record, item, index, e, eOpts){
			me.onDetail(record);
		});
		/**
         * @event toEdit
         * 打开编辑
         * @param {Ext.grid.Panel} grid 
         */
		grid.on('toEdit', me.doEdit, me);
	},
	onDetail: function(data) {
		var me = this;
		var listGrid = me.getListController().getGrid();
		var store = listGrid.getStore();
		store.loadPage(1, {
			params: {'mesClientId':data.get("id")}
		});
	},
	getSelectedData : function() {
		var me = this;
		var data = me.getGrid().getSelectionModel().getSelection();
		if (data.length == 0) {
			Ext.Msg.alert(Oit.msg.WARN, "请先在右侧栏视图中选择一条记录!");
			return null;
		} else {
			return data;
		}
	},
	onEnterClick : function(){
		var me = this;
		var data =me.getSelectedData()[0].getData();
		window.open('/bsmes/wip/terminal.action?ip='+data.clientIp+'&mac='+data.clientMac);
	}
});