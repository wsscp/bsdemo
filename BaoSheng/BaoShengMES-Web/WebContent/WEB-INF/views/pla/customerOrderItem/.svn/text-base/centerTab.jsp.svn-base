<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="sctx" value="/bsstatic" />
<head>
<style type="text/css">
.x-grid-record-red-color {
	color: red !important;
}

.x-grid-record-orange-color {
	color: #FF8C30 !important;
}

.x-grid-record-blue-color {
	color: #3880C9 !important;
}

.x-grid-record-green-color {
	color: #008000 !important;
}

.x-grid-record-purple-color {
	color: #9264E8 !important;
}

.x-grid-record-cyan-color {
	color: #6666FF !important;
}

.x-grid-record-grey-color {
	color: grey !important;
}

.icon_split_roll {
	background-image: url("${sctx}/icons/arrow-continue-090.png");
}

.icon_split {
	background-image: url("${sctx}/icons/arrow-split.png");
}

.icon_deadline {
	background-image: url("${sctx}/icons/calendar-blue.png");
}
</style>
</head>
<widget
	title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>"
	xtype="customerOrderList" controller="CustomerOrderItemController"
	closable="false"> </widget>
<div id="customerOrderStatus" statusArray='${cusOrderStatus}' />
