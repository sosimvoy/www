<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR061910.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �����ڱ���Ȳ��ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%
  List<CommonEntity> dailyMoneyList = (List<CommonEntity>)request.getAttribute("page.mn06.dailyMoneyList");
  String SucMsg = (String)request.getAttribute("page.mn06.SucMsg");
  if (SucMsg == null) {
    SucMsg = "";
  }
  
  int listSize = 0;
  if (dailyMoneyList != null) {
    listSize = dailyMoneyList.size();
  }
  String accName = "";

  if(request.getParameter("flag").equals("Y")){
    response.setContentType("application/vnd.ms-excel;charset=euc-kr");
    response.setHeader("Content-Disposition", "inline; filename=�����ڱ���Ȳ��ȸ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
    response.setHeader("Content-Description", "JSP Generated Data");
  }	 
%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���t</title>
<%if(request.getParameter("flag").equals("Y")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
<%} else {%>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<% } %>
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.big {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 20px; color: #333333}
.bottom {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 16px; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}

@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">

  function init(){
    <% if (listSize > 0 ) {%>
    cellMergeChk(document.all.dataList, 1, 0);
    <%}%>
  }

  function goExcel() {
    var form = document.sForm;
    form.flag.value = "Y";
    eSubmit(form);
  }

  function goPrint() {
    factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = true; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 2.0; // ���� ���� ������  
		factory.printing.topMargin = 2.0; // �� ���� ������  
		factory.printing.rightMargin = 1.0; // ������ ���� ������  
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		factory.printing.Print(true); // ����ϱ� 
	}
</script>
</head>

<body bgcolor="#FFFFFF" topmargin="10" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR062011.etax">
<input type="hidden" name="cmd" value="dailyMoneyList">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="fis_year" value="<%=request.getParameter("fis_year")%>">
<input type="hidden" name="flag" value="">
<br>
<table width="680" border="0" cellspacing="0" cellpadding="1">
  <tr> 
    <td class="big" colspan="5" align="center"> 
      <b><%=request.getParameter("fis_year")%> ��걤���� ���� �ڱ� ��Ȳ</b>
    </td>
  </tr>
</table>
<table width="680" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr>
    <td width="480" align="left"><%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy-MM-dd")%><br>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td align="right"> 
      ����(��)
    </td>
  </tr>
</table>
<table width="700" border="0" cellspacing="2" cellpadding="0">
  <tr>
    <td>
      <table id="dataList" width="680" border="1" cellspacing="0" cellpadding="1" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
        <tr> 
          <td colspan="2" align="center" width="34%"> 
            <b>����</b>
          </td>
          <td align="center"> 
            <b>�����ܾ�</b>
          </td>
          <td align="center"> 
            <b>�����ܾ�</b>
          </td>
          <td align="center"> 
            <b>��������</b>
          </td>
        </tr>
        <% for (int i=0; dailyMoneyList != null && i < dailyMoneyList.size(); i++) { 
             CommonEntity rowData = (CommonEntity)dailyMoneyList.get(i);
             accName = rowData.getString("M360_ACCNAME");
             if ("".equals(TextHandler.replace(accName, " ", "")) 
               && "".equals(TextHandler.replace(rowData.getString("GUBUN"), " ", "")) ) {
               accName = "�� ��";
             } else if ("".equals(TextHandler.replace(accName, " ", "")) 
               && !"".equals(TextHandler.replace(rowData.getString("GUBUN"), " ", "")) ) {
               accName = "�� ��";
             }

        %>
        <tr> 
          <td width="19"> 
            <%=rowData.getString("GUBUN")%>
          </td>
          <td> 
            <%=accName%>
          </td>
          <td align="right"> 
            <%=TextHandler.formatNumber(rowData.getString("YESTERDAY_AMT"))%>
          </td>
          <td align="right"> 
            <%=TextHandler.formatNumber(rowData.getString("TODAY_AMT"))%>
          </td>
          <td align="right"> 
            <%=TextHandler.formatNumber(rowData.getString("YT_AMT"))%>
          </td>
        </tr>
        <% } %>
      </table>
    </td>
  </tr>
</table>
<br>
<table width="700" border="0" cellspacing="0" cellpadding="2" class="basic">
  <tr> 
    <td width="680" class="bottom" align="right" colspan="5"> 
      <b>�泲�������û�����ñݰ�</b>
    </td>
  </tr>
</table>
<div class="noprint">
<p>&nbsp;</p>
<% if (!request.getParameter("flag").equals("Y")) { %>
<table width="700" border="0" cellspacing="0" cellpadding="4" class="basic">
  <tr> 
    <td width="690" class="bottom" align="right"> 
      <img src="../img/btn_excel.gif" alt="����" style="cursor:hand" onClick="goExcel()" align="absmiddle">
      <img src="../img/btn_print.gif" alt="�μ�" style="cursor:hand" onClick="goPrint()" align="absmiddle">
      <img src="../img/btn_close.gif" alt="�ݱ�" style="cursor:hand" onClick="self.close()" align="absmiddle">
    </td>
  </tr>
</table>
<% } %>
</div>
</body>
</html>
<% if (!"".equals(SucMsg)) { %>
<script>
alert("<%=SucMsg%>");
self.close();
</script>
<% } %>
