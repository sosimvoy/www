<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR040212.jsp
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
      
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
		<form name="sForm" method="post" action="IR040212.etax">
		<input type="hidden" name="cmd" value="chuKyungView">
    <% if (chukyngView != null) { %>
    <table width="310" border="0" cellspacing="0" cellpadding="0">	
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="290" height="40"><font size="3"><b><%=chukyngView.getString("M080_BUSINESSNAME")%> �߰濹��</b></font></td>
      </tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="290"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="30%">ȸ��</th>
							<td>&nbsp;�߰濹��</td>
					  </tr>
						<tr>
							<th>�߰濹��1</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT1"))%></td>
					  </tr>
						<tr>
							<th>�߰濹��2</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT2"))%></td>
					  </tr>
						<tr>
							<th>�߰濹��3</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT3"))%></td>
					  </tr>
						<tr>
							<th>�߰濹��4</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT4"))%></td>
					  </tr>
						<tr>
							<th>�߰濹��5</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT5"))%></td>
					  </tr>
						<tr>
							<th>�߰濹��6</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT6"))%></td>
					  </tr>
						<tr>
							<th>�߰濹��7</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT7"))%></td>
					  </tr>
						<tr>
							<th>�߰濹��8</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT8"))%></td>
					  </tr>
						<tr>
							<th>�߰濹��9</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT9"))%></td>
					  </tr>
            <tr>
							<th>�հ�</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("TOT_AMT"))%></td>
					  </tr>
          </table>
        </td>
      </tr>
				<td width="18">&nbsp;</td>
         <td width="300"> 
           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
				     <tr>
							 <td>
							   <a href="javascript:self.close();"><img src="../img/btn_close.gif" alt="�ݱ�" style="cursor:hand" align="absmiddle"></a>
					     </td>
				     </tr>
			     </table>
		     </td>
    </table>
    <% } %>
  </form>
  </body>
</html>