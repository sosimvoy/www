<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070118.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-13
* ���α׷�����		: �ϰ�/���� > ������ȸ > ��������ü��δ�ݼ����ϰ�ǥ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	CommonEntity endState   = (CommonEntity)request.getAttribute("page.mn07.endState");
	CommonEntity reportData = (CommonEntity)request.getAttribute("page.mn07.reportData");
    //out.println("reportList::"+reportList);
  
    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=��������ü��δ�ݼ����ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report td.re02 {font-size:7pt;}

    :root u {font-size:14pt; padding-left:315px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html u {font-size:14pt; padding-left:315px;} /*IE8 ���� */   
 * + html u {font-size:14pt; padding-left:315px;} /*IE7 ���� */   
 *   html u {font-size:14pt; padding-left:315px;} /*IE6 ���� */ 

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
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">

function init(){
}

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
<form name="sForm" method="post" action="IR070118.etax">
<input type="hidden" name="cmd" value="dayReport08">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<%
if (reportData != null) { 
%>

<div class="noprint">
<table width="975" align="center" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="975"> 
            <%if(saveFile.equals("")){%>
			<table width="975" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<!-- <img src="../img/btn_pdf.gif" alt="PDF" onClick="goPDF()" style="cursor:hand" align="absmiddle"> -->
						<img src="../img/btn_print.gif" alt="�μ�"  onClick="goPrint();" style="cursor:hand" align="absmiddle">		  
					</td>
				</tr>
			</table>
            <%}%>
		</td>
	</tr>
</table>
</div>

<div id="box" align="center">

<table width="975" border="0" cellspacing="0" cellpadding="1">
    <tr height="70"> 
        <td colspan="7"> 
            <u><b><%=request.getParameter("acc_year")%>�� ��������ü��δ�� �����ϰ�ǥ</b></u>
        </td>
        <td colspan="4" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ�� (�ú���)
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
    <tr height="25">
        <td colspan="7" align="left">������ : <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%></td>
        <td colspan="4" align="right">(����:��)</td>
    </tr>
</table>
<table width="975" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td class="re01" colspan="2"> 
            <div align="center"><b>�� ��(99) </b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>��������ü��δ�� </b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>���ڼ��� </b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>�̿��� </b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>�������Ա� </b></div>
        </td>
        <td class="re01" width="80"> 
            <div align="center"><b></b></div>
        </td>
        <td class="re01" width="80"> 
            <div align="center"><b></b></div>
        </td>
        <td class="re01" width="80"> 
            <div align="center"><b></b></div>
        </td>
        <td class="re01" width="80"> 
            <div align="center"><b></b></div>
        </td>
        <td class="re01" width="115"> 
            <div align="center"><b>�� �� </b></div>
        </td>
    </tr>
    <tr height="15"> 
        <td class="re01" rowspan="6" width="15"> 
            <p align="center">��<br>û</p>
        </td>
        <td class="re01" width="115">���ϱ������� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">���ϼ��� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">������ </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_05"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">������ ���� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_05"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">���� ���� �� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_04"))%></div></td>
        <td class="re02">&nbsp;</td>                                                         
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">���ϱ������� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    
    <% for (int i=0; i < 3; i++) {%>
    <tr height="10"> 
        <td rowspan="6">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr height="10"> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <%}%>

    <tr height="15"> 
        <td class="re01" rowspan="6" width="22"> 
            <p align="center">��<br>��</p>
        </td>
        <td class="re01" width="78">���ϱ������� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("JEONIL_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">���ϼ��� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAY_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">������ </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAO_05"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">������ ���� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOTOT_05"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">���� ���� �� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_04"))%></div></td>
        <td class="re02">&nbsp;</td>                                                         
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("GWAOMOK_99"))%></div></td>
    </tr>
    
    <tr height="15"> 
        <td class="re01">���ϱ������� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02">&nbsp;</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportData.getString("TODAYTOT_99"))%></div></td>
    </tr>
</table>
<table width="975" border="0" cellspacing="0" cellpadding="4" class="basic">
    <tr height="25"> 
        <td colspan="10"> 
            <span style="width:440px"></span><%=acc_date.substring(0,4)%>�� <%=acc_date.substring(4,6)%>�� <%=acc_date.substring(6,8)%>��
        </td>
        <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����) %>
        <td rowspan="2" width="60">
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60" height="60" align="right">
        </td>
        <% }%>
    </tr>
    <tr>
        <td colspan="2" width="300">�� �� ¡ �� �� ����
        </td>
        <td colspan="8" width="675"> 
            <div align="right">�泲���� ����û����<br>��&nbsp; ��&nbsp; ��&nbsp; ��&nbsp;&nbsp;��&nbsp; ��&nbsp; ��</div>
        </td>
    </tr>
</table>
</div>

<%}else{%>
<table width="975" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>