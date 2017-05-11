/**
 * Created by joker on 2014/7/4 0004.
 */
Ext.define('bsmes.view.VirtualKeyBoard',{
        extend:'Ext.tip.ToolTip',
        alias: 'plugin.virtualKeyBoard',
        //是否混淆键盘布局
        confound: false,
        autoHide: false,
        keyboard: null,
        showEvent: 'focus',
        itemId:'virtualKeyBoard',
        init: function(field) {
            var me = this;
            Ext.suspendLayouts();
            me.createKeyboard();
            me.add(me.keyboard);
            Ext.resumeLayouts(true);
            me.setKeyHandler(field);
        },
        createKeyboard: function() {
            this.keyboard = Ext.create('Ext.container.Container', {
                layout:{
                    type: 'table',
                    columns: 4
                },
                defaults: {
                    margin:'10 10 10 10',
                    width:50,
                    height:50
                },
                defaultType: 'button',
                items: [
                    { text: "1"},
                    { text: "2"},
                    { text: "3"},
                    { text: "4"},
                    { text: "5"},
                    { text: "6"},
                    { text: "7"},
                    { text: "8"},
                    { text: "9"},
                    { text: "0"},
                    { text: "."},
                    { text: "←"},
                    { text: '确定',colspan:2,width:120},
                    { text: "清空",colspan:2,width:120}
                ]
            });
        },
        setKeyHandler: function(field) {
            var me = this,
                btns = me.keyboard.items,
                btn, i, count;
            for(i = 0, count = btns.length; i < count; i++) {
                btns.get(i).on('click', function(btn, e) {
                    switch(btn.text) {
                        case '清空':
                            me.onClickClean(field);
                            break;
                        case '确定':
                            me.hide();
                            break;
                        case '←':
                            me.onClickBackspace(field);
                            break;
                        default:
                            me.onClickChar(btn, field);
                    }
                });
            }
            field.on(me.showEvent, function(field) {
                var inputEl = field.inputEl,
                    inputHeight = inputEl.getHeight(),
                    x = inputEl.getX(),
                    y = inputEl.getY() + inputHeight + 2,
                    posArr = [x, y];
                if(me.isHidden()) {
                    me.showAt(posArr);
                }
            }, me);
        },
        onClickClean: function(field) {
            field.setValue('');
        },
        onClickBackspace: function(field) {
            var oldValue = field.getValue(),
                newValue;
            newValue = oldValue.substring(0, oldValue.length - 1);
            field.setValue(newValue);
        },
        onClickChar: function(btn, field) {
            field.setValue(field.getValue() + btn.text);
        },
        getRandomNum: function(start, end) {
            return Math.floor(Math.random()*(end - start + 1) + start);
        },
        delArrayEl: function(arr, index) {
            if(index < 0) {
                return arr;
            }
            return arr.slice(0, index).concat(arr.slice(index + 1, arr.length));
        }
});