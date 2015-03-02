<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090410.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > �ŷ��αװ���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn09.jsp" %>

<%
	List<CommonEntity>logList = (List<CommonEntity>)request.getAttribute("page.mn09.logList");

	String su_sdate			  = request.getParameter("su_str_date");
	String su_edate			  = request.getParameter("su_end_date");
	String user_name			= request.getParameter("user_name");

	
	if (su_sdate.equals("")) {
		su_sdate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}  

	if (su_edate.equals("")) {
		su_edate  = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">

		  function init() {
  	    typeInitialize();
      }
     
      function goSearch() {
				var form = document.sForm;
	
				eSubmit(form);
			}

    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
		<form name="sForm" method="post" action="IR090510.etax">
		<input type="hidden" name="cmd" value="logList">

    <table width="993" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975" height="40"><img src="../img/title9_7.gif"></td>
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
										<td width="310">&nbsp;</td>
										<td width="490"><img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
										<span class="point">��ȸ����</span>
											<input type="text" style="width:80" name="su_str_date" value="<%=su_sdate%>" userType="date" required desc="������"> 
											<img src="../img/btn_calendar.gif" align="absmiddle"  border="0" onclick="Calendar(this,'sForm','su_str_date');" style="cursor:hand"> -
											<input type="text" style="width:80" name="su_end_date" value="<%=su_edate%>" userType="date" required desc="������"> 
											<img src="../img/btn_calendar.gif" align="absmiddle"  border="0" onclick="Calendar(this,'sForm','su_end_date');" style="cursor:hand">
											<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> <span class="point">����ڸ�</span>
												<input type="text" size="14" name="user_name" value="<%=user_name%>" onkeyDown="enterKey()">
										 </td>
										 <td width="50"> 
											<div align="right"><img src="../img/btn_search.gif" width="55" height="21" border="0" onClick="goSearch()" style="cursor:hand">
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
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr> 
              <th class="fstleft" width="20%">�ŷ��Ͻ�</th>
					    <th width="15%">����ڸ�</th>
					    <th width="10%">��������</th>
					    <th width="20%">�ŷ�����</th>
					    <th width="15%">�α׹�ȣ</th>
					    <th width="20%">����IP</th>	
            </tr>

						<%
						for (int i=0; logList != null && i < logList.size(); i++) {
							CommonEntity data = (CommonEntity)logList.get(i);
						%>

            <tr>                 
               <td class="fstleft">&nbsp;<%=data.getString("M280_TRANSDATE")%></td>
							 <td>&nbsp;<%=data.getString("M280_USERNAME")%></td>
							 <td>&nbsp;<%=data.getString("M280_WORKTYPE")%></td>
							 <td>&nbsp;<%=data.getString("M280_TRANSGUBUN")%></td>
							 <td>&nbsp;<%=data.getString("M280_LOGNO")%></td>
							 <td>&nbsp;<%=data.getString("M280_CONNECTIP")%></td>
            </tr>
					  <%				 
						} if (logList == null) { 
					  %>
						<tr>
							<td class="fstleft" colspan="10">��ȸ ������ �����ϴ�.</td>
						</tr>
						<% 
							} 
						%>
          </table>
        </td>
      </tr>
    </table>
    <p>&nbsp;</p>
    <p><img src="../img/footer.gif" width="1000" height="72"> </p>
  </form>
  </body>
</html>