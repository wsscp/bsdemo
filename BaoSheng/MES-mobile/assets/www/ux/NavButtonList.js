Ext.define('Ext.ux.NavButtonList', {
    extend: 'Ext.Container',
    xtype: 'navbuttonlist',
    config: {
        cls: 'info',
        width: '100%',
        height: 200,
        scrollable: {
            direction: 'vertical',
            directionLock: true,
            indicators: false
        },
        tpl: new Ext.XTemplate(
        '<div class="title tl">标题</div>',
        '<div class="box sm"><div class="left">时间</div><div>发布来源：</div></div>',
        '<div class="box"><div class="one"></div><div>',
            '<div class="x-button btn"><span class="x-button-icon shareIco x-icon-mask" doit="11">1</span></div>',
            '<div class="x-button btn"><span class="x-button-icon favorites x-icon-mask">2</span></div>',
            '<div class="x-button btn"><span class="x-button-icon commentIco x-icon-mask">3</span></div>',
         '</div></div>', 
         '<div class="con">{Summary}</div>')
    },
    /*初始化*/
    initialize: function () {
        this.callParent();
        //添加按钮监控
        this.element.on({
            tap: 'onBtnTap',//点击后激活方法
            delegate: 'div.x-button', //这里是指div中class为x-button的对象
            scope: this
        });
    },
    onBtnTap: function (e, span) {
        //获取所需参数
        var name = span.getAttribute("doit");
        console.log(name);
    }
});