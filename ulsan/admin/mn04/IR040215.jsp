<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040215.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 세외수입 > 예산서 조회/수정/삭제(엑셀)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  String charMarkForExcel = "";
  response.setContentType("application/vnd.ms-excel;charset=euc-kr");
  response.setHeader("Content-Disposition", "inline;filename=예산서조회_"+new java.sql.Date(System.currentTimeMillis())+".xls");  
  response.setHeader("Content-Description", "JSP Generated Data");
  charMarkForExcel = "`"; // 문자를 그대로 표시하기 위해

  List<CommonEntity> moneyRunExcelList  = (List<CommonEntity>)request.getAttribute("page.mn04.m080ExcelList");

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
    <meta name=ProgId content=Excel.Sheet>
    <style>
    table { table-layout:fixed; }
    tr,td { border:0.5pt solid silver; }
    </style>
		<script language="javascript" src="../js/base.js"></script>
		<script language="javascript" src="../js/tax_common.js"></script>
		<script language="javascript">
		  function init() {
  	    typeInitialize();
				window.print();
      }
		</script>
  </head>
  <body topmargin="0" leftmargin="0">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="975"> 
				  <div id="pDiv">

					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr>
						  <td colspan="31"> 2010년도 세입현황 </td>
						</tr>
            <tr> 
              <th rowspan="2">소관부처</th>
              <th rowspan="2">실과별</th>
              <th rowspan="2">담당자</th>
              <th rowspan="2">내선번호</th>
							<th rowspan="2">과목</th>
							<th rowspan="2">사업명</th>
							<th>미수령액</th>
							<th colspan="11">예산액(천원)</th>
							<th colspan="15">수령액</th>
							<th rowspan="2">회계년도</th>
							<th rowspan="2">목</th>
							<th rowspan="2">세목</th>
							<th rowspan="2">등록일자(최초)</th>
            </tr>
						<tr>
						  <th>(예산액-수령액)</th>
							<th>합계</th>
							<th>당초</th>
							<th>1회</th>
							<th>2회</th>
							<th>3회</th>
							<th>4회</th>
							<th>5회</th>
							<th>6회</th>
							<th>7회</th>
							<th>8회</th>
							<th>9회</th>
              <th>계</th>
              <th>1월</th>
              <th>2월</th>
              <th>3월</th>
              <th>4월</th>
              <th>5월</th>
              <th>6월</th>
              <th>7월</th>
              <th>8월</th>
              <th>9월</th>
              <th>10월</th>
              <th>11월</th>
              <th>12월</th>
              <th>13월</th>
              <th>14월</th>
						</tr>
						<% if (moneyRunExcelListSize > 0 && moneyRunExcelList != null) { 
               long tot_janamt = 0L;
               long gye1 = 0L;
               long gye2 = 0L;
							   for (int i=0; i < moneyRunExcelListSize; i++) {
									 CommonEntity rowData = (CommonEntity)moneyRunExcelList.get(i);
						%>
            <tr>
              <td> <%=rowData.getString("SOGWANPART")%></td>
              <td> <%=rowData.getString("SILGWA")%></td>
              <td> <%=rowData.getString("USERNAME")%></td>
              <td> <%=rowData.getString("INTELNO")%></td>
              <td> <%=rowData.getString("GWAMOK")%></td>
              <td> <%=rowData.getString("BUSINESSNAME")%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("TOT_NOTAMT"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("TOT_CHUKYNG"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("DANGCHOAMT"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT1"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT2"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT3"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT4"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT5"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT6"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT7"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT8"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT9"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("TOT_MONTH"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_1"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_2"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_3"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_4"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_5"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_6"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_7"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_8"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_9"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_10"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_11"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_12"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_13"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_14"))%></td>
              <td> <%=rowData.getString("YEAR")%></td>
              <td> <%=rowData.getString("MOK")%></td>
              <td> <%=rowData.getString("SEMOKCODE")%></td>
              <td> <%=rowData.getString("WRITEDATE")%></td>
            </tr>
             <% }	%>
		        <% }else { %>
						<tr> 
              <td colspan="37">&nbsp;</td>
            </tr>
						<% } %>
          </table>
					</div>
        </td>
      </tr>			
    </table>
    <p>&nbsp;</p>
  </body>
</html>