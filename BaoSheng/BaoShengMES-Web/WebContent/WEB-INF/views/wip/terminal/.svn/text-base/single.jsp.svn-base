<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<script type="text/javascript" src="${sctx}/jQuery/jquery.PrintArea.js"></script>
<script type="text/javascript" src="${sctx}/Lodop/LodopFuncs.js"></script>
<widget xtype="terminalSingleView" controller="TerminalSingleController"> </widget>
<link href="${sctx}/touch.css" type="text/css" rel="stylesheet"/>

<script src="${sctx}/jQuery/ion.checkRadio.min.js"></script> 
  
  
<div id="single" style="display: none;" single="${single}" isSkippable="${isSkippable}" isProIntercept="${isProIntercept}">
	<div id="equipInfo" equipName="${equip.name}" status="${equip.status}" code="${equip.code}" isFeedCompleted="${isFeedCompleted}"
         equipId="${equip.id}">
		<img id="titleImg" src="${sctx}/icons/${equip.status}.png"/>
		<span id="titleHtml" style="font-size: 28px;position:absolute;top:3px; width: 700px;left:50px;">${equip.name}${equip.statusText}</span>
	</div>
	<div id="orderInfo" status="${order.status}" num="${order.workOrderNo}">
	</div>
	<!-- 
	<div id="emphShowInfo" data='${emphShowInfo}'></div>
	 -->
    <div id="interval" value="${middleCheckInterval}"></div>
	<div id="orderDetail" num="${detailOrder.workOrderNo}"
		status="${detailOrder.timeByStatus}">
	</div>
	<div id="materialInfo">
		<table style="width: 100%">
			<tr>
				<td>编号</td>
				<td>名称</td>
				<td>位置</td>
				<td>描述(图片)</td>
				<td>批次号</td>
			</tr>
		</table>
	</div>
	<div id="operParams">
		<table style="width: 100%">
			<tr>
				<td>名称</td>
				<td>要求值</td>
				<td>设定值</td>
				<td>当前值</td>
			</tr>
		</table>
	</div>
	<div id="qcParams">
		<table style="width: 100%">
			<tr>
				<td>名称</td>
				<td>要求值</td>
				<td>机测值</td>
				<td>人测值</td>
			</tr>
		</table>
	</div>
    <div id="workUsers">
        <c:forEach items="${workUsers}" var="user">
            <c:out value="${user.userName}"></c:out>&nbsp;
        </c:forEach>
    </div>
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
window.getSelection?window.getSelection().removeAllRanges():document.selection.empty();


</script>