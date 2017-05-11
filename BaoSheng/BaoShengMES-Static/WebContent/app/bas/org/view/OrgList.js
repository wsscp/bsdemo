Ext.define("bsmes.view.OrgList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.orglist',
	store : 'OrgStore',
	
//	表头列名
	columns : [{
		text : Oit.msg.bas.org.orgCode,
		dataIndex : 'orgCode',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.org.name,
		dataIndex : 'name',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.org.parentCode,
		dataIndex : 'parentCode',
		editor : 'textfield'
	},{
		text :Oit.msg.bas.org.type,
		dataIndex : 'type',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.org.description,
		dataIndex : 'description',
		editor : 'textfield'
	},{
		text : 'demo',
		dataIndex : 'demo',
		editor : 'textfield'
	}],
	
//	详细和编辑，为表最前面的图标
	actioncolumn : [{
		itemId : 'detail'
    },'',{
    	itemId : 'edit'
    }],
    
//    添加删除按钮
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	} ],
	
//	查询组合件，首行
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		
		items : [ {
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.org.orgCode,
		        name: 'orgCode'
		    },
		    {
		        fieldLabel: Oit.msg.bas.org.name,
		        name: 'name'
		    },{
		        fieldLabel: 'demo',
		        name: 'demo'
		    }]
		
		
		}, {
			itemId : 'search'
		}]
	
	}]


});
