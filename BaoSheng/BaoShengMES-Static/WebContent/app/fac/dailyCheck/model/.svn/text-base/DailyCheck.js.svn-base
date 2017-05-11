Ext.define("bsmes.model.DailyCheck",{
	extend 	: 'Ext.data.Model',
	fields	:[
	     	  'equipCode',
	     	  {name:'startTime',type:'date'},
	     	  {name:'finishTime',type:'date'},
	     	  'describe',
	     	  'value',
	     	  'isPassed',
	     	  'remarks',
	     	  {
	     		  name:'status',type:'string',
	     		  renderer:function(value){
	     			if(value=='IN_PROGRESS'){
	     				return  Oit.msg.fac.dailyCheck.inProgress;
	     			}else if(value=='FINISHED'){
	     				return  Oit.msg.fac.dailyCheck.finished;
	     			}else{
	     				return '';
	     			}
	     		}
	     	  },
	     	  {
	  			name : 'issuedTime',
	  			type : 'date'
	     	  }]
});
