/**
 * 订单明细固定设备
 */
Ext.define('bsmes.view.ChooseProcessEquipArrayWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.chooseProcessEquipArrayWindow',
	width : 700,
	height : 432,
	autoScroll : true,
	// bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
	title : '修改默认生产线',
	layout : 'fit',
	modal : true,
	plain : true,
	rowIndex : null,
	processId : null,
	newEquipList : null, // 新值，判断是否默认选中
	storeUrl : null,
	backGrid : null,

	initComponent : function() {
		var me = this;

		if (me.rowIndex == null || me.processId == null || me.storeUrl == null || me.backGrid == null) {
			console.log('输入参数不正确');
			this.callParent(arguments);
			return;
		}

		me.items = [{
					xtype : 'form',
					bodyPadding : '20 10',
					width : 700,
					height : 432,
					autoScroll : true,
					items : [{
								name : 'equipList',
								xtype : 'checkboxgroup',
								columns : 2,
								items : []
							}]
				}];

		var store = Ext.create('Ext.data.Store', {
					fields : [{
								name : 'equipName',
								convert : function(value, record) {
									return value + '[' + record.get('equipCode') + ']';
								}
							}, 'equipCode', {
								name : 'isDefault',
								type : 'boolean'
							}],
					proxy : {
						type : 'rest',
						url : me.storeUrl,
						extraParams : {
							processId : me.processId
						}
					}
				});
		store.load({
					callback : function(records, options, success) {
						var equipListArray = me.newEquipList.split(','); // 当前设置选中的设备编码
						var checkgroup = Ext.ComponentQuery.query('chooseProcessEquipArrayWindow checkboxgroup')[0];;
						for (var i = 0; i < store.getCount(); i++) {
							var record = store.getAt(i);
							checkgroup.items.add(new Ext.form.Checkbox({
										boxLabel : record.get('equipName'),
										inputValue : record.get('equipCode'),
										checked : (equipListArray.indexOf(record.get('equipCode')) >= 0)
									}));
						}
						checkgroup.doLayout();
					}
				});

		Ext.apply(me, {
			buttons : [{
						itemId : 'ok',
						text : Oit.btn.ok,
						handler : function() {
							var equipList = this.up('window').down('form checkboxgroup[name="equipList"]').getChecked();

							var equipName = [], equipCode = [];
							Ext.Array.each(equipList, function(item) {
										equipName.push(item.boxLabel);
										equipCode.push(item.inputValue);
									});

							// 更改工序列表grid信息
							var mergeProcessGrid = Ext.ComponentQuery.query(me.backGrid)[0];
							var store = mergeProcessGrid.getStore();
							var record = store.getAt(me.rowIndex);
							record.set('equipNameArray', equipName.join());
							record.set('equipCodeArray', equipCode.join());
							me.close();
						}
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
