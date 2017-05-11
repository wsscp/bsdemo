<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<script type="text/javascript" src="${sctx}/jQuery/jquery.PrintArea.js"></script>
<script type="text/javascript" src="${sctx}/Lodop/LodopFuncs.js"></script>
<script src="${sctx}/jQuery/ion.checkRadio.min.js"></script> 
<style type="text/css">
    .x-panel-header-default .x-panel-header-icon {
        background-position: center center;
        height: 36px;
        width: 36px;
    }
    .x-panel-header-text-container-default {
        color: white;
        font-family: arial,helvetica,verdana,sans-serif;
        font-size: 16px;
        font-weight: bold;
        line-height: 15px;
        padding: 1px 0 0;
        text-transform: none;
    }
    .x-form-item-label {
        color: black;
        font: 16px/22px helvetica,arial,verdana,sans-serif;
        margin-top: 4px;
    }
    .x-grid-row{
        height:50px;
    }
    .x-finish-port {
		font: normal 30px/30px helvetica, arial, verdana, sans-serif;
	}
</style>
<widget title="${gridTitle}" xtype="terminalMultipleView" controller="TerminalMultipleController"> </widget>
<link href="${sctx}/touch.css" type="text/css" rel="stylesheet"/>
<div id="multiple" class="x-hide-display">
	<input id="clientMac" type="hidden" value="${mac}">
	<input id="workUsers" type="hidden" value="${workUsers}">
	<input id="maxClient" type="hidden" value="${maxClient}">
	<input id="equipStatusArray" type="hidden" value='${equipStatusArray}'>
	<div id="processInfo" section = "${processInfo.section}" code = "${processInfo.code}"></div>
    <div id="interval" value="${middleCheckInterval}"></div>
 	<c:forEach items="${eqips}" var="eqip" varStatus="s">
		<div 	id="equip${s.index}"
				status="${eqip.status}"
			 	equipCode="${eqip.equipCode}" 
			 	eqipId="${eqip.eqipId}" 
			 	equipName="${eqip.equipName }"
                equipAlias="${eqip.equipAlias}"
			 	planLength="${eqip.planLength}"
			 	toDoTaskNum="${eqip.toDoTaskNum}"
			 	remainQLength="${eqip.remainQLength}"
                section="${eqip.section}"
			 	workOrderNo="${eqip.workOrderNo}">
		</div>
	</c:forEach>
</div>
