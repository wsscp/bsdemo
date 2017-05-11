Ext.form.Field.prototype.msgTarget='qtip';
Ext.define('bsmes.view.EventTypeAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.eventTypeAdd',
	title: Oit.msg.eve.eventType.eventTypeAdd,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.eve.eventType.code,
        allowBlank: false, 
        name: 'code',
        vtype:'checkTypeCode',
        validateOnBlur : true,
    	validateOnChange :false
    },{
        fieldLabel: Oit.msg.eve.eventType.name,
        allowBlank: false, 
        name: 'name'
    },{
        fieldLabel: '是否在终端显示',
        xtype:'radiogroup',
        columns:4,
        vertical:true,
        items:[{boxLabel: '显示', name: 'needShow', inputValue: '1',checked:true},
               {boxLabel: '不显示', name: 'needShow', inputValue: '0'}]
    }] 
});
Ext.apply(Ext.form.field.VTypes,{
	checkTypeCode:function(value, field) {
        if (value === undefined || value === null || value =='') {
            return false;
        }
		var result = false;
		Ext.Ajax.request({
			url:'/bsmes/eve/eventType/checkCode/'+value+'/',
			method:'GET',
			async: false,
			success: function(response) {
				var data = Ext.decode(response.responseText);
				if(data && data.checkCode){
					result = true;
				}
			}
		});
		return result;
	},
	checkTypeCodeText:Oit.msg.eve.eventType.checkTypeCode
});