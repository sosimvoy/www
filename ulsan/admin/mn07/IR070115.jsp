<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070115.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-13
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���ܼ����ϰ�ǥ
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
    //out.println("reportList::"+reportList);
  
    String part_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    String acc_date = TextHandler.replace(request.getParameter("acc_date"),"-","");
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=���ܼ����ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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

    :root p {font-size:15pt; padding-left:285px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html p {font-size:15pt; padding-left:285px;} /*IE8 ���� */   
 * + html p {font-size:15pt; padding-left:285px;} /*IE7 ���� */   
 *   html p {font-size:15pt; padding-left:285px;} /*IE6 ���� */ 

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
<form name="sForm" method="post" action="IR070115.etax">
<input type="hidden" name="cmd" value="dayReport05">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<%
if (reportList.size() > 0 && reportList != null) { 
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
    <tr height="120"> 
        <td colspan="4"> 
            <p><u><b><%=request.getParameter("acc_year")%>�� ���ܼ��� �ϰ�ǥ (��û,��,��û,�����) </b><u></p>
        </td>
        <td colspan="3" width="200" align="right" valign="top"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ��
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
<table width="975" border="0" cellspacing="2" cellpadding="1" class="basic">
    <tr height="40"> 
        <td width="475">������ : <%=request.getParameter("acc_date")%></td>
        <td width="500"> 
            <div align="right">(����:��)</div>
        </td>
    </tr>
</table>
<table id="dataList" width="975" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
    <tr height="25"> 
        <td> 
            <div align="center"><b>�����</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>���ϱ�������</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>���ϼ���</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>ȯ��</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>ȯ�δ���</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>����������</b></div>
        </td>
        <td width="120"> 
            <div align="center"><b>���ϱ�������</b></div>
        </td>
    </tr>
    <% 
    for (int i=0; i < reportList.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);

        if(rowData.getString("M350_PARTNAME").equals("")){
            part_name = "<center>�� ��</center>";
        }else{
            part_name = rowData.getString("M350_PARTNAME");
        }
    %>
    <tr height="20"> 
        <td> 
            <div align="center"><%=part_name%></div>
        </td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("HWANBUTOT"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></div></td>
        <td><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT"))%></div></td>
    </tr>
    <%}%>
</table>
<br>
<table width="975" border="0" cellspacing="2" cellpadding="1" class="basic">
    <tr height="30"> 
        <td colspan="6"> 
            <span style="width:435px"></span>�̿� ���� ������.
        </td>
        <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����) %>
        <td rowspan="4" width="75">
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="70" height="70" align="right">
        </td>
        <% }%>
    </tr>
    <tr> 
        <td colspan="6"> 
            <span style="width:437px"></span><%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy��MM��dd��")%>
        </td>
    </tr>
    <tr> 
        <td colspan="6"> 
            <div align="right">�泲���� ����û����</div>
        </td>
    </tr>
    <tr> 
        <td colspan="6"> 
            <div align="right">��&nbsp; ��&nbsp; ��&nbsp; ��&nbsp;&nbsp;��&nbsp; ��&nbsp; ��</div>
        </td>
    </tr>
</table>
</div>

<%}else{%>
<table width="975" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>