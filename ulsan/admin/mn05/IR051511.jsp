<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR051510.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �ڱݹ������γ�����ȸ/����ó��(����������)
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
			if (confirm("å���ڽ����� �ʿ��մϴ�. å���ڽ��λ��� : " + "<%=TextHandler.getPmsName(rstData.getString("detail06"))%>"))	{
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
			alert('�����ڵ� : <%=rstData.getString("common10")%> ,   �������� : <%=SucMsg%> ');
		  parent.goSearch();
		<% } else if ("0000".equals(rstData.getString("common10")))	{ %>
			alert('<%=SucMsg%>');
		  parent.goSearch();
		<% }%>
		</script>
	</head>
</html>