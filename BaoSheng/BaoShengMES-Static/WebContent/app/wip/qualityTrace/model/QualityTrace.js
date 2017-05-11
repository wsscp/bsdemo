Ext.define("bsmes.model.QualityTrace", {
			extend : 'Ext.data.Model',
			fields : ['id',
					'contractNo', // 合同号
					'salesOrderNo', // 客户销售订单编号
					'customerOrderNo', // 客户生产单号
					'custProductType', // 客户产品型号
					'custProductSpec', // 客户产品规格
					'productCode', // 产品代码
					'productType', // 产品规格
					'productSpec', // 产品型号
					'workOrderNo', // 生产单号
					'craftsId', // 工艺id
					'processId', // 工序id
					'processCode', // 工序编号
					'processName', // 工序名称
					'sampleBarcode', // 样品条码
					'checkItemCode', // 采集参数
					'checkItemName',
					'eqipCode', // 生产设备编号
					'qcValue', // 检测值
					'qcResult', // 检测结论
					'processName', // 工序名称
					'operator', // 经办人
					'equipName', // 设备名称
					'equipAlias', // 设备别名
					'userName', // 质检员
					{
						name : 'type',
						type : 'string',
						renderer : function(value) {
							if (value == 'FIRST_CHECK') {
								return Oit.msg.wip.qualityTrace.firstCheck;
							} else if (value == 'MIDDLE_CHECK') {
								return Oit.msg.wip.qualityTrace.middleCheck;
							} else if (value == 'IN_CHECK') {
								return Oit.msg.wip.qualityTrace.inCheck;
							} else {
								return Oit.msg.wip.qualityTrace.outCheck;
							}
						}
					}, 'checkEqipCode', // 数据检测设备
					{
						name : 'createTime',
						type : 'date'
					}]
		});