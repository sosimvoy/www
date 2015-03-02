<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR060511.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금예탁통지서팝업
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  List<CommonEntity> bankDepViewList  = (List<CommonEntity>)request.getAttribute("page.mn06.bankDepViewList");
	CommonEntity totAmt  = (CommonEntity)request.getAttribute("page.mn06.totAmt");
  CommonEntity sealInfo  = (CommonEntity)request.getAttribute("page.mn06.sealInfo");
  CommonEntity manager  = (CommonEntity)request.getAttribute("page.mn06.manager");

	int bankDepViewListSize = 0;
	if (bankDepViewList != null ) {
		bankDepViewListSize = bankDepViewList.size();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 12pt; color: #333333}
.num   {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.line  {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11pt; color: #333333; text-decoration: underline}

@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function goList() {
		var form = document.sForm;
		window.open('IR060512.etax', 'baePop', 'height=600,width=760,top=10,left=200,scrollbars=yes');
		form.cmd.value="bankDepDetailList";
		form.action = "IR060512.etax";
		form.target = "baePop";
		eSubmit(form);
  }

	function goPrint() {
    factory.printing.header = ""; // Header에 들어갈 문장  
		factory.printing.footer = ""; // Footer에 들어갈 문장  
		factory.printing.portrait = true; // false 면 가로인쇄, true 면 세로 인쇄  
		factory.printing.leftMargin = 2.0; // 왼쪽 여백 사이즈  
		factory.printing.topMargin = 2.0; // 위 여백 사이즈  
		factory.printing.rightMargin = 1.0; // 오른쪽 여백 사이즈  
		factory.printing.bottomMargin = 0.5; // 아래 여백 사이즈  
		factory.printing.Print(true) // 출력하기 
	}
</script>
</head>

<body bgcolor="#FFFFFF" topmargin="20" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<% if (bankDepViewListSize > 0) { %>
<form name="sForm" method="post" action="IR060512.etax">
<input type="hidden" name="cmd" value="bankDepDetailList">
<input type="hidden" name="reg_date" value="<%=request.getParameter("reg_date")%>">
<input type="hidden" name="doc_no" value="<%=request.getParameter("doc_no")%>">
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr> 
    <td colspan="7" height="21">
      <table width="711" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
        <tr> 
          <td colspan="7" height="21"> 
            <table width="690" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="60"> 
                  <div align="center"><b><font size="6">울 산 광 역 시</font></b></div>
                </td>
              </tr>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="6" class="basic" align="center">
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225">&nbsp;</td>
              </tr>
              <tr> 
                <td width="484">문서번호 : <%=request.getParameter("doc_no")%></td>
                <td width="225"> 
                  <div align="left">시행일 : <%=TextHandler.formatDate(request.getParameter("reg_date"), "yyyyMMddd", "yyyy. MM. dd")%></div>
                </td>
              </tr>
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225"> 
                  <div align="left"></div>
                </td>
              </tr>
              <tr> 
                <td width="484">수신 : 경남은행 울산시청 지점장</td>
                <td width="225"> 
                  <div align="left">발 신 : 울산광역시장 </div>
                </td>
              </tr>
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
              <tr> 
                <td width="484">제목 : <%=request.getParameter("fis_year")%>년 일반회계 유휴자금 예탁</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
							<tr>
							  <td colspan="2"><hr color="black"></td>
							</tr>
              <tr> 
                <td colspan="2" height="50"> 
                  <div align="left"><span style="width:43"></span><%=request.getParameter("fis_year")%>년 일반회계 운영자금 중 유휴자금을 다음과 같이 예탁 하고자 하오니 처리하여 주시기 바랍니다.</div>
                </td>
              </tr>
              <tr> 
                <td colspan="2"> 
                  <div align="left"> 
                    <table width="500" border="0" cellspacing="1" cellpadding="3" class="num">
                      <tr> 
                        <td><span style="width:43"></span> 1. 예탁금액 : 금 <%=TextHandler.formatNumber(totAmt.getString("TOT_AMT"))%>원(금<%=totAmt.getString("HANGUL_AMT")%>) </td>
                      </tr>
                      <tr> 
                        <td><span style="width:43"></span> 2. 예탁내역</td>
                      </tr>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
            <table width="690" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="num" align="center">
              <tr> 
                <td width="95" height="34"> 
                  <div align="center">예탁종류</div>
                </td>
                <td colspan="2" height="34"> 
                  <div align="center">예탁액</div>
                </td>
                <td width="159" height="34"> 
                  <div align="center">만기일</div>
                </td>
                <td colspan="2" height="34"> 
                  <div align="center">예탁기간</div>
                </td>
                <td width="74" height="34"> 
                  <div align="center">이율(%)</div>
                </td>
              </tr>
							<% String due_date = "";
							   for (int i=0; i<bankDepViewListSize; i++) { 
								   CommonEntity rowData = (CommonEntity)bankDepViewList.get(i);
									 String state = rowData.getString("M120_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " 개월";
									 } else if ("".equals(rowData.getString("M120_DEPOSITDATE")) ) {
                     due_date = "";
                   } else if ("G2".equals(state) || "G3".equals(state)){
										 due_date = rowData.getString("M120_DEPOSITDATE") + " 일";
									 }
							%>
              <tr> 
                <td width="95" height="6"> 
                  <div align="center"><%=rowData.getString("DEPOSIT_NAME")%></div>
                </td>
                <td colspan="2" height="6"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M120_DEPOSITAMT"))%></div>
                </td>
                <td width="159" height="6"> 
                  <div align="center"><%=TextHandler.formatDate(rowData.getString("M120_MANGIDATE"), "yyyyMMdd", "yyyy. MM. dd")%></div>
                </td>
                <td colspan="2" height="35"> 
                  <div align="center"><%=due_date%></div>
                </td>
                <td width="74" height="6"> 
                  <div align="right"><%=rowData.getString("M120_INTERESTRATE")%></div>
                </td>
              </tr>
							<% } %>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic">
              <tr> 
                <td height="60" colspan="3"> 
                  <div align="left"> 
                    <table width="500" border="0" cellspacing="1" cellpadding="3" class="num">
                      <tr> 
                        <td><span style="width:52"></span> 3. 예 탁 일 : <%=TextHandler.formatDate(request.getParameter("reg_date"), "yyyyMMddd", "yyyy. MM. dd")%> </td>
                      </tr>
                      <tr> 
                        <td><span style="width:52"></span> 4. 이자수입 : 일반회계 이자수입으로 처리. 끝. </td>
                      </tr>
                    </table>
                    <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
                      <tr> 
                        <td height="60" colspan="3"> 
                          <div align="center"><br><font size="4"><b>울산광역시장 </b></font>
                          <% if ("Y".equals(manager.getString("M180_SIGNFINISHSTATE")) ) { %>
                          <img src="/report/seal/<%=sealInfo.getString("M340_FNAME")%>" width="60" height="60" align="absmiddle">
                          <% } %>
                          <br></div>
                        </td>
                      </tr>
                      <tr> 
                        <td height="25" width="210"> 
                          <div align="left">주무관: <%=manager.getString("M180_TASKNAME")%> </div>
                        </td>
                        <td height="25" width="312"> 세입관리담당사무관 : <%=manager.getString("M180_MIDDLENAME")%> </td>
                        <td height="30" width="162">세정담당관 : <%=manager.getString("M180_UPPERNAME")%></td>
                      </tr>
                    </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<div class="noprint">
<div align="right">
      <img src="../img/btn_banklist.gif" alt="예탁내역" onClick="goList()" align="absmiddle" style="cursor:hand">
			<img src="../img/btn_print.gif" alt="인쇄" onClick="goPrint()" align="absmiddle" style="cursor:hand">
      <img src="../img/btn_close.gif" alt="닫기" onClick="self.close()" align="absmiddle" style="cursor:hand">
			<span style="width:25"></span>
</div>
</div>
</form>
<p>&nbsp;</p>
<p><span class="count"></span> </p>
<% } %>
</body>
</html>