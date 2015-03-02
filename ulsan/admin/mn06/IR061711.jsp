<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061711.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 기간별잔액장
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
    response.setHeader("Content-Disposition", "inline;filename=울산시금고예금잔액장(기간별)_"+request.getParameter("acc_date")+".xls"); 
    response.setHeader("Content-Description", "JSP Generated Data");
    charMarkForExcel = "`"; // 문자를 그대로 표시하기 위해
  }
  List<CommonEntity> giganList = (List<CommonEntity>)request.getAttribute("page.mn06.giganList"); //일반회계
  CommonEntity social = (CommonEntity)request.getAttribute("page.mn06.social"); //사회복지
  CommonEntity addInfo = (CommonEntity)request.getAttribute("page.mn06.addReduce"); //세입,세출
  CommonEntity totAmt = (CommonEntity)request.getAttribute("page.mn06.totAmt"); //합계
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
    form.action = "IR061713.etax";
    form.cmd.value = "restMoneyList";
    form.target = "hidFrame";
    frames['hidFrame'].focus(); 
    eSubmit(form);
	}

  function goExcel() {
	  var form = document.sForm;
    form.excelFlag.value = "Y";
    form.action = "IR061711.etax";
    form.cmd.value = "restMoneyList";
    form.target = "_self";
		eSubmit(form);
	}
</script>
</head>

<body bgcolor="#FFFFFF" topmargin="20" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR061711.etax">
<input type="hidden" name="cmd" value="restMoneyList">
<input type="hidden" name="janak_type" value="<%=request.getParameter("janak_type")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="excelFlag" value="N">
<table width="1070" border="0" cellspacing="0" cellpadding="0">
  <tr style="font-weight:bold"> 
    <td align="center" colspan="13" class="line"><b>울산광역시 금고예금 잔액장</b></td>
  </tr>
  <tr> 
    <td align="center" colspan="13" style="font-size:7pt"><%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy-MM-dd")%></td>
  </tr>
</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr>
    <td colspan="4"><b>1. 예금종류별(기간)-울산시청</b></td>
    <td align="right" colspan="9">(단위:원)</td>
  </tr>
</table>
<table width="1070" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td colspan="2" rowspan="2" align="center" width="7%">구 분</td>
    <td rowspan="2" align="center" width="8%">공금예금</td>
    <td rowspan="2" align="center" width="8%">별단예금</td>
    <td rowspan="2" align="center" width="8%">기업베스트</td>
    <td colspan="4" align="center" width="34%">정기예금</td>
    <td colspan="4" align="center" width="34%">환매채권</td>
  </tr>
  <tr> 
    <td align="center">1월~3월미만</td>
    <td align="center">3월~6월미만</td>
    <td align="center">6월~12월미만</td>
    <td align="center">12월이상</td>
    <td align="center">30일~89일</td>
    <td align="center">90일~119일</td>
    <td align="center">120일~179일</td>
    <td align="center">180일이상</td>
  </tr>
  <% long byul_amt = 0L; 
     for (int i=0; giganList != null && i < giganList.size(); i++) {
       CommonEntity giganInfo = (CommonEntity)giganList.get(i);
       if (totAmt != null) {
         if ("F".equals(giganInfo.getString("M360_ACCGUBUN")) || "합 계".equals(giganInfo.getString("M360_ACCNAME"))) {
           byul_amt = totAmt.getLong("M170_CITYSPECIALDEPOSIT");
         } else {
           byul_amt = 0;
         }
       }
  %>
  <% if (("A".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))
         || ("F".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))
        || ("I".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))) {
     } else {
  %>
  <tr>
    <% if ("일반회계".equals(giganInfo.getString("M360_ACCNAME")) || "특별회계".equals(giganInfo.getString("M360_ACCNAME"))
         || "기금".equals(giganInfo.getString("M360_ACCNAME")) || "세입세출외".equals(giganInfo.getString("M360_ACCNAME"))
         || "일상경비".equals(giganInfo.getString("M360_ACCNAME"))
         || "합 계".equals(giganInfo.getString("M360_ACCNAME"))) { %>
    <td colspan="2" align="left"><b><%=giganInfo.getString("M360_ACCNAME")%></b></td>
    <% } else { %>
    <td width="1"> </td>
    <td align="left"><%=giganInfo.getString("M360_ACCNAME")%></td>
    <% } %>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_OFFICIALDEPOSITJANAMT"))%>
</td>
    <td align="right"><%=TextHandler.formatNumber(byul_amt)%>
</td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_MMDABEFOREDAYJANAMT"))%>
</td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT_1"))%>
</td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT_2"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT_3"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT_4"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT_1"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT_2"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT_3"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT_4"))%></td>
  </tr>
  <% if ("E".equals(giganInfo.getString("M360_ACCGUBUN")) && "15".equals(giganInfo.getString("M360_ACCCODE"))) { 
       if (social != null) { 
  %>
  <tr>
    <td width="1"> </td>
    <td align="left"><%=social.getString("M360_ACCNAME")%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_CITYSPECIALDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_MMDABEFOREDAYJANAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT_1"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT_2"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT_3"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT_4"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT_1"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT_2"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT_3"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT_4"))%></td>
  </tr>
  <%   } 
     }%>
  <%   } 
     }
  %>

</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="13"><b>2. 예금종류별(과목)</b></td>

  </tr>
</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <table width="762" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;' bordercolor='#000000' class="basic">
        <tr style="font-weight:bold"> 
          <td colspan="2" align="center" width="10%">구 분</td>
          <td align="center" width="13%">공금예금</td>
          <td align="center" width="13%">별단예금</td>
          <td align="center" width="13%">기업베스트</td>
          <td align="center" width="14%">정기예금</td>
          <td align="center" width="13%">환매채권</td>
          <td align="center" width="10%">금전신탁</td>
          <td align="center" width="14%">합계</td>

        </tr>
        <% byul_amt = 0L;
           long row_tot = 0L;
           long row_social = 0L;
           for (int i=0; giganList != null && i < giganList.size(); i++) {
             CommonEntity giganInfo = (CommonEntity)giganList.get(i);
             row_tot = giganInfo.getLong("M170_OFFICIALDEPOSITJANAMT") + giganInfo.getLong("M170_CITYSPECIALDEPOSIT")
                     + giganInfo.getLong("M170_MMDABEFOREDAYJANAMT") + giganInfo.getLong("M170_DEPOSITBEFOREDAYJANAMT")
                     + giganInfo.getLong("M170_RPBEFOREDAYAMT");
             if (totAmt != null) {
               if ("F".equals(giganInfo.getString("M360_ACCGUBUN")) || "합 계".equals(giganInfo.getString("M360_ACCNAME")) ) {
                 byul_amt = totAmt.getLong("M170_CITYSPECIALDEPOSIT");
               } else {
                 byul_amt = 0;
               }
             }
        %>
        <% if (("A".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))
            || ("F".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))
            || ("I".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))) {
           } else {
        %>
        <tr>
        <% if ("일반회계".equals(giganInfo.getString("M360_ACCNAME")) || "특별회계".equals(giganInfo.getString("M360_ACCNAME"))
            || "기금".equals(giganInfo.getString("M360_ACCNAME")) || "세입세출외".equals(giganInfo.getString("M360_ACCNAME"))
            || "일상경비".equals(giganInfo.getString("M360_ACCNAME"))
            || "합 계".equals(giganInfo.getString("M360_ACCNAME"))) { %>
          <td colspan="2" align="left"><b><%=giganInfo.getString("M360_ACCNAME")%></b></td>
        <% } else { %>
          <td width="1"> </td>
          <td align="left"><%=giganInfo.getString("M360_ACCNAME")%></td>
        <% } %>
          <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(byul_amt)%></td>
          <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_MMDABEFOREDAYJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT"))%></td>
          <td align="right">&nbsp;</td>
          <% if ("F".equals(giganInfo.getString("M360_ACCGUBUN")) || "".equals(giganInfo.getString("M360_ACCGUBUN")) ) { %>
          <td align="right"><%=TextHandler.formatNumber(row_tot + byul_amt)%></td>
          <% } else { %>
          <td align="right"><%=TextHandler.formatNumber(row_tot)%></td>
          <% } %>
        </tr>
        <% if ("E".equals(giganInfo.getString("M360_ACCGUBUN")) && "15".equals(giganInfo.getString("M360_ACCCODE"))) { 
             if (social != null) { 
               row_social = social.getLong("M170_OFFICIALDEPOSITJANAMT") + social.getLong("M170_CITYSPECIALDEPOSIT")
                          + social.getLong("M170_MMDABEFOREDAYJANAMT") + social.getLong("M170_DEPOSITBEFOREDAYJANAMT")
                          + social.getLong("M170_RPBEFOREDAYAMT");
        %>
        <tr>
          <td width="1"> </td>
          <td align="left"><%=social.getString("M360_ACCNAME")%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_CITYSPECIALDEPOSIT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_MMDABEFOREDAYJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT"))%></td>
          <td align="right">&nbsp;</td>
          <td align="right"><%=TextHandler.formatNumber(row_social)%></td>
        </tr>
        <%   } 
           }
        %>
        <%   } 
           }
        %>
      </table>
    </td>
    <td valign="top">
      <table width="300" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;' bordercolor='#000000' class="basic">
        <tr style="font-weight:bold">
          <td colspan="2">주요증감내역</td>
        </tr>
        <tr style="font-weight:bold">
          <td align="center" width="50%">(세입)</td>
          <td align="center" width="50%">(단위:천원)</td>
        </tr>
        <%
           for(int i=0; i<10; i++) {
             if (addInfo != null ) {
        %>
        <tr>
          <td><%=addInfo.getString("M170_JEUNGGAMSEIPHANGMOK_"+(i+1)+"")%></td>
          <td align="right"><%=TextHandler.formatNumber(addInfo.getString("M170_JEUNGGAMSEIPAMT_"+(i+1)+""))%></td>
        </tr>
        <%  }
          } %>
        <tr style="font-weight:bold">
          <td align="center">(세출)</td>
          <td align="center">(단위:천원)</td>
        </tr>
        <%
           for(int i=0; i<10; i++) {
             if (addInfo != null ) {
        %>
        <tr>
          <td><%=addInfo.getString("M170_JEUNGGAMSECHULHANGMOK_"+(i+1)+"")%></td>
          <td align="right"><%=TextHandler.formatNumber(addInfo.getString("M170_JEUNGGAMSECHULAMT_"+(i+1)+""))%></td>
        </tr>
        <%  }
          } %>
        <% if (addInfo != null ) { %>
        <tr>
          <td>전일대비</td>
          <td align="right"><%=TextHandler.formatNumber(addInfo.getLong("M170_JEUNGGAMSEIPAMT_10")-addInfo.getLong("M170_JEUNGGAMSECHULAMT_10"))%></td>
        </tr>        
        <tr>
          <td align="left" rowspan="3"><br>주행세 수입금(보통예금)<br></td>
          <td align="right" rowspan="3"><br><%=TextHandler.formatNumber(addInfo.getString("M170_JUHEANGORDINARYDEPOSIT"))%><br></td>
        </tr>
        <% } %>
      </table>
    </td>
  </tr>
</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="13"><b>3.기타</b></td>
  </tr>
</table>
<table width="1070" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr> 
    <td colspan="2"> 
      <div align="center"><b>구분</b></div>
    </td>
    <td width="107"> 
      <div align="center"><b>후순위금융채</b></div>
    </td>
    <td width="87"> 
      <div align="center"><b></b></div>
    </td>
    <td width="68"> 
      <div align="center"><b></b></div>
    </td>
    <td width="72">&nbsp;</td>
    <td width="76"> 
      <div align="center"><b></b></div>
    </td>
    <td width="93"> 
      <div align="center"><b></b></div>
    </td>
    <td width="80"> 
      <div align="center"><b></b></div>
    </td>
    <td width="166"> 
      <div align="center"><b>합 계</b></div>
    </td>
    <td width="152"> 
      <div align="center"><b></b></div>
    </td>
  </tr>

  <tr> 
    <td colspan="2"> 
      <div align="center">중소육성 </div>
    </td>
    <td width="107"> 
      <div align="right"></div>
    </td>
    <td width="87"> 
      <div align="right"></div>
    </td>
    <td width="68"> 
      <div align="right"></div>
    </td>
    <td width="72">&nbsp;</td>
    <td width="76"> 
      <div align="right"></div>
    </td>
    <td width="93"> 
      <div align="right"></div>
    </td>
    <td width="80">&nbsp;</td>
    <td width="166">&nbsp;</td>
    <td width="152">&nbsp;</td>
  </tr>
</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="13"><b>4.합계</b></td>
  </tr>
</table>
<% if (totAmt != null) { %>
<table width="1070" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td colspan="2" width="3%" align="center">구분</td>
    <td width="10%" align="center">공금예금</td>
    <td width="9%" align="center">별단예금</td>
    <td width="10%" align="center">기업베스트</td>
    <td width="10%" align="center">정기예금</td>
    <td width="9%" align="center">환매채권</td>
    <td width="9%" align="center">보통예금</td>
    <td width="6%" align="center">후순위채</td>
    <td width="10%" align="center">시금고예금합계</td>
    <td width="10%" align="center">주행세(보통예금)</td>
    <td width="12%" align="center">시금고관련예금합계</td>
    <td width="2%" align="center">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="2" align="center">시청</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_CITYSPECIALDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_JEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_DEPOSITJEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_RPJEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_CITYORDINARYDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("SIGUMGO_TOT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_JUHEANGORDINARYDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("SIGUMGO_TOT")+totAmt.getLong("M170_JUHEANGORDINARYDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="2" align="center">울산</td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_ULSANSPECIALDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right">&nbsp;</td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_ULSANORDINARYDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("ULSAN_TOT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("ULSAN_TOT"))%></td>
    <td align="right">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="2" align="center">계</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("M170_CITYSPECIALDEPOSIT")+totAmt.getLong("M170_ULSANSPECIALDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_JEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_DEPOSITJEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_RPJEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("M170_CITYORDINARYDEPOSIT")+totAmt.getLong("M170_ULSANORDINARYDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("SIGUMGO_TOT")+totAmt.getLong("ULSAN_TOT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_JUHEANGORDINARYDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("SIGUMGO_TOT")+totAmt.getLong("M170_JUHEANGORDINARYDEPOSIT")+totAmt.getLong("ULSAN_TOT"))%></td>
    <td align="right">&nbsp;</td>
  </tr>
</table>
<% } %>
<div class="noprint">
<p>&nbsp;</p>
<% if (!"Y".equals(request.getParameter("excelFlag")) ) { %>
<table width="1070" border="0">
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
<iframe name="hidFrame" width="0" height="0"></iframe>
</body>
<% if (!"".equals(SucMsg)) { %>
<script>
alert("<%=SucMsg%>");
self.close();
</script>
<% } %>
</html>
