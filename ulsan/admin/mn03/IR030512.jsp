<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR030512.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-29
* ���α׷�����	   : ���Լ�������� > ������������
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
    CommonEntity juheangView = (CommonEntity)request.getAttribute("page.mn03.juheangView");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
 <link href="../css/class.css" rel="stylesheet" type="text/css" />
 <script language="javascript" src="../js/calendar.js"></script>	
 <script language="javascript" src="../js/base.js"></script>
 <script language="javascript">
		function init() {
			typeInitialize();
		}
    
	function goPrint() {
		window.print();
	}
 </script>
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
</head>

<body bgcolor="#FFFFFF" topmargin="0" leftmargin="20" oncontextmenu="return false">
  <form name="sForm" method="post" action="IR030512.etax">
  <input type="hidden" name="cmd" value="YoungsuView">
	<% if (juheangView != null && juheangView.size() > 0) {%>
  <input type="hidden" name="seq" value="<%=juheangView.getString("M060_SEQ")%>">
  <input type="hidden" name="cashType" value="<%=juheangView.getString("M060_CASHTYPE")%>">
  <% } %>
<table width="357" border="0" cellspacing="0" cellpadding="1">
  <tr> 
    <td height="60"> 
      <div align="center"><b style="font-size:20px">�� �� �� �� �� ��</b></div>
    </td>
  </tr>
</table>
<table width="257" border="1" cellspacing="0" cellpadding="6" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
 <% if (juheangView != null && juheangView.size() > 0) {%>
	<tr> 
    <td rowspan="2" height="12" width="106"> 
      <div align="center"><font size="3">��û</font></div>
    </td>
    <td rowspan="2" height="40" width="121"> 
      <div align="center"><font size="3">���뿹��</font></div>
    </td>
  </tr>
  <tr> </tr>
  <tr> 
    <td height="12" width="106"> 
      <div align="left">(��)</div>
    </td>
    <td height="12" width="121"> 
      <div align="left">(��)</div>
    </td>
  </tr>
  <tr> 
    <td height="12" width="106"> 
      <div align="left">(��)</div>
    </td>
    <td height="12" width="121"> 
      <div align="center"><font size="3">���༼</font></div>
    </td>
  </tr>
  <tr> 
    <td height="12" colspan="2"> 
      <div align="center"></div>
      <div align="center">
        <table width="341" border="0" cellspacing="0" cellpadding="3" class="basic">
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129">&nbsp;</td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129"> 
              <div align="center"><b class="line">
							 <% if ("A1".equals(juheangView.getString("M060_CASHTYPE")) ) {  %>
							 <font size="5">��<%=TextHandler.formatNumber(juheangView.getString("SUIP_AMT"))%></font></b>
							 <% }else if ("A2".equals(juheangView.getString("M060_CASHTYPE")) ) {  %>
							 <font size="5">��<%=TextHandler.formatNumber(juheangView.getString("GWAO_AMT"))%></font></b>
							 <% }else if ("A3".equals(juheangView.getString("M060_CASHTYPE")) ) {  %>
							 <font size="5">��<%=TextHandler.formatNumber(juheangView.getString("JIGUP_AMT"))%></font></b>
							 <% }else if ("A4".equals(juheangView.getString("M060_CASHTYPE")) ) {  %>
							 <font size="5">��<%=TextHandler.formatNumber(juheangView.getString("BANNAP_AMT"))%></font></b>
							 <% } %>
							</div>
            </td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101" height="50">&nbsp;</td>
            <td width="129">&nbsp;</td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="230" class="line" colspan="2"><b><font size="4">��: <%=juheangView.getString("M060_NAPSEJA")%></b></td>
           
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129">&nbsp;</td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129" height="50">&nbsp;</td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="230" colspan="2"> 
              <div align="center"><b><font size="3">���ݾ��� ������.</font></b></div>
            </td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129" height="30">&nbsp;</td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129">
						<font size="3">
              <div align="center"><%=juheangView.getString("M060_DATE").substring(0,4)%>��<%=juheangView.getString("M060_DATE").substring(4,6)%>�� <%=juheangView.getString("M060_DATE").substring(6,8)%>��</div>
            </font>
						</td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129">&nbsp;</td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129"> 
              <table width="99" border="0" cellspacing="0" cellpadding="0" align="center" class="basic">
                <tr> 
                  <td colspan="3"><img src="../img/stamp1.gif" width="148" height="54"></td>
                </tr>
                <tr> 
                  <td width="20"><img src="../img/stamp2.gif" width="20" height="37"></td>
                  <td width="115"> 
                    <div align="center"><font size="3"><%=juheangView.getString("M060_DATE")%></font></div>
                  </td>
                  <td width="14"><img src="../img/stamp3.gif" width="14" height="37"></td>
                </tr>
                <tr> 
                  <td colspan="3"><img src="../img/stamp4.gif" width="148" height="56"></td>
                </tr>
              </table>
            </td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129">&nbsp;</td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="129" height="60"> 
              <div align="center"><font size="3">��걤���ñݰ�</font></div>
            </td>
            <td width="93">&nbsp;</td>
          </tr>
          <tr> 
            <td width="101">&nbsp;</td>
            <td width="222" colspan="2"> 
              <div align="left"><font size="3">��걤���ü���¡����</font></div>
            </td>
            
          </tr>
        </table>
      </div>
    </td>
  </tr>
<% } %>
</table>
<br><br>
<table width="350">
	<div class="noprint">
	<div align="center">
		<img src="../img/btn_print.gif" alt="�μ�" align="absmiddle" onclick="goPrint()" style="cursor:hand">
	</div>
	</div>
</table>
<p><span class="count"></span> </p>
</body>
</html>

