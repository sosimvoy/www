<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR010110.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� ������(�����Ӽ�)
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%
	//��Ʈ�ѷ��� ��ġ�� �ʰ� main.jsp �� ���� ���°�� ���� üũ�� Ÿ�� �����Ƿ�
	//main.etax?cmd=none ���� �̵��Ѵ�.
	//���� ���Դ����� ���δ� ��Ʈ�ѷ����� ������ ��ū ������ �Ǵ��Ѵ�.
	if( request.getAttribute("etax.controller.token") == null ) {
		response.sendRedirect("/admin/");
	} 
%>

<html>
  <head>
    <title>��걤���� ���� �� �ڱݹ��������ý���</title>
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