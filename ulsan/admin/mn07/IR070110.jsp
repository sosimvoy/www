<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070110.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-09
* ���α׷�����		: �ϰ�/���� > ������ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn07.jsp" %>

<%
	List<CommonEntity> reportCodeList = (List<CommonEntity>)request.getAttribute("page.mn07.reportCodeList");
	List<CommonEntity> partList = (List<CommonEntity>)request.getAttribute("page.mn07.partList");
	List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn07.accList");

	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");

    String acc_date = request.getParameter("acc_date");

    if(acc_date.equals("") || acc_date == null){
        acc_date =  TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy-MM-dd");
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	

<script language="javascript">
	
function goSearch(){
    var form =  document.forms[0];
	var pageurl = "";
    if(document.getElementById("accDiv").style.display == "none"){
        form.acc_type.value = "";
        form.part_code.value = "";
        form.acc_code.value = "";
    }
    form.part_name.value    = form.part_code.options[form.part_code.selectedIndex].text;   // �μ���
    form.acc_name.value     = form.acc_code.options[form.acc_code.selectedIndex].text;      // ȸ��� 
    
	form.target = "report";
    form.cmd.value = "dayReportAll";
	pageurl = "/admin/oz51/"+form.report_gubun.value + form.report_code.value.split("|")[0] + ".etax";

	/*
	form.action = "../mn07/IR0701"+form.report_code.value.substring(4);  // ��3�ڸ��� �����ڵ�
    form.submit();
  */

	funcOpenWindow(form,'post','NeWreport',pageurl , 10,10, 1000, 1000, 0, 0, 0, 0, 1);
	
}


function chAccBox(val){
    var report_code = val.substring(0,3);
    //Ư�������� ȸ�豸��,�μ�,ȸ��� ���� ����
    if(report_code == "102" || report_code == "113" || report_code == "104" || report_code == "114" || report_code == "115" || report_code == "205" || report_code == "206" || report_code == "207" || report_code == "305" || report_code == "306" || report_code == "307"){
        document.getElementById("accDiv").style.display = 'block';
    }else{
        document.getElementById("accDiv").style.display = 'none';
    }
}


function chReport(report_gubun) {
    var form = document.sForm;
    document.hidFrame.location = "IR070101.etax?cmd=cityReportList&report_gubun="+report_gubun+"&flag=2";
    document.getElementById("accDiv").style.display = 'none';
}


function chDept(acc_type) {
    var form = document.sForm;
    var acc_code;
    if (acc_type == "A")	{
        acc_code = "11";
    }	else if (acc_type == "B")	{
        acc_code = "04";
    } else if (acc_type == "D") {
        acc_code = "01"
    } else if (acc_type == "E") {
        acc_code = "01"
    }
    document.hidFrame.location = "IR070101.etax?cmd=cityReportList&acc_type="+acc_type+"&acc_code="+acc_code+"&flag=0";
}


function chAcc(part_code) {
    var form = document.sForm;
    document.hidFrame.location = "IR070101.etax?cmd=cityReportList&acc_type="+form.acc_type.value+"&part_code="+part_code+"&flag=1";
}
function iframe_autoresize(arg){
	arg.height = eval(arg.name + ".document.body.scrollHeight");
	arg.width = eval(arg.name + ".document.body.scrollWidth");
}

function init() {
    typeInitialize();
}

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
<form name="sForm" method="post" action="IR070110.etax">
<input type="hidden" name="cmd" value="cityReportList">
<input type="hidden" name="part_name" value = "">
<input type="hidden" name="acc_name" value = "">
<input type="hidden" name="saveFile" value = "">
<table width="993" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40"><img src="../img/title7_1.gif"></td>
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
								<td width="880">
									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									�������� 
									<select name="report_gubun" desc="��������" iValue="<%=request.getParameter("report_gubun")%>" onchange="chReport(this.value)">
										<option value="D">���Ϻ���</option>
										<option value="M">��������</option>
										<option value="Q">�б⸻����</option>
									</select>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									�������� 
									<select name="report_code" desc="��������" iValue="<%=request.getParameter("report_code")%>" onchange="chAccBox(this.value)">
                                        <% 
										if (reportCodeList.size() > 0 && reportCodeList != null) { 
											for (int i=0; i < reportCodeList.size(); i++) {
												CommonEntity rowData = (CommonEntity)reportCodeList.get(i);
										%>
										<option value="<%=rowData.getString("REPORTCODE")%>|<%=rowData.getString("REPORTURL")%>"><%=rowData.getString("REPORTNAME")%></option>
										<%
												}
											}
										%>
									</select>
                                    <br><br>
									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									ȸ�迬��
									<select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
									<% for (int i = Integer.parseInt(this_year); i >= 2010; i--) { %>
										<option value="<%=i%>"><%=i%></option>
                  <% } %>
									</select>

									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									ȸ������ <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date">
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand"> 
									
                                    <!-- ȸ�豸��/�μ�/ȸ��� START -->
                                    <div id="accDiv" style="display:none;">
                                    <br>
									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									ȸ�豸��
									<select name="acc_type" desc="ȸ�豸��" iValue="<%=request.getParameter("acc_type")%>" onchange="chDept(this.value)">
										<option value="">��ü</option>
										<option value="A">�Ϲ�ȸ��</option>
										<option value="B">Ư��ȸ��</option>
										<option value="C">�����Ư��ȸ��</option>
										<option value="D">���Լ��������</option>
										<option value="E">���</option>
									</select>

									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									�μ�
									<select name="part_code" desc="�μ�" iValue="<%=request.getParameter("part_code")%>" onchange="chAcc(this.value)">
										<% 
										if (partList.size() > 0 && partList != null) { 
											for (int i=0; i < partList.size(); i++) {
												CommonEntity rowData = (CommonEntity)partList.get(i);
										%>
										<option value="<%=rowData.getString("PARTCODE")%>"><%=rowData.getString("PARTNAME")%></option>
										<%
												}
											}
										%>
									</select>

									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									ȸ���
									<select name="acc_code" desc="ȸ���" iValue="<%=request.getParameter("acc_code")%>">
										<% 
										if (accList.size() > 0 && accList != null) { 
											for (int i=0; i < accList.size(); i++) {
												CommonEntity rowData = (CommonEntity)accList.get(i);
										%>
										<option value="<%=rowData.getString("ACCCODE")%>"><%=rowData.getString("ACCNAME")%></option>
										<%
												}
											}
										%>
									</select>
									<span style="width:20px"></span>
                                    </div>
                                    <!-- ȸ�豸��/�μ�/ȸ��� END -->
								</td>
								<td>
									<img src="../img/btn_search.gif" alt="��ȸ" onClick="goSearch()" style="cursor:hand" align="absmiddle">  
								</td>
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
	<tr height="20">
		<td width="18">&nbsp;</td>
		<td width="975">&nbsp;</td>
	</tr>
</table>
</form>

<form id="rForm" method="post">
<iframe id="report" name="report" width="1050" height="300" onload="iframe_autoresize(this);" scrolling="no" frameBorder="0">
</iframe>
</form>

<iframe name="hidFrame" width="0" height="0"></iframe>

<table width="993">
	<tr>
		<td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
</body>
</html>