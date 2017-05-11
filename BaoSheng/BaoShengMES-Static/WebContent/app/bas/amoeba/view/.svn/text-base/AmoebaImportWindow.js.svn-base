Ext.define('bsmes.view.AmoebaImportWindow', {
			extend : 'Ext.window.Window',
			width : 500,
			layout : 'fit',
			modal : true,
			plain : true,
			title : '阿米巴数据导入',
			alias : 'widget.amoebaImportWindow',
			height : 150,
			initComponent : function() {
				var me = this;
				Ext.apply(me, {
							buttons : [{
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
							url : 'amoeba/importAmoeba',
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