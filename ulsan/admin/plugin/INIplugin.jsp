<%
/***************************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : index.jsp
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-14
* 프로그램내용	 : 관리자 암호화모듈 체크 프레임
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
	var bINIinstall = false;					//초기화 화면을 무시하고 싶으면 true로 설정(최대한 사용금지)
	function Init() 
	{
		if(ModuleInstallCheck()!=null) 	{

			InstallModule(InstallModuleURL);	//추가 DLL 다운로드(암호화만 사용시 주석처리)
			
			ReSession();						//세션키 초기화
			InitCache();						//인증서 캐쉬 초기화

			//고도화 모드로 동작하도록 추가
			SetProperty("UseCertMode","1");

			//1024 bit 인증서 보안경고창 출력여부
			SetProperty("OldCertNotSafeMsg","1");

      SetProperty("certmanui_topmost", "yes"); // 인증서나 제출창을 맨 위로 띄우는 함수
			SetProperty("codepage", "949");		//한글처리에 문제가 발생시 주석처리 요망
      //SetProperty("certmanui_GPKI","all");	//NPKI, GPKI인증서 연동
			SetVerifyNegoTime(3600, 3600);		//인증서 유효기간 범위오차	default (0, 0)

			/*인증서 사용시 옵션으로 사용*/
			//LoadCACert(CACert);				//인증서 필터링(cert.js의 CACert 확인)
			SetLogoPath();					//인증서 선택창의 이미지 변경(INIplugin.js의 LogoURL 설정
			//SetCacheTime(100)					//인증서 캐쉬타임 default (360)

			/*아래 옵션은 최대한 사용금지*/
			//EnableCheckCRL(true);				//공인인증서 CRL확인여부 default(false)
			DisableInvalidCert(true);			//로그인창에서 페기/만료된인증서 표시여부 default (false)

			//특정 도메인에서 인증서 선택창을 크게 하고싶을때 사용 
			if (window.location.host == "tv.initech.com") SetTVBanking(true);

			if( !LoadCert(SCert))				//서버인증서 세팅(cert.js의 SCert 확인)
			{
				alert("서버인증서를 로드하는데 실패습니다.\r\n보안 프로그램이 동작하지 않습니다");
				return;
			}

			bINIinstall = true;					 
		
		} else {
			setTimeout("Init()", 1000);			//초기화 로딩화면 속도가 느리다 생각들면 500(0.5초)으로 변경
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
			if (s < 0) alert("버전확인불가");
			//if (s < 0) s = navigator.userAgent.indexOf("Windows NT ");
			tmp = navigator.userAgent.substring(s+5);
			e = tmp.indexOf(";");
			ver = tmp.substring(0, 3);
			if (ver>=4.0) return true;
		}

		var msg = "지금 사용하고 계시는 브라우져는\n";
			msg += "INISAFE Web Plugn이 지원하지 않습니다.\n";
			msg += "최신버전으로 업그레이드 하신후 다시 접속하시기 바랍니다.\n";
			msg += "\n\버전정보[";
			msg += ver + "]";
			msg += "\n\n브라우져 정보\n";
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
		document.write(" 접속가능버전 : IE[" + ieVersion + "] NS[" + nsVersion + "]");
		document.write(" 설치버전 : [" + GetVersion() + "]");
		document.write(" " + secureframename + "(" + window.location.host + ")");
		document.write("</font></center>");
	}
</script>
</body>
</html>
