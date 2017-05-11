/**
 * 实例工序查看
 */
Ext.define('bsmes.view.ShowWorkOrderWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.showWorkOrderWindow',
			title : '查看生产单',
			width : document.body.scrollWidth,
			height : document.body.scrollHeight,
			modal : true,
			plain : true,
			layout : 'fit',
			initComponent : function() {
				var me = this;

				me.items = [{
							xtype : 'showWorkOrderGrid'
						}];

				Ext.apply(me, {
						buttons : ['->', {
							text : Oit.btn.cancel,
							scope : me,
							handler : me.close
						}]
					});

				this.callParent(arguments);
			}
		});