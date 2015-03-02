<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070610.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-14
* 프로그램내용		: 일계/보고서 > 감사자료조회
* 비고              : 입력한 기간 내 일계&비고 내역의 일자별 합계금액
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
String excelFlag = request.getParameter("excelFlag");
String border = "0";
%>

<%if(excelFlag.equals("")){%>
<%@include file="../menu/mn07.jsp" %>
<%}%>

<%
    if(excelFlag.equals("Y")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=감사자료_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
        response.setHeader("Content-Description", "JSP Generated Data");
        border = "1";
    }

	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");

    String acc_sdate = request.getParameter("acc_sdate");
    String acc_edate = request.getParameter("acc_edate");
    
    if(acc_sdate.equals("") || acc_sdate == null){
        acc_sdate =  TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
    }
    if(acc_edate.equals("") || acc_edate == null){
        acc_edate =  TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
    }
    
	List<CommonEntity> dataList = (List<CommonEntity>)request.getAttribute("page.mn07.dataList");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%if(excelFlag.equals("Y")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
<%}%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<%if(excelFlag.equals("")){%>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<%}%>
<style>
<!--
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
<script language="javascript">
function init() {
    typeInitialize();
}

function goSearch(){
    var form =  document.forms[0];
    
	/*
	form.excelFlag.value = "";
    form.submit();
	*/

	pageurl = "/admin/oz51/IR070610.etax";
	funcOpenWindow(form,'post','NeWreport',pageurl , 10,10, 1000, 1000, 0, 0, 0, 0, 1);
}

function goExcel(){
    var form =  document.forms[0];

    <% if (dataList == null || dataList.size() == 0) {%>
        alert("저장할 자료가 없습니다.");
        return;
    <%}%>

    form.excelFlag.value = "Y";
    eSubmit(form);
}
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
<form name="sForm" method="post" action="IR070610.etax">
<%if(excelFlag.equals("")){%>
<input type="hidden" name="cmd" value="auditReportList">
<input type="hidden" name="excelFlag" value="">
<%}%>
<table width="993" border="0" cellspacing="0" cellpadding="0">
    <%if(excelFlag.equals("")){%>
	<tr> 
		<td width="18">&nbsp;</td><!-- 이미지 변경 -->
		<td width="975" height="40"><img src="../img/title7_13.gif"></td>
	</tr>
	<tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40"> 
			<table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
				<tr> 
					<td><img src="../img/box_top.gif" width="964" height="11"></td>
				</tr>
				<tr> 
					<td> 
						<table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr> 
								<td width="860">
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									회계연도
									<select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
									<% for (int i = Integer.parseInt(this_year); i >= 2010; i--) { %>
										<option value="<%=i%>"><%=i%></option>
                  <% } %>
									</select>

									<span style="width:80px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									회계일자 
                                    <input type="text" class="box3" size="8" name="acc_sdate" value="<%=acc_sdate%>" userType="date">
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_sdate');" style="cursor:hand"> 
                                    ~
                                    <input type="text" class="box3" size="8" name="acc_edate" value="<%=acc_edate%>" userType="date">
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_edate');" style="cursor:hand"> 

									<span style="width:20px"></span>
								</td>
								<td>
									<img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand" align="absmiddle">  
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr> 
					<td><img src="../img/box_bt.gif" width="964" height="10"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="20">
		<td width="18">&nbsp;</td>
		<td width="975">&nbsp;</td>
	</tr>
    <%}else{%>
	<tr height="30">
		<td colspan="2" align="center"><b>감사자료조회</b></td>
	</tr>
	<tr height="20">
		<td colspan="2" align="center">&nbsp;</td>
	</tr>
    <%}%>
    <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
            <table width="100%" border="<%=border%>" cellspacing="0" cellpadding="0"  class="list">	
			    <tr>
                    <th class="fstleft"  style="font-size:7pt;">회계년도</th>
                    <th style="font-size:7pt;">회계일자</th>
                    <th style="font-size:7pt;">전일누계</th>
                    <th style="font-size:7pt;">금일수입</th>
                    <th style="font-size:7pt;">과오납</th>
                    <th style="font-size:7pt;">과목정정</th>
                    <th style="font-size:7pt;">세입총계</th>
                    <th style="font-size:7pt;">자금배정액</th>
                    <th style="font-size:7pt;">잔액</th>
                    <th style="font-size:7pt;">정기예금</th>
                    <th style="font-size:7pt;">세계전용</th>
                    <th style="font-size:7pt;">비고1</th>
                    <th style="font-size:7pt;">금액1</th>
                    <th style="font-size:7pt;">비고2</th>
                    <th style="font-size:7pt;">금액2</th>
                    <th style="font-size:7pt;">비고3</th>
                    <th style="font-size:7pt;">금액3</th>
				</tr>
				<%
				for (int i=0; dataList != null && i < dataList.size(); i++) {
				    CommonEntity rowData = (CommonEntity)dataList.get(i);
				%>
				<tr> 
					<td class="fstleft" style="font-size:7pt;"><%=rowData.getString("M220_YEAR")%></td>
					<td class="center"  style="font-size:7pt;"><%=TextHandler.formatDate(rowData.getString("M220_DATE"),"yyyyMMdd","yyyy.MM.dd")%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPTOT"))%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("AMTBAEJUNG"))%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("AMTJAN"))%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("AMTJEONGGI"))%></td>
					<td class="center"  style="font-size:7pt;">&nbsp;<!--세계전용:공란--></td>
					<td class="left"    style="font-size:7pt;">&nbsp;<%=rowData.getString("REMARK1")%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("REMARK_AMT1"))%></td>
					<td class="left"    style="font-size:7pt;">&nbsp;<%=rowData.getString("REMARK2")%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("REMARK_AMT2"))%></td>
					<td class="left"    style="font-size:7pt;">&nbsp;<%=rowData.getString("REMARK3")%></td>
					<td class="right"   style="font-size:7pt;"><%=StringUtil.formatNumber(rowData.getString("REMARK_AMT3"))%></td>
				</tr>
				<% } 
				if (dataList == null || dataList.size() == 0) {  %>
				<tr>
					<td class="fstleft" colspan="17">조회내역이 없습니다.</td>
				</tr>            
				<% } %>
            </table>
        </td>
    </tr>
    <%if(excelFlag.equals("")){%>
    <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
                <tr> 
                    <td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goExcel('Y')" style="cursor:hand" align="absmiddle">
					</td>
                </tr>
            </table>
        </td>
    </tr>
    <%}%>
</table>
</form>
<%if(excelFlag.equals("")){%>
<table width="1000">
	<tr>
		<td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
<%}%>
</body>
</html>