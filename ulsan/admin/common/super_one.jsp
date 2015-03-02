<%
/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : super_one.jsp
* 프로그램작성자	: 
* 프로그램작성일	: 2010-09-17
* 프로그램내용	  : 아이디 중복체크 및 인증체크
*                 : iframe 에서 실행 
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
	return false; //반드시 false를 return;
}

</script>
<form name="sForm" action="super_two.etax?cmd=superCheck&v_flag=<%=request.getParameter("v_flag")%>" method="post">
	<input type="hidden" name="INIpluginData">
  <input type="hidden" name="v_flag" value="<%=request.getParameter("v_flag")%>">
</form>