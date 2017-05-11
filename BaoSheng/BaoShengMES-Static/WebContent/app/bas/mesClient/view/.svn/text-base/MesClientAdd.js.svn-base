Ext.define('bsmes.view.MesClientAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.mesClientadd',
	title: Oit.msg.bas.mesClient.addForm.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.bas.mesClient.clientMac,
        name: 'clientMac',
        allowBlank:false
//        vtype:'checkUnique'
    },{
        fieldLabel: Oit.msg.bas.mesClient.clientIp,
        name: 'clientIp'
    },{
        fieldLabel: Oit.msg.bas.mesClient.clientName,
        name:'clientName'
    },{
        fieldLabel: Oit.msg.bas.mesClient.printNum,
        name:'printNum',
        value:1
    }]
});

//Ext.apply(Ext.form.VTypes,{
//	checkUnique:function(value, field) {
//        if (value === undefined || value === null || value =='') {
//            return false;
//        }
//		var result = false;
//		Ext.Ajax.request({
//			url:'/bsmes/bas/mesClient/checkUnique/'+value,
//			method:'GET',
//			async: false,
//			success: function(response) {
//				var data = Ext.decode(response.responseText);
//				if(data && !data.exists){
//					result = true;
//				}
//			}
//		});
//		return result;
//	},
//	checkUniqueText:'该数据已存在!'
//});