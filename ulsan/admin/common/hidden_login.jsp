<%
/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : hidden_login.jsp
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2010-08-30
* ���α׷�����	  : �α��� iframe ���� ����
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
		return false; //�ݵ�� false�� return;
  }	

</script>
<form name="authForm" action="hidden_login.etax?cmd=managerLogin" method="post" target="_parent">
	<input type="hidden" name="INIpluginData">
	<input type="hidden" name="userid" value="<%=request.getParameter("userid")%>">
	<input type="hidden" name="userpw" value="<%=request.getParameter("userpw")%>">
</form>