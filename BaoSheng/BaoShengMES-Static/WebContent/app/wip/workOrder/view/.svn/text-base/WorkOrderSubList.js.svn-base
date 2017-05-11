Ext.define("bsmes.view.WorkOrderSubList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.workOrderSubList',
			store : 'WorkOrderSubStore',
			// collapsible : true,
			animCollapse : true,
			columnLines : true,
			autoWidth : true,
			autoHeight : true,
			defaultEditingPlugin : false,
//			selType : 'checkboxmodel',
			viewConfig : {
				stripeRows : false,
				getRowClass : function(record, rowIndex, rowParams, store){
					var resultColor = '';
					if (record.get("isDelayed") == true) {
						resultColor = 'x-grid-record-yellow';
					}
					return resultColor;

				}
			},
			forceFit : false,
			columns : [{
						text : Oit.msg.wip.workOrder.customerContractNO,
						dataIndex : 'contractNo',
						width : 150
					}, {
						text : Oit.msg.wip.workOrder.productType,
						dataIndex : 'productType',
						width : 200
					}, {
						text : Oit.msg.wip.workOrder.productSpec,
						dataIndex : 'productSpec',
						width : 150
					}, {
						text : '截面',
						dataIndex : 'section',
						width : 150
					}, {
						text : '线芯结构',
						dataIndex : 'wiresStructure',
						width : 150
					}, {
						text : '半成品编码',
						dataIndex : 'halfProductCode',
						width : 150
					}, {
						text : '颜色',
						dataIndex : 'color',
						width : 150
					}, {
						text : Oit.msg.wip.workOrder.orderLength,
						dataIndex : 'taskLength'
					}, {
						text : Oit.msg.wip.workOrder.reportNum,
						dataIndex : 'reportNum'
					}, {
						text : Oit.msg.wip.workOrder.preStartTime,
						dataIndex : 'planStartDate',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s',
						width : 150
					}, {
						text : Oit.msg.wip.workOrder.preEndTime,
						dataIndex : 'planFinishDate',
						xtype : 'datecolumn',
						format : 'Y-m-d H:i:s',
						width : 150
					}, {
						text : Oit.msg.wip.workOrder.status,
						dataIndex : 'status',
						renderer : function(value){
							switch (value) {
								case 'TO_AUDIT' :
									return '待下发';
								case 'TO_DO' :
									return '已下发';
								case 'IN_PROGRESS' :
									return '生产中';
								case 'CANCELED' :
									return '已取消';
								case 'FINISHED' :
									return '已完成';
								default :
									return '';
							}
						},
						width : 100
					}, {
						text : Oit.msg.wip.workOrder.percent,
						dataIndex : 'percent',
						renderer : function(value){
							return value + '%';
						},
						width : 150
					}]
		});
