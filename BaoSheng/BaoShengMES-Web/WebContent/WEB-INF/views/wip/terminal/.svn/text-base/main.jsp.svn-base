<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />

<script type="text/javascript" src="${sctx}/jQuery/jquery.PrintArea.js"></script>
<script type="text/javascript" src="${sctx}/Lodop/LodopFuncs.js"></script>
<script src="${sctx}/jQuery/ion.checkRadio.min.js"></script>

<link href="${sctx}/touch.css" type="text/css" rel="stylesheet"/>

<style type="text/css">
.x-finish-port {
		font: normal 30px/30px helvetica, arial, verdana, sans-serif;
	}
</style> 

<widget xtype="terminalMainView" controller="TerminalMainController"> </widget>

<div id="single" style="display: none;" single="${single}">
    <div id="equipInfo" equipName="${equip.name}" status="${equip.status}" code="${equip.code}" 
    		isFeedCompleted="${isFeedCompleted}" equipAlias="${equip.equipAlias}" equipId="${equip.id}" processCode="${equip.processCode}">
        <img id="titleImg" src="${sctx}/icons/${equip.status}.png"/>
        <span id="titleHtml" style="font-size: 23px;position:absolute;top:3px; width: ${nameSize}px;left:35px;">${equip.equipAlias}${equip.statusText}</span>
    </div>
    <div id="orderInfo" lastWorkOrderStatus="${lastWorkOrderStatus}" status="${order.status}" num="${order.workOrderNo}" 
    		userComment="${order.userComment}" isDispatch="${order.isDispatch}" processName="${order.processName}">
    </div>
    <div id="interval" value="${middleCheckInterval}"></div>
    <div id="processInfo" section = "${processInfo.section}" code = "${processInfo.code}"></div>
    <div id="currentOrderTask" orderTaskId="${currentOrderTaskId}" color="${currentColor}"></div>
    <div id="orderDetail" num="${detailOrder.workOrderNo}" status="${detailOrder.timeByStatus}"></div>
</div>

<script type="text/javascript">
    /***********************
     * 函数：模拟滚轮
     *************************/
    var y = 0;
    var isMouseDown = false;

    var grid; //$('div[id="contentPar"]');
    function scrollFunc(e){
        if(isMouseDown){
            grid = $(this).closest('div[id^="gridview-"]');
            var top = grid.scrollTop();

            e=e || window.event;

            var len = e.clientY - y;
            y = e.clientY;
            top = top - len;
            grid.scrollTop(top);
        }
    }

    function mDown(e){
        e=e || window.event;
        y=e.clientY;
        isMouseDown = true;
    }
    function mUp(e){
        isMouseDown = false;
    }
    function mOut(e){
        y = 0;
        isMouseDown = false;
    }

    /*注册事件*/
    if (document.addEventListener) { //FireFox,Chrome,Opera…
        document.addEventListener('mousedown',mDown,false);
        document.addEventListener('mouseup',mUp,false);
    }else if(document.attachEvent) { //IE
        document.addEventListener('onmousedown',mDown,false);
        document.addEventListener('onmouseup',mUp,false);
    }//W3C
    $('body').on('mousemove', 'table[id^="gridview-"][id$="-table"]', scrollFunc);
    $('body').on('mouseout', 'table[id^="gridview-"][id$="-table"]', mOut);



</script>