Ext.define('Oit.app.data.OitStore', {
	extend : 'Ext.data.Store',
    remoteSort : true,
    updateAll : function() {
        var me = this;
        me.proxy.batch(Ext.apply({}, {
            operations: {
                update : this.data.items
            },
            listeners: me.getBatchListeners()
        }));
    }
});
