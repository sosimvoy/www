<%
/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR070710.jsp
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-09-27
* ���α׷�����	   : �ϰ�/���� > ���������հ�ǥ��ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.etax.entity.*" %>
<%@ page import = "com.etax.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
String saveFile = request.getParameter("saveFile");
%>
<%if(saveFile.equals("")){%>
<%@include file="../menu/mn07.jsp" %>
<%}%>
<%

  List<CommonEntity> taxTransfer  = (List<CommonEntity>)request.getAttribute("page.mn07.taxTransfer");	
  List<CommonEntity> taxTotal = (List<CommonEntity>)request.getAttribute("page.mn07.taxTotal"); 

  String first_date = (String)request.getAttribute("page.mn07.first_date");
  String last_date =  (String)request.getAttribute("page.mn07.last_date");

  String last_year = "";
	String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");

	int last_int = Integer.parseInt(this_year) - 1;
	last_year = String.valueOf(last_int);  
  String acc_year = request.getParameter("acc_year");              //ȸ�迬��
  String acc_month  = request.getParameter("acc_month");           //��ȸ����
    
  if (acc_month.equals("")) {   // ȸ����� �������� ������� : ����� - 1��
	  acc_month  = TextHandler.formatDate(TextHandler.replace(TextHandler.getPrevMonthDate(StringUtil.getCurrentDate(),"yyyyMMdd"),"/",""),"yyyyMMdd","MM");
  }

  String date = request.getParameter("date");
  if (date.equals("")){
    date = TextHandler.formatDate(StringUtil.getCurrentDate(),"yyyyMMdd","yyyy�� MM�� dd��");
  }

  String sunap_date = ""; 
  String today = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMM");  // ���� ���
  
  if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=�����������հ�ǥ_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
.report td.re01 {font-size:10pt;}
.report td.re02 {font-size:9pt;}

@media print {
  .noprint {
	  display:none;
	}
}
</style>
<%}%>
<script language="javascript" src="../js/base.js"></script>
<script language="javascript" src="../js/calendar.js"></script>
<script language="javascript" src="../js/tax_common.js"></script>
<script language="javascript">
      
function init() {
  typeInitialize();
}	
function goSearch() {
  var form = document.sForm;

  if(form.acc_year.value + form.acc_month.value  >=  form.today.value){
      alert("���������� ��ȸ �����մϴ�.");
      return;
  }
	/*
	form.action = "IR070710.etax";
	form.cmd.value = "taxTotal";
    form.saveFile.value = "";
	form.target = "_self";
	eSubmit(form);
	*/
	
	form.acc_date.value = form.acc_year.value + form.acc_month.value + "01";
	form.cmd.value = "dayReportAll";
	pageurl = "/admin/oz51/IR070710.etax";
	funcOpenWindow(form,'post','NeWreport',pageurl , 10,10, 1000, 1000, 0, 0, 0, 0, 1);
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
<form name="sForm" method="post" action="IR070710.etax">
<input type="hidden" name="cmd" value="taxTotal">
<input type="hidden" name="saveFile" value="<%=saveFile%>">
<input type="hidden" name="today" value="<%=today%>"><!-- ������-->
<input type="hidden" name="acc_date" value="">

<%if(!saveFile.equals("1")){%>
<table width="993" border="0" cellspacing="0" cellpadding="0">
  <tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40"><div class="noprint"><img src="../img/title7_14.gif"></div></td>
	</tr>
	<tr> 
		<td width="18">&nbsp;</td>
		<td width="975" height="40">
      <div class="noprint">
			<table width="100" border="0" cellspacing="0" cellpadding="0" background="../img/box_bg.gif">
				<tr> 
					<td><img src="../img/box_top.gif" width="964" height="11"></td>
				</tr>
				<tr> 
					<td> 
						<table width="964" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr> 
								<td width="880">
									<span style="width:20px"></span>
                  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                  ȸ�迬��
                  <select name="acc_year" iValue="<%=request.getParameter("acc_year")%>">
                    <option value="<%=this_year%>"><%=this_year%></option>
                    <option value="<%=last_year%>"><%=last_year%></option>
                  </select>
                  
                  <span style="width:50px"></span>
                  <img src="../img/board_icon1.gif" width="8" height="8" align="absmiddle"> 
                  ��ȸ��� ��&nbsp;
                  <select name="acc_month" iValue="<%=acc_month%>">
                    <option value="01">1��</option>
                    <option value="02">2��</option>
                    <option value="03">3��</option>
                    <option value="04">4��</option>
                    <option value="05">5��</option>
                    <option value="06">6��</option>
                    <option value="07">7��</option>
                    <option value="08">8��</option>
                    <option value="09">9��</option>
                    <option value="10">10��</option>
                    <option value="11">11��</option>
                    <option value="12">12��</option>
                  </select>
								</td>
								<td>
									<img src="../img/btn_search.gif" alt="��ȸ" onClick="goSearch()" style="cursor:hand" align="absmiddle">  
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr> 
					<td><img src="../img/box_bt.gif" width="964" height="10"></td>
				</tr>
			</table>
      </div>
		</td>
	</tr>
	<tr height="20">
		<td width="18">&nbsp;</td>
		<td width="975">&nbsp;</td>
	</tr>
</table>
<% } %>

<!-- ���Ա� ��ü�� ������ -->
  <%
   if (!acc_year.equals("") && !request.getParameter("acc_month").equals("")) { 
  %>   

<table width="993" border="0" cellspacing="0" cellpadding="0">   
 <tr>
    <td style="padding-left:55px;">
      <div id="pDiv">
      <table width="650" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="50"> 
          <td colspan="5"> 
            <div align="center"><font size="5"><b><u>���Ա� ��ü�� ������</u></b></font></div>
          </td>
        </tr>
        <tr height="70"> 
          <td colspan="3" class="re01">
               <���� ��2-1ȣ ����>
          </td>   
          <td colspan="2" class="re01" align="right">
               <%=date%>
          </td>      
        </tr>
        <!-- ������� -->
        <tr height="50"> 
          <td class="re01" colspan="5">
            <div align="left"><b>��걤���� ������ϻ���� ����¡���� ����</b></div>
          </td>
        </tr>
        <tr height="35"> 
          <td class="re01" colspan="5">
            <div align="left"><%=acc_year%>�� <%=acc_month%>���� ���� ���Ա��� �Ʒ��� ���� �и��Ͽ� �ش� ȸ��� ���� ��ü �Ͽ����� �뺸�մϴ�.</div>
          </td>
        </tr>
        <tr height="35"> 
          <td class="re01" colspan="5">
            <div align="left">����¡���� �� : ��걤���� ������ϻ���� ����¡����</div>
          </td>
        </tr>
        <tr height="35">
          <td class="re01" colspan="5">
            <div align="left"><b>����¡���� ��ȣ : 160490</b></div>
          </td>
        </tr>
        <tr>
          <td class="re01" colspan="3">
            <div align="left">(�Ϲ�ȸ�� �����Ұ� ���¹�ȣ)</div>
          </td>
          <td class="re01" colspan="2">
            <div align="right">(���� : ��)</div>
          </td>
        </tr>
      </table>
      <table width="650" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
         <%
           for (int i=0; i < taxTransfer.size(); i++) {
             CommonEntity rowData = (CommonEntity)taxTransfer.get(i);
      	 %>
        <tr height="35">
          <td rowspan="2" class="re01" width="125">
            <div align="center">��   ��<br>(�����ȣ)</div>
          </td>
          <td colspan="3" class="re01" width="400">
            <div align="center">Ư��ȸ����� ��ü[B]</div>
          </td>
          <td rowspan="2" class="re01" width="125">       
            <div align="center">�Ϲ�ȸ�����ü<br>(���⵵�й�����)<br>[A-B]</div>
          </td>
        </tr>
        <tr height="35">
          <td class="re01" width="140">
            <div align="center">���������Ѿ�[A]</div>
          </td>
          <td class="re01" width="130">
            <div align="center">���汳���翩�ݰ���<br>(021186)</div>
          </td>
          <td class="re01" width="130">
            <div align="center">��Ư������<br>(134921-4)</div>
          </td> 
        </tr>
        <tr height="35">
          <td align="center" class="re01">������<br>(341)</td>
          <td align="right"  class="re01">-</td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">��Ÿ������<br>(345)</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td class="re01">&nbsp;</td>
          <td class="re01">&nbsp;</td>
          <td class="re01">&nbsp;</td>
          <td class="re01">&nbsp;</td>
          <td class="re01">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">��Ư��<br>(371)</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">��Ÿ��Ư��<br>(372)</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">��Ÿ������<br>(322)</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re01">-</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">�հ�<br>(�����)</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td class="re02">&nbsp;</td>
        </tr>
        <tr height="35">
          <td align="center" class="re01">�հ�<br>(����)</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("END_DATE_TAX"))%></td>
          <td class="re02">&nbsp;</td>
          <td align="right" class="re02">&nbsp;<%=StringUtil.formatNumber(rowData.getString("END_DATE_TAX"))%></td>
          <td class="re02">&nbsp;</td>
        </tr>
        <% } %>
      </table>
      <table width="650" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="100">
          <td></td>
        </tr>
        <tr height="50">
          <td class="re01"><div align="center"><%=date%><div></td>
        </tr>
        <tr height="25">
          <td class="re01"><div align="right">�泲���� ����û����<div></td>
        </tr>
        <tr height="25">
          <td class="re01"><div align="right">�� �� �� �� �� �� ��</div></td>
        </tr>
      </table>
      <br>
      <br>
      <br>
      <br>

      <!-- �������� ���հ�ǥ -->

      <table width="650" border="0" cellspacing="0" cellpadding="1" class="report">
        <tr height="100"> 
          <td class="re01"> 
            <div align="center"><font size="5"><b><u>�������� ���հ�ǥ</u></b></font></div>
          </td>
        </tr>
      </table>
      <table width="650" border="0" cellspacing="2" cellpadding="1" class="report">
        <tr height="30"> 
          <td class="re01">
            <div align="left"><font size="2"><b>��걤���� ������ϻ���� ����¡���� ����</b></font></div>
          </td>
        </tr>
        <tr height="25"> 
          <td class="re01">
            <div align="left"><%=acc_year%>�� <%=acc_month%>���� ������ ���Ա� ������ �Ʒ��� ���� �뺸�մϴ�.</div>
          </td>
        </tr>
        <tr height="20"> 
          <td class="re01">
            <div align="right">�泲���� ����û����</div>
          </td>
        </tr>
        <tr height="20"> 
          <td class="re01">
            <div align="right">�� �� �� �� �� �� ��</div>
          </td>
        </tr>
        <tr height="20"> 
          <td class="re01">
            <div align="center">(���Ⱓ: <%=TextHandler.formatDate(first_date,"yyyyMMdd","yyyy.MM.dd")%>~<%=TextHandler.formatDate(last_date,"yyyyMMdd","yyyy.MM.dd")%>)</div>
          </td>
        </tr>
      </table>

      <table width="650" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="report">
        <tr height="25">
          <td class="re01"><div align="center">��������</div></td>
          <td class="re01"><div align="center">�����հ�</div></td>
          <td class="re01"><div align="center">��ϱ�����</div></td>
          <td class="re01"><div align="center">��ϳ�Ư��</div></td>
          <td class="re01"><div align="center">������</div></td>
        </tr>
         <% 
           for (int i=0; i < taxTotal.size(); i++) {
             CommonEntity rowData = (CommonEntity)taxTotal.get(i);

               if(rowData.getString("TMP_DATE").equals("")){
                 sunap_date = "�� ��";
               }else{
                 sunap_date = rowData.getString("TMP_DATE");
               }
         %>
        <tr height="25">
          <td class="re01" align="center">&nbsp;<%=sunap_date%></td>
          <td class="re02" align="right">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td>&nbsp;</td>
          <td class="re02" align="right">&nbsp;<%=StringUtil.formatNumber(rowData.getString("SPECIAL_TAX"))%></td>
          <td>&nbsp;</td>         
        </tr>
      <% } %>
      </table>        
      </div>  
      
    </td>
  </tr>
</table>

<div class="noprint">
<table width="650" border="0" align="center" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="650" align="center"> 
            <%if(saveFile.equals("")){%>
			<table width="650" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="absmiddle">
						<img src="../img/btn_print.gif" alt="�μ�"  onClick="goPrint()" style="cursor:hand" align="absmiddle">		  
					</td>
				</tr>
			</table>
            <%}%>
		</td>
	</tr>
</table>
</div>

<%}else{%>
<table width="1000" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>
<div class="noprint">
<%if(!saveFile.equals("1")){%>
<table width="1000">
	<tr height="100">
		<td width="800" style="font-size:8px;"><img src="../img/footer.gif" width="1000" height="72"></td>
	</tr>
</table>
<% } %>
</div>
</form>
</body>
</html>
