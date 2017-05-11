Ext.define('bsmes.controller.ManualManageController', {
			extend : 'Oit.app.controller.GridController',
			view : 'manualManageList',
			manualManageEdit : 'manualManageEdit',
			views : ['ManualManageList','ManualManageEdit'],
			stores : ['ManualManageStore'],
			exportUrl:'manualManage/export/手动交货期管理',
			constructor : function() {
					var me = this;
			
					// 初始化refs
					me.refs = me.refs || [];
					me.refs.push({
						ref : 'manualManageList',
						selector : me.manualManageList,
						autoCreate : true,
						xtype : me.manualManageList
					});
					
					me.refs.push({
						ref : 'manualManageEdit',
						selector : me.manualManageEdit,
						autoCreate : true,
						xtype : me.manualManageEdit
					});
					
					me.refs.push({
						ref: 'manualManageEdit', 
						selector: me.manualManageEdit + ' form'
					});
					me.callParent(arguments);
				},
	
			init : function() {
				var me = this;
				me.control(me.view + ' button[itemId=change]', {
				          click: function(){
				          	var view = Ext.ComponentQuery.query('manualManageList')[0];
				  			var selection = me.getGrid().getSelectionModel().getSelection();
				  			var workOrderIds = "";
				  			var ptFinishTimes = "";
				  			if(selection && selection!=''){
				  				var win = me.getManualManageEdit();
				  				var form = win.down('form').getForm();
				  				Ext.Array.each(selection, function(record, i) {
				  					workOrderIds += record.get("id") + ',';
				  					ptFinishTimes = record.get("ptFinishTime") ;
				  					clFinishTimes = record.get("clFinishTime") ;
				  					bzFinishTimes = record.get("bzFinishTime") ;
				  					htFinishTimes = record.get("htFinishTime") ;
				  					remarkss = record.get("remarks") ;
				  					coordinateTimes = record.get("coordinateTime") ;
				  					infoSourcess = record.get("infoSources") ;
				  					form.findField('id').setValue(workOrderIds);
				  					if("" != ptFinishTimes || null != ptFinishTimes){
				  						form.findField('ptFinishTime').setValue(ptFinishTimes);
				  					}
				  					if("" != clFinishTimes || null != clFinishTimes){
				  						form.findField('clFinishTime').setValue(clFinishTimes);
				  					}
				  					if("" != bzFinishTimes || null != bzFinishTimes){
				  						form.findField('bzFinishTime').setValue(bzFinishTimes);
				  					}
				  					if("" != htFinishTimes || null != htFinishTimes){
				  						form.findField('htFinishTime').setValue(htFinishTimes);
				  					}
				  					if("" != remarkss || null != remarkss){
				  						form.findField('remarks').setValue(remarkss);
				  					}
				  					if("" != htFinishTimes || null != htFinishTimes){
				  						form.findField('coordinateTime').setValue(coordinateTimes);
				  					}
				  					if("" != infoSourcess || null != infoSourcess){
				  						form.findField('infoSources').setValue(infoSourcess);
				  					}
				  				});
				  				win.show();
				  			}else{
				  				Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条数据！');
				  			}
				          }
      			});
      			
      			me.control(me.manualManageEdit + ' button[itemId=ok]',{
      					click: me.updateFinishTime
      			});
      			
				me.callParent(arguments);
			},
			
			onSearch : function(btn) {
				var me = this;
				var store = Ext.ComponentQuery.query('#manualManageList')[0].getStore();
				store.getProxy().url = 'manualManage';
				store.loadPage(1);
			},
			
			updateFinishTime : function(btn){
				var me = this;
				var win = me.getManualManageEdit();
				var form = win.down('form');
		        if (form.isValid()) {
		        	form.submit({
		        		success: function(form,action) {
							 	Ext.ComponentQuery.query('manualManageList')[0].getStore().load();
								win.close(); 	
						  },
						failure : function(form, action) {
								Ext.Msg.alert(Oit.msg.WARN,'更新失败');
						}
		        	})
		        }
			}
			
		});