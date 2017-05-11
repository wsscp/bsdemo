/**
 * 查看生产单进度
 */
Ext.define('bsmes.view.LookUpProgressWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.lookUpProgressWindow',
			title : '查看完成进度',
			width : document.body.scrollWidth / 2,
			height : document.body.scrollHeight / 1.5,
			padding : '5',
			autoScroll : true,
			modal : true,
			plain : true,
			gridDataArray : null,
			initComponent : function() {
				var me = this;
				me.loadGridView(me);
				Ext.apply(me, {
							buttons : ['->', {
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});
				this.callParent(arguments);
			},

			loadGridView : function(me) {
				var items = [];

				var data = me.gridDataArray;
				var groupMap = {};
				var orderMenu = [];
				if (data.length == 0) {
					Ext.MessageBox.alert(Oit.msg.WARN, "未下发,无法生成数据.");
					me.hide();
				}
				Ext.Array.each(data, function(record, i) {
							groupMap[record.workOrderNo] = {
								'gridData' : record.data,
								'docMakerUserCode' : record.docMakerUserCode,
								'receiverUserCode' : record.receiverUserCode,
								'releaseDate' : record.releaseDate,
								'requireFinishDate' : record.requireFinishDate
							};
						});

				for (var key in groupMap) {
					var processGrid = me.getGrid();
					processGrid.getStore().loadData(groupMap[key].gridData, false);
					var orderfieldset = Ext.create('Ext.form.FieldSet', {
								title : key,
								padding : '0 10 10 10',
								items : [{
											xtype : 'panel',
											layout : 'hbox',
											bodyStyle : 'background: #DFEAF2;',
											defaults : { // 设置field的样式
												labelStyle : 'font-weight: 700;'
											},
											bodyPadding : '5 5 5 0',
											items : [{
														xtype : 'displayfield',
														fieldLabel : '下发人',
														value : groupMap[key].docMakerUserCode,
														labelWidth : 50,
														padding : '0 3 0 0',
														labelAlign : 'right'
													}, {
														xtype : 'displayfield',
														fieldLabel : '收货人',
														value : groupMap[key].receiverUserCode,
														padding : '0 3 0 3',
														labelAlign : 'right',
														labelWidth : 70
													}, {
														xtype : 'displayfield',
														fieldLabel : '发布日期',
														value : groupMap[key].releaseDate,
														padding : '0 3 0 3',
														labelAlign : 'right',
														labelWidth : 70
													}, {
														xtype : 'displayfield',
														fieldLabel : '交货日期',
														value : groupMap[key].requireFinishDate,
														padding : '0 3 0 3',
														labelAlign : 'right',
														labelWidth : 70
													}]
										}, processGrid]
							});
					items.push(orderfieldset);
				}
				me.items = items;
			},

			// 获取grid表格
			getGrid : function() {
				return Ext.create('Ext.grid.Panel', {
							store : Ext.create('Ext.data.Store', {
										fields : ['wireColor', {
													name : 'taskLength',
													type : 'double'
												}, 'finishedLength', 'finishedPercent', 'unFinishedLength']
									}),
							columnLines : true,
							selModel : {
								mode : "SINGLE"
							},
							columns : [{
										text : '颜色',
										dataIndex : 'wireColor',
										flex : 1.5,
										sortable : false,
										menuDisabled : true
									}, {
										text : '任务长度',
										dataIndex : 'taskLength',
										flex : 1,
										sortable : false,
										menuDisabled : true
									}, {
										text : '已完成长度',
										dataIndex : 'finishedLength',
										flex : 1,
										sortable : false,
										menuDisabled : true
									}, {
										text : '已完成百分比',
										dataIndex : 'finishedPercent',
										flex : 1,
										sortable : false,
										menuDisabled : true
									}, {
										text : '未完成长度',
										dataIndex : 'unFinishedLength',
										flex : 1,
										sortable : false,
										menuDisabled : true
									}]
						});
			}

		});
