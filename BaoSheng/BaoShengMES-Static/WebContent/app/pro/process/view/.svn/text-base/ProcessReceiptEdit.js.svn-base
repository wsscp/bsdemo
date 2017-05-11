Ext.define('bsmes.view.ProcessReceiptEdit', {
	extend : 'Oit.app.view.form.EditForm',
	alias : 'widget.processReceiptEdit',
	title : Oit.msg.pro.processReceipt.processReceiptEditText,
	iconCls : 'feed-edit',
	width : 650,
	formItems : [{
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 100,
					width : 300
				},
				margin : '0 0 10 0',
				items : [{
							fieldLabel : Oit.msg.pro.processReceipt.receiptCode,
							xtype : 'displayfield',
							name : 'receiptCode'
						}, {
							fieldLabel : Oit.msg.pro.processReceipt.receiptName,
							xtype : 'displayfield',
							margin : '0 0 0 10',
							name : 'receiptName'
						}]
			}, {
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 100,
					width : 300
				},
				margin : '0 0 10 0',
				items : [{
							fieldLabel : Oit.msg.pro.processReceipt.subReceiptCode,
							xtype : 'displayfield',
							name : 'subReceiptCode'
						}, {
							fieldLabel : Oit.msg.pro.processReceipt.subReceiptName,
							xtype : 'displayfield',
							margin : '0 0 0 10',
							name : 'subReceiptName'
						}]
			}, {
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 100,
					width : 300
				},
				margin : '0 0 10 0',
				items : [{
					fieldLabel : Oit.msg.pro.processReceipt.needDa,
					xtype : 'radiogroup',
					columns : 4,
					items : [{
								boxLabel : Oit.msg.pro.processReceipt.needDaYes,
								name : 'needDa',
								inputValue : 'true'
							}, {
								boxLabel : Oit.msg.pro.processReceipt.needDaNo,
								name : 'needDa',
								inputValue : 'false',
								checked : true
							}]
				}, {
					fieldLabel : Oit.msg.pro.processReceipt.needIs,
					xtype : 'radiogroup',
					margin : '0 0 0 10',
					columns : 4,
					items : [{
								boxLabel : Oit.msg.pro.processReceipt.needDaYes,
								name : 'needIs',
								inputValue : 'true'
							}, {
								boxLabel : Oit.msg.pro.processReceipt.needDaNo,
								name : 'needIs',
								inputValue : 'false',
								checked : true
							}]
				}]
			}, {
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 100,
					width : 300
				},
				margin : '0 0 10 0',
				items : [{
							fieldLabel : Oit.msg.pro.processReceipt.frequence,
							xtype : 'numberfield',
							decimalPrecision : 2,
							name : 'frequence'
						}, {
							fieldLabel : Oit.msg.pro.processReceipt.receiptTargetValue,
							margin : '0 0 0 10',
							name : 'receiptTargetValue'
						}]
			}, {
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 100,
					width : 300
				},
				margin : '0 0 10 0',
				items : [{
							fieldLabel : Oit.msg.pro.processReceipt.receiptMaxValue,
							xtype : 'displayfield',
							name : 'receiptMaxValue'
						}, {
							fieldLabel : Oit.msg.pro.processReceipt.receiptMinValue,
							xtype : 'displayfield',
							margin : '0 0 0 10',
							name : 'receiptMinValue'
						}]
			}, {
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 100,
					width : 300
				},
				margin : '0 0 10 0',
				items : [{
							fieldLabel : Oit.msg.pro.processReceipt.dataType,
							xtype : 'radiogroup',
							columns : 4,
							items : [{
										boxLabel : Oit.dataType.string,
										name : 'dataType',
										inputValue : 'STRING'
									}, {
										boxLabel : Oit.dataType.number,
										name : 'dataType',
										inputValue : 'DOUBLE'
									}, {
										boxLabel : Oit.dataType.boolean,
										name : 'dataType',
										inputValue : 'BOOLEAN'
									}]
						}, {
							fieldLabel : Oit.msg.pro.processReceipt.dataUnit,
							margin : '0 0 0 10',
							name : 'dataUnit'
						}]
			}, {
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 100,
					width : 300
				},
				margin : '0 0 10 0',
				items : [{
					fieldLabel : Oit.msg.pro.processReceipt.hasPic,
					xtype : 'radiogroup',
					columns : 4,
					items : [{
								boxLabel : Oit.msg.pro.processReceipt.hasPicYes,
								name : 'hasPic',
								inputValue : 'true'
							}, {
								boxLabel : Oit.msg.pro.processReceipt.hasPicNo,
								name : 'hasPic',
								inputValue : 'false',
								checked : true
							}]
				}, {
					fieldLabel : Oit.msg.pro.processReceipt.needAlarm,
					xtype : 'radiogroup',
					margin : '0 0 0 10',
					columns : 4,
					items : [{
								boxLabel : Oit.msg.pro.processReceipt.needAlarmYes,
								name : 'needAlarm',
								inputValue : 'true'
							}, {
								boxLabel : Oit.msg.pro.processReceipt.needAlarmNo,
								name : 'needAlarm',
								inputValue : 'false',
								checked : true
							}]
				}]
			}, {
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				defaults : {
					labelWidth : 100,
					width : 300
				},
				margin : '0 0 10 0',
				items : [{
							fieldLabel : Oit.msg.pro.processReceipt.valueDomain,
							name : 'valueDomain'
						}, {
							fieldLabel : Oit.msg.pro.processReceipt.emphShow,
							xtype : 'radiogroup',
							margin : '0 0 0 10',
							columns : 4,
							items : [{
								boxLabel : Oit.msg.pro.processReceipt.emphShowYes,
								name : 'emphShow',
								inputValue : 'true'
							}, {
								boxLabel : Oit.msg.pro.processReceipt.emphShowNo,
								name : 'emphShow',
								inputValue : 'false',
								checked : true
							}]
						}]
			}

	]
}
);