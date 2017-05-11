<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<widget title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>" xtype="processList" controller="ProcessController" 
		closable="false"> </widget>
<input type="text" id="craftsIdSearch" name="craftsIdSearch" value="${craftsId}"/>
