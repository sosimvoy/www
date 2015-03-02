/* INISAFE Web V6 - noframe.js */

/********************************************************************
1. 사용법 반드시 <body></body> 사이에 다음과 같이 사용해야함
  <script language="javascript" src="/initech/plugin/noframe.js"></script>
********************************************************************/

var checkFrame = false;
function StartIniPlugin()
{
	// INIplugin.js, install.js, cert.js 로딩여부 확인  
	if ( (typeof(LoadPlugin) == 'undefined') || (typeof(SCert) == 'undefined') || (typeof(LoadCert) == 'undefined'))
	{
		if (checkFrame == false)
		{
			checkFrame = true;
			//alert("reCheck");
			setTimeout("StartIniPlugin()", 2000);
			return;
		} else 
			alert("install.js/cert.js/INIplugin.js 파일이 include되지 않았습니다.")
			return;
	}

	//이중로드 방지 (특이한 경우 모듈 인스톨 체크에서 object 참조 오류 발생이 가능할 수도 있음 이때는 제거를 하도록 함)  
	if (typeof (ModuleInstallCheck) == "function") {
 		if (ModuleInstallCheck() != null) {
		//	alert("find secureframe skip noframe...");
			return;
		}
	}

    //document.writeln('<span id="secure" style="display:block;">');
    //document.writeln('noframe.js start');
	CheckPlugin();
	// 마이너 업데이트 수행   
	InstallModule(InstallModuleURL);
	//LoadCACert(CACert);
	//SetProperty("certmanui_GPKI", "all");	//GPKI 인증서 모두다 보임   
  SetProperty("E2ECrypt", "kings");        //확장E2E 킹스정보통신
  SetProperty("certmanui_securekey", "kings");     //킹스정보통신 인증서제출창 입력필드 키보드보안 
	DisableInvalidCert(true);  //로그인창에서 페기/만료된인증서 표시여부 default (false)   


	if (!LoadCert(SCert)) {
		//alert("보안 재설정중입니다.");
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
