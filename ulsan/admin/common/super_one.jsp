<%
/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		  : super_one.jsp
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2010-09-17
* ���α׷�����	  : ���̵� �ߺ�üũ �� ����üũ
*                 : iframe ���� ���� 
*****************************************************************/
%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

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
<form name="sForm" action="super_two.etax?cmd=superCheck&v_flag=<%=request.getParameter("v_flag")%>" method="post">
	<input type="hidden" name="INIpluginData">
  <input type="hidden" name="v_flag" value="<%=request.getParameter("v_flag")%>">
</form>