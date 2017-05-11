Ext.define("bsmes.model.TurnoverReport", {
			extend : 'Ext.data.Model',
			fields : ['equipCode', 'shiftName', 'dbUserName', 'fdbUserName',
					'fzgUserName', 'workOrderNo', 'custProductType',
					'custProductSpec', 'workOrderLength', 'reportLength',
					'contractNo','processName','processCode','matName',
					'matCode','quotaQuantity','realQuantity',
					{
						name : 'shiftDate',
						type : 'Date'
					}]
		});
