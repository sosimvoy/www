<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR030515.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-28
* 프로그램내용	   : 세입세출외현금 > 주행세 조회/수정/삭제 - 일계표
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>

<%
	CommonEntity juhangDayView =    (CommonEntity)request.getAttribute("page.mn03.juhangDayView");

	CommonEntity sealState =    (CommonEntity)request.getAttribute("page.mn03.sealState");
%>

<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}

 @media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
 <script language="javascript">
		function init() {
			typeInitialize();
		}
    
	function goPrint() {
		factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 7.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 0.5; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 1.0; // 아래 여백 사이즈  
		factory.printing.Print(true) // 출력하기 
	}
 </script>
</head>

<body bgcolor="#FFFFFF" topmargin="40" leftmargin="15" oncontextmenu="return false">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
	<form name="sForm" method="post" action="IR030515.etax">
    <input type="hidden" name="cmd" value="juhangDayView">
 <% if (juhangDayView != null ) { %>
<table width="1037" border="0" cellspacing="0" cellpadding="1">
  <tr> 
    <td height="60"> 
     <div align="center"><b>주행세 징수 및 배분 일계표(<%=juhangDayView.getString("M070_YEAR")%>)</b></div>
    </td>
  </tr>
</table>
<table width="1038" border="1" cellspacing="0" cellpadding="1" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr> 
    <td colspan="2" height="6" rowspan="2" width="8%" align="center"><b>구분</b></td>
    <td rowspan="2" height="6" align="center" width="10%"><b>전일잔액</b></td>
    <td colspan="4" height="3" align="center" width="36%"><b>징수</b></td>
    <td colspan="4" height="3" align="center" width="36%"><b>배분</b></td>
    <td height="3" rowspan="2" align="center" width="10%"><b>잔액</b></td>
  </tr>
  <tr> 
    <td height="3" align="center">전일누계</td>
    <td height="3" align="center"> 금일수입</td>
    <td height="3" align="center">과오납<br>반납</td>
		<td height="3" align="center">총액</td>
    <td height="3" align="center">전일누계</td>
    <td height="3" align="center">당일지급</td>
    <td height="3" align="center">반납액</td>
    <td height="3" align="center">총액</td>
  </tr>
  <%
    long jing_so_chong = 0L;
    long bae_so_chong = 0L;
    long tot_amt = 0L;
    jing_so_chong = juhangDayView.getLong("SP_JEON_NU") + juhangDayView.getLong("SP_DANG_NU") - juhangDayView.getLong("SP_GWAO_NU");
    bae_so_chong = juhangDayView.getLong("SP_JEON_BAE") + juhangDayView.getLong("SP_DANG_BAE") - juhangDayView.getLong("SP_BAN_BAE");
    tot_amt = juhangDayView.getLong("SP_JAN") + juhangDayView.getLong("SP_DANG_NU") - juhangDayView.getLong("SP_GWAO_NU") - juhangDayView.getLong("SP_DANG_BAE") + juhangDayView.getLong("SP_BAN_BAE");
  %>
  <tr> 
    <td colspan="2" height="3"> 
      <div align="center">소계</div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("SP_JAN"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("SP_JEON_NU"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("SP_DANG_NU"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("SP_GWAO_NU"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(jing_so_chong)%></div>
    </td>
    <td height="3" align="right"> 
      <%=TextHandler.formatNumber(juhangDayView.getString("SP_JEON_BAE"))%>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("SP_DANG_BAE"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("SP_BAN_BAE"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(bae_so_chong)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(jing_so_chong-bae_so_chong)%></div>
    </td>
  </tr>
  <%
    long sp_jing_so_cnt = 0L;
    long sp_bae_so_cnt = 0L;
    long sp_tot_cnt = 0L;
    sp_jing_so_cnt = juhangDayView.getLong("M070_SPREDAYTOTALCNT") + juhangDayView.getLong("M070_SDAYSUIPSUMCNT") + juhangDayView.getLong("M070_SDAYGWAONAPSUMCNT");
    sp_bae_so_cnt = juhangDayView.getLong("M070_SPREDAYDIVIDETOTALCNT") + juhangDayView.getLong("M070_SDAYJIGUPSUMCNT") + juhangDayView.getLong("M070_SDAYBANNAPSUMCNT");
    sp_tot_cnt = juhangDayView.getLong("SP_CNT") + juhangDayView.getLong("M070_SDAYSUIPSUMCNT") + juhangDayView.getLong("M070_SDAYGWAONAPSUMCNT") + juhangDayView.getLong("M070_SDAYJIGUPSUMCNT") + juhangDayView.getLong("M070_SDAYBANNAPSUMCNT");
  %>
  <tr> 
    <td rowspan="2" align="center"> 
      특별<br>
        징수<br>
        의무자
    </td>
    <td height="3" align="center">건수 </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("SP_CNT"))%></div>
    </td>
     <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SPREDAYTOTALCNT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYSUIPSUMCNT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYGWAONAPSUMCNT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(sp_jing_so_cnt)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SPREDAYDIVIDETOTALCNT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYJIGUPSUMCNT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYBANNAPSUMCNT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(sp_bae_so_cnt)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(sp_jing_so_cnt+sp_bae_so_cnt)%></div>
    </td>
  </tr>
  <%
    long sp_jing_so_amt = 0L;
    long sp_bae_so_amt = 0L;
    long sp_tot_amt = 0L;
    sp_jing_so_amt = juhangDayView.getLong("M070_SPREDAYTOTALAMT") + juhangDayView.getLong("M070_SDAYSUIPSUMAMT") - juhangDayView.getLong("M070_SDAYGWAONAPSUMAMT");
    sp_bae_so_amt = juhangDayView.getLong("M070_SPREDAYDIVIDETOTALAMT") + juhangDayView.getLong("M070_SDAYJIGUPSUMAMT") - juhangDayView.getLong("M070_SDAYBANNAPSUMAMT");
    sp_tot_amt = juhangDayView.getLong("M070_SPREDAYAMT") + juhangDayView.getLong("M070_SDAYSUIPSUMAMT") - juhangDayView.getLong("M070_SDAYGWAONAPSUMAMT") - juhangDayView.getLong("M070_SDAYJIGUPSUMAMT") + juhangDayView.getLong("M070_SDAYBANNAPSUMAMT");
  %>
  <tr> 
    <td height="3">금액 </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SPREDAYAMT"))%></div>
    </td>
     <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SPREDAYTOTALAMT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYSUIPSUMAMT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYGWAONAPSUMAMT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(sp_jing_so_amt)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SPREDAYDIVIDETOTALAMT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYJIGUPSUMAMT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYBANNAPSUMAMT"))%></div>
    </td> 
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(sp_bae_so_amt)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(sp_jing_so_amt-sp_bae_so_amt)%></div>
    </td>
  </tr>
  <%
    long jing_so_ija = 0L;
    long bae_so_ija = 0L;
    long tot_ija = 0L;
    jing_so_ija = juhangDayView.getLong("M070_SPREDAYTOTALINTEREST") + juhangDayView.getLong("M070_SDAYSUIPSUMINTEREST") - juhangDayView.getLong("M070_SDAYGWAONAPSUMINTEREST");
    bae_so_ija = juhangDayView.getLong("M070_SPREDAYDIVIDETOTALINTERES") + juhangDayView.getLong("M070_SDAYJIGUPSUMINTEREST") - juhangDayView.getLong("M070_SDAYBANNAPSUMINTEREST");
    tot_ija = juhangDayView.getLong("M070_SPREDAYINTERESTAMT") + juhangDayView.getLong("M070_SDAYSUIPSUMINTEREST") - juhangDayView.getLong("M070_SDAYGWAONAPSUMINTEREST") - juhangDayView.getLong("M070_SDAYJIGUPSUMINTEREST") + juhangDayView.getLong("M070_SDAYBANNAPSUMINTEREST");
  %>
  <tr> 
    <td height="8"> 
      <div align="center">이자<br>
        수입<br>
      </div>
    </td>
    <td height="3">금액 </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SPREDAYINTERESTAMT"))%></div>
    </td>
     <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SPREDAYTOTALINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYSUIPSUMINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYGWAONAPSUMINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(jing_so_ija)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SPREDAYDIVIDETOTALINTERES"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYJIGUPSUMINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_SDAYBANNAPSUMINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(bae_so_ija)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(tot_ija)%></div>
    </td>
  </tr>
  <%
    long bs_jing_amt = 0L;
    long bs_bae_amt = 0L;
    long bs_tot_amt = 0L;
    bs_jing_amt = juhangDayView.getLong("BS_JEON_NU") + juhangDayView.getLong("BS_DANG_NU") - juhangDayView.getLong("BS_GWAO_NU");
    bs_bae_amt = juhangDayView.getLong("BS_JEON_BAE") + juhangDayView.getLong("BS_DANG_BAE") - juhangDayView.getLong("BS_BAN_BAE");
    bs_tot_amt = juhangDayView.getLong("BS_JAN") + juhangDayView.getLong("BS_DANG_NU") - juhangDayView.getLong("BS_GWAO_NU") - juhangDayView.getLong("BS_DANG_BAE") + juhangDayView.getLong("BS_BAN_BAE");
  %>
	<tr> 
    <td height="8" colspan="2"> 
      <div align="center">소계
      </div>
    </td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("BS_JAN"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("BS_JEON_NU"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("BS_DANG_NU"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("BS_GWAO_NU"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_jing_amt)%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("BS_JEON_BAE"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("BS_DANG_BAE"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("BS_BAN_BAE"))%></td> 
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_bae_amt)%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_tot_amt)%></td>
  </tr>
  <%
    long bs_jing_so_cnt = 0L;
    long bs_bae_so_cnt = 0L;
    long bs_tot_cnt = 0L;
    bs_jing_so_cnt = juhangDayView.getLong("M070_PREDAYTOTALCNT") + juhangDayView.getLong("M070_DAYSUIPSUMCNT") + juhangDayView.getLong("M070_DAYGWAONAPSUMCNT");
    bs_bae_so_cnt = juhangDayView.getLong("M070_PREDAYDIVIDETOTALCNT") + juhangDayView.getLong("M070_DAYJIGUPSUMCNT") + juhangDayView.getLong("M070_DAYBANNAPSUMCNT");
    bs_tot_cnt = juhangDayView.getLong("BS_CNT") + juhangDayView.getLong("M070_DAYSUIPSUMCNT") + juhangDayView.getLong("M070_DAYGWAONAPSUMCNT") + juhangDayView.getLong("M070_DAYJIGUPSUMCNT") + juhangDayView.getLong("M070_DAYBANNAPSUMCNT");
  %>
  <tr> 
    <td rowspan="2" height="8"> 
      <div align="center">주된<br>
        특별징수<br>
        의무자</div>
    </td>
    <td height="3">건수 </td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("BS_CNT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_PREDAYTOTALCNT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYSUIPSUMCNT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYGWAONAPSUMCNT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_jing_so_cnt)%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_PREDAYDIVIDETOTALCNT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYJIGUPSUMCNT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYBANNAPSUMCNT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_bae_so_cnt)%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_tot_cnt)%></td>
  </tr>
  <%
    long bs_jing_so_amt = 0L;
    long bs_bae_so_amt = 0L;
    long bs_tot_so_amt = 0L;
    bs_jing_so_amt = juhangDayView.getLong("M070_PREDAYTOTALAMT") + juhangDayView.getLong("M070_DAYSUIPSUMAMT") - juhangDayView.getLong("M070_DAYGWAONAPSUMAMT");
    bs_bae_so_amt = juhangDayView.getLong("M070_PREDAYDIVIDETOTALAMT") + juhangDayView.getLong("M070_DAYJIGUPSUMAMT") - juhangDayView.getLong("M070_DAYBANNAPSUMAMT");
    bs_tot_so_amt = juhangDayView.getLong("M070_PREDAYAMT") + juhangDayView.getLong("M070_DAYSUIPSUMAMT") - juhangDayView.getLong("M070_DAYGWAONAPSUMAMT") - juhangDayView.getLong("M070_DAYJIGUPSUMAMT") + juhangDayView.getLong("M070_DAYBANNAPSUMAMT");
  %>
  <tr> 
    <td height="3">금액 </td>
	  <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_PREDAYAMT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_PREDAYTOTALAMT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYSUIPSUMAMT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYGWAONAPSUMAMT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_jing_so_amt)%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_PREDAYDIVIDETOTALAMT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYJIGUPSUMAMT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYBANNAPSUMAMT"))%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_bae_so_amt)%></td>
    <td height="3" align="right">&nbsp;<%=TextHandler.formatNumber(bs_tot_so_amt)%></td>
  </tr>
  <%
    long bs_jing_so_ija = 0L;
    long bs_bae_so_ija = 0L;
    long bs_tot_ija = 0L;
    bs_jing_so_ija = juhangDayView.getLong("M070_PREDAYTOTALINTEREST") + juhangDayView.getLong("M070_DAYSUIPSUMINTEREST") - juhangDayView.getLong("M070_DAYGWAONAPSUMINTEREST");
    bs_bae_so_ija = juhangDayView.getLong("M070_PREDAYDIVIDETOTALINTEREST") + juhangDayView.getLong("M070_DAYJIGUPSUMINTEREST") - juhangDayView.getLong("M070_DAYBANNAPSUMINTEREST");
    bs_tot_ija = juhangDayView.getLong("M070_PREDAYINTERESTAMT") + juhangDayView.getLong("M070_DAYSUIPSUMINTEREST") - juhangDayView.getLong("M070_DAYGWAONAPSUMINTEREST") - juhangDayView.getLong("M070_DAYJIGUPSUMINTEREST") + juhangDayView.getLong("M070_DAYBANNAPSUMINTEREST");
  %>
  <tr> 
    <td height="8"> 
      <div align="center">이자<br>
        수입<br>
      </div>
    </td>
    <td height="3">금액 </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_PREDAYINTERESTAMT"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_PREDAYTOTALINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYSUIPSUMINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYGWAONAPSUMINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(bs_jing_so_ija)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_PREDAYDIVIDETOTALINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYJIGUPSUMINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(juhangDayView.getString("M070_DAYBANNAPSUMINTEREST"))%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(bs_bae_so_ija)%></div>
    </td>
    <td height="3"> 
      <div align="right"><%=TextHandler.formatNumber(bs_tot_ija)%></div>
    </td>
  </tr>
</table>
<table width="1036" border="0" cellspacing="0" cellpadding="4" class="basic">
  <tr> 
    <td height="25" width="1036"> 
      <div align="center">위와같이 보고함<br>
       <div align="center"><%=TextHandler.formatDate(request.getParameter("end_date"), "yyyyMMdd", "yyyy년 MM월 dd일")%></div></div>
    </td>
  </tr>
</table>
<table width="1036" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr>
    <td width="351" height="60">울산광역시 주행세 수입금 출납원 귀하 <br>
    </td>
    <td width="625" height="60"> 
      <div align="right">경남은행 울산시청지점 <br>울 산 광 역 시 금 고 </div>
		<td width="60" height="60"> 	 
			 <% if (sealState != null && sealState.size() > 0) { %>
					<img src="/report/seal/<%=sealState.getString("M340_FNAME")%>" width="60" height="60">
	  	<% }%>
    </td>
    </td>
  </tr>
</table>
<%}%>
<br><br>
<table width="1037">
	<div class="noprint">
	<div align="right">
		<img src="../img/btn_print.gif" alt="인쇄" align="absmiddle" onclick="goPrint()" style="cursor:hand">
	</div>
	</div>
</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
<% if ("0".equals(juhangDayView.getString("CNT")) ) { %>
<script>
    alert("주행세 일일마감이 이뤄지지 않았습니다. 마감 후 수납부 버튼을 선택하세요.");
    self.close();
</script>
<% } %>
</body>
</html>
