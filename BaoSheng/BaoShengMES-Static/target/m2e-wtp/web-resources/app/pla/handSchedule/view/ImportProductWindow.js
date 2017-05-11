Ext.define('bsmes.view.ImportProductWindow', {
			extend : 'Ext.window.Window',
			width : 700,
			layout : 'fit',
			modal : true,
			plain : true,
			title : Oit.msg.pla.customerOrderItem.button.importProduct,
			alias : 'widget.importProductWindow',
			height : 300,
			itemId: 'importProductWindow',
			initComponent : function() {
				var me = this;
				Ext.apply(me, {
							buttons : [
							           {
											itemId : 'detail',
											text : Oit.btn.detail
										}, 
										{
											itemId : 'prcvXml',
											text : '生成工艺文件'
										}, 
							           {
										itemId : 'ok',
										text : Oit.btn.ok
									}, {
										itemId : 'cancel',
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									},{
										itemId : 'reset',
										text : Oit.btn.reset,
										handler: function (e) {
			                               me.down("form").getForm().reset();
			                                }
									}								
									]
						});

				me.items = [{
							xtype : 'form',
							bodyPadding : '12 10 10',
							defaultType : 'textfield',
							defaults : {
								labelAlign : 'left',
							    labelWidth: 120
							},
							url : 'customerOrderItem/importProductsToItemDec',
							enctype : "multipart/form-data",
							items : [{xtype : 'combobox',
								     name : 'seriesName',
								     fieldLabel : '系列名称',
								     displayField: 'seriesName',
									 valueField: 'seriesName',
								     //Width : 120,
								     allowBlank : false,
								     blankText:'请输入系列名',
							         maxLength: 50,
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
										xtype : 'filefield',
										name : 'plmProductDetail',
										fieldLabel : '产品导入',
										labelWidth : 120,
										msgTarget : 'side',
										//allowBlank : false,
										emptyText:'请选择plmProductDetail文件',
										anchor : '100%',
										buttonText : '选择文件'
									},
									{
										xtype : 'filefield',
										name : 'plmMpartDetail',
										fieldLabel : '同步物料',
										emptyText:'请选择plmMpartDetail文件',
										labelWidth : 120,
										msgTarget : 'side',
										allowBlank : true,
										anchor : '100%',
										buttonText : '选择文件'
									},
									{
										xtype : 'filefield',
										name : 'insertMpartObj',
										fieldLabel : '添加输入输出关系',
										emptyText:'请选择insertMpartObj文件',
										labelWidth : 120,
										msgTarget : 'side',
										allowBlank : true,
										anchor : '100%',
										buttonText : '选择文件'
									},
									{
										xtype : 'filefield',
										name : 'plmProcessDetail',
										fieldLabel : '同步工序属性',
										emptyText:'请选择plmProcessDetail文件',
										labelWidth : 120,
										msgTarget : 'side',
										allowBlank : true,
										anchor : '100%',
										buttonText : '选择文件'
									},
									{
										xtype : 'filefield',
										name : 'insertScx',
										fieldLabel : '同步生产线属性',
										emptyText:'请选择insertScx文件',
										labelWidth : 120,
										msgTarget : 'side',
										allowBlank : true,
										anchor : '100%',
										buttonText : '选择文件'
									}]
						}],

				this.callParent(arguments);
			}
		});