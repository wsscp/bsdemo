Ext.define("bsmes.controller.ShunDownAnalysisController",{
	extend : 'Oit.app.controller.GridController',
	view : 'shunDownAnalysisList',
	editview : 'shunDownAnalysisEdit',
	views : [ 'ShunDownAnalysisList','ShunDownAnalysisEdit'],
	stores : [ 'ShunDownAnalysisStore'],
	exportUrl : 'shunDownAnalysis/export/停机原因',
	init: function() {
		var me = this;
		me.control(me.view + ' button[itemId=exportToXls]', {
	        click: me.onExport
	    });
		me.control(me.view + ' button[itemId=search]', {
	        click: me.onSearch
	    });
		me.control(me.editview + ' button[itemId=ok]', {
            click: me.onFormSave
        });
	},
	onSearch:function(){
	    var me = this;
	    var store = me.getGrid().getStore();
	    var form = me.getSearchForm();
	    var findParams = form.getValues();
	    store.loadPage(1, {
	        params: findParams
	    });
	}
});
