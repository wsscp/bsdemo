/**
 * Created by joker on 2014/8/15 0015.
 */
Ext.define('bsmes.view.UserInfoForm',{
    extend : 'Oit.app.view.form.HForm',
    alias : 'widget.userInfoForm',
    layout : 'vbox',
    padding:'10 10 10 10',
    items: [{
                xtype: 'fieldset',
                title:Oit.msg.bas.userInfo.title.userInfo,
                width:'100%',
                defaults : {
                    labelAlign : 'right',
                    labelWidth : 150,
                    width:500
                },
                items: [{
                            fieldLabel:Oit.msg.bas.userInfo.name,
                            name:'name',
                            xtype:'displayfield'
                        },{
                            fieldLabel:Oit.msg.bas.userInfo.email,
                            name:'email',
                            xtype:'textfield',
                            vtype: 'email'
                        },{
                            fieldLabel:Oit.msg.bas.userInfo.telephone,
                            name:'telephone',
                            xtype:'textfield'
                        },{
                            fieldLabel:Oit.msg.bas.userInfo.certificate,
                            name:'certificate',
                            xtype:'displayfield'
                        },{
                            name:'employeeId',
                            xtype:'hidden'
                        }]
            },{
                xtype:'fieldset',
                title:Oit.msg.bas.userInfo.title.updatePwd,
                width:'100%',
                defaults : {
                    labelAlign : 'right',
                    labelWidth : 150,
                    width:500,
                    xtype:'textfield',
                    inputType:'password'
                },
                items:[{
                            fieldLabel:Oit.msg.bas.userInfo.nowPassword,
                            name:'oldPassword',
                            vtype:'oldPassword',
                            initialPassField:'password',
                            vtypeText:Oit.msg.bas.userInfo.msg.nowPwdMsg
                    },{
                            fieldLabel:Oit.msg.bas.userInfo.newPassword,
                            name:'newPassword',
                            id:'newPassword'
                    },{
                            fieldLabel:Oit.msg.bas.userInfo.confirmPassword,
                            name:'confirmPassword',
                            vtype: 'password',
                            initialPassField:'newPassword',
                            vtypeText:Oit.msg.bas.userInfo.msg.confirmPwdMsg
                    },{
                            name:'userId',
                            xtype:'hidden'
                    },{
                            name:'password',
                            xtype:'hidden',
                            id:'password'
                    }]
            }
    ],
    buttons : [ {
        itemId : 'save',
        text : Oit.btn.save
    }],
    initComponent:function(){
        var me = this;
        me.callParent(arguments);

        Ext.Ajax.request({
            url:'userInfo/getUserInfo',
            method:'GET',
            success:function(response){
                var result = Ext.decode(response.responseText);
                var infoModel = Ext.create('bsmes.model.UserInfo', result);
                me.loadRecord(infoModel);
            }
        });
    }
});

Ext.apply(Ext.form.VTypes, {
    //增加密码的验证
    password: function(val, field) {
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    }
});

Ext.apply(Ext.form.VTypes, {
    //增加密码的验证
    oldPassword: function(val, field) {
        if(Ext.isEmpty(val)){
            return true;
        }
        var pwd = Ext.getCmp(field.initialPassField);
        return ($.md5(val) == pwd.getValue());
    }
});