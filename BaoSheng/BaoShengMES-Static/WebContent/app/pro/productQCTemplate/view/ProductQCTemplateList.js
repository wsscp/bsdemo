Ext.define('bsmes.view.ProductQCTemplateList',{
	extend: 'Oit.app.view.Grid',
	alias : 'widget.productQCTemplateList',
	store : 'ProductQCTemplateStore',
	id:'productQCTemplateList',
	defaultEditingPlugin : false,
	columnLines: true,
	viewConfig: {
		enableTextSelection : true,
        stripeRows: true
    },
    initComponent: function (){
    	 this.columns =[{
    		 				text :Oit.msg.pro.productQCTemplate.name,
    		 				dataIndex: 'name'
    	 				},{
    	 					 text:Oit.msg.pro.productQCTemplate.testMethod,
    	 					 columns: [{
    	 						 			text     : Oit.msg.pro.productQCTemplate.productCode,
    	 						 			dataIndex: 'productCode'
    	 					    		},{
    	 					    			text     : Oit.msg.pro.productQCTemplate.wireRequest,
    	 						 			dataIndex: 'wireRequest'
    	 					    		},{
    	 					    			text     :Oit.msg.pro.productQCTemplate.preProcess,
    	 					    			dataIndex: 'preProcess'
    	 					    		},{
    	 					    			text     : Oit.msg.pro.productQCTemplate.environmentParameter,
    	 					    			dataIndex: 'environmentParameter'
    	 					    		},{
    	 					    			text     : Oit.msg.pro.productQCTemplate.environmentValue,
    	 					    			dataIndex: 'environmentValue'
    	 					    		},{
    	 					    			text     : Oit.msg.pro.productQCTemplate.matRequest,
    	 					    			dataIndex: 'matRequest'
    	 					    		},{
    	 					    			text     : Oit.msg.pro.productQCTemplate.equipRequest,
    	 					    			dataIndex: 'equipRequest'
    	 					    		}]
    	 					},{
    	 						text:Oit.msg.pro.productQCTemplate.performanceRequ,
    	 						columns: [{
    	 						 			text     : Oit.msg.pro.productQCTemplate.characterDesc,
    	 						 			dataIndex: 'characterDesc'
    	 								 },{
    	 									 text : Oit.msg.pro.productQCTemplate.characterValue,
    	 									 dataIndex: 'characterValue'
    	 								 }]
    	 				},{
    	 					text:Oit.msg.pro.productQCTemplate.refContent,
    	 	                dataIndex: 'refContent'
    	 				},{
    	 					text:Oit.msg.pro.productQCTemplate.remarks,
    	 	                dataIndex: 'remarks'
    	 				}];
    	 this.callParent();
    },
    actioncolumn : [{
		itemId : 'edit',
		handler : function(grid, rowIndex) {
			editQCTemp(grid,rowIndex);
		}
    },'',{
    	itemId : 'remove',
    	handler:function(grid,rowIndex){
    		Ext.MessageBox.confirm('确认', '确定要删除吗?',  function(btn){
    			if (btn == 'yes'){
    				 Ext.Ajax.request({
        				 url:'/bsmes/pro/productQCTemplate/delete',
        				 method:'GET',
        				 params:{'id':grid.getStore().getAt(rowIndex).getData().id},
        				 success:function(response){
        					 grid.getStore().reload();
        				 },
        				 failure : function(response, options) {  
                             Ext.MessageBox.alert('失败', '错误编号：' + response.status);  
                         } 
        			  });
    			}
    		});
    	}
    }],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			title : '查询条件',
			xtype : 'fieldset',
			width : '100%',
			items : [ {
				xtype : 'form',
				width : '100%',
				layout : 'vbox',
				buttonAlign : 'left',
				labelAlign : 'right',
				bodyPadding : 5,
				defaults : {
					xtype : 'panel',
					width : '100%',
					layout : 'hbox',
					defaults : {
						labelAlign : 'right'
						}
					},
					items : [{
						items : [ {
									fieldLabel: Oit.msg.pro.productQCTemplate.name,
									xtype : 'textfield',
									name: 'name'
							},{
									fieldLabel: Oit.msg.pro.productQCTemplate.productCode,
									name: 'productCode',
									xtype : 'combobox',
									editable:false,  
									mode:'remote',
									displayField:'text',
									valueField : 'productCode',
									triggerAction :'all',
									store:new Ext.data.Store({
										fields:[{name:'text'},{name:'productCode',mapping:'value'}],
										autoLoad:true,
										proxy:{
										  type: 'rest',
										  url:'productQCTemplate/product/0'
									}
								})
							}]
						}],
						buttons : [{
							itemId : 'search',
							text : Oit.btn.search
						}, {
							itemId:'reset',
							text : '重置',
							handler : function(e) {
								this.up("form").getForm().reset();
							}
						},{
							itemId : 'addProductTemp',
							text:'添加',
							handler:function(e){
								addProductQCTemp();
							}
						},{
                            itemId:'export',
                            text:Oit.btn.export
                        }]

					} ]
				}]
			}]
});

function editQCTemp(grid,rowIndex){
	var formPanel=getFormPanel(true);
	var win = getFormWindow(formPanel,grid);
	formPanel.loadRecord(grid.getStore().getAt(rowIndex));
	win.show();
}
function addProductQCTemp(){
	var formPanel=getFormPanel(false);
	var win = getFormWindow(formPanel,null);
	win.show();
}
function getFormPanel(isDisable){
	return new Ext.form.Panel({
		bodyPadding: "10 10 10",
		frame: false,
		layout:"form",
		fieldDefaults: {
	         labelAlign: 'left',
	    },
		items:[{
					xtype: 'hiddenfield',
					name: 'id'
				},
				new Ext.form.TextField({
						fieldLabel:Oit.msg.pro.productQCTemplate.name,
						allowBlank: false,  
						blankText:'实验名称不能为空',
						emptyText:'请输入实验名称',
						name:'name'
		        }),{
					fieldLabel: Oit.msg.pro.productQCTemplate.productCode,
					width:'100%',
					name: 'productCode',
					xtype : 'combobox',
					editable:false,  
					mode:'remote',
					displayField:'text',
					valueField : 'productCode',
					triggerAction :'all',
					allowBlank: false, 
					emptyText:"请输入产品代码",
					blankText:'产品代码不能为空',
					store:new Ext.data.Store({
						fields:[{name:'text'},{name:'productCode',mapping:'value'}],
						autoLoad:true,
						proxy:{
						  type: 'rest',
						  url:'productQCTemplate/product/1'
						}
					})
				},{
		            fieldLabel:Oit.msg.pro.productQCTemplate.wireRequest,
					xtype: 'textfield',
					name:'wireRequest'
		        },{
					fieldLabel:Oit.msg.pro.productQCTemplate.preProcess,
					xtype: 'textfield',
					name:'preProcess'
				},{
					 fieldLabel:Oit.msg.pro.productQCTemplate.environmentParameter,
					 xtype: 'textareafield',
					 anchor : '80%',
					 grow: true,
					 name:'environmentParameter'
				},{
					fieldLabel:Oit.msg.pro.productQCTemplate.environmentValue,
					 xtype: 'textareafield',
					 anchor : '80%',
					 grow: true,
					name:'environmentValue'
				},{
					fieldLabel:Oit.msg.pro.productQCTemplate.matRequest,
					xtype: 'textfield',
					name:'matRequest'
				},{
					fieldLabel:Oit.msg.pro.productQCTemplate.equipRequest,
					xtype: 'textfield',
					name:'equipRequest'
				},{
					fieldLabel:Oit.msg.pro.productQCTemplate.characterDesc,
					 xtype: 'textareafield',
					 anchor : '80%',
					 grow: true,
					name:'characterDesc'
				},{
					fieldLabel:Oit.msg.pro.productQCTemplate.characterValue,
					 xtype: 'textareafield',
					 anchor : '80%',
					 grow: true,
					name:'characterValue'
				},{
					fieldLabel:Oit.msg.pro.productQCTemplate.refContent,
					xtype: 'textfield',
					name:'refContent'
				},{
					 fieldLabel:Oit.msg.pro.productQCTemplate.remarks,
					 xtype: 'textfield',
			         name: 'remarks'
				}
		]
	});
}
function getFormWindow(formPanel,grid){
	var win = new Ext.window.Window({
        autoShow: true,
        title:Oit.msg.pro.productQCTemplate.productQCTest,
        width: 750,
        height: document.body.scrollHeight-10,
        overflowY:"auto",
        modal: true,
        items: formPanel,
        buttons:[{
                	 text:Oit.btn.ok,
                	 handler:function(){
	            		  var form = formPanel.getForm();
	            		  if (form.isValid()) {
	            			  Ext.Ajax.request({
	            				 url:'/bsmes/pro/productQCTemplate/update',
	            				 method:'GET',
	            				 params:{'jsonText':Ext.encode(form.getValues())},
	            				 success:function(response){
	            					 var result= Ext.decode(response.responseText);
	            					 if(result=='invalid'){
	            						 Ext.Msg.alert('info','实验名称重复');
	            						 return;
	            					 }else if(result=='update'){
	            						 Ext.Msg.alert('info','产品QC检验内容模板修改成功');
	            					 }else{
	            						 Ext.Msg.alert('info','产品QC检验内容模板新增成功');
	            						 grid=Ext.getCmp('productQCTemplateList');
	            					 }
	            				     grid.getStore().reload();
	            					 win.close();
	            				 },
	            				 failure : function(response, options) {  
	                                 Ext.MessageBox.alert('修改失败', '错误编号：' + response.status);  
	                             } 
	            			  });
	            		  }
           		 
                	 }
                 },{text:Oit.btn.cancel, handler:function(){win.close();}}]
    });
	return win;
}