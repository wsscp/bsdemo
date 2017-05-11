Ext.define("bsmes.view.HandleEquipAlarmDetailWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.handleEquipAlarmDetailWindow',
	layout:'fit',
	width:document.body.scrollWidth-300,
    height:document.body.scrollHeight-200,
    modal : true,
	plain : true,
	title: '警报详情',
	initComponent: function() {
		var me = this;
		
		me.items = [{
			xtype: 'form',
			bodyPadding: '5 50',
			autoScroll:true, 
			defaultType: 'displayfield',
			fieldDefaults: {  // 设置field的样式
	            msgTarget: 'side', // 错误信息提示位置
	            labelWidth: 150,  // 设置label的宽度
	            labelStyle: 'font-size:30px;margin-top:10px;',
	            fieldCls:'x-panel-body-default',
				fieldStyle: 'font-size:30px;border:none;'
	        },
		    items: [{
				fieldLabel : Oit.msg.wip.terminal.eventTitle,
				name : 'eventTitle',
				margin: '30 30 30 30',
			}, {
				fieldLabel : Oit.msg.wip.terminal.eventContent,
				name : 'eventContent',
				margin: '30 30 30 30',
			}, {
				fieldLabel : Oit.msg.wip.terminal.eventName,
				name : 'name',
				margin: '30 30 30 30',
			}, {
		    	fieldLabel: Oit.msg.wip.terminal.createTime,
		        name: 'createTime',
		        margin: '30 30 30 30',
		        renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
			}, {
				fieldLabel : Oit.msg.wip.terminal.eventStatus,
				name : 'eventStatus',
				margin: '30 30 30 30',
				renderer:function(value){
					if(value=='UNCOMPLETED'){
						return "未完成";
					}else if(value=='RESPONDED'){
						return "已响应";
					}else if(value=='COMPLETED'){
						return "已完成";
					}else{ return '' }
				}
			}]
		}];
		
		Ext.apply(me, {
			buttons: ['->', {
				itemId: 'cancel',
				text:Oit.btn.cancel,
				scope: me,
				handler: me.close
			}]
		});
		
		this.callParent(arguments);
		
	}
	
});

