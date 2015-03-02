<%
/***************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070140.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-10
* 프로그램내용		: 일계/보고서 > 보고서조회 > 세입세출일계표
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
		List<CommonEntity> reportList2 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList2");
    
    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");

    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=세입세출 일계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report td.re02 {font-size:5pt;}
.report td.re03 {font-size:4pt;}

    :root u {font-size:12pt; padding-left:400px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html u {font-size:12pt; padding-left:400px;} /*IE8 적용 */   
 * + html u {font-size:12pt; padding-left:400px;} /*IE7 적용 */   
 *   html u {font-size:12pt; padding-left:400px;} /*IE6 적용 */ 
 * + html p {padding-left:500px;} /*IE7 적용 */   
 *   html p {padding-left:500px;} /*IE6 적용 */ 

@media print {
  .noprint {
	  display:none;
	}
}
</style>
<% } %>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript">

function init(){
    <% if (reportList.size() > 0 && reportList != null) {%>
    cellMergeChk(document.all.dataList, 2, 0);
    <%}%>
		<% if (reportList2.size() > 0 && reportList2 != null) {%>
    cellMergeChk(document.all.dataList1, 2, 0);
    <%}%>
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.target = "_self";
    form.action = "IR070140.etax";
    form.submit();
}

function goPDF(){
    var form =  document.forms[0];
    form.action = "IR070152.etax";
    form.target = "pdfFrame";
    form.submit();
}
   
var initBody; 

function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 0.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true); // 출력하기 
}

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070140.etax">
<input type="hidden" name="cmd" value="dayReport02">
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
<table width="1050" align="center" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="1050"> 
      <%if(saveFile.equals("")){%>
			<table width="1050" border="0" cellspacing="0" cellpadding="0" class="btn">
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
<div id="box" align="center">
<table width="950" border="0" cellspacing="0" cellpadding="1">
    <tr height="50"> 
        <td colspan="14"> 
            <u><b><%=request.getParameter("acc_year")%>년 세입세출 일계표</b></u>
        </td>
        <td colspan="4" width="200" align="right" valign="top"> 
        </td>
    </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr> 
        <td colspan="14" width="870">수납일: <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%></td>
        <td colspan="4"  width="80" align="right">(단위:원)</td>
    </tr>
</table>
<table id="dataList" width="950" border="1" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#000000" class="report">
    <tr class="re03" height="7"> 
        <td colspan="2" rowspan="2" width="100" class="re03"> 
            <div align="center">회계명</div>
        </td>
        <td colspan="5" class="re03"> 
            <div align="center">세입</div>
        </td>
        <td rowspan="2" width="60" class="re03"> 
            <div align="center">자금배정액</div>
        </td>
        <td colspan="5" class="re03"> 
            <div align="center">세출</div>
        </td>
        <td rowspan="2" width="38" class="re03"> 
            <div align="center">잔액</div>
        </td>
        <td colspan="2" class="re03"> 
            <div align="center">자금운용</div>
        </td>
        <td rowspan="2" width="74" class="re03">
            <div align="center">공급차액</div>
        </td>
    </tr>
    <tr class="re03" height="7"> 
        <td width="49" class="re03"> 
            <div align="center">전일누계</div>
        </td>
        <td width="57" class="re03"> 
            <div align="center">금일수입</div>
        </td>
        <td width="66" class="re03"> 
            <div align="center">과오납반납</div>
        </td>
        <td width="74" class="re03"> 
            <div align="center">과목정정액등</div>
        </td>
        <td width="51" class="re03"> 
            <div align="center">총계</div>
        </td>
        <td width="60" class="re03"> 
            <div align="center">전일누계</div>
        </td>
        <td width="60" class="re03"> 
            <div align="center">당일지급</div>
        </td>
        <td width="51" class="re03"> 
            <div align="center">반납액</div>
        </td>
        <td width="72" class="re03"> 
            <div align="center">과목정정액등</div>
        </td>
        <td width="39" class="re03"> 
            <div align="center">총계</div>
        </td>
        <td width="63" class="re03"> 
            <div align="center">정기예금등</div>
        </td>
        <td width="56" class="re03"> 
            <div align="center">공금잔액</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size()-1; i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        if(!rowData.getString("ACCTYPENM").equals("")){
            gubun = rowData.getString("ACCTYPENM");
        }

        if(rowData.getString("SUMGUBUN").equals("A1") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;(본청)";
        }else if(rowData.getString("SUMGUBUN").equals("B3") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;교통사업(합계)";
        }else if(!rowData.getString("ACCTYPE").equals("") && rowData.getString("SUMGUBUN").equals("")){
            acc_name = "&nbsp;&nbsp;"+ gubun + " 합계";
        }else{
            acc_name = rowData.getString("ACCNAME");
        }        
    %>
    <tr class="re03" height="7"> 
        <td width="10"  class="re03"><%=gubun%></td>
        <td width="100" class="re03"><%=acc_name%></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPTOT"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTBAEJUNG"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONILTOT"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHUL"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULBANNAP"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONGJEONG"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULTOT"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJAN"))%></div></td>
        <td class="re03"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJEONGGI"))%></div></td>
        <td class="re03">
           <div align="right"><!-- 공금잔액 -->
            <%=StringUtil.formatNumber(rowData.getLong("AMTJAN") - rowData.getLong("AMTJEONGGI"))%>
            </div>
       </td>
        
        <td class="re03"><div align="right">
        <%=StringUtil.formatNumber(rowData.getLong("AMTJAN") - rowData.getLong("AMTJEONGGI") - rowData.getLong("M220_AMTJAN"))%>
        </div></td>
    </tr>
    <%
					}
	  %>
</table>
<table width="1050" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr height="5">
        <td colspan="16"></td>
    </tr>
    <tr height="30">
        <td colspan="11"> 
            <p>&nbsp;위와같이 보고함<br><%=acc_date.substring(0,4)%>년 <%=acc_date.substring(4,6)%>월 <%=acc_date.substring(6,8)%>일</p>
        </td>
        <td colspan="4"> 
            <div align="right">경남은행 울산시청지점<br>울 산 광 역 시 시 금 고</div>
        </td>
        <td rowspan="2" width="80" height="60" >
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60" height="60" align="right">
            <% } else {%>
                 &nbsp;
            <% } %>
        </td>
    </tr>
</table>
<% } %>
<%
if (reportList2.size() > 0 && reportList2 != null) { 
%>

<table width="950" border="0" cellspacing="0" cellpadding="1">
    <tr height="50"> 
        <td colspan="14"> 
            <u><b><%=request.getParameter("acc_year")%>년 세입세출 일계표</b></u>
        </td>
        <td colspan="4" width="200" align="right" valign="top"> 
        </td>
    </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr> 
        <td colspan="14" width="870">수납일: <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%></td>
        <td colspan="4"  width="80" align="right">(단위:원)</td>
    </tr>
</table>
<table id="dataList1" width="950" border="1" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#000000" class="report">
    <tr> 
        <td colspan="2" rowspan="2" width="100" class="re01"> 
            <div align="center">회계명</div>
        </td>
        <td colspan="5" class="re01"> 
            <div align="center">세입</div>
        </td>
        <td rowspan="2" width="60" class="re01"> 
            <div align="center">자금배정액</div>
        </td>
        <td colspan="5" class="re01"> 
            <div align="center">세출</div>
        </td>
        <td rowspan="2" width="38" class="re01"> 
            <div align="center">잔액</div>
        </td>
        <td colspan="2" class="re01"> 
            <div align="center">자금운용</div>
        </td>
        <td rowspan="2" width="74" class="re03">
            <div align="center">공급차액</div>
        </td>
    </tr>
    <tr class="re01"> 
        <td width="49" class="re01"> 
            <div align="center">전일누계</div>
        </td>
        <td width="57" class="re01"> 
            <div align="center">금일수입</div>
        </td>
        <td width="66" class="re01"> 
            <div align="center">과오납반납</div>
        </td>
        <td width="74" class="re01"> 
            <div align="center">과목정정액등</div>
        </td>
        <td width="51" class="re01"> 
            <div align="center">총계</div>
        </td>
        <td width="60" class="re01"> 
            <div align="center">전일누계</div>
        </td>
        <td width="60" class="re01"> 
            <div align="center">당일지급</div>
        </td>
        <td width="51" class="re01"> 
            <div align="center">반납액</div>
        </td>
        <td width="72" class="re01"> 
            <div align="center">과목정정액등</div>
        </td>
        <td width="39" class="re01"> 
            <div align="center">총계</div>
        </td>
        <td width="63" class="re01"> 
            <div align="center">정기예금등</div>
        </td>
        <td width="56" class="re01"> 
            <div align="center">공금잔액</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList2.size()-3; i++) {
        CommonEntity rowData = (CommonEntity)reportList2.get(i);
                   
        if(!rowData.getString("ACCTYPENM").equals("")){
            gubun = rowData.getString("ACCTYPENM");
        }

        if(rowData.getString("SUMGUBUN").equals("A1") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;본청(합계)";
        }else if(rowData.getString("SUMGUBUN").equals("B3") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;(교통사업)";
				}else if(rowData.getString("SUMGUBUN").equals("E9") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;(사회복지기금)";
        }else if(!rowData.getString("ACCTYPE").equals("") && rowData.getString("SUMGUBUN").equals("")){
            acc_name = "&nbsp;&nbsp;"+ gubun + " 합계";
        }else{
            acc_name = rowData.getString("ACCNAME");
        }		
      %>
    <tr class="re02" height="10"> 
        <td width="10"  class="re01"><%=gubun%></td>
        <td width="100" class="re01"><%=acc_name%></td>
				<td class="re02"><div align="right">
						<%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%>
				</div></td>
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
				<td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJEONGGI"))%></div></td>	 
        <td class="re02">
            <div align="right"><!-- 공금잔액 -->
            <%=StringUtil.formatNumber(rowData.getLong("AMTJAN") - rowData.getLong("AMTJEONGGI"))%>
            </div>
       </td>
        <td class="re02"><div align="right">
        <%=StringUtil.formatNumber(rowData.getLong("AMTJAN") - rowData.getLong("AMTJEONGGI") - rowData.getLong("M220_AMTJAN"))%>
        </div></td>
    </tr>
    <%
			 }
		%>
</table>
<table width="1050" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr height="5">
        <td colspan="16"></td>
    </tr>
    <tr height="30">
        <td colspan="11"> 
            <p>&nbsp;위와같이 보고함<br><%=acc_date.substring(0,4)%>년 <%=acc_date.substring(4,6)%>월 <%=acc_date.substring(6,8)%>일</p>
        </td>
        <td colspan="4"> 
            <div align="right">경남은행 울산시청지점<br>울 산 광 역 시 시 금 고</div>
        </td>
        <td rowspan="2" width="80" height="60">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60" height="60" align="right">
            <% } else {%>
                 &nbsp;
            <% } %>
        </td>
    </tr>
</table>
</div>
<% } %>

<% if (reportList.size() == 0) { %>
<table width="950" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>

</form>
<form name="pForm" method="post" action="IR070152.etax">
<iframe name="pdfFrame" width="0" height="0"></iframe>
</form>
</body>
</html>