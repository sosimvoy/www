<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091411.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-07-15
* ���α׷�����	   : ������ > ������������ > ���ε��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<% 
   CommonEntity signInfo = (CommonEntity)request.getAttribute("page.mn09.signInfo");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
  	<script language="javascript">
      function init() {
  	    typeInitialize();
				<% if (signInfo != null && signInfo.size() > 0)	{ %>
					   alert("���ε尡 �Ϸ�Ǿ����ϴ�.");
				opener.goSearch();
				self.close();
				<% } %>
      }

			function goClose() {
				self.close();
			}

			function goUpload() {
				var form = document.sForm;
				idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;

				<% if (signInfo != null && signInfo.size() > 0)	{ %>
					alert("������ ��ϿϷ� �Ǿ����ϴ�. ���� �� �̿��ϼ���.");
				  return;
				<% } %>

        if (form.upfile.value=="") {
					alert("������ ÷���ϼ���.");
					return;
        }

				if(form.upfile.value.substring(idx1+1, idx2).toLowerCase() != "jpg"){
          alert("�̹���(jpg) ���ϸ� ���ε� �����մϴ�.");
          form.upfile.select();
          document.selection.clear();
          return;
        }

				eSubmit(form);
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
	<form name="sForm" method="post" enctype="multipart/form-data" action="IR091411.etax?cmd=signUpload&uploadDir=seal">
     <table width="450" border="0" cellspacing="0" cellpadding="5">
  <tr> 
    <td> 
      <table width="81%" border="0" cellspacing="0" cellpadding="0" class="sub_title">
        <tr> 
          <td>���� ���ε�</td>
        </tr>
      </table>
    </td>
  </tr>
	<tr>
	  <td>&nbsp</td>
	</tr>
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
        <tr> 
          <th class="fstleft" width="40%">���� ���ε�</th>
          <td width="60%"><input type="file" name="upfile" desc="���� ���ε�" size="30" class="box3"></td>
        </tr>
      </table>
			<br>
			<table width="100%">
			  <tr>
				  <td style="text-align:right">
					 <img src="../img/btn_order.gif" onclick="goUpload()" style="cursor:hand" alt="���">
					 <img src="../img/btn_close.gif" onclick="goClose()" style="cursor:hand" alt="�ݱ�">
					</td>
			  </tr>
			</table>
    </td>
  </tr>
</table>
</form>
  </body>
</html>