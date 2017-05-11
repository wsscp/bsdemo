Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'qtip';
Ext.define('bsmes.view.RoleAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.roleadd',
	title: Oit.msg.bas.role.addForm.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.bas.role.code,
        allowBlank: false, 
        name: 'code',
        vtype:'checkRole',
        validateOnBlur : true,
    	validateOnChange :false
    },{
    	fieldLabel: Oit.msg.bas.role.name,
    	allowBlank: false, 
        name: 'name'
    },{
        fieldLabel: Oit.msg.bas.role.org,
        allowBlank: false, 
        name: 'orgCode',
        vtype:'checkOrgExists',
        validateOnBlur : true,
    	validateOnChange :false
    }
    ,{
        fieldLabel: Oit.msg.bas.role.description,
        name: 'description',
        xtype:'textarea'
    }
    ] 
});

Ext.apply(Ext.form.field.VTypes,{
	checkOrgExists:function(value, field) {
        if (value === undefined || value === null || value =='') {
            return false;
        }
		var result = false;
		Ext.Ajax.request({
			url:'/bsmes/bas/role/checkOrgExists/'+value+'/',
			method:'GET',
			async: false,
			success: function(response) {
				var data = Ext.decode(response.responseText);
				if(data && data.orgExists){
					result = true;
				}
			}
		});
		return result;
	},
	checkOrgExistsText:Oit.msg.bas.role.addForm.orgCodeNotExists,
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