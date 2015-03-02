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
<%
	
  CommonEntity bigoData = (CommonEntity) request.getAttribute("page.mn07.bigoData");
  CommonEntity ilgyeData = (CommonEntity) request.getAttribute("page.mn07.ilgyeData");
  CommonEntity signData = (CommonEntity) request.getAttribute("page.mn07.signData");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리 시스템</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
  	<script language="javascript">
	  function init() {
	    typeInitialize();
      goPrint();
    }

	  function goPrint() {
      factory.printing.header = ""; // Header에 들어갈 문장  
		  factory.printing.footer = ""; // Footer에 들어갈 문장  
		  factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
		  factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		  factory.printing.topMargin = 1.0; // 위 여백 사이즈  
		  factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		  factory.printing.bottomMargin = 1.0; // 아래 여백 사이즈  
		  factory.printing.Print(true); // 출력하기
	  }

    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
  <form name="sForm" method="post" action="IR070421.etax">
  <input type="hidden" name="cmd" value="jojungNoteList">  
<table width="993" border="0" cellspacing="0" cellpadding="0">
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
	  <td align="center"><font size="3"><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy.MM.dd");%></font></td>
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
	  <td align="center"><font size="3"><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy.MM.dd");%></font></td>
	  <td width="300" align="right"><font size="3">울   산   광   역   시   금   고<br>경남은행 울산시청지점</font></td>
    <td width="100"><img src="../../../report/seal/<%=signData.getString("F_NAME")%>" width="60" height="60" align="right"></td>
  </tr>
</table>
<% } %>
<% } %>
</form>
</body>
</html>