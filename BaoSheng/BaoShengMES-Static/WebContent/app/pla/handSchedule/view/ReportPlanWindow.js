/**
 * 选择订单产品
 */
Ext.define('bsmes.view.ReportPlanWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.reportPlanWindow',
			title : '报计划',
			width : 600,
			height : 370,
			modal : true,
			plain : true,
			overflowY : 'auto',
			padding : '5',
			dataArray : [],
			initComponent : function() {
				var me = this;

				var totalLenght = 0, totalStr = [], indexArray = [], idArray = [];
				Ext.Array.each(me.dataArray, function(record, i) {
							idArray.push(record.ID);
							indexArray.push(record.INDEX);
							totalStr.push(record.NUMBEROFWIRES + '*' + record.ORDERLENGTH);
							totalLenght += Number(record.NUMBEROFWIRES) * Number(record.ORDERLENGTH);
						});

				me.items = [{
							xtype : 'panel',
							html : '计划总长度：' + totalStr.join('+') + '=' + totalLenght
						}];
				Ext.apply(me, {
							buttons : ['->', {
										itemId : 'ok',
										text : Oit.btn.ok,
										handler : function() {
											// 更新订单特殊状态 : 1:报计划
											me.updateSpecialFlag('确认报计划？', idArray.join(), '1', false);
										}
									}, {
										itemId : 'cancel',
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});
				this.callParent(arguments);
			},

			// private : 更新订单特殊状态: [0:厂外计划;1:计划已报;2:手工单;3:库存生产]
			// @param title 提示标题
			// @param ids 订单id
			// @param specialFlag 状态值
			// @param finished 是否完成订单状态
			updateSpecialFlag : function(title, ids, specialFlag, finished) {
				var me = this;
				Ext.MessageBox.confirm('确认', title, function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
											url : 'handSchedule/updateSpecialFlag',
											params : {
												ids : ids,
												specialFlag : specialFlag,
												finished : finished ? true : false
											},
											success : function(response) {
												Ext.Msg.alert(Oit.msg.PROMPT, '操作成功');
												var grid = Ext.ComponentQuery.query('handScheduleGrid')[0];
												grid.getStore().reload();
											}
										});
							}
						});
			}

		});
