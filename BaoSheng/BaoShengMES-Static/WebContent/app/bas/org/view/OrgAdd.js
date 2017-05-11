Ext.define('bsmes.view.OrgAdd', {
			extend : 'Oit.app.view.form.EditForm',
			alias : 'widget.orgadd',
			title : Oit.msg.bas.org.addForm.title,
			iconCls : 'feed-add',
			formItems : [{
						fieldLabel : Oit.msg.bas.org.orgCode + '<font color=red>*</font>',
						id : 'orgCodeId',
						name : 'orgCode',
						allowBlank : false
					}, {
						fieldLabel : Oit.msg.bas.org.name + '<font color=red>*</font>',
						name : 'name',
						allowBlank : false
					}, {
						fieldLabel : Oit.msg.bas.org.parentCode,
						name : 'parentCode',
						xtype : 'combobox',
						displayField : 'name',
						valueField : 'orgCode',
						editable : false,
						store : new Ext.data.Store({
									proxy : {
										type : 'rest',
										url : 'employee/getOrgCode',
										reader : {
											type : 'json'
										}
									},
									fields : ['orgCode', 'name']
								})
					}, {
						fieldLabel : Oit.msg.bas.org.type,
						name : 'type'
					}, {
						fieldLabel : Oit.msg.bas.org.description,
						name : 'description'
					},{
						fieldLabel : 'demo'+ '<font color=red>*</font>',
						name : 'demo',
						allowBlank : false
					}]
		});
