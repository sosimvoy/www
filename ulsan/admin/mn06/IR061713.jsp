<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR061711.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-05-15
* ���α׷�����	   : �ڱݿ�� > �Ⱓ���ܾ���
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%
  List<CommonEntity> giganList = (List<CommonEntity>)request.getAttribute("page.mn06.giganList"); //�Ϲ�ȸ��
  CommonEntity social = (CommonEntity)request.getAttribute("page.mn06.social"); //��ȸ����
  CommonEntity addInfo = (CommonEntity)request.getAttribute("page.mn06.addReduce"); //����,����
  CommonEntity totAmt = (CommonEntity)request.getAttribute("page.mn06.totAmt"); //�հ�
%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style type="text/css">
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 6pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 14pt; color: #333333; text-decoration: underline}
@media print {
  .noprint {
	  display:none;
	}
}
-->
</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript">
  function goPrint() {
		factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = false; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 1.0; // ���� ���� ������  
		factory.printing.topMargin = 0.5; // �� ���� ������  
		factory.printing.rightMargin = 0.5; // ������ ���� ������  
		factory.printing.bottomMargin = 0.3; // �Ʒ� ���� ������  
		factory.printing.Print(true); // ����ϱ� 
	}
</script>
</head>
<body bgcolor="#FFFFFF" topmargin="10" leftmargin="15" onload="goPrint()">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<table width="1070" border="0" cellspacing="0" cellpadding="0">
  <tr style="font-weight:bold"> 
    <td align="center" colspan="13" class="line"><b>��걤���� �ݰ��� �ܾ���</b></td>
  </tr>
  <tr> 
    <td align="center" colspan="13" style="font-size:6pt"><%=TextHandler.formatDate(request.getParameter("acc_date"), "yyyyMMdd", "yyyy-MM-dd")%></td>
  </tr>
</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr>
    <td colspan="4"><b>1. ����������(�Ⱓ)-����û</b></td>
    <td align="right" colspan="9">(����:��)</td>
  </tr>
</table>
<table width="1070" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td colspan="2" rowspan="2" align="center" width="5%">�� ��</td>
    <td rowspan="2" align="center" width="9%">���ݿ���</td>
    <td rowspan="2" align="center" width="8%">���ܿ���</td>
    <td rowspan="2" align="center" width="9%">�������Ʈ</td>
    <td colspan="4" align="center" width="34%">���⿹��</td>
    <td colspan="4" align="center" width="34%">ȯ��ä��</td>
  </tr>
  <tr> 
    <td align="center">1��~3���̸�</td>
    <td align="center">3��~6���̸�</td>
    <td align="center">6��~12���̸�</td>
    <td align="center">12���̻�</td>
    <td align="center">30��~89��</td>
    <td align="center">90��~119��</td>
    <td align="center">120��~179��</td>
    <td align="center">180���̻�</td>
  </tr>
  <% long byul_amt = 0L; 
     for (int i=0; giganList != null && i < giganList.size(); i++) {
       CommonEntity giganInfo = (CommonEntity)giganList.get(i);
       if (totAmt != null) {
         if ("F".equals(giganInfo.getString("M360_ACCGUBUN")) || "�� ��".equals(giganInfo.getString("M360_ACCNAME")) ) {
           byul_amt = totAmt.getLong("M170_CITYSPECIALDEPOSIT");
         } else {
           byul_amt = 0;
         }
       }
  %>
  <% if (("A".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))
         || ("F".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART"))) 
         || ("I".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))) {
     } else {
  %>
  <tr>
    <% if ("�Ϲ�ȸ��".equals(giganInfo.getString("M360_ACCNAME")) || "Ư��ȸ��".equals(giganInfo.getString("M360_ACCNAME"))
         || "���".equals(giganInfo.getString("M360_ACCNAME")) || "���Լ����".equals(giganInfo.getString("M360_ACCNAME"))
         || "�ϻ���".equals(giganInfo.getString("M360_ACCNAME"))
         || "�� ��".equals(giganInfo.getString("M360_ACCNAME"))) { %>
    <td colspan="2" align="left"><b><%=giganInfo.getString("M360_ACCNAME")%></b></td>
    <% } else { %>
    <td width="1"> </td>
    <td align="left"><%=giganInfo.getString("M360_ACCNAME")%></td>
    <% } %>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_OFFICIALDEPOSITJANAMT"))%>
</td>
    <td align="right"><%=TextHandler.formatNumber(byul_amt)%>
</td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_MMDABEFOREDAYJANAMT"))%>
</td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT_1"))%>
</td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT_2"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT_3"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT_4"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT_1"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT_2"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT_3"))%></td>
    <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT_4"))%></td>
  </tr>
  <% if ("E".equals(giganInfo.getString("M360_ACCGUBUN")) && "15".equals(giganInfo.getString("M360_ACCCODE"))) { 
       if (social != null) { 
  %>
  <tr>
    <td width="1"> </td>
    <td align="left"><%=social.getString("M360_ACCNAME")%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_CITYSPECIALDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_MMDABEFOREDAYJANAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT_1"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT_2"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT_3"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT_4"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT_1"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT_2"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT_3"))%></td>
    <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT_4"))%></td>
  </tr>
  <%   } 
     }%>
  <%   } 
     }
  %>

</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="13"><b>2. ����������(����)</b></td>

  </tr>
</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <table width="762" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;' bordercolor='#000000' class="basic">
        <tr style="font-weight:bold"> 
          <td colspan="2" align="center" width="10%">�� ��</td>
          <td align="center" width="13%">���ݿ���</td>
          <td align="center" width="13%">���ܿ���</td>
          <td align="center" width="13%">�������Ʈ</td>
          <td align="center" width="14%">���⿹��</td>
          <td align="center" width="13%">ȯ��ä��</td>
          <td align="center" width="10%">������Ź</td>
          <td align="center" width="14%">�հ�</td>

        </tr>
        <% byul_amt = 0L;
           long row_tot = 0L;
           long row_social = 0L;
           for (int i=0; giganList != null && i < giganList.size(); i++) {
             CommonEntity giganInfo = (CommonEntity)giganList.get(i);
             row_tot = giganInfo.getLong("M170_OFFICIALDEPOSITJANAMT") + giganInfo.getLong("M170_CITYSPECIALDEPOSIT")
                     + giganInfo.getLong("M170_MMDABEFOREDAYJANAMT") + giganInfo.getLong("M170_DEPOSITBEFOREDAYJANAMT")
                     + giganInfo.getLong("M170_RPBEFOREDAYAMT");
             if (totAmt != null) {
               if ("F".equals(giganInfo.getString("M360_ACCGUBUN")) || "�� ��".equals(giganInfo.getString("M360_ACCNAME")) ) {
                 byul_amt = totAmt.getLong("M170_CITYSPECIALDEPOSIT");
               } else{
                 byul_amt = 0;
               }
             }
        %>
        <% if (("A".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))
            || ("F".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART")))
            || ("I".equals(giganInfo.getString("M360_ACCGUBUN")) && !"00".equals(giganInfo.getString("CHART"))) ) {
           } else {
        %>
        <tr>
        <% if ("�Ϲ�ȸ��".equals(giganInfo.getString("M360_ACCNAME")) || "Ư��ȸ��".equals(giganInfo.getString("M360_ACCNAME"))
            || "���".equals(giganInfo.getString("M360_ACCNAME")) || "���Լ����".equals(giganInfo.getString("M360_ACCNAME"))
            || "�ϻ���".equals(giganInfo.getString("M360_ACCNAME"))
            || "�� ��".equals(giganInfo.getString("M360_ACCNAME"))) { %>
          <td colspan="2" align="left"><b><%=giganInfo.getString("M360_ACCNAME")%></b></td>
        <% } else { %>
          <td width="1"> </td>
          <td align="left"><%=giganInfo.getString("M360_ACCNAME")%></td>
        <% } %>
          <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(byul_amt)%></td>
          <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_MMDABEFOREDAYJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_DEPOSITBEFOREDAYJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(giganInfo.getString("M170_RPBEFOREDAYAMT"))%></td>
          <td align="right">&nbsp;</td>
          <% if ("F".equals(giganInfo.getString("M360_ACCGUBUN")) || "".equals(giganInfo.getString("M360_ACCGUBUN")) ) { %>
          <td align="right"><%=TextHandler.formatNumber(row_tot+byul_amt)%></td>
          <% } else { %>
          <td align="right"><%=TextHandler.formatNumber(row_tot)%></td>
          <% } %>
        </tr>
        <% if ("E".equals(giganInfo.getString("M360_ACCGUBUN")) && "15".equals(giganInfo.getString("M360_ACCCODE"))) { 
             if (social != null) { 
               row_social = social.getLong("M170_OFFICIALDEPOSITJANAMT") + social.getLong("M170_CITYSPECIALDEPOSIT")
                          + social.getLong("M170_MMDABEFOREDAYJANAMT") + social.getLong("M170_DEPOSITBEFOREDAYJANAMT")
                          + social.getLong("M170_RPBEFOREDAYAMT");
        %>
        <tr>
          <td width="1"> </td>
          <td align="left"><%=social.getString("M360_ACCNAME")%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_CITYSPECIALDEPOSIT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_MMDABEFOREDAYJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_DEPOSITBEFOREDAYJANAMT"))%></td>
          <td align="right"><%=TextHandler.formatNumber(social.getString("M170_RPBEFOREDAYAMT"))%></td>
          <td align="right">&nbsp;</td>
          <td align="right"><%=TextHandler.formatNumber(row_social)%></td>
        </tr>
        <%   } 
           }
        %>
        <%   } 
           }
        %>
      </table>
    </td>
    <td valign="top">
      <table width="300" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;' bordercolor='#000000' class="basic">
        <tr style="font-weight:bold">
          <td colspan="2">�ֿ���������</td>
        </tr>
        <tr style="font-weight:bold">
          <td align="center" width="50%">(����)</td>
          <td align="center" width="50%">(����:õ��)</td>
        </tr>
        <%
           for(int i=0; i<10; i++) {
             if (addInfo != null ) {
        %>
        <tr>
          <td><%=addInfo.getString("M170_JEUNGGAMSEIPHANGMOK_"+(i+1)+"")%></td>
          <td align="right"><%=TextHandler.formatNumber(addInfo.getString("M170_JEUNGGAMSEIPAMT_"+(i+1)+""))%></td>
        </tr>
        <%  }
          } %>
        <tr style="font-weight:bold">
          <td align="center">(����)</td>
          <td align="center">(����:õ��)</td>
        </tr>
        <%
           for(int i=0; i<10; i++) {
             if (addInfo != null ) {
        %>
        <tr>
          <td><%=addInfo.getString("M170_JEUNGGAMSECHULHANGMOK_"+(i+1)+"")%></td>
          <td align="right"><%=TextHandler.formatNumber(addInfo.getString("M170_JEUNGGAMSECHULAMT_"+(i+1)+""))%></td>
        </tr>
        <%  }
          } %>
        <% if (addInfo != null ) { %>
        <tr>
          <td>���ϴ��</td>
          <td align="right"><%=TextHandler.formatNumber(addInfo.getLong("M170_JEUNGGAMSEIPAMT_10")-addInfo.getLong("M170_JEUNGGAMSECHULAMT_10"))%></td>
        </tr>        
        <tr>
          <td align="left" rowspan="3"><br>���༼ ���Ա�(���뿹��)<br></td>
          <td align="right" rowspan="3"><br><%=TextHandler.formatNumber(addInfo.getString("M170_JUHEANGORDINARYDEPOSIT"))%><br></td>
        </tr>
        <% } %>
      </table>
    </td>
  </tr>
</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="13"><b>3.��Ÿ</b></td>
  </tr>
</table>
<table width="1070" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr> 
    <td colspan="2"> 
      <div align="center"><b>����</b></div>
    </td>
    <td width="107"> 
      <div align="center"><b>�ļ�������ä</b></div>
    </td>
    <td width="87"> 
      <div align="center"><b></b></div>
    </td>
    <td width="68"> 
      <div align="center"><b></b></div>
    </td>
    <td width="72">&nbsp;</td>
    <td width="76"> 
      <div align="center"><b></b></div>
    </td>
    <td width="93"> 
      <div align="center"><b></b></div>
    </td>
    <td width="80"> 
      <div align="center"><b></b></div>
    </td>
    <td width="166"> 
      <div align="center"><b>�� ��</b></div>
    </td>
    <td width="152"> 
      <div align="center"><b></b></div>
    </td>
  </tr>

  <tr> 
    <td colspan="2"> 
      <div align="center">�߼����� </div>
    </td>
    <td width="107"> 
      <div align="right"></div>
    </td>
    <td width="87"> 
      <div align="right"></div>
    </td>
    <td width="68"> 
      <div align="right"></div>
    </td>
    <td width="72">&nbsp;</td>
    <td width="76"> 
      <div align="right"></div>
    </td>
    <td width="93"> 
      <div align="right"></div>
    </td>
    <td width="80">&nbsp;</td>
    <td width="166">&nbsp;</td>
    <td width="152">&nbsp;</td>
  </tr>
</table>
<table width="1070" border="0" cellspacing="0" cellpadding="0" class="basic">
  <tr> 
    <td colspan="13"><b>4.�հ�</b></td>
  </tr>
</table>
<% if (totAmt != null) { %>
<table width="1070" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr style="font-weight:bold"> 
    <td colspan="2" width="3%" align="center">����</td>
    <td width="10%" align="center">���ݿ���</td>
    <td width="9%" align="center">���ܿ���</td>
    <td width="10%" align="center">�������Ʈ</td>
    <td width="10%" align="center">���⿹��</td>
    <td width="9%" align="center">ȯ��ä��</td>
    <td width="9%" align="center">���뿹��</td>
    <td width="6%" align="center">�ļ���ä</td>
    <td width="10%" align="center">�ñݰ����հ�</td>
    <td width="10%" align="center">���༼(���뿹��)</td>
    <td width="10%" align="center">�ñݰ���ÿ����հ�</td>
    <td width="4%" align="center">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="2" align="center">��û</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_CITYSPECIALDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_JEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_DEPOSITJEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_RPJEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_CITYORDINARYDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("SIGUMGO_TOT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_JUHEANGORDINARYDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("SIGUMGO_TOT")+totAmt.getLong("M170_JUHEANGORDINARYDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="2" align="center">���</td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_ULSANSPECIALDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right">&nbsp;</td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_ULSANORDINARYDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("ULSAN_TOT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("ULSAN_TOT"))%></td>
    <td align="right">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="2" align="center">��</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_OFFICIALDEPOSITJANAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("M170_CITYSPECIALDEPOSIT")+totAmt.getLong("M170_ULSANSPECIALDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_JEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_DEPOSITJEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_RPJEUNGGAMAMT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("M170_CITYORDINARYDEPOSIT")+totAmt.getLong("M170_ULSANORDINARYDEPOSIT"))%></td>
    <td align="right">&nbsp;</td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("SIGUMGO_TOT")+totAmt.getLong("ULSAN_TOT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getString("M170_JUHEANGORDINARYDEPOSIT"))%></td>
    <td align="right"><%=TextHandler.formatNumber(totAmt.getLong("SIGUMGO_TOT")+totAmt.getLong("M170_JUHEANGORDINARYDEPOSIT")+totAmt.getLong("ULSAN_TOT"))%></td>
    <td align="right">&nbsp;</td>
  </tr>
</table>
<% } %>
</body>
</html>
