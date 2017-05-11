Ext.define("bsmes.view.HandleEquipValidWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.handleEquipValidWindow',
	width : 500,
	height : 350,
	layout:'fit',
	modal: true,
	refreshParams: null, // 提交后刷新的grid的store中传递参数
	title: '处理警报验证',
	initComponent: function() {
		var me = this;
		
		me.items = [{
			id: 'handleEquipValidForm',
			itemsId: 'handleEquipValidForm',
			xtype: 'form',
			bodyPadding: '80 0 0 30',
			defaultType: 'textfield',
			fieldDefaults: {  // 设置field的样式
	            msgTarget: 'side',
	            labelWidth: 80, 
	            labelStyle: 'font-size:30px; margin-top:20px;'
	        },
		    url: '/bsmes/wip/terminal/handleEquipAlarm', // form提交的url
		    items: [
				{
					xtype: 'hiddenfield',
				    name: 'id'
				},{
					xtype: 'hiddenfield',
				    name: 'equipCode'
				}, {
					xtype: 'textfield',
				    fieldLabel: Oit.msg.wip.workOrder.userCode,
				    name: 'userCode',
				    allowBlank : false,
					margin: '0 30 30 30',
					fieldStyle: 'font-size:30px;',
	        		height: 60,
	        		width: 330,
					plugins:{
                        ptype:'virtualKeyBoard'
                    },
                    listeners : {
                        specialKey : function(field, e) {
                            if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
                                Ext.getCmp('handleEquipValidWindowOkBtn').fireEvent('click')
                            }
                        }
                    }
				}/*, {
				    xtype: 'textfield',
				    inputType: 'password',
				    fieldLabel: Oit.msg.wip.workOrder.password,
				    id: 'passwordtmp',
				    allowBlank : false,
					margin: '30 30 30 30',
					plugins:{
                        ptype:'virtualKeyBoard'
                    }
				},{
				    xtype: 'hiddenfield',
				    name: 'password'
				}*/
			]
		}];
		
		Ext.apply(me, {
			buttons: [{
				itemId: 'ok',
                id:'handleEquipValidWindowOkBtn',
				text:Oit.btn.ok
			}, '->',{
				itemId: 'cancel',
				text:Oit.btn.cancel,
				scope: me,
				handler: me.close
			}]
		});
		
		this.callParent(arguments);
		
	}
	
});