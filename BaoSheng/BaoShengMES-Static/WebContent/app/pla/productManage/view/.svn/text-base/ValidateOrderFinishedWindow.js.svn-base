Ext.define('bsmes.view.ValidateOrderFinishedWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.validateOrderFinishedWindow',
	title : '完成订单',
	width : 680,
	height : 250,
	ids : null,
	initComponent : function(){
		var me = this;
		Ext.apply(me, {
			buttons: [{
				itemId: 'submit',
				text:'提交',
				handler : function(){
					var form = me.down('form').getForm().submit({
						url : 'productManage/updateOrdersStatus',
						params : {
							ids : Ext.encode(me.ids)
						},
						success : function(form, action){
							var result = Ext.decode(action.response.responseText);
							Ext.Msg.alert(Oit.msg.PROMPT, result.message);
                        	me.close();
							var grid = Ext.ComponentQuery.query('productManageList')[0];
							grid.getStore().reload();
						},
						failure : function(form, action) {
            				var result = Ext.decode(action.response.responseText);
        					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
            			}
					});
				}
			}]
		});
		me.items = [{
			xtype : 'form',
			bodyPadding: '10 0 0 30',
			fieldDefaults: {  
	            labelWidth: 120, 
	            labelStyle: 'font-size:16px;font-weight:nomal;'
	        },
			items : [{
				xtype: 'radiogroup',
		        fieldLabel: '订单是否合格',
		        columns: 2,
		        width : 300,
		        height : 40,
		        vertical: true,
		        items: [
		            { boxLabel: '合格',name: 'quality', inputValue: '0', checked: true},
		            { boxLabel: '不合格', name: 'quality',inputValue: '1' }
		        ],
		        listeners : {
		        	change : function(comp,newValue,oldValue,eOpts){
		        		var m = me.query('radiogroup')[1];
		        		if(newValue.quality == '1'){
		        			m.setVisible(true);
		        		}else{
		        			m.setVisible(false);
		        		}
		        		
		        	}
		        }
			},{
				xtype: 'radiogroup',
		        fieldLabel: '订单是否返工',
		        columns: 2,
		        width : 300,
		        height : 40,
		        hidden : true,
		        vertical: true,
		        items: [
		            { boxLabel: '需要',name: 'reWork', inputValue: '0', checked: true},
		            { boxLabel: '不需要', name: 'ReWork',inputValue: '1' }
		        ]
			},{
				xtype     : 'textareafield',
		        grow      : true,
		        name      : 'message',
		        fieldLabel: '订单备注',
		        anchor    : '99%',
		        height : 63,
		        overflowY : 'auto'
			}]
		}],
		this.callParent(arguments);
	}
	
});