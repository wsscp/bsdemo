Ext.define('bsmes.controller.ProcessQAController',{
		extend:'Oit.app.controller.GridController',
		view:'processQAList',
		editview:'processQAEdit',
		views:['ProcessQAList','ProcessQAEdit'],
		stores:['ProcessQAStore'],
        exportUrl:'processQA/export/QA检测数据',
		init : function(){
			var me = this;
			me.control(me.view + ' button[itemId=createQAAlarm]',{
				click:me.doCreateQAAlarm
			});
			
			// 初始化编辑表单按钮
			me.control(me.editview + ' button[itemId=ok]', {
				click: me.onFormSave
			});
			me.control(me.view + ' button[itemId=search]', {
		            click: me.onSearch
		    });

            me.control(me.view + ' button[itemId=export]', {
                click: me.onExport
            });
		},
		onFormSave: function(btn) {
			var me = this;
			var form = me.getEditForm(); 
			form.updateRecord();
			if (form.isValid()) {
				var store = me.getGrid().getStore();
				store.getProxy().url='processQA';
				// 同步到服务器
				store.sync();
				// 关闭窗口
				me.getEditView().close();
			}
		},
		doCreateQAAlarm:function(){
			var me = this;
			var record = me.getSelectedData();
			if(record){
				Ext.Ajax.request({
					url:'processQA/createQAAlarm',
					method:'POST',
					params:{
						'processQcValue':Ext.encode(record[0].data)
					},
					success:function(response){
						Ext.MessageBox.alert(Oit.msg.WARN, Oit.msg.pro.processQA.message.generateQAAlarmSuccesMessage);
					}
				});
			}
		}
});