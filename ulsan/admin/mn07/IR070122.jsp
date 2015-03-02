<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070122.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-13
* ���α׷�����		: �ϰ�/���� > ������ȸ > �߼ұ�� ������� ����,�����ϰ�ǥ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	CommonEntity reportList = (CommonEntity)request.getAttribute("page.mn07.reportList");
	CommonEntity endState = (CommonEntity)request.getAttribute("page.mn07.endState");

    //out.println("reportList::"+reportList);
  
    String gubun = "";
    String acc_name = "";
    String saveFile = request.getParameter("saveFile");
    
    String today = TextHandler.getCurrentDate();
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=�߼ұ�� ������� ����,�����ϰ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls"); 
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
.report {font-family: "arial","����", "Helvetica", "sans-serif"; border-top:0px solid #000000;border-right:0px solid #000000; border-left:0px solid #000000; border-bottom:0px solid #000000;}
.report td.re01 {font-size:6pt; border-top:1px solid #000000;border-right:1px solid #000000; border-left:1px solid #000000; border-bottom:1px solid #000000;}                               
.report td.re02 {font-size:5pt; border-top:1px solid #000000;border-right:1px solid #000000; border-left:1px solid #000000; border-bottom:1px solid #000000;}
.report td.re03 {font-size:6pt; border-top:0px solid #ffffff;border-right:0px solid #ffffff; border-left:0px solid #ffffff; border-bottom:0px solid #ffffff;}

    :root u {font-size:14pt; padding-left:300px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html u {font-size:14pt; padding-left:300px;} /*IE8 ���� */   
 * + html u {font-size:14pt; padding-left:300px;} /*IE7 ���� */   
 *   html u {font-size:14pt; padding-left:300px;} /*IE6 ���� */ 

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
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070122.etax">   
<input type="hidden" name="cmd" value="dayReport12">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">
<%
if (reportList.size() > 0 && reportList != null) { 
%>
<div id="box" align="center">
<div class="noprint">
<table width="960" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr height="20"> 
		<td width="960"> 
  <%if(saveFile.equals("")){%>
			<table width="960" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr height="20"> 
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


<table width="960" border="0" cellspacing="0" cellpadding="1">
    <tr height="80"> 
        <td colspan="12"> 
            <u><b>�߼ұ�� ������� ����,���� �ϰ�ǥ (<%=request.getParameter("acc_year")%>��)</b></u>
        </td>
	    <td colspan="2" width="200" align="right" valign="top"> 
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
<table width="960" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
  <tr height="20"> 
    <td colspan="3" rowspan="3" class="re01"> 
      <div align="center"><b>������</b></div>
    </td>
    <td colspan="3"  rowspan="2" class="re01"> 
      <div align="center"><b>���Աݾ�</b></div>
    </td>
    <td colspan="3" rowspan="2" class="re01"> 
      <div align="center"><b>����ݾ�</b></div>
    </td>
    <td rowspan="3" class="re01"> 
      <div align="center"><b>�ܾ�</b></div>
    </td>
    <td colspan="4" class="re01"> 
      <div align="center"><b>�ڱݿ��</b></div>
    </td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td rowspan="3" class="re03" valign="bottom"> 
      <div align="center">�ܾ���ġ</div>
    </td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td colspan="3" class="re01"> 
      <div align="center">����</div>
    </td>
    <td wclass="re01"> 
      <div align="center"></div>
    </td>
  </tr>
  <tr height="20"> 
    <td class="re01"> 
      <div align="center">���ϴ��� </div>
    </td>
    <td class="re01"> 
      <div align="center">���ϼ��� </div>
    </td>
    <td class="re01"> 
      <div align="center">�Ѱ� </div>
    </td>
    <td class="re01"> 
      <div align="center">���ϴ��� </div>
    </td>
    <td class="re01"> 
      <div align="center">�������� </div>
    </td>
    <td class="re01"> 
      <div align="center">�Ѱ� </div>
    </td>
    <td class="re01"> 
      <div align="center">���⿹�� </div>
    </td>
    <td class="re01"> 
      <div align="center">�ļ��� ����ä��</div>
    </td>
    <td class="re01"> 
      <div align="center">��Ź�� </div>
    </td>
    <td class="re01"> 
      <div align="center">���ݿ��� </div>
    </td>
  </tr>
  <tr height="20"> 
    <td colspan="3" class="re01"><div align="center">�Ѱ�(A+B+C+D+E+F)</div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTJEONSEIP"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTDAYSUIP"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSECHULJEONILTOT_1110100"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSECHUL1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSECHUL1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTJANAMT"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTJEONGGIETC"))%></div></td>
    <td class="re02"><div align="right"></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTLOAN"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTGONGGEUM"))%></div></td>
    <!--
		<%if(!endState.getString("END_STATE").equals("Y")){%>
	    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS10"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td colspan="3" class="re01"> 
      <div align="center">�濵���� �����ڱ�(A)</div>
    </td>
    <td width="70"  class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_A"))%><div></td>
    <td width="70"  class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("KYUNGYOUNGDAYSUIP"))%><div></td>
    <td width="70"  class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("KYUNGYOUNGTOTSUIP"))%><div></td>
    <td width="70"  class="re02">&nbsp;</td>
    <td width="70"  class="re02">&nbsp;</td>
    <td width="70"  class="re02">&nbsp;</td>
    <td width="70"  class="re02">&nbsp;</td>
    <td width="70"  class="re02">&nbsp;</td>
    <td width="70"  class="re02">&nbsp;</td>
    <td width="70"  class="re02">&nbsp;</td>
    <td width="70"  class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td width="40"  class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS11"))%><div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td rowspan="2" class="re01"><div align="center">���<br>����</div></td>
    <td class="re01" colspan="2">���⿹��(�̿�) </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1110100"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP1"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP1"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02">&nbsp;</td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td colspan="2" class="re01"> ��������(�̿�) </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1110200"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP2"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP2"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02">&nbsp;</td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td width="40" rowspan="4" class="re01"><div align="center">����<br>���� </div></td>
    <td width="40" rowspan="4" class="re01"><div align="center"><p>��</p><p>��</p></div></td>
    <td width="120" class="re01">���⿹�ݵ� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1120100"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP3"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP3"))%><div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS1"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01">�������Ա� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1120200"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP4"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP4"))%><div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS2"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01">��������(�������) </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1120300"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP5"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP5"))%><div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <!--
		<%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS3"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01"><div align="left">���հ���������� </div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1120400"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP6"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP6"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02"><div align="center"></div></td>
    <td class="re02"><div align="center"></div></td>
    <td class="re02"><div align="center"></div></td>
    <td class="re02"><div align="center"></div></td>
    <!--<%if(!endState.getString("END_STATE").equals("Y")){%>
	   <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS4"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="3"><div align="center">�߼ұ�� �ü��ڱ�(B+C+D+E) </div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("JUNGSOAMTSEIPJEONILTOT"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("JUNGSODAYSUIP"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("JUNGSOTOTSUIP"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02"></td>
    <td class="re02"></td>
    <td class="re02"></td>
    <td class="re02"></td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS12"))%><div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="3">â���� ����°�ȭ�����ڱ�(B)</td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_B"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("CHANGUPDAYSUIP"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("CHANGUPTOTSUIP"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS5"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="2"><div align="center">��ݿ��� </div></td>
    <td class="re01">��Ź�� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1210100"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP7"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP7"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
    <td class="re02">&nbsp;</td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="2"><div align="center">���ڼ��� </div></td>
    <td class="re01">���ڼ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1210200"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP8"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP8"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS5"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="3"><div align="center">�����簳�������ڱ�(C)</div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_C"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("MARKETDAYSUIP"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("MARKETTOTSUIP"))%><div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS6"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="2"><div align="center">��ݿ��� </div></td>
    <td class="re01">��Ź��</td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1220100"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP9"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP9"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
    <td class="re02">&nbsp;</td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="2"><div align="center">���ڼ��� </div></td>
    <td class="re01"> ���ڼ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1220200"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP10"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP10"))%><div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS1"))%></div></td>
    <%}%>
		-->
	</tr>
  <tr height="20"> 
    <td class="re01" colspan="3"><div align="center">���뱸�����������ڱ�(D)</div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_D"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("UTONGDAYSUIP"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("UTONGTOTSUIP"))%><div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS7"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="2"><div align="center">��ݿ��� </div></td>
    <td class="re01">��Ź��</td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1230100"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP11"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP11"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <!--
		<%if(!endState.getString("END_STATE").equals("Y")){%>
    <td class="re02">&nbsp;</td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="2"><div align="center">���ڼ��� </div></td>
    <td class="re01"> ���ڼ��� </td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1230200"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP12"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP12"))%><div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS7"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="3"><div align="center">���غ����ڱ�(E)</div></td>
    <td class="re02" width="78"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_E"))%></div></td>
    <td class="re02" width="68"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DISASTERDAYSUIP"))%></div></td>
    <td class="re02" width="55"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DISASTERTOTSUIP"))%></div></td>
    <td class="re02" width="68">&nbsp;</td>
    <td class="re02" width="68">&nbsp;</td>
    <td class="re02" width="55">&nbsp;</td>
    <td class="re02" width="52">&nbsp;</td>
    <td class="re02" width="51">&nbsp;</td>
    <td class="re02" width="104">&nbsp;</td>
    <td class="re02" width="75">&nbsp;</td>
    <td class="re02" width="108">&nbsp;</td>
   <!--
		<%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02" width="40"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS8"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="2"><div align="center">��ݿ��� </div></td>
    <td class="re01">��Ź��</td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1240100"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP13"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP13"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
    <td class="re02">&nbsp;</td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="2"><div align="center">���ڼ��� </div></td>
    <td class="re01">���ڼ���</td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1240200"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP14"))%></div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP14"))%></div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS8"))%></div></td>
    <%}%>
		-->
  </tr>
  <tr height="20"> 
    <td class="re01" colspan="3"><div align="center">�ü��ڱݴ�����޼�����(F)</div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("AMTSEIPJEONILTOT_1250100"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("DAYSUIP15"))%><div></td>
    <td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("TOTSUIP15"))%><div></td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
    <td class="re02">&nbsp;</td>
		<!--
    <%if(!endState.getString("END_STATE").equals("Y")){%>
	<td class="re02"><div align="right"><%=StringUtil.formatNumber(reportList.getString("SURPLUS9"))%></div></td>
    <%}%>
		-->
  </tr>
</table>
<table width="960" border="0" cellspacing="0" cellpadding="4" class="basic" align="center">
  <tr height="20"> 
    <td colspan="14" height="25" width="960"> 
       <span style="width:440px"></span>���Ͱ��� ������<br>
       <span style="width:450px"></span><%=request.getParameter("acc_date")%>
    </td>
    <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����) %>
    <td rowspan="2" width="80">
        <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
    </td>
    <% }%>
  </tr>
  <tr height="20">
    <td colspan="9">��걤�����߼ұ��������ݿ��� ����
    </td>
    <td colspan="5"> 
      <div align="right">�泲���� ����û����<br>
        �� �� �� �� �� �� �� </div>
    </td>
  </tr>
</table>
</div>

<%}else{%>
<table width="960" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>
</form>
</body>
</html>
