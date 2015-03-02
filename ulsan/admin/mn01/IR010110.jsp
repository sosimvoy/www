<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR010310.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 수기분등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ page import = "java.text.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%  
    CommonEntity dateInfo = (CommonEntity)request.getAttribute("page.mn01.fis_date"); 
    
    String fis_date = "";
    if (fis_date != null) { 
      fis_date = dateInfo.getString("FIS_DATE");
    }
    
    if (!"".equals(fis_date) ) {
      fis_date = TextHandler.formatDate(fis_date,"yyyyMMdd","yyyy-MM-dd");
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
			
			function gosaveData(){
        var form = document.sForm;
        form.action = "IR010110.etax?cmd=updateAccDate";  
			   eSubmit(form); 
      }

    </script>
  </head>
  <body topmargin="0" leftmargin="0" oncontextmenu="return false">
  <form name="sForm" method="post" action="IR010110.etax">
	<input type="hidden" name="cmd" value="getAccDate">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title1_1.gif"></td>
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
                <table width="964" border="0" cellspacing="0" cellpadding="0" align="center" height="100">
                  <tr> 
                    <td width="860"><span style="width:50"></span>
									    <span style="width:300px"></span>
                      <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									    회계일자&nbsp; <input type="text" value="<%=fis_date%>" name="fis_date" size="8" class="box3" userType="date">
									    <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
                      <span style="width:50px"></span>
                      <img src="../img/btn_order.gif" alt="등록" onClick="gosaveData()" style="cursor:hand" align="absmiddle">
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
  </body>
</html>