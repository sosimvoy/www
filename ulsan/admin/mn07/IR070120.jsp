<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070120.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-13
* 프로그램내용		: 일계/보고서 > 보고서조회 > 공단환경오염이주대책특별회계 세입세출일계표
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
    List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");
	
    CommonEntity totReportList = (CommonEntity)request.getAttribute("page.mn07.totReportList");

    CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");
  
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=공단환경오염이주대책 특별회계세입세출일계표" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report {font-family: "arial","굴림", "Helvetica", "sans-serif"; border-top:0px solid #000000;border-right:0px solid #000000; border-left:0px solid #000000; border-bottom:0px solid #000000; align:center;}
.report td.re01 {font-size:7pt; border-top:1px solid #000000;border-right:1px solid #000000; border-left:1px solid #000000; border-bottom:1px solid #000000;}                               
.report td.re02 {font-size:7pt; border-top:1px solid #000000;border-right:1px solid #000000; border-left:1px solid #000000; border-bottom:1px solid #000000;}
.report td.re03 {font-size:7pt; border-top:0px solid #ffffff;border-right:0px solid #ffffff; border-left:0px solid #ffffff; border-bottom:0px solid #ffffff;}

    :root p {font-size:14pt; padding-left:290px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html p {font-size:14pt; padding-left:290px;} /*IE8 적용 */   
 * + html p {font-size:14pt; padding-left:290px;} /*IE7 적용 */   
 *   html p {font-size:14pt; padding-left:290px;} /*IE6 적용 */ 

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<%}%>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">

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
<form name="sForm" method="post" action="IR070120.etax">
<input type="hidden" name="cmd" value="dayReport10">
<input type="hidden" name="acc_year"    value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date"    value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type"    value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code"   value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code"    value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile"    value="<%=saveFile%>">

<div class="noprint">
<table width="1050" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
	<tr> 
		<td width="1050"> 
  <%if(saveFile.equals("")){%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
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
<table width="1050" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr height="50">
        <td colspan="15"></td>
    </tr>
    <tr height="150"> 
        <td colspan="13"> 
            <p><b><u>공단환경오염이주대책 특별회계 세입세출 일계표 (<%=request.getParameter("acc_year")%>)</u></b></p>
        </td>
		<td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우 (시분초)
            %>
            <table width="150" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="35">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">미마감 자료</td>
                </tr>
                <tr height="35">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table width="1050" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
  <tr height="50"> 
    <td rowspan="2" colspan="2" class="re01"> 
      <div align="center"><b>회계명</b></div>
    </td>
    <td colspan="5" class="re01"> 
      <div align="center"><b>세입</b></div>
    </td>
    <td colspan="5" class="re01"> 
      <div align="center"><b>세출</b></div>
    </td>
    <td rowspan="2" width="78" class="re01"> 
      <div align="center"><b>잔액</b></div>
    </td>
    <td colspan="2" class="re01"> 
      <div align="center"><b>자금운용</b></div>
    </td>
    
    <%if(!endState.getString("END_STATE").equals("Y")){%><!-- 미마감시만 -->
     <td width="30" rowspan="2" class="re03"> 
      <div align="center">잉여금이입액</div>
    </td>
	 <td width="30" rowspan="2" class="re03"> 
      <div align="center">잔액일치</div>
    </td>
    <%}%>
  </tr>	
  <tr height="50"> 
    <td width="80" class="re01"> 
      <div align="center">전일누계 </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">금일수입 </div>
    </td>
    <td width="55" class="re01"> 
      <div align="center">과오납반납 </div>
    </td>
    <td width="55" class="re01"> 
      <div align="center">과목경정액 </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">총액</div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">전일누계 </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">당일지급 </div>
    </td>
    <td width="35" class="re01"> 
      <div align="center">반납액 </div>
    </td>
    <td width="55" class="re01"> 
      <div align="center">과목경정액 </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">총액 </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">정기예금등 </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">공금잔액 </div>
    </td>
  </tr>

  <tr height="50"> 
    <td colspan="2" class="re01"> 
      <div align="center"width="70"><%=totReportList.getString("M360_ACCNAME")%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSEIPJEONILTOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSEIP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSEIPGWAONAP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSEIPJEONGJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("ALLSEIPTOTAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSECHULJEONILTOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSECHUL"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSECHULBANNAP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSECHULJEONGJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("ALLSECHULTOTAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("ALLJANAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTJEONGGI"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTGONGGEUM"))%></div>
    </td>
    <%if(!endState.getString("END_STATE").equals("Y")){%><!-- 미마감시만 -->
	<td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("SUMSURPLUS"))%></div>
    </td>
	<td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("SURPLUSJAN"))%></div>
    </td>
    <%}%>
  </tr>
  <% 
   for (int i=0; i < reportList.size(); i++) {
		CommonEntity rowData = (CommonEntity)reportList.get(i);
  %>
  <tr height="50"> 
    <td width="10" class="re01">&nbsp;</td>
    <td class="re01" width="70"> 
      <div align="left"><%=rowData.getString("M360_ACCNAME")%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPJEONILTOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPGWAONAP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPJEONGJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("SEIPTOTAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSECHULJEONILTOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSECHUL"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSECHULBANNAP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSECHULJEONGJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("SECHULTOTAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("JANAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTJEONGGI"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTGONGGEUM"))%></div>
    </td>

	 <%if(!endState.getString("END_STATE").equals("Y")){%><!-- 미마감시만 -->
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("SUMSURPLUS"))%></div>
    </td>
		 <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("SURPLUSJAN"))%></div>
    </td>
    <%}%>
  </tr>
<%}%>
</table>
<table width="1050" border="0" cellspacing="0" cellpadding="4" class="basic" align="center">
  <tr height="100">
    <td colspan="15"></td>
  </tr>
  <tr> 
    <td colspan="14"> 
       <span style="width:470px"></span>위와 같이 보고함.<br>
			 <span style="width:480px"></span><%=request.getParameter("acc_date")%>
    </td>
    <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인) %>
    <td rowspan="2" width="80">
        <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
    </td>
    <% }%>
  </tr>
  <tr>
    <td colspan="8">공단환경오염이주대책특별회계 징수관</td>
    <td colspan="6"> 
      <div align="right">경남은행 울산시청지점<br>울&nbsp; 산&nbsp; 광&nbsp; 역&nbsp;&nbsp;시&nbsp; 금&nbsp; 고</div>
    </td>
  </tr>
  <tr height="100">
    <td colspan="15"></td>
  </tr>
</table>
</div>

<%}else{%>
<table width="1050" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>
</form>
</body>
</html>
