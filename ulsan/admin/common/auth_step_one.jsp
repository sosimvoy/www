<%
/****************************************************************
* ������Ʈ��		  : ���������� ������¹�ȣ ���ΰ����� �ý���
* ���α׷���		  : auth_step_one.jsp
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2009-12-17
* ���α׷�����	  : �ֹι�ȣ �ߺ�üũ �� ����üũ
*                 : iframe ���� ���� 
*****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%
    String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
%>
<script language="javascript" src="../js/INIplugin.js"></script>
<script language="javascript">
function window.onload(){
  InitCache();
	if (EncFormVerify(sForm)) {
		sForm.submit();
		return false;
	} 
	return false; //�ݵ�� false�� return;
}

</script>
<form name="sForm" action="auth_step_two.etax?cmd=managerCheck" method="post">
	<input type="hidden" name="INIpluginData">
	<input type="hidden" name="userid" value="<%=userid%>">
	<input type="hidden" name="userpw" value="<%=userpw%>">
</form>