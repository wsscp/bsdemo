/**
 *@Project: 超级奶爸之宝宝喂养记
 *@Author: LionGIS@163.com
 *@Date: 2014-06-20
 *@Copyright: 代码开源，欢迎转载，但请保留版本信息.
 */
 
Ext.define('MES.view.Main', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.main',
    requires: [
        'Ext.TitleBar',
         
        'MES.view.user.Main'
    ],
    config: {
        id: 'mainView',
        fullscreen: true,
        tabBarPosition: 'bottom',
        items: [{
            xtype: 'usermain'
        }
        /*, {
            xtype: 'muru'
        }, {
            xtype: 'weinai'
        }, {
            xtype: 'niaobu'
        }, {
            xtype: 'tiwen'
        }, {
            xtype: 'shuijiao'
        }*/
        ]
    }
});
