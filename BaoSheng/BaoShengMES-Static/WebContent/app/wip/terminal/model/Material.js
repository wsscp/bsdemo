Ext.define('bsmes.model.Material', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'matCode'
        },
        {
            dataIndex: 'name'
        },
        {
            dataIndex: 'location'
        },
        {
            dataIndex: 'pic'
        },
        {
            dataIndex: 'batchNo'
        }，
        {
            dataIndex: 'inAttrDesc'
        }
    ]
});