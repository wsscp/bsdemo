Ext.util.Cookies.set("ExtRepoDevMode", "false");
Ext.Loader.setConfig({
    enabled: true,
    disableCaching : true
});
Ext.Ajax.timeout = 90000;//90秒

Ext.Loader.setPath('Oit', '/bsstatic/oitjs/ext');
Ext.require("Oit.panel.IframePanel");

Ext.onReady(function(){
    // 解决iframe的登录问题
//    if(top.location != self.location){
//        top.location=self.location;
//    }

    var menu = Ext.get('menu');
    var menus = eval(menu.getAttribute('items'));
   /* menuItems.push('->');
    menuItems.push({
        text : menu.getAttribute('userCode'),

        menu : {
            items : [{
                href : menu.getAttribute('ctx') + '/logout.action',
                text : menu.getAttribute('exitText')
            }]
        }
    });*/


    var menuItems = new Array();
    menuItems.push({
        xtype:'container',
        contentEl : 'bsmesInfo',
        width:263
    });

    Ext.each(menus,function(item){
        menuItems.push(item);
    });

    menuItems.push('->');
    menuItems.push({
        xtype:'container',
        id:'loginUserInfo',
        contentEl:'userInfo',
        width:150
    });

    var firstTab = Ext.get('firstTab');
    Ext.create('Ext.Viewport',{
        layout : 'border',
        items : [{
                    region : 'north',
                    xtype : 'toolbar',
                    style: 'background-color:#2d5259;',
                    height: 60,
                    defaults : {
                        width : 100,
                        height:35
                    },
                    items:menuItems
                },{
                    xtype:'tabpanel',
                    region : 'center',
                    activeTab : 0,
                    items : [{
                        useIframe : true,
                        iframeName : firstTab.getAttribute('iframeName'),
                        iframeSrc : firstTab.getAttribute('href'),
                        itemId : firstTab.getAttribute('href'),
                        title : firstTab.getAttribute('title')
                    }]
                }]
    });
});

function openTab(title, href) {
    var tabpanel = getTabPanel();

    // 如已打开则激活
    var existTab = tabpanel.setActiveTab(href);
    if (existTab != null) {
        return;
    }

    // 否则新建tab
    tabpanel.add({
        closable : true,
        useIframe : true,
        iframeSrc : href,
        itemId : href,
        title : title
    });
    tabpanel.setActiveTab(href);
}

function getTabPanel() {
    return Ext.ComponentQuery.query('tabpanel')[0];
}

function openUserInfo(userCode){
    document.getElementById("userMenu").style.display='none';
    openTab("个人资料","bas/userInfo.action?userCode="+userCode);
}

function showMsg(){
    var userMenu = document.getElementById("userMenu");
    userMenu.style.left=document.getElementById('loginUserInfo').style.left;
    if(userMenu.style.display == 'none'){
        userMenu.style.display = 'block';
    }else{
        userMenu.style.display = 'none';
    }
}