Ext.define('Oit.app.data.TreeStore', {
	extend : 'Ext.data.TreeStore',
	constructor : function() {
		var me = this;
		Ext.applyIf(this.proxy, {
			reader : {
				root : 'rows'
			},
			writer : {
				type : 'json'
			},
			type : 'rest',
			listeners: {
			    exception: function(proxy, response, operation, eOpts){
			        var errorMsg = Ext.JSON.decode(response.responseText);  
			        Ext.Msg.show({title: '操作失败', msg: errorMsg.message, buttons: Ext.Msg.OK, icon: Ext.Msg.WARNING});
			    }
			}
		});
		
		this.callParent(arguments);
	}
});
