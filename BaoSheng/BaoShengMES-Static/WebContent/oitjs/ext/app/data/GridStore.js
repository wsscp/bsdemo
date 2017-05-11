Ext.define('Oit.app.data.GridStore', {
	extend : 'Oit.app.data.OitStore',
	autoLoad : true,
	proxy : {},
	constructor : function() {
		var me = this;
		Ext.applyIf(this.proxy, {
			type : 'rest',
			batchActions : true,
			reader : {
				root : 'rows'
			},
			writer : {
				type : 'json'
			},
			listeners : {
				exception : function(proxy, response, operation) {
					if (response.status == 200) {
						Ext.Msg.alert(Oit.msg.ERROR, Ext
								.decode(response.responseText).message);
					} else {
						Ext.Msg.alert(Oit.msg.ERROR, Oit.error.network);
					}

					// delete和create需要回滚，其他保留编辑痕迹即可。
					if (operation.action == "destroy" || operation.action == "create") {
						me.rejectChanges();
					}
				}
			}
		});

		this.callParent(arguments);
	}
});
