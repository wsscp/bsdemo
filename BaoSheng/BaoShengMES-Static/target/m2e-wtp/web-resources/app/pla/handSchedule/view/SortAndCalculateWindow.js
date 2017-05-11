/**
 * 选择工艺弹出层
 */
Ext.define('bsmes.view.SortAndCalculateWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.sortAndCalculateWindow',
			title : '订单排序并计算排程',
			width : 960,
			height : 500,
			modal : true,
			plain : true,
			overflowY : 'auto',
			requires : ['bsmes.view.SortAndCalculateGrid'],

			initComponent : function(){
				var me = this;

				me.items = [{
							xtype : 'panel',
							bodyPadding : "12 10 10",
							items : [{
										xtype : 'fieldset',
										title : '可排序订单列表',
										anchor : '100%',
										items : [{
													xtype : 'sortAndCalculateGrid'
												}]
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
