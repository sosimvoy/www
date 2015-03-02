// 신고납부 공통
function setDiv(form)
{
	if(form.m_div[0].checked)
	{
		form.name_corp.style.backgroundColor = "#CCCCCC";
		form.name_corp.disabled = true;
		form.name_corp.readOnly = true;
		form.rgstn_num_etpr1.readOnly = true;
		form.rgstn_num_etpr2.readOnly = true;
		form.rgstn_num_etpr3.readOnly = true;
		form.rgstn_num_etpr1.style.backgroundColor = "#CCCCCC";
		form.rgstn_num_etpr2.style.backgroundColor = "#CCCCCC";
		form.rgstn_num_etpr3.style.backgroundColor = "#CCCCCC";
		form.rgstn_num_etpr1.disabled = true;
		form.rgstn_num_etpr2.disabled = true;
		form.rgstn_num_etpr3.disabled = true;
		form.sprt_head_offc_brch.style.backgroundColor = "#CCCCCC";
		form.sprt_head_offc_brch.disabled = true;
		form.sprt_fnshd.style.backgroundColor = "#CCCCCC";
		form.sprt_fnshd.disabled = true;
	}
	else if(form.m_div[1].checked)
	{
		form.name_corp.style.backgroundColor = "#FFFFFF";
		form.name_corp.disabled = false;
		form.name_corp.readOnly = false;
		form.rgstn_num_etpr1.readOnly = false;
		form.rgstn_num_etpr2.readOnly = false;
		form.rgstn_num_etpr3.readOnly = false;
		form.rgstn_num_etpr1.style.backgroundColor = "#FFFFFF";
		form.rgstn_num_etpr2.style.backgroundColor = "#FFFFFF";
		form.rgstn_num_etpr3.style.backgroundColor = "#FFFFFF";
		form.rgstn_num_etpr1.disabled = false;
		form.rgstn_num_etpr2.disabled = false;
		form.rgstn_num_etpr3.disabled = false;
		form.sprt_head_offc_brch.style.backgroundColor = "#CCCCCC";
		form.sprt_head_offc_brch.disabled = true;
		form.sprt_fnshd.style.backgroundColor = "#CCCCCC";
		form.sprt_fnshd.disabled = true;
	}
	else if(form.m_div[2].checked)
	{
		form.name_corp.style.backgroundColor = "#FFFFFF";
		form.name_corp.disabled = false;
		form.name_corp.readOnly = false;
		form.rgstn_num_etpr1.readOnly = false;
		form.rgstn_num_etpr2.readOnly = false;
		form.rgstn_num_etpr3.readOnly = false;
		form.rgstn_num_etpr1.style.backgroundColor = "#FFFFFF";
		form.rgstn_num_etpr2.style.backgroundColor = "#FFFFFF";
		form.rgstn_num_etpr3.style.backgroundColor = "#FFFFFF";
		form.rgstn_num_etpr1.disabled = false;
		form.rgstn_num_etpr2.disabled = false;
		form.rgstn_num_etpr3.disabled = false;
		form.sprt_head_offc_brch.style.backgroundColor = "#FFFFFF";
		form.sprt_head_offc_brch.disabled = false;
		form.sprt_fnshd.style.backgroundColor = "#FFFFFF";
		form.sprt_fnshd.disabled = false;
	}
}

function decimal_format(numValue, patternValue, strLength)
{
    numValue = numValue.toString();
    if(numValue.length >= strLength)
        return numValue;
    for(var cnt = 0; cnt < strLength; cnt++)
    {
        if(numValue.length < strLength)
            numValue = patternValue + numValue;
        else
            break;
    }
    return numValue;
}

function trim(str)
{
	var newStr = "";
	for(var cnt = 0; cnt < str.length; cnt++)
	{
		charStr = str.substring(cnt, cnt + 1);
		if(charStr == " ")
			charStr = "";
		newStr = newStr + charStr;
	}
	return newStr;
}


function isDigit(value, allowDecimal)
{
	var natural_number = value;
	if(allowDecimal)
	{
		//소숫점이 있을경우 소숫점 자리만 검증
		var tmpAry = value.split(".");
		natural_number = tmpAry[0];
		//alert(tmpAry.length);
		//alert("#" + value.indexOf(".") + "#" + value.length + "#");

		if(tmpAry.length > 2)
			return false;
		if(tmpAry.length == 1 && value.indexOf(".") != -1)
			return false;
		if(value.indexOf(".") == 0 || value.indexOf(".") == value.length - 1)
			return false;

		if(tmpAry.length == 2)
		{
			for(var cnt = 0; cnt < tmpAry[1].length; cnt++)
			{
				var charValue = tmpAry[1].substring(cnt, cnt + 1);
				if(!(charValue >= "0" && charValue <= "9"))
					return false;
			}
		}
	}
	//자연수 자리 검증
	//음수일 경우 위치 검증
	var isNegative = false;
	if(natural_number.indexOf("-") == 0)
	{
		natural_number = natural_number.substring(1);
		isNegative = true;
	}
	else if(natural_number.indexOf("-") > 0)
		return false;

	for(var cnt = 0; cnt < natural_number.length; cnt++)
	{
		charValue = natural_number.substring(cnt, cnt + 1);
		if(!(charValue >= "0" && charValue <= "9"))
			return false;
	}
	return true;
}

// print
var tempHtmlContent; 

function printDiv() 
{ 
	if (document.all && window.print) 
	{ 
		window.onbeforeprint = beforeDivs; 
		window.onafterprint = afterDivs; 
		window.print(); 
	} 
} 

function beforeDivs ()
{ 
	if (document.all) 
	{ 
		var rng = document.body.createTextRange( ); 
		if (rng!=null) 
		{ 
			//alert(rng.htmlText); 
			tempHtmlContent = rng.htmlText; 
			rng.pasteHTML("<table border=0 align=center><tr><td align=center>"  
                                       + document.all("pDiv").innerHTML + "</td></tr></table>"); 
		} 
	} 
} 

function afterDivs () 
{ 
	if (document.all) 
	{ 
		var rng = document.body.createTextRange(); 
        if (rng!=null) 
		{ 
			rng.pasteHTML(tempHtmlContent); 
        } 
	} 
} 


function checkYear(year, title, isEmpty)
{
	date = new Date;
	if(!isEmpty)
	{
		if((trim(year) == ""))
		{
			alert(title + "을 입력해 주십시요.");
			return false;
		}
		if(year.length != 4)
		{
			alert(title + "(년도)가 잘못되었습니다.");
			return false;
		}
	}
	if(!isDigit(year))
	{
		alert(title + "에 문자가 포함되어 있습니다.");
		return false;
	}
	return true;
}

function checkMonth(year, month, title, isEmpty)
{
	date = new Date;
	if(!isEmpty)
	{
		if((trim(year) == "") || (trim(month) == ""))
		{
			alert(title + "을 입력해 주십시요.");
			return false;
		}
		if(year.length != 4)
		{
			alert(title + "(년도)가 잘못되었습니다.");
			return false;
		}
		if(month < 1 || month > 12)
		{
			alert(title + "(달)가 잘못되었습니다.");
			return false;
		}
	}
	if(trim(year + month) != "" && (year == "" || month == ""))
	{
		alert(title + "날짜에 입력하지 않은 부분이 있습니다.");
		return false;
	}
	if(!isDigit(year) || !isDigit(month))
	{
		alert(title + "에 문자가 포함되어 있습니다.");
		return false;
	}
	return true;
}

function checkDate(year, month, day, title, isEmpty)
{
	date = new Date;
	if(!isEmpty)
	{
		if((trim(year) == "") || (trim(month) == "") || (trim(day) == ""))
		{
			alert(title + "을 입력해 주십시요.");
			return false;
		}
		if(year.length != 4)
		{
			alert(title + "(년도)가 잘못되었습니다.");
			return false;
		}
		if(month < 1 || month > 12)
		{
			alert(title + "(달)가 잘못되었습니다.");
			return false;
		}
		if(day < 1 || day > 31)
		{
			alert(title + "(날)가 잘못되었습니다.");
			return false;
		}
	}
	if(trim(year + month + day) != "" && (year == "" || month == "" || day == ""))
	{
		alert(title + "날짜에 입력하지 않은 부분이 있습니다.");
		return false;
	}
	if(!isDigit(year) || !isDigit(month) || !isDigit(day))
	{
		alert(title + "에 문자가 포함되어 있습니다.");
		return false;
	}
	return true;
}

