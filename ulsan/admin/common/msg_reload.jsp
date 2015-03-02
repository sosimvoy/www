<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.etax.helper.MessageHelper" %>
<%
	MessageHelper helper = new MessageHelper();
	String messageFile = application.getRealPath("/WEB-INF/config/message.properties");
	System.out.println(messageFile);
	helper.load(messageFile);

	out.print("message.properties is reloaded.<br>");
%>