<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR080310.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-20
* ���α׷�����	   : �ý��ۿ > ������������
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>
<%@include file="../menu/mn08.jsp" %>

<%
	CommonEntity userInfo = (CommonEntity)request.getAttribute("page.mn08.userInfo");

	String SucMsg = (String)request.getAttribute("page.mn08.SucMsg");

	if (SucMsg == null ){
		SucMsg = "";
	}
  
	String userID   = (String)session.getAttribute("session.user_id");
	
	if( userID == null ) {
		userID = "";
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
      
			function confirmPw(){
				var form = document.sForm;
				if (form.chUserpw.value.length == form.reUserpw.value.length) {
					if (form.chUserpw.value != form.reUserpw.value)	{
						form.alert.value = "��й�ȣ�� ��ġ�����ʽ��ϴ�.";
					} else {
						form.alert.value = "��й�ȣ�� ��ġ�մϴ�.";
					}
				}
			}

			function gosaveData(){
        var form = document.sForm;
				 form.action = "IR080310.etax"; 
				 form.cmd.value = "updateUserInfo";
			   eSubmit(form); 
      }
    </script>
  </head>
  <body topmargin="0" leftmargin="0"  oncontextmenu="return false">
  <form name="sForm" method="post" action="IR080310.etax">
	<input type="hidden" name="cmd" value="userInfo">

   <table width="1001" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="15">&nbsp;</td>
    <td width="978" height="40"><img src="../img/title9_3.gif"></td>
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
                <td width="18">&nbsp;</td>
                <td width="470"> 
                  <table width="460" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <th class="fstleft" width="20%"  align="right"><font color="#000000" size="2">����ڸ�</font></th>
                      <td width="58%"> 
                        <div align="center"> 
												  <% if (userInfo != null ) { %>
                          <input type="text" value="<%=userInfo.getString("M260_USERNAME")%>" name="username" size="20" maxlength="20" class="box1" readonly tabindex="-1">
													<% } %>
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="20%" align="right"><font color="#000000" size="2">������ ����ڸ�</font></th>
                      <td width="58%"> 
                        <div align="center"> 
                           <input type="text" name="chUsername" size="20" value="" maxlength="20" class="box1">
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
                    <tr> 
                      <th class="fstleft" width="5%" align="right"><font color="#000000" size="2">������ PW</font></th>
                      <td class="left" width="58%"> 
                        <div align="center"> 
												 <% if (userInfo != null ) { %>
                         <input type="text" value="<%=userInfo.getString("M260_USERPW")%>" name="userpw" size="20" maxlength="20" class="box1" readonly tabindex="-1">
												 <% } %>
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
                    <tr> 
                      <th class="fstleft" width="5%" align="right"><font color="#000000" size="2">������ PW</font></th>
                      <td class="left" width="58%"> 
                        <div align="center"> 
                          <input type="password" name="chUserpw" size="20" value="" maxlength="20" class="box1" required desc="������ PW">
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
                    <tr> 
                      <th class="fstleft" width="5%" align="right"><font color="#000000" size="2">������ PWȮ��</font></th>
                      <td class="left" width="58%"> 
                        <div align="center"> 
                          <input type="password" name="reUserpw" size="20" maxlength="20" class="box1" onkeyup="confirmPw()" required desc="������ PWȮ��">
												</div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
										 <tr> 
                      <th class="fstleft" width="5%"></th>
                      <td class="left" width="58%"> 
                        <div>
													<input type="text" name="alert" size="40" maxlength="20" style="border:0" tabindex="-1">
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
										 <tr> 
                      <th class="fstleft" width="20%" align="right"><font color="#000000" size="2">å�������</font></th>
                      <td width="58%"> 
                        <div align="center"> 
												  <% if (userInfo != null ) { %>
                          <input type="text" value="<%=userInfo.getString("M260_MANAGERBANKERNO")%>" name="managerBankerNo" size="20" maxlength="20" class="box1" readonly tabindex="-1">
													<% } %>
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="20%" align="right"><font color="#000000" size="2">������ å�������</font></th>
                      <td width="58%"> 
                        <div align="center"> 
                           <input type="text" name="chManagerBankerNo" size="20" value="" maxlength="20" class="box1">
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
										 <tr> 
                      <th class="fstleft" width="20%" align="right"><font color="#000000" size="2">å���ڹ�ȣ</font></th>
                      <td width="58%"> 
                        <div align="center"> 
												  <% if (userInfo != null ) { %>
                          <input type="text" value="<%=userInfo.getString("M260_MANAGERNO")%>" name="managerNo" size="20" maxlength="20" class="box1" readonly tabindex="-1">
													<% } %>
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="20%" align="right"><font color="#000000" size="2">������ å�������</font></th>
                      <td width="58%"> 
                        <div align="center"> 
                           <input type="text" name="chManagerNo" size="20" value="" maxlength="20" class="box1">
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
										 <tr> 
                      <th class="fstleft" width="20%" align="right"><font color="#000000" size="2">�ܸ���ȣ</font></th>
                      <td width="58%"> 
                        <div align="center"> 
												  <% if (userInfo != null ) { %>
                          <input type="text" value="<%=userInfo.getString("M260_TERMINALNO")%>" name="terminalNo" size="20" maxlength="20" class="box1" readonly tabindex="-1">
													<% } %>
                        </div>
                      </td>
                    </tr>
										<tr>
                     <td height="10"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="20%" align="right"><font color="#000000" size="2">������ å�������</font></th>
                      <td width="58%"> 
                        <div align="center"> 
                           <input type="text" name="chTerminalno" size="20" value="" maxlength="20" class="box1" autoNext="chUsername">
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <table width="500" border="0" cellspacing="1" cellpadding="1" align="center">
              <tr> 
                <td height="50"> 
                  <div align="center"><img src="../img/btn_modify.gif" width="66" border="0" onclick="gosaveData()" style="cursor:hand">
                  <img src="../img/btn_cancel.gif" width="66" height="20" border="0" onclick="history.back()" style="cursor:hand"></div>
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
  </form>
  </body>
	<% if (!"".equals(SucMsg)) { %>
	 <script>
	   alert("<%=SucMsg%>");
	 </script>
	<% } %>
</html>