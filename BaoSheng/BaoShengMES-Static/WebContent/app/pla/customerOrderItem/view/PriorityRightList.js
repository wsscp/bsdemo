Ext.define('bsmes.view.PriorityRightList',{
    extend:'Ext.grid.Panel',
    id:'rightGrid',
    stripeRows: true,
    height: 430,
    selType: 'checkboxmodel',
    alias:'widget.rightGridView',
    store: 'RightStore',
    forceFit : false,
    plugins: [{
        ptype: 'rowexpander',
        rowBodyTpl: [
            '<div id="{contractNo}">',
            '</div>'
        ]
    }],
    columns: [
        {
            text: Oit.msg.pla.customerOrderItem.customerContractNO,
            dataIndex:'contractNo',
            width:150
        },{
            text: Oit.msg.pla.customerOrderItem.customerCompany,
            dataIndex:'customerCompany',
            width:150
        },{
            text: Oit.msg.pla.customerOrderItem.importance,
            dataIndex:'importance',
            width:100
        },{
            text: Oit.msg.pla.customerOrderItem.customerOaDate,
            dataIndex:'customerOaDate',
            xtype:'datecolumn',
            format:'Y-m-d',
            width:100
        },{
            text: Oit.msg.pla.customerOrderItem.fixedOa,
            dataIndex:'fixedOa',
            width:150,
            renderer:function(value){
                if(value){
                    return Oit.msg.boolean.YES;
                }else{
                    return Oit.msg.boolean.NO;
                }
            }
        },{
            text: Oit.msg.pla.customerOrderItem.operator,
            dataIndex:'operator',
            width:100
        }],
    tbar 	: [{
        itemId:'move',
        text:Oit.msg.pla.customerOrderItem.button.move},
        {
            itemId:'down',
            text:Oit.msg.pla.customerOrderItem.button.down},
        {
            itemId:'top',
            text:Oit.msg.pla.customerOrderItem.button.top},
        {
            itemId:'end',
            text:Oit.msg.pla.customerOrderItem.button.end}],
    initComponent: function () {
        var me = this;
        this.callParent(arguments);
        me.view.on('expandBody', function (rowNode, record, expandRow, eOpts) {
            console.log(record);
            var renderId = record.get('contractNo');
            var url =  "customerOrderItem/findOrderItemInfo/"+record.get('id');
            var subGrid = Ext.create("bsmes.view.SubPriorityList",{
                renderTo: renderId,
                store:Ext.create('bsmes.store.SubPriorityStore')
            });
            var subStore = subGrid.getStore();
            subStore.getProxy().url=url;
            subStore.reload();
        });
        me.view.on('collapsebody', function (rowNode, record, expandRow, eOpts) {
            var parent = document.getElementById(record.get('contractNo'));
            var child = parent.firstChild;
            while (child) {
                child.parentNode.removeChild(child);
                child = child.nextSibling;
            }
        });
    }

});