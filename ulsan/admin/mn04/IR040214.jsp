<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040214.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-03
* 프로그램내용	   : 세외수입 > 수령액팝업
***************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	CommonEntity revInfo = (CommonEntity)request.getAttribute("page.mn04.revInfo");

	String SucMsg = (String)request.getAttribute("page.mn04.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

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
				<% if (SucMsg != null && !"".equals(SucMsg)) { %>
					alert("<%=SucMsg%>");
				<% } %>
      }
		function goUpdate(){
  			var form = document.sForm;
				form.action = "IR040214.etax";  
				form.cmd.value = "revInfoUpdate";
				form.target = "_self";
				eSubmit(form);
		}		      

    function cal(){
      var form = document.sForm;
      var temp_val = 0;
  
      if (form.m080monthamt1.value == "") {
        form.m080monthamt1.value = "0";
      }
      if (form.m080monthamt2.value == "") {
        form.m080monthamt2.value = "0";
      }
      if (form.m080monthamt3.value == "") {
        form.m080monthamt3.value = "0";
      }
      if (form.m080monthamt4.value == "") {
        form.m080monthamt4.value = "0";
      }
      if (form.m080monthamt5.value == "") {
        form.m080monthamt5.value = "0";
      }
      if (form.m080monthamt6.value == "") {
        form.m080monthamt6.value = "0";
      }
      if (form.m080monthamt7.value == "") {
        form.m080monthamt7.value = "0";
      }
      if (form.m080monthamt8.value == "") {
        form.m080monthamt8.value = "0";
      }
      if (form.m080monthamt9.value == "") {
        form.m080monthamt9.value = "0";
      }
      if (form.m080monthamt10.value == "") {
        form.m080monthamt10.value = "0";
      }
      if (form.m080monthamt11.value == "") {
        form.m080monthamt11.value = "0";
      }
      if (form.m080monthamt12.value == "") {
        form.m080monthamt12.value = "0";
      }
      if (form.m080monthamt13.value == "") {
        form.m080monthamt13.value = "0";
      }
      if (form.m080monthamt14.value == "") {
        form.m080monthamt14.value = "0";
      }


      temp_val = parseInt(form.m080monthamt1.value.replace(/,/g, ""), 10) + 
                 parseInt(form.m080monthamt2.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt3.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt4.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt5.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt6.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt7.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt8.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt9.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt10.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt11.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt12.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt13.value.replace(/,/g, ""), 10) +
                 parseInt(form.m080monthamt14.value.replace(/,/g, ""), 10);
    	form.m080totamt.value = formatCurrency(temp_val);
    }

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
		<form name="sForm" method="post" action="IR040214.etax">
		<input type="hidden" name="cmd" value="revInfoView">
		<input type="hidden" name="m080year" value="<%=revInfo.getString("M080_YEAR")%>">
		<input type="hidden" name="m080seq" value="<%=revInfo.getString("M080_SEQ")%>">
		<input type="hidden" name="year" value="<%=revInfo.getString("year")%>">
		<input type="hidden" name="date" value="<%=revInfo.getString("date")%>">
		<input type="hidden" name="mok" value="<%=revInfo.getString("mok")%>">
		<input type="hidden" name="sogwanpart" value="<%=revInfo.getString("sogwanpart")%>">
		<input type="hidden" name="silgwa" value="<%=revInfo.getString("silgwa")%>">
		<input type="hidden" name="businessname" value="<%=revInfo.getString("businessname")%>">
		<input type="hidden" name="monthamt" value="<%=revInfo.getString("monthamt")%>">
    <table width="310" border="0" cellspacing="0" cellpadding="0">
	    <tr> 
        <td width="18">&nbsp;</td>
        <td width="290" height="40"><font size="3"><b><%=revInfo.getString("M080_BUSINESSNAME")%> 월별 수령액</b></font></td>
      </tr>
      <tr> 
        <td>&nbsp;</td>
        <td> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="20%">월별</th>
							<td>수령액</td>
					  </tr>
						<tr>
							<th>1</th>
							<td class="right">&nbsp;
					    <input type="text" name="m080monthamt1" class="box3" style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number" value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_1"))%>" >
              </td>
					  </tr>
						<tr>
							<th>2</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt2" class="box3" style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_2"))%>" >
              </td>
					  </tr>
						<tr>
							<th>3</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt3" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_3"))%>">
              </td>
					  </tr>
						<tr>
							<th>4</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt4" class="box3" style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_4"))%>">
              </td>
					  </tr>
						<tr>
							<th>5</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt5" class="box3" style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_5"))%>">
              </td>
					  </tr>
						<tr>
							<th>6</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt6" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_6"))%>">
              </td>
					  </tr>
						<tr>
							<th>7</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt7" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_7"))%>">
              </td>
					  </tr>
						<tr>
							<th>8</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt8" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_8"))%>">
              </td>
					  </tr>
						<tr>
							<th>9</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt9" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_9"))%>">
              </td>
					  </tr>
            <tr>
							<th>10</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt10" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_10"))%>">
              </td>
					  </tr>
            <tr>
							<th>11</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt11" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_11"))%>">
              </td>
					  </tr>
            <tr>
							<th>12</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt12" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_12"))%>">
              </td>
					  </tr>
            <tr>
							<th>13</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt13" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_13"))%>">
              </td>
					  </tr>
            <tr>
							<th>14</th>
							<td class="right">&nbsp;
              <input type="text" name="m080monthamt14" class="box3"  style="text-align:right;" size="15" maxlength="15"  onkeyup="cal(); keyupCurrencyObj(this)" userType="number"  value="<%=TextHandler.formatNumber(revInfo.getString("M080_MONTHAMT_14"))%>">
              </td>
					  </tr>
            <tr>
							<th>합계</th>
							<td class="right">&nbsp;
              <input type="text" name="m080totamt" class="box3" size="15" userType="money"  readonly value="<%=TextHandler.formatNumber(revInfo.getString("TOT_AMT"))%>" maxlength="15">
              </td>
					  </tr>
          </table>
        </td>
      </tr>
				<td width="18">&nbsp;</td>
         <td width="300"> 
           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
				     <tr>
							 <td>
							   <img src="../img/btn_modify.gif" alt="수정" style="cursor:hand" onClick="goUpdate()" align="absmiddle"></a>
							   <a href="javascript:self.close();"><img src="../img/btn_close.gif" alt="닫기" style="cursor:hand" align="absmiddle"></a>
					     </td>
				     </tr>
			     </table>
		     </td>
    </table>
  </form>
  </body>
</html>