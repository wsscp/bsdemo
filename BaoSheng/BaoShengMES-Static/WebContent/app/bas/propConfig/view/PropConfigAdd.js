Ext.define('bsmes.view.PropConfigAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.propConfigAdd',
	title: Oit.msg.bas.prop.addForm.title,
	/*iconCls: 'feed-add',*/
	formItems: [{
        fieldLabel: Oit.msg.bas.prop.keyK,
        name: 'keyK',
        vtype:'checkUnique',
        xtype:'combobox',
        queryMode: 'local',
        displayField:   'code',
        store:new Ext.data.Store({
        	fields:['code'],
        	autoLoad:true,
        	proxy:{
        		type: 'rest',
        		url:'propConfig/getCodeByTermsCode'
        	}
        }),
        listeners: {
        	'select' : function(value){
        		var keyK = value.lastValue;
        		Ext.Ajax.request({
        			async: false,
        			url:'propConfig/getValueV/'+keyK+'/',
        			method:'GET',
        			success:function(response){
        				var data = Ext.decode(response.responseText);
        				var name = data.name;
        				Ext.getCmp('add_description_id').setValue(name);
        			}
        		});
        	}
        }
    },{
        fieldLabel: Oit.msg.bas.prop.valueV,
        name: 'valueV',       
        id: 'valueV'
    },{
        fieldLabel: Oit.msg.bas.prop.description,
        name: 'description',
        id:'add_description_id'
    },{
        fieldLabel: Oit.msg.bas.prop.status,
        xtype:'radiogroup',
        columns:4,
        vertical:true,
        items:[{boxLabel: Oit.msg.bas.prop.normal, name: 'status', inputValue: '1',checked:true},
               {boxLabel: Oit.msg.bas.prop.freeze, name: 'status', inputValue: '0'}]
    }] 
});

Ext.apply(Ext.form.VTypes,{
	checkUnique:function(value, field) {
        if (value === undefined || value === null || value =='') {
            return false;
        }
		var result = false;
		Ext.Ajax.request({
			url:'/bsmes/bas/propConfig/checkPropKeyUnique/'+value+'/',
			method:'GET',
			async: false,
			success: function(response) {
				var data = Ext.decode(response.responseText);
				if(data && !data.propExists){
					result = true;
				}
			}
		});
		return result;
	},
	checkUniqueText:'键已存在!'
});
