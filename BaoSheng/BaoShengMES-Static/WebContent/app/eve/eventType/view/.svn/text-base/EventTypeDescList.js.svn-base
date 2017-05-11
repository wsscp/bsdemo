Ext.define("bsmes.view.EventTypeDescList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.eventTypeDescList',
			store : 'EventTypeDescStore',
			defaultEditingPlugin : false,
			height : 383,
			columns : [
					/*
					 * { text : '事件编号', dataIndex : 'code' },
					 */
					{
				text : '事件明细内容',
				dataIndex : 'name',
				flex: 4
			}, {
				text : '顺序号',
				dataIndex : 'seq',
				flex: 1
			}, {
				text : '备注',
				dataIndex : 'marks',
				flex: 1.5
			}, {
				text : '数据状态',
				dataIndex : 'status',
				flex: 1
			}],
			tbar : [{
						itemId : 'addEventTypeDesc',
						text : '新增',
						iconCls : 'icon_add'
					}, {
						itemId : 'editEventTypeDesc',
						text : '修改',
						iconCls : 'icon_edit'
					}],
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						items : [{
									xtype : 'hform',
									items : [{
												fieldLabel : '事件类型',
												xtype : 'hiddenfield',
												name : 'extatt'
											}]
								}]
					}]
		});