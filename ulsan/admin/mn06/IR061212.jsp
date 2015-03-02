<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR061212.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ڱ�������������ȸ - �ڱ����⳻��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  List<CommonEntity> inchulDetailList = (List<CommonEntity>)request.getAttribute("page.mn06.inchulDetailList");
%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11pt; color: #333333}
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
		factory.printing.leftMargin = 2.0; // ���� ���� ������  
		factory.printing.topMargin = 2.0; // �� ���� ������  
		factory.printing.rightMargin = 1.0; // ������ ���� ������  
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		factory.printing.Print(true) // ����ϱ� 
	}
</script>
</head>
<%
  if (inchulDetailList != null) {
%>
<body bgcolor="#FFFFFF" topmargin="20" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<table width="713" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="7" height="21">
      <table width="711" border="0" cellspacing="0" cellpadding="0" class="basic">
        <tr> 
          <td colspan="7" height="21"> 
            <table width="690" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="60" style="font-size:17pt"> 
                  <div align="center"><b><%=TextHandler.formatDate(request.getParameter("reg_date"), "yyyyMMdd", "yyyy")%> �Ϲ�ȸ�� �ڱ� ���� ����</b></div>
                </td>
              </tr>
            </table>
            <p>&nbsp;</p>
            <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic">
              <tr>
							  <td height="25"> 
                  <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%=TextHandler.formatDate(request.getParameter("reg_date"), "yyyyMMdd", "yyyy. MM. dd")%></div>
                </td>
              </tr>
            </table>
            <table width="690" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic" align="center">
              <tr height="35"> 
							  <td width="17%"> 
                  <div align="center">��Ź����</div>
                </td>
                <td width="18%"> 
                  <div align="center">�����</div>
                </td>
                <td width="15%"> 
                  <div align="center">��Ź����</div>
                </td>
                <td width="12%"> 
                  <div align="center">��Ź�Ⱓ</div>
                </td>
								<td width="10%"> 
                  <div align="center">����(%)</div>
                </td>
                <td width="16%"> 
                  <div align="center">����(��)</div>
                </td>
                <td width="12%"> 
                  <div align="center">mmda����</div>
                </td>
              </tr>
							<% String due_date = "";
	               for (int i=0; i<inchulDetailList.size(); i++) { 
	                 CommonEntity rowData = inchulDetailList.get(i);
									 String state = rowData.getString("M130_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M130_DEPOSITDATE") + " ����";
									 } else if ("".equals(rowData.getString("M130_DEPOSITDATE")) ) {
                     due_date = "";
                   } else if ("G2".equals(state) || "G3".equals(state)){
										 due_date = rowData.getString("M130_DEPOSITDATE") + " ��";
									 }
	            %>
              <tr> 
							  <td height="6">
									<div align="center"><%=rowData.getString("DEPOSIT_NAME")%></div>
                </td>
                <td height="6"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M130_INCHULAMT"))%></div>
                </td>
                <td height="6"> 
                  <div align="center"><%=TextHandler.formatDate(rowData.getString("M130_SINKYUDATE"), "yyyyMMdd", "yyyy.MM.dd")%></div>
                </td>
                <td height="35"> 
                  <div align="right"><%=due_date%></div>
                </td>
                <td height="6"> 
                  <div align="right"><%=rowData.getString("M130_INTERESTRATE")%></div>
                </td>
                <td height="6"> 
                  <div align="right">
                  <% if ("0".equals(rowData.getString("M130_CANCELINTEREST")) || "".equals(rowData.getString("M130_CANCELINTEREST"))) { %>
                  &nbsp;
                  <% } else { %>
                  <%=TextHandler.formatNumber(rowData.getString("M130_CANCELINTEREST"))%>
                  <% } %>
                  </div>
                </td>
                <td height="6"> 
                  <div align="center">
                  <% if ("Y".equals(rowData.getString("M130_MMDA_CANCEL_YN")) ) { %>
                  ����
                  <% } %>
                  </div>
                </td>
              </tr>
							<% } %>
            </table>
          <p>&nbsp;</p></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<div class="noprint">
<div align="right">
<img src="../img/btn_print.gif" alt="�μ�" onClick="goPrint()" align="absmiddle" style="cursor:hand">
<img src="../img/btn_close.gif" alt="�ݱ�" onClick="self.close()" style="cursor:hand"><span style="width:40"></span>
</div>
</div>
<p><span class="count"></span> </p>
</body>
<% } %>
</html>
