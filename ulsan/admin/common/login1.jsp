 <%@ page language="java" contentType="text/html; charset=euc-kr" %>
<html>
<head>
<title>울산광역시 세입 및 자금배정관리시스템</title>
<link rel="stylesheet" href="../css/class.css" type="text/css">
<script language="javascript" src="../js/base.js"></script>
<script language="JavaScript">
<!--

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

//인증서불러오기
function login() {
	var form = document.sForm;
	if (form.userid.value == "") {
		alert("아이디는 필수입니다.");
		form.userid.focus();
		return;
	}
	if (form.userpw.value == "") {
		alert("비밀번호는 필수입니다.");
		form.userpw.focus();
		return;
	}

    form.action = "login.etax";
    form.cmd.value = "masterLogin";
    form.submit();
}

//사용자등록
function goRegister() {
  document.location="register.etax";
}

function enterKey() {
  if(isEnterKey()) {
	  login();
	}
}

//사용자확인
function authConfirm() {
  document.location="auth_confirm.etax"; 
}
//-->
</script>
</head>
 
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" class="bg" onLoad="MM_preloadImages('../img/login_in_login_1.gif','../img/login_in_user_1.gif');document.sForm.userid.focus()">
<form name="sForm" method="post" action="login.etax">
<input type="hidden" name="cmd" value="masterLogin">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11%">&nbsp;</td>
    <td width="89%"> 
      <table width="100" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="../img/login_top1.gif" width="1000" height="290"></td>
        </tr>
        <tr> 
          <td width="603" bgcolor="#FFFFFF"> <br>
            <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/login_in_bg.gif" align="right">
              <tr> 
                <td><img src="../img/login_in_top.gif" width="355" height="13"></td>
              </tr>
              <tr> 
                <td> 
                  <table width="305" border="0" cellspacing="0" cellpadding="0" align="center">
                    <tr> 
                      <td width="50">&nbsp;</td>
                      <td width="177">&nbsp;</td>
                      <td width="78">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td width="50"><img src="../img/login_in_id.gif" width="50" height="14"></td>
                      <td width="177"> 
                        <input type="text" name="userid" size="20" maxlength="20" class="box1" tabindex="1" style="ime-mode:disabled">
                      </td>
                      <td rowspan="2" width="78"><a href="javascript:login()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','../img/login_in_login_1.gif',1)"><img name="Image7" border="0" src="../img/login_in_login.gif" width="68" height="49"></a></td>
                    </tr>
                    <tr>
                      <td width="50"><img src="../img/login_in_pw.gif" width="50" height="14"></td>
                      <td width="177"> 
                        <input type="password" name="userpw" size="20" maxlength="20" class="box1" tabindex="2" style="ime-mode:disabled" onkeyDown="enterKey()">
                      </td>
                    </tr>
                    <tr>
                      <td width="300" height="37" colspan="3"> 
                        <div align="left"><a href="javascript:goRegister();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','../img/login_in_user_1.gif',1)"><img name="Image8" border="0" src="../img/login_in_user.gif" width="103" height="26" align="absmiddle"></a>
                        <img src="../img/login_in_user_2.gif" align="absmiddle" alt="인증서갱신" onClick="authConfirm()" style="cursor:hand">
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td><img src="../img/login_in_btn.gif" width="355" height="25"></td>
              </tr>
            </table>
          </td>
          <td width="400"> 
            <div align="right"><img src="../img/login_top2.gif" width="378" height="180"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> 
          <td colspan="2" height="20">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="2"><img src="../img/footer.gif" width="1000" height="72"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
<iframe name="hFrame" width="0" height="0"></iframe>
</body>
</html>
