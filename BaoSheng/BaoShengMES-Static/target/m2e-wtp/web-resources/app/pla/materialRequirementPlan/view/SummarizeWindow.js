/**
 * 实例工序查看
 */
Ext.define('bsmes.view.SummarizeWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.summarizeWindow',
			title : '计划汇总',
			width : document.body.scrollWidth-400,
			height : document.body.scrollHeight-200,
			modal : true,
			plain : true,
			layout : 'fit',
			initComponent : function() {
				var me = this;

				me.items = [{
							xtype : 'summarizeGrid'
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
