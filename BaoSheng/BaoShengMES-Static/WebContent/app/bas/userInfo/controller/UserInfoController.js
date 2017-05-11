/**
 * Created by joker on 2014/8/15 0015.
 */
Ext.define('bsmes.controller.UserInfoController', {
    extend: 'Ext.app.Controller',
    view: 'userInfoForm',
    views: ['UserInfoForm'],
    constructor: function () {
        var me = this;

        // 初始化refs
        me.refs = me.refs || [];
        me.refs.push({
            ref: 'form',
            selector: me.view
        });

        me.callParent(arguments);
    },
    init: function () {
        var me = this;
        me.control(me.view + ' button[itemId=save]', {
            click: function () {
                var form = me.getForm();
                form.updateRecord();
                if (form.isValid()) {
                    var record = form.getRecord();
                    if(!Ext.isEmpty(record.get('newPassword'))){
                        if(Ext.isEmpty(record.get('oldPassword'))){
                            Ext.Msg.alert(Oit.msg.PROMPT,"请输入现在的密码");
                            return;
                        }
                        if(Ext.isEmpty(record.get('confirmPassword'))){
                            Ext.Msg.alert(Oit.msg.PROMPT,"请输入确认密码！");
                            return;
                        }
                        record.set('newPassword', $.md5(record.get('newPassword')));
                    }
                    Ext.Ajax.request({
                        url:'userInfo/updateUserInfo',
                        method:'POST',
                        params:record.getData(),
                        success:function(response){
                            Ext.Msg.alert(Oit.msg.PROMPT,"保存成功");
                        },
                        failure:function(form, action){
                            Ext.Msg.alert(Oit.msg.PROMPT,"保存失败");
                        }
                    });
                }
            }
        });
    }
});
