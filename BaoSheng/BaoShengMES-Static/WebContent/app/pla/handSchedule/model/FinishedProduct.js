Ext.define('bsmes.model.FinishedProduct', {
			extend : 'Ext.data.Model',
			fields : ['id','model', 'spec', 'qualifying', 'dish', 'remarks','region' , {name : 'length', type : 'double'},{name:'usedLength',type:'double'}]
		});