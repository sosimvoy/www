<%
/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR070511.jsp
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-09
* ���α׷�����		: �ϰ�/���� > �������� ������ȸ
****************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.util.*"			%>
<%@ page import = "com.etax.entity.*"	%>
<%@ page import = "com.etax.util.*"		%>
<%@ taglib uri="/WEB-INF/tlds/etax.tlds" prefix="etax" %>
<%
	List<CommonEntity> jigupReportList = (List<CommonEntity>)request.getAttribute("page.mn07.jigupReportList");
	List<CommonEntity> jigupCnt = (List<CommonEntity>)request.getAttribute("page.mn07.jigupCnt");
  CommonEntity semokName = (CommonEntity)request.getAttribute("page.mn07.semokName");
	
	 String saveFile = request.getParameter("saveFile");
  
    if(saveFile.equals("1")){
        response.setContentType("application/vnd.ms-excel;charset=euc-kr");
        response.setHeader("Content-Disposition", "inline; filename=�������� ����_" +new java.sql.Date(System.currentTimeMillis()) + ".xls");   
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
<style type="text/css">
.basic {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333}
.line {  font-family: "Arial", "Helvetica", "sans-serif"; font-size: 9pt; color: #333333; text-decoration: underline}
.basic.td { border: 1px solid red; }
.td.solid-white{ border: 5px solid white; }

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
<script language="javascript" src="../js/report.js"></script>	
<script language="javascript">
	

		function goSave(val){
				var form = document.sForm;
				form.saveFile.value = val;
				form.action = "IR070511.etax";
				form.target = "_self";
				form.submit();
		}
		function goPrint() {
				factory.printing.header = ""; // Header�� �� ����  
				factory.printing.footer = ""; // Footer�� �� ����  
				factory.printing.portrait = true; // false �� �����μ�, true �� ���� �μ�  
				factory.printing.leftMargin = 1.0; // ���� ���� ������  
				factory.printing.topMargin = 1.0; // �� ���� ������  
				factory.printing.rightMargin = 1.0; // ������ ���� ������  
				factory.printing.bottomMargin = 1.0; // �Ʒ� ���� ������  
				factory.printing.Print(true); // ����ϱ� 
		}
</script>
</head>
<body bgcolor="#FFFFFF"  topmargin="0" leftmargin="0" onload="init();">
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="../css/smsx.cab#Version=6,5,439,50"></object>
<form name="sForm" method="post" action="IR070511.etax">
<input type="hidden" name="cmd" value="jigupReportList">
<input type="hidden" name="year" value="<%=request.getParameter("year")%>">
<input type="hidden" name="su_str_date" value="<%=request.getParameter("su_str_date")%>">
<input type="hidden" name="su_end_date" value="<%=request.getParameter("su_end_date")%>">
<input type="hidden" name="accType" value="<%=request.getParameter("accGubun")%>">
<input type="hidden" name="partCode" value="<%=request.getParameter("partCode")%>">
<input type="hidden" name="accCode" value="<%=request.getParameter("accCode")%>">
<input type="hidden" name="saveFile" value="<%=saveFile%>">

<div class="noprint">

   <%if(saveFile.equals("")){%>
			<table width="613" border="0" cellspacing="0" cellpadding="0" class="btn" align="center">
				<tr> 
					<td>
						<img src="../img/btn_excel.gif" alt="Excel" onClick="goSave('1')" style="cursor:hand" align="right">
						<!--<img src="../img/btn_pdf.gif" alt="PDF" onClick="goPDF()" style="cursor:hand" align="right">-->
						<img src="../img/btn_print.gif" alt="�μ�"  onClick="goPrint();" style="cursor:hand" align="right">		  
					</td>
				</tr>
			</table>
  <%}%>

</div>
<br>
<div id="box" align="center">
<%
if (jigupReportList.size() > 0 && jigupReportList != null) { 
%>

			<table width="613" border="0" cellspacing="0" cellpadding="1">
				<tr> 
					<td height="60"> 
						<div align="center"><font size="4"><b>������������</b></font></div>
					</td>
				</tr>
			</table>

			<table width="613" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="359"> 
						<table width="605" border="0" cellspacing="2" cellpadding="1" class="basic">
							<tr> 
								<td height="11" colspan="2">
                  <b>ȸ�迬�� : <%=request.getParameter("year")%>��</b>
                </td>
							</tr>
							<tr> 
								<td height="11">
                  <b>ȸ���� : <%=TextHandler.formatDate(request.getParameter("su_str_date"),"yyyyMMdd","yyyy�� MM�� dd��")%> ~  <%=TextHandler.formatDate(request.getParameter("su_end_date"),"yyyyMMdd","yyyy�� MM�� dd��")%></b>
                </td>
								<td height="11">
									<div align="right"><b>ȸ�� :<%=semokName.getString("SEMOKNAME")%> �μ� : <%=semokName.getString("PARTNAME")%></b>
								</td>
							</tr>
						</table>
						<table width="605" border="1" cellspacing="0" cellpadding="2" class="basic" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
							<tr height="2"> 
								<td width="80"> 
									<div align="center">���� </div>
								</td>
								<td width="170"> 
									<div align="center">���޹�ȣ </div>
								</td>
								<td width="170"> 
									<div align="center">ä�ּ��� </div>
								</td>
								<td width="180"> 
									<div align="center">���ޱݾ�</div>
								</td>
							</tr>
							<% 				
								for (int i=0; jigupReportList != null && i < jigupReportList.size(); i++) {
									CommonEntity data = (CommonEntity)jigupReportList.get(i);
							%>
							<tr height="2"> 
								<td> 
									<div align="center"><%=data.getString("GBN")%></div>
								</td>
								<td> 
									<div align="right"><%=data.getString("M030_ORDERNO")%></div>
								</td>
								<td> 
									<div align="center"><%=data.getString("M030_ORDERNAME")%></div>
								</td>
								<td> 
									<div align="right"><%=TextHandler.formatNumber(data.getString("M030_AMT"))%></div>
								</td>
							</tr>
							<% 
								if (i>0 && i%45 == 0 ) { 
							%>
					</table>
					<br><br><br><br>
					<table width="613" border="0" cellspacing="0" cellpadding="1">
						<tr>
						 <td height="55"></td>
						</tr>
						<tr> 
							<td height="60"> 
								<div align="center"><font size="4"><b>������������</b></font></div>
							</td>
						</tr>
					</table>
					<table width="605" border="0" cellspacing="2" cellpadding="1" class="basic">
							<tr> 
								<td height="11" colspan="2">
                  <b>ȸ�迬�� : <%=request.getParameter("year")%>��</b>
                </td>
							</tr>
							<tr>
								<td height="11"><b>ȸ���� : <%=TextHandler.formatDate(request.getParameter("su_str_date"),"yyyyMMdd","yyyy�� MM�� dd��")%> ~  <%=TextHandler.formatDate(request.getParameter("su_end_date"),"yyyyMMdd","yyyy�� MM�� dd��")%></b></td>
								<td height="11">
									<div align="right"><b>ȸ�� :<%=semokName.getString("SEMOKNAME")%> �μ� : <%=semokName.getString("PARTNAME")%></b></div>
								</td>
							</tr>
						</table>
						<table width="605" border="1" cellspacing="0" cellpadding="2" class="basic" style='border-collapse:collapse; 'bordercolor='#000000' class="basic">
							<tr height="2"> 
								<td width="80"> 
									<div align="center">���� </div>
								</td>
								<td width="170"> 
									<div align="center">���޹�ȣ </div>
								</td>
								<td width="170"> 
									<div align="center">ä�ּ��� </div>
								</td>
								<td width="180"> 
									<div align="center">���ޱݾ�</div>
								</td>
							</tr>
								<%						
									}
								}
							%>
						</table>
					</td>
				</tr>
			</table>
			<br>
			<%}else{%>
			<table width="613" align="center" border="0" cellpadding="0" cellspacing="0">
					<tr height="50">
							<td align="center"><b>�ش��ڷᰡ �����ϴ�.</b></td>
					</tr>
			</table> 
<%}%>
</div>
</form>

<p><span class="count"></span> </p>
</body>
</html>
