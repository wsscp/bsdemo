<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<widget title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>" xtype="userlist" controller="UsersController"
		closable="false"> </widget>
<div id="orgInfo" orgCode = "${orgCode}" orgName = "${orgName}"></div>
