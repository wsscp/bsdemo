Ext.define('bsmes.view.StoreManageAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.storeManageAdd',
	title: '添加',
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: '机台',
        name: 'userCode',
        xtype:'combobox',
        displayField: 'name',
        valueField: 'userCode',
        allowBlank: false,
        //editable:false,
        forceSelection: true,
        minChars : 1,
        width: 390,
        store:new Ext.data.Store({
            proxy : {
                type: 'rest',
                url: 'user/findEmployeeNotInUser/-1',
                reader: { type: 'json' }
            },
            fields: ['userCode', 
                { name: 'name',
	                convert: function(value, record) {
	                    return record.get('userCode') + '_' + value;
	                }
                } 
            ]
        }),
        listeners : {
			'beforequery' : function(queryPlan, eOpts) {
				var me = this;
				var url = 'user/findEmployeeNotInUser';
				if (queryPlan.query) {
					me.getStore().getProxy().url = url + "/" + queryPlan.query+'/';
				} else {
					me.getStore().getProxy().url = url + "/-1";
				}
			}
		}
    },{
        fieldLabel: '工号',
        name: 'password',
        inputType: 'password',
        allowBlank: false,
        width: 390, 
        minLength:5
    },{
        fieldLabel: '废料',
        xtype:'radiogroup',
        columns:2,
        vertical:true,
        items:[{boxLabel: '废铜', name: 'feiLiao', inputValue: 'FEI_TONG',checked:true},
               {boxLabel: '废线', name: 'feiLiao', inputValue: 'FEI_XIAN'}]
    }] 
});