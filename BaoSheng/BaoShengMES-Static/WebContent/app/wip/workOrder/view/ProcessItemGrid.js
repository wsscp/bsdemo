/**
 * 工序选择生成生产单
 */
Ext.define('bsmes.view.ProcessItemGrid', {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.processItemGrid',
	hasPaging : false,
	defaultEditingPlugin : false,
	width : Math.floor((document.body.scrollWidth - 172) / 5 * 2 -20),
	// height : document.body.scrollHeight - 355,
	store : Ext.create('Ext.data.Store', {
				fields : ['color', {
							name : 'unFinishedLength',
							type : 'double'
						}, 'quantity', 'standardPly', 'standardMaxPly', 'standardMinPly', 'processIdArray']
			}),
	columns : [{
				text : '颜色',
				dataIndex : 'color',
				flex : 0.8,
				sortable : false,
				menuDisabled : true
			}, {
				text : '长度',
				dataIndex : 'unFinishedLength',
				flex : 1.2,
				sortable : false,
				menuDisabled : true
			}, {
				text : '标称厚度',
				dataIndex : 'propTargetValue',
				flex : 1.2,
				sortable : false,
				menuDisabled : true
			}, {
				text : '最大值',
				dataIndex : 'propMaxValue',
				flex : 1.2,
				sortable : false,
				menuDisabled : true
			}, {
				text : '最小值',
				dataIndex : 'propMinValue',
				flex : 1.2,
				sortable : false,
				menuDisabled : true
			}, {
				text : '外径',
				dataIndex : 'disk',
				flex : 1.2,
				sortable : false,
				menuDisabled : true
			}],
	listeners : {
		cellclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts){
			var processCode = Ext.ComponentQuery.query('#chooseProcess')[0].getValue();
			if (processCode == 'Respool') { // 如果是火花，按颜色分类
				return;
			}
			var processWorkOrderGrid = Ext.ComponentQuery.query('processWorkOrderGrid')[0];
			var result = processWorkOrderGrid.result;
			var matOaData = [], matOaDataMap = {};
			// 点击重新渲染物料grid
			var selection = grid.getSelectionModel().getSelection();
			if (selection && selection.length > 0) { // 获取选择项中所有的工序ID
				Ext.Array.each(selection, function(record, i){
					var processIdArray = record.getData().processIdArray;
					Ext.Array.each(processIdArray, function(processId, j){
								Ext.Array.each(result, function(item, n){
											var tmpRecord = Ext.clone(item);
											if (item.inOrOut == 'IN' && processId == (item.processId+item.orderItemId)) { // 物料需求
												var tmp = matOaDataMap[item.halfProductCode];
												if (tmp) {
													tmp.unFinishedLength = tmp.unFinishedLength + item.unFinishedLength;
												} else {
													matOaDataMap[item.halfProductCode] = tmpRecord;
												}
											}
										}
								);
							}
					);
				}
				);
			}

			for (var key in matOaDataMap) {
				matOaData.push(matOaDataMap[key]);
			}
			Ext.ComponentQuery.query('matOAGrid')[0].getStore().loadData(matOaData, false);
		},
		blur : function(grid, The, eOpts){
			var processCode = Ext.ComponentQuery.query('#chooseProcess')[0].getValue();
			if (processCode == 'Respool') { // 如果是火花，按颜色分类
				return;
			}
			var result = processWorkOrderGrid.result;
			var matOaData = [], matOaDataMap = {};
			Ext.Array.each(result, function(item, n){
						var tmpRecord = Ext.clone(item);
						if (item.inOrOut == 'IN' && processId == item.processId) { // 物料需求
							var tmp = matOaDataMap[item.halfProductCode];
							if (tmp) {
								tmp.unFinishedLength = tmp.unFinishedLength + item.unFinishedLength;
							} else {
								matOaDataMap[item.halfProductCode] = tmpRecord;
							}
						}

					});
			for (var key in matOaDataMap) {
				matOaData.push(matOaDataMap[key]);
			}
			Ext.ComponentQuery.query('matOAGrid')[0].getStore().loadData(matOaData, false);
		}
	}
}
);