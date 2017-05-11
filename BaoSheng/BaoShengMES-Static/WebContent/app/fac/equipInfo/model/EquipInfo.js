Ext.define('bsmes.model.EquipInfo', {
			extend : 'Ext.data.Model',
			fields : ['id', 'equipAlias', 'name', 'code', 'ecode', 'model', 'belongLine', 'productLineId', 'maintainer', 'type', {
						name : 'nextMaintainDate',
						type : 'date'
					}, {
						name : 'nextMaintainDateFirst',
						type : 'date'
					}, {
						name : 'nextMaintainDateSecond',
						type : 'date'
					}, {
						name : 'nextMaintainDateOverhual',
						type : 'date'
					}, 'nextMaintainDateFirstStr', 'nextMaintainDateSecondStr', 'nextMaintainDateOverhualStr', {
						name : 'maintainDate',
						type : 'int',
						convert : function(v, record) {
							if (!record.get('isNeedMaintain')) {
								return '';
							}
							return v;
						}
					}, {
						name : 'maintainDateFirst',
						type : 'int',
						convert : function(v, record) {
							return v;
						}
					}, {
						name : 'maintainDateSecond',
						type : 'int',
						convert : function(v, record) {
							return v;
						}
					}, {
						name : 'maintainDateOverhaul',
						type : 'int',
						convert : function(v, record) {
							return v;
						}
					}, {
						name : 'isNeedMaintain',
						type : 'bool',
						persist : false
					}]
		});