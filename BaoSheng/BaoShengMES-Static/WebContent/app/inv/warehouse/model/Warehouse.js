Ext.define('bsmes.model.Warehouse',{
	extend:'Ext.data.Model',
	fields:[
	        	'warehouseCode',
		    	'warehouseName',
		    	'address', 
		    	'orgCode',
		    	'id',
		    	{
	        		name:'type',
	        		type : 'string',
	        		renderer : function(value) {
	        			if(value=='OTHER'){
	        				return '其他';
	        			}else if(value=='WIP'){
	        				return '在制品仓库';
	        			}else{
	        				return ''
	        			}
	        		}
	        	}
	        ]
});