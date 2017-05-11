Ext.define('bsmes.view.UserIntoDetailValidWindow',{
    extend : 'Ext.window.Window',
    alias : 'widget.userIntoDetailValidWindow',
    width : 500,
    height : 350,
    layout:'fit',
    modal: true,
    title: Oit.msg.wip.title.valid,
    initComponent: function() {
        var me = this;
        me.items = [{
            xtype: 'form',
            bodyPadding: '80 0 0 30',
            defaultType: 'textfield',
            url: 'terminal/validUserPerm',
            method:'POST',
            fieldDefaults: {  // 设置field的样式
                msgTarget: 'side', // 错误信息提示位置
                labelWidth: 100,  // 设置label的宽度
                labelCls: 'x-boxlabel-size-20',
                fieldCls: 'x-panel-body-default',
                height: 30,
                width: 330
            },
            items: [{
                xtype: 'textfield',
                fieldLabel: Oit.msg.wip.workOrder.userCode,
                name: 'userCode',
                margin: '30 30 30 30',
                allowBlank : false,
                plugins:{
                    ptype:'virtualKeyBoard'
                },
                listeners : {
                    specialKey : function(field, e) {
                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
                        }
                    }
                }
            },
            {
                xtype: 'hiddenfield',
                name: 'equipCode'
            }]
        }];

        Ext.apply(me, {
            buttons: [{
                itemId: 'ok',
                text:Oit.btn.ok,
                id:'userInfoDetailValidOkButton',
                listeners:{
                    click:function(){
                        var form = me.down('form').getForm();
                        var equipCode = form.findField('equipCode').getValue();
                        if(form.isValid()){
                            var userCode = form.findField('userCode').getValue();
                            form.submit({
                                success:function(form,action){
                                    Ext.util.Cookies.set("operator",userCode);
                                    me.close();
                                    window.location.target="_self";
                                    window.location.href='/bsmes/wip/terminal/' + equipCode + '.action';
                                },
                                failure:function(form,action){
                                    var result = Ext.decode(action.response.responseText);
                                    Ext.Msg.alert(Oit.msg.PROMPT, result.msg);
                                }
                            })
                        }
                    }
                }
            },'->',{
                itemId: 'cancel',
                text:Oit.btn.cancel,
                scope: me,
                handler: me.close
            }]
        });

        this.callParent(arguments);
    }

});