Ext.define("bsmes.view.AlarmHistoryGrid", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.alarmHistoryGrid',
	store : 'AlarmHistoryStore',
	columns : [ 
	    { flex: 1,  text: '设备', sortable: false, dataIndex: 'equipCode' }, 
	    { flex: 1,  text: '参数名称', sortable: false, dataIndex: 'tagNameDec' }, 
		{ flex: 1,  text: '对象名称', sortable: false, dataIndex: 'tagName' }, 
		{ flex: 1,  text: '描述', sortable: false, dataIndex: 'description' }, 
		{ flex: 1,  text: '区域/组', sortable: false, dataIndex: 'area' }, 
		{ flex: 1,  text: '类型', sortable: false, dataIndex: 'type' }, 
		{ flex: 1,  text: '变量值', sortable: false, dataIndex: 'value' }, 
		{ flex: 1,  text: '限值', sortable: false, dataIndex: 'checkValue' }, 
		{ flex: 1, text: '事件日期', sortable: false, dataIndex: 'eventStampUTC', 
			renderer : function (val) {
			    return Ext.util.Format.date(new Date(parseInt(val)), 'Y-m-d H:i:s');
			} 
		}
	],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
				fieldLabel : '对象名称',
            	name: 'tagName',
				xtype : 'textfield'
		    }]
		}, {
			itemId : 'search'
		}]
	} ]
	
    
});