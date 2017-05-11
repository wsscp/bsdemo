Ext.define("bsmes.view.RoleList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.rolelist',
	store : 'RoleStore',
	selModel : {
		mode:'SINGLE'
	},
	selType : 'checkboxmodel',
	columns : [{
		text : Oit.msg.bas.role.code,
		dataIndex : 'code',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.role.name,
		dataIndex : 'name',
		editor : 'textfield'
	},{
		text : Oit.msg.bas.org.orgCode,
		dataIndex : 'orgCode'
	},{
		text : Oit.msg.bas.org.name,
		dataIndex : 'orgName'
	},{
		text : Oit.msg.bas.role.description,
		dataIndex : 'description',
		editor : 'textfield'
	}],
	actioncolumn : [{
    	itemId : 'edit'
    },{},{
    	itemId:'edit',
    	tooltip:'生产线分配',
    	getClass :function(value,metadata,record,rowIndex,colIndex,store){
			return "icon_detail";
    	},
    	handler:function(grid,rowIndex){
    		var data=grid.getStore().getAt(rowIndex).getData()
    		assignEquip(data);
    	}
    	
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	},{
		itemId : 'assignResources',
		text:Oit.msg.bas.role.assignResources
	} ],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.role.name,
		        name: 'name'
		    },{
		        fieldLabel: Oit.msg.bas.role.org,
		        name: 'orgCode'
		    }]
		}, {
			itemId : 'search'
		} ]
	}]
});

function assignEquip(data){
	var equipStore=Ext.create('bsmes.store.RoleEquipStore');
	equipStore.on('beforeload',function(store, options){
		var params={roleId:data.id};
		Ext.apply(store.proxy.extraParams,params);
	});  
	equipStore.load();
	var equipGrid = new Oit.app.view.Grid({
		stripeRows : true,
		store : equipStore,
		width : '100%',
		height:460,
		defaultEditingPlugin : false,
		columns : [ {
			text : '序号',
			width:'10%',
			xtype : 'rownumberer'
		}, {
			text : '生产线',
			dataIndex : 'code'
		}, {
			text : '名称',
			dataIndex : 'name'
		} ],
		actioncolumn : [{
			itemId : 'remove',
			handler : function(grid, rowIndex) {
				 Ext.MessageBox.confirm('确认', '确认修改吗?', function(btn){
					 if (btn == 'yes'){
						 var result=grid.getStore().getAt(rowIndex).getData();
						 Ext.Ajax.request({
			   				 url:'/bsmes/bas/role/deleteRoleEquip',
			   				 method:'GET',
			   				 params:{'id':result.id},
			   				 success:function(response){
			   					equipStore.reload();
			   				 }
			   			  });
					 }
				 });
			}
		} ],
		dockedItems : [ {
			xtype : 'toolbar',
			dock : 'top',
			items : [{
				xtype : 'hform',
				items : [{
					xtype:'panel',
					html:'生产线:&nbsp;&nbsp;'
				},{
					id:'comboxId',
					xtype : 'combo',
					mode : 'remote',
					displayField : 'name',
					valueField:'id',
					selectOnFocus : true,
					forceSelection:true,
					hideTrigger : true,
					minChars : 1,
					store:Ext.create('bsmes.store.EquipStore'),
					listeners: { 
						'beforequery': function (queryPlan, eOpts) {
							var me=this;
							var url = 'role/equip/'+queryPlan.query;
							me.getStore().getProxy().url=url;
						}
					}
				}]
			}, {
				itemId : 'add',
				handler:function(){
					var comb=Ext.getCmp('comboxId');
					var val=comb.value;
					if(val==null || val==''){
						return;
					}
					Ext.Ajax.request({
						 url:'/bsmes/bas/role/insertRoleEquip',
						 params:{'roleId':data.id,'equipId':comb.value},
						 success:function(response){
							 var result=Ext.decode(response.responseText);
							 if(result=='processerExist'){
								 comb.setValue('');
							 }else{
								 equipStore.reload();
							 }
						 }
					  });
				}
			}]
		} ]
	});
	new Ext.window.Window({
		title : '生产线分配' ,
		width : 750,
		height : 500,
		layout : 'border',
		modal : true,
		plain : true,
		frame : true,
		items : [ {
			region : 'center',
			items : equipGrid
		} ]
	}).show();
}
