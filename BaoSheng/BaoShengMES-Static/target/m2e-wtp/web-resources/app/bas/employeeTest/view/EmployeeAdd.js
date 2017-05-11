Ext.define('bsmes.view.EmployeeAdd', {
			extend : 'Oit.app.view.form.EditForm',
			alias : 'widget.employeeAdd',
			title : Oit.msg.bas.employee.addForm.title,
			iconCls : 'feed-add',//添加按钮的加号图片
			formItems : [{
						fieldLabel : Oit.msg.bas.employee.name + '<font color=red>*</font>',
						allowBlank : false,
						name : 'name'//列名数据值
					}, {
						fieldLabel : Oit.msg.bas.employee.userCode + '<font color=red>*</font>',
						allowBlank : false,
						name : 'userCode'
					}, {
						fieldLabel : Oit.msg.bas.employee.tel,
						name : 'telephone'
					}, {
						fieldLabel : Oit.msg.bas.employee.email,
						name : 'email'
					}, {
						fieldLabel : Oit.msg.bas.employee.orgCode + '<font color=red>*</font>',
						allowBlank : false,
						name : 'orgCode',
						xtype : 'combobox',
						displayField : 'name',
						valueField : 'orgCode',
						store : new Ext.data.Store({
									proxy : {
										type : 'rest',//与服务器进行交互
										url : 'employee/getOrgCode',
										reader : {
											type : 'json'
										}
									},
									fields : ['orgCode', 'name']
								}),
						listeners : {
							change : function(combo, newValue, oldValue, eOpts) {
								var form = this.up('form').getForm();
								form.findField('orgName').setValue(this.getDisplayValue());
							}
						}
					}, {
						xtype : 'hiddenfield',
						name : 'orgName'
					}, {
						fieldLabel : Oit.msg.bas.employee.certificate,
						name : 'certificate',
						xtype : 'combobox',
						displayField : 'name',
						valueField : 'code',
						store : new Ext.data.Store({
									proxy : {
										type : 'rest',
										url : 'employee/certificateCombo',
										reader : {
											type : 'json'
										}
									},
									fields : ['code', 'name']
								}),
						listeners : {
							change : function(combo, newValue, oldValue, eOpts) {
								var form = this.up('form').getForm();
								form.findField('certificateName').setValue(this.getDisplayValue());
							}
						}
					}, {
						xtype : 'hiddenfield',
						name : 'certificateName'
					}]
		});
