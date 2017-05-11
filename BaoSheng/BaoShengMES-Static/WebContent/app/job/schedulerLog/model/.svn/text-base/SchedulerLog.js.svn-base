Ext.define('bsmes.model.SchedulerLog', {
	extend : 'Ext.data.Model',
	fields : [ {name : 'id',type : 'string'},
	           {name : 'jobName',type : 'string'},
	           {name : 'jobDesc',type : 'string'},
	           {name : 'hostName',type : 'string'},
	           {name : 'hostAddress',type : 'string'},
	           {name : 'flag',type : 'string',
	        	   renderer : function(value) {
	        		   if(value=='false'){
	        			   return '完成';
	        		   }else{
	        			   return '进行中';
	        		   }
	        	   },
	        	},
	           {name : 'prevStartTime',type : 'date'},
	           {name : 'prevResult',type : 'string'},
	           {name : 'prevEndTime',type : 'date'},
	           {name : 'errorMessage',type : 'string'}]
});
