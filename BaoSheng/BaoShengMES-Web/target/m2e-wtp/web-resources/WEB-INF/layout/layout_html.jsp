<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<html>
 <head>
  <link href="${sctx}/extjs/resources/css/ext-all-neptune.css" type="text/css" rel="stylesheet">
  <link href="${sctx}/assets/css/font-awesome.min.css" type="text/css" rel="stylesheet">
  <link href="${sctx}/assets/css/bootstrap.min.css" type="text/css" rel="stylesheet">
  <link href="${sctx}/newstyle.css" type="text/css" rel="stylesheet">
  <!--  
  <script src="${sctx}/extjs/ext-all.js" type="text/javascript"></script>
  -->
  <script type="text/javascript" src="${sctx}/jQuery/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" src="${sctx}/jQuery/bootstrap.min.js"></script>

 </head>
 <body>
  <tiles:insertAttribute name="center" />
 </body>
</html>
