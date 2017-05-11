
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
	workOrderNo : null,
	initComponent : function(){
		var me = this;
//		//如果me.workOrderNo == 'TECHNIQUENUM'，表示查看内部附件,则上传人显示
//		var isShow = true;
//		if(me.workOrderNo == 'TECHNIQUENUM'){
//			isShow =false;
//		}
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
			var attachGrid = me.getAttachGrid(me);
			attachGrid.getStore().loadData(gridData, false);
			var attachfieldset = Ext.create('Ext.form.FieldSet', {
				title : key,
				items : [attachGrid]
			});
			items.push(attachfieldset);
		}
		me.items = items;
	},
	
	// 获取grid表格
	getAttachGrid : function(me){
		return Ext.create('Ext.grid.Panel', {
				store : Ext.create('Ext.data.Store', {
					fields : ['id','realFileName',{name:'createTime',type : 'date'},'userName']
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
						var html = '<a style="color:blue;cursor:pointer;" target="_blank" href="handSchedule/openAttachFile?id='+record.get('id')+'">查看</a>';
						return html;
					},
					sortable : false,
					menuDisabled : true
				},{
					text : '上传时间',
					dataIndex : 'createTime',
					flex : 0.5,
					sortable : false,
					menuDisabled : true,
					renderer: Ext.util.Format.dateRenderer('Y-m-d')
				},{
					text : '上传人',
					dataIndex : 'userName',
					flex : 0.5,
					sortable : false,
					menuDisabled : true,
					hidden : me.workOrderNo == 'TECHNIQUENUM' ? false : true
				}]
		});
	}
});
