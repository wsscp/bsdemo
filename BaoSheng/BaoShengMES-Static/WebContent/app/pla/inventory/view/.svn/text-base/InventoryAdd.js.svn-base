Ext.define('bsmes.view.InventoryAdd', {
	extend : 'Oit.app.view.form.EditForm',
	alias : 'widget.inventoryAdd',
	title : '新增库存物料信息',
	iconCls : 'icon_add',
	height : 300,
	formItems : [{
				fieldLabel : '物料名称<font color="red">*</font>',
				name : 'materialName',
				allowBlank : false,
				xtype : 'combobox',
				displayField : 'MATNAME',
				valueField : 'MATNAME',
				width : 400,
				store : 'MaterialNameStore',
				listeners : {
					beforequery : function(e) {
						var combo = e.combo;
						if (!e.forceAll) {
							var value = e.query;
							if (value != null && value != '') {
								combo.store.filterBy(function(record, id) {
											var text = record.get('MATNAME');
											return (text.indexOf(value) != -1);
										});
							} else {
								combo.store.clearFilter();
							}
							combo.expand();
							return false;
						}
					},
					change : function(field, newValue, oldValue) {
						if (newValue != '') {
							var combo = this.up("form").getForm().findField('materialDesc');
							combo.store.getProxy().url = 'materialRequirementPlan/getDescByMatName?matName=' + newValue;
							combo.store.load();
							combo.expand();
						}
					}
				}
			}, {
				fieldLabel : '物料信息<font color="red">*</font>',
				name : 'materialDesc',
				allowBlank : false,
				xtype : 'combobox',
				displayField : 'MATDESC',
				valueField : 'MATDESC',
				width : 400,
				store : new Ext.data.Store({
							fields : ['MATCODE', 'MATDESC'],
							proxy : {
								type : 'rest'
							}
						}),
				listeners : {
					beforequery : function(e) {
						var combo = e.combo;
						return;
					},
					change : function(field, newValue, oldValue) {
						if (field.displayTplData && field.displayTplData.length > 0) {
							this.up("form").getForm().findField('materialCode')
									.setValue(field.displayTplData[0].MATCODE);
						}
					}
				}
			}, {
				fieldLabel : '物料代码',
				name : 'materialCode',
				width : 400,
				xtype : 'hiddenfield',
				allowBlank : false
			}, {
				fieldLabel : '库存量<font color="red">*</font>',
				name : 'quantity',
				width : 400,
				xtype : 'numberfield',
				decimalPrecision : 5,
				allowBlank : false
			}, {
				fieldLabel : '单位<font color="red">*</font>',
				name : 'unit',
				width : 400,
				allowBlank : false,
				xtype : 'radiogroup',
				columns : 3,
				vertical : true,
				items : [{
							boxLabel : '克',
							name : 'unit',
							inputValue : 'G',
							checked : true
						}, {
							boxLabel : '千克',
							name : 'unit',
							inputValue : 'KG'
						}, {
							boxLabel : '吨',
							name : 'unit',
							inputValue : 'TON'
						}, {
							boxLabel : '米',
							name : 'unit',
							inputValue : 'M'
						}, {
							boxLabel : '千米',
							name : 'unit',
							inputValue : 'KM'
						}]
			}, {
				fieldLabel : '仓库<font color="red">*</font>',
				name : 'warehouseId',
				width : 400,
				allowBlank : false,
				xtype : 'combobox',
				displayField : 'warehouseName',
				valueField : 'id',
				width : 400,
				store : 'WarehouseStore',
				listeners : {
					change : function(field, newValue, oldValue) {
						if (newValue != '') {
							var combo = this.up("form").getForm().findField('locationId');
							combo.store.getProxy().url = '../inv/location/getByWarehouseId?warehouseId=' + newValue;
							combo.store.load();
							combo.expand();
						}
					}
				}
			}, {
				fieldLabel : '库位信息<font color="red">*</font>',
				name : 'locationId',
				width : 400,
				allowBlank : false,
				xtype : 'combobox',
				displayField : 'displayInfo',
				valueField : 'id',
				width : 400,
				store : new Ext.data.Store({
							fields : ['id', 'processCode', 'locationName', 'locationX', 'locationY', 'locationZ', {
								name : 'displayInfo',
								convert : function(value, record) {
									return record.get('processCode') + '_' + record.get('locationName') + '_'
											+ record.get('locationX') + '_' + record.get('locationY') + '_'
											+ record.get('locationZ');
								}
							}],
							proxy : {
								type : 'rest'
							}
						})
			}]
});