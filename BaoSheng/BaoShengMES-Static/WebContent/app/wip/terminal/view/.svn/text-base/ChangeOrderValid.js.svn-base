Ext.define("bsmes.view.ChangeOrderValid", {
	extend : 'Ext.window.Window',
	alias : 'widget.changeOrderValid',
	width : document.body.scrollWidth - 100,
	height : document.body.scrollHeight - 100,
	layout : 'fit',
	modal : true,
	title : '切换生产单',
	initComponent : function(){
		
		var me = this;
		var newWorkOrderNo =  Ext.ComponentQuery.query('#recentOrderGrid')[0].getSelectionModel().getSelection()[0].get('workOrderNo');
		me.items = [{
					xtype : 'form',
					bodyPadding : '5 50',
					url : 'changeOrderSeq',
					autoScroll : true,
					fieldDefaults : { // 设置field的样式
						msgTarget : 'side', // 错误信息提示位置
						labelWidth : 150, // 设置label的宽度
						labelStyle : 'font-size:30px; margin-top:20px;'
					},
					items : [{
								name : 'equipCode',
								xtype : 'hiddenfield',
								value : Ext.fly('equipInfo').getAttribute('code')
							},{
								name:'oldWorkOrderNo',
								xtype : 'hiddenfield',
								value : Ext.fly('orderInfo').getAttribute('num')
							},{
								name :	'newWorkOrderNo',
								xtype : 'hiddenfield',
								value : newWorkOrderNo
							},{
								xtype : 'textfield',
								fieldLabel : Oit.msg.wip.workOrder.userCode,
								name : 'userCode',
								margin : '30 30 30 30',
								allowBlank : false,
								height : 60,
								width : document.body.scrollWidth - 300,
								fieldCls : 'x-panel-body-default',
								fieldStyle : 'font-size:30px;',
								plugins : {
									ptype : 'virtualKeyBoard'
								}
							},{ xtype: 'textfield', inputType: 'password',
								fieldLabel: Oit.msg.wip.workOrder.password, 
								name:'password', 
								margin: '30 30 30 30', 
								allowBlank :false, 
								height : 60,
								width : document.body.scrollWidth - 300,
								fieldCls : 'x-panel-body-default',
								fieldStyle : 'font-size:30px;',
								plugins:{
									ptype:'virtualKeyBoard'
								}
							},{
								id : 'OptionsSet'
							}

					]
				}];

		Ext.apply(me, {
			buttons : [{
				itemId : 'ok',
				text : Oit.btn.ok,
				handler : function(){
					var form = this.up('window').down('form').getForm();
					form.findField('password').setValue($.md5(form.findField('password').getValue()));
					if (form.isValid()) {
						Ext.Msg.wait('处理中，请稍后...', '提示');
						form.submit({
									success : function(form, action){
										Ext.Msg.hide(); // 隐藏进度条
										me.close();
										location.reload(true);
									},
									failure : function(form, action){
										var msg = Ext.decode(action.response.responseText).message;
										if(!msg){
											Ext.Msg.hide(); // 隐藏进度条
											Ext.Msg.alert('错误','该生产单已被调度员取消!');
											me.close();
											Ext.ComponentQuery.query('#recentOrderGrid')[0].getStore().reload();
										}else{
											Ext.Msg.hide(); // 隐藏进度条
											Ext.Msg.alert('错误',msg);
											form.findField('password').setValue('');
										}
									}
								}
						);
					} else {
						if (form.findField('userCode').getValue() != '') {
							Ext.Msg.alert(Oit.msg.WARN, '请选择暂停原因！');
						}
					}
				}
			}, '->', {
				itemId : 'cancel',
				text : Oit.btn.cancel,
				scope : me,
				handler : me.close
			}]
		}
		);

		// 初始化checkbox
		Ext.Ajax.request({
			url : 'pauseReasonDic',// 请求路径，需要手动输入指定
			success : function(response){
				var itemsDic = [];
				var result = Ext.decode(response.responseText);
				for (var j = 0; j < result.length; j++) {
					itemsDic[j] = new Ext.form.Checkbox({
								boxLabel : result[j].name,
								inputValue : result[j].name,
								fieldStyle : 'margin-top:7px;',
								boxLabelCls : 'x-boxlabel-size-30 x-boxlabel-left-10',
								width : (document.body.scrollWidth - 330) / 2,
								name : 'pauseReasonDic' // 这个是后台接收的表单域，这里的checkboxgroup都使用同一个name
							}
					);
				}

				var itemsGroup = new Ext.form.CheckboxGroup({
							fieldLabel : Oit.msg.wip.workOrder.reasonDesc,
							name : 'pauseReasonDic',
							xtype : 'checkboxgroup',
							minHeight : 80,
							margin : '0 0 0 30',
							allowBlank : false,
							columns : 2, // 一行显示几个
							layout : { // 设置自动间距样式为false
								autoFlex : false
							},
							items : itemsDic
						});
				Ext.getCmp('OptionsSet').add(itemsGroup);
				Ext.getCmp('OptionsSet').doLayout();
			}
		}
		)

		this.callParent(arguments);
	}

}
);