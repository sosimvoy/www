<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR050411.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݹ��� > �ڱݹ�����������ȸ-�������˾�
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  CommonEntity srpRepInfo  = (CommonEntity)request.getAttribute("page.mn05.srpRepInfo");
	CommonEntity srpRepMan  = (CommonEntity)request.getAttribute("page.mn05.srpRepMan");
  CommonEntity sealInfo  = (CommonEntity)request.getAttribute("page.mn05.sealInfo");

	int srpRepInfoSize = 0;
	if (srpRepInfo != null ) {
		srpRepInfoSize = srpRepInfo.size();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 12pt; color: #333333}
.num   {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.line  {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11pt; color: #333333; text-decoration: underline}

@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript" src="../js/base.js"></script>
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
<body bgcolor="#FFFFFF" topmargin="10" leftmargin="10">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/sm
sx.cab#Version=6,5,439,50"></object>
<%
if (srpRepInfo != null) { %>
<form name="sForm" method="post" action="IR050411.etax">
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr> 
    <td colspan="7" height="21">
      <table width="711" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
        <tr> 
          <td colspan="7" height="21"> 
            <table width="690" border="0" cellspacing="0" cellpadding="1" align="center">
              <tr> 
                <td height="60"> 
                  <div align="center"><b><font size="6">�� �� �� �� ��</font></b></div>
                </td>
              </tr>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="6" class="basic" align="center">
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225">&nbsp;</td>
              </tr>
              <tr> 
                <td width="484">������ȣ : <%=srpRepInfo.getString("M310_DOCUMENTNO")%></td>
                <td width="225"> 
                  <div align="right">������ : <%=TextHandler.formatDate(srpRepInfo.getString("M310_DATE"), "yyyyMMdd", "yyyy. MM. dd")%>&nbsp;&nbsp;&nbsp;</div>
                </td>
              </tr>
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225"> 
                  <div align="left"></div>
                </td>
              </tr>
              <tr> 
                <td width="484">���� : �泲�������û������</td>
                <td width="225"> 
                  <div align="left">&nbsp;</div>
                </td>
              </tr>
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
              <tr> 
                <td width="484">���� : <%=srpRepInfo.getString("M310_YEAROVER")%>�� �Ϲ�ȸ�� �����׿����� ��� �� <%=srpRepInfo.getString("M310_YEARINTO")%>�⵵ ����</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
              <tr> 
                <td colspan="2"><hr color="black"></hr></td>
              </tr>
              <tr> 
                <td colspan="2" height="50">
                  <p>&nbsp;</p>
                  <p>&nbsp;</p>
                  <div align="left"><span style="width:33"></span>���������� ����� ��61���� ������ �ǰ� <%=srpRepInfo.getString("M310_YEAROVER")%>�⵵ �Ϲ�ȸ�� �����׿����� ��� ���� <br><span style="width:33"></span>���� �ݾ��� <%=srpRepInfo.getString("M310_YEARINTO")%>�⵵�� ���Կ� �����ϰ��� �մϴ�.</div>
                </td>
              </tr>
              <tr> 
                <td colspan="2"> 
                  <div align="center"></div>
                </td>
              </tr>
              <tr>
                <td colspan="2"> 
                  <div align="left"><span style="width:33"></span><b>
									1. ���� : <%=TextHandler.formatDate(srpRepInfo.getString("M310_DATE"), "yyyyMMdd", "yyyy. MM. dd")%> <br>
									<span style="width:33"></span>
									2. �ݾ� : �� <%=TextHandler.formatNumber(srpRepInfo.getString("M310_INTOTAMT"))%>�� (��<%=srpRepInfo.getString("HAN_AMT")%>) ��.</b> </div>
                </td>
              </tr>
            </table>
            <p>&nbsp;</p>
            <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
              <tr> 
                <td height="60" colspan="3"> 
                  <p>&nbsp;</p>
                  <p>&nbsp;</p>
                  <div align="center"><br><font size="4"><b>��걤������ </b></font>
                  <% if ("Y".equals(srpRepMan.getString("M180_SIGNFINISHSTATE")) ) { %>
                  <img src="/report/seal/<%=sealInfo.getString("M340_FNAME")%>" width="60" height="60" align="absmiddle">
                  <% } %>
                  <br></div>
                </td>
              </tr>
							<% if (srpRepMan != null) { %>
              <tr> 
                <td height="25" width="210">
                  <p>&nbsp;</p>
                  <div align="left">�ֹ��� : <%=srpRepMan.getString("M180_TASKNAME")%> </div>
                </td>
                <td height="25" width="312"> 
                  <p>&nbsp;</p>
                  ���԰������繫�� : <%=srpRepMan.getString("M180_MIDDLENAME")%> </td>
                <td height="30" width="162"> 
                  <p>&nbsp;</p>
                  �������� : <%=srpRepMan.getString("M180_UPPERNAME")%></td>
              </tr>
							<% } %>
            </table>
						
          <p>&nbsp;</p></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
<% } %>
<br>
<div class="noprint">
<div align="right">
<img src="../img/btn_print.gif" alt="�μ�" onClick="goPrint()" style="cursor:hand">
<img src="../img/btn_close.gif" alt="�ݱ�" onClick="self.close()" style="cursor:hand"><span style="width:30"></span>
</div>
</div>
</body>
</html>
