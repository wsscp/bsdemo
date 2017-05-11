Ext.define('bsmes.view.EquipMESWWMappingEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.equipMESWWMappingEdit',
	title: Oit.msg.bas.equipMESWWMapping.editForm.title,
	formItems: [{
        fieldLabel: Oit.msg.bas.equipMESWWMapping.acEquipCode,
        name: 'acEquipCode'
    },{
        fieldLabel: Oit.msg.bas.equipMESWWMapping.tagName,
        name: 'tagName'
    },{
        fieldLabel: Oit.msg.bas.equipMESWWMapping.equipCode,
        name: 'equipCode',
        xtype:'combobox',
        queryMode: 'local',
        allowBlank:false,
        displayField:   'code',
        store:new Ext.data.Store({
        	fields:['code'],
        	autoLoad:true,
        	proxy:{
        		type: 'rest',
        		url:'equipMESWWMapping/getEquipCode'
        	}
        }),
        listeners: {
        	'change' : function(me,value,oldValue,opts){
        		var parmCode=Ext.getCmp("parmCode");
        		parmCode.reset();
                var parmStore = parmCode.getStore();
                parmStore.getProxy().url="equipMESWWMapping/getParmCode/"+value+'/';
                parmStore.reload();
        	}
        }
    },{
        fieldLabel: Oit.msg.bas.equipMESWWMapping.parmCode,
        name: 'parmCode',
        xtype:'combobox',
        mode: 'local',
        displayField: 'itemCode',
        valueField: 'itemCode',
        store:'ParmCodeStore',
        id: 'parmCode'
    },{
        fieldLabel: Oit.msg.bas.equipMESWWMapping.eventType,
        name: 'eventType',
        xtype: 'combobox',
        displayField:'name',
        valueField : 'code',
        store: new Ext.data.Store({
            proxy : { type: 'rest', url: 'equipMESWWMapping/getEventType', reader: { type: 'json' } },
            fields: [ 'code', 'name']
        })
    }] 
});

