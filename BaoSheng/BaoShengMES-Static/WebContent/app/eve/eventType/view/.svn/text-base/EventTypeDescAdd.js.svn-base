Ext.define('bsmes.view.EventTypeDescAdd', {
			extend : 'Oit.app.view.form.EditForm',
			alias : 'widget.eventTypeDescAdd',
			title : '新增事件类型明细',
			/* iconCls: 'feed-add', */
			formItems : [{
						xtype : 'hiddenfield',
						name : 'extatt'
					}, {
						xtype : 'hiddenfield',
						name : 'code'
					}, {
						fieldLabel : '事件明细内容<font color=red>*</font>',
						xtype : 'textarea',
						width : 400,
						name : 'name',
						allowBlank : false
					}, {
						fieldLabel : '顺序号<font color=red>*</font>',
						width : 400,
						name : 'seq',
						allowBlank : false
					}, {
						fieldLabel : '备注',
						width : 400,
						name : 'marks'
					}, {
						fieldLabel : '数据状态',
						width : 400,
						xtype : 'radiogroup',
						columns : 2,
						vertical : true,
						items : [{
									boxLabel : '正常',
									name : 'status',
									inputValue : '1',
									checked : true
								}, {
									boxLabel : '冻结',
									name : 'status',
									inputValue : '0'
								}]
					}]
		});
