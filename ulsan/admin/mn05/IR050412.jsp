<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050412.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 자금(재)배정통지서조회-배정내역(팝업)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  List<CommonEntity> cityBaeList1 = (List<CommonEntity>)request.getAttribute("page.mn05.cityBaeList1");

  List<CommonEntity> cityBaeList2 = (List<CommonEntity>)request.getAttribute("page.mn05.cityBaeList2");
%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
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
  if (cityBaeList1 != null) {
%>
<body bgcolor="#FFFFFF" topmargin="20" leftmargin="10">
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
                <td height="60" style="font-size:16pt"> 
                  <div align="center"><b><%=request.getParameter("fis_year")%> 일반회계 자금 <% if ("ED06".equals(request.getParameter("doc_code")) ) { %>재<% } %>배정 <%if ("ED00".equals(request.getParameter("doc_code")) )  {%>및 재배정 <%}%>내역</b></div>
                </td>
              </tr>
            </table>
            <p>&nbsp;</p>
            <table width="720" border="0" cellspacing="0" cellpadding="1" class="basic">
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
              <tr height="30"> 
							  <td rowspan="2" width="13%" align="center">구분</td>
                <td colspan="2" width="26%" align="center">예산</td>
                <td colspan="3" width="37%" align="center">지급한도액</td>
                <td colspan="2" width="24%" align="center">잔액</td>
              </tr>
              <tr height="30"> 
                <td align="center">예산현액</td>
                <td align="center">배정액</td>
                <td align="center">기배정액</td>
                <td align="center">금회배정</td>
                <td align="center">합계</td>
                <td align="center">예산대</td>
                <td align="center">배정대</td>
              </tr>
              <% if ("ED00".equals(request.getParameter("doc_code")) ) { %>
              <tr height="30"> 
							  <td align="center"><b>총 계</b></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("M110_BUDGETAMT") + cityBaeList2.get(0).getLong("M110_BUDGETAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("M110_BUDGETALLOTAMT") + cityBaeList2.get(0).getLong("M110_BUDGETALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("M110_ORIALLOTAMT") + cityBaeList2.get(0).getLong("M110_ORIALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("ALLOTAMT") + cityBaeList2.get(0).getLong("ALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("TOT_AMT") + cityBaeList2.get(0).getLong("TOT_AMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("YESAN") + cityBaeList2.get(0).getLong("YESAN"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("BAE") + cityBaeList2.get(0).getLong("BAE"))%></td>
              </tr>
              <% } %>
              <% for (int i=0; cityBaeList1 != null && i < cityBaeList1.size(); i++) {
                   CommonEntity baeInfo = (CommonEntity) cityBaeList1.get(i); %>
              <tr height="30"> 
							  <td align="center"><%=baeInfo.getString("M350_PARTNAME")%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_BUDGETAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_BUDGETALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_ORIALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("ALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("TOT_AMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("YESAN"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("BAE"))%></td>
              </tr>
              <% } %>
            <%if ("ED00".equals(request.getParameter("doc_code")) )  {%>
              <% for (int i=0; cityBaeList2 != null && i < cityBaeList2.size(); i++) {
                   CommonEntity baeInfo = (CommonEntity) cityBaeList2.get(i); %>
              <tr height="30"> 
							  <td align="center"><%=baeInfo.getString("M350_PARTNAME")%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_BUDGETAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_BUDGETALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_ORIALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("ALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("TOT_AMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("YESAN"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("BAE"))%></td>
              </tr>
              <% } %>
            </table>
            <% }%>
          </td>
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
