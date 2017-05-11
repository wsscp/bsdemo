Ext.define('bsmes.store.ProcessQAEntryStore',{
	extend:'Ext.data.Store',
	model:'bsmes.model.ProcessQA',
	sorters : [ {
		property : 'checkItemCode',
		direction : 'ASC'
	} ],
    autoLoad:false,
	proxy : {
		type:'rest',
		url : '../terminal/queryProcessQcValue',
	    reader: {
	            type: 'json',
	            root: 'rows'
        	}
	},
	listeners:{
   	   'load':function(store, records, successful, eOpts){
   		   $("input[type='radio'], input[type='checkbox']").ionCheckRadio();
		   Ext.each(records,function(record){
			   if(record.getData().qcResult == '通过'){
				   $("input[name='qcResult"+record.index+"']:eq(0)").parent("label").find("+span").addClass('checked');
			   }else if(record.getData().qcResult == '不通过'){
				   $("input[name='qcResult"+record.index+"']:eq(1)").parent("label").find("+span").addClass('checked');
			   }
		   });
   	   },
   	   'beforeload': function( store, operation, eOpts ){
   		   if(Ext.fly('multiple')){ // 如果在设备列表页面，变更url请求地址
   		       store.proxy.url = 'terminal/queryProcessQcValue';
   		       // Ext.fly('single')multiple
   		   }
   	   }
	}



});