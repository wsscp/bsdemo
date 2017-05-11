Ext.define('bsmes.view.ProcessReceiptGrid', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.processReceiptGrid',
			collapsible : false,
			animCollapse : false,
			height : document.body.scrollHeight - 190,
			forceFit : false,
			hasPaging : false,
			store : 'ProcessReceiptStore',
			columns : [{
						text : Oit.msg.pro.processReceipt.receiptCode,
						dataIndex : 'receiptCode',
						minWidth : 120,
						flex : 1.5
					}, {
						text : Oit.msg.pro.processReceipt.receiptName,
						dataIndex : 'receiptName',
						minWidth : 180,
						flex : 2
					}, {
						text : Oit.msg.pro.processReceipt.subReceiptCode,
						dataIndex : 'subReceiptCode',
						minWidth : 120,
						flex : 1.5
					}, {
						text : Oit.msg.pro.processReceipt.subReceiptName,
						dataIndex : 'subReceiptName',
						minWidth : 180,
						flex : 2
					}, {
						text : Oit.msg.pro.processReceipt.needDa,
						dataIndex : 'needDa',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.needIs,
						dataIndex : 'needIs',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.frequence,
						dataIndex : 'frequence',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.receiptTargetValue,
						dataIndex : 'receiptTargetValue',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.receiptMaxValue,
						dataIndex : 'receiptMaxValue',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.receiptMinValue,
						dataIndex : 'receiptMinValue',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.dataType,
						dataIndex : 'dataType',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.dataUnit,
						dataIndex : 'dataUnit',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.emphShow,
						dataIndex : 'emphShow',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.hasPic,
						dataIndex : 'hasPic',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.needAlarm,
						dataIndex : 'needAlarm',
						minWidth : 100,
						flex : 1
					}, {
						text : Oit.msg.pro.processReceipt.valueDomain,
						dataIndex : 'valueDomain',
						minWidth : 100,
						flex : 1
					}],
			tbar : [{
						itemId : 'processReceiptEditBtn',
						text : Oit.msg.pro.processReceipt.processReceiptEditText
					}]
		}
);