Ext.define('bsmes.model.CustomerOrderItem',{
    extend : 'Ext.data.Model',
    fields : [
                'id',
                'orderLength',
                'contractLength',
                {
					name : 'productDate',
					type : 'date'
				}, {
					name : 'releaseDate',
					type : 'date'
				}, {
					name : 'subOaDate',
					type : 'date'
				}, {
					name : 'customerOaDate',
					type : 'date'
				}, {
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
                },
                'salesOrderItem.custProductType',
                'salesOrderItem.custProductSpec',
                'salesOrderItem.productType',
                'salesOrderItem.productSpec',
                'salesOrderItem.numberOfWires',
                'salesOrderItem.section',
                'salesOrderItem.wiresStructure',
                'salesOrderItem.wiresLength',
                'salesOrderItem.remarks',
                'salesOrderItem.contractAmount',
                'salesOrderItem.productCode',
                'salesOrderItem.idealMinLength',
                'salesOrderItem.standardLength',
                'customerCompany',
                'contractNo',
                'productName',
                'remarks', // 备注
                'processRequire', // 技术要求
                {name : 'planStartDate',type:'date'},
                {name : 'planFinishDate',type:'date'}
    ]
});