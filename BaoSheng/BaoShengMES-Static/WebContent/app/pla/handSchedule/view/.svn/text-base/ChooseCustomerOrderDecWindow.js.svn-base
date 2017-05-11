/**
 * 选择订单产品
 */
Ext.define('bsmes.view.ChooseCustomerOrderDecWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.chooseCustomerOrderDecWindow',
			title : '选择订单',
			width : 600,
			height : 370,
			modal : true,
			plain : true,
			overflowY : 'auto',
			padding : '5',
			preWorkOrderNo : null,
			orderItemIdProcessArray : null,
			orderIdProductTypeMap : null,
			orderData : null,
			workOrderInfo : null,

			usedProcessObj : null, // 已经下发了的工序情况
			allProcessObj : null, // 所有相关的产品的工序情况

			initComponent : function() {
				var me = this;

				if (me.orderData && me.orderData.length > 0) {
					me.items = [{
						xtype : 'grid',
						selType : 'checkboxmodel',
						selModel : {
							mode : "SIMPLE" // "SINGLE"/"SIMPLE"/"MULTI"
						},

						store : Ext.create('Ext.data.Store', {
									fields : ['id', 'contractNo', 'operator', 'productType', 'productSpec',
											'custProductType', 'custProductSpec', 'conductorStruct','splitLengthRole', {
												name : 'contractLength',
												type : 'double'
											}, {
												name : 'orderLength',
												type : 'double'
											}, {
												name : 'isGroup',
												type : 'boolean'
											}, 'color', 'standardPly', 'standardMaxPly', 'standardMinPly',
											'outsideValue', 'outMatDesc', 'wireCoil', {
												name : 'wireCoilCount',
												type : 'double'
											}, {
												name : 'isAudit',
												type : 'boolean'
											}, 'remarks', 'voltage', 'wiresStructure','processRequire'],
									data : me.orderData
								}),
						columnLines : true,
						allowDeselect : true,
						columns : [{
									text : '合同号',
									dataIndex : 'contractNo',
									flex : 3,
									minWidth : 150,
									sortable : false,
									menuDisabled : true
								}, {
									text : '型号规格',
									dataIndex : 'custProductType',
									flex : 4.6,
									minWidth : 230,
									sortable : false,
									menuDisabled : true,
									renderer : function(value, metaData, record) {
										return value + ' ' + record.get('productSpec');
									}
								}, {
									text : '合同长度',
									dataIndex : 'contractLength',
									flex : 1.8,
									minWidth : 90,
									sortable : false,
									menuDisabled : true
								}],
						listeners : {
							afterrender : function(grid) {// 侦听goodslistview渲染
								grid.getSelectionModel().selectAll(); // 选中所有记录
							}
						}
					}];
				}
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
			},
			listeners : {
				close : function() {
					var me = this;
					me.setOrderStatusAudited()
				},
				disable : function() {
					var me = this;
					me.setOrderStatusAudited()
				},
				hide : function() {
					var me = this;
					me.setOrderStatusAudited()
				}
			},
			setOrderStatusAudited : function() {
				console.log('----后期执行更新操作-----');
			}
		});
