Ext.define('bsmes.view.MesClientManEqipAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.mesClientManEqipadd',
	id:'edit',
	title: Oit.msg.bas.mesClientManEqip.addForm.title,
	iconCls: 'feed-add',
	formItems: [{
        name: 'mesClientId',
        xtype:'hiddenfield'
    },{
        fieldLabel: Oit.msg.bas.mesClientManEqip.mesClientId,
        name: 'mesClientName',
        xtype:'displayfield',
        labelWidth:80
    },
    {
        fieldLabel: Oit.msg.bas.mesClientManEqip.eqipId,
        name: 'eqipId',
        xtype:'combobox',
        queryMode: 'local',
        displayField:'name',
        valueField:'eqipId',
        width:400,
        labelWidth:80,
        allowBlank:false,
        store:new Ext.data.Store({
        	fields:['eqipId','name'],
        	autoLoad:true,
        	proxy:{
        		type: 'rest',
        		url:'mesClientManEqip/getEqipName'
        	}
        })
    }]
});

