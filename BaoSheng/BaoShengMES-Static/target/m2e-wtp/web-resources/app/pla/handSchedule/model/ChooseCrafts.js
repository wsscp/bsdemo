Ext.define('bsmes.model.ChooseCrafts', {
			extend : 'Ext.data.Model',
			fields : ['id', 'craftsCode', 'craftsName', 'craftsCname',  {name : 'createTime', type : 'date'}]
		});