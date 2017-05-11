Ext.define('bsmes.view.UserAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.useradd',
	title: Oit.msg.bas.user.addForm.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.bas.user.userCode,
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
        fieldLabel: Oit.msg.bas.user.password,
        name: 'password',
        inputType: 'password',
        allowBlank: false,
        width: 390, 
        minLength:5
    },{
        fieldLabel: Oit.msg.bas.user.status,
        xtype:'radiogroup',
        columns:2,
        vertical:true,
        items:[{boxLabel: Oit.msg.bas.user.normal, name: 'status', inputValue: '1',checked:true},
               {boxLabel: Oit.msg.bas.user.freeze, name: 'status', inputValue: '0'}]
    }] 
});