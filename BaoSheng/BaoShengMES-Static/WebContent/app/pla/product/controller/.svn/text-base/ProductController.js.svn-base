Ext.define('bsmes.controller.ProductController', {
	extend : 'Oit.app.controller.GridController',
	view : 'productList',
	readview  : 'productRead',
	editview  : 'productEdit',
	views : [ 'ProductList','ProductRead','ProductEdit'],
	stores : [ 'ProductStore' ],
    exportUrl:'product/export/产品信息',
	constructor: function() {

		var me = this;
		
		// 初始化refs
		me.refs = me.refs || [];
		me.refs.push({
			ref: 'grid', 
			selector: me.view
		});
		me.refs.push({
			ref: 'searchForm', 
			selector: me.view + ' form'
		});
		me.refs.push({
			ref: 'detailGrid', 
			selector: 'detailgrid'
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
		
		me.refs.push({
			ref: 'readView', 
			selector: me.readview, 
			autoCreate: true, 
			xtype: me.readview
		});
		me.refs.push({
			ref: 'readForm', 
			selector: me.readview + ' form'
		});
		
		// addForm
		me.refs.push({
			ref: 'addView', 
			selector: me.addview, 
			autoCreate: true, 
			xtype: me.addview
		});
		me.refs.push({
			ref: 'addForm', 
			selector: me.addview + ' form'
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
		me.control(me.view + ' button[itemId=search]', {
			click: me.onSearch
		});
		me.control(me.view + ' button[itemId=detail]', {
			click: me.onDetail
		});
		me.control(me.view + ' button[itemId=add]', {
			click: me.onAdd
		});
		me.control(me.view + ' button[itemId=remove]', {
			click: me.onRemove
		});
		me.control(me.view + ' button[itemId=read]', {
			click: me.onRead
		});
		me.control(me.view + ' object[itemId=edit]', {
			click: me.onEdit
		});
        me.control(me.view + ' button[itemId=export]', {
            click: me.onExport
        });

		// 初始化编辑表单按钮
		me.control(me.addview + ' button[itemId=ok]', {
			click: me.onFormAdd
		});
		me.control(me.editview + ' button[itemId=ok]', {
			click: me.onFormSave
		});
	},
    onSearch: function(btn) {
        var me = this;
        var store = me.getGrid().getStore();
        var form = me.getSearchForm();
        form.updateRecord();
        store.loadPage(1, {
            params: form.getValues()
        });
    },
    onRead: function() {
        var me = this;
        var data = me.getSelectedData();
        if (data) {
            me.doRead(data = data[0]); // 获取第一条记录
        }
    },
    doRead: function(data) {
        var me = this;
        if (me.newFormToEdit) {
            me.getReadView().show();
            me.getReadForm().loadRecord(data);
        } else {
            me.getGrid().editingPlugin.startEdit(data, 0);
        }
    }
});