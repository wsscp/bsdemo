Ext.define('bsmes.view.ImportOrderWindow', {
			extend : 'Ext.window.Window',
			width : 500,
			layout : 'fit',
			modal : true,
			plain : true,
			title : Oit.msg.pla.customerOrderItem.button.importOrder,
			alias : 'widget.importOrderWindow',
			height : 150,
			initComponent : function() {
				var me = this;
				Ext.apply(me, {
							buttons : [/*
										 * { itemId: 'downTemplate', text:'下载模板' },
										 */{
										itemId : 'ok',
										text : Oit.btn.ok
									}, {
										itemId : 'cancel',
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});

				me.items = [{
							xtype : 'form',
							bodyPadding : '12 10 10',
							defaultType : 'textfield',
							defaults : {
								labelAlign : 'right'
							},
							url : 'customerOrderItem/importOrdersToItemDec',
							enctype : "multipart/form-data",
							items : [{
										xtype : 'filefield',
										name : 'importFile',
										fieldLabel : '请选择文件',
										labelWidth : 80,
										msgTarget : 'side',
										allowBlank : false,
										anchor : '100%',
										buttonText : '选择文件'
									}]
						}],

				this.callParent(arguments);
			}
		});