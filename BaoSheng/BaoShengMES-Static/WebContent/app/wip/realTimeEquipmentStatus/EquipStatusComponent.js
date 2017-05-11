var cameraSensorWindow = null;
(function($) {
	var EquipStatusComponent = function(element, options) {
		this.init(element, options);
	}, old = null;

	EquipStatusComponent.prototype = {

		equipMapCache : {}, // 存放设备缓存
		total : 0, // 设备数量
		statusCount : {}, // 状态数量
		intervalTime : 0, // 自动刷新时间

		init : function(element, options) {
			var me = this;
			// 1、初始化参数
			me.setOptions(element, options);

			// 2、后台请求数据
			me.getEquipInfoStatusData(me.options.status, false);

			// 3、渲染整个页面
			me.drawComponentTitle(); // 渲染头部
			me.drawComponentContent(); // 渲染主题

			// 4、添加事件
			me.bindEvent();

			// 5、开始自动刷新事件
			me.startAutoRefresh();

		},

		/**
		 * 初始化参数
		 */
		setOptions : function(element, options) {
			this.options = $.extend({}, (this.options || $.fn.equipStatusComponent.defaults), options);

			this.$element = $(element);
			$(element).attr({
						id : this.options.id
					});
		},

		/**
		 * 渲染面板：渲染头部，checkbox和查询按钮
		 * 
		 * @param rowsArray 面板items内容(一个数组对象)
		 */
		drawComponentTitle : function(rowsArray) {
			var me = this;
			var checkBoxArray = [];
			var statusArray = me.options.status.split(',');
			var statusStrArray = me.options.statusStr.split(',');

			for (var i = 0; i < statusArray.length; i++) {
				var count = (me.statusCount[statusArray[i]] ? me.statusCount[statusArray[i]] : 0);
				checkBoxArray.push('<label style="margin-right: 15px;"><input type="checkbox" name="equipStatus" count="' + count
						+ '" value="' + statusArray[i] + '" checked><img style="height:15px;" src="/bsstatic/icons/' + statusArray[i]
						+ '-min.png"/>&nbsp;<span style="display:block;float:right;margin-top:1px;">' + statusStrArray[i] + '(' + count
						+ '/' + me.total + ')</span></label>');
			}
			checkBoxArray
					.push('<button class="btn btn-primary search" style="margin : 0 20px 0 0;padding: 0 5px;"><i style="margin-right: 10px;" class="fa fa-search"></i>查询</button>');
			checkBoxArray
					.push('<button class="btn btn-primary reset" style="padding: 0 5px;"><i style="margin-right: 10px;" class="fa fa-cog"></i>重置</button>');
			me.$element.append('<div class="checkbox" style="padding: 5 0 5px 13px;border-bottom: 1px solid silver;margin: 0;">设备状态：'
					+ checkBoxArray.join('') + '</div>');
		},

		/**
		 * 渲染面板: 主题部分
		 * 
		 * @param options 参数
		 */
		drawComponentContent : function() {
			var me = this;
			// 1、定义固定数据：{rowsArray：整个面板的容器}
			var rowsArray = [];

			// 2、创建每行、每列、每个item：item组放入列，列组放入行，行组最终放入me
			for (var i = this.options.rowNumber; i > 0; i--) {
				var columnArray = [];
				for (var j = 1; j <= this.options.columnNumber; j++) {
					var getKey = (i < 10 ? ('0' + i) : i) + '-' + (j < 10 ? ('0' + j) : j);
					var equipArray = me.equipMapCache[getKey];
					var itemArray = [];
					if (equipArray) {
						for (var e = 0; e < equipArray.length; e++) {
							var itemPanel = me.createItemPanel(equipArray[e]);
							itemArray.push(itemPanel);
						}
					}
					var columnFieldSet = me.createColumnFieldSet(j, itemArray);
					columnArray.push(columnFieldSet);
				}

				var rowsFieldSet = me.createRowsFieldSet(i, columnArray);
				rowsArray.push(rowsFieldSet);
			}

			var height = document.body.scrollHeight - 45;
			me.$element.append('<div class="equipStatusContainer" style="height: ' + height + 'px;overflow: auto;padding: 0 0 10px 10px;">'
					+ rowsArray.join('') + '</div>');
		},

		/**
		 * 创建行组件
		 * 
		 * @param i 第几行
		 * @param columnArray 行数组对象
		 */
		createRowsFieldSet : function(i, columnArray) {
			var rwidth = document.body.scrollWidth - 40; // 一行的宽度
			return '<fieldset class="rowsFieldSet" style="width: ' + rwidth
					+ 'px;padding: 0 0 5px 5px;border-width:5px !important;margin-bottom: ' + (i == 1 ? '10px' : '0')
					+ ' !important;"><legend>' + i + '</legend>' + columnArray.join('') + '</fieldset>';
		},

		/**
		 * 创建列组件
		 * 
		 * @param j 第几列
		 * @param itemArray 列数组对象
		 */
		createColumnFieldSet : function(j, itemArray) {
			var cwidth = Math.floor((document.body.scrollWidth - 40 - 110) / 9); // 每一列的宽度
			return '<fieldset class="columnFieldSet" style="width: ' + cwidth + 'px;padding: 0 5px 0 5px;margin: '
					+ (j == 1 ? '0' : '0 0 0 10px') + ' !important;float: left;"><legend>' + j + '</legend>' + itemArray.join('')
					+ '</fieldset>';
		},

		/**
		 * 创建item组件
		 * 
		 * @param equip 设备对象
		 */
		createItemPanel : function(equip) {
			var me = this;
			var status = equip.status;
			var color='';
			switch(status){
			case 'IN_PROGRESS':color = 'rgb(50,177,104)'; break;
			case 'IN_DEBUG' : color = 'rgb(0,119,172)'; break;
			case 'IDLE' : color = 'rgb(202,214,56)'; break;
			case 'CLOSED' : color = '#ADADAD'; break;
			case 'ERROR' : color = 'rgb(252,0,0)'; break;
			case 'IN_MAINTAIN' : color = 'rgb(253,128,6)'; break;
			}
			var iwidth = Math.floor((document.body.scrollWidth - 40 - 110) / 9) - 20; // 每一列的宽度
			return '<button class="btn btn-default" name="equipStatusBox" id="' + equip.section + '" ecode="' + equip.code + '" ename="'
					+ equip.name + '" status="' + status + '" style="margin: 0 0 5px 0;width: ' + iwidth + 'px;text-align:center;background:'+color+';">'
					+ '<font style="color : #000;font-weight: normal;">'
					+ equip.code + '<br/>' + equip.equipAlias + '</font></button>';
		},

		/**
		 * 请求后台获取设备状态信息
		 * 
		 * @param status 查询状态
		 * @param isfresh 是否刷新
		 */
		getEquipInfoStatusData : function(status, isfresh) {
			var me = this;
			// 同步请求获取设备状态信息
			$.ajax({
						url : 'realTimeEquipmentStatus/realSearch',
						type : 'GET',
						async : false,
						data : {
							status : status
						},
						success : function(result) {
							if (result) {
								if (isfresh) {
									me.freshEquipInfo(result)
								} else {
									me.initEquipInfoCache(result)
								}
							}
						}
					});

		},

		/**
		 * 组建初始化：处理数据查询结果
		 * 
		 * @param result 查询结果
		 */
		initEquipInfoCache : function(result) {
			var me = this;
			me.statusCount = {};
			me.equipMapCache = {};
			var equipInfos = result.equipInfos;
			for (i in equipInfos) {
				var section = equipInfos[i].section;
				if (section) {
					var mapKey = section.substring(0, section.lastIndexOf("-"));
					if (me.equipMapCache[mapKey]) {
						me.equipMapCache[mapKey].push(equipInfos[i]);
					} else {
						me.equipMapCache[mapKey] = [equipInfos[i]];
					}

					if (me.statusCount[equipInfos[i].status] >= 0) {
						me.statusCount[equipInfos[i].status] = me.statusCount[equipInfos[i].status] + 1;
					} else {
						me.statusCount[equipInfos[i].status] = 0;
					}
				}
			}
			me.total = result.totals;
		},

		/**
		 * 刷新数据：处理数据查询结果
		 * 
		 * @param result 查询结果
		 */
		freshEquipInfo : function(result) {
			var me = this;
			me.statusCount = {};
			// 1、循环设备信息，更新对应的设备状态
			$(result.equipInfos).each(function(i, equip) {
						var item = $('#' + equip.section, me.$element);
						if (item) { // 设备存在，更新
							if (item.attr('status') != equip.status) { // 设备状态不同，更新
								item.attr('status', equip.status);
								var color='';
								switch(equip.status){
								case 'IN_PROGRESS':color = 'rgb(50,177,104)'; break;
								case 'IN_DEBUG' : color = 'rgb(0,119,172)'; break;
								case 'IDLE' : color = 'rgb(202,214,56)'; break;
								case 'CLOSED' : color = '#ADADAD'; break;
								case 'ERROR' : color = 'rgb(252,0,0)'; break;
								case 'IN_MAINTAIN' : color = 'rgb(253,128,6)'; break;
								}
								item.css('background',color);
							}
						} else { // 设备不存在，更新到对应的区域上

						}

						// 2、更新不同状态的设备数量
						if (me.statusCount[equip.status] >= 0) {
							me.statusCount[equip.status] += 1;
						} else {
							me.statusCount[equip.status] = 0;
						}
					});

			// 3、将设备数量值更新到显示checkbox
			$('.checkbox input[name="equipStatus"]', me.$element).each(function(i, checkbox) {
						var count = me.statusCount[$(checkbox).val()];
						if (count && $(checkbox).attr('count') != count) { // 数量不一样变更checkbox后面文字
							$(checkbox).attr('count', count); // 变更属性count，以供下次比较
							var boxLabel = $(checkbox).siblings('span').text(); // 变更文字
							$(checkbox).siblings('span').text(boxLabel.replace(/\(\d+\/\d+\)/g, '(' + count + '/' + me.total + ')'));
						}
					});

			// 4、判断是否显示或者隐藏 
			var status = me.getCheckBoxStatus();
			$('button[name="equipStatusBox"]', me.$element).each(function(i, equipStatusBox) {
						if (me.arrayContains(status, $(equipStatusBox).attr('status'))) { // 状态在勾选里面，显示
							if (!$(equipStatusBox).is(":visible")) {
								$(equipStatusBox).show();
							}
						} else { // 状态不在勾选里面，影藏
							if ($(equipStatusBox).is(":visible")) {
								$(equipStatusBox).hide();
							}
						}
					});
		},

		bindEvent : function() {
			var me = this;
			// 1、绑定查询按钮事件
			$('.checkbox button.search', me.$element).on('click', function() {
						me.stopAutoRefresh();
						me.autoRefresh();
						me.startAutoRefresh();
					});

			// 2、绑定重置按钮事件
			$('.checkbox button.reset', me.$element).on('click', function() {
						$('.checkbox input[name="equipStatus"]', me.$element).each(function(i, checkbox) {
									$(checkbox).prop('checked', true);
								});
					});

			// 3、绑定设备点击事件
			$('button[name="equipStatusBox"]', me.$element).on('click', function() {
				parent.openTab('设备状态监控历史', 'wip/realEquipStatusChart.action?code=' + $(this).attr('ecode') + '&name='
								+ $(this).attr('ename'));
				if (cameraSensorWindow && cameraSensorWindow.location) {
					cameraSensorWindow.location.href = 'cameraSensor.action?equipCode=' + $(this).attr('ecode');
				} else {
					cameraSensorWindow = window.open('cameraSensor.action?equipCode=' + $(this).attr('ecode'), '_blank', 'fullscreen=yes,location=0,directories=no,scrollbars=yes');
				}
			});

			$(window).resize(function() {
						var height = document.body.clientHeight - 55;
						var width = document.body.scrollWidth;
						setTimeout(function() {
									var cheight = document.body.clientHeight - 55;
									var cwidth = document.body.scrollWidth;

									if (height == cheight && width == cwidth) {
										var iwidth = Math.floor((cwidth - 40 - 110) / 9) - 20; // 每一列的宽度
										var rwidth = cwidth - 40; // 一行的宽度
										var cwidth = Math.floor((cwidth - 40 - 110) / 9); // 每一列的宽度

										$('button[name="equipStatusBox"]', me.$element).css('width', iwidth);
										$('fieldset.columnFieldSet', me.$element).css('width', cwidth);
										$('fieldset.rowsFieldSet', me.$element).css('width', rwidth);
										$('div.equipStatusContainer', me.$element).css('height', cheight);
									}

								}, 250);
					});
		},

		startAutoRefresh : function() {
			var me = this;
			me.intervalTime = setInterval(function() {
						me.autoRefresh.apply(me);
					}, 30000);

		},

		stopAutoRefresh : function() {
			var me = this;
			clearInterval(me.intervalTime);
		},

		/**
		 * 自动刷新：刷新设备状态信息
		 */
		autoRefresh : function() {
			var me = this;
			var status = me.getCheckBoxStatus().join(',');
			// 1、刷新数据：更新状态
			me.getEquipInfoStatusData(status, true);
		},

		/**
		 * 获取status状态字符串
		 */
		getCheckBoxStatus : function() {
			var me = this;
			var status = [];
			$('.checkbox input[name="equipStatus"]:checked', me.$element).each(function(i, checkbox) {
						status.push($(checkbox).val());
					});
			return status;
		},

		/**
		 * 判断数组中包含element元素
		 * 
		 * @param array 源数组
		 * @param element 可为数组或者单个
		 */
		arrayContains : function(array, element) {
			if (Object.prototype.toString.call(element) === '[object Array]') {
				for (var j = 0; j < element.length; j++) {
					var has = false;
					for (var i = 0; i < array.length; i++) {
						if (array[i] == element[j]) {
							has = true;
							break;
						}
					}
					if (!has) {
						return false;
					}
				}
				return true;
			} else {
				for (var i = 0; i < array.length; i++) {
					if (array[i] == element) {
						return true;
					}
				}
				return false;
			}
		}

	};

	/*
	 * TYPEAHEAD PLUGIN DEFINITION ===========================
	 */
	old = $.fn.equipStatusComponent;
	$.fn.equipStatusComponent = function(options) {
		new EquipStatusComponent(this, options)
	};
	$.fn.equipStatusComponent.defaults = {
		id : new Date().getTime(),
		rowNumber : 5, // {rows\columns:默认设备现场布局为5行\9列}
		columnNumber : 9, // {rows\columns:默认设备现场布局为5行\9列}
		status : 'IN_PROGRESS,CLOSED,IDLE,IN_DEBUG,IN_MAINTAIN,ERROR', // {status: 默认查询状态}
		statusStr : '加工,关机,待机,调试,保养,故障' // {statusStr: 默认查询状态}
	};
	$.fn.equipStatusComponent.Constructor = EquipStatusComponent;
}(window.jQuery));

$('.container').equipStatusComponent();