<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070113.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-18
* 프로그램내용		: 일계/보고서 > 보고서조회 > 세입세출일계표(회계별)
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
    Integer totalCount = (Integer)request.getAttribute("page.mn07.totalCount");
    
    String cpage = request.getParameter("cpage");	// 현재 페이지 번호 받기
	if( "".equals(cpage) ) {
		cpage = "1";
	}

    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=세입세출일계표(회계별)_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
        response.setHeader("Content-Description", "JSP Generated Data");
    }
%>

<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<%if(saveFile.equals("")){%>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<%}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%if(saveFile.equals("1")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">

<%}%>

<style>
<!--
.report {font-family: "arial","굴림", "Helvetica", "sans-serif";}
.report td.re01 {font-size:10px;}
.report td.re02 {font-size:8px;}
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}
-->

    :root p {font-size:20pt; padding-left:340px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html p {font-size:20pt; padding-left:340px;} /*IE8 적용 */   
 * + html p {font-size:20pt; padding-left:340px;} /*IE7 적용 */   
 *   html p {font-size:20pt; padding-left:340px;} /*IE6 적용 */ 

@media print {
  .noprint {
	  display:none;
	}
}
</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">

function init(){
}

function PAGE(cpage) {
    if(cpage != null) {
    document.sForm.cpage.value=cpage;
  } else {
    document.sForm.cpage.value="";
  }
  document.sForm.target = "_self";
  eSubmit(document.sForm);
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.action = "IR070113.etax";
    form.target = "_self";
    form.submit();
}

function goPDF(){
    var form =  document.forms[0];
    form.action = "IR070153.etax";
    form.target = "pdfFrame";
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
<form name="sForm"   method="post"      action="IR070113.etax">
<input type="hidden" name="cmd"         value="dayReport03">
<%if(saveFile.equals("")){%>
<input type="hidden" name="acc_year"    value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date"    value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type"    value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code"   value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code"    value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile"    value="<%=saveFile%>">    
<input type="hidden" name="cpage"       value="">
<%}%>

<div class="noprint">
<table width="960" align="center" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
	<tr> 
		<td width="960"> 
            <%if(saveFile.equals("")){%>
			<table width="960" border="0" cellspacing="0" cellpadding="0" class="btn">
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
<% 
for (int i=0; i < reportList.size(); i++) {
    CommonEntity rowData = (CommonEntity)reportList.get(i);
%>

<table width="960" border="0" cellspacing="0" cellpadding="1">
    <tr height="250"> 
        <td colspan="10"> 
            <p><u><b>세 입 세 출 일 계 표  (<%=request.getParameter("acc_year")%>)</b></u></p>
        </td>
        <td colspan="3" width="200" align="right" valign="middle"> 
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
</table>
<table width="960" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr> 
        <td width="870" colspan="7"><!--수납일: <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%>--></td>
        <td width="80" colspan="6" align="right">(단위:원)</td>
    </tr>
</table>
<table id="dataList" width="960" border="1" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#000000" class="report">
    <tr height="50"> 
        <td rowspan="2" class="re01" width="100"> 
            <div align="center">회계명</div>
        </td>
        <td colspan="5" class="re01" width="350"> 
            <div align="center">세입</div>
        </td>
        <td rowspan="2" class="re01" width="80"> 
            <div align="center">자금<br>배정액</div>
        </td>
        <td colspan="5" class="re01" width="350"> 
            <div align="center">세출</div>
        </td>
        <td rowspan="2" class="re01" width="80"> 
            <div align="center">잔액</div>
        </td>
    </tr>
    <tr height="50"> 
        <td class="re01" width="70"> 
            <div align="center">전일누계</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">금일수입</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">과오납반납</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">과목<br>정정액등</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">총계</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">전일누계</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">당일지급</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">반납액</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">과목<br>정정액등</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">총계</div>
        </td>
    </tr>

    <tr height="50" class="re02"> 
        <td width="100" class="re01" align="center"><%=rowData.getString("ACCNAME")%></td>

        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPTOT"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTBAEJUNG"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONILTOT"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHUL"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULBANNAP"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONGJEONG"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULTOT"))%></div></td>

        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJAN"))%></div></td>
    </tr>
    </tr>
</table>
<table width="960" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
    <tr height="120">
        <td colspan="13"></td>
    </tr>
    <tr height="30">
        <td colspan="12"> 
             <span style="width:440"></span>위 와 같 이 보 고 함.
        </td>
        <td rowspan="3" colspan="3">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
            <% } %>
        </td>
    </tr>
    <tr height="30">
        <td colspan="12"> 
             <span style="width:440"></span><%=acc_date.substring(0,4)%> 년 <%=acc_date.substring(4,6)%> 월 <%=acc_date.substring(6,8)%> 일</div>
        </td>
    </tr>
    <tr height="30">
        <td width="500" colspan="7"> 
            <div align="left">울관광역시 세입징수관 귀하</div>
        </td>
        <td width="450" colspan="5"> 
            <div align="right">
            경남은행 울산시청지점<br>울 산 광 역 시 금 고</div>
        </td>
    </tr>
    <tr height="125">
        <td colspan="13"></td>
    </tr>
</table>

<%}%>
</div>

<div class="noprint">
<%// 페이지 %>
<%if(saveFile.equals("")){%>
<table width="960" align="center">
  <tr>
    <td align="center">
    <Br>
    <etax:pagelink totalCnt='<%=totalCount.intValue()%>' page='<%=cpage%>'	pageCnt='5' blockCnt='10'  />
    </td>
  </tr>
</table>
<%}%>
</div>

<%}else{%>
<table width="960" border="0" cellpadding="0" cellspacing="0" align="center">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>

</form>
<form name="pForm" method="post" action="IR070153.etax">
<iframe name="pdfFrame" width="0" height="0"></iframe>
</form>
</body>
</html>