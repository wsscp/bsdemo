/**
 * Created by JinHy on 2014/6/24 0024.
 */
Ext.define('bsmes.view.MatBatchGrid',{
    extend: 'Ext.grid.Panel',
    animCollapse : true,
    columnLines : true,
    autoWidth : true,
    autoHeight : true,
    forceFit:true,
    columns:[{
        text : Oit.msg.wip.terminal.batchNo,
        dataIndex:'batchNo'
    },{
        text :Oit.msg.wip.terminal.matInTime,
        dataIndex:'createTime',
        xtype: 'datecolumn',
        format: 'Y-m-d H:i:s'
    }],
    viewConfig:{
        stripeRows: false,
        getRowClass: function(record, rowIndex, rowParams, store){
            return 'x-grid-record-green';
        }
    }
});