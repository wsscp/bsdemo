Ext.define('bsmes.model.WorkOrderReportModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'workOrderNo',
        'serialNum',
        'reportUserCode',
        {
            name : 'reportTime',
            type : 'date'
        },
        'reportLength',
        'statusText',
        'color',
        'productType',
        'productSpec',
        'reportUserName'
    ]
});