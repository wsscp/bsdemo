Ext.define('bsmes.model.ProcessIn', {
			extend : 'Ext.data.Model',
			fields : ['id', 'matCode', 'matName', {
						name : 'hasPutIn',
						type : 'boolean'
					}, 'taskLength', 'color', 'inAttrDesc', 'quantity', 'unit', {
						name : 'planDate',
						type : 'date'
					}, 'remark']
		});