Ext.define('bsmes.view.OrgDetail', {
			extend : 'Oit.app.view.form.DetailForm',
			alias : 'widget.orgDetail',
			title : '组织机构信息',
			iconCls : 'feed-add',
	// formItems : [{
	// fieldLabel : Oit.msg.bas.employee.name + '<font color=red>*</font>',
	// allowBlank : false,
	// name : 'name'
	// }, {
	// fieldLabel : Oit.msg.bas.employee.userCode + '<font color=red>*</font>',
	// allowBlank : false,
	// name : 'userCode'
	// }, {
	// fieldLabel : Oit.msg.bas.employee.tel,
	// name : 'telephone'
	// }, {
	// fieldLabel : Oit.msg.bas.employee.email,
	// name : 'email'
	// }, {
	// fieldLabel : Oit.msg.bas.employee.orgCode + '<font color=red>*</font>',
	// allowBlank : false,
	// name : 'orgName'
	// }, {
	// fieldLabel : Oit.msg.bas.employee.certificate,
	// name : 'certificateName'
	//					}]




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
		})
