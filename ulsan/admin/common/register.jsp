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
			  form.alert.value = "비밀번호가 일치하지않습니다.";
		  } else {
				form.alert.value = "비밀번호가 일치합니다.";
			}
		}
	}

  //인증서확인
  function authCheck() {
    var form = document.sForm;
    if (form.username.value == "") {
      alert("이름을 입력하세요.");
      form.username.focus();
      return;
    }

		if (form.userid.value == "") {
			alert("아이디를 입력하세요.");
			form.userid.focus();
			return;
		}

		if (form.userpw.value == "") {
			alert("비밀번호를 입력하세요.");
			form.userpw.focus();
			return;
		}

    if (form.reUserpw.value == "") {
      alert("비밀번호 확인란에 비밀번호를 입력하세요.");
      form.reUserpw.focus();
      return;
    }
    
		if (form.reUserpw.value == "") {
      alert("비밀번호 확인란에 비밀번호를 입력하세요.");
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
      alert("인증서확인이 완료되어야 회원가입 할 수 있습니다.");
      return;
    }

		if (form.username.value == "") {
      alert("이름을 입력하세요.");
      form.username.focus();
      return;
    }
    if (form.userid.value == ""){
      alert("아이디를 입력하세요.");
      form.userid.focus();
      return;
    }
		if (form.userpw.value == ""){
      alert("비밀번호를 입력하세요.");
      form.userpw.focus();
      return;
    }
		
		if (form.currentdepart.value == "B1" && form.currentwork1.value=="B2" && form.currentsign.value == "B2"){
      
			if (form.managerHangNo.value ==""){
				alert("책임자 행번을 등록하세요");
			  form.managerHangNo.focus();
        return;
				
			} else if (form.managerNo.value ==""){
				alert("책임자 번호를 등록하세요");
				form.managerNo.focus();
        return;

			} else if (form.terminalNo.value ==""){
				alert("단말번호를 등록하세요");
				form.terminalNo.focus();
        return;
			}

    } else {

      if (form.managerHangNo.value !=""){
				alert("책임자만 책임자 행번 등록이 가능합니다.");
			  form.managerHangNo.value = "";
        return;
				
			} else if (form.managerNo.value !=""){
				alert("책임자만 책임자 번호 등록이 가능합니다.");
				form.managerNo.value = "";
        return;

			} else if (form.terminalNo.value !=""){
				alert("책임자만 단말번호 등록이 가능합니다.");
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
    //시청지점 자금배정 책임자일떄만 책임자행번 책임자번호 단말번호선택가능
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
                      <th class="fstleft" width="20%"><font color="#000000" size="2">이름</font></th>
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
                      <th class="fstleft" width="5%"><font color="#000000" size="2">PW확인</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <input type="password" name="reUserpw" size="20" maxlength="20" class="box1" onkeyup="confirmPw()">
                          <a href="javascript:authCheck()"><img src="../img/login_in_user_3.gif" width="86" height="22" align="absbottom" border="0" alt="인증서확인"></a>
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
                      <th class="fstleft" width="5%"><font color="#000000" size="2">소속기관</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="currentorgan" style="width:52%">
                            <option value="1">울산시시금고</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="5"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">소속부서</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="currentdepart" style="width:52%">
                            <option value="B1">시청지점</option>
														<option value="B2">OCR센터</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="5"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">주요업무</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="currentwork1" style="width:52%">
                            <option value="B1">세입등</option>
														<option value="B2">자금배정</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="5"></td>
										</tr>
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">결재권구분</font></th>
                      <td class="left" width="58%"> 
                        <div align="left"> 
                          <select name="currentsign" style="width:52%">
                            <option value="B1">담당자</option>
														<option value="B2">책임자</option>
                          </select>
                        </div>
                      </td>
                    </tr>
										<tr>
										  <td height="5"></td>
										</tr>
								
										<tr> 
                      <th class="fstleft" width="5%"><font color="#000000" size="2">책임자행번</font></th>
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
                      <th class="fstleft" width="5%"><font color="#000000" size="2">책임자번호</font></th>
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
                      <th class="fstleft" width="5%"><font color="#000000" size="2">단말번호</font></th>
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
