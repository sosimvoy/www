<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR011011.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ���Ա� �����䱸��
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
   CommonEntity informView = (CommonEntity)request.getAttribute("page.mn01.informView");
   CommonEntity stamp = (CommonEntity)request.getAttribute("page.mn01.stamp");    //����
%>

<html>
<head>
<title>��걤���� ���� �� �ڱݹ������� �ý���</title>
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

<style>
.report {font-family: "arial","�Ÿ���", "Helvetica", "sans-serif";}
.report td.re01 {font-size:11pt;}

@media print {
  .noprint {
	  display:none;
	}
}

</style>
</head>

<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" style="padding-left:45px;">
<form name="sForm" method="post" action="IR011011.etax">
<input type="hidden" name="cmd" value="informView">
<table width="349" border="0" cellspacing="0" cellpadding="1">
  <tr> 
    <td height="50"> 
      <div align="center"><b><font size="5">���Ա� �����䱸��</font></b></div>
    </td>
  </tr>
</table>
<table width="341" border="0" cellspacing="0" cellpadding="3" bordercolor="#000000" class="report">
  <tr> 
    <td width="335" height="40" class="re01"> 
      <div align="left">No ��&nbsp;<%=informView.getString("M020_DOCUMENTNO")%>&nbsp;ȣ</div>
    </td>
  </tr>
</table>
<table width="340" border="1" cellspacing="0" cellpadding="3" bordercolor="#000000" class="report">
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
    </td>
    <td width="122" class="re01"> 
      <div align="center">�������</div>
    </td>
    <td width="122" class="re01"> 
      <div align="center">��������</div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
    </td>
    <td width="122" height="2" class="re01"> 
      <div align="center"><%=informView.getString("M020_OYEARMONTH").substring(0,4)%>.<%=informView.getString("M020_OYEARMONTH").substring(4,6)%></div>
    </td>
    <td width="122" height="2" class="re01"> 
      <div align="center"><%=informView.getString("M020_CYEARMONTH").substring(0,4)%>.<%=informView.getString("M020_CYEARMONTH").substring(4,6)%></div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">ȸ&nbsp;&nbsp;��&nbsp;&nbsp;��</div>
    </td>
    <td width="122" height="2" class="re01"> 
      <div align="center">�Ϲ�ȸ��</div>
    </td>
    <td width="122" height="2" class="re01"> 
      <div align="center">&nbsp;<%=informView.getString("M020_CACCCLASS")%></div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">�ְ�¡����</div>
    </td>
    <td width="122" height="2" class="re01"> 
      <div align="center">��걤������</div>
    </td>
    <td width="122" height="2" class="re01"> 
      <div align="center">&nbsp;<%=informView.getString("M020_CJINGSUGWAN")%></div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
    </td>
    <td width="120" height="2" class="re01"> 
      <div align="center">&nbsp;<%=informView.getString("M020_OGWAMOK")%></div>
    </td>
    <td width="128" height="2" class="re01"> 
      <div align="center">&nbsp;<%=informView.getString("M020_CSUBJECT")%></div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">��������</div>
    </td>
    <td width="120" height="2" class="re01"> 
      <div align="center">&nbsp;
      <%=informView.getString("M020_ORECEIPTDATE").substring(0,4)%>.<%=informView.getString("M020_ORECEIPTDATE").substring(4,6)%>.<%=informView.getString("M020_ORECEIPTDATE").substring(6,8)%></div>
    </td>
    <td width="128" height="2" class="re01"> 
      <div align="center">&nbsp;
      <%=informView.getString("M020_CRECEIPTDATE").substring(0,4)%>.<%=informView.getString("M020_CRECEIPTDATE").substring(4,6)%>.<%=informView.getString("M020_CRECEIPTDATE").substring(6,8)%></div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">�������</div>
    </td>
    <td width="122" height="2" class="re01"> 
      <div align="center">�泲����</div>
    </td>
    <td width="122" height="2" class="re01"> 
      <div align="center">&nbsp;<%=informView.getString("M020_CRECEIPTLOCATION")%></div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">���Աݾ�</div>
    </td>
    <td width="120" height="2" class="re01"> 
      <div align="center">&nbsp;<%=TextHandler.formatNumber(informView.getString("M020_OPAYMENTAMT"))%>��</div>
    </td>
    <td width="128" height="2" class="re01"> 
      <div align="center">&nbsp;<%=TextHandler.formatNumber(informView.getString("M020_CPAYMENTAMT"))%>��</div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">�����ڸ�</div>
    </td>
    <td colspan="2" height="2" class="re01">
      <div align="left">&nbsp;<%=informView.getString("M020_OPAYMENTNAME")%></div>
    </td>
  </tr>
  <tr> 
    <td width="96" height="30" class="re01"> 
      <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
    </td>
    <td colspan="2" height="2" class="re01"> 
      <div align="left">&nbsp;<%=informView.getString("M020_OREASON")%></div>
    </td>
  </tr>
  <tr> 
    <td colspan="3" height="60"> 
      <div align="center"> 
        <table width="265" border="0" cellspacing="0" cellpadding="10" bordercolor="#000000" class="report" align="center">
          <tr> 
            <td width="259" height="50" colspan="2" class="re01"> 
              <div align="center"> �̿� ���� �����Ͽ� �ֽñ� �ٶ�.</div>
            </td>
          </tr>
          <tr> 
            <td width="259" colspan="2" class="re01"> 
              <div align="center">
              <%=informView.getString("M020_ODATE").substring(0,4)%>&nbsp;��&nbsp;
              <%=informView.getString("M020_ODATE").substring(4,6)%>&nbsp;��&nbsp;
              <%=informView.getString("M020_ODATE").substring(6,8)%>&nbsp;�� 
              </div>
            </td>
          </tr>
          <tr> 
            <td width="259" class="re01"> 
              <div align="right">��걤���� ����¡����</div>
            </td>
            <td> 
              <img src="../../../report/seal/<%=stamp.getString("M340_FNAME")%>" height="50"></div>
            </td>
          </tr>
          <tr> 
            <td width="259" colspan="2" class="re01"> 
              <div align="right">��걤���ñݰ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����</div>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
<span class="report"></span>
<div class="noprint">
<span style="width:140px"></span>
  <img src="../img/btn_print.gif" alt="�μ�" align="absmiddle" onclick="goPrint()" style="cursor:hand">
</div>
<p>&nbsp;</p>
</body>
</html>
