<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090811.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > �����ڵ����(�����˾�)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn09.jsp" %>

<%
	CommonEntity semokData = (CommonEntity)request.getAttribute("page.mn09.semokData");

  String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
	if (SucMsg == null ){
		SucMsg = "";
	}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">

      function init() {
				typeInitialize();
			}
      
			function goUpdate()	{
        var form = document.sForm;
				if (form.semokName.value == "") {
          alert("������� ����ϼ���.");
          form.semokName.focus();
          return;
        }

				form.action = "IR090811.etax";
				form.cmd.value = "semokCodeUpdate";
        form.target="_self";
				eSubmit(form);
			}

    </script>
  </head>
  <body>
		<form name="sForm" method="post" action="IR090811.etax">
    <input type="hidden" name="cmd" value="semokCodeUpdate">
    <% if (semokData != null) { %>
    <input type="hidden" name="year" value="<%=semokData.getString("M370_YEAR")%>">
    <input type="hidden" name="accGubun" value="<%=semokData.getString("M370_ACCGUBUN")%>">
    <input type="hidden" name="accCode" value="<%=semokData.getString("M370_ACCCODE")%>">
    <input type="hidden" name="semokCode" value="<%=semokData.getString("M370_SEMOKCODE")%>">
    <input type="hidden" name="workGubun" value="<%=semokData.getString("M370_WORKGUBUN")%>">
    <input type="hidden" name="mokGubun" value="<%=semokData.getString("M370_MOKGUBUN")%>">
    <% } %>
    <table width="690" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td colspan="2" height="30">&nbsp;</td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="675">
					<table width="670" border="0" cellspacing="0" cellpadding="0" class="list">
						<tr> 
							<th width="130">����׸�</th>
							<th width="150">�����ڵ�</th>
							<th width="260">�����</th>  
							<th width="130">��������</th>
						</tr>
					  <%
	 		 	 	    if (semokData != null) { 
					  %>
						<tr>                 
							<td>&nbsp;
							 <%=semokData.getString("MOKGUBUN")%>
							</td>
							<td>
							  <%=semokData.getString("M370_SEMOKCODE")%>
							</td>
							<td>
							  <input type="text" maxlength="30" name="semokName" class="box3" size="25" value="<%=semokData.getString("M370_SEMOKNAME")%>">
							</td>
							<td>
                <select name="segumGubun" iValue="<%=semokData.getString("M370_SEGUMGUBUN")%>">
									<option value="1">N</option>
									<option value="2">Y</option>
								</select>
							</td>
						</tr>
						<% } %>
					</table>
				</td>
			</tr>
    </table>
		<table width="690">
			<tr>
				<td align="right">
          <img src="../img/btn_modify.gif" alt="����" align="absmiddle" onclick="goUpdate()" style="cursor:hand">
				</td>
			</tr>
		</table>
  </form>
  <% if (!"".equals(SucMsg)) { %>
  <script>
    alert("<%=SucMsg%>");
    opener.goSearch();
    self.close();
  </script>
  <% } %>
</body>
</html>