<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.etax.helper.MessageHelper" %>
<%
	/* �޽��� ó�� ������ */
	String message = request.getParameter("message");
	String queryString = request.getQueryString();
	String redirectPage = queryString.substring(queryString.indexOf("&redirectPage=")+"&redirectPage=".length());
	
	message = MessageHelper.getMessage(message);
%>
<script>
	alert("<%=message%>");
	document.location = "<%=redirectPage%>";
</script>