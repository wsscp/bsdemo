Ext.define("bsmes.view.EmployeeList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.employeeList',
	store : 'EmployeeStore',
	columns : [ 
	/*{
		text : "ID",
		dataIndex : 'id'
	},*/{
		text : Oit.msg.bas.employee.name,
		dataIndex : 'name',
		editor : 'textfield'
	}, {
		text : Oit.msg.bas.employee.userCode,
		dataIndex : 'userCode'
	},{
		text : Oit.msg.bas.employee.tel,
		dataIndex : 'telephone',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.employee.email,
		dataIndex : 'email',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.employee.orgCode,
		dataIndex : 'orgName',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.employee.certificate,
		dataIndex : 'certificateName',
		editor : 'combobox'
	}],
	actioncolumn : [{
		itemId : 'detail'
    },'',{
    	itemId : 'edit'
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	} ],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			xtype : 'hform',
			items: [
			{
			    fieldLabel: Oit.msg.bas.employee.name,
			    name: 'name'
			},
			{
			    fieldLabel: Oit.msg.bas.employee.userCode,
			    name: 'userCode'
			},
			{
		        fieldLabel: Oit.msg.bas.employee.tel,
		        name: 'telephone'
		    },{
		        fieldLabel: Oit.msg.bas.employee.email,
		        name: 'email'
		    }]
		}, {
			itemId : 'search'
		} ]
	}]
});
