Ext.define('bsmes.view.ProcessQAEntryWindow',{
		extend:'Ext.window.Window',
		title:Oit.msg.wip.dataCollection.winTitle,
		alias:'widget.processQAEntryWindow',
		modal : true,
		plain : true,
		width:document.body.scrollWidth-100,
	    height:document.body.scrollHeight-100,
	    layout : 'fit',
		requires:['bsmes.view.ProcessQAEntryList'],
		closeAction:'destory',
		initComponent: function() {
			var me = this;
			Ext.apply(me, {
				buttons: ['->', {
					itemId: 'ok',
					text:Oit.btn.ok
				}, {
					itemId: 'cancel',
					text:Oit.btn.cancel,
					scope: me,
					handler: me.close
				}]
			});
			
			me.items = [{
				xtype: 'processQAEntryList'
			}],
			
			this.callParent(arguments);
		} 
});