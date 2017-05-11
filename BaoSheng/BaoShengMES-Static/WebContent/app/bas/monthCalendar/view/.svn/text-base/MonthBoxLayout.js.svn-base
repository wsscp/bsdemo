Ext.define('bsmes.view.MonthBoxLayout', {
    extend: 'Ext.XTemplate',
    
    requires: ['Ext.calendar.util.Date'],
    
    constructor: function(config){
        
        Ext.apply(this, config);
    
        var weekLinkTpl = this.showWeekLinks ? '<div id="{weekLinkId}" class="ext-cal-week-link">{weekNum}</div>' : '';
        
        this.callParent([
            '<tpl for="weeks">',
                '<div id="{[this.id]}-wk-{[xindex-1]}" class="ext-cal-wk-ct" style="top:{[this.getRowTop(xindex, xcount)]}%; height:{[this.getRowHeight(xcount)]}%; min-height:105px; ">',
                    weekLinkTpl,
                    '<table class="ext-cal-bg-tbl" cellpadding="0" cellspacing="0">',
                        '<tbody>',
                            '<tr>',
                                '<tpl for=".">',
                                     '<td id="{[this.id]}-day-{date:date("Ymd")}" class="{cellCls}">&#160;</td>',
                                '</tpl>',
                            '</tr>',
                        '</tbody>',
                    '</table>',
                    '<table class="ext-cal-evt-tbl" cellpadding="0" cellspacing="0">',
                        '<tbody>',
                            '<tr>',
                                '<tpl for=".">',
                                    '<td id="{[this.id]}-ev-day-{date:date("Ymd")}" class="{titleCls}"><div style="padding-top:5px;">{title}</div></td>',
                                '</tpl>',
                            '</tr>',
                        '</tbody>',
                    '</table>',
                '</div>',
            '</tpl>', {
                getRowTop: function(i, ln){
                    return ((i-1)*(100/ln));
                },
                getRowHeight: function(ln){
                    return 100/ln;
                }
            }
        ]);
    },

    applyTemplate : function(o){
        Ext.apply(this, o);
        
        var CalendarData = new Array(100);
        var madd = new Array(12);
        var numString = "一二三四五六七八九十";
        var monString = "正二三四五六七八九十冬腊";
        var cYear, cMonth, cDay;
        CalendarData = new Array(
        0xA4B, 0x5164B, 0x6A5, 0x6D4, 0x415B5, 0x2B6, 0x957, 0x2092F, 0x497, 0x60C96,
        0xD4A, 0xEA5, 0x50DA9, 0x5AD, 0x2B6, 0x3126E, 0x92E, 0x7192D, 0xC95, 0xD4A,
        0x61B4A, 0xB55, 0x56A, 0x4155B, 0x25D, 0x92D, 0x2192B, 0xA95, 0x71695, 0x6CA,
        0xB55, 0x50AB5, 0x4DA, 0xA5B, 0x30A57, 0x52B, 0x8152A, 0xE95, 0x6AA, 0x615AA,
        0xAB5, 0x4B6, 0x414AE, 0xA57, 0x526, 0x31D26, 0xD95, 0x70B55, 0x56A, 0x96D,
        0x5095D, 0x4AD, 0xA4D, 0x41A4D, 0xD25, 0x81AA5, 0xB54, 0xB6A, 0x612DA, 0x95B,
        0x49B, 0x41497, 0xA4B, 0xA164B, 0x6A5, 0x6D4, 0x615B4, 0xAB6, 0x957, 0x5092F,
        0x497, 0x64B, 0x30D4A, 0xEA5, 0x80D65, 0x5AC, 0xAB6, 0x5126D, 0x92E, 0xC96,
        0x41A95, 0xD4A, 0xDA5, 0x20B55, 0x56A, 0x7155B, 0x25D, 0x92D, 0x5192B, 0xA95,
        0xB4A, 0x416AA, 0xAD5, 0x90AB5, 0x4BA, 0xA5B, 0x60A57, 0x52B, 0xA93, 0x40E95);
        madd[0] = 0; madd[1] = 31; madd[2] = 59; madd[3] = 90;
        madd[4] = 120; madd[5] = 151; madd[6] = 181; madd[7] = 212;
        madd[8] = 243; madd[9] = 273; madd[10] = 304; madd[11] = 334;
        var me = this;
        var w = 0, title = '', first = true, isToday = false, showMonth = false, prevMonth = false, nextMonth = false,
            weeks = [[]],workDay = false,
            dt = Ext.Date.clone(this.viewStart),
            wd = false,
            thisMonth = this.startDate.getMonth();
        
        var weekcount = this.weekCount;
        var viewend = this.viewEnd;
        var year = this.startDate.getYear()+1900;
        var month = thisMonth+1;
        if(month.toString().length==1){
        	month = "0" + month;
        }
        var workday = year + "" +month;
        var id = me.id.substr(0,18);
        document.getElementById(id+'-tb-month').className = "ext-tb-month-xchen";
        document.getElementById(id+'-tb-month-btnInnerEl').className = "ext_cl_title";
        document.getElementById(id+'-tb-month-btnWrap').className = "ext_cl_wrap";
        
        Ext.Ajax.request({
        	method:'GET',
        	async: false,
            url: 'monthCalendar/queryWorkDay',
            params: {
            	workday: workday
            },
            success: function(response){
                var text = response.responseText;
            	var json = eval("(" + text + ")");
            	if(json.total==0){
            		Ext.Msg.alert('错误信息', '该月的工作日历不存在,请核实!');
            	}
            	var array = [];
            	array.push(json.rows[0].day1);
            	array.push(json.rows[0].day2);
            	array.push(json.rows[0].day3);
            	array.push(json.rows[0].day4);
            	array.push(json.rows[0].day5);
            	array.push(json.rows[0].day6);
            	array.push(json.rows[0].day7);
            	array.push(json.rows[0].day8);
            	array.push(json.rows[0].day9);
            	array.push(json.rows[0].day10);
            	array.push(json.rows[0].day11);
            	array.push(json.rows[0].day12);
            	array.push(json.rows[0].day13);
            	array.push(json.rows[0].day14);
            	array.push(json.rows[0].day15);
            	array.push(json.rows[0].day16);
            	array.push(json.rows[0].day17);
            	array.push(json.rows[0].day18);
            	array.push(json.rows[0].day19);
            	array.push(json.rows[0].day20);
            	array.push(json.rows[0].day21);
            	array.push(json.rows[0].day22);
            	array.push(json.rows[0].day23);
            	array.push(json.rows[0].day24);
            	array.push(json.rows[0].day25);
            	array.push(json.rows[0].day26);
            	array.push(json.rows[0].day27);
            	array.push(json.rows[0].day28);
            	array.push(json.rows[0].day29);
            	array.push(json.rows[0].day30);
            	array.push(json.rows[0].day31);
            	
            	var i = 0;
            	for(; w < weekcount || weekcount == -1; w++){
                    if(dt > viewend){
                        break;
                    }
                    weeks[w] = [];
                    for(var d = 0; d < me.dayCount; d++){
                    	if(dt.getDate()==1){
                    		i = 0;
                    	}
                    	
                    	var total, m, n, k;
                        var isEnd = false;
                        var temp = dt.getFullYear();
                        total = (temp - 1921) * 365 + Math.floor((temp - 1921) / 4) + madd[dt.getMonth()] + dt.getDate() - 38; 
                        if (dt.getYear() % 4 == 0 && dt.getMonth() > 1) { total++; } 
                    	for (m = 0; ; m++) {
                    		k = (CalendarData[m] < 0xfff) ? 11 : 12;
                    		for (n = k; n >= 0; n--) {
                    			if (total <= 29 +  ((CalendarData[m] >> n) & 1)) {
                    				isEnd = true; break; 
                    			} 
                    			total = total - 29 - ((CalendarData[m] >> n) & 1); 
                    		} 
                    		if (isEnd) break; 
                    	} 
                    	cYear = 1921 + m; 
                    	cMonth = k - n + 1; 
                    	cDay = total; 
                    	if (k == 12) {
                    		if (cMonth == Math.floor(CalendarData[m] / 0x10000) + 1) {
                    			cMonth = 1 - cMonth; 
                    		} 
                    		if (cMonth > Math.floor(CalendarData[m] / 0x10000) + 1) {
                    			cMonth--; 
                    		} 
                    	}
                        
                        var tmp = '';
                        if (cMonth < 1) {
                    		tmp += "(闰)"; 
                    		tmp += monString.charAt(-cMonth - 1); 
                    	} else { 
                    		tmp += monString.charAt(cMonth - 1); 
                    	} 
                    	tmp += "月"; 
                    	tmp += (cDay < 11) ? "初" : ((cDay < 20) ? "十" : ((cDay < 30) ? "廿" : "三十"));
                        if (cDay % 10 != 0 || cDay == 10) {
                    		tmp += numString.charAt((cDay - 1) % 10); 
                    	}
                    	
                    	wd = array[i] === "1";
                        isToday = dt.getTime() === Ext.calendar.util.Date.today().getTime();
                        showMonth = first || (dt.getDate() == 1);
                        prevMonth = (dt.getMonth() < thisMonth) && me.weekCount == -1;
                        nextMonth = (dt.getMonth() > thisMonth) && me.weekCount == -1;
                        if(dt){
                            // The ISO week format 'W' is relative to a Monday week start. If we
                            // make this check on Sunday the week number will be off.
                            weeks[w].weekNum = me.showWeekNumbers ? Ext.Date.format(dt, 'W') : '&#160;';
                            weeks[w].weekLinkId = 'ext-cal-week-'+Ext.Date.format(dt, 'Ymd');
                        }
                        if(showMonth){
                            if(isToday){
                                title = me.getTodayText();
                            }
                            else{
                                title = Ext.Date.format(dt, me.dayCount == 1 ? 'l, F j, Y' : (first ? 'M j, Y' : 'M j'));
                            }
                        }
                        else{
                            var dayFmt = (w == 0 && me.showHeader !== true) ? 'D j' : 'j';
                            title = isToday ? me.getTodayText() : Ext.Date.format(dt, dayFmt);
                        }
                        weeks[w].push({
                            title: title+""+"<span style='display: block;margin-top: 4px;font-size:14px;'>"+tmp+"</span>",
                            date: Ext.Date.clone(dt),
                            titleCls: 'ext-cal-dtitle ' + 
                                (w==0 ? ' ext-cal-dtitle-first' : '') +
                                (prevMonth ? ' ext-cal-dtitle-prev' : '') + 
                                (nextMonth ? ' ext-cal-dtitle-next' : ''),
                            cellCls: 'ext-cal-day' + (wd ? ' ext-cal-day-today' : '') + 
                                (prevMonth ? ' ext-cal-day-prev' : '') +
                                (nextMonth ? ' ext-cal-day-next' : '')
                        });
                        dt = Ext.calendar.util.Date.add(dt, {days: 1});
                        first = false;
                        i++;
                    }
                }
            }
        });
        
        return me.applyOut({
            weeks: weeks
        }, []).join('');
        
    },
  
    getTodayText : function(){
        var dt = Ext.Date.format(new Date(), 'l, F j, Y'),
            fmt,
            todayText = this.showTodayText !== false ? this.todayText : '',
            timeText = this.showTime !== false ? ' <span id="'+this.id+'-clock" class="ext-cal-dtitle-time">' + 
                    Ext.Date.format(new Date(), 'g:i a') + '</span>' : '',
            separator = todayText.length > 0 || timeText.length > 0 ? ' &mdash; ' : '';
        if(this.dayCount == 1){
            return dt + separator + todayText + timeText;
        }
        fmt = this.weekCount == 1 ? 'D j' : 'j';
        return todayText.length > 0 ? todayText + timeText : Ext.Date.format(new Date(), fmt) + timeText;
    }
    
}, 
function() {
    this.createAlias('apply', 'applyTemplate');
});