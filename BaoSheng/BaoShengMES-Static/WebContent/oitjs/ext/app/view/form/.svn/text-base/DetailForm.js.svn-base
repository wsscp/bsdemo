/**
 * config: formItems
 */
Ext.define('Oit.app.view.form.DetailForm', {
			extend : 'Ext.window.Window',
			/**
			 * @cfg {Object/Object[]} formItems (required)
			 */
			minWidth : 500,
			width : document.body.scrollWidth * 0.5,
			maxHeight : document.body.scrollHeight * 0.8,
			layout : 'fit',
			modal : true,
			plain : true,
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

				me.items = [{
							xtype : 'form',
							bodyPadding : 10,
							// 将会通过 AJAX 请求提交到此URL
							// url : 'save-form.php',
							// 表单域 Fields 将被竖直排列, 占满整个宽度
							layout : 'anchor',
							autoScroll : true,
							defaults : {
								//labelAlign:'right',
								msgTarget : 'under',
								anchor : '100%'
							},
							defaultType : 'displayfield',
							items : me.formItems
						}],

				this.callParent(arguments);
			}
		});
