Ext.define('bsmes.model.EquipRepairRecord', {
    extend: 'Ext.data.Model',
    fields: [{name : 'eventInfoId',type : 'string'},
             {name : 'startRepairTime',type : 'date'},
             {name : 'finishRepairTime',type : 'date'},
             {name : 'repairMan',type : 'string'},
             {name : 'failureModel',type : 'string'},
             {name : 'equipTroubleDescribetion',type : 'string'},
             {name : 'equipTroubleAnalyze',type : 'string'},
             {name : 'repairMeasures',type : 'string'},
             {name : 'equipName',type : 'string'},
             {name : 'equipModelStandard',type : 'string'},
             {name : 'protectMan',type : 'string'},
             {name : 'createTime',type : 'date'}]
});
