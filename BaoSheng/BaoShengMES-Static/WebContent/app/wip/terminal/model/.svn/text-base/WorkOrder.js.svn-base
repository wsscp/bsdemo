Ext.define('bsmes.model.WorkOrder', {
			extend : 'Ext.data.Model',
			fields : ['workOrderNo', 'contractNo', 'typeSpec', 'equipCode', 'processName', {
						name : 'status',
						convert : function(value, record) {
							var timeByStatus = record.get('timeByStatus');
							var text;
							if (value == 'TO_DO') {
								text = '待生产';
							}
							if (value == 'FINISHED') {
								text = '已完成';
							}
							if (value == 'IN_PROGRESS') {
								text = '生产中';
							}
							return text;
						}
					}, 'id', {
						name : 'modifyTime',
						type : 'Date',
						convert : function(v, record) {
							if (v) {
								var dt = new Date(v);
								return Ext.Date.format(dt, "Y-m-d");
							} else {
								return v;
							}
						}
					}, {
						name : 'createTime',
						type : 'Date',
						convert : function(v, record) {
							if (v) {
								var dt = new Date(v);
								return Ext.Date.format(dt, "Y-m-d");
							} else {
								return v;
							}
						}
					}, 'timeByStatus', 'matStatusText', 'matStatus', 'productType', 'productSpec', 'productLength', 'orderLength',
					'cusOrderItemIds', 'outProductColor', 'conductorstruct', 'outProductColor', 'colorProductLength', 'wiresStructureStr',
					'operateReason', 'docMakerUserCode', 'receiverUserCode', 'userComment', {
						name : 'releaseDate',
						type : 'Date',
						convert : function(v, record) {
							if (v) {
								var dt = new Date(v);
								return Ext.Date.format(dt, "Y-m-d");
							} else {
								return v;
							}
						}
					}, {
						name : 'realEndTime',
						type : 'Date',
						convert : function(v, record) {
							if (v) {
								var dt = new Date(v);
								return Ext.Date.format(dt, "Y-m-d");
							} else {
								return v;
							}
						}
					}, {
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
					}]
		});