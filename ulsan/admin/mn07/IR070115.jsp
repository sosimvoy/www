<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070115.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-13
* 프로그램내용		: 일계/보고서 > 보고서조회 > 세외수입일계표
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
    //out.println("reportList::"+reportList);
  
    String part_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    String acc_date = TextHandler.replace(request.getParameter("acc_date"),"-","");
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=세외수입일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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

    :root p {font-size:15pt; padding-left:285px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html p {font-size:15pt; padding-left:285px;} /*IE8 적용 */   
 * + html p {font-size:15pt; padding-left:285px;} /*IE7 적용 */   
 *   html p {font-size:15pt; padding-left:285px;} /*IE6 적용 */ 

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
<form name="sForm" method="post" action="IR070115.etax">
<input type="hidden" name="cmd" value="dayReport05">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<%
if (reportList.size() > 0 && reportList != null) { 
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
    <tr height="120"> 
        <td colspan="4"> 
            <p><u><b><%=request.getParameter("acc_year")%>년 세외수입 일계표 (본청,구,군청,사업소) </b><u></p>
        </td>
        <td colspan="3" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우
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
</table>
<table width="975" border="0" cellspacing="2" cellpadding="1" class="basic">
    <tr height="40"> 
        <td width="475">수납일 : <%=request.getParameter("acc_date")%></td>
        <td width="500"> 
            <div align="right">(단위:원)</div>
        </td>
    </tr>
</table>
<table id="dataList" width="975" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
    <tr height="25"> 
        <td> 
            <div align="center"><b>기관명</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>전일까지누계</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>금일수입</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>환부</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>환부누계</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>과목정정액</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>금일까지누계</b></div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);

        if(rowData.getString("M350_PARTNAME").equals("")){
            part_name = "<center>합 계</center>";
        }else{
            part_name = rowData.getString("M350_PARTNAME");
        }
    %>
    <tr height="20"> 
        <td> 
            <div align="center"><%=part_name%></div>
        </td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("HWANBUTOT"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT"))%></div></td>
    </tr>
    <%}%>
</table>
<br>
<table width="975" border="0" cellspacing="2" cellpadding="1" class="basic">
    <tr height="30"> 
        <td colspan="6"> 
            <span style="width:435px"></span>이와 같이 보고함.
        </td>
        <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인) %>
        <td rowspan="4" width="75">
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="70" height="70" align="right">
        </td>
        <% }%>
    </tr>
    <tr> 
        <td colspan="6"> 
            <span style="width:437px"></span><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy년MM월dd일")%>
        </td>
    </tr>
    <tr> 
        <td colspan="6"> 
            <div align="right">경남은행 울산시청지점</div>
        </td>
    </tr>
    <tr> 
        <td colspan="6"> 
            <div align="right">울&nbsp; 산&nbsp; 광&nbsp; 역&nbsp;&nbsp;시&nbsp; 금&nbsp; 고</div>
        </td>
    </tr>
</table>
</div>

<%}else{%>
<table width="975" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>