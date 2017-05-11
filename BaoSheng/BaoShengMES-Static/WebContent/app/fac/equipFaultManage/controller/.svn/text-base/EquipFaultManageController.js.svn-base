Ext.define('bsmes.controller.EquipFaultManageController', {
			extend : 'Oit.app.controller.GridController',
			view : 'equipFaultManageList',
			views : ['EquipFaultManageList','EquipEventFlowEdit','SparePartEdit'],
			stores : ['EquipFaultManageStore','SparePartStore'],
			newFormToEdit : false,
			constructor : function(){
				var me = this;
				// 初始化refs
				me.refs = me.refs || [{
			    	ref: 'equipEventFlowEdit',
			        selector: 'equipEventFlowEdit',
			        autoCreate: true,
			        xtype: 'equipEventFlowEdit'
			    },{
			    	ref: 'sparePartEdit',
			        selector: 'sparePartEdit',
			        autoCreate: true,
			        xtype: 'sparePartEdit'
			    }];
				

				me.callParent(arguments);

				setInterval(function(){
							me.refresh.apply(me);
						}, 60000);
				setInterval(function(){
					if(Ext.Msg.isVisible()){
						Ext.Msg.close();
					}
				}, 60000);
			},
			init : function(){
				var me = this;
				
				me.control('sparePartEdit button[itemId=ok]', {
		            click: function() {
		            	var form = Ext.ComponentQuery.query('#equipEventFlowEdit form')[0];
		            	var recordId = form.getValues().id;
		            	var eventInfoId = form.getValues().eventInfoId;
		            	var win = me.getSparePartEdit();
		            	var f = win.query('form')[0];
		            	f.submit({
		                	url : 'equipInformation/saveSparePart',
		                	params : {
		                		recordId : recordId,
		                		eventInfoId : eventInfoId
		                	},
		                    success: function(form, action) {
		                    	win.close();
		                    	var grid = Ext.ComponentQuery.query('#equipEventFlowEdit grid')[0];
		                    	var store = grid.getStore().load({
		                    		params : {
		                    			recordId : recordId
		                    		}
		                    	});
		                    },
		                    failure : function(form, action) {}
		                });
		            }
		        });
				
				 me.control('equipEventFlowEdit button[itemId=replace]', {
			            click: function() {
			                me.getSparePartEdit().show();
			            }
			        });
				
				
				me.callParent(arguments);
			},

			refresh : function(){
				var me = this;
				var msg;
		        var flag = true;
				me.getGrid().getStore().load();
				Ext.Ajax.request({
				    url: 'equipFaultManage/getWarnShow',
				    success: function(response){
				        var results = Ext.decode(response.responseText);
				        if(results.length==0){
				        	return;
				        }
				        Ext.each(results,function(record,index){
				        	if(flag){
				        		if(record.eventStatus == 'NOTCOMPLETED'){
				        			msg = '<p>'+record.eventContent + '<span class="responded">(需'
				        										+record.director+'),处理失败,请继续处理</span>' +'</p>';
				        		}else{
				        			msg = '<p>'+record.eventContent + '<span class="unresponded">(需'
				        										+record.director+'),未响应,请速处理</span>' +'</p>';
				        		}
				        		flag = false;
				        	}else{
				        		if(record.eventStatus == 'NOTCOMPLETED'){
				        			msg += '<p>'+record.eventContent + '<span class="responded">(需'
				        										+record.director+'),处理失败,请继续处理</span>' +'</p>';
				        		}else{
				        			msg += '<p>'+record.eventContent + '<span class="unresponded">(需'
				        										+record.director+'),未响应,请速处理</span>' +'</p>';
				        		}
				        	}
				        });
				        var maxHeight = document.body.scrollHeight/1.5;
				        msg = '<div style="max-height:' + maxHeight + ';width:100%;overflow:auto;">' + msg + '</div>'
			        	Ext.Msg.show({
			        		title:'设备异常提醒',
			        		msg: msg,
			        		buttons: Ext.Msg.YES,
			        		buttonText : {yes : '响应'},
			        		icon: Ext.Msg.WARNING,
			        		fn : function(btn){
			        			if(btn == 'yes'){
			        				Ext.Ajax.request({
			        					url : 'equipFaultManage/updateWarnShow',
			        					params : {
			        						jsonText : Ext.encode(results)
			        					},
			        					success: function(response){
			        						Ext.Msg.close();
			        						me.getGrid().getStore().reload();
			        					}
			        				});
			        			}
			        		}
			        	});
			        	soundManager.play('msgSound');
				    }
				});
			}
		});