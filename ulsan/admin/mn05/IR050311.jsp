<%/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050311.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 자금재배정승인내역조회/재배정처리(히든프레임)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %><%String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");%>
<html>
  <head>
	  <script language="javascript" src="../js/base.js"></script>
	  <script>
		var form = parent.document.sForm;
		alert('<%=SucMsg%>');	    parent.goSearch();
		</script>
	</head>
</html>