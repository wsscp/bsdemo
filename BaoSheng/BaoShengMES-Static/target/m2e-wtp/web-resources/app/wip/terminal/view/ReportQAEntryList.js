Ext.define('bsmes.view.ReportQAEntryList',{
    extend: 'Ext.grid.Panel',
    alias:'widget.reportQAEntryList',
    id:'reportQAEntryListId',
    plugins: [
        Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        })
    ],
    labelWidth : 'auto',
    forceFit : false,
    width:document.body.scrollWidth-120,
    height:document.body.scrollHeight-187,
    store:'ProcessQAEntryStore',
    requires : [ 'Oit.app.view.form.HForm' ],
    columns:[{
        header:Oit.msg.wip.dataCollection.checkItemCode,
        dataIndex:'checkItemName',
        flex:3
    },{
        header:Oit.msg.wip.dataCollection.checkItemRange,
        dataIndex:'checkItemRange',
        flex:2
    },{
        header:Oit.msg.wip.dataCollection.itemTargetValue,
        dataIndex:'itemTargetValue',
        flex:1
    },{
        header:Oit.msg.wip.dataCollection.qcValue,
        dataIndex:'qcValue',
        editor:{
            xtype:'textfield',
            decimalPrecision:6,
            listeners:{
                focus:function( me, the, eOpts ){
                    var win =  Ext.create('bsmes.view.VirtualKeyBoardWindow',{
                        targetGridId:'reportQAEntryListId',
                        targetFieldName:'qcValue',
                        targetVlaue:me.getValue()
                    });

                    win.show();
                    win.down('form').getForm().findField('targetValue').focus(false,100);
                }
            }
            //小数点后位数
        },
        flex:1
    },{
        header:Oit.msg.wip.dataCollection.dataUnit,
        dataIndex:'dataUnit',
        flex:1
    },{
        header:Oit.msg.wip.dataCollection.qcResult,
        dataIndex:'qcResult',
        flex:3,
        renderer:function(value, cellmeta, record,rowIndex,store){
            var pass=Oit.msg.wip.dataCollection.pass;
            var noPass=Oit.msg.wip.dataCollection.noPass;
            var res="<label><input value='"+pass+"' name='qcResult0"+rowIndex+"' type='radio' checked='checked'>"+pass+"</label>"+
                    "<label><input value='"+noPass+"' name='qcResult0"+rowIndex+"' type='radio'>"+noPass+"</label>";
            return res;
        }
    }],
    listeners:{
        'beforeedit':function(editor, e){
            var data=e.record.data;
            var itemTargetValue=data.itemTargetValue;
            if(itemTargetValue && isNaN(itemTargetValue)){
                if(e.field == 'qcValue'){
                    return false;
                }
            }
        },
        'edit':function(editor, e){
            var data=e.record.data;
            var value=e.value;
            var res=document.getElementsByName('qcResult0'+e.rowIdx);
            if(value==null){
                res[0].disabled=false;
                res[0].checked=false;
                res[1].disabled=false;
                res[1].checked=false;
                return;
            }
            var checkItemRange=data.checkItemRange;
            if(checkItemRange){
                var array=checkItemRange.split('~');
                if(value<array[0]*1 || value>array[1]*1){
                    res[1].checked=true;
                    e.store.getAt(e.rowIdx).set('qcResult',Oit.msg.wip.dataCollection.noPass);
                }else{
                    res[0].checked=true;
                    e.store.getAt(e.rowIdx).set('qcResult',Oit.msg.wip.dataCollection.pass);
                }
                res[0].disabled=true;
                res[1].disabled=true;
            }
        }
    }/*,
    dockedItems : [{
        xtype : 'toolbar',
        dock : 'top',
        items : [{
            xtype : 'form',
            items: [{
                fieldLabel: '检测类型',
                xtype:'radiogroup',
                labelAlign:'left',
                labelWidth:80,
                columns:4,
                width:600,
                vertical:true,
                items:[ {boxLabel: '首检',  name:'type', inputValue: 'FIRST_CHECK',checked: true},
                        {boxLabel:'下车检', name:'type', inputValue: 'OUT_CHECK'}],
                listeners:{
                    'change':function( me, newValue, oldValue, eOpts ){
                        var grid = me.up('grid');
                        var store = grid.getStore();
                        store.getProxy().url = '/bsmes/wip/terminal/queryProcessQcValue';
                        store.load({
                            params:{
                                workOrderNo:Ext.getCmp('reportInfoForm').getRecord().get('currentTask'),
                                type:newValue.type
                            }
                        });
                    }
                }
            }]
        }]
    }]*/
});