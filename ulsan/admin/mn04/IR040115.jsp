<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR040115.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-03
* ���α׷�����	   : ���ܼ��� > ���꼭���(�뷮)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn04.jsp" %>
<%
	
	String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy");
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
			}

			function goTmpUpload(){
        var form = document.sForm;
        idx1 = form.upfile.value.lastIndexOf('.');
        idx2 = form.upfile.length;
        if(form.upfile.value == ""){
          alert("���� ������ ÷���ϼ���.");
          return;
        }
        if(form.upfile.value.substring(idx1+1, idx2) != "xls"){
          alert("���� ���ϸ� ���ε� �����մϴ�.");
          form.upfile.select();
          document.selection.clear();
          return;
        }

				if (confirm("÷���� ��������("+form.upfile.value+")�� �ε��Ͻðڽ��ϱ�?")) {
					form.action = "IR040115.etax?cmd=budgetUpLoad&uploadDir=excel&work_log=C04&trans_gubun=111";
          eSubmit(form);
				}
      } 
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
	<form name="sForm" method="post" enctype="multipart/form-data" action="IR040115.etax">
	<input type="hidden" name="cmd" value="budgetUpLoad">
    <input type="hidden" name="work_log" value="C04">
    <input type="hidden" name="trans_gubun" value="111">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title4_28.gif"></td>
      </tr>
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
			</tr>
		</table>
		<table>
      <tr> 
        <td width="118">&nbsp;</td>
        <td width="775">
          <table width="80%" border="0" cellspacing="0" cellpadding="0"class="list">
            <tr> 
              <th width="30%">ȸ�迬��</th>
							<td>&nbsp;<select name="fis_year" class="box3">
                        <option value="<%=this_year%>"><%=this_year%></option>
                        <option value="<%=Integer.parseInt(this_year)-1%>"><%=Integer.parseInt(this_year)-1%></option>
                      </select> 
              </td>
						</tr>
            <tr> 
              <th width="30%">��������</th>
							<td>&nbsp;
							<input type="file" name="upfile" size="40">
							<img src="../img/btn_regi.gif" alt="����" style="cursor:hand" onClick="goTmpUpload()" align="absmiddle"></td>

						</tr>
          </table>
        </td>
      </tr>

    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
  <% if (SucMsg != "") { %>
  <script>
    alert("<%=SucMsg%>");
  </script>
  <% } %>
</html>