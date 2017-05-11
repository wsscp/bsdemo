Ext.define('bsmes.view.SetDelivery',{
	extend:'Oit.app.view.form.EditForm',
	title: Oit.msg.pla.customerOrderItem.button.setDelivery,
	alias: 'widget.setDelivery',
	formItems: [{
					xtype: 'displayfield',
                    labelAlign: 'right',
					fieldLabel: Oit.msg.pla.customerOrderItem.customerCompany,
					name:'customerCompany'},
			    {
					xtype: 'displayfield',
					fieldLabel: Oit.msg.pla.customerOrderItem.contractNo,
                    labelAlign: 'right',
					name:'contractNo'},
			    {
			    	xtype: 'datefield',
			    	fieldLabel: Oit.msg.pla.customerOrderItem.selectCustomerOaDate,
                    labelAlign: 'right',
                    name:'customerOaDate',
			    	format:'Y-m-d',
                    minValue:new Date()
                }]
});