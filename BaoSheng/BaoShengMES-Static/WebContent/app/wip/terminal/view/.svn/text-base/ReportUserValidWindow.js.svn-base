Ext.define('bsmes.view.ReportUserValidWindow',{
    extend : 'Ext.window.Window',
    alias : 'widget.reportUserValidWindow',
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
                            Ext.getCmp('reportUserValidWindowButton').fireEvent('click')
                        }
                    }
                }
            },
                /*{
                    xtype: 'textfield',
                    inputType: 'password',
                    fieldLabel: Oit.msg.wip.workOrder.password,
                    id: 'passwordtmp',
                    margin: '30 30 30 30',
                    plugins:{
                        ptype:'virtualKeyBoard'
                    },
                    allowBlank : false
                },{
                    xtype: 'hiddenfield',
                    name: 'password'
                },*/{
                    xtype: 'hiddenfield',
                    name: 'equipCode'
                }
            ]
        }];

        Ext.apply(me, {
            buttons: [{
                itemId: 'ok',
                id:'reportUserValidWindowButton',
                text:Oit.btn.ok
            },'->', {
                itemId: 'cancel',
                text:Oit.btn.cancel,
                scope: me,
                handler: me.close
            }]
        });

        this.callParent(arguments);
    }

});