Ext.define('bsmes.controller.MaterialMngController', {
	extend : 'Oit.app.controller.GridController',
	view : 'materialMngList',
	materialMngList : 'materialMngList',
	views : [ 'MaterialMngList'],
	stores : [ 'MaterialMngStore'],
	init : function(){
		var me = this;
		setInterval(function(){
			me.refresh.apply(me);
		}, 60000);
		me.refs.push({
			ref : 'materialMngList',
			selector : me.materialMngList,
			autoCreate : true,
			xtype : me.materialMngList
		});
        
    },
    refresh : function() {
		var me = this;
		var searchForm = Ext.ComponentQuery.query("materialMngList hform")[0];
		var params = searchForm.form.getValues();
		Ext.ComponentQuery.query('#materialMngGrid')[0].getStore().load({params : params});
	}
});