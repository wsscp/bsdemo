Ext.define('bsmes.model.ProcessQc', {
	extend : 'Ext.data.Model',
	fields : [
        'id',
	    'checkItemName',
	    'checkItemCode',
	    'dataUnit',
	    'itemTargetValue',
	    'itemMaxValue',
	    'itemMinValue',
	    'acquistionValue',
        'dataUnit',
        'qcValue',
        'qcResult',
        {
          name:'needDa',
          type:'boolean'
        },
	    {
            name: 'itemValueRange',
            convert: function(value, record) {
                var receiptMinValue  = record.get('itemMinValue');
                var receiptMaxValue  = record.get('itemMaxValue');
                if (receiptMinValue || receiptMaxValue) {
                	return receiptMinValue + '~' + receiptMaxValue;
                }
                return '';
            }
        }
	 ]
});
