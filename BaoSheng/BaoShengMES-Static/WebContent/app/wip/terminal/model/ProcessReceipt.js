Ext.define('bsmes.model.ProcessReceipt', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id',
        'receiptName',
	    'receiptCode',
	    'dataUnit',
        'equipCode',
	    'receiptMaxValue',
	    'receiptMinValue',
	    'receiptTargetValue',
	    'setValue',
	    'daValue',
        {
            name:'needDa',
            type:'boolean'
        },
	    {
            name: 'receiptValueRange',
            convert: function(value, record) {
                var receiptMinValue  = record.get('receiptMinValue');
                var receiptMaxValue  = record.get('receiptMaxValue');
                if (receiptMinValue || receiptMaxValue) {
                	return receiptMinValue + '~' + receiptMaxValue;
                }
                return '';
            }
        }
	 ]
});