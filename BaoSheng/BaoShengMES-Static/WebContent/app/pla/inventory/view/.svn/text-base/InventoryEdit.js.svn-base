Ext.define('bsmes.view.InventoryEdit', {
			extend : 'Oit.app.view.form.EditForm',
			alias : 'widget.inventoryEdit',
			title : '修改库存物料信息',
			iconCls : 'icon_edit',
			height : 420,
			formItems : [{
						fieldLabel : '物料名称',
						name : 'materialName',
						allowBlank : false,
						
						width : 400
					}, {
						fieldLabel : '物料信息',
						name : 'materialDesc',
						allowBlank : false,
						xtype : 'displayfield',
						width : 400
					}, {
						fieldLabel : '物料代码',
						name : 'materialCode',
						xtype : 'hiddenfield',
						width : 400,
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
									boxLabel : '千克',
									name : 'unit',
									inputValue : 'KG'
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
									combo.store.getProxy().url = '../inv/location/getByWarehouseId?warehouseId='
											+ newValue;
									combo.store.load();
									if (oldValue && oldValue != '') {
										combo.expand();
									}
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
									fields : ['id', 'processCode', 'locationName', 'locationX', 'locationY',
											'locationZ', {
												name : 'displayInfo',
												convert : function(value, record) {
													return record.get('processCode') + '_' + record.get('locationName')
															+ '_' + record.get('locationX') + '_'
															+ record.get('locationY') + '_' + record.get('locationZ');
												}
											}],
									proxy : {
										type : 'rest'
									}
								}),
						listeners : {
							beforequery : function(e) {
							},
							change : function(field, newValue, oldValue) {
							}
						}
					}]
		});