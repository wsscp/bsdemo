Ext.define("bsmes.model.ShunDownAnalysis",{
	extend 	: 'Ext.data.Model',
	fields	:['id','equipCode','equipName','productName','reason','isCompleted','timeBet',
	     	  {name:'startTime',type:'date'},
	     	  {name:'endTime',type:'date'}]
});
