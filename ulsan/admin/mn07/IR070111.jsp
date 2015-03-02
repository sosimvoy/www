<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070111.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-17
* 프로그램내용		: 일계/보고서 > 보고서조회 > 세입금일계표
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
    // 직인 
	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");

	List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");

    String gubun = "";
    String semok_name = "";
    String saveFile = request.getParameter("saveFile");
  
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=세입금일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report {font-family: "arial","굴림", "Helvetica", "sans-serif";}
.report td.re01 {font-size:7pt;}
.report td.re02 {font-size:6pt;}
.report td.re03 {font-size:5pt;}

    :root u {font-size:14pt; padding-left:100px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html u {font-size:14pt; padding-left:100px;} /*IE8 적용 */   
 * + html u {font-size:14pt; padding-left:100px;} /*IE7 적용 */   
 *   html u {font-size:14pt; padding-left:100px;} /*IE6 적용 */ 

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<%}%>

<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript" src="../js/tax_common.js"></script>	
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
    form.action = "IR070111.etax";
    form.target = "_self";
    form.submit();
}
    
function goPDF(){
    var form =  document.forms[0];
    form.action = "IR070151.etax";
    form.target = "pdfFrame";
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 0.5; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true); // 출력하기 
}
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070111.etax">
<input type="hidden" name="cmd" value="dayReport01">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="733" border="0" align="center" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="733"> 
            <%if(saveFile.equals("")){%>
			<table width="733" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<img src="../img/btn_pdf.gif" alt="PDF" onClick="goPDF()" style="cursor:hand" align="absmiddle"> 
						<img src="../img/btn_print.gif" alt="인쇄"  onClick="goPrint();" style="cursor:hand" align="absmiddle">		  
					</td>
				</tr>
			</table>
            <%}%>
		</td>
	</tr>
</table>
</div>

<%
if (reportList.size() > 0 && reportList != null) { 
%>

<div id="box" align="center">
<table width="733" border="0" cellspacing="0" cellpadding="1" class="report">
    <tr height="30"> 
        <td>&nbsp;</td>
        <td colspan="5" height="15"> 
            <b><u>울산광역시세 세입금 일계표</u></b>
        </td>
        <td colspan="4" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인 경우
            %>
            <table width="150" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="25">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="25">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
    <tr height="20">
        <td width="133" class="re01">회계년 : <%=request.getParameter("acc_year")%>년도</td>
        <td style="padding-left:190px;" colspan="5" class="re01">(<%=request.getParameter("acc_date")%>)</td>
    </tr> 
		<tr height="20">
        <td colspan="2" class="re01">소관청 : <%=request.getParameter("part_name")%></td>
        <td colspan="8" class="re01"><div align="right">경남은행 울산광역시금고</div></td>
    </tr>
</table>

<table id="dataList" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="6"> 
        <td width="40" class="re02"> 
            <div align="center">구분</div>
        </td>
        <td width="143" class="re02"> 
            <div align="center">세목명</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">환부</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">환부누계</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">정정</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">금일누계</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);

        if(rowData.getString("GUBUN").equals("")){
            gubun = "합 계"; 
        }else{
            gubun = rowData.getString("GUBUN"); 
        }

        if(rowData.getString("M370_SEMOKNAME").equals("")){
            semok_name = "&nbsp;&nbsp;소 계"; 
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME"); 
        }
         if(rowData.getString("GUBUN").equals("")){
            gubun = "합 계"; 
            semok_name = ""; 
        }else{
            gubun = rowData.getString("GUBUN"); 
        }

    %>
    <tr height="6">
        <td class="re02"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re03"><%=semok_name%></td>
        <td class="re03"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIP"))%></div>
        </td>
        <td class="re03">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPGWAONAP"))%></div>
        </td>
        <td class="re03"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPGWAONAPTOT"))%></div>
        </td>
        <td class="re03">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPJEONGJEONG"))%></div>
        </td>
        <td class="re03"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_TOT"))%></div>
        </td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr height="60">
        <td colspan="2"> 
            <p>&nbsp;세 입 징 수 관 귀 하</p>
        </td>
        <td colspan="5"> 
            <div align="right">경남은행 울산시청지점<br>울 산 광 역 시 시 금 고</div>
        </td>
        <td rowspan="2" width="80">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60" height="60" align="right">
            <% } %>
        </td>
    </tr>
</table>
</div>

<%}else{%>
<table width="733" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>
</form>

<form name="pForm" method="post" action="IR070151.etax">
<iframe name="pdfFrame" width="0" height="0"></iframe>
</form>
</body>
</html>