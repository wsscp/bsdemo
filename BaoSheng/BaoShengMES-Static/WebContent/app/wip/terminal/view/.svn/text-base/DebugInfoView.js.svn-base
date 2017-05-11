Ext.define("bsmes.view.DebugInfoView", {
			extend : 'Ext.window.Window',
			alias : 'widget.debugInfoView',
			width : document.body.scrollWidth - 100,
			height : document.body.scrollHeight - 100,
			title : '下发参数',
			modal : true,
			initComponent : function() {
				var me = this;
				me.items = [{
					xtype : 'grid',
					store : Ext.create('Ext.data.Store', {
								fields : ['id', 'workOrderId', 'receiptId', 'receiptName', 'receiptCode', 'equipCode', 'receiptMaxValue',
										'receiptMinValue', 'receiptTargetValue'],
								proxy : {
									type : 'rest',
									url : 'receipt/' + Ext.fly('orderDetail').getAttribute('num')
								}
							}),
					columnLines : true,
					allowDeselect : true,
					plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
								clicksToEdit : 1
							})],
					columns : [{
								text : Oit.msg.wip.terminal.equipCode,
								dataIndex : 'equipCode',
								flex : 2
							}, {
								text : Oit.msg.wip.terminal.code,
								dataIndex : 'receiptCode',
								flex : 2
							}, {
								text : Oit.msg.wip.terminal.name,
								dataIndex : 'receiptName',
								flex : 2
							}, {
								text : Oit.msg.wip.terminal.receiptMinValue,
								dataIndex : 'receiptMinValue',
								flex : 1
							}, {
								text : Oit.msg.wip.terminal.receiptMaxValue,
								dataIndex : 'receiptMaxValue',
								flex : 1
							}, {
								text : Oit.msg.wip.terminal.targetValue,
								dataIndex : 'receiptTargetValue',
								flex : 2,
								editor : {
									xtype : 'textfield',
									listeners : {
										focus : function(me, the, eOpts) {
											var win = Ext.create('bsmes.view.VirtualKeyBoardWindow', {
														targetGridId : 'debugInfoGrid',
														targetFieldName : 'receiptTargetValue',
														targetVlaue : me.getValue()
													});

											win.show();
											win.down('form').getForm().findField('targetValue').focus(false, 100);
										}
									}
								}
							}]
				}];
				Ext.apply(me, {
							buttons : ['->', {
										itemId : 'debugInfoSave',
										text : Oit.btn.ok
									}, {
										itemId : 'cancel',
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});
				this.callParent(arguments);
			}
		});
