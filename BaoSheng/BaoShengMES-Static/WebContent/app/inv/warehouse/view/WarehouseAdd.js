Ext.form.Field.prototype.msgTarget='qtip';
Ext.define('bsmes.view.WarehouseAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.warehouseadd',
	title:Oit.msg.inv.warehouse.addForm.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.inv.warehouse.warehouseCode,
        name: 'warehouseCode',
        vtype:'checkHouseExists',
        allowBlank: false,
        vtypeText:'仓库已存在'
    },{
    	fieldLabel: Oit.msg.inv.warehouse.warehouseName,
        name: 'warehouseName',
        allowBlank: false
    },{
        fieldLabel: Oit.msg.inv.warehouse.address,
        name: 'address',
        allowBlank: false
    },{
    	fieldLabel:Oit.msg.inv.warehouse.type,
        name: 'type',
        xtype : 'combobox',
        displayField: 'name',
	    valueField: 'code',
	    editable:false,
	    store:new Ext.data.Store({
		    fields:['code', 'name'],
		    data : [
		        {"name":'在制品仓库', "code":"WIP"},
		        {"name":'其他', "code":"OTHERS"}
	        ]
		}),
		listeners:{
			'select':function(combo, value, option){
				if(combo.value && combo.value=='WIP'){
					Ext.Ajax.request({
						url:'/bsmes/inv/warehouse/checkType/'+combo.value+'/',
						method : "GET",
						success : function(response) {
							var data = Ext.decode(response.responseText);
							if(data && data.checkExtist){
								Ext.Msg.alert('错误提示','在制品仓库已存在');
								combo.setValue('');
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert('失败', '错误编号：' + response.status);
						}
					});
				}
			}
		}
    }] 
});
Ext.apply(Ext.form.field.VTypes,{
	checkHouseExists:function(value, field) {
        if (value === undefined || value === null || value =='') {
            return false;
        }
		var result = false;
		Ext.Ajax.request({
			url:'/bsmes/inv/warehouse/check/'+value+'/',
			method:'GET',
			async: false,
			success: function(response) {
				var data = Ext.decode(response.responseText);
				if(data && data.codeExtist){
					result = true;
				}
			}
		});
		return result;
	},
});