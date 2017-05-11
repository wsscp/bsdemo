Ext.define('bsmes.model.EventType', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
		name : 'name',
		type : 'string'
	},{
		name : 'code',
		type : 'string'
	},
	 {name : 'needShow', type : 'string', renderer : function(value) {
			if (value == '1') {
				return '显示';
			} else if (value == '0') {
				return '不显示';
			} else {
				return "";
			}
	  }}
	 ]
});
