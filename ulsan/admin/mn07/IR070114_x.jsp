<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070114_x.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-18
* 프로그램내용		: 일계/보고서 > 보고서조회 > 광역시세일계표(엑셀)
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
    
    response.setContentType("application/vnd.ms-excel;charset=euc-kr");
    response.setHeader("Content-Disposition", "inline; filename=광역시세일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
    response.setHeader("Content-Description", "JSP Generated Data");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript" src="../js/report.js"></script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<form name="sForm" method="post" action="IR070114_x.etax">
<input type="hidden" name="cmd" value="dayReport04">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">
<%
if (reportList.size() > 0 && reportList != null) { 
%>
<table width="1400" border="0" cellspacing="0" cellpadding="1">
		<tr> 
         <td colspan="32" valign="middle" align="center">
            <b><u><%=request.getParameter("acc_year")%>년 울산 광역시세 일계표</u></b>
        </td>
        <td colspan="4" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){ // 미마감인경우 (시분초)
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000'>
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
<table width="1400" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="14" width="653">수납일:&nbsp;<%=request.getParameter("acc_date")%></td>
        <td colspan="24"  width="80" align="right">(단위:원)</td>
    </tr>
</table>
<table width="1400" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000'>
    <tr height="20"> 
        <td colspan="2" rowspan="2" align="center"><b>세목</b></td>
        <td colspan="3" align="center"><b>본청</b></td>
        <td colspan="3" align="center"><b>울산차량등록사업소</b></td>
        <td colspan="3" align="center"><b>중부소방서</b></td>
        <td colspan="3" align="center"><b>남부소방서</b></td>
        <td colspan="3" align="center"><b>농수산물도매시장</b></td>
        <td colspan="3" align="center"><b>문화예술회관</b></td>
        <td colspan="3" align="center"><b>중구청</b></td>
        <td colspan="3" align="center"><b>남구청</b></td>
        <td colspan="3" align="center"><b>동구청</b></td>
        <td colspan="3" align="center"><b>북구청</b></td>
        <td colspan="3" align="center"><b>울주군</b></td>
        <td colspan="3" align="center"><b>합 계</b></td>
    </tr>
    <tr height="20"> 
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
        <td width="85" align="center">전일까지누계</td>
        <td width="85" align="center">금일수입</td>
        <td width="85" align="center">금일까지누계</td>
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
            semok_name = "경상적 세외수입 소계";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "임시적 세외수입 소계";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = gubun + " 소계";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td width="15" align="center"><%=gubun%></td>
        <td><%=semok_name%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00000"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00000"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00000"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02240"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02240"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02240"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02130"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02130"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02130"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02140"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02140"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02140"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02220"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02220"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02220"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02190"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02190"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02190"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00110"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00110"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00110"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00140"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00140"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00140"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00170"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00170"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00170"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00200"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00200"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00200"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00710"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00710"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00710"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT"))%></td>
        <td align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT"))%></td>
    </tr>
    <%}%>
</table>
<table width="1400" border="0" cellspacing="0" cellpadding="0">    
		<tr height="76">
        <td colspan="8" align="left">세입징수관귀하</td>
        <td colspan="4" align="center">
        <span style="width:30px;"></span>
				<% if ("".equals(reportRemark.getString("M470_AMT1")) ) { %>
        &nbsp;
        <% } else { %>
           <%=StringUtil.formatNumber(reportRemark.getString("M470_AMT1"))%>
        <% } %>
           &nbsp;<%=reportRemark.getString("M470_REMARK1")%><br>
        <span style="width:30px;"></span>
        <% if ("".equals(reportRemark.getString("M470_AMT2")) ) { %>
          &nbsp;
        <% } else { %>
           <%=StringUtil.formatNumber(reportRemark.getString("M470_AMT2"))%>
        <% } %>
          &nbsp;<%=reportRemark.getString("M470_REMARK2")%><br>
        <span style="width:30px;"></span>
        <% if ("".equals(reportRemark.getString("M470_AMT3")) ) { %>
           &nbsp;
        <% } else { %>
           <%=StringUtil.formatNumber(reportRemark.getString("M470_AMT3"))%>
        <% } %>
         &nbsp;<%=reportRemark.getString("M470_REMARK3")%>
        </td>
        <td colspan="24" align="right">경남은행 울산시청지점<br>울산광역시금고</td>
        <td width="85" colspan="2">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="100"  height="100" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
</table>
<%}else{%>
<div align="center">
<table width="1400" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td colspan="38" align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
</div>
<%}%>
</form>
</body>
</html>