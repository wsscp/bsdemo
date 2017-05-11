/**
 * 选择工艺弹出层
 */
Ext.define('bsmes.view.ChooseCraftsWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.chooseCraftsWindow',
			title : '选择工艺',
			width : document.body.scrollWidth / 2,
			height : document.body.scrollHeight / 2,
			modal : true,
			plain : true,
			layout : 'fit',
			initComponent : function() {
				var me = this;

				me.items = [{
					xtype : 'grid',
					store : 'ChooseCraftsStore',
					selModel : {
						mode : "SINGLE", // "SINGLE"/"SIMPLE"/"MULTI"
						checkOnly : true, // 只能通过checkbox选择
						listeners : {
							'selectionchange' : function(sm, selections) {
							}
						}
					},
					selType : 'checkboxmodel',
					columns : [{
								text : '工艺名称',
								dataIndex : 'craftsCname',
								flex : 0.8,
								sortable : false,
								menuDisabled : true
							}, {
								text : '工艺名称',
								dataIndex : 'craftsName',
								flex : 1.5,
								sortable : false,
								menuDisabled : true
							}, {
								text : '工艺编码',
								dataIndex : 'craftsCode',
								flex : 1.5,
								sortable : false,
								menuDisabled : true
							}, {
								text : '创建时间',
								dataIndex : 'createTime',
								flex : 1.5,
								renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
								sortable : false,
								menuDisabled : true
							}, {
								text : '查看工序',
								dataIndex : 'id',
								flex : 0.8,
								hidden : true,
								renderer : function(value, cellmeta, record, rowIndex) {
									var me = this;
									var html = '<a style="color:blue;cursor:pointer;" onclick="getInstanceProcessGrid(\''
											+ record.get('id')
											+ '\',\''
											+ record.get('craftsCode')
											+ '\',\'\',false)">查看</a>';
									return html;
								},
								sortable : false,
								menuDisabled : true
							}]
				}];

				Ext.apply(me, {
							buttons : ['->', {
										itemId : 'ok',
										text : Oit.btn.ok
									}, {
										itemId : 'cancel',
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});

				this.callParent(arguments);
			}
		});
