Ext.define('bsmes.model.Location',{
	extend:'Ext.data.Model',
	fields:[
	        	'processCode',
	        	'processName',
	        	'warehouseName',
		    	'locationName',
		    	'locationX', 
		    	'locationY',
		    	'locationZ', 
		    	{
	        		name:'type',
	        		type : 'string',
	        		renderer : function(value) {
	        			if(value=='TEMP'){
	        				return '临时库位';
	        			}else if(value=='FIX'){
	        				return '普通库位';
	        			}else{
	        				return ''
	        			}
	        		}
	        	}
	        ]
});