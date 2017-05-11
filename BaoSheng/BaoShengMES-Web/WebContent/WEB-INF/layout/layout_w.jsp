<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
        isELIgnored="false"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<html>
<head>
    <tiles:insertAttribute name="import" />
    <script type="text/javascript" src="${sctx}/<tiles:insertAttribute name="app" />.js"></script>
</head>
<body>
    <div id="app" name="${ctx}" module="${moduleName}" submodule="${submoduleName}" class="x-hide-display">
        <div id="west"><tiles:insertAttribute name="west" /></div>
        <div id="center">
            <tiles:insertAttribute name="center" />
        </div>
    </div>
</body>
</html>
