<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050413.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �ڱ�(��)������������ȸ-������Ȳ(�˾�)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  List<CommonEntity> boyuReportList1 = (List<CommonEntity>)request.getAttribute("page.mn05.boyuReportList1");

  List<CommonEntity> boyuReportList2 = (List<CommonEntity>)request.getAttribute("page.mn05.boyuReportList2");
%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11pt; color: #333333}
.etc {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}

@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript">
	function goPrint() {
    factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = true; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 1.0; // ���� ���� ������  
		factory.printing.topMargin = 2.0; // �� ���� ������  
		factory.printing.rightMargin = 1.0; // ������ ���� ������  
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		factory.printing.Print(true) // ����ϱ� 
	}
</script>
</head>
<%
  if (boyuReportList1 != null) {
%>
<body bgcolor="#FFFFFF" topmargin="30" leftmargin="10">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<center>
<table width="730" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="7" height="21">
      <table width="727" border="0" cellspacing="0" cellpadding="0" class="basic">
        <tr> 
          <td colspan="7" height="21"> 
            <table width="720" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="80" style="font-size:18pt"> 
                <br>
                  <div align="center"><b><%=request.getParameter("fis_year")%> �Ϲ�ȸ�� �ڱ� <% if ("ED06".equals(request.getParameter("doc_code")) ) { %>��<% } %>������ ������Ȳ</b></div>
                </td>
              </tr>
            </table>
            <br>
            <table width="720" border="0" cellspacing="0" cellpadding="1" class="etc">
              <tr>
							  <td height="25"> 
                  <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy. MM. dd")%></div>
                </td>
                <td height="25"> 
                  <div align="right">(����:õ��)</div>
                </td>
              </tr>
            </table>
            <table width="720" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic" align="center">
              <tr> 
							  <td width="17%"> 
                  <div align="center"><b>�� �� ��</b></div>
                </td>
                <td width="26%"> 
                  <div align="center"><b>���ݺ����ܾ�</b></div>
                </td>
                <td width="27%" height="30"> 
                  <div align="center"><b>��ȸ������</b></div>
                </td>
                <td width="30%"> 
                  <div align="center"><b>���ݺ�����</b></div>
                </td>
              </tr>
							<% for (int i=0; i<boyuReportList1.size(); i++) { 
	                 CommonEntity rowData = boyuReportList1.get(i);
	            %>
              <% if ("67".equals(rowData.getString("M360_ACCCODE"))) { %>
              <tr> 
							  <td>
                  <div align="center">���μҹ漭</div>
                </td>
                <td> 
                  <div align="right">0</div>
                </td>
                <td> 
                  <div align="right">0</div>
                </td>
                <td height="30"> 
                  <div align="right"><b>0</b></div>
                </td>
              </tr>
              <% } %>
              <tr> 
							  <td>
                  <div align="center"><%=rowData.getString("M360_ACCNAME")%></div>
                </td>
                <td> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M170_OFFICIALDEPOSITJANAMT"))%></div>
                </td>
                <td> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT"))%></div>
                </td>
                <td height="30"> 
                  <div align="right"><b><%=TextHandler.formatNumber(rowData.getString("M_BOYU"))%></b></div>
                </td>
              </tr>
							<% } %>
            </table>

            <% if ("ED00".equals(request.getParameter("doc_code")) ) { %>
            <table width="720" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="80" style="font-size:18pt"> 
                <br>
                  <div align="center"><b><%=request.getParameter("fis_year")%> �Ϲ�ȸ�� �ڱ� ������� ������Ȳ</b></div>
                </td>
              </tr>
            </table>
            <br>
            <table width="720" border="0" cellspacing="0" cellpadding="1" class="etc">
              <tr>
							  <td height="25"> 
                  <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy. MM. dd")%></div>
                </td>
                <td height="25"> 
                  <div align="right">(����:õ��)</div>
                </td>
              </tr>
            </table>
            <table width="720" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic" align="center">
              <tr> 
							  <td width="17%"> 
                  <div align="center"><b>�� �� ��</b></div>
                </td>
                <td width="26%"> 
                  <div align="center"><b>���ݺ����ܾ�</b></div>
                </td>
                <td width="27%" height="30"> 
                  <div align="center"><b>��ȸ������</b></div>
                </td>
                <td width="30%"> 
                  <div align="center"><b>���ݺ�����</b></div>
                </td>
              </tr>
							<% for (int i=0; i<boyuReportList2.size(); i++) { 
	                 CommonEntity rowData = boyuReportList2.get(i);
	            %>
              <% if ("67".equals(rowData.getString("M360_ACCCODE"))) { %>
              <tr> 
							  <td>
                  <div align="center">���μҹ漭</div>
                </td>
                <td> 
                  <div align="right">0</div>
                </td>
                <td> 
                  <div align="right">0</div>
                </td>
                <td height="30"> 
                  <div align="right"><b>0</b></div>
                </td>
              </tr>
              <% } %>
              <tr> 
							  <td>
                  <div align="center"><%=rowData.getString("M360_ACCNAME")%></div>
                </td>
                <td> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M170_OFFICIALDEPOSITJANAMT"))%></div>
                </td>
                <td> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M100_ALLOTAMT"))%></div>
                </td>
                <td height="30"> 
                  <div align="right"><b><%=TextHandler.formatNumber(rowData.getString("M_BOYU"))%></b></div>
                </td>
              </tr>
							<% } %>
            </table>
            <% } %>
          <p>&nbsp;</p></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</center>
<div class="noprint">
<div align="right">
<img src="../img/btn_print.gif" alt="�μ�" onClick="goPrint()" style="cursor:hand">
<img src="../img/btn_close.gif" alt="�ݱ�" onClick="self.close()" style="cursor:hand"><span style="width:40"></span></div>
</div>
<p><span class="count"></span> </p>
</body>
<% } %>
</html>