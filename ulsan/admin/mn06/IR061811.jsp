<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR061811.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-07-15
* ���α׷�����	   : �ڱݿ�� > ����ǥ��ȸ(����)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%
  if ("Y".equals(request.getParameter("excelFlag")) ) {
    String charMarkForExcel = "";
    response.setContentType("application/vnd.ms-excel;charset=euc-kr");
    response.setHeader("Content-Disposition", "inline;filename=�ݰ�����ܾ�ǥ(�Ϲ�ȸ��)_"+request.getParameter("s_month")+".xls"); 
    response.setHeader("Content-Description", "JSP Generated Data");
    charMarkForExcel = "`"; // ���ڸ� �״�� ǥ���ϱ� ����
  }
  List<CommonEntity> lastAvgList = (List<CommonEntity>)request.getAttribute("page.mn06.lastAvgList"); //���⵵
  List<CommonEntity> thisAvgList = (List<CommonEntity>)request.getAttribute("page.mn06.thisAvgList"); //���⵵
  String last_year = (String)request.getAttribute("page.mn06.last_year"); //���⵵��ġ

  int thisListSize = 0;

  if (thisAvgList != null) {
    thisListSize = thisAvgList.size();
  }

%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<% if ("Y".equals(request.getParameter("excelFlag")) ) { %>
<meta name=ProgId content=Excel.Sheet>
<style>
  table { table-layout:fixed; }
  tr,td { border:0.5pt solid silver; }
</style>
<% } else { %>
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 14pt; color: #333333; text-decoration: underline}
@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<%  } %>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function goPrint() {
    var form = document.sForm;
    form.excelFlag.value = "N";
		factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = false; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 1.0; // ���� ���� ������  
		factory.printing.topMargin = 1.0; // �� ���� ������  
		factory.printing.rightMargin = 1.0; // ������ ���� ������  
		factory.printing.bottomMargin = 1.0; // �Ʒ� ���� ������  
		factory.printing.Print(true); // ����ϱ� 
	}

  function goExcel() {
	  var form = document.sForm;
    form.excelFlag.value = "Y";
    form.action = "IR061811.etax";
    form.cmd.value = "avgMoneyList";
		eSubmit(form);
	}
</script>
</head>
<body bgcolor="#FFFFFF" topmargin="10" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR061811.etax">
<input type="hidden" name="cmd" value="avgMoneyList">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_month" value="<%=request.getParameter("acc_month")%>">
<input type="hidden" name="s_month" value="<%=request.getParameter("s_month")%>">
<input type="hidden" name="excelFlag" value="">
<table width="1000" border="0" cellspacing="0" cellpadding="2" class="line">
  <tr style="font-weight:bolder"> 
    <td height="60" align="center" colspan="7">�ݰ�����ܾ�ǥ(�Ϲ�ȸ��)</td>
  </tr>
</table>
<table width="1000" border="0" cellspacing="0" cellpadding="2" class="basic">
  <tr> 
    <td colspan="3"><b>[<%=last_year%>�⵵] ��������</b></td>
    <td colspan="4" align="right">(����:��)</td>
  </tr>
</table>
<table width="1000" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td align="center" width="7%">����</td>
    <td align="center" width="16%">���ݿ���</td>
    <td align="center" width="16%">���⿹��</td>
    <td align="center" width="15%">ȯ��ä��</td>
    <td align="center" width="15%">MMDA</td>
    <td align="center" width="16%">���༺�����հ�</td>
    <td align="center" width="15%">���հ�</td>
  </tr>
  <% for (int i=0; lastAvgList != null && i<lastAvgList.size(); i++) { 
       CommonEntity rowData = (CommonEntity)lastAvgList.get(i);
  %>
  <tr> 
    <td align="center"><%=i+1%>��</td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("GONGGUM_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("JEONGGI_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("HWAN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("MMDA_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("SAVE_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("TOT_AMT"))%></td>
  </tr>
  <% } %>
</table>
<br>
<table width="1000" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td colspan="3"><b>[<%=request.getParameter("acc_year")%>�⵵] ��������</b></td>
    <td colspan="4"> 
      <div align="right">(����:��)</div>
    </td>
  </tr>
</table>
<table width="1000" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td align="center" width="7%">����</td>
    <td align="center" width="16%">���ݿ���</td>
    <td align="center" width="16%">���⿹��</td>
    <td align="center" width="15%">ȯ��ä��</td>
    <td align="center" width="15%">MMDA</td>
    <td align="center" width="16%">���༺�����հ�</td>
    <td align="center" width="15%">���հ�</td>
  </tr>
  <% for (int y=0; thisAvgList != null && y<thisAvgList.size(); y++) {
       CommonEntity data = (CommonEntity)thisAvgList.get(y);
  %>
  <tr> 
    <td align="center"><%=y+1%>��</td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("GONGGUM_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("JEONGGI_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("HWAN_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("MMDA_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("SAVE_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(data.getString("TOT_AMT"))%></td>
  </tr>
  <% } %>
  <% for (int z=thisListSize; z<12; z++) { %>
  <tr> 
    <td align="center"><%=z+1%>��</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <% } %>
</table>
<div class="noprint">
<p>&nbsp;</p>
<% if (!"Y".equals(request.getParameter("excelFlag")) ) { %>
<table width="1000" border="0">
  <tr>
    <td align="right">
    <img src="../img/btn_excel.gif" alt="����" align="absmiddle" onclick="goExcel()" style="cursor:hand">
		<img src="../img/btn_print.gif" alt="�μ�" align="absmiddle" onclick="goPrint()" style="cursor:hand">
    </td>
  </tr>
</table>
<% } %>
</div>
</form>
</body>
</html>
