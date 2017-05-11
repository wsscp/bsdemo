/**
 * 
 */
Ext.define("bsmes.view.SummarizeGrid", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.summarizeGrid',
			store : 'SummarizeStore',
			defaultEditingPlugin : false,
			hasPaging:false,
			columns : [{
						text : '物料名称',
						dataIndex : 'matName',
						flex : 2
					}, {
						text : '物料颜色',
						flex : 2,
						dataIndex : 'color'
					}, {
						text : '投入用量',
						flex : 1,
						dataIndex : 'weight'
					}],
});