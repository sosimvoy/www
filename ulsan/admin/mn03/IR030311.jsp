<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR030311.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-08-02
* ���α׷�����	   : ���Լ�������� > ������Ȳ
****************************************************************/
%>

<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>

<%
    List<CommonEntity>stampList =    (List<CommonEntity>)request.getAttribute("page.mn03.stampList");

		CommonEntity sealState =    (CommonEntity)request.getAttribute("page.mn03.sealState");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11px; color: #333333}
.num {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9px; color: #333333; text-align:right;}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 11pt; color: #333333; text-decoration: underline}

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
				factory.printing.portrait = false; // false �� �����μ�, true �� ���� �μ�  
				factory.printing.leftMargin = 1.0; // ���� ���� ������  
				factory.printing.topMargin = 1.0; // �� ���� ������  
				factory.printing.rightMargin = 0.5; // ������ ���� ������  
				factory.printing.bottomMargin = 1.0; // �Ʒ� ���� ������  
				factory.printing.Print(true) // ����ϱ� 
			}
		
		</script>
</head>

<body bgcolor="#FFFFFF" topmargin="0" leftmargin="20">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR030311.etax">
<input type="hidden" name="cmd" value="suipStampView">

<table width="1050" border="0" cellspacing="0" cellpadding="1" class="line">
  <tr> 
    <td height="100" align="center" style="font-size:14pt;"> 
      <u><b>��� ������ �������� ������Ȳ</b></u>
    </td>
  </tr>
</table>
<table width="1050" border="1" cellspacing="0" cellpadding="1" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr height="20"> 
    <td rowspan="3" width="40" align="center"> 
      ����
    </td>
    <td colspan="3" width="236" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td rowspan="3" width="66" align="center"> 
      ����<br>
        (���)
    </td>
    <td colspan="6" align="center" width="472"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td colspan="3" align="center" width="236"> 
      �����ܷ�
    </td>
  </tr>
  <tr height="20"> 
    <td rowspan="2" width="79" align="center"> 
      �����̿�
    </td>
    <td rowspan="2" width="78" align="center"> 
      �ű�����
    </td>
    <td rowspan="2" width="79" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td colspan="2" width="157" align="center"> 
      �ΰ��Ǹ�
    </td>
    <td colspan="2" width="157" align="center">
      ��û�Ǹ�
    </td>
    <td colspan="2" width="158" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td rowspan="2" width="80" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td rowspan="2" width="78" align="center"> 
      �ñݰ�
    </td>
    <td rowspan="2" width="78" align="center"> 
      ��û����
    </td>
  </tr>
  <tr height="20"> 
    <td width="78" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td width="79" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td width="78" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td width="79" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td width="79" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
    <td width="79" align="center"> 
      ��&nbsp;&nbsp;&nbsp;&nbsp;��
    </td>
  </tr>
	<%  
      long M050_LASTYEAR       = 0L;
      long M050_TOTALCREATE    = 0L;
      long SO_GYE              = 0L;
      long M050_TOTALDISUSE    = 0L;
      long M050_GUMGOSALE      = 0L;
      long M050_TOTALGUMGOSALE = 0L;
      long M050_CITYSALE       = 0L;
      long M050_TOTALCITYSALE  = 0L;
      long DANG_SO_GYE         = 0L;
      long DANG_NU_GYE         = 0L;
      long M050_CITYDIVIDE     = 0L;
      long M050_GUMGOREST      = 0L;
      long M050_CITYREST       = 0L;
      long REST_AMT            = 0L;
      long TOT_LASTYEAR        = 0L;
      long TOT_CREATE          = 0L;
      long TOT_SO_GYE          = 0L;
      long TOT_LDISUSE         = 0L;
      long TOT_GUMGOSALE       = 0L;
      long TOT_GUMGO_SUM       = 0L;
      long TOT_CITYSALE        = 0L;
      long TOT_CITY_SUM        = 0L;
      long TOT_DANG_GYE        = 0L;
      long TOT_DANG_NU         = 0L;
      long TOT_CITYDIVIDE      = 0L;
      long TOT_GUMGOREST       = 0L;
      long TOT_CITYREST        = 0L;
      long TOT_REST_AMT        = 0L;

			if (stampList != null && stampList.size() > 0) {
			for (int i=0; i < stampList.size(); i++) {
				CommonEntity data = (CommonEntity)stampList.get(i);

        M050_LASTYEAR       += data.getLong("M050_LASTYEAR");
        M050_TOTALCREATE    += data.getLong("M050_TOTALCREATE");
        SO_GYE              += data.getLong("SO_GYE");
        M050_TOTALDISUSE    += data.getLong("M050_TOTALDISUSE");
        M050_GUMGOSALE      += data.getLong("M050_GUMGOSALE");
        M050_TOTALGUMGOSALE += data.getLong("M050_TOTALGUMGOSALE");
        M050_CITYSALE       += data.getLong("M050_CITYSALE");
        M050_TOTALCITYSALE  += data.getLong("M050_TOTALCITYSALE");
        DANG_SO_GYE         += data.getLong("DANG_SO_GYE");
        DANG_NU_GYE         += data.getLong("DANG_NU_GYE");
        M050_CITYDIVIDE     += data.getLong("M050_CITYDIVIDE");
        M050_GUMGOREST      += data.getLong("M050_GUMGOREST");
        M050_CITYREST       += data.getLong("M050_CITYREST");
        REST_AMT            += data.getLong("REST_AMT");
        TOT_LASTYEAR        += data.getLong("TOT_LASTYEAR");
        TOT_CREATE          += data.getLong("TOT_CREATE");
        TOT_SO_GYE          += data.getLong("TOT_SO_GYE");
        TOT_LDISUSE         += data.getLong("TOT_LDISUSE");
        TOT_GUMGOSALE       += data.getLong("TOT_GUMGOSALE");
        TOT_GUMGO_SUM       += data.getLong("TOT_GUMGO_SUM");
        TOT_CITYSALE        += data.getLong("TOT_CITYSALE");
        TOT_CITY_SUM        += data.getLong("TOT_CITY_SUM");
        TOT_DANG_GYE        += data.getLong("TOT_DANG_GYE");
        TOT_DANG_NU         += data.getLong("TOT_DANG_NU");
        TOT_CITYDIVIDE      += data.getLong("TOT_CITYDIVIDE");
        TOT_GUMGOREST       += data.getLong("TOT_GUMGOREST");
        TOT_CITYREST        += data.getLong("TOT_CITYREST");
        TOT_REST_AMT        += data.getLong("TOT_REST_AMT");
			%>
 
  <tr class="num" height="20"> 
  	<td rowspan="2"> 
      <%=data.getString("M050_GUEONJONG")%>
		</td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("M050_LASTYEAR"))%>
    </td>
    <td>
     <%=TextHandler.formatNumber(data.getString("M050_TOTALCREATE"))%>
		</td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("SO_GYE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("M050_TOTALDISUSE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("M050_GUMGOSALE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("M050_TOTALGUMGOSALE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("M050_CITYSALE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("M050_TOTALCITYSALE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("DANG_SO_GYE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("DANG_NU_GYE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("REST_AMT"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("M050_GUMGOREST"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("M050_CITYREST"))%>
    </td>
  </tr>
	 <tr class="num" height="20"> 
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_LASTYEAR"))%>
    </td>
    <td>
     <%=TextHandler.formatNumber(data.getString("TOT_CREATE"))%>
		</td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_SO_GYE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_LDISUSE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_GUMGOSALE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_GUMGO_SUM"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_CITYSALE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_CITY_SUM"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_DANG_GYE"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_DANG_NU"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_REST_AMT"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_GUMGOREST"))%>
    </td>
    <td> 
      <%=TextHandler.formatNumber(data.getString("TOT_CITYREST"))%>
    </td>
  </tr>
  <% 
				} 
			}
	%>
  <tr height="20"> 
  	<td rowspan="2" align="center"> 
      �հ�
		</td>
    <td class="num"> 
      <%=TextHandler.formatNumber(M050_LASTYEAR)%>
    </td>
    <td class="num">
     <%=TextHandler.formatNumber(M050_TOTALCREATE)%>
		</td>
    <td class="num"> 
      <%=TextHandler.formatNumber(SO_GYE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(M050_TOTALDISUSE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(M050_GUMGOSALE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(M050_TOTALGUMGOSALE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(M050_CITYSALE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(M050_TOTALCITYSALE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(DANG_SO_GYE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(DANG_NU_GYE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(REST_AMT)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(M050_GUMGOREST)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(M050_CITYREST)%>
    </td>
  </tr>
	<tr height="20"> 
     <td class="num"> 
      <%=TextHandler.formatNumber(TOT_LASTYEAR)%>
    </td>
    <td class="num">
     <%=TextHandler.formatNumber(TOT_CREATE)%>
		</td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_SO_GYE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_LDISUSE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_GUMGOSALE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_GUMGO_SUM)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_CITYSALE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_CITY_SUM)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_DANG_GYE)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_DANG_NU)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_REST_AMT)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_GUMGOREST)%>
    </td>
    <td class="num"> 
      <%=TextHandler.formatNumber(TOT_CITYREST)%>
    </td>
  </tr>	
</table>
<table width="1050" border="0" cellspacing="0" cellpadding="4" class="basic">
  <tr> 
    <td height="60" align="left" colspan="3"> 
      ��걤���� ���Ա� �ⳳ�� ����      
    </td>
    <td height="60" colspan="8"> 
      <div align="center">�� �� �� �� �� �� ��.<br>
      <%=TextHandler.formatDate(request.getParameter("date"),"yyyyMMdd","yyyy�� MM�� dd��");%>
      </div>
    </td>
		<td height="60" align="right" colspan="3">
      �泲���� ����û����<br>
      ��걤���ñݰ�
		</td>
		 <td height="60" width="60">
		 <% if (sealState != null && sealState.size() > 0) { %>
					<img src="/report/seal/<%=sealState.getString("M340_FNAME")%>" width="60" height="60">
	  	<% }%>
		</td>
  </tr>
</table>
<table width="1050">
  <tr height="10">
    <td align="right">
	  <div class="noprint">
		<img src="../img/btn_print.gif" alt="�μ�" align="absmiddle" onclick="goPrint()" style="cursor:hand">
	  </div>
    </td>
  </tr>
</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><span class="count"></span> </p>
</body>
</html>