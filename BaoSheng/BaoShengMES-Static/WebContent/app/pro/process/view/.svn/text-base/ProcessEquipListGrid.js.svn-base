Ext.define('bsmes.view.ProcessEquipListGrid', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.processEquipListGrid',
			collapsible : false,
			animCollapse : false,
			hasPaging : false,
			height:document.body.scrollHeight-140,
			forceFit : false,
			store : 'ProcessEquipListStore',
			columns : [{
						text : Oit.msg.pro.equipList.equipCode,
						dataIndex : 'equipCode',
						flex : 1
					}, {
						text : Oit.msg.pro.equipList.equipName,
						dataIndex : 'equipName',
						flex : 1
					}, {
						text : Oit.msg.pro.equipList.equipType,
						dataIndex : 'type',
						flex : 1
					}, {
						text : Oit.msg.pro.equipList.equipCapacity,
						dataIndex : 'equipCapacity',
						flex : 1
					}, {
						text : Oit.msg.pro.equipList.setUpTime,
						dataIndex : 'setUpTime',
						flex : 1
					}, {
						text : Oit.msg.pro.equipList.shutDownTime,
						dataIndex : 'shutDownTime',
						flex : 1
					}],
			// 查询栏
			dockedItems : [{
						items : [{
									xtype : 'hform',
									items : [{
												xtype : 'hiddenfield',
												name : 'processId'
											}]
								}]

					}],
			tbar : [{
						itemId : 'equipListEdit',
						text : Oit.msg.pro.equipList.equipListEdit
					}, {
						itemId : 'processReceiptOpen',
						text : Oit.msg.pro.equipList.processReceiptOpen
					}, '->', {
						text : '关  闭',
						handler : function(){
							var me = this;
							me.up('window').close();
						}
					}]
		});