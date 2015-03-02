<%
/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : register.jsp
* 프로그램작성자 : 
* 프로그램작성일 : 2010-07-19
* 프로그램내용   : 사용자 등록 신청
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"  %>
<%@ page import = "com.etax.entity.*" %>

<%
    CommonEntity auth = (CommonEntity)request.getAttribute("auth.info");
		String message = "";
		String user_state = "";
		
		if (auth != null) {
		  message = auth.getString("message");
		  user_state = auth.getString("user_state");
		} 

    String SucMsg = (String)request.getAttribute("page.auth.SucMsg");
    if (SucMsg == null) {
      SucMsg = "";
    }

%>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/class.css" type="text/css">
<script language="javascript" type="text/javascript" src="../js/base.js"></script>
<script language=javascript src="../js/INIplugin.js"></script>
<script language="javascript">
<!--
  function init() {
    typeInitialize();
   }

  function goUpdate(){
    var form = document.sForm;
    form.cmd.value = "authUpdate";
    form.action.value = "auth_confirm.etax";
    eSubmit(form)
  }

 function idCheck(sForm, iForm) {
    var s_form = document.sForm;
		var i_form = document.iForm;
		if (s_form.userid.value == "") {
			alert("아이디를 입력하세요.");
			s_form.userid.focus();
			return;
		}

		if (s_form.userpw.value == "") {
			alert("비밀번호를 입력하세요.");
			s_form.userpw.focus();
			return;
		}
    
		InitCache();
    if (EncFormVerify2(s_form,i_form)) {
    i_form.action = "auth_confirm.etax?cmd=managerCheck&userid="+s_form.userid.value+"&userpw="+s_form.userpw.value;
    i_form.submit();
    return false;
  } 
	return; //반드시 false를 return;
 }
  
-->
</script>
</head>

<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
<table width="1004" border="0" cellspacing="0" cellpadding="0">
<form name="iForm" method="post">
<input type="hidden" name="INIpluginData" value="">
</form>
<form name="sForm" method="post" action="auth_confirm.etax">
  <input type="hidden" name="cmd" value="authUpdate">
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
    <td width="978" height="40"><img src="../img/title9_31.gif" ></td>
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
                      <th class="fstleft" width="20%"><font color="#000000" size="2">ID</font></th>
                      <td width="58%"> 
                        <div align="left"> 
                          <input type="text" name="userid" size="20" maxlength="20" value="<%=request.getParameter("userid")%>" class="box3">
                        </div>
                      </td>
                    </tr>
										<tr>
										<td height="5"></td>
										</tr>
                    <tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">PW</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="password" name="userpw" size="20" maxlength="20" value="<%=request.getParameter("userpw")%>" class="box3">
                        </div>
                      </td>
                    </tr>
										
                  </table>
                </td>
              </tr>
            </table>
						<br>
            <table width="106" border="0" cellspacing="1" cellpadding="1" align="center">
              <tr> 
                <td height="22"> 
                  <div align="right"><a href="javascript:idCheck(this, iForm)"><img src="../img/btn_hit.gif" alt="검색" width="61" height="22" align="absbottom" border="0"></a></div>
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
<%    if ("CHECKFAIL".equals(message)) { %>
        <script>
          alert("유효하지않은 인증서입니다.");
        </script>
<%    } else if ("OK".equals(user_state) ) {%>
        <script>
          alert("가입가능합니다.");
        </script>
<%    } else if ("RENEWAL".equals(user_state) ) {%>
        <script>
          if (confirm("인증서 갱신이 가능합니다. 인증서를 갱신하시겠습니까?")) {
            var form = document.sForm;
            form.serial.value = "<%=auth.getString("serial")%>";
            form.subjectDN.value = "<%=auth.getString("subjectDN")%>";
            form.cmd.value = "authUpdate";
            form.action.value = "auth_confirm.etax";
            eSubmit(form);
          }
        </script>
<%    } else if ("ORGAUTH".equals(user_state) ) {%>
        <script>
          alert("이미 등록된 인증서입니다. 아이디를 체크바랍니다. 아이디 : " + "<%=auth.getString("org_id")%>");
        </script>
<%    } else if ("PMS_WAIT".equals(user_state) ) {%>
        <script>
          alert("가입된 사용자입니다. 승인 대기중입니다.");
        </script>
<%    } else if ("PMS_OK".equals(user_state) ) {%>
        <script>
          alert("가입된 사용자입니다. 승인완료되어 로그인 가능합니다.");
          parent.document.location = "../";
        </script>
<%    } %>
	</form>
</body>
<% if (!"".equals(SucMsg)) { %>
<script>
  alert("<%=SucMsg%>");
  document.location = "../";
</script>
<% } %>
</html>