<%/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050311.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �ڱ���������γ�����ȸ/�����ó��(����������)
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