<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091310.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ý��ۿ > ��Ÿ���������
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn09.jsp" %>
<%
  CommonEntity bankOpen  = (CommonEntity)request.getAttribute("page.mn09.result");

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
     
      function goTrans() {
				var form = document.sForm;
				form.action = "IR091310.etax";
				form.cmd.value = "bankOpenList";
      	eSubmit(form);
      }

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR091310.etax">
	<input type="hidden" name="cmd" value="bankOpenList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_26.gif"></td>
      </tr>
			<tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"> 
          <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
            <tr> 
              <td><img src="../img/box_top.gif" width="964" height="11"></td>
            </tr>
            <tr> 
              <td> 
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr> 
                    <td width="860"><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    ��Ÿ�����
										  <select name="trans_type" iValue="<%=request.getParameter("trans_type")%>">
												<option value="OPEN">��������</option>
												<option value="END">��������</option>
												<option value="TEST">�׽�Ʈ��</option>
											</select>
										</td>
                    <td width="100">
                      <img src="../img/btn_send.gif" alt="����" style="cursor:hand" onClick="goTrans()" align="absmiddle">
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr> 
              <td><img src="../img/box_bt.gif" width="964" height="10"></td>
            </tr>
          </table>
        </td>
			</tr>	
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="15%">��Ÿ�����</th>
              <th width="15%">�������Žð�</th>
							<th width="30%">�����ڵ�</th>
							<th width="40%">��������</th>
            </tr>
						<% if (bankOpen != null) { 
							   String WorkGubun = "";
							%>
						     
						  <% if ("100".equals(bankOpen.getString("common05"))) { 
								   WorkGubun = "��������";
							   } else if ("200".equals(bankOpen.getString("common05"))) { 
									 WorkGubun = "��������";
								 } else if ("500".equals(bankOpen.getString("common05"))) { 
									 WorkGubun = "�׽�Ʈ��";
								 }
							%>
            <tr>						  
              <td class="fstleft">&nbsp;<%=WorkGubun%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(bankOpen.getString("common08")+bankOpen.getString("common09"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss")%></td>
              <td class="center">&nbsp;<%=bankOpen.getString("common10")%></td>
							<td class="center">&nbsp;<%=bankOpen.getString("common11")%></td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
</html>