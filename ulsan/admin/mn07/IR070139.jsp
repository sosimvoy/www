<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070139.jsp
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2010-09-13
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���޸�ɼ�
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	int i = 0;//for ����

	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");
	List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");

	Integer totalCount = (Integer)request.getAttribute("page.mn07.totalCount");
    
  String cpage = request.getParameter("cpage");	// ���� ������ ��ȣ �ޱ�
	if( "".equals(cpage) ) {
		cpage = "1";
	}

    String saveFile = request.getParameter("saveFile");

    String today = TextHandler.getCurrentDate();

    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=���޸�ɼ�_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");
        response.setHeader("Content-Description", "JSP Generated Data");
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%if(saveFile.equals("1")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">

<%}%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<%if(saveFile.equals("")){%>
<link href="style.css" media="print" rel="stylesheet" type="text/css" />  <!--���� �������� ����Ʈ CSS-->
<link href="style.css" media="screen" rel="stylesheet" type="text/css" />  <!--���� �������� ����Ʈ CSS-->
<style>
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}
-->

    :root p {font-size:12pt; padding-left:300px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html p {font-size:12pt; padding-left:300px;} /*IE8 ���� */   
 * + html p {font-size:12pt; padding-left:300px;} /*IE7 ���� */   
 *   html p {font-size:12pt; padding-left:300px;} /*IE6 ���� */ 

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<%}%>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>
<script language="javascript" src="../js/tax_common.js"></script>
<script language="javascript" src="../js/rowspan.js"></script>
<script language="javascript" src="../js/report.js"></script>
<script language="javascript">

function init(){
}

function PAGE(cpage) {
    if(cpage != null) {
    document.sForm.cpage.value=cpage;
  } else {
    document.sForm.cpage.value="";
  }
  document.sForm.target = "_self";
  eSubmit(document.sForm);
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header�� �� ����
		factory.printing.footer = ""; // Footer�� �� ����
		factory.printing.portrait = true; // false �� �����μ�, true �� ���� �μ�
		factory.printing.leftMargin = 1.0; // ���� ���� ������
		factory.printing.topMargin = 1.0; // �� ���� ������
		factory.printing.rightMargin = 1.0; // ������ ���� ������
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������
		factory.printing.Print(true); // ����ϱ�
}

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070139.etax">
<input type="hidden" name="cmd" value="dayReport29">
<%if(saveFile.equals("")){%>
<input type="hidden" name="acc_year"   value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date"   value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type"   value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code"  value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code"   value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile"   value="<%=saveFile%>">
<input type="hidden" name="cpage"      value="">
<% } %>

<%
	if (reportList.size() > 0 && reportList != null) {
%>
<center>
<div id="box">
<div class="noprint">
<table width="713" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr>
		<td width="713">
  <%if(saveFile.equals("")){%>
			<table width="713" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr>
					<td align="right">
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<img src="../img/btn_print.gif" alt="�μ�"  onClick="goPrint();" style="cursor:hand" align="absmiddle">
					</td>
				</tr>
			</table>
  <%}%>
		</td>
	</tr>
</table>
</div>
<table width="713" border="0" cellspacing="0" cellpadding="1">
<%
	  String accName = "";
	  int j = 0;

		for (i=0; i < reportList.size(); i++) {
			CommonEntity rowData = (CommonEntity)reportList.get(i);			
			
			if (i == 0 ) {
%>
	<tr height="30">
		<td colspan="4">
			<p><b><u>�� �� �� �� ��</u></b></p>
		</td>
		<td align="right" valign="middle">
    <%
		if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ�� (�ú���)
    %>
			<table width="150" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
				<tr height="25">
					<td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
				</tr>
				<tr height="25">
					<td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
				</tr>
			</table>
    <%
	    	}//end if(!endState.getString("END_STATE").equals("Y")){...
    %>
		</td>
	</tr>
	<tr>
		<td colspan="5">
			<div align="center">(
				<%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
				<%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>�Ϻ�)
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">ȸ�迬��:<%=request.getParameter("acc_year")%>��<br></td>
		<td colspan="3"><div align="right"><%=rowData.getString("M360_ACCNAME")%></div></td>
	</tr>
</table>
<table id="dataList" width="713" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
	<tr height="20">
		<td width="150"><div align="center">���������ȣ</div></td>
		<td width="143"><div align="center">ä�ּ���</div></td>
		<td width="100"><div align="center">�Ǽ�</div></td>
		<td width="120"><div align="center">�ݾ�</div></td>
		<td width="100"><div align="center">���</div></td>
	</tr>
  <% 
  	} //(if == 0) end
  %>
  <%
	  if(!"".equals(accName) && !accName.equals(rowData.getString("M360_ACCNAME"))) j = 0;   // ������ �޶����� ���ȣ�� ���� ä���Ͽ� �װ� �������� 45�� ����. �������� 1�϶� �������� ������ ���������� 45���� �����.
	  j++;
	  if (i > 0 && (j%45==1 || (!accName.equals(rowData.getString("M360_ACCNAME")))) ){  // ������ ��ȯ Ÿ��Ʋ
//		  out.println("accName=["+accName+"], i=["+i+"],rowData=["+rowData.getString("M360_ACCNAME")+"],j=["+j+"]");
  %>
</table>
<table width="713" border="0" cellspacing="0" cellpadding="4" class="basic">
	<tr height="25">
		<td width="150"></td>
		<td colspan="3">
			<span style="width:100px"></span>���� ���� ���� �����Ͽ���.<br>
			<span style="width:130px"></span>
			  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
				<%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>��
		</td>
		<td rowspan="2" width="80">
			<% 
			  if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)
			%>
					<img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
			<% 
		  	} 
			%>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="left">��걤���� ����� ���� </td>
		<td colspan="2" align="right">�� �� �� �� �� �� ��</td>
	</tr>
</table>
<br style="page-break-before:always;"/> <!--���� �������� ����Ʈ -->

<!--���������� title -->
<table width="713" border="0" cellspacing="0" cellpadding="1">
	<tr height="30">
		<td colspan="4">
		  <p><b><u>�� �� �� �� ��</u></b></p>
		</td>
		<td align="right" valign="middle">
    <%
			if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ�� (�ú���)
    %>
			<table width="150" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
				<tr height="25">
					<td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
				</tr>
				<tr height="25">
					<td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
				</tr>
			</table>
   <%
				}
   %>
		</td>
	</tr>
	<tr>
		<td colspan="5">
			<div align="center">(
			<%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
			<%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>�Ϻ�)</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">ȸ�迬��:<%=request.getParameter("acc_year")%>��<br></td>
		<td colspan="3"><div align="right"><%=rowData.getString("M360_ACCNAME")%></div></td>
	</tr>
</table>
<table id="dataList" width="713" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
	<tr height="20">
		<td width="150"><div align="center">���������ȣ</div></td>
		<td width="143"><div align="center">ä�ּ���</div></td>
		<td width="100"><div align="center">�Ǽ�</div></td>
		<td width="120"><div align="center">�ݾ�</div></td>
		<td width="100"><div align="center">���</div></td>
	</tr>
<%
		}//	if (i > 0 && !accName.equals(rowData.getString("M360_ACCNAME")) ) {... ������ ��ȯ End 	
%>
	<tr height="15">
		<td width="150">
			<div align="center">
			 <%
				if((!request.getParameter("acc_code").equals("") || request.getParameter("acc_code") != null) && rowData.getString("M030_ORDERNO").equals("")){
					out.println("�Ұ�");
				}else{
					out.println(rowData.getString("M030_ORDERNO"));
				}
			 %>
			</div>
		</td>
		<td width="143">
			<div align="left"><%=rowData.getString("M030_ORDERNAME")%></div>
		</td>
		<td width="100">
			<div align="center"><%=rowData.getString("TOT_CNT")%></div>
		</td>
		<td width="170">
			<div align="right"><%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></div>
		</td>
		<td width="150">
			<div align="center">&nbsp;</div>
		</td>
	</tr>
<%  
    accName = rowData.getString("M360_ACCNAME");//accName ȸ����� ��ƹ���
	}//end for (i=0; i < reportList.size(); i++) {... 
%>
</table>
<table width="713" border="0" cellspacing="0" cellpadding="4" class="basic">
	<tr height="25">
		<td width="150"></td>
		<td colspan="3">
			<span style="width:100px"></span>���� ���� ���� �����Ͽ���.<br>
			<span style="width:130px"></span>
			  <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
				<%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>��
		</td>
		<td rowspan="2" width="80">
<% 
	if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
			<img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
<% 
	} 
%>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="left">��걤���� ����� ���� </td>
		<td colspan="2" align="right">�� �� �� �� �� �� ��</td>
	</tr>
</table>
</div>

<div class="noprint">
<%// ������ %>
<%if(saveFile.equals("")){%>
<table width="960" align="center">
  <tr>
    <td align="center">
    <Br>
    <etax:pagelink totalCnt='<%=totalCount.intValue()%>' page='<%=cpage%>'	pageCnt='5' blockCnt='10'  />
    </td>
  </tr>
</table>
<%}%>
</div>

<%
}else{
%>
<table width="710" border="0" cellpadding="0" cellspacing="0">
	<tr height="50">
		<td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
	</tr>
</table>
<%
}// end if (reportList.size() > 0 && reportList != null) {...
%>
</center>
</form>
</body>
</html>