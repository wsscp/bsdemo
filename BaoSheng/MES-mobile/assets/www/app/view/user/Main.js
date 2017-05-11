/**
 *@Project: 超级奶爸之宝宝喂养记
 *@Author: LionGIS@163.com
 *@Date: 2014-06-20
 *@Copyright: 代码开源，欢迎转载，但请保留版本信息.
 */

Ext.define('MES.view.user.Main', {
			extend : 'Ext.NavigationView',
			requires : ['Ext.carousel.Carousel', 'Ext.ux.NavButtonList',
					'Ext.Img'],
			alias : 'widget.usermain',
			config : {
				title : '车间概况',
				iconCls : 'user',
				navigationBar : {
					items : [{
								iconMask : true,
								iconCls : 'user',
								hidden : true,
								text : '退出',
								ui : 'forward',
								action : 'changeUser',
								align : 'right'
							}]
				},
				items : [{
					xtype : 'container',
					title : '数字工厂',
					style : 'background-color: #FFFFFF',
					layout : {
						type : 'vbox',
						align : 'center'
					},
					items : [{
						xtype : 'panel',
						margin : '40 0 0 0',
						cls : 'home',
						defaults : {
							xtype : 'panel',
							layout : 'hbox'
						},
						items : [{
									defaults : {
										xtype : 'panel',
										margin : 10
									},
									items : [{
												xtype : 'button',
												text : '车间概况',
												iconAlign : 'top',
												icon : "resources/icon/muru.png",
												handler : function() {
													Ext.getCmp('mainView').setActiveItem(1);
												}
											}, {
												xtype : 'button',
												text : '奶瓶',
												iconAlign : 'top',
												icon : "resources/icon/weinai.png",
												handler : function() {
													Ext.getCmp('mainView').setActiveItem(2);
												}
											}, {
												xtype : 'button',
												text : '尿布',
												iconAlign : 'top',
												icon : "resources/icon/niaobu.png",
												handler : function() {
													Ext.getCmp('mainView').setActiveItem(3);
												}
											}]
								}, {
									defaults : {
										xtype : 'panel',
										margin : 10
									},
									items : [{
												xtype : 'button',
												text : '体温',
												iconAlign : 'top',
												icon : "resources/icon/tiwen.png",
												handler : function() {
													Ext.getCmp('mainView').setActiveItem(4);
												}
											}, {
												xtype : 'button',
												text : '睡觉',
												iconAlign : 'top',
												icon : "resources/icon/shuijiao.png",
												handler : function() {
													Ext.getCmp('mainView').setActiveItem(5);
												}
											}, {
												xtype : 'button',
												text : '关于',
												iconAlign : 'top',
												icon : "resources/icon/about.png",
												handler : function() {
													Ext.Msg.alert('关于', '欢迎使用《宝宝喂养记》<br/>版本：1.0-20140620<br/>作者：LionGIS@163.com', Ext.emptyFn);
												}

											}]
								}]
							/*
							 * { xtype : 'container', flex : 1, width: '100%',
							 * padding : '15px 5px 5px 10px',
							 * 
							 * items : []
							 */
							/*
							 * , html : [ '<div class="nav"><ul>', '<li id="testli1"><image
							 * src="resources/icon/muru.png"></image><a
							 * doit="11">母乳</a></li>', '<li><image
							 * src="resources/icon/weinai.png"></image><a>奶瓶</a></li>', '<li><image
							 * src="resources/icon/niaobu.png"></image><a>尿布</a></li>', '<li><image
							 * src="resources/icon/tiwen.png"></image><a>体温</a></li>', '<li><image
							 * src="resources/icon/shuijiao.png"></image><a>睡觉</a></li>', '<li><image
							 * src="resources/icon/about.png"></image><a>关于</a></li>', '</ul></div>'].join('')
							 */
					}, {
						xtype : 'container',
						padding : '15px 5px 5px 10px',
						docked : 'bottom',
						style : 'background-color: #5E99CC',
						html : '<div style="width: 100%; text-align: center;">'
								+ '欢迎使用超级奶爸系列之宝宝喂养记' + '<br/>' + '希望这个小工具能帮到你！'
								+ '<br/>' + '作者:LionGIS@163.com' + '</div>'
					}]
				}]
			}
		});
