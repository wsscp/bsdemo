/**
 * Created by JinHy on 2014/6/11 0011.
 */

Ext.define("bsmes.view.ProcessReceiptGrid", {
			extend : 'Ext.grid.Panel',
			alias : 'widget.processReceiptGrid',
			title : Oit.msg.wip.terminal.receiptTitle,
			forceFit : false,
			store : 'ProcessReceiptStore',
			itemId : 'processReceiptGrid',
			rowLines : true,
			columnLines : true,
			dataArray : null, // 加载数据对象
			width : document.body.scrollWidth,
			columns : [{
						text : Oit.msg.wip.terminal.name,
						dataIndex : 'receiptName',
						flex : 2
					}, {
						text : Oit.msg.wip.terminal.unit,
						dataIndex : 'dataUnit',
						flex : 1
					}, {
						text : Oit.msg.wip.terminal.valueRange,
						dataIndex : 'receiptValueRange',
						flex : 1
					}, {
						text : Oit.msg.wip.terminal.targetValue,
						dataIndex : 'receiptTargetValue',
						flex : 1
					}, {
						text : Oit.msg.wip.terminal.setValue,
						dataIndex : 'setValue',
						flex : 1
					}, {
						text : Oit.msg.wip.terminal.daValue,
						dataIndex : 'daValue',
						flex : 1
					}],
			initComponent : function() {
				var me = this;
				this.callParent(arguments);
				if (me.dataArray)
					me.getStore().loadData(me.dataArray) // 加载数据
			},

			/**
			 * 页面自动刷新调用方法
			 */
			refresh : function(dataArray) {
				var me = this;
				if (dataArray)
					me.getStore().loadData(dataArray);
			}
		});