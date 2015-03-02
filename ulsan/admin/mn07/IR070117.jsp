<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070117.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-13
* ���α׷�����		: �ϰ�/���� > ������ȸ > ������Ư��ȸ�輼���ϰ�ǥ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");
	List<CommonEntity> reportList1 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList1");
	List<CommonEntity> reportList2 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList2");
	List<CommonEntity> reportList3 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList3");
    
	CommonEntity totalData = (CommonEntity)request.getAttribute("page.mn07.totalData");
    //out.println("reportList1::"+reportList1);
  
    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=������Ư��ȸ�輼���ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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

    :root p {font-size:14pt; padding-left:370px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html p {font-size:14pt; padding-left:370px;} /*IE8 ���� */   
 * + html p {font-size:14pt; padding-left:370px;} /*IE7 ���� */   
 *   html p {font-size:14pt; padding-left:370px;} /*IE6 ���� */ 

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
<form name="sForm" method="post" action="IR070117.etax">
<input type="hidden" name="cmd" value="dayReport07">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">
<table width="1050" align="center" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr height="18"> 
		<td width="1050"> 
            <%if(saveFile.equals("")){%>
			<table width="1050" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr height="18"> 
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
if (reportList1.size() > 0 && reportList1 != null) { 
%>

<div id="box" align="center">
<table width="1050" border="0" cellspacing="0" cellpadding="0">
    <tr height="40"> 
        <td colspan="11"> 
            <p><u><b><%=request.getParameter("acc_year")%>�� ������ Ư��ȸ�� �����ϰ�ǥ</b><u></p> 
        </td>
        <td colspan="3" width="200" align="right" valign="top"> 
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
</table>
<table width="1050" border="0" cellspacing="0" cellpadding="0" class="basic">
    <tr> 
        <td colspan="7" width="300">������ : <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%></td>
        <td colspan="7" width="685"> 
            <div align="right">(����:��)</div>
        </td>
    </tr>
</table>
<table id="dataList" width="1050" border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
    <tr> 
        <td colspan="2" class="re01"> 
            <div align="center"><b>����(99)</b></div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">���·� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">�������ߺδ�� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">���ڼ��� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">��Ÿ����</div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">��Ÿ����� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">�õ����ȯ�ݼ��� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">ü��ó�м��� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">�̿��� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">������ݼ��� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">�������Ա� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">�������� </div>
        </td>
        <td width="80" class="re01"> 
            <div align="center">�� ��</div>
        </td>
    </tr>
    <!-- ��û START -->
    <% 
    for (int i=0; i < reportList1.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList1.get(i);
    %>
    <tr>
        <td class="re01" rowspan="6" width="15"><%=rowData.getString("M350_PARTNAME")%></td>
        <td class="re01" width="90">���ϱ�������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���ϼ���</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">������ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���� ���� ��</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���ϱ�������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    <%}%>
    <!--// ��û END -->

    <!-- ���������ϼ� START -->
    <% 
    for (int i=0; i < reportList3.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList3.get(i);
    %>
    <tr>
        <td class="re01" rowspan="6" width="15"><%=rowData.getString("M350_PARTNAME")%></td>
        <td class="re01" width="90">���ϱ�������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���ϼ���</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAY_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAO_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">������ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���� ���� ��</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("GWAOMOK_99"))%></div></td>
    </tr>
    <tr height="3">
        <td class="re01">���ϱ�������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    <%}%>
    <!--// ���������ϼ� END -->

    <!-- ��û START -->
    <% 
    for (int i=0; i < reportList2.size(); i++) {
        CommonEntity rowData = (CommonEntity)reportList2.get(i);
    %>
    <tr>
        <td class="re01" rowspan="6"><%=rowData.getString("M350_PARTNAME")%></td>
        <td class="re01">���ϱ������� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("JEONIL_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���⵵ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWAMT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���⵵ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("NOWTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���⵵ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTAMT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���⵵ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("PASTTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���ϱ�������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    <%}%>
    <!--// ��û END -->

    <!-- �հ� START -->
    <tr>
        <td class="re01" rowspan="8">�հ�</td>
        <td class="re01">���ϱ������� </td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("JEONIL_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���⵵ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWAMT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���⵵ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("NOWTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���⵵ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTAMT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���⵵ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("PASTTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAO_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">������ ����</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("GWAOTOT_99"))%></div></td>
    </tr>
    <tr>
        <td class="re01">���ϱ�������</td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_01"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_02"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_03"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_04"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_05"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_06"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_07"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_08"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_09"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_10"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_11"))%></div></td>
        <td class="re02"><div align="right"><%=StringUtil.formatNumber(totalData.getString("TODAYTOT_99"))%></div></td>
    </tr>
    <!--// �հ� END -->
</table>
<table width="1050" border="0" cellspacing="2" cellpadding="1" class="basic">
    <tr> 
        <td colspan="10"> 
             <span style="width:465px"></span><%=acc_date.substring(0,4)%>�� <%=acc_date.substring(4,6)%>�� <%=acc_date.substring(6,8)%>��
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

</div>


<%}else{%>
<table width="1050" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>

</form>
</body>
</html>