Ext.define('bsmes.store.EmployeeUserStore', {
	extend : 'Ext.data.Store',
	fields : [ 'id', 'userCode', 
	   { name: 'name',
         convert: function(value, record) {
            return record.get('userCode') + '_' + value;
        }
       } 
    ],
	sorters : [{}],
	proxy : {
		type: 'rest',
		url : 'eventType/getEmployeeOrRole'
		
	}
});