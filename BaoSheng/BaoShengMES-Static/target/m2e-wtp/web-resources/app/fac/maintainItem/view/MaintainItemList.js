Ext.define('bsmes.view.MaintainItemList',{
	extend : 'Oit.app.view.Grid',
	alias : 'widget.maintainItemList',
	store : 'MaintainItemStore',
	columns : [{
        text : Oit.msg.fac.maintainItem.describe,
        dataIndex : 'describe',
        filter : {
            type : 'string'
        }
    } ,{
		text : Oit.msg.modifyUserCode,
		dataIndex : 'modifyUserCode',
		filter : {
			type : 'string'
		}
	} ,{
		text : Oit.msg.modifyTime,
		dataIndex : 'modifyTime',
		xtype : 'datecolumn',
		filter : {
			type : 'date'
		}
	} ,{
		text : Oit.msg.createTime,
		dataIndex : 'createTime',
		xtype : 'datecolumn',
		filter : {
			type : 'date'
		}
	} ,{
		text : Oit.msg.createUserCode,
		dataIndex : 'createUserCode',
		filter : {
			type : 'string'
		}
	} ],
	actioncolumn : [{
		itemId : 'edit'
	}],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	}]
});