Ext.define('bsmes.model.ProcessReceipt', {
			extend : 'Ext.data.Model',
			fields : ['id', 'receiptCode', 'receiptName', 'subReceiptCode',
					'subReceiptName', {
						name : 'needDa',
						type : 'boolean',
						renderer : function(value){
							if (value == true) {
								return Oit.msg.pro.processReceipt.needDaYes;
							} else {
								return Oit.msg.pro.processReceipt.needDaNo;
							}
						}
					}, {
						name : 'needIs',
						type : 'boolean',
						renderer : function(value){
							if (value == true) {
								return Oit.msg.pro.processReceipt.needDaYes;
							} else {
								return Oit.msg.pro.processReceipt.needDaNo;
							}
						}
					}, {
						name : 'dataType',
						type : 'string',
						renderer : function(value){
							if (value == 'STRING') {
								return Oit.dataType.string;
							} else if (value == 'DOUBLE') {
								return Oit.dataType.number;
							} else if (value == 'BOOLEAN') {
								return Oit.dataType.boolean;
							} else {
								return '';
							}
						}
					}, 'dataUnit', 'marks', 'receiptTargetValue',
					'receiptMaxValue', 'receiptMinValue', {
						name : 'hasPic',
						type : 'boolean',
						renderer : function(value){
							if (value == true) {
								return Oit.msg.pro.processReceipt.hasPicYes;
							} else {
								return Oit.msg.pro.processReceipt.hasPicNo;
							}
						}
					}, {
						name : 'needShow',
						type : 'boolean',
						renderer : function(value){
							if (value == true) {
								return Oit.msg.pro.processReceipt.needShowYes;
							} else {
								return Oit.msg.pro.processReceipt.needShowNo;
							}
						}
					}, {
						name : 'needAlarm',
						type : 'boolean',
						renderer : function(value){
							if (value == true) {
								return Oit.msg.pro.processReceipt.needAlarmYes;
							} else {
								return Oit.msg.pro.processReceipt.needAlarmNo;
							}
						}
					}, 'valueDomain', 'frequence', {
						name : 'emphShow',
						type : 'string',
						renderer : function(value){
							if (value == '1') {
								return Oit.msg.pro.processReceipt.emphShowYes;
							} else if (value == '0') {
								return Oit.msg.pro.processReceipt.emphShowNo;
							} else {
								return '';
							}
						}
					}]
		});