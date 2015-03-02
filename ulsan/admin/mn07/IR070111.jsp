<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070111.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-17
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���Ա��ϰ�ǥ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
    // ���� 
	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");

	List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");

    String gubun = "";
    String semok_name = "";
    String saveFile = request.getParameter("saveFile");
  
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=���Ա��ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report {font-family: "arial","����", "Helvetica", "sans-serif";}
.report td.re01 {font-size:7pt;}
.report td.re02 {font-size:6pt;}
.report td.re03 {font-size:5pt;}

    :root u {font-size:14pt; padding-left:100px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html u {font-size:14pt; padding-left:100px;} /*IE8 ���� */   
 * + html u {font-size:14pt; padding-left:100px;} /*IE7 ���� */   
 *   html u {font-size:14pt; padding-left:100px;} /*IE6 ���� */ 

@media print {
  .noprint {
	  display:none;
	}
}

</style>
<%}%>

<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript" src="../js/tax_common.js"></script>	
<script language="javascript" src="../js/report.js"></script>	

<script language="javascript">

function init(){
    <% if (reportList.size() > 0 && reportList != null) {%>
    cellMergeChk(document.all.dataList, 1, 0);
    <%}%>
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.action = "IR070111.etax";
    form.target = "_self";
    form.submit();
}
    
function goPDF(){
    var form =  document.forms[0];
    form.action = "IR070151.etax";
    form.target = "pdfFrame";
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = true; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 1.0; // ���� ���� ������  
		factory.printing.topMargin = 0.5; // �� ���� ������  
		factory.printing.rightMargin = 1.0; // ������ ���� ������  
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		factory.printing.Print(true); // ����ϱ� 
}
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070111.etax">
<input type="hidden" name="cmd" value="dayReport01">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="733" border="0" align="center" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="733"> 
            <%if(saveFile.equals("")){%>
			<table width="733" border="0" cellspacing="0" cellpadding="0" class="btn">
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
<table width="733" border="0" cellspacing="0" cellpadding="1" class="report">
    <tr height="30"> 
        <td>&nbsp;</td>
        <td colspan="5" height="15"> 
            <b><u>��걤���ü� ���Ա� �ϰ�ǥ</u></b>
        </td>
        <td colspan="4" width="200" align="right" valign="top"> 
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
    <tr height="20">
        <td width="133" class="re01">ȸ��� : <%=request.getParameter("acc_year")%>�⵵</td>
        <td style="padding-left:190px;" colspan="5" class="re01">(<%=request.getParameter("acc_date")%>)</td>
    </tr> 
		<tr height="20">
        <td colspan="2" class="re01">�Ұ�û : <%=request.getParameter("part_name")%></td>
        <td colspan="8" class="re01"><div align="right">�泲���� ��걤���ñݰ�</div></td>
    </tr>
</table>

<table id="dataList" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="6"> 
        <td width="40" class="re02"> 
            <div align="center">����</div>
        </td>
        <td width="143" class="re02"> 
            <div align="center">�����</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">ȯ��</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">ȯ�δ���</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">����</div>
        </td>
        <td width="110" class="re02"> 
            <div align="center">���ϴ���</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);

        if(rowData.getString("GUBUN").equals("")){
            gubun = "�� ��"; 
        }else{
            gubun = rowData.getString("GUBUN"); 
        }

        if(rowData.getString("M370_SEMOKNAME").equals("")){
            semok_name = "&nbsp;&nbsp;�� ��"; 
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME"); 
        }
         if(rowData.getString("GUBUN").equals("")){
            gubun = "�� ��"; 
            semok_name = ""; 
        }else{
            gubun = rowData.getString("GUBUN"); 
        }

    %>
    <tr height="6">
        <td class="re02"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re03"><%=semok_name%></td>
        <td class="re03"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIP"))%></div>
        </td>
        <td class="re03">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPGWAONAP"))%></div>
        </td>
        <td class="re03"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPGWAONAPTOT"))%></div>
        </td>
        <td class="re03">
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("M220_AMTSEIPJEONGJEONG"))%></div>
        </td>
        <td class="re03"> 
            <div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_TOT"))%></div>
        </td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr height="60">
        <td colspan="2"> 
            <p>&nbsp;�� �� ¡ �� �� �� ��</p>
        </td>
        <td colspan="5"> 
            <div align="right">�泲���� ����û����<br>�� �� �� �� �� �� �� ��</div>
        </td>
        <td rowspan="2" width="80">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60" height="60" align="right">
            <% } %>
        </td>
    </tr>
</table>
</div>

<%}else{%>
<table width="733" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>
</form>

<form name="pForm" method="post" action="IR070151.etax">
<iframe name="pdfFrame" width="0" height="0"></iframe>
</form>
</body>
</html>