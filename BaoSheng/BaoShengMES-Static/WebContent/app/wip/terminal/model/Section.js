/**
 * Created by chanedi on 14-3-4.
 */
Ext.define('bsmes.model.Section', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'sectionType',
        'orgCode',
        {
            name: 'sectionLength',
            type: 'number'
        }, {
            name: 'goodLength',
            type: 'number'
        },
        {
            name:'startLocal',
            type: 'number'
        },
        {
            name:'sectionLocal',
            type: 'number'
        },
        {
            name:'productLength',
            type:'number'
        },
        {
            name:'currentLength',
            type:'number'
        },
        {
            name:'reportLength',
            type:'number'
        },
        {
            name: 'wasteLength',
            convert: function (value, record) {
                var goodLength = record.get('goodLength');
                var sectionLength = record.get('sectionLength');
                return sectionLength - goodLength;
            }
        },
        'sectionType',
        'sectionTypeText',
        'contractNo',
        'coilNum',
        {
            name:'sumReportLength',
            type:'number'
        },
        {
            name:'rowSpanSize',
            type:'number'
        }
    ],

    validations: [
        {type: 'presence', field: 'sectionLength'},
        {type: 'presence', field: 'goodLength'}
    ]

});