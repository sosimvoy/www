<%
/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR091011.jsp
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-21
* 프로그램내용	   : 시스템운영 > 휴일 수정
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>

<% 
    CommonEntity HolView = (CommonEntity)request.getAttribute("page.mn09.HolView");
		String SucMsg = (String)request.getAttribute("page.mn09.SucMsg");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function init() {
		typeInitialize();
		<% if (SucMsg != null && !"".equals(SucMsg)) { %>
       alert("<%=SucMsg%>");
	     self.close();	
   <% } %>
  }
	
  function gosaveData()	{
  	var form = document.sForm;
		form.action = "IR091011.etax";
		form.cmd.value = "UpdateHol";
		form.target = "_self";
  	eSubmit(form);
  }

//-->
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"  oncontextmenu="return false">
<form name="sForm" method="post" action="IR091011.etax">
<input type="hidden" name="cmd" value="UpdateHol">
<% if (HolView != null && HolView.size() > 0) {%>
<input type="hidden" name="date" value="<%=HolView.getString("M290_DATE")%>">
<% } %>
<table width="500" border="0" cellspacing="0" cellpadding="5">
  <tr> 
    <td> 
      <table width="81%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
					<td width="18">&nbsp;</td>
					<td width="482" height="50"><img src="../img/title9_9.gif"></td>
				</tr>
      </table>
    </td>
  </tr>
	<% if (HolView != null && HolView.size() > 0) {%>
  <tr>
    <td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="500" border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td>
								  <span style="width:50px"></span>
								  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                  회계연도&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="hol_date" class="box3" size="8" value="<%=HolView.getString("M290_DATE")%>">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									코드명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="holName" class="box3" size="8" value="<%=HolView.getString("M290_HOLNAME")%>">
                </td>
              </tr>
							<tr>
								<td width="370"> 
								<div align="center"><img src="../img/btn_modify.gif" alt="수정" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
								</div>
							</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
		</td>
	</tr>
	<% } %>
</table>
</form>
</body>
</html>