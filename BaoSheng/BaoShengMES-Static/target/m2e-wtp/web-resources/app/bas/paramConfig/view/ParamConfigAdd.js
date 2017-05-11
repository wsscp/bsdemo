Ext.define('bsmes.view.ParamConfigAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.paramConfigAdd',
	title: Oit.msg.bas.param.addForm.title,
	/*iconCls: 'feed-add',*/
	formItems: [{
        fieldLabel: Oit.msg.bas.param.code,
        name: 'code',
        vtype:'checkUnique',
        xtype:'combobox',
        queryMode: 'local',
        displayField:   'code',
        store:new Ext.data.Store({
        	fields:['code'],
        	autoLoad:true,
        	proxy:{
        		type: 'rest',
        		url:'paramConfig/getCodeByTermsCode'
        	}
        }),
        listeners: {
        	'select' : function(value){
        		var code = value.lastValue;
        		Ext.Ajax.request({
        			async: false,
        			url:'paramConfig/getName/'+encodeURI(encodeURI(code))+'/',
        			method:'GET',
        			success:function(response){
        				var data = Ext.decode(response.responseText);
        				var name = data.name;
        				Ext.getCmp('name').setValue(name);
        			}
        		});
        	}
        }
    },{
        fieldLabel: Oit.msg.bas.param.name,
        name: 'name',
        xtype: 'displayfield',
        id: 'name'
    },{
        fieldLabel: Oit.msg.bas.param.value,
        name: 'value'
    },{
        fieldLabel: Oit.msg.bas.param.type,
        name: 'type'
    },{
        fieldLabel: Oit.msg.bas.param.description,
        name: 'description'
    },{
        fieldLabel: Oit.msg.bas.param.status,
        xtype:'radiogroup',
        columns:4,
        vertical:true,
        items:[{boxLabel: Oit.msg.bas.param.normal, name: 'status', inputValue: '1',checked:true},
               {boxLabel: Oit.msg.bas.param.freeze, name: 'status', inputValue: '0'}]
    }] 
});

Ext.apply(Ext.form.VTypes,{
	checkUnique:function(value, field) {
        if (value === undefined || value === null || value =='') {
            return false;
        }
		var result = false;
		Ext.Ajax.request({
			url:'/bsmes/bas/paramConfig/checkParamCodeUnique/'+value+'/',
			method:'GET',
			async: false,
			success: function(response) {
				var data = Ext.decode(response.responseText);
				if(data && !data.paramExists){
					result = true;
				}
			}
		});
		return result;
	},
	checkUniqueText:'参数号已存在!'
});
