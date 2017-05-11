Ext.define('bsmes.model.ProcessInOut', {
			extend : 'Ext.data.Model',
			fields : ['id', 'productProcessId', 'matCode', 'matName', {
						name : 'inOrOut',
						type : 'string',
						renderer : function(value){
							if (value == 'IN') {
								return Oit.msg.pro.processInOut.inType;
							} else if (value == 'OUT') {
								return Oit.msg.pro.processInOut.outType;
							} else {
								return '';
							}
						}
					}, 'quantity', 'quantityFormula', 'unit', 'useMethod',
					'remark', 'matSpec', 'color']
		});