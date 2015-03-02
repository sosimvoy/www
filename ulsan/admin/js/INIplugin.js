//
// INIplugn-128 Java Script
// 1. update 2002/01/29 wakano@initech.com
//  - 신한은행 호환 메소드 기능 추가.
//		EncryptInput(form)     => EncForm
//		EncryptInput2(form, r) => EncFormVerify
//		위 메소드 사용시 내부적으로 ShinHan_plugin 변수를 사용하여 처리됨
//	-. INIpluginData없이 EcnForm.. 사용시 에러처리
//
// 2. update 2002/03/13 wakano@initech.com
//  - 에플릿이나 플레쉬 같은것은 elements의 값이 "" 일경우가 있음
//		if(element.name=="") continue;
//
// 3. update 2002/11/30 wakano@initech.com
//  - GatherValeu에서 element.type이 "select"일경우 암호화된 원본이 삭제되지 않는 버그수정
//
// 4. update 2002/06/11 brson@initech.com
//  -. GatherValue, EncLink, EncLocation 수정
//		:AddServerTime이 true일때 TimeURL에서 server시간 얻어서 데이타로 추가함.
//
// 5. update 2004/07/29 wakano@initech.com
//	-. iframe사용시 프레임명(secureframe)과 전역변수명이 충돌로 인하여 secureframe을 찾지 못하는 버그수정
//	   (전역변수로 선언된 secureframe를 __secureframe__로 변경)
//
// 6. update 2005/09/07 smgrl@initech.com
//      -. GatherValue 버그 수정, 그외 주석들 정리  

var TimeURL = "http://" + window.location.host + "/admin/plugin/tools/Time.jsp";       // 약한 Replay-Attack 방지(JSP)
//var TimeURL = "http://" + window.location.host + "/initech/plugin/tools/Time.asp";       // 약한 Replay-Attack 방지(ASP)
//var TimeURL = "http://" + window.location.host + "/initech/plugin/tools/Random.jsp";   // 강한 Replay-Attack 방지(JSP 만 있음)  

var LogoURL = 'http://' + window.location.host + '/admin/plugin/site/img/plugin.initech.com.gif';

var YessignCAIP = "203.233.91.234";
var YessignCMPPort = "4512";
//var YessignCAIP = "203.233.91.71";
//var YessignCMPPort = 4512;

var cipher = "SEED-CBC";
var InitechPackage = "INITECH";
var YessignPackage = "YESSIGN";

var EnableMsg = true;
var secureframename="secureframe";
var __secureframe__=null;
var framecount = 0;
var maxframecount = 10;
var ShinHan_plugin = false;
//var ShinHan_plugin = true;
var AddServerTime=true;
//20070803 팝업 및 모달창관련 수정  
function FindSecureFrame(inframe)
{
	if(__secureframe__!=null) return __secureframe__;
	if (framecount++ > maxframecount) return null;
	if ((typeof inframe == "undefined") || (inframe == null))
	{
		return null;
	}
	else if ((typeof inframe.secureframe != "undefined")  && (inframe.secureframe != null))
	{
		framecount = 0;
		return inframe.secureframe
	} 
	else if (inframe.parent.frames.length > 0) 
	{
		
		if(inframe != inframe.parent)
		{
			return FindSecureFrame(inframe.parent);
		}
	}
    	return null;
}
//20070803 팝업 및 모달창관련 수정  
function FrameCheck()
{
	if (typeof document.INIplugin != "undefined") 
	{
		__secureframe__ = self;
	} 
	else 
	{
		framecount = 0;		
		__secureframe__ = FindSecureFrame(parent);
		
		//팝업,모달 팝업창인 경우 추가  
		//모달창을 생성시 window개체를 Arguments로 전달했을경우 사용가능  
		if (__secureframe__ == null) {
			var open_frame = null;
			open_frame = top.opener;
			
			//opener가 없을경우 모달창으로 판단 모달창으로 넘겨받은 윈도우 개체를 open_frame으로 설정  
			if ((typeof open_frame) == "undefined" && (typeof window.dialogArguments)!="undefined")
			{
				open_frame = window.dialogArguments;
			}
			//최상위 opener까지 찾아간다.  
			while((typeof open_frame) != "undefined")
			{
				 if((typeof open_frame.document) == "unknown"){break;}//opener가 존재하는지 여부 체크  
				framecount = 0;
				 __secureframe__ = FindSecureFrame(open_frame);
				 
				if (__secureframe__ != null){
					break;
				}else{							
					var t_open_frame = open_frame;
					open_frame = open_frame.top.opener;

					if ((typeof open_frame) == "undefined" && (typeof t_open_frame.window.dialogArguments)!="undefined")//opener가 없을경우 모달으로 판단  
					{
						open_frame = t_open_frame.window.dialogArguments;
					}
				}
			}
		}
	}
}


function ModuleInstallCheck()
{
	FrameCheck();
	if (__secureframe__==null) return;

	if(navigator.appName == "Netscape")
	{
		return __secureframe__.document.INIplugin;
	}
	else
	{
		if(__secureframe__.INIplugin==null || typeof(__secureframe__.INIplugin) == "undefined" || __secureframe__.INIplugin.object==null) return null;
		else return __secureframe__.INIplugin;
	}
}

function GatherValue(form, start, bErase)
{
	var strResult = "";
	var name = "";
	var value = "";
	var sel=0;

	// INIplugin-128 Install Check
	obj = ModuleInstallCheck();
	if (obj == null) return "";
	
	len = form.elements.length;
	for(i=start; i<len; i++) 
	{
		element = form.elements[i];

		//add to wakano 2002/03/13
		if(element.name=="") continue;
		if(element.name=="INIpluginData") continue;
		//add to smgrl 2004/06/22
		if(element.name=="filedata") continue;

		if ((ShinHan_plugin) && (element.name=="input")) // with for Shinhan Bank 
			continue;
		if (!((form.elements[i].type != "button") && (form.elements[i].type != "reset") && (form.elements[i].type != "submit"))) 
			continue;

		if ( ((element.type == "radio") || (element.type == "checkbox")) && (element.checked!=true) ) 
			continue;
		// File Field는 SKIP한다.  
     		if(form.elements[i].name.indexOf('file_', 0) >= 0) {
			continue;
        	}

		if (element.type == "select-one") {
			sel = element.selectedIndex;
			if(sel<0) continue;
      //2007.04.19 수정 select box 구현시 value 값이 ""인경우 서버에는 text값이 value로 넘어가는 현상 수정  
      value = element.options[sel].value;
			/*if (element.options[sel].value != ''){	
				value = element.options[sel].value;
			} else {
				//value = element.options[sel].text;
                                value = '';
			}*/
			if(bErase) element.selectedIndex = -1;
		} else{
			value = element.value;
			if(bErase && !((element.type == "radio") || (element.type == "checkbox"))) element.value = "";
		}
		
		//add to smgrl 2004/06/29
		if(element.type == "select-multiple") {
			var j;
			
			for(j=0;j<element.options.length;j++){
				if(element.options[j].selected==true){
					if(strResult!="") strResult += "&";
					if(element.id=="")
						strResult += element.name;
					else
						strResult += element.id;
					strResult += "=";
					strResult += obj.URLEncode(element.options[j].value);
					if(bErase) element.options[j].select=false;
				}
			}
			if(bErase) element.selectedIndex = -1;
			continue;
		}

		// modify wakano 2001/08/21
		if ((element.type == "checkbox") && (bErase)) element.checked = false;

		if (strResult!="") strResult += "&";

		// modify brson 2002/06/11 check element.name
		/*
		if(element.name!=""){
			if(element.id=="")
				strResult += element.name;
			else
				strResult += element.id;
			strResult += "=";
			strResult += obj.URLEncode(value);
		}
		*/
		// 2007.05.03 수정 element.name default (id가 아니라 name을 써야만 HTML 규격에 맞음)  
    strResult += element.name;
    strResult += "=";
    strResult += obj.URLEncode(value);

	}

	//modify brson 2002/06/11 
	//dt에 server time 추가  
	var ver="4,2,0,0";
	if(AddServerTime && EnableFunction(ver)) {
		if(strResult!=""){
				strResult = "__INIts__=" + obj.GetServerTime(TimeURL) + "&" + strResult;
		}
		else{
				strResult = "__INIts__=" + obj.GetServerTime(TimeURL);
		}
	}

	return strResult;
}

function GatherFileValue(form, start, bErase)
{
	var strResult = "";
	var name = "";
	var value = "";
	var sel=0;

	// INIplugin-128 Install Check
	obj = ModuleInstallCheck();
	if (obj == null) return "";
	
	len = form.elements.length;
	for(i=start; i<len; i++) 
	{
		element = form.elements[i];

		//add to wakano 2002/03/13
		if(element.name=="") continue;
		if(element.name=="INIpluginData") continue;
		if(element.name=="filedata") continue;

		if ((ShinHan_plugin) && (element.name=="input")) // with for Shinhan Bank 
			continue;
		if (!((form.elements[i].type != "button") && (form.elements[i].type != "reset") && (form.elements[i].type != "submit"))) 
			continue;

		if ( ((element.type == "radio") || (element.type == "checkbox")) && (element.checked!=true) ) 
			continue;
		// File Field
        if(form.elements[i].name.indexOf('file_', 0)>=0)
		{
	        if(strResult!="")
			{
 	        	strResult += "&";
            }
            strResult+= form.elements[i].name;
            strResult += "=";
            strResult += obj.URLEncode(form.elements[i].value);
			if(bErase) form.elements[i].value = "";
 		}
	}
	return strResult;
}

// make for Shinhan Bank
function EncryptInput(form)
{	
	ShinHan_plugin = true;
	return EncForm(form);
}

function EncryptInput2(form, r)
{
	ShinHan_plugin = true;
	return EncFormVerify(form);
}

function EncForm(form) 
{
	var INIdata = "";
	var eletemp = "";
	var filetemp = "";

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}

	filetemp = GatherFileValue(form, 0, true);
	if (filetemp !=  "")
	{
		if ((form.filedata.value = obj.MakeFileData(0, cipher, filetemp)) == "") return false; 
	}

	eletemp = GatherValue(form, 0, true);
	if ((INIdata = obj.MakeINIpluginData(10, cipher, eletemp, TimeURL))=="") return false;

	//add bye wakano 2001/01/29
	if (typeof form.INIpluginData == "undefined") 
	{
		if (ShinHan_plugin) // with for Shinhan Bank 
		{
			form.input.value = INIdata;
			form.input.name = "INIpluginData"; // for Shinhan Bank
		} else {
			alert("INIpluginData(form.name)가 필요합니다.");
			return false;
		}
	} else {
		form.INIpluginData.value = INIdata;
	}

	//alert("암호화된 데이타 : [" + INIdata + "]");
	return true;
}

function EncForm2(form1, form2) 
{
	var INIdata = "";
	var eletemp = "";
	var filetemp = "";
	
	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}

	filetemp = GatherFileValue(form1, 0, false);
	if (filetemp !=  "") 
	{
		if ((form2.filedata.value = obj.MakeFileData(0, cipher, filetemp)) == "") return false; 
	}

	eletemp = GatherValue(form1, 0, false);
	if ((INIdata = obj.MakeINIpluginData(10, cipher, eletemp, TimeURL))=="") return false;

	//add bye wakano 2001/01/29
	if (typeof form2.INIpluginData == "undefined") 
	{
		if (ShinHan_plugin) // with for Shinhan Bank 
		{
			form2.input.value = INIdata;
			form2.input.name = "INIpluginData"; // for Shinhan Bank
		} else {
			alert("INIpluginData(form.name)가 필요합니다.");
			return false;
		}
	} else {
		form2.INIpluginData.value = INIdata;
	}

   	return true;
}

function EncLink(url, encData, target, style)
{
	var queryString = "INIpluginData=";
	var INIdata;

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}

	//modify brson 2002/06/11 
	//dt에 server time 추가
	var ver="4,2,0,0";
	if(AddServerTime && EnableFunction(ver)) {
		if(encData!=""){
				encData = "__INIts__=" + obj.GetServerTime(TimeURL) + "&" + encData;
		}
		else{
				encData = "__INIts__=" + obj.GetServerTime(TimeURL);
		}
	}
	
	if ((INIdata = obj.MakeINIpluginData("10", cipher, encData, TimeURL))=="") return;
	queryString += obj.URLEncode(INIdata);
	if(url.indexOf('?', 0) < 0) url += "?";
	if((url.charAt(url.length-1)!='?') && (url.charAt(url.length-1)!='&')) url += "&";
	url += queryString;
	
	window.open(url, target, style);
}


function Idecrypt(data)
{
	var decdata = "";
	obj = ModuleInstallCheck();
	if (obj == null) return "";
	
	//alert("복호화하기전 데이타 : [" + data + "]");

	if (navigator.appName == 'Netscape') 
		decdata = unescape(obj.Decrypt(cipher, data));
	else
		decdata = obj.Decrypt(cipher, data);

	//alert("복호화된 데이타 : [" + decdata + "]");

	return decdata;
}

function Idecrypt2(data)
{
	obj = ModuleInstallCheck();
	if (obj == null) return "";
	
	if (navigator.appName == 'Netscape') 
		return unescape(obj.Decrypt2(cipher, data));
	else
		return obj.Decrypt2(cipher, data);
}

//압축+암호화 된 내용을 복호화 하여 리턴함.(by Seon Jong Kim.)
function IdecryptWithGunzip(data)
{
	var decdata = "";
	obj = ModuleInstallCheck();
	if (obj == null) return "";

	var ver = "5, 1, 6, 1";
	if(!EnableFunction(ver)) {
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 본 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg) alert(msg);
		return false;
	}
	
	if (navigator.appName == 'Netscape') 
		decdata = unescape(obj.DecryptWithGunzip(cipher, data));
	else
		decdata = obj.DecryptWithGunzip(cipher, data);

	return decdata;
}

function EncFormVerify(form) 
{
	var INIdata = "";
	var eletemp = "";
	var filetemp = "";
	var Random = TimeURL; 

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}

	filetemp = GatherFileValue(form, 0, true);
	if (filetemp !=  "") 
	{
		if ((form.filedata.value = obj.MakeFileData(1, cipher, filetemp)) == "") return false; 
	}
	
	eletemp = GatherValue(form, 0, true);
	if ((INIdata = obj.MakeINIpluginData(11, cipher, eletemp, Random))=="") return false;

	//add bye wakano 2001/01/29
	if (typeof form.INIpluginData == "undefined") 
	{
		if (ShinHan_plugin) // with for Shinhan Bank 
		{
			form.input.value = INIdata;
			form.input.name = "INIpluginData"; // for Shinhan Bank
		} else {
			alert("INIpluginData(form.name)가 필요합니다.");
			return false;
		}
	} else {
		form.INIpluginData.value = INIdata;
	}
    
   	return true;
}

function EncFormVerify2(form1, form2)
{
	var INIdata = "";
	var eletemp = "";
	var filetemp = "";
	var Random = TimeURL;

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}
	
	filetemp = GatherFileValue(form1, 0, false);
	if (filetemp !=  "") 
	{
		if ((form2.filedata.value = obj.MakeFileData(1, cipher, filetemp)) == "") return false; 
	}

	eletemp = GatherValue(form1, 0, false);
	if ((INIdata = obj.MakeINIpluginData(11, cipher, eletemp, Random))=="") return false;

	//add bye wakano 2001/01/29
	if (typeof form2.INIpluginData == "undefined") 
	{
		if (ShinHan_plugin) // with for Shinhan Bank 
		{
			form2.input.value = INIdata;
			form2.input.name = "INIpluginData"; // for Shinhan Bank
		} else {
			alert("INIpluginData(form.name)가 필요합니다.");
			return false;
		}
	} else {
		form2.INIpluginData.value = INIdata;
	}
	//alert(form2.INIpluginData.value);
   	return true;
}

/* ASP Time Check 용 함수 임시용입니다. */
function imsi_FormVerify(form1, form2)
{
	var INIdata = "";
	var eletemp = "";
	var filetemp = "";
	var TimeURL = "http://" + window.location.host + "/initech/plugin/tools/Time.asp";
	var Random = TimeURL;

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}
	
	filetemp = GatherFileValue(form1, 0, false);
	if (filetemp !=  "") 
	{
		if ((form2.filedata.value = obj.MakeFileData(1, cipher, filetemp)) == "") return false; 
	}

	eletemp = GatherValue(form1, 0, false);
	if((form2.INIpluginData.value = obj.MakeINIpluginData(11, cipher, eletemp, Random))=="") return false;
	if ((INIdata = obj.MakeINIpluginData(11, cipher, eletemp, Random))=="") return false;

	//add bye wakano 2001/01/29
	if (typeof form2.INIpluginData == "undefined") 
	{
		if (ShinHan_plugin) // with for Shinhan Bank 
		{
			form2.input.value = INIdata;
			form2.input.name = "INIpluginData"; // for Shinhan Bank
		} else {
			alert("INIpluginData(form.name)가 필요합니다.");
			return false;
		}
	} else {
		form2.INIpluginData.value = INIdata;
	}
	
   	return true;
}

function EncLinkVerify(url, encData, target)
{
	var queryString = "INIpluginData=";
	var INIdata;
	var Random = TimeURL;

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}
	
	if((INIdata = obj.MakeINIpluginData(11, cipher, encData, Random))=="") return;
	queryString += obj.URLEncode(INIdata);

	if(url.indexOf('?', 0) < 0) url += "?";
	if((url.charAt(url.length-1)!='?') && (url.charAt(url.length-1)!='&')) url += "&";
	
	url += queryString;
	window.open(url, target);
}

function InsertUserCert(cert)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;

	return obj.InsertUserCert(InitechPackage, "", cert);
}

function InsertUserCert2(cert, storage)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;

	return obj.InsertUserCert(InitechPackage, storage, cert);
}

function CertRequest(form)
{
	var dn="";
	var temp=""
	len = form.elements.length;

	form.req.value="";

	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	for (i = 0; i < len; i++) 
	{
		var name = form.elements[i].name.toUpperCase();
		var temp = form.elements[i].value;
		if(name == "C")	dn = dn + "C=" + obj.URLEncode(temp) + "&";
		if(name == "L")	dn = dn + "L=" + obj.URLEncode(temp) + "&";
		if(name == "O")	dn = dn + "O=" + obj.URLEncode(temp) + "&";
		if(name == "OU") dn = dn + "OU=" + obj.URLEncode(temp) + "&";
		if(name == "CN") dn = dn + "CN=" + obj.URLEncode(temp) + "&";
		if(name == "EMAIL")
		{
			if(temp=="") temp = " ";

			dn = dn + "EMAIL=" + obj.URLEncode(temp) + "&";
		}
	}
	
	req = obj.CertRequest(InitechPackage, "", dn, form.challenge.value); 

	if(req=="") return false;
	form.req.value = req;
	
	return true;		
}


function CertRequest2(form)
{
	var dn="";
	var temp=""
	len = form.elements.length;

	form.req.value="";

	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	for (i = 0; i < len; i++) 
	{
		var name = form.elements[i].name.toUpperCase();
		var temp = form.elements[i].value;
		if(name == "C")	dn = dn + "C=" + obj.URLEncode(temp) + "&";
		if(name == "L")	dn = dn + "L=" + obj.URLEncode(temp) + "&";
		if(name == "O")	dn = dn + "O=" + obj.URLEncode(temp) + "&";
		if(name == "OU") dn = dn + "OU=" + obj.URLEncode(temp) + "&";
		if(name == "CN") dn = dn + "CN=" + obj.URLEncode(temp) + "&";
		if(name == "EMAIL")
		{
			if(temp=="") temp = " ";

			dn = dn + "EMAIL=" + obj.URLEncode(temp) + "&";
		}
	}
	
	req = obj.CertRequest2(InitechPackage, "", dn, form.challenge.value); 

	if(req=="") return false;
	form.req.value = req;
	
	return true;		
}

function IssueCertificate(szRef, szCode)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	var Arg = "";
	var challenge = "1111";
	
	Arg += "REF=";
	Arg += obj.URLEncode(szRef);
	Arg += "&CODE=";
	Arg += obj.URLEncode(szCode);
	Arg += "&CAIP=";
	Arg += obj.URLEncode(YessignCAIP);
	Arg += "&CAPORT=";
	Arg += obj.URLEncode(YessignCMPPort);
	
	obj.CertRequest(YessignPackage, "", Arg, challenge);
}

function UpdateCertificate()
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;

	var Arg = "";
	var challenge = "1111";
	
	Arg += "CAIP=";
	Arg += obj.URLEncode(YessignCAIP);
	Arg += "&CAPORT=";
	Arg += obj.URLEncode(YessignCMPPort);
	
	//obj.CertUpdate(YessignPackage, "", Arg);
	if(obj.CertUpdate2(YessignPackage, "", Arg)=="")	return false; //캐쉬된인증서사용시  
	return true;
}

function InsertCACert(cert)
{
	// INIplugin-128 Install Check
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	obj.InsertCACert(InitechPackage, cert);
	
	return true;
}

//add bye wakano 2001/01/29 with for Shinhan Bank 
function EncryptedCertRequest(form1)
{
        ShinHan_plugin = true;
    	if(CertRequest(form1)){
        	return EncForm(form1);
    	}
    	return false;
}

function EncCertReq(form1)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	if(CertRequest(form1)) return EncForm(form1);

	return false;
	
}

function EncCertReq2(form1, form2)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	if(!CertRequest(form1))
		return false;

	return EncForm2(form1, form2);
}

function LoadCACert(CACert)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	obj.LoadCACert(CACert);
	
	return true;
}

function DeleteUserCert(DelCert)
{
	obj = ModuleInstallCheck();
	if (obj == null) return;
	
	if (obj.DeleteUserCert(InitechPackage, "", DelCert)) 
	{
		//alert("해당 인증서 삭제하였습니다.");
	}
	else
	{
		//alert("현재 사용하시는 컴퓨터에 해당 인증서가 없어서 삭제하지 못하였습니다.");
	}
	
	return;		
}

function RevokeCertificate(serial)
{
    obj = ModuleInstallCheck();
    if (obj == null) return false;
    //alert(serial);
	if(obj.DeleteUserCert(YessignPackage, "", serial))
	{
		//alert("해당 인증서 삭제하였습니다.");
		return true;
	}
	else
	{
		//alert("현재 사용하시는 컴퓨터에 해당 인증서가 없어서 삭제하지 못하였습니다.");
		return false;
	}

    return  true;
}

function SelFile(field)
{
    obj = ModuleInstallCheck();
    if (obj == null) return false;

    field.value = obj.SelectFile();
}

function InstallModule(InstallModuleURL)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	if(InstallModuleURL=="") return true;
	obj.InstallModule(InstallModuleURL);
	return true;
}

function FilterUserCert(storage, issuerAndSerial)
{
    obj = ModuleInstallCheck();
    if (obj == null) return -1;
	return obj.FilterUserCert(storage, issuerAndSerial);
}

function URLEncode(data)
{
    obj = ModuleInstallCheck();
    if (obj == null) return "";
	return obj.URLEncode(data);
}

function GetStorageSerial(storage, pin)
{
    obj = ModuleInstallCheck();
    if (obj == null) return "";
	return obj.GetStorageSerial(storage, pin);
}

function IsCheckCard(storage)
{
    obj = ModuleInstallCheck();
    if (obj == null) return false;
	return obj.IsCheckCard(storage);
}

function VerifyPin(storage, pin)
{
    obj = ModuleInstallCheck();
    if (obj == null) return false;
	return obj.VerifyPIN(storage, pin);
}

function ChangePIN(storage, oldpin, newpin)
{
    obj = ModuleInstallCheck();
    if (obj == null) return false;
	return obj.ChangePIN(storage, oldpin, newpin);
}

//add to brson :  파일암호화 V4.0.2.4
///////////////////////////////////////////////////
///////////// 파일압호화 API  /////////////////////
///////////////////////////////////////////////////

function EncFile(url, form) 
{
	var eletemp = "";
	var filetemp = "";

	obj = ModuleInstallCheck();
	if (obj == null) return false;

	filetemp = GatherFileValue(form, 0, true);
	if (filetemp !=  "")
	{
		if ((form.INIfileData.value = obj.UploadEncryptFile(url, 0, cipher, filetemp, "")) == ""){
			alert("File Upload Fail");
			return false; 
		}
		//alert("INIfileData = " + form.INIfileData.value);
	}

	eletemp = GatherValue(form, 0, true);
	if ((form.INIpluginData.value = obj.MakeINIpluginData(10, cipher, eletemp, TimeURL))=="") return false;

   	return true;
}

function EncFile2(url, form, form2) 
{
	var eletemp = "";
	var filetemp = "";

	obj = ModuleInstallCheck();
	if (obj == null) return false;

	filetemp = GatherFileValue(form, 0, false);
	if (filetemp !=  "")
	{
		//alert("fileValue = " + filetemp);
		if ((form.INIfileData.value = obj.UploadEncryptFile(url, 0, cipher, filetemp, "")) == ""){
			alert("File Upload Fail");
			return false; 
		}
		//alert("INIfileData = " + form.INIfileData.value);
	}

	eletemp = GatherValue(form, 0, false);
	if ((form2.INIpluginData.value = obj.MakeINIpluginData(10, cipher, eletemp, TimeURL))=="") return false;

   	return true;
}

function EncDown(url, args) 
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	return obj.DownloadEncryptFile(url, 0, cipher, args, "");
}

function EncDownVerify(url, args) 
{

	obj = ModuleInstallCheck();
	if (obj == null) return false;
	return obj.DownloadEncryptFile(url, 1, cipher, args, TimeURL);
}

///////////////////////////////////////////////////
///////////// 초기값 세팅 API  /////////////////////
///////////////////////////////////////////////////

function LoadCert(Cert)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	return obj.LoadCert(Cert);
}

function InitCache()
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	obj.InitCache();
	
	return true;
}

function SetCacheTime(gap)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	
	obj.SetCacheTime(gap);
	
	return true;
}

function ReSession()
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	obj.ReSession();
	return true;
}

function SetLogoPath()
{
    obj = ModuleInstallCheck();
    if (obj == null) return false;
    return obj.SetLogoPath(LogoURL);
}

function EnableCheckCRL(check)
{
    obj = ModuleInstallCheck();
    if (obj == null) return false;
    obj.EnableCheckCRL(check);
}

function SetVerifyNegoTime(time1, time2)
{
    obj = ModuleInstallCheck();
    if (obj == null) return false;
    obj.SetVerifyNegoTime(time1, time2);
}

function DisableInvalidCert(check)
{
	obj = ModuleInstallCheck();
	if (obj == null) return false;
	obj.DisableInvalidCert(check);
}

function SetTVBanking(bTV)
{
	var ver = "4, 1, 3, 0";
	if(EnableFunction(ver)) {
		obj = ModuleInstallCheck();
		if (obj == null) return false;
		obj.SetTVBanking(bTV);
	} else {
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 지원하지 않는기능입니다."
	    msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg) alert(msg);
		return false;
	}
	return true;
}

///////////////////////////////////////////////////
///////////// 기타             /////////////////////
///////////////////////////////////////////////////

function GetVersion()
{
	var ver = "4,0,0,0"
    obj = ModuleInstallCheck();
    if (obj == null) return ver;
	return new String(obj.GetVersion());
}

function EnableFunction(inputVersion)
{
	var thisArray = GetVersion().split(',');
    var inputArray = inputVersion.split(',');

	for (i=0; i<4; i++)
	{
		if (parseInt(thisArray[i], 10) > parseInt(inputArray[i], 10))
			return true;
		else if (parseInt(thisArray[i], 10) < parseInt(inputArray[i], 10))
			return false;
	}
	return true;
}

function ManageCert()
{
	obj = ModuleInstallCheck();
	if (obj == null) return; 
	
	obj.ManageCert();
}

function INIAbout()
{
	obj = ModuleInstallCheck();
	if (obj == null) return;
	obj.About();
}

function GetClientUID()
{
	var ver = "4, 5, 0, 0";
	if(EnableFunction(ver)) {
		obj = ModuleInstallCheck();
		if (obj == null) return;
	    return obj.GetClientUID();
	} else {
		var msg;
		//msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 지원하지 않는기능입니다."
	    //msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
	    msg = "\n .. 공사중입니다... "
		if (EnableMsg) alert(msg);
	}
	return;
}


///////////////////////////////////////////////////
/////////////세금계산서 API 시작/////////////////////
///////////////////////////////////////////////////

function MakeTaxData(inform, outform)
{
	var gValue = "";
    var ret  = "";
    
	len = inform.elements.length;
    outform.INIpluginTax.value="";

    // INIplugin-128 Install Check
    INIpluginObject = ModuleInstallCheck();
    if (INIpluginObject == null) return false;

    for (i = 0; i < len; i++) {
    	var name = inform.elements[i].name;
        var value = INIpluginObject.URLEncode(inform.elements[i].value);
        gValue = gValue + name + "=" + value + "&";
    }

    ret = INIpluginObject.MakeTaxData(gValue);
    if(ret == "" || ret == "CERT_NOT_FOUND") return false;
    outform.INIpluginTax.value = ret;

    return true;
}

function EncMakeTaxData(inform, outform)
{
	if(MakeTaxData(inform, outform)) {
		alert(outform.INIpluginTax.value);
		return EncForm(outform);
	}
	return false;
}

function SaveTaxData(taxData)
{
        // INIplugin-128 Install Check
        INIpluginObject = ModuleInstallCheck();
        if (INIpluginObject == null) return false;
        if(INIpluginObject.SaveTaxData(taxData)) {
                return true;
        } else {
                return false;
        }
}

function SaveTaxData2Clt(pfile, taxData)
{
        // INIplugin-128 Install Check
        INIpluginObject = ModuleInstallCheck();
        if (INIpluginObject == null) return false;
        if(INIpluginObject.SaveTaxData2Clt(pfile, taxData)) {
                return true;
        } else {
                return false;
        }
}

function ManageTax()
{
	INIpluginObject = ModuleInstallCheck();
        if (INIpluginObject == null) return false;
        if(INIpluginObject.manageTax()) {
                return true;
        } else {
                return false;
        }
}


///////////////////////////////////////////////////
///////////// 전자서명 API 시작/////////////////////
///////////////////////////////////////////////////

function IniSign(form, data, inputtitle, inputdata)
{
	var ver = "4, 1, 9, 0";
	if(EnableFunction(ver)) {
		INIpluginObject = ModuleInstallCheck();
		if (INIpluginObject == null) return false;
		
		form.PKCS7SignedData.value = INIpluginObject.IniSign("sha1", data, TimeURL, inputtitle, inputdata);
		if(form.PKCS7SignedData.value=="") return false;
		//alert(PKCS7SignedData);
		return true;
	} else {
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 전자서명 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg) alert(msg);
		return false;
	}
}

function IniSign2(form, data)
{
	var ver = "4, 1, 9, 0";
	if(EnableFunction(ver)) {
		INIpluginObject = ModuleInstallCheck();
		if (INIpluginObject == null) return false;

		form.PKCS7SignedData.value = INIpluginObject.IniSign2("sha1", data, TimeURL);
		if(form.PKCS7SignedData.value=="") return false;
		return true;

	} else {alert("this");
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 전자서명 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		//msg = "\n .. 공사중입니다... "
		if (EnableMsg) alert(msg);
		return false;
	}
}

function AddSignValue(data, name, value)
{
	if(data!="") data += "&";
	data += URLEncode(name);
	data += "=";
	data += URLEncode(value);
	return data;
}

function EncLocation(indata)
{
	var	INIdata;
	var s = indata.indexOf('?');
	var url = indata.substring(0, s) + "?INIpluginData=";
	var encData = indata.substring(s+1);

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}
	//modify brson 2002/06/11 
	//dt에 server time 추가  
	var ver="4,2,0,0";
	if(AddServerTime && EnableFunction(ver)) {
		if(encData!=""){
				encData = "__INIts__=" + obj.GetServerTime(TimeURL) + "&" + encData;
		}
		else{
				encData = "__INIts__=" + obj.GetServerTime(TimeURL);
		}
	}

	if( (INIdata = obj.MakeINIpluginData("10", cipher, encData, TimeURL)) == "" )
		return;
	url += obj.URLEncode(INIdata);

	return url;
}



function AddSignValue(data, name, value)
{
	if(data!="") data += "&";
	data += URLEncode(name);
	data += "=";
	data += URLEncode(value);
	return data;
}

function PKCS7SignedData(form, data, view)
{
	var ver = "4, 1, 14, 0";
	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}
	if(EnableFunction(ver)) {
		form.PKCS7SignedData.value = obj.PKCS7SignData("sha1", data, TimeURL, view);
		if(form.PKCS7SignedData.value=="") return false;
		return true;
	} else {
		alert("this");
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 전자서명 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg)	alert(msg);
		return false;
	}
}

function SignFile(filename)
{
	var obj = ModuleInstallCheck();
	if (obj == null) return "";
	
	return obj.SignFile(filename);
}

//이중암호화  
function EncryptTo(cert, data)
{
	var ver = "4, 3, 1, 0";
	if(EnableFunction(ver)) {
		obj = ModuleInstallCheck();
		if (obj == null) "";
		return obj.MakeINIpluginData2(cert, 0, cipher, data, "");
	} else {
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 이중암호화 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg) alert(msg);
		return "";
	}
	return "";
}

function AddValue(data, name, value)
{
	if(data!="") data += "&";
	data += URLEncode(name);
	data += "=";
	data += URLEncode(value);
	return data;
}

function SetProperty(name, value)
{
	var obj = ModuleInstallCheck();
	if (obj == null) return "";
	return obj.SetProperty(name, value);
}

// 은행의 서버인증서(BSCert)를 이용하여 암호화된 SK생성  
function makeSK(BSCert, form)
{
	var ver = "4, 5, 2, 11";
	if(!EnableFunction(ver)) {
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 이중암호화 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg) alert(msg);
		return false;
	}

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}
	if (typeof form.INIencSK == "undefined") {
		alert("INIecnSK(form.name)가 필요합니다.");
		return false;
	}
	
	form.INIencSK.value = obj.MakeSessionKeyInfo(BSCert, "SEED-CBC");
	return true;
}

// sk를 이용하여 특정form의 value값을 암호화하여 기존 value를 교체함
function EncryptToSK(name, form)
{
	var ver = "4, 5, 2, 11";
	if(!EnableFunction(ver)) {
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 이중암호화 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg) alert(msg);
		return false;
	}

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}
	for(var i=0; i<form.elements.length; i++) 
	{
		var element = form.elements[i];
		if (element.name == name) {
			element.value = obj.EncryptWithSKInfo2(form.INIencSK.value, element.value);
			return true;
		}
	}
	alert("이중암호화할 form.name(" + name + ")을 찾을수가 없습니다.");
	return false;
}



function IsCachedCert()
{
	var obj = ModuleInstallCheck();
	if (obj == null) return false;
	return obj.IsCachedCert();
}

function GetCachedCert(name)
{
	var obj = ModuleInstallCheck();
	if (obj == null) return "";
	return obj.GetCachedCert(name);
}

function CheckCRL(cert)
{
	var obj = ModuleInstallCheck();
	if (obj == null) return false;
	return obj.CheckCRL(cert);
}

function ViewCert(cert)
{
	var obj = ModuleInstallCheck();
	if (obj == null) return;
	obj.ViewCert(cert);
}

//선종 추가... 공유 값을 넣을 수 있음  
function setSharedAttribute(name, value){
	obj = ModuleInstallCheck();
	if (obj == null) return false;

	var ver = "5, 1, 5, 23";
	if(!EnableFunction(ver)) {
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 본 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg) alert(msg);
		return false;
	}
	
	obj.setSharedAttribute(name, value);
	return true;
}

function getSharedAttribute(name){
	obj = ModuleInstallCheck();
	if (obj == null) return null;

	var ver = "5, 1, 5, 23";
	if(!EnableFunction(ver)) {
		var msg;
		msg = "현재 설치된 버전 V " + GetVersion() + " 에서는 본 기능을 지원하지 않습니다."
		msg += "\n\nV " + ver + " 이상으로 업그레이드 하시기 바랍니다."
		if (EnableMsg) alert(msg);
		return false;
	}
	
	return obj.getSharedAttribute(name);
}

/*
 * 캐쉬된 인증서의 serial 값을 리턴한다.
 * add by juno at 2004/11/11 - 신한은행 클라이언트에서 인증서 확인하기
 * GetCachedData("serial") - 현재 캐쉬된 인증서의 SerialNumber를 리턴한다.
 * GetCachedData("subjectcn") - 현재 캐쉬된 인증서의 SubjectCN를 리턴한다.
 * GetCachedData("subjectdn") - 현재 캐쉬된 인증서의 SubjectDN을 리턴한다.
 * GetCachedData("issuerdn") - 현재 캐쉬된 인증서의 IssuerDN을 리턴한다.
 */
function GetCachedData(key)
{
	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}

	if(key == "serial") {
	    return obj.GetCachedData(key);
	}
        else if(key == "subjectcn") {
	    return obj.GetCachedData(key);
	}
	else if(key == "subjectdn") {
	    return obj.GetCachedData(key);
	}
	else if(key == "issuerdn") {
	    return obj.GetCachedData(key);
	}
	else {
	    alert("적당한 key가 아닙니다. [serial, subjectdn, subjectcn, issuerdn]");
	    return false;
	}
}

//파일 경로를 입력받아 해쉬값 리턴해줌.  
function FileHash(filepath)
{
	//SetProperty("FileHash", filepath);

	alert("filepath : " + filepath);
	if (obj == null){ 
		alert("obj load fail");
	}
	else
	{
		return obj.ExtendMethod("FileHash", filepath);
	}
}

function FilterCert(storage, issuerAndSerial)
{
    obj = ModuleInstallCheck();
    if (obj == null) return -1;
	
	return obj.FilterCert(storage, issuerAndSerial);
}

function EncryptFile(sk,file1,file2){
	
	alert("file1["+file1+"]");
	alert("file2["+file2+"]");
	
	obj = ModuleInstallCheck();
 	if (obj == null) return null;
 	b64key = obj.Base64(0, sk+'INISAFE NETWORK.');
 	ret = obj.EncryptFile("SEED-CBC", b64key, file1, file2);
 	alert(ret);
  	return ret;

}
//인증서 창 캐쉬기능 및 캡션변경 함수(2007.04.30)
/* 
	ForceCertQuest("ShowOnlySelectedOne");   
    - 마지막 제출된 인증서만 출력 함
    ForceCertQuest("CertReplace,MsgForChangeCert"); 
    - 모든 인증서를 출력한 후, 마지막 제출된 인증서와 다른 인증서를 선택했을 때, 사용자에게 경고 메세지를 출력후 새로 선택한 인증서로 적용함
    ForceCertQuest("MsgForChangeCert"); 
    - 모든 인증서를 출력한 후, 마지막 제출된 인증서와 다른 인증서를 선택했을 때, 사용자에게 경고 메세지를 출력후 이전 인증서로 적용함
    ForceCertQuest("CertReplace,");
    - 모든 인증서를 출력한 후, 마지막 제출된 인증서와 다른 인증서를 선택했을 때, 메세지 없이 새로 선택한 인증서로 적용함
    ForceCertQuest(""); 
    - 모든 인증서를 출력한 후, 마지막 제출된 인증서와 다른 인증서를 선택했을 때, 메세지 없이 이전 인증서로 적용함
*/
function ForceCertQuest(caption)
{
  obj = ModuleInstallCheck();
  if (obj == null) return null;

  return obj.ExtendMethod("ForceCertQuest", caption); 
  //return obj.ExtendMethod("InitCache", "on");
 }

//INISafeWebSSO.exe로부터 해당 auth_ref를 키로 저장된 값을 읽어와
//새로운 INISafeWebClient에 셋팅한다.   
function SetCachedData(auth_ref)    
{
	obj = ModuleInstallCheck();
	if (obj == null) return "";

	return obj.SetCachedData(auth_ref);
}

//INISafeWebSSO.exe에 저장된 값을 모두 지운다.    
function ClearCachedData()   
{
	obj = ModuleInstallCheck();
	if (obj == null) return "";
	return obj.ClearCachedData();
}

//INISafeWebSSO.exe를 중지시킨다.   
function EndCacheProcess()
{
	obj = ModuleInstallCheck();
	if (obj == null) return "";
	return obj.EndCacheProcess();
}

//INISafeWebSSO.exe를 실행하고 form의 auth_ref를 키로 하여
//인증서 정보를 저장한다.   
function CacheEncFormVerify(form) 
{
	var INIdata = "";
	var eletemp = "";
	var filetemp = "";
	var Random = TimeURL; 

	obj = ModuleInstallCheck();
	if (obj == null) {
		alert("암호화프레임(secureframe)을 찾을수 없습니다.");
		return false;
	}

	filetemp = GatherFileValue(form, 0, true);
	if (filetemp !=  "") 
	{
		if ((form.filedata.value = obj.MakeFileData(1, cipher, filetemp)) == "") return false; 
	}

	eletemp = GatherValue(form, 0, true);
	
	if ((INIdata = obj.CacheINIpluginData(1, cipher, eletemp, Random))=="") return false;

	//add bye wakano 2001/01/29
	if (typeof form.INIpluginData == "undefined") 
	{
		if (ShinHan_plugin) // with for Shinhan Bank 
		{
			form.input.value = INIdata;
			form.input.name = "INIpluginData"; // for Shinhan Bank
		} else {
			alert("INIpluginData(form.name)가 필요합니다.");
			return false;
		}
	} else {
		form.INIpluginData.value = INIdata;
	}

   	return true;
}

