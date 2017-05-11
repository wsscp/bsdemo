Ext.define('bsmes.model.EventInformation',{
	extend : 'Ext.data.Model',
	fields : [{name : 'id',type : 'string'},
	          {name : 'name',type : 'string'},	        
	          {name : 'processType',type : 'string',renderer:function(value){
		  			if(value=='SMS'){
						return "短信";
					}else if (value=='MESSAGE'){
						return "系统消息";
					}else if(value=='ALL'){
						return "短信，邮件，系统消息";
					}else if(value=='SMSPLUSMESSAGE'){
						return "短息，系统消息";
					}else if(value=='EMAILPLUSMESSAGE'){
						return "邮件，系统消息";
					}else if(value=='SMSPLUSEMAIL'){
						return "短信，邮件";
					}else{
						return "邮件";
					}
		          }},
	          {name : 'eventTitle',type : 'string'},
	          {name : 'eventContent',type : 'string'},
	          {name : 'productCode',type : 'string'},
	          {name : 'eventStatus',type : 'string',renderer:function(value){
	  			if(value=='UNCOMPLETED'){
					return "未处理";
				}else if (value=='RESPONDED'){
					return "已响应";
				}else if (value=='PENDING'){
					return "待确认";
				}else{
					return "已完成";
				}
	          }},
	          {name : 'pendingProcessing',type : 'string',renderer:function(value){
		  			if(value=='true'){
						return "是"; 
					}else
						{
						return "否";
						}
		          }},
	          {name : 'eventReason',type : 'string'},
	          {name : 'eventResult',type : 'string'},
	          {name : 'eventProcessId',type : 'string'},
	          {name : 'eventTypeId',type : 'string'},
	          {name : 'equipCode',type : 'string'},
	          {name : 'createTime',type : 'date'},
	          {name : 'responseTime',type : 'date'},
	          {name : 'completeTime',type : 'date'},
	          {name : 'responsible',type : 'string'},
	          {name : 'batchNo',type : 'string'},
	          {name : 'processSeq', type: 'int'},
	          {name :'eqipCategory',type : 'string'}]
});