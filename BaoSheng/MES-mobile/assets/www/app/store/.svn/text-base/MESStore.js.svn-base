/**
 *@Project: 超级奶爸之宝宝喂养记
 *@Author: LionGIS@163.com
 *@Date: 2014-06-20
 *@Copyright: 代码开源，欢迎转载，但请保留版本信息.
 */

Ext.define('MES.store.MESStore', {
			extend : 'Ext.data.Store',
			config : {
				storeId : 'weiyangStore',
				model : 'MES.model.MESInfo',
				/*
				 * getGroupString: function(record) { return
				 * Ext.Date.format(record.get('date'), "Y-m-d"); },
				 */
				grouper : {
					sortProperty:"date", // 按照创建日期进行分组
					direction : "DESC",     // 日期倒序分组
					groupFn : function(record) {
						return Ext.Date.format(record.get('date'), "Y-m-d");
					}
				},
				sorters : [{
							property : 'date',
							direction : 'DESC'
						}],
				autoLoad : true
			}
		});