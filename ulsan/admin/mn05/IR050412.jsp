<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050412.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �ڱ�(��)������������ȸ-��������(�˾�)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  List<CommonEntity> cityBaeList1 = (List<CommonEntity>)request.getAttribute("page.mn05.cityBaeList1");

  List<CommonEntity> cityBaeList2 = (List<CommonEntity>)request.getAttribute("page.mn05.cityBaeList2");
%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
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
  if (cityBaeList1 != null) {
%>
<body bgcolor="#FFFFFF" topmargin="20" leftmargin="10">
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
                <td height="60" style="font-size:16pt"> 
                  <div align="center"><b><%=request.getParameter("fis_year")%> �Ϲ�ȸ�� �ڱ� <% if ("ED06".equals(request.getParameter("doc_code")) ) { %>��<% } %>���� <%if ("ED00".equals(request.getParameter("doc_code")) )  {%>�� ����� <%}%>����</b></div>
                </td>
              </tr>
            </table>
            <p>&nbsp;</p>
            <table width="720" border="0" cellspacing="0" cellpadding="1" class="basic">
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
              <tr height="30"> 
							  <td rowspan="2" width="13%" align="center">����</td>
                <td colspan="2" width="26%" align="center">����</td>
                <td colspan="3" width="37%" align="center">�����ѵ���</td>
                <td colspan="2" width="24%" align="center">�ܾ�</td>
              </tr>
              <tr height="30"> 
                <td align="center">��������</td>
                <td align="center">������</td>
                <td align="center">�������</td>
                <td align="center">��ȸ����</td>
                <td align="center">�հ�</td>
                <td align="center">�����</td>
                <td align="center">������</td>
              </tr>
              <% if ("ED00".equals(request.getParameter("doc_code")) ) { %>
              <tr height="30"> 
							  <td align="center"><b>�� ��</b></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("M110_BUDGETAMT") + cityBaeList2.get(0).getLong("M110_BUDGETAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("M110_BUDGETALLOTAMT") + cityBaeList2.get(0).getLong("M110_BUDGETALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("M110_ORIALLOTAMT") + cityBaeList2.get(0).getLong("M110_ORIALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("ALLOTAMT") + cityBaeList2.get(0).getLong("ALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("TOT_AMT") + cityBaeList2.get(0).getLong("TOT_AMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("YESAN") + cityBaeList2.get(0).getLong("YESAN"))%></td>
                <td align="right"><%=TextHandler.formatNumber(cityBaeList1.get(0).getLong("BAE") + cityBaeList2.get(0).getLong("BAE"))%></td>
              </tr>
              <% } %>
              <% for (int i=0; cityBaeList1 != null && i < cityBaeList1.size(); i++) {
                   CommonEntity baeInfo = (CommonEntity) cityBaeList1.get(i); %>
              <tr height="30"> 
							  <td align="center"><%=baeInfo.getString("M350_PARTNAME")%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_BUDGETAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_BUDGETALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_ORIALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("ALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("TOT_AMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("YESAN"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("BAE"))%></td>
              </tr>
              <% } %>
            <%if ("ED00".equals(request.getParameter("doc_code")) )  {%>
              <% for (int i=0; cityBaeList2 != null && i < cityBaeList2.size(); i++) {
                   CommonEntity baeInfo = (CommonEntity) cityBaeList2.get(i); %>
              <tr height="30"> 
							  <td align="center"><%=baeInfo.getString("M350_PARTNAME")%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_BUDGETAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_BUDGETALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("M110_ORIALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("ALLOTAMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("TOT_AMT"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("YESAN"))%></td>
                <td align="right"><%=TextHandler.formatNumber(baeInfo.getString("BAE"))%></td>
              </tr>
              <% } %>
            </table>
            <% }%>
          </td>
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
