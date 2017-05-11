/**
 * 可选工艺列表
 */
Ext.define('bsmes.view.SortAndCalculateSonGrid', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.sortAndCalculateSonGrid',
			store : 'HandScheduleStore',
			hasPaging : false,
			defaultEditingPlugin : false,
			columns : [{
						text : Oit.msg.pla.orderOA.productCode,
						dataIndex : 'salesOrderItem.productCode',
						flex : 1,
						sortable : false,
						menuDisabled : true
					}, {
						text : '型号规格',
						dataIndex : 'salesOrderItem.productType',
						flex : 1.4,
						renderer : function(value, metaData, record){
							return value + ' ' + record.get('salesOrderItem.productSpec');
						},
						sortable : false,
						menuDisabled : true
					}, {
						text : Oit.msg.pla.orderOA.orderLength,
						dataIndex : 'contractLength',
						flex : 0.5,
						sortable : false,
						menuDisabled : true
					}, {
						text : '当前分卷数',
						dataIndex : 'totalVolumes',
						flex : 0.5,
						sortable : false,
						menuDisabled : true
					}, {
						text : '当前工艺',
						dataIndex : 'craftsCname',
						flex : 1,
						renderer : function(value, cellmeta, record, rowIndex){
							var me = this;
							value = '<a style="color:blue;cursor:pointer;" onclick="getInstanceProcessGrid(\''
									+ record.get('craftsId') + '\')">' + value + '</a>'
							return value;
						},
						sortable : false,
						menuDisabled : true
					}]
		});
