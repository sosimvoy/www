
function typeDefine() {
	if( this.userType == null || typeof(this.userType) == "undefined" ) {
		return;
	}

	if( this.userType == "date" ) {
		this.onkeypress = keyNumeric;
		this.onkeyup = keyupDateObj;
		this.onfocus = selectObj;
		if( this.onblur == null ) {
			this.onblur = new Function("getDateFormatObj(this); return checkDateObj(this, this.desc);");
		}

		this.style.imeMode = "disabled";
		this.style.textAlign = "center";
		if( this.maxLength > 10000 ) this.maxLength="10";
		if(document.body.insertDash) {
			this.select();
			this.blur();
		}
	} else if(this.userType == "money") { /* 금액 입력 : ex) 5,105,000 */
		this.onkeypress = keyNumericDash;
		this.onkeyup = new Function("keyupCurrencyObj(this);");
		this.onfocus = selectObj;
		
		if ( this.onblur == null ) {
			this.onblur = new Function("getCurrencyFormatObj(this); return checkCurrencyObj(this, this.desc);");
		}
		this.style.imeMode = "disabled";
		this.style.textAlign = "right";
		if( this.maxLength > 10000 ) this.maxLength="15";
		if(document.body.insertComma) {
			this.select();
			this.blur();
		}

	} else if(this.userType == "sp_money") { /* 금액 입력 : ex) 5,105,000 */
		this.onkeypress = keyNumericDash;
		this.onkeyup = new Function("keyupCurrencyObj(this);");
		this.onfocus = selectObj;
		
		if ( this.onblur == null ) {
			this.onblur = new Function("getCurrencyFormatObj(this); return checkCurrencyObj(this, this.desc);");
		}
		this.style.imeMode = "disabled";
		this.style.textAlign = "right";
		if( this.maxLength > 10000 ) this.maxLength="15";
		/*
		if(document.body.insertComma) {
			this.select();
			this.blur();
		}
*/
	} else if(this.userType == "eng") { /* 영문 입력 : ex) abcd */
		this.onkeypress = keyAlphabat;
		
		this.onfocus = selectObj;
		this.onblur = new Function("return isAlphabatObj(this, this.desc);");
		this.style.imeMode = "disabled";
		this.style.textAlign = "center";
		if( this.maxLength > 10000 ) this.maxLength="15";

	} else if(this.userType == "engNumber") { /* 영문 숫자 입력 : ex) abcd123 */
		this.onkeypress = keyEngNumber;
		
		this.onfocus = selectObj;
		this.onblur = new Function("return isEngNumberObj(this, this.desc);");
		this.style.imeMode = "disabled";
		this.style.textAlign = "left";
		if( this.maxLength > 10000 ) this.maxLength="15";

	} else if(this.userType == "number") { /* 숫자 입력 : ex) 1500 */
		this.onkeypress = keyNumeric;
		this.onfocus = selectObj;
		
		this.style.imeMode = "disabled";
		this.style.textAlign = "right";
		if( this.maxLength > 10000 ) this.maxLength="15";
		 
	} else if(this.userType == "decimal") { /* 소수점 입력 : ex) 155.87 소수점 2자리까지만 입력가능 */
		this.onkeypress = keyDecimal;
		
		this.onfocus = selectObj;
		var oldHandler = this.onblur;
		this.onblur = new Function("checkDecimalObj(this, 3, 2, this.desc);");
		if( oldHandler != null ) {
			this.attachEvent("onblur", oldHandler);
		}
		this.style.imeMode = "disabled";
		this.style.textAlign = "right";
		if( this.maxLength > 10000 ) this.maxLength="6";

	}else if(this.userType == "decimal1") { /* 소수점 입력 : ex) 1555555.8 소수점 1자리까지만 입력가능 */
		this.onkeypress = keyDecimal;
		
		this.onfocus = selectObj;
		var oldHandler = this.onblur;
		this.onblur = new Function("checkDecimalObj(this, 7, 1, this.desc);");
		if( oldHandler != null ) {
			this.attachEvent("onblur", oldHandler);
		}
		this.style.imeMode = "disabled";
		this.style.textAlign = "right";
		if( this.maxLength > 10000000 ) this.maxLength="8";

	} else if( this.userType == "jumin" ) {
		this.onkeypress = keyNumeric;
		this.onfocus = selectObj;
		
		this.style.imeMode = "disabled";
		if( this.autoNext != null ) {
			this.maxLength = "6";
			this.onkeyup = autoTAB;
		} else {
			this.maxLength = "7";
		}
	} else if( this.userType == "account" ) {
		this.onkeypress = keyNumericDash;
		this.onfocus = selectObj;
		this.style.imeMode = "disabled";
		if( this.maxLength > 10000 ) this.maxLength="20";

	}

	if( this.autoNext != null ) {
		this.onkeyup = autoTAB;
	}
}


/* 모든 input 필드 초기화 */
function typeInitialize() {
	//input 초기화
	var allInput = document.all.tags("INPUT");
	for( var i=0 ; i<allInput.length ; i++ ) {
		var input = allInput[i];
		if( input.type == "radio" ) {
			// 타입정의 함수 할당
			input.init = radioInit;
			// 타입정의 함수 실행
			input.init();
		} else if( input.type == "checkbox" ) {
			// 타입정의 함수 할당
			input.init = checkboxInit;
			// 타입정의 함수 실행
			input.init();
		} else {
			// 타입정의 함수 할당
			input.typeDefine = typeDefine;
			// 타입정의 함수 실행
			input.typeDefine();
		}
	}
	//select 초기화
	var allSelect = document.all.tags("SELECT");
	for( var i=0 ; i<allSelect.length ; i++ ) {
		var select = allSelect[i];
		// 초기화 함수 할당
		select.init = selectInit;
		// 초기화 함수 실행
		select.init();
	}

}

/* select 객체 관련 초기화 작업 */
function selectInit() {
	//iValue 값이 있으면 선택해준다.
	if( this.iValue == null || this.iValue == "" ) return;
	this.value = this.iValue;
}

/* radio 객체 관련 초기화 작업 */
function radioInit() {
	//iValue 값이 있으면 선택해준다.
	if( this.iValue == null || this.iValue == "" ) return;
	if( this.iValue == this.value ) {
		this.checked = true;
	}
}

/* checkbox 객체 관련 초기화 작업 */
function checkboxInit() {
	//iValue 값이 있으면 선택해준다.
	if( this.iValue == null || this.iValue == "" ) return;
	if( this.iValue == this.value ) {
		this.checked = true;
	}
}


/* 모든 폼을 초기화한다.(자동체크 옵션 정의, 폼체크 함수 할당, comma 체크 함수 할당,  기존 onsubmit 헨들러 저장 등등) */
function formInitialize() {
	var forms = document.forms;
	for( var i=0 ; i<forms.length ; i++ ) {
		var form = forms[i];
		//자동체크 옵션
		form.autoCheck = false;
		//폼 체크 함수 할당
		form.checkForm = checkForm;
		//콤마 자동제거 옵션
		form.autoCheckComma = false;
		//"-" 자동제거 옵션
		form.autoCheckDash = false;
		//comma 체크 함수 할당
		form.checkComma = checkComma;
		//"-" 체크 함수 할당
		form.checkDash = checkDash;
		//화면에서 onsubmit 에 헨들러를 넣은경우 저장
		form.orgSubmitHandler = form.onsubmit;
		//onsubmit 에서 폼체크 함수 할당
		form.onsubmit = form.checkForm;	
		//submit 함수 할당
		form.formSubmit = formSubmit;
	}
}
/*submit 하기전에 userType="money" 인경우를 체크해서 comma 를 제거한다. */
function checkComma() {
	if( this.autoCheckComma ) {
		var allInput = this.tags("INPUT");
		for( var i=0 ; i<allInput.length ; i++ ) {
			var input = allInput[i];
			if(input.userType != null) {
				if( input.userType == "money" ) {
					input.value = out_comma(input.value);
				}
			}
		}
	}
}

/*submit 하기전에 userType="date" 인경우를 체크해서 "-" 를 제거한다. */
function checkDash() {
	if( this.autoCheckDash ) {
		var allInput = this.tags("INPUT");
		for( var i=0 ; i<allInput.length ; i++ ) {
			var input = allInput[i];
			if(input.userType != null) {
				if( input.userType == "date" ) {
					input.value = out_dash(input.value);
				} else if( input.userType == "account" ) {
					input.value = out_dash(input.value);
				}
			}
		}
	}
}

function checkFixLength() {
	if( this.value.length != this.fixLength ) {
		alert(this.desc+ " 는(은) "+ this.fixLength + "자리 입력항목 입니다.");
		this.select();
		return false;
	}
	return true;
}

/* 자동탭 기능 */
function autoTAB() {
	if( this.maxLength == this.value.length ) {
		if( event.keyCode != 9 && event.keyCode != 16) {
			try {
				//var nextInput = eval(this.autoNext);
				var nextInput = this.nextSibling.nextSibling;
				nextInput.select();
			} catch(e) {}
		}
	}
}


/* 전송버튼을 사용하지 않고 직접 스크립트로 submit 하는경우 */
function formSubmit() {
	
	this.autoCheck = true;
	this.autoCheckComma = true;
	this.autoCheckDash = true;
	if(this.checkForm()) this.submit();
}

/* onload 되기전에 form.formSubmit() 호출시 에러가 발생할 경우 */
function eSubmit(form) {
	try {
		form.formSubmit();
	} catch(e) {
		formInitialize();
		form.formSubmit();
	}
}

/**
 * form 체크  
 * 전송버튼을 쓴경우나, 명시적으로 formSubmit() 함수를 호출한경우 모두 처리
 * 1. 필수입력 항목 체크
 * 2. 오류가 없고, 전송버튼을 사용한경우 페이지에서 onsubmit 에 할당한 함수가 있다면 함수를 실행시킨다.
 * 3. formSubmit() 을 사용한경우를 위해  true/false return
 */
function checkForm() {
	var returnVal = true;
	
	if( this.autoCheck ) {
		//input check
		var allInput = this.tags("INPUT");
		for( var i=0 ; i<allInput.length ; i++ ) {
			var input = allInput[i];
			input.checkRequired = checkRequired;
			if( !input.checkRequired() ) {
				return false;
			}
			if(input.fixLength != null) {
				input.checkFixLength = checkFixLength;
				if( !input.checkFixLength() ) {
					return false;
				}
			}
			
		}
		//select check
		var allSelect = this.tags("SELECT");
		for( var i=0 ; i<allSelect.length ; i++ ) {
			var input = allSelect[i];
			input.checkRequired = checkRequired;
			if( !input.checkRequired() ) {
				return false;
			}
		}
		//textarea check
		var allTextArea = this.tags("TEXTAREA");
		for( var i=0 ; i<allTextArea.length ; i++ ) {
			var input = allTextArea[i];
			input.checkRequired = checkRequired;
			if( !input.checkRequired() ) {
				return false;
			}
		}

		if(event) event.returnValue = true;
		//this.checkComma(); 
	}



	if(this.orgSubmitHandler != null) {
		//화면에서 명시적으로 onsubmit 을 사용하는 경우 실행
		returnVal = this.orgSubmitHandler();
	}

	returnVal = checkPage();
	if( returnVal ) {
		this.checkComma();
		this.checkDash();
	}
	return returnVal;
}

function checkRequired() {
	if(this.required != null) {
		if( this.value == "" ) {
			if( this.desc != null ) {
				alert(this.desc+" 는[은] 필수입력 입니다.");
			} else {
				alert("는[은] 필수입력 입니다.");
			}
			this.focus();
			if(event) {
				event.returnValue = false;
				
			}
			return false;
		}
	}
	return true;
}


/* 폼 체크 이후에 최종적으로 페이지에서 체크해야할 내용 정의(페이지별로 재정의해서 사용) */
function checkPage() {
	return true;
}


/* 필수 입력항목 속성 추가 */
function addRequired(obj) {
	if(obj.length) {
		for( var i=0 ; i<obj.length ; i++ ) {
			obj[i].required = {};
		}
	} else {
		obj.required = {};
	}
}

/* 필수입력항목 속성 제거 */
function removeRequired(obj) {
	if(obj.length) {
		for( var i=0 ; i<obj.length ; i++ ) {
			obj[i].required = null;
		}
	} else {
		obj.required = null;
	}
}



/* 회면로드시 실행 */
function window.onload() {

	try {
		//userType="money" 인 경우 콤마 자동으로 삽입
		document.body.insertComma = true;
		document.body.insertDash = true;
		//모든 폼 초기화 
		formInitialize();
		//각 화면에서 재정의해서 사용( onload 함수를 쓰지않고 init() 을 써야함 )
		init();
		//스크롤이 생기는 경우를 위해 맨 위로 이동
		document.location.href="#startP"; 
	} catch(e) {
		alert("화면 초기화 작업중 오류가 발생했습니다. \n\n내용 : "+e.description);
	}

}

function init() {
//각 페이지에서 재정의해서 사용
}



//우편번호 찾기(폼이름,우편번호1필드,우편번호2필드,주소필드)
function zipFind(formName,zip1,zip2,address) {
	var theURL   = '../mn06/IR069910.etax?cmd=postNoListSelect';
	var formname = '&formField='+ formName;
	var zip1     = '&zip1Field='+ zip1;
	var zip2     = '&zip2Field='+ zip2;
	var address  = '&addressField='+ address;
	var winName  = '우편번호';
	var features =   "scrollbars=yes, width=450, height=300, resizable=no, menubar=no, top=150, left=260"
	window.open(theURL+formname+zip1+zip2+address,winName,features);
} 	


/****************************************************************************************
 * 내부에서 사용하는 함수
 ****************************************************************************************/

function selectObj() {
	this.next_yn='Y';
	try {
		this.select();
	} catch(e) {}
}


/*-------------------------------------------------------------------------
    Notes     : 콤마가 들어있는 금액을 순수 숫자로 변환
    Parameter : 콤마포함금액
    Return    : 콤마를뺀금액
    Use       :
-------------------------------------------------------------------------*/
function out_comma(str) {
	comm_str = String(str);
	uncomm_str = "";

	for(j=0; j<comm_str.length; j++){
		substr=comm_str.substring(j,j+1);
		if(substr!=",")
			uncomm_str += substr;
	}
	return uncomm_str;
}


function out_dash(str) {
	dash_str = String(str);
	undash_str = "";

	for(j=0; j<dash_str.length; j++){
		substr=dash_str.substring(j,j+1);
		if(substr!="-")
			undash_str += substr;
	}
	return undash_str;
}


/*-------------------------------------------------------------------------
    Notes     : 일자 객체에 일자 데이터를 일자 포맺으로 변경하는 함수
    Parameter : 일자정보
    Return    : 일자
    use       : this.onkeyup = keyupDateObj
-------------------------------------------------------------------------*/
function keyupDateObj(){
    if((event.keyCode >= 48 && event.keyCode <= 57) ||
       (event.keyCode >= 96 && event.keyCode <= 105)){
        if(countChar(this.value, '-') < 2){
            this.value = formDate(this.value);
        }
    }
}


/*-------------------------------------------------------------------------
    Notes     : 해당문자의 숫자를 계산하는 함수
    Parameter : 문자열
    Return    : 일자
    use       : onKeyup="countChar(str, '-');"
-------------------------------------------------------------------------*/
function countChar(str, chk_char){
    var cnt = 0;
    if(str != ""){
        for(var i=0; i<str.length; i++){
            if(str.charAt(i) == chk_char)
                cnt ++;
        }
    }
    return cnt;
}


/*-------------------------------------------------------------------------
    Notes     : 일자 데이터를 일자 형식으로 변환하는 함수
    Parameter : 일자정보
    Return    : 일자
    use       : bj.value = formDate(obj.value);
-------------------------------------------------------------------------*/
function formDate(strDate){
    var ret_val = "";
    if(src != ""){
        var src = replaceStr(strDate, "-", "");
        var len = src.length;

        if(len > 4 && len <= 6){
            ret_val = src.substring(0,4) + "-" + src.substring(4);
        } else if(len > 6 && len < 9){
            ret_val = src.substring(0,4) + "-" + src.substring(4,6) + "-" +src.substring(6);
        } else {
            ret_val = src;
        }
    } else ret_val = src;

    return ret_val;
}


/*--------------------------------------------------------------------------
    Spec      : 문자열 교환
    Parameter : 해당 값, 변경되는 값, 변경할 값
    Return    : String
--------------------------------------------------------------------------*/
function replaceStr(source, target, replace)
{
    var sourceData="";
    sourceData = source;

    if(sourceData == null) return "";
    if(target==null || target=="") return source;

    iTargetLen = target.length;

    var sbfReplace="";
    var i = 0;
    var j = 0;

    while (j > -1)
    {
        j = sourceData.indexOf(target,i);
        if (j > -1)
        {
            sbfReplace+=sourceData.substring(i,j);
            sbfReplace+=replace;
            i = j + iTargetLen;
        }
    }
    sbfReplace+=sourceData.substring(i,sourceData.length);

    return sbfReplace;
}


function keyNumeric()
{
    if((event.keyCode >= 48 && event.keyCode <= 57)  ||
       (event.keyCode == 13))
    {
        event.returnValue = true;
    }
    else{
        event.returnValue = false;
    }
}



/*-------------------------------------------------------------------------
    Notes     : 일자 데이터를 구분자의(yyyy-mm-dd or yyyy-mm) 형식으로 전환하는 함수
    Parameter : 일자(yyyymmdd or yyyymm), 구분자(ex: "-")
    Return    : 형식으로 전환된 일자("yyyy-mm-dd")
    Use       : getDateFormat('20040101', "-");
-------------------------------------------------------------------------*/
function getDateFormat(date, delimiter)
{
    var date_format = "";

    var sDate = new String(replaceStr(date, '-', ''));
    var len = sDate.length;

    if((typeof(delimiter) == "undefined")) delimiter = "-";

    switch (len){
        case 6:
            date_format = sDate.substring(0,4)+delimiter+sDate.substring(4);
            break;
        case 8:
            date_format = sDate.substring(0,4)+delimiter+sDate.substring(4,6)+delimiter+sDate.substring(6);
            break;
        default:
            date_format = formDate(sDate);
            break;
    }
    return date_format;
}

/*-------------------------------------------------------------------------
    Notes     : 일자 Object 데이터를 구분자의(yyyy-mm-dd or yyyy-mm) 형식으로 전환하는 함수
    Parameter : 일자(yyyymmdd or yyyymm), 구분자(ex: "-")
    Return    : 형식으로 전환된 일자("yyyy-mm-dd")
    Use       : getDateFormatObj(this.value, "-");
-------------------------------------------------------------------------*/
function getDateFormatObj(obj, delimiter)
{
    if(obj.value != "" && obj.value.length != 10){
        if((typeof(delimiter) == "undefined")) delimiter = "-";
        obj.value = getDateFormat(obj.value, delimiter);
    }
}


/*-------------------------------------------------------------------------
    Notes     : 일자의 정확성을 검사하는 함수
    Parameter : 일자(date_obj), title
    Return    : true/false
    Use       : checkDateObj(this, '입금일');
-------------------------------------------------------------------------*/
function checkDateObj(date_obj, title){
    if(date_obj.type=="text" && date_obj.next_yn == "Y"){
        var yyyymmdd = replaceStr(date_obj.value, '-', '');
        var args = checkDateObj.arguments;
        var len = args.length;

        if(!isCorrectDate(yyyymmdd)){
            date_obj.next_yn = "N"; // 다음 Ojbect로 이동불가
            if(len == 2)
                alert(title + "에 입력하신 일자[ " + date_obj.value + " ]는 유효하지 않은 일자입니다.");
            else alert("입력하신 일자[ " + date_obj.value + " ]는 유효하지 않은 일자입니다.");
            date_obj.focus();
            return false;
        } else date_obj.next_yn = "Y"; // 다음 Ojbect로 이동가능
    }

    return true;
}



/*-------------------------------------------------------------------------
    Notes         : 일자의 정확성을 검사하는 함수
    Parameter     :
       * yyyymmdd : 일자(yyyymmdd)
    Return        : true/false
    Use           : isCorrectDate('20040101');
-------------------------------------------------------------------------*/
function isCorrectDate(yyyymmdd){
    var retValue = true;
    if(yyyymmdd != "") {
        var sDate = new String(yyyymmdd);
        var iYear = 0, iMonth = 0, iDay = 0;
        // length Check
        if(sDate.length == 8){
            iYear = parseInt(sDate.substring(0,4));
            if(isNaN(iYear)) retValue = false; // number check
            if(iYear < 1) retValue = false;
            if(retValue){
                iMonth = parseInt(sDate.substring(4,5))*10 + parseInt(sDate.substring(5,6));
                if(isNaN(iMonth)) retValue = false; // number check
                if(iMonth < 1 || iMonth > 12) retValue = false;
            }
            if(retValue){
                iDay = parseInt(sDate.substring(6,7))*10 + parseInt(sDate.substring(7,8));
                if(isNaN(iDay)) retValue = false; // number check
                if(iDay < 1 || iDay > 31) retValue = false;
            }
            if(retValue){
                iMonth--;
                var date = new Date(iYear, iMonth, iDay);
                // 정확한 일자 여부 확인
                var com_year  = date.getYear();
                var com_month = date.getMonth();
                var com_day   = date.getDate();

                if(iYear >= 1900 && iYear <= 1999){
                    com_year = 1900+com_year;
                }

                if(iYear == com_year){
                    if(iMonth == com_month){
                        if(iDay == com_day)
                            retValue = true;
                        else retValue = false;
                    }
                    else retValue = false;
                }
                else retValue = false;
            }
        } else  retValue = false;
        sDate = null;
	}

    return retValue;
}

/*-------------------------------------------------------------------------
    Spec      : Keypress시 input에 입력 key 값이 숫자만입력 할수 있도록 하는 함수
    Parameter : 해당사항 없음
    Return    : Boolean
    Ex        : onKeypress='keyAlphabat();'
    Ref       : 0-48(96), 9-57(105) '-':45
-------------------------------------------------------------------------*/
function keyNumericDash()
{
    if((event.keyCode >= 48 && event.keyCode <= 57)  ||
       (event.keyCode == 13) ||
       (event.keyCode == 45))
    {
        event.returnValue = true;
    }
    else{
        event.returnValue = false;
    }
}

function keyEngNumber() {
    if((event.keyCode >= 48 && event.keyCode <= 57)  ||
	   (event.keyCode >= 97 && event.keyCode <= 122) ||
	   (event.keyCode >= 65 && event.keyCode <= 90) ||	
       (event.keyCode == 13))
    {
        event.returnValue = true;
    }
    else{
        event.returnValue = false;
    }

}


/*-------------------------------------------------------------------------
    Notes     : 금액 데이터를 금액 포맺으로 변경하는 함수
    Parameter : 금액
    Return    : 시간
    use       : onKeyup="keyupCurrencyObj(this);"
-------------------------------------------------------------------------*/
function keyupCurrencyObj(obj){
    if((event.keyCode >= 48 && event.keyCode <= 57)  ||
       (event.keyCode >= 96 && event.keyCode <= 105) ||
       (event.keyCode == 8) || // backspance key
       (event.keyCode == 46))  // delete key
    {
        obj.value = formatCurrency(obj.value);
    }
}


/*-------------------------------------------------------------------------
    Notes     : 해당데이터가 금액형식으로 변환하는 함수.
    Parameter : 금액
    Return    : boolean
    Use       : isCheckCurrency(val);
-------------------------------------------------------------------------*/
function formatCurrency(num) {
// Function courtesy of:  Cyanide_7
    var numData="";
    var startRealNum=0;
    var sign="";//양수[],음수[-]
    numData=num;

    numData = numData.toString().replace(/\$|\,/g,'');

    if(isNaN(numData)) {
        numData = num;
        return numData;
    }
    if(numData.substring(0,1)=="-"){
        sign="-";
        numData=numData.substring(1);
    }

    //소숫점 및 "000.." 제거
    for(var i=0;i<numData.length;i++){
        if(numData.charAt(i)!='0'){
            break;
        }
        startRealNum++;
    }

    if(numData.length!=1&&startRealNum>0){
        if(numData.charAt(startRealNum)=='.'){
            numData = numData.substring(startRealNum-1);
        }else{
            numData = numData.substring(startRealNum);
        }
    }

    //소숫점 제거
    if(numData.charAt(0)=="."){
        numData="0."+numData.substring(1);
    }

    tmpNum=numData.split('.');
    if(tmpNum.length==1){
        numData=tmpNum[0];
        cents="";
    }else if(tmpNum.length==2){
        numData =tmpNum[0];
        cents   =tmpNum[1];
    }else{
        return "";
    }

    for (var i = 0; i < Math.floor((numData.length-(1+i))/3); i++) {
        numData = numData.substring(0,numData.length-(4*i+3))+','+numData.substring(numData.length-(4*i+3));
	}

    if(cents==""){
        return sign+numData;
    }else{
        return sign+(numData + "." + cents);
    }
}


function getCurrencyFormatObj(obj)
{
    if(obj.value != ""){
        obj.value = formatCurrency(obj.value);
    }
}

/*-------------------------------------------------------------------------
    Notes     : 금액의 정확성을 검사하는 함수
    Parameter : 금액, title
    Return    : true/false
    Use       : checkDateObj(this, '입금일');
-------------------------------------------------------------------------*/
function checkCurrencyObj(obj, title){
    if(obj.type=="text" && obj.next_yn == "Y"){
        var amt = replaceStr(obj.value, ',', '');
        var args = checkCurrencyObj.arguments;
        var len = args.length;

        if(!isCorrectCurrency(amt)){
            obj.next_yn = "N"; // 다음 Ojbect로 이동불가
            if(len == 2)
                alert(title + "에 입력하신 금액[ " + obj.value + " ]은 유효하지 않은 금액입니다.");
            else alert("입력하신 금액[ " + obj.value + " ]은 유효하지 않은 금액입니다.");
            obj.focus();
            return false;
        } else obj.next_yn = "Y"; // 다음 Ojbect로 이동가능
    }

    return true;
}

/*-------------------------------------------------------------------------
    Notes     : 해당데이터가 금액형식이 맞는지를 판단하는 함수.
    Parameter : 금액
    Return    : boolean
    Use       : isCheckCurrency(val);
-------------------------------------------------------------------------*/
function isCorrectCurrency(val){
    var str = replaceStr(val, ',',  '');
    if(str.length > 0){
        var i = 0;
        var str_len = str.length;
        var ch = str.charAt(i);
        if(str == "-"){
            return false;
        }
        if(ch == '-') i++;
        while(i < str_len){
            ch = str.charAt(i);
            if (ch < '0' || ch > '9'){
                return false;
            }
            i++;
        }
    }
    return true;
}


/*-------------------------------------------------------------------------
    Spec      : input에 입력 값의 영문만입력 할수 있도록 하는 함수
    Parameter : 해당 값
    Return    : Boolean
    Ex        : onKeypress='KeyCheckAlphabat();'
    Ref       : a-97, z-122, A-65, Z-90, 0-48(96), 9-57(105), tab-9, shift-16,
-------------------------------------------------------------------------*/
function keyAlphabat()
{
    if((event.keyCode >= 97 && event.keyCode <= 122) ||
       (event.keyCode >= 65 && event.keyCode <= 90) ||
       (event.keyCode==13))
    {
        event.returnValue = true;
    }
    else{
        event.returnValue = false;
    }
}


/*-------------------------------------------------------------------------
    Spec      : input에 입력 값의 영문인지를 검사하는 함수
    Parameter : 해당 값
    Return    : Boolean
    Ex        : isAlphabatObj(this);
-------------------------------------------------------------------------*/
function isAlphabatObj(obj, title){
    //obj.value = obj.value.toUpperCase();
    if(obj.next_yn == "Y"){
        var args = isAlphabatObj.arguments;
        var len = args.length;

        if(!isAlphabat(obj.value)){
            obj.next_yn = "N"; // 다음 Ojbect로 이동불가

            if(len == 2) alert(title + "은(는) 영문만 입력이 가능합니다.");
            else alert("영문만 입력이 가능합니다.");

            obj.focus();
            return false;

        } else obj.next_yn = "Y"; // 다음 Ojbect로 이동가능
    }

    return true;
}
/*-------------------------------------------------------------------------
    Spec      : input에 입력 값의 영문인지를 검사하는 함수
    Parameter : 해당 값
    Return    : Boolean
    Ex        : isAlphabatObj(this);
-------------------------------------------------------------------------*/
function isEngNumberObj(obj, title){
    //obj.value = obj.value.toUpperCase();
    if(obj.next_yn == "Y"){
        var args = isEngNumberObj.arguments;
        var len = args.length;

        if(!isEngNumber(obj.value)){
            obj.next_yn = "N"; // 다음 Ojbect로 이동불가

            if(len == 2) alert(title + "은(는) 영문 숫자만 입력이 가능합니다.");
            else alert("영문 숫자만 입력이 가능합니다.");

            obj.focus();
            return false;

        } else obj.next_yn = "Y"; // 다음 Ojbect로 이동가능
    }

    return true;
}


/*-------------------------------------------------------------------------
    Spec      : 해당값이 영문인지를 검사하는 함수
    Parameter : 해당 값
    Return    : Boolean
    Ex        : isAlphabat(this.value);
-------------------------------------------------------------------------*/
function isAlphabat(str){
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    if(str != "")
        return restrictChars(str, chars);
    else return true;
}

function isEngNumber(str) {
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    if(str != "")
        return restrictChars(str, chars);
    else return true;
}


//****************************************************************************
// 입력데이터 유효성 체크관련 함수
//****************************************************************************

/*-------------------------------------------------------------------------
    Spec      : 입력값에 특정 문자(chars)가 있는지 체크
    Parameter : 해당 값
    Return    : Boolean
    Ex        : if (!restrictChars(form.name.value,"!,*&^%$#@~;")) {
                       alert("이름 필드에는 특수 문자를 사용할 수 없습니다.");
                    }
-------------------------------------------------------------------------*/
function restrictChars(str, chars) {
    for (var i = 0; i < str.length; i++) {
       if (chars.indexOf(str.charAt(i)) == -1)
           return false;
    }
    return true;
}


/*-------------------------------------------------------------------------
    Spec      : Keypress시 input에 입력 key 값이 숫자만입력 할수 있도록 하는 함수
    Parameter : 해당사항 없음
    Return    : Boolean
    Ex        : onKeypress='keyNumericDouble();'
    Ref       : 0-48(96), 9-57(105), '-':45, '.':46
-------------------------------------------------------------------------*/
function keyDecimal()
{
    if((event.keyCode >= 48 && event.keyCode <= 57)  ||
       (event.keyCode == 13) ||
       (event.keyCode == 45) ||
       (event.keyCode == 46))
    {
        event.returnValue = true;
    }
    else{
        event.returnValue = false;
    }
}


/*-------------------------------------------------------------------------
    Notes     : 소수점 들어가는 금액 자릿수 체크하는 함수.
    Parameter : val-체크할 값, constant_cnt-정수부분 자리수, decimal_cnt-소수부분 자리수, title-타이틀
    Return    : Boolean
    Use       : isDecimal('100.11', 5, 2, '미결금액');
-------------------------------------------------------------------------*/
function checkDecimalObj(obj, constant_cnt, decimal_cnt, title){

    if(obj.type=="text" && obj.next_yn == "Y"){
        var chk_val = replaceStr(obj.value, ',', '');
        var pre_cnt = 0; // 정수부분 자리수
        var pos_cnt = 0; // 소수부분 자리수
        var dot_pos = 0; // 소수점 위치
        var total_len = 0;
        var chk_constant_cnt = 0;

        if((typeof(title) == "undefined")) title = "금액";

        if(isNaN(chk_val)){
            obj.next_yn = "N"; // 다음 Ojbect로 이동불가
            alert("\"" + obj.value + "\""+"은(는) 올바른 " + title + "형식이 아닙니다.");
            obj.select();
            obj.focus();
            return false;
        } else {
            dot_pos = chk_val.indexOf(".");
            // '-'부호 입력시
            if(chk_val.substring(0,1) == '-')
                chk_constant_cnt = constant_cnt + 1;
            else chk_constant_cnt = constant_cnt;

            if(dot_pos == -1){
                pre_cnt = chk_val.length;
                if(pre_cnt > chk_constant_cnt){
                    obj.next_yn = "N"; // 다음 Ojbect로 이동불가
                    alert(title + "의 정수부분은 " + constant_cnt + "자리이하로 입력하십시오.");
            		obj.select();
                    obj.focus();
                    return false;
                }
            } else if(dot_pos > 0) {
                pre_cnt = dot_pos;
                total_len = chk_val.length;

                if(total_len == ++dot_pos){
                    obj.next_yn = "N"; // 다음 Ojbect로 이동불가
                    alert("\"" + chk_val + "\""+"은(는) 올바른 " + title + "형식 아닙니다.");
            		obj.select();
                    obj.focus();
                    return false;
                }
                else{
                    pos_cnt = chk_val.substring(pre_cnt+1).length;
                }

                if(pre_cnt > chk_constant_cnt){
                    obj.next_yn = "N"; // 다음 Ojbect로 이동불가
                    alert(title + "의 정수부분은 " + constant_cnt + "자리이하로 입력하십시오.");
            		obj.select();
                    obj.focus();
                    return false;
                }
                if(pos_cnt > decimal_cnt){
                    obj.next_yn = "N"; // 다음 Ojbect로 이동불가
                    alert(title + "의 소수부분은 " + decimal_cnt + "자리이하로 입력하십시오.");
            		obj.select();
                    obj.focus();
                    return false;
                }
            }
            obj.next_yn = "Y"; // 다음 Ojbect로 이동가능
            return true;
        }
    }
}


/*-------------------------------------------------------------------------
    Spec      : 해당값이 양의숫자인지 검사하는 함수
    Parameter : 해당 값
    Return    : Boolean
    Ex        : isNumeric(this.value);
-------------------------------------------------------------------------*/
function isNumeric(value)
{
  var result = true;
  
  for(var j = 0; result && (j < value.length); j++) { 
    if((value.substring(j, j+1) < "0") || (value.substring(j, j+1) > "9")) { 
      result = false;
    }
  }
  return result;
}


function checkAuth(auth) {
  
	if (!(document.all.select_yn) || !(document.all.insert_yn) || 
		!(document.all.update_yn) || !(document.all.delete_yn) || 
		!(document.all.print_yn)) {
		alert("Hidden 필드 추가후 권한 체크하세요.");
		return false;
	} 

	if (auth == "SELECT") {
		if (document.all.select_yn.value !="Y") {
			alert("조회 권한이 없습니다.");
			return false;
		}
	} else if (auth == "INSERT") {
		if (document.all.insert_yn.value !="Y") {
			alert("등록 권한이 없습니다.");
			return false;
		}
	} else if (auth == "UPDATE") {
		if (document.all.update_yn.value !="Y") {
			alert("수정 권한이 없습니다.");
			return false;
		}
	} else if (auth == "DELETE") {
		if (document.all.delete_yn.value !="Y") {
			alert("삭제 권한이 없습니다.");
			return false;
		}
	} else if (auth == "PRINT") {
		if (document.all.print_yn.value !="Y") {
			alert("출력 권한이 없습니다.");
			return false;
		}
	} else {
		alert("파라메터 [SELECT,INSERT,UPDATE,DELETE,PRINT] 오류입니다.");
		return false;
	}

}



function isEnterKey() {
	if(event.keyCode==13) return true;
	return false;
}


function layer_menu(target) {
if (document.all) {
  var find = 30;
  for (i = 1; i <= 30; i++) {
	if ( eval("document.all." + "m" + i + "Sub") ) {
	  thisMenu = eval("document.all." + "m" + i + "Sub" + ".style");
	  if (i == target) {
		if (thisMenu.display == "block")
		  thisMenu.display = "none";
		else
		  thisMenu.display = "block";
		if (i > find)
		break;

	  } else if (thisMenu.display == "block") {
		find = i;
		thisMenu.display = "none";
		if (find > target)
		  break;
	  }
	}
  }
  return false
} else {
  return true
}
}
      
//파업띄우기
function funcOpenWindow( form, method, target, action,   left,  top,  width,  height,  toolbar,  menubar,  statusbar,  scrollbar,  resizable)
{
    toolbar_str = toolbar ? 'yes' : 'no';
    menubar_str = menubar ? 'yes' : 'no';
    statusbar_str = statusbar ? 'yes' : 'no';
    scrollbar_str = scrollbar ? 'yes' : 'no';
    resizable_str = resizable ? 'yes' : 'no';
    
   var open =  window.open("/ubank/js/blank.etax",target, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str);
    open.focus();
    form.method = 'POST';
    form.target = target ;
    form.action = action ;
    form.submit() ;
	
}