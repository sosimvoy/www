<%
/***************************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR010710.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-09-08
* ���α׷�����	   : ���� > OCR��������/���
* ���α׷����      : 1. �Է��� ȸ������ ���� ���Լ����&���ܼ���OCR�ڷ� ������ ���� Ȯ��(ȸ���� ���� ���Ͻ� ���� �Ұ�)
                      2. �ش� ȸ������(ȸ������,ȸ���ڵ�,�����ڵ�,�μ��ڵ����) ���Լ���� ���
                      3. �ش� ȸ������ ���Լ���� ���� : 2010-10-07 ȸ��⵵ ���뿡 ���� ���� �Ұ� ó��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.etax.helper.MessageHelper" %>
<%@ page import = "com.etax.config.ETaxConfig.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@include file="../menu/mn01.jsp" %>
<%
	String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);

  String ago_acc_date  = (String)request.getAttribute("page.mn01.ago_acc_date");    // �������� �޾ƿ���
	//String fis_date   =(String)request.getAttribute("page.mn01.fis_date");

    String input_date = request.getParameter("input_date");

    if(input_date.equals("") || input_date == null){
        input_date = TextHandler.formatDate(ago_acc_date,"yyyyMMdd","yyyy-MM-dd");
    }
	
	String insMsg = (String)request.getAttribute("page.mn01.insMsg");
	String delMsg = (String)request.getAttribute("page.mn01.delMsg");
	if (delMsg == null ) {
		delMsg = "";
	}
	if (insMsg == null ) {
		insMsg = "";
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

function init() {
	typeInitialize();
}

function goSave(){
	var form = document.sForm;
	if(form.input_date.value == ""){
		alert("�۾����ڸ� �Է��ϼ���.");
		form.input_date.focus();
		return;
	}
	form.cmd.value		= "ocrProcess";
	form.action = "IR010710.etax";  
	eSubmit(form); 
}

function goCancel(){
	var form = document.sForm;
	if(form.input_date.value == ""){
		alert("�۾����ڸ� �Է��ϼ���.");
		form.input_date.focus();
		return;
	}
	form.cmd.value = "ocrCancel";
	form.action = "IR010710.etax";  
	eSubmit(form); 
}

</script>
</head>
<body topmargin="0" leftmargin="0" oncontextmenu="return false">
<form name="sForm" method="post" action="IR010710.etax">
<input type="hidden" name="cmd"			value="">
<input type="hidden" name="work_log"	value="A01"><!-- �������� -->
<input type="hidden" name="trans_gubun" value="177"><!-- �ŷ����� -->
<table width="993" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40"><img src="../img/title1_7.gif"></td>
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
						<table width="964" border="0" cellspacing="0" cellpadding="0" align="center" height="100">
							<tr> 
								<td width="860">
									<span style="width:50"></span>
									<!--
									<span style="width:10px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
									ȸ�迬��&nbsp;
									<select name="fis_year" iValue="<%=request.getParameter("fis_year")%>">
										<option value="<%=this_year%>"><%=this_year%></option>
										<option value="<%=last_year%>"><%=last_year%></option>
									</select>
									-->
									<span style="width:300px"></span>
									<img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 									
									�۾�����&nbsp; <input type="text" class="box3" name="input_date" value="<%=input_date%>" size="8" userType="date" desc="�۾�����">
									<img src="../img/btn_calendar.gif" align="absmiddle" onclick="Calendar(this,'sForm','input_date');" style="cursor:hand">
									<span style="width:50px"></span>
									<img src="../img/btn_process.gif" alt="ó��" onClick="goSave()" style="cursor:hand" align="absmiddle">
                                    <!-- 2010-10-07 ȸ��⵵ ���뿡 ���� ���� �Ұ� ó�� 
									<img src="../img/btn_cancel.gif" alt="���" onClick="goCancel()" style="cursor:hand" align="absmiddle">
                                    -->
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
</table>
<p>&nbsp;</p>
<p><img src="../img/footer.gif" width="1000" height="72"> </p>
</form>

<% if (!"".equals(delMsg)) { %>
	<script>
         alert("<%=delMsg%>");
	</script>
<% } %>
<% if (!"".equals(insMsg)) { %>
	<script>
         alert("<%=insMsg%>");
	</script>
<% } %>

</body>
</html>