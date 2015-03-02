<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR030410.jsp
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-28
* 프로그램내용   : 세입세출외현금 > 주행세등록
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn03.jsp" %>
<%	
    String SucMsg = (String)request.getAttribute("page.mn03.SucMsg");
    String fis_date = (String)request.getAttribute("page.mn03.fis_date");
    String acc_date             = request.getParameter("acc_date");            //회계일자
	if (acc_date.equals("")) {
			acc_date  = TextHandler.formatDate(fis_date,"yyyyMMdd","yyyy-MM-dd");
	}  

  String to_day = TextHandler.getCurrentDate();
%>


<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/class.css" type="text/css">
<script language="javascript" type="text/javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="JavaScript">
<!--
	function init() {
		typeInitialize();
		<% if (SucMsg != null && !"".equals(SucMsg)) { %>
       alert("<%=SucMsg%>");
	     self.close();	
   <% } %>
  }

  function gosaveData()	{
  	var form = document.sForm;
    var to_day = "<%=to_day%>";
    
    if (to_day < replaceStr(form.acc_date.value, "-", "")) {
      alert("등록하려는 회계일자가 금일보다 큽니다.");
      return;
    }
    if (confirm("기입사항을 등록하겠습니까?")) {
      form.action = "IR030410.etax";
		  form.cmd.value = "juheangInsert";
  	  eSubmit(form);
    }
  }
//-->
</script>
</head>
 
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" oncontextmenu="return false">
<table width="1004" border="0" cellspacing="0" cellpadding="0">
	<form name="sForm" method="post" action="IR030410.etax">
		<input type="hidden" name="cmd" value="juheangInsert">
		<input type="hidden" name="work_log" value="A03">
    <input type="hidden" name="trans_gubun" value="141">
<table width="1001" border="0" cellspacing="0" cellpadding="0">
    <td width="15">&nbsp;</td>
    <td width="978" height="40"><img src="../img/title3_6.gif"></td>
  </tr>
  <tr> 
    <td width="15">&nbsp;</td>
    <td width="978" height="40"> 
      <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
        <tr> 
          <td><img src="../img/box_top.gif" width="964" height="11"></td>
        </tr>
        <tr> 
          <td> 
            <table width="404" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td width="18">&nbsp;</td>
                <td width="386"> 
                  <table width="372" border="0" cellspacing="0" cellpadding="0">
										<tr>
										  <td height="15"></td>
										</tr>
                    <tr> 
                      <td class="fstleft" align="left" width="5%"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">회계일자</td>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="text" name="acc_date" class="box3" size="8" value="<%=acc_date%>" userType="date">
								        	<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand">
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="15"></td>
										</tr>
                    <tr> 
                      <td class="fstleft" align="left" width="5%"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">징수구분</td>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="jingsuType" style="width:55%">
                            <option value="J1">주된특별징수</option>
														<option value="J2">특별징수수납부</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="15"></td>
										</tr>
										<tr> 
                      <td class="fstleft" align="left" width="5%"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">원리금구분</td>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="repayType" style="width:55%">
                            <option value="R1">원금</option>
														<option value="R2">이자</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="15"></td>
										</tr>
										<tr> 
                      <td class="fstleft" align="left" width="5%"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">금액구분</td>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="cashType" style="width:55%">
                            <option value="A1">수입액</option>
														<option value="A2">과오납</option>
														<option value="A3">지급액</option>
														<option value="A4">반납액</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="15"></td>
										</tr>
										 <tr> 
                      <td class="fstleft" align="left" width="20%"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">금액</td>
                      <td width="58%"> 
                        <div align="left"> 
                          <input type="text" name="amt" size="20" maxlength="20" class="box3" required desc="금액" userType="money">
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="15"></td>
										</tr>
										 <tr> 
                      <td class="fstleft" align="left"width="20%"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">납세의무자</td>
                      <td width="58%"> 
                        <div align="left"> 
                          <input type="text" name="napseja" size="20" maxlength="20" class="box3" required desc="납세의무자">
                        </div>
                      </td>
                    </tr>
                    <tr>
										<td height="5"></td>
										</tr>
                  </table>
                </td>
              </tr>
            </table>
            <table width="200" border="0" cellspacing="1" cellpadding="1" align="center">
              <tr> 
                <td height="55"> 
                  <div align="center">
                  <img src="../img/btn_order.gif" onClick="gosaveData()" style="cursor:hand" align="absmiddle" alt="등록">
                  </div>
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
	</form>
</table>

<p>&nbsp;</p>
<p><img src="../img/footer.gif" width="1000" height="72"> </p>
</body>
</html>
