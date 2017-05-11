Ext.define('bsmes.view.OrgEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.orgedit',
	title: Oit.msg.bas.org.editForm.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.bas.org.orgCode,
        name: 'orgCode',
        xtype:'displayfield'
//        listeners:{
//        	'blur':function(){
//        		if(this.value==null || this.value==""){
//        			return;
//        		}
//        		Ext.Ajax.request({
//        			url:'/bsmes/bas/org/checkOrgCodeUnique/'+this.value+'/',
//        			method:'GET',
//        			async: false,
//        			success: function(response) {
//        				var data = Ext.decode(response.responseText);
//        				if(data && data.checkReult){
//        					Ext.Msg.alert("提示","机构编号重复！");
//        				}
//        			}
//        		});
//        	}
//        }
    },{
        fieldLabel: Oit.msg.bas.org.name + '<font color=red>*</font>',
        name: 'name',
        allowBlank:false 
    },{
        fieldLabel: Oit.msg.bas.org.parentCode,
        name: 'parentCode',
		xtype : 'combobox',
		displayField : 'name',
		valueField : 'orgCode',
		editable : false,
		store : new Ext.data.Store({
			proxy : {
				type : 'rest',
				url : 'employee/getOrgCode',
				reader : {
					type : 'json'
				}
			},
			fields : ['orgCode', 'name']
		})
    },{
        fieldLabel: Oit.msg.bas.org.type,
        name: 'type'
    },{
        fieldLabel: Oit.msg.bas.org.description,
        name: 'description'
    },{
        fieldLabel: 'demo',
        name: 'demo'
    }] 
});

