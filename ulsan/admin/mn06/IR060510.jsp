<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR060510.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 자금예탁 통지서조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
  List<CommonEntity> bankDepReportList  = (List<CommonEntity>)request.getAttribute("page.mn06.bankDepReportList");

	int bankDepReportListSize = 0;
	if (bankDepReportList != null ) {
		bankDepReportListSize = bankDepReportList.size();
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
				form.action = "IR060510.etax";
				form.cmd.value = "bankDepReportList";
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
				window.open('IR060511.etax', 'reportPop' ,'height=600,width=760,top=10,left=200,scrollbars=yes');
        form.doc_no.value = b;
        form.cmd.value ="bankDepReportView";
        form.action = "IR060511.etax";
				form.target = "reportPop";
        eSubmit(form);  
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR060510.etax">
	<input type="hidden" name="cmd" value="bankDepReportList">
  <input type="hidden" name="doc_no" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_7.gif"></td>
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
              <th class="fstleft" width="11%">문서번호</th>
              <th width="8%">회계연도</th>
							<th width="10%">예금종류</th>
              <th width="17%">신규금액</th>
							<th width="8%">이율(%)</th>
							<th width="10%">예탁기간</th>
							<th width="12%">만기일</th>
							<th width="10%">자금종별</th>
							<th width="14%">처리결과</th>
            </tr>
						<% long tot_amt = 0L;
               if (bankDepReportListSize > 0 && bankDepReportList != null) { 
							   String due_date = "";
							   for (int i=0; i < bankDepReportListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankDepReportList.get(i);
									 String state = rowData.getString("M120_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " 개월";
									 } else if ("".equals(rowData.getString("M120_DEPOSITDATE")) ) {
                     due_date = "";
                   } else if ("G2".equals(state) || "G3".equals(state) ) {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " 일";
									 } 
						%>
            <tr>
              <td  class="fstleft">&nbsp;<a href="javascript:goView('<%=rowData.getString("M120_DOCUMENTNO")%>')"><font color="red"><b><%=rowData.getString("M120_DOCUMENTNO")%></b></font></a></td>
						  <td>&nbsp;<%=rowData.getString("M120_YEAR")%></td>
              <td class="center">&nbsp;<%=rowData.getString("DEPOSIT_NAME")%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M120_DEPOSITAMT"))%></td>
							<td class="right">&nbsp;<%=rowData.getString("M120_INTERESTRATE")%></td>
							<td class="right">&nbsp;<%=due_date%></td>
              <td class="center">&nbsp;<%=TextHandler.formatDate(rowData.getString("M120_MANGIDATE"), "yyyyMMdd","yyyy-MM-dd")%></td>
							<td class="center">&nbsp;<%=rowData.getString("MMDA_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("STATE_NAME")%></td>
							<input type="hidden" name="doc_yn" value="<%=rowData.getString("DOC_YN")%>">
            </tr>
						<%     tot_amt += rowData.getLong("M120_DEPOSITAMT");
                 } %>
            <tr style="font-weight:bold">
              <td class="fstleft" colspan="5">합계</td>
              <td class="right" colspan="4"><%=TextHandler.formatNumber(tot_amt)%></td>
            </tr>
		        <% } else { %>
						<tr> 
              <td class="fstleft" colspan="10">&nbsp;</td>
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