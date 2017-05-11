// 查看生产单: 补开生产单
Ext.define('bsmes.view.WorkOrderOperateLogWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.workOrderOperateLogWindow',
			title : '生产单操作历史查看',
			width : document.body.scrollWidth * 0.8,
			height : document.body.scrollHeight * 0.8,
			modal : true,
			plain : true,
			layout : 'fit',
			initComponent : function() {
				var me = this;

				me.items = [{
							xtype : 'grid',
							store : 'WorkOrderOperateLogStore',
							columns : [{
										text : '设备名称',
										dataIndex : 'equipName',
										flex : 1,
										sortable : false,
										menuDisabled : true,
										renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
											return value + '[' + record.get('equipCode') + ']';
										}
									}, {
										text : '操作人',
										dataIndex : 'userName',
										flex : 1,
										sortable : false,
										menuDisabled : true
									}, {
										text : '原因',
										dataIndex : 'operateReason',
										flex : 2,
										sortable : false,
										menuDisabled : true
									}, {
										text : '操作时间',
										dataIndex : 'createTime',
										xtype : 'datecolumn',
										format : 'Y-m-d H:i:s',
										flex : 1,
										sortable : false,
										menuDisabled : true
									}]
						}];

				Ext.apply(me, {
							buttons : ['->', {
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});

				this.callParent(arguments);
			}
		});
