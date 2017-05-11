Ext.define('bsmes.model.Event',{
    extend: 'Sch.model.Event',
    resourceIdField : 'resourceId',
    startDateField  : 'startDate',
    endDateField    : 'endDate', 
    nameField       : 'name' ,
    clsField : 'halfProductCodeColor',
    fields : [
              'contractNo',
              'customerOrderNo',
              'workOrderNo',
              'productSpec',
              'halfProductCode',
              'productCode',
              'percentDone',
              {
                  name:'taskLength',
                  type:'number'
              }]
});
