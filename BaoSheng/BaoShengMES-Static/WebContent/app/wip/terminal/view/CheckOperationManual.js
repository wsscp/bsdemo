/**
 * CheckOperationManual.js 点检操作手册
 */
Ext.define('bsmes.view.CheckOperationManual', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.CheckOperationManual',
	processCode : '',
	autoScroll : true,
	initComponent : function() {
		var me = this;
		var items = [];
		var processCode = me.processCode;
		if (processCode == 'Extrusion-Single,Extrusion-Dual') {
			items = [{
						xtype : 'fieldset',
						title : '绝缘挤塑机作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、按规定对设备加油润滑,检查挤塑机电气元件及安全防护装置是否完好无损和灵敏可靠,热电偶指示温度是否正常,出现问题应通知维修人员,不得私自乱动'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、开机时应检查主机减速箱、牵引箱、收线箱是否正常,风机工作是否良好'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、开车前要检查各段的加热温度是否达到工艺要求的实际温度,加温时间要足够'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、不得在塑料塑化不良的情况下进行挤出生产,以防事故发生,发现问题应立即停机并设法排除'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、车速应由低向高渐渐升速,严禁高速启起动,并观察仪表上指示的电流值与温度是否正常,以防电流过大引起设备损坏'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、升降线盘时要注意升降极限位置不得过高或过低'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '7、工作中经常注意主机牵引机及收线各机组运转声响'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '8、严格按喷码机操作手册使用喷码机'
								}]
					}];
		} else if (processCode == 'Respool') {
			items = [{
						xtype : 'fieldset',
						title : '火花配套作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、对设备按规定进行加油润滑'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、开车前检查所有安全防护罩装置及旋转部位的紧固件是否拧紧上牢'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、复绕时禁止用手摸铜线、铁丝，如发现打套时须停车处理，防止勒手'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、复绕线芯或其它材料时操作者严禁站在机器旁或收线盘的后面,谨防线芯中途断开后弹出造成意外人身事故'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、放线盘上的线芯快要绕完时，设备要及时减速，停住车时放线盘上最好能留2~3圈线芯，以防线芯弹跳伤人'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、机器运转时，禁止对设备进行润滑、检修和擦拭，衣着上不得有飘拂部分，以免卷入设备造成人身事故'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '7、设备上严禁存放任何东西和杂物，以免卷入设备造成事故'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '8、工作区域的材料必须堆放整齐，地面要保持清洁，设备开动时严禁闲杂人员在工作区逗留'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '9、中途停电或离开设备时，操作者应关闭设备总电源，并对周围进行检查，确认无事后方可离开'
								}]
					}];
		} else if (processCode == 'wrapping_ymd') {
			items = [{
						xtype : 'fieldset',
						title : '立式绕包作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、开机前检查设备各部位有无异常现象,按规定进行润滑加油,短暂启动进行运转,运转正常时方可开机,开机时要缓慢启动,逐渐加速'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、检查设备所有安全装置,紧急停车按钮,信号装置开关及时启动是否灵活好用,否则禁止开机'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、设备运转是时禁止润滑,在设备运转区域禁止擦洗设备、打扫卫生和维修工作'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、收放线盘固定锁紧销应完备良好,上线时应把线盘固定销子关牢,放线盘张力制动装置应灵活好用,损坏的要及时修理,否则不能开机'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、禁止用手转动绕包头和用手帮助停车'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、停车时应将电位器旋至零位,设备进行修理或操作者离开机台时应将电源开关拉下'
								}]
					}];
		} else if (processCode == 'shield') {
			items = [{
						xtype : 'fieldset',
						title : '铠装作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、启动设备前认真检查各部位,如绕包头、钢带头、收线装置是否完好,设备周围是否有人和其它物品,在确认安全的情况下方能开机'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、绕包装置上锁紧,螺母应安全可靠,以防甩脱伤人'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、牵引铜套要及时加油以防磨损'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、收线力矩电机和牵引轮的速度要协调,防止收线过高引起收线架损坏或导致电缆拉伤'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、开机前要按警铃,车速由低至高到正常运转速度'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、当收线盘上排列的线不整齐时,在运转情况下不得用手直接整理'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '7、设备保护罩上的零部件及限位开关等不得随便拆卸,限位开关要灵敏可靠'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '8、设备停机后操作手柄应恢复到原位,并拉掉电源开关'
								}]
					}];
		} else if (processCode == 'Jacket-Extrusion' || processCode == 'Jacket-Extrusion,Extrusion-Single' || processCode == 'Jacket-Extrusion,Extrusion-Single,Extrusion-Dual') {
			items = [{
						xtype : 'fieldset',
						title : '护套挤塑作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、按规定对设备加油润滑,检查挤塑机电气元件及安全防护装置是否完好无损和灵敏可靠,热电偶指示温度是否正常,出现问题应通知维修人员,不得私自乱动'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、开机时应检查主机减速箱、牵引箱、收线箱是否正常,风机工作是否良好'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、开车前要检查各段的加热温度是否达到工艺要求的实际温度,加温时间要足够'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、不得在塑料塑化不良的情况下进行挤出生产,以防事故发生,发现问题应立即停机并设法排除'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、车速应由低向高渐渐升速,严禁高速启起动,并观察仪表上指示的电流值与温度是否正常,以防电流过大引起设备损坏'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、升降线盘时要注意升降极限位置不得过高或过低'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '7、工作中经常注意主机牵引机及收线各机组运转声响'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '8、严格按喷码机操作手册使用喷码机'
								}]
					}];
		} else if (processCode == 'Braiding') {
			items = [{
						xtype : 'fieldset',
						title : '编织机作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、设备开启前，检查主机各部分有无异常，各润滑部位按要求加注润滑油，并进行一次空车运转，穿戴好防护用品'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、将线头经导轮至花盘与编织导体后，再经牵引轮绕到收线盘上，固定好后，正式开车'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、每班停车后，应及时将转盘导轨面，曲线槽，上下锭及摆杆等部位的灰尘、油污及断线头清除干净'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、机器运转时应注意对机器进行观察，经常检查润滑油能否从管道顺利到达润滑点。摆臂支架的旋转部件内须充满黄油，每工作120小时后须往内加满黄油'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、凡是自动润滑系统不能涉及的机器运转部件，应经常进行人工添加润滑剂。节距交换齿轮，收放齿轮和牵引蜗轮蜗杆部位应每班添加工业脂一次；排线器光杆每班添加工业机油数次；排线器光杆及收线主动齿轮轴两端的带座轴承油嘴处，每班应加油一次'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、如出现不明情况的停机，可根据《故障报警》画面出现的报警闪烁灯信号，作相应的排除；如经常发生和油泵报警设定时间相同的无报警信号的停机，应检查油泵.油路是否正常；发现润滑油不能到达润滑点则须排除故障才能重新启动'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '7、设备运转过程中，不得进行挂轮的配换，经常注意设备各部运转是否正常，操作工不得擅离工作岗位或交给无操作证的人操作，如设备出现故障，应及时停车，请修理人员协助解决后，再重新开车'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '8、工作完毕，切断总电源，清扫工作现场，清除花盘上的丝毛和灰尘，擦拭设备各部，把剩余挂轮存放在指定区域，摆放整齐，填写好工艺流程卡及各项报表和记录，做好交接班工作'
								}]
					}];
		} else if (processCode == 'wrapping') {
			items = [{
						xtype : 'fieldset',
						title : '卧式绕包作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、开机前检查设备各部位有无异常现象,按规定进行润滑加油,短暂启动进行运转,运转正常时方可开机,开机时要缓慢启动,逐渐加速'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、检查设备所有安全装置,紧急停车按钮,信号装置开关及时启动是否灵活好用,否则禁止开机'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、设备运转是时禁止润滑,在设备运转区域禁止擦洗设备、打扫卫生和维修工作'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、收放线盘固定锁紧销应完备良好,上线时应把线盘固定销子关牢,放线盘张力制动装置应灵活好用,损坏的要及时修理,否则不能开机'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、禁止用手转动绕包头和用手帮助停车'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、停车时应将电位器旋至零位,设备进行修理或操作者离开机台时应将电源开关拉下'
								}]
					}];
		} else if (processCode == 'Cabling' || processCode == 'Cabling,wrapping' || processCode == 'Twisting,Cabling') {
			items = [{
						xtype : 'fieldset',
						title : '成缆机作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、开机前应检查各传动部位是否灵活，安全装置是否灵敏可靠及润滑点是否润滑良好'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、使用电动葫芦吊运线盘时应遵守电动葫芦安全操作规程，检查电动葫芦排线器及电动葫芦是否好用,钢丝绳是否磨损，有问题应及时修理与更换'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、往绞笼上上线盘时，要考虑平衡，事先将绞笼制动，防止绞笼自动滚动'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、开车要按警铃，确认安全的情况下再逐渐加速至正常运转，运转中注意设备是否正常,若发生异常应立即检查并排除，注意各部位轴承轴瓦温度是否正常'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、设备运转中禁止在压模架入口处及牵引轮前调整及挪动电缆或线芯，加填料时禁止戴手套'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、设备上所有保护罩及其零件不得随意拆卸，严禁在开车中擦拭设备，设备上禁止放工具和物品,以免卷入机器中造成事故'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '7、经常检查托轮与滚边的接触情况，并及时润滑退环与偏心轮接触要均匀良好'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '8、上下线盘时应俩人协调配合好并注意通道上不得有人及杂物'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '9、设备上各电器限位一定要完整无缺灵敏可靠，不得任意拆卸'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '10、工作结束后各手柄应恢复到原位并断开电源'
								}]
					}];
		} else if (processCode == 'Twisting') {
			items = [{
						xtype : 'fieldset',
						title : '单绞机及弓绞机作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、开机前应仔细检查各传动部位是否灵活好用,安全装置是否灵敏可靠及各润滑点是否润滑良好'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、开机前应检查各限位器是否安全可靠'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、设备上所有保护罩及其零件不得随意拆卸，严禁在开机过程中擦拭设备，设备上不得放置工具和物品,以免卷入机器中造成事故'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、上下盘时应检查好盘具顶针、模具、导轮是否松动安全可靠，如发现问题应及时报修，不得带病开机'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、停机后应关闭电源和气源'
								}]
					}];
		} else if (processCode == 'hyperthermia-Extrusion') {
			items = [{
						xtype : 'fieldset',
						title : '高温挤塑机作业步骤',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '1、按规定对设备加油润滑,检查挤塑机电气元件及安全防护装置是否完好无损和灵敏可靠,热电偶指示温度是否正常,出现问题应通知维修人员,不得私自乱动'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '2、开机时应检查加热器防护，主机减速箱、牵引箱、收线箱是否正常,风机工作是否良好'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '3、开车前要检查各段的加热温度是否达到工艺要求的实际温度,加温时间要足够'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '4、不得在塑料塑化不良的情况下进行挤出生产,以防事故发生,发现问题应立即停机并设法排除'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '5、车速应由低向高渐渐升速,严禁高速起动,并观察仪表上指示的电流值与温度是否正常,以防电流过大引起设备损坏'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '6、升降线盘时要注意升降极限位置不得过高或过低'
								}, {
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '7、工作中经常注意主机牵引机及收线各机组运转声响'
								}]
					}];
		} 
		else {
			items = [{
						xtype : 'fieldset',
						title : '提醒',
						padding : '10',
						items : [{
									xtype : 'displayfield',
									fieldStyle : {
										color : 'red'
									},
									value : '该工序未添加点检，请联系班组长让电子工厂添加'
								}]
					}];
		}

		me.items = items;
		me.callParent(arguments); // ------------call父类--------------

	}
});
