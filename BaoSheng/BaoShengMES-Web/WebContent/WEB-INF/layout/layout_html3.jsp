<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<html>
 <head>
 	<link rel="stylesheet" type="text/css" href="${sctx}/touch.css" />
 </head>
 <body>
  <tiles:insertAttribute name="center" />
 </body>
</html>
