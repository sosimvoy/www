<%
/***************************************************************
* ������Ʈ��	 : ���������� ������¹�ȣ ���ΰ����� �ý���
* ���α׷���	 : logout.jsp
* ���α׷��ۼ��� :
* ���α׷��ۼ��� : 2007-02-26
* ���α׷�����	 : ��й�ȣ ����
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"  %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ taglib uri="../WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	String userID = (String)session.getAttribute("session.userID");
	String userName = (String)session.getAttribute("session.userName");
%>
<html>
  <head>
	  <title>���������� ������¹�ȣ ���ΰ����� �ý���</title>
	  <link href="../css/admin.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="../js/base.js"></script>
    <script language="javascript">
<!--
      function init() {
	      typeInitialize();
      }

      function goSave() {
	      var form = document.sForm;
      	form.formSubmit();
      }

      function checkPage() {
	      var form = document.sForm;
	      if( form.new_pwd.value != form.new_pwd_confirm.value ) {
		      alert("�ű� ��й�ȣ Ȯ�� ���� �ٸ��ϴ�.");
		      form.new_pwd_confirm.select();
		      return false;
	      }
	      return true;
      }
-->
    </script>
  </head>

  <body>
    <form name="sForm" method="post" action="logout.etax">
      <input type="hidden" name="cmd" value="changePwd">
      <input type="hidden" name="message" value="S002">
      <input type="hidden" name="mgr_id" value="<%=userID%>">
      <table style="margin-left:16px">
        <tr valign="top">
      	  <td width="766">
        		<table class="title">
		          <tr>
          			<td>����� ��й�ȣ ����</td>
           			<td align="right">
           				<div id="location">
           					<img src="../img/common/icon_lo.gif" align="absmiddle"> HOME &gt; <span class="on">����� ��й�ȣ ����</span>
           				</div>
           			</td>
           		</tr>
        		</table>
		
        		<p></p>

        		<table class="input">
          		<tr>
          			<th width="20%">�����ID</th>
          			<td class="last" width="85%"><%=userID%></td>
          		</tr>
          		<tr>
          			<th>����ڸ�</th>
          			<td class="last"><%=userName%></td>
          		</tr>
          		<tr>
          			<th>���� ��й�ȣ</th>
          			<td class="last"><input type="password" name="old_pwd" required desc="���� ��й�ȣ" userType="engNumber"></td>
          		</tr>
          		<tr>
          			<th>�ű� ��й�ȣ</th>
          			<td class="last"><input type="password" name="new_pwd" required desc="�ű� ��й�ȣ" userType="engNumber"></td>
          		</tr>
          		<tr>
          			<th>�ű� ��й�ȣ Ȯ��</th>
          			<td class="last"><input type="password" name="new_pwd_confirm" required desc="�ű� ��й�ȣ Ȯ��" userType="engNumber"></td>
          		</tr>
            </table>
          
          	<table class="btn">
          		<tr>
          			<td>
          				<a href="javascript:goSave()"><img src="../img/common/btn_save.gif" alt="����"></a>
          			</td>
          		</tr>
        		</table>
        	</td>
        </tr>
      </table>

      <script language="JavaScript" src="../js/Bottom.js"></script>

    </form>
  </body>
</html>