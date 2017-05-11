Ext.define('bsmes.store.CheckGridStore',{
    extend:'Oit.app.data.GridStore',
    fields : ['id','describe','value','isPassed','remarks','recordId','isPassedtmp'],
    autoLoad:false,
    proxy:{
    	type:'rest',
    	url : 'check'
    },
	listeners:{
   	   'load':function(store, records, successful, eOpts){
   		  $("input[type='radio'], input[type='checkbox']").ionCheckRadio();
   	   }
	}
});