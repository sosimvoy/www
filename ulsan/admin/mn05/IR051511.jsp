<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR051510.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 자금배정승인내역조회/배정처리(히든프레임)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%
  CommonEntity rstData = (CommonEntity)request.getAttribute("page.mn05.rstData");
	String SucMsg = (String)request.getAttribute("page.mn05.SucMsg");
%>
<html>
  <head>
	  <script language="javascript" src="../js/base.js"></script>
	  <script>
		var form = parent.document.sForm;
		<% if ("OVRD".equals(rstData.getString("common10")) ) { %>
			if (confirm("책임자승인이 필요합니다. 책임자승인사유 : " + "<%=TextHandler.getPmsName(rstData.getString("detail06"))%>"))	{
			  form.err_code.value = "<%=rstData.getString("detail06")%>";
				form.pms_cnt.value = "<%=rstData.getString("detail05")%>";
				form.send_no.value = "<%=rstData.getString("detail11")%>";
			  parent.goPermission();
			} else {
				form.err_code.value = "<%=rstData.getString("detail06")%>";
				form.pms_cnt.value = "<%=rstData.getString("detail05")%>";
				form.send_no.value = "<%=rstData.getString("detail11")%>";
				parent.goCancel();
			}
		<% } else if (!"0000".equals(rstData.getString("common10")))	{ %>
			alert('에러코드 : <%=rstData.getString("common10")%> ,   에러내용 : <%=SucMsg%> ');
		  parent.goSearch();
		<% } else if ("0000".equals(rstData.getString("common10")))	{ %>
			alert('<%=SucMsg%>');
		  parent.goSearch();
		<% }%>
		</script>
	</head>
</html>