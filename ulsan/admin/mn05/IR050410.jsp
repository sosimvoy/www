<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR050410.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 자금배정통지서조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> bankAllotNoticeList  = (List<CommonEntity>)request.getAttribute("page.mn05.bankAllotNoticeList");
	int bankAllotNoticeListSize = 0;
	if (bankAllotNoticeList != null ) {
		bankAllotNoticeListSize = bankAllotNoticeList.size();
	}
  
	String allot_date = request.getParameter("allot_date");
	if ("".equals(allot_date) ) {
    allot_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
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
				form.action = "IR050410.etax";
				form.cmd.value = "bankAllotNoticeList";
				form.target="_self";
      	eSubmit(document.sForm);
      }

			function goView(b) {
				var form = document.sForm;
        form.doc_no.value = b;
				form.action = "IR050411.etax";
				form.cmd.value = "bankAllotNoticeView";
				window.open('IR050411.etax', 'allotNotify' ,'height=600,width=760,top=10,left=200,scrollbars=yes');				
				form.target = "allotNotify";
      	eSubmit(form);
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR050410.etax">
	<input type="hidden" name="cmd" value="bankAllotNoticeList">
	<input type="hidden" name="doc_no" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_3.gif"></td>
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
                    <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    통지서구분
											<select name="report_gubun" iValue="<%=request.getParameter("report_gubun")%>">
                        <option value="0">통합배정통지서</option>
											  <option value="1">배정통지서</option>
												<option value="2">재배정통지서</option>
											</select>
											<span style="width:50"></span>
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계연도
										  <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
												<option value="<%=this_year%>"><%=this_year%></option>
												<option value="<%=last_year%>"><%=last_year%></option>
											</select>
											<span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    배정일자
										  <input type="text" class="box3" size="8" name="allot_date" value="<%=allot_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','allot_date');" style="cursor:hand">
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
        <td width="975" height="20" align="right"> 단위(천원)</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" rowspan="2" width="5%">문서<br>번호</th>
              <th rowspan="2" width="12%">예산현액</th>
							<th rowspan="2" width="11%">예산배정액</th>
							<th colspan="3" width="33%">자금</th>
							<th rowspan="2" width="11%">자금소요액</th>
							<th rowspan="2" width="11%">자금배정<br>요구액</th>
							<th rowspan="2" width="8%">처리상태</th>
							<th rowspan="2" width="8%">배정결과</th>
            </tr>
						<tr> 
              <th width="11%">기배정액</th>
              <th width="11%">지출액(누계)</th>
							<th width="11%">잔액</th>
            </tr>
						<% if (bankAllotNoticeListSize > 0 && bankAllotNoticeList != null) { 
							   for (int i=0; i < bankAllotNoticeListSize; i++) {
									 CommonEntity rowData = (CommonEntity)bankAllotNoticeList.get(i);
						%>
            <tr>
              <td class="fstleft">&nbsp;<a href="javascript:goView('<%=rowData.getString("M100_DOCUMENTNO")%>')"><b><font color="red"><%=rowData.getString("M100_DOCUMENTNO")%></font></b></a></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_BUDGETAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_BUDGETALLOTAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_ORIALLOTAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_TOTALJICHULAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_JANAMT"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_SOYOAMT"))%></td>
							<td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT"))%></td>
							<td class="center">&nbsp;<%=rowData.getString("M100_ALLOTSTATE_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("M100_ALLOTCODE_NAME")%></td>
            </tr>
						<%   }
		           }else { %>
						<tr> 
              <td class="fstleft" colspan="10">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
</html>