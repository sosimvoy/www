//
// INIplugin128 Java Script
//
// 1. update 2000.04.12 wakano@initech.com

function MsgBox2(szMsg)
{
	alert(szMsg);
}

function ToUpper(text)
{
	var value = text.value;
	text.value = value.toUpperCase();
}

function KeyTest(key) {
	if (navigator.appName == 'Netscape')
		keyValue =  key.which;
	else
		keyValue = event.keyCode;

	alert(keyValue);
}

function KeyCheckEMAIL(key) {
	if (navigator.appName == 'Netscape')
		keyValue =  key.which;
	else
		keyValue = event.keyCode;

	if (  ((keyValue >= 48) && (keyValue <= 57 )) //0-9
		|| ((keyValue >= 65) && (keyValue <= 90 )) //A-Z
		|| ((keyValue >= 97) && (keyValue <= 122 )) //a-z
		|| (keyValue == 45) //-
		|| (keyValue == 46) //.
		|| (keyValue == 64) //@
		|| (keyValue == 95) //@
		)
		return true;

	return false;
}

function KeyCheckPCBANKID(key) {
	return KeyCheckID(key); 
}

//���ڿ� �빮��(�ҹ��ڸ� �빮�ڷ� ����)�� �Է�
function KeyCheckID(key) {
	if (navigator.appName == 'Netscape')
		keyValue = key.which;
	else
		keyValue = event.keyCode;

	//�ҹ��ڸ� �빮�ڷ� ����
 	if ((keyValue >= 97) && (keyValue <= 122 )) { //a-z
		if (navigator.appName == 'Netscape')
			key.which = key.which & 0xDF; //�̰� �߾ȵ� �ƴ»�� ��ġ����..
		else 		
			event.keyCode = event.keyCode & 0xDF;  // �빮�ڷ� ����
		return true;
	}
		
  	// ���ڿ� ����, backspace�� �Է��Ҷ� return true)
	if (  ((keyValue >= 48) && (keyValue <= 57 )) //0-9
		|| ((keyValue >= 65) && (keyValue <= 90 )) //a-z
		|| ((keyValue >= 97) && (keyValue <= 122 )) //A-Z
		)
		return true;

	return false;
}

function KeyCheckNum(key) {
	if (navigator.appName == 'Netscape')
		keyValue = key.which;
	else
		keyValue = event.keyCode;

	if (  ((keyValue >= 48) && (keyValue <= 57 )) //0-9
		)
		return true;
	return false;
}

var showMsg_Start = 0;
var showMsg_Msg = "";
var showMsg_Time = 1000;
function setMsg(msg, start, time) {
	showMsg_Start = start;
	showMsg_Msg = msg;
	showMsg_Time = time;
}
function showMsg() {
	showMsg_Start++;
	if (showMsg_Start > showMsg_Msg.length) showMsg_Start = 0;
	window.status = showMsg_Msg.substr(0, showMsg_Start);
  	setTimeout("showMsg()", showMsg_Time);
}

function showTime() {
  	var now = new Date();
  	var hrs = now.getHours();
  	var disp = ((hrs>12) ? (hrs-12) : hrs) + ":";
 	disp += now.getMinutes() + ":" + now.getSeconds();
  	disp += ((hrs>12) ? " PM" : " AM");
  	window.status = disp;
  	setTimeout("showTime()", 1000);
}

