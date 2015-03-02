<%
/****************************************************************
* 프로젝트명		  : 대전광역시 가상계좌번호 납부관리자 시스템
* 프로그램명		  : auth_step_one.jsp
* 프로그램작성자	: 
* 프로그램작성일	: 2009-12-17
* 프로그램내용	  : 주민번호 중복체크 및 인증체크
*                 : iframe 에서 실행 
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
	return false; //반드시 false를 return;
}

</script>
<form name="sForm" action="auth_step_two.etax?cmd=managerCheck" method="post">
	<input type="hidden" name="INIpluginData">
	<input type="hidden" name="userid" value="<%=userid%>">
	<input type="hidden" name="userpw" value="<%=userpw%>">
</form>