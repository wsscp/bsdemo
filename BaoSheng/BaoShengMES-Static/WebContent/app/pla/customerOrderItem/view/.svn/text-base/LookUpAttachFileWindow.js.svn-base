
Ext.define('bsmes.view.LookUpAttachFileWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.lookUpAttachFileWindow',
	title : '附件查看',
	width : document.body.scrollWidth / 2,
	height : document.body.scrollHeight / 1.5,
	padding : '5',
	autoScroll : true,
	modal : true,
	plain : true,
//	layout : 'hbox',
	gridDataMap : {}, // 每个合同下的附件组
	initComponent : function(){
		var me = this;
		me.loadGridView(me);
		Ext.apply(me, {
			buttons : ['->', {
						text : Oit.btn.cancel,
						scope : me,
						handler : me.close
					}]
		});
		this.callParent(arguments);
	},
	
	// 初始化页面
	loadGridView : function(me){
		var items = [];
		for (var key in me.gridDataMap) {
			var gridData = me.gridDataMap[key]
			var grid = me.getGrid();
			grid.getStore().loadData(gridData, false);
			var attachfieldset = Ext.create('Ext.form.FieldSet', {
				title : key,
				items : [grid]
			});
			items.push(attachfieldset);
		}
		me.items = items;
	},
	
	// 获取grid表格
	getGrid : function(){
		return Ext.create('Ext.grid.Panel', {
				store : Ext.create('Ext.data.Store', {
					fields : ['id','realFileName']
				}),
				columnLines : true,
				selModel : {
					mode : "SINGLE"
				},
				columns : [{
					text : '附件文件名',
					dataIndex : 'realFileName',
					flex : 1,
					sortable : false,
					menuDisabled : true
				},{
					text : '查看',
					flex : 0.5,
					renderer : function(value, cellmeta, record, rowIndex) {
						var me = this;
						var html = '<a style="color:blue;cursor:pointer;" target="_blank" href="newHandSchedule/openAttachFile?id='+record.get('id')+'">查看</a>';
						return html;
					},
					sortable : false,
					menuDisabled : true
				}]
		});
	}
});
