Ext.define("bsmes.view.SemiFinishedProductsUsingView",{
	extend: 'Oit.app.view.Grid',
	alias: 'widget.semiFinishedProductsUsingView',
	id : 'SemiFinishedProductsUsingView',
	store : 'SemiFinishedProductsUsingStore',
	defaultEditingPlugin : false,
	selType : 'checkboxmodel',
	selModel : {
		mode : "SIMPLE" // "SINGLE"/"SIMPLE"/"MULTI"
		// checkOnly : true
	},
	columns : [{
		dataIndex : 'contractNo',
		flex : 0.5,
		text : '合同号',
		sortable : false,
		renderer: function(value, metaData, record){
			return value.substr(value.length-4,value.length);
		}
	},{
		dataIndex : 'matCode',
		flex : 1.5,
		text : '物料代码',
		hidden :true,
		sortable : false,
		renderer: function(value, metaData, record){	
			metaData.tdAttr='data-qtip="'+value+'"';
			return value;
		}
	},{
		dataIndex : 'matName',
		flex : 1.2,
		sortable : false,
		text : '物料名称',
	},{
		dataIndex : 'productType',
		flex : 1.5,
		sortable : false,
		text : '产品型号'
	},{
		dataIndex : 'productSpec',
		flex : 0.5,
		sortable : false,
		text : '产品规格'
	},{
		dataIndex : 'taskLength',
		flex : 0.5,
		sortable : false,
		text : '长度'
	},{
		dataIndex : 'isUsed',
		flex:0.5,
		sortable : false,
		text : '使用状态'
	}
	,{
		dataIndex : 'wireCoil',
		flex : 0.5,
		sortable : false,
		text : '盘具'
	},{
		dataIndex : 'processName',
		flex : 0.5,
		sortable : false,
		text : '所在工序'
	},{
		dataIndex : 'finishDate',
		flex : 1,
		text : '最后工序结束时间',
		renderer: function(value, metaData, record){
			var date=Ext.Date.format(value,'Y-m-d H:s:i');
			return date;
		}
	},{
		dataIndex : 'locationName',
		flex : 1,
		sortable : false,
		text : '存放位置'
	},{
		dataIndex : 'userName',
		flex : 0.5,
		sortable : false,
		text : '盘点人'
	}],
	dockedItems: [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			title : '查询条件',
			xtype : 'fieldset',
			collapsible : true,
			width : '100%',
			items : [{
				xtype : 'form',
				width : '100%',
				layout : 'hbox',
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
				    fieldLabel : '合同号',
					name : 'contractNo',
					id:'contractNo',
					xtype: 'combobox',
					width : 220,
					labelWidth:70,
					displayField : 'contractNo',
					valueField:'contractNo',
					selectOnFocus : true,
					hideTrigger : true,
					minChars : 1,
					height: 25,
					margin: '10,15,10,10',
					store: new Ext.data.Store({
						fields:['contractNo'],
						proxy:{
							type:'rest',
							url:'SemiFinishedProductsUsing/searchContractNo/1'
						}
					}),
					listeners: {
				    	 beforequery : function(e) {
								var combo = e.combo;
								combo.collapse();
								if (!e.forceAll) {
									var value = e.query;
									if(value==null||value==''){
										value=-1;
									}
									combo.store.getProxy().url='SemiFinishedProductsUsing/searchContractNo'+'/'+value;
									combo.store.load();
									combo.expand();
									return false;
								}
							}
				    }				
				},{
				    fieldLabel : '工序名称',
					name : 'processName',
					id:'processName',
					xtype: 'combobox',
					width : 220,
					labelWidth:70,
					displayField : 'processName',
					valueField:'processName',
					selectOnFocus : true,
					height: 25,
					margin: '10,15,10,10',
					store: new Ext.data.Store({
						fields:['processName'],
						proxy:{
							type:'rest',
							url:'SemiFinishedProductsUsing/searchProcessName'
						}
					})			
				},{
				    fieldLabel : '使用状态',
					name : 'isUsed',
					xtype: 'combobox',
					width : 220,
					labelWidth:70,
					displayField : 'abbr',
					valueField:'name',
					selectOnFocus : true,
					height: 25,
					RawValue:'未使用',
					value : '未使用',
					margin: '10,15,10,10',
					store: new Ext.data.Store({
						fields:['abbr','name'],
						data : [{
							"abbr" : "全部",
							"name" : ""
						}, {
							"abbr" : "已使用",
							"name" : "已使用"
						}, {
							"abbr" : "未使用",
							"name" : "未使用"
						}]
					})			
				}],
				buttons : [{
						itemId : 'search',
						text : '查询'
					}, {
						itemId : 'reset',
						text : '重置',
						handler : function(e){
						this.up("form").getForm().reset();
					}
				},{
					text : '物料定额',
					handler : function(e){
						var thisGrid = this.up('grid');
						var selection = thisGrid.getSelectionModel().getSelection();
						if(selection.length > 0){
							var idArray = selection[0].data.id;
							for(var i=1;i<selection.length;i++){
								idArray = idArray + ',' + selection[i].data.id;
							}
							var win = new Ext.Window({
	                        	title : '物料定额',
	                        	width : Math.round(document.body.scrollWidth / 2),
	            				height : Math.round(document.body.scrollHeight / 2),
	            				items : [{
	            					xtype : 'grid',
	            					store : new Ext.data.Store({
	            						autoLoad : true,
		            					fields : ['MATNAME','QUANTITY'],
		            					proxy : {
											type : 'rest',
											url : 'SemiFinishedProductsUsing/getMatQuan' + '/' + idArray
										}
		            				}),
		            				columns : [{
		            					dataIndex : 'MATNAME',
		            					flex : 1.5,
		            					text : '物料名称'
		            				},{
		            					dataIndex : 'QUANTITY',
		            					flex : 0.5,
		            					text : '物料用量(KG)',
		            					renderer : function(value) {
		            						return value.toFixed(2);
		            					}
		            				}]
	            				}]
	                        });
							win.show();
						}else{
							Ext.Msg.alert(Oit.msg.WARN, '请选择盘点半制品');
						}
					}				
				},{
					text : '定额汇总',
					handler : function(e){
						var win = new Ext.Window({
							title : '定额汇总',
                        	width : document.body.scrollWidth - 200,
            				height : document.body.scrollHeight - 100,
            				items : [{
            					xtype : 'grid',
            					autoScroll : true,
                				height : document.body.scrollHeight - 150,
                				bodyStyle: 'overflow-x:hidden; overflow-y:hidden',
            					store : new Ext.data.Store({
            						autoLoad : true,
	            					fields : ['MATNAME','QUANTITY'],
	            					proxy : {
										type : 'rest',
										url : 'SemiFinishedProductsUsing/getMatQuan' + '/-1'
									}
	            				}),
	            				columns : [{
	            					dataIndex : 'MATNAME',
	            					flex : 1.5,
	            					text : '物料名称'
	            				},{
	            					dataIndex : 'QUANTITY',
	            					flex : 0.5,
	            					text : '物料用量(KG)',
	            					renderer : function(value) {
	            						return value.toFixed(2);
	            					}
	            				}]            				
            				}]
						});
						win.show();
					}
				}
//				,{
//					text : '生成数据',
//					handler : function(e){
//						Ext.Ajax.request({
//							url : 'SemiFinishedProductsUsing/insertAllMat',
//							success : function(response) {
//								Ext.Msg.alert(Oit.msg.PROMPT, '成功');
//							},
//							failure : function(response) {
//								Ext.Msg.alert(Oit.msg.ERROR, '失败');
//							}
//						});
//					}
//				}
				]
			}]
		}]
	}] 	              
	      
});