<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050413.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 자금(재)배정통지서조회-보유현황(팝업)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  List<CommonEntity> boyuReportList1 = (List<CommonEntity>)request.getAttribute("page.mn05.boyuReportList1");

  List<CommonEntity> boyuReportList2 = (List<CommonEntity>)request.getAttribute("page.mn05.boyuReportList2");
%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11pt; color: #333333}
.etc {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}

@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript">
	function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 2.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true) // 출력하기 
	}
</script>
</head>
<%
  if (boyuReportList1 != null) {
%>
<body bgcolor="#FFFFFF" topmargin="30" leftmargin="10">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<center>
<table width="730" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="7" height="21">
      <table width="727" border="0" cellspacing="0" cellpadding="0" class="basic">
        <tr> 
          <td colspan="7" height="21"> 
            <table width="720" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="80" style="font-size:18pt"> 
                <br>
                  <div align="center"><b><%=request.getParameter("fis_year")%> 일반회계 자금 <% if ("ED06".equals(request.getParameter("doc_code")) ) { %>재<% } %>배정액 보유현황</b></div>
                </td>
              </tr>
            </table>
            <br>
            <table width="720" border="0" cellspacing="0" cellpadding="1" class="etc">
              <tr>
							  <td height="25"> 
                  <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy. MM. dd")%></div>
                </td>
                <td height="25"> 
                  <div align="right">(단위:천원)</div>
                </td>
              </tr>
            </table>
            <table width="720" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic" align="center">
              <tr> 
							  <td width="17%"> 
                  <div align="center"><b>소 관 별</b></div>
                </td>
                <td width="26%"> 
                  <div align="center"><b>현금보유잔액</b></div>
                </td>
                <td width="27%" height="30"> 
                  <div align="center"><b>금회배정액</b></div>
                </td>
                <td width="30%"> 
                  <div align="center"><b>현금보유액</b></div>
                </td>
              </tr>
							<% for (int i=0; i<boyuReportList1.size(); i++) { 
	                 CommonEntity rowData = boyuReportList1.get(i);
	            %>
              <% if ("67".equals(rowData.getString("M360_ACCCODE"))) { %>
              <tr> 
							  <td>
                  <div align="center">동부소방서</div>
                </td>
                <td> 
                  <div align="right">0</div>
                </td>
                <td> 
                  <div align="right">0</div>
                </td>
                <td height="30"> 
                  <div align="right"><b>0</b></div>
                </td>
              </tr>
              <% } %>
              <tr> 
							  <td>
                  <div align="center"><%=rowData.getString("M360_ACCNAME")%></div>
                </td>
                <td> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M170_OFFICIALDEPOSITJANAMT"))%></div>
                </td>
                <td> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT"))%></div>
                </td>
                <td height="30"> 
                  <div align="right"><b><%=TextHandler.formatNumber(rowData.getString("M_BOYU"))%></b></div>
                </td>
              </tr>
							<% } %>
            </table>

            <% if ("ED00".equals(request.getParameter("doc_code")) ) { %>
            <table width="720" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="80" style="font-size:18pt"> 
                <br>
                  <div align="center"><b><%=request.getParameter("fis_year")%> 일반회계 자금 재배정액 보유현황</b></div>
                </td>
              </tr>
            </table>
            <br>
            <table width="720" border="0" cellspacing="0" cellpadding="1" class="etc">
              <tr>
							  <td height="25"> 
                  <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy. MM. dd")%></div>
                </td>
                <td height="25"> 
                  <div align="right">(단위:천원)</div>
                </td>
              </tr>
            </table>
            <table width="720" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic" align="center">
              <tr> 
							  <td width="17%"> 
                  <div align="center"><b>소 관 별</b></div>
                </td>
                <td width="26%"> 
                  <div align="center"><b>현금보유잔액</b></div>
                </td>
                <td width="27%" height="30"> 
                  <div align="center"><b>금회배정액</b></div>
                </td>
                <td width="30%"> 
                  <div align="center"><b>현금보유액</b></div>
                </td>
              </tr>
							<% for (int i=0; i<boyuReportList2.size(); i++) { 
	                 CommonEntity rowData = boyuReportList2.get(i);
	            %>
              <% if ("67".equals(rowData.getString("M360_ACCCODE"))) { %>
              <tr> 
							  <td>
                  <div align="center">동부소방서</div>
                </td>
                <td> 
                  <div align="right">0</div>
                </td>
                <td> 
                  <div align="right">0</div>
                </td>
                <td height="30"> 
                  <div align="right"><b>0</b></div>
                </td>
              </tr>
              <% } %>
              <tr> 
							  <td>
                  <div align="center"><%=rowData.getString("M360_ACCNAME")%></div>
                </td>
                <td> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M170_OFFICIALDEPOSITJANAMT"))%></div>
                </td>
                <td> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT"))%></div>
                </td>
                <td height="30"> 
                  <div align="right"><b><%=TextHandler.formatNumber(rowData.getString("M_BOYU"))%></b></div>
                </td>
              </tr>
							<% } %>
            </table>
            <% } %>
          <p>&nbsp;</p></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</center>
<div class="noprint">
<div align="right">
<img src="../img/btn_print.gif" alt="인쇄" onClick="goPrint()" style="cursor:hand">
<img src="../img/btn_close.gif" alt="닫기" onClick="self.close()" style="cursor:hand"><span style="width:40"></span></div>
</div>
<p><span class="count"></span> </p>
</body>
<% } %>
</html>