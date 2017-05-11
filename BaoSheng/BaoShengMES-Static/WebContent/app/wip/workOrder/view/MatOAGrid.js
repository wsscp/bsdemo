/**
 * 工序选择生成生产单
 */
Ext.define('bsmes.view.MatOAGrid', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.matOAGrid',
			hasPaging : false,
			defaultEditingPlugin : false,
			width : Math.floor((document.body.scrollWidth - 172) / 5 * 3 - 20),
			selModel : {
				mode : "SIMPLE" // "SINGLE"/"SIMPLE"/"MULTI"
			},
			store : Ext.create('Ext.data.Store', {
						fields : ['matName', 'color', 'unit', {
									name : 'quantity',
									type : 'double'
								}, {
									name : 'unFinishedLength',
									type : 'double'
								}, 'disk']
					}),
			columns : [{
						text : '物料名称',
						dataIndex : 'matName',
						flex : 1.2,
						sortable : false,
						menuDisabled : true
					}, {
						text : '颜色',
						dataIndex : 'color',
						flex : 0.8,
						sortable : false,
						menuDisabled : true
					}, {
						text : '单位用量',
						dataIndex : 'quantity',
						flex : 0.5,
						sortable : false,
						menuDisabled : true,
						renderer : function(value, metaData, record){
							return value + ' ' + record.get('unit');
						}
					}, {
						text : '总用量',
						dataIndex : 'unFinishedLength',
						flex : 0.8,
						sortable : false,
						menuDisabled : true,
						renderer : function(value, metaData, record){
							return Math.round(value * record.get('quantity')/1000) + ' ' + record.get('unit');
						}
					}, {
						text : '库位',
						dataIndex : 'disk',
						flex : 1.2,
						sortable : false,
						menuDisabled : true
					}]
		});