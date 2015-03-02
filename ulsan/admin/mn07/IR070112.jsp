<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070112.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-10
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���Լ����ϰ�ǥ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
   	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");
   	CommonEntity prtState = (CommonEntity)request.getAttribute("page.mn07.prtState");
    List<CommonEntity> reportList = (List<CommonEntity>)request.getAttribute("page.mn07.reportList");
		List<CommonEntity> reportList2 = (List<CommonEntity>)request.getAttribute("page.mn07.reportList2");
    
    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    String acc_date = StringUtil.replace(request.getParameter("acc_date"),"-","");

    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=���Լ��� �ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report td.re01 {font-size:6pt;}
.report td.re02 {font-size:6pt;}
.report td.re03 {font-size:6pt;}

    :root u {font-size:12pt; padding-left:400px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html u {font-size:12pt; padding-left:400px;} /*IE8 ���� */   
 * + html u {font-size:12pt; padding-left:400px;} /*IE7 ���� */   
 *   html u {font-size:12pt; padding-left:400px;} /*IE6 ���� */ 
 * + html p {padding-left:500px;} /*IE7 ���� */   
 *   html p {padding-left:500px;} /*IE6 ���� */ 

@media print {
  .noprint {
	  display:none;
	}
}
</style>
<% } %>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>	
<script language="javascript" src="../js/rowspan.js"></script>	
<script language="javascript">

function init(){
    <% if (reportList.size() > 0 && reportList != null) {%>
    cellMergeChk(document.all.dataList, 2, 0);
    <%}%>
		<% if (reportList2.size() > 0 && reportList2 != null) {%>
    cellMergeChk(document.all.dataList1, 2, 0);
    <%}%>
}

function goSave(val){
    var form = document.sForm;
    form.saveFile.value = val;
    form.target = "_self";
    form.action = "IR070112.etax";
    form.submit();
}

function goPDF(){
    var form =  document.forms[0];
    form.action = "IR070152.etax";
    form.target = "pdfFrame";
    form.submit();
}
   
var initBody; 

function goPrint() {
    factory.printing.header = ""; // Header�� �� ����  
		factory.printing.footer = ""; // Footer�� �� ����  
		factory.printing.portrait = false; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 0.3; // ���� ���� ������  
		factory.printing.topMargin = 0.0; // �� ���� ������  
		factory.printing.rightMargin = 0.3; // ������ ���� ������  
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		factory.printing.Print(true); // ����ϱ� 
}

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="20" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070112.etax">
<input type="hidden" name="cmd" value="dayReport02">
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
<table width="1050" align="center" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="1050"> 
      <%if(saveFile.equals("")){%>
			<table width="1050" border="0" cellspacing="0" cellpadding="0" class="btn">
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
<div id="box" align="center">
<table width="1040" border="0" cellspacing="0" cellpadding="2">
    <tr height="70"> 
        <td colspan="14"> 
            <u><b><%=request.getParameter("acc_year")%>�� ���Լ��� �ϰ�ǥ</b></u>
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
</table>
<table width="1040" border="0" cellspacing="0" cellpadding="2" class="basic">
    <tr> 
        <td colspan="14" width="870">������: <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%></td>
        <td colspan="4"  width="80" align="right">(����:��)</td>
    </tr>
</table>
<table id="dataList" width="1040" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#000000" class="report">
    <tr class="re01"> 
        <td colspan="2" rowspan="2" class="re01" align="center">ȸ���</td>
        <td colspan="5" class="re01" align="center">����</td>
        <td rowspan="2" class="re01" align="center">�ڱݹ�����</td>
        <td colspan="5" class="re01" align="center">����</td>
        <td rowspan="2" class="re01" align="center">�ܾ�</td>
        <td colspan="3" class="re01" align="center">�ڱݿ��</td>
          <%if(prtState.getString("PRT_STATE").equals("Y")){%><!-- �׿������Ծ׿� �ڷᰡ �����ϸ� -->
        <td rowspan="2" class="re01" align="center">�׿������Ծ�</td>
        <%}%>
    </tr>
    <tr class="re01"> 
        <td width="49" class="re01" align="center">���ϴ���</td>
        <td width="57" class="re01" align="center">���ϼ���</td>
        <td width="66" class="re01" align="center">�������ݳ�</td>
        <td width="68" class="re01" align="center">���������׵�</td>
        <td width="51" class="re01" align="center">�Ѱ�</td>
        <td width="60" class="re01" align="center">���ϴ���</td>
        <td width="60" class="re01" align="center">��������</td>
        <td width="51" class="re01" align="center">�ݳ���</td>
        <td width="72" class="re01" align="center">���������׵�</td>
        <td width="39" class="re01" align="center">�Ѱ�</td>
        <td width="63" class="re01" align="center">���⿹�ݵ�</td>
        <td width="56" class="re01" align="center">�����ܾ�</td>
        <td width="56" class="re01" align="center">������������</td>
    </tr>
    <% 
    for (int i=0; i < reportList.size()-1; i++) {
        CommonEntity rowData = (CommonEntity)reportList.get(i);
        
        if(!rowData.getString("ACCTYPENM").equals("")){
            gubun = rowData.getString("ACCTYPENM");
        }

        if(rowData.getString("SUMGUBUN").equals("A1") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;(��û)";
        }else if(rowData.getString("SUMGUBUN").equals("B3") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;������(�հ�)";
        }else if(!rowData.getString("ACCTYPE").equals("") && rowData.getString("SUMGUBUN").equals("")){
            acc_name = "&nbsp;&nbsp;"+ gubun + " �հ�";
        }else{
            acc_name = rowData.getString("ACCNAME");
        }        
    %>
    <tr class="re01"> 
        <td width="8"  class="re01"><%=gubun%></td>
        <td width="92" class="re01"><%=TextHandler.replace(acc_name, "�����繫��", "")%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPTOT"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTBAEJUNG"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONILTOT"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHUL"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULBANNAP"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONGJEONG"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULTOT"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJAN"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJEONGGI"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("GONGGEUMJANAMT"))%></td>
        <td class="re01" align="right"><%=StringUtil.formatNumber(rowData.getString("SEGYE_AMT"))%></td>
    
        <%if(prtState.getString("PRT_STATE").equals("Y")){%><!-- �׿������Ծ׿� �ڷᰡ �����ϸ� -->
        <td class="re01" align="right">
        <%=StringUtil.formatNumber(rowData.getString("AMTSURPLUS"))%>
        </td>
        <%}%>
    </tr>
    <%
					}
	  %>
</table>
<table width="1040" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr height="5">
        <td colspan="60"></td>
    </tr>
    <tr height="60">
        <td colspan="11"> 
            <p>&nbsp;���Ͱ��� ������<br><%=acc_date.substring(0,4)%>�� <%=acc_date.substring(4,6)%>�� <%=acc_date.substring(6,8)%>��</p>
        </td>
        <td colspan="4"> 
            <div align="right">�泲���� ����û����<br>�� �� �� �� �� �� �� ��</div>
        </td>
        <td rowspan="2" width="80" height="60">
            <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
            <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="60" height="60" align="right">
            <% } else {%>
                 &nbsp;<br><br><br><br><br><br>
            <% } %>
        </td>
    </tr>
</table>
<% } %>
<%
if (reportList2.size() > 0 && reportList2 != null) { 
%>
<p>&nbsp;</p>
<p>&nbsp;</p>
<table width="1040" border="0" cellspacing="0" cellpadding="1">
    <tr height="100"> 
        <td colspan="14"> 
            <u><b><%=request.getParameter("acc_year")%>�� ���Լ��� �ϰ�ǥ</b></u>
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
</table>
<table width="1040" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr> 
        <td colspan="14" width="870">������: <%=TextHandler.formatDate(acc_date,"yyyyMMdd","yyyy-MM-dd")%></td>
        <td colspan="4"  width="80" align="right">(����:��)</td>
    </tr>
</table>
<table id="dataList1" width="1040" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#000000" class="report">
    <tr> 
        <td colspan="2" rowspan="2" class="re01" align="center">ȸ���</td>
        <td colspan="5" class="re01" align="center">����</td>
        <td rowspan="2" class="re01" align="center">�ڱݹ�����</td>
        <td colspan="5" class="re01" align="center">����</td>
        <td rowspan="2" class="re01" align="center">�ܾ�</td>
        <td colspan="3" class="re01" align="center">�ڱݿ��</td>
         <%if(prtState.getString("PRT_STATE").equals("Y")){%><!-- �׿������Ծ��� �����ϸ� -->
        <td rowspan="2" class="re01" align="center">�׿������Ծ�</td>
        <%}%>
    </tr>
    <tr class="re01"> 
        <td width="49" class="re01" align="center">���ϴ���</td>
        <td width="57" class="re01" align="center">���ϼ���</td>
        <td width="66" class="re01" align="center">�������ݳ�</td>
        <td width="68" class="re01" align="center">���������׵�</td>
        <td width="51" class="re01" align="center">�Ѱ�</td>
        <td width="60" class="re01" align="center">���ϴ���</td>
        <td width="60" class="re01" align="center">��������</td>
        <td width="51" class="re01" align="center">�ݳ���</td>
        <td width="72" class="re01" align="center">���������׵�</td>
        <td width="39" class="re01" align="center">�Ѱ�</td>
        <td width="63" class="re01" align="center">���⿹�ݵ�</td>
        <td width="56" class="re01" align="center">�����ܾ�</td>
        <td width="56" class="re01" align="center">������������</td>
    </tr>
    <% 
        long AMTSEIPJEONILTOT = 0L;
        long AMTSEIP = 0L;
        long AMTSEIPGWAONAP = 0L;
        long AMTSEIPJEONGJEONG = 0L;
        long AMTSEIPTOT = 0L;
        long AMTBAEJUNG = 0L;
        long AMTSECHULJEONILTOT = 0L;
        long AMTSECHUL = 0L;
        long AMTSECHULBANNAP = 0L;
        long AMTSECHULJEONGJEONG = 0L;
        long AMTSECHULTOT = 0L;
        long AMTJAN = 0L;
        long AMTJEONGGI = 0L;
        long JEONGGIJAN = 0L;
        long SEGYE_AMT = 0L;
        long AMTSURPLUS = 0L;


    for (int i=0; i < reportList2.size()-3; i++) {
        CommonEntity rowData = (CommonEntity)reportList2.get(i);

        AMTSEIPJEONILTOT += rowData.getLong("AMTSEIPJEONILTOT");
        AMTSEIP += rowData.getLong("AMTSEIP");
        AMTSEIPGWAONAP += rowData.getLong("AMTSEIPGWAONAP");
        AMTSEIPJEONGJEONG += rowData.getLong("AMTSEIPJEONGJEONG");
        AMTSEIPTOT += rowData.getLong("AMTSEIPTOT");
        AMTBAEJUNG += rowData.getLong("AMTBAEJUNG");
        AMTSECHULJEONILTOT += rowData.getLong("AMTSECHULJEONILTOT");
        AMTSECHUL += rowData.getLong("AMTSECHUL");
        AMTSECHULBANNAP += rowData.getLong("AMTSECHULBANNAP");
        AMTSECHULJEONGJEONG += rowData.getLong("AMTSECHULJEONGJEONG");
        AMTSECHULTOT += rowData.getLong("AMTSECHULTOT");
        AMTJAN += rowData.getLong("AMTJAN");
        AMTJEONGGI += rowData.getLong("AMTJEONGGI");
        JEONGGIJAN += rowData.getLong("GONGGEUMJANAMT");
        SEGYE_AMT += rowData.getLong("SEGYE_AMT");
        AMTSURPLUS += rowData.getLong("AMTSURPLUS");


                   
        if(!rowData.getString("ACCTYPENM").equals("")){
            gubun = rowData.getString("ACCTYPENM");
        }

        if(rowData.getString("SUMGUBUN").equals("A1") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;��û(�հ�)";
        }else if(rowData.getString("SUMGUBUN").equals("B3") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;(������)";
				}else if(rowData.getString("SUMGUBUN").equals("E9") && rowData.getString("ACCCODE").equals("")){
            acc_name = "&nbsp;(��ȸ�������)";
        }else if(!rowData.getString("ACCTYPE").equals("") && rowData.getString("SUMGUBUN").equals("")){
            acc_name = "&nbsp;&nbsp;"+ gubun + " �հ�";
        }else{
            acc_name = rowData.getString("ACCNAME");
        }		
      %>
    <tr class="re02" height="10"> 
        <td width="10"  class="re01"><%=gubun%></td>
        <td width="106" class="re01"><%=acc_name%></td>
				<td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONILTOT"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIP"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPGWAONAP"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPJEONGJEONG"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSEIPTOT"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTBAEJUNG"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONILTOT"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHUL"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULBANNAP"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULJEONGJEONG"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTSECHULTOT"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJAN"))%></td>			
				<td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("AMTJEONGGI"))%></td>	 
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("GONGGEUMJANAMT"))%></td>
        <td class="re02" align="right"><%=StringUtil.formatNumber(rowData.getString("SEGYE_AMT"))%></td>
        <%if(prtState.getString("PRT_STATE").equals("Y")){%><!-- �׿������Ծ��� �����ϸ� -->
        <td align="right" class="re02">
        <%=StringUtil.formatNumber(rowData.getString("AMTSURPLUS"))%></td>
        <%}%>
    </tr>
    <% if ("03".equals(rowData.getString("ACCCODE")) && "B".equals(rowData.getString("ACCTYPE")) && "2010".equals(request.getParameter("acc_year"))) { %>
    <tr class="re02" height="10"> 
        <td width="10"  class="re01"><%=gubun%></td>
        <td width="106" class="re01">(���ȯ�����)</td>
				<td class="re02">
						<%=StringUtil.formatNumber(AMTSEIPJEONILTOT)%>
				</td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSEIP)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSEIPGWAONAP)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSEIPJEONGJEONG)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSEIPTOT)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTBAEJUNG)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSECHULJEONILTOT)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSECHUL)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSECHULBANNAP)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSECHULJEONGJEONG)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTSECHULTOT)%></td>
        <td class="re02"><%=StringUtil.formatNumber(AMTJAN)%></td>			
				<td class="re02"><%=StringUtil.formatNumber(AMTJEONGGI)%></td>	 
        <td class="re02"><%=StringUtil.formatNumber(JEONGGIJAN)%></td>
        <td class="re02">
            <!-- �����ܾ� -->
            <%=StringUtil.formatNumber(SEGYE_AMT)%>
            
       </td>
        <%if(prtState.getString("PRT_STATE").equals("Y")){%><!-- �׿������Ծ��� �����ϸ� -->
        <td class="re02">
        <%=StringUtil.formatNumber(AMTSURPLUS)%>
        </td>
        <%}%>
    </tr>
    <% } %>
    <%
			 }
		%>
</table>
<table width="1040" border="0" cellspacing="0" cellpadding="1" class="basic">
    <tr height="5">
        <td colspan="16"></td>
    </tr>
    <tr height="30">
        <td colspan="11"> 
            <p>&nbsp;���Ͱ��� ������<br><%=acc_date.substring(0,4)%>�� <%=acc_date.substring(4,6)%>�� <%=acc_date.substring(6,8)%>��</p>
        </td>
        <td colspan="4"> 
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
<% } %>

<% if (reportList.size() == 0) { %>
<table width="1040" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>

</form>
<form name="pForm" method="post" action="IR070152.etax">
<iframe name="pdfFrame" width="0" height="0"></iframe>
</form>
</body>
</html>
