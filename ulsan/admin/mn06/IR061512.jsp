<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061512.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금운용현황 조회(엑셀)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  if (!"P".equals(request.getParameter("flag")) ) {
  String charMarkForExcel = "";
  response.setContentType("application/vnd.ms-excel;charset=euc-kr");
  response.setHeader("Content-Disposition", "inline;filename=자금운용현황조회_"+new java.sql.Date(System.currentTimeMillis())+".xls");  
  response.setHeader("Content-Description", "JSP Generated Data");
  charMarkForExcel = "`"; // 문자를 그대로 표시하기 위해
  }
  List<CommonEntity> moneyRunExcelList  = (List<CommonEntity>)request.getAttribute("page.mn06.moneyRunExcelList");

	int moneyRunExcelListSize = 0;
	if (moneyRunExcelList != null ) {
		moneyRunExcelListSize = moneyRunExcelList.size();
	}

  String due_day = "";
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<meta http-equiv="Content-Type" content="text/html;charset=euc-kr">
    <% if ("P".equals(request.getParameter("flag")) ) { %>
		<link href="../css/class.css" rel="stylesheet" type="text/css" />
		<% } else if (!"P".equals(request.getParameter("flag")) ) { %>
    <meta name=ProgId content=Excel.Sheet>
    <style>
    table { table-layout:fixed; }
    tr,td { border:0.5pt solid silver; }
    </style>
		<% } %>
		<script language="javascript" src="../js/base.js"></script>
		<script language="javascript" src="../js/tax_common.js"></script>
		<script language="javascript">
		  function init() {
  	    typeInitialize();
				goPrint();
      }

      function goPrint() {
		    factory.printing.header = ""; // Header에 들어갈 문장  
		    factory.printing.footer = ""; // Footer에 들어갈 문장  
		    factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
		    factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		    factory.printing.topMargin = 1.0; // 위 여백 사이즈  
		    factory.printing.rightMargin = 0.5; // 오른쪽 여백 사이즈  
		    factory.printing.bottomMargin = 1.0; // 아래 여백 사이즈  
		    factory.printing.Print(true); // 출력하기 
	    }
		</script>
  </head>
  <body topmargin="10" leftmargin="10">
  <object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
    <table width="993" border="0" cellspacing="0" cellpadding="0">
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
				  <div id="pDiv">
				  <% if ("A".equals(request.getParameter("src_gubun")) || "B".equals(request.getParameter("src_gubun"))
					    || "C".equals(request.getParameter("src_gubun")) ) { %>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <% if ("A".equals(request.getParameter("src_gubun")) ) { %>
					  <tr>
						  <td colspan="12"> 잔액명세 </td>
						</tr>
            <% } else if ("B".equals(request.getParameter("src_gubun")) ) { %>
            <tr>
						  <td colspan="12"> 기간중 해지 </td>
						</tr>
            <% } else if ("C".equals(request.getParameter("src_gubun")) ) { %>
            <tr>
						  <td colspan="12"> 기간중 신규 </td>
						</tr>
            <% } %>
            <tr> 
						  <th>회계명</th>
              <th>신규일</th>
              <th>예금종류</th>
							<th>계좌번호</th>
							<th>좌수번호</th>
              <% if ("B".equals(request.getParameter("src_gubun"))) { %>
							<th>해지금액</th>
              <% } else { %>
              <th>신규금액</th>
              <% } %>
							<th>만기일</th>
							<th>예치일수</th>
							<th>이율</th>
							<th>해지일</th>
							<th>해지이자</th>
							<th>현재잔액</th>
            </tr>
						<% if (moneyRunExcelListSize > 0 && moneyRunExcelList != null) { 
							   for (int i=0; i < moneyRunExcelListSize; i++) {
									 CommonEntity rowData = (CommonEntity)moneyRunExcelList.get(i);
                   if ("G1".equals(rowData.getString("DEPOSIT_TYPE")) ) { 
                     due_day = "개월";
                   } else if ("".equals(rowData.getString("DEPOSIT_DATE")) ) {
                     due_day = "";
                   } else {
                     due_day = "일";
                   }
						%>
            <tr>
						  <td style='mso-number-format:"\@";'>&nbsp;<%=rowData.getString("ACC_NAME")%></td>
              <td style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatDate(rowData.getString("SINKYU_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td>&nbsp;<%=rowData.getString("TYPE_NAME")%></td>
							<td style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("ACCOUNTNO"))%></td>
              <td style='mso-number-format:"\@";'>&nbsp;<%=rowData.getString("JWASUNO")%></td>
							<td align="right"><%=TextHandler.formatNumber(rowData.getString("DEPOSIT_AMT"))%></td>
							<td style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatDate(rowData.getString("MANGI_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td align="right" style='mso-number-format:"\@";'>&nbsp;<%=rowData.getString("DEPOSIT_DATE")%><%=due_day%></td>
							<td align="right" style='mso-number-format:"\@";'>&nbsp;<%=rowData.getString("RATE")%></td>
							<td style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatDate(rowData.getString("CANCEL_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
							<td align="right"><%=TextHandler.formatNumber(rowData.getString("IJA"))%></td>
              <% if ("A".equals(request.getParameter("src_gubun"))) { %>
							<td align="right"><%=TextHandler.formatNumber(rowData.getString("JAN_AMT"))%></td>
              <% } else { %>
              <td align="right" >&nbsp;</td>
              <% } %>
            </tr>
						<%   }	%>
		        <% }else { %>
						<tr> 
              <td colspan="12">&nbsp;</td>
            </tr>
						<% } %>
          </table>
					<%  } else if ("D".equals(request.getParameter("src_gubun")) ) { %>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
					  <tr>
						  <td colspan="9"> 자금운용내역장 </td>
						</tr>
            <tr> 
              <th rowspan="2">거래일</th>
              <th rowspan="2">적요</th>
							<th rowspan="2">종별</th>
							<th colspan="2">지불</th>
							<th rowspan="2">종별</th>
							<th colspan="2">환입</th>
							<th rowspan="2">잔액</th>
            </tr>
						<tr>
						  <th>정기예금등</th>
							<th>계(A)</th>
							<th>정기예금등</th>
							<th>계(B)</th>
						</tr>
						<% if (moneyRunExcelListSize > 0 && moneyRunExcelList != null) { 
               long tot_janamt = 0L;
               long gye1 = 0L;
               long gye2 = 0L;
               long tot_amt = 0L;
							   for (int i=0; i < moneyRunExcelListSize; i++) {
									 CommonEntity rowData = (CommonEntity)moneyRunExcelList.get(i);
									 tot_janamt = rowData.getLong("YETAK_AMT") - rowData.getLong("INCHUL_AMT");
						%>
            <% if (i == 0) {
              gye1 = rowData.getLong("YETAK_AMT");
              gye2 = rowData.getLong("INCHUL_AMT");
              tot_amt =  tot_janamt;
            %>
            <tr>
              <td style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatDate(rowData.getString("ACC_DATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td>&nbsp;<%=rowData.getString("JUKYO")%></td>
							<td>&nbsp;<%=rowData.getString("YETAK_NAME")%></td>
              <td align="right"><%=TextHandler.formatNumber(rowData.getString("YETAK_AMT"))%></td>
							<td align="right"><%=TextHandler.formatNumber(rowData.getString("YETAK_AMT"))%></td>
							<td>&nbsp;<%=rowData.getString("INCHUL_NAME")%></td>
							<td align="right"><%=TextHandler.formatNumber(rowData.getString("INCHUL_AMT"))%></td>
							<td align="right"><%=TextHandler.formatNumber(rowData.getString("INCHUL_AMT"))%></td>
							<td align="right"><%=TextHandler.formatNumber(tot_janamt)%></td>
            </tr>
             <% } else { 
              gye1 = gye1+rowData.getLong("YETAK_AMT");
              gye2 = gye2+rowData.getLong("INCHUL_AMT");
              tot_amt = tot_amt + rowData.getLong("YETAK_AMT") - rowData.getLong("INCHUL_AMT");
            %>
            <tr>
              <td style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatDate(rowData.getString("M160_TRANSACTIONDATE"), "yyyyMMdd", "yyyy-MM-dd")%></td>
              <td>&nbsp;<%=rowData.getString("JUKYO")%></td>
							<td>&nbsp;<%=rowData.getString("YETAK_NAME")%></td>
              <td align="right"><%=TextHandler.formatNumber(rowData.getString("YETAK_AMT"))%></td>
							<td align="right"><%=TextHandler.formatNumber(gye1)%></td>
							<td>&nbsp;<%=rowData.getString("INCHUL_NAME")%></td>
							<td align="right"><%=TextHandler.formatNumber(rowData.getString("INCHUL_AMT"))%></td>
							<td align="right"><%=TextHandler.formatNumber(gye2)%></td>
							<td align="right"><%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
						<%  }   
              }	%>
		        <% }else { %>
						<tr> 
              <td colspan="9">&nbsp;</td>
            </tr>
						<% }
						}%>
          </table>
					</div>
        </td>
      </tr>			
    </table>
    <p>&nbsp;</p>
  </body>
</html>