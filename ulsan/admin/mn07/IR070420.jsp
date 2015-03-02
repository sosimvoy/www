<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR070410.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-12
* 프로그램내용	   : 일계/보고서 > 세입일계표조정내역조회 
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
	
  CommonEntity bigoData = (CommonEntity) request.getAttribute("page.mn07.bigoData");
  CommonEntity ilgyeData = (CommonEntity) request.getAttribute("page.mn07.ilgyeData");
  CommonEntity signData = (CommonEntity) request.getAttribute("page.mn07.signData");
	
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

	String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date)) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

  String acc_year = request.getParameter("acc_year");
	if ("".equals(acc_year)) {
    acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	}


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리 시스템</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
	  function init() {
	    typeInitialize();
    }

    /*
	  function goPrint() {
      var form = document.sForm;
	    form.action = "IR070421.etax";
      form.search_yn.value = "Y";
	    form.cmd.value = "dailyBigoList"; 
      form.target = "printFrame";
	    eSubmit(form);
	  }*/
	

	  function goSearch() {
      /*
	    var form = document.sForm;
	    form.action = "IR070420.etax";
      form.search_yn.value = "Y";
	    form.cmd.value = "dailyBigoList"; 
	    eSubmit(form);
      */
      
      var form = document.sForm;
      var acc_year = form.acc_year.value;
      var acc_date = form.acc_date.value;
      var report_gubun = form.report_gubun.value;
      var pageurl = "/admin/oz51/IR070420.jsp?acc_year="+acc_year+"&acc_date="+acc_date+"&report_gubun="+report_gubun;
      //document.printFrame.location.href="/admin/oz51/IR070420.jsp?acc_year="+acc_year+"&acc_date="+acc_date+"&report_gubun="+report_gubun;
      funcOpenWindow(form,'post','NeWreport',pageurl , 10,10, 1024, 1000, 0, 0, 0, 0, 1);
      
	  }

    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
  <form name="sForm" method="post" action="IR070420.etax">
  <input type="hidden" name="cmd" value="jojungNoteList">   
  <input type="hidden" name="search_yn" value="<%=request.getParameter("search_yn")%>">
  
<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr> 
	<td width="18">&nbsp;</td>
	<td width="975" height="40"><img src="../img/title6_34.gif"></td>
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
				  보고서종류&nbsp;
				  <select name="report_gubun" iValue="<%=request.getParameter("report_gubun")%>">
					<option value="1">일반회계조정내역</option>
					<option value="2">차량등록사업소조정내역</option>
				  </select>
				  <span style="width:50px"></span>
				  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
				  회계연도&nbsp;
				  <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
					<option value="<%=this_year%>"><%=this_year%></option>
					<option value="<%=last_year%>"><%=last_year%></option>
				  </select>
				  <span style="width:10px"></span>
				  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
				  회계일자&nbsp;<input type="text" value="<%=acc_date%>" class="box3" name="acc_date" size="8" userType="date">
				   <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand">
				</td>
				<td width="200">
				<img src="../img/btn_search.gif" alt="조회" onClick="goSearch()" style="cursor:hand" align="absmiddle">
				<!--<img src="../img/btn_print.gif" alt="인쇄" onClick="goPrint()" style="cursor:hand" align="absmiddle">-->
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
<tr>
	<td width="18">&nbsp;</td>
	<td width="975"> 
  <% if (bigoData != null && bigoData.size() > 0) { %>
  <% if ("1".equals(request.getParameter("report_gubun"))) { %>
	<!-------- 표 시작 ----->
<table width="993" border="0" cellspacing="2" cellpadding="3">
<tr>
	<td align="center" style="font-weight:bold"><font size="4"><%=acc_year%>년 일반회계 세입일계표 조정내역</font></td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0" class="list">
<tr>
	<td colspan="3" width="25%">수납기준항목</td>
	<td rowspan="2" width="11%">이체예정일</td>
	<td rowspan="2">미수령액</td>
	<td rowspan="2">현재잔액</td>
	<td rowspan="2">일계잔액</td>
	<td rowspan="2">공금예금<br>통장잔액</td>
</tr>
<tr>
	<td>세목</td>
	<td>기간</td>
	<td>형태</td>
</tr>
<tr>
	<td rowspan="2">차량취득세</td>
	<td>금결원</td>
	<td>카드</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_CAR_AGODATE_GC01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_CAR_SUM_GC01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>금결원 외</td>
	<td>카드</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_CAR_AGODATE_GC02"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_CAR_SUM_GC02"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>

<tr>
	<td rowspan="3">구군청 발행 시세</td>
	<td>금결원</td>
	<td>일반</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_BAL_AGODATE_GP01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_BAL_SUM_GP01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>금결원</td>
	<td>카드</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_BAL_AGODATE_GC01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_BAL_SUM_GC01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td class="right">실제잔액(<%=TextHandler.formatNumber(ilgyeData.getLong("GONGGEUMJANAMT")-bigoData.getLong("IL_MI_AMT")-ilgyeData.getLong("AMTSURPLUS"))%>)</td>
</tr>
<tr>
	<td>금결원 외</td>
	<td>카드</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_BAL_AGODATE_GC02"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_BAL_SUM_GC02"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td class="right">잉여금(<%=TextHandler.formatNumber(ilgyeData.getString("AMTSURPLUS"))%>)</td>
</tr>
<tr>
	<td colspan="3">합계</td>
	<td>&nbsp;</td>
	<td class="right"><%=TextHandler.formatNumber(bigoData.getString("IL_MI_AMT"))%></td>
	<td class="right"><%=TextHandler.formatNumber(ilgyeData.getLong("AMTJAN")-bigoData.getLong("IL_MI_AMT"))%></td>
	<td class="right"><%=TextHandler.formatNumber(ilgyeData.getString("AMTJAN"))%></td>
	<td class="right"><%=TextHandler.formatNumber(ilgyeData.getLong("GONGGEUMJANAMT")-bigoData.getLong("IL_MI_AMT"))%></td>
</tr>
</table>
<!-------- 표 끝 ----->
	</td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr>
	  <td width="400"><font size="3">&nbsp;&nbsp;&nbsp;울산광역시 세입징수관 귀하</font></td>
	  <td align="center"><font size="3"><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy.MM.dd")%></font></td>
	  <td width="300" align="right"><font size="3">울   산   광   역   시   금   고<br>경남은행 울산시청지점</font></td>
    <td width="100"><img src="../../../report/seal/<%=signData.getString("F_NAME")%>" width="60" height="60" align="right"></td>
  </tr>
</table>
<% } else if ("2".equals(request.getParameter("report_gubun"))) { %>
<table width="993" border="0" cellspacing="2" cellpadding="3">
<tr>
	<td align="center" style="font-weight:bold"><font size="4"><%=acc_year%>년 차량등록사업소 세입일계표 조정내역</font></td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0" class="list">
<tr>
	<td colspan="3" width="25%">수납기준항목</td>
	<td rowspan="2" width="11%">이체예정일</td>
	<td rowspan="2">미수령액</td>
	<td rowspan="2">이체완료된<br>세입누계</td>
	<td rowspan="2">총세입누계</td>
</tr>
<tr>
	<td>세목</td>
	<td>기간</td>
	<td>형태</td>
</tr>
<tr>
	<td rowspan="2">차량취득세</td>
	<td>금결원</td>
	<td>카드</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_CAR_AGODATE_GC01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_CAR_SUM_GC01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>금결원 외</td>
	<td>카드</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_CAR_AGODATE_GC02"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_CAR_SUM_GC02"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>

<tr>
  <td rowspan="2">차량농특세</td>
	<td>금결원</td>
	<td>카드</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_NONG_AGODATE_GC01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_NONG_SUM_GC01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>금결원 외</td>
	<td>카드</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_NONG_AGODATE_GC02"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_NONG_SUM_GC02"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td colspan="3">합계</td>
	<td>&nbsp;</td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("CAR_MI_AMT"))%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(ilgyeData.getLong("TODAYTOT")-bigoData.getLong("CAR_MI_AMT"))%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(ilgyeData.getString("TODAYTOT"))%></td>
</tr>
</table>
<!-------- 표 끝 ----->
	</td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr>
	  <td width="400"><font size="3">&nbsp;&nbsp;&nbsp;울산광역시 차량등록사업소장 귀하</font></td>
	  <td align="center"><font size="3"><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy.MM.dd")%></font></td>
	  <td width="300" align="right"><font size="3">울   산   광   역   시   금   고<br>경남은행 울산시청지점</font></td>
    <td width="100"><img src="../../../report/seal/<%=signData.getString("F_NAME")%>" width="60" height="60" align="right"></td>
  </tr>
</table>
<% } %>
<% } %>
<table>
  <tr>
	  <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
<iframe name="printFrame" width="0" height="0"></iframe>
</form>
</body>
</html>