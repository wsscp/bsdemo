/**
 * Created by chanedi on 14-3-4.
 */
Ext.define('bsmes.view.ReportView', {
			extend : 'Ext.window.Window',
			alias : 'widget.reportView',
			title : '报工',
			modal : true,
			plain : true,
			width : document.body.scrollWidth - 100,
			height : document.body.scrollHeight - 100,
			layout : 'fit',
			requires : ['bsmes.view.ReportGrid', 'bsmes.view.ReportHTJYGrid'],
			record : null, // 报工信息
			reprotUser : {}, // 报工人员信息
			initComponent : function() {
				var me = this;
				me.getReprotUser(); // 获取报工人员信息

				var processCode = Ext.fly("processInfo").getAttribute('code');
				if (processCode == 'Braiding' || processCode == 'wrapping' || processCode == 'Cabling' || processCode == 'Twisting'
						|| processCode == 'Respool' || processCode == 'Steam-Line' || processCode == 'Extrusion-Single'
							|| processCode == 'Jacket-Extrusion' || processCode == 'shield' || processCode == 'wrapping_ht') {
					me.items = [{
								xtype : 'reportGrid',
								record : me.record,
								reprotUser : me.reprotUser,
								processCode : processCode
							}]
				} else {
					me.items = [{
								xtype : 'reportHTJYGrid',
								record : me.record,
								reprotUser : me.reprotUser
							}]
				}
				me.callParent(arguments);
			},

			/**
			 * 获取报工人员信息
			 */
			getReprotUser : function() {
				var me = this
				var dbItems = [], /*fdbItems = [], fzgItems = [],*/ reprotUser = [];
				Ext.Ajax.request({
							url : 'getUsers',
							async : false,
							success : function(response) {
								var users = Ext.decode(response.responseText); // 请求响应转换成json格式
								Ext.Array.each(users, function(user, i) {
											reprotUser.push(user.id);
											var item = {
												boxLabel : user.userName,
												inputValue : user.id,
												width : 90,
												name : 'reprotUser',
												checked : false
											};
											if (user.certificate == 'DB') {
												dbItems.push(item);
											}
											/*if (user.certificate == 'FDB') {
												fdbItems.push(item);
											}
											if (user.certificate == 'FZG') {
												fzgItems.push(item)
											}*/
										})
							}
						});
				me.reprotUser.dbItems = dbItems;
				/*me.reprotUser.fdbItems = fdbItems;
				me.reprotUser.fzgItems = fzgItems;*/
				if (me.record)
				//	me.record.set('reprotUser', reprotUser);
				me.record.set('operator', Ext.util.Cookies.get('operator'));
			}

		});