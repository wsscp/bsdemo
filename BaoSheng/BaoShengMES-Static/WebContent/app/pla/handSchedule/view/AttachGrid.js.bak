Ext.define('bsmes.view.AttachGrid', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.attachGrid',
			hasPaging : false,
			columnLines : true,
			defaultEditingPlugin : false,
			selModel : {
				mode : "SINGLE"
			},
			columns : [{
						text : '附件',
						dataIndex : 'fileName',
						flex : 1.5,
						sortable : false,
						menuDisabled : true
					},{
						text : '创建时间',
						dataIndex : 'createTime',
						flex : 1,
						sortable : false,
						menuDisabled : true,
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
					},{
						text : '查看',
						flex : 0.5,
						renderer : function(value, cellmeta, record, rowIndex) {
							var me = this;
							var html = '<a style="color:blue;cursor:pointer;" target="view_window" href="handSchedule/openAttachFile?id='+record.get('id')+'">查看</a>';
							return html;
						},
						sortable : false,
						menuDisabled : true
					}]
		});

function openAttachFile(value){
	
}