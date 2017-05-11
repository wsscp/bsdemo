Ext.define('bsmes.view.RoleEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.roleedit',
	title: Oit.msg.bas.role.editForm.title,
	iconCls: 'feed-add',
	formItems: [{
		 fieldLabel: Oit.msg.bas.role.code,
	     name: 'code',
	     allowBlank: false, 
	     vtype:'checkRole',
	     validateOnBlur : true,
	     validateOnChange :false
	},{
        fieldLabel: Oit.msg.bas.role.name,
        name: 'name'
    },{
        fieldLabel: Oit.msg.bas.org.orgCode,
        xtype:'displayfield',
        name: 'orgCode'
    },{
        fieldLabel: Oit.msg.bas.org.name,
        xtype:'displayfield',
        name: 'orgName'
    },{
        fieldLabel: Oit.msg.bas.role.description,
        name: 'description',
        xtype:'textarea'
    }] 
});
Ext.apply(Ext.form.field.VTypes,{
	checkRole:function(value,field){
		if (value === undefined || value === null || value =='') {
            return false;
        }
		var result = false;
		Ext.Ajax.request({
			url:'/bsmes/bas/role/checkRole/'+value+'/',
			method:'GET',
			async: false,
			success: function(response) {
				var data = Ext.decode(response.responseText);
				if(data && data.checkRole){
					result = true;
				}
			}
		});
		return result;
	},
	checkRoleText:Oit.msg.bas.role.addForm.checkRole,
});