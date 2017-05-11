Ext.define('bsmes.model.EventTypeDescModel',{
	extend : 'Ext.data.Model',
	fields : [{name : 'id',type : 'string'},
	          {name : 'code',type : 'string'},	        
	          {name : 'name',type : 'string'},
	          {name : 'marks',type : 'string'},
	          {name : 'seq',type : 'string'},
	          {name : 'extatt',type : 'string'},
	          {name : 'status', type : 'string', renderer : function(value) {
	  				if (value == '1') {
	  					return '正常';
	  				} else if (value == '0') {
	  					return '冻结';
	  				} else {
	  					return "";
	  				}
	  		  }}
    ]
});