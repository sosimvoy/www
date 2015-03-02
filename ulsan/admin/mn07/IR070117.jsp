<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070117.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-13
* 프로그램내용		: 일계/보고서 > 보고서조회 > 교통사업특별회계세입일계표
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");
	List<CommonEntity> reportList1 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList1");
	List<CommonEntity> reportList2 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList2");
	List<CommonEntity> reportList3 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList3");
    
	CommonEntity totalData = (CommonEntity)request.getAttribute("page.mn07.totalData");
    //out.println("reportList1::"+reportList1);
  
    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=교통사업특별회계세입일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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

    :root p {font-size:14pt; padding-left:370px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html p {font-size:14pt; padding-left:370px;} /*IE8 적용 */   
 * + html p {font-size:14pt; padding-left:370px;} /*IE7 적용 */   
 *   html p {font-size:14pt; padding-left:370px;} /*IE6 적용 */ 

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
<form name="sForm" method="post" action="IR070117.etax">
<input type="hidden" name="cmd" value="dayReport07">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="1050" align="center" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr height="18"> 
		<td width="1050"> 
            <%if(saveFile.equals("")){%>
			<table width="1050" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr height="18"> 
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
if (reportList1.size() > 0 && reportList1 != null) { 
%>

<div id="box" align="center">
<table width="1050" border="0" cellspacing="0" cellpadding="0">
    <tr height="40"> 
        <td colspan="11"> 
            <p><u><b><%=request.getParameter("acc_year")%>년 교통사업 특별회계 세입일계표</b><u></p> 
        </td>
        <td colspan="3" width="200" align="right" valign="top"> 
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
</table>
<table width="1050" border="0" cellspacing="0" cellpadding="0" class="basic">
    <tr> 
        <td colspan="7" width="300">수납일 : <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%></td>
        <td colspan="7" width="685"> 
            <div align="right">(단위:원)</div>
        </td>
    </tr>
</table>
<table id="dataList" width="1050" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr> 
        <td colspan="2" class="re01"> 
            <div align="center"><b>구분(99)</b></div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">과태료 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">교통유발부담금 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">이자수입 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">기타사용료</div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">기타잡수입 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">시도비반환금수입 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">체납처분수입 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">이월금 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">주차요금수입 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">내부전입금 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">국고보조금 </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">합 계</div>
        </td>
    </tr>
    <!-- 본청 START -->
    <% 
    for (int i=0; i < reportList1.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList1.get(i);
    %>
    <tr>
        <td class="re01" rowspan="6" width="15"><%=rowData.getString("M350_PARTNAME")%></td>
        <td class="re01" width="90">전일까지누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">금일수입</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과오납</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과오납 누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과목 갱정 등</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">금일까지누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    <%}%>
    <!--// 본청 END -->

    <!-- 울산차량등록소 START -->
    <% 
    for (int i=0; i < reportList3.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList3.get(i);
    %>
    <tr>
        <td class="re01" rowspan="6" width="15"><%=rowData.getString("M350_PARTNAME")%></td>
        <td class="re01" width="90">전일까지누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">금일수입</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과오납</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과오납 누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과목 갱정 등</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_99"))%></div></td>
    </tr>
    <tr height="3">
        <td class="re01">금일까지누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    <%}%>
    <!--// 울산차량등록소 END -->

    <!-- 구청 START -->
    <% 
    for (int i=0; i < reportList2.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList2.get(i);
    %>
    <tr>
        <td class="re01" rowspan="6"><%=rowData.getString("M350_PARTNAME")%></td>
        <td class="re01">전일까지누계 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">현년도 수입</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">현년도 누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과년도 수입</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과년도 누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">금일까지누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    <%}%>
    <!--// 구청 END -->

    <!-- 합계 START -->
    <tr>
        <td class="re01" rowspan="8">합계</td>
        <td class="re01">전일까지누계 </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">현년도 수입</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">현년도 누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과년도 수입</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과년도 누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과오납</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">과오납 누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">금일까지누계</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    <!--// 합계 END -->
</table>
<table width="1050" border="0" cellspacing="2" cellpadding="1" class="basic">
    <tr> 
        <td colspan="10"> 
             <span style="width:465px"></span><%=acc_date.substring(0,4)%>년 <%=acc_date.substring(4,6)%>월 <%=acc_date.substring(6,8)%>일
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

</div>


<%}else{%>
<table width="1050" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>