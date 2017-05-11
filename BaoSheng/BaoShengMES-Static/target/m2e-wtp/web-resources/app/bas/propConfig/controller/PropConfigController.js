Ext.define('bsmes.controller.PropConfigController', {
	extend : 'Oit.app.controller.GridController',
	view : 'propConfigList',
	addview  : 'propConfigAdd',
	editview  : 'propConfigEdit',
	views : [ 'PropConfigList','PropConfigAdd','PropConfigEdit'],
	stores : [ 'PropConfigStore' ],
	doEdit: function(data) {
		var me = this;
		if (me.newFormToEdit) {
			me.getEditView().show();
			if(data.data.status=='false'){
				Ext.getCmp("status-2001").setValue(true);
			}else{
				Ext.getCmp("status-2000").setValue(true);
			}
			me.getEditForm().loadRecord(data);
		} else {
			me.getGrid().editingPlugin.startEdit(data, 0);
		}
	}
});