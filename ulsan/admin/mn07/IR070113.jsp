<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070113.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-18
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���Լ����ϰ�ǥ(ȸ�躰)
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%  
	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");
	List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");
    Integer totalCount = (Integer)request.getAttribute("page.mn07.totalCount");
    
    String cpage = request.getParameter("cpage");	// ���� ������ ��ȣ �ޱ�
	if( "".equals(cpage) ) {
		cpage = "1";
	}

    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=���Լ����ϰ�ǥ(ȸ�躰)_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
        response.setHeader("Content-Description", "JSP Generated Data");
    }
%>

<html>
<head>
<title>��걤���� ���� �� �ڱݹ��������ý���</title>
<%if(saveFile.equals("")){%>
<link href="../css/class.css" rel="stylesheet" type="text/css" />
<%}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%if(saveFile.equals("1")){%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">

<%}%>

<style>
<!--
.report {font-family: "arial","����", "Helvetica", "sans-serif";}
.report td.re01 {font-size:10px;}
.report td.re02 {font-size:8px;}
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}
-->

    :root p {font-size:20pt; padding-left:340px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html p {font-size:20pt; padding-left:340px;} /*IE8 ���� */   
 * + html p {font-size:20pt; padding-left:340px;} /*IE7 ���� */   
 *   html p {font-size:20pt; padding-left:340px;} /*IE6 ���� */ 

@media print {
  .noprint {
	  display:none;
	}
}
</style>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">

function init(){
}

function PAGE(cpage) {
    if(cpage != null) {
    document.sForm.cpage.value=cpage;
  } else {
    document.sForm.cpage.value="";
  }
  document.sForm.target = "_self";
  eSubmit(document.sForm);
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.action = "IR070113.etax";
    form.target = "_self";
    form.submit();
}

function goPDF(){
    var form =  document.forms[0];
    form.action = "IR070153.etax";
    form.target = "pdfFrame";
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
<form name="sForm"   method="post"      action="IR070113.etax">
<input type="hidden" name="cmd"         value="dayReport03">
<%if(saveFile.equals("")){%>
<input type="hidden" name="acc_year"    value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date"    value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type"    value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code"   value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code"    value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile"    value="<%=saveFile%>">    
<input type="hidden" name="cpage"       value="">
<%}%>

<div class="noprint">
<table width="960" align="center" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
	<tr> 
		<td width="960"> 
            <%if(saveFile.equals("")){%>
			<table width="960" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<img src="../img/btn_pdf.gif" alt="PDF" onClick="goPDF()" style="cursor:hand" align="absmiddle">
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
<% 
for (int i=0; i < reportList.size(); i++) {
    CommonEntity rowData = (CommonEntity)reportList.get(i);
%>

<table width="960" border="0" cellspacing="0" cellpadding="1">
    <tr height="250"> 
        <td colspan="10"> 
            <p><u><b>�� �� �� �� �� �� ǥ  (<%=request.getParameter("acc_year")%>)</b></u></p>
        </td>
        <td colspan="3" width="200" align="right" valign="middle"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����� ���
            %>
            <table width="150" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="25">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="25">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table width="960" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr> 
        <td width="870" colspan="7"><!--������: <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%>--></td>
        <td width="80" colspan="6" align="right">(����:��)</td>
    </tr>
</table>
<table id="dataList" width="960" border="1" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#000000" class="report">
    <tr height="50"> 
        <td rowspan="2" class="re01" width="100"> 
            <div align="center">ȸ���</div>
        </td>
        <td colspan="5" class="re01" width="350"> 
            <div align="center">����</div>
        </td>
        <td rowspan="2" class="re01" width="80"> 
            <div align="center">�ڱ�<br>������</div>
        </td>
        <td colspan="5" class="re01" width="350"> 
            <div align="center">����</div>
        </td>
        <td rowspan="2" class="re01" width="80"> 
            <div align="center">�ܾ�</div>
        </td>
    </tr>
    <tr height="50"> 
        <td class="re01" width="70"> 
            <div align="center">���ϴ���</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">���ϼ���</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">�������ݳ�</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">����<br>�����׵�</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">�Ѱ�</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">���ϴ���</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">��������</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">�ݳ���</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">����<br>�����׵�</div>
        </td>
        <td class="re01" width="70"> 
            <div align="center">�Ѱ�</div>
        </td>
    </tr>

    <tr height="50" class="re02"> 
        <td width="100" class="re01" align="center"><%=rowData.getString("ACCNAME")%></td>

        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPTOT"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTBAEJUNG"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONILTOT"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHUL"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULBANNAP"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONGJEONG"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULTOT"))%></div></td>

        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJAN"))%></div></td>
    </tr>
    </tr>
</table>
<table width="960" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
    <tr height="120">
        <td colspan="13"></td>
    </tr>
    <tr height="30">
        <td colspan="12"> 
             <span style="width:440"></span>�� �� �� �� �� �� ��.
        </td>
        <td rowspan="3" colspan="3">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
            <% } %>
        </td>
    </tr>
    <tr height="30">
        <td colspan="12"> 
             <span style="width:440"></span><%=acc_date.substring(0,4)%> �� <%=acc_date.substring(4,6)%> �� <%=acc_date.substring(6,8)%> ��</div>
        </td>
    </tr>
    <tr height="30">
        <td width="500" colspan="7"> 
            <div align="left">��������� ����¡���� ����</div>
        </td>
        <td width="450" colspan="5"> 
            <div align="right">
            �泲���� ����û����<br>�� �� �� �� �� �� ��</div>
        </td>
    </tr>
    <tr height="125">
        <td colspan="13"></td>
    </tr>
</table>

<%}%>
</div>

<div class="noprint">
<%// ������ %>
<%if(saveFile.equals("")){%>
<table width="960" align="center">
  <tr>
    <td align="center">
    <Br>
    <etax:pagelink totalCnt='<%=totalCount.intValue()%>' page='<%=cpage%>'	pageCnt='5' blockCnt='10'  />
    </td>
  </tr>
</table>
<%}%>
</div>

<%}else{%>
<table width="960" border="0" cellpadding="0" cellspacing="0" align="center">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>

</form>
<form name="pForm" method="post" action="IR070153.etax">
<iframe name="pdfFrame" width="0" height="0"></iframe>
</form>
</body>
</html>