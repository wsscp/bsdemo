Ext.define("bsmes.model.ProductTrace", {
			extend : 'Ext.data.Model',
			fields : ['ID', 'OPERATOR', 'CUSTOMERCOMPANY', 'PRODUCTCODE', 'YUNMU', 'YMCOUNT', {
						name : 'ORDERLENGTH',
						type : 'double'
					}, 'CONTRACTNO', 'CRAFTSID', 'CRAFTSCNAME', {
						name : 'CONTRACTLENGTH',
						type : 'double'
					}, 'PLANSTARTDATE', 'PLANFINISHDATE', 'PRODUCTTYPE', 'OADATE', 'PRODUCTSPEC', 'CUSTPRODUCTSPEC', 'CUSTPRODUCTTYPE',
					'WIRESSTRUCTURE', 'SALESORDERITEMID', {
						name : 'STANDARDLENGTH',
						type : 'double'
					}, {
						name : 'HASYUNMU',
						type : 'double'
					}, 'ROWINDEX', 'CONDUCTORSTRUCT', 'SENDDOWNPERCENT', 'PROCESSREQUIRE', 'REMARKS', 'STATUS', 'NUMBEROFWIRES', 'ERPBM',
					'ORDERFILENUM', 'SPLITLENGTHROLE', 'SPECIALFLAG']
		});