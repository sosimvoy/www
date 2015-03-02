<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070120.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-13
* ���α׷�����		: �ϰ�/���� > ������ȸ > ����ȯ��������ִ�åƯ��ȸ�� ���Լ����ϰ�ǥ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
    List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");
	
    CommonEntity totReportList = (CommonEntity)request.getAttribute("page.mn07.totReportList");

    CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");
  
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=����ȯ��������ִ�å Ư��ȸ�輼�Լ����ϰ�ǥ" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
        response.setHeader("Content-Description", "JSP Generated Data");
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%if(saveFile.equals("1")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
<%}%>
<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<%if(saveFile.equals("")){%>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<style>
.report {font-family: "arial","����", "Helvetica", "sans-serif"; border-top:0px solid #000000;border-right:0px solid #000000; border-left:0px solid #000000; border-bottom:0px solid #000000; align:center;}
.report td.re01 {font-size:7pt; border-top:1px solid #000000;border-right:1px solid #000000; border-left:1px solid #000000; border-bottom:1px solid #000000;}                               
.report td.re02 {font-size:7pt; border-top:1px solid #000000;border-right:1px solid #000000; border-left:1px solid #000000; border-bottom:1px solid #000000;}
.report td.re03 {font-size:7pt; border-top:0px solid #ffffff;border-right:0px solid #ffffff; border-left:0px solid #ffffff; border-bottom:0px solid #ffffff;}

    :root p {font-size:14pt; padding-left:290px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html p {font-size:14pt; padding-left:290px;} /*IE8 ���� */   
 * + html p {font-size:14pt; padding-left:290px;} /*IE7 ���� */   
 *   html p {font-size:14pt; padding-left:290px;} /*IE6 ���� */ 

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<%}%>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = false; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 1.0; // ���� ���� ������  
		factory.printing.topMargin = 1.0; // �� ���� ������  
		factory.printing.rightMargin = 1.0; // ������ ���� ������  
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		factory.printing.Print(true); // ����ϱ� 
}

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070120.etax">
<input type="hidden" name="cmd" value="dayReport10">
<input type="hidden" name="acc_year"    value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date"    value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type"    value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code"   value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code"    value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile"    value="<%=saveFile%>">

<div class="noprint">
<table width="1050" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
	<tr> 
		<td width="1050"> 
  <%if(saveFile.equals("")){%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<img src="../img/btn_print.gif" alt="�μ�"  onClick="goPrint();" style="cursor:hand" align="absmiddle">		  
					</td>
				</tr>
			</table>
   <%}%>
		</td>
	</tr>
</table>
</div>

<%
if (reportList.size() > 0 && reportList != null) { 
%>

<div id="box" align="center">
<table width="1050" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr height="50">
        <td colspan="15"></td>
    </tr>
    <tr height="150"> 
        <td colspan="13"> 
            <p><b><u>����ȯ��������ִ�å Ư��ȸ�� ���Լ��� �ϰ�ǥ (<%=request.getParameter("acc_year")%>)</u></b></p>
        </td>
		<td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ�� (�ú���)
            %>
            <table width="150" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="35">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="35">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table width="1050" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
  <tr height="50"> 
    <td rowspan="2" colspan="2" class="re01"> 
      <div align="center"><b>ȸ���</b></div>
    </td>
    <td colspan="5" class="re01"> 
      <div align="center"><b>����</b></div>
    </td>
    <td colspan="5" class="re01"> 
      <div align="center"><b>����</b></div>
    </td>
    <td rowspan="2" width="78" class="re01"> 
      <div align="center"><b>�ܾ�</b></div>
    </td>
    <td colspan="2" class="re01"> 
      <div align="center"><b>�ڱݿ��</b></div>
    </td>
    
    <%if(!endState.getString("END_STATE").equals("Y")){%><!-- �̸����ø� -->
     <td width="30" rowspan="2" class="re03"> 
      <div align="center">�׿������Ծ�</div>
    </td>
	 <td width="30" rowspan="2" class="re03"> 
      <div align="center">�ܾ���ġ</div>
    </td>
    <%}%>
  </tr>	
  <tr height="50"> 
    <td width="80" class="re01"> 
      <div align="center">���ϴ��� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">���ϼ��� </div>
    </td>
    <td width="55" class="re01"> 
      <div align="center">�������ݳ� </div>
    </td>
    <td width="55" class="re01"> 
      <div align="center">��������� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">�Ѿ�</div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">���ϴ��� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">�������� </div>
    </td>
    <td width="35" class="re01"> 
      <div align="center">�ݳ��� </div>
    </td>
    <td width="55" class="re01"> 
      <div align="center">��������� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">�Ѿ� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">���⿹�ݵ� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">�����ܾ� </div>
    </td>
  </tr>

  <tr height="50"> 
    <td colspan="2" class="re01"> 
      <div align="center"width="70"><%=totReportList.getString("M360_ACCNAME")%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSEIPJEONILTOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSEIP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSEIPGWAONAP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSEIPJEONGJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("ALLSEIPTOTAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSECHULJEONILTOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSECHUL"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSECHULBANNAP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTSECHULJEONGJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("ALLSECHULTOTAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("ALLJANAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTJEONGGI"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("AMTGONGGEUM"))%></div>
    </td>
    <%if(!endState.getString("END_STATE").equals("Y")){%><!-- �̸����ø� -->
	<td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("SUMSURPLUS"))%></div>
    </td>
	<td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("SURPLUSJAN"))%></div>
    </td>
    <%}%>
  </tr>
  <% 
   for (int i=0; i < reportList.size(); i++) {
		CommonEntity rowData = (CommonEntity)reportList.get(i);
  %>
  <tr height="50"> 
    <td width="10" class="re01">&nbsp;</td>
    <td class="re01" width="70"> 
      <div align="left"><%=rowData.getString("M360_ACCNAME")%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPJEONILTOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPGWAONAP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPJEONGJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("SEIPTOTAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSECHULJEONILTOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSECHUL"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSECHULBANNAP"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSECHULJEONGJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("SECHULTOTAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("JANAMT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTJEONGGI"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTGONGGEUM"))%></div>
    </td>

	 <%if(!endState.getString("END_STATE").equals("Y")){%><!-- �̸����ø� -->
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("SUMSURPLUS"))%></div>
    </td>
		 <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(totReportList.getString("SURPLUSJAN"))%></div>
    </td>
    <%}%>
  </tr>
<%}%>
</table>
<table width="1050" border="0" cellspacing="0" cellpadding="4" class="basic" align="center">
  <tr height="100">
    <td colspan="15"></td>
  </tr>
  <tr> 
    <td colspan="14"> 
       <span style="width:470px"></span>���� ���� ������.<br>
			 <span style="width:480px"></span><%=request.getParameter("acc_date")%>
    </td>
    <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����) %>
    <td rowspan="2" width="80">
        <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
    </td>
    <% }%>
  </tr>
  <tr>
    <td colspan="8">����ȯ��������ִ�åƯ��ȸ�� ¡����</td>
    <td colspan="6"> 
      <div align="right">�泲���� ����û����<br>��&nbsp; ��&nbsp; ��&nbsp; ��&nbsp;&nbsp;��&nbsp; ��&nbsp; ��</div>
    </td>
  </tr>
  <tr height="100">
    <td colspan="15"></td>
  </tr>
</table>
</div>

<%}else{%>
<table width="1050" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>
</form>
</body>
</html>
