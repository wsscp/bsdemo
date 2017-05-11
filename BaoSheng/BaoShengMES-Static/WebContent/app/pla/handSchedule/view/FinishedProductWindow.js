Ext.define('bsmes.view.FinishedProductWindow', {
	extend : 'Ext.window.Window',
	title : '成品现货',
	alias : 'widget.finishedProductWindow',
	id : 'finishedProductWindow',
	layout : 'fit',
	modal : true,
	plain : true,
	width : document.body.scrollWidth/2 + 200,
	height : document.body.scrollHeight -200,
    useLength : 0,
	id : null,
	contractLength : 0,
	items: {  
        xtype: 'finishedProductList'
    },
    initComponent : function() {
		var me = this;
		Ext.apply(me, {
			buttons : [{
						itemId : 'cancel',
						text : Oit.btn.cancel,
						scope : me,
						handler : me.close
					}]
		});
		this.callParent(arguments);
    }
});