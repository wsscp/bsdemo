/**
 * Created by joker on 2014/6/24 0024.
 */
Ext.define('bsmes.model.RealCost',{
    extend:'Ext.data.Model',
    fields:[
        'id',
        'batchNo',
        {
            name:'createTime',
            type:'date'
        }
    ]
});