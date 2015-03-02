<%
/*************************************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	    : IR070136.jsp
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-09
* 프로그램내용		: 일계/보고서 > 보고서조회 > 세입분기표(회계별)
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
  
  Integer totalCount = (Integer)request.getAttribute("page.mn07.totalCount");	//총 카운트

  String cpage = request.getParameter("cpage");	// 현재 페이지 번호 받기
	if( "".equals(cpage) ) {
		cpage = "1";
	}
    
    String semok_name = "";   
    String saveFile = request.getParameter("saveFile");

    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=세입분기표(회계별)_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
        response.setHeader("Content-Description", "JSP Generated Data");
    }
    
    String quarter =  (String)request.getAttribute("page.mn07.quarter");
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
.report td.re01 {font-size:10pt;}
.report td.re02 {font-size:8pt;}
-->

    :root p {font-size:20pt; padding-left:300px;} /*:root 지원되는 다른 모든 브라우저 */       
		 html p {font-size:20pt; padding-left:300px;} /*IE8 적용 */   
 * + html p {font-size:20pt; padding-left:300px;} /*IE7 적용 */   
 *   html p {font-size:20pt; padding-left:300px;} /*IE6 적용 */ 

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>
<script language="javascript" src="../js/tax_common.js"></script>	
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">

function init() {
    typeInitialize();
}

function PAGE(cpage) {
    if(cpage != null) {
    document.sForm.cpage.value=cpage;
  } else {
    document.sForm.cpage.value="";
  }
  eSubmit(document.sForm);
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.target = "_self";
    form.action = "IR070136.etax";
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
<form name="sForm" method="post" action="IR070136.etax">
<input type="hidden" name="cmd" value="dayReport26">      
<input type="hidden" name="cpage" value="">
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

<% 
  for (int i=0; i < reportList.size(); i++) {
    CommonEntity rowData = (CommonEntity)reportList.get(i);
%>
<table width="980" border="0" cellspacing="0" cellpadding="1">
  <tr>
    <td>
      <table width="960" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="185"> 
          <td colspan="7"><p><b><u>
            <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_year"),"-",""),"yyyyMMdd","yyyy")%>년 <%=quarter%>/4분기 세입 분기표
            </u></b></p>
          </td>
          <td colspan="4" width="300" align="right" valign="middle"> 
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
		    <tr height="30">
		      <td colspan="7" class="re01">
		        <div align="left">(<%=request.getParameter("acc_year")%>년도)</div>
		      </td>
          <td colspan="4" class="re01">
		        <div align="right">(단위 : 원)</div>
		      </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
	    <table width="960" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
        <tr height="50">
          <td rowspan="2" width="150" class="re01">
            <div align="center">회&nbsp;&nbsp;계&nbsp;&nbsp;명</div>
          </td>
          <td rowspan="2" width="110" class="re01">
            <div align="center">전 분 기 계</div>
          </td>
          <td colspan="4" width="480" class="re01">
            <div align="center">세&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;입</div>
          </td>
          <td rowspan="2" width="120" class="re01">
            <div align="center">누&nbsp;&nbsp;&nbsp;&nbsp;계</div>
          </td>
          <td rowspan="2" width="60" class="re01">
            <div align="center">지급명령미지급액</div>
          </td>
          <td rowspan="2" width="40" class="re01">
            <div align="center">비&nbsp;&nbsp;고</div>
          </td>  
        </tr>
        <tr height="50">
          <td width="120" class="re01">
            <div align="center">세 입 액</div>
          </td>
          <td width="120" class="re01">
            <div align="center">과 오 납 액</div>
          </td>
          <td width="120" class="re01">
            <div align="center">과 목 정 정</div>
          </td>
          <td width="120" class="re01">
            <div align="center">차&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;액</div>
          </td>
        </tr>
        <tr height="50">
          <td class="re01"> 
            <div align="center">
            <%=StringUtil.formatNumber(rowData.getString("M360_ACCNAME"))%>
            <%        
                 if(rowData.getString("M370_SEMOKNAME").equals("")){
                   semok_name = "";
                 }else{
                   semok_name = rowData.getString("M370_SEMOKNAME");
            %>
            (<%=semok_name%>)
            <%}%>
            </div>      
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("DIFFERENCE"))%></div>
          </td>
          <td class="re02"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></div>
          </td>
          <td class="re02"> 
            <div align="center">&nbsp;</div>
          </td>
          <td class="re02"> 
            <div align="center">&nbsp;</div>
          </td>    
        </tr>  
      </table>
    </td>
  </tr>
  <tr height="140">
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>
      <table width="960" border="0" cellspacing="0" cellpadding="4" class="basic" align="center">
        <tr height="25"> 
          <td colspan="17" style="font-size:11pt;"> 
            <div align="center">위와 같이 보고함.
              <br><%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>년
                  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>월
                  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>일
            </div>
          </td>
          <% if(endState.getString("END_STATE").equals("Y")){  // 마감인경우 (직인) %>
          <td rowspan="2" width="70">
          <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="70" height="70" align="right">
          </td>
          <% }%>
        </tr>
        <tr height="200">
          <td colspan="10" style="font-size:12pt;">울산광역시 세입징수관 귀하</td>
          <td colspan="7" style="font-size:12pt;"> 
            <div align="right">경남은행 울산시청지점<br>울&nbsp; 산&nbsp; 광&nbsp; 역&nbsp;&nbsp;시&nbsp; 금&nbsp; 고</div>
          </td>
        </tr>
      </table>
	  </td>
  </tr>
</table>  
<%}%>
</div>

<div class="noprint">
<%// 페이지 %>
<table width="1000">
  <tr>
    <td align="center">
    <Br>
    <etax:pagelink totalCnt='<%=totalCount.intValue()%>' page='<%=cpage%>'	pageCnt='5' blockCnt='10'  />
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
