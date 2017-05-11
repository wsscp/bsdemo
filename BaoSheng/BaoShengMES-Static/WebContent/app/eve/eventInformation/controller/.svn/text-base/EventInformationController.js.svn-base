Ext.define('bsmes.controller.EventInformationController', {
			extend : 'Oit.app.controller.GridController',
			view : 'eventInformationList',
			views : ['EventInformationList'],
			stores : ['EventInformationStore'],
			exportUrl : 'eventInformation/export/事件前台处理',
			newFormToEdit : false,
			constructor : function(){
				var me = this;
				// 初始化refs
				me.refs = me.refs || [];

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
				me.control(me.view + ' button[itemId=exportToXls]', {
							click : me.onExport
						});
				me.control(me.view + ' button[itemId=search]', {
							click : me.onSearch
						});
			},
			onSearch : function(){
				var me = this;
				var store = me.getGrid().getStore();
				var form = me.getSearchForm();
				var findParams = form.getValues();
				store.loadPage(1, {
							params : findParams
						});
			},

			refresh : function(){
				var me = this;
				//设置定时任务，没5秒钟响铃一次
				var task = {
	        			run : function(){
	        				soundManager.play('msgSound');
	        			},
	        			interval : 5000
	        	};
				me.getGrid().getStore().load();
				Ext.Ajax.request({
				    url: 'eventInformation/getWarnShow',
				    success: function(response){
				        var result = Ext.decode(response.responseText);
				        // process server response here
				        var msg='';
				        var title='';
				        Ext.each(result,function(obj,index){
				        	if(msg == ''){
				        		if(obj.director == 'dg'){
				        			msg = obj.eventContent + '请'+'<span style = "font-weight: bold;font-size : 16px;color : blue;padding-left:5px;padding-right:5px;">'+'电工'+'</span>'+'及时处理';
				        		}else if(obj.director == 'qg'){
				        			msg = obj.eventContent + '请'+'<span style = "font-weight: bold;font-size : 16px;color : blue;padding-left:5px;padding-right:5px;">'+'钳工'+'</span>'+'及时处理';
				        		}else{
				        			msg = obj.eventContent + '请维修班人员及时处理';
				        		}
				        	}else{
				        		if(obj.director == 'dg'){
				        			msg = msg + '</br>' + obj.eventContent + '请'+'<span style = "font-weight: bold;font-size : 16px;color : blue;padding-left:5px;padding-right:5px;">'+'电工'+'</span>'+'及时处理';
				        		}else if(obj.director == 'qg'){
				        			msg = msg + '</br>' + obj.eventContent + '请'+'<span style = "font-weight: bold;font-size : 16px;color : blue;padding-left:5px;padding-right:5px;">'+'钳工'+'</span>'+'及时处理';
				        		}else{
				        			msg = msg + '</br>' + obj.eventContent + '请维修班人员及时处理';
				        		}
				        	}
				        	title = obj.eventTitle;
				        });
				        if(title != ''){
				        	//关闭定时任务
    						Ext.TaskManager.destroy();
				        	Ext.Msg.show({
				        		title:title,
				        		msg: '<span style="color: red">' + msg,
				        		buttons: Ext.Msg.YES,
				        		buttonText : {yes : '响应'},
				        		icon: Ext.Msg.WARNING,
				        		fn : function(btn){
				        			if(btn == 'yes'){
				        				Ext.Ajax.request({
				        					url : 'eventInformation/updateWarnShow',
				        					params : {
				        						jsonText : Ext.encode(result)
				        					},
				        					success: function(response){
				        						//关闭定时任务
				        						Ext.TaskManager.stop(task);
				        						Ext.Msg.close();
				        					}
				        				});
				        			}
				        		}
				        	});
				        	//开启定时任务
				        	Ext.TaskManager.start(task); 
				        }
				    }
				});
			}
		});