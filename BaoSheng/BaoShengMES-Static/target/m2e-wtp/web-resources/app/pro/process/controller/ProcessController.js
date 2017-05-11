Ext.define('bsmes.controller.ProcessController', {
	extend : 'Oit.app.controller.GridController',
	view : 'processList',
	editview : 'processEdit',
	processQcView : 'processQcWindow',
	qcEditView : 'processQcEdit',
	processEquipListWindow : 'processEquipListWindow',
	processEquipListEdit : 'processEquipListEdit',
	processReceiptWindow : 'processReceiptWindow',
	processReceiptEdit : 'processReceiptEdit',
	processInOutWindow : 'processInOutWindow',
	processInOutEdit : 'processInOutEdit',
	views : ['ProcessList', 'ProcessEdit', 'ProcessQcWindow', 'ProcessQcEdit',
			'ProcessEquipListWindow', 'ProcessEquipListEdit',
			'ProcessReceiptWindow', 'ProcessReceiptEdit', 'ProcessInOutWindow',
			'ProcessInOutEdit'],
	stores : ['ProcessStore', 'ProcessQcStore', 'ProcessEquipListStore',
			'ProcessReceiptStore', 'ProcessInOutStore'],
	exportUrl : 'processBz/export/产品工艺流程',
	constructor : function(){
		var me = this;

		// 初始化refs
		me.refs = me.refs || [];

		me.refs.push({
					ref : 'processList',
					selector : 'processList',
					autoCreate : true,
					xtype : 'processList'
				});

		me.refs.push({
					ref : 'editView',
					selector : me.editview,
					autoCreate : true,
					xtype : me.editview
				});
		me.refs.push({
					ref : 'editForm',
					selector : me.editview + " form"
				});

		me.refs.push({
					ref : 'processQcView',
					selector : me.processQcView,
					autoCreate : true,
					xtype : me.processQcView
				});
		me.refs.push({
					ref : 'qcEditView',
					selector : me.qcEditView,
					autoCreate : true,
					xtype : me.qcEditView
				});

		me.refs.push({
					ref : 'processQcGrid',
					selector : me.processQcView + " processQcList"
				});

		me.refs.push({
					ref : 'qcEditForm',
					selector : me.qcEditView + " form"
				});

		// EQUIPLIST

		me.refs.push({
					ref : 'equipListWindow',
					selector : me.processEquipListWindow,
					autoCreate : true,
					xtype : me.processEquipListWindow
				});

		me.refs.push({
					ref : 'equipListGrid',
					selector : me.processEquipListWindow
							+ " processEquipListGrid"
				});
		me.refs.push({
					ref : 'equipListEditWin',
					selector : me.processEquipListEdit,
					autoCreate : true,
					xtype : me.processEquipListEdit
				});
		me.refs.push({
					ref : 'equipListEditForm',
					selector : me.processEquipListEdit + " form"
				});

		// / RECEIPT
		me.refs.push({
					ref : 'processReceiptWindow',
					selector : me.processReceiptWindow,
					autoCreate : true,
					xtype : me.processReceiptWindow
				});
		me.refs.push({
					ref : 'receiptListGrid',
					selector : me.processReceiptWindow + " processReceiptGrid"
				});

		me.refs.push({
					ref : 'receiptEditWin',
					selector : me.processReceiptEdit,
					autoCreate : true,
					xtype : me.processReceiptEdit
				});

		me.refs.push({
					ref : 'receiptEditForm',
					selector : me.processReceiptEdit + " form"
				});

		// /INOUT

		me.refs.push({
					ref : 'inOutWindow',
					selector : me.processInOutWindow,
					autoCreate : true,
					xtype : me.processInOutWindow
				});
		me.refs.push({
					ref : 'inOutGrid',
					selector : me.processInOutWindow + " processInOutGrid"
				});

		me.refs.push({
					ref : 'inOutEditWin',
					selector : me.processInOutEdit,
					autoCreate : true,
					xtype : me.processInOutEdit
				});

		me.refs.push({
					ref : 'inOutEditForm',
					selector : me.processInOutEdit + " form"
				});

		me.callParent(arguments);
	},
	init : function(){
		var me = this;

		// 初始化工具栏
		me.control(me.view + ' button[itemId=search]', {
					click : me.onSearch
				});

		me.control(me.view + ' button[itemId=editProcess]', {
					click : me.openProcessEditWindow
				});

		me.control(me.view + ' button[itemId=editProcessQC]', {
					click : me.openProcessQcWindow
				});

		me.control(me.view + ' button[itemId=export]', {
					click : me.onExport
				});
		me.control(me.processQcView + ' button[itemId=qcEdit]', {
					click : me.qcEdit
				});

		me.control(me.qcEditView + ' button[itemId=ok]', {
					click : me.qcSave
				});

		// EQUIPLIST
		me.control(me.view + ' button[itemId=processEquipListBtn]', {
					click : me.openEquipListWindow
				});
		me.control(me.processEquipListWindow + ' button[itemId=equipListEdit]',
				{
					click : me.openEditEquipListWindow
				}
		);
		me.control(me.processEquipListEdit + ' button[itemId=ok]', {
					click : me.saveEquipListInfo
				});

		// /RECEIPT
		me.control(me.processEquipListWindow
						+ ' button[itemId=processReceiptOpen]', {
					click : me.openProcessReceiptWindow
				});

		me.control(me.processReceiptWindow
						+ ' button[itemId=processReceiptEditBtn]', {
					click : me.openProcessReceiptEditWindow
				});

		me.control(me.processReceiptEdit + ' button[itemId=ok]', {
					click : me.saveProcessReceipt
				});

		// INOUT

		me.control(me.view + ' button[itemId=processInOutBtn]', {
					click : me.openInOutWindow
				});
		me.control(me.processInOutWindow
						+ ' button[itemId=processInOutEditBtn]', {
					click : me.openEditInOutWindow
				});
		me.control(me.processInOutEdit + ' button[itemId=ok]', {
					click : me.saveInOutInfo
				});

	},
	/**
	 * @overwrite
	 */
	// onSearch: function(btn) {
	// var me = this;
	// console.log(me);
	// var store = me.getGrid().getStore();
	// var form = me.getSearchForm();
	// store.loadPage(1, {
	// params: form.getValues()
	// });
	// },
	onExport : function(){
		var me = this;
		if (!me.exportUrl) {
			Ext.Error.raise("A view configuration must be specified!");
		}

		var params = [];
		Ext.each(me.getGrid().columns, function(column){
					if (!column.dataIndex) {
						return;
					}
					params.push({
								text : column['text'],
								dataIndex : column['dataIndex']
							})
				});
		falseAjaxTarget.document
				.write('<form method="post"><input id="params" name="params">'
						+ '<input id="queryParams" name="queryParams"></form>');
		falseAjaxTarget.document.getElementById("params").value = JSON
				.stringify(params);
		falseAjaxTarget.document.getElementById("queryParams").value = JSON
				.stringify({
							productCraftsId : Ext.fly('craftsIdSearch')
									.getAttribute('value')
						});
		var form = falseAjaxTarget.document.forms[0];
		form.action = me.exportUrl;
		form.submit();

	},

	openProcessEditWindow : function(){
		var me = this;
		var selection = me.getSelectedData();
		if (selection.length == 0) {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		} else {
			var record = selection[0];
			var window = me.getEditView();
			var editForm = me.getEditForm();
			editForm.loadRecord(record);
			window.show();
		}
	},
	openProcessQcWindow : function(){
		var me = this;
		var selection = me.getSelectedData();
		if (selection && selection.length > 0) {
			var record = selection[0];
			var window = me.getProcessQcView();
			window.down('form').form.findField('processId').setValue(record
					.getData().id);
			me.getProcessQcGrid().getStore().load({
						params : {
							checkType : 'needInCheck',
							processId : record.getData().id
						}
					});
			window.show();
		}
	},
	qcEdit : function(){
		var me = this;
		var data = me.getProcessQcGrid().getSelectionModel().getSelection();
		if (data.length == 0) {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		}
		me.getQcEditView().show();
		me.getQcEditForm().loadRecord(data[0]);
	},
	qcSave : function(){
		var me = this;
		var form = me.getQcEditForm();
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getProcessQcGrid().getStore();
			store.sync();
			me.getQcEditView().close();
		}
	},
	openEquipListWindow : function(){
		var me = this;
		var selection = me.getSelectedData();
		if (selection && selection.length > 0) {
			var record = selection[0];
			var window = me.getEquipListWindow();
			window.down('form').form.findField('processId').setValue(record
					.getData().id);
			me.getEquipListGrid().getStore().load({
						params : {
							processId : record.getData().id
						}
					});
			window.show();
		}
	},
	openEditEquipListWindow : function(){
		var me = this;
		var selection = me.getEquipListGrid().getSelectionModel()
				.getSelection();
		if (selection && selection.length > 0) {
			var record = selection[0];
			var window = me.getEquipListEditWin();
			var form = me.getEquipListEditForm();
			form.loadRecord(record);
			window.show();
		} else {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		}
	},
	saveEquipListInfo : function(){
		var me = this;
		var window = me.getEquipListEditWin();
		var form = me.getEquipListEditForm();
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getEquipListGrid().getStore();
			store.sync({
						success : function(batch, options){
							window.close();
						},
						failure : function(batch, options){
							console.log(batch);
							console.log(options);
						}
					});
		}
	},
	openProcessReceiptWindow : function(){
		var me = this;

		var selection = me.getEquipListGrid().getSelectionModel()
				.getSelection();
		if (selection.length == 0) {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		} else {
			var record = selection[0];
			var window = me.getProcessReceiptWindow();
			me.getReceiptListGrid().getStore().load({
						params : {
							eqipListId : record.getData().id
						}
					});
			window.show();
		}
	},
	openProcessReceiptEditWindow : function(){
		var me = this;
		var selection = me.getReceiptListGrid().getSelectionModel()
				.getSelection();
		if (selection.length == 0) {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		} else {
			var record = selection[0];
			var window = me.getReceiptEditWin();

			var form = me.getReceiptEditForm();
			form.loadRecord(record);

			window.show();
		}
	},
	saveProcessReceipt : function(){
		var me = this;
		var window = me.getReceiptEditWin();
		var form = me.getReceiptEditForm();
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getReceiptListGrid().getStore();
			store.sync({
						success : function(batch, options){
							window.close();
						},
						failure : function(batch, options){
							console.log(batch);
							console.log(options);
						}
					});
		}
	},
	openInOutWindow : function(){
		var me = this;
		var selection = me.getSelectedData();
		if (selection && selection.length > 0) {
			var record = selection[0];
			var window = me.getInOutWindow();
			window.down('form').form.findField('processId').setValue(record
					.getData().id);
			me.getInOutGrid().getStore().load({
						params : {
							processId : record.getData().id
						}
					});
			window.show();
		} else {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		}
	},
	openEditInOutWindow : function(){
		var me = this;
		var selection = me.getInOutGrid().getSelectionModel().getSelection();
		if (selection && selection.length > 0) {
			var record = selection[0];
			var window = me.getInOutEditWin();
			var form = me.getInOutEditForm();
			form.loadRecord(record);
			window.show();
		} else {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		}
	},
	saveInOutInfo : function(){
		var me = this;
		var window = me.getInOutEditWin();
		var form = me.getInOutEditForm();
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getInOutGrid().getStore();
			store.sync({
						success : function(batch, options){
							window.close();
						},
						failure : function(batch, options){
							console.log(batch);
							console.log(options);
						}
					});
		}
	}
}
);