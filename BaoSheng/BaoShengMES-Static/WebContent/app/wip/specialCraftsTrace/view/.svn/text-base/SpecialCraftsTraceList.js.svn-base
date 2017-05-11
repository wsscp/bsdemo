Ext.define("bsmes.view.SpecialCraftsTraceList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.specialCraftsTraceList',
	store : 'SpecialCraftsTraceStore',
	forceFit : false,	
	defaultEditingPlugin : false,
	columns : [{
				width : 60,
				text : '合同号',
				dataIndex : 'contractNo',
				renderer: function(value, metaData, record){
					return value.substr(value.length-4,value.length);
				}
			}, {
				width : 60,
				text : '经办人',
				dataIndex : 'operator'
			},{
				width : 150,
				text : '单位',
				dataIndex : 'customerCompany',
				renderer: function(value, metaData, record){	
					metaData.tdAttr='data-qtip="'+value+'"';
					return value;
			}
			},{
				width : 250,
				text : '产品型号规格',
				dataIndex : 'productInfo',
				renderer: function(value, metaData, record){	
						metaData.tdAttr='data-qtip="'+value+'"';
						return value;
				}
			},{
				width : 60,
				text : '合同'+'<br>'+'长度',
				dataIndex : 'saleorderLength'
			} ,{
				width : 250,
				text : '工艺代号',
				hidden: true,
				dataIndex : 'craftsCode'
			}, {
				width : 120,
				text : '工序名称',
				dataIndex : 'processName'
			}, {
				width : 380,
				text : '修改操作',
				dataIndex : 'modifyValue',
				renderer: function(value, metaData, record){					
					var modifyInfo=value.split('&');
					metaData.tdAttr = 'data-qtip="' + modifyInfo[0] + '&nbsp'+'<b>改为</b>'+'&nbsp'+'<font color=\'blue\'>'+modifyInfo[1]+
					'</font>'+'"';
					var value=modifyInfo[0]+'&nbsp'+'<b>改为</b>'+'<br>'+'<font color="blue">'+modifyInfo[1]+'</font>';
					return value;
				}
			}, {
				width : 80,
				text : '修改类型',
				dataIndex : 'type'
			}, {
				width : 60,
				text : '修改人',
				dataIndex : 'modifyUserCode'
			}, {
				width : 140,
				text : '修改时间',
				dataIndex : 'modifyTime',
				renderer: function(value, metaData, record){
					var date=Ext.Date.format(value,'Y-m-d H:i:s');
					return date;
				}
			}],
	dockedItems : [{
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
						multiSelect : true,
						//forceSelection:true,
						hideTrigger : true,
						minChars : 1,
						height: 25,
						margin: '10,15,10,10',
						store: new Ext.data.Store({
							fields:['contractNo'],
							proxy:{
								type:'rest',
								url:'specialCraftsTrace/searchContractNo/1'
							}
						}),
						listeners: {
					    	 beforequery : function(e) {
									var combo = e.combo;
									combo.collapse();
									if (!e.forceAll) {
										var value = e.query;
										//console.log(value);
										if(value==null||value==''){
											value=-1;
										}
										combo.store.getProxy().url='specialCraftsTrace/searchContractNo'+'/'+value;
										combo.store.load();
										combo.expand();
										return false;
									}
								}
					    }
				  
					},{
					    fieldLabel : '产品型号',
						name : 'productType',
						id:'productType',
						xtype: 'combobox',
						width : 220,
						labelWidth:70,
						displayField : 'PRODUCT_TYPE',
						valueField:'PRODUCT_TYPE',
						selectOnFocus : true,
						hideTrigger : true,
						minChars : 1,
						height: 25,
						margin: '10,15,10,10',
						store: new Ext.data.Store({
							fields:['PRODUCT_TYPE'],
							proxy:{
								type:'rest',
								url:'specialCraftsTrace/searchProductType/1'
							}
						}),
						listeners: {
					    	 beforequery : function(e) {
									var combo = e.combo;
									combo.collapse();
									if (!e.forceAll) {
										var value = e.query;
										//console.log(value);
										if(value==null||value==''){
											value=-1;
										}
										combo.store.getProxy().url='specialCraftsTrace/searchProductType'+'/'+value;
										combo.store.load();
										combo.expand();
										return false;
									}
								}
					    }
				  
					
					},{
					    fieldLabel : '产品规格',
						name : 'productSpec',
						id:'productSpec',
						xtype: 'combobox',
						width : 220,
						labelWidth:70,
						displayField : 'PRODUCT_SPEC',
						valueField:'PRODUCT_SPEC',
						selectOnFocus : true,
						hideTrigger : true,
						minChars : 1,
						height: 25,
						margin: '10,15,10,10',
						store: new Ext.data.Store({
							fields:['PRODUCT_SPEC'],
							proxy:{
								type:'rest',
								url:'specialCraftsTrace/searchProductSpec/1'
							}
						}),
						listeners: {
					    	 beforequery : function(e) {
									var combo = e.combo;
									var type=Ext.ComponentQuery.query('#productType')[0].value;
									if(type){
										combo.store.getProxy().extraParams ={
											'productType': type
										};
									}				
									combo.collapse();
									if (!e.forceAll) {
										var value = e.query;
										if(value==null||value==''){
											value=-1;
										}
										combo.store.getProxy().url='specialCraftsTrace/searchProductSpec'+'/'+value;
										combo.store.load();
										combo.expand();
										return false;
									}
								}
					    }					
					}]
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
						}]

			}]
		}]
	}]
}
);