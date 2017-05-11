Ext.define('bsmes.view.CheckGridWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.checkGridWindow',
	title : '点检',
	width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-100,
    layout : 'fit',
	requires : [ 'bsmes.view.CheckGrid','bsmes.store.CheckGridStore'],
	items : [ {
		xtype : 'checkGrid'
	} ],
	buttons: ['->', {
		text: Oit.msg.wip.terminal.check.complete,
		itemId: 'complete'
//	 	          hidden: Ext.fly('completed').getHTML() == 'true'
	}, {
		xtype:'button',
		text:'关闭',
		handler : function() {
			this.up('.window').close();
		}
	}]
});
