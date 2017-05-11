Ext.define('bsmes.controller.ProductManageController', {
	extend : 'Oit.app.controller.GridController',
	view : 'productManageList',
	views : [ 'ProductManageList'/*,'ValidateOrderFinishedWindow'*/],
	stores : [ 'ProductManageStore','AttachStore' ],
	constructor : function() {
		var me = this;

		// 初始化refs
		me.refs = me.refs || [];
		
		me.refs.push({
			ref : me.view,
			selector : me.view
		});
		/*me.refs.push({
			ref : 'validateOrderFinishedWindow',
			selector : 'validateOrderFinishedWindow',
			autoCreate: true, 
			xtype: 'validateOrderFinishedWindow'
		});*/
		me.callParent(arguments);
	},
	init : function() {
		var me = this;
		
		// 初始化工具栏
		me.control(me.view + ' button[itemId=finished]', {
			click: me.onFinished
		});
		
		me.callParent(arguments);
	},
	
	onFinished : function(){
		var me = this;
		var grid = me.getProductManageList();
		var selections = grid.getSelectionModel().getSelection();
		var jsonData = [];
		if(selections.length>0){
			Ext.Array.each(selections, function(value) {
				jsonData.push({
					'id' : value.get('id'),
					'salesOrderItemId' : value.get('salesOrderItemId'),
					'contractNo' : value.get('contractNo'),
					'custProductType' : value.get('custProductType'),
					'contractLength' : value.get('contractLength'),
					'createUserCode' : value.get('createUserCode'),
					'custProductSpec' : value.get('custProductSpec')
					});
		    });
			Ext.Ajax.request({
			    url: 'productManage/updateOrdersStatus',
			    params: {
			    	jsonData : Ext.encode(jsonData)
			    },
			    success: function(response){
			        var result = Ext.decode(response.responseText);
					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
					grid.getStore().reload();
			    },
			    failure : function(response) {
    				var result = Ext.decode(response.responseText);
    				var msg;
    				var flag = true;
    				for(var key in result){
    					if(key=='message' || key=='success'){
    						continue;
    					}
    					var arry = result[key];
    					if(flag){
    						msg = '<p>合同号:'+arry[0]+'&nbsp;'+'客户型号规格:'+arry[1]+arry[2]+'&nbsp;'+arry[3]+'</p>';
    						flag = false;
    					}else{
    						msg = msg + '<p>合同号:'+arry[0]+'&nbsp;'+'客户型号规格:'+arry[1]+arry[2]+'&nbsp;'+arry[3]+'</p>';
    					}
    				}
					Ext.Msg.alert(Oit.msg.PROMPT, msg+'<p>订单未完成或未报工</p>');
					grid.getStore().reload();
    			}
			});
			/*var window = me.getValidateOrderFinishedWindow({
				ids : ids
			}).show();*/
		}else{
			Ext.Msg.alert('提示','请至少选择一行');
			return;
		}
	}
	
});

