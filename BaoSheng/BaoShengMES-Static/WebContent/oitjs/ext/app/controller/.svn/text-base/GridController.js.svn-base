/**
 * config: view newFormToEdit editview detailUrl 扩展方法：saveChange getGrid getEditView getEditForm getSearchForm
 * getSelectedData 模板方法： 事件：toEdit
 */
Ext.define('Oit.app.controller.GridController', {
			extend : 'Ext.app.Controller',
			/**
			 * @cfg {String} view (required)
			 */
			/**
			 * @cfg {String} detailUrl 详细信息获取数据的url。
			 */
			/**
			 * @cfg {String} exportUrl 导出当前表格的url。
			 */
			/**
			 * @cfg {String} editview (如newFormToEdit则必须) add或edit时的弹出窗口。
			 */
			/**
			 * @cfg {Boolean} [newFormToEdit=true] 为true时弹出编辑表单进行编辑(针对add和edit按钮)，否则直接在表格上编辑。
			 */
			newFormToEdit : true,
			constructor : function() {
				var me = this;

				// 初始化refs
				me.refs = me.refs || [];
				me.refs.push({
							ref : 'grid',
							selector : me.view
						});
				me.refs.push({
							ref : 'searchForm',
							selector : me.view + ' form'
						});
				me.refs.push({
							ref : 'detailGrid',
							selector : 'detailgrid'
						});

				// editForm
				me.refs.push({
							ref : 'editView',
							selector : me.editview,
							autoCreate : true,
							xtype : me.editview
						});
				me.refs.push({
							ref : 'editForm',
							selector : me.editview + ' form'
						});

				// addForm
				me.refs.push({
							ref : 'addView',
							selector : me.addview,
							autoCreate : true,
							xtype : me.addview
						});
				me.refs.push({
							ref : 'addForm',
							selector : me.addview + ' form'
						});
				// detailForm
				me.refs.push({
							ref : 'detailView',
							selector : me.detailview,
							autoCreate : true,
							xtype : me.detailview
						});
				me.refs.push({
							ref : 'detailForm',
							selector : me.detailview + ' form'
						});
				me.callParent(arguments);
			},
			init : function() {
				var me = this;
				if (!me.view) {
					Ext.Error.raise("A view configuration must be specified!");
				}
				/**
				 * @event detail 点击add按钮前触发。
				 * @param {Ext.button.Button} btn 点击的button
				 */

				// 初始化工具栏
				me.control(me.view + ' button[itemId=search]', {
							click : me.onSearch
						});
				me.control(me.view + ' button[itemId=reset]', {
							click : me.onReset
						});
				me.control(me.view + ' button[itemId=detail]', {
							click : me.onDetail
						});
				me.control(me.view + ' button[itemId=add]', {
							click : me.onAdd
						});
				me.control(me.view + ' button[itemId=remove]', {
					click : function(){me.onRemove()}
				});
				me.control(me.view + ' object[itemId=edit]', {
							click : me.onEdit
						});
				me.control(me.view + ' button[itemId=export]', {
							click : me.onExport
						});

				// 初始化编辑表单按钮
				me.control(me.addview + ' button[itemId=ok]', {
							click : me.onFormAdd
						})
				me.control(me.editview + ' button[itemId=ok]', {
							click : me.onFormSave
						})

				me.callParent(arguments);
			},
			onLaunch : function() {
				var me = this;
				var grid = me.getGrid();

				if (!me.newFormToEdit) { // for edit plugin
					grid.on('edit', function() {
								me.onSave(grid);
							});
				}

				/**
				 * @event toEdit 打开编辑
				 * @param {Ext.grid.Panel} grid
				 */
				grid.on('toEdit', me.doEdit, me);
				grid.on('remove', me.onRemove, me);
				grid.on('detail', me.doDetail, me);
				me.callParent(arguments);
			},
			/**
			 * 获取选中行的记录(允许多行)
			 * 
			 * @protected
			 * @param {Ext.grid.Panel} grid要获取行的表格(可选)
			 */
			getSelectedData : function() {
				var me = this;
				var data = me.getGrid().getSelectionModel().getSelection();
				if (data.length == 0) {
					Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
					return null;
				} else {
					return data;
				}
			},
			/**
			 * @private
			 */
			onFormAdd : function(btn) {
				var me = this;
				var form = me.getAddForm();
				form.updateRecord();
				if (form.isValid()) {
					var store = me.getGrid().getStore();
					// 同步到服务器
					store.insert(0, form.getRecord());
					store.sync();
					// 关闭窗口
					me.getAddView().close();
				}
			},
			/**
			 * @private
			 */
			onFormSave : function(btn) {
				var me = this;
				var form = me.getEditForm();
				form.updateRecord();
				if (form.isValid()) {
					var store = me.getGrid().getStore();
					// 同步到服务器
					store.sync();
					// 关闭窗口
					me.getEditView().close();
				}
			},
			/**
			 * @private
			 */
			onSearch : function(btn) {
				var me = this;
				var store = me.getGrid().getStore();

				store.loadPage(1);
			},
			// 重置
			onReset : function(btn){
				var me = this;
				if(btn.up("form"))
					btn.up("form").getForm().reset();
			},
			/**
			 * @protected
			 */
			onDetail : function(data) {
				var me = this;
				if (!me.detailUrl) {
					Ext.Error.raise("A view configuration must be specified!");
				}

				var grid = me.getDetailGrid();
				Ext.Ajax.request({
							url : detailUrl,
							params : {
								id : data.id
							},
							success : function(response) {
								var text = response.responseText;
								grid.setSource(eval(text));
							}
						});
			},
			/**
			 * @private
			 */
			onSave : function() {
				var me = this;
				// me.fireEvent('beforeSave', me); TODO chanedi
				var store = Ext.ComponentQuery.query('#recentOrderGrid').getStore();
				store.load();
			},
			/**
			 * @private
			 */
			onAdd : function() {
				var me = this;
				// me.fireEvent('beforeAdd', me); TODO chanedi
				me.doAdd();
			},
			/**
			 * Template method
			 * 
			 * @protected
			 */
			doAdd : function() {
				var me = this;
				var record = Ext.create(me.getGrid().getStore().model);

				me.getAddView().show();
				me.getAddForm().loadRecord(record);
			},
			/**
			 * @private
			 */
			onRemove : function(data) {
				var me = this;
				data = data ? data : me.getSelectedData(); // 区分选择的删除对象，列表删除还是tbar按钮删除
				if (data) {
					Ext.MessageBox.confirm('确认', '确认删除?', function(btn){
						if (btn == 'yes') {
							// var data = me.getSelectedData();
								me.doRemove(data);
						}
					});
				}
			},
			/**
			 * Template method
			 * 
			 * @protected
			 * @param {Ext.data.Model} data
			 */
			doRemove : function(data) {
				var me = this;
				var store = me.getGrid().getStore();
				store.remove(data);
				store.sync();
			},
			/**
			 * @private
			 */
			onEdit : function() {
				var me = this;
				var data = me.getSelectedData();
				if (data) {
					me.doEdit(data = data[0]); // 获取第一条记录
				}
			},
			/**
			 * @private
			 */
			onExport : function() {
				var me = this;
				if (!me.exportUrl) {
					Ext.Error.raise("A view configuration must be specified!");
				}
				var searchForm = me.getSearchForm();
				var params = [];
				Ext.each(me.getGrid().columns, function(column) {
							if (!column.dataIndex) {
								return;
							}
							params.push({
										text : column['text'],
										dataIndex : column['dataIndex']
									})
						});
				falseAjaxTarget.document.write('<form method="post"><input id="params" name="params">'
						+ '<input id="queryParams" name="queryParams"></form>');
				falseAjaxTarget.document.getElementById("params").value = JSON.stringify(params);
				if (searchForm) {
					falseAjaxTarget.document.getElementById("queryParams").value = JSON.stringify(searchForm
							.getValues());
				}
				var form = falseAjaxTarget.document.forms[0];
				form.action = me.exportUrl;
				form.submit();

			},
			/**
			 * Template method
			 * 
			 * @protected
			 * @param {Ext.data.Model} data
			 */
			doEdit : function(data) {
				var me = this;
				if (me.newFormToEdit) {
					me.getEditView().show();
					me.getEditForm().loadRecord(data);
				} else {
					me.getGrid().editingPlugin.startEdit(data, 0);
				}
			},
			/**
			 * @private
			 */
			doDetail : function(data) {
				var me = this;
				me.getDetailView().show();
				me.getDetailForm().loadRecord(data);
			},
			statics : {
				openSubWindow : function(url, title) {
					var subWindow = Ext.ComponentQuery.query("subWindow")[0];
					if (!subWindow) {
						subWindow = Ext.create('Oit.app.view.window.SubWindow', {
									title : title
								});
					}
					subWindow.setTitle(title);
					subWindow.show(url);
				},
				closeSubWindow : function(url) {
					var subWindow = Ext.ComponentQuery.query("subWindow")[0];
					if (!subWindow) {
						return;
					}
					subWindow.close();
				},
				// 从subwindow刷新当前window
				refreshParentWindow : function(url, title) {
					parent.window.Oit.app.controller.GridController.openSubWindow(url, title);
				}
			}
		});