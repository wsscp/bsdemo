Ext.define('bsmes.model.MesClientEquipInfo', {
			extend : 'Ext.data.Model',
			fields : ['equipCode', 'equipName', 'status', 'contracts', 'discNo', 'planLength', 'qualifiedLength', 'toDoTaskNum',
					'workOrderNo', 'remainQLength', 'remainQLengthExceptDisc', 'currentDiscQLength', 'currentDiscLength',
					'currentReportLength', 'productType', 'productSpec', 'currentProcess', 'currentProduct', 'sumReportLength',
					'productColor', 'productSpec', 'needFirstCheck', 'needMiddleCheck', 'needInCheck', 'needOutCheck', 'currentLength',
					'reportLength', 'contractLength', 'locationName', 'disk','diskNumber','section', 'reprotUser', 'operator','energyConsumptio', {
						name : 'requireFinishDate',
						type : 'date',
						convert : function(v, record) {
							if (v) {
								var dt = new Date(v);
								return Ext.Date.format(dt, "Y-m-d");
							} else {
								return v;
							}
						}
					}, {
						name : 'releaseDate',
						type : 'date',
						convert : function(v, record) {
							if (v) {
								var dt = new Date(v);
								return Ext.Date.format(dt, "Y-m-d");
							} else {
								return v;
							}
						}
					}]
		});
