Ext.define('bsmes.view.ProcessList', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.processList',
			store : 'ProcessStore',
			columns : [{
						text : Oit.msg.pro.process.processCode,
						width : 180,
						dataIndex : 'processCode'
					}, {
						text : Oit.msg.pro.process.processName,
						width : 70,
						dataIndex : 'processName'
					}, {
						text : Oit.msg.pro.process.seq,
						width : 60,
						dataIndex : 'seq'
					}, {
						text : Oit.msg.pro.process.craftName,
						width : 200,
						dataIndex : 'craftsName'
					},
					/*
					 * { text : Oit.msg.pro.process.processTime, width : 60,
					 * dataIndex : 'processTime' }, { text :
					 * Oit.msg.pro.process.setUpTime, width : 60, dataIndex :
					 * 'setUpTime' }, { text : Oit.msg.pro.process.shutDownTime,
					 * width : 60, dataIndex : 'shutDownTime' }, { text :
					 * Oit.msg.pro.process.fullPath, dataIndex : 'fullPath' },
					 */
					{
						text : Oit.msg.pro.process.nextProcess,
						width : 60,
						dataIndex : 'nextProcessName',
						sortable : false
					}, {
						text : Oit.msg.pro.process.sameProductLine,
						dataIndex : 'sameProductLineText',
						sortable : false
					}, {
						text : Oit.msg.pro.process.option,
						width : 60,
						sortable : false,
						dataIndex : 'isOptionText'
					}, {
						text : Oit.msg.pro.process.isDefaultSkip,
						width : 80,
						dataIndex : 'isDefaultSkipText',
						sortable : false
					}],
			tbar : [{
				xtype : 'button',
				itemId : 'editProcess',
				text : Oit.msg.pro.process.btn.editProcess,
				hidden : true
					// TODO 暂时不让修改
				}, {
				xtype : 'button',
				itemId : 'editProcessQC',
				text : '质量检测参数维护'
			}, {
				xtype : 'button',
				itemId : 'processEquipListBtn',
				text : Oit.msg.pro.process.btn.processEquipListBtn
			}, {
				xtype : 'button',
				itemId : 'processInOutBtn',
				text : Oit.msg.pro.process.btn.processInOutBtn
			}, {
				itemId : 'export'
			}]
		});