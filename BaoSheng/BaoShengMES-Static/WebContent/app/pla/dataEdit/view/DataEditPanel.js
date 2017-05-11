/**
 * 主页面
 */

Ext.define("bsmes.view.DataEditPanel", {
			extend : 'Ext.panel.Panel',
			autoScroll : true,
			alias : 'widget.dataEditPanel',
			width : document.body.scrollWidth,
			height : document.body.scrollHeight,
			items : [],
			tbar : new Ext.Toolbar({
						style : 'background:-moz-linear-gradient(53% 87% 270deg, #FFFFFF, #C0C0C0 68%)',
						items : [{
									itemId : 'equipLineButton',
									text : '产品工序添加生产线'
								}, {
									itemId : 'inQuantityButton',
									text : '产品工序修改投入用量'
								}, {
									itemId : 'processQcButton',
									text : '产品工序修改质量参数'
								}]
					}),

			initComponent : function() {
				var me = this;

				this.callParent(arguments);
			}
		});

