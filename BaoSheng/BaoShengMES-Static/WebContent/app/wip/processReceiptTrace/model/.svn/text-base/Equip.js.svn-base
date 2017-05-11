//Employee
Ext.define('bsmes.model.Equip', {
			extend : 'Ext.data.Model',
			fields : ['id', 'code', 'ecode', 'equipAlias', {
						name : 'name',
						convert : function(value, record) {
							if (record.get('equipAlias') != '') {
								return record.get('equipAlias') + '-' + value + '[' + record.get('code') + ']';
							}
							return value + '[' + record.get('code') + ']';
						}
					}]
		});