Ext.define('bsmes.model.CustomerOrder',{
    extend : 'Ext.data.Model',
    fields : [
        'id',
        'customerCompany',
        'contractNo',
        'operator',
        {
            name:'customerOaDate',
            type:'date'
        },
        {
            name:'oaDate',
            type:'date'
        },
        {
            name:'planStartDate',
            type:'date'
        },
        {
            name:'planFinishDate',
            type:'date'
        },
        {
            name:'lastOa',
            type:'date'
        },
        {
            name:'status',
            type:'string',
            renderer:function(value){
                var satusArray = Ext.decode(Ext.fly('customerOrderStatus').getAttribute('statusArray'));
                var text = "";
                Ext.each(satusArray,function(customerOrderStatus,i){
                    if(value == customerOrderStatus[0]){
                        text = customerOrderStatus[1];
                        return;
                    }
                });
                return text;
            }
        }, {
            name:'seq',
            type:'int'
        }, {
            name:'orderFileNum',
            type:'int'
        }, {
            name:'fixedOaText'
        }, {
            name:'lastOa',
            type:'date'
        }
    ]
});