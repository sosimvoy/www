<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR070410.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-12
* ���α׷�����	   : �ϰ�/���� > ���Ϻ����� 
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>
<%
  List<CommonEntity> dateNoteList = (List<CommonEntity>)request.getAttribute("page.mn07.dateNoteList");
  List<CommonEntity> AgoDateNoteList = (List<CommonEntity>)request.getAttribute("page.mn07.AgoDateNoteList");
  List<CommonEntity> TotDateNoteList = (List<CommonEntity>)request.getAttribute("page.mn07.TotDateNoteList");
	

	int dateSize = 0; 
	if(dateNoteList != null && dateNoteList.size() != 0) {
		dateSize =  dateNoteList.size();
	}

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

	String fis_date = request.getParameter("fis_date");
	if ("".equals(fis_date)) {
    fis_date = TextHandler.getCurrentDate();
	}

/************** �޼��� *****************/
	String delMsg =  (String)request.getAttribute("page.mn07.delMsg");
	if (delMsg == null) {
		delMsg = "";
	}

	String SucMsg = (String)request.getAttribute("page.mn07.SucMsg");
	if (SucMsg == null ) {
	  SucMsg = "";
	}
	
	String ErrMsg = (String)request.getAttribute("page.mn07.ErrMsg");
	if (ErrMsg == null ) {
	  ErrMsg = "";
	}


// ������ �����̸� ����������
	String last_agoDay = (String)request.getAttribute("page.mn07.last_agoDay");

 /* ȸ������ 13��,14�� ���� ���� ����������,����������*/
	  String agoDay13 = (String)request.getAttribute("page.mn07.agoDay13");
	  String agoDay14 = (String)request.getAttribute("page.mn07.agoDay14");
	  String afterDay13 = (String)request.getAttribute("page.mn07.afterDay13");
	  String afterDay14 = (String)request.getAttribute("page.mn07.afterDay14");



// ��ü������ �������� 
    String m470_car_agodate_gc01 =  "";
	String m470_car_agodate_gc02 =  "";
	String m470_bal_agodate_gp01 =  "";
	String m470_bal_agodate_gc01 =  "";
	String m470_bal_agodate_gc02 =  "";

	m470_car_agodate_gc01 =  (String)request.getAttribute("page.mn07.m470_car_agodate_gc01");
	m470_car_agodate_gc02 =  (String)request.getAttribute("page.mn07.m470_car_agodate_gc02");
	m470_bal_agodate_gp01 =  (String)request.getAttribute("page.mn07.m470_bal_agodate_gp01");
	m470_bal_agodate_gc01 =  (String)request.getAttribute("page.mn07.m470_bal_agodate_gc01");
	m470_bal_agodate_gc02 =  (String)request.getAttribute("page.mn07.m470_bal_agodate_gc02");

/*	�Է¿��� ��������*/
	String inputYn01 = "";
	String inputYn02 = "";

	inputYn01 =  (String)request.getAttribute("page.mn07.inputYn01");
	inputYn02 =  (String)request.getAttribute("page.mn07.inputYn02");

	
	String m470_car_gc01			 = "0";
	String m470_car_agodate_tot_gc01 = "0";
	String m470_car_tot_gc01		 = "0";

	String m470_car_gc02			 = "0";
	String m470_car_agodate_tot_gc02 = "0";
	String m470_car_tot_gc02		 = "0";

	String m470_bal_gp01			 = "0";
	String m470_bal_agodate_tot_gp01 = "0";
	String m470_bal_tot_gp01		 = "0";

	String m470_bal_gc01			 = "0";
	String m470_bal_agodate_tot_gc01 = "0";
	String m470_bal_tot_gc01		 = "0";

	String m470_bal_gc02			 = "0";
	String m470_bal_agodate_tot_gc02 = "0";
	String m470_bal_tot_gc02		 = "0";

	String m470_car_gc01_tot		 = "0";
	String m470_bal_gc01_tot		 = "0";



	 for (int i=0; i < dateNoteList.size(); i++) {
        CommonEntity rowData = (CommonEntity)dateNoteList.get(i);

		m470_car_gc01				= rowData.getString("m470_car_gc01");
		m470_car_agodate_tot_gc01	= rowData.getString("m470_car_agodate_tot_gc01");
		m470_car_tot_gc01			= rowData.getString("m470_car_tot_gc01");
		m470_car_agodate_gc01		= rowData.getString("m470_car_agodate_gc01");

		m470_car_gc02				= rowData.getString("m470_car_gc02");
		m470_car_agodate_tot_gc02	= rowData.getString("m470_car_agodate_tot_gc02");
		m470_car_tot_gc02			= rowData.getString("m470_car_tot_gc02");
		m470_car_agodate_gc02		= rowData.getString("m470_car_agodate_gc02");

		m470_bal_gp01				= rowData.getString("m470_bal_gp01");
		m470_bal_agodate_tot_gp01	= rowData.getString("m470_bal_agodate_tot_gp01");
		m470_bal_tot_gp01			= rowData.getString("m470_bal_tot_gp01");
		m470_bal_agodate_gp01		= rowData.getString("m470_bal_agodate_gp01");

		m470_bal_gc01				= rowData.getString("m470_bal_gc01");
		m470_bal_agodate_tot_gc01	= rowData.getString("m470_bal_agodate_tot_gc01");
		m470_bal_tot_gc01			= rowData.getString("m470_bal_tot_gc01");
		m470_bal_agodate_gc01		= rowData.getString("m470_bal_agodate_gc01");

		m470_bal_gc02				= rowData.getString("m470_bal_gc02");
		m470_bal_agodate_tot_gc02	= rowData.getString("m470_bal_agodate_tot_gc02");
		m470_bal_tot_gc02			= rowData.getString("m470_bal_tot_gc02");
		m470_bal_agodate_gc02		= rowData.getString("m470_bal_agodate_gc02");

	 }
  
//========= ���� ���� ����
	for (int i=0; i < AgoDateNoteList.size(); i++) {
        CommonEntity agoData = (CommonEntity)AgoDateNoteList.get(i);

		m470_car_tot_gc01			= agoData.getString("m470_car_tot_gc01");
		m470_bal_tot_gc01			= agoData.getString("m470_bal_tot_gc01");


		m470_car_agodate_tot_gc01	= agoData.getString("m470_car_tot_gc01");
		m470_car_agodate_tot_gc02	= agoData.getString("m470_car_tot_gc02");
		m470_bal_agodate_tot_gp01	= "0"; // �׻� ����
		m470_bal_agodate_tot_gc01	= agoData.getString("m470_bal_tot_gc01");
		m470_bal_agodate_tot_gc02	= agoData.getString("m470_bal_tot_gc02");
	 }


//========= �հ� ���� ����
	for (int i=0; i < TotDateNoteList.size(); i++) {
        CommonEntity TotData = (CommonEntity)TotDateNoteList.get(i);

		m470_car_gc01_tot	= TotData.getString("m470_car_gc01");
		m470_bal_gc01_tot	= TotData.getString("m470_bal_gc01");
	 }


if(agoDay13.equals(fis_date)){
	m470_car_agodate_tot_gc02 = "0";
}

if(afterDay14.equals(fis_date)){
	m470_bal_agodate_tot_gc02 = "0";
}

int tot_gc01 = 0;
if(afterDay13.equals(fis_date)){
	tot_gc01 = Integer.parseInt(m470_car_tot_gc01) - Integer.parseInt(m470_car_gc01_tot);

	m470_car_agodate_tot_gc01 = Integer.toString(tot_gc01);
	m470_car_tot_gc01 = Integer.toString(tot_gc01);
}


int tot_gc02 = 0;
if(afterDay14.equals(fis_date)){
	tot_gc02 = Integer.parseInt(m470_bal_tot_gc01) - Integer.parseInt(m470_bal_gc01_tot);

	m470_bal_agodate_tot_gc01 = Integer.toString(tot_gc02);
	m470_bal_tot_gc01 = Integer.toString(tot_gc02);
}


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ������� �ý���</title>
  	<link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>	
  	<script language="javascript">
	function init() {
	typeInitialize();
    }

	function goBack() {
	var form = document.sForm;
	form.action = "IR070410.etax";
	form.target = "_self";
	eSubmit(form);
	}
	

	function goSearch() {
	var form = document.sForm;
	form.action = "IR070411.etax";
    form.search_yn.value = "Y";
	form.cmd.value = "dateNoteList"; 
	form.target = "_self";
	eSubmit(form);
	}

	function saveData()	{
		var form = document.sForm;
		var dateNoteList = <%=dateSize%>; 
		var search_date =form.search_date.value;   // ��ȸ�� ���� (request)

		
		form.action = "IR070411.etax";
		form.cmd.value = "dateNoteInsert";
		eSubmit(form);
	}

function goDelete()	{
	var form = document.sForm;
	form.action = "IR070411.etax";
	form.cmd.value = "dateNoteDelete";
	eSubmit(form);
}


// �޸����
function Numberchk() { 
if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false; 
} 

function vComma(obj) { 
    var str    = "" + obj.value.replace(/,/gi,''); // �޸� ���� 

    var regx    = new RegExp(/(-?\d+)(\d{3})/); 
    var bExists = str.indexOf(".",0); 
    var strArr  = str.split('.'); 

    while(regx.test(strArr[0])){ 
        strArr[0] = strArr[0].replace(regx,"$1,$2"); 
    } 
    if (bExists > -1) 
        obj.value = strArr[0] + "." + strArr[1]; 
    else 
        obj.value = strArr[0];
	
	TotView1();
} 



function TotView1(){
	var str011    = "" + sForm.m470_car_gc01.value.replace(/,/gi,'');				// �޸� ���� 
	var str012    = "" + sForm.m470_car_agodate_tot_gc01.value.replace(/,/gi,''); // �޸� ���� 

	var str021    = "" + sForm.m470_car_gc02.value.replace(/,/gi,'');				// �޸� ���� 
	var str022    = "" + sForm.m470_car_agodate_tot_gc02.value.replace(/,/gi,''); // �޸� ���� 

	var str031    = "" + sForm.m470_bal_gp01.value.replace(/,/gi,'');				// �޸� ���� 
	var str032    = "" + sForm.m470_bal_agodate_tot_gp01.value.replace(/,/gi,''); // �޸� ���� 

	var str041    = "" + sForm.m470_bal_gc01.value.replace(/,/gi,'');				// �޸� ���� 
	var str042    = "" + sForm.m470_bal_agodate_tot_gc01.value.replace(/,/gi,''); // �޸� ���� 

	var str051    = "" + sForm.m470_bal_gc02.value.replace(/,/gi,'');				// �޸� ���� 
	var str052    = "" + sForm.m470_bal_agodate_tot_gc02.value.replace(/,/gi,''); // �޸� ���� 

	var str013 = parseInt(str011) + parseInt(str012); 
	var str023 = parseInt(str021) + parseInt(str022); 
	var str033 = parseInt(str031) + parseInt(str032); 
	var str043 = parseInt(str041) + parseInt(str042); 
	var str053 = parseInt(str051) + parseInt(str052); 
	
	sForm.m470_car_tot_gc01.value = str013;
	sForm.m470_car_tot_gc02.value = str023;
	sForm.m470_bal_tot_gp01.value = str033;
	sForm.m470_bal_tot_gc01.value = str043;
	sForm.m470_bal_tot_gc02.value = str053;

	vComma1(sForm.m470_car_tot_gc01);
	vComma1(sForm.m470_car_tot_gc02);
	vComma1(sForm.m470_bal_tot_gp01);
	vComma1(sForm.m470_bal_tot_gc01);
	vComma1(sForm.m470_bal_tot_gc02);

	
}


function vComma1(obj) { 
    var str    = "" + obj.value.replace(/,/gi,''); // �޸� ���� 

    var regx    = new RegExp(/(-?\d+)(\d{3})/); 
    var bExists = str.indexOf(".",0); 
    var strArr  = str.split('.'); 

    while(regx.test(strArr[0])){ 
        strArr[0] = strArr[0].replace(regx,"$1,$2"); 
    } 
    if (bExists > -1) 
        obj.value = strArr[0] + "." + strArr[1]; 
    else 
        obj.value = strArr[0];
	
} 


function trim(str) { 
    return str.replace(/(^\s*)|(\s*$)/g, ""); 
} 

function getNumber(str) { 
    str = "" + str.replace(/,/gi,''); // �޸� ���� 
    str = str.replace(/(^\s*)|(\s*$)/g, ""); // trim 
    return (new Number(str)); 
} 

    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"">
  <form name="sForm" method="post" action="IR070411.etax">
  <input type="hidden" name="cmd" value="dateNoteList">
  <input type="hidden" name="search_date" value="<%=fis_date%>">     
  <input type="hidden" name="search_yn" value="<%=request.getParameter("search_yn")%>">
  
<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr> 
	<td width="18">&nbsp;</td>
	<td width="975" height="40"><img src="../img/title7_11.gif"></td>
  </tr>
  <tr>
	<td width="18">&nbsp;</td>
	<td width="975" height="40"> 
	  <table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
		<tr> 
		  <td><img src="../img/box_top.gif" width="964" height="11"></td>
		</tr>
		<tr> 
		  <td> 
			<table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
				<td width="860">
				  <span style="width:50px"></span>
				  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
				  ȸ�迬��&nbsp;
				  <select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
					<option value="<%=this_year%>"><%=this_year%></option>
					<option value="<%=last_year%>"><%=last_year%></option>
				  </select>
				  <span style="width:50px"></span>
				  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
				  ȸ������&nbsp;<input type="text" value="<%=TextHandler.formatDate(fis_date,"yyyyMMdd","yyyy-MM-dd");%>" class="box3" name="fis_date" size="8" userType="date">
				   <img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','fis_date');" style="cursor:hand">
				</td>
				<td><img src="../img/btn_search.gif" alt="��ȸ" onClick="goSearch()" style="cursor:hand" align="absmiddle"></td>
			</tr>
			</table>
		</td>
		</tr>
		<tr>
			<td><img src="../img/box_bt.gif" width="964" height="10"></td>
		</tr>
	  </table>
	</td>
</tr>

<tr>
	<td width="18">&nbsp;</td>
	<td width="975"> 
	<!-------- ǥ ���� ----->

<table width="993" border="0" cellspacing="0" cellpadding="0" class="list">
<tr>
	<td colspan="3">���������׸�</td>
	<td rowspan="2">���ϼ����ݾ�</td>
	<td rowspan="2">���ϱ�������</td>
	<td rowspan="2">�� ��</td>
	<td rowspan="2">�ڱ���ü������</td>
</tr>
<tr>
	<td>����</td>
	<td>�Ⱓ</td>
	<td>����</td>
</tr>
<tr>
	<td rowspan="2">������漼</td>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td><input type="text" name="m470_car_gc01" style="text-align:right;" onkeypress="Numberchk()" onKeyUp="vComma(this);" 
	<% if(Integer.parseInt(fis_date) >= Integer.parseInt(m470_car_agodate_gc01)){ out.println("readonly");} %> value="<%=StringUtil.formatNumber(m470_car_gc01)%>" ></td>
	<td><input type="text" name="m470_car_agodate_tot_gc01" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_car_agodate_tot_gc01)%>" ></td>
	<td><input type="text" name="m470_car_tot_gc01" style="text-align:right;border:0px" value="<%=StringUtil.formatNumber(m470_car_tot_gc01)%>"></td>
	<td><input type="text" name="m470_car_agodate_gc01" value="<%=TextHandler.formatDate(m470_car_agodate_gc01,"yyyyMMdd","yyyy-MM-dd");%>" style="text-align:right;border:0px"></td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td><input type="text" name="m470_car_gc02" style="text-align:right;" onkeypress="Numberchk()" onKeyUp='vComma(this);' <%//inputYn01%> value="<%=StringUtil.formatNumber(m470_car_gc02)%>"></td>
	<td><input type="text" name="m470_car_agodate_tot_gc02" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_car_agodate_tot_gc02)%>"></td>
	<td><input type="text" name="m470_car_tot_gc02" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_car_tot_gc02)%>"></td>
	<td><input type="text" name="m470_car_agodate_gc02" value="<%=TextHandler.formatDate(m470_car_agodate_gc02,"yyyyMMdd","yyyy-MM-dd");%>" style="text-align:right;border:0px"></td>
</tr>

<tr>
	<td rowspan="3">����û ���� �ü�</td>
	<td>�ݰ��</td>
	<td>�Ϲ�</td>
	<td><input type="text" name="m470_bal_gp01" style="text-align:right;" onkeypress="Numberchk()" onKeyUp='vComma(this);' value="<%=StringUtil.formatNumber(m470_bal_gp01)%>"></td>
	<td><input type="text" name="m470_bal_agodate_tot_gp01" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_bal_agodate_tot_gp01)%>"></td>
	<td><input type="text" name="m470_bal_tot_gp01" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_bal_tot_gp01)%>"></td>
	<td><input type="text" name="m470_bal_agodate_gp01" value="<%=TextHandler.formatDate(m470_bal_agodate_gp01,"yyyyMMdd","yyyy-MM-dd");%>" style="text-align:right;border:0px"></td>
</tr>
<tr>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td><input type="text" name="m470_bal_gc01" style="text-align:right;" onkeypress="Numberchk()" onKeyUp='vComma(this);' <% if(Integer.parseInt(fis_date) >= Integer.parseInt(m470_bal_agodate_gc01)){ out.println("readonly");} %>  value="<%=StringUtil.formatNumber(m470_bal_gc01)%>"></td>
	<td><input type="text" name="m470_bal_agodate_tot_gc01" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_bal_agodate_tot_gc01)%>"></td>
	<td><input type="text" name="m470_bal_tot_gc01" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_bal_tot_gc01)%>"></td>
	<td><input type="text" name="m470_bal_agodate_gc01" value="<%=TextHandler.formatDate(m470_bal_agodate_gc01,"yyyyMMdd","yyyy-MM-dd");%>" style="text-align:right;border:0px"></td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td><input type="text" name="m470_bal_gc02" style="text-align:right;" onkeypress="Numberchk()" onKeyUp='vComma(this);' <%//=inputYn02%> value="<%=StringUtil.formatNumber(m470_bal_gc02)%>"></td>
	<td><input type="text" name="m470_bal_agodate_tot_gc02" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_bal_agodate_tot_gc02)%>"></td>
	<td><input type="text" name="m470_bal_tot_gc02" style="text-align:right;border:0px" readonly value="<%=StringUtil.formatNumber(m470_bal_tot_gc02)%>"></td>
	<td><input type="text" name="m470_bal_agodate_gc02" value="<%=TextHandler.formatDate(m470_bal_agodate_gc02,"yyyyMMdd","yyyy-MM-dd");%>" style="text-align:right;border:0px" readonly></td>
</tr>
</table>
<!-------- ǥ �� ----->
	</td>
</tr>
<tr>
  <td width="18">&nbsp;</td>
	<td width="975" height="20"> &nbsp;</td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr>
	  <td>
		  <div align="right">
		  <img src="../img/btn_order.gif" alt="���" onClick="saveData()" style="cursor:hand" align="absmiddle">	
			<img src="../img/btn_delete.gif" alt="����" onClick="goDelete()" style="cursor:hand" align="absmiddle">
			</div>
		</td>
	</tr>
</table>

<table>
  <tr>
	  <td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
</form>

</body>
<% if (!"".equals(SucMsg)) { %>
	 <script>
	 alert("<%=SucMsg%>");
	</script>
<% } %> 


<% if (!"".equals(ErrMsg)) { %>
	 <script>
	 alert("<%=ErrMsg%>");
	 goBack();
	 </script>
<% } %> 



<% if (!"".equals(delMsg)) { %>
  <script>
  alert("<%=delMsg%>");
	</script>
<% } %>
</html>
