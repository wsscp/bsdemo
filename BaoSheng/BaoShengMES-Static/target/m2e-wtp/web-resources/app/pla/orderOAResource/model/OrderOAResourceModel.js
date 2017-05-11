Ext.define('bsmes.model.OrderOAResourceModel',{
    extend: 'Sch.model.Resource',
    fields: [{name: 'Id',mapping:'id'},
             {name: 'Name',mapping:'name'},
             {name: 'startDate', mapping: 'startDate', type : 'date'},
             {name: 'endDate', mapping: 'endDate', type : 'date'},
             {name: 'percentDone', mapping: 'percentDone'},
             {name: 'outPut',mapping:'outPut'}]
});
