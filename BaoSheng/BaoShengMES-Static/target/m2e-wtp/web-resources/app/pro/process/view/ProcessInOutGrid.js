Ext.define('bsmes.view.ProcessInOutGrid', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.processInOutGrid',
			collapsible : false,
			animCollapse : false,
			height:document.body.scrollHeight-140,
			forceFit : true,
			hasPaging : false,
			store : 'ProcessInOutStore',
			columns : [{
						text : Oit.msg.pro.processInOut.matCode,
						dataIndex : 'matCode',
						minWidth: 150,
						flex : 5
					}, {
						text : Oit.msg.pro.processInOut.matName,
						dataIndex : 'matName',
						minWidth: 150,
						flex : 5
					}, {
						text : Oit.msg.pro.processInOut.inOrOut,
						dataIndex : 'inOrOut',
						flex : 1.8
					},  {
						text : Oit.msg.pro.processInOut.color,
						dataIndex : 'color',
						flex : 2.2
					}, {
						text : Oit.msg.pro.processInOut.matSpec,
						dataIndex : 'matSpec',
						flex : 2.2
					}, {
						text : Oit.msg.pro.processInOut.quantity,
						dataIndex : 'quantity',
						flex : 2.2
					}, {
						text : Oit.msg.pro.processInOut.quantityFormula,
						dataIndex : 'quantityFormula',
						flex : 4
					}, {
						text : Oit.msg.pro.processInOut.unit,
						dataIndex : 'unit',
						flex : 1.8
					}, {
						text : Oit.msg.pro.processInOut.useMethod,
						dataIndex : 'useMethod',
						flex : 2.4
					}, {
						text : Oit.msg.pro.processInOut.remark,
						dataIndex : 'remark',
						flex : 2.2
					}],
			// 查询栏
			dockedItems : [{
						items : [{
									xtype : 'hform',
									items : [{
												xtype : 'hiddenfield',
												name : 'processId'
											}]
								}]

					}],
			tbar : [{
						itemId : 'processInOutEditBtn',
						text : Oit.msg.pro.processInOut.processInOutEditText
					}, '->', {
						text : '关  闭',
						handler : function(){
							var me = this;
							me.up('window').close();
						}
					}]
		});