
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
	} else if(this.userType == "money") { /* �ݾ� �Է� : ex) 5,105,000 */
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

	} else if(this.userType == "sp_money") { /* �ݾ� �Է� : ex) 5,105,000 */
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
	} else if(this.userType == "eng") { /* ���� �Է� : ex) abcd */
		this.onkeypress = keyAlphabat;
		
		this.onfocus = selectObj;
		this.onblur = new Function("return isAlphabatObj(this, this.desc);");
		this.style.imeMode = "disabled";
		this.style.textAlign = "center";
		if( this.maxLength > 10000 ) this.maxLength="15";

	} else if(this.userType == "engNumber") { /* ���� ���� �Է� : ex) abcd123 */
		this.onkeypress = keyEngNumber;
		
		this.onfocus = selectObj;
		this.onblur = new Function("return isEngNumberObj(this, this.desc);");
		this.style.imeMode = "disabled";
		this.style.textAlign = "left";
		if( this.maxLength > 10000 ) this.maxLength="15";

	} else if(this.userType == "number") { /* ���� �Է� : ex) 1500 */
		this.onkeypress = keyNumeric;
		this.onfocus = selectObj;
		
		this.style.imeMode = "disabled";
		this.style.textAlign = "right";
		if( this.maxLength > 10000 ) this.maxLength="15";
		 
	} else if(this.userType == "decimal") { /* �Ҽ��� �Է� : ex) 155.87 �Ҽ��� 2�ڸ������� �Է°��� */
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

	}else if(this.userType == "decimal1") { /* �Ҽ��� �Է� : ex) 1555555.8 �Ҽ��� 1�ڸ������� �Է°��� */
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


/* ��� input �ʵ� �ʱ�ȭ */
function typeInitialize() {
	//input �ʱ�ȭ
	var allInput = document.all.tags("INPUT");
	for( var i=0 ; i<allInput.length ; i++ ) {
		var input = allInput[i];
		if( input.type == "radio" ) {
			// Ÿ������ �Լ� �Ҵ�
			input.init = radioInit;
			// Ÿ������ �Լ� ����
			input.init();
		} else if( input.type == "checkbox" ) {
			// Ÿ������ �Լ� �Ҵ�
			input.init = checkboxInit;
			// Ÿ������ �Լ� ����
			input.init();
		} else {
			// Ÿ������ �Լ� �Ҵ�
			input.typeDefine = typeDefine;
			// Ÿ������ �Լ� ����
			input.typeDefine();
		}
	}
	//select �ʱ�ȭ
	var allSelect = document.all.tags("SELECT");
	for( var i=0 ; i<allSelect.length ; i++ ) {
		var select = allSelect[i];
		// �ʱ�ȭ �Լ� �Ҵ�
		select.init = selectInit;
		// �ʱ�ȭ �Լ� ����
		select.init();
	}

}

/* select ��ü ���� �ʱ�ȭ �۾� */
function selectInit() {
	//iValue ���� ������ �������ش�.
	if( this.iValue == null || this.iValue == "" ) return;
	this.value = this.iValue;
}

/* radio ��ü ���� �ʱ�ȭ �۾� */
function radioInit() {
	//iValue ���� ������ �������ش�.
	if( this.iValue == null || this.iValue == "" ) return;
	if( this.iValue == this.value ) {
		this.checked = true;
	}
}

/* checkbox ��ü ���� �ʱ�ȭ �۾� */
function checkboxInit() {
	//iValue ���� ������ �������ش�.
	if( this.iValue == null || this.iValue == "" ) return;
	if( this.iValue == this.value ) {
		this.checked = true;
	}
}


/* ��� ���� �ʱ�ȭ�Ѵ�.(�ڵ�üũ �ɼ� ����, ��üũ �Լ� �Ҵ�, comma üũ �Լ� �Ҵ�,  ���� onsubmit ��鷯 ���� ���) */
function formInitialize() {
	var forms = document.forms;
	for( var i=0 ; i<forms.length ; i++ ) {
		var form = forms[i];
		//�ڵ�üũ �ɼ�
		form.autoCheck = false;
		//�� üũ �Լ� �Ҵ�
		form.checkForm = checkForm;
		//�޸� �ڵ����� �ɼ�
		form.autoCheckComma = false;
		//"-" �ڵ����� �ɼ�
		form.autoCheckDash = false;
		//comma üũ �Լ� �Ҵ�
		form.checkComma = checkComma;
		//"-" üũ �Լ� �Ҵ�
		form.checkDash = checkDash;
		//ȭ�鿡�� onsubmit �� ��鷯�� ������� ����
		form.orgSubmitHandler = form.onsubmit;
		//onsubmit ���� ��üũ �Լ� �Ҵ�
		form.onsubmit = form.checkForm;	
		//submit �Լ� �Ҵ�
		form.formSubmit = formSubmit;
	}
}
/*submit �ϱ����� userType="money" �ΰ�츦 üũ�ؼ� comma �� �����Ѵ�. */
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

/*submit �ϱ����� userType="date" �ΰ�츦 üũ�ؼ� "-" �� �����Ѵ�. */
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
		alert(this.desc+ " ��(��) "+ this.fixLength + "�ڸ� �Է��׸� �Դϴ�.");
		this.select();
		return false;
	}
	return true;
}

/* �ڵ��� ��� */
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


/* ���۹�ư�� ������� �ʰ� ���� ��ũ��Ʈ�� submit �ϴ°�� */
function formSubmit() {
	
	this.autoCheck = true;
	this.autoCheckComma = true;
	this.autoCheckDash = true;
	if(this.checkForm()) this.submit();
}

/* onload �Ǳ����� form.formSubmit() ȣ��� ������ �߻��� ��� */
function eSubmit(form) {
	try {
		form.formSubmit();
	} catch(e) {
		formInitialize();
		form.formSubmit();
	}
}

/**
 * form üũ  
 * ���۹�ư�� ����쳪, ��������� formSubmit() �Լ��� ȣ���Ѱ�� ��� ó��
 * 1. �ʼ��Է� �׸� üũ
 * 2. ������ ����, ���۹�ư�� ����Ѱ�� ���������� onsubmit �� �Ҵ��� �Լ��� �ִٸ� �Լ��� �����Ų��.
 * 3. formSubmit() �� ����Ѱ�츦 ����  true/false return
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
		//ȭ�鿡�� ��������� onsubmit �� ����ϴ� ��� ����
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
				alert(this.desc+" ��[��] �ʼ��Է� �Դϴ�.");
			} else {
				alert("��[��] �ʼ��Է� �Դϴ�.");
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


/* �� üũ ���Ŀ� ���������� ���������� üũ�ؾ��� ���� ����(���������� �������ؼ� ���) */
function checkPage() {
	return true;
}


/* �ʼ� �Է��׸� �Ӽ� �߰� */
function addRequired(obj) {
	if(obj.length) {
		for( var i=0 ; i<obj.length ; i++ ) {
			obj[i].required = {};
		}
	} else {
		obj.required = {};
	}
}

/* �ʼ��Է��׸� �Ӽ� ���� */
function removeRequired(obj) {
	if(obj.length) {
		for( var i=0 ; i<obj.length ; i++ ) {
			obj[i].required = null;
		}
	} else {
		obj.required = null;
	}
}



/* ȸ��ε�� ���� */
function window.onload() {

	try {
		//userType="money" �� ��� �޸� �ڵ����� ����
		document.body.insertComma = true;
		document.body.insertDash = true;
		//��� �� �ʱ�ȭ 
		formInitialize();
		//�� ȭ�鿡�� �������ؼ� ���( onload �Լ��� �����ʰ� init() �� ����� )
		init();
		//��ũ���� ����� ��츦 ���� �� ���� �̵�
		document.location.href="#startP"; 
	} catch(e) {
		alert("ȭ�� �ʱ�ȭ �۾��� ������ �߻��߽��ϴ�. \n\n���� : "+e.description);
	}

}

function init() {
//�� ���������� �������ؼ� ���
}



//�����ȣ ã��(���̸�,�����ȣ1�ʵ�,�����ȣ2�ʵ�,�ּ��ʵ�)
function zipFind(formName,zip1,zip2,address) {
	var theURL   = '../mn06/IR069910.etax?cmd=postNoListSelect';
	var formname = '&formField='+ formName;
	var zip1     = '&zip1Field='+ zip1;
	var zip2     = '&zip2Field='+ zip2;
	var address  = '&addressField='+ address;
	var winName  = '�����ȣ';
	var features =   "scrollbars=yes, width=450, height=300, resizable=no, menubar=no, top=150, left=260"
	window.open(theURL+formname+zip1+zip2+address,winName,features);
} 	


/****************************************************************************************
 * ���ο��� ����ϴ� �Լ�
 ****************************************************************************************/

function selectObj() {
	this.next_yn='Y';
	try {
		this.select();
	} catch(e) {}
}


/*-------------------------------------------------------------------------
    Notes     : �޸��� ����ִ� �ݾ��� ���� ���ڷ� ��ȯ
    Parameter : �޸����Աݾ�
    Return    : �޸������ݾ�
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
    Notes     : ���� ��ü�� ���� �����͸� ���� �������� �����ϴ� �Լ�
    Parameter : ��������
    Return    : ����
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
    Notes     : �ش繮���� ���ڸ� ����ϴ� �Լ�
    Parameter : ���ڿ�
    Return    : ����
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
    Notes     : ���� �����͸� ���� �������� ��ȯ�ϴ� �Լ�
    Parameter : ��������
    Return    : ����
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
    Spec      : ���ڿ� ��ȯ
    Parameter : �ش� ��, ����Ǵ� ��, ������ ��
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
    Notes     : ���� �����͸� ��������(yyyy-mm-dd or yyyy-mm) �������� ��ȯ�ϴ� �Լ�
    Parameter : ����(yyyymmdd or yyyymm), ������(ex: "-")
    Return    : �������� ��ȯ�� ����("yyyy-mm-dd")
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
    Notes     : ���� Object �����͸� ��������(yyyy-mm-dd or yyyy-mm) �������� ��ȯ�ϴ� �Լ�
    Parameter : ����(yyyymmdd or yyyymm), ������(ex: "-")
    Return    : �������� ��ȯ�� ����("yyyy-mm-dd")
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
    Notes     : ������ ��Ȯ���� �˻��ϴ� �Լ�
    Parameter : ����(date_obj), title
    Return    : true/false
    Use       : checkDateObj(this, '�Ա���');
-------------------------------------------------------------------------*/
function checkDateObj(date_obj, title){
    if(date_obj.type=="text" && date_obj.next_yn == "Y"){
        var yyyymmdd = replaceStr(date_obj.value, '-', '');
        var args = checkDateObj.arguments;
        var len = args.length;

        if(!isCorrectDate(yyyymmdd)){
            date_obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�
            if(len == 2)
                alert(title + "�� �Է��Ͻ� ����[ " + date_obj.value + " ]�� ��ȿ���� ���� �����Դϴ�.");
            else alert("�Է��Ͻ� ����[ " + date_obj.value + " ]�� ��ȿ���� ���� �����Դϴ�.");
            date_obj.focus();
            return false;
        } else date_obj.next_yn = "Y"; // ���� Ojbect�� �̵�����
    }

    return true;
}



/*-------------------------------------------------------------------------
    Notes         : ������ ��Ȯ���� �˻��ϴ� �Լ�
    Parameter     :
       * yyyymmdd : ����(yyyymmdd)
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
                // ��Ȯ�� ���� ���� Ȯ��
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
    Spec      : Keypress�� input�� �Է� key ���� ���ڸ��Է� �Ҽ� �ֵ��� �ϴ� �Լ�
    Parameter : �ش���� ����
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
    Notes     : �ݾ� �����͸� �ݾ� �������� �����ϴ� �Լ�
    Parameter : �ݾ�
    Return    : �ð�
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
    Notes     : �ش絥���Ͱ� �ݾ��������� ��ȯ�ϴ� �Լ�.
    Parameter : �ݾ�
    Return    : boolean
    Use       : isCheckCurrency(val);
-------------------------------------------------------------------------*/
function formatCurrency(num) {
// Function courtesy of:  Cyanide_7
    var numData="";
    var startRealNum=0;
    var sign="";//���[],����[-]
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

    //�Ҽ��� �� "000.." ����
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

    //�Ҽ��� ����
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
    Notes     : �ݾ��� ��Ȯ���� �˻��ϴ� �Լ�
    Parameter : �ݾ�, title
    Return    : true/false
    Use       : checkDateObj(this, '�Ա���');
-------------------------------------------------------------------------*/
function checkCurrencyObj(obj, title){
    if(obj.type=="text" && obj.next_yn == "Y"){
        var amt = replaceStr(obj.value, ',', '');
        var args = checkCurrencyObj.arguments;
        var len = args.length;

        if(!isCorrectCurrency(amt)){
            obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�
            if(len == 2)
                alert(title + "�� �Է��Ͻ� �ݾ�[ " + obj.value + " ]�� ��ȿ���� ���� �ݾ��Դϴ�.");
            else alert("�Է��Ͻ� �ݾ�[ " + obj.value + " ]�� ��ȿ���� ���� �ݾ��Դϴ�.");
            obj.focus();
            return false;
        } else obj.next_yn = "Y"; // ���� Ojbect�� �̵�����
    }

    return true;
}

/*-------------------------------------------------------------------------
    Notes     : �ش絥���Ͱ� �ݾ������� �´����� �Ǵ��ϴ� �Լ�.
    Parameter : �ݾ�
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
    Spec      : input�� �Է� ���� �������Է� �Ҽ� �ֵ��� �ϴ� �Լ�
    Parameter : �ش� ��
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
    Spec      : input�� �Է� ���� ���������� �˻��ϴ� �Լ�
    Parameter : �ش� ��
    Return    : Boolean
    Ex        : isAlphabatObj(this);
-------------------------------------------------------------------------*/
function isAlphabatObj(obj, title){
    //obj.value = obj.value.toUpperCase();
    if(obj.next_yn == "Y"){
        var args = isAlphabatObj.arguments;
        var len = args.length;

        if(!isAlphabat(obj.value)){
            obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�

            if(len == 2) alert(title + "��(��) ������ �Է��� �����մϴ�.");
            else alert("������ �Է��� �����մϴ�.");

            obj.focus();
            return false;

        } else obj.next_yn = "Y"; // ���� Ojbect�� �̵�����
    }

    return true;
}
/*-------------------------------------------------------------------------
    Spec      : input�� �Է� ���� ���������� �˻��ϴ� �Լ�
    Parameter : �ش� ��
    Return    : Boolean
    Ex        : isAlphabatObj(this);
-------------------------------------------------------------------------*/
function isEngNumberObj(obj, title){
    //obj.value = obj.value.toUpperCase();
    if(obj.next_yn == "Y"){
        var args = isEngNumberObj.arguments;
        var len = args.length;

        if(!isEngNumber(obj.value)){
            obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�

            if(len == 2) alert(title + "��(��) ���� ���ڸ� �Է��� �����մϴ�.");
            else alert("���� ���ڸ� �Է��� �����մϴ�.");

            obj.focus();
            return false;

        } else obj.next_yn = "Y"; // ���� Ojbect�� �̵�����
    }

    return true;
}


/*-------------------------------------------------------------------------
    Spec      : �ش簪�� ���������� �˻��ϴ� �Լ�
    Parameter : �ش� ��
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
// �Էµ����� ��ȿ�� üũ���� �Լ�
//****************************************************************************

/*-------------------------------------------------------------------------
    Spec      : �Է°��� Ư�� ����(chars)�� �ִ��� üũ
    Parameter : �ش� ��
    Return    : Boolean
    Ex        : if (!restrictChars(form.name.value,"!,*&^%$#@~;")) {
                       alert("�̸� �ʵ忡�� Ư�� ���ڸ� ����� �� �����ϴ�.");
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
    Spec      : Keypress�� input�� �Է� key ���� ���ڸ��Է� �Ҽ� �ֵ��� �ϴ� �Լ�
    Parameter : �ش���� ����
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
    Notes     : �Ҽ��� ���� �ݾ� �ڸ��� üũ�ϴ� �Լ�.
    Parameter : val-üũ�� ��, constant_cnt-�����κ� �ڸ���, decimal_cnt-�Ҽ��κ� �ڸ���, title-Ÿ��Ʋ
    Return    : Boolean
    Use       : isDecimal('100.11', 5, 2, '�̰�ݾ�');
-------------------------------------------------------------------------*/
function checkDecimalObj(obj, constant_cnt, decimal_cnt, title){

    if(obj.type=="text" && obj.next_yn == "Y"){
        var chk_val = replaceStr(obj.value, ',', '');
        var pre_cnt = 0; // �����κ� �ڸ���
        var pos_cnt = 0; // �Ҽ��κ� �ڸ���
        var dot_pos = 0; // �Ҽ��� ��ġ
        var total_len = 0;
        var chk_constant_cnt = 0;

        if((typeof(title) == "undefined")) title = "�ݾ�";

        if(isNaN(chk_val)){
            obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�
            alert("\"" + obj.value + "\""+"��(��) �ùٸ� " + title + "������ �ƴմϴ�.");
            obj.select();
            obj.focus();
            return false;
        } else {
            dot_pos = chk_val.indexOf(".");
            // '-'��ȣ �Է½�
            if(chk_val.substring(0,1) == '-')
                chk_constant_cnt = constant_cnt + 1;
            else chk_constant_cnt = constant_cnt;

            if(dot_pos == -1){
                pre_cnt = chk_val.length;
                if(pre_cnt > chk_constant_cnt){
                    obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�
                    alert(title + "�� �����κ��� " + constant_cnt + "�ڸ����Ϸ� �Է��Ͻʽÿ�.");
            		obj.select();
                    obj.focus();
                    return false;
                }
            } else if(dot_pos > 0) {
                pre_cnt = dot_pos;
                total_len = chk_val.length;

                if(total_len == ++dot_pos){
                    obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�
                    alert("\"" + chk_val + "\""+"��(��) �ùٸ� " + title + "���� �ƴմϴ�.");
            		obj.select();
                    obj.focus();
                    return false;
                }
                else{
                    pos_cnt = chk_val.substring(pre_cnt+1).length;
                }

                if(pre_cnt > chk_constant_cnt){
                    obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�
                    alert(title + "�� �����κ��� " + constant_cnt + "�ڸ����Ϸ� �Է��Ͻʽÿ�.");
            		obj.select();
                    obj.focus();
                    return false;
                }
                if(pos_cnt > decimal_cnt){
                    obj.next_yn = "N"; // ���� Ojbect�� �̵��Ұ�
                    alert(title + "�� �Ҽ��κ��� " + decimal_cnt + "�ڸ����Ϸ� �Է��Ͻʽÿ�.");
            		obj.select();
                    obj.focus();
                    return false;
                }
            }
            obj.next_yn = "Y"; // ���� Ojbect�� �̵�����
            return true;
        }
    }
}


/*-------------------------------------------------------------------------
    Spec      : �ش簪�� ���Ǽ������� �˻��ϴ� �Լ�
    Parameter : �ش� ��
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
		alert("Hidden �ʵ� �߰��� ���� üũ�ϼ���.");
		return false;
	} 

	if (auth == "SELECT") {
		if (document.all.select_yn.value !="Y") {
			alert("��ȸ ������ �����ϴ�.");
			return false;
		}
	} else if (auth == "INSERT") {
		if (document.all.insert_yn.value !="Y") {
			alert("��� ������ �����ϴ�.");
			return false;
		}
	} else if (auth == "UPDATE") {
		if (document.all.update_yn.value !="Y") {
			alert("���� ������ �����ϴ�.");
			return false;
		}
	} else if (auth == "DELETE") {
		if (document.all.delete_yn.value !="Y") {
			alert("���� ������ �����ϴ�.");
			return false;
		}
	} else if (auth == "PRINT") {
		if (document.all.print_yn.value !="Y") {
			alert("��� ������ �����ϴ�.");
			return false;
		}
	} else {
		alert("�Ķ���� [SELECT,INSERT,UPDATE,DELETE,PRINT] �����Դϴ�.");
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
      
//�ľ�����
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