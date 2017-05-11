Ext.form.Field.prototype.msgTarget = 'qtip';
Ext.define('bsmes.view.EquipInformationAdd', {
    extend: 'Oit.app.view.form.EditForm',
    alias: 'widget.equipInformationAdd',
    title: Oit.msg.fac.equipInformation.addTitle,
    iconCls: 'feed-add',
    formItems: [
        {
            fieldLabel: Oit.msg.fac.equipInformation.code,
            xtype: 'textfield',
            blankText: '设备CODE不能为空',
            allowBlank: false,
            name: 'code',
            validateOnBlur: true,
            validateOnChange: false,
            vtype: 'checkUniqueCode'
        },
        {
            fieldLabel: Oit.msg.fac.equipInformation.name,
            allowBlank: false,
            blankText: Oit.msg.fac.equipInformation.name + '不能为空',
            name: 'name'
        },
        {
            fieldLabel: Oit.msg.fac.equipInformation.model,
            name: 'model',
            xtype: 'combobox',
            editable: false,
            mode: 'remote',
            displayField: 'name',
            valueField: 'code',
            triggerAction: 'all',
            store: new Ext.data.Store({
                fields: [
                    {name: 'code'},
                    {name: 'name'}
                ],
                autoLoad: false,
                proxy: {
                    type: 'rest',
                    url: 'equipInformation/equipModel'
                }
            }),
            listeners: {
                change: function (combo, newValue, oldValue, eOpts) {
                    var record = Ext.ComponentQuery.query('equipInformationAdd form')[0].form.getRecord();
                    if (record.get('type') == '' || record.get('type') == 'TOOLS' || record.get('type') == 'PRODUCT_LINE') {
                        return;
                    }
                    Ext.Ajax.request({
                        url: 'equipInformation/selectModel?model=' + newValue,
                        success: function (response) {
                            var text = response.responseText;
                            var tmplMap = Ext.JSON.decode(text);
                            Ext.ComponentQuery.query('equipInformationAdd #maintainDateFirst')[0].setDisabled(!tmplMap.FIRST_CLASS || tmplMap.FIRST_CLASS.triggerType != 'NATURE');
                            Ext.ComponentQuery.query('equipInformationAdd #maintainDateSecond')[0].setDisabled(!tmplMap.SECOND_CLASS || tmplMap.SECOND_CLASS.triggerType != 'NATURE');
                            Ext.ComponentQuery.query('equipInformationAdd #maintainDateOverhaul')[0].setDisabled(!tmplMap.OVERHAUL || tmplMap.OVERHAUL.triggerType != 'NATURE');
                        }
                    });

                }
            }
        },{
            fieldLabel: '设备别名',
            xtype: 'textfield',
            blankText: '设备别名不能为空',
            allowBlank: false,
            name: 'equipAlias',
            validateOnBlur: true,
            validateOnChange: false
        },{
            fieldLabel: '设备分类',
            xtype: 'textfield',
            blankText: '设备分类不能为空',
            allowBlank: false,
            name: 'equipCategory',
            validateOnBlur: true,
            validateOnChange: false
        },{
            fieldLabel: '设备价格',
            xtype: 'textfield',
            blankText: '设备价格不能为空',
            allowBlank: false,
            name: 'equipPrice',
            validateOnBlur: true,
            validateOnChange: false
        },{
            fieldLabel: '设备厂家',
            xtype: 'textfield',
            blankText: '设备厂家不能为空',
            allowBlank: false,
            name: 'equipFact',
            validateOnBlur: true,
            validateOnChange: false
        },{
            fieldLabel: '采购时间',
            xtype: 'datefield',
            format : 'Y-m-d',
            blankText: '采购时间不能为空',
            allowBlank: false,
            name: 'equipPurtime',
            validateOnBlur: true,
            validateOnChange: false
        },{
            fieldLabel: Oit.msg.fac.equipInformation.type,
            name: 'type',
            xtype: 'combobox',
            editable: false,
            mode: 'remote',
            displayField: 'name',
            valueField: 'code',
            triggerAction: 'all',
            allowBlank: false,
            blankText: '设备类型不能为空',
            store: new Ext.data.Store({
                fields: [
                    {name: 'name'},
                    {name: 'code'}
                ],
                autoLoad: false,
                proxy: {
                    type: 'rest',
                    url: 'equipInformation/equipType'
                }
            }),
            listeners: {
                select: function (combo, records, eOpts) {
                    var result = combo.value;
                    var display = Ext.getCmp('productLineId');
                    display.setValue('');
                    var record = Ext.ComponentQuery.query('equipInformationAdd form')[0].form.getRecord();
                    record.set('type', result);
                    var subType=Ext.getCmp('subType');
                    if (result == 'MAIN_EQUIPMENT') {
                        display.setDisabled(false);
                        display.setVisible(true);
                    } else {
                        display.setDisabled(true);
                        display.setVisible(false);
                    }
                    if(result == 'MAIN_EQUIPMENT' || result == 'TOOLS'){
                    	subType.setDisabled(false);
                    	subType.setVisible(true);
                    }else{
                    	subType.setDisabled(true);
                    	subType.setVisible(false);
                    }
                }
            }
        },
        {
        	 fieldLabel: Oit.msg.fac.equipInformation.subType,
             id: 'subType',
             name: 'subType',
             hidden: true,
             xtype: 'combobox',
             editable: false,
             mode: 'remote',
             displayField: 'name',
             valueField: 'code',
             triggerAction: 'all',
             allowBlank: false,
             blankText: '设备子类型不能为空',
             store: new Ext.data.Store({
                 fields: [
                     {name: 'name'},
                     {name: 'code'}
                 ],
                 autoLoad: false,
                 proxy: {
                     type: 'rest',
                     url: 'equipInformation/subType'
                 }
             })
        },
        {
            fieldLabel: Oit.msg.fac.equipInformation.belongLine,
            id: 'productLineId',
            name: 'productLineId',
            hidden: true,
            xtype: 'combobox',
            editable: false,
            mode: 'remote',
            displayField: 'name',
            valueField: 'id',
            triggerAction: 'all',
            allowBlank: false,
            blankText: '所属生产线不能为空',
            store: new Ext.data.Store({
                fields: [
                    {name: 'name'},
                    {name: 'id'}
                ],
                autoLoad: false,
                proxy: {
                    type: 'rest',
                    url: 'equipInfo/getEquipLine'
                }
            })
        },
        {
            fieldLabel: Oit.msg.fac.equipInformation.maintainDate,
            name: 'maintainDate',
            xtype: 'numberfield'
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
        },
        {
            fieldLabel: Oit.msg.fac.equipInformation.maintainer,
            xtype: 'displayfield',
            name: 'maintainer'
        }
    ]
});
Ext.apply(Ext.form.VTypes, {
    checkUniqueCode: function (value, field) {
        if (value === undefined || value === null || value == '') {
            return false;
        }
        var result = false;
        Ext.Ajax.request({
            url: '/bsmes/fac/equipInformation/checkCode/' + value,
            method: 'GET',
            async: false,
            success: function (response) {
                var data = Ext.decode(response.responseText);
                if (data && data.checkResult) {
                    result = true;
                }
            }
        });
        return result;
    },
    checkUniqueCodeText: '设备CODE已存在'
});