/**
 * 绝缘查看库存清单
 */
Ext.define('bsmes.view.InventoryGridWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.inventoryGridWindow',
			title : '查看库存明细',
			width : 850,
			height : 500,
			modal : true,
			plain : true,
			overflowY : 'auto',
			padding : '5',
			orderItemIds : null,
			section : null,

			initComponent : function() {
				var me = this;

				var store = Ext.create('Ext.data.Store', {
							fields : ['MATERIALCODE', 'MATERIALNAME', 'MATERIALDESC', 'QUANTITY', 'LOCKEDQUANTITY',
									'UNIT', 'DESCRIPTION'],
							// 'id', 'locationId', 'warehouseName', 'materialCode', 'materialName', 'barCode',
							// 'quantity', 'unit', 'locationName', 'processCode', 'processName', 'locationX',
							// 'locationY', 'locationZ'
							proxy : {
								type : 'rest',
								url : 'handSchedule/inventoryGrid?orderItemIds=' + me.orderItemIds + '&section='
										+ me.section
							}
						});
				store.load();
				me.items = [{
							xtype : 'grid',
							store : store,
							columnLines : true,
							allowDeselect : true,
							columns : [{
										text : '物料编码',
										dataIndex : 'MATERIALCODE',
										minWidth : 100,
										hidden : true,
										flex : 2
									}, {
										text : '物料名称',
										dataIndex : 'MATERIALNAME',
										minWidth : 120,
										flex : 2.4,
										renderer : function(value, metaData, record) {
											metaData.style = "white-space:normal;padding:5px 5px 5px 5px;";
											return value;
										}
									}, {
										text : '物料信息',
										dataIndex : 'MATERIALDESC',
										minWidth : 300,
										flex : 6,
										renderer : function(value, metaData, record) {
											metaData.tdAttr = 'data-qtip="' + value + '"';
											// metaData.style = "white-space:normal;padding:5px 5px 5px 5px;";
											return value;
										}
									}, {
										text : '库存量',
										dataIndex : 'QUANTITY',
										minWidth : 90,
										flex : 1.8,
										renderer : function(value, metaData, record) {
											value = value + ' ' + record.get('UNIT');
											metaData.tdAttr = 'data-qtip="' + value + '"';
											return value;
										}
									}, {
										// text : '单位',
										// dataIndex : 'UNIT',
										// minWidth : 50,
										// flex : 1
										// }, {
										text : '库位信息',
										dataIndex : 'DESCRIPTION',
										minWidth : 250,
										flex : 5,
										renderer : function(value, metaData, record) {
											var disk = value == '' ? [] : value.split(",");
											var res = '';
											for (var i = 0; i < disk.length; i++) {
												if (disk.length > 1) {
													res = res + (i + 1) + '、' + disk[i].split(';')[0] + "<br/>";
												} else {
													res = disk[i].split(';')[0];
												}
											}
											metaData.tdAttr = 'data-qtip="' + res + '"';
											return res;
										}
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
