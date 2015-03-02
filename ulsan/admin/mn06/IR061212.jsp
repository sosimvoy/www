<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061212.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금인출통지서조회 - 자금인출내역
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  List<CommonEntity> inchulDetailList = (List<CommonEntity>)request.getAttribute("page.mn06.inchulDetailList");
%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11pt; color: #333333}
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
		factory.printing.leftMargin = 2.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 2.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true) // 출력하기 
	}
</script>
</head>
<%
  if (inchulDetailList != null) {
%>
<body bgcolor="#FFFFFF" topmargin="20" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<table width="713" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="7" height="21">
      <table width="711" border="0" cellspacing="0" cellpadding="0" class="basic">
        <tr> 
          <td colspan="7" height="21"> 
            <table width="690" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="60" style="font-size:17pt"> 
                  <div align="center"><b><%=TextHandler.formatDate(request.getParameter("reg_date"), "yyyyMMdd", "yyyy")%> 일반회계 자금 인출 내역</b></div>
                </td>
              </tr>
            </table>
            <p>&nbsp;</p>
            <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic">
              <tr>
							  <td height="25"> 
                  <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%=TextHandler.formatDate(request.getParameter("reg_date"), "yyyyMMdd", "yyyy. MM. dd")%></div>
                </td>
              </tr>
            </table>
            <table width="690" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic" align="center">
              <tr height="35"> 
							  <td width="17%"> 
                  <div align="center">예탁종류</div>
                </td>
                <td width="18%"> 
                  <div align="center">인출액</div>
                </td>
                <td width="15%"> 
                  <div align="center">예탁일자</div>
                </td>
                <td width="12%"> 
                  <div align="center">예탁기간</div>
                </td>
								<td width="10%"> 
                  <div align="center">이율(%)</div>
                </td>
                <td width="16%"> 
                  <div align="center">이자(원)</div>
                </td>
                <td width="12%"> 
                  <div align="center">mmda해지</div>
                </td>
              </tr>
							<% String due_date = "";
	               for (int i=0; i<inchulDetailList.size(); i++) { 
	                 CommonEntity rowData = inchulDetailList.get(i);
									 String state = rowData.getString("M130_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " 개월";
									 } else if ("".equals(rowData.getString("M130_DEPOSITDATE")) ) {
                     due_date = "";
                   } else if ("G2".equals(state) || "G3".equals(state)){
										 due_date = rowData.getString("M130_DEPOSITDATE") + " 일";
									 }
	            %>
              <tr> 
							  <td height="6">
									<div align="center"><%=rowData.getString("DEPOSIT_NAME")%></div>
                </td>
                <td height="6"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M130_INCHULAMT"))%></div>
                </td>
                <td height="6"> 
                  <div align="center"><%=TextHandler.formatDate(rowData.getString("M130_SINKYUDATE"), "yyyyMMdd", "yyyy.MM.dd")%></div>
                </td>
                <td height="35"> 
                  <div align="right"><%=due_date%></div>
                </td>
                <td height="6"> 
                  <div align="right"><%=rowData.getString("M130_INTERESTRATE")%></div>
                </td>
                <td height="6"> 
                  <div align="right">
                  <% if ("0".equals(rowData.getString("M130_CANCELINTEREST")) || "".equals(rowData.getString("M130_CANCELINTEREST"))) { %>
                  &nbsp;
                  <% } else { %>
                  <%=TextHandler.formatNumber(rowData.getString("M130_CANCELINTEREST"))%>
                  <% } %>
                  </div>
                </td>
                <td height="6"> 
                  <div align="center">
                  <% if ("Y".equals(rowData.getString("M130_MMDA_CANCEL_YN")) ) { %>
                  해지
                  <% } %>
                  </div>
                </td>
              </tr>
							<% } %>
            </table>
          <p>&nbsp;</p></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<div class="noprint">
<div align="right">
<img src="../img/btn_print.gif" alt="인쇄" onClick="goPrint()" align="absmiddle" style="cursor:hand">
<img src="../img/btn_close.gif" alt="닫기" onClick="self.close()" style="cursor:hand"><span style="width:40"></span>
</div>
</div>
<p><span class="count"></span> </p>
</body>
<% } %>
</html>
