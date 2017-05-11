Ext.define('Ext.ux.NiaoBuList', {
			extend : 'Ext.Panel',
			xtype : ['niaobulist'],
			requires : ['Ext.List', 'WeiYang.model.WeiYangInfo',
					'WeiYang.store.WeiYangStore'],
			config : {
				items : [{
					xtype : 'toolbar',
					docked : 'top',
					title: '尿布记录',
					items : [{
								xtype : 'spacer'
							}, {
								text : '新建',
								ui : 'action',
								handler : function() {
									var niaobuEditor = Ext
											.create('Ext.ux.NiaoBuEditor');
									Ext.getCmp('niaobuView').push(niaobuEditor);

									var newNiaoBu = Ext.create(
											"WeiYang.model.WeiYangInfo", {
												itemhash : '',
												stype : 'niaobu', 
												date : new Date(),
												volume : 0
											});
											
									Ext.getCmp('niaobuFormPanel').setRecord(newNiaoBu);
								}
							}]
				}, {
					xtype : 'list',
					id : 'niaobuList',
					width : '100%',// 200,
					height : '100%',
					grouped: true,
					onItemDisclosure : {// 若配置该项，list每一项的右侧都会出现一个小图标。其他功能请查看api
						handler : function(record, btn, index) {
							// this.fireEvent('itemDisclosure', this, record);
							var niaobuEditor = Ext
									.create('Ext.ux.NiaoBuEditor');
							Ext.getCmp('niaobuView').push(niaobuEditor);

							//record.set('time', new Date("2014-06-10 " + record.get('time')));
							Ext.getCmp('niaobuFormPanel').setRecord(record);
						}
					},
					/*
					 * left : 0, top : 0, width : 200, height : '100%',
					 */
					itemTpl : new Ext.XTemplate(
							"<div style='height:42px'>",
								//"<div class='list-item-number'>{#}</div>",
								"<div class='list-item-content'>",
									"<div class='list-item-title'>时间：{date:date('H:i')} </div>",
										'<tpl if="volume==\'0\'">大便&小便</tpl>',
							            '<tpl if="volume==\'1\'">小便</tpl>',
							            '<tpl if="volume==\'2\'">大便</tpl>',
									"<div class='list-item-remark'>{remark}</div>",
								"</div>",
							"</div>"
							)
				}]
			},
			initialize : function() {
				this.loadDate();
			},
			loadDate: function() {
				var me = this;
				if (fgDB != null) {
					var sql = "SELECT * FROM weiyang WHERE stype='niaobu' order by date desc";
					fgDB.executeSql(sql, function(res) {
								var data = [], item = null;
								for (var i = 0; i < res.rows.length; i++) {
									if (Ext.os.is.Windows) {
										item = res.rows.item(i);
									} else {
										item = res.rows[i];
									}
									data.push(item);
								}

								niaobuStore.setData(data);
								Ext.getCmp('niaobuList').setStore(niaobuStore);
							})
				}
			}
		});