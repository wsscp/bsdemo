Ext.define("bsmes.view.ReceiptList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.receiptList',
	store : 'ReceiptStore',
	defaultEditingPlugin : false,
	forceFit:false,
	columns : [ {
		text : Oit.msg.wip.receipt.workOrderNo,
		width:200,
		dataIndex : 'workOrderNo'
	}, {
		text : Oit.msg.wip.receipt.productCode,
		width:200,
		dataIndex : 'productCode'
	}, {
		text : Oit.msg.wip.receipt.equipCode,
		width:200,
		dataIndex : 'equipCode'
	}, {
		text : Oit.msg.wip.receipt.type,
		width:200,
		dataIndex : 'type'
	}, {
		text : Oit.msg.wip.receipt.receiptCode,
		width:150,
		dataIndex : 'receiptCode'
	}, {
		text : Oit.msg.wip.receipt.receiptName,
		width:200,
		dataIndex : 'receiptName'
	},{
		text : Oit.msg.wip.receipt.receiptTargetValue,
		width:200,
		dataIndex : 'receiptTargetValue'
	}, {
		text : Oit.msg.wip.receipt.receiptMaxValue,
		width:200,
		dataIndex : 'receiptMaxValue'
	}, {
		text : Oit.msg.wip.receipt.receiptMinValue,
		width:150,
		dataIndex : 'receiptMinValue'
	}, {
		text : Oit.msg.wip.receipt.createUserCode,
		width:150,
		dataIndex : 'createUserCode'
	},{
		text : Oit.msg.wip.receipt.issuedTime,
		width:150,
		dataIndex : 'issuedTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	} ],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			title : '查询条件',
			xtype : 'fieldset',
			collapsible : true,
			width : '100%',
			items : [ {
				xtype : 'form',
				width : '100%',
				layout : 'vbox',
				buttonAlign : 'left',
				labelAlign : 'right',
				bodyPadding : 5,
				defaults : {
					xtype : 'panel',
					width : '100%',
					layout : 'hbox',
					defaults : {
						labelAlign : 'right'
					}
				},
				items : [ {
					items : [{
						fieldLabel :Oit.msg.wip.receipt.workOrderNo,
						xtype : 'textfield',
						width:240,
						name : 'workOrderNo'
					},{
						fieldLabel : Oit.msg.wip.receipt.productCode,
						xtype : 'textfield',
						width:240,
						name : 'productCode'
					}, {
						fieldLabel :Oit.msg.wip.receipt.type,
						name : 'type',
						xtype : 'combobox',
						displayField: 'name',
					    valueField: 'code',
					    width:240,
					    editable:false,
					    store:new Ext.data.Store({
						    fields:['code', 'name'],
						    data : [
						        {"name":Oit.msg.wip.receipt.all, "code":""},
						        {"name":Oit.msg.wip.receipt.processReceipt, "code":"PROCESS_RECEIPT"},
						        {"name":Oit.msg.wip.receipt.qaReceipt, "code":"QA_RECEIPT"}
					        ]
						})
					},{
						fieldLabel : Oit.msg.wip.receipt.equipCode,
						xtype : 'combobox',
						name : 'equipCode',
						editable:false,  
						mode:'remote',
						displayField:'text',
						valueField : 'value',
						width:420,
						store:new Ext.data.Store({
							fields:[{name:'text',mapping:'name'},{name:'value',mapping:'code'}],
						    autoLoad:true,
							proxy:{
								type: 'rest',
								url:'receipt/equip'
							}
						})
					}]
				}],
				buttons : [ {
					itemId : 'search',
					text : Oit.btn.search
				}, {
					itemId : 'reset',
					text : '重置',
					handler : function(e) {
						this.up("form").getForm().reset();
					}
				}, {
					itemId : 'exportToXls',
					text : Oit.btn.export
				} ]

			} ]
		} ]
	} ]

});