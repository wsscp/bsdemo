Ext.define('bsmes.view.ImportProductDetailWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.importProductDetailWindow',
			title : '导入详情',
			width : document.body.scrollWidth-50,
			height : document.body.scrollHeight-30,
			modal : true,
			plain : true,
			initComponent : function() {
				var me = this;

				me.items = [{
							xtype : 'importProductDetail'
						}];

				Ext.apply(me, {
							buttons : ['->', {
										itemId : 'cancel',
										text : '关 闭',
										scope : me,
										handler : me.close
									}]
						});

				this.callParent(arguments);
			}
		});