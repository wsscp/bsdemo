Ext.define("bsmes.view.UserList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.userlist',
	id:'userListBindId',
	store : 'UserStore',
	defaultEditingPlugin : false,
	columns : [{
		text : Oit.msg.bas.user.userCode,
		dataIndex : 'userCode'
	}, {
		text : Oit.msg.bas.user.name,
		dataIndex : 'name'
	},{
		text : Oit.msg.bas.user.password,
		sortable: false, 
		dataIndex : 'password'
	},{
		text : Oit.msg.bas.user.status,
		sortable: false, 
		dataIndex : 'status'
	},{
		text : '查看简历',
		dataIndex : 'checkUserInfo',
		sortable : false,
		menuDisabled : true,
		renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
			var me = this;
			var html = '<a style="color:blue;cursor:pointer;" onclick="checkResume(\''
				+ record.get('userCode') + '\')">简历</a>';
			return html;
		}
	},{
		text : '角色',
		sortable: false,
		dataIndex : 'role'
	},{
		text : '所属机构',
    	dataIndex : 'orgName'
	}],
	
	
	
	//编辑
	actioncolumn : [{
    	itemId : 'edit'
    }],
    
//    添加删除
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	},{
		text:'角色绑定',
		handler:function(){
			var grid=Ext.getCmp('userListBindId');//通过id获得组件，cmp是所有组件基类
//			返回正在使用的选择模型model，若没有，则配置创建
//			getSelection：返回一个当前被选择的记录数组
			var selections = grid.getSelectionModel().getSelection();
			var id=null;
			var userCode=null;
			var orgCode=null;
			var name=null;
		    if (selections.length > 0) {
		    	for(var i=0;i<selections.length;i++){
		    		 var row = selections[i];
		             if(row.get('id')){
		            	 id=row.get('id');
		             }
		             if(row.get('userCode')){
		            	 userCode=row.get('userCode');
		             }
		             if(row.get('userCode')){
		            	 orgCode=row.get('orgCode')
		             }
		             if(row.get('name')){
		            	 name=row.get('name')
		             }
		    	}
		    	bindUserRole(id,userCode,orgCode,name);
		    }else{
		    	Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
				return;
		    }
		    grid.getStore().reload();
		}
	},{
		text : '上传简历',
		itemId : 'ImportUserInfo'
	}],
	
	
	
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			id : 'form',
			items: [{
		        fieldLabel: Oit.msg.bas.user.userCode,
		        name: 'userCode'
		    },{
		        fieldLabel: Oit.msg.bas.user.name,
		        name: 'name'
		    },{
		    	fieldLabel : '角色',
		    	xtype:'combobox',
				name : 'role',
				displayField: 'name',
				valueField: 'name',
				labelWidth:30,
				store : new Ext.data.Store({
					autoLoad : false,
					fields : [ 'name', 'id' ],
					proxy : {
						type : 'rest',
						url : 'userRole/role'
					},
					sorters : [ {
						property : 'name',
						direction : 'ASC'
					} ]
				}),
				listeners:{
					expand:function(){
							Ext.ComponentQuery.query('userlist combobox')[0].getStore().load();
				}
			}
		    },{
		    	fieldLabel :'所属机构',
		    	xtype:'combobox',
		    	name :'orgCode',
		    	id: 'orgCode',
		    	displayField: 'orgName',
				valueField: 'orgCode',
				labelWidth:60,
				queryMode: 'local',
				value : Ext.fly('orgInfo').getAttribute('orgCode'),
				store : new Ext.data.Store(
						{
							autoLoad : true,
							fields : [
									'orgCode',
									'orgName' ],
							proxy : {
								type : 'rest',
								url : 'user/getAllOrgCode'
							},
							sorters : [ {
								property : 'orgCode',
								direction : 'ASC'
							} ]
						})
		    }]
		}, {
			itemId : 'search'
		}]
	}],
	initComponent : function() {
		var me = this;
		this.callParent(arguments);
	}
});


function bindUserRole(id,userCode,orgCode,name){
	var roleStore=new Oit.app.data.GridStore({
		fields : [ 'id', 'name', 'code','roleId','description','orgCode','userCode', 'userName'],
		proxy : {
			type : 'rest',
			url : 'userRole/userRoleBind/'+id,
			reader : {
				type : 'json',
				root : 'rows'
			}
		}
	});
	
	var combo=new Ext.form.ComboBox({
		 selectOnFocus:true,
		 store:new Oit.app.data.GridStore({
				fields : ['name','id'],
				proxy : {
					type : 'rest',
					url : 'userRole/role'
				} 
			}),
		  editable : false,
		  valueField:'id',
		  displayField: 'name',
		  listeners:{
			  'select':function(){
				  var me=this;
				  var value=this.value;
				  rowIndex = roleStore.find("id",value);
				  
			  }
		  }
	 })
	var bindGrid = new Oit.app.view.Grid({
		stripeRows : true,
		selType : 'checkboxmodel',
		store : roleStore,
		width : '100%',
		defaultingPlugin : false,
		height:420,
		columns : [ {
			text : '员工号',
			dataIndex : 'userCode',
			width : 100
		}, {
			text : '姓名',
			dataIndex : 'userName',
			width : 100
		}, {
			text :'角色代码',
			dataIndex : 'code',
			or:combo
		},{
			text : '角色名称',
			dataIndex : 'name'
			
		},{
			text : '所属机构',
	    	dataIndex : 'orgCode'
		},{
			text : '描述',
	    	dataIndex : 'description'
	   } ],
	   plugins: [rowing],
		actioncolumn : [ {
		    itemId : 'remove',
			handler : function(grid, rowIndex) {
				var data=grid.getStore().getAt(rowIndex).getData();
				var g=Ext.getCmp('userListBindId');
				Ext.MessageBox.confirm(Oit.msg.WARN, Oit.msg.bas.user.confirmDeleteMsg, function(result){
	                if(result == 'yes'){
	                	if(data){
	                		Ext.Ajax.request({
	       	   				 url:'/bsmes/bas/userRole/delete',
	       	   				 method:'GET',
	       	   				 params:{'id':data.id,'roleId':data.roleId},
	       	   				 success:function(response){
	       	   					 grid.getStore().reload();
	       	   					 g.getStore().reload();
	       	   				 }
	       	   			  });
	                	}
	                }
	            });
			}
		} ],
		dockems : [{
			xtype : 'toolbar',
			dock : 'top',
			items : [{
				itemId : 'add',
				handler:function(){
					rowing.cancelEdit();
					roleStore.insert(roleStore.totalCount, Ext.create(roleStore.model,{userCode:userCode,userName:name,orgCode:orgCode}));
                    rowEditing.startEdit(roleStore.totalCount,1);
				}
			}]
		} ]
	});
	  Ext.each(bindGrid.dockedItems.items, function(item, index) {
			if (item.xtype == 'pagingtoolbar') {
				item.hidden = true;
			}
		});
	  bindGrid.on('edit', function(editor,e){
		 var grid = Ext.getCmp('userListBindId');
		 var data=e.record.data;
      	 var roleId=combo.value;
      	 var index=roleStore.find('name',combo.rawValue);
      	 if(roleId==null || roleId=='' || index>=0){
      		 e.store.remove(e.record);
   	    	return;
      	 }else{
      		 Ext.Ajax.request({
  				 url:'/bsmes/bas/userRole/insertOrUpdate',
  				 params:{'id':data.id,'userId':id,'roleId':roleId},
  				 success:function(response){
  					if(data.id==null || data.id==''){
  						  Ext.Msg.alert('info','添加成功');
  					}else{
  						  Ext.Msg.alert('info','修改成功');
  					}
  					roleStore.reload();
  					grid.getStore().reload();
  				 }
  			  });
      	 }
      	 
		});
	getWindow(bindGrid,'用户角色绑定').show();
}

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
function downLoad(userCode){
	Ext.Ajax.request({
		url : 'user/downLoadUserInfo?userCode='+userCode,
		method : 'GET',
		success : function(response) {
			window.location.href = 'user/downLoadUserInfo?userCode='+userCode;
		},
		failure : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(Oit.msg.WARN, result.message);
		}
	});
}
//查看简历
function checkResume(userCode){
//	Ext.create('bsmes.view.CheckResume').show();
	Ext.Ajax.request({
		url : 'user/getResumeInfo?userCode='+userCode,
		method : 'GET',
		success : function(response) {
			var result = Ext.decode(response.responseText).resume;
			var win = Ext.create('bsmes.view.CheckResume');
			var form = win.down('form').getForm();
			form.setValues({
				userCode : userCode,
				accountProperties : result.accountProperties,
				admissionDate : result.admissionDate,
				birthDate : result.birthDate,
				birthPlace : result.birthPlace,
				education : result.education,
				entryDate : result.entryDate,
				gender : result.gender,
				graduationDate : result.graduationDate,
				homeAddress : result.homeAddress,
				idNumbers: result.idNumbers,
				isAviationCollege : result.isAviationCollege,
				maritalStatus : result.maritalStatus,
				originPlace: result.originPlace,
				phoneNumber: result.phoneNumber,
				politicalClimate: result.politicalClimate,
				school: result.school,
				userName : result.userName
			});
			win.query('grid')[0].getStore().load({
				params : {
					userCode : userCode
				}
			});
			win.query('grid')[1].getStore().load({
				params : {
					userCode : userCode
				}
			});
			win.show();
		},
		failure : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(Oit.msg.WARN, result.message);
		}
	});
}
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