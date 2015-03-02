<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR061810.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-05-15
* 프로그램내용	   : 자금운용 > 평잔표조회
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn06.jsp" %>
<%
	String acc_date = request.getParameter("acc_date");
	if ("".equals(acc_date) ) {
    acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}

  String this_year =  TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy");
  int year =  Integer.parseInt(this_year);
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
        var mon = form.acc_month.value;
        var avg_type;

        if (mon.length == 1) {
          mon = "0"+mon;
        }
        form.s_month.value = form.acc_year.value + mon;

        if (form.acc_month.value != "" && form.start_date.value != "") {
          alert("월별평잔 또는 기간중 평잔 둘 중 하나만 조회가 가능합니다.");
          form.acc_month.value = "";
          return;
        }

        if (form.acc_month.value == "" && form.start_date.value != "") {
          avg_type = "B";
        } else if (form.acc_month.value != "" && (form.start_date.value == "" && form.end_date.value == "")) {
          avg_type = "A";
        }

        form.cmd.value = "avgMoneyList";
        if (avg_type == "A") {
          form.action = "IR061811.etax";
          window.open('IR061811.etax', 'janakJang' ,'height=700,width=1060,top=10,left=200,scrollbars=yes');
        } else if (avg_type == "B") {
          form.action = "IR061812.etax";
          window.open('IR061812.etax', 'janakJang' ,'height=600,width=750,top=10,left=200,scrollbars=yes');
        }
        
        form.target = "janakJang";
				eSubmit(form);
			}
    </script>
  </head>
  <body topmargin="10" leftmargin="10">
  <form name="sForm" method="post" action="IR061810.etax">
	<input type="hidden" name="cmd" value="avgMoneyList">
  <input type="hidden" name="s_month" value="">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title6_27.gif"></td>
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
									  <td> &nbsp;</td>
                  </tr>
                  <tr>
                    <td>
										  <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									    일반회계 월별평잔
										  <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
											  <option value="<%=year%>"><%=year%></option>
                        <option value="<%=year-1%>"><%=year-1%></option>
                        <option value="<%=year-2%>"><%=year-2%></option>
											</select>
                      년
                      <input type="text" name="acc_month" maxlength="2" size="1" userType="money" class="box3">
                      월
                      <span style="width:50"></span>
										  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									    기간중 평잔
										  <input type="text" class="box3" size="8" name="start_date" value="<%=acc_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','start_date')" align="absmiddle">-
										  <input type="text" class="box3" size="8" name="end_date" value="<%=acc_date%>" userType="date"> 
						          <img src="../img/btn_calendar.gif" style="cursor:hand" onclick="Calendar(this,'sForm','end_date')" align="absmiddle">
                      <span style="width:200"></span>
											<img src="../img/btn_search.gif" alt="조회" style="cursor:hand" onClick="goSearch()" align="absmiddle">
                      <br><br>
                    </td>
                  </tr>
									<tr>
									  <td> &nbsp; </td>
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
    </table>
	  </form>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"></p>
  </body>
</html>