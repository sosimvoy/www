<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR060511.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �ڱݿ�Ź�������˾�
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  List<CommonEntity> bankDepViewList  = (List<CommonEntity>)request.getAttribute("page.mn06.bankDepViewList");
	CommonEntity totAmt  = (CommonEntity)request.getAttribute("page.mn06.totAmt");
  CommonEntity sealInfo  = (CommonEntity)request.getAttribute("page.mn06.sealInfo");
  CommonEntity manager  = (CommonEntity)request.getAttribute("page.mn06.manager");

	int bankDepViewListSize = 0;
	if (bankDepViewList != null ) {
		bankDepViewListSize = bankDepViewList.size();
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
  function goList() {
		var form = document.sForm;
		window.open('IR060512.etax', 'baePop', 'height=600,width=760,top=10,left=200,scrollbars=yes');
		form.cmd.value="bankDepDetailList";
		form.action = "IR060512.etax";
		form.target = "baePop";
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
		factory.printing.Print(true) // ����ϱ� 
	}
</script>
</head>

<body bgcolor="#FFFFFF" topmargin="20" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<% if (bankDepViewListSize > 0) { %>
<form name="sForm" method="post" action="IR060512.etax">
<input type="hidden" name="cmd" value="bankDepDetailList">
<input type="hidden" name="reg_date" value="<%=request.getParameter("reg_date")%>">
<input type="hidden" name="doc_no" value="<%=request.getParameter("doc_no")%>">
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
                <td width="484">������ȣ : <%=request.getParameter("doc_no")%></td>
                <td width="225"> 
                  <div align="left">������ : <%=TextHandler.formatDate(request.getParameter("reg_date"), "yyyyMMddd", "yyyy. MM. dd")%></div>
                </td>
              </tr>
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225"> 
                  <div align="left"></div>
                </td>
              </tr>
              <tr> 
                <td width="484">���� : �泲���� ����û ������</td>
                <td width="225"> 
                  <div align="left">�� �� : ��걤������ </div>
                </td>
              </tr>
              <tr> 
                <td width="484">&nbsp;</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
              <tr> 
                <td width="484">���� : <%=request.getParameter("fis_year")%>�� �Ϲ�ȸ�� �����ڱ� ��Ź</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
							<tr>
							  <td colspan="2"><hr color="black"></td>
							</tr>
              <tr> 
                <td colspan="2" height="50"> 
                  <div align="left"><span style="width:43"></span><%=request.getParameter("fis_year")%>�� �Ϲ�ȸ�� ��ڱ� �� �����ڱ��� ������ ���� ��Ź �ϰ��� �Ͽ��� ó���Ͽ� �ֽñ� �ٶ��ϴ�.</div>
                </td>
              </tr>
              <tr> 
                <td colspan="2"> 
                  <div align="left"> 
                    <table width="500" border="0" cellspacing="1" cellpadding="3" class="num">
                      <tr> 
                        <td><span style="width:43"></span> 1. ��Ź�ݾ� : �� <%=TextHandler.formatNumber(totAmt.getString("TOT_AMT"))%>��(��<%=totAmt.getString("HANGUL_AMT")%>) </td>
                      </tr>
                      <tr> 
                        <td><span style="width:43"></span> 2. ��Ź����</td>
                      </tr>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
            <table width="690" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="num" align="center">
              <tr> 
                <td width="95" height="34"> 
                  <div align="center">��Ź����</div>
                </td>
                <td colspan="2" height="34"> 
                  <div align="center">��Ź��</div>
                </td>
                <td width="159" height="34"> 
                  <div align="center">������</div>
                </td>
                <td colspan="2" height="34"> 
                  <div align="center">��Ź�Ⱓ</div>
                </td>
                <td width="74" height="34"> 
                  <div align="center">����(%)</div>
                </td>
              </tr>
							<% String due_date = "";
							   for (int i=0; i<bankDepViewListSize; i++) { 
								   CommonEntity rowData = (CommonEntity)bankDepViewList.get(i);
									 String state = rowData.getString("M120_DEPOSITTYPE");
									 if ("G1".equals(state) ) {
										 due_date = rowData.getString("M120_DEPOSITDATE") + " ����";
									 } else if ("".equals(rowData.getString("M120_DEPOSITDATE")) ) {
                     due_date = "";
                   } else if ("G2".equals(state) || "G3".equals(state)){
										 due_date = rowData.getString("M120_DEPOSITDATE") + " ��";
									 }
							%>
              <tr> 
                <td width="95" height="6"> 
                  <div align="center"><%=rowData.getString("DEPOSIT_NAME")%></div>
                </td>
                <td colspan="2" height="6"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M120_DEPOSITAMT"))%></div>
                </td>
                <td width="159" height="6"> 
                  <div align="center"><%=TextHandler.formatDate(rowData.getString("M120_MANGIDATE"), "yyyyMMdd", "yyyy. MM. dd")%></div>
                </td>
                <td colspan="2" height="35"> 
                  <div align="center"><%=due_date%></div>
                </td>
                <td width="74" height="6"> 
                  <div align="right"><%=rowData.getString("M120_INTERESTRATE")%></div>
                </td>
              </tr>
							<% } %>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic">
              <tr> 
                <td height="60" colspan="3"> 
                  <div align="left"> 
                    <table width="500" border="0" cellspacing="1" cellpadding="3" class="num">
                      <tr> 
                        <td><span style="width:52"></span> 3. �� Ź �� : <%=TextHandler.formatDate(request.getParameter("reg_date"), "yyyyMMddd", "yyyy. MM. dd")%> </td>
                      </tr>
                      <tr> 
                        <td><span style="width:52"></span> 4. ���ڼ��� : �Ϲ�ȸ�� ���ڼ������� ó��. ��. </td>
                      </tr>
                    </table>
                    <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
                      <tr> 
                        <td height="60" colspan="3"> 
                          <div align="center"><br><font size="4"><b>��걤������ </b></font>
                          <% if ("Y".equals(manager.getString("M180_SIGNFINISHSTATE")) ) { %>
                          <img src="/report/seal/<%=sealInfo.getString("M340_FNAME")%>" width="60" height="60" align="absmiddle">
                          <% } %>
                          <br></div>
                        </td>
                      </tr>
                      <tr> 
                        <td height="25" width="210"> 
                          <div align="left">�ֹ���: <%=manager.getString("M180_TASKNAME")%> </div>
                        </td>
                        <td height="25" width="312"> ���԰������繫�� : <%=manager.getString("M180_MIDDLENAME")%> </td>
                        <td height="30" width="162">�������� : <%=manager.getString("M180_UPPERNAME")%></td>
                      </tr>
                    </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<div class="noprint">
<div align="right">
      <img src="../img/btn_banklist.gif" alt="��Ź����" onClick="goList()" align="absmiddle" style="cursor:hand">
			<img src="../img/btn_print.gif" alt="�μ�" onClick="goPrint()" align="absmiddle" style="cursor:hand">
      <img src="../img/btn_close.gif" alt="�ݱ�" onClick="self.close()" align="absmiddle" style="cursor:hand">
			<span style="width:25"></span>
</div>
</div>
</form>
<p>&nbsp;</p>
<p><span class="count"></span> </p>
<% } %>
</body>
</html>