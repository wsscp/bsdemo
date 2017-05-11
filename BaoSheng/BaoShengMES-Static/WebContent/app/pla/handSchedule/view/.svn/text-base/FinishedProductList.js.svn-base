Ext.define("bsmes.view.FinishedProductList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.finishedProductList',
	store : 'FinishedProductStore',
	defaultEditingPlugin : false,
	columnLines : true,
	/*selType : 'checkboxmodel',
	selModel : {
		mode : "SIMPLE" // "SINGLE"/"SIMPLE"/"MULTI"
		checkOnly : true
	},*/
	listeners : {
		'itemdblclick' : function(view, record, item,index,e,eOpts){
			var win = Ext.create('bsmes.view.FinishedProductEdit');
			var form = win.down('form');
			form.loadRecord(record);
			form.getForm().setValues({
				uselength : record.get('length')
			});
			form.updateRecord();
			win.show();
		}
	},
	initComponent : function() {
		var me = this;
		var dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			items : [ {
				xtype : 'hform',
				defaults : {
					width : 180,
					padding : 1,
					labelAlign : 'right',
					labelWidth : 30
				},
				items: [
				/*{
					xtype : 'hiddenfield',
					name : 'id'
				},*/{
					xtype : 'hiddenfield',
					name : 'useLength'
				},/*{
					xtype : 'hiddenfield',
					name : 'contractLength'
				},*/{
				    fieldLabel: '型号',
				    name: 'model',
				    xtype : 'combobox',
				    queryMode: 'remote',
				    displayField: 'model',
				    valueField: 'model',
				    minChars : 2, // 最少几个字开始查询
					triggerAction : 'all', // 请设置为”all”,否则默认
					// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
					typeAhead : true, // 是否延迟查询
					typeAheadDelay : 1000, // 延迟时间
					firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
				    store : new Ext.data.Store({
				    	fields : ['model'],
				    	proxy : {
							type : 'rest',
							url : 'handSchedule/getAllModelORSpec?type='+'model'
						}
				    }),
				    listeners : {
						beforequery : function(e) {
							var combo = e.combo;
							combo.collapse(); // 折叠
							if (!e.forceAll) { // 模糊查询走的方法
								var value = e.query;
								if(value != null && value != ''){
									combo.getStore().load({ params : { 'query' : value } });
								}else{
									combo.getStore().load();
								}
								combo.expand(); // 展开
								return false;
							}else{ // 点击下拉框
								combo.firstExpand ++;
							}
						},
						expand : function(e) {
							if (e.firstExpand > 1) {
								e.getStore().load();
							}
						}
					}
				},{
					fieldLabel: '规格',
				    name: 'spec',
				    xtype : 'combobox',
				    queryMode: 'remote',
				    displayField: 'spec',
				    valueField: 'spec',
				    minChars : 1, // 最少几个字开始查询
					triggerAction : 'all', // 请设置为”all”,否则默认
					// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
					typeAhead : true, // 是否延迟查询
					typeAheadDelay : 1000, // 延迟时间
					firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
				    store : new Ext.data.Store({
				    	fields : ['spec'],
				    	proxy : {
							type : 'rest',
							url : 'handSchedule/getAllModelORSpec?type='+'spec'
						}
				    }),
				    listeners : {
						beforequery : function(e) {
							var combo = e.combo;
							combo.collapse(); // 折叠
							if (!e.forceAll) { // 模糊查询走的方法
								var value = e.query;
								if(value != null && value != ''){
									combo.getStore().load({ params : { 'query' : value } });
								}else{
									combo.getStore().load();
								}
								combo.expand(); // 展开
								return false;
							}else{ // 点击下拉框
								combo.firstExpand ++;
							}
						},
						expand : function(e) {
							if (e.firstExpand > 1) {
								e.getStore().load();
							}
						}
					}
				}]
			}, {
				itemId : 'search',
				handler : function(){
					var me = this;
					var grid = me.up('grid');
					var form = grid.down('form');
					var store = grid.getStore();
					store.reload({
						params : form.getValues()
					})
				}
			},{
				itemId : 'socket',
				text : '成品现货'/*,
				handler : function(){
					var me = this;
					var grid = me.up('grid');
					var win = Ext.ComponentQuery.query('#finishedProductWindow')[0];
//					win.useLength
					var useLength = win.useLength;
					var contractLength = win.contractLength;
					console.log('使用长度'+useLength);
					return;
					var id = grid.id;
					var finished = true;
					if(Number(contractLength) > Number(useLength)){
						finished = false;
					}
					Ext.MessageBox.confirm('确认', '确认库存生产？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
										url : 'handSchedule/updateSpecialFlag',
										params : {
											ids : grid.id,
											specialFlag : 3,
											finished : finished,
											useLength : useLength
										},
										success : function(response) {
											Ext.Msg.alert(Oit.msg.PROMPT, '操作成功');
											grid.getStore().reload();
										}
									});
						}
					});
				}*/
			} ]
		}];
		var columns = [{
			text : '型号',
			flex : 1,
			dataIndex : 'model'
		}, {
			text : '规格',
			flex : 1,
			dataIndex : 'spec'
		},{
			text : '剩余长度',
			flex : 0.5,
			dataIndex : 'length'
		},{
			text : '已用长度',
			flex : 0.5,
			dataIndex : 'usedLength'
		},{
			text : '盘具',
			flex : 0.5,
			dataIndex : 'dish'
		},{
			text : '排位',
			flex : 0.5,
			dataIndex : 'qualifying'
		},{
			text : '区域',
			flex : 0.5,
			dataIndex : 'region'
		},{
			text : '备注',
			flex : 1,
			dataIndex : 'remarks'
		}];
		me.dockedItems = dockedItems;
		me.columns = columns;
		me.callParent(arguments);
	}
});
