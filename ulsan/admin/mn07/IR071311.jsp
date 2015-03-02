<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR071311.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : ���� > �ϰ�/������ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  String charMarkForExcel = "";
  response.setContentType("application/vnd.ms-excel;charset=euc-kr");
  response.setHeader("Content-Disposition", "inline;filename=���꼭��ȸ_"+new java.sql.Date(System.currentTimeMillis())+".xls");  
  response.setHeader("Content-Description", "JSP Generated Data");
  charMarkForExcel = "`"; // ���ڸ� �״�� ǥ���ϱ� ����

  List<CommonEntity>budgetList     =    (List<CommonEntity>)request.getAttribute("page.mn07.budgetexcelList");

	int budgetListSize = 0;

	if (budgetList != null ) {
		budgetListSize = budgetList.size();
	}

  String due_day = "";
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<meta http-equiv="Content-Type" content="text/html;charset=euc-kr">
    <meta name=ProgId content=Excel.Sheet>
    <style>
    table { table-layout:fixed; }
    tr,td { border:0.5pt solid silver; }
    </style>
		<script language="javascript" src="../js/base.js"></script>
		<script language="javascript" src="../js/tax_common.js"></script>
		<script language="javascript">
		  function init() {
  	    typeInitialize();
				window.print();
      }
		</script>
  </head>
  <body topmargin="0" leftmargin="0">
    <table width="993" border="0" cellspacing="0" cellpadding="0">
			<tr>
        <td width="18">&nbsp;</td>
        <td width="975" height="20"> &nbsp;</td>
			</tr>
      <tr> 
        <td width="18">&nbsp;</td>
        <td width="975"> 
				  <div id="pDiv">

						<% if (budgetListSize > 0 && budgetList != null) { 
               long tot_janamt = 0L;
               long gye1 = 0L;
               long gye2 = 0L;
            %>
            <%
							   for (int i=0; i < budgetListSize; i++) {
									 CommonEntity rowData = (CommonEntity)budgetList.get(i);
						%>
            <%
                   if ( i == 0 ) {
            %>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr>
						  <td colspan="10"> <%=rowData.getString("TITLENAME")%> </td>
						</tr>
            <tr> 
              <td height="18" colspan="6"> 
                ����
              </td>
              <td colspan="2" height="18"> 
                ������
              </td>
              <td colspan="2" height="18"> 
                ȯ�ξ�
              </td>
              <td colspan="2" height="18"> 
                �����ݾ�
              </td>
            </tr>
						<tr>
              <td width="70" height="18">��</td>
              <td width="16" height="18">��</td>
              <td width="22" height="18">��</td>
              <td width="92" height="18">��</td>
              <td width="92" height="18">����</td>
              <td width="92" height="18">������</td>
              <td width="44" height="18">�Ǽ�</td>
              <td width="98" height="18">�ݾ�</td>
              <td width="39" height="18">�Ǽ�</td>
              <td width="92" height="18">�ݾ�</td>
              <td width="55" height="18">�Ǽ�</td>
              <td width="100" height="18">�ݾ�</td>
						</tr>
            <%
                   }
            %>
            <tr>
              <%
              if ("0".equals(rowData.getString("M550_MOKGUBUN"))) {
              %>
                <td colspan="6">&nbsp;<%=rowData.getString("M550_SEMOKNAME")%></td>
              <%
              }else if ("1".equals(rowData.getString("M550_MOKGUBUN"))) {
              %>
                <td>&nbsp;</td>
                <td colspan="5">&nbsp;<%=rowData.getString("M550_SEMOKNAME")%></td>
              <%
              }else if ("2".equals(rowData.getString("M550_MOKGUBUN"))) {
              %>
                <td colspan="2">&nbsp;</td>
                <td colspan="4">&nbsp;<%=rowData.getString("M550_SEMOKNAME")%></td>
              <%
              }else if ("3".equals(rowData.getString("M550_MOKGUBUN"))) {
              %>
                <td colspan="3">&nbsp;</td>
                <td colspan="3">&nbsp;<%=rowData.getString("M550_SEMOKNAME")%></td>
              <%
              }else if ("4".equals(rowData.getString("M550_MOKGUBUN"))) {
              %>
                <td colspan="4">&nbsp;</td>
                <td colspan="2">&nbsp;<%=rowData.getString("M550_SEMOKNAME")%></td>
              <%
              }else if ("5".equals(rowData.getString("M550_MOKGUBUN"))) {
              %>
                <td colspan="5">&nbsp;</td>
                <td>&nbsp;<%=rowData.getString("M550_SEMOKNAME")%></td>
              <%
              }
               %>
              <td align="right" style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatNumber(rowData.getString("M500_SUNABCNT"))%></td>
              <td align="right" style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatNumber(rowData.getString("M500_SUNABAMT"))%></td>
              <td align="right" style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatNumber(rowData.getString("M500_HWANBUCNT"))%></td>
              <td align="right" style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatNumber(rowData.getString("M500_HWANBUAMT"))%></td>
              <td align="right" style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatNumber(rowData.getString("CHAGAM_CNT"))%></td>
              <td align="right" style='mso-number-format:"\@";'>&nbsp;<%=TextHandler.formatNumber(rowData.getString("CHAGAM_AMT"))%></td>
            </tr>
             <% }	%>
		        <% }else { %>
						<tr> 
              <td colspan="13">&nbsp;�ڷ����</td>
            </tr>
						<% } %>
          </table>
					</div>
        </td>
      </tr>			
    </table>
    <p>&nbsp;</p>
  </body>
</html>