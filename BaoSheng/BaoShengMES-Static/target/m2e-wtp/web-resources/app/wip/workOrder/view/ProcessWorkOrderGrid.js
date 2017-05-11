/**
 * 工序选择生成生产单
 */
var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
	pluginId : 'rowEditing',
	saveBtnText : '保存',
	cancelBtnText : "取消",
	autoCancel : false,
	clicksToEdit : 2,
	listeners : {
		edit : function(row, edit, fun){
			var record = edit.record;
			if (record.get('roll') > Math.ceil(record.get('orderLength') / record.get('standardLength'))) {
				console.log('最大只能为' + Math.ceil(record.get('orderLength') / record.get('standardLength')));
				record.set('roll', Math.ceil(record.get('orderLength') / record.get('standardLength')));
				return false;
			}

			var me = this;
			var grid = Ext.ComponentQuery.query('processWorkOrderGrid')[0];
			var result = grid.result;
			var store = grid.getStore();

			// 遍历产品列表获取长度
			for (var i = 0; i < store.getCount(); i++) {
				var orderRecord = store.getAt(i);
				if (record.get('id') != orderRecord.get('id')) {
					continue;
				}
				Ext.Array.each(result, function(record, i){ // 遍历工序列表变更长度
							if (orderRecord.getData().id == record.orderItemId) {
								record.unFinishedLength = Math.round((orderRecord.getData().roll * orderRecord
										.getData().standardLength));
							}
						}
				);
			}

			// 重新渲染两个grid
			// data存放工序列表record，matOaData存放物料需求列表record，Map为临时缓存
			var date = [], dateMap = {}, matOaData = [], matOaDataMap = {};
			var processCode = Ext.ComponentQuery.query('#chooseProcess')[0].getValue();

			Ext.Array.each(result, function(record, i){
						var tmpRecord = Ext.clone(record); // 存放新对象，防止提交对象的变更
						if (record.inOrOut == 'IN') { // 物料需求
							var item = matOaDataMap[record.halfProductCode];
							if (item) {
								item.unFinishedLength = item.unFinishedLength + record.unFinishedLength;
							} else {
								matOaDataMap[record.halfProductCode] = tmpRecord;
							}
						} else if (record.inOrOut == 'OUT') { // 
							var item = dateMap[record.color];
							if (item) {
								item.unFinishedLength = item.unFinishedLength + record.unFinishedLength;
							} else {
								dateMap[record.color] = tmpRecord;
							}
						}
					});
			for (var key in dateMap) {
				date.push(dateMap[key])
			}
			for (var key in matOaDataMap) {
				matOaData.push(matOaDataMap[key])
			}

			Ext.ComponentQuery.query('#processItemGrid')[0].getStore().loadData(date, false);

			Ext.ComponentQuery.query('matOAGrid')[0].getStore().loadData(matOaData, false);

		}
	}
		// 双击进行修改:1-单击;2-双击;0-可取消双击/单击事件
	}
);

Ext.define('bsmes.view.ProcessWorkOrderGrid', {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.processWorkOrderGrid',
	store : Ext.create('Ext.data.Store', {
				fields : ['id', 'contractNo', 'productType', 'conductorStruct', {
							name : 'contractLength',
							type : 'double'
						}, {
							name : 'orderLength',
							type : 'double'
						}, {
							name : 'roll',
							type : 'double'
						}, {
							name : 'standardLength',
							type : 'double'
						}]
			}),
	hasPaging : false,
	defaultEditingPlugin : false,
	selModel : {
		mode : "SIMPLE", // "SINGLE"/"SIMPLE"/"MULTI"
		listeners : {
			beforerowselect : function(sm, rowIndex){ // /当管理员查看管理员用户及自己时，不可以对角色选择
				console
						.log('beforerowselectbeforerowselectbeforerowselectbeforerowselectbeforerowselectbeforerowselectbeforerowselectbeforerowselect');
				return false
			}
		}
	},
	plugins : [rowEditing], // 定义使用插件
	width : Math.floor((document.body.scrollWidth - 172) / 5 * 3 - 20),
	minHeight : 100,
	allowDeselect : true,
	columns : [{
				text : '序号',
				// flex : 0.3,
				width : 45,
				xtype : 'rownumberer'
			}, {
				text : '合同号',
				dataIndex : 'contractNo',
				// flex : 0.8,
				width : 110,
				sortable : false,
				menuDisabled : true
			}, {
				text : '型号规格',
				dataIndex : 'productType',
				flex : 1.2,
				sortable : false,
				menuDisabled : true
			}, {
				text : '合同长度',
				dataIndex : 'contractLength',
				flex : 0.6,
				sortable : false,
				menuDisabled : true
			}, {
				text : '投产长度',
				dataIndex : 'orderLength',
				flex : 0.6,
				sortable : false,
				menuDisabled : true
			}, {
				text : '实际投产长度',
				dataIndex : 'roll',
				flex : 0.6,
				sortable : false,
				menuDisabled : true,
				renderer : function(value, metaData, record){
					return value + '*' + record.get('standardLength');
				},
				editor : new Ext.form.NumberField({
							decimalPrecision : 2,// 精确到小数点后两位
							allowDecimals : true,
							name : 'roll',
							minValue : 1
						})
			}, {
				text : '导体结构',
				dataIndex : 'conductorStruct',
				flex : 0.6,
				sortable : false,
				menuDisabled : true
			}]
}
);
