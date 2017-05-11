Ext.define('bsmes.controller.MesClientManEqipController', {
	extend : 'Oit.app.controller.GridController',
	view : 'mesClientManEqiplist',
	editview : 'mesClientManEqipedit',
	addview  : 'mesClientManEqipadd',
	views : [ 'MesClientManEqipList', 'MesClientManEqipEdit' ,'MesClientManEqipAdd'],
	stores : [ 'MesClientManEqipStore' ],
	listController:"MesClientsController",
	getListController:function(){
		return bsmes.app.getController(this.listController);
	},
	doAdd: function() {
		var me = this;
		var record = Ext.create(me.getGrid().getStore().model);
		var data = me.getListController().getSelectedData();
        if(data == null){
            return;
        }
		record.set( "mesClientId", data[0].data.id);
		record.set("mesClientName",data[0].data.clientName);
		me.getAddView().show();
		me.getAddForm().loadRecord(record);
	},
	onFormAdd: function(btn) {
		var me = this;
		var form = me.getAddForm();
		var data = me.getListController().getSelectedData();
		var mesClientId = data[0].data.id;
		var eqipId = form.getValues().eqipId;
		if(form.isValid()){
			Ext.Ajax.request({
				async: false,
				url:'mesClientManEqip/checkUnique/'+mesClientId+'/'+eqipId,
				method:'GET',
				success:function(response){
					me.getGrid().getStore().reload();
					me.getAddView().close();
				},
				failure:function(response){
					Ext.Msg.alert("错误信息","该生产线已添加到该终端");
				}
			});
		}
    },
	onFormSave: function(btn) {
		var me = this;
		var form = me.getEditForm();
		var eqipId = form.getValues().eqipId;
		if(form.isValid()){
			Ext.Ajax.request({
				url:"mesClientManEqip/"+form.getValues().id,
				params:{
					equipId:eqipId
				},
				method:'POST',
				success:function(response){
					me.getGrid().getStore().reload();
					me.getEditView().close();
				},
				failure:function(response){
					Ext.Msg.alert("网络错误!")
				}
			});
		}
	}
});