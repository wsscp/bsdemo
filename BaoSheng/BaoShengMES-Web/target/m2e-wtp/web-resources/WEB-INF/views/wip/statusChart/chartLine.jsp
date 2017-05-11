<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<widget title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>" 
	xtype="lineList" controller="LineController"> </widget>
<input id="equipCode" type="hidden" value='${equipCode}'>
<input id="equipName" type="hidden" value='${equipCode}'>