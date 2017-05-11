Ext.define('bsmes.view.TestView', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.testView',
			initComponent : function() {
				var me = this;

				me.items = [{
							itemId : 'equipCodes',
							xtype : 'combobox',
							fieldLabel : '选择机台',
							store : Ext.create('Ext.data.Store', {
										fields : ['code', 'name', 'processCode']
									}),
							displayField : 'name',
							valueField : 'code'
						}];
				this.callParent(arguments);
				me.getEquip();
			},
			getEquip : function(processCode) {
				var me = this;
				var equipCodes = me.query('#equipCodes')[0]
				var store = equipCodes.getStore();
				// store.loadData([{
				// code : 'Steam-Line-04',
				// name : '蒸线房04'
				// }, {
				// code : 'Steam-Line-05',
				// name : '蒸线房05'
				// }, {
				// code : 'Steam-Line-06',
				// name : '蒸线房06'
				// }], false);
				// store.insert(3, {
				// code : 'Steam-Line-04',
				// name : '蒸线房04'
				// });

				store.getProxy().data = [{
							code : 'Steam-Line-room－04',
							name : '蒸线房04'
						}, {
							code : 'Steam-Line-05',
							name : '蒸线房05'
						}, {
							code : 'Steam-Line-06',
							name : '蒸线房06'
						}];
				// for (var i = 0; i < store.getCount(); i++) {
				// var record = store.getAt(i);
				// if (record.get('processCode') == 'jy') {
				// store.removeAt(i);
				// }
				// }
				store.load({
							callback : function(records, operation, success) {
								if (records.length > 0) { // 选择第一个 暂时
									var record = records[0];
									var code = record.getData().code;
									equipCodes.select(code);
								}
							}
						});
				console.log(store)

				// store.setProxy({
				// type : 'localstorage',
				// id : 'twitter-Searches'
				// type : 'rest',
				// url : 'handSchedule/getProcessEquip',
				// extraParams : {
				// processCode : processCode,
				// orderItemIdArray : me.orderItemIdArray
				// }
				// });

				// store.load({
				// callback : function(records, operation, success) {
				// console.log('===================================================');
				// store.insert(0, {
				// code : 'Steam-Line-04',
				// name : '蒸线房04'
				// });
				// console.log(store)
				// }
				// });

			}
		});