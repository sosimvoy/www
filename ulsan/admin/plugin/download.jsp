<%
/***************************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : download.jsp
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-14
* ���α׷�����	 : ������ ��ȣȭ��� �ٿ�ε�������
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<html>
<head>
	<title>��ȣȭ ���α׷� ��ġ</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	<style type="text/css">
		<!--
		.text {  font-family: "����"; font-size: 12px; line-height: 17px; text-decoration: none}
		-->
	</style>
	<script language=javascript src="/workman/plugin/install.js"></script>
	<script language="JavaScript">
		function Exit()
		{
			if (navigator.appName == 'Netscape') {
				var msg = "����ϰ� ��� �������� ��ȿ���� �ٽ� �����Ͽ��� �մϴ�.";
				alert(msg);
			} else {
				history.back();
			}
		}
		function DownLoad()
		{
			if (navigator.appName == 'Netscape')
				window.location=  nsManualPackageURL;
			else 
				window.location = ieManualPackageURL;
		}
		</script>
		<!--
		function MM_reloadPage(init) {  //reloads the window if Nav4 resized
		  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
			document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
		  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
		}
		MM_reloadPage(true);
		// -->
	</script>
</head>

<body bgcolor="#FFFFFF" topmargin="18" leftmargin="30" rightmargin="20" marginheight="18" marginwidth="30">
<div id="Layer1" style="position:absolute; left:744px; top:657px; width:61px; height:23px; z-index:1"><a href="javascript:history.back();" onfocus='this.blur()'><img src="img/before.gif" width="61" height="23" border="0"></a></div>
<div align="left"></div>
<table width="776" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top"> 
    <td colspan="4" height="137"><img src="img/title.gif" width="776" height="106"></td>
  </tr>
  <tr> 
    <td width="40" align="right" valign="top"><img src="img/number01.gif" width="16" height="16">&nbsp;</td>
    <td width="736" class="text" colspan="3" valign="top">���ͳݹ�ŷ�� ���ӽ� ��ȣȭ���α׷��� 
      �ڵ����� �ٿ�ε� �����ʰ� ������ �Ʒ��� ��ȣȭ ���α׷��� �������� ��ġ�ؾ� �մϴ�. �Ʒ��� ��ȣȭ���α׷��� Ŭ���մϴ�.</td>
  </tr>
  <tr align="center"> 
    <td colspan="4" height="103"><a href="javascript:DownLoad();" onfocus='this.blur()'><img src="img/down1.gif" width="166" height="37" border="0"></a><br>
      <br>
      <br>
      <br>
    </td>
  </tr>
  <tr> 
    <td width="40" align="right" valign="top"><img src="img/number02.gif" width="16" height="16">&nbsp;</td>
    <td width="348" valign="top" class="text" height="50">'�� ������ ��ũ�� ����'�� ������ 
      ���� 'Ȯ��' ��ư�� ������ ������ ����(��ġ)�� �����ϴ� ȭ���� ��Ÿ���� ������ ��ġ�� ������ �ֽð� Ȯ���� �����ϴ�.</td>
    <td width="40" align="right" valign="top"><img src="img/number03.gif" width="16" height="16">&nbsp;</td>
    <td width="348" valign="top" class="text" height="50">�Ʒ��� ���� �ٿ�ε� ȭ���� �����鼭 
      �ٿ��� �Ϸ�Ǹ� ���ͳ��ͽ��÷η��� ������ ���� ��Ű�� 3��° �������� ������ ������ ����� ������ ����Ŭ���ϸ� �ڵ����� ��ġ�� �˴ϴ�.</td>
  </tr>
  <tr> 
    <td width="40">&nbsp;</td>
    <td width="348" valign="top"><img src="img/down_09.gif" width="337"><br>
      <br>
      <br>
      <br>
    </td>
    <td width="40">&nbsp;</td>
    <td width="348" valign="top"><img src="img/down_10.gif" width="337"><br>
      <br>
      <br>
      <br>
    </td>
  </tr>
  <tr> 
    <td width="40" align="right" valign="top"><img src="img/number04.gif" width="16" height="16">&nbsp;</td>
    <td width="736" colspan="3" class="text">�׷��� ������ �ȵǴ� ��쿡�� <a href="http://www.initech.com/sup/faq.jsp" target="_new" style="COLOR: #0000ff; TEXT-DECORATION: underline">FAQ</a>�� 
      Ȯ���Ͻñ� �ٶ��ϴ�. (Windows95�� ��� �Ʒ��� �ڵ���ġ �������α׷��� �߰��� ��ġ�Ͽ��� �մϴ�.)</td>
  </tr>
  <tr align="center"> 
    <td colspan="4" height="93"><a href="http://download.initech.com/INIsafeWeb/plugin/50/etc/mfc42.exe" onfocus='this.blur()'><img src="img/down2.gif" width="201" height="37" border="0"></a><br>
      <br>
      <br>
    </td>
  </tr>
  <tr> 
    <td colspan="4"> 
      <hr width="100%" size="1" align="center" noshade>
    </td>
  </tr>
  <tr align="center"> 
    <td colspan="4" class="text">Copyright (c) 1998-2002 INITECH All Rights Reserved<br>
      mailto : <a href=mailto:support@initech.com>support@initech.com</a> </td>
  </tr>
</table>
</body>
</html>