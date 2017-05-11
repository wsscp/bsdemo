// Employee
Ext.define('bsmes.model.EmployeeDemo', {
			extend : 'Ext.data.Model',
			fields : ['id', 'name', 'userCode', 'telephone', 'email', 'orgCode', 'orgName', 'topOrgCode', 'sources', {
						name : 'createTime',
						type : 'date'
					},'certificate','certificateName']
		});