<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040212.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-03
* 프로그램내용	   : 세외수입 > 추경예산팝업
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
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
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
        <td width="290" height="40"><font size="3"><b><%=chukyngView.getString("M080_BUSINESSNAME")%> 추경예산</b></font></td>
      </tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="290"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="30%">회차</th>
							<td>&nbsp;추경예산</td>
					  </tr>
						<tr>
							<th>추경예산1</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT1"))%></td>
					  </tr>
						<tr>
							<th>추경예산2</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT2"))%></td>
					  </tr>
						<tr>
							<th>추경예산3</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT3"))%></td>
					  </tr>
						<tr>
							<th>추경예산4</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT4"))%></td>
					  </tr>
						<tr>
							<th>추경예산5</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT5"))%></td>
					  </tr>
						<tr>
							<th>추경예산6</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT6"))%></td>
					  </tr>
						<tr>
							<th>추경예산7</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT7"))%></td>
					  </tr>
						<tr>
							<th>추경예산8</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT8"))%></td>
					  </tr>
						<tr>
							<th>추경예산9</th>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(chukyngView.getString("M080_CHUKYNGAMT9"))%></td>
					  </tr>
            <tr>
							<th>합계</th>
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
							   <a href="javascript:self.close();"><img src="../img/btn_close.gif" alt="닫기" style="cursor:hand" align="absmiddle"></a>
					     </td>
				     </tr>
			     </table>
		     </td>
    </table>
    <% } %>
  </form>
  </body>
</html>