Ext.define('bsmes.view.ProcessIdleEquipWindow',{
    extend:'Ext.window.Window',
    title: '可选设备',
    width:250,
    layout: 'fit',
    modal: true,
    plain: true,
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
            buttons: [{
                itemId: 'ok',
                text:Oit.btn.ok,
                handler:function(){
                    var form = me.items.items[0]
                    var equipCodes = '';
                    if( form.getValues().equipCode){
                        equipCodes = form.getValues().equipCode.toString();
                        var updateInfo = form.getValues();
                        updateInfo.equipCodes = equipCodes;
                        Ext.Ajax.request({
                           url:'orderOA/updateProDecEquipInfo',
                           method:'POST',
                           params:updateInfo,
                           success:function(response){
                               me.close();
                               Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.orderOA.msg.enableSuccess);
                               me.parentGrid.getStore().reload();
                           }
                        });
                    }else{
                        Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.orderOA.msg.selectOnMsg);
                    }
                }
            }, {
                itemId: 'cancel',
                text:Oit.btn.cancel,
                scope: me,
                handler: me.close
            }]
        });

        me.items = [{
            xtype: 'form',
            bodyPadding: '12 10 10',
            defaultType: 'textfield',
            items: [{
                xtype: 'checkboxgroup',
                columns: 1,
                vertical: true,
                items: me.checkboxItem
            },{
                xtype:'hiddenfield',
                name:'orderItemId',
                value:me.orderItemId
            },{
                xtype:'hiddenfield',
                name:'processId',
                value:me.processId
            }]
        }],

        this.callParent(arguments);
    }
});