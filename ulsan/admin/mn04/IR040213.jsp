<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR020212.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-03
* ���α׷�����	   : ���ܼ��� > �߰濹���˾�
***************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	CommonEntity chukyngView = (CommonEntity)request.getAttribute("page.mn04.chukyngView");

		String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
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
				<% if (SucMsg != null && !"".equals(SucMsg)) { %>
					 alert("<%=SucMsg%>");
			 <% } %>
			}

			
			function gosaveData()	{
				var form = document.sForm;
        var cnt1 = 0;
        var cnt2 = 0;
        if (confirm("�ݾ��� ȸ�������� �����ϼ̽��ϱ�?")) {
          form.action = "IR040213.etax";
				  form.cmd.value = "chukyungUpdate";
				  form.target = "_self";
				  eSubmit(form);
        }
				
			}
      
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
		<form name="sForm" method="post" action="IR040213.etax">
		<input type="hidden" name="cmd" value="chukyungView">
		<% if (chukyngView != null && chukyngView.size() > 0) {%>
    <input type="hidden" name="seq" value="<%=chukyngView.getString("M080_SEQ")%>">
		<input type="hidden" name="year" value="<%=chukyngView.getString("M080_YEAR")%>">
		
    <table width="310" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="300" height="40"><img src="../img/title4_9.gif"></td>
      </tr>
	
     <tr> 
        <td width="18">&nbsp;</td>
        <td width="500"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="3%">ȸ��</th>
							<td>&nbsp;�߰濹��</td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��1</th>
							<td>&nbsp;<input type="text" name="chukyngamt1" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT1"))%>"></td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��2</th>
							<td>&nbsp;<input type="text" name="chukyngamt2" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT2"))%>"></td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��3</th>
							<td>&nbsp;<input type="text" name="chukyngamt3" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT3"))%>"></td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��4</th>
							<td>&nbsp;<input type="text" name="chukyngamt4" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT4"))%>"></td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��5</th>
							<td>&nbsp;<input type="text" name="chukyngamt5" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT5"))%>"></td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��6</th>
							<td>&nbsp;<input type="text" name="chukyngamt6" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT6"))%>"></td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��7</th>
							<td>&nbsp;<input type="text" name="chukyngamt7" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT7"))%>"></td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��8</th>
							<td>&nbsp;<input type="text" name="chukyngamt8" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT8"))%>"></td>
					  </tr>
						<tr>
							<th width="30%">�߰濹��9</th>
							<td>&nbsp;<input type="text" name="chukyngamt9" style="text-align:right;" class="box3" size="15" value="<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT9"))%>"></td>
					  </tr>
          </table>
        </td>
      </tr>
      <%}%>
    </table>
    <table>
				<td width="18">&nbsp;</td>
         <td width="300"> 
           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
				     <tr>
							 <td>
							   <div align="right"><img src="../img/btn_modify.gif" alt="����" onClick="gosaveData()" style="cursor:hand">
							   <a href="javascript:self.close();"><img src="../img/btn_close.gif" alt="�ݱ�" style="cursor:hand" align="absmiddle"></a></div>
					     </td>
				     </tr>
			     </table>
		     </td>
    </table>
  </form>
  </body>
</html>