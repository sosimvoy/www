<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR070410.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-12
* ���α׷�����	   : �ϰ�/���� > �����ϰ�ǥ����������ȸ 
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	
  CommonEntity bigoData = (CommonEntity) request.getAttribute("page.mn07.bigoData");
  CommonEntity ilgyeData = (CommonEntity) request.getAttribute("page.mn07.ilgyeData");
  CommonEntity signData = (CommonEntity) request.getAttribute("page.mn07.signData");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
  	<title>��걤���� ���� �� �ڱݹ������� �ý���</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link href="../css/class.css" rel="stylesheet" type="text/css" />
  	<script language="javascript" src="../js/base.js"></script>
  	<script language="javascript">
	  function init() {
	    typeInitialize();
      goPrint();
    }

	  function goPrint() {
      factory.printing.header = ""; // Header�� �� ����  
		  factory.printing.footer = ""; // Footer�� �� ����  
		  factory.printing.portrait = false; // false �� �����μ�, true �� ���� �μ�  
		  factory.printing.leftMargin = 1.0; // ���� ���� ������  
		  factory.printing.topMargin = 1.0; // �� ���� ������  
		  factory.printing.rightMargin = 1.0; // ������ ���� ������  
		  factory.printing.bottomMargin = 1.0; // �Ʒ� ���� ������  
		  factory.printing.Print(true); // ����ϱ�
	  }

    </script>
  </head>
  <body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
  <object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
  <form name="sForm" method="post" action="IR070421.etax">
  <input type="hidden" name="cmd" value="jojungNoteList">  
<table width="993" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="18">&nbsp;</td>
	<td width="975"> 
  <% if (bigoData != null && bigoData.size() > 0) { %>
  <% if ("1".equals(request.getParameter("report_gubun"))) { %>
	<!-------- ǥ ���� ----->
<table width="993" border="0" cellspacing="2" cellpadding="3">
<tr>
	<td align="center" style="font-weight:bold"><font size="4"><%=acc_year%>�� �Ϲ�ȸ�� �����ϰ�ǥ ��������</font></td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0" class="list">
<tr>
	<td colspan="3" width="25%">���������׸�</td>
	<td rowspan="2" width="11%">��ü������</td>
	<td rowspan="2">�̼��ɾ�</td>
	<td rowspan="2">�����ܾ�</td>
	<td rowspan="2">�ϰ��ܾ�</td>
	<td rowspan="2">���ݿ���<br>�����ܾ�</td>
</tr>
<tr>
	<td>����</td>
	<td>�Ⱓ</td>
	<td>����</td>
</tr>
<tr>
	<td rowspan="2">������漼</td>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_CAR_AGODATE_GC01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_CAR_SUM_GC01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_CAR_AGODATE_GC02"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_CAR_SUM_GC02"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>

<tr>
	<td rowspan="3">����û ���� �ü�</td>
	<td>�ݰ��</td>
	<td>�Ϲ�</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_BAL_AGODATE_GP01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_BAL_SUM_GP01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_BAL_AGODATE_GC01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_BAL_SUM_GC01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td class="right">�����ܾ�(<%=TextHandler.formatNumber(ilgyeData.getLong("GONGGEUMJANAMT")-bigoData.getLong("IL_MI_AMT")-ilgyeData.getLong("AMTSURPLUS"))%>)</td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_BAL_AGODATE_GC02"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_BAL_SUM_GC02"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td class="right">�׿���(<%=TextHandler.formatNumber(ilgyeData.getString("AMTSURPLUS"))%>)</td>
</tr>
<tr>
	<td colspan="3">�հ�</td>
	<td>&nbsp;</td>
	<td class="right"><%=TextHandler.formatNumber(bigoData.getString("IL_MI_AMT"))%></td>
	<td class="right"><%=TextHandler.formatNumber(ilgyeData.getLong("AMTJAN")-bigoData.getLong("IL_MI_AMT"))%></td>
	<td class="right"><%=TextHandler.formatNumber(ilgyeData.getString("AMTJAN"))%></td>
	<td class="right"><%=TextHandler.formatNumber(ilgyeData.getLong("GONGGEUMJANAMT")-bigoData.getLong("IL_MI_AMT"))%></td>
</tr>
</table>
<!-------- ǥ �� ----->
	</td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr>
	  <td width="400"><font size="3">&nbsp;&nbsp;&nbsp;��걤���� ����¡���� ����</font></td>
	  <td align="center"><font size="3"><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy.MM.dd");%></font></td>
	  <td width="300" align="right"><font size="3">��   ��   ��   ��   ��   ��   ��<br>�泲���� ����û����</font></td>
    <td width="100"><img src="../../../report/seal/<%=signData.getString("F_NAME")%>" width="60" height="60" align="right"></td>
  </tr>
</table>
<% } else if ("2".equals(request.getParameter("report_gubun"))) { %>
<table width="993" border="0" cellspacing="2" cellpadding="3">
<tr>
	<td align="center" style="font-weight:bold"><font size="4"><%=acc_year%>�� ������ϻ���� �����ϰ�ǥ ��������</font></td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0" class="list">
<tr>
	<td colspan="3" width="25%">���������׸�</td>
	<td rowspan="2" width="11%">��ü������</td>
	<td rowspan="2">�̼��ɾ�</td>
	<td rowspan="2">��ü�Ϸ��<br>���Դ���</td>
	<td rowspan="2">�Ѽ��Դ���</td>
</tr>
<tr>
	<td>����</td>
	<td>�Ⱓ</td>
	<td>����</td>
</tr>
<tr>
	<td rowspan="2">������漼</td>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_CAR_AGODATE_GC01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_CAR_SUM_GC01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_CAR_AGODATE_GC02"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_CAR_SUM_GC02"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>

<tr>
  <td rowspan="2">������Ư��</td>
	<td>�ݰ��</td>
	<td>ī��</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_NONG_AGODATE_GC01"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_NONG_SUM_GC01"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>�ݰ�� ��</td>
	<td>ī��</td>
	<td>&nbsp;<%=TextHandler.formatDate(bigoData.getString("M470_NONG_AGODATE_GC02"), "yyyyMMdd", "yyyy-MM-dd")%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("M470_NONG_SUM_GC02"))%></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td colspan="3">�հ�</td>
	<td>&nbsp;</td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(bigoData.getString("CAR_MI_AMT"))%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(ilgyeData.getLong("TODAYTOT")-bigoData.getLong("CAR_MI_AMT"))%></td>
	<td class="right">&nbsp;<%=TextHandler.formatNumber(ilgyeData.getString("TODAYTOT"))%></td>
</tr>
</table>
<!-------- ǥ �� ----->
	</td>
</tr>
</table>

<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr>
	  <td width="400"><font size="3">&nbsp;&nbsp;&nbsp;��걤���� ������ϻ������ ����</font></td>
	  <td align="center"><font size="3"><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy.MM.dd");%></font></td>
	  <td width="300" align="right"><font size="3">��   ��   ��   ��   ��   ��   ��<br>�泲���� ����û����</font></td>
    <td width="100"><img src="../../../report/seal/<%=signData.getString("F_NAME")%>" width="60" height="60" align="right"></td>
  </tr>
</table>
<% } %>
<% } %>
</form>
</body>
</html>