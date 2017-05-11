/**
 * Created by JinHy on 2014/6/11 0011.
 */
Ext.define('bsmes.view.MaterialGrid',{
    extend:'Ext.grid.Panel',
    itemId : 'materialGrid',
    alias:'widget.materialGrid',
    woNo:'',
    store:'ProcessInStore',
    forceFit : true,
    columnLines:true,
    title : Oit.msg.wip.terminal.materialTitle,
    viewConfig:{
        stripeRows: false,
        enableTextSelection : true,
        getRowClass: function(record, rowIndex, rowParams, store){
            var resultColor = '';
            if(record.get("hasPutIn")){
                resultColor = 'x-grid-record-green';
            }
            return resultColor;
        }
    },
    sortableColumns:false,
    plugins: [
        {
            ptype: 'rowexpander',
            rowBodyTpl: [
                '<div id="{id}">',
                '</div>'
            ]
        }
    ],
    columns : [ {
        text : Oit.msg.wip.terminal.code,
        dataIndex : 'matCode',
        flex:3
    },  {
        text : Oit.msg.wip.terminal.name,
        dataIndex : 'matName',
        flex:3
    },{
        text : Oit.msg.wip.terminal.matColor,
        dataIndex : 'color',
        flex:1,
        sortableColumns:false
    },{
        text:Oit.msg.wip.terminal.matSpec,
        dataIndex : 'matSpec',
        flex:1,
        sortableColumns:false
    },{
        text : Oit.msg.wip.terminal.planAmount,
        dataIndex : 'planAmount',
        flex:1,
        sortableColumns:false
    }],
    initComponent: function () {
        var me = this;
        this.callParent(arguments);
        me.view.on('expandBody', function (rowNode, record, expandRow, eOpts) {
            var renderId = record.get('id');
            var url = "terminal/matBatchs";
            var subGrid = Ext.create("bsmes.view.MatBatchGrid", {
                renderTo: renderId,
                store: Ext.create('bsmes.store.MatBatchGridStore')
            });
            var subStore = subGrid.getStore();
            subStore.getProxy().url = url;
            subStore.load({
                params:{
                    matCode:record.get('matCode'),
                    workOrderNo:me.woNo
                }
            });

            subGrid.getEl().swallowEvent([
                'mousedown', 'mouseup', 'click',
                'contextmenu','dblclick', 'mousemove'
            ]);
        });
        me.view.on('collapsebody', function (rowNode, record, expandRow, eOpts) {
            var parent = document.getElementById(record.get('id'));
            var child = parent.firstChild;
            while (child) {
                child.parentNode.removeChild(child);
                child = child.nextSibling;
            }
        });
    }

});