Ext.define("bsmes.view.TodayMatPlanView", {
			extend : 'Ext.grid.Panel',
			alias : 'widget.todayMatPlanView',
			forceFit : true,
			store : 'TodayMatPlanStore',
			title : Oit.msg.wip.terminal.btn.toDayMatPlan,
			dataArray : null, // 加载数据对象
			columns : [{
						text : Oit.msg.wip.terminal.contractNo,
						dataIndex : 'contractNo',
						flex : 3,
						minWidth : 150,
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							return value;
						}
					}, {
						text : Oit.msg.wip.terminal.name,
						dataIndex : 'matName',
						flex : 3,
						minWidth : 150,
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							//value += '(' + record.get('matCode') + ')'
							return value;
						}
					}, {
						text : '物料信息',
						dataIndex : 'inAttrDesc',
						flex : 3,
						minWidth : 150,
						renderer : function(value, metaData, record) {
							var json = Ext.decode(value);
							var matDesc = '';
							if (record.get('color')) { // 宽度
								matDesc += '<font color="red">颜色:' + record.get('color') + '</font><br/>'
							}
							if (json.kuandu) { // 宽度
								matDesc += '宽度:' + json.kuandu + '<br/>'
							}
							if (json.houdu) { // 厚度
								matDesc += '厚度:' + json.houdu + '<br/>'
							}
							if (json.caizhi) { // 材质
								matDesc += '材质:' + json.caizhi + '<br/>'
							}
							if (json.chicun) { // 尺寸
								matDesc += '尺寸:' + json.chicun + '<br/>'
							}
							if (json.guige) { // 规格
								matDesc += '规格:' + json.guige + '<br/>'
							}
							if (json.dansizhijing) { // 单丝直径
								matDesc += '单丝直径:' + json.dansizhijing + '<br/>'
							}
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							return matDesc;
						}
					}, {
						text : Oit.msg.wip.terminal.planAmount,
						dataIndex : 'unFinishedLength',
						flex : 1.4,
						minWidth : 70,
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
							value = (value / 1000).toFixed(3) + ' ' + record.get('unit');
							return value;
						}
					}],
			initComponent : function() {
				var me = this;
				this.callParent(arguments);
				if (me.dataArray) {
					me.getStore().loadData(me.dataArray) // 加载数据
				}
			}
		});

//'matCode', 'matName', {
//	name : 'quantity',
//	type : 'double'
//}, {
//	name : 'unFinishedLength',
//	type : 'double'
//}, 'inAttrDesc', 'unit', '', 'contractNo'