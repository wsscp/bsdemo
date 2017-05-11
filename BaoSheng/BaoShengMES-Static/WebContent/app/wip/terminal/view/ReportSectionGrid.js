Ext.define('bsmes.view.ReportSectionGrid',{
    extend:'Ext.grid.Panel',
    forceFit : true,
    rowLines:true,
    columnLines:true,
    overflowY:'auto',
    height:200,
    columns: [{
                    xtype: 'rownumberer',
                    text: Oit.msg.wip.terminal.sn,
                    width: 50
                },
                {
                    dataIndex: 'startLocal',
                    text: '起始位置',
                    width:90
                },
                {
                    dataIndex: 'sectionLocal',
                    text: '结束位置',
                    width:90
                },
                {
                    dataIndex: 'sectionLength',
                    text: '分段长度',
                    width:90
                },
                {
                    dataIndex:'sectionTypeText',
                    text:'分段类型',
                    width:90
                },
                {
                    dataIndex:'contractNo',
                    text:'合同号',
                    width:185
                }]
});