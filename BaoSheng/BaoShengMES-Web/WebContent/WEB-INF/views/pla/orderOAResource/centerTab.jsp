<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="sctx" value="/bsstatic" />
<link href="${sctx}/lib/gantt/css/sch-gantt-all.css" rel="stylesheet" type="text/css" />
<link href="${sctx}/lib/gantt/css/orderTask.css" rel="stylesheet" type="text/css" /> 
<script src="${sctx}/lib/gantt/js/sch-all-debug.js" type="text/javascript"></script> 

<<style>
<!--
.x-tree-icon, .x-tree-expander, .x-tree-elbow, .x-tree-elbow-end{
 display: none;
}
.x-tree-elbow-img{
 width: 10px;
}
.x-tree-node-text {
 padding: 13px 0 0;
}
.x-tree-node-text, .sch-event-inner {
 font-size: 12px;
}
.group-title .x-tree-node-text{
 font-size: 18px;
 font-weight: 700;
}
-->
</style>
<widget title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>" xtype="orderOAResource" controller="OrderOAResourceController" 
		closable="false"> </widget>