Ext.define('bsmes.view.ImportEqipImageWindow', {
			extend : 'Ext.window.Window',
			width : 500,
			layout : 'fit',
			modal : true,
			plain : true,
			title : '上传设备图片',
			alias : 'widget.importEqipImageWindow',
			height : 150,
			initComponent : function() {
				var me = this;
				Ext.apply(me, {
							buttons : [/*
										 * { itemId: 'downTemplate', text:'下载模板' },
										 */{
										itemId : 'import',
										text : '上传',
										scope : me,
										handler : function(){
											var form = me.down('form').getForm();
											var view = Ext.ComponentQuery.query('equipInformationList')[0];
								  			var selection = view.getSelectionModel().getSelection();
								  			var eqipId = selection[0].get('id');
							                if(form.isValid()){
							                    form.submit({
							                    	url : 'equipInformation/importEqipImage',
							                    	params : {
							                    		eqipId : eqipId
							                    	},
							                        waitMsg: '上传图片中...',
							                        success: function(form, action) {
							                        	var result = Ext.decode(action.response.responseText);
							                        	Ext.Msg.alert(Oit.msg.PROMPT, result.message);
							                        	me.close();
							                        },
							                        failure : function(form, action) {
							            				var result = Ext.decode(action.response.responseText);
							        					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
							            			}
							                    });
							                }
										}
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
							enctype : "multipart/form-data",
							items : [{
					            xtype: 'textfield',
					            fieldLabel: '图片名称',
					            name : 'imageName',
					            labelWidth : 80,
								emptyText: 'Picture name',
								allowBlank : false,
								anchor : '100%'
					        },{
										xtype : 'filefield',
										name : 'importFile',
										fieldLabel : '请选择文件',
										labelWidth : 80,
										emptyText: 'Select an image',
										msgTarget : 'side',
										allowBlank : false,
										anchor : '100%',
										buttonText : '选择图片'
									}]
						}],

				this.callParent(arguments);
			}
		});