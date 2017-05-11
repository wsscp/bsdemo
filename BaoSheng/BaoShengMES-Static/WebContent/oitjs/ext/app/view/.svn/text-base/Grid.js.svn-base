/**
 * config: defaultEditingPlugin actioncolumn
 */
Ext.define("Oit.app.view.Grid", function() {
	var defaultBarItems = {
		'add' : {
			text : Oit.btn.add,
			iconCls : 'icon_add'
		},
		'search' : {
			text : Oit.btn.search,
			iconCls : 'icon_search'
		},
		'back' : {
			text : Oit.btn.back,
			iconCls : 'icon_back'
		},
		'remove' : {
			text : Oit.btn.remove,
			iconCls : 'icon_remove'
		},
		'export' : {
			text : Oit.btn.export,
			iconCls : 'icon_export'
		},
		'save' : {
			text : Oit.btn.save,
			iconCls : 'icon_save'
		},
		'reset' : {
			text : Oit.btn.reset,
			iconCls : 'icon_reset'
		}
	};

	var initComponent = function() {
		var me = this;
		
		// 设置权限，显示或隐藏
		for(var key in me.defaultBarItems){
			var item = me.defaultBarItems[key];
			// 根据菜单角色权限修改增删改
			if (!(Oit.url.urlParam(key) && Oit.url.urlParam(key) == 'false')) {
				item.hidden = true;
			}else{
				item.hidden = false;
			}
		}
		
		var defaultActionColumnItems = {
			'remove' : {
				tooltip : Oit.btn.remove,
				iconCls : 'icon_remove',
				handler : function(grid, rowIndex) {
					me.fireEvent('remove', grid.getStore().getAt(rowIndex));
				}
			},
			'detail' : {
				tooltip : Oit.btn.detail,
				iconCls : 'icon_detail',
				handler : function(grid, rowIndex) {
					me.fireEvent('detail', grid.getStore().getAt(rowIndex));
				}
			},
			'edit' : {
				tooltip : Oit.btn.edit,
				iconCls : 'icon_edit',
				handler : function(grid, rowIndex) {
					me.fireEvent('toEdit', grid.getStore().getAt(rowIndex));
				}
			}
		};

		// dockedItems
		if (!me.dockedItems) {
			me.dockedItems = [];
		} else {
			Ext.each(me.dockedItems, function(dockedItem) {
				Ext.each(dockedItem.items, function(item) {
			        // 添加 dockedItems->toolbar->add/delete/edit 这种结构
					Ext.applyIf(item, defaultBarItems[item.itemId]);

					// 添加 dockedItems->toolbar->fieldset->form->buttons->add/delete/edit 这种结构
					Ext.each(item.items, function(form) {
						Ext.each(form.buttons, function(buttons) {
							Ext.applyIf(buttons, defaultBarItems[buttons.itemId]);
						});
					});

				});
			});
		}

		// tbar
		if (me.tbar) {
			Ext.each(me.tbar, function(item) {
				Ext.applyIf(item, defaultBarItems[item.itemId]);
			});
		}

		// 分页
		if (me.hasPaging === undefined || me.hasPaging) {
			me.dockedItems.push({
				xtype : 'pagingtoolbar',
				store : me.store,
				dock : 'bottom',
				displayInfo : true
			});
		}

		/**
		 * @cfg {Object/Object[]} actioncolumn 表格中操作类的按钮
		 */
		if (me.actioncolumn) {
			var newActioncolumn = [];
			Ext.each(me.actioncolumn, function(item) {
				// 根据菜单角色权限修改增删改
				if (!(Oit.url.urlParam(item.itemId) && Oit.url.urlParam(item.itemId) == 'false')) {
					newActioncolumn.push(Ext.applyIf(item, defaultActionColumnItems[item.itemId]));
				}
			});
			me.actioncolumn = newActioncolumn;
			me.columns.unshift({
				menuDisabled : true,
				sortable : false,
				xtype : 'actioncolumn',
				width : 20 * me.actioncolumn.length,
				items : me.actioncolumn
			});
		}

		var filters = [];
		Ext.each(me.columns, function(column) {
			if (column.filter) {
				column.filter['dataIndex'] = column.dataIndex;
				filters.push(column.filter);
			}
		});
		me.features = me.features || [];
		me.features.push({
			ftype : 'formFilters',
			filters : filters
		});

		me.callParent(arguments); // ------------call父类--------------

		// editingPlugin
		if (me.defaultEditingPlugin && Oit.url.urlParam('edit') == 'true') {
			if (!me.plugins) {
				me.plugins = [];
			}
			me.plugins.push(Ext.create('Ext.grid.plugin.RowEditing', {
						autoCancel : false
					}));
		}

		// 更新render
		var fields = me.store.model.prototype.fields.items;
		Ext.each(fields, function(field) {
					var length = me.columns.length;
					for (i = 0; i < length; i++) {
						if (me.columns[i].dataIndex == field.name && !me.columns[i].renderer) {
							me.columns[i].renderer = field.renderer;
							break;
						}
					}
				});
	};
	var beforerender = function() {
		// 修改pagingtoolbar会越加越多的bug
		var me = this;
		var flg = false;
		if (me.dockedItems) {
			Ext.each(me.dockedItems.items, function(item) {
						if (item.xtype == 'pagingtoolbar') {
							if (flg) {
								// item.destroy()方法无法完全销毁对象,暂时用hide()方法代替
								item.hide();
							}
							flg = true;
						}
					});
		}
		return true;
	};
	return {
		extend : 'Ext.grid.Panel',
		forceFit : true,
		// selType : 'checkboxmodel',
		initComponent : initComponent,
		/**
		 * @cfg {Boolean} [defaultEditingPlugin=true] 为true时使用默认editing插件： Ext.create('Ext.grid.plugin.RowEditing',
		 *      {autoCancel: false})。 如需自定义插件请设为false并使用plugins参数。{@link Ext.grid.Panel plugins}
		 */
		defaultEditingPlugin : true,
		columnLines : true,
		requires : ['Oit.app.view.form.HForm', 'Oit.app.view.grid.FormFiltersFeature', 'Ext.toolbar.Paging',
				'Ext.ux.ajax.SimManager'],
		viewConfig : {
			stripeRows : true, // 隔行换色，默认true
			enableTextSelection : true // 允许选则grid中的文字，默认false
		},
		defaults : {
			labelWidth : 'auto'
		},
		listeners : {
			beforerender : beforerender
		}
	};
});
