<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070114.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-18
* ���α׷�����		: �ϰ�/���� > ������ȸ > �����ü��ϰ�ǥ
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
	CommonEntity reportRemark = (CommonEntity)request.getAttribute("page.mn07.reportRemark");
  
    String gubun = "";
    String semok_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=�����ü��ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report td.re01 {font-size:5pt;}
.report td.re02 {font-size:7pt;}
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 7pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 7pt; color: #333333; text-decoration: underline}

    :root p {padding-left:250px; font-size:18px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html p {padding-left:250px; font-size:18px;} /*IE8 ���� */   
 * + html p {padding-left:250px; font-size:18px;} /*IE7 ���� */   
 *   html p {padding-left:250px; font-size:18px;} /*IE6 ���� */ 

 *   html .report td.re01 {font-size:8px;}
 *   html .report td.re02 {font-size:9px;}


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
    <% if (reportList.size() > 0 && reportList != null) {%>
    cellMergeChk(document.all.dataList1, 2, 0);
    cellMergeChk(document.all.dataList2, 2, 0);
    cellMergeChk(document.all.dataList3, 2, 0);
    cellMergeChk(document.all.dataList4, 2, 0);
    cellMergeChk(document.all.dataList5, 2, 0);
    cellMergeChk(document.all.dataList6, 2, 0);
    <%}%>
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.submit();
}

function goPrint() {
    factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = true; // false �� �����μ�, true �� ���� �μ�  
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
<form name="sForm" method="post" action="IR070114.etax">
<input type="hidden" name="cmd" value="dayReport04">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="733" align="center" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="733"> 
            <%if(saveFile.equals("")){%>
			<table width="733" border="0" cellspacing="0" cellpadding="0" class="btn">
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

<%
if (reportList.size() > 0 && reportList != null) { 
%>
<div id="box" align="center">
<!-- PAGE 1 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>�� ��� �����ü� �ϰ�ǥ</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){ // �̸����ΰ�� (�ú���)
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList1" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>����</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>��û</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>���������ϻ����</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // �Ѱ� �� ���� �Ұ� ����ó��
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "�Ѱ�";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "���漼";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "���ܼ���";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "��������";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // ���� �Ұ� ����ó��
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;����� ���ܼ��� �Ұ�";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;�ӽ��� ���ܼ��� �Ұ�";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " �Ұ�";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00000"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00000"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00000"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02240"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02240"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02240"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">����¡��������</td>
        <td colspan="2" align="center" style=></td>
        <td colspan="2" align="right">�泲���� ����û����<br>��걤���ñݰ�</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="45">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-1</td>
    </tr>
</table>
<!-- //PAGE1 END -->


<!-- PAGE 2 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>�� ��� �����ü� �ϰ�ǥ</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ��
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList2" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>����</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>�ߺμҹ漭</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>���μҹ漭</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // �Ѱ� �� ���� �Ұ� ����ó��
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "�Ѱ�";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "���漼";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "���ܼ���";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "��������";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // ���� �Ұ� ����ó��
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;����� ���ܼ��� �Ұ�";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;�ӽ��� ���ܼ��� �Ұ�";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " �Ұ�";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02130"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02130"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02130"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02140"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02140"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02140"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">����¡��������</td>
        <td colspan="2" align="center" style="font-size:7pt;"></td>
        <td colspan="2" align="right">�泲���� ����û����<br>��걤���ñݰ�</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="45">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-2</td>
    </tr>
</table>
<!-- //PAGE2 END -->


<!-- PAGE 3 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>�� ��� �����ü� �ϰ�ǥ</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ��
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList3" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>����</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>����깰���Ž���</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>��ȭ����ȸ��</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // �Ѱ� �� ���� �Ұ� ����ó��
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "�Ѱ�";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "���漼";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "���ܼ���";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "��������";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // ���� �Ұ� ����ó��
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;����� ���ܼ��� �Ұ�";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;�ӽ��� ���ܼ��� �Ұ�";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " �Ұ�";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02220"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02220"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02220"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_02190"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_02190"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_02190"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">����¡��������</td>
        <td colspan="2" align="center" style="font-size:7pt;"></td>
        <td colspan="2" align="right">�泲���� ����û����<br>��걤���ñݰ�</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="45">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-3</td>
    </tr>
</table>
<!-- //PAGE3 END -->


<!-- PAGE 4 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
       <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>�� ��� �����ü� �ϰ�ǥ</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ��
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList4" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>����</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>�߱�û</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>����û</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // �Ѱ� �� ���� �Ұ� ����ó��
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "�Ѱ�";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "���漼";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "���ܼ���";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "��������";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // ���� �Ұ� ����ó��
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;����� ���ܼ��� �Ұ�";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;�ӽ��� ���ܼ��� �Ұ�";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " �Ұ�";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00110"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00110"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00110"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00140"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00140"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00140"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">����¡��������</td>
        <td colspan="2" align="center" style="font-size:7pt;"></td>
        <td colspan="2" align="right">�泲���� ����û����<br>��걤���ñݰ�</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="50">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-4</td>
    </tr>
</table>
<!-- //PAGE4 END -->

<!-- PAGE 5 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>�� ��� �����ü� �ϰ�ǥ</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ��
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList5" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>����</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>����û</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>�ϱ�û</b></div>
        </td>
    </tr>
    <tr height="20">  
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // �Ѱ� �� ���� �Ұ� ����ó��
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "�Ѱ�";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "���漼";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "���ܼ���";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "��������";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // ���� �Ұ� ����ó��
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;����� ���ܼ��� �Ұ�";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;�ӽ��� ���ܼ��� �Ұ�";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " �Ұ�";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00170"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00170"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00170"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00200"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00200"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00200"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">����¡��������</td>
        <td colspan="2" align="center" style="font-size:7pt;"></td>
        <td colspan="2" align="right">�泲���� ����û����<br>��걤���ñݰ�</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="50">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-5</td>
    </tr>
</table>
<!-- //PAGE 5 END -->


<!-- PAGE 6 START -->
<table width="733" border="0" cellspacing="0" cellpadding="1">
    <tr> 
        <td colspan="6"  height="65" valign="middle" style="padding-left:280px; font-size:12pt;"> 
            <b><u><%=request.getParameter("acc_year")%>�� ��� �����ü� �ϰ�ǥ</u></b>
        </td>
        <td colspan="2" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ��
            %>
            <table width="150" height="30" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="15">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="15">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table id="dataList6" width="733" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr height="20"> 
        <td colspan="2" rowspan="2" class="re02"> 
            <div align="center"><b>����</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>���ֱ�</b></div>
        </td>
        <td colspan="3" class="re02"> 
            <div align="center"><b>�հ�</b></div>
        </td>
    </tr>
    <tr height="20"> 
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϼ���</div>
        </td>
        <td width="85" class="re02"> 
            <div align="center">���ϱ�������</div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        // �Ѱ� �� ���� �Ұ� ����ó��
        if(rowData.getString("TYPE").equals("") && rowData.getString("GUBUN").equals("")){
            gubun = "�Ѱ�";
        }else if(rowData.getString("TYPE").equals("1") && rowData.getString("GUBUN").equals("")){
            gubun = "���漼";
        }else if(rowData.getString("TYPE").equals("2") && rowData.getString("GUBUN").equals("")){
            gubun = "���ܼ���";
        }else if(rowData.getString("TYPE").equals("3") && rowData.getString("GUBUN").equals("")){
            gubun = "��������";
        }else{
            gubun = rowData.getString("GUBUN");
        }
        
        // ���� �Ұ� ����ó��
        if(rowData.getString("GUBUN_CODE").equals("21") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;����� ���ܼ��� �Ұ�";
        }else if(rowData.getString("GUBUN_CODE").equals("22") && rowData.getString("M390_SEMOKCODE").equals("")){
            semok_name = "&nbsp;�ӽ��� ���ܼ��� �Ұ�";
        }else if((!rowData.getString("GUBUN_CODE").equals("") && rowData.getString("M390_SEMOKCODE").equals("")) || !rowData.getString("TYPE").equals("") && rowData.getString("GUBUN_CODE").equals("") ){
            semok_name = "&nbsp;&nbsp;"+ gubun + " �Ұ�";
        }else{
            semok_name = rowData.getString("M370_SEMOKNAME");
        }

    %>
    <tr> 
        <td class="re02" width="15"> 
            <div align="center"><%=gubun%></div>
        </td>
        <td class="re01"><%=semok_name%></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT_00710"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT_00710"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT_00710"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMT"))%></div></td>
        <td class="re01"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYAMTTOT"))%></div></td>
    </tr>
    <%}%>
</table>
<table width="733" border="0" cellspacing="0" cellpadding="0" class="report">
    <tr height="76">
        <td colspan="3" align="left">����¡��������</td>
        <td colspan="2" align="center" style="font-size:7pt;"><%=reportRemark.getString("M470_REMARK1")%><br><%=reportRemark.getString("M470_REMARK2")%><br><%=reportRemark.getString("M470_REMARK3")%></td>
        <td colspan="2" align="right">�泲���� ����û����<br>��걤���ñݰ�</td>
        <td width="85">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60"  height="60" align="right" valign="middle">
            <% } %>
        </td>
    </tr>
    <tr height="45">
        <td colspan="8" align="center" style="font-size:6pt;mso-number-format:'\@'">6-6</td>
    </tr>
</table>
<!-- //PAGE 6 END -->
</div>

<%}else{%>
<div align="center">
<table width="733" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
</div>
<%}%>

</form>
</body>
</html>