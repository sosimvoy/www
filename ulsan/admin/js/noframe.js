/* INISAFE Web V6 - noframe.js */

/********************************************************************
1. ���� �ݵ�� <body></body> ���̿� ������ ���� ����ؾ���
  <script language="javascript" src="/initech/plugin/noframe.js"></script>
********************************************************************/

var checkFrame = false;
function StartIniPlugin()
{
	// INIplugin.js, install.js, cert.js �ε����� Ȯ��  
	if ( (typeof(LoadPlugin) == 'undefined') || (typeof(SCert) == 'undefined') || (typeof(LoadCert) == 'undefined'))
	{
		if (checkFrame == false)
		{
			checkFrame = true;
			//alert("reCheck");
			setTimeout("StartIniPlugin()", 2000);
			return;
		} else 
			alert("install.js/cert.js/INIplugin.js ������ include���� �ʾҽ��ϴ�.")
			return;
	}

	//���߷ε� ���� (Ư���� ��� ��� �ν��� üũ���� object ���� ���� �߻��� ������ ���� ���� �̶��� ���Ÿ� �ϵ��� ��)  
	if (typeof (ModuleInstallCheck) == "function") {
 		if (ModuleInstallCheck() != null) {
		//	alert("find secureframe skip noframe...");
			return;
		}
	}

    //document.writeln('<span id="secure" style="display:block;">');
    //document.writeln('noframe.js start');
	CheckPlugin();
	// ���̳� ������Ʈ ����   
	InstallModule(InstallModuleURL);
	//LoadCACert(CACert);
	//SetProperty("certmanui_GPKI", "all");	//GPKI ������ ��δ� ����   
  SetProperty("E2ECrypt", "kings");        //Ȯ��E2E ŷ���������
  SetProperty("certmanui_securekey", "kings");     //ŷ��������� ����������â �Է��ʵ� Ű���庸�� 
	DisableInvalidCert(true);  //�α���â���� ���/����������� ǥ�ÿ��� default (false)   


	if (!LoadCert(SCert)) {
		//alert("���� �缳�����Դϴ�.");
		//location.reload();
		setTimeout("StartIniPlugin()", 1000);
	}

}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
if (typeof(StartIniPlugin) != 'undefined')
	StartIniPlugin();
else
	setTimeout("StartIniPlugin()", 1000);
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
