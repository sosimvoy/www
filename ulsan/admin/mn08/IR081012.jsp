<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR081012.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-21
* ���α׷�����	   : �ý��ۿ > ���� ���
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>

<% 
		String SucMsg = (String)request.getAttribute("page.mn08.SucMsg");

		String hol_date = request.getParameter("hol_date");  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
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
		form.action = "IR081012.etax";
		form.cmd.value = "InsertHol";
		form.target = "_self";
  	eSubmit(form);
  }

//-->
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"  oncontextmenu="return false">
<form name="sForm" method="post" action="IR081012.etax">
<input type="hidden" name="cmd" value="InsertHol">

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
                  ��������&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="hol_date" class="box3" size="8" value="<%=hol_date%>" userType="date">
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','hol_date');" style="cursor:hand">
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									���ϸ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" name="holName" class="box3" size="8" value="" required desc="���ϸ�">
									<br><br>
									
                </td>
              </tr>
							<tr>
								<td width="360"> 
								<div align="center"><img src="../img/btn_order.gif" alt="���" width="55" height="21" border="0" onClick="gosaveData()" style="cursor:hand">
								</div>
							</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
		</td>
	</tr>
</table>
</form>
</body>
</html>