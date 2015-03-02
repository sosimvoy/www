<%
/*************************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070128.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-09
* 프로그램내용		: 일계/보고서 > 보고서조회 > 세입월계표(본청 세외수입)
*************************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");

	List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");
    
    String gubun = "";
    String semok_name = "";   
    String border_right = "";
    String border_left = "";
    String saveFile = request.getParameter("saveFile");
    String today = TextHandler.getCurrentDate();
  
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=세입월계표(본청 세외수입)_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report {font-family: "arial","굴림", "Helvetica", "sans-serif";}
.report td.re01 {font-size:6pt;}
.report td.re02 {font-size:5pt;}

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/rowspan.js"></script>
<script language="javascript" src="../js/tax_common.js"></script>	
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">

function init(){
    <% if (reportList.size() > 0 && reportList != null) {%>
    cellMergeChk(document.all.dataList, 1, 0);
    <%}%>
     <% if (reportList.size() > 0 && reportList != null) {%>
    cellMergeChk(document.all.dataList1, 1, 0);
    <%}%>
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.target = "_self";
    form.action = "IR070128.etax";
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
<form name="sForm" method="post" action="IR070128.etax">
<input type="hidden" name="cmd" value="dayReport18">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="1000" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="980"> 
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
<table width="980" border="0" cellspacing="0" cellpadding="1">
  <tr>
    <td colspan="3">
      <table width="980" border="0" cellspacing="0" cellpadding="1">
        <tr height="70"> 
          <td colspan="2" style="font-size:12pt; padding-left:150px;"> 
              <b><u>세 입 월 계 표 (본청 세외수입)</u></b>
          </td>
          <td colspan="2" width="200" align="right" valign="top" rowspan="2"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // 미마감인경우
            %>
            <table width="100" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
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
    </td>
	</tr>

  <tr>
    <td>
		  <div align="left">회계별 : <%=request.getParameter("acc_year")%>년도 일반회계
      <span style="width:310px;">&nbsp;</span>    
		  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_year"),"-",""),"yyyyMMdd","yyyy")%>년 <%=(Integer.parseInt(request.getParameter("acc_date").substring(0,4))-Integer.parseInt(request.getParameter("acc_year")))*12+Integer.parseInt(TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM"))%>월분</div>
		</td>
    <td></td>
    <td></td>
  </tr>
  
  <tr>
    <td>
	    <table id="dataList" width="550" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
		    <tr>  
          <td colspan="2" width="120" class="re01">
            <div align="center">세&nbsp;&nbsp;&nbsp;&nbsp;목</div>
          </td>
		      <td width="100" class="re01">
            <div align="center">전&nbsp;&nbsp;월&nbsp;&nbsp;계</div>
          </td>
	        <td width="100" class="re01">
            <div align="center">본&nbsp;&nbsp;월&nbsp;&nbsp;분</div>
          </td >
		      <td width="100" class="re01">
            <div align="center">과오납액반납</div>
          </td>
		      <td width="100" class="re01">
            <div align="center">누&nbsp;&nbsp;&nbsp;&nbsp;계</div>
          </td>
		      <td width="30" class="re01">
            <div align="center">비&nbsp;&nbsp;고</div>
          </td>
        </tr>
        <% 
          for (int i=0; i < reportList.size(); i++) {
            CommonEntity rowData = (CommonEntity)reportList.get(i);

              if(rowData.getString("M370_SEMOKNAME").equals("")){
                semok_name = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;합&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;계";
                gubun = "";
                border_right = "border-right:none;";    
                border_left  = "border-left:none;";
              }else{
                semok_name = rowData.getString("M370_SEMOKNAME");
                gubun = "세<br><br><br><br><br><br><br><br><br><br><br><br>외<br><br><br><br><br><br><br><br><br><br><br><br>수<br><br><br><br><br><br><br><br><br><br><br><br>입";       
                border_right = "";    
                border_left  = "";
              }
        %>
		    <tr> 
      	  <td width="10" style="<%=border_right%>" class="re02">
            <div align="center"><%=gubun%></div>
          </td>
		      <td width="130" style="<%=border_left%>" class="re02">
            <div align="reft"><%=semok_name%></div>
          </td>
		      <td class="re02">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div>
          </td>
          <td class="re02">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div>
          </td>
          <td class="re02">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAONAP"))%></div>
          </td>
          <td class="re02">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></div>
          </td>
          <td class="re02">
            <div align="right">&nbsp;</div>
          </td>
        </tr>
        <%}%>
      </table>
	  </td>
    <td>
	    <table width="10" border="0" cellspacing="0" cellpadding="1">
	      <tr>
		      <td>&nbsp;</td>
		    </tr>
	    </table>
	  </td>
    <td>
	    <table id="dataList1" width="400" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
		    <tr>  
          <td colspan="2" width="130" class="re01">
            <div align="center">세&nbsp;&nbsp;&nbsp;&nbsp;목</div>
          </td>
		      <td width="100" class="re01">
            <div align="center">징수관 세입누계</div>
          </td>
          <td width="110" class="re01">
            <div align="center">금고 세입누계</div>
          </td>
		      <td width="30" class="re01">
            <div align="center">증&nbsp;&nbsp;감</div>
          </td>
	        <td width="30" class="re01">
            <div align="center">사&nbsp;&nbsp;유</div>
          </td>
        </tr>
        <% 
          for (int i=0; i < reportList.size(); i++) {
            CommonEntity rowData = (CommonEntity)reportList.get(i);

              if(rowData.getString("M370_SEMOKNAME").equals("")){
                semok_name = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;합&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;계";
                gubun = "";
                border_right = "border-right:none;";    
                border_left  = "border-left:none;";
              }else{
                semok_name = rowData.getString("M370_SEMOKNAME");
                gubun = "세<br><br><br><br><br><br><br><br><br><br><br><br>외<br><br><br><br><br><br><br><br><br><br><br><br>수<br><br><br><br><br><br><br><br><br><br><br><br>입";                    
                border_right = "";    
                border_left  = "";
              }
        %>
		    <tr> 
          <td width="10" class="re02" style="<%=border_right%>" >
            <div align="center"><%=gubun%></div>
          </td>
		      <td width="120" class="re02" style="<%=border_left%>">
            <div align="reft"><%=semok_name%></div>
          </td>
          <td class="re02">
            <div align="right">&nbsp;</div>
          </td>
          <td class="re02">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></div>
          </td>
          <td class="re02">
            <div align="right">&nbsp;</div>
          </td>
          <td class="re02">
            <div align="right">&nbsp;</div>
          </td>
		    </tr>
       <%}%>
	    </table>
    </td>
	</tr>
  <tr>
    <td>
      <table width="550" border="0" cellspacing="0" cellpadding="4" class="basic">
        <tr> 
          <td colspan="2" style="font-size:8pt;"> 
            <div align="center">상기와 같이 보고함.</div>
			      <div align="center"><%=request.getParameter("acc_date")%></div>
          </td>
        </tr>
        <tr>
          <td style="font-size:8pt;">
            <div align="left">울산광역시 징수관 귀하</div>
          </td>
          <td style="font-size:8pt;"> 
            <div align="right">울산광역시금고</div>
          </td>
          <td rowspan="2" width="80">
            <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="50" height="50" align="right">
            <% } %>
          </td>
        </tr>
      </table>
	  </td>
    <td>
	    <table width="10" border="0" cellspacing="0" cellpadding="1">
	      <tr>
          <td>&nbsp;</td>
		    </tr>
	    </table>
	  </td>
    <td>
	    <table width="400" border="0" cellspacing="2" cellpadding="1" class="basic">
	      <tr> 
          <td style="font-size:8pt;"> 
            <div align="center">상기와 상이없음을 확인함.</div>
			      <div align="center"><%=request.getParameter("acc_date")%></div>
          </td>
        </tr>
		    <tr height="50"> 
          <td style="font-size:8pt;"> 
            <div align="right">울산광역시 징수관</div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>

<%}else{%>
<table width="1000" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>해당자료가 없습니다.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>
