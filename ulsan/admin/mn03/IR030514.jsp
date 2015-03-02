<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR030514.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-28
* 프로그램내용	   : 세입세출외현금> 주행세 조회/수정/삭제 > 주된특별징수수납부
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
	List<CommonEntity>spJuheangList =    (List<CommonEntity>)request.getAttribute("page.mn03.spJuheangList");
  
  List<CommonEntity>spIjaList =    (List<CommonEntity>)request.getAttribute("page.mn03.spIjaList");

  CommonEntity iwalData =    (CommonEntity)request.getAttribute("page.mn03.iwalData");

  int jhListSize = 0;
  if (spJuheangList != null) {
    jhListSize = spJuheangList.size();
  } 

  long max_jan = 0L;
  long max_ija = 0L;
%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
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
				factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄  
				factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
				factory.printing.topMargin = 1.0; // 위 여백 사이즈  
				factory.printing.rightMargin = 0.5; // 오른쪽 여백 사이즈  
				factory.printing.bottomMargin = 1.0; // 아래 여백 사이즈  
				factory.printing.Print(true) // 출력하기 
	    }
    </script>

		
</head>

<body bgcolor="#FFFFFF" topmargin="10" leftmargin="20" oncontextmenu="return false">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<table width="713" border="0" cellspacing="0" cellpadding="1">
  <tr> 
    <td height="30"> 
     <div align="center"><b><font size="5"><%=TextHandler.formatDate(request.getParameter("start_date"),"yyyyMMdd","MM");%>월 주행세(발생이자) 주된특별징수 수납부</font></b></div>
    </td>    
  </tr>
</table>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td width="343"><b>O 주행세</b></td>
  </tr>
</table>
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td height="20" align="center" width="11%"> 
      수납일자
    </td>
    <td height="20" align="center" width="32%"> 
      납세의무자
    </td>
    <td height="20" align="center" width="19%"> 
      수입액 
    </td>
    <td height="20" align="center" width="19%"> 
      안분액 
    </td>
    <td height="20" align="center" width="19%"> 
      잔액 
    </td>
  </tr>
	 <% if (iwalData != null ) { %>
	<tr> 
    <td height="2" align="center"> 
      <%=TextHandler.formatDate(iwalData.getString("first_date"),"yyyyMMdd","yyyy.MM.dd")%>
    </td>
    <td height="2" align="left"> 
      &nbsp;이월금
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(iwalData.getString("BASE_MONTH_AMT"))%>&nbsp;
    </td>
    <td height="2" align="center">
      &nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(iwalData.getString("BASE_MONTH_AMT"))%>&nbsp;
    </td>
  </tr>
	<%
	  max_jan = iwalData.getLong("BASE_MONTH_AMT");
  }
		long list_jan = max_jan;
		long temp_amt = 0L;
		long suip_tot = 0L;
		long jigup_tot = 0L;
		long tot_amt = 0L;
	  for (int i=0; spJuheangList != null && i < spJuheangList.size(); i++) {
	  	CommonEntity data = (CommonEntity)spJuheangList.get(i);
			temp_amt =  data.getLong("SUIP_AMT") - data.getLong("JIGUP_AMT");
			list_jan += temp_amt;
	%>
  <tr> 
    <td height="2"> 
      <div align="center"><%=TextHandler.formatDate(data.getString("M060_DATE"),"yyyyMMdd","yyyy.MM.dd")%></div>
    </td>
    <td height="2" align="left"> 
      &nbsp;<%=data.getString("M060_NAPSEJA")%>
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(data.getString("SUIP_AMT"))%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(data.getString("JIGUP_AMT"))%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(list_jan)%>&nbsp;
    </td>
  </tr>
	<%
		suip_tot = suip_tot + data.getLong("SUIP_AMT");
		jigup_tot = jigup_tot + data.getLong("JIGUP_AMT");
    tot_amt = list_jan;
	  }
	%> 
  <% int cnt = 0;
     if (jhListSize < 43 ) {
       cnt = 43-jhListSize;
     }
  for (int y=0; y < cnt;  y++ ) { %>
	<tr> 
    <td height="2"> 
      <div align="center">&nbsp;</div>
    </td>
    <td height="2"> 
      <div align="center"></div>
    </td>
    <td height="2"> 
      <div align="right"></div>
    </td>
    <td height="2"> 
      <div align="right"></div>
    </td>
    <td height="2"> 
      <div align="right"></div>
    </td>
  </tr>
  <% } %>
	<tr style="font-weight:bold"> 
    <td height="2" align="center"> 
      월 계
    </td>
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(max_jan+suip_tot)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(jigup_tot)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(tot_amt)%>&nbsp;
    </td>
  </tr>
</table>
<br>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td width="343"><b>O 발생이자</b></td>
  </tr>
</table>
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td height="20" align="center" width="11%"> 
      일자
    </td>
    <td height="20" align="center" width="32%"> 
      납세의무자
    </td>
    <td height="20" align="center" width="19%"> 
      수입액
    </td>
    <td height="20" align="center" width="19%"> 
      안분액
    </td>
    <td height="20" align="center" width="19%"> 
      잔액
    </td>
  </tr>
		 <% if (iwalData != null ) { %>
  <tr> 
    <td height="2" align="center"> 
      <%=TextHandler.formatDate(iwalData.getString("first_date"),"yyyyMMdd","yyyy.MM.dd")%>
    </td>
    <td height="2" align="left"> 
      &nbsp;이월금
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(iwalData.getString("BASE_MONTH_IJA"))%>&nbsp;
    </td>
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(iwalData.getString("BASE_MONTH_IJA"))%>&nbsp;
    </td>
  </tr>
  <%
	  max_ija = iwalData.getLong("BASE_MONTH_IJA");
  }
		long list_ija = max_ija;
		long temp_ija = 0L;
		long suip_ija = 0L;
		long jigup_ija = 0L;
		long tot_ija = 0L;
	  for (int i=0; spIjaList != null && i < spIjaList.size(); i++) {
	  	CommonEntity ijaData = (CommonEntity)spIjaList.get(i);
			temp_ija =  ijaData.getLong("SUIP_AMT") - ijaData.getLong("JIGUP_AMT");
			list_ija += temp_ija;
	%>
	 <tr> 
    <td height="2" align="center"> 
      <%=TextHandler.formatDate(ijaData.getString("M060_DATE"),"yyyyMMdd","yyyy.MM.dd")%>
    </td>
    <td height="2" align="left"> 
      <%=ijaData.getString("M060_NAPSEJA")%>
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(ijaData.getString("SUIP_AMT"))%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(ijaData.getString("JIGUP_AMT"))%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(list_ija)%>&nbsp;
    </td>
  </tr>
  <%
		suip_ija = suip_ija + ijaData.getLong("SUIP_AMT");
		jigup_ija = jigup_ija + ijaData.getLong("JIGUP_AMT");
    tot_ija = list_ija;
		} 
	%>
 <tr> 
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      &nbsp;
    </td>
  </tr>
	<tr style="font-weight:bold"> 
    <td height="2" align="center"> 
      월 계
    </td>
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(max_ija+suip_ija)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(jigup_ija)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(tot_ija)%>&nbsp;
    </td>
  </tr>
</table>
<br>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td width="343"><b>O 주행세 + 발생이자</b></td>
  </tr>
</table>
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td height="20" align="center" width="11%"> 
      구 분
    </td>
    <td height="20" align="center" width="32%"> 
      &nbsp;
    </td>
    <td height="20" align="center" width="19%"> 
      수입액
    </td>
    <td height="20" align="center" width="19%"> 
      안분액
    </td>
    <td height="20" align="center" width="19%"> 
      잔액
    </td>
  </tr>
  <tr> 
    <td height="2" rowspan="3" align="center"> 
      월 계
    </td>
    <td height="2" align="center"> 
      주행세
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(max_jan+suip_tot)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(jigup_tot)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(list_jan)%>&nbsp;
    </td>
  </tr>
  <tr> 
    <td height="2"align="center">발생이자</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(max_ija+suip_ija)%>&nbsp;</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(jigup_ija)%>&nbsp;</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(list_ija)%>&nbsp;</td>
  </tr>
	 <tr style="font-weight:bold">    
    <td height="2"align="center">소계</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(max_jan+suip_tot+max_ija+suip_ija)%>&nbsp;</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(jigup_tot+jigup_ija)%>&nbsp;</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(list_jan+list_ija)%>&nbsp;</td>
  </tr>
</table>
<div class="noprint">
<br><br>
<table width="713">
	<div align="right">
		<img src="../img/btn_print.gif" alt="인쇄" align="absmiddle" onclick="goPrint()" style="cursor:hand">
	</div>
</table>
</div>
</body>
</html>