Ext.define('bsmes.view.EquipInformationEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.equipInformationEdit',
	title: Oit.msg.fac.equipInformation.editTitle,
	iconCls: 'feed-add',
	width: 600,
	defaults:{
		layout: {
	        type: 'table',
	        // The total column count must be specified here
	        columns: 2
	    },
    },
	
	formItems: [{
		fieldLabel: Oit.msg.fac.equipInformation.code,
		xtype:'displayfield',
        name: 'code'
    },{
    	fieldLabel: Oit.msg.fac.equipInformation.name,
    	allowBlank: false,  
    	blankText:Oit.msg.fac.equipInformation.name+'不能为空',
    	name: 'name'
    },{
    	fieldLabel: '设备别名',
    	allowBlank: false,  
    	blankText:'设备别名'+'不能为空',
    	name: 'equipAlias'
    },{
    	fieldLabel: '设备分类',
    	allowBlank: false,  
    	blankText:'设备分类'+'不能为空',
    	name: 'equipCategory'
    },{
    	fieldLabel: '设备价格',
    	allowBlank: false,  
    	blankText:'设备价格'+'不能为空',
    	name: 'equipPrice'
    },{
    	fieldLabel: '设备厂家',
    	allowBlank: false,  
    	blankText:'设备厂家'+'不能为空',
    	name: 'equipFact'
    },{
    	fieldLabel: '采购时间',
    	allowBlank: false,  
    	xtype: 'datefield',
    	format : 'Y-m-d',
    	blankText:'采购时间'+'不能为空',
    	name: 'equipPurtime'
    },{
    	fieldLabel: Oit.msg.fac.equipInformation.model,
    	name: 'model',
    	itemId: 'model',
    	xtype : 'combobox',
    	editable:false,
    	mode:'remote',
    	displayField:'name',
    	valueField : 'code',
    	triggerAction :'all',
		store:new Ext.data.Store({
			fields:[{name:'code'},{name:'name'}],
			autoLoad:false,
			proxy:{
			  type: 'rest',
			  url:'equipInformation/equipModel'
			}
		}),
        listeners : {
            change : function(combo, newValue, oldValue, eOpts) {
                var record = Ext.ComponentQuery.query('equipInformationEdit form')[0].form.getRecord();
                if (!record.get('isNeedMaintain')) {
                    return;
                }
                Ext.Ajax.request({
                    url: 'equipInformation/selectModel?model=' + newValue,
                    success: function(response){
                        var text = response.responseText;
                        var tmplMap = Ext.JSON.decode(text);
                        record.setTmplMap(tmplMap);
                        Ext.ComponentQuery.query('equipInformationEdit #maintainDateFirst')[0].setDisabled(!tmplMap.FIRST_CLASS || tmplMap.FIRST_CLASS.triggerType != 'NATURE');
                        Ext.ComponentQuery.query('equipInformationEdit #maintainDateSecond')[0].setDisabled(!tmplMap.SECOND_CLASS || tmplMap.SECOND_CLASS.triggerType != 'NATURE');
                        Ext.ComponentQuery.query('equipInformationEdit #maintainDateOverhaul')[0].setDisabled(!tmplMap.OVERHAUL || tmplMap.OVERHAUL.triggerType != 'NATURE');
                    }
                });

            }
        }
    },{
    	fieldLabel: Oit.msg.fac.equipInformation.type,
    	name: 'type',
    	itemId: 'type',
    	xtype:'displayfield',
    	renderer:function(value, cellmeta, record){
			if (value == 'MAIN_EQUIPMENT'){
				return '主生产设备';
			}
			if (value == 'ASSIT_EQUIPMENT'){
				return '辅助生产设备';
			}
			if (value == 'TOOLS') {
				return '工装夹具';
			}
			if (value == 'PRODUCT_LINE') {
				return '生产线';
			}
		}
    },{
    	fieldLabel: Oit.msg.fac.equipInformation.subType,
    	name: 'subType',
    	xtype : 'combobox',
    	editable:false,
    	mode:'remote',
    	displayField:'name',
    	valueField : 'code',
    	triggerAction :'all',
		store:new Ext.data.Store({
			fields:['code','name'],
			autoLoad:false,
			proxy:{
			  type: 'rest',
			  url:'equipInformation/subType'
			}
		})
    },{
    	fieldLabel: Oit.msg.fac.equipInformation.belongLine,
		xtype:'displayfield',
        name: 'belongLine'
    },{
        fieldLabel : Oit.msg.fac.equipInformation.maintainDate,
        name : 'maintainDate',
        xtype : 'numberfield'
    },{
        fieldLabel : Oit.msg.fac.equipInformation.maintainDateFirst,
        name : 'maintainDateFirst',
        itemId : 'maintainDateFirst',
        xtype : 'numberfield',
        disabled : true
    },{
        fieldLabel : Oit.msg.fac.equipInformation.maintainDateSecond,
        name : 'maintainDateSecond',
        itemId : 'maintainDateSecond',
        xtype : 'numberfield',
        disabled : true
    },{
        fieldLabel : Oit.msg.fac.equipInformation.maintainDateOverhaul,
        name : 'maintainDateOverhaul',
        itemId : 'maintainDateOverhaul',
        xtype : 'numberfield',
        disabled : true
    },{
    	fieldLabel: Oit.msg.fac.equipInformation.maintainer,
        xtype : 'displayfield',
        name: 'maintainer'
    }],
    onShow : function() {
        setTimeout(function() {
            var form = Ext.ComponentQuery.query('equipInformationEdit form')[0];
            var field = Ext.ComponentQuery.query('equipInformationEdit numberfield[name=maintainDate]')[0];
            if (form.getRecord().get('type') != 'MAIN_EQUIPMENT') {
                field.setDisabled(true);
            } else {
                field.setDisabled(false);
            }
        }, 0);
    }
});
