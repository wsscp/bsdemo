<%@page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cc.oit.bsmes.common.exception.MESException" %>
<%
    request.setAttribute("errorMsg", ((MESException) request.getAttribute("javax.servlet.error.exception")).getLocalizedMessage());
%>
${errorMsg}
<br><a href="javascript:window.history.back();">返回</a>