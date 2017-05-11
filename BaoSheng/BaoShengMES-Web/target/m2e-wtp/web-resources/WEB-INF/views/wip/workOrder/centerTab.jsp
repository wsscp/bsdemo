<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="sctx" value="/bsstatic" />
<link href="${sctx}/lib/gantt/css/sch-gantt-all.css" rel="stylesheet"
	type="text/css" />
<link href="${sctx}/lib/gantt/css/tree.css" rel="stylesheet"
	type="text/css" />
<script src="${sctx}/lib/gantt/js/sch-all-debug.js"
	type="text/javascript"></script>
<head>
<style type="text/css">
.x-grid-record-yellow {
	background: yellow;
}

.x-grid-record-orange {
	color: orange
}
</style>
</head>
<widget
	title="<fmt:message key='${moduleName}.${submoduleName}.title'/>"
	xtype="workOrderList" controller="WorkOrderController" closable="false">
</widget>


<p id="userSessionKeyName" userName="${userSessionKey.name}"
	userCode="${userSessionKey.userCode}"></p>