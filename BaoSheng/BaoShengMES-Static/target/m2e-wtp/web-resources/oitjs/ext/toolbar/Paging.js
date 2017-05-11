/**
 * 有bug
 */
Ext.define("Oit.toolbar.Paging", {
	override : 'Ext.toolbar.Paging',
    doRefresh : function(){
        var me = this,
            current = me.store.currentPage;
        if (me.fireEvent('beforechange', me, current) !== false) {
            me.store.loadPage(current,typeof me.store.autoLoad === 'object' ? me.store.autoLoad : undefined);
        }
    }
});