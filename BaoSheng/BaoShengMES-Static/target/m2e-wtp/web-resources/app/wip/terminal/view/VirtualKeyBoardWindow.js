/**
 * Created by chanedi on 14-3-4.
 */
Ext.define('bsmes.view.VirtualKeyBoardWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.virtualKeyBoardWindow',
    width: 400,
    height:300,
    layout: 'fit',
    modal: true,
    plain: true,
    targetGridId:null,
    targetFieldName:null,
    targetVlaue:null,
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            buttons: [{
                itemId: 'ok',
                text:Oit.btn.ok,
                handler:function(){
                    var value = me.down('form').getForm().findField('targetValue').getValue();
                    var grid =Ext.getCmp(me.targetGridId);
                    var record = grid.getSelectionModel().getSelection()[0];
                    record.set(me.targetFieldName,value);
                    me.close();
                    $("input[type='radio'], input[type='checkbox']").ionCheckRadio();
                }
            },'->', {
                itemId: 'cancel',
                text:Oit.btn.close,
                scope: me,
                handler: me.close
            }]
        });

        me.items = [{
            xtype: 'form',
            bodyPadding: '30 10 10',
            defaultType: 'textfield',
            defaults:{
                labelAlign:'right'
            },
            items:[{
                fieldLabel: '请输入检测值',
                name: 'targetValue',
                inputType:'textfield',
                labelWidth:120,
                value:me.targetVlaue,
                width:300,
                height:50,
                plugins:{
                    ptype:'virtualKeyBoard'
                }
            }]
        }],

        this.callParent(arguments);
    }
});