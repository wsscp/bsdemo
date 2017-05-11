Ext.define("bsmes.view.EmployeeDemoList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.employeeDemoList',
	store : 'EmployeeDemoStore',
	
	
	columns : [ 
	/*{
		text : "ID",
		dataIndex : 'id'
	},*/{
		text : Oit.msg.bas.employee.name,//表头列名
		dataIndex : 'name',//表列数据
		editor : 'textfield'//表类型
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
	
	
	
//	actioncolumn : [{
//		itemId : 'detail'
//    },'',{
//    	itemId : 'edit'
//    }],
//    
//    
//    /**
//     * 添加删除按钮
//     */
//	tbar : [ {
//		itemId : 'add'
//	},{
//		itemId : 'remove'
//	} ],
//	
//	
//	dockedItems : [{//相当于工具条，员工管理顶部工具栏
//		xtype : 'toolbar',
//		dock : 'top',
//		items : [ {
//			xtype : 'hform',
//			items: [
//			{
//				    fieldLabel : Oit.msg.bas.employee.name,
//			    name: 'name'//name列数据
//			},
//			{
//			    fieldLabel: Oit.msg.bas.employee.userCode,
//			    name: 'userCode'
//			},
//			{
//		        fieldLabel: Oit.msg.bas.employee.tel,
//		        name: 'telephone'
//		    },{
//		        fieldLabel: Oit.msg.bas.employee.email,
//		        name: 'email'
//		    }]
//		}, {
//			itemId : 'search'//查询按钮
//		} ]
//	}]
});
