<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<head>
    <style type="text/css">
        
        
    </style>
</head>
<widget title="<fmt:message key='${moduleName}.${submoduleName}.js.title'/>" xtype="reportJSGrid" controller="WorkOrderReportJSController" closable="false"> </widget>
