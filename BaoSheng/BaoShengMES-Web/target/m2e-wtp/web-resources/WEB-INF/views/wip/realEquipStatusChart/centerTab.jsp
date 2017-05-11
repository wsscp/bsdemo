<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="sctx" value="/bsstatic" />
<link href="${sctx}/lib/gantt/css/sch-gantt-all.css" rel="stylesheet" type="text/css" />
<link href="${sctx}/lib/gantt/css/orderTask.css" rel="stylesheet" type="text/css" /> 
<script src="${sctx}/lib/gantt/js/sch-all-debug.js" type="text/javascript"></script> 
<widget title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>" xtype="realEquipStatusChartList" controller="RealEquipStatusChartController"> </widget>
<div id="single" style="display: none;">
	<div id="equipInfo" equipName="${equip.name}" status="${equip.status}"
		code="${equip.code}" equipAlias="${equip.equipAlias}"
		equipId="${equip.id}">
		<img id="titleImg" src="${sctx}/icons/${equip.status}.png" /> <span
			id="titleHtml"
			style="font-size: 28px; position: absolute; top: 3px; width: 700px; left: 50px;">${equip.name}${equip.statusText}</span>
	</div>
	<div id="orderInfo" status="${order.status}" num="${order.workOrderNo}" contractNo="${contractNo}">
    </div>
    <!--  
    <div id="emphShowInfo" data='${emphShowInfo}'></div>
     -->
</div>