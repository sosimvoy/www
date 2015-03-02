<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070114.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-18
* 프로그램내용		: 일계/보고서 > 보고서조회 > 광역시세일계표
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
	CommonEntity reportRemark = (CommonEntity)request.getAttribute("page.mn07.reportRemark");
  
    String gubun = "";
    String semok_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=광역시세일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report td.re01 {font-size:5pt;}
.report td.re02 {font-size:7pt;}
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 7pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 7pt; color: #333333; text-decoration: underline}

    :root p {padding-left:250px; font-size:18px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html p {padding-left:250px; font-size:18px;} /*IE8 적용 */   
 * + html p {padding-left:250px; font-size:18px;} /*IE7 적용 */   
 *   html p {padding-left:250px; font-size:18px;} /*IE6 적용 */ 

 *   html .report td.re01 {font-size:8px;}
 *   html .report td.re02 {font-size:9px;}


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
<script language="javascript" src="../js/report.js"></script>
<script language="javascript">

function init(){
    <% if (reportList.size() > 0 && reportList != null) {%>
    cellMergeChk(document.all.dataList1, 2, 0);
    cellMergeChk(document.all.dataList2, 2, 0);
    cellMergeChk(document.all.dataList3, 2, 0);
    cellMergeChk(document.all.dataList4, 2, 0);
    cellMergeChk(document.all.dataList5, 2, 0);
    cellMergeChk(document.all.dataList6, 2, 0);
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
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070114.etax">
<input type="hidden" name="cmd" value="dayReport04">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="733" align="center" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="733"> 
            <%if(saveFile.equals("")){%>
			<table width="733" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<!-- <img src="../img/btn_pdf.gif" alt="PDF" onClick="goPDF()" style="cursor:hand" align="absmiddle"> -->
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
<!-- PAGE 1 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>년 울산 광역시세 일계표</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){ // 미마감인경우 (시분초)
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList1" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>세목</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>본청</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>울산차량등록사업소</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // 총계 및 세별 소계 예외처리
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "총계";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "지방세";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "세외수입";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "의존수입";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // 세목 소계 예외처리
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;경상적 세외수입 소계";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;임시적 세외수입 소계";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " 소계";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00000"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00000"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00000"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02240"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02240"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02240"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">세입징수관귀하</td>
        <td colspan="2" align="center" style=></td>
        <td colspan="2" align="right">경남은행 울산시청지점<br>울산광역시금고</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="45">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-1</td>
    </tr>
</table>
<!-- //PAGE1 END -->


<!-- PAGE 2 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>년 울산 광역시세 일계표</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList2" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>세목</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>중부소방서</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>남부소방서</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // 총계 및 세별 소계 예외처리
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "총계";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "지방세";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "세외수입";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "의존수입";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // 세목 소계 예외처리
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;경상적 세외수입 소계";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;임시적 세외수입 소계";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " 소계";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02130"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02130"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02130"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02140"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02140"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02140"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">세입징수관귀하</td>
        <td colspan="2" align="center" style="font-size:7pt;"></td>
        <td colspan="2" align="right">경남은행 울산시청지점<br>울산광역시금고</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="45">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-2</td>
    </tr>
</table>
<!-- //PAGE2 END -->


<!-- PAGE 3 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>년 울산 광역시세 일계표</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList3" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>세목</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>농수산물도매시장</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>문화예술회관</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // 총계 및 세별 소계 예외처리
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "총계";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "지방세";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "세외수입";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "의존수입";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // 세목 소계 예외처리
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;경상적 세외수입 소계";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;임시적 세외수입 소계";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " 소계";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02220"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02220"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02220"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02190"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02190"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02190"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">세입징수관귀하</td>
        <td colspan="2" align="center" style="font-size:7pt;"></td>
        <td colspan="2" align="right">경남은행 울산시청지점<br>울산광역시금고</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="45">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-3</td>
    </tr>
</table>
<!-- //PAGE3 END -->


<!-- PAGE 4 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
       <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>년 울산 광역시세 일계표</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList4" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>세목</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>중구청</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>남구청</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // 총계 및 세별 소계 예외처리
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "총계";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "지방세";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "세외수입";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "의존수입";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // 세목 소계 예외처리
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;경상적 세외수입 소계";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;임시적 세외수입 소계";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " 소계";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00110"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00110"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00110"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00140"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00140"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00140"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">세입징수관귀하</td>
        <td colspan="2" align="center" style="font-size:7pt;"></td>
        <td colspan="2" align="right">경남은행 울산시청지점<br>울산광역시금고</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="50">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-4</td>
    </tr>
</table>
<!-- //PAGE4 END -->

<!-- PAGE 5 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>년 울산 광역시세 일계표</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList5" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>세목</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>동구청</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>북구청</b></div>
        </td>
    </tr>
    <tr height="20">  
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // 총계 및 세별 소계 예외처리
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "총계";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "지방세";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "세외수입";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "의존수입";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // 세목 소계 예외처리
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;경상적 세외수입 소계";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;임시적 세외수입 소계";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " 소계";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00170"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00170"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00170"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00200"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00200"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00200"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">세입징수관귀하</td>
        <td colspan="2" align="center" style="font-size:7pt;"></td>
        <td colspan="2" align="right">경남은행 울산시청지점<br>울산광역시금고</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="50">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-5</td>
    </tr>
</table>
<!-- //PAGE 5 END -->


<!-- PAGE 6 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>년 울산 광역시세 일계표</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList6" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>세목</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>울주군</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>합계</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">전일까지누계</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일수입</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">금일까지누계</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // 총계 및 세별 소계 예외처리
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "총계";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "지방세";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "세외수입";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "의존수입";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // 세목 소계 예외처리
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;경상적 세외수입 소계";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;임시적 세외수입 소계";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " 소계";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00710"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00710"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00710"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">세입징수관귀하</td>
        <td colspan="2" align="center" style="font-size:7pt;"><%=reportRemark.getString("M470_REMARK1")%><br><%=reportRemark.getString("M470_REMARK2")%><br><%=reportRemark.getString("M470_REMARK3")%></td>
        <td colspan="2" align="right">경남은행 울산시청지점<br>울산광역시금고</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="45">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-6</td>
    </tr>
</table>
<!-- //PAGE 6 END -->
</div>

<%}else{%>
<div align="center">
<table width="733" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
</div>
<%}%>

</form>
</body>
</html>