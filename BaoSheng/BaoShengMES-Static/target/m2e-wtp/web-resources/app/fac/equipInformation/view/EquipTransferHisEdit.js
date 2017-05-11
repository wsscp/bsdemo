Ext.define('bsmes.view.EquipTransferHisEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.equipTransferHisEdit',
	title: '设备迁移',
	width: 450,
	
	formItems: [{
    	fieldLabel: '设备编码',
    	allowBlank: false,
    	xtype : 'displayfield',
    	name: 'equipCode'
    },{
    	fieldLabel: '目标制造部',
    	allowBlank: false,  
    	name: 'targetMFG'
    },{
    	fieldLabel: '迁移时间',
    	allowBlank: false,  
    	name: 'transferTime'
    }]
});
