<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<widget
	title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>"
	xtype="manualManageList" controller="ManualManageController" closable="false">
</widget>