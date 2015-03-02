<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR010110.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 메인 페이지(프레임셋)
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%
	//컨트롤러를 거치지 않고 main.jsp 로 직접 들어온경우 인증 체크를 타지 않으므로
	//main.etax?cmd=none 으로 이동한다.
	//직접 들어왔는지의 여부는 컨트롤러에서 발행한 토큰 유무로 판단한다.
	if( request.getAttribute("etax.controller.token") == null ) {
		response.sendRedirect("/admin/");
	} 
%>

<html>
  <head>
    <title>울산광역시 세입 및 자금배정관리시스템</title>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
  </head>
	<frameset rows="145,768*" cols="*" border="0" framespacing="0"> 
		<frame src="menu/top.jsp" name="topFrame" frameborder="NO" noresize scrolling="NO">
  <frameset rows="960" cols="1024*" border="0" framespacing="0"> 
		<frame src="./mn01/IR000000.etax" name="mainFrame" frameborder="NO" scrolling="AUTO">
  </frameset>
</frameset>
<noframes>
<body bgcolor="#FFFFFF">

</body></noframes>

</html>