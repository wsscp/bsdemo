Ext.define('bsmes.model.ProcessIn', {
	extend : 'Ext.data.Model',
	fields : [
        'id',
        'matCode',
        'matName',
        'location',
        'pic',
        {
            name : 'hasPutIn',
            type : 'boolean'
        },
        'planAmount',
        'batchNo',
        {
            name:'putTime',
            type:'date'
        },
        {
            name:'rowSpanSize',
            type:'number'
        },
        'productProcessId',
        'matType',
        'color',
        'matSpec',
        'matSize',
        'contractNo',
        'productCode',
        'quantity',
        {
            name:'planDate',
            type:'date'
        }
    ]
});