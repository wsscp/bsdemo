Ext.define('bsmes.store.LocationStore',{
		extend:'Oit.app.data.GridStore',
		model:'bsmes.model.Location',
		sorters:[{}],
		proxy:{
			url:'location',
			extraParams : Oit.url.urlParams()
		}
});