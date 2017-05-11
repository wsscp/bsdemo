Ext.define('bsmes.controller.OrgController', {
			extend : 'Oit.app.controller.GridController',
			views : ['OrgList', 'OrgEdit', 'OrgAdd','OrgDetail'],
			view : 'orglist',
			addview : 'orgadd',
			editview : 'orgedit',
			detailview  : 'orgDetail',//在继承的js中定义了，所以必须添加
			stores : ['OrgStore'],

			onFormAdd : function(btn){
				var me = this;
				var form = me.getAddForm();

				// form.updateRecord();
				if (!form.isValid()) {
					Ext.Msg.alert("提示", "填写信息不完整！");
				} else {
					var isrepeat = false;
					Ext.Ajax.request({
								url : '/bsmes/bas/org/checkOrgCodeUnique/' + Ext.getCmp('orgCodeId').getValue() + '/',
								method : 'GET',
								async : false,
								success : function(response){
									var data = Ext.decode(response.responseText);
									if (data && data.checkReult) {
										isrepeat = true;
									}
								}
							});
					if (isrepeat) {
						Ext.Msg.alert("提示", "机构编号重复！");
					} else {
						form.updateRecord();
						var store = me.getGrid().getStore();
						// 同步到服务器
						store.insert(0, form.getRecord());
						store.sync();
						me.getAddView().close();
					}
				}
			}
		});
