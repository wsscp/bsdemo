Ext.define('bsmes.model.SetPriority',{
    extend: 'Ext.data.Model',
    fields:[
        'id',
        'customerCompany',
        'importance',//客户重要程度
        'operator',
        'contractNo',//合同号
        {
            name:'customerOaDate',//客户要求交货期
            type:'date'
        },
        {
            name:'fixedOa',
            type:'boolean',
            renderer:function(value){
                if(value){
                    return Oit.msg.boolean.YES;
                }else{
                    return Oit.msg.boolean.NO;
                }
            }
        },
        'seq',
        'highPriorityId'
    ]
});