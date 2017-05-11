Ext.define("bsmes.view.EventTypeList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.eventTypelist',
	store : 'EventTypeStore',
	columns : [ {
		text : Oit.msg.eve.eventType.code,
		dataIndex : 'code'
	}, {
		text : Oit.msg.eve.eventType.name,
		editor : 'textfield',
		dataIndex : 'name'
	} , {
		text : '是否在终端显示',
		editor : 'textfield',
		dataIndex : 'needShow'
	} ],
	tbar :[{
		itemId : 'add'
	},{
		itemId : 'remove'
	},{
		itemId : 'eventTypeDesc',
		text : '添加事件明细定义',
		iconCls : 'icon_add'
	}],
	actioncolumn : [{
		itemId : 'edit'
	},{},{
		itemId : 'eventProcess',
		tooltip : Oit.msg.eve.eventType.eventProcess,
		handler : function(grid, rowIndex) {
			eventTypeId = grid.getStore().getAt(rowIndex).getData().id;
			showProcess(eventTypeId);
		},
		getClass :function(value,metadata,record,rowIndex,colIndex,store){
				return "icon_detail";
		}
	}],

});
var eventTypeId = null; //
var status=0; //判断是员工或者角色(员工：0 角色：1)

/**
 * RowEditing 事件
 */
var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
		 cancelEdit: function(){
			 var me = this;
			 if (me.editing) {
				  me.getEditor().cancelEdit();
                  var record = me.context.record; 
                  var id = record.data.id;
                  if (typeof(id)=='undefined') {
                	   var grid = me.context.grid;
                       var items = grid.getSelectionModel().getSelection();
                       Ext.each(items, function(item) {
                    	   grid.store.remove(item);
                       })
                  }
			 }
		 }
   });
function showProcess(id) {
	status=0;
	var store = new Oit.app.data.GridStore({
		fields : [ 'id', 'processSeq', 'processType','eventTypeId','stepInterval'],
		proxy : {
			type : 'rest',
			url : 'eventProcess/getProcess/' + id,
			reader : {
				type : 'json',
				root : 'rows'
			}
		}
	});
	var combobox= new Ext.form.ComboBox({
		 selectOnFocus:true,
		 name:'processType',
		 store:new Oit.app.data.GridStore({
				fields : ['text','value'],
				proxy : {
					type : 'rest',
					url : 'eventProcess/processType'
				} 
			}),
		  editable : false,
		  valueField:'value',
		  displayField: 'text'
	 })
	var grid = new Oit.app.view.Grid({
		stripeRows : true,
		selType : 'checkboxmodel',
		store : store,
		width : '100%',
		height:420,
		defaultEditingPlugin : false,
		columns : [{
			text : Oit.msg.eve.eventType.processSeq,
			dataIndex : 'processSeq'
		},{
			text : Oit.msg.eve.eventType.processType,
			dataIndex : 'processType',
			renderer:function(value, cellmeta, record){
				if (value == 'SMS'){
					return '短信';
				}
				if (value == 'MESSAGE') {
					return '系统消息';
				}
				if (value == 'EMAIL') {
					return '邮件';
				}
				if (value == 'SMSPLUSMESSAGE') {
					return '短息，系统消息';
				}
				if (value == 'EMAILPLUSMESSAGE') {
					return '邮件，系统消息';
				}
				if (value == 'SMSPLUSEMAIL') {
					return '短信，邮件';
				}
				if(value=='ALL'){
					return '短信，邮件，系统消息';
				}
			},
			editor:combobox
		},{
			text : Oit.msg.eve.eventType.stepInterval,
			dataIndex : 'stepInterval',
			editor:new Ext.form.NumberField({
			    decimalPrecision : 2,//精确到小数点后两位  
                allowDecimals : true,
				name:'stepInterval'
            })
			
		}],
		plugins: [rowEditing],
		listeners:{  
			itemdblclick: function(dataview, record, item, index, e){  
				 var val=record.data.processType;
					if (val == 'SMS'){
						val= '短信';
					}
					if (val == 'MESSAGE') {
						val='系统消息';
					}
					if (val == 'EMAIL') {
						val='邮件';
					}
					if(val == 'ALL'){
						val='所有';
					}
					combobox.setValue(val);
			}
		},
		actioncolumn : [ {
			itemId : 'edit',
			handler : function(grid, rowIndex) {
				rowEditing.startEdit(rowIndex, 0);
			}
		},'',{
			itemId : 'remove',
			handler : function(grid, rowIndex) {
				var data=grid.getStore().getAt(rowIndex).getData();
				if(data){
					 Ext.Ajax.request({
		   				 url:'/bsmes/eve/eventProcess/deleteEventProcess',
		   				 method:'GET',
		   				 params:{'id':data.id,'eventTypeId':data.eventTypeId},
		   				 success:function(response){
		   					 if(response){
		   					   var respText = Ext.decode(response.responseText); 
		   						 if(respText=='unComplete'){
		   							 Ext.Msg.alert('info','事件未处理，无法删除该步骤');
		   							 return;
		   						 }else{
		   							store.reload();
		   						 }
		   					 }
		   				 }
		   			  });
				}
			}
		},'',{
			itemId : 'eventPerson',
			tooltip : Oit.msg.eve.eventType.eventPerson,
			iconCls : 'icon_detail',
			handler : function(grid, rowIndex) {
				this.up('.window').close();
				showPerson(store.getAt(rowIndex).getData().id);
			}
		} ],
		dockedItems:[{
			xtype : 'toolbar',
			dock : 'top',
			items : [{
				itemId : 'add',
				handler : function(e){
					rowEditing.cancelEdit();
                    store.insert(store.totalCount, Ext.create(store.model,{processSeq:store.totalCount+1}));
                    rowEditing.startEdit(store.totalCount, 3);
				}
			},{
				itemId : 'back',
				handler : function(e){
					this.up('.window').close();
				}
			}]		
		}]
	});
	Ext.each(grid.dockedItems.items, function(item, index) {
		if (item.xtype == 'pagingtoolbar') {
			item.hidden = true;
		}
	});
	grid.on('edit', function(editor,e){
	    var data=e.record.data;
	    var proType=combobox.value;
    	if(proType==null || proType==''){
    		e.store.remove(e.record);
    		return;
    	}else{
    		Ext.Ajax.request({
				 url:'/bsmes/eve/eventProcess/insertOrUpdate',
				 params:{'id':data.id,'eventTypeId':id,'processType':proType,'stepInterval':data.stepInterval},
				 success:function(response){
					 if(data.id==null || data.id==''){
 						  Ext.Msg.alert('提示','添加成功');
    				 }else{
 						  Ext.Msg.alert('提示','修改成功');
    				 }
    				 store.reload();
				 }
			  });
    	}
    
	});
	getWindow(grid,Oit.msg.eve.eventType.eventProcess).show();
}
function showPerson(id) {
	var personStore = new Oit.app.data.GridStore({
		fields : [ 'id', 'processer', 'type','eventProcessId', 'name'],
		proxy : {
			type : 'rest',
			url : 'eventProcesser/getPerson/' + id,
			reader : {
				type : 'json',
				root : 'rows'
			}
		}
	});
	
	var personGrid = new Oit.app.view.Grid({
		stripeRows : true,
		selType : 'checkboxmodel',
		store : personStore,
		width : '100%',
		defaultEditingPlugin : false,
		height:420,
		columns : [ {
			text : Oit.msg.eve.eventType.rowNum,
			xtype : 'rownumberer',
			width : 100
		}, {
			text : Oit.msg.eve.eventType.eventPerson,
			dataIndex : 'name',
			editor : 'textfield'
		}, {
			text : Oit.msg.eve.eventType.personType,
			dataIndex : 'type',
			renderer:function(value){
				if (value == 'ROLE'){
					return '角色';
				}
				if (value == 'USER') {
					return '用户';
				}
			},
			editor : 'textfield'
		} ],
		actioncolumn : [ {
			itemId : 'remove',
			handler : function(grid, rowIndex) {
				var data=grid.getStore().getAt(rowIndex).getData();
				if(data){
					 Ext.Ajax.request({
		   				 url:'/bsmes/eve/eventProcesser/deleteEventProcesser',
		   				 method:'GET',
		   				 params:{'id':data.id},
		   				 success:function(response){
		   					personStore.reload();
		   				 }
		   			  });
				}
			}
		} ],
		dockedItems : [ {
			xtype : 'toolbar',
			dock : 'top',
			items : [ {
				xtype : 'hform',
				items : [{
					xtype : 'radiogroup',
					items : [ {
						boxLabel : Oit.msg.eve.eventType.employee,
						checked :true,
						name : 'status',
						inputValue : '0'
					},{
						boxLabel : Oit.msg.eve.eventType.role,
						name : 'status',
						inputValue : '1'
					}],
					listeners:{
						'change':function(){
							Ext.each(this.items.items, function(item, index) {
								if(item.checked == true){
									status=item.inputValue;
								}
							});
						}
					}
				}, {
					xtype : 'label',
					width : 20
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
					store:Ext.create('bsmes.store.EmployeeUserStore'),
					listeners: { 
						'beforequery': function (queryPlan, eOpts) {
							var me=this;
							var url = 'eventType/getEmployeeOrRole/'+queryPlan.query+'/'+status+'/';
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
						 url:'/bsmes/eve/eventProcesser/insert',
						 params:{'status':status,'processer':comb.value,'eventProcessId':id,'eventTypeId':eventTypeId},
						 success:function(response){
							 var result=Ext.decode(response.responseText);
							 if(result=='processerExist'){
								 var comb=Ext.getCmp('comboxId');
								 comb.setValue('');
							 }else{
								 personStore.reload();
							 }
						 }
					  });
				}
			}, {
				itemId : 'back',
				handler : function(grid, rowIndex){
					this.up('.window').close();
					showProcess(eventTypeId);
				}
			} ]
		} ]
	});
	Ext.each(personGrid.dockedItems.items, function(item, index) {
		if (item.xtype == 'pagingtoolbar') {
			item.hidden = true;
		}
	});
	personGrid.on('edit', function(editor,e){
	    var data=e.record.data;
		Ext.Ajax.request({
			 url:'/bsmes/eve/eventProcesser/update',
			 params:{'id':data.id,'processer':data.processer,'type':data.type},
			 success:function(response){
				 if(response){
					 e.record.commit();
				 }
			 }
		  });
	});
	getWindow(personGrid,Oit.msg.eve.eventType.eventPerson).show();
}
/**
 * 弹出窗
 * @param grid
 * @returns {Ext.window.Window}
 */
function getWindow(grid,title) {
	var win = new Ext.window.Window({
		title : title ,
		width : 750,
		height : 420,
		layout : 'border',
		modal : true,
		plain : true,
		frame : true,
		items : [ {
			region : 'center',
			items : grid
		} ]
	});
	return win;
}
