var target;
var pop_top;
var pop_left;
var cal_Day;

var oPopup = window.createPopup();

var fm;			// �� name
var nm;			// Input box name
var fm1 = null;
var nm1 = null;

function Calendar_Click(e) {
	cal_Day = e.title;
	if (cal_Day.length > 6) {
	    document[fm][nm].value = cal_Day;
      if (nm1 != null) {
        document[fm1][nm1].value = cal_Day;
      }
//		target.value = cal_Day
	}
	oPopup.hide();
}

function Calendar(obj, form, name) {
	fm = form;
	nm = name;

	target = obj;
	pop_top = document.body.clientTop + GetObjectTop(obj) - document.body.scrollTop;
	pop_left = document.body.clientLeft + GetObjectLeft(obj) -  document.body.scrollLeft;

	now = new Date();
	Show_cal(now.getFullYear(), now.getMonth()+1, now.getDate());
}

//��ȸ �������� �����ϰ� ����
function Calendar_Same(obj, form, name) {
	fm = form;
	nm = name;

  fm1 = form;
  nm1 = "to_paid_date";

	target = obj;
	pop_top = document.body.clientTop + GetObjectTop(obj) - document.body.scrollTop;
	pop_left = document.body.clientLeft + GetObjectLeft(obj) -  document.body.scrollLeft;

	now = new Date();
	Show_cal(now.getFullYear(), now.getMonth()+1, now.getDate());
}

//��������(���漼, ���ܼ���)
function Calendar_R(obj, form, name) {
	fm = form;
	nm = name;

	target = obj;
	pop_top = document.body.clientTop + GetObjectTop(obj) - document.body.scrollTop;
	pop_left = document.body.clientLeft + GetObjectLeft(obj) -  document.body.scrollLeft;

	now = new Date();

	Show_cal(now.getFullYear(), now.getMonth()+1, now.getDate()+2);
}

//��������(��������, ������, ȯ�氳��)
function Calendar_R2(obj, form, name) {
	fm = form;
	nm = name;

	target = obj;
	pop_top = document.body.clientTop + GetObjectTop(obj) - document.body.scrollTop;
	pop_left = document.body.clientLeft + GetObjectLeft(obj) -  document.body.scrollLeft;

	now = new Date();

	Show_cal(now.getFullYear(), now.getMonth()+1, now.getDate()+1);
}

function Calendar_M(obj, form, name) {
	var now = obj.value.split("-");
	target = obj;
	fm = form;
	nm = name;

	pop_top = document.body.clientTop + GetObjectTop(obj) - document.body.scrollTop;
	pop_left = document.body.clientLeft + GetObjectLeft(obj) -  document.body.scrollLeft;

	if (now.length == 2) {
		Show_cal_M(now[0],now[1]);					
	} else {
		now = new Date();
		Show_cal_M(now.getFullYear(), now.getMonth()+1);
	}
}

function Calendar_MM(obj, form, name) {
	target = obj;
	fm = form;
	nm = name;

	pop_top = document.body.clientTop + GetObjectTop(obj) - document.body.scrollTop;
	pop_left = document.body.clientLeft + GetObjectLeft(obj) -  document.body.scrollLeft;

  now = new Date();
  Show_cal_M(now.getFullYear(), now.getMonth()+1);
}

function doOver(el) {
	cal_Day = el.title;

	if (cal_Day.length > 7) {
		el.style.borderColor = "#FF0000";
	}
}

function doOut(el) {
	cal_Day = el.title;

	if (cal_Day.length > 7) {
		el.style.borderColor = "#FFFFFF";
	}
}

function day2(d) {	// 2�ڸ� ���ڷ� ����
	var str = new String();
	
	if (parseInt(d) < 10) {
		str = "0" + parseInt(d);
	} else {
		str = "" + parseInt(d);
	}
	return str;
}

function Show_cal(sYear, sMonth, sDay) {
	var Months_day = new Array(0,31,28,31,30,31,30,31,31,30,31,30,31)
	var Month_Val = new Array("01","02","03","04","05","06","07","08","09","10","11","12");
	var intThisYear = new Number(), intThisMonth = new Number(), intThisDay = new Number();

	datToday = new Date();													// ���� ���� ����
	
	intThisYear = parseInt(sYear,10);
	intThisMonth = parseInt(sMonth,10);
	intThisDay = parseInt(sDay,10);
	
	if (intThisYear == 0) intThisYear = datToday.getFullYear();				// ���� ���� ���
	if (intThisMonth == 0) intThisMonth = parseInt(datToday.getMonth(),10)+1;	// �� ���� ������ ���� -1 �� ���� �ŵ��� ����.
	if (intThisDay == 0) intThisDay = datToday.getDate();
	
	switch(intThisMonth) {
		case 1:
				intPrevYear = intThisYear -1;
				intPrevMonth = 12;
				intNextYear = intThisYear;
				intNextMonth = 2;
				break;
		case 12:
				intPrevYear = intThisYear;
				intPrevMonth = 11;
				intNextYear = intThisYear + 1;
				intNextMonth = 1;
				break;
		default:
				intPrevYear = intThisYear;
				intPrevMonth = parseInt(intThisMonth,10) - 1;
				intNextYear = intThisYear;
				intNextMonth = parseInt(intThisMonth,10) + 1;
				break;
	}
	intPPyear = intThisYear-1
	intNNyear = intThisYear+1

	NowThisYear = datToday.getFullYear();									// ���� ��
	NowThisMonth = datToday.getMonth()+1;									// ���� ��
	NowThisDay = datToday.getDate();											// ���� ��
	
	datFirstDay = new Date(intThisYear, intThisMonth-1, 1);			// ���� ���� 1�Ϸ� ���� ��ü ����(���� 0���� 11������ ����(1������ 12��))
	intFirstWeekday = datFirstDay.getDay();									// ���� �� 1���� ������ ���� (0:�Ͽ���, 1:������)
	//intSecondWeekday = intFirstWeekday;
	intThirdWeekday = intFirstWeekday;
	
	datThisDay = new Date(intThisYear, intThisMonth, intThisDay);	// �Ѿ�� ���� ���� ����
	//intThisWeekday = datThisDay.getDay();										// �Ѿ�� ������ �� ����
	
	intPrintDay = 1;																// ���� ���� ����
	secondPrintDay = 1;
	thirdPrintDay = 1;

	Stop_Flag = 0
	
	if ((intThisYear % 4)==0) {												// 4�⸶�� 1���̸� (��γ����� ��������)
		if ((intThisYear % 100) == 0) {
			if ((intThisYear % 400) == 0) {
				Months_day[2] = 29;
			}
		} else {
			Months_day[2] = 29;
		}
	}
	intLastDay = Months_day[intThisMonth];						// ������ ���� ����

	Cal_HTML = "<html><body>";
	Cal_HTML += "<form name='calendar'>";
	Cal_HTML += "<table id=Cal_Table border=0 bgcolor='#f4f4f4' cellpadding=1 cellspacing=1 width=100% onmouseover='parent.doOver(window.event.srcElement)' onmouseout='parent.doOut(window.event.srcElement)' style='font-size : 12;font-family:����;'>";
	Cal_HTML += "<tr height='35' align=center bgcolor='#f4f4f4'>";
	Cal_HTML += "<td colspan=7 align=center>";
	Cal_HTML += "	<select name='selYear' STYLE='font-size:11;' OnChange='parent.fnChangeYearD(calendar.selYear.value, calendar.selMonth.value, "+intThisDay+")';>";
	for (var optYear=(intThisYear-5); optYear<(intThisYear+3); optYear++) {
		Cal_HTML += "		<option value='"+optYear+"' ";
		if (optYear == intThisYear) Cal_HTML += " selected>\n";
		else Cal_HTML += ">\n";
		Cal_HTML += optYear+"</option>\n";
	}
	Cal_HTML += "	</select>";
	Cal_HTML += "&nbsp;&nbsp;&nbsp;<a style='cursor:hand;' OnClick='parent.Show_cal("+intPrevYear+","+intPrevMonth+","+intThisDay+");'>��</a> ";
	Cal_HTML += "<select name='selMonth' STYLE='font-size:11;' OnChange='parent.fnChangeYearD(calendar.selYear.value, calendar.selMonth.value, "+intThisDay+")';>";
	for (var i=1; i<13; i++) {	
		Cal_HTML += "		<option value='"+Month_Val[i-1]+"' ";
		if (intThisMonth == parseInt(Month_Val[i-1],10)) Cal_HTML += " selected>\n";
		else Cal_HTML += ">\n";
		Cal_HTML += Month_Val[i-1]+"</option>\n";
	}
	Cal_HTML += "	</select>&nbsp;";
	Cal_HTML += "<a style='cursor:hand;' OnClick='parent.Show_cal("+intNextYear+","+intNextMonth+","+intThisDay+");'>��</a>";
	Cal_HTML += "</td></tr>";
	Cal_HTML += "<tr align=center bgcolor='#87B3D6' style='color:#2065DA;' height='25'>";
	Cal_HTML += "	<td style='padding-top:3px;' width='24'><font color=black>��</font></td>";
	Cal_HTML += "	<td style='padding-top:3px;' width='24'><font color=black>��</font></td>";
	Cal_HTML += "	<td style='padding-top:3px;' width='24'><font color=black>ȭ</font></td>";
	Cal_HTML += "	<td style='padding-top:3px;' width='24'><font color=black>��</font></td>";
	Cal_HTML += "	<td style='padding-top:3px;' width='24'><font color=black>��</font></td>";
	Cal_HTML += "	<td style='padding-top:3px;' width='24'><font color=black>��</font></td>";
	Cal_HTML += "	<td style='padding-top:3px;' width='24'><font color=black>��</font></td>";
	Cal_HTML += "</tr>";
		
	for (intLoopWeek=1; intLoopWeek < 7; intLoopWeek++) {	// �ִ��� ���� ����, �ִ� 6��
		Cal_HTML += "<tr height='24' align=right bgcolor='white'>"
		for (intLoopDay=1; intLoopDay <= 7; intLoopDay++) {	// ���ϴ��� ���� ����, �Ͽ��� ����
			if (intThirdWeekday > 0) {											// ù�� �������� 1���� ũ��
				Cal_HTML += "<td>";
				intThirdWeekday--;
			} else {
				if (thirdPrintDay > intLastDay) {								// �Է� ��¦ �������� ũ�ٸ�
					Cal_HTML += "<td>";
				} else {																// �Է³�¥�� ������� �ش� �Ǹ�
					Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-"+day2(intThisMonth).toString()+"-"+day2(thirdPrintDay).toString()+" style=\"cursor:Hand;border:1px solid white;";
					if (intThisYear == NowThisYear && intThisMonth==NowThisMonth && thirdPrintDay==intThisDay) {
						Cal_HTML += "background-color:#C6F2ED;";
					}
					
					switch(intLoopDay) {
						case 1:															// �Ͽ����̸� ���� ������
							Cal_HTML += "color:red;"
							break;
						//case 7:
						//	Cal_HTML += "color:blue;"
						//	break;
						default:
							Cal_HTML += "color:black;"
							break;
					}
					Cal_HTML += "\">"+thirdPrintDay;
				}
				thirdPrintDay++;
				
				if (thirdPrintDay > intLastDay) {								// ���� ��¥ ���� ���� ������ ũ�� ������ Ż��
					Stop_Flag = 1;
				}
			}
			Cal_HTML += "</td>";
		}
		Cal_HTML += "</tr>";
		if (Stop_Flag==1) break;
	}
	Cal_HTML += "</table></form></body></html>";

	var oPopBody = oPopup.document.body;
	oPopBody.style.backgroundColor = "lightyellow";
	oPopBody.style.border = "solid black 1px";
	oPopBody.innerHTML = Cal_HTML;

	var calHeight = oPopBody.document.all.Cal_Table.offsetHeight;
	//���� 6�� ������, 5������ ����
	if (intLoopWeek == 6)	calHeight = 214;
	else	calHeight = 189;
	
	oPopup.show(pop_left, (pop_top + target.offsetHeight), 170, calHeight, document.body);
}


function Show_cal_M(sYear, sMonth) {
	var intThisYear = new Number(), intThisMonth = new Number()
	datToday = new Date();													// ���� ���� ����
	
	intThisYear = parseInt(sYear,10);
	intThisMonth = parseInt(sMonth,10);
	
	if (intThisYear == 0) intThisYear = datToday.getFullYear();				// ���� ���� ���
	if (intThisMonth == 0) intThisMonth = parseInt(datToday.getMonth(),10)+1;	// �� ���� ������ ���� -1 �� ���� �ŵ��� ����.
			
	switch(intThisMonth) {
		case 1:
				intPrevYear = intThisYear -1;
				intNextYear = intThisYear;
				break;
		case 12:
				intPrevYear = intThisYear;
				intNextYear = intThisYear + 1;
				break;
		default:
				intPrevYear = intThisYear;
				intNextYear = intThisYear;
				break;
	}
	intPPyear = intThisYear-1
	intNNyear = intThisYear+1

	Cal_HTML = "<html><head>\n";
	Cal_HTML += "</head><body>\n";
	Cal_HTML += "<table id=Cal_Table border=0 bgcolor='#f4f4f4' cellpadding=1 cellspacing=1 width=100% onmouseover='parent.doOver(window.event.srcElement)' onmouseout='parent.doOut(window.event.srcElement)' style='font-size : 12;font-family:����;'>\n";
	Cal_HTML += "<tr height='30' align=center bgcolor='#f4f4f4'>\n";
	Cal_HTML += "<td colspan='4' align='center'>\n";
	Cal_HTML += "<a style='cursor:hand;' OnClick='parent.Show_cal_M("+intPPyear+","+intThisMonth+");'>��</a>&nbsp;";
	Cal_HTML += "<select name='selYear' STYLE='font-size:11;' OnChange='parent.fnChangeYearM(this.value, "+intThisMonth+")';>";
	for (var optYear=(intThisYear-2); optYear<(intThisYear+2); optYear++) {
			Cal_HTML += "		<option value='"+optYear+"' ";
			if (optYear == intThisYear) Cal_HTML += " selected>\n";
			else Cal_HTML += ">\n";
			Cal_HTML += optYear+"</option>\n";
	}
	Cal_HTML += "	</select>\n";
	Cal_HTML += "<a style='cursor:hand;' OnClick='parent.Show_cal_M("+intNNyear+","+intThisMonth+");'>��</a>";
	Cal_HTML += "</td></tr>\n";
	Cal_HTML += "<tr><td colspan=4 height='1' bgcolor='#000000'></td></tr>";
	Cal_HTML += "<tr height='20' align=center bgcolor=white>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-01"+" style=\"cursor:Hand;\">1��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-02"+" style=\"cursor:Hand;\">2��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-03"+" style=\"cursor:Hand;\">3��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-04"+" style=\"cursor:Hand;\">4��</td>";
	Cal_HTML += "</tr>\n";
	Cal_HTML += "<tr height='20' align=center bgcolor=white>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-05"+" style=\"cursor:Hand;\">5��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-06"+" style=\"cursor:Hand;\">6��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-07"+" style=\"cursor:Hand;\">7��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-08"+" style=\"cursor:Hand;\">8��</td>";
	Cal_HTML += "</tr>\n";
	Cal_HTML += "<tr height='20' align=center bgcolor=white>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-09"+" style=\"cursor:Hand;\">9��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-10"+" style=\"cursor:Hand;\">10��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-11"+" style=\"cursor:Hand;\">11��</td>";
	Cal_HTML += "<td onClick=parent.Calendar_Click(this); title="+intThisYear+"-12"+" style=\"cursor:Hand;\">12��</td>";
	Cal_HTML += "</tr>\n";
	Cal_HTML += "</table>\n</body></html>";

	var oPopBody = oPopup.document.body;
	oPopBody.style.backgroundColor = "lightyellow";
	oPopBody.style.border = "solid black 1px";
	oPopBody.innerHTML = Cal_HTML;

	oPopup.show(pop_left, (pop_top + target.offsetHeight), 160, 99, document.body);
}


//----------------------------------
//	�ϴ޷� �⵵����Ʈ���� �⵵ ����
//----------------------------------
function fnChangeYearD(sYear,sMonth,sDay){
	Show_cal(sYear, sMonth, sDay);
}


//----------------------------------
//	���޷� �⵵����Ʈ���� �⵵ ����
//----------------------------------
function fnChangeYearM(sYear,sMonth){
	Show_cal_M(sYear, sMonth);
}


/**
	HTML ��ü�� ��ƿ��Ƽ �Լ�
**/
function GetObjectTop(obj)
{
	if (obj.offsetParent == document.body)
		return obj.offsetTop;
	else
		return obj.offsetTop + GetObjectTop(obj.offsetParent);
}

function GetObjectLeft(obj)
{
	if (obj.offsetParent == document.body)
		return obj.offsetLeft;
	else
		return obj.offsetLeft + GetObjectLeft(obj.offsetParent);
}

