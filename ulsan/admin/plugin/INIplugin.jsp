<%
/***************************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : index.jsp
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-14
* ���α׷�����	 : ������ ��ȣȭ��� üũ ������
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>

<html>
<head>
<title>INISAFE Web - Secure Frame </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Progma" content="no-cache">

<script src="/admin/js/install.js"></script>
<script src="/admin/js/cert.js"></script>
<script src="/admin/js/INIplugin.js"></script>

<script>
	var bINIinstall = false;					//�ʱ�ȭ ȭ���� �����ϰ� ������ true�� ����(�ִ��� ������)
	function Init() 
	{
		if(ModuleInstallCheck()!=null) 	{

			InstallModule(InstallModuleURL);	//�߰� DLL �ٿ�ε�(��ȣȭ�� ���� �ּ�ó��)
			
			ReSession();						//����Ű �ʱ�ȭ
			InitCache();						//������ ĳ�� �ʱ�ȭ

			//��ȭ ���� �����ϵ��� �߰�
			SetProperty("UseCertMode","1");

			//1024 bit ������ ���Ȱ��â ��¿���
			SetProperty("OldCertNotSafeMsg","1");

      SetProperty("certmanui_topmost", "yes"); // �������� ����â�� �� ���� ���� �Լ�
			SetProperty("codepage", "949");		//�ѱ�ó���� ������ �߻��� �ּ�ó�� ���
      //SetProperty("certmanui_GPKI","all");	//NPKI, GPKI������ ����
			SetVerifyNegoTime(3600, 3600);		//������ ��ȿ�Ⱓ ��������	default (0, 0)

			/*������ ���� �ɼ����� ���*/
			//LoadCACert(CACert);				//������ ���͸�(cert.js�� CACert Ȯ��)
			SetLogoPath();					//������ ����â�� �̹��� ����(INIplugin.js�� LogoURL ����
			//SetCacheTime(100)					//������ ĳ��Ÿ�� default (360)

			/*�Ʒ� �ɼ��� �ִ��� ������*/
			//EnableCheckCRL(true);				//���������� CRLȮ�ο��� default(false)
			DisableInvalidCert(true);			//�α���â���� ���/����������� ǥ�ÿ��� default (false)

			//Ư�� �����ο��� ������ ����â�� ũ�� �ϰ������ ��� 
			if (window.location.host == "tv.initech.com") SetTVBanking(true);

			if( !LoadCert(SCert))				//���������� ����(cert.js�� SCert Ȯ��)
			{
				alert("������������ �ε��ϴµ� ���н��ϴ�.\r\n���� ���α׷��� �������� �ʽ��ϴ�");
				return;
			}

			bINIinstall = true;					 
		
		} else {
			setTimeout("Init()", 1000);			//�ʱ�ȭ �ε�ȭ�� �ӵ��� ������ ������� 500(0.5��)���� ����
		}
	} /* end of Init() */

	function bSupportBrowser()
	{	
		if (navigator.appName == 'Netscape') {
			s = navigator.userAgent.indexOf("/");
			e = navigator.userAgent.indexOf(" ");
			ver = navigator.userAgent.substring(s+1, s+4);
			if (ver>=4.0) return true;
		} else {
			s = navigator.userAgent.indexOf("MSIE ");
			if (s < 0) alert("����Ȯ�κҰ�");
			//if (s < 0) s = navigator.userAgent.indexOf("Windows NT ");
			tmp = navigator.userAgent.substring(s+5);
			e = tmp.indexOf(";");
			ver = tmp.substring(0, 3);
			if (ver>=4.0) return true;
		}

		var msg = "���� ����ϰ� ��ô� ��������\n";
			msg += "INISAFE Web Plugn�� �������� �ʽ��ϴ�.\n";
			msg += "�ֽŹ������� ���׷��̵� �Ͻ��� �ٽ� �����Ͻñ� �ٶ��ϴ�.\n";
			msg += "\n\��������[";
			msg += ver + "]";
			msg += "\n\n������ ����\n";
			msg += "navigator.appName \t[" + navigator.appName + "]\n";
			msg += "navigator.userAgent \t[" + navigator.userAgent + "]\n";
			msg += "navigator.appCodeName \t[" + navigator.appCodeName + "]\n";
			msg += "navigator.appVersion \t[" + navigator.appVersion + "]\n";
			msg += "navigator.systemLanguage \t[" + navigator.systemLanguage + "]\n";
			msg += "navigator.javaEnabled() \t[" + navigator.javaEnabled() + "]\n";
			msg += "navigator.systemLanguage \t[" + navigator.systemLanguage + "]\n";
			msg += "navigator.language \t\t[" + navigator.language + "]\n";
			msg += "navigator.platform \t\t[" + navigator.platform + "]\n";
		
			alert(msg);
			//history.back();
		return false;
	} /* end of bSupportBrowser() */	

</script>

</head>

<body bgcolor="red" onload="Init();">
<script language=javascript>
	if (bSupportBrowser())
	{
		document.write("<center><font size=2>");
		LoadPlugin(); 
		//CheckPlugin(); 
		document.write("INITECH INISAFEWeb Install Info - ");
		document.write(" ���Ӱ��ɹ��� : IE[" + ieVersion + "] NS[" + nsVersion + "]");
		document.write(" ��ġ���� : [" + GetVersion() + "]");
		document.write(" " + secureframename + "(" + window.location.host + ")");
		document.write("</font></center>");
	}
</script>
</body>
</html>
