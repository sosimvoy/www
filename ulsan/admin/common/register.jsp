<%
/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : register.jsp
* ���α׷��ۼ��� : 
* ���α׷��ۼ��� : 2010-07-19
* ���α׷�����   : ����� ��� ��û
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"  %>
<%@ page import = "com.etax.entity.*" %>
<%

  String SucMsg = (String)request.getAttribute("page.auth.SucMsg");

  if (SucMsg == null) {
    SucMsg = "";
  }
%>

<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/class.css" type="text/css">
<script language="javascript" type="text/javascript" src="../js/base.js"></script>
<script language="JavaScript">
<!--
  var memberEnabled = false;
	function init() {
    typeInitialize();
  }

	function confirmPw(){
		var form = document.sForm;
		if (form.userpw.value.length == form.reUserpw.value.length) {
			if (form.userpw.value != form.reUserpw.value)	{
			  form.alert.value = "��й�ȣ�� ��ġ�����ʽ��ϴ�.";
		  } else {
				form.alert.value = "��й�ȣ�� ��ġ�մϴ�.";
			}
		}
	}

  //������Ȯ��
  function authCheck() {
    var form = document.sForm;
    if (form.username.value == "") {
      alert("�̸��� �Է��ϼ���.");
      form.username.focus();
      return;
    }

		if (form.userid.value == "") {
			alert("���̵� �Է��ϼ���.");
			form.userid.focus();
			return;
		}

		if (form.userpw.value == "") {
			alert("��й�ȣ�� �Է��ϼ���.");
			form.userpw.focus();
			return;
		}

    if (form.reUserpw.value == "") {
      alert("��й�ȣ Ȯ�ζ��� ��й�ȣ�� �Է��ϼ���.");
      form.reUserpw.focus();
      return;
    }
    
		if (form.reUserpw.value == "") {
      alert("��й�ȣ Ȯ�ζ��� ��й�ȣ�� �Է��ϼ���.");
      form.reUserpw.focus();
      return;
    }

    form.action = "auth_step_one.etax?userid=" + form.userid.value + "&userpw=" +form.userpw.value;
    form.target = "authFrame";
    form.submit();
  }
  
	function goInsert() {
    var form = document.sForm;

    if( !memberEnabled ) {
      alert("������Ȯ���� �Ϸ�Ǿ�� ȸ������ �� �� �ֽ��ϴ�.");
      return;
    }

		if (form.username.value == "") {
      alert("�̸��� �Է��ϼ���.");
      form.username.focus();
      return;
    }
    if (form.userid.value == ""){
      alert("���̵� �Է��ϼ���.");
      form.userid.focus();
      return;
    }
		if (form.userpw.value == ""){
      alert("��й�ȣ�� �Է��ϼ���.");
      form.userpw.focus();
      return;
    }
		
		if (form.currentdepart.value == "B1" && form.currentwork1.value=="B2" && form.currentsign.value == "B2"){
      
			if (form.managerHangNo.value ==""){
				alert("å���� ����� ����ϼ���");
			  form.managerHangNo.focus();
        return;
				
			} else if (form.managerNo.value ==""){
				alert("å���� ��ȣ�� ����ϼ���");
				form.managerNo.focus();
        return;

			} else if (form.terminalNo.value ==""){
				alert("�ܸ���ȣ�� ����ϼ���");
				form.terminalNo.focus();
        return;
			}

    } else {

      if (form.managerHangNo.value !=""){
				alert("å���ڸ� å���� ��� ����� �����մϴ�.");
			  form.managerHangNo.value = "";
        return;
				
			} else if (form.managerNo.value !=""){
				alert("å���ڸ� å���� ��ȣ ����� �����մϴ�.");
				form.managerNo.value = "";
        return;

			} else if (form.terminalNo.value !=""){
				alert("å���ڸ� �ܸ���ȣ ����� �����մϴ�.");
				form.terminalNo.value = "";
        return;
			}

    }

    form.target = "_self";
		form.action = "register.etax";
    form.cmd.value = "managerInsert";
    eSubmit(form);
  }
/*
	function chBox(val){
    var currentsign = val;
    //��û���� �ڱݹ��� å�����ϋ��� å������� å���ڹ�ȣ �ܸ���ȣ���ð���
    if(currentsign == "B2"){
      document.all.accDiv.visibility      = 'show'; 
      document.all.accDiv.style.visibility = 'visible' ; 
      //document.getElementById("accDiv").style.display = 'visible';
    } else {
      document.all.accDiv.visibility      = 'hide';
      document.all.accDiv.style.visibility = 'hidden';
      //document.getElementById("accDiv").style.display = 'hidden';
    }
}
*/
//-->
</script>
</head>
 
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
<table width="1004" border="0" cellspacing="0" cellpadding="0">
<form name="sForm" method="post" action="register.etax">
	<input type="hidden" name="cmd" value="">
  <input type="hidden" name="message" value="">
  <input type="hidden" name="serial" value="">
  <input type="hidden" name="subjectDN" value="">
  <tr> 
    <td colspan="2"><img src="../img/top.gif" width="1000" height="132"></td>
  </tr>
</table>
<table width="1001" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="15">&nbsp;</td>
    <td width="978" height="40"><img src="../img/title0.gif" width="112" height="25"></td>
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
                      <th class="fstleft" width="20%"><font color="#000000" size="2">�̸�</font></th>
                      <td width="58%"> 
                        <div align="left"> 
                          <input type="text" name="username" size="20" maxlength="20" class="box1">
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">ID</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="text" name="userid" size="20" maxlength="20" class="box1" userType="engNumber">
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2"> 
                        PW</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="password" name="userpw" size="20" value="" maxlength="20" class="box1">
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">PWȮ��</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="password" name="reUserpw" size="20" maxlength="20" class="box1" onkeyup="confirmPw()">
                          <a href="javascript:authCheck()"><img src="../img/login_in_user_3.gif" width="86" height="22" align="absbottom" border="0" alt="������Ȯ��"></a>
												</div>
                      </td>
                    </tr> 
										 <tr> 
                      <th class="fstleft" width="5%"></th>
                      <td class="left" width="58%"> 
                        <div>
													<input type="text" name="alert" size="40" maxlength="20" style="border:0">
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">�Ҽӱ��</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="currentorgan" style="width:52%">
                            <option value="1">���ýñݰ�</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="5"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">�ҼӺμ�</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="currentdepart" style="width:52%">
                            <option value="B1">��û����</option>
														<option value="B2">OCR����</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="5"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">�ֿ����</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="currentwork1" style="width:52%">
                            <option value="B1">���Ե�</option>
														<option value="B2">�ڱݹ���</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="5"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">����Ǳ���</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="currentsign" style="width:52%">
                            <option value="B1">�����</option>
														<option value="B2">å����</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="5"></td>
										</tr>
								
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">å�������</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="text" name="managerHangNo" size="20" maxlength="6" class="box1">
												</div>
                      </td>
                    </tr> 
										<tr>
										  <td height="5"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">å���ڹ�ȣ</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="text" name="managerNo" size="20" maxlength="2" class="box1">
												</div>
                      </td>
                    </tr> 
										<tr>
										  <td height="5"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">�ܸ���ȣ</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="text" name="terminalNo" size="20" maxlength="2" class="box1">
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
            <table width="106" border="0" cellspacing="1" cellpadding="1" align="center">
              <tr> 
                <td height="55"> 
                  <div align="right"><a href="javascript:goInsert()"><img src="../img/btn_order.gif" width="66" border="0"></a> 
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
 <iframe name="authFrame" width="0" height="0"> </iframe>
<p>&nbsp;</p>
<p><img src="../img/footer.gif" width="1000" height="72"> </p>
</body>
<% if (!"".equals(SucMsg)) { %>
<script>
  alert("<%=SucMsg%>");
  document.location = "../";
</script>
<% } %>
</html>
