<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061910.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 일일자금현황조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%
  List<CommonEntity> dailyMoneyList = (List<CommonEntity>)request.getAttribute("page.mn06.dailyMoneyList");
  String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
  if (SucMsg == null) {
    SucMsg = "";
  }
  
  int listSize = 0;
  if (dailyMoneyList != null) {
    listSize = dailyMoneyList.size();
  }
  String accName = "";

  if(request.getParameter("flag").equals("Y")){
    response.setContentType("application/vnd.ms-excel;charset=euc-kr");
    response.setHeader("Content-Disposition", "inline; filename=일일자금현황조회_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
    response.setHeader("Content-Description", "JSP Generated Data");
  }	 
%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템t</title>
<%if(request.getParameter("flag").equals("Y")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
<%} else {%>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<% } %>
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.big {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 20px; color: #333333}
.bottom {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 16px; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}

@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">

  function init(){
    <% if (listSize > 0 ) {%>
    cellMergeChk(document.all.dataList, 1, 0);
    <%}%>
  }

  function goExcel() {
    var form = document.sForm;
    form.flag.value = "Y";
    eSubmit(form);
  }

  function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 2.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 2.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true); // 출력하기 
	}
</script>
</head>

<body bgcolor="#FFFFFF" topmargin="10" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR062011.etax">
<input type="hidden" name="cmd" value="dailyMoneyList">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="fis_year" value="<%=request.getParameter("fis_year")%>">
<input type="hidden" name="flag" value="">
<br>
<table width="680" border="0" cellspacing="0" cellpadding="1">
  <tr> 
    <td class="big" colspan="5" align="center"> 
      <b><%=request.getParameter("fis_year")%> 울산광역시 일일 자금 현황</b>
    </td>
  </tr>
</table>
<table width="680" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr>
    <td width="480" align="left"><%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy-MM-dd")%><br>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td align="right"> 
      단위(원)
    </td>
  </tr>
</table>
<table width="700" border="0" cellspacing="2" cellpadding="0">
  <tr>
    <td>
      <table id="dataList" width="680" border="1" cellspacing="0" cellpadding="1" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
        <tr> 
          <td colspan="2" align="center" width="34%"> 
            <b>구분</b>
          </td>
          <td align="center"> 
            <b>전일잔액</b>
          </td>
          <td align="center"> 
            <b>당일잔액</b>
          </td>
          <td align="center"> 
            <b>당일증감</b>
          </td>
        </tr>
        <% for (int i=0; dailyMoneyList != null && i < dailyMoneyList.size(); i++) { 
             CommonEntity rowData = (CommonEntity)dailyMoneyList.get(i);
             accName = rowData.getString("M360_ACCNAME");
             if ("".equals(TextHandler.replace(accName, " ", "")) 
               && "".equals(TextHandler.replace(rowData.getString("GUBUN"), " ", "")) ) {
               accName = "합 계";
             } else if ("".equals(TextHandler.replace(accName, " ", "")) 
               && !"".equals(TextHandler.replace(rowData.getString("GUBUN"), " ", "")) ) {
               accName = "소 계";
             }

        %>
        <tr> 
          <td width="19"> 
            <%=rowData.getString("GUBUN")%>
          </td>
          <td> 
            <%=accName%>
          </td>
          <td align="right"> 
            <%=TextHandler.formatNumber(rowData.getString("YESTERDAY_AMT"))%>
          </td>
          <td align="right"> 
            <%=TextHandler.formatNumber(rowData.getString("TODAY_AMT"))%>
          </td>
          <td align="right"> 
            <%=TextHandler.formatNumber(rowData.getString("YT_AMT"))%>
          </td>
        </tr>
        <% } %>
      </table>
    </td>
  </tr>
</table>
<br>
<table width="700" border="0" cellspacing="0" cellpadding="2" class="basic">
  <tr> 
    <td width="680" class="bottom" align="right" colspan="5"> 
      <b>경남은행울산시청지점시금고</b>
    </td>
  </tr>
</table>
<div class="noprint">
<p>&nbsp;</p>
<% if (!request.getParameter("flag").equals("Y")) { %>
<table width="700" border="0" cellspacing="0" cellpadding="4" class="basic">
  <tr> 
    <td width="690" class="bottom" align="right"> 
      <img src="../img/btn_excel.gif" alt="엑셀" style="cursor:hand" onClick="goExcel()" align="absmiddle">
      <img src="../img/btn_print.gif" alt="인쇄" style="cursor:hand" onClick="goPrint()" align="absmiddle">
      <img src="../img/btn_close.gif" alt="닫기" style="cursor:hand" onClick="self.close()" align="absmiddle">
    </td>
  </tr>
</table>
<% } %>
</div>
</body>
</html>
<% if (!"".equals(SucMsg)) { %>
<script>
alert("<%=SucMsg%>");
self.close();
</script>
<% } %>
