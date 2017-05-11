Ext.define('bsmes.view.WorkOrderNoInfoGrid', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.workOrderNoInfoGrid',
			store : 'WorkOrderNoInfoStore',
			id : 'workOrderNoInfoGrid',
			forceFit : true,
			rowLines : true,
			columnLines : true,
			overflowY : 'auto',
			width : document.body.scrollWidth - 10,
			height : document.body.scrollHeight - 10,

			initComponent : function() {
				var me = this;
				me.columns = [{
							text : '合同号', dataIndex : 'CONTRACT_NO',flex : 2
						},{
							text : '工序',dataIndex : 'PROCESS_NAME',flex : 1
						},{
							text : '颜色',dataIndex : 'COLOR',flex : 1
						},{
							text : '投产长度',dataIndex : 'REPORT_LENGTH',flex : 1
						},{
							text : '下一道工序是否已下发',dataIndex : 'nextProcessWoNo',flex : 4
						}];
				
				me.dockedItems = [{
					xtype : 'toolbar',
					dock : 'top',
					items : {
						width : '100%',
						xtype : 'panel',
						layout : 'fit',
						buttonAlign : 'left',
						buttons : ['->',{
							xtype : 'button',
							text : '切换生产单',
							itemId : 'pause',
	
							}]
					}
				}];
				
				this.callParent(arguments);
			},
});