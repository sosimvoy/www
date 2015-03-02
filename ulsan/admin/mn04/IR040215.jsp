<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR040215.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : ���ܼ��� > ���꼭 ��ȸ/����/����(����)
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

  List<CommonEntity> moneyRunExcelList  = (List<CommonEntity>)request.getAttribute("page.mn04.m080ExcelList");

	int moneyRunExcelListSize = 0;

	if (moneyRunExcelList != null ) {
		moneyRunExcelListSize = moneyRunExcelList.size();
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
        <td width="975"> 
				  <div id="pDiv">

					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list">
            <tr>
						  <td colspan="31"> 2010�⵵ ������Ȳ </td>
						</tr>
            <tr> 
              <th rowspan="2">�Ұ���ó</th>
              <th rowspan="2">�ǰ���</th>
              <th rowspan="2">�����</th>
              <th rowspan="2">������ȣ</th>
							<th rowspan="2">����</th>
							<th rowspan="2">�����</th>
							<th>�̼��ɾ�</th>
							<th colspan="11">�����(õ��)</th>
							<th colspan="15">���ɾ�</th>
							<th rowspan="2">ȸ��⵵</th>
							<th rowspan="2">��</th>
							<th rowspan="2">����</th>
							<th rowspan="2">�������(����)</th>
            </tr>
						<tr>
						  <th>(�����-���ɾ�)</th>
							<th>�հ�</th>
							<th>����</th>
							<th>1ȸ</th>
							<th>2ȸ</th>
							<th>3ȸ</th>
							<th>4ȸ</th>
							<th>5ȸ</th>
							<th>6ȸ</th>
							<th>7ȸ</th>
							<th>8ȸ</th>
							<th>9ȸ</th>
              <th>��</th>
              <th>1��</th>
              <th>2��</th>
              <th>3��</th>
              <th>4��</th>
              <th>5��</th>
              <th>6��</th>
              <th>7��</th>
              <th>8��</th>
              <th>9��</th>
              <th>10��</th>
              <th>11��</th>
              <th>12��</th>
              <th>13��</th>
              <th>14��</th>
						</tr>
						<% if (moneyRunExcelListSize > 0 && moneyRunExcelList != null) { 
               long tot_janamt = 0L;
               long gye1 = 0L;
               long gye2 = 0L;
							   for (int i=0; i < moneyRunExcelListSize; i++) {
									 CommonEntity rowData = (CommonEntity)moneyRunExcelList.get(i);
						%>
            <tr>
              <td> <%=rowData.getString("SOGWANPART")%></td>
              <td> <%=rowData.getString("SILGWA")%></td>
              <td> <%=rowData.getString("USERNAME")%></td>
              <td> <%=rowData.getString("INTELNO")%></td>
              <td> <%=rowData.getString("GWAMOK")%></td>
              <td> <%=rowData.getString("BUSINESSNAME")%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("TOT_NOTAMT"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("TOT_CHUKYNG"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("DANGCHOAMT"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT1"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT2"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT3"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT4"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT5"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT6"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT7"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT8"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("CHUKYNGAMT9"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("TOT_MONTH"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_1"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_2"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_3"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_4"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_5"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_6"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_7"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_8"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_9"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_10"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_11"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_12"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_13"))%></td>
              <td align="right" style='mso-number-format:"\@";'> <%=TextHandler.formatNumber(rowData.getString("MONTHAMT_14"))%></td>
              <td> <%=rowData.getString("YEAR")%></td>
              <td> <%=rowData.getString("MOK")%></td>
              <td> <%=rowData.getString("SEMOKCODE")%></td>
              <td> <%=rowData.getString("WRITEDATE")%></td>
            </tr>
             <% }	%>
		        <% }else { %>
						<tr> 
              <td colspan="37">&nbsp;</td>
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