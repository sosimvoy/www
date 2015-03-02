<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR030514.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-28
* ���α׷�����	   : ���Լ��������> ���༼ ��ȸ/����/���� > �ֵ�Ư��¡��������
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.TextHandler" %>
<%@ page import = "com.etax.util.StringUtil" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%@ page import="java.net.InetAddress" %>

<%
	List<CommonEntity>spJuheangList =    (List<CommonEntity>)request.getAttribute("page.mn03.spJuheangList");
  
  List<CommonEntity>spIjaList =    (List<CommonEntity>)request.getAttribute("page.mn03.spIjaList");

  CommonEntity iwalData =    (CommonEntity)request.getAttribute("page.mn03.iwalData");

  int jhListSize = 0;
  if (spJuheangList != null) {
    jhListSize = spJuheangList.size();
  } 

  long max_jan = 0L;
  long max_ija = 0L;
%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
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
		 	
			function init() {
				typeInitialize();
			}
      function goPrint() {
		    factory.printing.header = ""; // Header�� �� ����  
				factory.printing.footer = ""; // Footer�� �� ����  
				factory.printing.portrait = true; // false �� �����μ�, true �� ���� �μ�  
				factory.printing.leftMargin = 1.0; // ���� ���� ������  
				factory.printing.topMargin = 1.0; // �� ���� ������  
				factory.printing.rightMargin = 0.5; // ������ ���� ������  
				factory.printing.bottomMargin = 1.0; // �Ʒ� ���� ������  
				factory.printing.Print(true) // ����ϱ� 
	    }
    </script>

		
</head>

<body bgcolor="#FFFFFF" topmargin="10" leftmargin="20" oncontextmenu="return false">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<table width="713" border="0" cellspacing="0" cellpadding="1">
  <tr> 
    <td height="30"> 
     <div align="center"><b><font size="5"><%=TextHandler.formatDate(request.getParameter("start_date"),"yyyyMMdd","MM");%>�� ���༼(�߻�����) �ֵ�Ư��¡�� ������</font></b></div>
    </td>    
  </tr>
</table>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td width="343"><b>O ���༼</b></td>
  </tr>
</table>
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td height="20" align="center" width="11%"> 
      ��������
    </td>
    <td height="20" align="center" width="32%"> 
      �����ǹ���
    </td>
    <td height="20" align="center" width="19%"> 
      ���Ծ� 
    </td>
    <td height="20" align="center" width="19%"> 
      �Ⱥо� 
    </td>
    <td height="20" align="center" width="19%"> 
      �ܾ� 
    </td>
  </tr>
	 <% if (iwalData != null ) { %>
	<tr> 
    <td height="2" align="center"> 
      <%=TextHandler.formatDate(iwalData.getString("first_date"),"yyyyMMdd","yyyy.MM.dd")%>
    </td>
    <td height="2" align="left"> 
      &nbsp;�̿���
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(iwalData.getString("BASE_MONTH_AMT"))%>&nbsp;
    </td>
    <td height="2" align="center">
      &nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(iwalData.getString("BASE_MONTH_AMT"))%>&nbsp;
    </td>
  </tr>
	<%
	  max_jan = iwalData.getLong("BASE_MONTH_AMT");
  }
		long list_jan = max_jan;
		long temp_amt = 0L;
		long suip_tot = 0L;
		long jigup_tot = 0L;
		long tot_amt = 0L;
	  for (int i=0; spJuheangList != null && i < spJuheangList.size(); i++) {
	  	CommonEntity data = (CommonEntity)spJuheangList.get(i);
			temp_amt =  data.getLong("SUIP_AMT") - data.getLong("JIGUP_AMT");
			list_jan += temp_amt;
	%>
  <tr> 
    <td height="2"> 
      <div align="center"><%=TextHandler.formatDate(data.getString("M060_DATE"),"yyyyMMdd","yyyy.MM.dd")%></div>
    </td>
    <td height="2" align="left"> 
      &nbsp;<%=data.getString("M060_NAPSEJA")%>
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(data.getString("SUIP_AMT"))%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(data.getString("JIGUP_AMT"))%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(list_jan)%>&nbsp;
    </td>
  </tr>
	<%
		suip_tot = suip_tot + data.getLong("SUIP_AMT");
		jigup_tot = jigup_tot + data.getLong("JIGUP_AMT");
    tot_amt = list_jan;
	  }
	%> 
  <% int cnt = 0;
     if (jhListSize < 43 ) {
       cnt = 43-jhListSize;
     }
  for (int y=0; y < cnt;  y++ ) { %>
	<tr> 
    <td height="2"> 
      <div align="center">&nbsp;</div>
    </td>
    <td height="2"> 
      <div align="center"></div>
    </td>
    <td height="2"> 
      <div align="right"></div>
    </td>
    <td height="2"> 
      <div align="right"></div>
    </td>
    <td height="2"> 
      <div align="right"></div>
    </td>
  </tr>
  <% } %>
	<tr style="font-weight:bold"> 
    <td height="2" align="center"> 
      �� ��
    </td>
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(max_jan+suip_tot)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(jigup_tot)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(tot_amt)%>&nbsp;
    </td>
  </tr>
</table>
<br>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td width="343"><b>O �߻�����</b></td>
  </tr>
</table>
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td height="20" align="center" width="11%"> 
      ����
    </td>
    <td height="20" align="center" width="32%"> 
      �����ǹ���
    </td>
    <td height="20" align="center" width="19%"> 
      ���Ծ�
    </td>
    <td height="20" align="center" width="19%"> 
      �Ⱥо�
    </td>
    <td height="20" align="center" width="19%"> 
      �ܾ�
    </td>
  </tr>
		 <% if (iwalData != null ) { %>
  <tr> 
    <td height="2" align="center"> 
      <%=TextHandler.formatDate(iwalData.getString("first_date"),"yyyyMMdd","yyyy.MM.dd")%>
    </td>
    <td height="2" align="left"> 
      &nbsp;�̿���
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(iwalData.getString("BASE_MONTH_IJA"))%>&nbsp;
    </td>
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(iwalData.getString("BASE_MONTH_IJA"))%>&nbsp;
    </td>
  </tr>
  <%
	  max_ija = iwalData.getLong("BASE_MONTH_IJA");
  }
		long list_ija = max_ija;
		long temp_ija = 0L;
		long suip_ija = 0L;
		long jigup_ija = 0L;
		long tot_ija = 0L;
	  for (int i=0; spIjaList != null && i < spIjaList.size(); i++) {
	  	CommonEntity ijaData = (CommonEntity)spIjaList.get(i);
			temp_ija =  ijaData.getLong("SUIP_AMT") - ijaData.getLong("JIGUP_AMT");
			list_ija += temp_ija;
	%>
	 <tr> 
    <td height="2" align="center"> 
      <%=TextHandler.formatDate(ijaData.getString("M060_DATE"),"yyyyMMdd","yyyy.MM.dd")%>
    </td>
    <td height="2" align="left"> 
      <%=ijaData.getString("M060_NAPSEJA")%>
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(ijaData.getString("SUIP_AMT"))%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(ijaData.getString("JIGUP_AMT"))%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(list_ija)%>&nbsp;
    </td>
  </tr>
  <%
		suip_ija = suip_ija + ijaData.getLong("SUIP_AMT");
		jigup_ija = jigup_ija + ijaData.getLong("JIGUP_AMT");
    tot_ija = list_ija;
		} 
	%>
 <tr> 
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      &nbsp;
    </td>
  </tr>
	<tr style="font-weight:bold"> 
    <td height="2" align="center"> 
      �� ��
    </td>
    <td height="2" align="center"> 
      &nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(max_ija+suip_ija)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(jigup_ija)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(tot_ija)%>&nbsp;
    </td>
  </tr>
</table>
<br>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td width="343"><b>O ���༼ + �߻�����</b></td>
  </tr>
</table>
<table width="713" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td height="20" align="center" width="11%"> 
      �� ��
    </td>
    <td height="20" align="center" width="32%"> 
      &nbsp;
    </td>
    <td height="20" align="center" width="19%"> 
      ���Ծ�
    </td>
    <td height="20" align="center" width="19%"> 
      �Ⱥо�
    </td>
    <td height="20" align="center" width="19%"> 
      �ܾ�
    </td>
  </tr>
  <tr> 
    <td height="2" rowspan="3" align="center"> 
      �� ��
    </td>
    <td height="2" align="center"> 
      ���༼
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(max_jan+suip_tot)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(jigup_tot)%>&nbsp;
    </td>
    <td height="2" align="right"> 
      <%=TextHandler.formatNumber(list_jan)%>&nbsp;
    </td>
  </tr>
  <tr> 
    <td height="2"align="center">�߻�����</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(max_ija+suip_ija)%>&nbsp;</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(jigup_ija)%>&nbsp;</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(list_ija)%>&nbsp;</td>
  </tr>
	 <tr style="font-weight:bold">    
    <td height="2"align="center">�Ұ�</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(max_jan+suip_tot+max_ija+suip_ija)%>&nbsp;</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(jigup_tot+jigup_ija)%>&nbsp;</td>
    <td height="2"align="right"><%=TextHandler.formatNumber(list_jan+list_ija)%>&nbsp;</td>
  </tr>
</table>
<div class="noprint">
<br><br>
<table width="713">
	<div align="right">
		<img src="../img/btn_print.gif" alt="�μ�" align="absmiddle" onclick="goPrint()" style="cursor:hand">
	</div>
</table>
</div>
</body>
</html>