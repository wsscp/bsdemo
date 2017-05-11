Ext.define('bsmes.view.MesClientManEqipEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.mesClientManEqipedit',
	title: Oit.msg.bas.mesClientManEqip.addForm.title,
	iconCls: 'feed-add',
	formItems: [
        {
            name: 'id',
            xtype:'hiddenfield'
        },{
            fieldLabel: Oit.msg.bas.mesClientManEqip.mesClientId,
            name: 'mesClientName',
            xtype:'displayfield',
            labelWidth:80
        },{
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
