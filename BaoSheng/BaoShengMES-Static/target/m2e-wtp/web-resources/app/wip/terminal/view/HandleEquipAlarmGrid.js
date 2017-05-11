Ext.define('bsmes.view.HandleEquipAlarmGrid', {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.handleEquipAlarmGrid',
	store : 'EquipAlarmListStore',
	height : document.body.scrollHeight - 140,
	defaultEditingPlugin : false,
	columns : [{
				text : Oit.msg.wip.terminal.eventTitle,
				dataIndex : 'eventTitle',
				flex : 1
			}, {
				text : Oit.msg.wip.terminal.eventContent,
				dataIndex : 'eventContent',
				flex : 3
			}, {
				text : Oit.msg.wip.terminal.eventName,
				dataIndex : 'name',
				flex : 1
			}, {
				text : Oit.msg.wip.terminal.createTime,
				dataIndex : 'createTime',
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
				flex : 1.7
			},
			/*
			 * { text : '事件处理方式', dataIndex : 'processType' },
			 */
			{
				text : Oit.msg.wip.terminal.eventStatus,
				dataIndex : 'eventStatus',
				renderer : function(value){
					if (value == 'UNCOMPLETED') {
						return "未完成";
					} else if (value == 'RESPONDED') {
						return "已响应";
					} else if (value == 'PENDING') {
						return "待确认";
					} else if (value == 'COMPLETED') {
						return "已完成";
					}else{
						return ''
					}
				},
				flex : 0.85
			}],
	dockedItems : [{
		xtype : 'container',
		margin : '10',
		layout : 'hbox',
		items : [{
					xtype : 'hform',
					items : [ {
								xtype : 'hiddenfield',
								name : 'equipCode'
							}, {
								id : 'eventStatusComb',
								name : 'eventStatusFindParam',
								xtype : 'checkboxgroup',
								// columns: 4, // 一行显示几个
								layout : { // 设置自动间距样式为false
									autoFlex : false
								},
								width : 450,
								defaults : { // 设置样式
									margin : '5 20 0 0'
									// boxLabelCls: 80 // 设置label的宽度
								},
								items : [{
											boxLabel : '未完成',
											boxLabelCls : 'x-boxlabel-size-20',
											name : 'eventStatusFindParam',
											inputValue : 'UNCOMPLETED'
										}, {
											boxLabel : '已响应',
											boxLabelCls : 'x-boxlabel-size-20',
											name : 'eventStatusFindParam',
											inputValue : 'RESPONDED'
										},{
											boxLabel : '待确认',
											boxLabelCls : 'x-boxlabel-size-20',
											name : 'eventStatusFindParam',
											inputValue : 'PENDING'
										}, {
											boxLabel : '已完成',
											boxLabelCls : 'x-boxlabel-size-20',
											name : 'eventStatusFindParam',
											inputValue : 'COMPLETED'
										}, {
											boxLabel : '全部',
											name : 'eventStatusFindParam',
											inputValue : '',
											hidden : true
										}],
										setValue: function (val) {
							                if (val.split) {
							                    val = val.split(',');
							                }
							                this.reset();
							                for (var i = 0; i < val.length; i++) {
							                    this.items.each(function (c) {
							                        if (c.inputValue == val[i]) {

							                            c.setValue(true);
							                        }
							                    });
							                }
							            },
							            reset: function () {
							                this.items.each(function (c) {
							                    c.setValue(false);
							                });
							            },
								listeners : {
									change : function(obj, newValue, oldValue,
											eOpts){
										var me = this;
										var store = me.up('grid').getStore();
										store.loadPage(1, {
													params : {
														eventStatusFindParam : newValue
													}
												}
										);
									}
								}
							}]
				}, {
					itemId : 'handleAlarm',
					xtype : 'button',
					text : '处理警报',
					margin : '0 10 0 10'
				}, {
					itemId : 'finishRepair',
					xtype : 'button',
					text : '维修完成',
					margin : '0 10 0 10'
				}, {
					text : Oit.btn.close,
					xtype : 'button',
					margin : '0 10 0 10',
					handler : function(){
						var me = this;
						me.up('window').close();
					}
				}

		]

	}],
	listeners : {
		'itemdblclick' : function(grid, record, item, index, e, eOpts){
			var win = Ext.create('bsmes.view.HandleEquipAlarmDetailWindow')
			win.show();
			win.down('form').loadRecord(record);
		},
		'beforerender' : function(){
			// 修改pagingtoolbar会越加越多的bug
			var me = this;
			var flg = false;
			if (me.dockedItems) {
				Ext.each(me.dockedItems.items, function(item){
							if (item.xtype == 'pagingtoolbar') {
								if (flg) {
									// item.destroy()方法无法完全销毁对象,暂时用hide()方法代替
									item.hide();
								}
								flg = true;
							}
						});
			}
			return true;
		}
	}
}
);
