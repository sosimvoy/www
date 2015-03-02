<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070123.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-13
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���Լ���������ϰ�ǥ
* ���α׷����		: 2010-10-08 ȸ�躰�� ���� �����ֵ��� ����.(paging) , �հ� ������ �߰� 
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
  
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
                                                      
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename= ���Լ���������ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls"); 
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
<style media="print">
.report {font-family: "arial","����", "Helvetica", "sans-serif";}
.report td.re01 {font-size:7pt;}
.report td.re02 {font-size:7pt;}

    :root u {font-size:16pt; padding-left:385px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html u {font-size:16pt; padding-left:385px;} /*IE8 ���� */   
 * + html u {font-size:16pt; padding-left:385px;} /*IE7 ���� */   
 *   html u {font-size:16pt; padding-left:385px;} /*IE6 ���� */ 

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
<form name="sForm" method="post" action="IR070123.etax">
<input type="hidden" name="cmd" value="dayReport13">
<input type="hidden" name="acc_year"    value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date"    value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type"    value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code"   value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code"    value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile"    value="<%=saveFile%>">
<input type="hidden" name="cpage"       value="">

<div class="noprint">
<table width="1000" border="0" cellspacing="0" cellpadding="1" class="basic" align="center">
	<tr> 
		<td width="1000"> 
  <%if(saveFile.equals("")){%>
			<table width="1000" border="0" cellspacing="0" cellpadding="0" class="btn">
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

<% 
for (int i=0; i < reportList.size(); i++) {
    CommonEntity rowData = (CommonEntity)reportList.get(i);

%>
<table width="1000" border="0" cellspacing="0" cellpadding="1">
    <tr height="130"> 
        <td> 
            <b><u>���Լ�������� �ϰ�ǥ</u></b>
        </td>
        <td colspan="3" width="200" align="right" valign="middle"> 
            <%
            if(!endState.getString("END_STATE").equals("Y")){  // �̸����ΰ�� (�ú���)
            %>
            <table width="150" height="50" align="right" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
                <tr height="26">
                    <td align="center" valign="bottom" style="border-bottom-style:none;">�̸��� �ڷ�</td>
                </tr>
                <tr height="26">
                    <td align="center" style="border-top-style:none;">(<%=TextHandler.formatDate(TextHandler.getCurrentTime(),"yyyyMMddHHmmss","a hh:mm:ss")%>)</td>
                </tr>
            </table>
            <%
            }
            %>
        </td>
    </tr>
</table>
<table width="1000" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td><b>ȸ �� : <%=rowData.getString("M360_ACCNAME")%></b> </td>
    <td> 
      <div align="right"><%=TextHandler.formatDate(request.getParameter("acc_date"),"yyyy-MM-dd","yyyy�� MM�� dd��")%></div>
    </td>
  </tr>
</table>
<table width="1000" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
  <tr height="26"> 
    <td colspan="2" rowspan="2" class="re01"> 
      <div align="center"><b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</b></div>
    </td>
    <td rowspan="2" width="92" class="re01"> 
      <div align="center"><b>�����ܾ�</b></div>
    </td>
    <td colspan="4" class="re01"> 
      <div align="center"><b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� (A)</b></div>
    </td>
    <td colspan="4" class="re01"> 
      <div align="center"><b>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� (B)</b></div>
    </td>
    <td width="84" class="re01"> 
      <div align="center"><b>�����ܾ�(C)</b></div>
    </td>
    <td width="45" rowspan="2" class="re01"> 
      <div align="center">�� ��</div>
    </td>
  </tr>
  <tr height="26">  
    <td width="80" class="re01"> 
      <div align="center">���ϴ��� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">�����Ա� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">��������,��ȯ </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">���ϴ��� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">���ϴ��� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">�������� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">��������,�ݳ� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">���ϴ��� </div>
    </td>
    <td width="80" class="re01"> 
      <div align="center">(C=(A)-B)</div>
    </td>
  </tr>
  <tr height="26"> 
    <td colspan="2" class="re01"> 
      <div align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("BEFOREC1TOT") + rowData.getLong("BEFOREC2TOT") + rowData.getLong("BEFOREC3TOT") + rowData.getLong("BEFOREC4TOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G1C2TOT") + rowData.getLong("G1C3TOT") + rowData.getLong("G1C4TOT") + rowData.getLong("G1C1TOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G1C1IB") + rowData.getLong("G1C2IB") + rowData.getLong("G1C3IB") + rowData.getLong("G1C4IB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G1C1BAN") + rowData.getLong("G1C2BAN") + rowData.getLong("G1C3BAN") + rowData.getLong("G1C4BAN"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G1C1NUGYE") + rowData.getLong("G1C2NUGYE") + rowData.getLong("G1C3NUGYE") + rowData.getLong("G1C4NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G2C2TOT") + rowData.getLong("G2C3TOT") + rowData.getLong("G2C4TOT") + rowData.getLong("G2C1TOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G2C1IB") + rowData.getLong("G2C2IB") + rowData.getLong("G2C3IB") + rowData.getLong("G2C4IB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G2C1BAN") + rowData.getLong("G2C2BAN") + rowData.getLong("G2C3BAN") + rowData.getLong("G2C4BAN"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G2C1NUGYE") + rowData.getLong("G2C2NUGYE") + rowData.getLong("G2C3NUGYE") + rowData.getLong("G2C4NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getLong("G1G2C1NUGYE") + rowData.getLong("G1G2C2NUGYE") + rowData.getLong("G1G2C3NUGYE") + rowData.getLong("G1G2C4NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"></div>
    </td>
  </tr>
  <tr height="26"> 
    <td rowspan="4" width="38" class="re01"> 
      <p align="center">��<br>��<br>��</p>
    </td>
    <td class="re01" align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC1TOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C1TOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C1IB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C1BAN"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C1NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C1TOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C1IB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C1BAN"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C1NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C1NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"></div>
    </td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���⿹�� </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC1D1"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGJEONGGIIBJEON"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGJEONGGIIBGEUM"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGJEONGGIIBJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C1D1"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGJEONGGIJIJEON"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGJEONGGIJIGEUB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGJEONGGIJIJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C1D1"))%></div></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C1D1"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"></div>
    </td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���ܿ��� </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC1D2"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGBYULDANIBJEON"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGBYULDANIBGEUM"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGBYULDANIBJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C1D2"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGBYULDANJIJEON"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGBYULDANJIGEUB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGBYULDANJIJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C1D2"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C1D2"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"></div>
    </td>
  </tr>
  <tr height="26"> 
    <td width="70" class="re01" align="center">���ݿ��� </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC1D3"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGGONGGEUMIBJEON"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGGONGGEUMIBGEUM"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGGONGGEUMIBJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C1D3"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGGONGGEUMJIJEON"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGGONGGEUMJIGEUB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOJEUNGGONGGEUMJIJEONG"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C1D3"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C1D3"))%></div>
    </td>
    <td class="re02">
      <div align="right"></div>
    </td>
  </tr>
  <tr height="26"> 
    <td rowspan="4" width="38" class="re01"> 
      <div align="center">��<br>��<br>��</div>
    </td>
    <td width="70" class="re01" align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC2TOT"))%></div>
    </td>
    <td class="re02">
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C2TOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C2IB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C2BAN"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C2NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C2TOT"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C2IB"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C2BAN"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C2NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C2NUGYE"))%></div>
    </td>
    <td class="re02"> 
      <div align="right"></div>
    </td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���⿹�� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC2D1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANJEONGGIIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANJEONGGIIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANJEONGGIIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C2D1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANJEONGGIJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANJEONGGIJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANJEONGGIJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C2D1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C2D1"))%></div></td>
    <td class="re02"><div align="right"></div></td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���ܿ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC2D2"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANBYULDANIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANBYULDANIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANBYULDANIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C2D2"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANBYULDANJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANBYULDANJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANBYULDANJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C2D2"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C2D2"))%></td>
    <td class="re02">&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���ݿ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC2D3"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANGONGGEUMIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANGONGGEUMIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANGONGGEUMIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C2D3"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANGONGGEUMJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANGONGGEUMJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BOGWANGONGGEUMJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C2D3"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C2D3"))%></div></td>
    <td class="re02">&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td rowspan="4" class="re01"> 
      <div align="center">����<br>�ݵ�<br>��Ÿ</div>
    </td>
    <td class="re01" align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC3TOT"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C3TOT"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C3IB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C3BAN"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C3NUGYE"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C3TOT"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C3IB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C3BAN"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C3NUGYE"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C3NUGYE"))%></div></td>
    <td class="re02">&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���⿹�� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC3D1"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGJEONGGIIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGJEONGGIIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGJEONGGIIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C3D1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGJEONGGIJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGJEONGGIJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGJEONGGIJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C3D1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C3D1"))%></div></td>
    <td class="re02">&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���ܿ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC3D2"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGBYULDANIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGBYULDANIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGBYULDANIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C3D2"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGBYULDANJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGBYULDANJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGBYULDANJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C3D2"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C3D2"))%></div></td>
    <td class="re02">&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���ݿ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC3D3"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGGONGGEUMIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGGONGGEUMIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGGONGGEUMIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C3D3"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGGONGGEUMJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGGONGGEUMJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_JABJONGGONGGEUMJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C3D3"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C3D3"))%></div></td>
    <td class="re02"></td>
  </tr>
  <tr height="26"> 
    <td rowspan="4" class="re01"> 
      <div align="center">�ΰ�<br>
        ��ġ��</div>
    </td>
    <td class="re01" align="center">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC4TOT"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C4TOT"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C4IB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C4BAN"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C4NUGYE"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C4TOT"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C4IB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C4BAN"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C4NUGYE"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C4NUGYE"))%></div></td>
    <td class="re02">&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���⿹�� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC4D1"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEJEONGGIIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEJEONGGIIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEJEONGGIIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C4D1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEJEONGGIJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEJEONGGIJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEJEONGGIJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C4D1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C4D1"))%></div></td>
    <td class="re02">&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���ܿ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC4D2"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEBYULDANIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEBYULDANIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEBYULDANIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C4D2"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEBYULDANJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEBYULDANJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEBYULDANJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C4D2"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C4D2"))%></div></td>
    <td class="re02">&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td class="re01" align="center">���ݿ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("BEFOREC4D3"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEGONGGEUMIBJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEGONGGEUMIBGEUM"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEGONGGEUMIBJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1C4D3"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEGONGGEUMJIJEON"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEGONGGEUMJIGEUB"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("M270_BUGASEGONGGEUMJIJEONG"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G2C4D3"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(rowData.getString("G1G2C4D3"))%></div></td>
    <td class="re02">&nbsp;</td>
  </tr>
</table>
<table width="1000" border="0" cellspacing="0" cellpadding="4" class="basic" align="center">
  <tr height="22">
    <td colspan="12"></td>
  </tr>
  <tr height="40"> 
    <td colspan="12"> 
       <span style="width:455px"></span>���� ���� ������.<br>
       <span style="width:450px"></span> <%=TextHandler.formatDate(request.getParameter("acc_date"),"yyyy-MM-dd","yyyy�� MM�� dd��")%> </div>
    </td>
    <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����) %>
    <td rowspan="2" width="80">
        <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
    </td>
    <% }%>
  </tr>
  <tr height="30">
    <td colspan="7">��걤���� <%=rowData.getString("M360_ACCNAME")%>�ⳳ�� ����</td>
    <td colspan="5"> 
      <div align="right">�� �� �� �� �� �� �� </div>
    </td>
  </tr>  
 
</table>
<%}%>
</div>

<div class="noprint">
<%// ������ %>
<%if(saveFile.equals("")){%>
<table width="950">
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
<table width="1000" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>
</form>
</body>
</html>
