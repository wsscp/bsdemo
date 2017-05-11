Ext.define('bsmes.view.EquipLineForm', {
			extend : 'Ext.form.Panel',
			alias : 'widget.equipLineForm',
			defaultType : 'textfield',
			bodyPadding : '5 50',
			autoScroll : true,
			url : '',
			items : [{
						xtype : 'textareafield',
						name : 'productCode',
						grow : true,
						fieldLabel : '产品代码',
						anchor : '100%'
					}, {
						xtype : 'textareafield',
						name : 'processCode',
						grow : true,
						fieldLabel : '工序代码',
						anchor : '100%'
					}, {
						xtype : 'textareafield',
						name : 'equipCode',
						grow : true,
						fieldLabel : '生产线',
						anchor : '100%'
					}]
		});