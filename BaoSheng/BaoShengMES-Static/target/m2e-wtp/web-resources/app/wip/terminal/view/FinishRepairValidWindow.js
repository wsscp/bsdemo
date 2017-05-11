Ext.define("bsmes.view.FinishRepairValidWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.finishRepairValidWindow',
	width : 600,
	height : 400,
	layout:'fit',
	modal: true,
	title: '维修完成确认',
	listeners : {
		'afterrender' : function(e, eOpts) {
			var e = Ext.ComponentQuery.query('#thought')[0];
			e.setVisible(false);
		}
	},
	initComponent: function() {
		var me = this;
		
		me.items = [{
			xtype: 'form',
			bodyPadding: '30 0 0 30',
			defaultType: 'textfield',
			fieldDefaults: {  // 设置field的样式
	            msgTarget: 'side',
	            labelWidth: 200, 
	            labelStyle: 'font-size:30px;'
	        },
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
					margin: '0 0 50 0',
					labelStyle : 'font-size:30px;margin-top : 20px;',
	        		height: 60,
	        		width: 500,
					plugins:{
                        ptype:'virtualKeyBoard'
                    },
                    listeners : {
                        specialKey : function(field, e) {
                            if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
                                Ext.getCmp('submit').fireEvent('click')
                            }
                        }
                    }
				},{
					fieldLabel : '是否维修完成',
					xtype : 'radiogroup',
					name : 'isFinished',
					columns: 2,
					width : 400,
					height: 60,
					margin: '0 0 30 0',
			        vertical: true,
					items : [{
						boxLabel  : '是',
						boxLabelCls : 'x-finish-port',
	                    name      : 'isFinished',
	                    inputValue: '1'
					},{
						boxLabel  : '否',
						boxLabelCls : 'x-finish-port',
	                    name      : 'isFinished',
	                    inputValue: '0'
					}],
					listeners : {
						change : function(cop,newValue, oldValue,eOpts) {
							var thought = Ext.ComponentQuery.query('#thought')[0];
							console.log(newValue.isFinished)
							if (newValue.isFinished == '1') {
								thought.setVisible(true);
							} else {
								thought.setVisible(false);
							}
						}
					}
				},{
					fieldLabel : '维修过程评价',
					xtype : 'radiogroup',
					id : 'thought',
					name : 'thought',
					columns: 3,
					width : 500,
					height: 60,
					margin: '0 0 30 0',
			        vertical: true,
					items : [{
						boxLabel  : '好',
						boxLabelCls : 'x-finish-port',
	                    name      : 'thought',
	                    inputValue: '好',
	                    checked : true
					},{
						boxLabel  : '中',
						boxLabelCls : 'x-finish-port',
	                    name      : 'thought',
	                    inputValue: '中'
					},{
						boxLabel  : '差',
						boxLabelCls : 'x-finish-port',
	                    name      : 'thought',
	                    inputValue: '差'
					}]
				}
			]
		}];
		
		Ext.apply(me, {
			buttons: [{
				itemId: 'submit',
				id : 'submit',
				text:'提交',
				handler : function(){
					var win = Ext.ComponentQuery.query('finishRepairValidWindow')[0];
					var form = win.down('form');
					if(form.isValid()){
						form.submit({
							url : '/bsmes/wip/terminal/eventStatusChanged',
							success : function(form, action) {
								var result = Ext.decode(action.response.responseText);
								Ext.Msg.alert(Oit.msg.PROMPT, result.message);
								var grid = Ext.ComponentQuery.query('handleEquipAlarmGrid')[0];
								grid.getStore().reload();
								win.close();
							},
							failure : function(form, action) {
								var result = Ext.decode(action.response.responseText);
								Ext.Msg.alert(Oit.msg.WARN, result.message);
							}
					});
					}
				}
			}]
		});
		
		this.callParent(arguments);
		
	}
	
});