
Ext.define('bsmes.view.PrintPreviewWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.printPreviewWindow',
			width : document.body.scrollWidth - 800,
			height : document.body.scrollHeight - 350,
			modal : true,
			plain : true,
			printParams: {},
			id: 'printPreviewWindow',
			initComponent: function(){
				var me=this;
				me.items=[{
					xtype : 'hform',
					padding: '25 0 0 30',
					layout: 'vbox',
					items: [{
						fieldLabel : '产品型号',
	                    itemId : 'productType',
	                    xtype : 'displayfield',
	                    margin : '0 0 20 0',
	                    allowBlank : false,
	                    value : me.printParams.productType
					},{
						fieldLabel : '产品规格',
	                    itemId : 'productSpec',
	                    margin : '0 0 20 0',
	                    xtype : 'displayfield',
	                    allowBlank : false,
	                    value : me.printParams.productSpec
					},{
						fieldLabel : '生产长度',
	                    itemId : 'length',
	                    xtype : 'displayfield',
	                    allowBlank : false,
	                    value : me.printParams.length
					}]
				
				}];
				
				Ext.apply(me, {
					buttons: [{
						itemId: 'issuePrintParam',
						text: Oit.msg.wip.terminal.btn.issuedParam,
						handler: function(){
							Ext.MessageBox.confirm('确认','确认下发参数?',function(btn){
								if(btn=='yes'){
									Ext.Ajax.request({
										url : 'issuePrintParam',
										async : false,
										params : me.printParams,
										success: function(response){
											alert('下发成功！');
											me.close();
										}
									});
								}
							});
						}
					},'->',{
						itemId: 'cancel',
						text:Oit.btn.cancel,
						scope: me,
						handler: me.close
					}]
				});
				this.callParent(arguments);
			}
		});
