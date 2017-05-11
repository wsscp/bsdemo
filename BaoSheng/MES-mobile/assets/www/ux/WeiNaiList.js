Ext.define('Ext.ux.WeiNaiList', {
			extend : 'Ext.Panel',
			xtype : ['weinailist'],
			requires : ['Ext.List', 'MES.model.MESInfo',
					'MES.store.MESStore'],
			config : {
				items : [{
					xtype : 'toolbar',
					docked : 'top',
					title: '奶瓶记录',
					items : [{
								xtype : 'spacer'
							}, {
								text : '新建',
								ui : 'action',
								handler : function() {
									var weinaiEditor = Ext
											.create('Ext.ux.WeiNaiEditor');
									Ext.getCmp('weinaiView').push(weinaiEditor);

									var newWeiNai = Ext.create(
											"WeiYang.model.WeiYangInfo", {
												itemhash : '',
												stype : 'weinai',
												date : new Date(),
												volume : 0
											});
											
									Ext.getCmp('weinaiFormPanel').setRecord(newWeiNai);
								}
							}]
				}, {
					xtype : 'list',
					id : 'weinaiList',
					width : '100%',// 200,
					height : '100%',
					grouped: true,
					onItemDisclosure : {// 若配置该项，list每一项的右侧都会出现一个小图标。其他功能请查看api
						handler : function(record, btn, index) {
							// this.fireEvent('itemDisclosure', this, record);
							var weinaiEditor = Ext
									.create('Ext.ux.WeiNaiEditor');
							Ext.getCmp('weinaiView').push(weinaiEditor);

							//record.set('time', new Date("2014-06-10 " + record.get('time')));
							Ext.getCmp('weinaiFormPanel').setRecord(record);
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
									"<div class='list-item-narrative'>计量：{volume}ml</div>",
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
					var sql = "SELECT * FROM weiyang WHERE stype='weinai' order by date asc";
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

								weinaiStore.setData(data);
								Ext.getCmp('weinaiList').setStore(weinaiStore);
							})
				}
			}
		});