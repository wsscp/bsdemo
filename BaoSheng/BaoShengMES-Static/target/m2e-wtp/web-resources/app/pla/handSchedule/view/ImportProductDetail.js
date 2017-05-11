/**
 * 查看导入详情
 */

Ext.define('bsmes.view.ImportProductDetail', {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.importProductDetail',
	store : 'ImportProductStore',
	itemId : 'importProductDetail',
	defaultEditingPlugin : false,
	overflowY : 'auto',
	overflowX : 'auto',
	autoScroll : true,
	rowLines : true,
	columnLines : true,
	height : document.body.scrollHeight - 71,
	columns : [{
				text : '系列名',
				dataIndex : 'seriesName',
				flex : 2,
				minWidth : 1,
				menuDisabled : true,
				renderer : function(value){
					return value.toUpperCase();
				}
			}, {
				text:'产品表导入状态',
				flex : 1.6,
				dataIndex:'productStatus',
				menuDisabled : true,
				minWidth : 50,
				renderer : function(value) {
					switch(value){
					case '0':
						return '<a style="color: red">需修改</a>';
					case '1':
					    return '<a style="color: blue">导入成功</a>';
					default:
						return '未导入';
					}
				}
			}, {
				text : '物料表导入状态',
				flex : 1.6,
				dataIndex : 'mpartStatus',
				minWidth : 50,
				menuDisabled : true,
				renderer : function(value) {
					switch(value){
					case '0':
						return '<a style="color: red">需修改</a>';
					case '1':
					    return '<a style="color: blue">导入成功</a>';
					default:
						return '未导入';
					}
				}
			}, {
				text : '输入输出表导入状态',
				flex : 1.8,
				dataIndex : 'insertMpartStatus',
				minWidth : 60,
				menuDisabled : true,
				renderer : function(value) {
					switch(value){
					case '0':
						return '<a style="color: red">需修改</a>';
					case '1':
					    return '<a style="color: blue">导入成功</a>';
					default:
						return '未导入';
					}
					}
			}, {
				text : '单元操作表导入状态',
				dataIndex : 'processStatus',
				flex : 1.8,
				minWidth : 60,
				menuDisabled : true,
				renderer : function(value) {
					switch(value){
					case '0':
						return '<a style="color: red">需修改</a>';
					case '1':
					    return '<a style="color: blue">导入成功</a>';
					default:
						return '未导入';
					}
				}
			}, {
				text : '生产线表导入状态',
				dataIndex : 'scxStatus',
				flex : 1.8,
				minWidth : 55,
				menuDisabled : true,
				renderer : function(value) {
					switch(value){
					case '0':
						return '<a style="color: red">需修改</a>';
					case '1':
					    return '<a style="color: blue">导入成功</a>';
					default:
						return '未导入';
					}
				}
			}, {
				text : '导入人',
				dataIndex : 'createUserCode',
				flex : 1,
				minWidth : 35,
				menuDisabled : true
			}, {
				text : '导入日期',
				dataIndex : 'createTime',
				xtype : 'datecolumn',
				format : 'Y-m-d',
				flex : 1.6,
				minWidth : 100,
				menuDisabled : true
			}, {
				text : '错误日志位置',
				dataIndex : 'location',
				flex : 5,
				minWidth : 160,
				menuDisabled : true
			}],
   dockedItems: [{
	            xtype: 'toolbar',
	            dock: 'top',
	            items: [{
	            	title : '查询条件',
	            	width : '100%',
					xtype : 'fieldset',
					height : 110,
					collapsible : true,
					width : '99%',
					items : [{
						xtype : 'hform',
						layout : 'hbox',
						buttonAlign : 'left',
						labelAlign : 'right',
						bodyPadding : 5,
						defaults : {
							xtype : 'panel',
							width : '100%',
							layout : 'column',
							defaults : {
								width : 100,
								padding : 1,
								labelAlign : 'right'
							}
						},
						items: [{
							fieldLabel : '系列名',
							name : 'seriesName',
							xtype: 'combobox',
							width : 280,
							labelWidth:50,
							allowBlank : false,
							displayField: 'seriesName',
							valueField: 'seriesName',
							//multiSelect:true,
							mode: 'local', 
							margin: '10 20 10 20',  
							minChars: 1 ,
							typeAhead:true,
							triggerAction:'all',
							mode : 'local',
							store:new Ext.data.Store({
								  autoLoad:false,
								  fields:['seriesName','seriesName'],
								  		proxy : {										
								  			type : 'rest',
											url : 'customerOrderItem/seriesName'
									},
									sorters : [{
										property : 'seriesName',
										direction : 'ASC'
									}]											
								}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											var value = e.query;
											var regExp =new RegExp("^.*"+value+".*$", 'i');
											if (value != null && value != '') {
												combo.store.filterBy(function(record, id) {
															var text = record.get('seriesName');
															return regExp.test(text);
														});
											} else {
												combo.store.clearFilter();
											}
											combo.onLoad();
											combo.expand();
											return false;
										}
									}
								}
						},
						{
							fieldLabel : '导入人',
							name : 'createUserCode',
							xtype: 'combobox',
							width : 280,
							labelWidth:50,
							allowBlank : false,
							displayField: 'name',
							valueField: 'createUserCode',
							//multiSelect:true,
							mode: 'local', 
							margin: '10 20 10 20',  
							minChars: 1 ,
							typeAhead:true,
							triggerAction:'all',
							mode : 'local',
							store:new Ext.data.Store({
								  autoLoad:false,
								  fields:['createUserCode','name'],
								  		proxy : {										
								  			type : 'rest',
											url : 'customerOrderItem/userName'
									},
									sorters : [{
										property : 'name',
										direction : 'ASC'
									}]											
								}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											var value = e.query;
											if (value != null && value != '') {
												combo.store.filterBy(function(record, id) {
															var text = record.get('seriesName');
															return (text.indexOf(value) != -1);
														});
											} else {
												combo.store.clearFilter();
											}
											combo.onLoad();
											combo.expand();
											return false;
										}
									}
								}
						},
						{
						 fieldLabel : '导入日期',
						 width : 250,
						 labelWidth : 80,
						 margin : '10 20 10 20',
						 name : 'createTime',
					     xtype : 'datefield',
						 itemId : 'importDate',
						 format : 'Y-m-d'	
						}],
						buttons: [{
							text : '查找',
							itemId : 'search',
							handler: function(e){
								var store=Ext.ComponentQuery.query('#importProductDetail')[0].getStore()
								.load();
							}
						},
						{
							itemId : 'reset',
							text : '重置',
							handler : function(e) {
								this.up("form").getForm().reset();
								}		
					}]
					
	            }]
	        }]
	   
   }],
   initComponent : function() {
		var me = this;
		this.callParent(arguments);
	}
});

