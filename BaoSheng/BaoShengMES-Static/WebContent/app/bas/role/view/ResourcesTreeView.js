Ext.define("bsmes.view.ResourcesTreeView", {
	extend : 'Ext.tree.Panel',
	xtype : 'check-tree',
	alias : 'widget.resourcesTreeView',
	store : 'ResourcesTreeStore',
	width : '100%',
	height : 400,
	displayField : 'name',
	rootVisible : false,
	multiSelect : true,
	// singleExpand: true,
	columns : [ {
		xtype : 'treecolumn',
		text : Oit.msg.bas.resources.name,
		dataIndex : 'name',
		sortable: false,
		width : 200
	}, {
		text : '查询',
		xtype: 'checkcolumn',
		dataIndex : 'roleQuery',
		sortable: false/*,
		listeners:{
			checkchange:function( cell, rowIndex, checked, eOpts ){
				console.log(cell);
            	console.log(cell.findParentByType("resourcesTreeView").getStore().getAt(rowIndex));
            	console.log(rowIndex);
            	console.log(checked);
			}
		}*/
	}, {
		text : '新建',
		xtype: 'checkcolumn',
		dataIndex : 'roleCreate',
		sortable: false
	}, {
		text : '删除',
		xtype: 'checkcolumn',
		dataIndex : 'roleDelete',
		sortable: false
	}, {
		text : '修改',
		xtype: 'checkcolumn',
		dataIndex : 'roleEdit',
		sortable: false
	}, {
		text : '高级',
		xtype: 'checkcolumn',
		dataIndex : 'roleAdvanced',
		sortable: false
	} ]
});
