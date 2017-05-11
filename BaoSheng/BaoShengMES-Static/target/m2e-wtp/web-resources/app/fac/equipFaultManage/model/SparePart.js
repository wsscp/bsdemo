Ext.define('bsmes.model.SparePart', {
    extend: 'Ext.data.Model',
    fields: [{name : 'newSparePartCode',type : 'string'},
             {name : 'sparePartModel',type : 'string'},
             {name : 'useSite',type : 'string'},
             {name : 'oldSparePartCode',type : 'string'},
             {name : 'oldSparePartSituation',type : 'string'},
             {name : 'eventInfoId', type : 'string'},
             {name : 'quantity',type : 'string'}]
});
