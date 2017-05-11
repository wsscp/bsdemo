Ext.define('Ext.ux.ShuiJiaoList', {
			extend : 'Ext.Panel',
			xtype : ['shuijiaolist'],
			requires : ['Ext.List', 'WeiYang.model.WeiYangInfo',
					'WeiYang.store.WeiYangStore'],
			config : {
				loadingText:false,
				emptyText: '暂无内容',
				items : [{
					xtype : 'toolbar',
					docked : 'top',
					title: '睡觉记录',
					items : [{
								xtype : 'spacer'
							}, {
								text : '新建',
								ui : 'action',
								handler : function() {
									var shuijiaoEditor = Ext
											.create('Ext.ux.ShuiJiaoEditor');
									Ext.getCmp('shuijiaoView').push(shuijiaoEditor);

									var newShuiJiao = Ext.create(
											"WeiYang.model.WeiYangInfo", {
												itemhash : '',
												stype : 'shuijiao',
												date : new Date(),
												volume : 0
											});
											
									Ext.getCmp('shuijiaoFormPanel').setRecord(newShuiJiao);
								}
							}]
				}, {
					xtype : 'list',
					id : 'shuijiaoList',
					width : '100%',// 200,
					height : '100%',
					grouped: true,
					onItemDisclosure : {// 若配置该项，list每一项的右侧都会出现一个小图标。其他功能请查看api
						handler : function(record, btn, index) {
							// this.fireEvent('itemDisclosure', this, record);
							var shuijiaoEditor = Ext
									.create('Ext.ux.ShuiJiaoEditor');
							Ext.getCmp('shuijiaoView').push(shuijiaoEditor);

							//record.set('time', new Date("2014-06-10 " + record.get('time')));
							Ext.getCmp('shuijiaoFormPanel').setRecord(record);
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
									"<div class='list-item-narrative'>时长：{volume}分钟</div>",
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
					var sql = "SELECT * FROM weiyang WHERE stype='shuijiao' order by date desc";
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

								shuijiaoStore.setData(data);
								Ext.getCmp('shuijiaoList').setStore(shuijiaoStore);
							})
				}
			}
		});