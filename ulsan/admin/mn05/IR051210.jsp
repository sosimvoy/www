<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR051210.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금배정 > 잉여금이입통지서조회
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn05.jsp" %>
<%
  List<CommonEntity> srpRepList  = (List<CommonEntity>)request.getAttribute("page.mn05.srpRepList");
	int srpRepListSize = 0;
	if (srpRepList != null ) {
		srpRepListSize = srpRepList.size();
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
				form.action = "IR051210.etax";
				form.cmd.value = "srpRepList";
				form.target = "_self";
      	eSubmit(form);
      }

			function goView(b) {
				var form = document.sForm;
				window.open('IR051211.etax', 'srpRepWin' ,'height=600,width=760,top=10,left=200,scrollbars=yes');
				form.doc_no.value = b;
				form.action = "IR051211.etax";
				form.cmd.value = "srpRepView";
				form.target = "srpRepWin";
      	eSubmit(form);
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
  <form name="sForm" method="post" action="IR051210.etax">
	<input type="hidden" name="cmd" value="srpRepList">
	<input type="hidden" name="doc_no">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title5_29.gif"></td>
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
									    회계연도<span style="width:20"></span>
										  <select name="fis_year" value="<%=request.getParameter("fis_year")%>">
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
              <th class="fstleft" width="9%">문서번호</th>
              <th width="11%">이월회계연도</th>
              <th width="17%">출금계좌</th>
							<th width="11%">이입회계연도</th>
							<th width="17%">입금계좌</th>
							<th width="15%">이입금액</th>
							<th width="10%">처리상태</th>
							<th width="10%">이월결과</th>
            </tr>
						<% if (srpRepListSize > 0 && srpRepList != null) { 
							   for (int i=0; i < srpRepListSize; i++) {
									 CommonEntity rowData = (CommonEntity)srpRepList.get(i);
						%>
            <tr>
              <td class="fstleft">&nbsp;<a href="javascript:goView('<%=rowData.getString("M310_DOCUMENTNO")%>')"><font color="red"><b><%=rowData.getString("M310_DOCUMENTNO")%></b></font></a></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_YEAROVER")%></td>
              <td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M310_ACCOUNTNOOVER"))%></td>
              <td class="center">&nbsp;<%=rowData.getString("M310_YEARINTO")%></td>
							<td class="center">&nbsp;<%=TextHandler.formatAccountNo(rowData.getString("M310_ACCOUNTNOINTO"))%></td>
              <td class="right">&nbsp;<%=TextHandler.formatNumber(rowData.getString("M310_INTOTAMT"))%>원</td>
							<td class="center">&nbsp;<%=rowData.getString("M310_INTOSTATE_NAME")%></td>
							<td class="center">&nbsp;<%=rowData.getString("M310_INTOCODE_NAME")%></td>
            </tr>
						<%   }
		           } else { %>
						<tr> 
              <td class="fstleft" colspan="8">&nbsp;</td>
            </tr>
						<% } %>
          </table>
        </td>
      </tr>
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </body>
</html>