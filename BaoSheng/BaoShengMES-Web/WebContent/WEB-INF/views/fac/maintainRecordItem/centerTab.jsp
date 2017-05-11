<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="sctx" value="/bsstatic" />
<c:if test="${touch}">
    <link href="${sctx}/touch.css" type="text/css" rel="stylesheet"/>
</c:if>
<widget title='<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>' xtype="maintainRecordItemList" controller="MaintainRecordItemController" closable="false"> </widget>
<div style="display: none">
    <div id = "touch">${touch}</div>
    <div id = "completed">${completed}</div>
    <div id = "startTime">${startTime}</div>
    <div id = "finishTime">${finishTime}</div>
</div>