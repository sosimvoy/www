<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061712.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 전일대비잔액장
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%
  if ("Y".equals(request.getParameter("excelFlag")) ) {
    String charMarkForExcel = "";
    response.setContentType("application/vnd.ms-excel;charset=euc-kr");
    response.setHeader("Content-Disposition", "inline;filename=울산시금고예금잔액장(전일대비)_"+request.getParameter("acc_date")+".xls"); 
    response.setHeader("Content-Description", "JSP Generated Data");
    charMarkForExcel = "`"; // 문자를 그대로 표시하기 위해
  }
  List<CommonEntity> daebiList = (List<CommonEntity>)request.getAttribute("page.mn06.daebiList");
  CommonEntity sdInfo = (CommonEntity)request.getAttribute("page.mn06.sdInfo");
  CommonEntity totInfo = (CommonEntity)request.getAttribute("page.mn06.totInfo");
  String SucMsg = (String)request.getAttribute("page.mn06.SucMsg"); //메시지
  if (SucMsg == null ) {
    SucMsg = "";
  }
%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<% if ("Y".equals(request.getParameter("excelFlag")) ) { %>
<meta name=ProgId content=Excel.Sheet>
<style>
  table { table-layout:fixed; }
  tr,td { border:0.5pt solid silver; }
</style>
<% } else { %>
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 8pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 14pt; color: #333333; text-decoration: underline}
@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<%  } %>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function goPrint() {
    var form = document.sForm;
    form.excelFlag.value = "N";
		factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = false; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 1.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 1.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 1.0; // 아래 여백 사이즈  
		factory.printing.Print(true); // 출력하기 
	}

  function goExcel() {
	  var form = document.sForm;
    form.excelFlag.value = "Y";
    form.action = "IR061712.etax";
    form.cmd.value = "restMoneyList";
    form.target = "_self";
		eSubmit(form);
	}
</script>
</head>

<body bgcolor="#FFFFFF" topmargin="10" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR061712.etax">
<input type="hidden" name="cmd" value="restMoneyList">
<input type="hidden" name="janak_type" value="<%=request.getParameter("janak_type")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="excelFlag" value="N">
<table width="1030" border="0" cellspacing="0" cellpadding="0">
  <tr style="font-weight:bold"> 
    <td align="center" colspan="11" class="line">울산광역시 금고예금 잔액장</td>
  </tr>
  <tr> 
    <td height="30" align="center" colspan="11" style="font-size:8pt"><%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy-MM-dd")%></td>
  </tr>
</table>
<table width="1030" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr style="font-weight:bold"> 
    <td colspan="6">1. 예금종류별(과목)</td>
    <td colspan="5" align="right">(단위:원)</td>
  </tr>
</table>
<table width="1030" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td align="center" width="13%">구분</td>
    <td align="center">공금예금</td>
    <td align="center">별단예금</td>
    <td align="center">기업베스트</td>
    <td align="center">정기예금</td>
    <td align="center">환매채권</td>
    <td align="center">금전신탁</td>

    <td align="center">금일합계</td>
    <td align="center">전일합계</td>
    <td colspan="2" align="center">전일대비</td>
  </tr>
  <%  long byul_amt = 0L;
      long yester_byul = 0L;
      for (int i=0; daebiList != null && i < daebiList.size(); i++) {
        CommonEntity rowData = (CommonEntity)daebiList.get(i);
  %>
  <% if (("A".equals(rowData.getString("M360_ACCGUBUN")) && !"00".equals(rowData.getString("M360_ACCCODE")))
         || ("F".equals(rowData.getString("M360_ACCGUBUN")) && !"00".equals(rowData.getString("M360_ACCCODE")))
           || ("I".equals(rowData.getString("M360_ACCGUBUN")) && !"00".equals(rowData.getString("M360_ACCCODE"))) ) {
     } else {
       if ("A".equals(rowData.getString("M360_ACCGUBUN")) ) {
         byul_amt = rowData.getLong("TODAY_BYUL_AMT");
         yester_byul = rowData.getLong("YESTER_BYUL_AMT");
       }
  %>
  <tr>
    <% if ("일반회계".equals(rowData.getString("M360_ACCNAME")) || "특별회계".equals(rowData.getString("M360_ACCNAME"))
         || "기금".equals(rowData.getString("M360_ACCNAME")) || "세입세출외".equals(rowData.getString("M360_ACCNAME"))
         || "합 계".equals(rowData.getString("M360_ACCNAME")) || "일상경비".equals(rowData.getString("M360_ACCNAME")) ) { %>
    <td align="left" style="font-weight:bold"><%=rowData.getString("M360_ACCNAME")%></td>
    <% } else { %>
    <td align="left"><%=rowData.getString("M360_ACCNAME")%></td>
    <% } %>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("TODAY_OFFICIAL_AMT"))%></td>
    <td align="right">
    <% if ("F".equals(rowData.getString("M360_ACCGUBUN")) || "".equals(rowData.getString("M360_ACCGUBUN")) ) { %>
         <%=TextHandler.formatNumber(byul_amt)%>
    <% } else { %>
         <%=TextHandler.formatNumber("0")%>
    <% } %></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("TODAY_MMDA_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("TODAY_JEONGI_AMT"))%></td>

    <td align="right"><%=TextHandler.formatNumber(rowData.getString("TODAY_HWAN_AMT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right">
    <% if ("F".equals(rowData.getString("M360_ACCGUBUN")) || "".equals(rowData.getString("M360_ACCGUBUN")) ) { %>
    <%=TextHandler.formatNumber(rowData.getLong("TODAY_TOTAL_AMT") + byul_amt)%>
    <% } else { %>
    <%=TextHandler.formatNumber(rowData.getLong("TODAY_TOTAL_AMT"))%>
    <% } %>
    </td>
    <td align="right">
    <% if ("F".equals(rowData.getString("M360_ACCGUBUN")) || "".equals(rowData.getString("M360_ACCGUBUN")) ) { %>
    <%=TextHandler.formatNumber(rowData.getLong("YESTER_TOTAL_AMT") + yester_byul)%>
    <% } else { %>
    <%=TextHandler.formatNumber(rowData.getLong("YESTER_TOTAL_AMT"))%>
    <% } %>
    </td>
    <td colspan="2" align="right">
    <% if ("F".equals(rowData.getString("M360_ACCGUBUN")) || "".equals(rowData.getString("M360_ACCGUBUN")) ) { %>
    <%=TextHandler.formatNumber(rowData.getLong("TODAY_TOTAL_AMT") + byul_amt - rowData.getLong("YESTER_TOTAL_AMT") - yester_byul)%>
    <% } else { %>
    <%=TextHandler.formatNumber(rowData.getLong("TODAY_TOTAL_AMT") - rowData.getLong("YESTER_TOTAL_AMT"))%>
    <% } %>
    </td>
  </tr>
  <% if ("E".equals(rowData.getString("M360_ACCGUBUN")) && "15".equals(rowData.getString("M360_ACCCODE"))) { 
       if (sdInfo != null) { 
  %>
  <tr>
    <td align="left"><%=sdInfo.getString("M360_ACCNAME")%></td>
    <td align="right"><%=TextHandler.formatNumber(sdInfo.getString("TODAY_OFFICIAL_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(sdInfo.getString("0"))%></td>
    <td align="right"><%=TextHandler.formatNumber(sdInfo.getString("TODAY_MMDA_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(sdInfo.getString("TODAY_JEONGI_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(sdInfo.getString("TODAY_HWAN_AMT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(sdInfo.getString("TODAY_TOTAL_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(sdInfo.getString("YESTER_TOTAL_AMT"))%></td>
    <td colspan="2" align="right"><%=TextHandler.formatNumber(sdInfo.getLong("TODAY_TOTAL_AMT") - sdInfo.getLong("YESTER_TOTAL_AMT"))%></td>
  </tr>
  <%   } 
     }%>
  <%   } 
     }%>

</table>
<table width="1030" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr style="font-weight:bold"> 
    <td width="343">2.기타</td>
    <td width="685"> 
      <div align="right"></div>
    </td>
  </tr>
</table>
<table width="1030" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td align="center" width="9%">구분</td>
    <td width="92">&nbsp;</td>
    <td width="95">&nbsp;</td>
    <td width="71">&nbsp;</td>
    <td width="79">&nbsp;</td>
    <td width="82">&nbsp;</td>
    <td width="101">&nbsp;</td>
    <td width="83">&nbsp;</td>
    <td width="84" align="center">금일합계</td>
    <td width="84" align="center">전일합계</td>
    <td width="93" align="center">전일대비</td>
  </tr>
  <tr> 
    <td align="center">중소육성(금융채)</td>
    <td align="right">&nbsp;
    </td>
    <td> 
      <div align="right"></div>
    </td>
    <td> 
      <div align="right"></div>
    </td>
    <td> 
      <div align="right"></div>
    </td>
    <td> 
      <div align="right"></div>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <% if (totInfo != null) { %>
  <tr> 
    <td align="center">주행세(보통예금)</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_RUN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("YESTER_RUN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getLong("TODAY_RUN_AMT") - totInfo.getLong("YESTER_RUN_AMT"))%></td>
  </tr>
  <% } %>
</table>
<table width="1030" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr style="font-weight:bold"> 
    <td width="343">3.합계</td>
    <td width="685"> 
      <div align="right"></div>
    </td>
  </tr>
</table>
<table width="1030" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td align="center" width="6%">구분
</td>
    <td align="center">공금예금</td>
    <td align="center">별단예금</td>
    <td align="center">기업베스트</td>
    <td align="center">정기예금</td>
    <td align="center">환매채권</td>
    <td align="center">보통예금</td>
    <td align="center">주행세(보통예금)
</td>
    <td align="center">시금고예금합계</td>
    <td align="center">전일시금고예금합계</td>
    <td align="center">전일대비</td>
  </tr>
  <% if (totInfo != null) { %>
  <tr> 
    <td align="left">시청지점</td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_OFFICIAL_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_BYUL_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_MMDA_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_JEONGI_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_HWAN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_BOTONG_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_RUN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_TOTAL_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("YESTER_TOTAL_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getLong("TODAY_TOTAL_AMT") - totInfo.getLong("YESTER_TOTAL_AMT"))%></td>
  </tr>
  <tr> 
    <td align="left">울산영업부</td>
    <td>&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_ULSAN_BYUL"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right">&nbsp;</td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_ULSAN_BOTONG"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_ULSAN_TOT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("YESTER_ULSAN_TOT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getLong("TODAY_ULSAN_TOT") - totInfo.getLong("YESTER_ULSAN_TOT"))%></td>
  </tr>
  <tr> 
    <td align="left">계</td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_OFFICIAL_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getLong("TODAY_BYUL_AMT") + totInfo.getLong("TODAY_ULSAN_BYUL"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_MMDA_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_JEONGI_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_HWAN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getLong("TODAY_BOTONG_AMT") + totInfo.getLong("TODAY_ULSAN_BOTONG"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getString("TODAY_RUN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getLong("TODAY_TOTAL_AMT") + totInfo.getLong("TODAY_ULSAN_TOT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getLong("YESTER_TOTAL_AMT") + totInfo.getLong("YESTER_ULSAN_TOT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totInfo.getLong("TODAY_TOTAL_AMT") + totInfo.getLong("TODAY_ULSAN_TOT") - (totInfo.getLong("YESTER_TOTAL_AMT") + totInfo.getLong("YESTER_ULSAN_TOT")))%></td>
  </tr>
  <% } %>
</table>
<div class="noprint">
<p>&nbsp;</p>
<% if (!"Y".equals(request.getParameter("excelFlag")) ) { %>
<table width="1030" border="0">
  <tr>
    <td align="right">
    <img src="../img/btn_excel.gif" alt="엑셀" align="absmiddle" onclick="goExcel()" style="cursor:hand">
		<img src="../img/btn_print.gif" alt="인쇄" align="absmiddle" onclick="goPrint()" style="cursor:hand">
    </td>
  </tr>
</table>
<% } %>
</div>

</form>
</body>
<% if (!"".equals(SucMsg)) { %>
<script>
alert("<%=SucMsg%>");
self.close();
</script>
<% } %>
</html>
