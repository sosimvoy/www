<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091410.jsp
* ���α׷��ۼ��� :												  
* ���α׷��ۼ��� : 2010-08-10
* ���α׷�����	 : ���ΰ���
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
  
  List<CommonEntity> sealInfoList = (List<CommonEntity>)request.getAttribute("page.mn09.sealInfoList");
	CommonEntity signInfo           = (CommonEntity)request.getAttribute("page.mn09.signInfo");

  String currentOrgan   = (String)session.getAttribute("session.current_organ");

	if( currentOrgan == null ) {
		currentOrgan = "";
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
        
			function signUpload() {
				var form = document.signForm;
				<% if (signInfo != null && signInfo.size() > 0) { %>
					alert("������ ��ϵ� �����Դϴ�. ���� �� ����ϼ���.");
				  return;
				<% } %>
				var Url = "IR091411.etax";
        window.open(Url, 'signPop' ,'height=250,width=500,top=100,left=100,scrollbars=yes');
			}

		
			function signDelete(){
				var form = document.signForm;
				if (form.upfile.value=="" || form.upfile.value==null)	{
					alert("������ ������ �����ϴ�.");
					return;
				}
				if (confirm("������ �����Ͻðڽ��ϱ�?")) {
					form.action = "IR091410.etax?cmd=signDelete&deleteDir=sign&message=S003";
					eSubmit(form);
				}
			}
			
			function goSearch (){
		   	var form = document.signForm;

				form.action = "IR091410.etax"; 
				form.cmd.value = "sealInfoList";
			  eSubmit(form); 
			}
    </script>
  </head>
  <body topmargin="0" leftmargin="0">
		<form name="signForm" method="post" action="IR091410.etax">
		<input type="hidden" name="cmd" value="sealInfoList">

   <table width="1001" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="15">&nbsp;</td>
    <td width="978" height="40"><img src="../img/title9_27.gif"></td>
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
            <table width="500" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
								<td width="55%" style="text-align:center" width="150" height="150" style="border:1px solid light-grey">&nbsp;
								<% if (signInfo != null && signInfo.size() > 0) { %>
								   <img src="/report/seal/<%=signInfo.getString("M340_FNAME")%>" width="150" height="150">
								<% }%>
									<input type="hidden" name="upfile" value="<%=signInfo.getString("M340_FNAME")%>">
                  <input type="hidden" name="current_organ" value="<%=signInfo.getString("M340_CURRENTORGAN")%>">
								</td>
							
								<td width="45%" valign="bottom"><img src="../img/btn_order.gif" onClick="signUpload()" style="cursor:pointer" alt="���"> &nbsp; <img src="../img/btn_delete.gif" onClick="signDelete()" style="cursor:hand" alt="����"></td>
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
  </form>
  </body>
</html>