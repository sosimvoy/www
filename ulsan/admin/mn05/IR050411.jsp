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
  CommonEntity bonData1  = (CommonEntity)request.getAttribute("page.mn05.bonData1");
  CommonEntity bonData2  = (CommonEntity)request.getAttribute("page.mn05.bonData2");
  List<CommonEntity> cityBaeList1  = (List<CommonEntity>)request.getAttribute("page.mn05.cityBaeList1");

  List<CommonEntity> cityBaeList2  = (List<CommonEntity>)request.getAttribute("page.mn05.cityBaeList2");
	CommonEntity manager  = (CommonEntity)request.getAttribute("page.mn05.manager");
  CommonEntity sealInfo  = (CommonEntity)request.getAttribute("page.mn05.sealInfo");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 10pt; color: #333333}
.num   {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
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
    form.cmd.value="bankReportList";
		form.action = "IR050412.etax";
		window.open('IR050412.etax', 'baePop', 'height=600,width=760,top=10,left=200,scrollbars=yes');
		
		form.target = "baePop";
		eSubmit(form);
  }

  function goBoyu() {
		var form = document.sForm;
    form.cmd.value="bankReportBoyu";
		form.action = "IR050413.etax";
		window.open('IR050413.etax', 'boyu', 'height=600,width=760,top=10,left=200,scrollbars=yes');
		
		form.target = "boyu";
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
<%
if (bonData1 != null) { %>
<form name="sForm" method="post" action="IR050411.etax">
<input type="hidden" name="cmd" value="bankReportList">
<input type="hidden" name="doc_no" value="<%=request.getParameter("doc_no")%>">
<input type="hidden" name="fis_year" value="<%=request.getParameter("fis_year")%>">
<input type="hidden" name="report_gubun" value="<%=request.getParameter("report_gubun")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("allot_date")%>">
<input type="hidden" name="doc_code" value="<%=manager.getString("M180_DOCUMENTCODE")%>">
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
                  <div align="right">������ : <%=TextHandler.formatDate(request.getParameter("allot_date"), "yyyyMMdd", "yyyy. MM. dd")%>&nbsp;&nbsp;&nbsp;</div>
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
                <td width="484">���� : <%=request.getParameter("fis_year")%>�� �Ϲ�ȸ�� <% if ("ED05".equals(manager.getString("M180_DOCUMENTCODE")) ) { %>�ڱ� ���� <% } else if ("ED06".equals(manager.getString("M180_DOCUMENTCODE")) ) { %>�ڱ� ����� <% } else if ("ED00".equals(manager.getString("M180_DOCUMENTCODE")) ) {%>�ڱ� ���� �� ����� <% } %>����</td>
                <td width="225"> 
                  <div align="right"></div>
                </td>
              </tr>
              <tr> 
                <td colspan="2"><hr color="black" border="1px"></hr></td>
              </tr>
              <tr> 
                <td colspan="2" height="50">
                  <p>&nbsp;</p>
                  <p>&nbsp;</p>
                  <div align="left"><span style="width:33"></span><%=request.getParameter("fis_year")%>�� �Ϲ�ȸ�� <% if ("ED05".equals(manager.getString("M180_DOCUMENTCODE")) ) { %>�ڱ� ���� <% } else if ("ED06".equals(manager.getString("M180_DOCUMENTCODE")) ) { %>�ڱ� ����� <% } else if ("ED00".equals(manager.getString("M180_DOCUMENTCODE")) ) {%>�ڱ� ���� �� ����� <% } %> ������ ������ ���� �����ϴ� ó���Ͽ� �ֽñ� 
                    �ٶ��ϴ�.</div>
                </td>
              </tr>
              <tr> 
                <td colspan="2"> 
                  <div align="center"></div>
                </td>
              </tr>
              <tr>
                <td colspan="2"> 
                  <b><font size="4">
									<% if ("ED05".equals(manager.getString("M180_DOCUMENTCODE")) ) { %>
									�� �ڱݹ����� : �� <%=TextHandler.formatNumber(bonData1.getString("ALLOTAMT")+"000")%>�� 
                    (��<%=bonData1.getString("HANGUL_AMT")%>)
									<% } else if ("ED06".equals(manager.getString("M180_DOCUMENTCODE")) ) { %>
									�� �ڱ�������� : �� <%=TextHandler.formatNumber(bonData1.getString("ALLOTAMT")+"000")%>�� 
                    (��<%=bonData1.getString("HANGUL_AMT")%>)
									<% } else { %>
                  �� �ڱ�(��)������ : �� <%=TextHandler.formatNumber((bonData1.getLong("ALLOTAMT")+bonData2.getLong("ALLOTAMT")) * 1000)%>�� 
                    (��<%=bonData2.getString("REHANGUL_AMT")%>)
                  <% } %>
									</font></b>
                </td>
              </tr>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="1" class="num">
              <tr> 
                <td height="25"> 
                  <div align="right">(����:õ��)</div>
                </td>
              </tr>
            </table>
            <table width="690" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic" align="center">
              <tr>
                <td rowspan="2" align="center">�� ��</td>
                <td colspan="2"> 
                  <div align="center">����</div>
                </td>
                <td colspan="3" height = "34"> 
                  <div align="center">�����ѵ���</div>
                </td>
                <td colspan="2"> 
                  <div align="center">�ܾ�</div>
                </td>
              </tr>
              <tr> 
                <td height = "34"> 
                  <div align="center">��������</div>
                </td>
                <td width="76" height = "34"> 
                  <div align="center">������</div>
                </td>
                <td height = "34"> 
                  <div align="center">�������</div>
                </td>
                <td width="87" height = "34"> 
                  <div align="center">��ȸ����</div>
                </td>
                <td width="87" height = "34"> 
                  <div align="center">�հ�</div>
                </td>
                <td width="87" height = "34"> 
                  <div align="center">�����</div>
                </td>
                <td width="87" height = "34"> 
                  <div align="center">������</div>
                </td>
              </tr>
              <% if ("ED00".equals(manager.getString("M180_DOCUMENTCODE")) ) { %>
              <tr>
                <td height = "34"> 
                  <div align="center">�� ��</div>
                </td>
                <td height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(bonData1.getLong("M110_BUDGETAMT")+bonData2.getLong("M110_BUDGETAMT"))%></div>
                </td>
                <td width="76" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(bonData1.getLong("M110_BUDGETALLOTAMT")+bonData2.getLong("M110_BUDGETALLOTAMT"))%></div>
                </td>
                <td height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(bonData1.getLong("M110_ORIALLOTAMT")+bonData2.getLong("M110_ORIALLOTAMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(bonData1.getLong("ALLOTAMT")+bonData2.getLong("ALLOTAMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(bonData1.getLong("TOT_AMT")+bonData2.getLong("TOT_AMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(bonData1.getLong("YESAN")+bonData2.getLong("YESAN"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(bonData1.getLong("BAE")+bonData2.getLong("BAE"))%></div>
                </td>
              </tr>
              <% } %>
              <% for (int i=0; cityBaeList1 !=null && i<3; i++) { 
                   CommonEntity rowData = (CommonEntity)cityBaeList1.get(i);
              %>
              <tr>
                <td height = "34"> 
                  <div align="center"><%=rowData.getString("M350_PARTNAME")%></div>
                </td>
                <td height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M110_BUDGETAMT"))%></div>
                </td>
                <td width="76" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M110_BUDGETALLOTAMT"))%></div>
                </td>
                <td height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M110_ORIALLOTAMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("ALLOTAMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("TOT_AMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("YESAN"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("BAE"))%></div>
                </td>
              </tr>
              <% } %>
              <% if ("ED00".equals(manager.getString("M180_DOCUMENTCODE")) ) { %>
              <% for (int i=0; cityBaeList2 !=null && i<3; i++) { 
                   CommonEntity rowData = (CommonEntity)cityBaeList2.get(i);
              %>
              <tr>
                <td height = "34"> 
                  <div align="center"><%=rowData.getString("M350_PARTNAME")%></div>
                </td>
                <td height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M110_BUDGETAMT"))%></div>
                </td>
                <td width="76" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M110_BUDGETALLOTAMT"))%></div>
                </td>
                <td height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("M110_ORIALLOTAMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("ALLOTAMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("TOT_AMT"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("YESAN"))%></div>
                </td>
                <td width="87" height = "34"> 
                  <div align="right"><%=TextHandler.formatNumber(rowData.getString("BAE"))%></div>
                </td>
              </tr>
              <% } %>
            <% } //end if (ED05)%>
            </table>
            <table width="690" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
              <tr> 
                <td height="60" colspan="3"> 
                  <p>&nbsp;</p>
                  <p>&nbsp;</p>
                  <div align="center"><br><font size="4"><b>��걤������ </b></font>
                  <% if ("Y".equals(manager.getString("M180_SIGNFINISHSTATE")) ) { %>
                  <img src="/report/seal/<%=sealInfo.getString("M340_FNAME")%>" width="60" height="60" align="absmiddle">
                  <% } %>
                  <br></div>
                </td>
              </tr>
							<% if (manager != null) { %>
              <tr> 
                <td height="25" width="210"> 
                  <p>&nbsp;</p>
                  <div align="left">�ֹ��� : <%=manager.getString("M180_TASKNAME")%> </div>
                </td>
                <td height="25" width="312">
                  <p>&nbsp;</p>
                  ���԰������繫�� : <%=manager.getString("M180_MIDDLENAME")%>
                </td>
                <td height="30" width="162"> 
                  <p>&nbsp;</p>
                  �������� : <%=manager.getString("M180_UPPERNAME")%>
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
</form>
<% } %>
<br>
<div class="noprint">
<div align="right">
<img src="../img/btn_baelist.gif" alt="��������" onClick="goList()" style="cursor:hand">
<img src="../img/btn_bolist.gif" alt="������Ȳ" onClick="goBoyu()" style="cursor:hand">
<img src="../img/btn_print.gif" alt="�μ�" onClick="goPrint()" style="cursor:hand">
<img src="../img/btn_close.gif" alt="�ݱ�" onClick="self.close()" style="cursor:hand"><span style="width:30"></span>
</div>
</div>
</body>
</html>