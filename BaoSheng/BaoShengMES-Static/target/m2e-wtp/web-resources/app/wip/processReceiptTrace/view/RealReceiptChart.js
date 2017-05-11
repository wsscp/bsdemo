Ext.define("bsmes.view.RealReceiptChart", {
	extend : 'Ext.window.Window',
	alias : 'widget.realReceiptChart',
	width : document.body.scrollWidth-5,
	height : document.body.scrollHeight-5,
	 maximizable:true,
     title:"实时数据曲线图",
	autoScroll:true,
	overflowY:'auto',
	modal : true,
	closeAction : "hide",
	plain : true,
	initComponent: function () {
		var me = this;
		me.items = [{
			xtype:'panel',
			id:'realReceiptChart_id',
			width : document.body.scrollWidth-120,
			height : document.body.scrollHeight-124
		} ]
		me.callParent(arguments);
	},

	buttons : [ {
		text : Oit.btn.close,
		handler : function() {
			//chart.hide();
			this.up('.window').hide();
		}
	} ]

});