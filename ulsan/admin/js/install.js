/* INISAFE Web V6 - install.js 
   �������� : 2007.07.19 
   �� �� �� : ������Ʈ ��
*/

/********************************************************************
1. update 2004/09/01 wakano@initech.com
 - INISAFEWeb.html���� �����Ӿ��� ����ϴ� ����
	: function CheckPlugin() �� var InstallPluginURL, CheckVersion �߰�

2. update 2005/11/01 wakano@initech.com
 - IDC�� �̿��Ͽ� �ٿ�ε� �޴� ������ ����
	: var InitechGroupID �߰�(�Ǹ����� ��������)

3. update 2005/11/01 young@initech.com
 - componentName, mimeType ���� ���� (Netscape �� ���̾�����)
********************************************************************/

var baseURL				= "http://" + window.location.host + "/admin/plugin/";

var InstallModuleURL	= baseURL + "dll/INIS60.vcs"; 
var iePackageURL		= baseURL + "down/INIS60.cab"; 
var nsPackageURL		= baseURL + "down/INIS60.jar"; 
var ieManualPackageURL	= baseURL + "down/INIS60.exe";
var nsManualPackageURL	= ieManualPackageURL;
var manualInstallURL	= baseURL + "download.jsp";

//var ieVersion			= "6,5,0,3"; 
var ieVersion			= "6,4,0,41"; 
var nsVersion			= ieVersion;

// add 2004/09/01 wakano@initech.com INISAFEWeb.html ���� ���)  
var InstallPluginURL	= "http://" + window.location.host + "/admin/plugin/site/install.html";
var CheckVersion		= ieVersion;  // ���׷��̵� Ȯ�ι��� ieVersion�� �����ϰ� ����� ����

var componentName		= "plugins/admin/INISAFE60/npINISAFEWeb60.dll";
var mimeType			= "application/x-INISAFEWebv60";
//var CLSID				= "286A75C3-11FB-4FB4-AC4A-4DD1B0750050";
var CLSID                               = "DD8C54E8-9028-4a54-96B9-30761B1F80DF";

/****************************************************/
/*********** �Ʒ��� ������ �������� ������ **********/
/****************************************************/

function getIntVersion(versionStr)
{
	var version = new Array(4);
	versionArray = versionStr.split(",");	
	for(i=0;i<4;i++)
		version[i] = parseInt(versionArray[i], 10);

	return version;
}

function myVersionCompare()
{
	var myMimetype = navigator.mimeTypes[mimeType];
	var desc = myMimetype.enabledPlugin.description;
    var index = desc.indexOf('v.', 0);
    if (index < 0)
        return -5;
    desc += ' ';

    versionString = desc.substring(index+2, desc.length);
    arrayOfStrings = versionString.split('.');

	var existing = new Array(4);
	for(i=0; i<4; i++)
    	existing[i] = parseInt(arrayOfStrings[i], 10);

	var version = getIntVersion(nsVersion);

	for(i=0; i<4; i++)
	{
		if(existing[i]>version[i])
			return (4-i);
		else if(existing[i]<version[i])
			return -(4-i);
	}

    return 0;
}

//add  brson 2002/4/16
function getUserAgentVersion()
{	
	var s = navigator.userAgent.indexOf("/");
	var	e = navigator.userAgent.indexOf(" ");
	var	ver = navigator.userAgent.substring(s+1, s+4);
	return ver;
}

//change brson 2002/4/16
function startDownload() 
{
	var trigger;
	var version;
	var newVI;
	var existingVI;
	var myMimetype = navigator.mimeTypes[mimeType];
	// If some version is already installed on this machine...
	if ( myMimetype ) {
		if(getUserAgentVersion()>=5.0){
			if(myVersionCompare()<0){
				top.location = manualInstallURL;
				return true;
			} else{
				return true;
			}
		}
		trigger = netscape.softupdate.Trigger;
		version = getIntVersion(nsVersion);
		newVI = new netscape.softupdate.VersionInfo(version[0], version[1], version[2], version[3]);
		existingVI = netscape.softupdate.Trigger.GetVersionInfo(componentName);
		if ( existingVI==null)
		{
			if(myVersionCompare()<0)
			{
				alert("INIplugin�� ��ġ�մϴ�.");
				//return trigger.StartSoftwareUpdate(nsPackageURL, trigger.DEFAULT_MODE|trigger.SILENT_MODE);
				return trigger.StartSoftwareUpdate(nsPackageURL, trigger.DEFAULT_MODE);
			}
			else
			{
				return true;
			}
		}
		else if ( existingVI.compareTo(newVI)<0)
		{
			alert("INIplugin�� ��ġ�մϴ�.");
			//return trigger.ConditionalSoftwareUpdate(nsPackageURL, componentName, newVI, trigger.DEFAULT_MODE|trigger.SILENT_MODE);
			return trigger.ConditionalSoftwareUpdate(nsPackageURL, componentName, newVI, trigger.DEFAULT_MODE);
		}
		else
		{
			return true;
		}
	}
	else
	{
		if(getUserAgentVersion()>=5.0){
			top.location = manualInstallURL;
			return true;
		}
		else{
			trigger = netscape.softupdate.Trigger;
			alert("INIplugin�� ��ġ�մϴ�.");
			//return trigger.StartSoftwareUpdate(nsPackageURL, trigger.DEFAULT_MODE|trigger.SILENT_MODE);
			return trigger.StartSoftwareUpdate(nsPackageURL, trigger.DEFAULT_MODE);
		}
	}

	return false;
}



//change brson 2002/4/16
function isInstalled()
{
	var myMimetype = navigator.mimeTypes[mimeType];
	if ( myMimetype ){
		if(getUserAgentVersion()>=5.0){
			if(myVersionCompare()>=0){
				return true;
			}else{
				return false;
			}
		} else{
			var version = getIntVersion(nsVersion);
			var newVI = new netscape.softupdate.VersionInfo(version[0], version[1], version[2], version[3]);
			var existingVI = netscape.softupdate.Trigger.GetVersionInfo(componentName);
			if(existingVI==null) {
				if(myVersionCompare()>=0){
					return true;
				}else{
					return false;
				}
			}
			else if ( existingVI.compareTo(newVI)>=0){
				return true;
			}
		}
	}
	return false;
}

var loadOK;
function LoadPlugin()
{
	loadOK=true;	
	if (navigator.appName == 'Netscape') 
	{
		if(isInstalled())
		{
			document.writeln('<EMBED type=' + mimeType + ' name="INIplugin" width=2 height=2>INIplugin Load OK</EMBED>');
		}
		else
		{
			loadOK = startDownload();
			NS_Init();
		}
	}
	else
	{
		document.writeln('<OBJECT ID="INIplugin" CLASSID="CLSID:' + CLSID + '" width=1 height=1 ');
		document.writeln('CODEBASE='+ iePackageURL + '#Version=' + ieVersion +'>');
		document.writeln('</OBJECT>');	
	}
}

var loopCount=0;
function NS_Init()
{
	
	if(!loadOK)
	{
		alert("��ġ ����");
	}
	else if(isLoaded())
	{
		return;
	}
	else if(isInstalled())
	{
		location.reload();
		//location.replace("./INIplugin.html");	
	}
	else
	{
		loopCount++;
		if(loopCount>60*10)
		{
			alert("��ġ ����");
		}
		else
		{
			setTimeout("NS_Init()", 1000);
		}
	}
}

function isLoaded()
{
	if(navigator.appName == "Netscape")
	{
		if(document.INIplugin==null)
			return false;
		else
			return true;
	}
	else
	{
		if(frame.INIplugin==null || typeof(frame.INIplugin) == "undefined" || frame.INIplugin.object==null) return false;
		else 
			return true;
	}
}

// add 2004/09/01 wakano@initech.com
function CheckPlugin()
{
	var installOK = false;
	loadOK=true;

//alert(installOK + "=[start]");

	if (navigator.appName == 'Netscape') 
	{
		if(isInstalled()) {
			document.writeln('<EMBED type=' + mimeType + ' name="INIplugin" width=2 height=2>INIplugin Load OK</EMBED>');
			installOK = true;
		}
	} 
	else
	{
		document.writeln('<OBJECT ID="INIplugin" CLASSID="CLSID:' + CLSID + '" width=1 height=1 ');
		document.writeln('CODEBASE='+ iePackageURL + '#Version=' + ieVersion +'>');
		document.writeln('</OBJECT>');	
		//document.writeln('<OBJECT ID="INIplugin" CLASSID="CLSID:' + CLSID + '" width=1 height=1 ></OBJECT>');

		//alert("check 1 = " + typeof(this.document.INIplugin));
		//alert("check 2 = " + this.document.INIplugin);
		//alert("check 3 = " + this.document.INIplugin);
		//alert("check 4 = " + this.document.INIplugin.object);

		if( !((typeof(this.document.INIplugin) == "undefined") || (this.document.INIplugin == "undefined") ||
				(this.document.INIplugin == null) || (this.document.INIplugin.object == null) ))
		{
			var thisArray = String(this.document.INIplugin.GetVersion()).split(',');
			var inputArray = CheckVersion.split(',');
			for (i=0; i<4; i++)
			{
//alert(thisArray[i] + "|" + inputArray[i]);
				if (parseInt(thisArray[i], 10) > parseInt(inputArray[i], 10)) {
					installOK = true;
					break;
				} else if (parseInt(thisArray[i], 10) < parseInt(inputArray[i], 10)) {
					break;
				} else {
					if (i==3) installOK = true;
				}
			}
//alert(installOK + "=[version check end]");

			if (installOK == true)
			{
				var inputArray2 = ieVersion.split(',');
				for (i=0; i<4; i++)
				{
//alert(thisArray[i] + "|" + inputArray2[i]);
					if (parseInt(thisArray[i], 10) > parseInt(inputArray2[i], 10)) {
						break;
					} else if (parseInt(thisArray[i], 10) < parseInt(inputArray2[i], 10)) {
						if (confirm("��ȣȭ���(INISAFE Web)�� ���׷��̵� �Ǿ����ϴ�. ���׷��̵� �Ͻðڽ��ϱ�")) {
							installOK = false;
							break;
						}
						break;
					}
				}
//alert(installOK + "=[upgrade check end]");
			}
		}
	}
//alert(installOK + "=[end]");
	if (installOK == false) top.location = InstallPluginURL;
}

