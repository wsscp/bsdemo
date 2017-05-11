Ext.define('bsmes.view.ProcessEquipListEdit',{
	extend:'Ext.window.Window',
	alias:'widget.processEquipListEdit',
	width: 500,
	layout: 'fit',
	title:'产品设备维护',
	iconCls: 'feed-edit',
	modal: true,
	plain: true,
	initComponent: function() {
		var me = this;
		Ext.apply(me, {
			buttons: [{
				itemId: 'ok',
				text:Oit.btn.ok
			}, {
				itemId: 'cancel',
				text:Oit.btn.cancel,
				scope: me,
				handler: me.close
			}]
		});
		me.callParent(arguments);
	},
	items:[{
			xtype: 'form',
			bodyPadding: '12 10 10',
		    defaultType: 'textfield',
            defaults: {
                labelAlign: 'right',
                labelWidth: 150
            },
		    items: [{	
		    			fieldLabel: Oit.msg.pro.equipList.equipCode,
		    			xtype: 'displayfield',
						name : 'equipCode'
			    	 },{	
			    		fieldLabel: Oit.msg.pro.equipList.equipType,
			    		xtype: 'displayfield',
			    		name : 'type',
			    		renderer:function(value,object){
			    			if(value == 'PRODUCT_LINE'){
			      				return Oit.msg.pro.equipList.equipTypeLine;
			      			} else {
			      				return Oit.msg.pro.equipList.equipTypeDefault;
			      			}
			    		}
			    	 },{	
			    		 fieldLabel : Oit.msg.pro.equipList.equipCapacity,
			    		 name : 'equipCapacity'
			    	 },{	
			    		 fieldLabel : Oit.msg.pro.equipList.setUpTime,
			    		 name : 'setUpTime'
			    	 },{	
			    		 fieldLabel : Oit.msg.pro.equipList.shutDownTime,
			    		 name : 'shutDownTime'
			    	 }]	
		}]
});