<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061811.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-07-15
* 프로그램내용	   : 자금운용 > 평잔표조회(월별)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%
  if ("Y".equals(request.getParameter("excelFlag")) ) {
    String charMarkForExcel = "";
    response.setContentType("application/vnd.ms-excel;charset=euc-kr");
    response.setHeader("Content-Disposition", "inline;filename=금고평균잔액표(일반회계)_"+request.getParameter("s_month")+".xls"); 
    response.setHeader("Content-Description", "JSP Generated Data");
    charMarkForExcel = "`"; // 문자를 그대로 표시하기 위해
  }
  List<CommonEntity> lastAvgList = (List<CommonEntity>)request.getAttribute("page.mn06.lastAvgList"); //과년도
  List<CommonEntity> thisAvgList = (List<CommonEntity>)request.getAttribute("page.mn06.thisAvgList"); //현년도
  String last_year = (String)request.getAttribute("page.mn06.last_year"); //과년도수치

  int thisListSize = 0;

  if (thisAvgList != null) {
    thisListSize = thisAvgList.size();
  }

%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<% if ("Y".equals(request.getParameter("excelFlag")) ) { %>
<meta name=ProgId content=Excel.Sheet>
<style>
  table { table-layout:fixed; }
  tr,td { border:0.5pt solid silver; }
</style>
<% } else { %>
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 14pt; color: #333333; text-decoration: underline}
@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<%  } %>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function goPrint() {
    var form = document.sForm;
    form.excelFlag.value = "N";
		factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 1.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 1.0; // 아래 여백 사이즈  
		factory.printing.Print(true); // 출력하기 
	}

  function goExcel() {
	  var form = document.sForm;
    form.excelFlag.value = "Y";
    form.action = "IR061811.etax";
    form.cmd.value = "avgMoneyList";
		eSubmit(form);
	}
</script>
</head>
<body bgcolor="#FFFFFF" topmargin="10" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR061811.etax">
<input type="hidden" name="cmd" value="avgMoneyList">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_month" value="<%=request.getParameter("acc_month")%>">
<input type="hidden" name="s_month" value="<%=request.getParameter("s_month")%>">
<input type="hidden" name="excelFlag" value="">
<table width="1000" border="0" cellspacing="0" cellpadding="2" class="line">
  <tr style="font-weight:bolder"> 
    <td height="60" align="center" colspan="7">금고평균잔액표(일반회계)</td>
  </tr>
</table>
<table width="1000" border="0" cellspacing="0" cellpadding="2" class="basic">
  <tr> 
    <td colspan="3"><b>[<%=last_year%>년도] 월중평잔</b></td>
    <td colspan="4" align="right">(단위:원)</td>
  </tr>
</table>
<table width="1000" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td align="center" width="7%">월별</td>
    <td align="center" width="16%">공금예금</td>
    <td align="center" width="16%">정기예금</td>
    <td align="center" width="15%">환매채권</td>
    <td align="center" width="15%">MMDA</td>
    <td align="center" width="16%">저축성예금합계</td>
    <td align="center" width="15%">총합계</td>
  </tr>
  <% for (int i=0; lastAvgList != null && i<lastAvgList.size(); i++) { 
       CommonEntity rowData = (CommonEntity)lastAvgList.get(i);
  %>
  <tr> 
    <td align="center"><%=i+1%>월</td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("GONGGUM_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("JEONGGI_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("HWAN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("MMDA_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("SAVE_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("TOT_AMT"))%></td>
  </tr>
  <% } %>
</table>
<br>
<table width="1000" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td colspan="3"><b>[<%=request.getParameter("acc_year")%>년도] 월중평잔</b></td>
    <td colspan="4"> 
      <div align="right">(단위:원)</div>
    </td>
  </tr>
</table>
<table width="1000" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td align="center" width="7%">월별</td>
    <td align="center" width="16%">공금예금</td>
    <td align="center" width="16%">정기예금</td>
    <td align="center" width="15%">환매채권</td>
    <td align="center" width="15%">MMDA</td>
    <td align="center" width="16%">저축성예금합계</td>
    <td align="center" width="15%">총합계</td>
  </tr>
  <% for (int y=0; thisAvgList != null && y<thisAvgList.size(); y++) {
       CommonEntity data = (CommonEntity)thisAvgList.get(y);
  %>
  <tr> 
    <td align="center"><%=y+1%>월</td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("GONGGUM_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("JEONGGI_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("HWAN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("MMDA_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("SAVE_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("TOT_AMT"))%></td>
  </tr>
  <% } %>
  <% for (int z=thisListSize; z<12; z++) { %>
  <tr> 
    <td align="center"><%=z+1%>월</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <% } %>
</table>
<div class="noprint">
<p>&nbsp;</p>
<% if (!"Y".equals(request.getParameter("excelFlag")) ) { %>
<table width="1000" border="0">
  <tr>
    <td align="right">
    <img src="../img/btn_excel.gif" alt="엑셀" align="absmiddle" onclick="goExcel()" style="cursor:hand">
		<img src="../img/btn_print.gif" alt="인쇄" align="absmiddle" onclick="goPrint()" style="cursor:hand">
    </td>
  </tr>
</table>
<% } %>
</div>
</form>
</body>
</html>
