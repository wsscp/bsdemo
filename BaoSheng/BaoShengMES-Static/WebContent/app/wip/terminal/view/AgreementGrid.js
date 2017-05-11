/**
 * Created by 合同信息 on 2014/6/11 0011.
 */
Ext.define("bsmes.view.AgreementGrid",{
    extend:'Ext.grid.Panel',
    alias:'widget.agreementGrid',
    title : "合同信息",
    store : 'AgreementStore',
    itemId : 'agreementGrid',
    forceFit : true,
    rowLines:true,
    columnLines:true,
    columns : [{
        text : Oit.msg.pla.product.contractNo,
        dataIndex : 'contractNo',
        flex:2
    }, {
        text :  Oit.msg.pla.product.orderLength,
        dataIndex : 'orderLength',
        flex:1
    },{
        text:'在该机台生产的长度',
        dataIndex:'orderProLengthOnEquip',
        flex:1
    }, {
        text : Oit.msg.pla.product.productType,
        dataIndex : 'productType',
        flex:2
    }, {
        text : Oit.msg.pla.product.productSpec,
        dataIndex : 'productSpec',
        flex:1
    }]
});