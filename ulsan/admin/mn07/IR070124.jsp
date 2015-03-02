<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070124.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-13
* ���α׷�����		: �ϰ�/���� > ������ȸ > ���Լ����������������
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
  //int i = 0;

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
    
    String today = TextHandler.getCurrentDate();
    
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=���ϼ�������� ���޸�ɼ�_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
<link href="style.css" media="print" rel="stylesheet" type="text/css" />  <!--���� �������� ����Ʈ CSS-->
<link href="style.css" media="screen" rel="stylesheet" type="text/css" />  <!--���� �������� ����Ʈ CSS-->
<style>
<!--
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}

    :root u {font-size:15pt; padding-left:230px;} /*:root �����Ǵ� �ٸ� ��� ������ */       
		 html u {font-size:15pt; padding-left:230px;} /*IE8 ���� */   
 * + html u {font-size:15pt; padding-left:230px;} /*IE7 ���� */   
 *   html u {font-size:15pt; padding-left:230px;} /*IE6 ���� */ 

@media print {
  .noprint {
	  display:none;
	}
}
-->
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
		factory.printing.portrait = true; // false �� �����μ�, true �� ���� �μ�  
		factory.printing.leftMargin = 1.0; // ���� ���� ������  
		factory.printing.topMargin = 1.0; // �� ���� ������  
		factory.printing.rightMargin = 1.0; // ������ ���� ������  
		factory.printing.bottomMargin = 0.5; // �Ʒ� ���� ������  
		factory.printing.Print(true) // ����ϱ� 
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

</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();" style="padding-left:20px">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070124.etax">
<input type="hidden" name="cmd" value="dayReport14">
<input type="hidden" name="acc_year" value="<%=request.getParameter("acc_year")%>">
<input type="hidden" name="acc_date" value="<%=request.getParameter("acc_date")%>">
<input type="hidden" name="acc_type" value="<%=request.getParameter("acc_type")%>">
<input type="hidden" name="part_code" value="<%=request.getParameter("part_code")%>">
<input type="hidden" name="acc_code" value="<%=request.getParameter("acc_code")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">
<input type="hidden" name="cpage"      value="">
<div id="box" align="center">
<%
if (reportList.size() > 0 && reportList != null) { 
%>
<div class="noprint">
<table width="713" border="0" cellspacing="0" cellpadding="1" class="basic">
	<tr> 
		<td width="713"> 
  <%if(saveFile.equals("")){%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="btn">
				<tr> 
					<td  align="right">
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

<table width="713" border="0" cellspacing="0" cellpadding="1">
<%
	  String accName = "";
	  int j = 0;

		for (int i=0; i < reportList.size(); i++) {
			CommonEntity rowData = (CommonEntity)reportList.get(i);			
			if (i == 0 ) {
%>
  <tr height="100"> 
    <td colspan="4"> 
      <b><u>���Լ�������� ��������</u></b>
    </td>
    <td align="right" valign="middle"> 
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
       <%}%>
     </td>
  </tr>
</table>

<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td colspan="2">
      <div align="center">
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>�Ϻ�</div>
    </td>
  </tr>
  <tr> 
    <td width="257" height="2">ȸ�迬��:<%=request.getParameter("acc_year")%>��<br>
    </td>
    <td width="500" height="2"> 
      <div align="right"><%=rowData.getString("M360_ACCNAME")%></div>
    </td>
  </tr>
</table>
<table width="713" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
  <tr> 
    <td width="150" height="2"> 
      <div align="center">���������ȣ</div>
    </td>
    <td width="143" height="2"> 
      <div align="center">ä�ּ���</div>
    </td>
    <td width="100" height="2"> 
      <div align="center">�Ǽ�</div>
    </td>
    <td width="170" height="2"> 
      <div align="center">�ݾ�</div>
    </td>
    <td width="150" height="2"> 
      <div align="center">���</div>
    </td>
  </tr>
	 <% 
  	} //(if == 0) end
  %>
  <% 
	  if(!"".equals(accName) && !accName.equals(rowData.getString("M360_ACCNAME"))) j = 0;   // ������ �޶����� ���ȣ�� ���� ä���Ͽ� �װ� �������� 45�� ����. �������� 1�϶� �������� ������ ���������� 45���� �����.
	  j++;
	   if ( i>0 && j%45 == 1 || (!"".equals(accName) && !accName.equals(rowData.getString("M360_ACCNAME"))) ) {  // ������ ��ȯ Ÿ��Ʋ

  %>
</table>
<table width="713" border="0" cellspacing="0" cellpadding="4" class="basic">
  <tr height="1000"> 
    <td height="25" width="1036"> 
      <div align="center">���� ���� ���� �����Ͽ���.<br>
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>��</div>
    </td>
  </tr>
</table>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td colspan="2">��걤���� ����� ����</td>
    <td colspan="2"> 
      <div align="right"> �� �� �� �� �� �� ��</div>
    </td>
    <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
    <td width="80"> 
    <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
    </td>
    <% } %>
  </tr>
</table>
<br style="page-break-before:always;"/> <!--���� �������� ����Ʈ -->


<!--���� ������ Ÿ��Ʋ start -->
<table width="713" border="0" cellspacing="0" cellpadding="1">
  <tr height="100"> 
    <td colspan="4"> 
      <b><u>���Լ�������� ��������</u></b>
    </td>
    <td align="right" valign="middle"> 
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
       <%}%>
     </td>
  </tr>
</table>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td colspan="2">
      <div align="center">
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>�Ϻ�</div>
    </td>
  </tr>
  <tr> 
    <td width="257" height="2">ȸ�迬��:<%=request.getParameter("acc_year")%>��<br>
    </td>
    <td width="500" height="2"> 
      <div align="right"><%=rowData.getString("M360_ACCNAME")%></div>
    </td>
  </tr>
</table>
<table width="713" border="1" cellspacing="0" cellpadding="2" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">

  <tr> 
    <td width="150" height="2"> 
      <div align="center">���������ȣ</div>
    </td>
    <td width="143" height="2"> 
      <div align="center">ä�ּ���</div>
    </td>
    <td width="100" height="2"> 
      <div align="center">�Ǽ�</div>
    </td>
    <td width="170" height="2"> 
      <div align="center">�ݾ�</div>
    </td>
    <td width="150" height="2"> 
      <div align="center">���</div>
    </td>
  </tr>
	 <%
		}//	if (i > 0 && !accName.equals(rowData.getString("M360_ACCNAME")) ) {... ������ ��ȯ End 	
   %>
   <tr height="15">
		<td width="150" height="2"> 
			<div align="center">
			 <%
		 	
				if( rowData.getString("M040_ORDERNO").equals("")){
					out.println("�Ұ�");
				}else{
					out.println(rowData.getString("M040_ORDERNO"));
				}
			 %>
			</div>
		</td> 
    <td width="143" height="2"> 
      <div align="center"><%=rowData.getString("M040_ORDERNAME")%></div>
    </td>
    <td width="100" height="2"> 
      <div align="center"><%=rowData.getString("TOT_CNT")%></div>
    </td>
    <td width="170" height="2"> 
      <div align="right"><%=StringUtil.formatNumber(rowData.getString("TOT_AMT"))%></div>
    </td>
    <td width="150" height="2">
      <div align="center">&nbsp;<%=rowData.getString("M040_NOTE")%></div>
    </td>
  </tr>
	 <%  
     accName = rowData.getString("M360_ACCNAME");//accName ȸ����� ��ƹ���
	}//end for (i=0; i < reportList.size(); i++) {... 
%>
</table>
<table width="713" border="0" cellspacing="0" cellpadding="4" class="basic">
  <tr height="1000"> 
    <td height="25" width="1036"> 
      <div align="center">���� ���� ���� �����Ͽ���.<br>
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","yyyy")%>�� <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","MM")%>��
      <%=TextHandler.formatDate(TextHandler.replace(request.getParameter("acc_date"),"-",""),"yyyyMMdd","dd")%>��</div>
    </td>
  </tr>
</table>
<table width="713" border="0" cellspacing="2" cellpadding="1" class="basic">
  <tr> 
    <td colspan="2">��걤���� ����� ����</td>
    <td colspan="2"> 
      <div align="right"> �� �� �� �� �� �� ��</div>
    </td>
    <% if(endState.getString("END_STATE").equals("Y")){  // �����ΰ�� (����)%>
    <td width="80"> 
    <img src="../../../report/seal/<%=endState.getString("F_NAME")%>" width="77" height="77" align="right">
    </td>
    <% } %>

  </tr>
</table>

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
<table width="710" border="0" cellpadding="0" cellspacing="0">
    <tr height="50">
        <td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
    </tr>
</table> 
<%}%>
</div>
</form>
</body>
</html>