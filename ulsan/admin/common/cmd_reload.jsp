<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.etax.helper.CommandHelper" %>
<%
	CommandHelper helper = new CommandHelper();
	helper.setCommand("cmdReload");
	helper.executeCommand(request,response);

	out.print("command_list.xml is reloaded.<br>");
	out.print("CommandMapper is created.");
%>