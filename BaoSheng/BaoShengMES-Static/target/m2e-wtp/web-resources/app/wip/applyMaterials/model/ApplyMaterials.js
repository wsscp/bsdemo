Ext.define('bsmes.model.ApplyMaterials', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'equipCode',  type: 'string'},
        {name: 'userName', type: 'string'},
        {name: 'matName', type: 'string'},
        {name: 'applyQuntity', type: 'string'},
        {name: 'issueQuntity', type: 'string'},
        {name: 'createTime', type: 'date'},
        {name : 'id',type : 'string'},
        {name : 'status',type : 'string'}
    ]
});