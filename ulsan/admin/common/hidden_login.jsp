<%
/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : hidden_login.jsp
* 프로그램작성자	: 
* 프로그램작성일	: 2010-08-30
* 프로그램내용	  : 로그인 iframe 에서 실행
*****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>


<script language="javascript" src="../js/INIplugin.js"></script>
<script language="javascript">
  function window.onload() {
    CheckSendForm(authForm);
  }

  function CheckSendForm(authForm) {
	  InitCache();
		if (EncFormVerify(authForm)) {
			authForm.submit();
			return false;
		} 
		return false; //반드시 false를 return;
  }	

</script>
<form name="authForm" action="hidden_login.etax?cmd=managerLogin" method="post" target="_parent">
	<input type="hidden" name="INIpluginData">
	<input type="hidden" name="userid" value="<%=request.getParameter("userid")%>">
	<input type="hidden" name="userpw" value="<%=request.getParameter("userpw")%>">
</form>