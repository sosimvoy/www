<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070139.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-13
* 프로그램내용		: 일계/보고서 > 보고서조회 > 지급명령서
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");
	List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");
    String saveFile = request.getParameter("saveFile");

    String today = TextHandler.getCurrentDate();
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=지급명령서_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
        response.setHeader("Content-Description", "JSP Generated Data");
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%if(saveFile.equals("1")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
<%}%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<%if(saveFile.equals("")){%>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<style>
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}
-->

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<%}%>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/tax_common.js"></script>
<script language="javascript" src="../js/rowspan.js"></script>
<script language="javascript" src="../js/report.js"></script>

<script language="javascript">
function init(){
    <% if (reportList.size() > 0 && reportList != null) {%>
    cellMergeChk(document.all.dataList, 1, 0);
    <%}%>
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 1.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true); // 출력하기 
}

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();" style="padding-left:150px">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070139.etax">
<input type="hidden" name="cmd" value="dayReport29">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">
<%
if (reportList.size() > 0 && reportList != null) { 
%>

<div id="box">
<table width="713" border="0" cellspacing="0" cellpadding="1">
  <tr height="30"> 
    <td colspan="4" style="font-size:12pt; padding-left:300px;"> 
      <b><u>지 급 증 명 서</u></b>
    </td>
    <td align="right" valign="middle"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우 (시분초)
            %>
            <table width="150" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="25">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="25">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%}%>
        </td>
  </tr>
  <tr> 
    <td colspan="5">
      <div align="center">(
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>년 <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>월
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>일분)</div>
    </td>
  </tr>
  <tr> 
    <td colspan="2">회계연도:<%=request.getParameter("acc_year")%>년<br>
    </td>
    <td colspan="3"> 
      <div align="right"><%=request.getParameter("acc_name")%></div>
    </td>
  </tr>
</table>
<table id="dataList" width="713" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr height="20"> 
    <%if(request.getParameter("acc_code").equals("") || request.getParameter("acc_code") == null){%>
    <td width="200"> 
      <div align="center">구분</div>
    </td>
    <%}%>
    <td width="150"> 
      <div align="center">지급증명번호</div>
    </td>
    <td width="143"> 
      <div align="center">채주성명</div>
    </td>
    <td width="100"> 
      <div align="center">건수</div>
    </td>
    <td width="120"> 
      <div align="center">금액</div>
    </td>
    <td width="100"> 
      <div align="center">비고</div>
    </td>
  </tr>
    <% 
      for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
    %>
  <tr height="15"> 
    <%if(request.getParameter("acc_code").equals("") || request.getParameter("acc_code") == null){%>
    <td width="150"> 
      <div align="left"><%=rowData.getString("M360_ACCNAME")%></div>
    </td>
    <%}%>
    <td width="150"> 
      <div align="center">
        <%if((!request.getParameter("acc_code").equals("") || request.getParameter("acc_code") != null) && rowData.getString("M030_ORDERNO").equals("")){
            out.println("소계");
        }else{
            out.println(rowData.getString("M030_ORDERNO"));
        }
        %>
      </div>
    </td>
    <td width="143"> 
      <div align="left"><%=rowData.getString("M030_ORDERNAME")%></div>
    </td>
    <td width="100"> 
      <div align="center"><%=rowData.getString("TOT_CNT")%></div>
    </td>
    <td width="170"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></div>
    </td>
    <td width="150">
      <div align="center">&nbsp;</div>
    </td>
  </tr> 
    <%}%>
</table>
<table width="713" border="0" cellspacing="0" cellpadding="4" class="basic">
  <tr height="25"> 
    <td width="150"></td>
    <td colspan="3"> 
      <div align="center">상기와 같이 본일 지급하였음.<br>
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>년 <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>월
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>일</div>
    </td>
    <td rowspan="2" width="80">
        <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
        <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
        <% } %>
    </td>
  </tr>
  <tr> 
    <td colspan="2" align="left">울산광역시 지출원 귀하 </td>
    <td colspan="2" align="right">울 산 광 역 시 금 고</td>
  </tr>
</table>
</div>

<div class="noprint">
<table width="713" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="713"> 
  <%if(saveFile.equals("")){%>
			<table width="713" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<img src="../img/btn_print.gif" alt="인쇄"  onClick="goPrint();" style="cursor:hand" align="absmiddle">		  
					</td>
				</tr>
			</table>
   <%}%>
		</td>
	</tr>
</table>
</div>

<%}else{%>
<table width="710" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>
</form>
</body>
</html>