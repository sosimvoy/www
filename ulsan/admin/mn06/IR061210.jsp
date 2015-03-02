<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061210.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금예탁 일계등록 조회/취소
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  List<CommonEntity> inchulReportList  = (List<CommonEntity>)request.getAttribute("page.mn06.inchulReportList");

	int inchulReportListSize = 0;
	if (inchulReportList != null ) {
		inchulReportListSize = inchulReportList.size();
	}
  String reg_date = request.getParameter("reg_date");
	if ("".equals(reg_date) ) {
    reg_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

	String last_year = "";
  String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>울산광역시 세입 및 자금배정관리시스템</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
      function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
				form.action = "IR061210.etax";
				form.cmd.value = "inchulReportList";
				form.target = "_self";
      	eSubmit(form);
      }

			function goView(b) {
				var form = document.sForm;
        /*
				if (form.doc_yn == null)	{
					alert("조회내역이 없습니다.");
					return;
				}
				var cnt = 0;
				if (form.doc_yn.length == 1) {
					if (form.doc_yn.value == "Y")	{
						cnt = 1;
					}
				} else {
					for (var i=0; i<form.doc_yn.length; i++) {
						if (form.doc_yn[i].value == "Y") {
							cnt ++;
						}
					}
				}

				if (cnt == 0)	{
					alert("출력가능한 통지서가 없습니다.");
					return;
				}
        */
				window.open('IR061211.etax', 'reportPop' ,'height=600,width=760,top=10,left=200,scrollbars=yes');
        form.doc_no.value = b;
        form.cmd.value ="inchulReportView";
        form.action = "IR061211.etax";
				form.target = "reportPop";
        eSubmit(form);  
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR061210.etax">
	<input type="hidden" name="cmd" value="inchulReportList">
  <input type="hidden" name="doc_no" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_18.gif"></td>
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
                    <td width="860"><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도<span style="width:20"></span>
										  <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select><span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    등록일자
										  <input type="text" class="box3" size="8" name="reg_date" value="<%=reg_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','reg_date')" align="absmiddle">
										</td>
                    <td width="100"> 
                      <img src="../img/btn_search.gif" alt="조회" style="cursor:hand" onClick="goSearch()" align="absmiddle">
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
        <td width="975" height="20"> &nbsp;</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr>
              <th class="fstleft">문서번호</th>
              <th>회계<br>연도</th>
							<th>예금종류</th>
              <th>계좌번호</th>
              <th>좌수번호</th>
              <th>신규일</th>
              <th>만기일</th>
              <th>예탁기간</th>
							<th>이율(%)</th>
							<th>원금</th>
              <th>원금인출/<br>해지금액</th>
							<th>처리<br>결과</th>
              <th>mmda<br>해지</th>
            </tr>
						<% long tot_amt = 0L;
               long inchul_amt = 0L;
               if (inchulReportListSize > 0 && inchulReportList != null) { 
							   String due_date = "";
							   for (int i=0; i < inchulReportListSize; i++) {
									 CommonEntity rowData = (CommonEntity)inchulReportList.get(i);
									 String state = rowData.getString("M130_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " 개월";
									 } else if ("".equals(rowData.getString("M130_DEPOSITDATE")) ) {
                     due_date = "";
                   } else {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " 일";
									 }
						%>
            <tr>
              <td  class="fstleft"><a href="javascript:goView('<%=rowData.getString("M130_DOCUMENTNO")%>')"><font color="red"><b><%=rowData.getString("M130_DOCUMENTNO")%></b></font></a></td>
						  <td><%=rowData.getString("M130_YEAR")%></td>
              <td><%=rowData.getString("DEPOSIT_NAME")%></td>
              <td><%=TextHandler.formatAccountNo(rowData.getString("M130_ACCOUNTNO"))%></td>
              <td>&nbsp;<%=rowData.getString("M130_JWASUNO")%></td>
              <td>&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_SINKYUDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
              <td>&nbsp;<%=TextHandler.formatDate(rowData.getString("M130_MANGIDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
							<td class="right">&nbsp;<%=rowData.getString("M130_INTERESTRATE")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M130_JANAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M130_INCHULAMT"))%></td>
							<td>&nbsp;<%=rowData.getString("STATE_NAME")%></td>
              <td>&nbsp;
              <% if ("G3".equals(state)) { %>
              <%=rowData.getString("M130_MMDA_CANCEL_YN")%>
              <% } %>
              </td>
            </tr>
						<%     tot_amt += rowData.getLong("M130_JANAMT");
                   inchul_amt += rowData.getLong("M130_INCHULAMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="3">원금</td>
              <td class="right" colspan="3"><%=TextHandler.formatNumber(tot_amt)%></td>
              <td class="center" colspan="3">인출금액</td>
              <td class="right" colspan="3"><%=TextHandler.formatNumber(inchul_amt)%></td>
            </tr>
		        <% } else { %>
						<tr> 
              <td class="fstleft" colspan="13">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"></p>
  </body>
</html>