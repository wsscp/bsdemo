Ext.define('bsmes.model.EquipInformation', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'name', type: 'string'},
        {name: 'code', type: 'string'},
        {name: 'equipAlias', type: 'string'},
        {name: 'equipCategory', type: 'string'},
        {name: 'equipPrice', type: 'BigDecimal'},
        {name: 'equipFact', type: 'string'},
        {name: 'equipPurtime', type: 'date'},
        {name: 'model', type: 'string'},
        {name: 'isNeedMaintain', type: 'bool', persist: false},
        {name: 'productLineId', type: 'string'},
        {name: 'maintainer', type: 'string'},
        {name: 'subType', type: 'string'},
        {name: 'nextMaintainDate', type: 'date'},
        {name: 'nextMaintainDateFirstStr',type: 'string'},
        {name: 'nextMaintainDateSecondStr',type: 'string'},
        {name: 'nextMaintainDateSecond', type: 'date'},
        {name: 'nextMaintainDateOverhual', type: 'date'},
        {name: 'maintainDate', type: 'int', convert: function (v, record) {
            if (!record.get('isNeedMaintain')) {
                return '';
            }
            return v;
        }},
        {name: 'maintainDateFirst', type: 'int', convert: function (v, record) {
            return v;
        }},
        {name: 'maintainDateSecond', type: 'int', convert: function (v, record) {
            return v;
        }},
        {name: 'maintainDateOverhaul', type: 'int', convert: function (v, record) {
            return v;
        }},
        {name: 'type', type: 'string'}
    ],
    setTmplMap : function(tmplMap) {
        this.tmplMap = tmplMap;
    },
    getTmplMap : function() {
        return this.tmplMap;
    }
});
