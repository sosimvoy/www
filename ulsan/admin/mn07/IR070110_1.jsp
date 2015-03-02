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
	List<CommonEntity> partList = (List<CommonEntity>)request.getAttribute("page.mn07.partList");
	List<CommonEntity> accList = (List<CommonEntity>)request.getAttribute("page.mn07.accList");
	List<CommonEntity> codeList = (List<CommonEntity>)request.getAttribute("page.mn07.codeList");

	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

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
    form.part_name.value = form.part_code.options[form.part_code.selectedIndex].text;   // �μ��� 
    form.target = "report";
    form.action = form.report_code.value;
    form.submit();
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
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0"">
<form name="sForm" method="post" action="IR070110.etax">
<input type="hidden" name="cmd" value="ReportList">
<input type="hidden" name="part_name" value = "">
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
								<td width="860">
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									�������� 
									<select name="report_type" desc="��������" iValue="<%=request.getParameter("report_type")%>">
										<option value="D">���Ϻ���</option>
										<option value="M">��������</option>
										<option value="Q">�б�ߺ���</option>
									</select>

									<span style="width:20px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									��������
									<select name="report_code" desc="��������" iValue="<%=request.getParameter("report_code")%>">
										<option value="">��ü</option>
										<% 
										if (codeList.size() > 0 && codeList != null) { 
											for (int i=0; i < codeList.size(); i++) {
												CommonEntity rowData = (CommonEntity)codeList.get(i);
										%>
										<option value="<%=rowData.getString("REPORTCODE")%>"><%=rowData.getString("REPORTNAME")%></option>
										<%
												}
											}
										%>
									</select>
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									ȸ�迬��
									<select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
										<option value="<%=this_year%>"><%=this_year%></option>
										<option value="<%=last_year%>"><%=last_year%></option>
									</select>

									<span style="width:80px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									ȸ������ <input type="text" class="box3" size="8" name="acc_date" value="<%=acc_date%>" userType="date">
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','acc_date');" style="cursor:hand"> 
									<br><br>
									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									ȸ�豸��
									<select name="acc_type" desc="ȸ�豸��" iValue="<%=request.getParameter("acc_type")%>">
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
									<select name="part_code" desc="�μ�" iValue="<%=request.getParameter("report_code")%>">
										<option value="">��ü</option>
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

									<span style="width:50px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle">
									ȸ���
									<select name="acc_code" desc="ȸ���" iValue="<%=request.getParameter("report_code")%>">
										<option value="">��ü</option>
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
<iframe id="report" name="report" width="1000" height="300" onload="iframe_autoresize(this);" scrolling="no" frameBorder="0">
</iframe>
</form>

<table width="1000">
	<tr>
		<td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
<iframe name="hidFrame" width="0" height="0"></iframe>
</body>
</html>