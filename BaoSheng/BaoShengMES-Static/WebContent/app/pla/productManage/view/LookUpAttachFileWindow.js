
Ext.define('bsmes.view.LookUpAttachFileWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.lookUpAttachFileWindow',
	title : '附件查看',
	width : document.body.scrollWidth/2,
	minWidth : 600,
	height : document.body.scrollHeight - 100,
	overflowY : 'auto',
	autoScroll : true,
	padding : '10',
	initComponent : function(){
		var me = this;
		me.loadGridView(me);
		Ext.apply(me, {
			buttons : ['->', {
						text : '关闭',
						scope : me,
						handler : me.close
					}]
		});

		this.callParent(arguments);

	},
	loadGridView : function(me){
		var items = [];
		var contractNoArr = me.contractNo;
		Ext.Ajax.request({
			url:'handSchedule/lookUpAttachFile',
			params:{
				orderItemId : me.orderItemId,
				contractNo : me.contractNo
			},
			success:function(response){
				var data = Ext.decode(response.responseText);
				var gridData = {};
				Ext.Array.each(data, function(record, i){
					gridData[contractNoArr[i]] = {
						'gridData':	record.data
					};
			    });
				for (var key in gridData) {
					var attachGrid = Ext.create('bsmes.view.AttachGrid', {
						store : new bsmes.store.AttachStore()
					});
					attachGrid.getStore().loadData(gridData[key].gridData, false);
					var attachfieldset = Ext.create('Ext.form.FieldSet', {
						title : key,
						items : [attachGrid]
					});
					items.push(attachfieldset);
				}
				me.items.addAll(items);
				me.doLayout();
			}
		});
		
		
	}
});
