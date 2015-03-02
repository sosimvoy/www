<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070116.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-13
* 프로그램내용		: 일계/보고서 > 보고서조회 > 차량등록사업소일계표
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
    String semok_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String acc_date = TextHandler.replace(request.getParameter("acc_date"),"-","");
    String today = TextHandler.getCurrentDate();
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=차량등록사업소일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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

    :root p {font-size:15pt; padding-left:350px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html p {font-size:15pt; padding-left:350px;} /*IE8 적용 */   
 * + html p {font-size:15pt; padding-left:350px;} /*IE7 적용 */   
 *   html p {font-size:15pt; padding-left:350px;} /*IE6 적용 */ 

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
		factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 1.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true) // 출력하기 
}

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070116.etax">
<input type="hidden" name="cmd" value="dayReport06">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="985" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="985"> 
            <%if(saveFile.equals("")){%>
			<table width="975" border="0" cellspacing="0" cellpadding="0" class="btn">
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

<%
if (reportList.size() > 0 && reportList != null) { 
%>

<div id="box" align="center">
<table width="975" border="0" cellspacing="0" cellpadding="1">
    <tr height="100"> 
        <td colspan="5"> 
            <p><u><b><%=request.getParameter("acc_year")%>년 차량등록사업소 일계표</b></u></p>
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
    <tr> 
        <td colspan="4" width="400">수납일자 : <%=request.getParameter("acc_date")%></td>
        <td colspan="4" width="575"> 
            <div align="right">(단위:원)</div>
        </td>
    </tr>
</table>
<table id="dataList" width="975" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
    <tr height="24"> 
        <td width="100"> 
            <div align="center"><b>구분</b></div>
        </td>
        <td> 
            <div align="center"><b>회계명</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>전일누계</b></div>
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
            <div align="center"><b>정정</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>금일누계</b></div>
        </td>
    </tr>
     <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        semok_name = rowData.getString("M370_SEMOKNAME");

        if(rowData.getString("M390_PARTCODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")){    // 합계
            part_name = "<b>합 계</b>";
        }else if(rowData.getString("M390_PARTCODE").equals("") && !rowData.getString("M390_SEMOKCODE").equals("")){ // 소계
            part_name = "<b>계</b>";

            if(rowData.getString("M390_SEMOKCODE").equals("1110200")){
                semok_name = "등록세";
            }else if(rowData.getString("M390_SEMOKCODE").equals("1120500")){
                semok_name = "지방교육세";
            }else if(rowData.getString("M390_SEMOKCODE").equals("1199900")){
                semok_name = "농어촌특별세";
            }
        }else{
            part_name = rowData.getString("M350_PARTNAME");
        }
    %>
    <tr height="24"> 
        <td> 
            <div align="center"><b><%=part_name%></b></div>
        </td>
        <td><%=semok_name%></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("HWANBUTOT"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="975" border="0" cellspacing="2" cellpadding="1" class="basic">
    <tr> 
        <td colspan="7"> 
             <span style="width:445px"></span>이와 같이 보고함.
        </td>
        <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인) %>
        <td rowspan="4" width="80">
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
        </td>
        <% }%>
    </tr>
    <tr> 
        <td colspan="7"> 
             <span style="width:445px"></span><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy년MM월dd일")%>
        </td>
    </tr>
    <tr> 
        <td colspan="7"> 
            <div align="right">경남은행 울산시청지점</div>
        </td>
    </tr>
    <tr> 
        <td colspan="7"> 
            <div align="right">울 산 광 역 시 금 고</div>
        </td>
    </tr>
</table>
</div>

<%}else{%>
<table width="985" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>