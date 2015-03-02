<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070118.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-13
* 프로그램내용		: 일계/보고서 > 보고서조회 > 광역교통시설부담금세입일계표
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	CommonEntity endState   = (CommonEntity)request.getAttribute("page.mn07.endState");
	CommonEntity reportData = (CommonEntity)request.getAttribute("page.mn07.reportData");
    //out.println("reportList::"+reportList);
  
    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=광역교통시설부담금세입일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report td.re02 {font-size:7pt;}

    :root u {font-size:14pt; padding-left:315px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html u {font-size:14pt; padding-left:315px;} /*IE8 적용 */   
 * + html u {font-size:14pt; padding-left:315px;} /*IE7 적용 */   
 *   html u {font-size:14pt; padding-left:315px;} /*IE6 적용 */ 

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
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
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
<form name="sForm" method="post" action="IR070118.etax">
<input type="hidden" name="cmd" value="dayReport08">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<%
if (reportData != null) { 
%>

<div class="noprint">
<table width="975" align="center" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="975"> 
            <%if(saveFile.equals("")){%>
			<table width="975" border="0" cellspacing="0" cellpadding="0" class="btn">
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

<div id="box" align="center">

<table width="975" border="0" cellspacing="0" cellpadding="1">
    <tr height="70"> 
        <td colspan="7"> 
            <u><b><%=request.getParameter("acc_year")%>년 광역교통시설부담금 세입일계표</b></u>
        </td>
        <td colspan="4" width="200" align="right" valign="top"> 
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
            <%
            }
            %>
        </td>
    </tr>
    <tr height="25">
        <td colspan="7" align="left">수납일 : <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%></td>
        <td colspan="4" align="right">(단위:원)</td>
    </tr>
</table>
<table width="975" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td class="re01" colspan="2"> 
            <div align="center"><b>구 분(99) </b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>광역교통시설부담금 </b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>이자수입 </b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>이월금 </b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>내부전입금 </b></div>
        </td>
        <td class="re01" width="80"> 
            <div align="center"><b></b></div>
        </td>
        <td class="re01" width="80"> 
            <div align="center"><b></b></div>
        </td>
        <td class="re01" width="80"> 
            <div align="center"><b></b></div>
        </td>
        <td class="re01" width="80"> 
            <div align="center"><b></b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>합 계 </b></div>
        </td>
    </tr>
    <tr height="15"> 
        <td class="re01" rowspan="6" width="15"> 
            <p align="center">본<br>청</p>
        </td>
        <td class="re01" width="115">전일까지누계 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">금일수입 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">과오납 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_05"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">과오납 누계 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_05"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">과목 갱정 등 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_04"))%></div></td>
        <td class="re02">&nbsp;</td>                                                         
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">금일까지누계 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    
    <% for (int i=0; i < 3; i++) {%>
    <tr height="10"> 
        <td rowspan="6">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <%}%>

    <tr height="15"> 
        <td class="re01" rowspan="6" width="22"> 
            <p align="center">합<br>계</p>
        </td>
        <td class="re01" width="78">전일까지누계 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">금일수입 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">과오납 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_05"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">과오납 누계 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_05"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">과목 갱정 등 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_04"))%></div></td>
        <td class="re02">&nbsp;</td>                                                         
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">금일까지누계 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_99"))%></div></td>
    </tr>
</table>
<table width="975" border="0" cellspacing="0" cellpadding="4" class="basic">
    <tr height="25"> 
        <td colspan="10"> 
            <span style="width:440px"></span><%=acc_date.substring(0,4)%>년 <%=acc_date.substring(4,6)%>월 <%=acc_date.substring(6,8)%>일
        </td>
        <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인) %>
        <td rowspan="2" width="60">
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60" height="60" align="right">
        </td>
        <% }%>
    </tr>
    <tr>
        <td colspan="2" width="300">세 입 징 수 관 귀하
        </td>
        <td colspan="8" width="675"> 
            <div align="right">경남은행 울산시청지점<br>울&nbsp; 산&nbsp; 광&nbsp; 역&nbsp;&nbsp;시&nbsp; 금&nbsp; 고</div>
        </td>
    </tr>
</table>
</div>

<%}else{%>
<table width="975" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>