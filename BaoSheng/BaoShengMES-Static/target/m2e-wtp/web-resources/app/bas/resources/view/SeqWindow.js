Ext.define('bsmes.view.SeqWindow', {
	extend : 'Ext.window.Window',
	title : Oit.msg.bas.resources.btn.seqBtn,
	// layout : "border",
	alias : 'widget.seqWindow',
	width : 600,
	height : 400,
	modal : true,
	closeAction : 'destory',
	plain : true,
	items : [ {
		xtype : "grid",
		//store : Ext.create('bsmes.store.ResourcesStore'),
		stripeRows : true,
		selType : 'checkboxmodel',
		forceFit : true,
		width:'100%',
		height: 325,
		columns : [ {
			text : Oit.msg.bas.resources.seq,
			sortable:false,
			menuDisabled:true,
			dataIndex : 'seq'
		}, {
			text : Oit.msg.bas.resources.name,
			sortable:false,
			menuDisabled:true,
			dataIndex : 'name'
		}, {
			text : Oit.msg.bas.resources.type,
			dataIndex : 'typeName',
			menuDisabled:true,
			sortable:false
		}],
		tbar : [ {
			itemId : 'move',
			text : Oit.msg.bas.resources.btn.move
		}, {
			itemId : 'down',
			text : Oit.msg.bas.resources.btn.down
		}, {
			itemId : 'top',
			text : Oit.msg.bas.resources.btn.top
		}, {
			itemId : 'end',
			text : Oit.msg.bas.resources.btn.end
		} ]
	} ],
	buttons : [{
		text : Oit.btn.ok,
		itemId : 'ok'
	}, {
		text : Oit.btn.close,
		itemId : 'close',
		handler: function() {
			this.up('.window').close();
	    }
	} ]

});