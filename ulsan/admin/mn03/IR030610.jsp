<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR030610.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-28
* 프로그램내용	   : 세입세출외현금 > 주행세 일일마감
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn03.jsp" %>

<%

  String SucMsg = (String)request.getAttribute("page.mn03.SucMsg");
		if (SucMsg == null ){
		SucMsg = "";
	}

  String fis_date =    (String)request.getAttribute("page.mn03.fis_date");
  
	String acc_date           = request.getParameter("acc_date");           //시작날짜
	 
	if (acc_date.equals("")) {
      acc_date  = TextHandler.formatDate(fis_date,"yyyyMMdd","yyyy-MM-dd");
  }  

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

			 function goUpdate(){
				 var form = document.sForm;				
				 form.action = "IR030610.etax";
				 form.cmd.value = "juhaengMagam";
				 eSubmit(form);
			 }

    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
		<form name="sForm" method="post" action="IR030610.etax">
    <input type="hidden" name="cmd" value="juheangList">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title3_13.gif"></td>
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
										<td width="790">
                    <span style="width:400"></span>
										<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">회계일자</span>
										<input type="text"  class="box3" name="acc_date" value="<%=acc_date%>" size="8" userType="date" required desc="회계일자"> 
                    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand">
										<span style="width:50"></span>
										 </td>
										 <td width="100"> 
                      <img src="../img/btn_end.gif" alt="일일마감" align="absmiddle" onclick="goUpdate()" style="cursor:hand">
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
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
   <% if (SucMsg != null && !"".equals(SucMsg)) { %>
   <script>
					  alert("<%=SucMsg%>");
   </script>
				  <% } %>
  </body>
</html>