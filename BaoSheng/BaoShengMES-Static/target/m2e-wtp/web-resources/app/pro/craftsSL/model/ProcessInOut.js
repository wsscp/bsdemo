Ext.define('bsmes.model.ProcessInOut', {
			extend : 'Ext.data.Model',
			fields : ['id', 'productProcessId', 'matCode', 'matName', {
						name : 'inOrOut',
						type : 'string'
					}, {
						name : 'quantity',
						type : 'double'
					}, 'quantityFormula', 'unit', 'useMethod', 'remark', 'matSpec', 'color', {
						name : 'oldMatCode',
						type : 'string',
						convert : function(value, record) {
							return record.get('matCode');
						}
					}, {
						name : 'oldMatName',
						type : 'string',
						convert : function(value, record) {
							return record.get('matName');
						}
					}]
		});