Ext.define('bsmes.view.EquipMESWWMappingAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.equipMESWWMappingAdd',
	title: Oit.msg.bas.equipMESWWMapping.addForm.title,
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
        		var parmStore = parmCode.getStore();
        		parmCode.reset();
                if(value!=""){
                	parmStore.getProxy().url="equipMESWWMapping/getParmCode/"+value+'/';
                	parmStore.load();
                }
        	}
        }
    },{
        fieldLabel: Oit.msg.bas.equipMESWWMapping.parmCode,
        id : 'parmCode',
        name: 'parmCode',
        xtype:'combobox',
        mode: 'local',
        displayField: 'itemCode',
        valueField: 'itemCode',
        store:'ParmCodeStore'
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
