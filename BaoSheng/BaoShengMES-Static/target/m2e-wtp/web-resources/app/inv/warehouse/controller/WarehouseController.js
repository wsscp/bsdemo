Ext.define('bsmes.controller.WarehouseController',{
		extend:'Oit.app.controller.GridController',
		view:'warehouseList',
		editview : 'warehouseedit',
		addview : 'warehouseadd',
		views:['WarehouseList','WarehouseAdd','WarehouseEdit'],
		stores:['WarehouseStore'],
		init : function(){
			var me = this;
			me.control(me.view + ' button[itemId=add]', {
	            click: me.onAdd
	        });
	        me.control(me.view + ' button[itemId=remove]', {
	            click: me.onRemove
	        });
	        // 初始化编辑表单按钮
	        me.control(me.addview + ' button[itemId=ok]', {
	            click: me.onFormAdd
	        })
	        me.control(me.editview + ' button[itemId=ok]', {
	            click: me.onFormSave
	        });
	        // 初始化工具栏
	        me.control(me.view + ' button[itemId=search]', {
	            click: me.onSearch
	        });
		},
		onFormAdd:function(btn){
			var me = this;
	        var form = me.getAddForm();
	        form.updateRecord();
	        if (form.isValid()) {
	            var record = form.getRecord();
	            var store = me.getGrid().getStore();
	            // 同步到服务器
	            store.insert(store.totalCount, record);
	            store.sync();
	            // 关闭窗口
	            me.getAddView().close();
	        }
		},
		onFormSave:function(btn){
			 var me = this;
		        var form = me.getEditForm();
		        form.updateRecord();
		        if (form.isValid()) {
		            var record = form.getRecord();
		            var store = me.getGrid().getStore();
		            // 同步到服务器
		            store.sync();
		            // 关闭窗口
		            me.getEditView().close();
		        }
		}
});