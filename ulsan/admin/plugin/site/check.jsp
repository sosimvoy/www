<%-- 2002/05/16 INIplugn ��ġ������ ��¿��� Ȯ��
 	���ϸ� :	check.jsp 
	����(ȣ����)
		url		: ��ġȮ���� ���������� ���(�����η� �Է�?)
				default : /initech/plugin/start_pub.html
		check : ��ġȭ����¿���(���������� �����) 
				default : no(ȭ����¾���)
				
	��뿹..
        check.jsp?url=/initech/demo/start.html
        check.jsp?url=/initech/demo/start.html&check=yes
--%>

<%@ page language="java" %>
<%@ page contentType="text/html;charset=EUC-KR" %>
<%@ page import="java.io.*,java.util.*,java.lang.*" %>
<%
	response.setHeader("Cache-Control", "no-cache, post-check=0, pre-check=0");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");

	String check = request.getParameter("check");
	String url = request.getParameter("url");
	if ( (url == null) || (url.equals(""))) url = "/admin/plugin/start.html";
%>

<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<title> INIplugin-128 Install Check Page</title>
<style type="text/css">
<!--
.text {  font-family: "����"; font-size: 12px; text-decoration: none; color: #FFFFFF}
-->
</style>
<script language=javascript src="/admin/js/INIplugin.js"></script>
<script language=javascript src="/admin/js/install.js"></script>
<script language="JavaScript">
var startURL = "/admin/common/login.jsp";
var installURL = "./check.jsp?check=yes&url=" + startURL;
var UpradeCheck = true;

function Check() 
{
	var findframe = FindSecureFrame(parent);
	if (findframe == null) {
		alert("��ȣȭ ������(secureframe)�� ã���� �����ϴ�.");
	}
	else if( ModuleInstallCheck() != null && findframe.bINIinstall)	{
        	window.location = startURL;
	} 
	else {
    	setTimeout("Check()", 500);
	}
}

function setupPlugin()
{
	top.location = manualInstallURL;
}
</script>
</head>


<%  // ��ġ���θ� Ȯ�� : ��ġ���Ȼ����̸� ����ȭ������ �����... �������� ����...
	if ( (check == null) || (!check.equals("yes"))) { 
%>
<body>
<script language="JavaScript">
	var url = startURL;

	//����Ʈ�η�.... OBject//
	//document.writeln('<OBJECT ID="XecureWeb" CLASSID="CLSID:7E9FDB80-5316-11D4-B02C-00C04F0CD404"><COMMENT><EMBED type="application/x-SoftForum-XecSSL40" hidden=true name="XecureWeb"><NOEMBED></COMMENT>No XecureWeb 4.0 PlugIn</NOEMBED></EMBED></OBJECT>');

	document.writeln('<OBJECT ID="INIplugin_check" CLASSID="CLSID:6AD92401-CE2D-452B-AA63-1291D60EC2D2" width=1 height=1><COMMENT><EMBED type="application/x-INIplugin128v40" hidden="true" name="INIplugin_check"><NOEMBED></COMMENT>No IniPlugin V4</NOEMBED></EMBED></OBJECT>');

if (navigator.appName == 'Netscape') 
{
	if( (typeof(this.document.INIplugin_check) == "undefined") || (this.document.INIplugin_check==null) ) {
		//alert("not Install");
		url = installURL;
	} else if (UpradeCheck == true) {
		var thisArray = this.INIplugin_check.document.GetVersion().split(',');
	    var inputArray = ieVersion.split(',');
	    //var inputArray = "5,1,15,0".split(',');
		for (i=0; i<4; i++)
		{
			if (parseInt(thisArray[i], 10) > parseInt(inputArray[i], 10)) {
				break;
			} else if (parseInt(thisArray[i], 10) < parseInt(inputArray[i], 10)) {
				url = installURL;
				break;
			}
		}
	}
} 
else 
{
	if( (typeof(this.document.INIplugin_check) == "undefined") || (this.document.INIplugin_check==null) || (this.document.INIplugin_check.object==null) ) {
	//if( (typeof(this.document.INIplugin_check) == "undefined") || (this.document.INIplugin_check==null) ) {
		//alert("not Install");
		url = installURL;
	} else if (UpradeCheck == true) {
		var thisArray = this.INIplugin_check.GetVersion().split(',');
	    var inputArray = ieVersion.split(',');
	    //var inputArray = "5,1,15,0".split(',');
		for (i=0; i<4; i++)
		{
			if (parseInt(thisArray[i], 10) > parseInt(inputArray[i], 10)) {
				break;
			} else if (parseInt(thisArray[i], 10) < parseInt(inputArray[i], 10)) {
				url = installURL;
				break;
			}
		}
	}
}
//	alert(url);
//   	window.location = startURL;
	location.replace(url);
</script> 
</body>
</html>

<%  //��ġ���Ǿ� ���� �ʰų� Upgrade�� ȭ�� ���.. �����κ����� �Ʒ����븸 �Ұ�...
	} else {
%>

<body onload="Check();" bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" link="#00FF99" vlink="#00FF99" alink="#00FF99">
<div align="center">
  <p>&nbsp;</p><p>&nbsp;</p>
  <table width="423" border="0" cellspacing="0" cellpadding="0" height="348">
    <tr> 
      <td background="/admin/plugin/site/img/plugin_bg.gif" valign="top" align="right">
        <table width="418" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="38"><img src="/admin/plugin/site/img/plugin.gif" width="408" height="15"></td>
          </tr>
          <tr> 
            <td height="239" valign="top" class="text"> 
              <center>
                <b><font color="#00FF99"><br>
                **�� ��ǻ���� ��ȣȭ ���α׷� Ȯ�����Դϴ�.**</font></b><br>
                <font color="#00FF99">(��ø� ��ٷ��ֽñ� �ٶ��ϴ�) <br>
                <br>
                </font> 
              </center>
              <div align="left">
                <table width="410" border="0" cellspacing="0" cellpadding="10">
                  <tr> 
                    <td  class="text"> ó�� �����Ͻ� ��쿡�� ��ġ�� ���Ῡ �� 10�ʿ��� ������ �ҿ� �Ǹ� 
                      ���� ����ڴ� ��ȣȭ ���α׷� �ε��� ���Ͽ� 5������ �ҿ� �˴ϴ�.<br>
                      <br>
                      ��ȣȭ ���α׷��� ��ġ���θ� ����<font color="#FFFF00"> <b><font color="#00FF99">''���Ȱ��''</font></b></font>â�� 
                      ��Ÿ���� �ݵ��<font color="#FFFF00"><b><font color="#00FF99">''��''</font></b></font>�� 
                      �����Ͽ� �ֽʽÿ�. ��Ҹ� ���� �Ͻ� ���� <a href="javascript:top.secureframe.document.location.reload()">���ΰ�ħ</a> 
                      �� �Ͽ� �ٽ� ��ġ �Ͻñ� �ٶ��ϴ�.<br>
                      �̴� ������ ��� ����� �����ϱ� ���� �ʿ��� ����Ʈ��� ��ġ�ϴ� �����̸� '�ƴϿ�'�� �����Ͻô� 
                      ���� ���� ������ �Ѿ�� �����ϴ�.<br>
                      <br>
                      �����߻��� �Ǵ� ȭ���� ��ð� ������ <font color="#FFFF00"><a href="javascript:setupPlugin();"><b><font color="#00FF99">��ȣȭ 
                      ���α׷� ��ġ�ȳ�</font></b></a></font><font color="#00FF99"><b>''</b></font>�� �����Ͽ� ��ġ�� �̿��Ͻñ� �ٶ��ϴ�. </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr> 
            <td height="65" align="center" class="text"><font color="#000000">��ֽ� 
              ���� : ������<a href=mailto:support@initech.com  class="text"><font color="#3366FF"> 
              support@initech.com</font></a></font></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  
</div>
</body>
</html>
<%  //������ ������ ��������..
	}
%>

