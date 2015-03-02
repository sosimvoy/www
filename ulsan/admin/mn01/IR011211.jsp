<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR011211.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2014-11-05
* ���α׷�����	   : ���� > GIRO��翢��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ page import = "java.text.*" %>
<%  
String job_dt = request.getParameter("job_dt");

response.setContentType("application/vnd.ms-excel;charset=euc-kr");
response.setHeader("Content-Disposition", "inline;filename=GIRO���_"+job_dt+".xls"); 
response.setHeader("Content-Description", "JSP Generated Data");

CommonEntity giroData = (CommonEntity)request.getAttribute("page.mn01.giroData");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
    <title>��걤���� ���� �� �ڱݹ��������ý���</title>
  	<meta name=ProgId content=Excel.Sheet>
<style>
  table { table-layout:fixed; }
  tr,td { border:0.5pt solid silver; }
</style>
  </head>
  <body>
    <table width="993" border="0" cellspacing="0" cellpadding="0">
			<% if (giroData != null ) { %>
			<tr>
			 <td align="center"><span style="font-size:24px; font-weight:bold">���������� ���μ�������</span></td>
			</tr>
			<tr>
			 <td>
			     <table>
			         <tr>
			             <td style='mso-number-format:"\@"; border-right:none'>�������� : <%=TextHandler.formatDate(job_dt, "yyyyMMdd", "yyyy-MM-dd") %></td>
			             <td align="right" colspan="7" style="border-left:none">(������ϻ����)&nbsp;&nbsp;</td>
			         </tr>
			     </table>
			 </td>
			</tr>
			<tr>
             <td>
                 <table width="975" border="0" cellspacing="0" cellpadding="0" >
                    <tr> 
                        <th width="10%" rowspan="2">����</th>
                            <th width="30%" colspan="2">�Ϲݼ���</th>
                            <th width="30%" colspan="2">ī�����</th>
                            <th width="30%" colspan="3">�հ�</th>
                    </tr>
                    <tr>
                        <th width="10%">�Ǽ�</th>
                        <th>�ݾ�</th>
                        <th width="10%">�Ǽ�</th>
                        <th>�ݾ�</th>
                        <th width="10%">�Ǽ�</th>
                        <th colspan="2">�ݾ�</th>
                    </tr>
                    <%
                    long TOT_CNT = 0L; //�հ�Ǽ�
                    long TOT_AMT = 0L; //�հ�ݾ�
                    TOT_CNT = giroData.getLong("ILCNT")+giroData.getLong("CACNT")+giroData.getLong("REGI_ILCNT")+giroData.getLong("REGI_CACNT");
                    TOT_AMT = giroData.getLong("ILAMT")+giroData.getLong("CAAMT")+giroData.getLong("REGI_ILAMT")+giroData.getLong("REGI_CAAMT");
                    %>
                    <tr>
                        <td>��걤����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("ILAMT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAAMT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(TOT_CNT) %></td>
                        <td align="right" colspan="2"><%=TextHandler.formatNumber(TOT_AMT) %></td>
                    </tr>
                    <tr>
                        <td colspan="8" height="6"></td>
                    </tr>
                    <tr>
                        <th rowspan="2">��������</th>
                        <th colspan="2">����</th>
                        <th colspan="5">��������(��漼)</th>
                    </tr>
                    <tr>
                        <th>ȸ��/����</th>
                        <th>�ڱ���ü</th>
                        <th colspan="3">����</th>
                        <th>�Ǽ�</th>
                        <th>����</th>
                    </tr>
                    <%
                    TOT_CNT = giroData.getLong("ILCNT")+giroData.getLong("CACNT");
                    TOT_AMT = giroData.getLong("ILAMT")+giroData.getLong("CAAMT");
                    %>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td align="center"><b>�հ�</b></td>
                        <td>&nbsp;</td>
                        <td align="right"><b><%=TextHandler.formatNumber(TOT_CNT) %></b></td>
                        <td align="right"><b><%=TextHandler.formatNumber(TOT_AMT) %></b></td>
                    </tr>
                    <tr>
                        <td rowspan="5" colspan="3" align="center">�հ�</td>
                        <td rowspan="2">��漼</td>
                        <td rowspan="2">(����)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_AMT")) %></td>
                    </tr>
                    <tr>
                        <td>��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_AMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">��漼</td>
                        <td rowspan="3">(������)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_AMT")) %></td>
                    </tr>
                    <tr>
                        <td >������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_AMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_AMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="6" align="center">�Ϲ���ü</td>
                        <td rowspan="6" align="center" style='mso-number-format:"\@";'><%=TextHandler.formatDate(giroData.getString("AC_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td rowspan="6" align="center" style='mso-number-format:"\@";'><%=TextHandler.formatDate(giroData.getString("ILBAN_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td >&nbsp;</td>
                        <td>�Ұ�</td>
                        <td >&nbsp;</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="2">��漼</td>
                        <td rowspan="2">(����)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">��漼</td>
                        <td rowspan="3">(������)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="6" align="center">�ſ�ī��</td>
                        <td rowspan="6" align="center" style='mso-number-format:"\@";'><%=TextHandler.formatDate(giroData.getString("AC_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td rowspan="6" align="center" style='mso-number-format:"\@";'><%=TextHandler.formatDate(giroData.getString("CARD_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td >&nbsp;</td>
                        <td>�Ұ�</td>
                        <td >&nbsp;</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="2">��漼</td>
                        <td rowspan="2">(����)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_BON_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_NONG_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">��漼</td>
                        <td rowspan="3">(������)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_BON_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_EDU_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_NONG_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td colspan="8" height="6"></td>
                    </tr>
                    <tr>
                        <th rowspan="2">��������</th>
                        <th colspan="2">����</th>
                        <th colspan="5">��������(��ϼ�)</th>
                    </tr>
                    <tr>
                        <th>ȸ��/����</th>
                        <th>�ڱ���ü</th>
                        <th colspan="3">����</th>
                        <th>�Ǽ�</th>
                        <th>����</th>
                    </tr>
                    <%
                    TOT_CNT = giroData.getLong("REGI_ILCNT")+giroData.getLong("REGI_CACNT");
                    TOT_AMT = giroData.getLong("REGI_ILAMT")+giroData.getLong("REGI_CAAMT");
                    %>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td align="center"><b>�հ�</b></td>
                        <td>&nbsp;</td>
                        <td align="right"><b><%=TextHandler.formatNumber(TOT_CNT) %></b></td>
                        <td align="right"><b><%=TextHandler.formatNumber(TOT_AMT) %></b></td>
                    </tr>
                    <tr>
                        <td rowspan="6" align="center" colspan="3">�հ�</td>
                        <td rowspan="3">��ϼ�</td>
                        <td rowspan="3">(����)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_AMT")) %></td>
                    </tr>
                    <tr>
                        <td>������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_AMT")) %></td>
                    </tr>
                    <tr>
                        <td>��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_AMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">��ϼ�</td>
                        <td rowspan="3">(������)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_AMT")) %></td>
                    </tr>
                    <tr>
                        <td >������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_AMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_CNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_AMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="7" align="center">�Ϲ���ü</td>
                        <td rowspan="7" align="center"><%=TextHandler.formatDate(giroData.getString("AC_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td rowspan="7" align="center"><%=TextHandler.formatDate(giroData.getString("ILBAN_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td >&nbsp;</td>
                        <td>�Ұ�</td>
                        <td >&nbsp;</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("REGI_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("REGI_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">��ϼ�</td>
                        <td rowspan="3">(����)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">��ϼ�</td>
                        <td rowspan="3">(������)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_ILCNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_ILAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="7" align="center">�ſ�ī��</td>
                        <td rowspan="7" align="center"><%=TextHandler.formatDate(giroData.getString("AC_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td rowspan="7" align="center"><%=TextHandler.formatDate(giroData.getString("CARD_DATE"), "yyyyMMdd", "yy/MM/dd") %></td>
                        <td >&nbsp;</td>
                        <td>�Ұ�</td>
                        <td >&nbsp;</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("REGI_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("REGI_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">��ϼ�</td>
                        <td rowspan="3">(����)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_BON_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_EDU_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("CAR_REGI_NONG_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td rowspan="3">��ϼ�</td>
                        <td rowspan="3">(������)</td>
                        <td >����</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_BON_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >������</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_EDU_CAAMT")) %></td>
                    </tr>
                    <tr>
                        <td >��Ư��</td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_CACNT")) %></td>
                        <td align="right"><%=TextHandler.formatNumber(giroData.getString("MAC_REGI_NONG_CAAMT")) %></td>
                    </tr>
                </table>
             </td>
             </tr>
             
             <% } %>
    </table>
  </body>
</html>