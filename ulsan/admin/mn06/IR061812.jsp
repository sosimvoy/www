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
    response.setHeader("Content-Disposition", "inline;filename=�ݰ�����ܾ�ǥ(�Ⱓ��)_"+request.getParameter("start_date")+"_"+request.getParameter("end_date")+".xls"); 
    response.setHeader("Content-Description", "JSP Generated Data");
    charMarkForExcel = "`"; // ���ڸ� �״�� ǥ���ϱ� ����
  }
  List<CommonEntity> giganList = (List<CommonEntity>)request.getAttribute("page.mn06.giganList"); //�Ⱓ�� ������ȸ
  CommonEntity giganInfo = (CommonEntity)request.getAttribute("page.mn06.date"); //�Ⱓ

  int giganListSize = 0;

  if (giganList != null) {
    giganListSize = giganList.size();
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
<script language="javascript" src="../js/rowspan.js"></script>
<script language="javascript">

  function init(){
    <% if (giganListSize > 0 ) {%>
    cellMergeChk(document.all.dataList, 2, 0);
    <%}%>
  }
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
    form.action = "IR061812.etax";
    form.cmd.value = "avgMoneyList";
		eSubmit(form);
	}
</script>
</head>

<body bgcolor="#FFFFFF" topmargin="10" leftmargin="10">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR061811.etax">
<input type="hidden" name="cmd" value="avgMoneyList">
<input type="hidden" name="start_date" value="<%=request.getParameter("start_date")%>">
<input type="hidden" name="end_date" value="<%=request.getParameter("end_date")%>">
<input type="hidden" name="excelFlag" value="">
<% if (giganListSize > 0) { %>
<table width="713" border="0" cellspacing="0" cellpadding="1" class="line">
  <tr style="font-weight:bolder"> 
    <td height="60" colspan="5" align="center">��걤���� �ݰ� ����ܾ� ��Ȳ</td>
  </tr>
</table>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td colspan="2">(�泲���� ����û����)</td>
    <td align="center" colspan="2">(<%=TextHandler.formatDate(giganInfo.getString("start_date"), "yyyyMMdd", "yyyy-MM-dd")%> ~ <%=TextHandler.formatDate(giganInfo.getString("end_date"), "yyyyMMdd", "yyyy-MM-dd")%>)</td>
    <td align="right">(����:��)</td>
  </tr>
</table>
<table id="dataList" width="713" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr> 
    <td colspan="2" rowspan="2" align="center" width="40%">ȸ���</td>
    <td colspan="3" align="center" width="60%">�ܾױ���</td>
  </tr>
  <tr> 
    <td width="163" align="center" width="20%">�Ұ�</td>
    <td width="161" align="center" width="20%">���⿹�� ��</td>
    <td width="161" align="center" width="20%">���ݿ���</td>
  </tr>
  <% for (int i=0; i<giganListSize; i++) { 
       CommonEntity rowData = (CommonEntity)giganList.get(i);
  %>
  <tr> 
    <td width="10%"><%=rowData.getString("TMPNAME")%></td>
    <td align="left" width="30%"><%=rowData.getString("TMPACCNAME")%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("TOT_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("SAVE_AMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(rowData.getString("GONGGUM_AMT"))%></td>
  </tr>
  <% } %>
</table>
<div class="noprint">
<p>&nbsp;</p>
<% if (!"Y".equals(request.getParameter("excelFlag")) ) { %>
<table width="713" border="0">
  <tr>
    <td align="right">
    <img src="../img/btn_excel.gif" alt="����" align="absmiddle" onclick="goExcel()" style="cursor:hand">
		<img src="../img/btn_print.gif" alt="�μ�" align="absmiddle" onclick="goPrint()" style="cursor:hand">
    </td>
  </tr>
</table>
<% } %>
</div>
<% } %>
</form>
</body>
</html>
