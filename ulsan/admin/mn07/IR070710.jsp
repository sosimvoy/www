<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR070710.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-09-27
* 프로그램내용	   : 일계/보고서 > 국세수납합계표조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
String saveFile = request.getParameter("saveFile");
%>
<%if(saveFile.equals("")){%>
<%@include file="../menu/mn07.jsp" %>
<%}%>
<%

  List<CommonEntity> taxTransfer  = (List<CommonEntity>)request.getAttribute("page.mn07.taxTransfer");	
  List<CommonEntity> taxTotal = (List<CommonEntity>)request.getAttribute("page.mn07.taxTotal"); 

  String first_date = (String)request.getAttribute("page.mn07.first_date");
  String last_date =  (String)request.getAttribute("page.mn07.last_date");

  String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");

	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);  
  String acc_year = request.getParameter("acc_year");              //회계연도
  String acc_month  = request.getParameter("acc_month");           //조회대상월
    
  if (acc_month.equals("")) {   // 회계월을 선택하지 않은경우 : 현재월 - 1달
	  acc_month  = TextHandler.formatDate(TextHandler.replace(TextHandler.getPrevMonthDate(StringUtil.getCurrentDate(),"yyyyMMdd"),"/",""),"yyyyMMdd","MM");
  }

  String date = request.getParameter("date");
  if (date.equals("")){
    date = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy년 MM월 dd일");
  }

  String sunap_date = ""; 
  String today = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMM");  // 현재 년월
  
  if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=국세수납총합계표_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report td.re01 {font-size:10pt;}
.report td.re02 {font-size:9pt;}

@media print {
  .noprint {
	  display:none;
	}
}
</style>
<%}%>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>
<script language="javascript" src="../js/tax_common.js"></script>
<script language="javascript">
      
function init() {
  typeInitialize();
}	
function goSearch() {
  var form = document.sForm;

  if(form.acc_year.value + form.acc_month.value  >=  form.today.value){
      alert("전월까지만 조회 가능합니다.");
      return;
  }
	/*
	form.action = "IR070710.etax";
	form.cmd.value = "taxTotal";
    form.saveFile.value = "";
	form.target = "_self";
	eSubmit(form);
	*/
	
	form.acc_date.value = form.acc_year.value + form.acc_month.value + "01";
	form.cmd.value = "dayReportAll";
	pageurl = "/admin/oz51/IR070710.etax";
	funcOpenWindow(form,'post','NeWreport',pageurl , 10,10, 1000, 1000, 0, 0, 0, 0, 1);
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄  
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
<form name="sForm" method="post" action="IR070710.etax">
<input type="hidden" name="cmd" value="taxTotal">
<input type="hidden" name="saveFile" value="<%=saveFile%>">
<input type="hidden" name="today" value="<%=today%>"><!-- 현재년월-->
<input type="hidden" name="acc_date" value="">

<%if(!saveFile.equals("1")){%>
<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40"><div class="noprint"><img src="../img/title7_14.gif"></div></td>
	</tr>
	<tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40">
      <div class="noprint">
			<table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
				<tr> 
					<td><img src="../img/box_top.gif" width="964" height="11"></td>
				</tr>
				<tr> 
					<td> 
						<table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr> 
								<td width="880">
									<span style="width:20px"></span>
                  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                  회계연도
                  <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
                    <option value="<%=this_year%>"><%=this_year%></option>
                    <option value="<%=last_year%>"><%=last_year%></option>
                  </select>
                  
                  <span style="width:50px"></span>
                  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                  조회대상 월&nbsp;
                  <select name="acc_month" iValue="<%=acc_month%>">
                    <option value="01">1월</option>
                    <option value="02">2월</option>
                    <option value="03">3월</option>
                    <option value="04">4월</option>
                    <option value="05">5월</option>
                    <option value="06">6월</option>
                    <option value="07">7월</option>
                    <option value="08">8월</option>
                    <option value="09">9월</option>
                    <option value="10">10월</option>
                    <option value="11">11월</option>
                    <option value="12">12월</option>
                  </select>
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
      </div>
		</td>
	</tr>
	<tr height="20">
		<td width="18">&nbsp;</td>
		<td width="975">&nbsp;</td>
	</tr>
</table>
<% } %>

<!-- 세입금 이체필 통지서 -->
  <%
   if (!acc_year.equals("") && !request.getParameter("acc_month").equals("")) { 
  %>   

<table width="993" border="0" cellspacing="0" cellpadding="0">   
 <tr>
    <td style="padding-left:55px;">
      <div id="pDiv">
      <table width="650" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="50"> 
          <td colspan="5"> 
            <div align="center"><font size="5"><b><u>세입금 이체필 통지서</u></b></font></div>
          </td>
        </tr>
        <tr height="70"> 
          <td colspan="3" class="re01">
               <별지 제2-1호 서식>
          </td>   
          <td colspan="2" class="re01" align="right">
               <%=date%>
          </td>      
        </tr>
        <!-- 여기까지 -->
        <tr height="50"> 
          <td class="re01" colspan="5">
            <div align="left"><b>울산광역시 차량등록사업소 세입징수관 귀하</b></div>
          </td>
        </tr>
        <tr height="35"> 
          <td class="re01" colspan="5">
            <div align="left"><%=acc_year%>년 <%=acc_month%>월분 수납 세입금을 아래와 같이 분리하여 해당 회계로 각각 이체 하였음을 통보합니다.</div>
          </td>
        </tr>
        <tr height="35"> 
          <td class="re01" colspan="5">
            <div align="left">세입징수관 명 : 울산광역시 차량등록사업소 세입징수관</div>
          </td>
        </tr>
        <tr height="35">
          <td class="re01" colspan="5">
            <div align="left"><b>세입징수관 번호 : 160490</b></div>
          </td>
        </tr>
        <tr>
          <td class="re01" colspan="3">
            <div align="left">(일반회계 재경원소관 계좌번호)</div>
          </td>
          <td class="re01" colspan="2">
            <div align="right">(단위 : 원)</div>
          </td>
        </tr>
      </table>
      <table width="650" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
         <%
           for (int i=0; i < taxTransfer.size(); i++) {
             CommonEntity rowData = (CommonEntity)taxTransfer.get(i);
      	 %>
        <tr height="35">
          <td rowspan="2" class="re01" width="125">
            <div align="center">세   목<br>(세목번호)</div>
          </td>
          <td colspan="3" class="re01" width="400">
            <div align="center">특별회계로의 이체[B]</div>
          </td>
          <td rowspan="2" class="re01" width="125">       
            <div align="center">일반회계로이체<br>(과년도분방위세)<br>[A-B]</div>
          </td>
        </tr>
        <tr height="35">
          <td class="re01" width="140">
            <div align="center">수납세입총액[A]</div>
          </td>
          <td class="re01" width="130">
            <div align="center">지방교육양여금관리<br>(021186)</div>
          </td>
          <td class="re01" width="130">
            <div align="center">농특세관리<br>(134921-4)</div>
          </td> 
        </tr>
        <tr height="35">
          <td align="center" class="re01">교육세<br>(341)</td>
          <td align="right"  class="re01">-</td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">기타교육세<br>(345)</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td class="re01">&nbsp;</td>
          <td class="re01">&nbsp;</td>
          <td class="re01">&nbsp;</td>
          <td class="re01">&nbsp;</td>
          <td class="re01">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">농특세<br>(371)</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">기타농특세<br>(372)</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">기타방위세<br>(322)</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">합계<br>(당월분)</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">합계<br>(누계)</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("END_DATE_TAX"))%></td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("END_DATE_TAX"))%></td>
          <td class="re02">&nbsp;</td>
        </tr>
        <% } %>
      </table>
      <table width="650" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="100">
          <td></td>
        </tr>
        <tr height="50">
          <td class="re01"><div align="center"><%=date%><div></td>
        </tr>
        <tr height="25">
          <td class="re01"><div align="right">경남은행 울산시청지점<div></td>
        </tr>
        <tr height="25">
          <td class="re01"><div align="right">울 산 광 역 시 금 고</div></td>
        </tr>
      </table>
      <br>
      <br>
      <br>
      <br>

      <!-- 국세수납 총합계표 -->

      <table width="650" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="100"> 
          <td class="re01"> 
            <div align="center"><font size="5"><b><u>국세수납 총합계표</u></b></font></div>
          </td>
        </tr>
      </table>
      <table width="650" border="0" cellspacing="2" cellpadding="1" class="report">
        <tr height="30"> 
          <td class="re01">
            <div align="left"><font size="2"><b>울산광역시 차량등록사업소 세입징수관 귀하</b></font></div>
          </td>
        </tr>
        <tr height="25"> 
          <td class="re01">
            <div align="left"><%=acc_year%>년 <%=acc_month%>월분 수납된 세입금 내역을 아래와 같이 통보합니다.</div>
          </td>
        </tr>
        <tr height="20"> 
          <td class="re01">
            <div align="right">경남은행 울산시청지점</div>
          </td>
        </tr>
        <tr height="20"> 
          <td class="re01">
            <div align="right">울 산 광 역 시 금 고</div>
          </td>
        </tr>
        <tr height="20"> 
          <td class="re01">
            <div align="center">(대상기간: <%=TextHandler.formatDate(first_date,"yyyyMMdd","yyyy.MM.dd")%>~<%=TextHandler.formatDate(last_date,"yyyyMMdd","yyyy.MM.dd")%>)</div>
          </td>
        </tr>
      </table>

      <table width="650" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
        <tr height="25">
          <td class="re01"><div align="center">수납일자</div></td>
          <td class="re01"><div align="center">국고합계</div></td>
          <td class="re01"><div align="center">등록교육세</div></td>
          <td class="re01"><div align="center">등록농특세</div></td>
          <td class="re01"><div align="center">방위세</div></td>
        </tr>
         <% 
           for (int i=0; i < taxTotal.size(); i++) {
             CommonEntity rowData = (CommonEntity)taxTotal.get(i);

               if(rowData.getString("TMP_DATE").equals("")){
                 sunap_date = "합 계";
               }else{
                 sunap_date = rowData.getString("TMP_DATE");
               }
         %>
        <tr height="25">
          <td class="re01" align="center">&nbsp;<%=sunap_date%></td>
          <td class="re02" align="right">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td>&nbsp;</td>
          <td class="re02" align="right">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td>&nbsp;</td>         
        </tr>
      <% } %>
      </table>        
      </div>  
      
    </td>
  </tr>
</table>

<div class="noprint">
<table width="650" border="0" align="center" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="650" align="center"> 
            <%if(saveFile.equals("")){%>
			<table width="650" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<img src="../img/btn_print.gif" alt="인쇄"  onClick="goPrint()" style="cursor:hand" align="absmiddle">		  
					</td>
				</tr>
			</table>
            <%}%>
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
<div class="noprint">
<%if(!saveFile.equals("1")){%>
<table width="1000">
	<tr height="100">
		<td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
<% } %>
</div>
</form>
</body>
</html>
